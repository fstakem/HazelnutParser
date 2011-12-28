package test.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import main.research.fstakem.mocap.parser.AcclaimFrame;
import main.research.fstakem.mocap.parser.AmcParser;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmcParserTest 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AmcParserTest.class);
		
	// Constants
	private static final String[] bones = { "root", "lowerback", "upperback", "thorax" };
	private static final float[] axis = { 2.1f, 2.3f, 2.5f };
	
	// Variables
	ArrayList<ArrayList<String>> frames;
	int[] frame_numbers;
	
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
    	this.frame_numbers = new int[2];
		this.frame_numbers[0] = 1;
		this.frame_numbers[1] = 2;
		ArrayList<String> raw_lines = AmcParserTest.createAmcData(frame_numbers);
		this.frames = AmcParser.seperateFrames(raw_lines);
    }
 
    @After
    public void tearDown()
    {
    	
    }
    
    @Test
    public void seperateFramesTest()
    {
    	logger.debug("Test to make sure the amc is split on frames properly.");
    	
    	if(this.frames.size() != this.frame_numbers.length)
    		Assert.fail("The number of parsed frames do not match the initial number of frames.");
    }
    
    @Test
    public void parseFramesTest()
    {
    	logger.debug("Test to make sure the frames are properly parsed.");
    	
    	ArrayList<AcclaimFrame> frames = null;
    	try 
    	{
			frames = AmcParser.parseFrames(this.frames);
		} 
    	catch (ParseException e) 
    	{
			Assert.fail(e.getMessage());
		}
    	
    	for(int i= 0; i < frames.size(); i++)
    	{
    		AcclaimFrame frame = frames.get(i);
    		if(frame.number != this.frame_numbers[i])
    			Assert.fail("Parsed frame number does match initial frame number.");
    		
    		if(i != 0)
    		{
    			AcclaimFrame previous_frame = frames.get(i-1);
    			AcclaimFrame current_frame = frames.get(i);
    			this.compareFrames(previous_frame, current_frame, current_frame.number);
    		}
    	}
    }
        
    private void compareFrames(AcclaimFrame frame, AcclaimFrame other_frame, int multiplier)
    {
    	float difference = 0.01f;
    	for(Map.Entry<String, ArrayList<Float>> entry : frame.bone_positions.entrySet())
		{
			String key = entry.getKey();
			if(other_frame.bone_positions.containsKey(key))
			{
				ArrayList<Float> frame_values = entry.getValue();
				ArrayList<Float> other_frame_values = other_frame.bone_positions.get(key);
				for(int j = 0; j < frame_values.size(); j++)
				{
					if(frame_values.get(j) * multiplier - other_frame_values.get(j) < -difference || 
					   frame_values.get(j) * multiplier - other_frame_values.get(j) > difference )
						Assert.fail("Parsed bone values do not match initial bone values.");
				}
			}
			else
				Assert.fail("Parsed bone name does match initial bone name.");
		}
    }
           
    public static ArrayList<String> createAmcData(int[] frame_numbers)
    {
    	ArrayList<String> frames = new ArrayList<String>();
    	frames.add("#!OML:ASF F:\\VICON\\USERDATA\\INSTALL\\rory3\\rory3.ASF");
    	frames.add(":FULLY-SPECIFIED");
    	frames.add(":DEGREES");
    	
    	for(int i = 0; i < frame_numbers.length; i++)
    		frames.addAll(frames.size(), AmcParserTest.createFrame(String.valueOf(frame_numbers[i]), frame_numbers[i]));
    	
    	return frames;
    }
    
    private static ArrayList<String> createFrame(String number, int multiplier)
    {
    	ArrayList<String> frame = new ArrayList<String>();
    	frame.add(number);
    	String bone_values = AmcParserTest.createBoneValues(multiplier);
    	
    	for(int i = 0; i < AmcParserTest.bones.length; i++)
    	{
    		String bone = AmcParserTest.bones[i] + " " + bone_values;
    		if(i == 0)
    			bone += " " + bone_values;
    		frame.add(bone);
    	}
    	
    	return frame;
    }
    
    private static String createBoneValues(int multiplier)
    {
    	String axis = "";
    	for(int i = 0; i < AmcParserTest.axis.length; i++)
    	{
    		axis += String.valueOf(AmcParserTest.axis[i] * multiplier);
    		if(i < AmcParserTest.axis.length - 1)
    			axis += " ";
    	}
    	
    	return axis;
    }
}
