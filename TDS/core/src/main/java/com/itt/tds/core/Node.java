package com.itt.tds.core;

public class Node {
	/**
     * Default constructor
     */
    public Node() {
    }

    /**
     * 
     */
    private String iP;

    /**
     * 
     */
    private int port;

    /**
     * 
     */
    private int Status;

    /**
     * 
     */
    private int id;
    
    
    public String getiP() {
		return iP;
	}

	public void setiP(String iP) {
		this.iP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
