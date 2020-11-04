package org.txhsl.ppml.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.protocol.Web3j;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BlockchainApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainApp.class);

    private final Web3j web3j;

    public BlockchainApp(Web3j web3j) {
        this.web3j = web3j;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApp.class, args);
    }

    @PostConstruct
    public void listen() {

        web3j.transactionFlowable().subscribe(tx -> {

            LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());

        });

        LOGGER.info("Subscribed");
    }
}
