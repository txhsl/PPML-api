package org.txhsl.ppml.api.controller;

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
        String encryptedKey = cryptoService.encrypt(request.getKey(), "");

        blockchainService.createDataSet(encryptedKey);

        request.setEncryptedKey(encryptedKey);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/add")
    public DataSetRequest add(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
        String encryptedHash = cryptoService.encrypt(request.getHash(), "");

        blockchainService.addVolume(encryptedKey, request.getName(), encryptedHash);

        request.setEncryptedHash(encryptedHash);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/share")
    public DataSetRequest share(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
        String reEncryptedKey = cryptoService.reEncrypt(encryptedKey, "");

        blockchainService.shareKey(encryptedKey, request.getTo(), reEncryptedKey);

        request.setReEncryptedKey(reEncryptedKey);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/decryptHash")
    public DataSetRequest decryptHash(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
        String encryptedHash = request.getEncryptedHash();
        String key = cryptoService.decrypt(blockchainService.getReEncryptedKey(encryptedKey), "");

        request.setHash(cryptoService.decrypt(encryptedHash, key));
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
            String hash = cryptoService.decrypt(encryptedHash, "");

            volumes[i - 1] = new Volume(i, name, sdf.format(new Date(time * 1000L)), hash);
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
}