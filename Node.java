import java.util.*;

public class Node
{
	public String name;
	public int duration;
	public LinkedList<Node> futures = new LinkedList<Node>();
	public String[] prev = null;
	public boolean end = false;

	public Node(String name1, int duration1)
	{
			name = name1;
			duration = duration1;
	}

	public Node(String name1, String dependencies, int duration1)
	{
		name = name1;
		duration = duration1;
		prev = dependencies.split("\\s*,\\s*");
		end = true;
	}

	public int getWeight()
	{
		if (end == true)
			return 0;
		else
		{
			int w = prev.length * futures.size();
			if(w > 1)
				return w;
			else
				return 0;
		}
	}


}
