package main.research.fstakem.mocap.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Vector3f;

import main.research.fstakem.mocap.scene.AsfMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AsfParser extends Parser
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AsfMapper.class);
		
	// Constants	
	public static final String VERSION_KEYWORD = "version";
	public static final String NAME_KEYWORD = "name";
	public static final String UNITS_KEYWORD = "units";
	public static final String ROOT_KEYWORD = "root";
	public static final String DOCUMENTATION_KEYWORD = "documentation";
	public static final String BONES_KEYWORD = "bonedata";
	public static final String HIERARCHY_KEYWORD = "hierarchy";
	private static final String START_LABEL = "begin";
	private static final String END_LABEL = "end";
	
	public static final String ROOT_ORDER_LABEL = "order";
	public static final String ROOT_AXIS_LABEL = "axis";
	public static final String ROOT_POSITION_LABEL = "position";
	public static final String ROOT_ORIENTATION_LABEL = "orientation";
	
	public static final String BONE_ID_LABEL = "id";
	public static final String BONE_NAME_LABEL = "name";
	public static final String BONE_DIRECTION_LABEL = "direction";
	public static final String BONE_POSITION_LABEL = "position";
	public static final String BONE_ORIENTATION_LABEL = "orientation";
	public static final String BONE_LENGTH_LABEL = "length";
	public static final String BONE_ORDER_LABEL = "order";
	public static final String BONE_AXIS_LABEL = "axis";
	public static final String BONE_DOF_LABEL = "dof";
	public static final String BONE_LIMITS_LABEL = "limits";
			
	public AsfParser()
	{
	
	}
	
	public static HashMap<String, List<String>> seperateSections(List<String> raw_lines)
	{
		logger.debug("AsfParser.seperateSections(): Entering method.");
		
		HashMap<String, List<String>> asf_sections = new HashMap<String, List<String>>();
		String current_keyword = "";
		List<String> current_data = null;
		String line = "";
		String[] tokens = null;
		
		for(int i = 0; i < raw_lines.size(); i++)
		{
			line = raw_lines.get(i).trim();
			if( line.startsWith(Parser.COMMENT_CHAR)  )
					continue;
			
			if( line.startsWith(Parser.KEYWORD_CHAR)  )
			{
				if(current_data != null)
					asf_sections.put(current_keyword, current_data);
				
				tokens = line.split(Parser.TOKEN_DELIMITER);
				current_keyword = tokens[0].substring(1, tokens[0].length());
				current_data = new ArrayList<String>();
				
				String formatted_line = "";
				if(tokens.length > 1)
				{
					for(int j = 1; j < tokens.length; j++)
						formatted_line += tokens[j] + " ";
				}
				
				if(formatted_line.length() > 0)
					current_data.add(formatted_line.trim());
			}
			else if(current_data != null)
				current_data.add(line);
		}
		
		if(current_data != null)
			asf_sections.put(current_keyword, current_data);
		
		logger.debug("AsfParser.seperateSections(): Exiting method.");
		return asf_sections;
	}
		
	public static String parseVersion(HashMap<String, List<String>> sections) throws ParseException
	{
		logger.debug("AsfParser.parseVersion(): Entering method.");
		
		List<String> lines = sections.get(AsfParser.VERSION_KEYWORD);
		if(lines.size() == 0)
			throw new ParseException("The Asf version section was not formatting properly.", 0); 
		
		String version = lines.get(0).split(Parser.TOKEN_DELIMITER)[0];
		logger.info("AsfParser.parseVersion(): Version => \'{}\'.", version);
		
		logger.debug("AsfParser.parseVersion(): Exiting method.");
		return version;
	}
	
	public static String parseName(HashMap<String, List<String>> sections) throws ParseException
	{
		logger.debug("AsfParser.parseName(): Entering method.");
		
		List<String> lines = sections.get(AsfParser.NAME_KEYWORD);
		if(lines.size() == 0)
			throw new ParseException("The Asf name section was not formatting properly.", 0); 
		
		String name = lines.get(0).split(Parser.TOKEN_DELIMITER)[0];
		logger.info("AsfParser.parseName(): Name => \'{}\'.", name);
		
		logger.debug("AsfParser.parseName(): Exiting method.");
		return name;
	}
	
	public static HashMap<String, String> parseUnits(HashMap<String, List<String>> sections) throws ParseException
	{
		logger.debug("AsfParser.parseUnits(): Entering method.");
		
		List<String> lines = sections.get(AsfParser.UNITS_KEYWORD);
		HashMap<String, String> units = new HashMap<String, String>();
		String line;
		String[] tokens;

		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			
			if(tokens.length > 1)
			{
				logger.info("AsfParser.parseUnits(): Units => \'{}\' - \'{}\'.", tokens[0], tokens[1]);
				units.put(tokens[0], tokens[1]);
			}
			else
				throw new ParseException("The Asf units section was not formatting properly.", 0);
		}
		
		logger.debug("AsfParser.parseUnits(): Exiting method.");
		return units;
	}
	
	public static List<String> parseDocumentation(HashMap<String, List<String>> sections) throws ParseException
	{
		logger.debug("AsfParser.parseDocumentation(): Entering method.");
		List<String> documentation = sections.get(AsfParser.DOCUMENTATION_KEYWORD);
		
		logger.debug("AsfParser.parseDocumentation(): Exiting method.");
		return documentation;
	}
	
	public static AcclaimRoot parseRoot(HashMap<String, List<String>> sections) throws ParseException
	{
		logger.debug("AsfParser.parseRoot(): Entering method.");
		
		List<String> lines = sections.get(AsfParser.ROOT_KEYWORD);
		AcclaimRoot root = new AcclaimRoot();
		String line;
		String[] tokens;
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			
			if(tokens.length > 1)
			{
				if(tokens[0].equals(AsfParser.ROOT_ORDER_LABEL) && tokens.length > 6)
				{
					for(int j = 1; j < tokens.length; j++)
						root.amc_data_order[j-1] = AcclaimData.getOperationOnAxisFromString(tokens[j]);
				}
				else if(tokens[0].equals(AsfParser.ROOT_AXIS_LABEL))
				{
					for(int j = 0; j < 3; j++)
						root.orientation_order[j] = AcclaimData.getAxisFromString(tokens[1].substring(j, j+1));
				}
				else if(tokens[0].equals(AsfParser.ROOT_POSITION_LABEL) && tokens.length > 3)
					root.position = new Vector3f( Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
				else if(tokens[0].equals(AsfParser.ROOT_ORIENTATION_LABEL) && tokens.length > 3)
					root.orientation = new Vector3f( Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
				else
					throw new ParseException("The Asf root section was not formatting properly.", 0);
			}
			else
				throw new ParseException("The Asf root section was not formatting properly.", 0);
		}
		
		logger.debug("AsfParser.parseRoot(): Exiting method.");
		return root;
	}
	
	public static List<AcclaimBone> parseBones(HashMap<String, List<String>> sections)
	{
		logger.debug("AsfParser.parseBones(): Entering method.");
		
		List<String> lines = sections.get(AsfParser.BONES_KEYWORD);
		List<AcclaimBone> bones = new ArrayList<AcclaimBone>();
		String line;
		String[] tokens;
		List<String> bone_lines = new ArrayList<String>();
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			
			if(tokens[0].equals(AsfParser.START_LABEL))
				bone_lines = new ArrayList<String>();
			else if(tokens[0].equals(AsfParser.END_LABEL))
				bones.add( AsfParser.parseBone(bone_lines) );
			else
				bone_lines.add(line);
		}
		
		logger.debug("AsfParser.parseBones(): Exiting method.");
		return bones;
	}
	
	private static AcclaimBone parseBone(List<String> lines)
	{
		logger.debug("AsfParser.parseBone(): Entering method.");
		
		AcclaimBone bone = new AcclaimBone();
		String line;
		String[] tokens;
		String last_label = "";
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			
			if(tokens[0].equals(AsfParser.BONE_ID_LABEL) &&
			   tokens.length > 1)
			{
				bone.id = Integer.valueOf(tokens[1]);
				last_label = AsfParser.BONE_ID_LABEL;
			}
			else if(tokens[0].equals(AsfParser.BONE_NAME_LABEL) &&
					tokens.length > 1)
			{
				bone.name = tokens[1];
				last_label = AsfParser.BONE_NAME_LABEL;
			}
			else if(tokens[0].equals(AsfParser.BONE_DIRECTION_LABEL) &&
					tokens.length > 3)
			{
				bone.direction = new Vector3f( Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
				last_label = AsfParser.BONE_DIRECTION_LABEL;
			}
			else if(tokens[0].equals(AsfParser.BONE_LENGTH_LABEL) &&
					tokens.length > 1)
			{
				bone.length = Float.valueOf(tokens[1]);
				last_label = AsfParser.BONE_LENGTH_LABEL;
			}
			else if(tokens[0].equals(AsfParser.BONE_AXIS_LABEL) &&
					tokens.length > 4)
			{
				bone.orientation = new Vector3f( Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
				for(int j = 0; j < 3; j++)
					bone.orientation_order[j] = AcclaimData.getAxisFromString(tokens[4].substring(j, j+1));
					
				last_label = AsfParser.BONE_AXIS_LABEL;
			}
			else if(tokens[0].equals(AsfParser.BONE_DOF_LABEL) &&
					tokens.length > 1)
			{
				for(int j = 1; j < tokens.length; j++)
					bone.dof.add(AcclaimData.getOperationOnAxisFromString(tokens[j]));
				last_label = AsfParser.BONE_DOF_LABEL;
			}
			else if( (tokens[0].equals(AsfParser.BONE_LIMITS_LABEL) &&
					tokens.length > 1) || last_label.equals(AsfParser.BONE_LIMITS_LABEL))
			{
				line = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				tokens = line.trim().split(Parser.TOKEN_DELIMITER);
				ArrayList<Float> values = new ArrayList<Float>();
				
				for(int j = 0; j < tokens.length; j++)
					values.add(Float.valueOf(tokens[j]));
				
				bone.limits.add(values);
				last_label = AsfParser.BONE_LIMITS_LABEL;
			}
		}
		
		logger.info("AsfParser.parseBone(): Parsed bone => \'{}\'.", bone.name);
		logger.debug("AsfParser.parseBone(): Exiting method.");
		return bone;
	}
	
	public static HashMap<String, List<String>> parseHierarchy(HashMap<String, List<String>> sections) throws ParseException
	{
		logger.debug("AsfParser.parseHierarchy(): Entering method.");
		
		List<String> lines = sections.get(AsfParser.HIERARCHY_KEYWORD);
		HashMap<String, List<String>> hierarchy = new HashMap<String, List<String>>();
		String line;
		String[] tokens;
		
		for(int i = 0; i < lines.size(); i++)
		{
			line = lines.get(i);
			tokens = line.split(Parser.TOKEN_DELIMITER);
			
			if(tokens[0].equals(AsfParser.START_LABEL))
				continue;
			else if(tokens[0].equals(AsfParser.END_LABEL))
				break;
			
			if(tokens.length > 0)
			{
				ArrayList<String> values = new ArrayList<String>();
				for(int j = 1; j < tokens.length; j++)
				{
					logger.info("AsfParser.parseHierarchy(): Link => \'{}\' has \'{}\'", tokens[0], tokens[j]);
					values.add(tokens[j]);
				}
				hierarchy.put(tokens[0], values);
			}
			else
				throw new ParseException("The Asf hierarchy section was not formatting properly.", 0);
		}
		
		logger.debug("AsfParser.parseHierarchy(): Exiting method.");
		return hierarchy;
	}
}
