package com.itt.tds.node;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.itt.tds.core.Task;
import com.itt.tds.core.TaskResult;
import com.itt.tds.logging.TDSLogger;
import com.itt.tds.node.ConfigGenerator.LogLevel;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 */
@Command(name = "start", mixinStandardHelpOptions = true, header = "starts the node")
public class Node implements Runnable {
	@Option(names = { "-t",
			"--timeout" }, description = "timeout for valid connection with co-ordinator in minutes. Valid values: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
	int timeout = 10;

	static Logger logger = new TDSLogger().getLogger();

	@Override
	public void run() {
		NodeConfiguration nodeCfg = NodeConfiguration.getInstance();
		
	}

	// /**
	// *
	// */
	// private String iP;
	//
	// /**
	// *
	// */
	// private int port;
	//
	// /**
	// *
	// */
	// private int Status;
	//
	// /**
	// *
	// */
	// private Task currentTask;
	//
	// /**
	// *
	// */
	// private int id;
	//
	// /**
	// *
	// */
	// private Socket coordinatorSocket;
	//
	// /**
	// * @param taskInstance
	// */
	// public void executeTask(Task taskInstance) {
	// // TODO implement here
	// }
	//
	// /**
	// * @param result
	// */
	// public void postResults(TaskResult result) {
	// // TODO implement here
	// }
	//
	// /**
	// *
	// */
	// public void pingCoordinator() {
	// // TODO implement here
	// }
	//
	// /**
	// * @return
	// */
	// public Task waitForTask() {
	// // TODO implement here
	// return null;
	// }
	//
	// public String getiP() {
	// return iP;
	// }
	//
	// public void setiP(String iP) {
	// this.iP = iP;
	// }
	//
	// public int getPort() {
	// return port;
	// }
	//
	// public void setPort(int port) {
	// this.port = port;
	// }
	//
	// public int getStatus() {
	// return Status;
	// }
	//
	// public void setStatus(int status) {
	// Status = status;
	// }
	//
	// public Task getCurrentTask() {
	// return currentTask;
	// }
	//
	// public void setCurrentTask(Task currentTask) {
	// this.currentTask = currentTask;
	// }
	//
	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }

}