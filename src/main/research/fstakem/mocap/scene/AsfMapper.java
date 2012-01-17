package main.research.fstakem.mocap.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3f;
import main.research.fstakem.mocap.parser.AcclaimBone;
import main.research.fstakem.mocap.parser.AcclaimRoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsfMapper 
{
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AsfMapper.class);
		
	public static RootElement createCharacterElementHierarchy(Map<String, List<String>> hierarchy) throws Exception
	{
		logger.debug("AsfMapper.createCharacterElementHierarchy(): Entering method.");
		
		logger.info("AsfMapper.createCharacterElementHierarchy(): Creating root element.");
		RootElement root = new RootElement();
		root.setChildren( AsfMapper.createChildBones(root, hierarchy) );
		
		logger.debug("AsfMapper.createCharacterElementHierarchy(): Exiting method.");
		return root;
	}
	
	public static void addDetailsToRoot(RootElement root, AcclaimRoot acclaim_root) throws Exception
	{
		logger.debug("AsfMapper.addDetailsToRoot(): Entering method.");
		
		root.setStartPosition(new Vector3f(acclaim_root.position));
		root.setOrientation(new Vector3f(acclaim_root.orientation));
		root.setAmcDataOrder(acclaim_root.amc_data_order);
		root.setOrientationOrder(acclaim_root.orientation_order);
		
		logger.debug("AsfMapper.addDetailsToRoot(): Exiting method.");
	}
	
	public static void addDetailsToBones(RootElement root, List<AcclaimBone> acclaim_bones) throws Exception
	{
		logger.debug("AsfMapper.addDetailsToBones(): Entering method.");
		
		for(int i = 0; i < acclaim_bones.size(); i++)
		{
			AcclaimBone acclaim_bone = acclaim_bones.get(i);
			Bone bone = (Bone) root.findCharacterElement(acclaim_bone.name);
			
			if(bone == null)
				throw new Exception("Could not find " + acclaim_bone.name + ". ");
			
			logger.info("AsfMapper.addDetailsToBones(): Adding details to {}.", bone.getName());
			
			bone.setId(acclaim_bone.id);
			bone.setDirection(new Vector3f(acclaim_bone.direction));
			bone.setLength(acclaim_bone.length);
			bone.setOrientation(new Vector3f(acclaim_bone.orientation));
			bone.setOrientationOrder(acclaim_bone.orientation_order);
			bone.setDof(acclaim_bone.dof);
			bone.setLimits(acclaim_bone.limits);
		}
		
		logger.debug("AsfMapper.addDetailsToBones(): Exiting method.");
	}
	
	private static List<CharacterElement> createChildBones(CharacterElement parent, Map<String, List<String>> hierarchy)
	{
		logger.debug("AsfMapper.createChildBones(): Entering recursive method with parent => \'{}\'.", parent.getName());
		
		List<CharacterElement> child_bones = new ArrayList<CharacterElement>();
		List<String> child_names_of_bones = hierarchy.get(parent.getName());
		
		if(child_names_of_bones != null)
		{
			for(String bone_name : child_names_of_bones)
			{
				logger.info("AsfMapper.createChildBones(): Creating bone \'{}\' connected to parent \'{}\' in the hierarchy.", bone_name, parent.getName());
				Bone bone = new Bone(bone_name);
				List<CharacterElement> children = AsfMapper.createChildBones(bone, hierarchy);
				
				if(children.size() > 0)
					bone.addChildren(children);
				child_bones.add(bone);
			}
		}
		
		logger.debug("AsfMapper.createChildBones(): Exiting recursive method with parent => \'{}\'.", parent.getName());
		return child_bones;
	}
}
