package com.example.service;

import com.example.repository.MainRepository;
import com.example.model.MainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MainService {

	@Autowired
	private MainRepository repository;

	@Autowired
	private Scheduler scheduler;

	@Transactional(readOnly = true)
	public List<MainEntity> findAll() {
		List<MainEntity> result = repository.findAll();
		return result;
	}

	@Transactional(readOnly = true)
	public Flux<MainEntity> findAllReactive() {
		List<MainEntity> result = repository.findAll();
		return Flux.defer(() -> Flux.fromIterable(result))
				.subscribeOn(scheduler)
				.doOnError(e -> {
					log.error("Error when findingById a VSN by ID", e);
					throw new RuntimeException(e);
				});
	}

	public MainEntity create(MainEntity mainEntity) {
		return repository.save(mainEntity);
	}

	public MainEntity update(String id, MainEntity mainEntity) {
		mainEntity.setId(id);
		return repository.save(mainEntity);
	}

	public void delete(String id) {
		repository.deleteById(id);
	}
}
