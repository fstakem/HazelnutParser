package main.research.fstakem.mocap.parser;

import java.util.ArrayList;
import java.util.List;

public class AmcData extends AcclaimData
{
	// Constants
	
	// Variables
	public List<AcclaimFrame> frames;
	
	public AmcData()
	{
		this.frames = new ArrayList<AcclaimFrame>();
	}
	
	public String toString()
	{
		String output = "";
		for(int i = 0; i < this.frames.size(); i++)
			output += this.frames.get(i).toString();
		
		return output;
	}
}

