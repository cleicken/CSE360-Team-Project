import java.util.*;
//import java.util.ArrayList;
//import java.util.Scanner;

public class Network {
	//Scanner keyboard = new Scanner(System.in);
    private int duration;
    private String activityName;
    private String dependencies;
    private String node;
    private String answer;
    private LinkedList<Node> activitiesList = new LinkedList<Node>(); //linked list for all activities
    //private LinkedList<Integer> durationlist1 = new LinkedList<Integer>();
    private	LinkedList<Node> starters = new LinkedList<Node>();
    private	LinkedList<Node> enders = new LinkedList<Node>();
    private String[] separateCommas;
    //private LinkedList<String> multdep = new LinkedList<String>();
    //private String tempString;
    //private int current = -1; //keeps track of num of dependencies
    //private int currentbegin = 0;
    private char comma = ',';
    //boolean isDone = false;



	public void addActivity(Node n)					//adds a node as a new activity in the network
	{
		activitiesList.add(n);						//adds the node to the list of activities
		for(int i = 0; i < n.prev.length;i++)		//loops through prev[] array to update the "futures" arrays of those nodes
		{
			getNode(n.prev[i]).futures.add(n);		//gets the node that's named in the prev[] array and then adds the current node into its futures
		}
	}

    public String calcPath()
    {
		//this will iterate throught the list of ending nodes and (eventually, hopefully I'll figure it out tonight) work backwards recursively to create all the paths
		ListIterator<Node> itr = enders.listIterator(0);
		while( itr.hasNext())
		{
		}
		return "hi";	//temporary return so it compiles
	}

	public Node getNode(String name)								//find the node with that string name and returns it
	{
		Node found = null;											//initializes variable
		ListIterator<Node> itr = activitiesList.listIterator(0);	//creates iterator to go through activities list, searching for the node
		while(itr.hasNext())
		{
			if( itr.next().name == name)							//if the next node's name matches the parameter's name, return the node whose name that is
				found =  itr.next();
		}
		return found;
	}



}
