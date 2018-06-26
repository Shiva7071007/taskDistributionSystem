
public class Help {

	public static void showHelp() {
		System.out.println("Task Distribution System");
		System.out.println("usage: taskmgr [options] [arguments]");
		System.out.println();
		System.out.println("Distribute task on different nodes within network for execution");
		System.out.println();
		System.out.println("Options:");
		System.out.println("queue <taskName>		queue the task that needs to be executed");
		System.out.println("quety <taskID>			get the current status of task");
		System.out.println("result <taskID>			get the result got from task");
		System.out.println("help				output usage information");	
	}
	
}
