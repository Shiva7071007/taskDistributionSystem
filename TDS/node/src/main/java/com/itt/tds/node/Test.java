package com.itt.tds.node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import picocli.CommandLine.Command;

@Command(name = "test", mixinStandardHelpOptions = true, header = "test")
public class Test implements Runnable {

	@Override
	public void run() {
		try {
			String line = null;
			String command = "welcome.exe ";
			Runtime run = Runtime.getRuntime();
			Process proc = run.exec(command);
			BufferedReader bri = new BufferedReader
			        (new InputStreamReader(proc.getInputStream()));
			BufferedReader bre = new BufferedReader
			        (new InputStreamReader(proc.getErrorStream()));
			while ((line = bri.readLine()) != null) {
		        System.out.println(line);
			}
			bri.close();
		      while ((line = bre.readLine()) != null) {
		        System.out.println(line);
		      }
		      bre.close();
		      proc.waitFor();
		      System.out.println("Done.");
			System.out.println("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
