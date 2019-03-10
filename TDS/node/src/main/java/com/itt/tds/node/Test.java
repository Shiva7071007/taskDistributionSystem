package com.itt.tds.node;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itt.tds.logging.TDSLogger;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

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
