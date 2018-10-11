import java.util.*;

public class Node
{
	public String name;
	public int duration;
	public LinkedList<Node> futures = new LinkedList<Node>();
	public String[] prev = null;
	public boolean end = false;

	public void Node(String name, int duration)
	{
			name = name;
			duration = duration;
	}

	public void Node(String name, String dependencies, int duration)
	{
		name = name;
		duration = duration;
		prev = dependencies.split("\\s*,\\s*");
		end = true;
	}


}
