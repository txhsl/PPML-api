package org.txhsl.ppml.api.model;

public class RBACRequest {

    private String role;
    private String data;
    private String user;
    private boolean permitted;

    private String name;
    private String address;
    private String hash;
    private String path;

    private String owner;
    private int amount;
    private Volume[] volumes;

    private boolean completed;

    public RBACRequest() {

    }

    public String getRole() {
        return role;
    }

    public String getUser() {
        return user;
    }

    public String getData() {
        return data;
    }

    public boolean isPermitted() {
        return permitted;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
