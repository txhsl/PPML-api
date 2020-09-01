package org.txhsl.ppml.api.model;

public class DataSetRequest {
    private String filePath;
    private String hash;
    private int volume;
    private String encryptedHash;
    private String key;
    private String encryptedKey;
    private String reEncryptedKey;
    private String to;
    private boolean completed;

    public DataSetRequest() {

    }

    public String getEncryptedHash() {
        return encryptedHash;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getKey() {
        return key;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public String getReEncryptedKey() {
        return reEncryptedKey;
    }

    public String getHash() {
        return hash;
    }

    public int getVolume() {
        return volume;
    }

    public String getTo() {
        return to;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setEncryptedHash(String encryptedHash) {
        this.encryptedHash = encryptedHash;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public void setReEncryptedKey(String reEncryptedKey) {
        this.reEncryptedKey = reEncryptedKey;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
