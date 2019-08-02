package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {"spring.main.WebApplicationType=reactive"})
@AutoConfigureWebTestClient
public class CarControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void should_get_car_info_when_get_flux() {
		client.get().uri("/api/v1/cars/flux")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$.[0].id").isEqualTo(1);
	}

	@Test
	public void should_not_get_car_info_when_get_list() {
		client.get().uri("/api/v1/cars/list")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$.[0].id").isEqualTo(1);
	}
}