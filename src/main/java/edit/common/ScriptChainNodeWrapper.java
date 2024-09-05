/*
 * ScriptChainNodeWrapper.java      Version 1.00  11/19/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Systems Engineering Group, LLC ("Confidential Information").
 */

package edit.common;

//import engine.dm.*;
import edit.common.vo.ScriptLineVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScriptChainNodeWrapper {

	//Used to store scriptName
	private String nodeDescription;
	
	//Used to store unique ID for script in scriptChain 
	private int nodeId = 0;

	//Used to hold the name of script from which different scripts are called 
	private ScriptChainNodeWrapper parent;

	//Used to store Called scripts
	private List children = new ArrayList();

	//Used to set that script has more call instructions
	private boolean hasChildren;

	private long scriptId;
	
	private ScriptLineVO[] scriptLines;

	//default constructor
	public ScriptChainNodeWrapper()
	{
	}
	
	public String getNodeDescription()
	{
		return nodeDescription;
	}

	public void setNodeDescription(String newValue)
	{
		nodeDescription = newValue;
	}

	public int getNodeId()
	{
		return nodeId;
	}	

	public void setNodeId(int newValue)
	{
		nodeId = newValue;
	}

	public List getChildren()
	{
		return children;
	}

	public void addChild(ScriptChainNodeWrapper child)
	{
		children.add(child);
		child.parent = this;
	}
	
	public ScriptChainNodeWrapper getParent()
	{
		return parent;
	}

	public void setParent(ScriptChainNodeWrapper newValue)
	{
		parent = newValue;
	}

	public void deleteAllChildren()
	{
		children.clear();
	}

	public void deleteChildAt(int index)
	{
		children.remove(index);
	}	

	public Iterator children()
	{
		return children.iterator();
	}

	public ScriptChainNodeWrapper getChildAt(int index)
	{
		return (ScriptChainNodeWrapper)children.get(index);
	}

	public void insertChildAt(int index, ScriptChainNodeWrapper child)
	{
		children.add(index, child);
		child.parent = this;
	}

	public int indexOfChild(ScriptChainNodeWrapper child)
	{
		return children.indexOf(child);
	}

	public void removeChild(int index)
	{
		children.remove(index);
	}

	public void setChildAt(int index, ScriptChainNodeWrapper child)
	{
		children.add(index, child);
		child.parent = this;
	}

//	public void setHasChildren(boolean newValue)
//	{
//		hasChildren = newValue;
//	}

	public boolean hasChildren()
	{
		return (children.size() > 0);
	}

	public int childCount()
	{
		return children.size();
	}

	public void setScriptId(long newValue)
	{
		scriptId = newValue;
	}

	public long getScriptId()
	{
		return scriptId;
	}
		
	public ScriptLineVO[] getScriptLines()
	{
		return scriptLines;
	}
	
	public void setScriptLines(ScriptLineVO[] newValue)
	{
		scriptLines = newValue;
	}
}