package org.txhsl.ppml.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.txhsl.ppml.api.model.DataSetRequest;
import org.txhsl.ppml.api.service.BlockchainService;
import org.txhsl.ppml.api.service.IPFSService;

import java.io.IOException;

@RestController
public class DataSetController {

    private final BlockchainService blockchainService;
    private final IPFSService ipfsService;

    public DataSetController(BlockchainService blockchainService, IPFSService ipfsService) {
        this.blockchainService = blockchainService;
        this.ipfsService = ipfsService;
    }

    @PostMapping("/create")
    public DataSetRequest create(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getKey();

        blockchainService.createDataSet(encryptedKey);

        request.setEncryptedKey(encryptedKey);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/add")
    public DataSetRequest add(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getKey();
        String encryptedHash = request.getHash();

        blockchainService.addVolume(encryptedKey, encryptedHash);

        request.setEncryptedKey(encryptedKey);
        request.setEncryptedHash(encryptedHash);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/share")
    public DataSetRequest share(@RequestBody DataSetRequest request) throws Exception {
        String encryptedKey = request.getKey();
        String reEncryptedKey = encryptedKey;

        blockchainService.shareKey(encryptedKey, request.getTo(), reEncryptedKey);

        request.setEncryptedKey(encryptedKey);
        request.setReEncryptedKey(reEncryptedKey);
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/get")
    public DataSetRequest get(@RequestBody DataSetRequest request) throws Exception {
        String reEncryptedKey = blockchainService.getReEncryptedKey(request.getEncryptedKey());
        String encryptedHash = blockchainService.getVolume(request.getEncryptedKey(), request.getVolume());

        String hash = encryptedHash;

        request.setHash(hash);
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