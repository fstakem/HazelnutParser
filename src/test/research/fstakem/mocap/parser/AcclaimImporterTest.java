package test.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;

import main.research.fstakem.mocap.parser.AcclaimImporter;
import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.parser.AsfData;

import org.junit.*;
import org.junit.runner.Description;
import org.junit.rules.TestWatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcclaimImporterTest 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AcclaimImporterTest.class);
		
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
    public void parseAsfDataTest()
    { 
    	logger.debug("Test to make sure the asf data is properly parsed."); 
    	
    	ArrayList<String> raw_lines = AsfParserTest.createAsfData();
    	AsfData asf_data = null;
    	
    	try 
    	{
    		logger.info("Test to make sure the asf data can be properly parsed.");
			asf_data = AcclaimImporter.parseAsfData(raw_lines);
		} 
    	catch (ParseException e) 
    	{
			Assert.fail(e.getMessage());
		}
    }
    
    @Test
    public void parseAmcData()
    {
    	logger.debug("Test to make sure the amc data is properly parsed."); 
    	
    	int[] frame_numbers = {1, 2};
		ArrayList<String> raw_lines = AmcParserTest.createAmcData(frame_numbers);
		AmcData amc_data = null;
		
		try 
		{
			logger.info("Test to make sure the amc data can be properly parsed.");
			amc_data = AcclaimImporter.parseAmcData(raw_lines);
		} 
		catch (ParseException e) 
		{
			Assert.fail(e.getMessage());
		}
    }
}
