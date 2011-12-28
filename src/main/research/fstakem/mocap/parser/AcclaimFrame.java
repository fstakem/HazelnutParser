package main.research.fstakem.mocap.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class AcclaimFrame 
{
	// Constants
	
	// Variables
	public int number;
	public HashMap<String, ArrayList<Float>> bone_positions;
	
	public AcclaimFrame()
	{
		this.number = -1;
		this.bone_positions = new HashMap<String, ArrayList<Float>>();
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.number);
		sb.append("\n");
		for(Entry<String, ArrayList<Float>> entry : this.bone_positions.entrySet())
		{
			sb.append(entry.getKey() + "\t");
			ArrayList<Float> bone_values = entry.getValue();
			for(int i = 0; i < bone_values.size(); i++)
			{	
				sb.append(bone_values.get(i));
				sb.append("\t");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
