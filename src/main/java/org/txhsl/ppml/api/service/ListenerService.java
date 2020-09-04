package org.txhsl.ppml.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.ArrayList;

@Service
public class ListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerService.class);

    private final Web3j web3j;
    private ArrayList<Address> listening = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public ListenerService(Web3j web3j) {
        this.web3j = web3j;
    }

    public boolean enableListener(Address address) {
        this.web3j.transactionFlowable().subscribe(tx -> {
            transactions.add(tx);
        });
        return true;
    }

    public Transaction[] getTransactions() {
        return this.transactions.toArray(new Transaction[0]);
    }

    public Address[] getListening() {
        return this.listening.toArray(new Address[0]);
    }
}
