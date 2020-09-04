package org.txhsl.ppml.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txhsl.ppml.api.service.ListenerService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final ListenerService listenerService;

    public TaskController(ListenerService listenerService) {
        this.listenerService = listenerService;
    }
}
