/*
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: May 29, 2002
 * Time: 12:07:00 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package engine.sp;


import edit.common.EDITBigDecimal;

import java.util.HashMap;
import java.util.Map;

public final class Vectorlumpsum  extends InstOneOperand  {

    //member variables

    /**
     * Vectorlumpsum constructor
     * <p>
     * @exception SPException
     */
     public Vectorlumpsum() throws SPException {

        super();
     }

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

    }

    /**
     *  Executes the instruction
     *  <p>
     */
   	protected void exec(ScriptProcessor execSP)  {

        sp = execSP;

        Map ht = sp.getVectorEntry("LumpSum");

        if (ht == null)
        {

            ht = createLumpSum();
            ht = adjustLumpSum(ht);
        }
        else
        {

            ht = adjustLumpSum(ht);
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
        sp.addVectorEntry("LumpSum", ht);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    private Map createLumpSum() {

        int mtm = Integer.parseInt((String)sp.getWSEntry("MonthsToMaturity"));
        mtm += 1;

        //get the start date from the ws
//        String payoutStartDate  = ((String) sp.getWSEntry("StartDate"));

//        Double zero = new Double (0);
        EDITBigDecimal zero = new EDITBigDecimal("0");
        String currMonth = null;
        Map vector = new HashMap();

        for (int i = 1; i < mtm; i++) {

            currMonth = new Integer(i).toString();
            vector.put(currMonth, zero);
        }
        return vector;
    }

       private Map adjustLumpSum(Map ht){

        int startMonth = Integer.parseInt((String)sp.getWSEntry("StartMonth"));
        int totalMonths = Integer.parseInt((String)sp.getWSEntry("TotalMonths"));
//        double amount    =  Double.parseDouble((String)sp.getWSEntry("Amount"));
        String frequencyLiteral = ((String) sp.getWSEntry("Frequency"));
        EDITBigDecimal amount = new EDITBigDecimal((String)sp.getWSEntry("Amount"));

        int modeCnt =0;
        modeCnt = getFrequencyCnt(frequencyLiteral);

        int cnt = 0;
        for (int j = startMonth; cnt < totalMonths; j++) {

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
