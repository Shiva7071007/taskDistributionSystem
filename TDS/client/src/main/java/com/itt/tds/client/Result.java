package com.itt.tds.client;

import static picocli.CommandLine.*;

@Command(name = "result", mixinStandardHelpOptions = true, header = "get the result for passed task ID")
public class Result implements Runnable {
	public static void main(String[] args) {


	}

	@Override
	public void run() {
		System.out.println("result" );
		
	}
}