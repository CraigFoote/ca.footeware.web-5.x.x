/*******************************************************************************
 * Copyright (c) 2016 Footeware.ca
 *******************************************************************************/
package ca.footeware.web.tests;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.footeware.web.controllers.ImageController;
import ca.footeware.web.controllers.JokeController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private JokeController jokeController;

	@Autowired
	private ImageController imageController;

	@Test
	public void jokeControllerLoads() {
		Assertions.assertThat(jokeController).isNotNull();
	}

	@Test
	public void imageControllerLoads() {
		Assertions.assertThat(imageController).isNotNull();
	}

}