package org.txhsl.ppml.api.controller;

import io.ipfs.multibase.Base58;
import org.ethereum.crypto.ECKey;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.txhsl.ppml.api.model.BlockchainRequest;
import org.txhsl.ppml.api.model.Volume;
import org.txhsl.ppml.api.service.BlockchainService;
import org.txhsl.ppml.api.service.CryptoService;
import org.txhsl.ppml.api.service.IPFSService;
import org.txhsl.ppml.api.service.crypto.*;

import java.io.File;

@RestController
@RequestMapping("/dataset")
public class BlockchainController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BlockchainController.class);

    private final BlockchainService blockchainService;
    private final IPFSService ipfsService;
    private final CryptoService cryptoService;

    private String current = "";

    public BlockchainController(BlockchainService blockchainService, IPFSService ipfsService, CryptoService cryptoService) {
        this.blockchainService = blockchainService;
        this.ipfsService = ipfsService;
        this.cryptoService = cryptoService;
    }

    @PostMapping("/createData")
    public BlockchainRequest createData(@RequestBody BlockchainRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        int count = 0;
        while(count < 10) {
            try {
                byte[] capsule = cryptoService.encryptKeyGen(ecKey.getPubKeyPoint());
                blockchainService.createDataSet(request.getData(), Capsule.fromBytes(capsule));

                request.setDcKey(Base58.encode(capsule));
                request.setCompleted(true);

                return request;
            } catch (UnsupportedOperationException e) {
                count++;
                LOGGER.warn("Function 'createData' attempt " + count);
            }
        }
        throw new UnsupportedOperationException();
    }

    @PostMapping("/createRole")
    public BlockchainRequest createRole(@RequestBody BlockchainRequest request) throws Exception {
        ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());

        int count = 0;
        while(count < 10) {
            try {
                byte[] capsule = cryptoService.encryptKeyGen(ecKey.getPubKeyPoint());
                ECKey key = ECKey.fromPrivate(cryptoService.prvGen(capsule, ecKey.getPrivKey()));
                String rcPubKey = Base58.encode(key.getPubKeyPoint().getEncoded());
                blockchainService.createRole(request.getRole(), rcPubKey, Capsule.fromBytes(capsule));

                request.setRcPubKey(rcPubKey);
                request.setCompleted(true);

                return request;
            } catch (UnsupportedOperationException e) {
                count++;
                LOGGER.warn("Function 'createRole' attempt " + count);
            }
        }
        throw new UnsupportedOperationException();
    }

    @PostMapping("/registerUser")
    public BlockchainRequest registerUser(@RequestBody BlockchainRequest request) throws Exception {
        int count = 0;
        while(count < 5) {
            try {
                if (!blockchainService.checkRoleAdmin(request.getRole(), blockchainService.getCredentials().getAddress())) {
                    throw new Exception("Permission denied");
                }
                ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
                ECKey usrPubKey = ECKey.fromPublicOnly(Base58.decode(request.getUserPubKey()));
                byte[] reEncryptKey = cryptoService.getReKey(ecKey.getPrivKey(), usrPubKey.getPubKeyPoint());

                request.setTx(blockchainService.register(request.getUser(), request.getRole(), ReEncryptionKey.fromBytes(reEncryptKey)).getTransactionHash());
                request.setAccessKey(Base58.encode(blockchainService.getUserKey(blockchainService.getCredentials().getAddress()).toBytes()));
                request.setCompleted(true);
                return request;
            } catch (Exception e) {
                count++;
                LOGGER.warn("Function 'registerUser' attempt " + count);
            }
        }
        throw new Exception("Permission denied");
    }

    @PostMapping("/addVolume")
    public BlockchainRequest addVolume(@RequestBody BlockchainRequest request) throws Exception {
        request.setTx(blockchainService.addvolume(request.getData(), request.getName(), request.getHash()).getTransactionHash());
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/assignReader")
    public BlockchainRequest assignReader(@RequestBody BlockchainRequest request) throws Exception {
        int count = 0;
        while(count < 5) {
            try {
                if (!blockchainService.checkDataOwner(request.getData(), blockchainService.getCredentials().getAddress())) {
                    throw new Exception("Permission denied");
                }

                ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
                String role = request.getRole();
                String data = request.getData();

                ECKey rcPubKey = ECKey.fromPublicOnly(Base58.decode(blockchainService.getRCPubKey(role)));
                byte[] reEncryptKey = cryptoService.getReKey(ecKey.getPrivKey(), rcPubKey.getPubKeyPoint());

                request.setTx(blockchainService.assignReader(data, role, ReEncryptionKey.fromBytes(reEncryptKey)).getTransactionHash());
                request.setAccessKey(Base58.encode(blockchainService.getRoleKey(role, data).toBytes()));
                request.setCompleted(true);

                return request;
            } catch (Exception e) {
                count++;
                LOGGER.warn("Function 'assignReader' attempt " + count);
            }
        }
        throw new Exception("Permission denied");
    }

    @PostMapping("/assignWriter")
    public BlockchainRequest assignWriter(@RequestBody BlockchainRequest request) throws Exception {
        int count = 0;
        while(count < 5) {
            try {
                if (!blockchainService.checkDataOwner(request.getData(), blockchainService.getCredentials().getAddress())) {
                    throw new Exception("Permission denied");
                }
                request.setTx(blockchainService.assignWriter(request.getData(), request.getRole()).getTransactionHash());
                request.setCompleted(true);
                return request;
            } catch (Exception e) {
                count++;
                LOGGER.warn("Function 'assignWriter' attempt " + count);
            }
        }
        throw new Exception("Permission denied");
    }

    @PostMapping("/getAll")
    public BlockchainRequest getAll(@RequestBody BlockchainRequest request) throws Exception {
        int count = 0;
        while(count < 5) {
            try {
                String data = request.getData();
                this.current = data;
                int amount = blockchainService.getAmount(data);

                Volume[] volumes = new Volume[amount];
                for (int i = 1; i <= amount; i++) {
                    volumes[i - 1] = blockchainService.read(data, i);
                }

                request.setOwner(blockchainService.getOwner(data));
                request.setAmount(amount);
                request.setVolumes(volumes);
                request.setCompleted(true);

                return request;
            } catch (Exception e) {
                count++;
                LOGGER.warn("Function 'getAll' attempt " + count);
            }
        }
        throw new Exception();
    }

    @PostMapping("/upload")
    public BlockchainRequest upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        int count = 0;
        while(count < 5) {
            try {
                String data = this.current;
                if(blockchainService.getCredentials().getAddress().equals(blockchainService.getOwner(data))) {
                    ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
                    Capsule dcKey = blockchainService.getDataKey(data);

                    File raw = ipfsService.save(multipartFile, multipartFile.getOriginalFilename());
                    String encPath = cryptoService.aesEncryptFile(dcKey.toBytes(), ecKey.getPrivKey(), raw);

                    String hash = ipfsService.upload(encPath);

                    BlockchainRequest request = new BlockchainRequest();
                    request.setHash(hash);
                    request.setName(multipartFile.getName());
                    request.setCompleted(true);

                    return request;
                }
                else if (blockchainService.checkWriter(data, blockchainService.getCredentials().getAddress())) {
                    ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
                    Capsule userKey = blockchainService.getUserKey(blockchainService.getCredentials().getAddress());
                    ECKey rcPrvKey = ECKey.fromPrivate(cryptoService.prvGen(userKey.toBytes(), ecKey.getPrivKey()));
                    Capsule dcKey = blockchainService.getRoleKey(blockchainService.getRole(blockchainService.getCredentials().getAddress()), data);

                    File raw = ipfsService.save(multipartFile, multipartFile.getOriginalFilename());
                    String encPath = cryptoService.aesEncryptFile(dcKey.toBytes(), rcPrvKey.getPrivKey(), raw);

                    String hash = ipfsService.upload(encPath);

                    BlockchainRequest request = new BlockchainRequest();
                    request.setHash(hash);
                    request.setName(multipartFile.getName());
                    request.setCompleted(true);

                    return request;
                }
                else {
                    throw new Exception("Permission denied");
                }

            } catch (Exception e) {
                count++;
                LOGGER.warn("Function 'upload' attempt " + count);
            }
        }
        throw new Exception("Permission denied");
    }

    @PostMapping("/download")
    public BlockchainRequest download(@RequestBody BlockchainRequest request) throws Exception {
        File cipher = ipfsService.download(request.getHash());
        request.setPath(cipher.getAbsolutePath());
        request.setCompleted(true);

        return request;
    }

    @PostMapping("/decrypt")
    public BlockchainRequest decrypt(@RequestBody BlockchainRequest request) throws Exception {
        int count = 0;
        while(count < 5) {
            try {
                String data = request.getData();
                if (blockchainService.getCredentials().getAddress().equals(blockchainService.getOwner(data))) {
                    ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
                    Capsule dcKey = blockchainService.getDataKey(data);

                    String decPath = cryptoService.aesDecryptFile(dcKey.toBytes(), ecKey.getPrivKey(), new File(request.getPath()), request.getName());

                    request.setPath(decPath);
                    request.setCompleted(true);

                    return request;
                }
                else if (blockchainService.checkReader(data, blockchainService.getCredentials().getAddress())) {
                    ECKey ecKey = ECKey.fromPrivate(blockchainService.getCredentials().getEcKeyPair().getPrivateKey());
                    Capsule userKey = blockchainService.getUserKey(blockchainService.getCredentials().getAddress());
                    ECKey rcPrvKey = ECKey.fromPrivate(cryptoService.prvGen(userKey.toBytes(), ecKey.getPrivKey()));
                    Capsule dcKey = blockchainService.getRoleKey(blockchainService.getRole(blockchainService.getCredentials().getAddress()), data);

                    String decPath = cryptoService.aesDecryptFile(dcKey.toBytes(), rcPrvKey.getPrivKey(), new File(request.getPath()), request.getName());

                    request.setPath(decPath);
                    request.setCompleted(true);

                    return request;
                }
                else {
                    throw new Exception("Permission denied");
                }
            } catch (Exception e) {
                count++;
                LOGGER.warn("Function 'decrypt' attempt " + count);
            }
        }
        throw new Exception("Permission denied");
    }

    @PostMapping("/getRole")
    public BlockchainRequest getRole(@RequestBody BlockchainRequest request) throws Exception {
        request.setRole(blockchainService.getRole(request.getUser()));
        return request;
    }

    @PostMapping("/checkReader")
    public BlockchainRequest checkReader(@RequestBody BlockchainRequest request) throws Exception {
        request.setPermitted(blockchainService.checkReader(request.getData(), request.getRole()));
        request.setCompleted(true);
        return request;
    }

    @PostMapping("/checkWriter")
    public BlockchainRequest checkWriter(@RequestBody BlockchainRequest request) throws Exception {
        request.setPermitted(blockchainService.checkWriter(request.getData(), request.getRole()));
        request.setCompleted(true);
        return request;
    }
}