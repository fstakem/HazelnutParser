package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector3f;
import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AsfParser;
import main.research.fstakem.mocap.scene.CharacterElement.Axis;
import main.research.fstakem.mocap.scene.CharacterElement.Dof;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsfMapper 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AsfMapper.class);
		
	public static RootElement createCharacterElementHierarchy(HashMap<String, ArrayList<String>> hierarchy) throws Exception
	{
		logger.debug("AsfMapper.createCharacterElementHierarchy(): Entering method.");
		
		logger.info("AsfMapper.createCharacterElementHierarchy(): Creating root element.");
		RootElement root = new RootElement();
		root.setChildren( AsfMapper.createChildBones(root, hierarchy) );
		
		logger.debug("AsfMapper.createCharacterElementHierarchy(): Exiting method.");
		return root;
	}
	
	public static void addDetailsToRoot(RootElement root, HashMap<String, ArrayList<String>> acclaim_root) throws Exception
	{
		logger.debug("AsfMapper.addDetailsToRoot(): Entering method.");
		
		String label;
		ArrayList<String> values;
		
		for(Map.Entry<String, ArrayList<String>> entry : acclaim_root.entrySet())
		{
			label = entry.getKey();
			values = entry.getValue();
			
			if(label.equals(AsfParser.BONE_ORDER_LABEL))
				root.setOrder(AsfMapper.getRootOrder(values));
			else if(label.equals(AsfParser.BONE_AXIS_LABEL))
				root.setAxis(AsfMapper.getRootAxis(values));
			else if(label.equals(AsfParser.BONE_POSITION_LABEL))
			{
				float[] out = stringToFloatArray(values);
				root.setStartPosition(new Vector3f(out[0], out[1], out[2]));
			}
			else if(label.equals(AsfParser.BONE_ORIENTATION_LABEL))
			{
				float[] out = stringToFloatArray(values);
				root.setOrientation(new Vector3f(out[0], out[1], out[2]));
			}
		}
		
		logger.debug("AsfMapper.addDetailsToRoot(): Exiting method.");
	}
	
	public static void addDetailsToBones(RootElement root, ArrayList<AcclaimBone> acclaim_bones) throws Exception
	{
		logger.debug("AsfMapper.addDetailsToBones(): Entering method.");
		
		for(int i = 0; i < acclaim_bones.size(); i++)
		{
			AcclaimBone acclaim_bone = acclaim_bones.get(i);
			Bone bone = (Bone) root.findCharacterElement(acclaim_bone.name);
			
			if(bone == null)
			{
				throw new Exception("Could not find " + acclaim_bone.name + ". ");
			}
			
			logger.info("AsfMapper.addDetailsToBones(): Adding details to {}.", bone.getName());
			
			// Id
			bone.setId(acclaim_bone.id);
			
			// Orientation
			ArrayList<Float> d = acclaim_bone.direction;
			bone.setOrientation(new Vector3f(d.get(0).floatValue(), 
											 d.get(1).floatValue(), 
											 d.get(2).floatValue()));
			
			// Length
			bone.setLength(acclaim_bone.length);
			
			// Axis
			bone.setAxis(AsfMapper.getBoneAxis(acclaim_bone));
			
			// Dof
			bone.setDof(AsfMapper.getBoneDof(acclaim_bone));
			
			// Limits
			bone.setLimits(AsfMapper.getBoneLimits(acclaim_bone));
		}
		
		logger.debug("AsfMapper.addDetailsToBones(): Exiting method.");
	}
	
	private static ArrayList<CharacterElement> createChildBones(CharacterElement parent, HashMap<String, ArrayList<String>> hierarchy)
	{
		logger.debug("AsfMapper.createChildBones(): Entering recursive method with parent => \'{}\'.", parent.getName());
		
		ArrayList<CharacterElement> child_bones = new ArrayList<CharacterElement>();
		ArrayList<String> child_names_of_bones = hierarchy.get(parent.getName());
		
		if(child_names_of_bones != null)
		{
			for(String bone_name : child_names_of_bones)
			{
				logger.info("AsfMapper.createChildBones(): Creating bone \'{}\' connected to parent \'{}\' in the hierarchy.", bone_name, parent.getName());
				Bone bone = new Bone(bone_name);
				ArrayList<CharacterElement> children = AsfMapper.createChildBones(bone, hierarchy);
				
				if(children.size() > 0)
					bone.addChildren(children);
				child_bones.add(bone);
			}
		}
		
		logger.debug("AsfMapper.createChildBones(): Exiting recursive method with parent => \'{}\'.", parent.getName());
		return child_bones;
	}
	
	private static ArrayList<CharacterElement.Dof> getRootOrder(ArrayList<String> values)
	{
		logger.debug("AsfMapper.getRootOrder(): Entering method.");
		
		ArrayList<CharacterElement.Dof> order = new ArrayList<CharacterElement.Dof>();
		for(int i = 0; i < values.size(); i++)
			order.add( CharacterElement.getDofValueFromString(values.get(i)) );
		
		logger.debug("AsfMapper.getRootOrder(): Exiting method.");
		return order;
	}
	
	private static ArrayList<Axis> getRootAxis(ArrayList<String> values)
	{
		logger.debug("AsfMapper.getRootAxis(): Entering method.");
		
		String out = values.get(0);
		ArrayList<Axis> axis = new ArrayList<Axis>();
		for(int i = 0; i < values.size(); i++)
			axis.add( CharacterElement.getAxisValueFromString(out.substring(i, i+1)) );
		
		logger.debug("AsfMapper.getRootAxis(): Exiting method.");
		return axis;	
	}
	
	private static ArrayList<Float> getBoneAxis(AcclaimBone acclaim_bone)
	{
		logger.debug("AsfMapper.getBoneAxis(): Entering method.");
		
		ArrayList<Float> axis = new ArrayList<Float>();
		for(int j = 0; j < acclaim_bone.axis.size(); j++)
			axis.add( acclaim_bone.axis.get(j) );
		
		logger.debug("AsfMapper.getBoneAxis(): Exiting method.");
		return axis;
	}
	
	private static ArrayList<Dof> getBoneDof(AcclaimBone acclaim_bone)
	{
		logger.debug("AsfMapper.getBoneDof(): Entering method.");
		
		ArrayList<Dof> dof = new ArrayList<Dof>();
		for(int j = 0; j < acclaim_bone.dof.size(); j++)
			dof.add( CharacterElement.getDofValueFromString(acclaim_bone.dof.get(j)) );
		
		logger.debug("AsfMapper.getBoneDof(): Exiting method.");
		return dof;
	}
	
	private static ArrayList<ArrayList<Float>> getBoneLimits(AcclaimBone acclaim_bone)
	{
		logger.debug("AsfMapper.getBoneLimits(): Entering method.");
		
		ArrayList<ArrayList<Float>> limits = new ArrayList<ArrayList<Float>>();
		for(int j = 0; j < acclaim_bone.limits.size(); j++)
		{
			ArrayList<Float> limits_tuple = new ArrayList<Float>();
			for(int k = 0; k < acclaim_bone.limits.get(j).size(); k++)
				limits_tuple.add( acclaim_bone.limits.get(j).get(k).floatValue() );
			limits.add(limits_tuple);
		}
		
		logger.debug("AsfMapper.getBoneLimits(): Exiting method.");
		return limits;
	}
		
	private static float[] stringToFloatArray(ArrayList<String> values)
	{
		logger.debug("AsfMapper.stringToFloatArray(): Entering method.");
		
		float[] output = new float[values.size()];
		for(int i = 0; i < values.size(); i++)
			output[i] = Float.valueOf(values.get(i));
		
		logger.debug("AsfMapper.stringToFloatArray(): Exiting method.");
		return output;
	}
}
