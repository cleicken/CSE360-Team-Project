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
    public LinkedList<Node> activitiesList = new LinkedList<Node>(); //linked list for all activities
    //private LinkedList<Integer> durationlist1 = new LinkedList<Integer>();
    private	LinkedList<Node> starters = new LinkedList<Node>();
    private LinkedList<LinkedList<Node>> pathList = new LinkedList<LinkedList<Node>>();
    private	LinkedList<Node> enders = new LinkedList<Node>();
    private LinkedList<Node> success = new LinkedList<Node>();
    private LinkedList<Node>[] printers;
    private String[] separateCommas;
    //private LinkedList<String> multdep = new LinkedList<String>();
    //private String tempString;
    //private int current = -1; //keeps track of num of dependencies
    //private int currentbegin = 0;
    private char comma = ',';
    //boolean isDone = false;
    

    public int sort()
    {
    	int result= 0;
    	/*
    	 0 = no start node
    	 1 = success
    	 2 = cycle error 
    	 3 = disconnect (multiple start nodes)
    	 */
    	
    	LinkedList<Node> queue = new LinkedList<Node>(activitiesList);
    	ListIterator<Node> temp = queue.listIterator(0);
    	boolean check = false;
    	
    	while (temp.hasNext() && !check)
    	{
    		Node t = temp.next();
    		if (t.prev == null)
    		{
    			success.add(t);
    			queue.remove(t);
    			check = true;
    		}
    	}
    	
    	if (!check)
    	{
    		return result;
    	}
    	System.out.println("STARTING NODE FOUND");
    	result++; //makes result 1 so success is the default case
    	

    	while (queue.size() > 0 )
    	{
    		boolean progress = false;
    		ListIterator<Node> itr = queue.listIterator(0);
    		while (itr.hasNext())
    		{
    			check = true;
    			Node current = itr.next();
    			System.out.println("Current node is " + current.name);
    			
    			if (current.prev == null)
    			{
    				System.out.println("DISCONNECT");
    				return result + 2; //returns result as 3, meaning a disconnect was detected
    			}
    			
    			for (int i = 0; i < current.prev.length;i++)
    			{
    				System.out.println("CHECKING DEPENDENCY "+ i + " FOR NODE " + current.name);
    				Node p = getNode(current.prev[i]);
    				if (!success.contains(p))
    				{
    					check = false;
    				}
    			}
    			
    			if (check)
    			{
    				success.add(current);
    				System.out.println("NODE " + current.name + " HAS BEEN ADDED");
    				queue.remove(current);
    				progress = true;
    			}
    			
    			if(!progress)
        		{
    				System.out.println("CYCLE");
        			return result + 1; //returns result as 2, meaning a cycle was detected
        		}
    		}		
    	}
    	
    	return result; //result is defaulted to 1, meaning successful sort 
    }
    
	public void addActivity(Node n)					//adds a node as a new activity in the network
	{
		activitiesList.add(n);						//adds the node to the list of activities
		//LinkedList<Node> test = new LinkedList<Node>();
		//test.add(n);
		//pathList.add(test);
		//ListIterator<LinkedList<Node>> pathItr = pathList.listIterator(0);
		if(n.prev != null)
		{
			for(int i = 0; i < n.prev.length;i++)		//loops through prev[] array to update the "futures" arrays of those nodes
			{
				//getNode(n.prev[i]).futures.add(n);		//gets the node that's named in the prev[] array and then adds the current node into its futures
				//getNode(n.prev[i]).end = false;			//sets previous nodes to not be marked as endpoints any more
				//Iterator<Node> nodeItr = pathItr.next().descendingIterator();
			}
		}
	}

    public int calcPathNum()										//calculates the number of paths in the network at processing time
    {
		ListIterator<Node> itr = activitiesList.listIterator(0);	//iterates through all nodes in the network
		int num = 0;													//will store number of paths
		while( itr.hasNext())										//loops until the end of the list
		{
			num = num + itr.next().getWeight();						//adds up the weights of each node to calculate the number of paths
		}
		return num;													//returns number of paths
	}

	public String processNetwork()
	{
		
		String name = "";
                int dur = 0;
		//ListIterator<LinkedList<Node>> pathItr = pathList.listIterator(0);
                ListIterator<Node> itr = success.listIterator(0);
                System.out.println("before");
		while(itr.hasNext())
		{
			//Iterator<Node> nodeItr = pathItr.next().descendingIterator();
                    System.out.println("after");
                    Node neX = itr.next();
                    dur = dur + neX.duration;
                    name = name + " => " + neX.name;
		}
                name = name + " Duration: " + dur;
		return name;
	}

	public Node getNode(String name)								//find the node with that string name and returns it
	{
		Node found = null;											//initializes variable
		ListIterator<Node> itr = activitiesList.listIterator(0);	//creates iterator to go through activities list, searching for the node
		while(itr.hasNext() && (found != null))
		{
			Node r = itr.next();
            System.out.println("Node " + r.name + " is checked against " + name);			
			if( r.name.equals(name) )							//if the next node's name matches the parameter's name, return the node whose name that is
				found =  r;
		}
		return found;
	}
}