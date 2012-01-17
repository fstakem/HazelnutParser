package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.research.fstakem.mocap.parser.AcclaimData;

public class RootElement extends CharacterElement
{
	// Default values
	public static final String ROOT = "root";
	
	// Root characteristics
	private AcclaimData.OperationOnAxis[] amc_data_order;
	private AcclaimData.Axis[] orientation_order;
	
	public RootElement()
	{
		this.setName(RootElement.ROOT);
		List<AcclaimData.OperationOnAxis> order = new ArrayList<AcclaimData.OperationOnAxis>(Arrays.asList(AcclaimData.OperationOnAxis.TX, 
																												AcclaimData.OperationOnAxis.TY, 
																												AcclaimData.OperationOnAxis.TZ, 
																												AcclaimData.OperationOnAxis.RX, 
																												AcclaimData.OperationOnAxis.RY, 
																												AcclaimData.OperationOnAxis.RZ));
		this.setOrder(order);
		List<AcclaimData.Axis> axis = new ArrayList<AcclaimData.Axis>(Arrays.asList(AcclaimData.Axis.X, AcclaimData.Axis.Y, AcclaimData.Axis.Z));
		this.setAxis(axis);
	}
	
	public RootElement(List<CharacterElement> bones)
	{
		this();
		this.setChildren(bones);
	}
		
	public AcclaimData.OperationOnAxis[] getAmcDataOrder()
	{
		return this.amc_data_order;
	}
	
	public void setAmcDataOrder(AcclaimData.OperationOnAxis[] amc_data_order)
	{
		if(amc_data_order.length == 6)
		{
			for(int i = 0; i < amc_data_order.length; i++)
				this.amc_data_order[i] = amc_data_order[i];
		}
		else
			throw new IllegalArgumentException("The order of the amc must have 6 values.");
	}
	
	public AcclaimData.Axis[] getOrientationOrder()
	{
		return this.orientation_order;
	}
	
	public void setOrientationOrder(AcclaimData.Axis[] orientation_order)
	{
		if(orientation_order.length == 3)
		{
			for(int i = 0; i < orientation_order.length; i++)
				this.orientation_order[i] = orientation_order[i];
		}
		else
			throw new IllegalArgumentException("The order of the amc orientation must have 3 values.");
	}
}
