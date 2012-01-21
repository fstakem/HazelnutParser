package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.List;

public class CharacterElementState 
{
	public List<Float> state;
	
	public CharacterElementState()
	{
		this(new ArrayList<Float>());
	}
	
	public CharacterElementState(List<Float> state)
	{
		if(state != null)
			this.state = state;
		else
			this.state = new ArrayList<Float>();
	}
	
	public List<Float> getValues()
	{
		return this.state;
	}
	
	public void setValues(List<Float> state)
	{
		if(state != null)
			this.state = state;
		else
			throw new IllegalArgumentException("The state cannot be null.");
	}
	
	@Override
	public boolean equals(Object obj)
	{
        if(obj == this)
            return true;
        
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        
       CharacterElementState other_state = (CharacterElementState) obj;
        
       for(int i = 0; i < this.state.size(); i++)
       {
    	   if(this.state.get(i) != other_state.state.get(i))
    		   return false;
       }
        
        return true;
	}
	
	@Override
	public String toString()
	{
		String output = "(";
		
		for(int i = 0; i < this.state.size(); i++)
		{
			output += String.valueOf(this.state.get(i));
			
			if(i < this.state.size() - 1)
				output += ", ";
			else
				output += ")";
		}
		
		return output;
	}
}
