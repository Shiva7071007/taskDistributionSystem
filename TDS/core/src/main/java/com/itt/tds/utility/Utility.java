package com.itt.tds.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itt.tds.TDSExceptions.SocketReadWriteException;
import com.itt.tds.TDSExceptions.UnableToReadFileException;
import com.itt.tds.TDSExceptions.RuntimeExceptions.FatalException;
import com.itt.tds.comm.TDSRequest;
import com.itt.tds.comm.TDSResponse;
import com.itt.tds.errorCodes.TDSError;
import com.itt.tds.logging.TDSLogger;

public class Utility {
	static Logger logger = new TDSLogger().getLogger();

	private static final String USER_NAME = "user.name";
	protected static final String SEPARATOR = " : ";
	protected static final String ERROR_CODE = "Error-code";

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

	public static byte[] convertFileToByte(File file) throws UnableToReadFileException {
		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();
		} catch (IOException e) {
			throw new UnableToReadFileException(TDSError.FAILED_TO_READ_FILE, e);
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
			throw new FatalException(TDSError.UNKNOWN_IP, e);
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
			throw new FatalException(TDSError.UNKNOWN_HOST, e);
		}
		return hostName;
	}

	public static String getUserName() {
		return System.getProperty(USER_NAME);
	}

	public static TDSResponse prepareResponseFromrequest(TDSRequest request) {
		TDSResponse response = new TDSResponse();

		response.setProtocolVersion(request.getProtocolVersion());
		response.setProtocolFormat(request.getProtocolFormat());
		response.setSourceIp(request.getDestIp());
		response.setSourcePort(request.getDestPort());
		response.setDestIp(request.getSourceIp());
		response.setDestPort(request.getSourcePort());

		return response;
	}

	public static void displayErrorMsg(TDSResponse response) {
		String errorCode = response.getErrorCode();
		String errorMsg = response.getErrorMessage();
		logger.error(ERROR_CODE + SEPARATOR + errorCode + " " + errorMsg);
	}

	public static String getRequest(Socket sock) throws SocketReadWriteException {
		BufferedReader socketReader = null;
		String request = "";
		try {
			socketReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			request = socketReader.readLine();
		} catch (IOException e) {
			throw new SocketReadWriteException(TDSError.FAILED_TO_READ_FROM_SOCKET, e);
		}
		return request;
	}

	public static void writeResponse(Socket sock, String responseData) throws SocketReadWriteException {
		PrintWriter socketWriter = null;
		try {
			socketWriter = new PrintWriter(sock.getOutputStream(), true);
			socketWriter.println(responseData);
		} catch (IOException e) {
			throw new SocketReadWriteException(TDSError.FAILED_TO_READ_FROM_SOCKET, e);
		}
	}

	public static void closeSocket(Socket sock) {
		try {
			sock.close();
		} catch (IOException e) {
			try {
				sock.close();
			} catch (IOException e1) {
				logger.error("Not able to close sock " + sock, e1);
			}
		}
	}
}
