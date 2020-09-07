package org.txhsl.ppml.api.service;

import org.ethereum.ConcatKDFBytesGenerator;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.EthereumIESEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.modes.SICBlockCipher;
import org.spongycastle.crypto.params.*;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import org.txhsl.ppml.api.service.crypto.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class CryptoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoService.class);

    public static final int KEY_SIZE = 128;
    public static ECDomainParameters curve;
    public static final X9ECParameters IES_CURVE_PARAM = SECNamedCurves.getByName("secp256k1");

    public CryptoService() {
        curve = new ECDomainParameters(IES_CURVE_PARAM.getCurve(), IES_CURVE_PARAM.getG(), IES_CURVE_PARAM.getN(), IES_CURVE_PARAM.getH());
    }

    public byte[] encryptKeyGen(ECPoint toPub) throws NoSuchAlgorithmException {
        List<Object> cp = Proxy.encapsulate(new PublicKey(new GroupElement(new Curve("secp256k1"), toPub)));
        LOGGER.info("Symmetric key generated: " + Hex.toHexString(((Scalar) cp.get(1)).toBytes()));
        return ((Capsule) cp.get(0)).toBytes();
    }

    public String encrypt(byte[] capsule, BigInteger prv, String plaintext) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        Scalar symmetricKey = Proxy.decapsulate(Capsule.fromBytes(capsule), PrivateKey.fromBytes(prv.toByteArray()));
        LOGGER.info("Symmetric key decrypted: " + Hex.toHexString(symmetricKey.toBytes()));
        return aesEncrypt(Hex.toHexString(symmetricKey.getValue().toByteArray()), plaintext);
    }

    public byte[] reEncrypt(byte[] capsule, BigInteger prv, ECPoint pub) throws NoSuchAlgorithmException {
        ReEncryptionKey rk = Proxy.generateReEncryptionKey(PrivateKey.fromBytes(prv.toByteArray()), new PublicKey(new GroupElement(new Curve("secp256k1"), pub)));
        return Proxy.reEncryptCapsule(Capsule.fromBytes(capsule), rk).toBytes();
    }

    public String decrypt(byte[] reCapsule, BigInteger prv, String cipher) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        Scalar reSymmetricKey = Proxy.decapsulate(Capsule.fromBytes(reCapsule), PrivateKey.fromBytes(prv.toByteArray()));
        LOGGER.info("Symmetric key decrypted: " + Hex.toHexString(reSymmetricKey.toBytes()));
        return aesDecrypt(Hex.toHexString(reSymmetricKey.getValue().toByteArray()), cipher);
    }

    public String eccDecrypt(BigInteger prv, String cipher) throws InvalidCipherTextException, IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(Hex.decode(cipher));
        byte[] ephemBytes = new byte[2*((curve.getCurve().getFieldSize()+7)/8) + 1];
        is.read(ephemBytes);
        ECPoint ephem = curve.getCurve().decodePoint(ephemBytes);
        byte[] IV = new byte[KEY_SIZE /8];
        is.read(IV);
        byte[] cipherBody = new byte[is.available()];
        is.read(cipherBody);

        EthereumIESEngine iesEngine = makeIESEngine(false, ephem, prv, IV);
        byte[] plaintext = iesEngine.processBlock(cipherBody, 0, cipherBody.length);

        LOGGER.info("Cipher " + cipher + " decrypted, plaintext: " + new String(plaintext));

        return new String(plaintext);
    }

    public String eccEncrypt(ECPoint toPub, String plaintext) throws InvalidCipherTextException, IOException {
        byte[] raw = plaintext.getBytes();
        ECKeyPairGenerator eGen = new ECKeyPairGenerator();
        SecureRandom random = new SecureRandom();
        KeyGenerationParameters gParam = new ECKeyGenerationParameters(curve, random);

        eGen.init(gParam);

        byte[] IV = new byte[KEY_SIZE/8];
        new SecureRandom().nextBytes(IV);

        AsymmetricCipherKeyPair ephemPair = eGen.generateKeyPair();
        BigInteger prv = ((ECPrivateKeyParameters)ephemPair.getPrivate()).getD();
        ECPoint pub = ((ECPublicKeyParameters)ephemPair.getPublic()).getQ();
        EthereumIESEngine iesEngine = makeIESEngine(true, toPub, prv, IV);


        ECKeyGenerationParameters keygenParams = new ECKeyGenerationParameters(curve, random);
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        generator.init(keygenParams);

        ECKeyPairGenerator gen = new ECKeyPairGenerator();
        gen.init(new ECKeyGenerationParameters(ECKey.CURVE, random));

        byte[] cipher = iesEngine.processBlock(raw, 0, raw.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(pub.getEncoded(false));
        bos.write(IV);
        bos.write(cipher);

        String result = Hex.toHexString(bos.toByteArray());

        LOGGER.info("Plaintext " + plaintext + " encrypted, cipher: " + result);

        return result;
    }

    private EthereumIESEngine makeIESEngine(boolean isEncrypt, ECPoint pub, BigInteger prv, byte[] IV) {
        AESEngine aesFastEngine = new AESEngine();

        EthereumIESEngine iesEngine = new EthereumIESEngine(
                new ECDHBasicAgreement(),
                new ConcatKDFBytesGenerator(new SHA256Digest()),
                new HMac(new SHA256Digest()),
                new SHA256Digest(),
                new BufferedBlockCipher(new SICBlockCipher(aesFastEngine)));

        byte[] d = new byte[] {};
        byte[] e = new byte[] {};

        IESParameters p = new IESWithCipherParameters(d, e, KEY_SIZE, KEY_SIZE);
        ParametersWithIV parametersWithIV = new ParametersWithIV(p, IV);

        iesEngine.init(isEncrypt, new ECPrivateKeyParameters(prv, curve), new ECPublicKeyParameters(pub, curve), parametersWithIV);
        return iesEngine;
    }

    public String aesKeyGen() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        LOGGER.info("AESKey generated: " + Hex.toHexString(key.getEncoded()));

        return Hex.toHexString(key.getEncoded());
    }

    public String aesEncrypt(String key, String plaintext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKey = new SecretKeySpec(Hex.decode(key), "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipher = c.doFinal(plaintext.getBytes());

        LOGGER.info("Plaintext " + plaintext + " encrypted, cipher: " + Hex.toHexString(cipher));

        return Hex.toHexString(cipher);
    }

    public String aesDecrypt(String key, String cipher) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKey = new SecretKeySpec(Hex.decode(key), "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plaintext = c.doFinal(Hex.decode(cipher));

        LOGGER.info("Cipher " + cipher + " decrypted, plaintext: " + new String(plaintext));

        return new String(plaintext);
    }
}
