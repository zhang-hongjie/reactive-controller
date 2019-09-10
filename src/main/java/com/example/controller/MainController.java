package com.example.controller;

import com.example.service.MainService;
import com.example.model.MainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/mains")
public class MainController {

    @Autowired
    private MainService service;

    @GetMapping
    public List<MainEntity> findAll() {
        return service.findAll();
    }

    @GetMapping("/reactive")
    public Flux<MainEntity> findAllReactive() {
        return service.findAllReactive();
    }

    @PostMapping
    public MainEntity create(MainEntity main) {
        return service.create(main);
    }

    @PutMapping("/{id}")
    public MainEntity update(@PathVariable("id") String id, MainEntity main) {
        Objects.requireNonNull(id);
        return service.update(id, main);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        Objects.requireNonNull(id);
        service.delete(id);
    }
}