package com.itt.tds.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class UtilityTest {

	@Test
	public void testStringArrayListToJSONArray() {
		// arrange
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("hi");
		arrList.add("hello");
		arrList.add("world");
		String jsonString = "[\"hi\",\"hello\",\"world\"]";

		// act
		String convertedJSONArrayString = Utility.stringArrayListToJSONArray(arrList);

		// assert
		assertEquals(convertedJSONArrayString, jsonString);
	}

	@Test
	public void testJsonArrayToStringArrayList() {
		// arrange
		String jsonString = "[\"hi\",\"hello\",\"world\"]";

		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("hi");
		arrList.add("hello");
		arrList.add("world");

		// act
		ArrayList<String> convertedArrayList = Utility.jsonArrayToStringArrayList(jsonString);

		// assert
		assertEquals(convertedArrayList, arrList);
	}

}
