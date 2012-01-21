package test.research.fstakem.mocap.scene;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AcclaimRoot;
import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.scene.AmcMapper;
import main.research.fstakem.mocap.scene.AsfMapper;
import main.research.fstakem.mocap.scene.CharacterElement;
import main.research.fstakem.mocap.scene.CharacterElementState;
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

public class AmcMapperTest extends MapperTest
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AmcMapperTest.class);

	// Constants
	private static final float DELTA_STATE_VALUE_ALLOWED = 0.1f;
	
	// Variables
	
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
    public void addStateDataToBonesTest()
    {  
    	logger.debug("Test to make sure the amc state data is created properly.");  
    	
    	Map<String, List<String>> hierarchy = MapperTest.createElementHierachy();
    	RootElement root = null;
    	AcclaimRoot asf_root = null;
    	List<AcclaimBone> asf_bones = null;
    	AmcData amc_data = MapperTest.createAmcData();
    	
		try 
		{
			root = AsfMapper.createCharacterElementHierarchy(hierarchy);
			asf_root = MapperTest.createAsfRoot();
			AsfMapper.addDetailsToRoot(root, asf_root);
			asf_bones = MapperTest.createAsfBones();
			AsfMapper.addDetailsToBones(root, asf_bones);
			AmcMapper.addStateDataToBones(root, amc_data);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		List<CharacterElement> character_elements = root.getAllSubElements();
		
		for(int i = 0; i < character_elements.size(); i++)
		{
			CharacterElement element = character_elements.get(i);
			List<CharacterElementState> states = element.getStates();
			logger.debug("Element \'{}\' has {} states.", element.getName(), states.size());
			
			int bone_index = AmcMapperTest.getBoneIndex(element.getName());
			if(bone_index == -1)
				Assert.fail("Bone not found.");
			
			for(int j = 0; j < amc_data.frames.size(); j++)
			{
				CharacterElementState state = states.get(j);
				List<Float> state_values = state.getValues();
				logger.debug("Frame index {} has {} values.", j, state_values.size());
				
				for(int k = 0; k < MapperTest.amc_bone_orientation.length; k++)
				{
					logger.info("Testing that \'{}\' has state \'{}\'.", element.getName(), state_values.get(k));
					Assert.assertEquals("Character element state created does not match initial state.", MapperTest.amc_bone_orientation[k] * (bone_index+1) * (j+1), state_values.get(k), AmcMapperTest.DELTA_STATE_VALUE_ALLOWED);
				}
			}
		}	
    }
    
    private static int getBoneIndex(String bone_name)
    {
		for(int j = 0; j < MapperTest.asf_bone_names.length; j++)
		{
			if(bone_name.equals(MapperTest.asf_bone_names[j]))
				return j;
		}
		
		return -1;
    }
}
