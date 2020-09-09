package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.txhsl.ppml.api.contract.DataSets_sol_DataSets;
import org.txhsl.ppml.api.service.crypto.*;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

@Service
public class BlockchainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);

    private final Web3j web3j;
    private Credentials credentials;
    private DataSets_sol_DataSets dataContract;

    public BlockchainService(Web3j web3j) {
        this.web3j = web3j;
        try {
            this.login("0x6a2fb5e3bf37f0c3d90db4713f7ad4a3b2c24111", "Innov@teD@ily1");
            if(!this.recover(this.credentials)){
                this.deploy(this.credentials);
            }
        } catch (Exception e){
            LOGGER.error("Default wallet not found. " + e.getMessage());
        }
    }

    // Self
    public boolean recover(Credentials credentials) {
        try {
            Resource resource = new ClassPathResource("system.json");
            JSONObject json = new JSONObject(new String(FileCopyUtils.copyToByteArray(resource.getFile())));
            this.dataContract = DataSets_sol_DataSets.load(json.getString("address"), web3j, credentials, GAS_PRICE, GAS_LIMIT);
            LOGGER.info("Contract connected: " + this.dataContract.getContractAddress());
            return true;
        } catch (Exception e) {
            LOGGER.error("Contract address not found. " + e.getMessage());
            return false;
        }
    }

    public TransactionReceipt deploy(Credentials credentials) throws Exception {
        if(this.dataContract == null) {
            this.dataContract = DataSets_sol_DataSets.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
            LOGGER.info("Contract deployed: " + this.dataContract.getContractAddress());
        }
        else {
            LOGGER.info("Contract existed: " + this.dataContract.getContractAddress());
        }

        if(this.dataContract.getTransactionReceipt().isPresent()) {
            return this.dataContract.getTransactionReceipt().get();
        }
        else {
            return null;
        }
    }

    // UserController
    public void login(String address, String password) throws IOException, CipherException {
        Resource resource = new ClassPathResource(address);
        File file = resource.getFile();
        this.credentials = WalletUtils.loadCredentials(
                password,
                file.getAbsolutePath());
        LOGGER.info("Wallet opened: " + credentials.getAddress());
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public double getBalance() throws IOException {
        BigInteger wei =  web3j.ethGetBalance(this.credentials.getAddress(), DefaultBlockParameter.valueOf("latest")).send().getBalance();
        double balance = Convert.fromWei(wei.toString(), Convert.Unit.ETHER).doubleValue();
        LOGGER.info("Balance read: " + balance);
        return balance;
    }

    // DataSetController
    public TransactionReceipt createDataSet(String key, Capsule capsule) throws Exception {
        TransactionReceipt receipt = this.dataContract.createDataSet(new Utf8String(key), new Uint256(capsule.getE().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getE().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getV().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getV().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getS().getValue())).send();
        LOGGER.info("DataSet created: " + key);
        return receipt;
    }

    public TransactionReceipt addVolume(String key, String name, String hash) throws Exception {
        TransactionReceipt receipt = this.dataContract.addVolume(new Utf8String(key), new Utf8String(name), new Utf8String(hash)).send();
        LOGGER.info("Volume added. Key: " + key + ", volume: " + hash);
        return receipt;
    }

    public TransactionReceipt shareKey(String key, String to, ReEncryptionKey reEncryptedKey) throws Exception {
        TransactionReceipt receipt = this.dataContract.shareKey(new Utf8String(key), new Address(to), new Uint256(reEncryptedKey.getReKey().getValue()),
                new Uint256(reEncryptedKey.getInternalPublicKey().getValue().getXCoord().toBigInteger()), new Uint256(reEncryptedKey.getInternalPublicKey().getValue().getYCoord().toBigInteger())).send();
        LOGGER.info("Key " + key + " shared, to " + to);
        return receipt;
    }

    public String getOwner(String key) throws Exception {
        return this.dataContract.getOwner(new Utf8String(key)).send().getValue();
    }

    public int getAmount(String key) throws Exception {
        return this.dataContract.getAmount(new Utf8String(key)).send().getValue().intValue();
    }

    public String getVolumeName(String key, int volume) throws Exception {
        return this.dataContract.getVolumeName(new Utf8String(key), new Uint256(volume)).send().getValue();
    }

    public int getVolumeTime(String key, int volume) throws Exception {
        return this.dataContract.getVolumeTime(new Utf8String(key), new Uint256(volume)).send().getValue().intValue();
    }

    public String getVolumeHash(String key, int volume) throws Exception {
        return this.dataContract.getVolumeHash(new Utf8String(key), new Uint256(volume)).send().getValue();
    }

    public Capsule getReEncryptedKey(String key) throws Exception {
        List<Uint256> reCapsule = this.dataContract.getReEncryptedKey(new Address(this.credentials.getAddress()), new Utf8String(key)).send().getValue();
        Curve crv = new Curve("secp256k1");
        return new Capsule(new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(0).getValue(), reCapsule.get(1).getValue())),
                new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(2).getValue(), reCapsule.get(3).getValue())),
                new Scalar(reCapsule.get(4).getValue(), crv), new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(5).getValue(), reCapsule.get(6).getValue())), true);
    }
}
