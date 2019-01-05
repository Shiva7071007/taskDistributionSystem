package com.itt.tds.client;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import static picocli.CommandLine.*;


@Command(name = "query", header = "get the current status of task by task ID")
public class Query implements Runnable {

	public static void main(String[] args) {
	}

	@Override
	public void run() {
		System.out.println("Query");
		// TODO Auto-generated method stub
		
	}

}
