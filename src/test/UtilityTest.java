package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.itt.tds.utility.Utility;

class UtilityTest {

	@Test
	void testStringArrayListToJSONArray() {
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
	void testJsonArrayToStringArrayList() {
		// arrange
		String jsonString = "[\"hi\",\"hello\",\"world\"]";
		
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("hi");
		arrList.add("hello");
		arrList.add("world");
		
		// act
		ArrayList<String> convertedArrayList =  Utility.jsonArrayToStringArrayList(jsonString);
		
		// assert
		assertEquals(convertedArrayList, arrList);
	}

}
