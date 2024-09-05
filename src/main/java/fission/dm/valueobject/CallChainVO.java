package fission.dm.valueobject;

import edit.common.ScriptChainNodeWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import engine.sp.*;

public class CallChainVO extends ValueObject {

	private ScriptChainNodeWrapper node;
	private List rows = new ArrayList();
	private int rowSize;
//	private List row;
		
	public CallChainVO(ScriptChainNodeWrapper rootNode) {
	
		this.node = rootNode;
		printNode(node);
		
	}
	
	private static int rowNumber = -1;
	private static int colNumber = -1;
	private static Map nextCol = new HashMap();

	public void printNode(ScriptChainNodeWrapper node) {

		rowNumber++;
	
		Integer colObj = (Integer) nextCol.get("nextCol:" + rowNumber);
		
		if (colObj == null) {
		
			nextCol.put("nextCol:" + rowNumber, new Integer(1)); 	
			
			colNumber = 0;
		}
		else {
		
			colNumber = colObj.intValue();
			
			nextCol.put("nextCol:" + rowNumber, new Integer(colNumber + 1));		
		}
	
		String space = "";
		
		for (int i = 0; i < rowNumber; i++) {
		
			space += "  ";
		}

		ScriptChainNodeWrapper parent = node.getParent();
		String parentName = "";
		if (parent ==  null)
		{
			parentName = "RootNode";
			List row = new ArrayList();
/*
			Scriptroot = new ScriptChainNodeWrapper();
			root.setNodeId(++nodeCount);
			root.setScriptId(scriptId);
			root.setNodeDescription(scriptName);
			root.setParent(null);
			root.setScriptLines(scriptLineVO);*/
							 
		
//			Map s = new HashMap();
//			s.put("NodeName",node.getNodeDescription());
//			s.put("NodeID",(node.getNodeId())+"");
//			s.put("Parent",parentName);
//			row.add(s);
			row.add(node);
			rows.add(row);

		}
		else
		{
			parentName = parent.getNodeDescription();
		}
		
//		System.out.println(space + node.getNodeId()+" "+node.getNodeDescription() + "Parent is = "+parentName +" \t\t" + rowNumber + "," + colNumber);	
//		
//		ScriptLineVO[] scriptLines = node.getScriptLines();
//		System.out.println("ScriptLines are:  ");
//		for(int i=0; i<scriptLines.length; ++i)
//		{
//			System.out.println(i+"  "+scriptLines[i].getScriptLine());
//		}

		if (node.hasChildren()) {
		
			List children = node.getChildren();
			
			for (int i = 0; i < children.size(); i++) {
				
				ScriptChainNodeWrapper child = (ScriptChainNodeWrapper) children.get(i);
				int size = rows.size();
				//when first element in row
				if ((size >= rowNumber) && ((i+"").equals("0"))  )
				{
					List row;
					try
					{
						 row = (List)rows.get(rowNumber+1);
					}catch(Exception e)
					{
						 row = new ArrayList();
					}
					//List row = new ArrayList();
//					Map s = new HashMap();
//					s.put("NodeName",child.getNodeDescription());
//					s.put("NodeID",(child.getNodeId())+"");
//					s.put("Parent",((ScriptChainNodeWrapper)child.getParent()).getNodeDescription());
//					row.add(s);
					row.add(child);
					if ((row.size()) == 1)
					{
						rows.add(row);
					}else
					{
					}
				}
				else if ((size >= rowNumber) && !((i+"").equals("0")) )  //other elements in same row
				{	
					List row = (List)rows.get(rowNumber+1);
//					Map s = new HashMap();
//					s.put("NodeName",child.getNodeDescription());
//					s.put("NodeID",(child.getNodeId())+"");
//					s.put("Parent",((ScriptChainNodeWrapper)child.getParent()).getNodeDescription());
//					row.add(s);
					
					row.add(child);
				}
				else
				{}
				printNode(child);				
			}		
		}
		
		rowNumber--;	
	}

	public List getRow(int rowNumber)
	{
		return (List)rows.get(rowNumber);
	}

	public int getRowSize(int rowNumber)
	{
		return ((List)rows.get(rowNumber)).size();
	}

	public List getRows()
	{
		return rows;
	}

	public int getRowCount()
	{
		
		return rows.size();
	}
}