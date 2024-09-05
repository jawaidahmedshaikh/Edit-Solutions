/*
 * Adjust.java       06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;

import java.util.Map;


/**
 * This is the implementation for the Adjust instruction.
 * The Adjust instruction is used to change the rates of a vectorpayout of lumpsum.
 *
 * <p>
 */
public final class Adjust extends InstOneOperand {

    //Member variables
    int saveInstPtr = 0;

    /**
     * Vectorpayout constructor
     * <p>
     * @exception SPException
     */
     public Adjust() throws SPException {

        super();
     }

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        commonCompileTasks();
    }

    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;

        //save instruction pointer to restore later
		saveInstPtr = sp.getInstPtr();

        int mtm = Integer.parseInt(sp.getWSEntry("MonthsToMaturity"));
        mtm += 1;

        String vectorName = getOperand();
        //Might have to adjust name for just Payout of Lumpsum
        Map ht = sp.getVectorEntry(vectorName);


        //get the payout option, modal amount and frequency and start date from the params
        String frequencyLiteral = ((String) sp.getWSEntry("Frequency"));

        int startMonth = Integer.parseInt((String)sp.getWSEntry("StartMonth"));
        int totalMonths = Integer.parseInt((String)sp.getWSEntry("TotalMonths"));
        String costOfLivingInd = ((String) sp.getWSEntry("CostOfLivingInd"));
//        double amount    =  Double.parseDouble((String)sp.getWSEntry("Amount"));
        EDITBigDecimal amount = new EDITBigDecimal((String)sp.getWSEntry("Amount"));
//        double percent   =  Double.parseDouble((String)sp.getWSEntry("Percent"));
        EDITBigDecimal percent = new EDITBigDecimal((String)sp.getWSEntry("Percent"));
        String transaction = (String)sp.getWSEntry("TrxType");

        int modeCnt = 0;
        if (frequencyLiteral.equalsIgnoreCase("Annual")) {
            modeCnt = 12;
        } else if (frequencyLiteral.equalsIgnoreCase("Semi")) {
                    modeCnt = 6;
        } else if (frequencyLiteral.equalsIgnoreCase("Quarterly")) {
                    modeCnt = 3;
        } else if (frequencyLiteral.equalsIgnoreCase("Monthly")) {
                    modeCnt = 1;
        } else  throw new SPException("Frequency Not Valid For Vector", SPException.INSTRUCTION_PROCESSING_ERROR);




        if (transaction.equalsIgnoreCase("Increase") || transaction.equalsIgnoreCase("Decrease"))
        {

            ht = adjustForIncDec(ht, startMonth, totalMonths, modeCnt, transaction, amount, costOfLivingInd, percent);
        }
        else
        {

            ht = adjustForLumpSum(ht, startMonth, totalMonths, modeCnt, amount);
        }



 // test vector creation

//        for (int i = 1; i <= vector.size(); i++) {
//
//            String key = i + "";
//            Double d   = (Double) vector.get(key);
//
//            System.out.println(key + " : " + d.toString());
//        }


        //store vector under name in hashtable
        sp.addVectorEntry(vectorName, ht);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    private Map adjustForIncDec(Map ht, int startMonth, int totalMonths, int modeCnt,
                                        String transaction, EDITBigDecimal amount, String costOfLivingInd,
                                         EDITBigDecimal percent) {

        int cnt = 0;
        int nextOccurrence = startMonth;
        EDITBigDecimal costOfLivingAmount = new EDITBigDecimal("0");
        for (int j = startMonth; j < totalMonths; j++) {

            EDITBigDecimal vectorAmount =  (EDITBigDecimal)  ht.get(new Integer(j).toString());
            EDITBigDecimal percentAmount = vectorAmount.multiplyEditBigDecimal(percent);

            vectorAmount.isEQ(costOfLivingAmount);
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

                        vectorAmount.subtractEditBigDecimal(percentAmount.getScaled(2));
                    } // end inner if
                }  //end outer if
            }

            ht.put(new Integer(j).toString(), vectorAmount);
        }
        return ht;
    }

    private Map adjustForLumpSum(Map ht, int startMonth, int totalMonths, int modeCnt, EDITBigDecimal amount)  {

        int cnt = 0;
        for (int j = startMonth; cnt < totalMonths; j++)
        {
//            Double modalAmt = null;
//            double vectorAmount = ((Double) ht.get(new Integer(j).toString())).doubleValue();
//            modalAmt = new Double (vectorAmount + amount);
            EDITBigDecimal vectorAmount = (EDITBigDecimal) ht.get(new Integer(j).toString());
            vectorAmount.addEditBigDecimal(amount);
            ht.put(new Integer(j).toString(), vectorAmount);

            j += (modeCnt - 1);
            cnt += modeCnt;
        }   // end inner for

        return ht;
    }
}