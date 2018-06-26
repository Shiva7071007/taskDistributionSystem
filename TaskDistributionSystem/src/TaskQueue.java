
public class TaskQueue {
	
	public static void addTaskToQueue(String taskName) {
		//will add task to Queue database
	}
	
	public static int getTaskID(String taskName) {
		//will return ID of task. It will auto generate inside db.
		int taskId = 0;	
		// logic	
		return taskId;
	}
	
	public static String setStatus(String taskName) {
		//will set task status initially to queued.
		String taskStatus = "queued";
		// logic
		return taskStatus;
	}
	
	

	public static void initTask(String taskName) {
		// TODO Auto-generated method stub
		addTaskToQueue( taskName );
		String taskStatus = setStatus( taskName );
		int taskId = getTaskID( taskName );
		
		System.out.print(taskStatus);
		System.out.print(", task ID: ");
		System.out.print(taskId);
	}

}
