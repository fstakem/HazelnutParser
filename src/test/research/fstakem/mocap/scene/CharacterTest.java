package test.research.fstakem.mocap.scene;

import java.util.ArrayList;

import junit.framework.Assert;

import main.research.fstakem.mocap.scene.Character;
import main.research.fstakem.mocap.scene.CharacterElement;
import main.research.fstakem.mocap.scene.RootElement;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterTest 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CharacterTest.class);
		
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
    public void getAllCharacterElementsTest()
    {
    	logger.debug("Test to make sure all of the character elements are returned.");
    	
    	Character character = new Character();
    	String[] names = { "A", "B", "C" };
    	
    	ArrayList<CharacterElement> elements = CharacterTest.createCharacterElements(names);
    	character.setRootCharacterElement( CharacterTest.createNestedCharacterElements(names) );
    	ArrayList<CharacterElement> nested_elements = character.getAllCharacterElements();
    
    	for(CharacterElement element : elements)
    	{
    		logger.info("Testing character element \'{}\' to make sure is was returned.", element.getName());
    		Assert.assertTrue("Could not find a nested character element.", nested_elements.contains(element));
    	}
    }
    
    public static CharacterElement createNestedCharacterElements(String[] names)
    {
    	RootElement root = new RootElement();
    	CharacterElement current_element = null;
    	CharacterElement last_element = root;
    	
    	for(String name : names)
    	{
    		current_element = new CharacterElement(name);
    		last_element.addChild(current_element);
    		last_element = current_element;
    	}
    	
    	return root;
    }
    
    public static ArrayList<CharacterElement> createCharacterElements(String[] names)
    {
    	ArrayList<CharacterElement> elements = new ArrayList<CharacterElement>();
    	elements.add(new RootElement());
    	
    	for(String name : names)
    		elements.add(new CharacterElement(name));
    	
    	return elements;
    }
}
