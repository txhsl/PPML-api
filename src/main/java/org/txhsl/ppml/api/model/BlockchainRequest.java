package org.txhsl.ppml.api.model;

public class BlockchainRequest {

    // rbac
    private String role;
    private String data;
    private String user;
    private String accessKey;
    private String rcPubKey;
    private String dcKey;
    private String userPubKey;
    private boolean permitted;

    // ipfs
    private String name;
    private String hash;
    private String path;

    // dc
    private String owner;
    private int amount;
    private Volume[] volumes;
    private String tx;

    private boolean completed;

    public BlockchainRequest() {

    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setRcPubKey(String rcPubKey) {
        this.rcPubKey = rcPubKey;
    }

    public void setDcKey(String dcKey) {
        this.dcKey = dcKey;
    }

    public void setUserPubKey(String userPubKey) {
        this.userPubKey = userPubKey;
    }

    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setVolumes(Volume[] volumes) {
        this.volumes = volumes;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getRole() {
        return role;
    }

    public String getData() {
        return data;
    }

    public String getUser() {
        return user;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getRcPubKey() {
        return rcPubKey;
    }

    public String getDcKey() {
        return dcKey;
    }

    public String getUserPubKey() {
        return userPubKey;
    }

    public boolean isPermitted() {
        return permitted;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public String getPath() {
        return path;
    }

    public String getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public Volume[] getVolumes() {
        return volumes;
    }

    public String getTx() {
        return tx;
    }

    public boolean isCompleted() {
        return completed;
    }
}
