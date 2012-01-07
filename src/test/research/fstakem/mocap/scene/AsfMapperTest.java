package test.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.HashMap;

import javax.vecmath.Vector3f;

import junit.framework.Assert;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.scene.AsfMapper;
import main.research.fstakem.mocap.scene.Bone;
import main.research.fstakem.mocap.scene.CharacterElement;
import main.research.fstakem.mocap.scene.RootElement;
import main.research.fstakem.mocap.scene.CharacterElement.Axis;
import main.research.fstakem.mocap.scene.CharacterElement.Dof;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsfMapperTest extends MapperTest
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AsfMapperTest.class);
	
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
    	
    }
 
    @After
    public void tearDown()
    {
    	  
    }
    
    @Test
    public void createCharacterElementHierarchyTest()
    {
    	logger.debug("Test to make sure the asf hierarchy is created properly.");
    	
    	HashMap<String, ArrayList<String>> hierarchy = MapperTest.createElementHierachy();
    	CharacterElement root = null;
		try 
		{
			root = AsfMapper.createCharacterElementHierarchy(hierarchy);
		} 
		catch (Exception e) 
		{
			Assert.fail(e.getMessage());
		}
		
		ArrayList<CharacterElement> all_elements = new ArrayList<CharacterElement>();
		all_elements.add(root);
		all_elements.addAll(root.getAllSubElements());
    	for(CharacterElement element : all_elements)
    	{
    		ArrayList<String> children_names = hierarchy.get(element.getName());
    		if(children_names != null)
    		{
    			ArrayList<CharacterElement> children = element.getChildren();
    			for(CharacterElement child_element : children)
    			{
    				logger.info("Testing that \'{}\' has child \'{}\'.", element.getName(), child_element.getName());
    				Assert.assertTrue("Character element hierachy created does not match initial hierachy.", children_names.contains(child_element.getName()));
    			}
    		}
    	}
    }
    
    @Test
    public void addDetailsToRootTest()
    {
    	logger.debug("Test to make sure the asf root is created properly.");
    	
    	HashMap<String, ArrayList<String>> hierarchy = MapperTest.createElementHierachy();
    	RootElement root = null;
    	HashMap<String, ArrayList<String>> asf_root = null;
		try 
		{
			root = AsfMapper.createCharacterElementHierarchy(hierarchy);
			asf_root = MapperTest.createAsfRoot();
			AsfMapper.addDetailsToRoot(root, asf_root);
		} 
		catch (Exception e) 
		{
			Assert.fail(e.getMessage());
		}
    	
    	// Test name
    	Assert.assertEquals("Root element name created does not match initial name.", 
    						MapperTest.asf_root_name, root.getName());
    	
    	// Test dof
    	ArrayList<Dof> order = root.getOrder();
    	for(int i = 0; i < order.size(); i++)
    		Assert.assertEquals("Root element dof created does not match initial dof.", 
    							CharacterElement.getDofValueFromString(MapperTest.asf_root_order[i]), 
    							order.get(i));
    	
    	// Test axis
    	ArrayList<Axis> axis = root.getAxis();
    	for(int i = 0; i < axis.size(); i++)
    		Assert.assertEquals("Root element axis dof created does not match initial axis.", 
    							CharacterElement.getAxisValueFromString(MapperTest.asf_root_axis.substring(i, i+1)), 
    							axis.get(i));

    	// Test position
    	Vector3f position = root.getPosition();
    	Assert.assertEquals("Root element position created does not match initial position.", 
    						Float.valueOf(MapperTest.asf_root_position[0]), 
    						position.x);
    	Assert.assertEquals("Root element position created does not match initial position.", 
    						Float.valueOf(MapperTest.asf_root_position[1]), 
    						position.y);
    	Assert.assertEquals("Root element position created does not match initial position.", 
    						Float.valueOf(MapperTest.asf_root_position[2]), 
    						position.z);
    	
    	// Test orientation
    	Vector3f orientation = root.getOrientation();
    	Assert.assertEquals("Root element orientation created does not match initial orientation.", 
    						Float.valueOf(MapperTest.asf_root_orientation[0]), 
    						orientation.x);
    	Assert.assertEquals("Root element orientation created does not match initial orientation.", 
    						Float.valueOf(MapperTest.asf_root_orientation[1]), 
    						orientation.y);
    	Assert.assertEquals("Root element orientation created does not match initial orientation.", 
    						Float.valueOf(MapperTest.asf_root_orientation[2]), 
    						orientation.z);
    }
    
    @Test
    public void addDetailsToBonesTest()
    {
    	logger.debug("Test to make sure the asf bones are created properly.");
    	
    	HashMap<String, ArrayList<String>> hierarchy = MapperTest.createElementHierachy();
    	RootElement root = null;
    	HashMap<String, ArrayList<String>> asf_root = null;
    	ArrayList<AcclaimBone> asf_bones = null;
		try 
		{
			root = AsfMapper.createCharacterElementHierarchy(hierarchy);
			asf_root = MapperTest.createAsfRoot();
			AsfMapper.addDetailsToRoot(root, asf_root);
			asf_bones = MapperTest.createAsfBones();
			AsfMapper.addDetailsToBones(root, asf_bones);
		} 
		catch (Exception e) 
		{
			Assert.fail(e.getMessage());
		}
		
    	ArrayList<CharacterElement> bones = root.getAllSubElements();
    	for(AcclaimBone acclaim_bone : asf_bones)
    	{
    		int bone_index = -1;
    		for(int i = 0; i < bones.size(); i++)
    		{
    			if(acclaim_bone.name.equals(bones.get(i).getName()))
    			{
    				bone_index = i;
    				break;
    			}
    		}
    		
    		if(bone_index != -1)
    			this.compareBoneValues(acclaim_bone, (Bone) bones.get(bone_index));
    		else
    			Assert.fail("Could not find a bone created with the same name as the initial bone.");
    	}
    		
    }
    
    private void compareBoneValues(AcclaimBone asf_bone, Bone bone)
    {
    	// Test id
    	Assert.assertEquals("Bone id created does not match initial id.", asf_bone.id, bone.getId());
    	
    	// Test orientation
    	Assert.assertEquals("Bone direction created does not match initial direction.", 
    						asf_bone.direction.get(0), bone.getOrientation().x);
    	Assert.assertEquals("Bone direction created does not match initial direction.", 
    						asf_bone.direction.get(1), bone.getOrientation().y);
    	Assert.assertEquals("Bone direction created does not match initial direction.", 
    						asf_bone.direction.get(2), bone.getOrientation().z);
    	
    	// Test length
    	Assert.assertEquals("Bone length created does not match initial length.", 
    						asf_bone.length, bone.getLength());
    	
    	// Test axis
    	for(int i = 0; i < asf_bone.axis.size(); i++)
    		Assert.assertEquals("Bone axis created does not match initial axis.", 
    							asf_bone.axis.get(i), bone.getAxis().get(i));
    	
    	// Test dof
    	for(int i = 0; i < asf_bone.dof.size(); i++)
    		Assert.assertEquals("Bone dof created does not match initial dof.", 
    							CharacterElement.getDofValueFromString(asf_bone.dof.get(i)), bone.getDof().get(i));
    	
    	// Test limits
    	for(int i = 0; i < asf_bone.limits.size(); i++)
    	{
    		for(int j = 0; j < asf_bone.limits.get(i).size(); j++)
    			Assert.assertEquals("Bone limits created does not match initial limits.", asf_bone.limits.get(i).get(j), 
    								bone.getLimits().get(i).get(j));
    		
    	}	
    }
}
