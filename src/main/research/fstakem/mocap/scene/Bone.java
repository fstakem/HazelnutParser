package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3f;

import main.research.fstakem.mocap.parser.AcclaimData;

public class Bone extends CharacterElement
{
	// Bone characteristics
	private int id;
	private Vector3f direction;
	private float length;
	private List<AcclaimData.OperationOnAxis> dof;
	private List<List<Float>> limits;
		
	public Bone()
	{
		this.setId(1);
		
		this.setLength(0.0f);
		ArrayList<AcclaimData.OperationOnAxis> dof = new ArrayList<AcclaimData.OperationOnAxis>(Arrays.asList(AcclaimData.OperationOnAxis.RX, 
																							    			  AcclaimData.OperationOnAxis.RY, 
																							    			  AcclaimData.OperationOnAxis.RZ));
		this.setDof(dof);
		List<Float> x_limits = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f));
		List<Float> y_limits = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f));
		List<Float> z_limits = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f));
		List<List<Float>>limits = new ArrayList<List<Float>>();
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
	
	public Vector3f getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(Vector3f direction)
	{
		if(direction != null)
			this.direction = direction;
		else
			throw new IllegalArgumentException("The direction cannot be null.");
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
		
	public List<AcclaimData.OperationOnAxis> getDof()
	{
		return this.dof;
	}
	
	public void setDof(List<AcclaimData.OperationOnAxis> dof)
	{
		if(dof != null)
			this.dof = dof;
		else
			throw new IllegalArgumentException("The dof cannot be set to null.");
	}
	
	public List<List<Float>> getLimits()
	{
		return this.limits;
	}
	
	public void setLimits(List<List<Float>> limits)
	{
		if(limits != null)
			this.limits = limits;
		else
			throw new IllegalArgumentException("The limits cannot be set to null.");
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
		Vector3f start_position = this.getStartPosition();
		Vector3f direction = this.getDirection();
		direction.normalize();
		direction.scale(this.getLength());
		start_position.x += direction.x;
		start_position.y += direction.y;
		start_position.z += direction.z;
		
		return start_position;
	}
	
	@Override
	public CharacterElement getParent()
	{
		CharacterElement parent = super.getParent();
		if(parent == null)
			throw new IllegalStateException("Cannot calculate the position of an element that does not have a parent.");
		
		return parent;
	}
	
	@Override
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		Vector3f end_position = this.getEndPosition();
		output.append(" end point(");
		output.append(end_position.x);
		output.append(", ");
		output.append(end_position.y);
		output.append(", ");
		output.append(end_position.z);
		output.append(")");
		
		return super.toString() + output.toString();
	}
}
