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
import org.txhsl.ppml.api.service.crypto.Capsule;
import org.txhsl.ppml.api.service.crypto.ReEncryptionKey;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/dataset")
public class DataSetController {

    private final BlockchainService blockchainService;
    private final IPFSService ipfsService;
    private final CryptoService cryptoService;

    public DataSetController(BlockchainService blockchainService, IPFSService ipfsService, CryptoService cryptoService) {
        this.blockchainService = blockchainService;
        this.ipfsService = ipfsService;
        this.cryptoService = cryptoService;
    }

    @PostMapping("/create")
    public DataSetRequest create(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        byte[] encryptedKey = cryptoService.encryptKeyGen(ecKey.getPubKeyPoint());

        blockchainService.createDataSet(Base58.encode(encryptedKey), Capsule.fromBytes(encryptedKey));

        request.setEncryptedKey(Base58.encode(encryptedKey));
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/add")
    public DataSetRequest add(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String encryptedKey = request.getEncryptedKey();
        String hash = request.getHash();

        byte[] encryptedHash = cryptoService.encrypt(Base58.decode(encryptedKey), ecKey.getPrivKey(), hash.getBytes());

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
        byte[] reEncryptedKey = cryptoService.getReKey(Base58.decode(encryptedKey), ecKey.getPrivKey(), to.getPubKeyPoint());
        String toAddr = Numeric.prependHexPrefix(Hex.toHexString(to.getAddress()));
        blockchainService.shareKey(encryptedKey, toAddr, ReEncryptionKey.fromBytes(reEncryptedKey));

        request.setReEncryptedKey(Base58.encode(reEncryptedKey));
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/getReKey")
    public DataSetRequest getReKey(@RequestBody DataSetRequest request) throws Exception {
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
        String requestEncryptedKey = request.getReEncryptedKey();
        String encryptedHash = request.getEncryptedHash();

        byte[] hash = cryptoService.decrypt(Base58.decode(requestEncryptedKey), ecKey.getPrivKey(), Base58.decode(encryptedHash));

        request.setHash(new String(hash));
        request.setCompleted(true);
        return request;
    }

    @PostMapping("/get")
    public DataSetRequest get(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
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
    public DataSetRequest upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String path = ipfsService.save(multipartFile, multipartFile.getOriginalFilename());
        String hash = ipfsService.upload(path);

        DataSetRequest request = new DataSetRequest();
        request.setHash(hash);
        request.setName(multipartFile.getName());
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/download")
    public DataSetRequest download(@RequestBody DataSetRequest request) throws IOException {
        String path = ipfsService.download(request.getHash()).getAbsolutePath();

        request.setPath(path);
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
        byte[] cipher = cryptoService.encrypt(capsule, ecKey1.getPrivKey(), plaintext.getBytes());
        byte[] reCapsule = cryptoService.reEncrypt(capsule, ecKey1.getPrivKey(), ecKey2.getPubKeyPoint());
        String result = new String(cryptoService.decrypt(capsule, ecKey1.getPrivKey(), cipher));
        String result1 = new String(cryptoService.decrypt(reCapsule, ecKey2.getPrivKey(), cipher));
    }
}