package com.rent.store;

import com.rent.store.web.rest.controllers.MovieController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RentStoreServiceApplicationTests {

	@Autowired
	private MovieController movieController;

	@Test
	public void shouldLoadContext() {
		Assertions.assertThat(movieController).isNotNull();
	}

}
