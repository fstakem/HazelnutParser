package test.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import main.research.fstakem.mocap.parser.AcclaimData;
import main.research.fstakem.mocap.scene.CharacterElement;
import main.research.fstakem.mocap.scene.CharacterElementState;

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

public class CharacterElementTest 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CharacterElementTest.class);
		
	// Constants
	private static final Float[] character_state_values = {1.1f, 2.2f, 3.3f};
	
	// Variables
	CharacterElement character_element;
	
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
    	this.character_element = new CharacterElement();
    	this.character_element.setName("Test Character Element");
    }
 
    @After
    public void tearDown() throws Exception 
    {
    	
    }
    
    @Test
    public void dofFromStringTest()
    {
    	logger.debug("Test to make sure the dof string is properly parsed.");
    	
    	String[] dof_strings = { "tx", "ty", "tz", "rx", "ry", "rz" }; 
    	AcclaimData.OperationOnAxis[] dof = { AcclaimData.OperationOnAxis.TX, 
    										  AcclaimData.OperationOnAxis.TY, 
    										  AcclaimData.OperationOnAxis.TZ, 
    										  AcclaimData.OperationOnAxis.RX, 
    										  AcclaimData.OperationOnAxis.RY, 
    										  AcclaimData.OperationOnAxis.RZ };
    	
    	for(int i = 0; i < dof_strings.length; i++)
    	{
    		AcclaimData.OperationOnAxis d = null;
    		try 
    		{
    			logger.info("Testing the \'{}\' dof element.", i+1);
				d = AcclaimData.getOperationOnAxisFromString(dof_strings[i]);
			} 
    		catch (Exception e) 
    		{
				Assert.fail(e.getMessage());
			}
    		
    		if(d != dof[i])
    			Assert.fail("Parsed dof does not match the corresponding initial value.");
    	}
    }
    
    @Test
    public void axisFromStringTest()
    {
    	logger.debug("Test to make sure the axis string is properly parsed.");
    	
    	String[] axis_strings = { "x", "y", "z" }; 
    	AcclaimData.Axis[] axis = { AcclaimData.Axis.X, AcclaimData.Axis.Y, AcclaimData.Axis.Z };
    	
    	for(int i = 0; i < axis_strings.length; i++)
    	{
    		AcclaimData.Axis a = null;
    		try 
    		{
    			logger.info("Testing the \'{}\' axis element.", i+1);
				a = AcclaimData.getAxisFromString(axis_strings[i]);
			} 
    		catch (Exception e) 
    		{
				Assert.fail(e.getMessage());
			}
    		
    		if(a != axis[i])
    			Assert.fail("Parsed axis does not match the corresponding initial value.");
    	}
    }
    
    @Test
    public void characterStateTest()
    {
    	logger.debug("Test to make sure the state is properly set and retreived.");
    	
    	logger.info("Creating different character element states.");
    	CharacterElementState state_a = this.createState(1);
    	CharacterElementState state_b = this.createState(2);
    	CharacterElementState state_c = this.createState(3);
    	CharacterElementState state_d = this.createState(4);
    	
    	logger.info("Adding the new states to the character element.");
    	ArrayList<CharacterElementState> states = new ArrayList<CharacterElementState>();
    	states.add(state_a);
    	states.add(state_b);
    	this.character_element.setStates(states);
    	this.character_element.addState(state_c);
    	this.character_element.addStateAt(1, state_d);
    	
    	logger.info("Restting the current state and testing.");
    	this.character_element.resetCurrentState();
    	CharacterElementState state = this.character_element.getCurrentState();
		this.compareStates(state, state_a);
		this.character_element.incrementState();
		
		logger.info("Incrementing and decrementing the current state and testing.");
		state = this.character_element.getCurrentState();
		this.compareStates(state, state_d);
		this.character_element.incrementState();
		this.character_element.incrementState();
		this.character_element.decrementState();
		
		state = this.character_element.getCurrentState();
		this.compareStates(state, state_b);
		this.character_element.setCurrentState(this.character_element.getNumberOfStates()-1);
		
		logger.info("Setting the current state and testing.");
		state = this.character_element.getCurrentState();
		this.compareStates(state, state_c);
    }
    
    @Test
    public void childrenIterationTest()
    {
    	logger.debug("Test to make sure the child elements are properly set and retreived.");
    	
    	logger.info("Creating nested elements.");
    	String[] nested_names = { "A", "B" };
    	ArrayList<CharacterElement> nested_elements = new ArrayList<CharacterElement>();
    	nested_elements.add(this.createNestedCharacterElements(nested_names));
    	this.character_element.setChildren(nested_elements);
    	Assert.assertTrue("The child nodes are not recognized.", this.character_element.hasChildren());
    	Assert.assertEquals("The number of child nodes stated does not match the actual number.", 1, this.character_element.getNumberOfChildren());
    	
    	logger.info("Testing the child elements to make sure they have the proper nesting.");
    	List<CharacterElement> character_elements = this.character_element.getChildren();
    	CharacterElement child = character_elements.get(0);
    	if(!child.getName().equals(nested_names[0]))
    		Assert.fail("Parsed children do not match the initial children.");
    	
    	if(!child.getChildren().get(0).getName().equals(nested_names[1]))
    		Assert.fail("Parsed children do not match the initial children.");
    }
    
    @Test
    public void getAllSubElementsTest()
    {
    	logger.debug("Test to make sure nested children can be flattened into an array.");
    	
    	String[] names = { "A", "B", "C" };
    	List<CharacterElement> elements = this.createListOfCharacterElements(names);
    	this.character_element.addChild(this.createNestedCharacterElements(names));
    	List<CharacterElement> nested_elements = this.character_element.getAllSubElements();
    	
    	for(CharacterElement element : elements)
    	{
    		logger.info("Testing character element \'{}\' to make sure is was returned.", element.getName());
    		Assert.assertTrue("Could not find a nested character element.", nested_elements.contains(element));
    	}
    }
    
    @Test
    public void findCharacterElementTest()
    {
    	logger.debug("Test to make sure the child elements can be searched.");
    	
    	String[] nested_names = { "A", "B", "C", "D" };
    	this.character_element.addChild(this.createNestedCharacterElements(nested_names));
    	
    	for(int i = 0; i < nested_names.length; i++)
    	{
    		logger.info("Testing character element \'{}\' to make sure is was found.", nested_names[i]);
    		CharacterElement element = this.character_element.findCharacterElement(nested_names[i]);
    		if(element == null)
    			Assert.fail("Could not find a nested character element.");
    		
			Assert.assertEquals("Could not find a nested character element.", 
								new CharacterElement(nested_names[i]), 
								element);
    	}
    }
    
    @Test
    public void equalityTest()
    {
    	logger.debug("Test to make compare character elements.");
    	
    	CharacterElement new_character_element = new CharacterElement();
    	new_character_element.setName("Test Character Element");
    	Assert.assertTrue("Character element objects are not equal.", this.character_element.equals(new_character_element));
    }
    
    private void compareStates(CharacterElementState state, CharacterElementState other_state)
    {
    	if(!state.equals(other_state))
    		Assert.fail("Character element states do no match.");
    }
       
    private CharacterElementState createState(int multiplier)
    {
    	ArrayList<Float> values = new ArrayList<Float>();
    	for(Float s : CharacterElementTest.character_state_values)
    		values.add(s * multiplier);
    	
    	CharacterElementState state = new CharacterElementState(values);
    	return state;
    }
    
    private CharacterElement createNestedCharacterElements(String[] names)
    {
    	CharacterElement first_character_element = null;
    	CharacterElement new_character_element = null;
    	CharacterElement last_character_element = null;
    	
    	for(String name : names)
    	{
    		new_character_element = new CharacterElement(name);
    		
    		if(last_character_element == null)
    			first_character_element = new_character_element;
    		else
    			last_character_element.addChild(new_character_element);
    		
    		last_character_element = new_character_element;
    	}
    	
    	return first_character_element;
    }
    
    private List<CharacterElement> createListOfCharacterElements(String[] names)
    {
    	List<CharacterElement> elements = new ArrayList<CharacterElement>();
    	for(String name : names)
    		elements.add(new CharacterElement(name));
    	
    	return elements;
    }
}
