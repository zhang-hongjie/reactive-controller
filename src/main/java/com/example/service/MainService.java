package com.example.service;

import com.example.repository.MainRepository;
import com.example.model.MainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.util.List;

@Service
@Slf4j
public class MainService {

	@Autowired
	private MainRepository repository;

	@Autowired
	private Scheduler scheduler;

	@Transactional(readOnly = true)
	public Flux<MainEntity> findAll() {
		List<MainEntity> result = repository.findAll();
		return Flux.fromIterable(result).subscribeOn(scheduler);
	}

}
