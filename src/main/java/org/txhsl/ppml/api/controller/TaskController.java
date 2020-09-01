package org.txhsl.ppml.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txhsl.ppml.api.service.BlockchainService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final BlockchainService blockchainService;

    public TaskController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }
}
