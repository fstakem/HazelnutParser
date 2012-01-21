package test.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3f;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AcclaimData;
import main.research.fstakem.mocap.parser.AcclaimFrame;
import main.research.fstakem.mocap.parser.AcclaimRoot;
import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.parser.AsfData;
import main.research.fstakem.mocap.parser.AsfParser;

public class MapperTest 
{
	// Constants
	public static final String[] asf_section_keywords = { AsfParser.VERSION_KEYWORD, AsfParser.NAME_KEYWORD, AsfParser.UNITS_KEYWORD,
													  	  AsfParser.DOCUMENTATION_KEYWORD, AsfParser.ROOT_KEYWORD, AsfParser.BONES_KEYWORD, 
													  	  AsfParser.HIERARCHY_KEYWORD };
	public static final String asf_version = "1.10";
	public static final String asf_name = "VICON";
	public static final String asf_unit_mass = "1.0";
	public static final String asf_unit_length = "0.45";
	public static final String asf_unit_angle = "deg";
	public static final String asf_documentation = "This is a test of parsing the asf file.";
	public static final String asf_root_name = "root";
	public static final AcclaimData.OperationOnAxis[] asf_root_order = { AcclaimData.OperationOnAxis.TX,
																	  	 AcclaimData.OperationOnAxis.TY, 
																	  	 AcclaimData.OperationOnAxis.TZ, 
																	  	 AcclaimData.OperationOnAxis.RX, 
																	  	 AcclaimData.OperationOnAxis.RY, 
																	  	 AcclaimData.OperationOnAxis.RZ };
	public static final AcclaimData.Axis[] asf_root_axis = { AcclaimData.Axis.X, 
														  	 AcclaimData.Axis.Y, 
														  	 AcclaimData.Axis.Z };
	public static final Vector3f asf_root_position = new Vector3f(0.0f, 0.0f, 0.0f);
	public static final Vector3f asf_root_orientation = new Vector3f(0.0f, 0.0f, 0.0f);
	public static final int asf_bone_starting_id = 1;
	public static final int[][] asf_bone_hierarchy = { {0, 1}, {0, 2} };
	public static final String[] asf_bone_names = { "lhipjoint", "rhipjoint", "lfemur" };
	public static final Vector3f asf_bone_direction = new Vector3f(0.5f, 1.25f, 2.25f);
	public static final float asf_bone_length = 1.5f;
	public static final Vector3f asf_bone_axis_values = new Vector3f(1.0f, 2.0f, 3.0f); 
	public static final AcclaimData.Axis[] asf_bone_axis_order = { AcclaimData.Axis.X,
															       AcclaimData.Axis.Y,
															       AcclaimData.Axis.Z };
	public static final AcclaimData.OperationOnAxis[] asf_bone_dof = { AcclaimData.OperationOnAxis.RX, 
		  															   AcclaimData.OperationOnAxis.RY, 
		  															   AcclaimData.OperationOnAxis.RZ };
	public static final float[] asf_bone_limits = { 45.0f, 180.0f };
	
	public static final int amc_number_of_frames = 2;
	public static final float[] amc_bone_orientation = { 0.25f, 0.5f, 0.75f };
	
	// Variables
	
    public static AsfData createAsfData()
    {
    	AsfData asf_data = new AsfData();
    	asf_data.version = MapperTest.asf_version;
    	asf_data.name = MapperTest.asf_name;
    	asf_data.units = MapperTest.createAsfUnits();
    	asf_data.documentation = MapperTest.createAsfDocumentation();
    	asf_data.root = MapperTest.createAsfRoot();
    	asf_data.bones = MapperTest.createAsfBones();
    	asf_data.hierarchy = MapperTest.createElementHierachy();
    	
    	return asf_data;
    }
    
    private static Map<String, String> createAsfUnits()
    {
    	Map<String, String> units = new HashMap<String, String>();
    	units.put("mass", MapperTest.asf_unit_mass);
    	units.put("length", MapperTest.asf_unit_length);
    	units.put("angle", MapperTest.asf_unit_angle);
    	
    	return units;
    }
    
    public static List<String> createAsfDocumentation()
    {
    	List<String> documentation = new ArrayList<String>();
    	documentation.add(MapperTest.asf_documentation);
    	
    	return documentation;
    }
    
    public static AcclaimRoot createAsfRoot()
    {
    	AcclaimRoot acclaim_root = new AcclaimRoot();
    	acclaim_root.amc_data_order = MapperTest.asf_root_order;
    	acclaim_root.orientation_order = MapperTest.asf_root_axis;
    	acclaim_root.position = MapperTest.asf_root_position;
    	acclaim_root.orientation = MapperTest.asf_root_orientation;
    	
    	return acclaim_root;
    }
    
    public static List<AcclaimBone> createAsfBones()
    {
    	List<AcclaimBone> bones = new ArrayList<AcclaimBone>();
    	for(int i = 0; i < MapperTest.asf_bone_names.length; i++)
    		bones.add(MapperTest.addDetailsToBone(MapperTest.asf_bone_starting_id+i, MapperTest.asf_bone_names[i]));
    	
    	return bones;
    }
	
    public static Map<String, List<String>> createElementHierachy()
    {
    	Map<String, List<String>> hierarchy = new HashMap<String, List<String>>();
    	String key = "";
    	ArrayList<String> values;
    	int start_index = 0;
    	
    	for(int i = 0; i < MapperTest.asf_bone_hierarchy.length; i++)
    	{
    		if(i == 0)
    		{
    			key = MapperTest.asf_root_name;
    			start_index = 0;
    		}
    		else
    		{
    			int index = MapperTest.asf_bone_hierarchy[i][0];
    			key = MapperTest.asf_bone_names[index];
    			start_index = 1;
    		}
    		
    		values = new ArrayList<String>();
    		for(int j = start_index; j < MapperTest.asf_bone_hierarchy[i].length; j++)
    		{
    			int index = MapperTest.asf_bone_hierarchy[i][j];
    			values.add(MapperTest.asf_bone_names[index]);
    		}
    		
    		hierarchy.put(key, values);
    	}
    	
    	return hierarchy;
    }
	      
    public static AmcData createAmcData()
    {
    	AmcData amc_data = new AmcData();
    	
    	for(int i = 0; i < MapperTest.amc_number_of_frames; i++)
    	{
    		AcclaimFrame frame = new AcclaimFrame();
    		frame.number = i + 1;
    		
    		for(int j = 0; j < MapperTest.asf_bone_names.length; j++)
    		{
    			ArrayList<Float> values = new ArrayList<Float>();
    			for(int k = 0; k < MapperTest.amc_bone_orientation.length; k++)
    				values.add(MapperTest.amc_bone_orientation[k] * (j+1) * frame.number);
    			frame.bone_orientation.put(MapperTest.asf_bone_names[j], values);
    		}
    		amc_data.frames.add(frame);
    	}
    	
    	return amc_data;
    }
       
    private static AcclaimBone addDetailsToBone(int id, String name)
    {
    	AcclaimBone bone = new AcclaimBone();
    	int multiplier = id;
       	bone.id = id;
    	bone.name = name;	
    	bone.direction = new Vector3f(MapperTest.asf_bone_direction.x * multiplier, 
    								  MapperTest.asf_bone_direction.y * multiplier,
    								  MapperTest.asf_bone_direction.z * multiplier);
    	bone.length = MapperTest.asf_bone_length * multiplier;
    	bone.orientation = new Vector3f(MapperTest.asf_bone_axis_values.x * multiplier,
    									MapperTest.asf_bone_axis_values.y * multiplier,
    									MapperTest.asf_bone_axis_values.z * multiplier);
    	
    	List<AcclaimData.OperationOnAxis> asf_bone_dof = new ArrayList<AcclaimData.OperationOnAxis>();
    	for(AcclaimData.OperationOnAxis dof : MapperTest.asf_bone_dof)
    		asf_bone_dof.add(dof);
    	bone.dof = asf_bone_dof;
    	
    	List< List<Float>> limits = new ArrayList<List<Float>>();
    	for(int i = 0; i < 3; i++)
    	{
    		ArrayList<Float> l = new ArrayList<Float>();
    		for(float v: MapperTest.asf_bone_limits)
    			l.add(v);
    		limits.add(l);
    	}
    	bone.limits = limits;
    	
    	return bone;
    }
}
