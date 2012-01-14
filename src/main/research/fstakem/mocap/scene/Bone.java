package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.Arrays;

import javax.vecmath.Vector3f;


public class Bone extends CharacterElement
{
	// Bone characteristics
	private int id;
	private Vector3f orientation;
	private float length;
	private ArrayList<Float> axis;
	private ArrayList<Dof> dof;
	private ArrayList<ArrayList<Float>> limits;
	
	// Graphics
	protected GraphicsObject graphics_object;
	
	public Bone()
	{
		this.setId(1);
		this.setLength(0.0f);
		ArrayList<Float> axis = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f, 0.0f));
		this.setAxis(axis);
		ArrayList<Dof> dof = new ArrayList<Dof>(Arrays.asList(Dof.RX, Dof.RY, Dof.RZ));
		this.setDof(dof);
		ArrayList<Float> x_limits = new ArrayList<Float>(Arrays.asList(0.0f, 10.0f));
		ArrayList<Float> y_limits = new ArrayList<Float>(Arrays.asList(0.0f, 10.0f));
		ArrayList<Float> z_limits = new ArrayList<Float>(Arrays.asList(0.0f, 10.0f));
		ArrayList<ArrayList<Float>>limits = new ArrayList<ArrayList<Float>>();
		limits.add(x_limits);
		limits.add(y_limits);
		limits.add(z_limits);
		this.setLimits(limits);
		this.setGraphicsObject(null);
	}
	
	public Bone(String name)
	{
		this();
		this.setName(name);
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		if(id >= 0)
			this.id = id;
		else
			throw new IllegalArgumentException("The id cannot be negative.");
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
	
	public float getLength()
	{
		return this.length;
	}
	
	public void setLength(float length)
	{
		if(length >= 0)
			this.length = length;
		else
			throw new IllegalArgumentException("The length must be greater than 0.");
	}
		
	public ArrayList<Float> getAxis()
	{
		return this.axis;
	}
	
	public void setAxis(ArrayList<Float> axis)
	{
		if(axis != null)
			this.axis = axis;
		else
			throw new IllegalArgumentException("The axis cannot be set to null.");
	}
	
	public ArrayList<Dof> getDof()
	{
		return this.dof;
	}
	
	public void setDof(ArrayList<Dof> dof)
	{
		if(dof != null)
			this.dof = dof;
		else
			throw new IllegalArgumentException("The dof cannot be set to null.");
	}
	
	public ArrayList<ArrayList<Float>> getLimits()
	{
		return this.limits;
	}
	
	public void setLimits(ArrayList<ArrayList<Float>> limits)
	{
		if(limits != null)
			this.limits = limits;
		else
			throw new IllegalArgumentException("The limits cannot be set to null.");
	}
	
	public GraphicsObject getGraphicsObject()
	{
		return this.graphics_object;
	}
	
	public void setGraphicsObject(GraphicsObject graphics_object)
	{
		this.graphics_object = graphics_object;
	}
}
