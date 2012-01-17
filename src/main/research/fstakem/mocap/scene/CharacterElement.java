package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterElement 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CharacterElement.class);
		
	// State
	private Vector3f start_position;
	private Vector3f orientation;
	
	// Attributes
	private String name;
	private List<CharacterElementState> states;
	private int current_state;
	
	// Linking
	private CharacterElement parent;
	private List<CharacterElement> children;
	
	public CharacterElement()
	{
		this("undefined-element");
	}
	
	public CharacterElement(String name)
	{
		this.setName(name);
		this.setChildren(new ArrayList<CharacterElement>());
		this.setStates(new ArrayList<CharacterElementState>());
		this.current_state = 0;
		this.setStartPosition(new Vector3f());
		this.setOrientation(new Vector3f());
	}
	
	public Vector3f getStartPosition()
	{
		return this.start_position;
	}
	
	public void setStartPosition(Vector3f position)
	{
		if(position != null)
			this.start_position = position;
		else
			throw new IllegalArgumentException("The position cannot be set to null.");
	}
	
	public Vector3f getEndPosition()
	{
		return this.start_position;
	}
	
	public Vector3f getGlobalOrientation()
	{
		return new Vector3f(this.orientation);
	}
				
	public Vector3f getOrientation()
	{
		return this.orientation;
	}
	
	public void setOrientation(Vector3f orientation)
	{
		if(orientation != null)
			this.orientation = orientation;
		else
			throw new IllegalArgumentException("The orientation cannot be set to null.");
	}
		
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setStates(List<CharacterElementState> states)
	{
		if(states != null)
			this.states = states;
		else
			throw new IllegalArgumentException("The state cannot be set to null.");
	}
	
	public void addState(CharacterElementState state)
	{
		if(state != null)
			this.states.add(state);
		else
			throw new IllegalArgumentException("The state cannot be set to null.");
	}
	
	public void addStateAt(int index, CharacterElementState state)
	{
		if(index == -1)
			this.states.add(state);
		else if(index > 0 && index < this.states.size())
			this.states.add(index, state);
		else
			throw new IndexOutOfBoundsException("The state index is out of bounds.");
	}
	
	public CharacterElementState getState(int index) throws Exception
	{
		if(index > 0 && index < this.states.size())
			return this.states.get(index);
		else
			throw new IndexOutOfBoundsException("The state index is out of bounds.");
	}
	
	public void setCurrentState(int current_state)
	{
		if(current_state >= 0 && current_state < this.states.size())
			this.current_state = current_state;
		else
			throw new IndexOutOfBoundsException("The state index is out of bounds.");
	}
	
	public void resetCurrentState()
	{
		this.current_state = 0;
	}
	
	public void incrementState()
	{
		if(this.current_state < this.states.size() - 1)
			this.current_state += 1;
	}
	
	public void decrementState()
	{
		if(this.current_state > 0)
			this.current_state -= 1;
	}
	
	public CharacterElementState getCurrentState()
	{
		return this.states.get(this.current_state);
	}
	
	public int getNumberOfStates()
	{
		return this.states.size();
	}
	
	public CharacterElement getParent()
	{
		return this.parent;
	}
		
	public boolean hasChildren()
	{
		return (this.children.size() > 0);
	}
	
	public int getNumberOfChildren()
	{
		return this.children.size();
	}
	
	public List<CharacterElement> getChildren()
	{
		return this.children;
	}
	
	public List<CharacterElement> getAllSubElements()
	{
		List<CharacterElement> elements = new ArrayList<CharacterElement>();
		for(CharacterElement e : this.children)
		{
			elements.add(e);
			List<CharacterElement> child_elements = e.getAllSubElements();
			if(child_elements.size() > 0)
				elements.addAll(child_elements);
		}
		
		return elements;
	}
	
	public void setChildren(List<CharacterElement> children)
	{
		if(children != null)
		{
			for(CharacterElement child : children)
				child.parent = this;
			
			this.children = children;
		}	
		else
			throw new IllegalArgumentException("The child nodes cannot be set to null.");
	}
	
	public void addChildren(List<CharacterElement> children)
	{
		if(children != null)
		{
			for(CharacterElement child : children)
			{
				child.parent = this;
				this.children.add(child);
			}
		}	
		else
			throw new IllegalArgumentException("The child nodes cannot be set to null.");
	}
	
	public void addChild(CharacterElement child)
	{
		if(child != null)
		{
			child.parent = this;
			this.children.add(child);
		}
		else
			throw new IllegalArgumentException("The child nodes cannot be set to null.");
	}
		
	public CharacterElement findCharacterElement(String name)
	{
		if(this.name.equals(name))
			return this;
		else
		{
			for(CharacterElement bone : this.children)
			{
				CharacterElement element = bone.findCharacterElement(name);
				if(element != null)
					return element;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean equals(Object obj)
	{
        if(obj == this)
            return true;
        
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        
       CharacterElement other_element = (CharacterElement) obj;
        
       if(this.name.equals(other_element.name))
    	   return true;
        
        return false;
	}
	
	@Override
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		Vector3f global_orientation = this.getGlobalOrientation();
		output.append("\'");
		output.append(this.getName());
		output.append("\' location(");
		output.append(this.start_position.x);
		output.append(", ");
		output.append(this.start_position.y);
		output.append(", ");
		output.append(this.start_position.z);
		output.append(") rotation(");
		output.append(global_orientation.x);
		output.append(", ");
		output.append(global_orientation.y);
		output.append(", ");
		output.append(global_orientation.z);
		output.append(")");
		
		return output.toString();
	}
}
