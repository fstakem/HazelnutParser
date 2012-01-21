package test.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3f;

import junit.framework.Assert;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AcclaimData;
import main.research.fstakem.mocap.parser.AcclaimRoot;
import main.research.fstakem.mocap.scene.AsfMapper;
import main.research.fstakem.mocap.scene.Bone;
import main.research.fstakem.mocap.scene.CharacterElement;
import main.research.fstakem.mocap.scene.RootElement;

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
    	
    	Map<String, List<String>> hierarchy = MapperTest.createElementHierachy();
    	CharacterElement root = null;
		try 
		{
			root = AsfMapper.createCharacterElementHierarchy(hierarchy);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		List<CharacterElement> all_elements = new ArrayList<CharacterElement>();
		all_elements.add(root);
		all_elements.addAll(root.getAllSubElements());
    	for(CharacterElement element : all_elements)
    	{
    		List<String> children_names = hierarchy.get(element.getName());
    		if(children_names != null)
    		{
    			List<CharacterElement> children = element.getChildren();
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
    	
    	Map<String, List<String>> hierarchy = MapperTest.createElementHierachy();
    	RootElement root = null;
    	AcclaimRoot asf_root = null;
		try 
		{
			root = AsfMapper.createCharacterElementHierarchy(hierarchy);
			asf_root = MapperTest.createAsfRoot();
			AsfMapper.addDetailsToRoot(root, asf_root);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
    	
    	// Test name
		logger.info("Testing the root element.");
    	Assert.assertEquals("Root element name created does not match initial name.", 
    						MapperTest.asf_root_name, root.getName());
    	
    	// Test amc data order
    	logger.info("Testing the root ordering of the amc data.");
    	AcclaimData.OperationOnAxis[] amc_data_order = root.getAmcDataOrder();
    	for(int i = 0; i < amc_data_order.length; i++)
    		Assert.assertEquals("Root element order of amc data created does not match order of amc data.", 
    							MapperTest.asf_root_order[i], 
    							amc_data_order[i]);
    	
    	// Test orientation order
    	logger.info("Testing the root orientation order.");
    	AcclaimData.Axis[] orientation_order = root.getOrientationOrder();
    	for(int i = 0; i < orientation_order.length; i++)
    		Assert.assertEquals("Root element orientation order created does not match initial orientation order.", 
    							MapperTest.asf_root_axis[i], 
    							orientation_order[i]);

    	// Test position
    	logger.info("Testing the root element position.");
    	Vector3f position = root.getStartPosition();
    	Assert.assertEquals("Root element position created does not match initial position.", 
    						MapperTest.asf_root_position.x, 
    						position.x);
    	Assert.assertEquals("Root element position created does not match initial position.", 
    						MapperTest.asf_root_position.y, 
    						position.y);
    	Assert.assertEquals("Root element position created does not match initial position.", 
    						MapperTest.asf_root_position.z, 
    						position.z);
    	
    	// Test orientation
    	logger.info("Testing the root element orientation.");
    	Vector3f orientation = root.getOrientation();
    	Assert.assertEquals("Root element orientation created does not match initial orientation.", 
    						MapperTest.asf_root_orientation.x, 
    						orientation.x);
    	Assert.assertEquals("Root element orientation created does not match initial orientation.", 
    						MapperTest.asf_root_orientation.y, 
    						orientation.y);
    	Assert.assertEquals("Root element orientation created does not match initial orientation.", 
    						MapperTest.asf_root_orientation.z, 
    						orientation.z);
    }
    
    @Test
    public void addDetailsToBonesTest()
    {
    	logger.debug("Test to make sure the asf bones are created properly.");
    	
    	Map<String, List<String>> hierarchy = MapperTest.createElementHierachy();
    	RootElement root = null;
    	AcclaimRoot asf_root = null;
    	List<AcclaimBone> asf_bones = null;
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
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
    	List<CharacterElement> bones = root.getAllSubElements();
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
    		
    		logger.info("Testing the bone \'{}\'.", bones.get(bone_index).getName());
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
    	
    	// Test direction
    	Assert.assertEquals("Bone direction created does not match initial direction.", 
    						asf_bone.direction.x, bone.getDirection().x);
    	Assert.assertEquals("Bone direction created does not match initial direction.", 
    						asf_bone.direction.y, bone.getDirection().y);
    	Assert.assertEquals("Bone direction created does not match initial direction.", 
    						asf_bone.direction.z, bone.getDirection().z);
    	
    	// Test length
    	Assert.assertEquals("Bone length created does not match initial length.", 
    						asf_bone.length, bone.getLength());
    	
    	// Test orientation 
    	Assert.assertEquals("Bone orientation created does not match initial orientation.", 
							asf_bone.orientation.x, bone.getOrientation().x);
    	Assert.assertEquals("Bone orientation created does not match initial orientation.", 
							asf_bone.orientation.y, bone.getOrientation().y);
    	Assert.assertEquals("Bone orientation created does not match initial orientation.", 
							asf_bone.orientation.z, bone.getOrientation().z);
    	
    	// Test orientation order
    	for(int i = 0; i < asf_bone.orientation_order.length; i++)
    		Assert.assertEquals("Bone orientation order created does not match initial orientation order.", 
								asf_bone.orientation_order[i], bone.getOrientationOrder()[i]);
		
    	// Test dof
    	for(int i = 0; i < asf_bone.dof.size(); i++)
    		Assert.assertEquals("Bone dof created does not match initial dof.", 
								asf_bone.dof.get(i), bone.getDof().get(i));
    	
    	// Test limits
    	for(int i = 0; i < asf_bone.limits.size(); i++)
    	{
    		for(int j = 0; j < asf_bone.limits.get(i).size(); j++)
    			Assert.assertEquals("Bone limits created does not match initial limits.", asf_bone.limits.get(i).get(j), 
    								bone.getLimits().get(i).get(j));
    		
    	}	
    }
}
