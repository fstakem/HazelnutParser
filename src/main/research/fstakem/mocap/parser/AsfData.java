package main.research.fstakem.mocap.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class AsfData 
{
	// Constants
	
	// Variables
	public String version;
	public String name;
	public HashMap<String, String> units;
	public HashMap<String, ArrayList<String>> root;
	public ArrayList<AcclaimBone> bones;
	public HashMap<String, ArrayList<String>> hierarchy;
	
	public AsfData()
	{
		this.version = "";
		this.name = "";
		this.units = new HashMap<String, String>();
		this.root = new HashMap<String, ArrayList<String>>();
		this.bones = new ArrayList<AcclaimBone>();
		this.hierarchy = new HashMap<String, ArrayList<String>>();
	}
}
