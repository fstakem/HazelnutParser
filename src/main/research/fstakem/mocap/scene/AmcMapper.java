package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.Map;


import main.research.fstakem.mocap.parser.AcclaimFrame;
import main.research.fstakem.mocap.parser.AmcData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmcMapper 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AmcMapper.class);
		
	public static void addStateDataToBones(RootElement root, AmcData amc_data) throws Exception
	{
		logger.debug("AmcMapper.addStateDataToBones(): Entering method.");
		logger.info("AmcMapper.addStateDataToBones(): {} frames to parse.", amc_data.frames.size());
		
		for(int i = 0; i < amc_data.frames.size(); i++)
		{
			logger.info("AmcMapper.addStateDataToBones(): Adding frame {}.", i+1);
			
			AcclaimFrame frame = amc_data.frames.get(i);
			for(Map.Entry<String, ArrayList<Float>> entry : frame.bone_positions.entrySet())
			{
				CharacterElement character_element = root.findCharacterElement(entry.getKey());
				character_element.addState(new CharacterElementState(entry.getValue()));
				logger.info("AmcMapper.addStateDataToBones(): Add state to {}.", character_element.getName());
			}
		}
		
		logger.debug("AmcMapper.addStateDataToBones(): Exiting method.");
	}

}
