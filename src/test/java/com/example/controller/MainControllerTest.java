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
@Sql(scripts = {"classpath:db/fixture/main.sql"})
@IntegrationTest
@AutoConfigureWebTestClient
public class MainControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void test() {
		client.get().uri("/api/v1/mains?page=0&size=20")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.length()").isEqualTo(0);
	}

}