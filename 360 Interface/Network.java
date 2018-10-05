import java.util.*;
//import java.util.ArrayList;
//import java.util.Scanner;

public class Network {
	Scanner keyboard = new Scanner(System.in);
    private int duration;
    private String activity;
    private String dependencies;
    private String node;
    private String answer;
    private LinkedList<String> activitieslist1 = new LinkedList<String>(); //linked list for activities and durations
    private LinkedList<Integer> durationlist1 = new LinkedList<Integer>();
    private		LinkedList<String> dependencieslist1 = new LinkedList<String>();
    private String[] separateCommas;
    private LinkedList<String> multdep = new LinkedList<String>();
    private String tempString;
    private int current = -1; //keeps track of num of dependencies
    private int currentbegin = 0;
    private char comma = ',';
    boolean isDone = false;


 

	// constructor
    public void Network() {
    	//do { //input activities, durations, and dependicies until done
    	/*
    	System.out.print("duration: ");
		duration = keyboard.nextInt();
		durationlist1.add(duration);
		
		System.out.print("activity: ");
		activity = keyboard.next();
		activitieslist1.add(activity);
		
		System.out.print("dependencies: ");
		dependencies = keyboard.next();
		dependencieslist1.add(dependencies);*/
		
		//checkIfDone(isDone);

		/*
    	System.out.print(durationlist1);
    	System.out.print("Done with input?: ");
    	answer = keyboard.next();
    	if (answer.equals("yes")) {
    		isDone = true;
    	}
		System.out.print(isDone);
    	}while(isDone == false);*/
    	
    	System.out.print("Enter crap here");
    
    }
    

    public LinkedList<Integer> setDuration(LinkedList<Integer> durationlist1) {
    	
    	System.out.print("duration: ");
		duration = keyboard.nextInt();
		durationlist1.add(duration);
		System.out.print(durationlist1);
		return durationlist1;
    }
    
    public LinkedList<String> setActivity(LinkedList<String> activitieslist1) {
    	
    	System.out.print("activity: ");
		activity = keyboard.next();
		activitieslist1.add(activity);
		
		return activitieslist1;
    }
    
    
    public LinkedList<String> setDependency(LinkedList<String> dependencieslist1) {
    	
		System.out.print("dependencies: ");
		dependencies = keyboard.next();
		dependencieslist1.add(dependencies);
		//separateCommas = dependencieslist1.getLast().split(",");
		current = current + 1;
		return dependencieslist1;
    }
    
    
    

    public String[] checkForDependencies(LinkedList<String> dependencieslist1) {
    
    String[] multipledependencies;
    multipledependencies = dependencieslist1.get(currentbegin).split("\\s*,\\s*"); //split dependencies of activities into array by comma
    System.out.print(multipledependencies);
    ++currentbegin;
	return multipledependencies;  
   }
   
   
   
    public void printNetwork(LinkedList<Integer> durationlist1) {
        System.out.print(durationlist1);
    }
    
    
    //checking to see if user is done with input
    
    /*
    public boolean checkIfDone(boolean isDone){
    	String answer;
    	System.out.print("Done with input?");
    	answer = keyboard.next();
    	if (answer.equals("yes")) {
    		isDone = true;
    	}
    	//System.out.print(isDone);
    	return isDone;
    }*/
}