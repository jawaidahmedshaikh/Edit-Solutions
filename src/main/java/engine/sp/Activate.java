/*
 * Activate.java      06/04/2001
 *
  * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
package engine.sp;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.hibernate.*;

import engine.dm.*;

import org.dom4j.*;

import java.io.*;

import java.util.*;


/**
 * This is the implementation for the Activate instruction.
 * The Activate instruction is used to initialize a function(TableName).
 * Activate requires multiple parameters to determine which
 * tableKey reocords to retrieve.  Activate also has one operand which
 * represents the function name.
 * <p/>
 * Example:
 * push param:Class
 * push param:IssueAge
 * push num:3
 * activate
 */
public final class Activate extends InstOneOperand
{
    private static boolean eiaCapsFound = false;
    private static boolean eiaMarginFound = false;
    private boolean incrementInstPtr = true;
    String classId = null;
    String genderId = null;
    String areaId = null;
    EDITBigDecimal bandAmt;
    String currGuarId = null;
    String userKey = null;
    int issueAge = 0;
    int duration = 0;
    String tableType = null;

    /**
     * Activate constructor
     * <p/>
     */
    public Activate()
    {
        super();
    }

    /**
     * Compiles the instruction
     * <p/>
     * @param aScriptProcessor Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor)
    {
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
    }

    /**
     * Executes the instruction
     * <p/>
     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        try
        {
            sp = execSP;

            String odn = (sp.popFromStack());
            currGuarId = (String) sp.getWSEntry("CurrGuarInd");
            genderId = (String) sp.getWSEntry("Gender");
            classId = (String) sp.getWSEntry("ClassType");
            userKey = (String) sp.getWSEntry("UserKey");

            String effectiveDate = (String) sp.getWSEntry("EffectiveDate");
            String bandAmount = (String) sp.getWSEntry("BandAmount");
            areaId = (String) sp.getWSEntry("State");
            tableType = (String) sp.getWSEntry("TableType");

            String age = (String) sp.getWSEntry("IssueAge");
            String interpolateInd = (String) sp.getWSEntry("InterpolateInd");
            String durationString = (String) sp.getWSEntry("Duration");

            if (interpolateInd == null)
            {
                interpolateInd = "N";
            }

            if (age != null && !age.equalsIgnoreCase("#NULL"))
            {
                issueAge = Integer.parseInt(age);
            }
            
            if (durationString != null && !durationString.equalsIgnoreCase("#NULL"))
            {
                duration = Integer.parseInt(durationString);
            }

            if ((bandAmount == null) || bandAmount.equals(" "))
            {
                bandAmt = new EDITBigDecimal("0");
            }
            else
            {
                bandAmt = new EDITBigDecimal(bandAmount);
            }
            int step = 1;

            List rateTable = new ArrayList();

            TableDefVO tableDefVO = CSCache.getCSCache().getTableDefVOBy_TableName(odn);
            step = 2;

            long tableDefId = tableDefVO.getTableDefPK();
            step = 3;

            String accessType = tableDefVO.getAccessType();
            step = 4;

            TableKeysVO[] tableKeysVOs = CSCache.getCSCache().getTableKeysBy_TableDefPK_EffectiveDate(tableDefId, effectiveDate);
            step = 5;

            TableKeysVO tableKeysVO = null;

            if (tableKeysVOs != null)
            {
                if (tableKeysVOs.length == 1)
                {
                    tableKeysVO = tableKeysVOs[0];
                    step = 6;
                }
                else
                {
                    tableKeysVO = matchKeysToParams(tableKeysVOs);
                    step = 7;
                    
                    if (tableKeysVO == null) {
                    	// look for a default userKey entry
                    	String originalKey = userKey;
                    	userKey = "*";
                    	tableKeysVO = matchKeysToParams(tableKeysVOs);
                    	userKey = originalKey;
                        step = 8;
                    }
                }
            }

            if (tableKeysVO == null)
            {
                throw new SPException("Active - TableKeys entry not found for table " + odn 
                		+ " - userKey = " + userKey + " - tableDefId = " + tableDefId
                		+ " - step = " + step, SPException.INSTRUCTION_PROCESSING_ERROR);
            }

            long tableKeysId = tableKeysVO.getTableKeysPK();

            rateTable = RateTable.getExpandedTable(accessType, tableKeysId, issueAge, interpolateInd, duration);

            odn = odn += currGuarId;

            // Place function on function HashTable
            sp.addFunctionEntry(odn, rateTable);
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    private void prettyPrint(String odn)
    {
        System.out.println("---------------------------------------------------");

        System.out.println("tableName: " + odn);
        System.out.println("classId: " + classId);
        System.out.println("genderId: " + genderId);
        System.out.println("areaId: " + areaId);
        System.out.println("bandAmt: " + bandAmt);
        System.out.println("currGuarId: " + currGuarId);
        System.out.println("userKey: " + userKey);
        System.out.println("issueAge: " + issueAge);
        System.out.println("tableType: " + tableType + "\n");

        Element element = sp.getSPParams().getLastActiveElement("NaturalDocVO");

        StringWriter output = new StringWriter();

        SessionHelper.prettyPrint(element, output);

        System.out.println(output.toString());

        System.out.println("---------------------------------------------------");
    }

    /**
     * match the tableKey records to the parameters set during script processing
     * @param tableKeysVOs
     * @return  tableKeysVO
     */
    private TableKeysVO matchKeysToParams(TableKeysVO[] tableKeysVOs)
    {
        TableKeysVO tableKeysVO = null;
        EDITBigDecimal tableKeyBandAmtStart = new EDITBigDecimal("0");
        EDITBigDecimal tableKeyBandAmtEnd = new EDITBigDecimal("0");
        List tableKeys = new ArrayList();

        for (int i = 0; i < tableKeysVOs.length; i++)
        {
            // UserKey has to be exact match ... default value should not be matched 
            // Gender, ClassId, TableType is ... either exact match or default value
            if ((tableKeysVOs[i].getGender().equalsIgnoreCase(genderId) || isDefaultValue(tableKeysVOs[i].getGender())) &&
                 (tableKeysVOs[i].getClassType().equalsIgnoreCase(classId) || isDefaultValue(tableKeysVOs[i].getClassType())) &&
                 ((tableKeysVOs[i].getUserKey() != null) && tableKeysVOs[i].getUserKey().equalsIgnoreCase(userKey)) &&
                 (tableKeysVOs[i].getTableType().equalsIgnoreCase(tableType) || isDefaultValue(tableKeysVOs[i].getTableType())))
            {
            EDITBigDecimal bandAmount = new EDITBigDecimal(tableKeysVOs[i].getBandAmount());

                if (bandAmount.isGT("0"))
                {
                    tableKeyBandAmtStart = tableKeyBandAmtEnd;

                    tableKeyBandAmtEnd = bandAmount;

                    if (tableKeyBandAmtStart.isGT(tableKeyBandAmtEnd))
                    {
                        tableKeyBandAmtStart = new EDITBigDecimal("0");
                    }

                    if (bandAmt.isGTE(tableKeyBandAmtStart) & bandAmt.isLTE(tableKeyBandAmtEnd))
                    {
                        tableKeys.add(tableKeysVOs[i]);
                    }
                }
                else
                {
                    tableKeys.add(tableKeysVOs[i]);
                }
            }
        }

        tableKeysVO = stateMatch(tableKeys);

        return tableKeysVO;
    }

    /**
     * From the tableKeys that matched so far, get the one for the state requested.
     * @param tableKeys
     * @return   tableKeysVO
     */
    private TableKeysVO stateMatch(List tableKeys)
    {
        TableKeysVO tableKeysVO = null;
        String defaultState = "NotApplicable";
        int defaultAreaIndex = 0;
        int exactMatchIndex = 0;
        boolean exactAreaMatchFound = false;
        boolean defaultAreaMatchFound = false;

        for (int i = 0; i < tableKeys.size(); i++)
        {
            tableKeysVO = (TableKeysVO) tableKeys.get(i);

            if (tableKeysVO.getState().equalsIgnoreCase(areaId))
            {
                exactAreaMatchFound = true;
                exactMatchIndex = i;
            }

            if (tableKeysVO.getState().equalsIgnoreCase(defaultState))
            {
                defaultAreaMatchFound = true;
                defaultAreaIndex = i;
            }
        }

        if (defaultAreaMatchFound && !exactAreaMatchFound)
        {
            tableKeysVO = (TableKeysVO) tableKeys.get(defaultAreaIndex);
        }

        if (exactAreaMatchFound)
        {
            tableKeysVO = (TableKeysVO) tableKeys.get(exactMatchIndex);
        }

        return tableKeysVO;
    }

    /**
     * Returns true if the value = "NotApplicable" (Case insensitive)
     * @param value
     * @return
     */
    private boolean isDefaultValue(String value)
    {
        boolean isDefaultValue = false;

        if (value.equalsIgnoreCase("NotApplicable"))
        {
            isDefaultValue = true;
        }

        return isDefaultValue;
    }
    
    /**
     * Returns true if the value = "*" 
     * @param value
     * @return
     */
    private boolean isDefaultUserKeyValue(String value)
    {
        boolean isDefaultValue = false;

        if (value.equalsIgnoreCase("*"))
        {
            isDefaultValue = true;
        }

        return isDefaultValue;
    }
}
