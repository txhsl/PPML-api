package org.txhsl.ppml.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txhsl.ppml.api.service.IPFSService;
import org.txhsl.ppml.api.service.MLService;

import java.io.IOException;

@RestController
@RequestMapping("/ml")
public class MLController {

    private final MLService mlService;

    private final IPFSService ipfsService;

    public MLController(MLService mlService, IPFSService ipfsService) {
        this.mlService = mlService;
        this.ipfsService = ipfsService;
    }

    @GetMapping("/testML")
    public void testML() throws IOException {
        mlService.testNaiveBayes();
        mlService.testMultinomialNaiveBayes();
    }
}
