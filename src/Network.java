
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.ArrayList;
//import java.util.Scanner;

public class Network {

    private int duration;
    public boolean criticals = false;
    public LinkedList<Node> activitiesList = new LinkedList<>(); //linked list for all activities
    private LinkedList<String> sortedList = new LinkedList<>();
    private LinkedList<Stack<Node>> pathList = new LinkedList<>();
    private ArrayList<Stack<Node>> printList = new ArrayList<>();
    private ArrayList<Integer> durationList = new ArrayList();
    Stack<Node>[] pathArray;
    Integer[] durationArray;
    private LinkedList<Node> success = new LinkedList<>();
    private boolean fileCreated = false;
    private String reporttitle = "";
    private FileOutputStream fos;
    private DataOutputStream outStream;
    private boolean datePrinted = false;
    
    public void createReport(String name) {
    	
    	try {
    		if (fileCreated == false) {
    		fos = new FileOutputStream(name + ".txt");
    		String currentdate = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(new Date());
    		//outStream = new DataOutputStream(new BufferedOutputStream(fos));
    		PrintStream out = new PrintStream(fos);
    		out.println("[Title]" + System.lineSeparator());
    		out.println(name + System.lineSeparator() + System.lineSeparator());
    		out.println("[Date, Time]" + System.lineSeparator());
    		out.println(currentdate + System.lineSeparator() + System.lineSeparator());
    		out.println("[Activity Name   |   Duration]"+System.lineSeparator());
    		out.println(sortActivities() + System.lineSeparator() + System.lineSeparator());
    		out.println("[Path =>| Duration]" + System.lineSeparator());
    		fileCreated = true;
    		}

	    } catch (IOException ioe) {
			System.out.println("Trouble reading from the file: " + ioe.getMessage());
	    }

    }

    public int sort() {
        int result = 0;
        /*
    	 0 = no start node
    	 1 = success
    	 2 = cycle error 
    	 3 = disconnect (multiple start nodes)
         */

        LinkedList<Node> toRemove = new LinkedList<>();
        LinkedList<Node> queue = new LinkedList<>(activitiesList);
        ListIterator<Node> temp = queue.listIterator(0);
        boolean check = false;

        while (temp.hasNext() && !check) {
            Node t = temp.next();
            if (t.prev == null) {
                success.add(t);
                toRemove.add(t);
                check = true;
            }
        }
        ListIterator<Node> rem = toRemove.listIterator(0);
        while (rem.hasNext())
        {
            queue.remove(rem.next());
        }

        if (!check) {
            return result;
        }
        System.out.println("STARTING NODE FOUND");
        result++; //makes result 1 so success is the default case

        while (queue.size() > 0) {
            boolean progress = false;
            ListIterator<Node> itr = queue.listIterator(0);
            while (itr.hasNext()) {
                check = true;
                Node current = itr.next();
                System.out.println("Current node is " + current.name);

                if (current.prev == null) {
                    System.out.println("DISCONNECT");
                    return result + 2; //returns result as 3, meaning a disconnect was detected
                }

                for (int i = 0; i < current.prev.length; i++) 
                {
                    System.out.println("CHECKING DEPENDENCY " + i + " FOR NODE " + current.name);
                    Node p = getNode(current.prev[i]);
                    if (!success.contains(p)) {
                        check = false;
                    }
                }

                if (check) {
                    success.add(current);
                    if (current.prev != null) 
                    {
                        for (int i = 0; i < current.prev.length; i++)
                        {
                            getNode(current.prev[i]).futures.add(current);		
                            getNode(current.prev[i]).end = false;			
                        }
                    }
                    System.out.println("NODE " + current.name + " HAS BEEN ADDED");
                    toRemove.add(current);
                    progress = true;
                }

                if (!progress) {
                    System.out.println("CYCLE");
                    return result + 1; //returns result as 2, meaning a cycle was detected
                }
            }
            rem = toRemove.listIterator(0);
            while (rem.hasNext())
            {
                queue.remove(rem.next());
            }
        }

        return result; //result is defaulted to 1, meaning successful sort 
    }

    public void addActivity(Node n) //adds a node as a new activity in the network
    {
        activitiesList.add(n);						//adds the node to the list of activities        
    }

    public int calcPathNum() //calculates the number of paths in the network at processing time
    {
        ListIterator<Node> itr = activitiesList.listIterator(0);	//iterates through all nodes in the network
        int num = 0;													//will store number of paths
        while (itr.hasNext()) //loops until the end of the list
        {
            num = num + itr.next().getWeight();						//adds up the weights of each node to calculate the number of paths
        }
        return num;													//returns number of paths
    }

    public String processNetwork() 
    {
        makePaths();
        sortPaths();
        String name = "";
        LinkedList<Stack<Node>> pList = new LinkedList<>(pathList);
        ArrayList<Integer> dList = new ArrayList(durationList);
        ListIterator<Stack<Node>> pathItr = pList.listIterator();
        ListIterator<Integer> durItr = dList.listIterator();
        
        Stack<Node> tempStack = pathItr.next();
        while(!tempStack.isEmpty())
        {
            Node temp = tempStack.pop();
            name = name  + temp.name + " => ";
        }
        int criticalDuration = durItr.next();
        name = name + "| Duration: " + criticalDuration + "\n";
        
        if(criticals)
        {
            while (pathItr.hasNext()) 
            {
                tempStack = pathItr.next();
                Node temp = tempStack.peek();
                int dur = durItr.next();
                if(dur == criticalDuration)
                {
                    while(!tempStack.isEmpty())
                    {
                        temp = tempStack.pop();
                        name = name  + temp.name + " => ";
                    }
                    name = name + "| Duration: " + dur + "\n";
                }
                else
                {
                    break;
                }
            }
        }
        else
        {
            while (pathItr.hasNext()) 
            {
                tempStack = pathItr.next();
                while(!tempStack.isEmpty())
                {
                    Node temp = tempStack.pop();
                    name = name  + temp.name + " => ";
                }
                name = name + "| Duration: " + durItr.next() + "\n";
            
            }
        }
        //name = name + sortActivities();
        
        return name;
    }
    
    private void makePaths()
    {
        ListIterator<Node> itr = success.listIterator(0);
        Stack<Node> s = new Stack<>();
        s.push(itr.next());
        System.out.println(s.peek().name + " is the starting node.");
        pathList.add(s);        
        
        
        while(itr.hasNext())
        {
            Node temp = itr.next();
            for(int i = 0; i < temp.prev.length; i++)
            {
                Node dep = getNode(temp.prev[i]);
                System.out.println(dep.name + " is being added to pathlist");
                LinkedList<Stack<Node>> tempPath = new LinkedList<>(pathList);
                ListIterator<Stack<Node>> pathItr = tempPath.listIterator(0);
                while(pathItr.hasNext())
                {
                    Stack ptr = pathItr.next();
                    if (ptr.peek() == dep)
                    {
                        Stack tStack = (Stack)ptr.clone();
                        tStack.push(temp);
                        pathList.add(tStack);
                    }
                }
            }
        }
        
        LinkedList<Stack<Node>> tempPath = new LinkedList<>(pathList);
        ListIterator<Stack<Node>> pathItr = tempPath.listIterator(0);
        while(pathItr.hasNext())
        {
            duration = 0;
            boolean end = false;
            Stack<Node> midSwap = new Stack<>();
            Stack<Node> mid = pathItr.next();
            
            if(mid.peek().end)
            {
                end = true;
            }
            
            while(!mid.isEmpty())
            {
                Node temp = mid.pop();
                midSwap.push(temp);
                duration = duration + temp.duration;
            }
                        
            if(end)
            {
                printList.add(midSwap);
                durationList.add(duration);
            }
        }
    }
    
    public void sortPaths()
    {
        pathArray = printList.toArray(new Stack[printList.size()]);
        durationArray = durationList.toArray(new Integer[durationList.size()]);
        for(int i = durationArray.length; i > 0;i--)
        {
            for(int j = 1; j < i; j++)
            {
                if(durationArray[j-1] < durationArray[j])
                {
                    Stack<Node> tempStack = pathArray[j-1];
                    pathArray[j-1] = pathArray[j];
                    pathArray[j] = tempStack;
                    
                    Integer tempInt = durationArray[j-1];
                    durationArray[j-1] = durationArray[j];
                    durationArray[j] = tempInt;
                }
            }
        }
        pathList.clear();
        durationList.clear();
        for(int k = 0; k < durationArray.length; k++)
        {
            pathList.add(pathArray[k]);
            durationList.add(durationArray[k]);
        }
    }
    
    public void clearData()
    {
        activitiesList.clear();
        success.clear();
        pathList.clear();
        printList.clear();
        durationList.clear();
    }
    
    public String sortActivities()
    {
        sortedList.clear();
        String sortedActivities = "";
        LinkedList<Node> queue = new LinkedList<>(activitiesList);
        ListIterator<Node> temp = queue.listIterator(0);
        while(temp.hasNext())
        {
            Node t = temp.next();
            sortedList.add(t.name);
        }
        Collections.sort(sortedList);
        ListIterator<String> tempS = sortedList.listIterator(0);
        while(tempS.hasNext())
        {
            String s = tempS.next();
            int sDur = getNode(s).duration;
            sortedActivities += s + " | " + sDur + System.lineSeparator();
        }     
        return sortedActivities;
    }

    public Node getNode(String name) //find the node with that string name and returns it
    {
        Node found = null;											//initializes variable
        ListIterator<Node> itr = activitiesList.listIterator(0);	//creates iterator to go through activities list, searching for the node
        while (itr.hasNext() && (found == null)) {
            Node r = itr.next();
            System.out.println("Node " + r.name + " is checked against " + name);
            if (r.name.equals(name)) //if the next node's name matches the parameter's name, return the node whose name that is
            {
                found = r;
            }
        }
        return found;
    }
    
    
    


}