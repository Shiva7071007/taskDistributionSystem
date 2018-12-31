package com.itt.tds.core;

public class Client {

	/**
	 * Default constructor
	 */
	public Client() {
	}

	/**
	 * 
	 */
	private String hostName;

	/**
	 * 
	 */
	private String userName;

	/**
	 * 
	 */
	private int id;
	
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}
