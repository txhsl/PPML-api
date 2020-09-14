package org.txhsl.ppml.api.controller;

import io.ipfs.multibase.Base58;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.txhsl.ppml.api.model.DataSetRequest;
import org.txhsl.ppml.api.model.Volume;
import org.txhsl.ppml.api.service.BlockchainService;
import org.txhsl.ppml.api.service.CryptoService;
import org.txhsl.ppml.api.service.IPFSService;
import org.txhsl.ppml.api.service.crypto.*;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/dataset")
public class DataSetController {

    private final BlockchainService blockchainService;
    private final IPFSService ipfsService;
    private final CryptoService cryptoService;

    private String current = "";

    public DataSetController(BlockchainService blockchainService, IPFSService ipfsService, CryptoService cryptoService) {
        this.blockchainService = blockchainService;
        this.ipfsService = ipfsService;
        this.cryptoService = cryptoService;
    }

    @PostMapping("/create")
    public DataSetRequest create(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        int count = 0;
        while(count < 10) {
            try {
                byte[] encryptedKey = cryptoService.encryptKeyGen(ecKey.getPubKeyPoint());
                blockchainService.createDataSet(Base58.encode(encryptedKey), Capsule.fromBytes(encryptedKey));

                request.setEncryptedKey(Base58.encode(encryptedKey));
                request.setCompleted(true);

                return request;
            } catch (UnsupportedOperationException e) {
                count++;
            }
        }
        throw new UnsupportedOperationException();
    }

    @PostMapping("/add")
    public DataSetRequest add(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String encryptedKey = request.getEncryptedKey();
        String hash = request.getHash();

        byte[] encryptedHash = cryptoService.encryptHash(Base58.decode(encryptedKey), ecKey.getPrivKey(), hash.getBytes());

        blockchainService.addVolume(encryptedKey, request.getName(), Base58.encode(encryptedHash));

        request.setEncryptedHash(Base58.encode(encryptedHash));
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/share")
    public DataSetRequest share(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String encryptedKey = request.getEncryptedKey();
        String toPub = request.getTo();

        ECKey to = ECKey.fromPublicOnly(Base58.decode(toPub));
        byte[] reEncryptKey = cryptoService.getReKey(ecKey.getPrivKey(), to.getPubKeyPoint());
        String toAddr = Numeric.prependHexPrefix(Hex.toHexString(to.getAddress()));
        blockchainService.shareKey(encryptedKey, toAddr, ReEncryptionKey.fromBytes(reEncryptKey));

        request.setReEncryptedKey(Base58.encode(reEncryptKey));
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/getReEncryptedKey")
    public DataSetRequest getReEncryptedKey(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();

        String reKey = blockchainService.getOwner(encryptedKey).equals(blockchainService.getCredentials().getAddress()) ?
                encryptedKey : Base58.encode(blockchainService.getReEncryptedKey(encryptedKey).toBytes());

        request.setReEncryptedKey(reKey);
        request.setCompleted(true);
        return request;
    }

    @PostMapping("/decryptHash")
    public DataSetRequest decryptHash(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String reEncryptedKey = request.getReEncryptedKey();
        String encryptedHash = request.getEncryptedHash();

        byte[] hash = cryptoService.decryptHash(Base58.decode(reEncryptedKey), ecKey.getPrivKey(), Base58.decode(encryptedHash));

        request.setHash(new String(hash));
        request.setCompleted(true);
        return request;
    }

    @PostMapping("/get")
    public DataSetRequest get(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
        this.current = encryptedKey;
        int amount = blockchainService.getAmount(encryptedKey);

        Volume[] volumes = new Volume[amount];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i = 1; i <= amount; i++) {
            String name = blockchainService.getVolumeName(encryptedKey, i);
            int time = blockchainService.getVolumeTime(encryptedKey, i);
            String encryptedHash = blockchainService.getVolumeHash(encryptedKey, i);

            volumes[i - 1] = new Volume(i, name, sdf.format(new Date(time * 1000L)), encryptedHash);
        }

        request.setOwner(blockchainService.getOwner(encryptedKey));
        request.setAmount(amount);
        request.setVolumes(volumes);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/upload")
    public DataSetRequest upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String encryptedKey = this.current;

        File raw = ipfsService.save(multipartFile, multipartFile.getOriginalFilename());
        String encPath = cryptoService.encryptFile(Base58.decode(encryptedKey), ecKey.getPrivKey(), raw);

        String hash = ipfsService.upload(encPath);

        DataSetRequest request = new DataSetRequest();
        request.setHash(hash);
        request.setName(multipartFile.getName());
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/download")
    public DataSetRequest download(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String reEncryptedKey = request.getReEncryptedKey();

        File cipher = ipfsService.download(request.getHash());
        String decPath = cryptoService.decryptFile(Base58.decode(reEncryptedKey), ecKey.getPrivKey(), cipher, request.getName());

        request.setPath(decPath);
        request.setCompleted(true);

        return request;
    }

    @GetMapping("/testECC")
    public void testECC() throws Exception {
        blockchainService.login("0x6a2fb5e3bf37f0c3d90db4713f7ad4a3b2c24111", "Innov@teD@ily1");
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String plaintext = "Hello world";
        byte[] cipher = cryptoService.eccEncrypt(ecKey.getPubKeyPoint(), plaintext.getBytes());
        String result = new String(cryptoService.eccDecrypt(ecKey.getPrivKey(), cipher));
    }

    @GetMapping("/testAES")
    public void testAES() throws Exception {
        byte[] aesKey = cryptoService.aesKeyGen();
        String plaintext = "Hello world";
        byte[] cipher = cryptoService.aesEncrypt(aesKey, plaintext.getBytes());
        String result = new String(cryptoService.aesDecrypt(aesKey, cipher));
    }

    @GetMapping("/testPRE")
    public void testPRE() throws Exception {
        blockchainService.login("0x6a2fb5e3bf37f0c3d90db4713f7ad4a3b2c24111", "Innov@teD@ily1");
        ECKey ecKey1 = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String plaintext = "Hello world";
        blockchainService.login("0x38a5d4e63bbac1af0eba0d99ef927359ab8d7293", "Innov@teD@ily1");
        ECKey ecKey2 = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        byte[] capsule = cryptoService.encryptKeyGen(ecKey1.getPubKeyPoint());

        byte[] cipher1 = cryptoService.encryptHash(capsule, ecKey1.getPrivKey(), plaintext.getBytes());
        byte[] cipher2 = cryptoService.encryptHashB(capsule, ecKey1.getPrivKey(), plaintext.getBytes());
        byte[] reCapsule = cryptoService.reEncrypt(capsule, ecKey1.getPrivKey(), ecKey2.getPubKeyPoint());

        String result11 = new String(cryptoService.decryptHash(capsule, ecKey1.getPrivKey(), cipher1));
        String result12 = new String(cryptoService.decryptHash(reCapsule, ecKey2.getPrivKey(), cipher1));

        String result21 = new String(cryptoService.decryptHashB(capsule, ecKey1.getPrivKey(), cipher2));
        String result22 = new String(cryptoService.decryptHashB(reCapsule, ecKey2.getPrivKey(), cipher2));
    }

    @GetMapping("/testSHA")
    public void testSHA() throws Exception {
        Curve crv = new Curve("secp256k1");
        GroupElement ge = new GroupElement(crv, crv.getCurve().createPoint(BigInteger.ONE,BigInteger.ONE));
        System.out.println(Base58.encode(ProxyUtils.SHA256(ge).toBytes()));
        System.out.println(Base58.encode(ProxyUtils.SHA3(ge).toBytes()));
    }
}