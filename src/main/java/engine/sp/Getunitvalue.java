/*
 * Getunitvalue.java       02/13/2002
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.UnitValuesVO;
import engine.dm.dao.DAOFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * This is the implementation for the Getunitvalue instruction.
 * The Getunitvalue instruction is used to get a unit value for the
 * filtered fund number and counter on the data stack.
 * <p/>
 * Example:
 * push ws:FiltereFundPK
 * Getunitvalue
 */
public final class Getunitvalue extends InstOneOperand
{

    /**
     * Getunitvalue constructor
     * <p/>
     * @throws SPException
     */
    public Getunitvalue() throws SPException
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

        //get the filteredFundId
        long fundId = Long.parseLong((String) sp.getWSEntry("FilteredFundId"));

        String effectiveDate = (String) sp.getWSEntry("UnitValueDate");

        FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(fundId);

        String pricingDirection = filteredFundVO.getPricingDirection();


        UnitValuesVO[] unitValuesVO = DAOFactory.getUnitValuesDAO().
                findUnitValuesByFilteredFundIdDate(
                        fundId, effectiveDate, pricingDirection);



        if (chargeCodesExistForFilteredFund(filteredFundVO))
        {
            long chargeCodePK = 0L;

            String chargeCodePKStr = (String) sp.getWSEntry("ChargeCodePK");

            chargeCodePK = Long.parseLong(chargeCodePKStr);

            // Keep logic off DAO method.  Put it here.
            if (chargeCodePK != 0L)
            {
                unitValuesVO = filterUnitValuesForChargeCode(unitValuesVO, chargeCodePK);
            }

        }


        EDITBigDecimal unitValue = new EDITBigDecimal("0");

        try
        {
            if (unitValueType.equalsIgnoreCase("Accumulation"))
            {
                unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
            }
            else if (unitValueType.equalsIgnoreCase("Annuity"))
            {
                unitValue = new EDITBigDecimal(unitValuesVO[0].getAnnuityUnitValue());
            }
        }
        catch (Exception e)
        {
            throw new SPException(e.getMessage(), SPException.INSTRUCTION_PROCESSING_ERROR);

        }

        String uvEffDate = new String(unitValuesVO[0].getEffectiveDate());
        sp.addWSEntry("UnitValueEffectiveDate", uvEffDate);

        sp.push(unitValue.toString());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    /**
     * Filter out the UnitValues value objects by chargeCodeFK
     * @param unitValuesVO
     * @param chargeCodePK
     * @return
     */
    private UnitValuesVO[] filterUnitValuesForChargeCode(UnitValuesVO[] unitValuesVO, long chargeCodePK)
    {

        // walk thru them and keep only the ones that have our chargeCodeFK
        List unitValuesForChargeCodes = new ArrayList();

        for (int i = 0; i < unitValuesVO.length; i++)
        {
            UnitValuesVO unitValueVO = unitValuesVO[i];

            long fk = unitValueVO.getChargeCodeFK();

            if (fk == chargeCodePK)
            {
                unitValuesForChargeCodes.add(unitValueVO);
            }

        }

        return (UnitValuesVO[]) unitValuesForChargeCodes
                .toArray(new UnitValuesVO[unitValuesForChargeCodes.size()]);

    }

    /**
     * return true if the filtered fund has charge codes that have been
     * assigned to it.
     * @param filteredFundVO
     * @return
     */
    private boolean chargeCodesExistForFilteredFund(FilteredFundVO filteredFundVO)
    {
        return "Y".equalsIgnoreCase(filteredFundVO.getChargeCodeIndicator());
    }
}
