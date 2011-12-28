package main.research.fstakem.mocap.scene;

import java.util.ArrayList;

public class CharacterElementState 
{
	public ArrayList<Float> state;
	
	public CharacterElementState()
	{
		this(new ArrayList<Float>());
	}
	
	public CharacterElementState(ArrayList<Float> state)
	{
		if(state != null)
			this.state = state;
		else
			this.state = new ArrayList<Float>();
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
}
