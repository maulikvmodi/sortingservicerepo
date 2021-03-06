package com.sortingservice.sortingservice.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sortingservice.constants.SortingServiceConstants;
import com.sortingservice.sortingservice.beans.SortedValuesBean;
import com.sortingservice.sortingservice.beans.SortingValuesRepository;


/**
 * @author maulik
 * Class declared with @RestController annotation for Generating and Sorting Random Number Array
 */
@RestController
public class SortNumbersController {

	
	private JSONArray randomNumberArrayClone;
	private int arr[];
	
	@Autowired
	SortingValuesRepository sortingRepository;
	
	@RequestMapping(value="/previousValues", method = RequestMethod.GET)
	public @ResponseBody String getPreviouslyGeneratedValues() {
		try {
			SortedValuesBean bean = sortingRepository.getLastSortedValues();
			JSONObject lastSortedObj = new JSONObject();
			lastSortedObj.put("original", bean.getOriginalstring());
			lastSortedObj.put("sorted", bean.getSortedstring());
			return lastSortedObj.toString();
		}catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Request Method for generating Array of Random Numbers.
	 * @return
	 */
	@RequestMapping(value="/getRandomNumbers", method=RequestMethod.GET)
	public @ResponseBody String getRandomNumbersArray() {
		JSONArray randomNumArray = new JSONArray();
		
		for(int randomCount = 0; randomCount < 50; randomCount++) {
			Random r = new Random();
			randomNumArray.put(r.ints(1,(100+1)).limit(1).findFirst().getAsInt());
		}
		return randomNumArray.toString();
	}
	
	/**
	 * Request Method for Sorting the Array of Random Numbers.
	 * @param randomArrayString
	 * @return
	 */
	@RequestMapping(value="/sortRandomNumbers", method=RequestMethod.POST)
	public @ResponseBody String sortRandomArray(@RequestParam("randomObj") String randomArrayString) {
		try {
			JSONArray randomNumberArray = new JSONArray(randomArrayString);
			this.arr = new int[randomNumberArray.length()];
			for(int i=0;i<randomNumberArray.length();i++) {
				this.arr[i]= randomNumberArray.getInt(i);
			}
			randomNumberArrayClone = new JSONArray();
			implementSortingAlgorithm();
			
			if(sortingRepository != null) {
				SortedValuesBean bean = new SortedValuesBean();
				bean.setOriginalstring(randomArrayString);
				bean.setSortedstring(Arrays.toString(this.arr));
				bean.setCreateddate(new java.sql.Timestamp(new Date().getTime()));
				sortingRepository.insertSortedData(bean);
			}          
			return randomNumberArrayClone.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
	
	/**
	 * Implementing Bubble Sorting Algorithm
	 */
	private void implementSortingAlgorithm() {
		try {
			int length = this.arr.length;
			int tempVar;
			for(int randomNumCount =0; randomNumCount<length;randomNumCount++) {
				for(int i = 0;i<length-1;i++) {
					tempVar = i+1;
					if(this.arr[i] > this.arr[tempVar]) {
						swapNumbers(i, tempVar);
					}
				}
				//printNumbers();
				JSONObject sortedArrayObject = new JSONObject();
				sortedArrayObject.put(SortingServiceConstants.KEY, Integer.toString(randomNumCount));
				sortedArrayObject.put(SortingServiceConstants.VALUE, new JSONArray(Arrays.asList(this.arr)));
				randomNumberArrayClone.put(sortedArrayObject);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method swaps two number for sorting purpose
	 * @param firstNumber
	 * @param secondNumber
	 */
	private void swapNumbers(int firstNumber, int secondNumber) {
		try {
			int tempVar = this.arr[firstNumber];
			this.arr[firstNumber] = this.arr[secondNumber];
			this.arr[secondNumber]= tempVar;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method Prints Array Numbers.
	 */
	private void printNumbers() {
		for (int i = 0; i < this.arr.length; i++) {
            System.out.print(this.arr[i] + ", ");
        }
        System.out.println("\n");
	}
	
}

