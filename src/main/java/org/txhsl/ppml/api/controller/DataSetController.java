package org.txhsl.ppml.api.controller;

import org.ethereum.crypto.ECKey;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.txhsl.ppml.api.model.DataSetRequest;
import org.txhsl.ppml.api.model.Volume;
import org.txhsl.ppml.api.service.BlockchainService;
import org.txhsl.ppml.api.service.CryptoService;
import org.txhsl.ppml.api.service.IPFSService;

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

        String encryptedKey = cryptoService.eccEncrypt(ecKey.getPubKeyPoint(), cryptoService.aesKeyGen());

        blockchainService.createDataSet(encryptedKey);

        request.setEncryptedKey(encryptedKey);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/add")
    public DataSetRequest add(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        String encryptedKey = request.getEncryptedKey();
        String key = cryptoService.eccDecrypt(ecKey.getPrivKey(), encryptedKey);
        String encryptedHash = cryptoService.aesEncrypt(key, request.getHash());

        blockchainService.addVolume(encryptedKey, request.getName(), encryptedHash);

        request.setEncryptedHash(encryptedHash);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/share")
    public DataSetRequest share(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        String encryptedKey = request.getEncryptedKey();
        String reEncryptedKey = "";//cryptoService.eccReEncrypt(encryptedKey, ecKey.getPrivKey(), ecKey.getPrivKey());

        blockchainService.shareKey(encryptedKey, request.getTo(), reEncryptedKey);

        request.setReEncryptedKey(reEncryptedKey);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/decryptHash")
    public DataSetRequest decryptHash(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        String encryptedKey = request.getEncryptedKey();
        String encryptedHash = request.getEncryptedHash();

        String key = cryptoService.eccDecrypt(ecKey.getPrivKey(), encryptedKey);

        request.setHash(cryptoService.aesDecrypt(key, encryptedHash));
        request.setCompleted(true);
        return request;
    }

    @PostMapping("/get")
    public DataSetRequest get(@RequestBody DataSetRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        String encryptedKey = request.getEncryptedKey();
        int amount = blockchainService.getAmount(encryptedKey);
        String key = cryptoService.eccDecrypt(ecKey.getPrivKey(), encryptedKey);

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
    public void testECC() throws IOException, InvalidCipherTextException {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String plaintext = "Hello world";
        String cipher = cryptoService.eccEncrypt(ecKey.getPubKeyPoint(), plaintext);
        String result = cryptoService.eccDecrypt(ecKey.getPrivKey(), cipher);
    }

    @GetMapping("/testAES")
    public void testAES() throws Exception {
        String aesKey = cryptoService.aesKeyGen();
        String plaintext = "Hello world";
        String cipher = cryptoService.aesEncrypt(aesKey, plaintext);
        String result = cryptoService.aesDecrypt(aesKey, cipher);
    }

    @GetMapping("/testPRE")
    public void testPRE() throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
        String plaintext = "Hello world";
        byte[] capsule = cryptoService.encryptKeyGen(ecKey.getPubKeyPoint());
        String cipher = cryptoService.encrypt(capsule, ecKey.getPrivKey(), plaintext);
        //byte[] reCapsule = cryptoService.reEncrypt(capsule, ecKey.getPrivKey(), kp.getPublicKey().getValue().getValue());
        String result = cryptoService.decrypt(capsule, ecKey.getPrivKey(), cipher);
        //String result1 = cryptoService.decrypt(reCapsule, ecKey1.getPrivKey(), cipher);
    }
}