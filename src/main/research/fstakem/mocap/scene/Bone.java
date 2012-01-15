package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.Arrays;

import javax.vecmath.Vector3f;

public class Bone extends CharacterElement
{
	// Bone characteristics
	private int id;
	private float length;
	private ArrayList<Float> axis;
	private ArrayList<Dof> dof;
	private ArrayList<ArrayList<Float>> limits;
		
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
	
	@Override
	public Vector3f getGlobalOrientation()
	{
		CharacterElement parent = this.getParent();
		Vector3f orientation = parent.getGlobalOrientation();
		orientation.add(this.getOrientation());
		return orientation;
	}
		
	@Override
	public Vector3f getStartPosition()
	{
		CharacterElement parent = this.getParent();
		return new Vector3f(parent.getEndPosition());
	}
		
	@Override
	public Vector3f getEndPosition()
	{
		CharacterElement parent = this.getParent();
		Vector3f end_position = parent.getEndPosition();
		Vector3f global_orientation = this.getGlobalOrientation();
		end_position.x += global_orientation.x * this.length;
		end_position.y += global_orientation.y * this.length;
		end_position.z += global_orientation.z * this.length;
		
		return end_position;
	}
	
	@Override
	public CharacterElement getParent()
	{
		CharacterElement parent = super.getParent();
		if(parent == null)
			throw new IllegalStateException("Cannot calculate the position of an element that does not have a parent.");
		
		return parent;
	}
}
