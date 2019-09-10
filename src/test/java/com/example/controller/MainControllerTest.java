package com.example.controller;

import com.example.model.MainEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@IntegrationTest
@AutoConfigureWebTestClient
public class MainControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	@Sql(scripts = {"classpath:db/fixture/mains.sql"})
	public void test_get_all() {
		client.get().uri("/api/v1/mains")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.length()").isEqualTo(2)
				.jsonPath("$.[0].id").isEqualTo("id")
				.jsonPath("$.[1].id").isEqualTo("id2");
	}

	@Test
	public void test_create() {
		client.post().uri("/api/v1/mains")
				.body(Mono.just(MainEntity.builder().comment("abc").build()), MainEntity.class)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.comment").isEqualTo("abc");
	}

}