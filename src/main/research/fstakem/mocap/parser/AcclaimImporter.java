package main.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcclaimImporter 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AcclaimImporter.class);
		
	public static AsfData parseAsfData(ArrayList<String> lines) throws ParseException
	{
		logger.debug("AcclaimImporter.parseAsfData(): Entering method.");
		logger.info("AcclaimImporter.parseAsfData(): There are {} lines of asf data.", lines.size());
		
		HashMap<String, ArrayList<String>> sections = AsfParser.seperateSections(lines);
		AsfData asf_data = new AsfData();
		asf_data.version = AsfParser.parseVersion(sections);
		asf_data.name =  AsfParser.parseName(sections);
		asf_data.units =  AsfParser.parseUnits(sections);
		asf_data.root = AsfParser.parseRoot(sections);
		asf_data.bones = AsfParser.parseBones(sections);
		asf_data.hierarchy = AsfParser.parseHierarchy(sections);
		
		logger.debug("AcclaimImporter.parseAsfData(): Exiting method.");
		return asf_data;
	}
	
	public static AmcData parseAmcData(ArrayList<String> lines) throws ParseException
	{
		logger.debug("AcclaimImporter.parseAmcData(): Entering method.");
		logger.info("AcclaimImporter.parseAmcData(): There are {} lines of amc data.", lines.size());
		
		ArrayList<ArrayList<String>> frames = AmcParser.seperateFrames(lines);
		AmcData amc_data = new AmcData();
		amc_data.frames = AmcParser.parseFrames(frames);
		
		logger.debug("AcclaimImporter.parseAmcData(): Exiting method.");
		return amc_data;
	}
}
