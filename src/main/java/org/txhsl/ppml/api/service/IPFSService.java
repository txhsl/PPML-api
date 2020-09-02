package org.txhsl.ppml.api.service;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class IPFSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPFSService.class);
    private static final String IPFS_URL = "/ip4/127.0.0.1/tcp/5001";

    private final IPFS ipfs;
    private final File cacheFolder;

    public IPFSService() {
        ipfs = new IPFS(IPFS_URL);
        cacheFolder = new File("cache");

        try {
            LOGGER.info("IPFS connected, version: " + ipfs.version());
        } catch (IOException e){
            LOGGER.error("Connection failed. " + e.getMessage());
        }

        if (!cacheFolder.exists()) {
            cacheFolder.mkdir();
            LOGGER.info("Data folder created, path: " + cacheFolder.getAbsolutePath());
        }
    }

    public String save(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(cacheFolder.getAbsolutePath() + '\\' + fileName);
        multipartFile.transferTo(file);
        return file.getAbsolutePath();
    }

    public String upload(String path) throws IOException {
        File target = new File(path);
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(target);
        MerkleNode addResult = ipfs.add(file).get(0);
        String hash = addResult.hash.toString();
        LOGGER.info("File uploaded, hash: " + hash + ", path: " + target.getPath());

        return hash;
    }

    public File download(String hash) throws IOException {
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
        if(data != null){
            File file = new File(cacheFolder.getAbsolutePath() + '\\' + hash);
            if(file.exists()){
                LOGGER.info("File already existed, path: " + file.getPath());
                return file;
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
            LOGGER.info("File downloaded, hash: " + hash + ", path: " + file.getPath());
            return file;
        }
        else {
            LOGGER.error("File not exist, hash: " + hash);
            return null;
        }
    }
}
