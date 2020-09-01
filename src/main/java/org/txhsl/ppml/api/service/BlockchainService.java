package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.txhsl.ppml.api.contract.DataSets_sol_DataSets;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.io.IOException;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

@Service
public class BlockchainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);

    private final Web3j web3j;
    private Credentials credentials;
    private DataSets_sol_DataSets contract;

    public BlockchainService(Web3j web3j) {
        this.web3j = web3j;
    }

    public void login(String address, String password) throws IOException, CipherException {
        Resource resource = new ClassPathResource(address);
        File file = resource.getFile();
        this.credentials = WalletUtils.loadCredentials(
                password,
                file.getAbsolutePath());
        LOGGER.error("Wallet opened: " + credentials.getAddress());
    }

    public void recover(Credentials credentials) {
        try {
            Resource resource = new ClassPathResource("system.json");
            JSONObject json = new JSONObject(new String(FileCopyUtils.copyToByteArray(resource.getFile())));
            this.contract = DataSets_sol_DataSets.load(json.getString("address"), web3j, credentials, GAS_PRICE, GAS_LIMIT);
        } catch (Exception e) {
            LOGGER.error("Contract address not found. " + e.getMessage());
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public TransactionReceipt deploy(Credentials credentials) throws Exception {
        if(this.contract == null) {
            this.contract = DataSets_sol_DataSets.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
            LOGGER.info("Contract deployed: " + this.contract.getContractAddress());
        }
        else {
            LOGGER.info("Contract existed: " + this.contract.getContractAddress());
        }

        if(this.contract.getTransactionReceipt().isPresent()) {
            return this.contract.getTransactionReceipt().get();
        }
        else {
            return null;
        }
    }

    public TransactionReceipt createDataSet(String key) throws Exception {
        TransactionReceipt receipt = this.contract.createDataSet(new Utf8String(key)).send();
        LOGGER.info("DataSet created: " + key);
        return receipt;
    }

    public TransactionReceipt addVolume(String key, String hash) throws Exception {
        TransactionReceipt receipt = this.contract.addVolume(new Utf8String(key), new Utf8String(hash)).send();
        LOGGER.info("Volume added. Key: " + key + ", volume: " + hash);
        return receipt;
    }

    public TransactionReceipt shareKey(String key, String to, String reEncryptedKey) throws Exception {
        TransactionReceipt receipt = this.contract.shareKey(new Utf8String(key), new Address(to), new Utf8String(reEncryptedKey)).send();
        LOGGER.info("Key " + key + " shared, to " + to);
        return receipt;
    }

    public String getOwner(String key) throws Exception {
        return this.contract.getOwner(new Utf8String(key)).send().getValue();
    }

    public int getAmount(String key) throws Exception {
        return this.contract.getAmount(new Utf8String(key)).send().getValue().intValue();
    }

    public String getVolume(String key, int volume) throws Exception {
        return this.contract.getVolume(new Utf8String(key), new Uint256(volume)).send().getValue();
    }

    public String getReEncryptedKey(String key) throws Exception {
        return this.contract.getReEncryptedKey(new Utf8String(key)).send().getValue();
    }
}