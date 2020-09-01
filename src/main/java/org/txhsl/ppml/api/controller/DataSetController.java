package org.txhsl.ppml.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/getKey")
    public DataSetRequest getKey(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
        blockchainService.getReEncryptedKey(encryptedKey);

        request.setKey(cryptoService.decrypt(encryptedKey, ""));
        request.setCompleted(true);
        return request;
    }

    @PostMapping("/getVolumes")
    public DataSetRequest getVolumes(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getEncryptedKey();
        int amount = blockchainService.getAmount(encryptedKey);

        Volume[] volumes = new Volume[amount];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i = 1; i <= amount; i++) {
            String name = blockchainService.getVolumeName(encryptedKey, i);
            int time = blockchainService.getVolumeTime(encryptedKey, i);
            String encryptedHash = blockchainService.getVolumeHash(encryptedKey, i);
            String hash = cryptoService.decrypt(encryptedHash, "");

            volumes[i] = new Volume(i, name, sdf.format(new Date(time * 1000L)), hash);
        }

        request.setVolumes(volumes);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/upload")
    public DataSetRequest upload(@RequestBody DataSetRequest request) throws IOException {
        String hash = ipfsService.upload(request.getFilePath());

        request.setHash(hash);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/download")
    public DataSetRequest download(@RequestBody DataSetRequest request) throws IOException {
        String path = ipfsService.download(request.getHash()).getAbsolutePath();

        request.setFilePath(path);
        request.setCompleted(true);

        return request;
    }
}