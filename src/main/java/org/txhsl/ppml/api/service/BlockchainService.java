package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.txhsl.ppml.api.contract.DataSet_sol_DataSet;
import org.txhsl.ppml.api.contract.PPML_sol_PPML;
import org.txhsl.ppml.api.contract.Role_sol_Role;
import org.txhsl.ppml.api.model.Volume;
import org.txhsl.ppml.api.service.crypto.pre.*;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
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
    private PPML_sol_PPML systemContract;
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
        try {
            Resource resource = new ClassPathResource("system.json");
            JSONObject json = new JSONObject(new String(FileCopyUtils.copyToByteArray(resource.getFile())));

            this.systemContract = PPML_sol_PPML.load(json.getString("ppml"), this.web3j, credentials, GAS_PRICE, GAS_LIMIT);
            LOGGER.info("Contract connected: " + this.systemContract.getContractAddress());
        } catch (Exception e) {
            LOGGER.error("Contract address not found. " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deploy(Credentials credentials) throws Exception {
        if(this.systemContract == null) {
            this.systemContract = PPML_sol_PPML.deploy(this.web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
            LOGGER.info("Contract deployed: " + this.systemContract.getContractAddress());
        }
        else {
            LOGGER.info("Contract existed: " + this.systemContract.getContractAddress());
        }
        return true;
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
    public TransactionReceipt createRole(String name, String pubkey, Capsule capsule) throws Exception {
        TransactionReceipt receipt = this.systemContract.addRC(new Utf8String(name), new Utf8String(pubkey), new Uint256(capsule.getE().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getE().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getV().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getV().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getS().getValue())).send();
        LOGGER.info("Role created: " + name + ", pubkey: " + pubkey);
        return receipt;
    }

    public TransactionReceipt createDataSet(String name, Capsule capsule) throws Exception {
        TransactionReceipt receipt = this.systemContract.addDC(new Utf8String(name), new Uint256(capsule.getE().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getE().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getV().getValue().getXCoord().toBigInteger()),
                new Uint256(capsule.getV().getValue().getYCoord().toBigInteger()), new Uint256(capsule.getS().getValue())).send();
        LOGGER.info("DataSet created: " + name);
        return receipt;
    }

    public TransactionReceipt register(String address, String role, ReEncryptionKey reKey) throws Exception {
        TransactionReceipt receipt = this.systemContract.addUser(new Address(address), new Utf8String(role), new Uint256(reKey.getReKey().getValue()),
                new Uint256(reKey.getInternalPublicKey().getValue().getXCoord().toBigInteger()), new Uint256(reKey.getInternalPublicKey().getValue().getYCoord().toBigInteger())).send();
        LOGGER.info("Role " + role + " shared, to " + address);
        return receipt;
    }

    public TransactionReceipt assignReader(String name, String role, ReEncryptionKey reKey) throws Exception {
        TransactionReceipt receipt = this.systemContract.assignReader(new Utf8String(name), new Utf8String(role), new Uint256(reKey.getReKey().getValue()),
                new Uint256(reKey.getInternalPublicKey().getValue().getXCoord().toBigInteger()), new Uint256(reKey.getInternalPublicKey().getValue().getYCoord().toBigInteger())).send();
        LOGGER.info("DataSet " + name + " shared, to " + role);
        return receipt;
    }

    public TransactionReceipt assignWriter(String name, String role) throws Exception {
        TransactionReceipt receipt = this.systemContract.assignWriter(new Utf8String(name), new Utf8String(role)).send();
        LOGGER.info("DataSet " + name + " shared, to " + role);
        return receipt;
    }

    public TransactionReceipt addvolume(String name, String volume, String hash) throws Exception {
        DataSet_sol_DataSet dc = DataSet_sol_DataSet.load(this.getDCAddr(name), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return dc.addVolume(new Utf8String(volume), new Utf8String(hash)).send();
    }

    public TransactionReceipt proxyAdd(String name, String volume, String hash) throws Exception {
        return this.systemContract.proxyAdd(new Utf8String(name), new Utf8String(volume), new Utf8String(hash)).send();
    }

    public String getOwner(String name) throws Exception {
        DataSet_sol_DataSet dc = DataSet_sol_DataSet.load(this.getDCAddr(name), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return dc.owner().send().getValue();
    }

    public int getAmount(String name) throws Exception {
        DataSet_sol_DataSet dc = DataSet_sol_DataSet.load(this.getDCAddr(name), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return dc.amount().send().getValue().intValue();
    }

    public String getRole(String address) throws Exception {
        return this.systemContract.getRole(new Address(address)).send().getValue();
    }

    public Capsule getUserKey(String addr) throws Exception {
        List<Uint256> reCapsule = this.systemContract.getKey(new Address(addr)).send().getValue();
        Curve crv = new Curve("secp256k1");
        return new Capsule(new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(0).getValue(), reCapsule.get(1).getValue())),
                new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(2).getValue(), reCapsule.get(3).getValue())),
                new Scalar(reCapsule.get(4).getValue(), crv), new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(5).getValue(), reCapsule.get(6).getValue())), true);
    }

    public Capsule getRoleKey(String role, String data) throws Exception {
        Role_sol_Role rc = Role_sol_Role.load(this.getRCAddr(role), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        List<Uint256> reCapsule = rc.getReading(new Address(this.getDCAddr(data))).send().getValue();

        Curve crv = new Curve("secp256k1");
        return new Capsule(new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(0).getValue(), reCapsule.get(1).getValue())),
                new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(2).getValue(), reCapsule.get(3).getValue())),
                new Scalar(reCapsule.get(4).getValue(), crv), new GroupElement(crv, crv.getCurve().createPoint(reCapsule.get(5).getValue(), reCapsule.get(6).getValue())), true);
    }

    public Capsule getDataKey(String data) throws Exception {
        DataSet_sol_DataSet dc = DataSet_sol_DataSet.load(this.getDCAddr(data), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        Tuple7<Uint256, Uint256, Uint256, Uint256, Uint256, Uint256, Uint256> capsule = dc.cipher().send();

        Curve crv = new Curve("secp256k1");
        return new Capsule(new GroupElement(crv, crv.getCurve().createPoint(capsule.getValue1().getValue(), capsule.getValue2().getValue())),
                new GroupElement(crv, crv.getCurve().createPoint(capsule.getValue3().getValue(), capsule.getValue4().getValue())),
                new Scalar(capsule.getValue5().getValue(), crv), new GroupElement(crv, crv.getCurve().createPoint(capsule.getValue6().getValue(), capsule.getValue7().getValue())), false);
    }

    public Volume read(String name, int id) throws Exception {
        DataSet_sol_DataSet dc = DataSet_sol_DataSet.load(this.getDCAddr(name), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String volume = dc.getVolumeName(new Uint256(id)).send().getValue();
        String time = sdf.format(new Date(dc.getVolumeTime(new Uint256(id)).send().getValue().intValue() * 1000L));
        String hash = dc.getVolumeHash(new Uint256(id)).send().getValue();
        return new Volume(id, volume, time, hash);
    }

    public String getAdminAddr() {
        return this.adminAddr;
    }

    public String getRCAddr(String name) throws Exception {
        return this.systemContract.getRC(new Utf8String(name)).send().getValue();
    }

    public String getRCPubKey(String name) throws Exception {
        Role_sol_Role rc = Role_sol_Role.load(this.getRCAddr(name), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return rc.pubkey().send().getValue();
    }

    public String getDCAddr(String name) throws Exception {
        return this.systemContract.getDC(new Utf8String(name)).send().getValue();
    }

    public boolean checkReader(String data, String address) throws Exception {
        Role_sol_Role rc = Role_sol_Role.load(this.getRCAddr(this.getRole(address)), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return !rc.getReading(new Address(this.getDCAddr(data))).send().getValue().get(0).getValue().equals(BigInteger.ZERO);
    }

    public boolean checkWriter(String data, String address) throws Exception {
        Role_sol_Role rc = Role_sol_Role.load(this.getRCAddr(this.getRole(address)), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return rc.getWriting(new Address(this.getDCAddr(data))).send().getValue();
    }

    public boolean checkRoleAdmin(String role, String address) throws Exception {
        Role_sol_Role rc = Role_sol_Role.load(this.getRCAddr(role), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return address.equals(rc.admin().send().getValue());
    }

    public boolean checkDataOwner(String data, String address) throws Exception {
        DataSet_sol_DataSet dc = DataSet_sol_DataSet.load(this.getDCAddr(data), this.web3j, this.credentials, GAS_PRICE, GAS_LIMIT);
        return address.equals(dc.owner().send().getValue());
    }
}
