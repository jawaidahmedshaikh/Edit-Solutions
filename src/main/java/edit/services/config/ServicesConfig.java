/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 4, 2002
 * Time: 11:07:34 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.config;

import edit.common.vo.*;
import fission.utility.UtilFile;

import java.io.File;
import java.io.FileReader;

import com.editsolutions.web.utility.CommonConstants;


public class ServicesConfig
{
    private static EDITServicesConfig editServicesObj;
    public static final String SERVICENAME_EDITSOLUTIONS = "EDITSOLUTIONS";
    public static final String SERVICENAME_SECURITY = "SECURITY";
    public static final String SERVICENAME_PRASE = "PRASE";
    public static final String SERVICENAME_STAGING = "STAGING";
    public static final String SERVICENAME_DATAWAREHOUSE = "DATAWAREHOUSE";

    /**
     * Specicies the fully qualified path to the EDITServicesConfig.xml file. The file is then unmarshalled to its
     * corresponding value object (EDITServiceConfig) and validated.
     * @param editServicesConfigFile
     */
    public static void setEditServicesConfig(String editServicesConfigFile)
    {
        FileReader fr = null;

        try
        {
            fr = new FileReader(editServicesConfigFile);

            editServicesObj = EDITServicesConfig.unmarshal(fr);

            fr.close();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        validateEditServicesConfig();
    }

    public static EDITServicesConfig getEditServicesConfig()
    {
        return editServicesObj;
    }

    /**
     * Finds the EDITLog entry by fileName.
     * @param fileName
     * @return
     */
    public static EDITLog getEDITLogByFileName(String fileName)
    {
        EDITLog targetLog = null;

        File file = new File(fileName);

        for (int i = 0; i < editServicesObj.getEDITLogCount(); i++)
        {
            File currentFile = new File(editServicesObj.getEDITLog(i).getFile());

            if (currentFile.equals(file))
            {
                targetLog = editServicesObj.getEDITLog(i);

                break;
            }
        }

        return targetLog;
    }

    /**
     * Finds the EDITLog entry by logName.
     * @param logName
     * @return
     */
    public static EDITLog getEDITLogByLogName(String logName)
    {
        EDITLog targetLog = null;

        for (int i = 0; i < editServicesObj.getEDITLogCount(); i++)
        {
            if (editServicesObj.getEDITLog(i).getLogName().equalsIgnoreCase(logName))
            {
                targetLog = editServicesObj.getEDITLog(i);

                break;
            }
        }

        return targetLog;
    }

//    @Deprecated
    public static EDITExport getEDITExport(String exportName)
    {
//        EDITExport targetExport = null;
//
//        for (int i = 0; i < editServicesObj.getEDITExportCount(); i++)
//        {
//            if (editServicesObj.getEDITExport(i).getExportName().equalsIgnoreCase(exportName))
//            {
//                targetExport = editServicesObj.getEDITExport(i);
//
//                break;
//            }
//        }
    	EDITExport targetExport = new EDITExport();
    	targetExport.setExportName(exportName);
    	targetExport.setDirectory(System.getProperty(exportName).trim());
        return targetExport;
    }

    @Deprecated
    public static UnitValueImport getUnitValueImport()
    {
//        UnitValueImport uvImport = editServicesObj.getUnitValueImport();
        UnitValueImport uvImport = new UnitValueImport();
        uvImport.setDirectory(System.getProperty(
        		CommonConstants.UnitValueImportDirectory).trim());
        return uvImport;
    }

    public static JAASConfig getJAASConfig(String property)
    {
        JAASConfig targetProperty = null;

        for (int i = 0; i < editServicesObj.getJAASConfigCount(); i++)
        {
            if (editServicesObj.getJAASConfig(i).getProperty().equalsIgnoreCase(property))
            {
                targetProperty = editServicesObj.getJAASConfig(i);

                break;
            }
        }

        return targetProperty;
    }

    /**
    * Method added by sramamurthy 07/28/2004 for OFAC-SOAP call
    * Returns the OFACConfig object
    * @return OFACConfig
    */
    public static OFACConfig getOFACConfig()
    {
        return editServicesObj.getOFACConfig();
    }

    public static DateCalculations getDateCalculations(String businessContractName)
    {
        DateCalculations dateCalculations = null;

        for (int i = 0; i < editServicesObj.getDateCalculationsCount(); i++)
        {
            if (editServicesObj.getDateCalculations(i).getBusinessContract().equalsIgnoreCase(businessContractName))
            {
                dateCalculations = editServicesObj.getDateCalculations(i);

                break;
            }
        }

        return dateCalculations;
    }

    /**
     * This method will retrieve the EDITIssueProcess(es) from the EDITServicesConfig and
     * return the list of transactions to be generated for the given company structure.
     * @param companyStructurePK
     * @return String[] - a list of transactions to be generated during the issue process.
     */
    public static String[] getEDITIssueProcess(long companyStructurePK)
    {
        String[] transactions = null;

        for (int i = 0; i < editServicesObj.getEDITIssueProcessCount(); i++)
        {
            if (Long.parseLong(editServicesObj.getEDITIssueProcess(i).getCompanyStructurePK()) == companyStructurePK)
            {
                transactions = editServicesObj.getEDITIssueProcess(i).getTransaction();

                break;
            }
        }

        return transactions;
    }

    /**
     * This method will retrieve the EDITDeathProcess(es) from the EDITServicesConfig and
     * return the list of transactions to be generated for the given company structure.
     * @param companyStructurePK
     * @return AllowableTransaction[] - an array of AllowableTransactions (these are transactions that are allowed to
     *                                  process when a contract is in one of the "Death" statuses.
     */
    public static AllowableTransaction[] getEDITDeathProcess(long companyStructurePK)
    {
        AllowableTransaction[] allowableTransactions = null;
        for (int i = 0; i < editServicesObj.getEDITDeathProcessCount(); i++)
        {
            if (Long.parseLong(editServicesObj.getEDITDeathProcess(i).getCompanyStructurePK()) == companyStructurePK)
            {
                allowableTransactions = editServicesObj.getEDITDeathProcess(i).getAllowableTransaction();

                break;
            }
        }

        return allowableTransactions;
    }

    /**
     * Returns the SOAPConfig object
     * @return SOAPConfig
     */
    public static SOAPConfig getSOAPConfig()
    {
        return editServicesObj.getSOAPConfig();
    }

    /**
     * Finds the configured set of transactions to spawn (if any) for the given spawning transaction.
     * @param transactionTypeCT
     * @return
     */
    public static SpawnedTransaction[] getSpawnedTransactions(String transactionTypeCT)
    {
        SpawnedTransaction[] spawnedTransactions = null;

        SpawningTransaction[] spawningTransactions = editServicesObj.getSpawningTransaction();

        for (int i = 0; i < spawningTransactions.length; i++)
        {
            String currentTransactionTypeCT = spawningTransactions[i].getTransactionTypeCT();

            if (currentTransactionTypeCT.equals(transactionTypeCT))
            {
                spawnedTransactions = spawningTransactions[i].getSpawnedTransaction();

                break;
            }
        }

        return spawnedTransactions;
    }

    /**
     * Determine if the contents of the EDITServicesConfig is valid
     * Specifically, if the EDITLog and EDITExport directories exist.  If they don't, will create them or throw an error
     * if it can't.
     * @return
     */
    private static boolean validateEditServicesConfig()
    {
        boolean created = false;

        for (int i = 0; i < editServicesObj.getEDITLogCount(); i++)
        {
            String logFile = editServicesObj.getEDITLog(i).getFile();
            String logParentPath = new File(logFile).getParent();
            UtilFile.createDirectories(logParentPath);
        }

        // This is now taken care of in Bootup.java
//        for (int i = 0; i < editServicesObj.getEDITExportCount(); i++)
//        {
//            String exportDirectory = editServicesObj.getEDITExport(i).getDirectory();
//            UtilFile.createDirectories(exportDirectory);
//        }

        return created;
    }

    @Deprecated
    public static CashClearanceImport getCashClearanceImport()
    {
//        CashClearanceImport cashClearanceImport = editServicesObj.getCashClearanceImport();
        CashClearanceImport cashClearanceImport = new CashClearanceImport();
        cashClearanceImport.setDirectory(System.getProperty(
        		CommonConstants.CashClearanceImportDirectory).trim());
        return cashClearanceImport;
    }

    /**
     * Returns the value of IncludeUndoTransactionsInCommissionStatementIndicator tag.
     * @return
     */
    @Deprecated
    public static String getIncludeUndoTransactionsInCommissionStatementsIndicator()
    {
//        return editServicesObj.getIncludeUndoTransactionsInCommissionStatementsIndicator();
    	return System.getProperty(CommonConstants.
    			IncludeUndoTransactionsInCommissionStatementsIndicator).trim();
    }

    /**
     * Returns the value of CheckAmountForLastStatmentAmount element.
     * @return
     */
    @Deprecated
    public static String getCheckAmountForLastStatmentAmount()
    {
//        return editServicesObj.getCheckAmountForLastStatmentAmount();
    	return System.getProperty(
    			CommonConstants.CheckAmountForLastStatmentAmount).trim();
    }

    public static String getCheckForOverlappingSituationDates()
    {
        return editServicesObj.getCheckForOverlappingSituationDates();
    }
    
    /**
     * The set of entities that will be tracked for non-financial changes.
     * @return
     */
    public static NonFinancialEntity[] getNonFinancialEntities()
    {
        return editServicesObj.getNonFinancialEntity();
    }

    @Deprecated
    public static ConversionData getConversionData()
    {
//        ConversionData conversionData = editServicesObj.getConversionData();
        ConversionData conversionData = new ConversionData();
        conversionData.setDirectory(System.getProperty(
        		CommonConstants.ConversionDataDirectory).trim());
        return conversionData;
    }
    
    /**
     * Determines if the UI will expose the option for the user to
     * capture Inputs/Output of PRASE testing purposes.
     * If it has not been configured, it defaults to no 'N'.
     * @return 'Y' or 'N'
     */
    @Deprecated
    public static String getAllowPRASETest()
    {
//        String allowPRASETest = (editServicesObj.getAllowPRASETest() == null)?"N":editServicesObj.getAllowPRASETest();
//        return allowPRASETest;
    	return System.getProperty(CommonConstants.AllowPRASETest).trim();
    }

    /**
     * Determines if the UI will expose the option for the user to capture PRASE processing information.
     * If it has not been configured, it defaults to 'N' (no).
     *
     * @return 'Y' or 'N'
     */
    @Deprecated
    public static String getAllowPRASERecording()
    {
//        String allowPRASERecording = (editServicesObj.getAllowPRASERecording() == null)?"N":editServicesObj.getAllowPRASERecording();
//        return allowPRASERecording;
        return System.getProperty(CommonConstants.AllowPRASERecording).trim();
    }
}
