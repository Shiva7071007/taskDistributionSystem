import java.util.List;

import com.itt.tds.coordinator.Server;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.*;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
@Command(description = "A multi-node Task executer Server", mixinStandardHelpOptions = true, // auto-include --help and
																								// --version
		subcommands = { Server.class })
public class App implements Runnable {

	public static void main(String[] args) {
		App app = new App();
		if (args.length == 0)
			CommandLine.usage(app, System.out);

		List<Object> result = new CommandLine(app).parseWithHandler(new RunAll(), args);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
