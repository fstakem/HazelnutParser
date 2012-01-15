package main.research.fstakem.mocap.scene;

import java.util.ArrayList;

public class Character 
{
	private String name;
	private CharacterElement root;
	
	public Character()
	{
		this("Burdell");
	}
	
	public Character(String name)
	{
		this.setName(name);
		this.setRootCharacterElement(new RootElement());
	}
	
	public CharacterElement getRootCharacterElement()
	{
		return this.root;
	}
	
	public void setRootCharacterElement(CharacterElement root)
	{
		if(root != null)
			this.root = root;
		else
			throw new IllegalArgumentException("The root cannot be set to null.");
	}
	
	public ArrayList<CharacterElement> getAllCharacterElements()
	{
		ArrayList<CharacterElement> character_elements = new ArrayList<CharacterElement>();
		character_elements.add(this.root);
		character_elements.addAll(this.root.getAllSubElements());
	
		return character_elements;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void calculatePositionOfElements()
	{
		
	}
}
