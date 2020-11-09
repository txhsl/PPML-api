package org.txhsl.ppml.api.service;

import io.ipfs.multibase.Base58;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.*;
import org.spongycastle.math.ec.ECPoint;
import org.springframework.stereotype.Service;
import org.txhsl.ppml.api.service.crypto.pre.*;

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
        LOGGER.info("DataSet key generated: " + Base58.encode(((Scalar) cp.get(1)).toBytes()));
        return ((Capsule) cp.get(0)).toBytes();
    }

    public BigInteger prvGen(byte[] capsule, BigInteger prv) throws NoSuchAlgorithmException {
        List<Scalar> symmetricKey = Proxy.decapsulate(Capsule.fromBytes(capsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] seed = symmetricKey.get(0).toBytes();
        BigInteger prvkey = PrivateKey.generate(new Curve("secp256k1"), seed).getValue().getValue();
        LOGGER.info("Role key generated: " + Base58.encode(prvkey.toByteArray()));
        return prvkey;
    }

    public byte[] aesEncryptHash(byte[] capsule, BigInteger prv, byte[] plaintext) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        List<Scalar> symmetricKey = Proxy.decapsulate(Capsule.fromBytes(capsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = symmetricKey.get(0).toBytes();
        LOGGER.info("DataSet key decrypted: " + Base58.encode(key));
        return aesEncrypt(key, plaintext);
    }

    public String aesEncryptFile(byte[] capsule, BigInteger prv, File raw) throws Exception {
        List<Scalar> symmetricKey = Proxy.decapsulate(Capsule.fromBytes(capsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = symmetricKey.get(0).toBytes();
        LOGGER.info("DataSet key decrypted: " + Base58.encode(key));

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

    public byte[] aesDecryptHash(byte[] reCapsule, BigInteger prv, byte[] cipher) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        List<Scalar> reSymmetricKey = Proxy.decapsulate(Capsule.fromBytes(reCapsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = reSymmetricKey.get(0).toBytes();
        LOGGER.info("DataSet key decrypted: " + Base58.encode(key));
        return aesDecrypt(key, cipher);
    }

    public String aesDecryptFile(byte[] reCapsule, BigInteger prv, File cipher, String name) throws Exception {
        List<Scalar> reSymmetricKey = Proxy.decapsulate(Capsule.fromBytes(reCapsule), PrivateKey.fromBytes(prv.toByteArray()));
        byte[] key = reSymmetricKey.get(0).toBytes();
        LOGGER.info("DataSet key decrypted: " + Base58.encode(key));

        return aesDecFile(key, cipher, name);
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
        LOGGER.info("Target file: " + raw.getAbsolutePath() + ", key: " + Base58.encode(key));

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
        LOGGER.info("Target file: " + encrypted.getAbsolutePath() + ", key: " + Base58.encode(key));

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
