
public class TaskMgr {

	public static void main(String[] args) {
		
		if (args.length == 0) {
	         System.out.println("No option passed. Showing help.");
	         Help.showHelp();
	      } else {
	    	  
	    	  String options = null;
	    	  String arguments = null;
	    	  
	    	  try {
	    		  
	    		  options = args[0];
	    		  arguments = args[1];
	    	  
	    		  switch (options) {
	    	  		case "queue" : TaskQueue.initTask(arguments);
	    	  		break;
	    	  		case "query" : Query.showTaskStatus(arguments);
	    	  		break;
	    	  		case "result" : Result.showResult(arguments);
	    	  		break;
	    	  		case "help" : Help.showHelp();
	    	  		break;
	    	  		default : System.out.println("invalid options. Refer help");
	    	  		Help.showHelp();  
	    		  }
	    		  
	    	  }catch (Exception e) {
	    		  System.out.println("invalid format. Refer help");
		  			Help.showHelp();  
	    	  }
	      }
	}

}
