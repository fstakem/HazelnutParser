package test.research.fstakem.mocap.scene;

import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.parser.AsfData;
import main.research.fstakem.mocap.scene.AcclaimCharacterGenerator;
import main.research.fstakem.mocap.scene.Character;

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

import test.research.fstakem.mocap.integration.AcclaimFileImporterTest;

public class AcclaimCharacterGeneratorTest 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AcclaimCharacterGeneratorTest.class);
	
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
    public static void setUpClass() throws Exception 
    {
		    
    }
 
    @AfterClass
    public static void tearDownClass() throws Exception 
    {
    	  
    }
 
    @Before
    public void setUp() throws Exception 
    {
    	   
    }
 
    @After
    public void tearDown() throws Exception 
    {
    	 
    }
    
    @Test
    public void characterNameTest()
    {  
    	logger.debug("Test to make sure character's name is set properly.");  
    	
    	Character character = null;
    	String name = "George";
    	AsfData asf_data = MapperTest.createAsfData();
    	AmcData amc_data = MapperTest.createAmcData();
    	
    	try 
    	{
    		logger.info("Testing that the character \'{}\' can be created.", name);
    		character = AcclaimCharacterGenerator.createCharacterFromData(name, asf_data, amc_data);
		} 
    	catch (Exception e) 
    	{
			Assert.fail(e.getMessage());
		}
    	
    	if(!character.getName().equals(name))
    		Assert.fail("The character name set does not equal the initial character name.");
    }
}
