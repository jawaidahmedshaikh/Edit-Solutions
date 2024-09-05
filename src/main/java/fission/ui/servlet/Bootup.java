package fission.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.exceptions.EDITSecurityException;
import edit.common.vo.EDITServicesConfig;
import edit.common.vo.JAASConfig;
import edit.portal.menu.MenuCreator;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.editsolutions.web.utility.CommonConstants;

import edit.services.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import fission.utility.Util;
import fission.utility.UtilFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import security.ComponentMethod;
import security.business.Security;
import security.component.SecurityComponent;
import security.utility.ProductSecurityConversion;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 24, 2005
 * Time: 10:35:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bootup extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(Bootup.class);
	
  /**
   * The path to the main configuration file.
   */
  protected String editServicesConfigPath;

  /**
   * The name of the main configuration file.
   */
  protected String editServicesConfigFileName;
  
  /**
   * Assuming editServicesConfigPath and editServicesConfigFileName
   * have been successfully defined, this represents the File instance
   * of editServicesConfigPath + editServicesConfigFileName.
   */
  protected String editServicesConfigFile;

  /**
   * The objectified representation of the configuration
   * file identified by editServicesConfigPath + editServicesConfigFileName.
   */
  protected EDITServicesConfig editServicesConfig;

  /**
   * The set of system steady-state intializes to perform upon bootup.
   * @param sc
   * @throws ServletException
   */
  public void init(ServletConfig sc) throws ServletException
  {
    super.init();
    
    try
    {
      System.out.println("");
      
      System.out.println("\n\t\t ********************************* BOOTUP BEGIN *********************************");
      
      System.out.println("\n\t\t ** BOOTUP: Verify VarArgs Configuration ...");
      
      verifyVarArgsConfig();
      
      System.out.println("\n\t\t ** BOOTUP: Setting C3p0 Configurations ...");
      
      // TODO: Keep until C3P0 is completely removed from all environments
      System.setProperty("com.mchange.v2.resourcepool.experimental.useScatteredAcquireTask","true");
      
      System.out.println("\n\t\t ** BOOTUP: Loading the main configuration file...");
      
      loadEDITServicesConfig(sc);
      
      System.out.println("\n\t\t ** BOOTUP: Loading System properties ...");
      
      loadSystemProperties();
      
      System.out.println("\n\t\t ** BOOTUP: Loading System version info ...");
      
      loadVersionInfo();
      
      System.out.println("\n\t\t ** BOOTUP: Loading database metadata ...");

      loadDBDatabase();
      
      System.out.println("\n\t\t ** BOOTUP: Loading Code-Table entries ...");

      loadCodeTable();
      
      System.out.println("\n\t\t ** BOOTUP: Synchronizing component methods ...");
      
      synchronizeAllComponentMethodsWithDB();
      
      System.out.println("\n\t\t ** BOOTUP: Loading Implied Roles By Filtered Role ...");
      
      loadImpliedRolesByFilteredRole();
      
      System.out.println("\n\t\t ** BOOTUP: Starting batch agent ...");
      
      startBatchAgent();
      
      System.out.println("\n\t\t ********************************* BOOTUP END *********************************");

      System.out.println("");
      
      // This should NOT be necessary. Security does NOT have to be
      // compiled into the system, so don't add a dependency to it.
      //inititializeSecurity();
    }
    catch (Exception e)
    {
      System.out.println(e);

      e.printStackTrace();

      throw new ServletException(e);
    }
    finally
    {
      // Bootup is not a "user" thread, but part of the system startup.
      // Clear any Hibernate sessions just-in-case some were invoked.
    
      SessionHelper.clearSessions();
    }
  }

  /**
     * Starts the HTTP agent that will listen and execute batch requests from an HTTP client.
     */
  private void startBatchAgent()
  {
    try
    {
      EditServiceLocator.getSingleton().getBatchAgent().startService();
      
      System.out.println("\n\t\t ** -----> Batch agent successfully started.");
    }
    catch (Exception e)
    {
      System.out.println("\n\t\t ** -----> WARNING: The batch agent could not be started. Batch processes will not run. You may have conflicting port #'s.");
    }
  }

  /**
   * Triggers the CodeTable to load all of its values.
   */
  private void loadCodeTable()
  {
    // PreLoad the CodeTable
    try
    {
      CodeTableWrapper.getSingleton();
      
      System.out.println("\n\t\t ** -----> Code-Table entries were successfully loaded.");
    }
    catch (Exception e)
    {
      System.out.println("\n\t\t ** -----> ERROR: Code-Table entries could not be loaded. Bootup can not continue.");
      
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads the settings in the EDITServicesConfig.xml file into the EDITServicesConfig object
   * @param sc
   * @throws IOException
   * @throws Exception
   */
  private void loadEDITServicesConfig(ServletConfig sc) throws IOException, Exception
  {
    File editServicesConfigFile = getEDITServicesConfigFile(sc);

    if (editServicesConfigFile != null)
    {
      ServicesConfig.setEditServicesConfig(editServicesConfigFile.getAbsolutePath());

      EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();

      // Set it for this Bootup instance
      setEditServicesConfig(editServicesConfig);
    }
  }

  /**
   * Initializes security
   * @throws EDITSecurityException
   */
  private void inititializeSecurity() throws EDITSecurityException
  {
    Security security = new SecurityComponent();

    // Does the database define FilteredRoles?  If the database table is
    // undefined put out error message.
    if (!security.isFilteredRoleDefinedInDatabase())
    {
      String msg = "ERROR! This database does not have FilteredRole defined\n" + "in the SECURITY schema.  You must run the database DDL\n" + "for FilteredRole and alter for SecuredMethod and restart";
      
      System.out.println("\n\n\n" + msg + "\n\n\n");
      
      throw new RuntimeException(msg);
    }

    // Does the security database not match the product structures?
    // This is true if a backup from a customer has been swapped into
    // the system, e.g.
    if (security.isSecurityDbMismatch()) // this is false if not converted
    {
      security.handleDbMismatch();
    }

    if (!security.securityIsInitialized()) // is there an admin user?
    {
      security.loadInitialSecurity();
    }
    else
    {
      // if it is already initialized, does it need to be converted
      // to product-type security using FilteredRoles and CompanyStructures?
      ProductSecurityConversion p = new ProductSecurityConversion();
      
      if (p.isConversionToProductSecurityNeeded())
      {
        p.convertDataToProductSecurity();
      }
    }
  }

  /**
   * Determines the full path and filename of the EDITServicesConfig file
   * <p/>
   * The path can come from one of two areas:
   * 1.  The servlet's initialization parameter EDITServicesConfigPath (common when running in developer's area)
   * 2.  The path of where the RequestManager is running from (common when running from an installation, usually via a jar)
   * <p/>
   * First determine if the path where the RequestManager is running from is a valid path for the config file.
   * If the path contains the WEB-INF directory, this path is valid ('cuz that's where the config file lives)
   * <p/>
   * If it does not, then get the path from the initialization parameters
   * <p/>
   * Build the full filename with the path and filename (from initialization parameters)
   *
   * @param sc
   * @return
   */
  protected File getEDITServicesConfigFile(ServletConfig sc)
  {
    File configurationFile = null;

    try
    {
      String editServicesConfigFileName = sc.getInitParameter("EDITServicesConfigFileName");
  
      String editServicesConfigPath = sc.getInitParameter("EDITServicesConfigPath");
  
      String configFilename = editServicesConfigPath + UtilFile.DIRECTORY_DELIMITER + editServicesConfigFileName;
      
      configurationFile = new File(configFilename);
      
      setEditServicesConfigPath(editServicesConfigPath);
      
      setEditServicesConfigFileName(editServicesConfigFileName);
      
      System.out.println("\n\t\t ** -----> A configuration file was successfully found at [" + configurationFile.getAbsolutePath() + "]");
    }
    catch (Exception e)
    {
      System.out.println("\n\t\t ** -----> ERROR: Unable to load the main configuration file.");
      
      System.out.println("\n\t\t ** -----> ERROR: Make sure that the web.xml is properly configured and points to the main EDIT Services configuration file.");
      
      System.out.println("\n\t\t ** -----> ERROR: Bootup can not continue.");
      
      throw new RuntimeException(e);
    }

    return configurationFile;
  }

  /**
   * Loads the necessary System properties specified in the EDITServicesConfig
   *
   * NOTE:  At this time, this only includes the JAASConfig
   *
   */
  protected void loadSystemProperties()
  {
    JAASConfig[] jaasConfigs = getEditServicesConfig().getJAASConfig();

    for (int i = 0; i < jaasConfigs.length; i++)
    {
      System.setProperty(jaasConfigs[i].getProperty(), jaasConfigs[i].getFile());
      
      System.out.println("\n\t\t ** -----> System.put(\"" + jaasConfigs[i].getProperty() + "\",\"" + jaasConfigs[i].getFile() + "\"");
    }
  }

    /**
     * Reads the versionInfo file and loads the parameters as system properties for access from user interface
     */
  	protected void loadVersionInfo() {
  		String editServicesConfigPath = getEditServicesConfigPath();
  		String configFilename = "versionInfo.txt";
  		configFilename = editServicesConfigPath + UtilFile.DIRECTORY_DELIMITER + configFilename;

  		try {
  			String[] fileContents = UtilFile.readFile(configFilename);
  			for (int i = 0; i < fileContents.length; i++) {
  				String[] tokens = Util.fastTokenizer(fileContents[i], "=");
  				if (tokens.length > 1) {
  					String name = tokens[0];
  					String value = tokens[1];
  					System.setProperty(name, value);
  				}
  			}
  			System.out.println("\n\t\t ** -----> Version information successfully loaded.");
  		} catch (IOException e) {
  			System.out.println("\n\t\t ** -----> WARNING: Version information could not be found. Verify that web.xml is configured properly");
  			//  Can't find the file, don't do anything.  System should not error, ui will just display null for fields
  		}
  		Properties prop = new Properties();
  		try {
  			prop.load(this.getClass().getResourceAsStream("/venus-version.properties"));
  			System.setProperty("venus.revision", prop.getProperty("scmVersion", "unknown"));
  			System.setProperty("venus.branch", (prop.getProperty("scmBranch", "unknown")));
  			System.setProperty("maxBatchThreads", (prop.getProperty("maxBatchThreads", "9")));
  			System.setProperty("maxAccountingThreads", (prop.getProperty("maxAccountingThreads", "3")));
  			System.setProperty("maxStrategyChainTrx", (prop.getProperty("maxStrategyChainTrx", "50")));
  			System.setProperty("debugBatch", (prop.getProperty("debugBatch", "false")));
  			System.setProperty("debugScripts", (prop.getProperty("debugScripts", "false")));
  		} catch (IOException ioe) {
  			System.out.println(ioe.getMessage());
  		}
  	}

  /**
   * Considerable meta-data is used for CRUD (and other) operations. This is stored
   * in DBDatabase/DBTable/DBColumn structures.
   */
  private void loadDBDatabase()
  {
    // Kicks-off static block.
    try
    {
//      new DBDatabase();

      Class.forName("edit.services.db.DBDatabase");
        
      System.out.println("\n\t\t ** -----> Database metadata was successfully loaded.");
    }
    catch (Exception e)
    {
      System.out.println("\n\t\t ** -----> ERROR: Database metadata could not be loaded. Bootup can not continue.");            
    }
  }
  
  /**
   * All the use case component methods are synchronized with database. 
   */
  private void synchronizeAllComponentMethodsWithDB() 
  {
      try 
      {
          if (MenuCreator.isCompiledWithSecurity())
          {
              ComponentMethod.synchronizeAllComponentMethodsWithDB();
              
              System.out.println("\n\t\t ** -----> Component Methods were successfully synchronized.");
          }
      }
      catch (Exception e) 
      {
          System.out.println("\n\t\t ** -----> ERROR: Component Methods could not be synchronized.");
      }      
  }

  /**
   * Maps all ImpliedRoles by FilteredRole.
   */
  private void loadImpliedRolesByFilteredRole()
  {
      try
      {
          security.FilteredRole.loadImpliedRolesByFilteredRole();

          System.out.println("\n\t\t ** -----> Implied Roles by Filtered Role successfully loaded.");
      }
      catch (Exception e)
      {
          System.out.println("\n\t\t ** -----> ERROR: Implied Roles by Filtered Role could not be loaded.");
      }
  }

    private void verifyVarArgsConfig() {
    	String ExportDirectory1 = System.getProperty(
    			CommonConstants.ExportDirectory1);
    	String ExportDirectory2 = System.getProperty(
    			CommonConstants.ExportDirectory2);
    	String UnitValueImportDirectory = System.getProperty(
    			CommonConstants.UnitValueImportDirectory);
    	String CashClearanceImportDirectory = System.getProperty(
    			CommonConstants.CashClearanceImportDirectory);
    	String ConversionDataDirectory = System.getProperty(
    			CommonConstants.ConversionDataDirectory);
    	String AllowPRASETest = System.getProperty(
    			CommonConstants.AllowPRASETest);
    	String AllowPRASERecording = System.getProperty(
    			CommonConstants.AllowPRASERecording);
    	String IncludeUndoTransactionsInCommissionStatementsIndicator = 
    			System.getProperty(CommonConstants.
    					IncludeUndoTransactionsInCommissionStatementsIndicator);
    	String CheckAmountForLastStatmentAmount = System.getProperty(
    			CommonConstants.CheckAmountForLastStatmentAmount);
    	String reportServerRoot = System.getProperty(CommonConstants.ReportServerRoot);
    	
    	// Only WARN if the following are not set
    	List<String> varArgIssues = new ArrayList<String>();
    	if (AllowPRASETest == null) {
    		String warn = "\tWARN: AllowPRASETest is not configured. Default will be 'N'\n";
    		varArgIssues.add(warn);
    		System.setProperty("AllowPRASETest", "N");
    	}
    	if (AllowPRASERecording == null) {
    		String warn = "\tWARN: AllowPRASERecording is not configured. Default will be 'N'\n";
    		varArgIssues.add(warn);
    		System.setProperty("AllowPRASERecording", "N");
    	}
    	if(reportServerRoot == null) {
    		String warn = "\tWARN: " + CommonConstants.ReportServerRoot + " is not configured. Ad-hoc reporting may be disabled in some parts of the application.\n";
    		varArgIssues.add(warn);
    		System.setProperty(CommonConstants.ReportServerRoot, "");
    	}
    	if (IncludeUndoTransactionsInCommissionStatementsIndicator == null) {
    		String warn = "\tWARN: IncludeUndoTransactionsInCommissionStatementsIndicator is not configured. Default will be 'N'\n";
    		varArgIssues.add(warn);
    		System.setProperty("IncludeUndoTransactionsInCommissionStatementsIndicator", "N");
    	}
    	if (CheckAmountForLastStatmentAmount == null) {
    		String warn = "\tWARN: CheckAmountForLastStatmentAmount is not configured. Default will be 'N'\n";
    		varArgIssues.add(warn);
    		System.setProperty("CheckAmountForLastStatmentAmount", "N");
    	}
    	
    	// Throw a RuntimeException if any of the following aren't configured.
    	List<String> varArgErrors = new ArrayList<String>();
    	if (ExportDirectory1 == null) {
    		String error = "\tERROR: EDITExport.ExportDirectory1 is not configured!\n";
    		varArgErrors.add(error);
    	} else {
    		UtilFile.createDirectories(ExportDirectory1);
    	}
    	if (ExportDirectory2 == null) {
    		String error = "\tERROR: EDITExport.ExportDirectory2 is not configured!\n";
    		varArgErrors.add(error);
    	} else {
    		UtilFile.createDirectories(ExportDirectory2);
    	}
    	if (UnitValueImportDirectory == null) {
    		String error = "\tERROR: UnitValueImport.Directory is not configured!\n";
    		varArgErrors.add(error);
    	}
    	if (CashClearanceImportDirectory == null) {
    		String error = "\tERROR: CashClearanceImport.Directory is not configured!\n";
    		varArgErrors.add(error);
    	}
    	if (ConversionDataDirectory == null) {
    		String error = "\tERROR: ConversionData.Directory is not configured!\n";
    		varArgErrors.add(error);
    	}
    	
    	if (varArgIssues.size() > 0) {
    		logger.warn(varArgIssues.toString());
    	}
    	if (varArgErrors.size() > 0) {
    		throw new RuntimeException("ERROR: EditSolutions didn't startup properly.\n"
    				+ "The following need be configured in the container:\n" +
    				varArgErrors.toString());
    	}
    }
  
  public void setEditServicesConfigPath(String editServicesConfigPath)
  {
    this.editServicesConfigPath = editServicesConfigPath;
  }

  public String getEditServicesConfigPath()
  {
    return editServicesConfigPath;
  }

  public void setEditServicesConfigFileName(String editServicesConfigFileName)
  {
    this.editServicesConfigFileName = editServicesConfigFileName;
  }

  public String getEditServicesConfigFileName()
  {
    return editServicesConfigFileName;
  }

  public EDITServicesConfig getEditServicesConfig()
  {
    return editServicesConfig;
  }

  public void setEditServicesConfig(EDITServicesConfig editServicesConfig)
  {
    this.editServicesConfig = editServicesConfig;
  }
}
