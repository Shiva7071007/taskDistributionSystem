package com.itt.tds.client;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "queue", header = "add a program in queue for execution")
public class Queue implements Runnable{

	public static void main(String[] args) {

	}

	@Override
	public void run() {
		System.out.println("Queue");
		// TODO Auto-generated method stub
		
	}

}
