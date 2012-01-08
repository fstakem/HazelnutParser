package test.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AsfParser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsfParserTest
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AsfParserTest.class);
		
	// Constants
	private static final String[] section_keywords = { "version", "name", "units", "root", 
													   "bonedata", "hierarchy" };
	private static final String version = "1.10";
	private static final String name = "VICON";
	private static final String unit_mass = "1.0";
	private static final String unit_length = "0.45";
	private static final String unit_angle = "deg";
	private static final String root_order = "TX TY TZ RX RY RZ";
	private static final String root_axis = "XYZ";
	private static final String root_position = "0 0 0";
	private static final String root_orientation = "0 0 0";
	private static final String[] bone_names = { "lhipjoint", "rhipjoint", "lfemur" };
	private static final float[] bone_direction = { 0.5f, 1.25f, 2.25f };
	private static final float bone_length = 1.5f;
	private static final String bone_dof = "rx ry rz";
	private static final float[] bone_axis = { 10.0f, 20.0f, 30.0f };
	private static final float[] bone_limits = { 45.0f, 180.0f };
	
	// Variables
	private HashMap<String, ArrayList<String>> sections;
	
	@Rule
	public TestWatcher watchman = new TestWatcher()
	{
		@Override
		protected void starting(Description description)
		{
			logger.debug("########################################################################");
			logger.debug("#### Starting method: {}().", description.getMethodName());
		}
		
		@Override
		protected void finished(Description description)
		{
			logger.debug("#### Finishing method: {}().", description.getMethodName());
			logger.debug("########################################################################");
			logger.debug("");
		}
	};
	
	@BeforeClass
    public static void setUpClass()
    {
		    
    }
 
    @AfterClass
    public static void tearDownClass() 
    {
    	 
    }
 
    @Before
    public void setUp()
    {
    	ArrayList<String> raw_lines = AsfParserTest.createAsfData();
    	this.sections = AsfParser.seperateSections(raw_lines);
    }
 
    @After
    public void tearDown()
    {
    
    }
    
    @Test
    public void seperateSectionsTest()
    {
    	logger.debug("Test to make sure the asf is split on keywords properly.");
    	
    	for(String keyword : AsfParserTest.section_keywords)
    	{
    		logger.info("Testing section \'{}\' to make sure it was properly seperated.");
    		if(!this.sections.containsKey(keyword))
    			Assert.fail("Section: " + keyword + " not found.");
    	}
    }
    
    @Test
    public void parseVersionTest()
    {
    	logger.debug("Test to make sure the version is properly parsed.");
    	
    	String version;
    	try 
    	{
			version = AsfParser.parseVersion(this.sections);
			logger.info("Testing \'{}\' to make sure it was properly parsed.", version);
			if(!AsfParserTest.version.equals(version))
				Assert.fail("Parsed version does match initial version.");
		} 
    	catch (ParseException e) 
    	{
    		Assert.fail(e.getMessage());
		}
    }
    
    @Test 
    public void parseNameTest()
    {
    	logger.debug("Test to make sure the name is properly parsed.");
    	
    	String name;
    	try 
    	{
			name = AsfParser.parseName(this.sections);
			logger.info("Testing \'{}\' to make sure it was properly parsed.", name);
			if(!AsfParserTest.name.equals(name))
				Assert.fail("Parsed name does match initial name.");
		} 
    	catch (ParseException e) 
    	{
    		Assert.fail(e.getMessage());
		}
    }
    
    @Test
    public void parseUnitsTest()
    {
    	logger.debug("Test to make sure the units is properly parsed.");
    	
    	HashMap<String, String> units = null;
		try 
		{
			units = AsfParser.parseUnits(this.sections);
		} 
		catch (ParseException e) 
		{
			Assert.fail(e.getMessage());
		}
    	
    	for(Map.Entry<String, String> entry : units.entrySet())
		{
    		logger.info("Testing \'{}\' to make sure it was properly parsed.", entry.getValue());
			boolean mapContainsValue = false;
			if(entry.getValue().equals(AsfParserTest.unit_mass) ||
			   entry.getValue().equals(AsfParserTest.unit_length) ||
			   entry.getValue().equals(AsfParserTest.unit_angle))
				mapContainsValue = true;
			
			if(!mapContainsValue)
				Assert.fail("Parsed units does match initial units.");
		}
    }
    
    @Test
    public void parseRootTest()
    {
    	logger.debug("Test to make sure the root is properly parsed.");
    	
    	HashMap<String, ArrayList<String>> root = null;
		try 
		{
			root = AsfParser.parseRoot(this.sections);
		} 
		catch (ParseException e) 
		{
			Assert.fail(e.getMessage());
		}
    	
       	for(Map.Entry<String, ArrayList<String>> entry : root.entrySet())
		{
       		logger.info("Testing \'{}\' to make sure it was properly parsed.", entry.getValue());
			boolean mapContainsValue = false;
			String line = "";
			ArrayList<String> values = entry.getValue();
			
			for(int i = 0; i < values.size(); i++)
				line += values.get(i) + " ";
			
			line = line.trim();
			if(line.equals(AsfParserTest.root_order) ||
			   line.equals(AsfParserTest.root_axis) ||
			   line.equals(AsfParserTest.root_position) ||
			   line.equals(AsfParserTest.root_orientation))
			   	mapContainsValue = true;
			
			if(!mapContainsValue)
				Assert.fail("Parsed root does match initial root.");
		}
    }
    
    @Test
    public void parseBonesTest()
    {
    	logger.debug("Test to make sure the bones are properly parsed.");
    	
    	ArrayList<AcclaimBone> bones = AsfParser.parseBones(this.sections);
    	
       	for(int i = 0; i < bones.size(); i++)
    	{
    		AcclaimBone bone = bones.get(i);
    		logger.info("Testing \'{}\' to make sure it was properly parsed.", bone.name);
    		this.compareBonesName(bone.name, AsfParserTest.bone_names[i]);
    		this.compareBonesDirection(bone.direction, AsfParserTest.bone_direction);
    		this.compareBonesLength(bone.length, AsfParserTest.bone_length);
    		this.compareBonesDof(bone.dof, AsfParserTest.bone_dof);
    		this.compareBonesAxis(bone.axis, AsfParserTest.bone_axis);
    		this.compareBonesLimits(bone.limits, AsfParserTest.bone_limits);
    	}
    }
    
    @Test
    public void parseHierarchyTest()
    {
    	logger.debug("Test to make sure the hierarchy is properly parsed.");
    	
    	HashMap<String, ArrayList<String>> hierarchy = null;
		try 
		{
			hierarchy = AsfParser.parseHierarchy(this.sections);
		} 
		catch (ParseException e) 
		{
			Assert.fail(e.getMessage());
		}
    	
    	for(Map.Entry<String, ArrayList<String>> entry : hierarchy.entrySet())
		{
    		logger.info("Testing \'{}\' to make sure it was properly parsed.", entry.getValue());
    		this.compareBoneHierarchy(entry.getKey());
    		ArrayList<String> values = entry.getValue();
    		for(String value : values)
    			this.compareBoneHierarchy(value);
		}
    }
        
    private void compareBonesName(String name, String other_name)
    {
    	if(!name.equals(other_name))
    		Assert.fail("Parsed bone name does match initial bone name.");
    }
	
    private void compareBonesDirection(ArrayList<Float> direction, float[] other_direction)
    {
    	for(int i = 0; i < direction.size(); i++)
    	{
    		if(direction.get(i).floatValue() != other_direction[i])
    			Assert.fail("Parsed bone direction does match initial bone direction.");
    	}
    }
	
    private void compareBonesLength(float length, float other_length)
    {
    	if(length != other_length)
			Assert.fail("Parsed bone length does match initial bone length.");
    }
	
    private void compareBonesDof(ArrayList<String> dof, String other_dof)
    {
    	String d = "";
    	for(int i = 0; i < dof.size(); i++)
    	{
    		d += dof.get(i);
    		
    		if(i < dof.size() -1)
    			d += " ";
    	}
    	
    	if(!d.equals(other_dof))
    		Assert.fail("Parsed bone dof does match initial bone dof.");
    		
    }
	
    private void compareBonesAxis(ArrayList<Float> axis, float[] other_axis)
    {
    	for(int i = 0; i < axis.size(); i++)
		{
			if(axis.get(i) != other_axis[i])
				Assert.fail("Parsed bone axis does match initial bone axis.");
		}
    }
	
    private void compareBonesLimits(ArrayList< ArrayList<Float>> limits, float[] other_limits)
    {
    	for(ArrayList<Float> limits_set : limits)
    	{
    		for(int i = 0; i < limits_set.size(); i++)
    		{
    			if(limits_set.get(i) != other_limits[i])
    				Assert.fail("Parsed bone limits does match initial bone limits.");
    		}
    	}
    }
    
    private void compareBoneHierarchy(String name)
    {
    	for(String b : AsfParserTest.bone_names)
    	{
    		if(b.equals(name))
    			return;
    	}
    	
    	if(name.equals("root"))
    		return;
    	
    	Assert.fail("Parsed bone hierarchy does match initial bone hierachy.");
    }
          
    public static ArrayList<String> createAsfData()
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();
    	asf_lines.add(":version " + AsfParserTest.version);
    	asf_lines.add(":name " + AsfParserTest.name);
    	asf_lines.add(":units");
    	asf_lines.add("  mass " + AsfParserTest.unit_mass);
    	asf_lines.add("  length " + AsfParserTest.unit_length);
    	asf_lines.add("  angle " + AsfParserTest.unit_angle);
    	asf_lines.add(":documentation");
    	asf_lines.add("   .ast/.asf automatically generated from VICON data using");
    	asf_lines.add("   VICON BodyBuilder and BodyLanguage model FoxedUp or BRILLIANT.MOD");
    	asf_lines.addAll(AsfParserTest.createRoot());
    	asf_lines.addAll(AsfParserTest.createBones());
    	asf_lines.addAll(AsfParserTest.createHierarchy());
 
    	return asf_lines;
    }
    
    private static ArrayList<String> createRoot()
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();
    	asf_lines.add(":root");
    	asf_lines.add("   order " + AsfParserTest.root_order);
    	asf_lines.add("   axis " + AsfParserTest.root_axis);
    	asf_lines.add("   position " + AsfParserTest.root_position);
    	asf_lines.add("   orientation " + AsfParserTest.root_orientation);
    	
    	return asf_lines;
    }
    
    private static ArrayList<String> createBones()
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();
    	asf_lines.add(":bonedata");
    	int id = 1;
    	
    	for(String bone : AsfParserTest.bone_names)
    	{
    		asf_lines.addAll(AsfParserTest.createBone(bone, id));
    		id += 1;
    	}
    	
    	return asf_lines;
    }
    
    private static ArrayList<String> createBone(String bone_name, int bone_id)
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();	
    	String limits = AsfParserTest.boneLimitsToString();
    
    	asf_lines.add("  begin");
		asf_lines.add("     id " + String.valueOf(bone_id));
		asf_lines.add("     name " + bone_name);
		asf_lines.add("     direction " + AsfParserTest.boneDirectionToString());
		asf_lines.add("     length " + String.valueOf(AsfParserTest.bone_length));
    	asf_lines.add("     axis " + AsfParserTest.boneAxisToString());
    	asf_lines.add("   dof " + AsfParserTest.bone_dof);
    	asf_lines.add("   limits " + limits);
    	asf_lines.add("          " + limits);
    	asf_lines.add("          " + limits);
    	asf_lines.add("  end");
    	
    	return asf_lines;
    }
    
    private static ArrayList<String> createHierarchy()
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();	
    	asf_lines.add(":hierarchy");
    	asf_lines.add("  begin");
    	asf_lines.add("   root " + AsfParserTest.bone_names[0] + " " + AsfParserTest.bone_names[1]);
    	asf_lines.add("   " + AsfParserTest.bone_names[0] + " " + AsfParserTest.bone_names[2]);
    	asf_lines.add("  end");
    	
    	return asf_lines;
    }
    
    private static String boneDirectionToString()
    {
    	String direction = "";
    	for(int i = 0; i < AsfParserTest.bone_direction.length; i++)
    	{
    		direction += String.valueOf(AsfParserTest.bone_direction[i]);
    		if(i < AsfParserTest.bone_direction.length - 1)
    			direction += " ";
    	}
    	
    	return direction;
    }
    
    private static String boneAxisToString()
    {
    	String axis = "";
    	for(int i = 0; i < AsfParserTest.bone_axis.length; i++)
    	{
    		axis += String.valueOf(AsfParserTest.bone_axis[i]);
    		if(i < AsfParserTest.bone_axis.length - 1)
    			axis += " ";
    	}
    	
    	axis += "  XYZ";
    	return axis;
    }
    
    private static String boneLimitsToString()
    {
    	String limits = "(";
    	for(int i = 0; i < AsfParserTest.bone_limits.length; i++)
    	{
    		limits += String.valueOf(AsfParserTest.bone_limits[i]);
    		if(i < AsfParserTest.bone_limits.length - 1)
    			limits += " ";
    	}
    	
    	limits += ")";
    	return limits;
    }
}
