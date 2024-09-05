package engine.ui.applet;

import com.oreilly.servlet.HttpMessage;
import fission.beans.DataResultBean;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class EnhancedEditApplet extends JApplet implements MouseListener, ActionListener {

	private JPanel topSPPanel;
	private JPanel mainSPPanel;
	private JPanel bottomAppletPanel;

	private JTabbedPane tabbedPane;

	private JSplitPane splitPane;

	private JTree instructionTree;
	private JTree scriptTree;
    private JTree scriptRuleTree;
	private JTree tableTree;
    private JTree tableRuleTree;

	//information to send with the request
	private String scriptName;
	private String scriptId;

	private String action;
	private String transaction;

	private String cancelPage;

	private JTree topJTree;

	// components in bottomAppletPanel
	private JPanel 	mainPanelSouthApplied;
	private JButton applyButton;
	private JButton saveButton;
	private JButton cancelButton;
    private JButton deleteButton;
	private JLabel  bottomLabel;

	// two components to be placed in mainSPPanel
	// they can be any component
	private ScriptViewComponent scriptViewComponent;
	private CalcComponent calculatorComponent;

	private PropertyChangeSupport propChangeSupport;

	private String servletURL;

	//Helpful Constants
	//private static final String ACTION_CANCEL = "actionCancel";
	//private static final String ACTION_APPLY = "actionApply";
	private static final String ACTION_SAVE = "actionSave";
    private static final String ACTION_DELETE = "actionDelete";
	private static final String ACTION_SHOWENHANCEDNEW = "showEnhancedScriptNew";
	private static final String ACTION_SHOWENHANCEDMOD = "showEnhancedScriptMod";
	private static final String ACTION_SHOW_DEFAULT = "showEnhancedScriptDefault";
    private static final String SCRIPT_LC = "script";
    private static final String SCRIPT_ID_LC = "scriptId";

	private 	int 	port;
	private 	URL 	baseURL;
	private 	String 	host;
	String value = "";
	String[] scriptNames;
    String[] scriptRuleNames;
    String[] tableRuleNames;
	List scriptBeforeSave = new ArrayList();

	public EnhancedEditApplet() {

	}

	// -----------------------------------------------------
	// 						Public Methods
	// -----------------------------------------------------

	public void init() {

		initVariables();

		// always do this, whether in showEnhancedScriptMod or showEnhancedScriptNew mode
		setTreeModels();

		if (action.equalsIgnoreCase(ACTION_SHOWENHANCEDMOD)) {

			setJScriptViewComp();
		}
	}

	public void start() {

		setupLayout();
	}

	public void setScript(String transaction, String action, String scriptName, String scriptId) {

		setScript(transaction, action, scriptName, scriptId, null);
	}

	public void setScript(String transaction, String action, String scriptName, String scriptId, List scriptLines) {

		this.transaction  = transaction;
		this.action       = action;
		this.scriptName   = scriptName;
		this.scriptId     = scriptId;

		DataResultBean aDataBean = sendToServlet(transaction, action, scriptName, scriptId, null);

		if (scriptLines == null) {

			scriptLines = (List) aDataBean.getObjectBy(SCRIPT_LC);
		}

        if (scriptId == null) {

            this.scriptId = (String) aDataBean.getObjectBy(SCRIPT_ID_LC);
        }

		scriptViewComponent.setModelData(scriptLines);

		if (scriptName == null) {
			scriptName = "N/A";
		}

		setScriptName(scriptName);
	}

	public void mouseClicked(MouseEvent e) {

		if (e != null && e.getClickCount() == 2) {

			JTree tree = (JTree) e.getSource();

			String val = tree.getSelectionPath().getLastPathComponent().toString().trim();

			if (e.getSource() == instructionTree) {
				val = tree.getSelectionPath().getLastPathComponent().toString().toLowerCase().trim();
				if ( (val.equalsIgnoreCase("Push"))
			    	|| (val.equalsIgnoreCase("Pop"))
			    	|| (val.equalsIgnoreCase("Peek"))
                    || (val.equalsIgnoreCase("Output"))
                    || (val.equalsIgnoreCase("compare"))) {

					Object frame = getParent();
					while ( !( frame instanceof Frame) )
						frame = ( (Component) frame).getParent();

					SelectOperandDialog so = new SelectOperandDialog(val, (Frame) frame);
					so.setSize(375,300);
					so.setVisible(true);
					if (so.cancelSelected() == false) {

						scriptViewComponent.addItem(so.getScriptLine());
					}
					else if ( (val.equalsIgnoreCase("Output"))
			 			    || (val.equalsIgnoreCase("whileeq"))
                            || (val.equalsIgnoreCase("whilege"))
                            || (val.equalsIgnoreCase("whilegt"))
                            || (val.equalsIgnoreCase("whilele"))
			   			    || (val.equalsIgnoreCase("whilelt"))
                            || (val.equalsIgnoreCase("whilene"))
                            || (val.equalsIgnoreCase("whilenetol")) ) {
                        scriptViewComponent.addItem(so.getScriptLine());
					}
					return;
				}
				else if ( (val.equalsIgnoreCase("ifeq"))
						|| (val.equalsIgnoreCase("ifeqtol"))
						|| (val.equalsIgnoreCase("ifge"))
						|| (val.equalsIgnoreCase("ifgt"))
						|| (val.equalsIgnoreCase("ifle"))
						|| (val.equalsIgnoreCase("iflt"))
						|| (val.equalsIgnoreCase("ifne"))
						|| (val.equalsIgnoreCase("ifnetol"))
						|| (val.equalsIgnoreCase("call")) ) {

						Object frame = getParent();
						while ( !( frame instanceof Frame) )
							frame = ( (Component) frame).getParent();

						scriptNames = getScriptNames();
					    ScriptListDialog sld = new ScriptListDialog(scriptNames, val, (Frame) frame);
						sld.setSize(379,280);
						sld.setVisible(true);
						if ((sld.cancelSelected() == false) &&  (sld.continueProcess() == false)){

						    scriptViewComponent.addItem(sld.getScriptLine());
						}

					return;
				}
				else if ((val.equalsIgnoreCase("comment")) ||
                         (val.equalsIgnoreCase("name")) ||
                         (val.equalsIgnoreCase("variable")) ||
                         (val.equalsIgnoreCase("calc"))) {


					Object frame = getParent();
					while ( !( frame instanceof Frame) )
						frame = ( (Component) frame).getParent();

					FreeFormCommonTextDialog ffcd = new FreeFormCommonTextDialog(val, (Frame) frame);
					ffcd.setSize(350,180);
					ffcd.setVisible(true);
					scriptViewComponent.addItem(ffcd.getTextLine());
					return;
				}
                else if (val.equalsIgnoreCase("getvector") || val.equalsIgnoreCase("vector")) {


                    Object frame = getParent();
				    while ( !( frame instanceof Frame) )
					    frame = ( (Component) frame).getParent();

				    FreeFormVectorDialog ffvd = new FreeFormVectorDialog(val, (Frame) frame);
				    ffvd.setSize(375,280);
				    ffvd.setVisible(true);
			        scriptViewComponent.addItem(ffvd.getVector());
			        return;
                }
			    else if (val.equalsIgnoreCase("getscript")) {

                    Object frame = getParent();

                    while ( !( frame instanceof Frame) )
                        frame = ( (Component) frame).getParent();

                    scriptRuleNames = getScriptRuleNames();
                    ScriptRuleListDialog srld = new ScriptRuleListDialog(scriptRuleNames, val, (Frame) frame);
                    srld.setSize(379,175);
                    srld.setVisible(true);
                    if (srld.cancelSelected() == false) {

                        scriptViewComponent.addItem(srld.getScriptLine());
                    }
                    return;
                }
                else if ((val.equalsIgnoreCase("gettable")) ||
                         (val.equalsIgnoreCase("gettablename")) ||
                         (val.equalsIgnoreCase("pre"))) {

                    Object frame = getParent();

                    while (!(frame instanceof Frame))
                        frame = ( (Component) frame).getParent();

                    tableRuleNames = getTableRuleNames();
                    TableRuleListDialog trld = new TableRuleListDialog(tableRuleNames, val, (Frame) frame);
                    trld.setSize(379,280);
                    trld.setVisible(true);
                    if (trld.cancelSelected() == false) {

                        scriptViewComponent.addItem(trld.getScriptLine());
                    }
                    return;
			    }

			    else if (val.equalsIgnoreCase("counter")) {

                    Object frame = getParent();

                    while ( !( frame instanceof Frame) )
                        frame = ( (Component) frame).getParent();

                    CounterDialog counterld = new CounterDialog(val, (Frame) frame);
                    counterld.setSize(379,280);
                    counterld.setVisible(true);
                    if (counterld.cancelSelected() == false) {

                        scriptViewComponent.addItem(counterld.getCounterOption());
                    }
                    return;
                }
			    else if (val.startsWith("inst") ||
                        (val.equalsIgnoreCase("type"))) {

                    Object frame = getParent();

                    while ( !( frame instanceof Frame) )
                        frame = ( (Component) frame).getParent();

                    InstructionDialog instld = new InstructionDialog(val, (Frame) frame);
                    instld.setSize(379,280);
                    instld.setVisible(true);
                    if (instld.cancelSelected() == false) {

                        scriptViewComponent.addItem(instld.getInstructionOption());
                    }
                    return;
                }

                PropertyChangeEvent evt = new PropertyChangeEvent(tree, "IST_EVENT", null, val);

                propChangeSupport.firePropertyChange(evt);
            }

			else if (e.getSource() == scriptTree) {
				value = tree.getSelectionPath().getLastPathComponent().toString().trim();
				if (value.equalsIgnoreCase("<new>")) {

                    Object frame = getParent();
					while ( !( frame instanceof Frame) )
						frame = ( (Component) frame).getParent();

					FreeFormScriptDialog ffsd = new FreeFormScriptDialog(value, (Frame) frame);
					ffsd.setSize(350,180);
					ffsd.setVisible(true);
					if (ffsd.cancelSelected() == false) {

                        setScript(transaction, ACTION_SHOW_DEFAULT, ffsd.getScriptLine(), null);
					}
					return;
				}

                else {

                    setScript(transaction, ACTION_SHOWENHANCEDMOD, value, null);
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

	public void addPropertyChangeListener(PropertyChangeListener pcl) {

		propChangeSupport.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {

		propChangeSupport.removePropertyChangeListener(pcl);
	}

	public String[] getScriptNames() {

		List scripts = (List) ((ArrayList)scriptTree.getModel().getRoot()).clone();

		scripts.add(0, "Select Scripts");
		return (String[]) scripts.toArray(new String [scripts.size()]);
	}

    public String[] getScriptRuleNames() {

		List scriptRules = (List) ((ArrayList)scriptRuleTree.getModel().getRoot()).clone();

		scriptRules.add(0, "Select Rules");
		return (String[]) scriptRules.toArray(new String [scriptRules.size()]);
	}

    public String[] getTableRuleNames() {

		List tableRules = (List) ((ArrayList)tableRuleTree.getModel().getRoot()).clone();

		tableRules.add(0, "Select Rules");
		return (String[]) tableRules.toArray(new String [tableRules.size()]);
	}

	private void setTreeModels() {

		DataResultBean aDataBean = sendToServlet(transaction, ACTION_SHOWENHANCEDNEW, null, null, null);

		List tmpInstruction = (List) aDataBean.getObjectBy("ScriptBeanInstuctions");

		List instruction = new ArrayList();

		for (int i = 0; i < tmpInstruction.size(); i++) {

			if ( (tmpInstruction.get(i).toString().equalsIgnoreCase("Displaytime"))
			   	|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procdefend"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procdefstart"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procexec"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procinput"))
                || (tmpInstruction.get(i).toString().equalsIgnoreCase("Procinputall"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procinstall"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procpush"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Procscript"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Assign"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Exp"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Label"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Length"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Log"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Return"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Toxml"))
                || (tmpInstruction.get(i).toString().equalsIgnoreCase("Tostring"))
				|| (tmpInstruction.get(i).toString().equalsIgnoreCase("Trim")) ){

				continue;
			}

			instruction.add(tmpInstruction.get(i));
		}

		instructionTree.setModel(new EnhancedTreeModel(instruction, false));
		scriptTree.setModel(new EnhancedTreeModel((List) aDataBean.getObjectBy("ScriptBeanScripts"),true));
		tableTree.setModel(new EnhancedTreeModel((List) aDataBean.getObjectBy("TableBeanTables"),true));
        scriptRuleTree.setModel(new EnhancedTreeModel((List) aDataBean.getObjectBy("RuleBeanScriptRules"),true));
        tableRuleTree.setModel(new EnhancedTreeModel((List) aDataBean.getObjectBy("RuleBeanTableRules"),true));
	}

	private void setJScriptViewComp() {

		DataResultBean aDataBean = sendToServlet(transaction, action, scriptName, null, null);

		scriptViewComponent.setModelData((List) aDataBean.getObjectBy("script"));

		//******************************************//
		scriptBeforeSave = (List) aDataBean.getObjectBy("script");
	}


	private DataResultBean sendToServlet(String transaction,
										 String action,
										 String scriptName,
										 String scriptId,
										 String script) {

		System.out.println(transaction +" :" + action + ":" + scriptName + ":" + scriptId + ":" + script);

		DataResultBean aDataBean = null;

		try {

			Properties props = new Properties();

			if (transaction != null && transaction.length() != 0) {

				props.put("transaction", transaction);
			}
			if (action != null && action.length() != 0) {

				props.put("action", action);
			}
			if (scriptName != null && scriptName.length() != 0) {

				props.put("scriptName", scriptName);
			}
			if (scriptId != null && scriptId.length() != 0) {

				props.put("scriptId", scriptId);
			}
			if (script != null && script.length() != 0) {

				props.put("script", script);
			}

			HttpMessage msg = new HttpMessage(new URL(baseURL, servletURL));

			ObjectInputStream in = new ObjectInputStream(msg.sendPostMessage(props));

			aDataBean = (DataResultBean) in.readObject();
		}
		catch(Exception e) {

		}

		return aDataBean;
	}


	private final void initVariables() {

	// get base URL
		try {
			port		= Integer.parseInt(this.getParameter("port"));

			host		= this.getParameter("host");

			baseURL 	= new URL("http",
						  	  		host,
						      		port,
						      		"/");

			scriptName  = this.getParameter("scriptName");
			scriptId    = this.getParameter("scriptId");

			action      = this.getParameter("action");
			transaction = this.getParameter("transaction");

			servletURL  = this.getParameter("servletURL");

			cancelPage  = this.getParameter("cancelPage");
		}
		catch(Exception e) {
		}


		topSPPanel			= new JPanel();
		mainSPPanel			= new JPanel();
		bottomAppletPanel	= new JPanel();

		// components is mainPanelSouth
		mainPanelSouthApplied = new JPanel();
		applyButton 		  = new JButton("Apply");
		saveButton			  = new JButton("Save");
		cancelButton		  = new JButton("Clear");
        deleteButton          = new JButton("Delete");

		tabbedPane            = new JTabbedPane();

		instructionTree 	= new JTree();
		scriptTree			= new JTree();
		tableTree			= new JTree();
        scriptRuleTree      = new JTree();
        tableRuleTree       = new JTree();

		topJTree 			= new JTree();

		splitPane	= new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		scriptViewComponent = new ScriptViewComponent(baseURL, this);
		calculatorComponent	= new CalcComponent(baseURL, this);
		propChangeSupport = new PropertyChangeSupport(this);
	}

	private final void setScriptName(String name) {

		bottomLabel.setText("Mode: Enhanced         Script: " + name.trim());
	}

	private final void setupLayout() {

		this.getContentPane().setLayout(new BorderLayout());
		topSPPanel.setLayout(new GridLayout(1,1));
		mainSPPanel.setLayout(new GridLayout(1,1));

		bottomAppletPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		if (action.equalsIgnoreCase(ACTION_SHOWENHANCEDNEW)) {

			bottomLabel = new JLabel("Mode: Enhanced         Script: N/A", SwingConstants.LEFT);
			scriptViewComponent.setModelHasChanged(false);
		}
		else if (action.equalsIgnoreCase(ACTION_SHOWENHANCEDMOD)) {

			bottomLabel = new JLabel("Mode: Enhanced              Script: " + scriptName,
			SwingConstants.LEFT);
		}

		bottomLabel.setFont(new Font("TimesRoman", Font.BOLD, 15));
		bottomLabel.setForeground(Color.black);

		bottomAppletPanel.add(bottomLabel);
		bottomAppletPanel.add(Box.createHorizontalStrut(100));
		bottomAppletPanel.add(applyButton);
		bottomAppletPanel.add(saveButton);
		bottomAppletPanel.add(cancelButton);
        bottomAppletPanel.add(deleteButton);

		instructionTree.setRootVisible(false);
		scriptTree.setRootVisible(false);
		tableTree.setRootVisible(false);
        scriptRuleTree.setRootVisible(false);

	//	bottomAppletPanel.add(mainSPPanel);

		tabbedPane.addTab("Instruc",new JScrollPane(instructionTree));
		tabbedPane.addTab("Scripts",new JScrollPane(scriptTree));
		tabbedPane.addTab("Tables",new JScrollPane(tableTree));

		//topSPPanel.add(new JScrollPane(topJTree));

		splitPane.setTopComponent(topSPPanel);
		splitPane.setBottomComponent(mainSPPanel);
		splitPane.setDividerSize(4);
		splitPane.setDividerLocation(20);

		this.getContentPane().add(tabbedPane, BorderLayout.WEST);
		this.getContentPane().add(splitPane, BorderLayout.CENTER);
		this.getContentPane().add(bottomAppletPanel, BorderLayout.SOUTH);

		// GridBagLayoutStuff

		GridBagConstraints constraints = new GridBagConstraints();

		// leftComp
		buildConstraints(constraints, 0, 0, 1, 1, 100, 50);
		constraints.fill = GridBagConstraints.BOTH;
		mainSPPanel.add(scriptViewComponent, constraints);

		// rightComp
		buildConstraints(constraints, 1, 0, 1, 1, 00, 50);
		constraints.fill = GridBagConstraints.VERTICAL;

		mainSPPanel.add(calculatorComponent, constraints);

		// register component listeners
		calculatorComponent.addPropertyChangeListener(scriptViewComponent);
		scriptViewComponent.addActionListener(calculatorComponent);
		this.addPropertyChangeListener(scriptViewComponent);


		instructionTree.addMouseListener(this);
		scriptTree.addMouseListener(this);
		applyButton.addActionListener(this);
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
        deleteButton.addActionListener(this);
	}

	private void buildConstraints(GridBagConstraints gbc,
								   int x,
								   int y,
								   int width,
								   int height,
								   int weightx,
								   int weighty) {
		gbc.gridx 		= x;
		gbc.gridy 		= y;
		gbc.gridwidth 	= width;
		gbc.gridheight 	= height;
		gbc.weightx 	= weightx;
		gbc.weighty 	= weighty;
	}

	// -----------------------------------------------------
	// 						Inner Classes
	// -----------------------------------------------------
	private class EnhancedTreeModel implements TreeModel {

		private List modelData;
		private List listeners;


		public EnhancedTreeModel(List modelData, boolean addNewElement) {

			this.modelData = modelData;
			listeners = new ArrayList();
			if (addNewElement) {
				modelData.add(0, "<new>");
			}
		}

		// methods of TreeModel

		public Object getRoot() {

			return modelData;
		}

		public Object getChild(Object parent, int index) {

			return ((List) parent).get(index).toString();
		}

		public int getChildCount(Object parent)  {

			if (parent instanceof List) {

				return modelData.size();
			}
			else {

				return 0;
			}
		}

		public boolean isLeaf(Object node) {

			if (node instanceof List) {

				return ( ((List) node).size() == 0);
			}
			else {

				return false;
			}
		}

		public void valueForPathChanged(TreePath path, Object val) {


		}

		public int getIndexOfChild(Object parent, Object child) {

			return ((List) parent).indexOf(child);
		}

		public void addTreeModelListener(TreeModelListener l) {

			listeners.add(l);
		}

		public void removeTreeModelListener(TreeModelListener l) {

			listeners.remove(l);
		}

		public void addNode(String node) {

			modelData.add(node);
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == applyButton) {

            DataResultBean aDataBean = sendToServlet(transaction,
                                                      ACTION_SAVE,
                                                       scriptName,
                                                        scriptId,
                                                         scriptViewComponent.getModelDataAsText());

            this.scriptId = (String) aDataBean.getObjectBy(SCRIPT_ID_LC);

            setTreeModels();
		}

        else if (e.getSource() == saveButton) {

			sendToServlet(transaction,
						  ACTION_SAVE,
						  scriptName,
						  scriptId,
					  	  scriptViewComponent.getModelDataAsText());

			setScript(transaction, ACTION_SHOW_DEFAULT, null, null, new ArrayList());

			setTreeModels();
		}

        else if (e.getSource() == cancelButton) {

			if (! scriptViewComponent.modelHasChanged())  {

				setScript(transaction, ACTION_SHOW_DEFAULT, null, null, new ArrayList());

			}
			else {

				Object frame = getParent();
				while ( !( frame instanceof Frame) )
					frame = ( (Component) frame).getParent();
				CancelDialog cd = new CancelDialog((Frame) frame);
				cd.setSize(395,125);
				cd.setVisible(true);
				if (cd.yesSelected() == true) {

					setScript(transaction, ACTION_SHOW_DEFAULT, null, null, new ArrayList());
				}
				else  {
					return;
				}
			}
		}

        else if (e.getSource() == deleteButton) {

			DataResultBean aDataBean = sendToServlet(transaction,
                                                      ACTION_DELETE,
                                                       scriptName,
                                                        scriptId,
                                                         scriptViewComponent.getModelDataAsText());

            String message = (String) aDataBean.getObjectBy("message");

            Object frame = getParent();
            while ( !( frame instanceof Frame) )
                frame = ( (Component) frame).getParent();

            ScriptDeleteErrorDialog sded = new ScriptDeleteErrorDialog(message, (Frame) frame);
            sded.setSize(475,125);
            sded.setVisible(true);
            if (sded.enterSelected() == true) {

                if (message.equalsIgnoreCase("Script Deleted Successfully")) {

                    setScript(transaction, ACTION_SHOW_DEFAULT, null, null, new ArrayList());

                    setTreeModels();
                }

                else {

                    setScript(transaction, ACTION_SHOWENHANCEDMOD, scriptName, null);
                }
            }

            else  {

                return;
            }
		}
	}


	public static void main(String[] args) {

		JFrame frame = new JFrame();

		Container c = frame.getContentPane();

		c.setLayout(new BorderLayout());

		EnhancedEditApplet eea = new EnhancedEditApplet();

		c.add(eea);

		eea.init();

		frame.setSize(300,300);
		frame.setVisible(true);
	}
}