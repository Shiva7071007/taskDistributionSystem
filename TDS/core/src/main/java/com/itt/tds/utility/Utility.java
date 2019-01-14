package com.itt.tds.utility;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Utility {

	public static String stringArrayListToJSONArray(ArrayList<String> arrList) {
		Gson converter = new Gson();
		String jsonArray = converter.toJson(arrList);
		return jsonArray;
	}
	
	public static ArrayList<String> jsonArrayToStringArrayList(String jsonArray) {
		Gson converter = new Gson();
		Type type = new TypeToken<ArrayList<String>>(){}.getType(); 
		ArrayList<String> arrList =converter.fromJson(jsonArray, type );
		return arrList;
	}
	
	public static byte[] convertToByte(File task) {
		byte[] bytesArray = new byte[(int) task.length()];

		FileInputStream fis;
		try {
			fis = new FileInputStream(task);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();
		} catch (Exception e) {
			System.err.println("error: unable to read file");
		}

		return bytesArray;
	}
}
