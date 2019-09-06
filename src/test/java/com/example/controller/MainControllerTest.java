package com.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@IntegrationTest
@AutoConfigureWebTestClient
public class MainControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void test() {
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

}