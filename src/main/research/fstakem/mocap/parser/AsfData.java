package main.research.fstakem.mocap.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsfData extends AcclaimData
{
	// Constants
	
	// Variables
	public String version;
	public String name;
	public Map<String, String> units;
	public List<String> documentation;
	public AcclaimRoot root;
	public List<AcclaimBone> bones;
	public Map<String, List<String>> hierarchy;
	
	public AsfData()
	{
		this.version = "";
		this.name = "";
		this.units = new HashMap<String, String>();
		this.documentation = new ArrayList<String>();
		this.root = new AcclaimRoot();
		this.bones = new ArrayList<AcclaimBone>();
		this.hierarchy = new HashMap<String, List<String>>();
	}
}
