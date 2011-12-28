package test.research.fstakem.mocap.integration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

import test.research.fstakem.mocap.parser.AcclaimImporterTest;

public class AcclaimFileImporterTest 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AcclaimImporterTest.class);
		
	// Constants
	private static String DATA_DIRECTORY = "test_data";
	private static String ASF_FILE_PATH = DATA_DIRECTORY + "/asf.asf";
	private static String AMC_FILE_PATH = DATA_DIRECTORY + "/amc.amc";
	
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
    	
    	try 
    	{
    		ArrayList<String> raw_lines = this.importFile(AcclaimFileImporterTest.ASF_FILE_PATH);
    		AsfData asf_data = AcclaimImporter.parseAsfData(raw_lines);
		} 
    	catch (ParseException e) 
    	{
			Assert.fail(e.getMessage());
		} 
    	catch (FileNotFoundException e) 
		{
    		Assert.fail(e.getMessage());
		} 
    	catch (IOException e) 
		{
    		Assert.fail(e.getMessage());
		}
    }
    
    @Test
    public void parseAmcData()
    {
    	logger.debug("Test to make sure the amc data is properly parsed."); 
    	
		try 
		{
			ArrayList<String> raw_lines = this.importFile(AcclaimFileImporterTest.AMC_FILE_PATH);
			AmcData amc_data = AcclaimImporter.parseAmcData(raw_lines);
		} 
		catch (ParseException e) 
		{
			Assert.fail(e.getMessage());
		} 
		catch (FileNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		} 
		catch (IOException e) 
		{
			Assert.fail(e.getMessage());
		}
    }
    
    private ArrayList<String> importFile(String path) throws FileNotFoundException, IOException 
    {
    	ArrayList<String> lines = new ArrayList<String>();
		String line;
		
		FileInputStream file_input_stream = new FileInputStream(path);
		BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(file_input_stream));
		while ( (line = buffer_reader.readLine()) != null ) 
			lines.add(line);
		
		return lines;
    }
}
