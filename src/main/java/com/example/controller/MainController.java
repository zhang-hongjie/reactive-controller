package com.example.controller;

import com.example.service.MainService;
import com.example.model.MainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/api/v1/mains")
public class MainController {

    @Autowired
    private MainService service;

    @ResponseBody
    @GetMapping
    public Flux<MainEntity> find() {
        return service.findAll();
    }

}