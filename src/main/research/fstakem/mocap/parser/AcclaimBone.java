package main.research.fstakem.mocap.parser;

import java.util.ArrayList;

public class AcclaimBone 
{
	// Constants
	
	// Variables
	public int id;
	public String name;
	public ArrayList<Float> direction;
	public float length;
	public ArrayList<Float> axis;
	public ArrayList<String> dof;
	public ArrayList< ArrayList<Float>> limits;
	
	public AcclaimBone()
	{
		this.id = -1;
		this.name = "";
		this.direction = new ArrayList<Float>();
		this.length = -1.0f;
		this.axis = new ArrayList<Float>();
		this.dof = new ArrayList<String>();
		this.limits = new ArrayList< ArrayList<Float>>();
	}
}
