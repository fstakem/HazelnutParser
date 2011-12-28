package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.Arrays;


import javax.vecmath.Vector3f;



public class RootElement extends CharacterElement
{
	// Default values
	public static final String ROOT = "root";
	
	// Root characteristics
	private ArrayList<Dof> order;
	private ArrayList<Axis> axis;
	private Vector3f position;
	private Vector3f orientation;
	
	public RootElement()
	{
		this.setName(RootElement.ROOT);
		ArrayList<Dof> order = new ArrayList<Dof>(Arrays.asList(Dof.TX, Dof.TY, Dof.TZ, Dof.RX, Dof.RY, Dof.RZ));
		this.setOrder(order);
		ArrayList<Axis> axis = new ArrayList<Axis>(Arrays.asList(Axis.X, Axis.Y, Axis.Z));
		this.setAxis(axis);
		this.setPosition(new Vector3f());
		this.setOrientation(new Vector3f());
	}
	
	public RootElement(ArrayList<CharacterElement> bones)
	{
		this();
		this.setChildren(bones);
	}
		
	public ArrayList<Dof> getOrder()
	{
		return this.order;
	}
	
	public void setOrder(ArrayList<Dof> order)
	{
		if(order != null)
			this.order = order;
		else
			throw new IllegalArgumentException("The order cannot be set to null.");
	}
	
	public ArrayList<Axis> getAxis()
	{
		return this.axis;
	}
	
	public void setAxis(ArrayList<Axis> axis)
	{
		if(axis != null)
			this.axis = axis;
		else
			throw new IllegalArgumentException("The axis cannot be set to null.");
	}
	
	public Vector3f getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Vector3f position)
	{
		if(position != null)
			this.position = position;
		else
			throw new IllegalArgumentException("The position cannot be set to null.");
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
}
