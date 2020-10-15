package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.txhsl.ppml.api.contract.DataSets_sol_DataSets;
import org.txhsl.ppml.api.contract.System_sol_System;
import org.txhsl.ppml.api.model.Volume;
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
import org.web3j.tuples.generated.Tuple3;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

@Service
public class BlockchainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);

    private final Web3j web3j;
    private Credentials credentials;
    // PRE
    private DataSets_sol_DataSets dataSetContract;
    // RBAC
    private System_sol_System systemContract;
    private final String adminAddr = "0x6a2fb5e3bf37f0c3d90db4713f7ad4a3b2c24111";

    public BlockchainService(Web3j web3j) {
        this.web3j = web3j;
        try {
            this.login(adminAddr, "Innov@teD@ily1");
            if(!this.recover(this.credentials)){
                this.deploy(this.credentials);
            }
        } catch (Exception e){
            LOGGER.error("Default wallet not found. " + e.getMessage());
        }
    }

    // Self
    public boolean recover(Credentials credentials) {
        boolean flag = true;
        try {
            Resource resource = new ClassPathResource("system.json");
            JSONObject json = new JSONObject(new String(FileCopyUtils.copyToByteArray(resource.getFile())));

            this.dataSetContract = DataSets_sol_DataSets.load(json.getString("pre"), web3j, credentials, GAS_PRICE, GAS_LIMIT);
            LOGGER.info("PRE Contract connected: " + this.dataSetContract.getContractAddress());
        } catch (Exception e) {
            LOGGER.error("Contract address not found. " + e.getMessage());
            flag = false;
        }

        try {
            Resource resource = new ClassPathResource("system.json");
            JSONObject json = new JSONObject(new String(FileCopyUtils.copyToByteArray(resource.getFile())));

            this.systemContract = System_sol_System.load(json.getString("rbac"), web3j, credentials, GAS_PRICE, GAS_LIMIT);
            LOGGER.info("RBAC Contract connected: " + this.systemContract.getContractAddress());
        } catch (Exception e) {
            LOGGER.error("Contract address not found. " + e.getMessage());
            flag = false;
        }

        return flag;
    }

    public boolean deploy(Credentials credentials) throws Exception {
        if(this.dataSetContract == null) {
            this.dataSetContract = DataSets_sol_DataSets.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
            LOGGER.info("PRE Contract deployed: " + this.dataSetContract.getContractAddress());
        }
        else {
            LOGGER.info("PRE Contract existed: " + this.dataSetContract.getContractAddress());
        }

        if(this.systemContract == null) {
            this.systemContract = System_sol_System.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
            LOGGER.info("RBAC Contract deployed: " + this.systemContract.getContractAddress());
        }
        else {
            LOGGER.info("RBAC Contract existed: " + this.systemContract.getContractAddress());
        }

        return this.dataSetContract.getTransactionReceipt().isPresent() && this.systemContract.getTransactionReceipt().isPresent();
    }

    // UserController
    public void login(String address, String password) throws IOException, CipherException {
        Resource resource = new ClassPathResource(address);
        File file = resource.getFile();
        this.credentials = WalletUtils.loadCredentials(
                password,
                file.getAbsolutePath());
        LOGGER.info("Wallet opened: " + credentials.getAddress());
        this.recover(this.credentials);
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
    // PRE
    public TransactionReceipt createDataSet(String key, Capsule capsule) throws Exception {
        TransactionReceipt receipt = this.dataSetContract.createDataSet(new Utf8String(key), new Uint256(capsule.getE().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getE().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getV().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getV().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getS().getValue())).send();
        LOGGER.info("DataSet created: " + key);
        return receipt;
    }

    public TransactionReceipt addVolume(String key, String name, String hash) throws Exception {
        TransactionReceipt receipt = this.dataSetContract.addVolume(new Utf8String(key), new Utf8String(name), new Utf8String(hash)).send();
        LOGGER.info("Volume added. Key: " + key + ", volume: " + hash);
        return receipt;
    }

    public TransactionReceipt shareKey(String key, String to, ReEncryptionKey reEncryptedKey) throws Exception {
        TransactionReceipt receipt = this.dataSetContract.shareKey(new Utf8String(key), new Address(to), new Uint256(reEncryptedKey.getReKey().getValue()),
                new Uint256(reEncryptedKey.getInternalPublicKey().getValue().getXCoord().toBigInteger()), new Uint256(reEncryptedKey.getInternalPublicKey().getValue().getYCoord().toBigInteger())).send();
        LOGGER.info("Key " + key + " shared, to " + to);
        return receipt;
    }

    public String getOwner(String key) throws Exception {
        return this.dataSetContract.getOwner(new Utf8String(key)).send().getValue();
    }

    public int getAmount(String key) throws Exception {
        return this.dataSetContract.getAmount(new Utf8String(key)).send().getValue().intValue();
    }

    public String getVolumeName(String key, int volume) throws Exception {
        return this.dataSetContract.getVolumeName(new Utf8String(key), new Uint256(volume)).send().getValue();
    }

    public int getVolumeTime(String key, int volume) throws Exception {
        return this.dataSetContract.getVolumeTime(new Utf8String(key), new Uint256(volume)).send().getValue().intValue();
    }

    public String getVolumeHash(String key, int volume) throws Exception {
        return this.dataSetContract.getVolumeHash(new Utf8String(key), new Uint256(volume)).send().getValue();
    }

    public Capsule getReEncryptedKey(String key) throws Exception {
        List<Uint256> reCapsule = this.dataSetContract.getReEncryptedKey(new Address(this.credentials.getAddress()), new Utf8String(key)).send().getValue();
        Curve crv = new Curve("secp256k1");
        return new Capsule(new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(0).getValue(), reCapsule.get(1).getValue())),
                new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(2).getValue(), reCapsule.get(3).getValue())),
                new Scalar(reCapsule.get(4).getValue(), crv), new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(5).getValue(), reCapsule.get(6).getValue())), true);
    }

    // RBAC
    public String getAdminAddr() {
        return this.adminAddr;
    }

    public TransactionReceipt addUser(String address, String roleName) throws Exception {
        TransactionReceipt receipt = this.systemContract.register(new Address(address), new Utf8String(roleName)).send();
        LOGGER.info("User registered: " + address + ", role: " + roleName);
        return receipt;
    }

    public TransactionReceipt addRole(String name) throws Exception {
        TransactionReceipt receipt = this.systemContract.addRC(new Utf8String(name)).send();
        LOGGER.info("Role added: " + name + ", admin: " + this.credentials.getAddress());
        return receipt;
    }

    public TransactionReceipt addData(String name) throws Exception {
        TransactionReceipt receipt = this.systemContract.addDC(new Utf8String(name)).send();
        LOGGER.info("Data added: " + name + ", admin: " + this.credentials.getAddress());
        return receipt;
    }

    public TransactionReceipt assignReader(String data, String role) throws Exception {
        TransactionReceipt receipt = this.systemContract.assignReader(new Utf8String(data), new Utf8String(role)).send();
        LOGGER.info("Reader assigned: " + role + ", on: " + data);
        return receipt;
    }

    public TransactionReceipt assignWriter(String data, String role) throws Exception {
        TransactionReceipt receipt = this.systemContract.assignWriter(new Utf8String(data), new Utf8String(role)).send();
        LOGGER.info("Writer assigned: " + role + ", on: " + data);
        return receipt;
    }

    public TransactionReceipt write(String data, String fileName, String hash) throws Exception {
        TransactionReceipt receipt = this.systemContract.writeData(new Utf8String(data), new Utf8String(fileName), new Utf8String(hash)).send();
        LOGGER.info("File added: " + fileName + ", hash: " + hash + ", in: " + data);
        return receipt;
    }

    public String getRCAddr(String name) throws Exception {
        return this.systemContract.getRC(new Utf8String(name)).send().getValue();
    }

    public String getDCAddr(String name) throws Exception {
        return this.systemContract.getDC(new Utf8String(name)).send().getValue();
    }

    public String getRole(String address) throws Exception {
        return this.systemContract.getRole(new Address(address)).send().getValue();
    }

    public boolean checkReader(String data, String address) throws Exception {
        return this.systemContract.checkReader(new Utf8String(data), new Address(address)).send().getValue();
    }

    public boolean checkWriter(String data, String address) throws Exception {
        return this.systemContract.checkWriter(new Utf8String(data), new Address(address)).send().getValue();
    }

    public boolean checkRoleAdmin(String role, String address) throws Exception {
        return this.systemContract.checkRoleAdmin(new Utf8String(role), new Address(address)).send().getValue();
    }

    public boolean checkDataAdmin(String data, String address) throws Exception {
        return this.systemContract.checkDataAdmin(new Utf8String(data), new Address(address)).send().getValue();
    }

    public String getDataAdmin(String data) throws Exception {
        return this.systemContract.getDataAdmin(new Utf8String(data)).send().getValue();
    }

    public int getTotal(String data) throws Exception {
        return this.systemContract.getAmount(new Utf8String(data)).send().getValue().intValue();
    }

    public Volume read(String data, int id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Tuple3 tuple3 = this.systemContract.readData(new Utf8String(data), new Uint256(id)).send();
        return new Volume(id, ((Utf8String)tuple3.getValue1()).getValue(), sdf.format(new Date(((Uint256)tuple3.getValue2()).getValue().intValue() * 1000L)), ((Utf8String)tuple3.getValue3()).getValue());
    }
}
