package com.itt.tds.utility;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class Utility {
	static Logger logger = new TDSLogger().getLogger();
	private static final String USER_NAME = "user.name";

	public static String stringArrayListToJSONArray(ArrayList<String> arrList) {
		Gson converter = new Gson();
		String jsonArray = converter.toJson(arrList);
		return jsonArray;
	}

	public static ArrayList<String> jsonArrayToStringArrayList(String jsonArray) {
		Gson converter = new Gson();
		Type type = new TypeToken<ArrayList<String>>() {
		}.getType();
		ArrayList<String> arrList = converter.fromJson(jsonArray, type);
		return arrList;
	}

	public static byte[] convertFileToByte(File task) {
		byte[] bytesArray = new byte[(int) task.length()];

		FileInputStream fis;
		try {
			fis = new FileInputStream(task);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();
		} catch (Exception e) {
			logger.error(TDSError.FAILED_TO_READ_FILE.toString());
			logger.trace(e);
		}

		return bytesArray;
	}

	public static byte[] stringToByteArray(String str) {
		byte[] byteArr = str.getBytes();
		return byteArr;
	}

	public static String byteToString(byte[] byteArr) {
		return new String(byteArr);
	}

	public static String getSourceIp() {
		InetAddress inetAddress = null;
		String ip = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			ip = inetAddress.getHostAddress();

		} catch (UnknownHostException e) {
			logger.error(TDSError.UNKNOWN_IP.toString());
			logger.trace(e);
		}
		return ip;
	}

	public static String getHostName() {
		InetAddress inetAddress = null;
		String hostName = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();

		} catch (UnknownHostException e) {
			logger.error(TDSError.UNKNOWN_HOST.toString());
			logger.trace(e);
		}
		return hostName;
	}

	public static String getUserName() {
		return System.getProperty(USER_NAME);
	}
}
