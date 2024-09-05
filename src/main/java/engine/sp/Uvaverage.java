/*
 * Getunitvalue.java      Version 1.1  02/13/2002
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

import edit.common.vo.FilteredFundVO;
import edit.common.vo.UnitValuesVO;
import edit.common.EDITBigDecimal;
import engine.dm.dao.DAOFactory;
import fission.dm.SMException;

/**
 * This is the implementation for the Getunitvalue instruction.
 * The Getunitvalue instruction is used to get a unit value for the fund
 * number and counter on the data stack.
 * <p/>
 * Example:
 * push ws:FundCtr
 * Getunitvalue
 */
public final class Uvaverage extends InstOneOperand
{

    /**
     * Getunitvalue constructor
     * <p/>
     * @throws SPException
     */
    public Uvaverage() throws SPException
    {

        super();
    }

    /**
     * Compiles the instruction
     * <p/>
     * @param aScriptProcessor Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException
    {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        //NO edit step for this instruction.  There is no operand.

    }

    /**
     * Executes the instruction
     * <p/>
     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;

        //get the UnitValueType
        String unitValueType = (String) sp.getWSEntry("UnitValueType");
        //fundId is now filteredFundId
        long fundId = Long.parseLong((String) sp.getWSEntry("FilteredFundId"));
        String avgStartDate = (String) sp.getWSEntry("AvgStartDate");
        String avgStopDate = (String) sp.getWSEntry("AvgStopDate");

        FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(fundId);

        String pricingDirection = filteredFundVO.getPricingDirection();

        UnitValuesVO[] unitValuesVO = DAOFactory.getUnitValuesDAO().
                findUnitValuesByFilteredFundIdDate(fundId, avgStartDate, pricingDirection);

        UnitValuesVO[] unitValuesVOs = null;
        if (unitValuesVO != null)
        {
            unitValuesVOs = DAOFactory.getUnitValuesDAO().findByDateRange(fundId, unitValuesVO[0].getEffectiveDate(), avgStopDate);
        }

        EDITBigDecimal avgUnitValue = new EDITBigDecimal("0");

        if (unitValuesVOs != null)
        {
            avgUnitValue = computeAverage(unitValuesVOs, unitValueType);
        }

        sp.push(avgUnitValue.toString());


        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    private EDITBigDecimal computeAverage(UnitValuesVO[] unitValuesVOs, String unitValueType)
    {
        EDITBigDecimal unitValue = new EDITBigDecimal("0");

        int count = unitValuesVOs.length;

        for (int i = 0; i < count; i++)
        {
            if (unitValueType.equalsIgnoreCase("Accumulation"))
            {

                unitValue = unitValue.addEditBigDecimal(unitValuesVOs[i].getUnitValue());
            }
            else if (unitValueType.equalsIgnoreCase("Annuity"))
            {

                unitValue = unitValue.addEditBigDecimal(unitValuesVOs[i].getAnnuityUnitValue());
            }
        }

        unitValue = unitValue.divideEditBigDecimal(count + "");

        return unitValue;
    }
}