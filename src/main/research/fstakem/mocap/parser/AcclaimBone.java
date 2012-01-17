package main.research.fstakem.mocap.parser;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

public class AcclaimBone 
{
	// Constants
	
	// Variables
	public int id;
	public String name;
	public Vector3f direction;
	public float length;
	public Vector3f orientation;
	public AcclaimData.Axis[] orientation_order;
	public List<AcclaimData.OperationOnAxis> dof;
	public List<List<Float>> limits;
	
	public AcclaimBone()
	{
		this.id = -1;
		this.name = "";
		this.direction = new Vector3f();
		this.length = -1.0f;
		this.orientation = new Vector3f();
		this.orientation_order = new AcclaimData.Axis[3];
		this.dof = new ArrayList<AcclaimData.OperationOnAxis>();
		this.limits = new ArrayList< List<Float>>();
	}
}
