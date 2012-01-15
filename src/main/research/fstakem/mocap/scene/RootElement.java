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
	// TODO => change axis to an enum of XYZ, YXZ, etc; test as well
	private ArrayList<Axis> axis;
	
	public RootElement()
	{
		this.setName(RootElement.ROOT);
		ArrayList<Dof> order = new ArrayList<Dof>(Arrays.asList(Dof.TX, Dof.TY, Dof.TZ, Dof.RX, Dof.RY, Dof.RZ));
		this.setOrder(order);
		ArrayList<Axis> axis = new ArrayList<Axis>(Arrays.asList(Axis.X, Axis.Y, Axis.Z));
		this.setAxis(axis);
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
}
