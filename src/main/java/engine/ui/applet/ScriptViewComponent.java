package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ScriptViewComponent extends JPanel implements
												MouseListener, ActionListener, PropertyChangeListener {

	// -------------------------------------------
	//            Class / Instance Variables
	// -------------------------------------------
	
	private JPanel    	southPanel;
	
	private String      clipArea;
	
	private JList		scriptViewArea;
	private JButton   	deleteButton;
	private JButton		cutButton;
	private JButton		copyButton;
	private JButton		pasteButton;
	private JButton		undoButton;
	private JButton		findButton;
	private JButton		upButton;
	private JButton		downButton;	
	private String[]    scriptNames;
	
	private ScriptViewComponentModel scriptViewComponentModel;
 	private EnhancedEditApplet enhancedApplet;	
	private URL baseURL;
		
	// -------------------------------------------
	//                 Constructor
	// -------------------------------------------	
	public ScriptViewComponent(URL baseURL, EnhancedEditApplet enhancedApplet) {
		
		this.baseURL = baseURL;
		this.enhancedApplet = enhancedApplet;
		
		initVariables();
		setupLayout();		
	}	


	
	//---------------------------------------------- 
	//        		Public Methods
	// ---------------------------------------------
	public void addActionListener(ActionListener al) {

		deleteButton.addActionListener(al);
		cutButton.addActionListener(al);
		copyButton.addActionListener(al);
		pasteButton.addActionListener(al);
		undoButton.addActionListener(al);
		findButton.addActionListener(al);
		upButton.addActionListener(al);
		downButton.addActionListener(al);
	}
	
	
	public void addItem(String item) {
	
		scriptViewComponentModel.addItem(item);	
	}
	
	public void setModelData(List modelData) {
	
		scriptViewComponentModel.setModelData(modelData);	
	}
	
	public String getModelDataAsText() {
	
		return scriptViewComponentModel.getModelDataAsText();
	}
	
	public List getModelData() {
	
		return scriptViewComponentModel.getModelData();
	}
	
	public boolean modelHasChanged() {
	
		return scriptViewComponentModel.modelHasChanged();
	}
	
	public void setModelHasChanged(boolean bool) {
	
		scriptViewComponentModel.setModelHasChanged(bool);
	}


	
	//----------------------------------------------
	//        		Private Methods
	// ---------------------------------------------
	private void initVariables() {
	
		scriptViewArea		= new JList();

		southPanel 			= new JPanel();
		
		clipArea			= new String("");
		
		deleteButton		= new JButton("");			
		cutButton			= new JButton("");
		copyButton			= new JButton("");
		pasteButton			= new JButton("");
		undoButton			= new JButton("");
		findButton			= new JButton("");
		upButton			= new JButton("");
		downButton			= new JButton("");	
		    		
		scriptViewComponentModel = new ScriptViewComponentModel();
		
	}
	

	private void setupLayout() {
	
		this.setLayout(new BorderLayout());
		
		
		// i-candy changes			
					
		this.add(new JScrollPane(scriptViewArea,
								 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
								 JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), 
							 	 BorderLayout.CENTER);
			 
		try { 
						
    		deleteButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/delete.gif")));
			deleteButton.setToolTipText("delete");
		
			cutButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/cut.gif")));
			cutButton.setToolTipText("cut");
		
			copyButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/copy.gif")));
			copyButton.setToolTipText("copy");
		
			pasteButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/paste.gif")));
			pasteButton.setToolTipText("paste");
		
			undoButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/undo.gif")));
			undoButton.setToolTipText("undo");
		
			findButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/find.gif")));
			findButton.setToolTipText("under construction");
		
			upButton.setIcon(new 
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/up.gif")));
			upButton.setToolTipText("line up");
		
			downButton.setIcon(new
			ImageIcon(new URL(baseURL, "PORTAL/engine/images/down.gif")));
			downButton.setToolTipText("line down");								
		}
		catch(Exception e) {
		
			System.out.println(e);
			e.printStackTrace();
		}

		southPanel.setLayout(new GridLayout(1,8));
		southPanel.add(deleteButton);		
		southPanel.add(cutButton);
		southPanel.add(copyButton);
		southPanel.add(pasteButton);
		southPanel.add(undoButton);
		southPanel.add(findButton);
		southPanel.add(upButton);
		southPanel.add(downButton);
		
		this.add(southPanel, BorderLayout.SOUTH);	

	
		// register listeners
		deleteButton.addActionListener(this);
		cutButton.addActionListener(this);
		copyButton.addActionListener(this);
		pasteButton.addActionListener(this);
		undoButton.addActionListener(this);
		findButton.addActionListener(this);
		upButton.addActionListener(this);
		downButton.addActionListener(this);
		scriptViewArea.addMouseListener(this);
				
		// register model with JList
		scriptViewArea.setModel(scriptViewComponentModel);
	}	
	// callback method for PropertyChangeListener
	public void propertyChange(PropertyChangeEvent e) {	
		
		scriptViewComponentModel.addItem((String) e.getNewValue());
	}
	
	//----------------------------------------------
	//        		Event Handlers
	// ---------------------------------------------		

	public void mouseClicked(MouseEvent e) {	
	
		if (e != null && e.getClickCount() == 2) {
		
			int first =0;
			int last  =0;
			
			//int index = scriptViewArea.getSelectedIndex();
						
			JList list = (JList) e.getSource();
						
			String listVal = list.getSelectedValue().toString().trim();
			String indexString = listVal;
			
			if (listVal.toLowerCase().indexOf("call", 0) >= 0) {
								
				first = indexString.indexOf("c");
				last  = indexString.indexOf(" ");
			} 	
			else if (listVal.toLowerCase().indexOf("push", 0) >= 0) {
				
				first = indexString.indexOf("p");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("pop", 0) >= 0) {
				
				first = indexString.indexOf("p");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("peek", 0) >= 0) {
				
				first = indexString.indexOf("p");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("output", 0) >= 0) {
				
				first = indexString.indexOf("o");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifeq", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifeqtol", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whileeq", 0) >= 0) {
						
				first = indexString.indexOf("w");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whilege", 0) >= 0) {
						
				first = indexString.indexOf("w");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifge", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifgt", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whilegt", 0) >= 0) {
						
				first = indexString.indexOf("w");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifle", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whilele", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("iflt", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whilelt", 0) >= 0) {
						
				first = indexString.indexOf("w");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifne", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("ifnetol", 0) >= 0) {
						
				first = indexString.indexOf("i");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whilene", 0) >= 0) {
						
				first = indexString.indexOf("w");
				last  = indexString.indexOf(" ");
			}
			else if (listVal.toLowerCase().indexOf("whilenetol", 0) >= 0) {
						
				first = indexString.indexOf("w");
				last  = indexString.indexOf(" ");
			}
			listVal = listVal.substring(first, last);
											
			if (e.getSource() == scriptViewArea) {
				if ( (listVal.equalsIgnoreCase("ifeq"))
						|| (listVal.equalsIgnoreCase("ifeqtol"))
						|| (listVal.equalsIgnoreCase("whileeq"))
						|| (listVal.equalsIgnoreCase("ifge"))
						|| (listVal.equalsIgnoreCase("whilege"))
						|| (listVal.equalsIgnoreCase("ifgt"))
						|| (listVal.equalsIgnoreCase("whilegt"))
						|| (listVal.equalsIgnoreCase("ifle"))
						|| (listVal.equalsIgnoreCase("whilele"))
						|| (listVal.equalsIgnoreCase("iflt"))
						|| (listVal.equalsIgnoreCase("whilelt"))
						|| (listVal.equalsIgnoreCase("ifne"))
						|| (listVal.equalsIgnoreCase("ifnetol"))
						|| (listVal.equalsIgnoreCase("whilene"))
						|| (listVal.equalsIgnoreCase("whilenetol"))
						|| (listVal.equalsIgnoreCase("call")) )  {
				
					
					Object frame = enhancedApplet.getParent();
					while ( !( frame instanceof Frame) )  
						frame = ( (Component) frame).getParent();
																				
					scriptNames = enhancedApplet.getScriptNames();
					ScriptListDialog sld = new ScriptListDialog(scriptNames, listVal, (Frame) frame);
						
					sld.setSize(379,175);
					sld.setVisible(true);
					if (sld.cancelSelected() == false) {
					
						scriptViewComponentModel.insertItem(sld.getScriptLine());	
						scriptViewArea.updateUI();
					}
				return;
										
				}
			   		else if ( (listVal.equalsIgnoreCase("push"))
						|| (listVal.equalsIgnoreCase("pop"))
						|| (listVal.equalsIgnoreCase("peek"))
						|| (listVal.equalsIgnoreCase("output")) ) {
				
					Object frame = enhancedApplet.getParent();
					while ( !( frame instanceof Frame) ) 
						frame = ( (Component) frame).getParent();
									
					scriptNames = enhancedApplet.getScriptNames();
				   	FreeFormTextDialog fftd = new FreeFormTextDialog(indexString, (Frame) frame);
					fftd.setSize(425,200);
					fftd.setVisible(true);
					if (fftd.cancelSelected() == false) {
					
						scriptViewComponentModel.insertItem(fftd.getCommentLine());	
						scriptViewArea.updateUI();
					}
				return;
					
				}
			}
		}
	}
		
	public void mouseEntered(MouseEvent e) {	
	}
	
	public void mouseExited(MouseEvent e)  {
	}
	
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
	
		Object ref = e.getSource();

		
		if (ref == deleteButton)  {

			scriptViewComponentModel.deleteItem(scriptViewArea.getSelectedIndex());
		}
		
		else if (ref == cutButton)  {

			if (scriptViewArea.getSelectedIndex() >= 0) {
			
				scriptViewComponentModel.cutItem(scriptViewArea.getSelectedIndex());
			}
		}
		
		else if (ref == copyButton) {
		
			scriptViewComponentModel.copyItem(scriptViewArea.getSelectedIndex());
			}
		
		else if (ref == pasteButton) {
			
			scriptViewComponentModel.pasteItem(scriptViewArea.getSelectedIndex());
		}
		
		else if (ref == undoButton) {

			scriptViewComponentModel.undo();
		}
		else if (ref == upButton) {
		
			scriptViewComponentModel.goUp(scriptViewArea.getSelectedIndex());
		}
		else if (ref == downButton) {
		
			scriptViewComponentModel.goDown(scriptViewArea.getSelectedIndex());
		}
	}	
	
	private class ScriptViewComponentModel extends AbstractListModel {
	
		private List modelList;
		private List undoList;
		private boolean modelHasChanged = false;
				
		private String clipArea;
		
		public ScriptViewComponentModel() {
		
			modelList = new ArrayList();
			undoList  = new ArrayList();
		}
		
		
		//---------------------------------------------- 
		//        		Methods of ListModel
		// ---------------------------------------------
		
		public int getSize() {
			
			return modelList.size();
		}
		
		public Object getElementAt(int index) {
		
			return modelList.get(index);
		}
		
		//---------------------------------------------- 
		//        		Public Methods
		// ---------------------------------------------
		
		public void setModelData(List modelData) {
		
			modelHasChanged = false;
			modelList = modelData;
			
			scriptViewArea.updateUI();
			
			if (modelData.size() > 0) {
			
				scriptViewArea.setSelectedIndex(0);
			}
						
		}
		
		public boolean modelHasChanged() {
		
			return modelHasChanged;
		}
		
		public void setModelHasChanged(boolean bool) {
		
			modelHasChanged = bool;
		}
		
		public String getModelDataAsText() {
		
			StringBuffer buffer = new StringBuffer();
		
			for (int i = 0; i < modelList.size(); i++) {
			
				buffer.append(modelList.get(i).toString());
				
				if (i != (modelList.size() - 1)){
				
					buffer.append("\n");
				}
			}
			
			return buffer.toString();		
		}
		
		public List getModelData() {
		
			return modelList;
		}
		
		public void addItem(String item) {
			
			modelHasChanged = true;
			scriptViewArea.setAutoscrolls(false);		
			undoList = cloneModel(modelList, undoList);
						
			int counter = scriptViewArea.getSelectedIndex();
			if (counter == -1) {
			
				counter = 0;
			}
			
			modelList.add(counter, item);
					
			counter = scriptViewArea.getSelectedIndex();
			
			scriptViewArea.updateUI();
									
			scriptViewArea.setSelectedIndex(counter + 1);
								
			scriptViewArea.ensureIndexIsVisible(counter);
							
			scriptViewArea.updateUI();
			
		}
		
		//Method used only for dialog boxes
		public void insertItem(String item) {
					
			modelHasChanged = true;
			int counter = scriptViewArea.getSelectedIndex();
			if (counter == -1) {
			
				counter = 0;
			}
			
			modelList.add(counter, item);
		
			scriptViewArea.updateUI();
		}
		
		public void deleteItem(int index) {
		
			modelHasChanged = true;
			undoList = cloneModel(modelList, undoList);
			
			if (index >= 0) {
			
				modelList.remove(index);			
							
				scriptViewArea.updateUI();
			}
		}
		
		public void cutItem(int index) {
		
			modelHasChanged = true;
			undoList = cloneModel(modelList, undoList);
					
			clipArea = (String) modelList.get(index);
			
			deleteItem(index);
		}
		
		public void copyItem(int index) {
		
			clipArea = (String) modelList.get(index);
		}
		
		public void pasteItem(int index) {
		
			modelHasChanged = true;
			undoList = cloneModel(modelList, undoList);
			
			modelList.add(index, clipArea);
			
			scriptViewArea.updateUI();			
		}
		
		public void undo() {
		
			modelHasChanged = true;
			modelList = cloneModel(undoList, modelList);
			
			scriptViewArea.updateUI();		
		}
		
		public void goUp(int index) {
								
			if (index != 0) {
			
				modelHasChanged = true;
			
				undoList = cloneModel(modelList, undoList);					
			
				String item = scriptViewArea.getSelectedValue().toString();
			
				deleteItem(index);
			
				modelList.add(index - 1, item);
				
				scriptViewArea.setSelectedIndex(index - 1);
			
				scriptViewArea.updateUI();
			}			
		}
		
		public void goDown(int index) {
					
			if (index != (modelList.size() - 1)) {
			
				modelHasChanged = true;
			
				undoList = cloneModel(modelList, undoList);					
			
				String item = scriptViewArea.getSelectedValue().toString();
			
				deleteItem(index);
			
				modelList.add(index + 1, item);
				
				scriptViewArea.setSelectedIndex(index + 1);				
			
				scriptViewArea.updateUI();
			}
		}
		
		private List cloneModel(List orig, List clone) {
		
			List origList  = orig;
			List cloneList = clone;
			
			synchronized(this) {
				
				cloneList = (List) ((ArrayList) origList).clone();
			}
			
			return cloneList;
		}
	}
	
//	public static void main(String[] args) {
//	
//		JFrame frame = new JFrame();
//		
//		frame.getContentPane().add(new ScriptViewComponent(null));
//	
//		frame.setSize(300,300);
//		frame.setVisible(true);	
//	}
}