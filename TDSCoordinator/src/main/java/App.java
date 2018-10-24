/*
 * This Java source file was generted by the Gradle 'init' task.
 */

import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.itt.tds.cfg.TDSConfiguration;
import com.itt.tds.coordinator.db.repository.TDSNodeRepository;
import com.itt.tds.core.NodeState;
import com.itt.tds.node.Node;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new App().getGreeting());
	}
}
