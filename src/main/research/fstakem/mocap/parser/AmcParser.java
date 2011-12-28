package main.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmcParser extends Parser
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AmcParser.class);
		
	public AmcParser()
	{
		
	}
	
	public static ArrayList<ArrayList<String>> seperateFrames(ArrayList<String> lines)
	{
		logger.debug("AmcParser.seperateFrames(): Entering method.");
		
		ArrayList<ArrayList<String>> frames = new ArrayList<ArrayList<String>>();
		ArrayList<String> frame_lines = new ArrayList<String>();
		Pattern number_pattern = Pattern.compile("[0-9]+");
		String line;
		String[] tokens;
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			if( line.startsWith(Parser.COMMENT_CHAR) || 
				line.startsWith(Parser.KEYWORD_CHAR) )
				continue;
			
			tokens = line.split(Parser.TOKEN_DELIMITER);
			if( number_pattern.matcher(tokens[0]).matches() )
			{
				if(frame_lines.size() > 0)
					frames.add(frame_lines);
				frame_lines = new ArrayList<String>();
				frame_lines.add(line);
			}
			else
				frame_lines.add(line);
		}
		
		frames.add(frame_lines);
		
		logger.debug("AmcParser.seperateFrames(): Exiting method.");
		return frames;
	}
	
	public static ArrayList<AcclaimFrame> parseFrames(ArrayList<ArrayList<String>> frames) throws ParseException
	{
		logger.debug("AmcParser.parseFrames(): Entering method.");
		
		ArrayList<AcclaimFrame> acclaim_frames = new ArrayList<AcclaimFrame>();
		HashSet<String> bones = AmcParser.initializeBones(frames.get(0));
		
		for(int i = 0; i < frames.size(); i++)
		{
			logger.info("AmcParser.parseFrames(): Parsing frame {}.", i+1);
			
			ArrayList<String> frame = frames.get(i);
			acclaim_frames.add(AmcParser.parseFrame(frame, bones));
		}
		
		logger.debug("AmcParser.parseFrames(): Exiting method.");
		return acclaim_frames;
	}
		
	private static AcclaimFrame parseFrame(ArrayList<String> lines, HashSet<String> bones) throws ParseException
	{
		logger.debug("AmcParser.parseFrame(): Entering method.");
		
		AcclaimFrame frame = new AcclaimFrame();
		Pattern number_pattern = Pattern.compile("[0-9]+");	
		String line;
		String[] tokens;
		ArrayList<Float> position;
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			
			if( number_pattern.matcher(tokens[0]).matches() )
				frame.number = Integer.parseInt(tokens[0]);
			else
			{
				position = new ArrayList<Float>();
				
				for(int j = 1; j < tokens.length; j++)
					position.add(Float.valueOf(tokens[j]));
				
				if(!position.isEmpty())
					frame.bone_positions.put(tokens[0], position);
				else
					throw new ParseException("The Amc frame has no values for the bone.", 0);
			}
		}
		
		if( !AmcParser.checkForAllBonesInFrame(frame, bones) )
			throw new ParseException("The Amc frame does not contain all of the bones from a previous frame.", 0);
		
		logger.info("AmcParser.parseFrame(): Parsed frame {} and it has {} bones.", frame.number, frame.bone_positions.size());
		logger.debug("AmcParser.parseFrame(): Exiting method.");
		return frame;
	}
	
	private static HashSet<String> initializeBones(ArrayList<String> lines)
	{
		logger.debug("AmcParser.initializeBones(): Entering method.");
		
		HashSet<String> bones = new HashSet<String>();
		Pattern number_pattern = Pattern.compile("[0-9]+");
		String line;
		String[] tokens;
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			if( !number_pattern.matcher(tokens[0]).matches() )
			{
				logger.info("AmcParser.initializeBones(): Adding bone \'{}\'.", tokens[0]);
				bones.add(tokens[0]);
			}
		}
		
		logger.debug("AmcParser.initializeBones(): Exiting method.");
		return bones;
	}
	
	private static boolean checkForAllBonesInFrame(AcclaimFrame frame, HashSet<String> bones)
	{
		logger.debug("AmcParser.checkForAllBonesInFrame(): Entering method.");
		
		Iterator<String> iter = bones.iterator();
		 
		while(iter.hasNext())
		{
			if( !frame.bone_positions.keySet().contains(iter.next()) )
			{
				logger.debug("AmcParser.checkForAllBonesInFrame(): Exiting method.");
				return false;
			}
		}
		
		logger.info("AmcParser.checkForAllBonesInFrame(): All bones found in frame.");
		logger.debug("AmcParser.checkForAllBonesInFrame(): Exiting method.");
		return true;
	}
}
