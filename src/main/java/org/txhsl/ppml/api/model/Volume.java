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
}
