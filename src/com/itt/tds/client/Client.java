package com.itt.tds.client;

import com.itt.tds.core.Task;
import com.itt.tds.core.TaskResult;

/**
 * 
 */
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

	/**
	 * @param task
	 */
	public void sendTask(Task task) {
		// TODO implement here
	}

	/**
	 * @param taskId
	 * @return
	 */
	public TaskResult fetchResult(int taskId) {
		// TODO implement here
		return null;
	}

//	/**
//	 * @return
//	 */
//	private Array<byte> getProgram() {
//        // TODO implement here
//        return null;
//    }

	/**
	 * @return
	 */
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