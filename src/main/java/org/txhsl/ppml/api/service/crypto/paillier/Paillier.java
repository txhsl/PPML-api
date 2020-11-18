package org.txhsl.ppml.api.service.crypto.paillier;

import java.math.BigInteger;

public class Paillier {

    public static BigInteger add(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public static BigInteger multiply(BigInteger a, int b) {
        return a.pow(b);
    }
}
