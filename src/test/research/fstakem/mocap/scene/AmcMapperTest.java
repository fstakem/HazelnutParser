package test.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;

import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.scene.AmcMapper;
import main.research.fstakem.mocap.scene.AsfMapper;
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

public class AmcMapperTest extends MapperTest
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AmcMapperTest.class);

	// Constants
	
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
    	
    	HashMap<String, ArrayList<String>> hierarchy = MapperTest.createElementHierachy();
    	RootElement root = null;
    	HashMap<String, ArrayList<String>> asf_root = null;
    	ArrayList<AcclaimBone> asf_bones = null;
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
			Assert.fail(e.getMessage());
		}
		
		// TODO
		// Check the state of each element
		ArrayList<CharacterElement> character_elements = root.getAllSubElements();
		for(int i = 0; i < character_elements.size(); i++)
		{
			CharacterElement element = character_elements.get(i);
		
		}
    }
}
