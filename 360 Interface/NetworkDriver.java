import java.util.LinkedList;
import java.util.Scanner;
public class NetworkDriver {

	/*
	NetworkDriver() {
	    System.out.println("constructor of networkdriver");
	  }*/
	 
	/*
	  void computer_method() {
	    System.out.println("Power gone! Shut down your PC soon...");
	  }*/
	
	static boolean isDone = false;
	static Scanner keyboard = new Scanner(System.in);
    /*public static boolean checkIfDone(boolean isDone){
    	String answer;
    	System.out.print("Done with input?");
    	answer = keyboard.next();
    	if (answer.equals("yes")) {
    		isDone = true;
    	}
    	//System.out.print(isDone);
    	return isDone;
    }*/
    	
	public static void main(String[] args) {
		
	   LinkedList<Integer> durationlist = new LinkedList<Integer>();
	   LinkedList<String> activitylist = new LinkedList<String>();
	   LinkedList<String> dependencylist = new LinkedList<String>();
	   LinkedList<String> multdeplist= new LinkedList<String>();

		//int duration;
		//String activity;
		
		
		//do we need to ask for input or does the gui do that

		
	   // NetworkDriver my = new NetworkDriver();
	   Network o1 = new Network();
	 do {
	   
	    o1.setDuration(durationlist);
	    o1.setActivity(activitylist);
	    o1.setDependency(dependencylist);
	    o1.checkForDependencies(multdeplist);
	    //////////PUT CHECK FOR DEPENDENCIS HERE

	    
	    String answer;
    	System.out.print("Done with input?");
    	answer = keyboard.next();
    	if (answer.equals("yes")) {
    		isDone = true;
    	}
	 }while(isDone == false);
	 
	    //o1.checkForDependencies(multdeplist);
	 
	 
	    //o1.printNetwork(durationlist);
	    
		
	 
	    //my.computer_method();
	    //your.laptop_method();
	  }
}