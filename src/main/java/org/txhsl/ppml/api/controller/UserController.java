package org.txhsl.ppml.api.controller;

import org.springframework.web.bind.annotation.*;
import org.txhsl.ppml.api.model.UserRequest;
import org.txhsl.ppml.api.service.BlockchainService;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final BlockchainService blockchainService;

    public UserController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @PostMapping("/login")
    public UserRequest login(@RequestBody UserRequest request) throws Exception {
        blockchainService.login(request.getAccount(), request.getPassword());
        request.setBalance(blockchainService.getBalance());

        return request;
    }

    @GetMapping("/balance")
    public double getBalance() throws IOException {
        return blockchainService.getBalance();
    }
}
