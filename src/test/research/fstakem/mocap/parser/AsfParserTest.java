package test.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3f;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AcclaimData;
import main.research.fstakem.mocap.parser.AcclaimRoot;
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
	
	// Test variables
	private static final boolean showData = false;
		
	// Constants
	private static final String[] section_keywords = {AsfParser.VERSION_KEYWORD, AsfParser.NAME_KEYWORD, AsfParser.UNITS_KEYWORD,
													  AsfParser.DOCUMENTATION_KEYWORD, AsfParser.ROOT_KEYWORD, AsfParser.BONES_KEYWORD, 
													  AsfParser.HIERARCHY_KEYWORD};
	private static final String version = "1.10";
	private static final String name = "VICON";
	private static final String unit_mass = "1.0";
	private static final String unit_length = "0.45";
	private static final String unit_angle = "deg";
	private static final String documentation = "This is a test of parsing the asf file.";
	private static final AcclaimData.OperationOnAxis[] root_order = { AcclaimData.OperationOnAxis.TX,
																	  AcclaimData.OperationOnAxis.TY, 
																	  AcclaimData.OperationOnAxis.TZ, 
																	  AcclaimData.OperationOnAxis.RX, 
																	  AcclaimData.OperationOnAxis.RY, 
																	  AcclaimData.OperationOnAxis.RZ };
	private static final AcclaimData.Axis[] root_axis = { AcclaimData.Axis.X, 
														  AcclaimData.Axis.Y, 
														  AcclaimData.Axis.Z};
	private static final float[] root_position = { 0.0f, 0.0f, 0.0f };
	private static final float[] root_orientation = { 0.0f, 0.0f, 0.0f };
	private static final String[] bone_names = { "lhipjoint", "rhipjoint", "lfemur" };
	private static final float[] bone_direction = { 0.5f, 1.25f, 2.25f };
	private static final float bone_length = 1.5f;
	private static final float[] bone_axis_values = { 10.0f, 20.0f, 30.0f };
	private static final AcclaimData.Axis[] bone_axis_order = { AcclaimData.Axis.X,
															    AcclaimData.Axis.Y,
															    AcclaimData.Axis.Z };
	private static final AcclaimData.OperationOnAxis[] bone_dof = { AcclaimData.OperationOnAxis.RX, 
		  															AcclaimData.OperationOnAxis.RY, 
		  															AcclaimData.OperationOnAxis.RZ };
	private static final float[] bone_limits = { 45.0f, 180.0f };
	
	// Variables
	private HashMap<String, List<String>> sections;
	
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
    	List<String> raw_lines = AsfParserTest.createAsfData();
    	
    	if(AsfParserTest.showData)
    	{
    		logger.debug("");
    		logger.debug("+++++++++++++++++++++++++++++++");
	    	logger.debug("+++ Data used for the tests +++");
	    	logger.debug("+++++++++++++++++++++++++++++++");
	    	for(String line : raw_lines)
	    		logger.debug(line);
	    	
	    	logger.debug("");
    	}
    	
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
    		logger.info("Testing section \'{}\' to make sure it was properly seperated.", keyword);
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
    		e.printStackTrace();
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
    		e.printStackTrace();
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
			e.printStackTrace();
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
    public void parseDocumentationTest()
    {
    	logger.debug("Test to make sure the documentation is properly parsed.");
    	
    	List<String> documentation = null;
		try 
		{
			documentation = AsfParser.parseDocumentation(this.sections);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		if(!documentation.get(0).equals(AsfParserTest.documentation))
			Assert.fail("Parsed documentation does match initial documentation.");
    }
    
    @Test
    public void parseRootTest()
    {
    	logger.debug("Test to make sure the root is properly parsed.");
    	
    	AcclaimRoot root = null;
		try 
		{
			root = AsfParser.parseRoot(this.sections);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
    	
		for(int i = 0; i < root.amc_data_order.length; i++)
		{
			if(!root.amc_data_order[i].equals(AsfParserTest.root_order[i]))
				Assert.fail("Parsed root order does match initial root order.");
		}
		
		for(int i = 0; i < root.orientation_order.length; i++)
		{
			if(!root.orientation_order[i].equals(AsfParserTest.root_axis[i]))
				Assert.fail("Parsed root axis does match initial root axis.");
		}
		
		if(root.position.x != AsfParserTest.root_position[0] ||
		   root.position.y != AsfParserTest.root_position[1] ||
		   root.position.z != AsfParserTest.root_position[2])
			Assert.fail("Parsed root position does match initial root position.");
		
		if(root.orientation.x != AsfParserTest.root_orientation[0] ||
		   root.orientation.y != AsfParserTest.root_orientation[1] ||
		   root.orientation.z != AsfParserTest.root_orientation[2])
					Assert.fail("Parsed root orientation does match initial root orientation.");
    }
    
    @Test
    public void parseBonesTest()
    {
    	logger.debug("Test to make sure the bones are properly parsed.");
    	
    	List<AcclaimBone> bones = AsfParser.parseBones(this.sections);
    	
       	for(int i = 0; i < bones.size(); i++)
    	{
    		AcclaimBone bone = bones.get(i);
    		logger.info("Testing \'{}\' to make sure it was properly parsed.", bone.name);
    		this.compareBonesName(bone.name, AsfParserTest.bone_names[i]);
    		this.compareBonesDirection(bone.direction, AsfParserTest.bone_direction);
    		this.compareBonesLength(bone.length, AsfParserTest.bone_length);
    		this.compareBonesAxisValues(bone.orientation, AsfParserTest.bone_axis_values);
    		this.compareBoneAxisOrder(bone.orientation_order, AsfParserTest.bone_axis_order);
    		this.compareBonesDof(bone.dof, AsfParserTest.bone_dof);
    		this.compareBonesLimits(bone.limits, AsfParserTest.bone_limits);
    	}
    }
    
    @Test
    public void parseHierarchyTest()
    {
    	logger.debug("Test to make sure the hierarchy is properly parsed.");
    	
    	HashMap<String, List<String>> hierarchy = null;
		try 
		{
			hierarchy = AsfParser.parseHierarchy(this.sections);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
    	
    	for(Map.Entry<String, List<String>> entry : hierarchy.entrySet())
		{
    		logger.info("Testing \'{}\' to make sure it was properly parsed.", entry.getValue());
    		this.compareBoneHierarchy(entry.getKey());
    		List<String> values = entry.getValue();
    		for(String value : values)
    			this.compareBoneHierarchy(value);
		}
    }
        
    private void compareBonesName(String name, String other_name)
    {
    	if(!name.equals(other_name))
    		Assert.fail("Parsed bone name does match initial bone name.");
    }
	
    private void compareBonesDirection(Vector3f direction, float[] other_direction)
    {
    	if(direction.x != other_direction[0] ||
    	   direction.y != other_direction[1] ||
    	   direction.z != other_direction[2])
			Assert.fail("Parsed bone direction does match initial bone direction.");
    }
	
    private void compareBonesLength(float length, float other_length)
    {
    	if(length != other_length)
			Assert.fail("Parsed bone length does match initial bone length.");
    }
    
    private void compareBonesAxisValues(Vector3f orientation, float[] other_orientation)
    {
    	if(orientation.x != other_orientation[0] ||
    	   orientation.y != other_orientation[1] ||
    	   orientation.z != other_orientation[2])
    		Assert.fail("Parsed bone axis values does match initial bone axis values.");
    }
    
    private void compareBoneAxisOrder(AcclaimData.Axis[] orientation_order, AcclaimData.Axis[] other_orientation_order)
    {
    	for(int i = 0; i < orientation_order.length; i++)
    	{
    		if(!orientation_order[i].equals(other_orientation_order[i]))
    			Assert.fail("Parsed bone axis order does match initial bone axis order.");
    	}
    }
	
    private void compareBonesDof(List<AcclaimData.OperationOnAxis> dof, AcclaimData.OperationOnAxis[] other_dof)
    {
    	for(int i = 0; i < dof.size(); i++)
    	{
    		if(!dof.get(i).equals(other_dof[i]))
    			Assert.fail("Parsed bone dof does match initial bone dof.");
    	}	
    }
	
    private void compareBonesLimits(List< List<Float>> limits, float[] other_limits)
    {
    	for(List<Float> limits_set : limits)
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
          
    public static List<String> createAsfData()
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();
    	asf_lines.add(":version " + AsfParserTest.version);
    	asf_lines.add(":name " + AsfParserTest.name);
    	asf_lines.add(":units");
    	asf_lines.add("  mass " + AsfParserTest.unit_mass);
    	asf_lines.add("  length " + AsfParserTest.unit_length);
    	asf_lines.add("  angle " + AsfParserTest.unit_angle);
    	asf_lines.add(":documentation");
    	asf_lines.add("   " + AsfParserTest.documentation);
    	asf_lines.addAll(AsfParserTest.createRoot());
    	asf_lines.addAll(AsfParserTest.createBones());
    	asf_lines.addAll(AsfParserTest.createHierarchy());
 
    	return asf_lines;
    }
    
    private static List<String> createRoot()
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();
    	String line = "";
    	asf_lines.add(":root");
    	
    	line = "   order ";
    	for(int i = 0; i < AsfParserTest.root_order.length; i++)
    	{
    		line += AcclaimData.operationOnAxisToString(AsfParserTest.root_order[i]).toUpperCase();
    		if(i < AsfParserTest.root_order.length - 1)
    			line += " ";
    	}
    	asf_lines.add(line);
    	
    	line = "   axis ";
    	for(int i = 0; i < AsfParserTest.root_axis.length; i++)
    	{
    		line += AcclaimData.axisToString(AsfParserTest.root_axis[i]).toUpperCase();
    	}
    	asf_lines.add(line);
    	
    	line = "   position ";
    	for(int i = 0; i < AsfParserTest.root_position.length; i++)
    	{
    		line += String.valueOf(AsfParserTest.root_position[i]);
    		if(i < AsfParserTest.root_position.length - 1)
    			line += " ";
    	}
    	asf_lines.add(line);
    	
    	line = "   orientation ";
    	for(int i = 0; i < AsfParserTest.root_orientation.length; i++)
    	{
    		line += String.valueOf(AsfParserTest.root_orientation[i]);
    		if(i < AsfParserTest.root_orientation.length - 1)
    			line += " ";
    	}
    	asf_lines.add(line);
    	
    	return asf_lines;
    }
    
    private static List<String> createBones()
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
    
    private static List<String> createBone(String bone_name, int bone_id)
    {
    	ArrayList<String> asf_lines = new ArrayList<String>();	
    	String limits = AsfParserTest.boneLimitsToString();
    
    	asf_lines.add("  begin");
		asf_lines.add("     id " + String.valueOf(bone_id));
		asf_lines.add("     name " + bone_name);
		asf_lines.add("     direction " + AsfParserTest.boneDirectionToString());
		asf_lines.add("     length " + String.valueOf(AsfParserTest.bone_length));
    	asf_lines.add("     axis " + AsfParserTest.boneAxisToString());
    	asf_lines.add("   dof " + AsfParserTest.boneDofToString());
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
    	for(int i = 0; i < AsfParserTest.bone_axis_values.length; i++)
    		axis += String.valueOf(AsfParserTest.bone_axis_values[i]) + " ";
    	
    	for(int i = 0; i < AsfParserTest.bone_axis_order.length; i++)
    		axis += AcclaimData.axisToString(AsfParserTest.bone_axis_order[i]).toUpperCase();
    	
    	return axis;
    }
    
    private static String boneDofToString()
    {
    	String dof = "";
    	for(int i = 0; i < AsfParserTest.bone_dof.length; i++)
    	{
    		dof += AcclaimData.operationOnAxisToString(AsfParserTest.bone_dof[i]).toLowerCase();
    		if(i < AsfParserTest.bone_dof.length - 1)
    			dof += " ";
    	}
    	
    	return dof;
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
