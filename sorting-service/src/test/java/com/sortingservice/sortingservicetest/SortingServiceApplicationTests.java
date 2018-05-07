package com.sortingservice.sortingservicetest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sortingservice.sortingservice.controller.SortNumbersController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortingServiceApplicationTests {

	private SortNumbersController controller;
	
	@Test
	public void contextLoads() {
		controller = new SortNumbersController();
	}
	
	@Test
	public void checkRandomNumberGeneration() {
		controller = new SortNumbersController();
		String randomNumberArray = controller.getRandomNumbersArray();
		assertNotNull(randomNumberArray);
	}
	
	@Test
	public void sortRandomNumbers() {
		controller = new SortNumbersController();
		String randomNumberArray = controller.getRandomNumbersArray();
		String sortedArray = controller.sortRandomArray(randomNumberArray);
		assertNotNull(sortedArray);
	}

}
