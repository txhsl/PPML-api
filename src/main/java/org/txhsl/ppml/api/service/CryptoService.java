package org.txhsl.ppml.api.service;

import io.ipfs.multibase.Base58;
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
import org.springframework.stereotype.Service;
import org.txhsl.ppml.api.service.crypto.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
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
        LOGGER.info("Symmetric key generated: " + Base58.encode(((Scalar) cp.get(1)).toBytes()));
        return ((Capsule) cp.get(0)).toBytes();
    }

    public byte[] encryptHash(byte[] capsule, BigInteger prv, byte[] plaintext) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        List<Scalar> symmetricKeys = Proxy.decapsulate(Capsule.fromBytes(capsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = symmetricKeys.get(0).toBytes();
        LOGGER.info("Symmetric key A decrypted: " + Base58.encode(key));
        return aesEncrypt(key, plaintext);
    }

    public String encryptFile(byte[] capsule, BigInteger prv, File raw) throws Exception {
        List<Scalar> symmetricKeys = Proxy.decapsulate(Capsule.fromBytes(capsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = symmetricKeys.get(1).toBytes();
        LOGGER.info("Symmetric key B decrypted: " + Base58.encode(key));

        return aesEncFile(key, raw);
    }

    public byte[] reEncrypt(byte[] capsule, BigInteger prv, ECPoint pub) throws NoSuchAlgorithmException {
        ReEncryptionKey rk = Proxy.generateReEncryptionKey(PrivateKey.fromBytes(prv.toByteArray()), new PublicKey(new GroupElement(new Curve("secp256k1"), pub)));
        LOGGER.info("ReEncryption key generated: " + Base58.encode(rk.toBytes()));
        return Proxy.reEncryptCapsule(Capsule.fromBytes(capsule), rk).toBytes();
    }

    public byte[] getReKey(BigInteger prv, ECPoint pub) throws NoSuchAlgorithmException {
        return Proxy.generateReEncryptionKey(PrivateKey.fromBytes(prv.toByteArray()), new PublicKey(new GroupElement(new Curve("secp256k1"), pub))).toBytes();
    }

    public byte[] decryptHash(byte[] reCapsule, BigInteger prv, byte[] cipher) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        List<Scalar> reSymmetricKeys = Proxy.decapsulate(Capsule.fromBytes(reCapsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = reSymmetricKeys.get(0).toBytes();
        LOGGER.info("Symmetric key A decrypted: " + Base58.encode(key));
        return aesDecrypt(key, cipher);
    }

    public String decryptFile(byte[] reCapsule, BigInteger prv, File cipher, String name) throws Exception {
        List<Scalar> reSymmetricKeys = Proxy.decapsulate(Capsule.fromBytes(reCapsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = reSymmetricKeys.get(1).toBytes();
        LOGGER.info("Symmetric key B decrypted: " + Base58.encode(key));

        return aesDecFile(key, cipher, name);
    }

    public byte[] eccDecrypt(BigInteger prv, byte[] cipher) throws InvalidCipherTextException, IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(cipher);
        byte[] ephemBytes = new byte[2*((curve.getCurve().getFieldSize()+7)/8) + 1];
        is.read(ephemBytes);
        ECPoint ephem = curve.getCurve().decodePoint(ephemBytes);
        byte[] IV = new byte[KEY_SIZE /8];
        is.read(IV);
        byte[] cipherBody = new byte[is.available()];
        is.read(cipherBody);

        EthereumIESEngine iesEngine = makeIESEngine(false, ephem, prv, IV);
        byte[] plaintext = iesEngine.processBlock(cipherBody, 0, cipherBody.length);

        LOGGER.info("Cipher " + Base58.encode(cipher) + " decrypted, plaintext: " + new String(plaintext));

        return plaintext;
    }

    public byte[] eccEncrypt(ECPoint toPub, byte[] plaintext) throws InvalidCipherTextException, IOException {
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

        byte[] cipher = iesEngine.processBlock(plaintext, 0, plaintext.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(pub.getEncoded(false));
        bos.write(IV);
        bos.write(cipher);

        LOGGER.info("Plaintext " + new String(plaintext) + " encrypted, cipher: " + Base58.encode(bos.toByteArray()));

        return bos.toByteArray();
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

    public byte[] aesKeyGen() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        LOGGER.info("AESKey generated: " + Base58.encode(key.getEncoded()));

        return key.getEncoded();
    }

    public byte[] aesEncrypt(byte[] key, byte[] plaintext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipher = c.doFinal(plaintext);

        LOGGER.info("Plaintext " + new String(plaintext) + " encrypted, cipher: " + Base58.encode(cipher));

        return cipher;
    }

    public byte[] aesDecrypt(byte[] key, byte[] cipher) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plaintext = c.doFinal(cipher);

        LOGGER.info("Cipher " + Base58.encode(cipher) + " decrypted, plaintext: " + new String(plaintext));

        return plaintext;
    }

    public String aesEncFile(byte[] key, File raw) throws Exception {
        FileInputStream in = new FileInputStream(raw);

        byte[] salt = new byte[8];
        SecureRandom srand = new SecureRandom();
        srand.nextBytes(salt);

        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        AlgorithmParameters params = cipher.getParameters();

        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

        File encrypted = new File(raw.getAbsolutePath() + ".fenc");
        FileOutputStream out = new FileOutputStream(encrypted);

        byte[] input = new byte[64];
        int bytesRead;
        while ((bytesRead = in.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }

        byte[] output = cipher.doFinal();
        if (output != null) {
            out.write(output);
        }

        in.close();
        out.flush();
        out.write(salt);
        out.write(iv);
        out.close();

        return encrypted.getAbsolutePath();
    }

    public String aesDecFile(byte[] key, File encrypted, String name) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(encrypted, "rw");
        byte[] salt = new byte[8];
        byte[] iv = new byte[16];

        raf.seek(encrypted.length() - (salt.length + iv.length));
        raf.read(salt, 0, salt.length);
        raf.seek(encrypted.length() - (iv.length));
        raf.read(iv, 0, iv.length);
        raf.setLength(encrypted.length() - (salt.length + iv.length));

        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        FileInputStream in = new FileInputStream(encrypted);
        File plain = new File(encrypted.getParent() + "/" + name);
        FileOutputStream out = new FileOutputStream(plain);

        byte[] b = new byte[64];
        int read;
        while ((read = in.read(b)) != -1) {
            byte[] output = cipher.update(b, 0, read);
            if (output != null) {
                out.write(output);
            }
        }

        try {
            byte[] output = cipher.doFinal();
            if (output != null)
                out.write(output);
        } catch (Exception e) {
            raf.seek(encrypted.length());
            raf.write(salt);
            raf.seek(encrypted.length());
            raf.write(iv);
            raf.close();
        }

        raf.seek(encrypted.length());
        raf.write(salt);
        raf.seek(encrypted.length());
        raf.write(iv);
        raf.close();
        in.close();
        out.flush();
        out.close();

        return plain.getAbsolutePath();
    }
}
