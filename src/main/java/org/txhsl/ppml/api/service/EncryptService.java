package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptService.class);

    public EncryptService() {}

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
