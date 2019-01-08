package com.itt.tds.client;

import java.io.File;
import java.util.Collections;
import java.util.List;

import picocli.CommandLine.*;
import picocli.CommandLine.Parameters;

@Command(name = "queue", mixinStandardHelpOptions = true, header = "add a program in queue for execution")
public class Queue implements Runnable {
	@Parameters(index = "0", description = "Executables files that needs to be sent to server")
	File task;

	@Parameters(index = "1..*", description = "parameters need to be passed with task")
	List<String> parameters;

	@Override
	public void run() {

		if (!task.exists()) {
			System.err.println("passed task: " + task.getName() + "doesn't exist");
			System.err.println("please provide a correct task file address");
			System.exit(0);
		} else {
			if (parameters == null)
				parameters = Collections.emptyList();

			Client client = new Client();
			String statusId = client.queueTask(task, parameters);
			System.out.println(statusId);
		}

	}

}
