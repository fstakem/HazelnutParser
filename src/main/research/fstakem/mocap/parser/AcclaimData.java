package main.research.fstakem.mocap.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AcclaimData 
{
	// Unique types
	public enum OperationOnAxis { TX, TY, TZ, RX, RY, RZ };
	public enum Axis { X, Y, Z };
	
	// Data structures for unique types
	private static final Map<String, OperationOnAxis> STRINGS_FOR_OPERATION_ON_AXIS;
	private static final Map<String, Axis> STRINGS_FOR_AXIS;
	
	static 
	{
		HashMap<String, OperationOnAxis> temp_map_a = new HashMap<String, OperationOnAxis>();
		temp_map_a.put("tx", OperationOnAxis.TX);
		temp_map_a.put("ty", OperationOnAxis.TY);
		temp_map_a.put("tz", OperationOnAxis.TZ);
		temp_map_a.put("rx", OperationOnAxis.RX);
		temp_map_a.put("ry", OperationOnAxis.RY);
		temp_map_a.put("rz", OperationOnAxis.RZ);
		STRINGS_FOR_OPERATION_ON_AXIS = Collections.unmodifiableMap(temp_map_a);
		
		HashMap<String, Axis> temp_map_b = new HashMap<String, Axis>();
		temp_map_b.put("x", Axis.X);
		temp_map_b.put("y", Axis.Y);
		temp_map_b.put("z", Axis.Z);
		STRINGS_FOR_AXIS = Collections.unmodifiableMap(temp_map_b);
    }

	public static OperationOnAxis getOperationOnAxisFromString(String value)
	{
		String lowerCaseValue = value.toLowerCase();
		
		for(Map.Entry<String, OperationOnAxis> entry : AcclaimData.STRINGS_FOR_OPERATION_ON_AXIS.entrySet())
		{
			if(lowerCaseValue.equals(entry.getKey()))
				return entry.getValue();
		}
	
		throw new IllegalArgumentException("The string value is not a recognized OperationOnAxis.");
	}
	
	public static String operationOnAxisToString(OperationOnAxis operationOnAxis)
	{
		for(Map.Entry<String, OperationOnAxis> entry : AcclaimData.STRINGS_FOR_OPERATION_ON_AXIS.entrySet())
		{
			if(entry.getValue().equals(operationOnAxis))
				return entry.getKey();
		}
	
		throw new IllegalArgumentException("The OperationOnAxis value is not a recognized OperationOnAxis.");
	}
	
	public static Axis getAxisFromString(String value)
	{
		String lowerCaseValue = value.toLowerCase();
		
		for(Map.Entry<String, Axis> entry : AcclaimData.STRINGS_FOR_AXIS.entrySet())
		{
			if(lowerCaseValue.equals(entry.getKey()))
				return entry.getValue();
		}
		
		throw new IllegalArgumentException("The string value is not a recognized Axis.");
	}
	
	public static String axisToString(Axis axis)
	{
		for(Map.Entry<String, Axis> entry : AcclaimData.STRINGS_FOR_AXIS.entrySet())
		{
			if(entry.getValue().equals(axis))
				return entry.getKey();
		}
		
		throw new IllegalArgumentException("The Axis value is not a recognized Axis.");
	}
}
