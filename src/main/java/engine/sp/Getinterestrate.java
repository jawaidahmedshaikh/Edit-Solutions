/*
 * Getinterestrate.java      03/20/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITDate;
import edit.common.vo.InterestRateParametersVO;
import edit.common.vo.InterestRateVO;
import engine.dm.dao.DAOFactory;

import java.util.*;

import fission.utility.Util;

/**
 * This is the implementation for the Getinterestrate instruction.
 * For the Filtered Fund key and date in ws, access the Interest Rates.
 * Build a map of the interest rates that qualify and their effective dates.
 * These are added to a list. Which can be access later by the push instruction.
 */
public final class Getinterestrate extends InstOneOperand {

	public static String RATE_DATE_DATA = "RateDateData";

    public EDITDate lastValuationDate = null;
    public EDITDate trxEffectiveDate = null;
    public String transactionEffectiveDate = null;

    /**
     * Getinterestrate constructor
     * <p>
     * @exception SPException
     */
     public Getinterestrate() throws SPException {

        super();
     }

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction


//        //Does common tasks in superclass such as
//        //setting member variables and some operand editting
//        commonCompileTasks();
//
//	    // The operandDataName contains the rulename of the instruction
//	    setOperandDataName(extractOperandDataName());


    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while
     *       executing the instruction
     */
   	public void exec(ScriptProcessor execSP) throws SPException {

		sp = execSP;
        ProductRuleProcessor pr = sp.getProductRule();

        long fundId = Long.parseLong((String)sp.getWSEntry("FilteredFundId"));
        String intRateDate = (String) sp.getWSEntry("IntRateDate");
        String lastValDate = (String)sp.getWSEntry("LastValuationDate");
		lastValuationDate = new EDITDate(lastValDate);

        transactionEffectiveDate = (String)sp.getWSEntry("TrxEffectiveDate");
		trxEffectiveDate = new EDITDate(transactionEffectiveDate);

		//get the Option from working Storage
		String option = (String)sp.getWSEntry("Option");

		//go to interest table and get all the rows having original date
		//less than effective date and option  for filtered fund specified
		InterestRateVO[] interestRateVOs = null;
		InterestRateParametersVO[] interestRateParamVO = DAOFactory.getInterestRateParametersDAO().
                findInterestRatesByOriginalDateOptionFund(intRateDate, option, fundId);

        if (interestRateParamVO != null)
        {
		    interestRateVOs = interestRateParamVO[0].getInterestRateVO();
        }

        //check option value for the processing path to take Bonus or Declared
        if (option.equalsIgnoreCase("Bonus"))
        {
            setBonusInterestRates(interestRateParamVO, interestRateVOs, intRateDate);
        }
        else
        {
            setDeclaredInterestRates(interestRateVOs, lastValDate);
        }

        // Increment instruction pointer
        sp.incrementInstPtr();

    }

    private void setBonusInterestRates(InterestRateParametersVO[] interestRateParamVO, InterestRateVO[] interestRateVOs, String intRateDate)
    {
        if (interestRateParamVO == null)
        {
            sp.addWSEntry("BonusCalcInd", "N");
        }
        else
        {
            try
            {
                processRates(interestRateVOs, intRateDate);

                sp.addWSEntry("BonusCalcInd", "Y");
                sp.addWSEntry("StopDate", interestRateParamVO[0].getStopDate());
                sp.addWSEntry("GuarDur", interestRateVOs[0].getGuaranteeDuration());
            }
            catch(Exception e)
            {
                throw new RuntimeException("Getinterestrate.exec() - Could Not Set Bonus Interest Rates");
            }
        }
    }
    private void setDeclaredInterestRates(InterestRateVO[] interestRateVOs, String lastValDate)
    {
        if (interestRateVOs == null)
        {
            throw new RuntimeException("Getinterestrate.exec() - No rates found");
        }

        try
        {
            processRates(interestRateVOs, lastValDate);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Getinterestrate.exec() - Could Not Process Rates");
        }

    }

    private void processRates(InterestRateVO[] interestRateVOs, String lastValDate) throws Exception
    {
        //Eliminate interest rate records past the intRateDate - all were retrieved
        List interestRateVOToUse = new ArrayList();
        int compareNumber = 0;

        long interestRateParametersPK = 0;

        for (int j = 0; j < interestRateVOs.length; j++)
        {
            if (interestRateParametersPK == 0)
            {
                interestRateParametersPK = interestRateVOs[j].getInterestRateParametersFK();
            }

            compareNumber = interestRateVOs[j].getEffectiveDate().compareTo(transactionEffectiveDate);
            if (compareNumber <= 0)
            {
                compareNumber = interestRateVOs[j].getEffectiveDate().compareTo(lastValDate);

                if (compareNumber >= 0)
                {
                    interestRateVOToUse.add(interestRateVOs[j]);
                }
            }
        }

        InterestRateVO[] singleIntRateVO = DAOFactory.getInterestRateDAO().findInterestRateByIdAndEffectiveDate(interestRateParametersPK, lastValDate);

        if (singleIntRateVO != null)
        {
            interestRateVOToUse.add(singleIntRateVO[0]);
        }

        InterestRateVO[] interestRateVO = (InterestRateVO[])interestRateVOToUse.toArray(new InterestRateVO[interestRateVOToUse.size()]);

        interestRateVO = (InterestRateVO[]) Util.sortObjects(interestRateVO, new String[] {"getEffectiveDate"});

		List dateRateVector = new ArrayList();
        Map dateRateData;
        String interestRateDuration = null;

		for (int i = 1; i < interestRateVO.length; i++)
		{
			String effectiveDateFromTable = interestRateVO[i].getEffectiveDate();
			EDITDate effDate = new EDITDate(effectiveDateFromTable);

			boolean tt1 = effDate.before(lastValuationDate);
			boolean tt2 = effDate.before(trxEffectiveDate);

			if (tt1 && tt2)
			{
				dateRateData = new HashMap();

				dateRateData.put("Date", effectiveDateFromTable);

				String interestRate  = (interestRateVO[i - 1].getRate()) + "";

				dateRateData.put("Rate", interestRate);

                String rateEffDate = interestRateVO[i - 1].getEffectiveDate();

                dateRateData.put("RateEffectiveDate", rateEffDate);

				dateRateVector.add(dateRateData);

                interestRateDuration = interestRateVO[i - 1].getGuaranteeDuration();
			}
		}

        //process last entry in table
        dateRateData = new HashMap();
        dateRateData.put("Date", transactionEffectiveDate);
        String interestRate = (interestRateVO[interestRateVO.length - 1].getRate()) + "";
        dateRateData.put("Rate", interestRate);
        String rateEffDate = interestRateVO[interestRateVO.length - 1].getEffectiveDate();
        dateRateData.put("RateEffectiveDate", rateEffDate);
        dateRateVector.add(dateRateData);

		//add daterate data to script processor
		sp.addWSVector(RATE_DATE_DATA, dateRateVector);

		//set the dateCounter
		sp.addWSEntry("DateCounter", new String(dateRateVector.size() + ""));

        if (interestRateDuration == null)
        {
            interestRateDuration = interestRateVO[interestRateVO.length - 1].getGuaranteeDuration();
            sp.addWSEntry("CurrentGuarDuration", interestRateDuration);
        }

    }
}