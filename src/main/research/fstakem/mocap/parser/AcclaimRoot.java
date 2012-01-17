package main.research.fstakem.mocap.parser;

import javax.vecmath.Vector3f;

public class AcclaimRoot 
{
	// Constants
	
	// Variables
	public AcclaimData.OperationOnAxis[] amc_data_order;
	public AcclaimData.Axis[] orientation_order;
	public Vector3f position;
	public Vector3f orientation;
	
	public AcclaimRoot()
	{
		this.amc_data_order = new AcclaimData.OperationOnAxis[6];
		this.orientation_order = new AcclaimData.Axis[3];
		this.position = new Vector3f();
		this.orientation = new Vector3f();
	}
}
