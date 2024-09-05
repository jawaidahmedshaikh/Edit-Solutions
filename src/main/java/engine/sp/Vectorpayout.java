/*
 * Vectorpayout.java      Version 1.10  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

import fission.utility.Util;

import java.util.HashMap;
import java.util.Map;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Getrate instruction.
 * The Vectorpayout instruction is used to build rates for modal amount
 * processing.
 * <p>
 * Example:
 *
 *  Vectorpayout
 */
public final class Vectorpayout extends InstOneOperand {

    //Member variables

    /**
     * Vectorpayout constructor
     * <p> 
     * @exception SPException  
     */ 
     public Vectorpayout() throws SPException {
    
        super(); 
     }
     
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
		
    }

    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) {

        sp = execSP;

        Map ht = sp.getVectorEntry("Payout");
        String includeAdjustments = (String)sp.getWSEntry("IncludeAdjustments");

        if (ht == null)
        {

            ht = createPayout();
            if (!includeAdjustments.equalsIgnoreCase("N"))
            {
                ht = adjustPayout(ht);
            }
        }
        else
        {

            if (!includeAdjustments.equalsIgnoreCase("N"))
            {
                ht = adjustPayout(ht);
            }
        }

        //store vector under name in hashtable
        sp.addVectorEntry("Payout", ht);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    private Map createPayout() {

//        int mtm = ((Integer)sp.getWSEntry("MonthsToMaturity")).intValue();
        String monthsToMaturity = (String)sp.getWSEntry("MonthsToMaturity");
        if (monthsToMaturity.endsWith(".0"))
        {
            monthsToMaturity = monthsToMaturity.substring(0, monthsToMaturity.lastIndexOf("."));
        }
        int mtm = Integer.parseInt(monthsToMaturity);
        mtm += 1;

        //get the modal amount, frequency and start date from ws
        String frequencyLiteral = (String) sp.getWSEntry("Frequency");
        String modalValue = (String)sp.getWSEntry("ModalAmount");
//        double modalAmount = 0;
        EDITBigDecimal modalAmount = new EDITBigDecimal("0");

        if (modalValue != null)
        {
            modalAmount = new EDITBigDecimal(modalValue + "");
        }

        int modeCnt =0;
        modeCnt = getFrequencyCnt(frequencyLiteral);

        int ctr = 1;
//        Double zero = new Double (0);
        EDITBigDecimal zero = new EDITBigDecimal("0");
        String currMonth = null;
        Map vector = new HashMap();

        //Fill the vector with the modal amount based on frequency
        for (int i = 1; i < mtm; i++) {

            if (i == ctr){
                ctr += modeCnt;
                currMonth = new Integer(i).toString();
//                Double modalAmt = new Double (modalAmount);
                vector.put(currMonth, modalAmount);
            } else {
                currMonth = new Integer(i).toString();
                vector.put(currMonth, zero);
            }
        }

        return vector;
    }

    private Map adjustPayout(Map ht)  {

        int startMonth = Integer.parseInt((String)sp.getWSEntry("StartMonth"));
        int totalMonths = Integer.parseInt((String)sp.getWSEntry("TotalMonths"));
        String costOfLivingInd = (String) sp.getWSEntry("CostOfLivingInd");
        EDITBigDecimal amount = new EDITBigDecimal((String)sp.getWSEntry("Amount"));
        EDITBigDecimal percent = new EDITBigDecimal((String)sp.getWSEntry("Percent"));
        String transaction = (String)sp.getWSEntry("TrxType");
        String frequencyLiteral = (String) sp.getWSEntry("Frequency");

        int modeCnt =0;
        modeCnt = getFrequencyCnt(frequencyLiteral);

        int cnt = 0;
        int nextOccurrence = startMonth;
        EDITBigDecimal costOfLivingAmount = new EDITBigDecimal("0");

        for (int j = startMonth; j < totalMonths; j++) {

            EDITBigDecimal vectorAmount = (EDITBigDecimal) ht.get(new Integer(j).toString());
            EDITBigDecimal percentAmount = vectorAmount.multiplyEditBigDecimal(percent);

            vectorAmount.addEditBigDecimal(costOfLivingAmount);

            if (j == nextOccurrence) {

                nextOccurrence = j + modeCnt;
                if (transaction.equalsIgnoreCase("Increase")) {
                    if (!amount.isEQ("0")) {

                        vectorAmount.addEditBigDecimal(amount);
                        if (costOfLivingInd.equalsIgnoreCase("Y")) {

                            costOfLivingAmount.addEditBigDecimal(amount);
                        }
                    }
                    else if (!percent.isEQ("0")) {

                        vectorAmount.addEditBigDecimal(percentAmount);
                        if (costOfLivingInd.equalsIgnoreCase("Y")) {

                            costOfLivingAmount.addEditBigDecimal(percentAmount);
                        }
                    } // end inner if
                }
                else {

                    if (!amount.isEQ("0")) {

                        vectorAmount.subtractEditBigDecimal(amount);
                    }
                    else {

                        vectorAmount.subtractEditBigDecimal((percentAmount).getScaled(2));
                    } // end inner if
                }  //end outer if
            }

            ht.put(new Integer(j).toString(), vectorAmount);
        }
        return ht;
    }

    public int getFrequencyCnt(String frequency) {

        int modeCnt = 0;
        if (frequency.equalsIgnoreCase("Annual")) {
            modeCnt = 12;
        }
        else if (frequency.equalsIgnoreCase("SemiAnnual")) {
            modeCnt = 6;
        }
        else if (frequency.equalsIgnoreCase("Quarterly")) {
            modeCnt = 3;
        }
        else if (frequency.equalsIgnoreCase("Monthly")) {
            modeCnt = 1;
        }
        else {
            throw new RuntimeException("Frequency not valid for vector");
        }

        return modeCnt;
    }
}