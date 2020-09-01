package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CryptoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoService.class);

    public CryptoService() {}

    public String encrypt(String raw, String pubKey) {
        return raw;
    }

    public String decrypt(String encrypted, String prvKey) {
        return encrypted;
    }

    public String reEncrypt(String encrypted, String preKey) {
        return encrypted;
    }
}
