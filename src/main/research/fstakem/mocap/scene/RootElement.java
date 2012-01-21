package main.research.fstakem.mocap.scene;

import java.util.List;

import javax.vecmath.Vector3f;

import main.research.fstakem.mocap.parser.AcclaimData;

public class RootElement extends CharacterElement
{
	// Default values
	public static final String ROOT = "root";
	
	// Root characteristics
	private Vector3f position;
	private AcclaimData.OperationOnAxis[] amc_data_order;
	
	public RootElement()
	{
		this.setName(RootElement.ROOT);
		this.setStartPosition(new Vector3f());
		AcclaimData.OperationOnAxis[]  amc_data_order = new AcclaimData.OperationOnAxis[]{ AcclaimData.OperationOnAxis.TX,
																						   AcclaimData.OperationOnAxis.TY, 
																						   AcclaimData.OperationOnAxis.TZ, 
																						   AcclaimData.OperationOnAxis.RX, 
																						   AcclaimData.OperationOnAxis.RY, 
																						   AcclaimData.OperationOnAxis.RZ }; 
		this.setAmcDataOrder(amc_data_order);
	}
	
	public RootElement(List<CharacterElement> bones)
	{
		this();
		this.setChildren(bones);
	}
	
	public Vector3f getStartPosition()
	{
		return this.position;
	}
	
	public void setStartPosition(Vector3f position)
	{
		if(position != null)
			this.position = position;
		else
			throw new IllegalArgumentException("The position cannot be set to null.");
	}
	
	public Vector3f getEndPosition()
	{
		return this.position;
	}
		
	public AcclaimData.OperationOnAxis[] getAmcDataOrder()
	{
		return this.amc_data_order;
	}
	
	public void setAmcDataOrder(AcclaimData.OperationOnAxis[] amc_data_order)
	{
		if(amc_data_order.length == 6)
		{
			if(this.amc_data_order == null)
				this.amc_data_order = new AcclaimData.OperationOnAxis[6];
			
			for(int i = 0; i < amc_data_order.length; i++)
				this.amc_data_order[i] = amc_data_order[i];
		}
		else
			throw new IllegalArgumentException("The order of the amc must have 6 values.");
	}
}
