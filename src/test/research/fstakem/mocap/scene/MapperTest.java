package test.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.HashMap;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AcclaimFrame;
import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.parser.AsfData;


public class MapperTest 
{
	// Constants
	public static final String asf_version = "1.10";
	public static final String asf_name = "VICON";
	public static final String asf_unit_mass = "1.0";
	public static final String asf_unit_length = "0.45";
	public static final String asf_unit_angle = "deg";
	public static final String[] asf_root_order = { "TX", "TY", "TZ", "RX", "RY", "RZ" };
	public static final String asf_root_axis = "XYZ";
	public static final String[] asf_root_position = { "1.0f", "2.0f", "3.0f" };
	public static final String[] asf_root_orientation = { "4.0f", "5.0f", "6.0f" };
	public static final String asf_root_name = "root";
	public static final String[] asf_bone_names = { "lhipjoint", "rhipjoint", "lfemur" };
	public static final int[][] asf_bone_hierarchy = { {0, 1}, {0, 2} };
	public static final int asf_bone_starting_id = 1;
	public static final float[] asf_bone_direction = { 0.5f, 1.25f, 2.25f };
	public static final float asf_bone_length = 1.5f;
	public static final String[] asf_bone_dof = { "RX", "RY", "RZ" };
	public static final float[] asf_bone_axis = { 10.0f, 20.0f, 30.0f };
	public static final float[] asf_bone_limits = { 45.0f, 180.0f };
	
	public static final int amc_number_of_frames = 2;
	public static final float[] amc_bone_positions = { 0.25f, 0.5f, 0.75f };
	
	// Variables
	
    public static AsfData createAsfData()
    {
    	AsfData asf_data = new AsfData();
    	asf_data.version = MapperTest.asf_version;
    	asf_data.name = MapperTest.asf_name;
    	asf_data.units = MapperTest.createAsfUnits();
    	asf_data.root = MapperTest.createAsfRoot();
    	asf_data.bones = MapperTest.createAsfBones();
    	asf_data.hierarchy = MapperTest.createElementHierachy();
    	
    	return asf_data;
    }
	
    public static HashMap<String, ArrayList<String>> createElementHierachy()
    {
    	HashMap<String, ArrayList<String>> hierarchy = new HashMap<String, ArrayList<String>>();
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
	   
    public static HashMap<String, ArrayList<String>> createAsfRoot()
    {
    	HashMap<String, ArrayList<String>> root = new HashMap<String, ArrayList<String>>();
    	
    	String key = "order";
    	ArrayList<String> values = new ArrayList<String>();
    	for(String v : MapperTest.asf_root_order)
    		values.add(v);
    	root.put(key, values);
    	
    	key = "axis";
    	values = new ArrayList<String>();
    	values.add(MapperTest.asf_root_axis);
    	root.put(key, values);
    	
       	key = "position";
    	values = new ArrayList<String>();
    	for(String v : MapperTest.asf_root_position)
    		values.add(v);
    	root.put(key, values);
    	
       	key = "orientation";
    	values = new ArrayList<String>();
    	for(String v: MapperTest.asf_root_orientation)
    		values.add(v);
    	root.put(key, values);
    	
    	return root;
    }
    
    public static ArrayList<AcclaimBone> createAsfBones()
    {
    	ArrayList<AcclaimBone> bones = new ArrayList<AcclaimBone>();
    	for(int i = 0; i < MapperTest.asf_bone_names.length; i++)
    		bones.add(MapperTest.addDetailsToBone(MapperTest.asf_bone_starting_id+i, MapperTest.asf_bone_names[i]));
    	
    	return bones;
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
    			for(int k = 0; k < MapperTest.amc_bone_positions.length; k++)
    				values.add(MapperTest.amc_bone_positions[k] * j+1 * frame.number);
    			frame.bone_positions.put(MapperTest.asf_bone_names[j], values);
    		}
    		amc_data.frames.add(frame);
    	}
    	
    	return amc_data;
    }
    
    private static HashMap<String, String> createAsfUnits()
    {
    	HashMap<String, String> units = new HashMap<String, String>();
    	units.put("mass", MapperTest.asf_unit_mass);
    	units.put("length", MapperTest.asf_unit_length);
    	units.put("angle", MapperTest.asf_unit_angle);
    	
    	return units;
    }
    
    private static AcclaimBone addDetailsToBone(int id, String name)
    {
    	AcclaimBone bone = new AcclaimBone();
    	int multiplier = id;
       	bone.id = id;
    	bone.name = name;
    	
    	ArrayList<Float> direction = new ArrayList<Float>();
    	for(float v : MapperTest.asf_bone_direction)
    		direction.add(v * multiplier);
    	bone.direction = direction;
    	bone.length = MapperTest.asf_bone_length * multiplier;
    	
    	ArrayList<Float> axis = new ArrayList<Float>();
    	for(float v : MapperTest.asf_bone_axis)
    		axis.add(v);
    	bone.axis = axis;
    	
    	ArrayList<String> dof = new ArrayList<String>();
    	for(String v : MapperTest.asf_bone_dof)
    		dof.add(v);
    	bone.dof = dof;
    	
    	ArrayList< ArrayList<Float>> limits = new ArrayList< ArrayList<Float>>();
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
