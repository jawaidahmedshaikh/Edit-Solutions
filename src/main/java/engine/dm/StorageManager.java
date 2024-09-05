/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm;

import edit.common.*;

import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITLockException;
import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.ElementLockManager;
import edit.services.db.hibernate.*;
import engine.dm.dao.DAOFactory;
import engine.dm.dao.ChargeCodeDAO;
import engine.sp.CSCache;
import engine.*;
import fission.dm.SMException;
import fission.utility.*;

import java.io.Serializable;
import java.util.*;

import security.FilteredRole;
import security.Role;

/**
 * StorageManager - Copyright Systems Engineering Group, LLC 2000
 * The StorageManager will handle the Object persistence.  There
 * are methods for adding, updating, deleting, and retrieving data
 * to the database.  The StorageManager will also establish and
 * maintain all database connections. *
 */
public class StorageManager implements Serializable
{

    // Member variables:

    /**
     * StorageManager constructor.
     */
    public StorageManager()
    {

    }

    public long saveVO(Object valueObject) throws Exception
    {
        CRUD crud = null;

        long pkValue = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            pkValue = crud.createOrUpdateVOInDBRecursively(valueObject);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return pkValue;
    }

    public long saveVONonRecursive(Object valueObject) throws Exception
    {
        CRUD crud = null;

        long pkValue = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            pkValue = crud.createOrUpdateVOInDB(valueObject);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return pkValue;
    }

    public int deleteVORecursively(Class voName, long primaryKey) throws Exception
    {
        CRUD crud = null;

        int numDeleted = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            return crud.deleteVOFromDBRecursively(voName, primaryKey);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void copyScript(String newScriptName,
                           long oldScriptId) throws SMException, Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            ScriptVO[] scriptVOs = DAOFactory.getScriptDAO().findScriptByIdRecursively(oldScriptId);

//save new in Script
            scriptVOs[0].setScriptPK(0);
            scriptVOs[0].setScriptName(newScriptName);
            ScriptLineVO[] scriptLines = scriptVOs[0].getScriptLineVO();

            for (int i = 0; i < scriptLines.length; ++i)
            {

                scriptLines[i].setScriptLinePK(0);
                scriptLines[i].setScriptFK(0);
                scriptVOs[0].addScriptLineVO(scriptLines[i]);
            }

            long pkValue = crud.createOrUpdateVOInDBRecursively(scriptVOs[0]);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    /**
     * Copies the table at TableKey level
     */
    public void copyTable(String tableId,
                          String newTableName,
                          String accessType) throws SMException, Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            long oldTableId = Long.parseLong(tableId);
            long defaultKey = 0;

            TableDefVO[] tableDefVOs = DAOFactory.getTableDefDAO().findTableNameByIdRecursively(oldTableId);

            //initializing the keys will create a table copy
            tableDefVOs[0].setTableDefPK(defaultKey);
            tableDefVOs[0].setTableName(newTableName);
            tableDefVOs[0].setAccessType(accessType);
            TableKeysVO[] tableKeysVOs = tableDefVOs[0].getTableKeysVO();

            for (int i = 0; i < tableKeysVOs.length; i++)
            {
                tableKeysVOs[i].setTableKeysPK(defaultKey);
                tableKeysVOs[i].setTableDefFK(defaultKey);

                RateTableVO[] rateTableVOs = tableKeysVOs[i].getRateTableVO();

                for (int j = 0; j < rateTableVOs.length; j++)
                {

                    rateTableVOs[j].setTableKeysFK(defaultKey);
                    rateTableVOs[j].setRateTablePK(defaultKey);
                }
            }

            long pkValue = crud.createOrUpdateVOInDBRecursively(tableDefVOs[0]);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void deleteRule(long rulesPK) throws Exception, EDITDeleteException
    {

        ProductRuleStructureVO[] productRuleStructureVO = DAOFactory.getProductRuleStructureDAO().findByRulesPK(rulesPK, false, null);

        if (productRuleStructureVO == null)
        {

            deleteVORecursively(RulesVO.class, rulesPK);
        }
        else
        {

            throw new EDITDeleteException("Rule Is Attached To At Least One Product Structure and Cannot Be Deleted");
        }
    }

    public void deleteProductStructure(long productStructurePK) throws Exception, EDITDeleteException
    {

        FilteredRole[] filteredRoles = FilteredRole.findByProductStructure(productStructurePK);
        if (filteredRoles != null && filteredRoles.length > 0)
        {
            String message = makeDetailedMessage(filteredRoles);
            throw new EDITDeleteException(message);
        }

        ProductRuleStructureVO[] productRuleStructureVO = DAOFactory.getProductRuleStructureDAO().
                findByProductStructurePK(productStructurePK, false, null);

        ProductFilteredFundStructureVO[] productFilteredFundStructureVO = DAOFactory.getProductFilteredFundStructureDAO().
                findByProductStructurePK(productStructurePK, false, null);

//        ProductAreaStructureVO[] productAreaStructureVOs = DAOFactory.getProductAreaStructureDAO().findByProductStructurePK(productStructurePK, false, null);

        if ((productRuleStructureVO == null) && (productFilteredFundStructureVO == null))
        {

            deleteVORecursively(ProductStructureVO.class, productStructurePK);
        }
        else
        {

            if ((productRuleStructureVO.length > 0) || (productFilteredFundStructureVO.length > 0))
            {

                throw new EDITDeleteException("Company Has At Least One Rule or Area Attached To It And Can Not Be Deleted");
            }
        }
    }


    private String makeDetailedMessage(FilteredRole[] filteredRoles)
    {
        StringBuffer sb = new StringBuffer(200);
        for (int i = 0; i < filteredRoles.length; i++)
        {
            FilteredRole filteredRole = filteredRoles[i];
            Role role = filteredRole.getRole();

            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(role.getName());
       }
       return "Cannot delete. Company has security attached to these roles - " + sb;
    }

    /**
     *
     * @param scriptId
     * @param scriptName
     * @param scriptText
     * @param scriptType
     * @param scriptStatus
     * @param operator
     * @return
     * @throws SMException
     * @throws Exception
     */
    public long saveScript(long scriptId,
                           String scriptName,
                           String scriptText,
                           String scriptType,
                           String scriptStatus,
                           String operator) throws SMException, Exception
    {

        CRUD crud = null;

        crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

        long newScriptId = 0;
        ScriptVO[] scriptVOs = null;
        long pkValue = 0;

        try
        {
            // Get Script

            scriptVOs = DAOFactory.getScriptDAO().findScriptByNameRecursively(scriptName);

            if (scriptVOs != null)
            {

                scriptId = scriptVOs[0].getScriptPK();
            }


            // Convert string to a vector
            List script = stringToVector(scriptText);

            //if given id is not present in Script Table
            //then first create scriptId for this new Script
            //in Script Table and then add scriptLines to the
            //ScriptLineTable
            //if(!(flag)) {

            //start Transaction
            crud.startTransaction();

            if (scriptId == 0)
            {
                // New script
                //get the scriptId created for new script
                //so that it may be used in ScriptLineTable

                ScriptVO scriptVO = saveInScriptTable(newScriptId, scriptName, scriptType, scriptStatus, operator);
                newScriptId = crud.createOrUpdateVOInDB(scriptVO);

                saveInScriptLineTable(scriptName, newScriptId, script, crud);
            }

            else
            {

                ScriptVO scriptVO = saveInScriptTable(scriptId, scriptName, scriptType, scriptStatus, operator);
                scriptId = crud.createOrUpdateVOInDB(scriptVO);

                //delete all sriptLines of script form scriptLineTable
                ScriptLineVO[] scriptLineVOs = scriptVOs[0].getScriptLineVO();

                for (int i = 0; i < scriptLineVOs.length; i++)
                {

                    long scriptLinePK = scriptLineVOs[i].getScriptLinePK();
                    crud.deleteVOFromDBRecursively(ScriptLineVO.class, scriptLinePK);
                }

                // Existing Script
                saveInScriptLineTable(scriptName, scriptId, script, crud);

                newScriptId = scriptId;
            }

            crud.commitTransaction();
        }
        catch (Exception e)
        {
            System.out.println("engine.dm.StorageManager.saveScript(): " + e);

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            CSCache.getCSCache().clearCSCache();
        }

        return newScriptId;
    }

    /**
     * This method is used to add Rows
     * in Script Table
     */
    public ScriptVO saveInScriptTable(long newScriptId,
                                      String scriptName,
                                      String scriptType,
                                      String scriptStatus,
                                      String operator) throws SMException, Exception
    {
        ScriptVO scriptVO = new ScriptVO();
        scriptVO.setScriptPK(newScriptId);
        scriptVO.setScriptName( Util.initString( scriptName, null) );
        scriptVO.setScriptTypeCT(scriptType);
        scriptVO.setScriptStatusCT(scriptStatus);
        scriptVO.setOperator(operator);
        String maintDate = new EDITDateTime().getFormattedDateTime();
        scriptVO.setMaintDateTime(maintDate);
        return scriptVO;
    }

    /**
     * This method is used to create the
     * scriptLine Rows in ScriptLineTable
     */
    public void saveInScriptLineTable(String name,
                                      long newScriptId,
                                      List script,
                                      CRUD crud)
            throws SMException, Exception
    {


//create new scriptLines in ScriptLineTable
        int size = script.size();
        int i = 0;

        for (int j = 0; j < size; ++j)
        {
            String line = (String) script.get(j);
            String s = line.trim();

            if ((s == null) || (s.length() == 0))
            {

                s = new String(" ");
            }

            ++i;
            ScriptLineVO scriptLineVO = new ScriptLineVO();
            scriptLineVO.setScriptFK(newScriptId);
            scriptLineVO.setScriptLine(s);
            scriptLineVO.setLineNumber(i);
            long pkValue = crud.createOrUpdateVOInDB(scriptLineVO);
        }
    }

    /**
     * This method saves the data in RateTable
     */
    public long saveRateTable(String tableId, String tableName, String sex, String class_cd,
                              String bandAmount, String month, String day, String year,
                              String userKey, String area, String type, String accessType,
                              String tableKeyId, List rateData)
            throws SMException, Exception
    {

        long newTableDefId = 0;
        long newTableKeyId = 0;

        //putting this try block so that if any error(error
        //other than during dataAccess) arises then
        //with rollback, Tables can be unlocked

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            TableDefVO tableDefVO = new TableDefVO();
            TableKeysVO tableKeysVO = new TableKeysVO();
            RateTableVO[] rateTableVO = null;

            boolean tableIdFlag = false;
            boolean tableKeyFlag = false;

            if (!tableId.equals(""))
            {

                tableIdFlag = true;
                newTableDefId = Long.parseLong(tableId.trim());

//get the table key ids associated with tableDefId
                TableKeysVO[] tableKeyVO = DAOFactory.getTableKeysDAO().findTableKeysById(newTableDefId);

                for (int j = 0; j < tableKeyVO.length; j++)
                {

                    long storedTableKeyId = tableKeyVO[j].getTableKeysPK();

                    if (storedTableKeyId == Long.parseLong(tableKeyId.trim()))
                    {

                        tableKeyFlag = true;
                        break;
                    }
                }
            }

//if new table ie user had hit add button on tabmod.jsp
            if (!tableKeyFlag)
            {

                rateTableVO = saveInRateTable(newTableKeyId, rateData);

//create a row in Table_Keys Table
                tableKeysVO = saveInTableKeys(sex, class_cd, bandAmount, month, day, year,
                        userKey, area, type, newTableKeyId, newTableDefId);

                tableKeysVO.setRateTableVO(rateTableVO);

                newTableKeyId = crud.createOrUpdateVOInDBRecursively(tableKeysVO);

                if (!tableIdFlag)
                {

//create a Row in TableDef
                    tableDefVO = saveInTableDef(newTableDefId, tableName, accessType);

                    tableDefVO.addTableKeysVO(tableKeysVO);

                    newTableDefId = crud.createOrUpdateVOInDBRecursively(tableDefVO);

                    TableKeysVO[] tableKeyVO = DAOFactory.getTableKeysDAO().findTableKeysById(newTableDefId);

                    newTableKeyId = tableKeyVO[0].getTableKeysPK();
                }
            }

            else
            {

//update the row in Table_Keys Table
                newTableDefId = Long.parseLong(tableId.trim());

                newTableKeyId = Long.parseLong(tableKeyId.trim());

                rateTableVO = saveInRateTable(newTableKeyId, rateData);

                tableKeysVO = saveInTableKeys(sex, class_cd, bandAmount, month, day, year,
                        userKey, area, type, newTableKeyId, newTableDefId);


                tableKeysVO.setRateTableVO(rateTableVO);

                newTableKeyId = crud.createOrUpdateVOInDBRecursively(tableKeysVO);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new Exception(e.toString());
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return newTableKeyId;
    }

    /**
     * This method saves rows in TableDef Table
     */
    public TableDefVO saveInTableDef(long tableDefId, String tableName, String accessType)
            throws SMException, Exception
    {

        TableDefVO tableDefVO = new TableDefVO();

        //create a row in TableDef table
        tableDefVO.setTableDefPK(tableDefId);
        tableDefVO.setTableName(tableName);
        tableDefVO.setAccessType(accessType);
        tableDefVO.setOperator("");
        String maintDate = new EDITDateTime().getFormattedDateTime();
        tableDefVO.setLockDateTime(maintDate);
        tableDefVO.setMaintDateTime(maintDate);

        return tableDefVO;
    }

    /**
     * This method saves rows in RateTable
     */
    public RateTableVO[] saveInRateTable(long newTableKeyId,
                                         List rateData)
            throws SMException, Exception
    {

        if (rateData != null)
        {

            List rates = new ArrayList();
            Iterator enumer = rateData.iterator();
            while (enumer.hasNext())
            {

                Map data = (Map) enumer.next();

                String ageParameter = (String) data.get("age");
                String durationParameter = (String) data.get("duration");
                String rateParameter = (String) data.get("rate");

                int age = Integer.parseInt(ageParameter);
                int duration = Integer.parseInt(durationParameter);
                EDITBigDecimal rate = new EDITBigDecimal(rateParameter);

                RateTableVO rateTable = new RateTableVO();
                rateTable.setTableKeysFK(newTableKeyId);
                rateTable.setAge(age);
                rateTable.setDuration(duration);
                rateTable.setRate(rate.getBigDecimal());
                rates.add(rateTable);
            }

            return ((RateTableVO[]) rates.toArray(new RateTableVO[rates.size()]));
        }

        return null;
    }

    /**
     * This method saves data in Table_Keys Table
     */
    public TableKeysVO saveInTableKeys(String sex,
                                       String class_cd,
                                       String bandAmount,
                                       String month,
                                       String day,
                                       String year,
                                       String userKey,
                                       String area,
                                       String type,
                                       long tableKeyId,
                                       long tableDefId)
            throws Exception
    {

        TableKeysVO tableKeysVO = new TableKeysVO();
        long defaultKey = 0;
        long primaryKey = 0;
        String codeTableName = null;

        tableKeysVO.setGender(sex);
        tableKeysVO.setClassType(class_cd);
        tableKeysVO.setState(area);
        tableKeysVO.setTableType(type);
        tableKeysVO.setBandAmount((new EDITBigDecimal(bandAmount)).getBigDecimal());

        if (!userKey.equals(""))
        {

            tableKeysVO.setUserKey(userKey);
        }

        else
        {

            tableKeysVO.setUserKey("-");
        }
        tableKeysVO.setEffectiveDate(new EDITDate(year, month, day).getFormattedDate());

        tableKeysVO.setTableKeysPK(tableKeyId);
        tableKeysVO.setTableDefFK(tableDefId);

        return tableKeysVO;
    }

    public long saveProductStructure(ProductStructureVO productStructureVO) throws Exception
    {

        long productStructurePK = 0;

        // Default to "*" where there are nulls
        productStructureVO.setAreaName(Util.initString(productStructureVO.getAreaName(), "*"));
        productStructureVO.setBusinessContractName(Util.initString(productStructureVO.getBusinessContractName(), "*"));
        productStructureVO.setGroupProductName(Util.initString(productStructureVO.getGroupProductName(), "*"));
        productStructureVO.setMarketingPackageName(Util.initString(productStructureVO.getMarketingPackageName(), "*"));
        productStructureVO.setTypeCodeCT(Util.initString(productStructureVO.getTypeCodeCT(), "System"));


        ProductStructureVO[] productStructureVOs = DAOFactory.
                getProductStructureDAO().
                findProductStructureByNames_CompanyFK(productStructureVO.getMarketingPackageName(),
                        productStructureVO.getGroupProductName(),
                        productStructureVO.getAreaName(),
                        productStructureVO.getBusinessContractName(),
                        productStructureVO.getCompanyFK());

        if (productStructureVOs == null)
        {
            CRUD crud = null;

            try
            {
                crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

                productStructurePK = crud.createOrUpdateVOInDB(productStructureVO);
            }
            catch (Exception e)
            {
                System.out.println(e);
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
            finally
            {
                if (crud != null) crud.close();
            }
        }
        else
        {
            productStructurePK = productStructureVOs[0].getProductStructurePK();
        }

        return productStructurePK;
    }

    public long saveProductStructure(String newProductStructure) throws Exception
    {

        long productStructurePK = 0;

        StringTokenizer productStructureData = new StringTokenizer(newProductStructure, ",");

        //get companyName
        String companyName = productStructureData.nextToken();

        //get marketingPackage name
        String marketingPackageName = productStructureData.nextToken();

        //get groupProduct name
        String groupProductName = productStructureData.nextToken();

        //get area name
        String areaName = productStructureData.nextToken();

        //get businessContractName
        String businessContractName = productStructureData.nextToken();

        //check for duplicate Product Structure
        ProductStructureVO[] duplicate = DAOFactory.getProductStructureDAO().
                findProductStructureByNames(companyName,
                        marketingPackageName,
                        groupProductName,
                        areaName,
                        businessContractName);

        if (duplicate == null)
        {

            //add product structure to database

            productStructurePK = saveInProductStructure(companyName,
                    marketingPackageName,
                    groupProductName,
                    areaName,
                    businessContractName);
        }

        else
        {

            throw new Exception("Company Structure with CompanyName= " + companyName +
                    ", Marketing Package Name= " + marketingPackageName +
                    ", GroupProduct Name= " + groupProductName +
                    ", AreaName= " + areaName +
                    "and BusinessContract Name= " + businessContractName + "exists in database.");
        }

        return productStructurePK;
    }

    public long saveInProductStructure(String companyName,
                                       String marketingPackageName,
                                       String groupProductName,
                                       String areaName,
                                       String businessContractName)
            throws Exception
    {
        CRUD crud = null;

        long pkValue = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            Company company = (Company)SessionHelper.newInstance(Company.class, SessionHelper.ENGINE);
            company.setCompanyName(companyName);

            SessionHelper.beginTransaction(SessionHelper.ENGINE);
            SessionHelper.saveOrUpdate(company, SessionHelper.ENGINE);
            SessionHelper.commitTransaction(SessionHelper.ENGINE);

            ProductStructureVO productStructure = new ProductStructureVO();

            productStructure.setMarketingPackageName(marketingPackageName);
            productStructure.setGroupProductName(groupProductName);
            productStructure.setAreaName(areaName);
            productStructure.setBusinessContractName(businessContractName);
            productStructure.setOperator(" ");
            productStructure.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            productStructure.setCompanyFK(company.getCompanyPK().longValue());
            
            pkValue = crud.createOrUpdateVOInDB(productStructure);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
            SessionHelper.closeSessions();
        }

        return pkValue;
    }

    public String updateRateTablesFromImport(String[] fileContents, String fileName, String operator) throws Exception
    {

        boolean createNewTables = false;
        List voExclusionList = new ArrayList();
        voExclusionList.add(RulesVO.class);
        TableDefVO[] tableDefVOs = DAOFactory.getTableDefDAO().findTableIdByName(fileName, true, voExclusionList);
        TableDefVO tableDefVO = new TableDefVO();
        long tableDefPK = 0;

        if (tableDefVOs == null)
        {

            createNewTables = true;
            tableDefVO.setTableDefPK(0);
            tableDefVO.setTableName(fileName);
            tableDefVO.setOperator(operator);
            String maintDate = new EDITDateTime().getFormattedDateTime();
            tableDefVO.setMaintDateTime(maintDate);
            tableDefVO.setLockDateTime(maintDate);
        }
        else
        {

            tableDefVO = tableDefVOs[0];
            tableDefPK = tableDefVO.getTableDefPK();
        }

        List rateTable = new ArrayList();
        List tableKeysTable = new ArrayList();

        String sexValue = "";
        String classValue = "";
        String bandAmtValue = "";
        String tableTypeValue = "";
        String areaValue = "";
        String effectiveDateFieldValue = "";

        EDITDate effectiveDateValue = null;

        String userKeyValue = "";
        String accessType = "";
        String compareArea = "";
        EDITBigDecimal bandAmt = new EDITBigDecimal("0");
        String effectiveMonth = "";
        String effectiveDay = "";
        String effectiveYear = "";
        int age = 0;
        int duration = 0;
        EDITBigDecimal rate = new EDITBigDecimal("0");
        TableKeysVO tableKeysVO = new TableKeysVO();
        boolean breakOccurred = false;

        for (int i = 1; i < fileContents.length; i++)
        {

            StringTokenizer values = new StringTokenizer(fileContents[i], ",");
            RateTableVO rateTableVO = new RateTableVO();
            int count = 1;

            while (values.hasMoreTokens())
            {

                String fieldValue = values.nextToken().trim();

                if (count == 1)
                {

                    sexValue = fieldValue;
                }
                else if (count == 2)
                {

                    classValue = fieldValue;
                }
                else if (count == 3)
                {

                    bandAmtValue = fieldValue;
                    bandAmt = new EDITBigDecimal(fieldValue);
                }
                else if (count == 4)
                {

                    tableTypeValue = fieldValue;
                }
                else if (count == 5)
                {

                    areaValue = fieldValue;
                }
                else if (count == 6)
                {
                    effectiveDateFieldValue = fieldValue;
                    effectiveDateValue = DateTimeUtil.getEDITDateFromMMDDYYYY(fieldValue);

//                    if (effectiveMonth.length() == 1)
//                    {
//                        effectiveMonth = "0" + effectiveMonth;
//                    }
//                    if (effectiveDay.length() == 1)
//                    {
//                        effectiveDay = "0" + effectiveDay;
//                    }
//                    effectiveDateValue = effectiveYear + "/" + effectiveMonth + "/" + effectiveDay;
                }
                else if (count == 7)
                {

                    if (fieldValue.equals("_"))
                    {
                        fieldValue = "-";
                    }
                    userKeyValue = fieldValue;
                }
                else if (count == 8)
                {

                    age = Integer.parseInt(fieldValue);
                }
                else if (count == 9)
                {

                    duration = Integer.parseInt(fieldValue);
                }
                else if (count == 10)
                {

                    rate = new EDITBigDecimal(fieldValue);
                }
                else if (count == 11)
                {

                    accessType = fieldValue.toUpperCase();
                }
                count++;

            }   //end while

            // if no data in file record , count never incremented
            if (count > 1)
            {

                rateTableVO.setAge(age);
                rateTableVO.setDuration(duration);
                rateTableVO.setRate(rate.getBigDecimal());
                rateTableVO.setRateTablePK(0);

                if (!compareArea.equalsIgnoreCase(sexValue + classValue + bandAmtValue + tableTypeValue + areaValue + effectiveDateValue + userKeyValue))
                {

                    if (i == 1)
                    {

                        compareArea = sexValue + classValue + bandAmtValue + tableTypeValue + areaValue + effectiveDateValue + userKeyValue;
                        tableKeysVO = setupTableKeys(sexValue, classValue, bandAmt, tableTypeValue, areaValue, effectiveDateValue.getFormattedDate(), userKeyValue);
                        tableKeysVO.setTableDefFK(tableDefPK);
                        tableDefVO.setAccessType(accessType);
                    }
                    else
                    {

                        if (createNewTables)
                        {

                            buildNewTableDefVO(rateTable, tableKeysVO, tableDefVO);
                        }
                        else
                        {
                            findTableKeysVO(compareArea, tableDefVO, tableKeysVO, rateTable);
//                            tableKeysVO = storeRateVOs(rateTable, tableKeysVO, tableDefVO, compareArea);
                        }

                        compareArea = sexValue + classValue + bandAmtValue + tableTypeValue + areaValue + effectiveDateValue + userKeyValue;
                        tableKeysVO = setupTableKeys(sexValue, classValue, bandAmt, tableTypeValue, areaValue, effectiveDateValue.getFormattedDate(), userKeyValue);
                        tableKeysVO.setTableDefFK(tableDefPK);
                        tableDefVO.setAccessType(accessType);

                        rateTable = new ArrayList();
                    }
                }

                rateTable.add(rateTableVO);
            }
        }  //end for

        if (createNewTables)
        {

            tableDefVO = buildNewTableDefVO(rateTable, tableKeysVO, tableDefVO);
        }
        else
        {
//            tableKeysVO = storeRateVOs(rateTable, tableKeysVO, tableDefVO, compareArea);
            findTableKeysVO(compareArea, tableDefVO, tableKeysVO, rateTable);
//            if (tableKeysVO == null)
//            {
//
//                return "Import Completed Successfully";
//            }
        }

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            // when end of file set up the full vo
            if (createNewTables)
            {
                long pkValue = crud.createOrUpdateVOInDBRecursively(tableDefVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return "Import Completed Successfully";
    }

    private TableKeysVO setupTableKeys(String sexValue, String classValue, EDITBigDecimal bandAmt, String tableTypeValue, String areaValue, String effectiveDateValue, String userKey)
    {
         TableKeysVO tableKeysVO = new TableKeysVO();
         tableKeysVO = new TableKeysVO();
         tableKeysVO.setGender(sexValue);
         tableKeysVO.setClassType(classValue);
         tableKeysVO.setBandAmount(bandAmt.getBigDecimal());
         tableKeysVO.setTableType(tableTypeValue);
         tableKeysVO.setState(areaValue);
         tableKeysVO.setEffectiveDate(effectiveDateValue);
         tableKeysVO.setUserKey(userKey);

        return tableKeysVO;
    }

    private TableDefVO buildNewTableDefVO(List rateTable, TableKeysVO tableKeysVO,
                                          TableDefVO tableDefVO) throws Exception
    {

        RateTableVO[] rateTableVOs = (RateTableVO[])
                rateTable.toArray(new RateTableVO[rateTable.size()]);

        tableKeysVO.setTableKeysPK(0);
        tableKeysVO.setRateTableVO(rateTableVOs);
        tableDefVO.addTableKeysVO(tableKeysVO);

        return tableDefVO;
    }

    private TableKeysVO storeRateVOs(List rateTable, TableKeysVO tableKeysVO,
                                     TableDefVO tableDefVO, String compareArea) throws Exception
    {
        findTableKeysVO(compareArea, tableDefVO, tableKeysVO, rateTable);
//        tableKeysVO = findTableKeysVO(compareArea, tableDefVO, tableKeysVO, rateTable);

//        if (tableKeysVO != null)
//        {
//            RateTableVO[] dbRateTableVOs = tableKeysVO.getRateTableVO();
//
//            RateTableVO[] rateTableVOs = (RateTableVO[])
//                    rateTable.toArray(new RateTableVO[rateTable.size()]);
//
//            if (dbRateTableVOs == null || (dbRateTableVOs.length == 0))
//            {
//                tableKeysVO.setRateTableVO(rateTableVOs);
//            }
//            else
//            {
//                long tableKeysPK = tableKeysVO.getTableKeysPK();
//
//                for (int i = 0; i < rateTableVOs.length; i++)
//                {
//
//                    rateTableVOs[i].setTableKeysFK(tableKeysPK);
//                    tableKeysVO.addRateTableVO(rateTableVOs[i]);
//                }
//            }
//        }

        return tableKeysVO;
    }

    private void findTableKeysVO(String compareArea,
                                        TableDefVO tableDefVO,
                                        TableKeysVO tableKeysVOIn,
                                        List rateTable) throws Exception
    {
        boolean matchFound = false;
        int tableKeyOccurrance = 0;

        TableKeysVO[] tableKeysVOs = tableDefVO.getTableKeysVO();

        for (int i = 0; i < tableKeysVOs.length; i++)
        {

            String sexValue = Util.initString(tableKeysVOs[i].getGender(), "");
            String classValue = Util.initString(tableKeysVOs[i].getClassType(), "");
            String bandAmtValue = Util.initString((tableKeysVOs[i].getBandAmount() + ""), "0");

            String tableTypeValue = Util.initString(tableKeysVOs[i].getTableType(), "");
            String areaValue = Util.initString(tableKeysVOs[i].getState(), "");
            String effectiveDateValue = Util.initString(tableKeysVOs[i].getEffectiveDate(), "");
            String userKeyValue = Util.initString(tableKeysVOs[i].getUserKey(), "");


            if (compareArea.equalsIgnoreCase(sexValue + classValue + bandAmtValue + tableTypeValue + areaValue + effectiveDateValue + userKeyValue))
            {

                matchFound = true;
                tableKeyOccurrance = i;
                break;
            }
        }

        if (!matchFound)
        {

            long tableKeysPK = saveTableKeys(tableKeysVOIn);
            simpleRateUpdate(tableKeysPK, rateTable);
        }
    }

    private long saveTableKeys(TableKeysVO tableKeysVO) throws Exception
    {
        CRUD crud = null;
        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);
            long pkValue = crud.createOrUpdateVOInDBRecursively(tableKeysVO);

            return pkValue;
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }


    }


    private void simpleRateUpdate(long tableKeysPK, List rateTable) throws Exception
    {

        RateTableVO[] rateTableVOs = (RateTableVO[])
                rateTable.toArray(new RateTableVO[rateTable.size()]);

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            for (int i = 0; i < rateTableVOs.length; i++)
            {

                rateTableVOs[i].setTableKeysFK(tableKeysPK);
                long pkValue = crud.createOrUpdateVOInDB(rateTableVOs[i]);

            }

        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {

            if (crud != null) crud.close();
        }
    }


    public List determineFields(String column1,
                                  String column2,
                                  String column3,
                                  String column4,
                                  String column5)
    {

        List columnValues = new ArrayList();

        if (column1.equalsIgnoreCase("fund"))
        {

            columnValues.add("fund");
        }
        else if (column1.equalsIgnoreCase("eff date"))
        {

            columnValues.add("effDate");
        }
        else if (column1.equalsIgnoreCase("unit value"))
        {

            columnValues.add("unitValue");
        }
        else if (column1.equalsIgnoreCase("annuity unit value"))
        {

            columnValues.add("annUnitValue");
        }

        if (column2.equalsIgnoreCase("fund"))
        {

            columnValues.add("fund");
        }
        else if (column2.equalsIgnoreCase("eff date"))
        {

            columnValues.add("effDate");
        }
        else if (column2.equalsIgnoreCase("unit value"))
        {

            columnValues.add("unitValue");
        }
        else if (column2.equalsIgnoreCase("annuity unit value"))
        {

            columnValues.add("annUnitValue");
        }

        if (column3.equalsIgnoreCase("fund"))
        {

            columnValues.add("fund");
        }
        else if (column3.equalsIgnoreCase("eff date"))
        {

            columnValues.add("effDate");
        }
        else if (column3.equalsIgnoreCase("unit value"))
        {

            columnValues.add("unitValue");
        }
        else if (column3.equalsIgnoreCase("annuity unit value"))
        {

            columnValues.add("annUnitValue");
        }

        if (column4.equalsIgnoreCase("fund"))
        {

            columnValues.add("fund");
        }
        else if (column4.equalsIgnoreCase("eff date"))
        {

            columnValues.add("effDate");
        }
        else if (column4.equalsIgnoreCase("unit value"))
        {

            columnValues.add("unitValue");
        }
        else if (column4.equalsIgnoreCase("annuity unit value"))
        {

            columnValues.add("annUnitValue");
        }

        if (column5 != null && column5.equalsIgnoreCase("charge code"))
        {
            if (!columnValues.contains("chargeCode"))
            {
                columnValues.add("chargeCode");
            }
        }
        return columnValues;
    }

    public UnitValuesVO formatVOs(String fieldValue,
                                  String columnName,
                                  UnitValuesVO unitValuesVO) throws Exception
    {


        if (columnName.equalsIgnoreCase("effDate"))
        {

            EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(fieldValue);

            unitValuesVO.setEffectiveDate(effectiveDate.getFormattedDate());
        }
        else if (columnName.equalsIgnoreCase("unitValue"))
        {

            EDITBigDecimal unitValue = new EDITBigDecimal(fieldValue);
            unitValuesVO.setUnitValue(unitValue.getBigDecimal());
        }
        else if (columnName.equalsIgnoreCase("annUnitValue"))
        {

            EDITBigDecimal annUnitValue = new EDITBigDecimal(fieldValue);
            unitValuesVO.setAnnuityUnitValue(annUnitValue.getBigDecimal());
        }

        return unitValuesVO;
    }

    private UnitValuesVO getChargeCodeValue(String fieldValue, UnitValuesVO unitValuesVO)
    {
        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeDAO().findByFilteredFundPK_ChargeCode(fieldValue, unitValuesVO.getFilteredFundFK());

        long chargeCodePK = 0;
        long filteredFundFK = unitValuesVO.getFilteredFundFK();
        String effectiveDate = unitValuesVO.getEffectiveDate();
        EDITBigDecimal unitValue = new EDITBigDecimal(unitValuesVO.getUnitValue());
        EDITBigDecimal annUnitValue = new EDITBigDecimal(unitValuesVO.getAnnuityUnitValue());
        UnitValuesVO newUnitValuesVO = null;

        if (chargeCodeVOs != null)
        {
            for (int i = 0; i < chargeCodeVOs.length; i++)
            {
                chargeCodePK = chargeCodeVOs[i].getChargeCodePK();
                newUnitValuesVO = new UnitValuesVO();
                newUnitValuesVO.setFilteredFundFK(filteredFundFK);
                newUnitValuesVO.setEffectiveDate(effectiveDate);
                newUnitValuesVO.setUnitValue(unitValue.getBigDecimal());
                newUnitValuesVO.setAnnuityUnitValue(annUnitValue.getBigDecimal());
                newUnitValuesVO.setChargeCodeFK(chargeCodePK);
            }
        }

        return newUnitValuesVO;
    }

    public String updateUnitValuesFromImport(String[] fileContents) throws Exception
    {

        StringTokenizer st = new StringTokenizer(fileContents[0], ",");

        String column1 = st.nextToken();
        String column2 = st.nextToken();
        String column3 = st.nextToken();
        String column4 = st.nextToken();
        String column5 = st.nextToken();

        FilteredFundVO[] filteredFundVOs = null;

        List fieldOrder = determineFields(column1, column2, column3, column4, column5);
        String columnName = null;
        String fundNumber = null;
        List unitValues = new ArrayList();
        String saveFundNumber = null;


        for (int i = 1; i < fileContents.length; i++)
        {
            StringTokenizer values = new StringTokenizer(fileContents[i], ",");
            UnitValuesVO unitValuesVO = new UnitValuesVO();

            int numberOfTokens = values.countTokens();

            while (values.hasMoreTokens())
            {
                String fieldValue = values.nextToken();

                int commasRemaining = values.countTokens();

                columnName = (String) fieldOrder.get(numberOfTokens - (commasRemaining + 1));

                if (columnName.equalsIgnoreCase("fund"))
                {
                    fundNumber = fieldValue;

                    if (!fundNumber.equals(saveFundNumber))
                    {
                        saveFundNumber = fundNumber;
                        filteredFundVOs = DAOFactory.getFilteredFundDAO().findByFundNumber(fundNumber);

                        if (filteredFundVOs == null)
                        {

                            return "Import problem: Fund Number not found on FilteredFund Table";
                        }

                        unitValuesVO.setFilteredFundFK(filteredFundVOs[0].getFilteredFundPK());
                    }
                    else
                    {

                        unitValuesVO.setFilteredFundFK(filteredFundVOs[0].getFilteredFundPK());
                    }
                }

                else
                {

                    if (!columnName.equalsIgnoreCase("chargeCode"))
                    {
                        unitValuesVO = formatVOs(fieldValue, columnName, unitValuesVO);

                        //No charge code processing
                        if (commasRemaining == 0)
                        {
                            unitValues.add(unitValuesVO);
                        }
                    }
                    else
                    {
                        unitValuesVO = getChargeCodeValue(fieldValue, unitValuesVO);
                        if (unitValuesVO == null)
                        {
                            return "ChargeCode For the Fund Requested Not Found";
                        }
                        else
                        {
                            unitValues.add(unitValuesVO);
                        }
                    }
                }
            }   //end while
        }  //end for

        UnitValuesVO[] unitValuesVOs = (UnitValuesVO[])
                unitValues.toArray(new UnitValuesVO[unitValues.size()]);

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            for (int j = 0; j < unitValuesVOs.length; j++)
            {
                boolean duplicateFound = checkForDuplicatesUV(unitValuesVOs[j]);

                if (duplicateFound)
                {
                    return "Duplicate Record Found, Import Terminated";
                }

                long pkValue = crud.createOrUpdateVOInDB(unitValuesVOs[j]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return "Import Completed Successfully";
    }

    /**
     * If the unitValue already exists in the table generate a duplicate error
     * @param unitValuesVO
     * @return
     */
    public boolean checkForDuplicatesUV(UnitValuesVO unitValuesVO)
    {
        boolean duplicateFound = false;
        long filteredFundFK = unitValuesVO.getFilteredFundFK();
        String effectiveDate = unitValuesVO.getEffectiveDate();
        EDITBigDecimal unitValue = new EDITBigDecimal(unitValuesVO.getUnitValue());
        UnitValuesVO[] dbUnitValuesVO = null;
        long chargeCodeFK = unitValuesVO.getChargeCodeFK();

        if (chargeCodeFK == 0)
        {
            dbUnitValuesVO = DAOFactory.getUnitValuesDAO().findByFilteredFundFKDate(filteredFundFK, effectiveDate);
        }
        else
        {
            dbUnitValuesVO = DAOFactory.getUnitValuesDAO().findByFilteredFundFKDate_ChargeCode(filteredFundFK, chargeCodeFK, effectiveDate);
        }

        if (dbUnitValuesVO != null)
        {
            EDITBigDecimal uvFromTable = new EDITBigDecimal(dbUnitValuesVO[0].getUnitValue());
            if (unitValue.isEQ(uvFromTable) && (chargeCodeFK == dbUnitValuesVO[0].getChargeCodeFK()))
            {
                duplicateFound = true;

            }
            else
            {
                if ((chargeCodeFK == dbUnitValuesVO[0].getChargeCodeFK()))
                {
                    unitValuesVO.setUnitValuesPK(dbUnitValuesVO[0].getUnitValuesPK());
                }
            }
        }

        return duplicateFound;
    }
    public void attachFilteredFundToProduct(long productStructurePK,
                                            long[] filteredFundPKs) throws Exception
    {
        CRUD crud = null;

        long pk = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            for (int i = 0; i < filteredFundPKs.length; i++)
            {
                // Check to see if this aleady exists...
                ProductFilteredFundStructureVO[] productFilteredFundStructureVOs =
                        DAOFactory.getProductFilteredFundStructureDAO().
                        findByNaturalKey(productStructurePK,
                                filteredFundPKs[i],
                                false,
                                null);

                if (productFilteredFundStructureVOs == null)
                {

                    ProductFilteredFundStructureVO productFilteredFundStructureVO = new ProductFilteredFundStructureVO();
                    productFilteredFundStructureVO.setProductStructureFK(productStructurePK);
                    productFilteredFundStructureVO.setFilteredFundFK(filteredFundPKs[i]);

                    crud.createOrUpdateVOInDB(productFilteredFundStructureVO);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void detachFilteredFundFromProduct(long productStructurePK,
                                              long[] filteredFundPKs) throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            for (int i = 0; i < filteredFundPKs.length; i++)
            {
                // Get the existing one for its PK
                ProductFilteredFundStructureVO[] productFilteredFundStructureVOs = DAOFactory.
                        getProductFilteredFundStructureDAO().
                        findByNaturalKey(productStructurePK,
                                filteredFundPKs[i],
                                false,
                                null);

                if (productFilteredFundStructureVOs != null)
                {
                    crud.deleteVOFromDBRecursively(ProductFilteredFundStructureVO.class,
                            productFilteredFundStructureVOs[0].getProductFilteredFundStructurePK());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void attachRulesToProductStructure(long productStructurePK, long[] rulesPKs) throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            for (int i = 0; i < rulesPKs.length; i++)
            {
                ProductRuleStructureVO[] productRulesStructureVOs = DAOFactory.getProductRuleStructureDAO().findByProductStructurePKAndRulesPK(productStructurePK, rulesPKs[i], false, null);

                if (productRulesStructureVOs == null)
                {
                    ProductRuleStructureVO productRuleStructureVO = new ProductRuleStructureVO();
                    productRuleStructureVO.setProductStructureFK(productStructurePK);
                    productRuleStructureVO.setRulesFK(rulesPKs[i]);

                    crud.createOrUpdateVOInDB(productRuleStructureVO);
                }
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            CSCache.getCSCache().clearCSCache();
            
            if (crud != null) crud.close();
        }
    }

    public void detachRulesFromProductStructure(long productStructurePK, long[] rulesPKs) throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            for (int i = 0; i < rulesPKs.length; i++)
            {
                ProductRuleStructureVO[] productRulesStructureVOs = DAOFactory.getProductRuleStructureDAO().findByProductStructurePKAndRulesPK(productStructurePK, rulesPKs[i], false, null);

                if (productRulesStructureVOs != null)
                {
                    crud.deleteVOFromDB(ProductRuleStructureVO.class, productRulesStructureVOs[0].getProductRuleStructurePK());
                }
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            CSCache.getCSCache().clearCSCache();
            
            if (crud != null) crud.close();
        }
    }

    public void cloneProductStructure(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws Exception
    {
        CRUD crud = null;

        try
        {
            ProductRuleStructureVO[] cloneFromProductRuleStructureVOs = DAOFactory.getProductRuleStructureDAO().findByProductStructurePK(cloneFromProductStructurePK, false, null);

            if (cloneFromProductRuleStructureVOs != null)
            {
                crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

                List rulesPKs = new ArrayList();

                for (int i = 0; i < cloneFromProductRuleStructureVOs.length; i++)
                {
                    rulesPKs.add(new Long(cloneFromProductRuleStructureVOs[i].getRulesFK()));
                }

                attachRulesToProductStructure(cloneToProductStructurePK, Util.convertLongToPrim((Long[]) rulesPKs.toArray(new Long[rulesPKs.size()])));
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void saveRule(RulesVO rulesVO) throws Exception
    {
        rulesVO.setEventName(Util.initString(rulesVO.getEventName(), "*"));
        rulesVO.setEventTypeName(Util.initString(rulesVO.getEventTypeName(), "*"));
        rulesVO.setProcessName(Util.initString(rulesVO.getProcessName(), "*"));

        String maintDateTime = new EDITDateTime().getFormattedDateTime();
        rulesVO.setMaintDateTime(maintDateTime);

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            crud.createOrUpdateVOInDB(rulesVO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e);

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public int deleteTable(long tableDefPK, long tableKeysPK) throws Exception, EDITDeleteException
    {

        RulesVO[] rulesVO = DAOFactory.getRulesDAO().findByTableDefPK(tableDefPK, false, null);

        int numDeleted = 0;

//        if (rulesVO == null)
//        {

        List voExclusionList = new ArrayList();
        voExclusionList.add(RateTableVO.class);
        voExclusionList.add(RulesVO.class);

        TableDefVO tableDefVO = DAOFactory.getTableDefDAO().findByTableDefPK(tableDefPK, true, voExclusionList)[0];

        // If this was the last key, then delete the entire table, only if not attached to a rule.
        if (tableDefVO.getTableKeysVOCount() == 1)
        {
            if (rulesVO == null)
            {
                numDeleted = deleteVORecursively(TableDefVO.class, tableDefPK);
            }
            else
            {
                throw new EDITDeleteException("Table Is Attached To At Least One Rule And Can Not Be Deleted");
            }
        }
        else
        {
            numDeleted = deleteVORecursively(TableKeysVO.class, tableKeysPK);
        }

        return numDeleted;
    }

    public int deleteScript(long scriptPK) throws Exception, EDITDeleteException
    {

        RulesVO[] rulesVO = DAOFactory.getRulesDAO().findByScriptPK(scriptPK, false, null);

        int numDeleted = 0;

        if (rulesVO == null)
        {

            List voExclusionList = new ArrayList();
            voExclusionList.add(ScriptLineVO.class);
            voExclusionList.add(RulesVO.class);

            ScriptVO scriptVO = DAOFactory.getScriptDAO().findByScriptPK(scriptPK, true, voExclusionList)[0];

            numDeleted = deleteVORecursively(ScriptVO.class, scriptPK);
        }
        else
        {

            throw new EDITDeleteException("Script Is Attached To At Least One Rule And Can Not Be Deleted");
        }

        return numDeleted;
    }

    public long saveTable(TableDefVO tableDefVO, TableKeysVO tableKeysVO, RateTableVO[] rateTableVOs) throws Exception
    {
        CRUD crud = null;

        long tableDefPK = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            // CRUD will "synchronize" the voModel. If we don't save tableDefVO separately, all of the
            // other TableKeysVOs would be deleted.
            tableDefVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            tableDefVO.setLockDateTime(tableDefVO.getMaintDateTime());

            tableDefPK = crud.createOrUpdateVOInDB(tableDefVO);

            tableKeysVO.setTableDefFK(tableDefPK);

            tableKeysVO.setRateTableVO(rateTableVOs);

            crud.createOrUpdateVOInDBRecursively(tableKeysVO);

            CSCache.getCSCache().clearCSCache();
        }
        catch (Exception e)
        {
            System.out.println(e);

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return tableDefPK;
    }

    public static List stringToVector(String stringIn)
    {

        StringTokenizer s = new StringTokenizer(stringIn, "\n");
        List text = new ArrayList();

        while (s.hasMoreTokens())
        {
            String line = new String(Util.rTrim(s.nextToken().replace('\n', ' ').replace('\r', ' ')));
            text.add(line);
        }

        return text;
    }

    public ElementLockVO lockElement(long elementPK, String username) throws EDITLockException
    {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.lockElement(elementPK, username);
    }

    public int unlockElement(long lockedElementPK)
    {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.unlockElement(lockedElementPK);
    }

}
