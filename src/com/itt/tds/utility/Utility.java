package com.itt.tds.utility;

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
}
