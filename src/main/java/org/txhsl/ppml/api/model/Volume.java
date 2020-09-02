package org.txhsl.ppml.api.model;

public class Volume {
    int number;
    String name;
    String time;
    String hash;

    public Volume(int number, String name, String time, String hash) {
        this.number = number;
        this.name = name;
        this.time = time;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public int getNumber() {
        return number;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
