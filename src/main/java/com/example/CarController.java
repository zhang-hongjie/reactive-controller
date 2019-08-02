package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping(value = "/api/v1/cars")
public class CarController {

    private Car car1 = new Car()
            .setId("1")
            .setComment("good")
            .setDate(new Date())
            .setEstimation(6000D)
            .setIsout(false);

    /**
     *  Works OK
     */
    @GetMapping("flux")
    public Flux<Car> findAllFlux() {
        return Flux.fromIterable(asList(car1));
    }

    /**
     * The 4 methods below not work
     */
    @GetMapping("pojo")
    public Car findOnePojo() {
        return car1;
    }

    @GetMapping("list")
    public List<Car> findAllList() {
        return asList(car1);
    }

    @GetMapping("mono-pojo")
    public Mono<Car> findOneMono() {
        return Mono.just(car1);
    }

    @GetMapping("mono-list")
    public Mono<List<Car>> findAllMonoList() {
        return Mono.just(asList(car1));
    }


}