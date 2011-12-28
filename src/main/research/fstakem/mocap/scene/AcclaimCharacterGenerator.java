package main.research.fstakem.mocap.scene;


import main.research.fstakem.mocap.parser.AmcData;
import main.research.fstakem.mocap.parser.AsfData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcclaimCharacterGenerator 
{	
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AcclaimCharacterGenerator.class);
		
	public AcclaimCharacterGenerator()
	{
		
	}
	
	public static Character createCharacterFromData(String character_name, AsfData asf_data, AmcData amc_data) throws Exception
	{
		logger.debug("AcclaimCharacterGenerator.createCharacterFromData(): Entering method.");
		
		Character character = new Character(character_name);
		RootElement root = AsfMapper.createCharacterElementHierarchy(asf_data.hierarchy);
		character.setRootCharacterElement(root);
		AsfMapper.addDetailsToRoot(root, asf_data.root);
		AsfMapper.addDetailsToBones(root, asf_data.bones);
		AmcMapper.addStateDataToBones(root, amc_data);
		
		logger.debug("AcclaimCharacterGenerator.createCharacterFromData(): Exiting method.");
		return character;
	}
}
