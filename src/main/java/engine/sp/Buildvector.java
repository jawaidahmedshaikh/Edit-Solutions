/*
 * Buildvector.java      01/04/2002
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import com.javathings.math.Eval;
import com.javathings.math.cannotConvertException;
import edit.common.vo.RulesVO;
import edit.common.vo.TableDefVO;
import edit.common.EDITBigDecimal;
import engine.dm.dao.DAOFactory;
import fission.utility.Util;

import java.util.*;

/**
 * This is the implementation for the Buildvector instruction.
 * The Buildvector instruction is used to build a vector for the
 * parameters specified in the script lines.
 * ScriptProcessor has removed all Vector instructions from the script and replaced them with the
 * Buildvector instruction.  The Vector instructions are stored in ScriptProcessor in a Map called
 * vectorParams.  This instruction gets those parameters and builds rate tables accordindingly.
 */
public final class Buildvector extends InstOneOperand
{

    Map vectorParams;
//    int yrCnt = 0;
    int ind = 0;
    int mtm = 0;
    int ruleSub = 0;
    int payeeCounter = 0;
    int relationCnt = 0;
    int certainPeriod = 0;
    Map ws;
    Map vector = new HashMap();
    Hashtable vars = new Hashtable();
    Map ht = new HashMap();
    List varTable = new ArrayList();
    List payeeVector = new ArrayList();
    String tableName = null;
    String name = null;
    String type = null;
    String result = null;
    List rules = new ArrayList();
    String cgInd = null;
    String mode = null;
    String ruleName = null;
    String tableType = null;
    String tkType = null;
    String var = null;
    String frequencyMode = null;
//    boolean payeesExist = false;
    String relationId = null;
    String payoutOption = null;
//    double allocIntRate = 0;
//    double zero = 0;
    EDITBigDecimal zero;

    /**
     * Buildvector constructor
     * <p/>
     * @throws SPException
     */
    public Buildvector() throws SPException
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

        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();

    }

    /**
     * Executes the instruction
     * <p/>
     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        vector.clear();

        sp = execSP;
        //save instruction pointer to restore later
        int saveInstPtr = 0;
        saveInstPtr = sp.getInstPtr();

        String variableInst = (String) vectorParams.get("Variable");
        name = (String) vectorParams.get("Name");
        type = (String) vectorParams.get("Type");
        String calcTemp = (String) vectorParams.get("Calc");
        String calc = calcTemp.toLowerCase();


        //setting monthsToMaturity, frequency
        getParameters();
        mtm += 1;

        //determine how many variables
        processVariables(variableInst);

        //apply rates to calc
        vector = doCalc(ht, calc);

        //store vector under name in hashtable
        sp.addVectorEntry(name, vector);

        //reset InstPtr
        sp.setInstPtr(saveInstPtr += 1);

//************************************** test code
//        if (name.equalsIgnoreCase("Glpcoi")) {
//            printVector(vector);
//        }
//************************************** test code
    }

    private void processVariables(String variableInst)  throws SPException
    {
        String[] tokens = Util.fastTokenizer(variableInst, ";");

        for (int i = 0; i < tokens.length; i++)
        {

            int ind = tokens[i].indexOf("=");
            String variable = ((tokens[i].substring(0, ind)).toLowerCase()).trim();   //left side
            String varEquation = tokens[i].substring(ind + 1);                  //right side
            String[] equations = null;

            //The variable can be defined with an if statement for rate processing
            if (varEquation != null)
            {
                if (varEquation.startsWith("if"))
                {
                    equations = buildEquation(varEquation);
                }
            }

            varTable.add(variable);

            ht = processRates(variable, i, equations);
        }    //end for
    }

    /**
     * From the parameters in the Variable instruction, build the if statment equation
     * @param varEquation
     * @return  String array of the equation and the true and false conditions
     */
    private String[] buildEquation(String varEquation)
    {
        int index = 0;
        String[] equationDef = Util.fastTokenizer(varEquation, ",");
        String equation = null;
        String[] variableArray = new String[3];

        for (int i = 0; i < equationDef.length; i++)
        {
            index = equationDef[i].indexOf("=");
            String var = equationDef[i].substring(index + 1);
            if (var.equals("MonthCounter"))
            {
                equation = var;
            }
            else
            {
                if (equationDef[i].startsWith("true"))
                {
                    variableArray[1] = var;
                }
                 else if (equationDef[i].startsWith("false"))
                {
                    variableArray[2] = var.substring(0, var.length() - 1);
                }
                else
                {
                    if (var.startsWith("ws:"))
                    {
                        int colonIndex = var.indexOf(":");
                        String operandDataName = var.substring(colonIndex + 1);
                        var = (String) sp.getWSEntry(operandDataName);
                    }

                    equation = equation + var;
                }
            }

        }

        variableArray[0] = equation;
        return variableArray;
    }

    /**
     * For each varable defined, a string array is built for the rates needed. If an equation array is not
     * empty, it will be used to build the Map with the correct rates.
     * Payee processing not being applied at this time.
     * @param variable
     * @param occurrenceOfTokens
     * @param equations
     * @return Map of rates from 1 to months to maturity
     * @throws SPException
     */
    private Map processRates(String variable, int occurrenceOfTokens, String[] equations) throws SPException
    {
        ruleSub = 0;
        if (occurrenceOfTokens > 0)
        {
            ruleSub = occurrenceOfTokens + (occurrenceOfTokens * 2);
        }

		rules = (List) vectorParams.get("Rules");

//get the table rules
        ruleName = (String) rules.get(ruleSub);
        tableType = (String) rules.get(ruleSub += 1);
        mode = (String) rules.get(ruleSub += 1);
        ruleSub += 1;

        getTableName();

        String stringRate [] = new String[mtm];

        buildVectorOfRates(stringRate, equations);

        ht.put(variable, stringRate);
        return ht;
    }

    /**
     * The calc vector instruction can define the rounding factor for each rate.  If the instruction
     * starts with "ROUND", the rounding number is parsed out and used to define the scale of each rate.
     * @param ht
     * @param calc
     * @return Map of rates from 1 to Month to Maturity
     */
    private Map doCalc(Map ht, String calc)
    {
        int roundValue = 0;
        if (calc.startsWith("round"))
        {
            int index1 = calc.indexOf("(");
            int index2 = calc.indexOf(",");
            int index3 = calc.lastIndexOf(")");
            String val = calc.substring(index2 + 1, index3);
            roundValue = Integer.parseInt(val);
            calc = calc.substring(index1 + 1, index2);
        }
        else
        {
            roundValue = 10;
        }

        Eval eval = new Eval();

        try
        {
            vector.put("0", new EDITBigDecimal("1", roundValue));

            for (int j = 1; j < mtm; j++)
            {

                for (int i = 0; i < varTable.size(); i++)
                {

                    var = ((String) varTable.get(i)).trim();
                    String stringRate [] = (String[]) ht.get(var);
                    vars.put(var, stringRate[j]);
                }

                String currMonth = null;

                vars.put("m", currMonth = new Integer(j).toString());

                //This class returns a double
                double vectorValue = eval.eval(calc, vars);

                vector.put(currMonth, new EDITBigDecimal(vectorValue + "", roundValue));
            }

            //Type VectorProd multi prior result and current to get current
            if (type.equalsIgnoreCase("VectorProd"))
            {

                vector = multiplyRates(vector, roundValue);
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        return vector;

    }

    /**
     * When a string array of equations exist, it will be applied to the rates.
     * @param stringRate
     * @param equations
     * @return
     * @throws SPException
     */
    private String[] buildVectorOfRates(String stringRate[], String[] equations) throws SPException
    {

        String tempName = tableName + cgInd;

        boolean nameFound = checkTablesActivated(tempName);

        //currently not set 09/28/04
        EDITBigDecimal allocIntRate = new EDITBigDecimal("0");

        //check if air to be used
        boolean airFound = false;

        if (name.equalsIgnoreCase("Interest"))
        {

            if (!allocIntRate.isEQ("0"))
            {

                airFound = true;
            }
        }

        //get the table requested through activate
        if ((nameFound == false) && (airFound == false))
        {
            processTable();
        }

        tableName = tempName;
        List f = null;

        //Need tableName found for ruleName not the variable
        if (airFound == false)
        {
            f = sp.getFunctionEntry(tableName);
        }

        int k = 12;
        int ctr = 1;
        String vectorValue = "0";
        int yrCnt = getYearCounter() - 1;
        EDITBigDecimal rate = new EDITBigDecimal("0");

        int modeCnt = getModeCnt();

        for (int i = 1; i < mtm; i++)
        {

            if (i == ctr)
            {
                ctr += modeCnt;

                if (airFound == false)
                {
                    if (equations == null)
                    {
                        rate = ((EDITBigDecimal) (f.get(yrCnt)));
                    }
                    else
                    {
                        try
                        {
                            boolean notUseTableRate = applyEquation(equations, i);
                            if (notUseTableRate)
                            {
                                rate = new EDITBigDecimal(equations[1]);
                            }
                            else
                            {
                                rate = ((EDITBigDecimal) (f.get(yrCnt)));
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    rate = allocIntRate;
                }

                stringRate[i] = (rate + "");

            }
            else
            {
                stringRate[i] = vectorValue;
            }
            int l = (i % k);
            if (l == 0)
            {
                yrCnt = (yrCnt + 1);
            }
        }

        if ((name.equalsIgnoreCase("Mortality")) && (payoutOption.equalsIgnoreCase("LifePerCert")))
        {

            String zero = "0";
            certainPeriod += 1;
            for (int x = 1; x < certainPeriod; x++)
            {

                stringRate[x] = zero;
                //System.out.println(x + ":" + stringRate[x]);
            }
        }

        return stringRate;
    }

    private int getYearCounter()
    {
        int yearCounter = (Integer.parseInt((String) (sp.getWSEntry("YearCounter"))));
        return yearCounter;
    }

    /**
     * For the equation defined with the variable, execute it using the Eval api.  If a value of 0.0
     * get returned the equation is false and set the false value.  A return value of 1.0, signifies the
     * equation is true, set the true value.
     * @param equations
     * @param i
     * @return
     * @throws Exception
     */
    private boolean applyEquation(String[] equations, int i) throws Exception
    {
        Eval e = new Eval();
        String equation = equations[0];

        if (equation != null)
        {
            if (equation.startsWith("MonthCounter"))
            {
                int index = equation.indexOf("<");
                equation = i + equation.substring(index);

            }

            try
            {
                double result = e.eval(equation);
                if (result == 0.0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            catch (cannotConvertException e1)
            {
                System.out.println(e1);
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * This method is used only if the table requested has not been activated
     * @throws SPException
     */
    private void processTable() throws SPException
    {
        sp.addWSEntry("CurrGuarInd", cgInd);
        // Put table name onto the stack
        sp.push(tableName);

        //exec activate
        Activate activate = new Activate();
        activate.compile(sp);
        activate.exec(sp);
    }

    /**
     * For the ruleName define in the vector instructions, get the tableName.
     */
    private void getTableName()
    {
        String dateTypeAccessInd = Util.initString((String) sp.getWSEntry("DateTypeAccessInd"), "N");
        String effectiveDate = null;

        //From the rule get the table
        RulesVO rulesVO = null;
        if (dateTypeAccessInd.equalsIgnoreCase("N"))
        {
            rulesVO = sp.getProductRule().getBestMatchTableId(ruleName);
        }
        else
        {
            effectiveDate =  (String) sp.getWSEntry("ContractEffectiveDate");
            rulesVO = sp.getProductRule().getBestMatchTableId(ruleName, effectiveDate);
        }

        long tableId = rulesVO.getTableDefFK();

        TableDefVO tableDefVO = CSCache.getCSCache().getTableDefVOBy_TableDefPK(tableId);

        tableName = tableDefVO.getTableName();

        //Store tableType as the CurrGuar
        if (tableType.equals("None"))
        {
            cgInd = "Current";
        }
        else
        {
            cgInd = tableType;
        }

//         sp.addWSEntry("CurrGuarInd", cgInd);
    }

    /**
     * An activated table will be in the scriptProcessor functions map
     * @param tableName
     * @return
     */
    private boolean checkTablesActivated(String tableName)
    {

        String[] names = sp.getFunctions();
        boolean nameFound = false;
        for (int i = 0; i < names.length; i++)
        {
            if (tableName.equals(names[i]))
            {

                nameFound = true;
                break;
            }
        }
        return nameFound;
    }

    /**
     * Based on the mode defined by the vector instruction, set the modeCnt.
     * @return   modeCnt
     */
    private int getModeCnt()
    {

        int modeCnt = 0;

        if (mode.equalsIgnoreCase("Frequency"))
        {
            mode = frequencyMode;

        }

        if (mode.equals("12"))
        {
            modeCnt = 1;
        }
        else if (mode.equals("04"))
        {
            modeCnt = 3;
        }
        else if (mode.equals("02"))
        {
            modeCnt = 6;
        }
        else if (mode.equals("01"))
        {
            modeCnt = 12;
        }

        return modeCnt;
    }

    /**
     * For vectory type VectorProd, the rates need a final adjustment
     * @param vector
     * @return
     */
    private Map multiplyRates(Map vector, int roundValue)
    {

        int j = 1;
        for (int i = 0; j < mtm; i++)
        {

            String currMonth = new Integer(i).toString();
            EDITBigDecimal rate1 = (EDITBigDecimal) vector.get(currMonth);

            currMonth = new Integer(j).toString();
            EDITBigDecimal rate2 = (EDITBigDecimal) vector.get(currMonth);

            rate2 = rate2.multiplyEditBigDecimal(rate1);

            vector.put(currMonth, rate2.round(roundValue));
            j++;

        }

        return vector;
    }

    /**
     * For scriptProcessor and the vector script lines set up the constants.
     */
    private void getParameters()
    {
        String monthsToMaturity = (String) sp.getWSEntry("MonthsToMaturity");
        if (monthsToMaturity.endsWith(".0"))
        {
            monthsToMaturity = monthsToMaturity.substring(0, monthsToMaturity.lastIndexOf("."));
        }
        mtm = Integer.parseInt(monthsToMaturity);

//get frequency
        String frequencyLiteral = (String) sp.getWSEntry("Frequency");
        if (frequencyLiteral == null)
        {

            frequencyLiteral = "Monthly";
        }

        if (frequencyLiteral.equalsIgnoreCase("Annual"))
        {
            frequencyMode = "01";
        }
        else if (frequencyLiteral.equalsIgnoreCase("SemiAnnual"))
        {
            frequencyMode = "02";
        }
        else if (frequencyLiteral.equalsIgnoreCase("Quarterly"))
        {
            frequencyMode = "04";
        }
        else if (frequencyLiteral.equalsIgnoreCase("Monthly"))
        {
            frequencyMode = "12";
        }
        else
            throw new RuntimeException("Frequency not valid for vector");

        if (name.equalsIgnoreCase("Mortality"))
        {

            payoutOption = (String) sp.getWSEntry("OptionCode");

            if (payoutOption == null)
            {

                payoutOption = "Life";
            }

            if (payoutOption.equalsIgnoreCase("LifePerCert"))
            {
                certainPeriod = (Integer.parseInt((String) (sp.getWSEntry("CertainDuration"))));
            }
        }

//get param air for Interest List
//temp change for demo 3-10-04
//        if (name.equalsIgnoreCase("Interest"))  {
//
//            allocIntRate = Double.parseDouble((String)sp.getWSEntry("Air"));
//        }
    }

    public void setVectorParams(Map vp)
    {

        vectorParams = vp;
    }


//    private void printVector(Map vector){
//
//        Iterator enum = vector.keySet().iterator();
//
//        while (enum.hasNext()) {
//
//            String key = (String) enum.next();
//            EDITBigDecimal ebd   = (EDITBigDecimal) vector.get(key);
//
//            System.out.println(key + " : " + ebd.toString());
//        }
//    }



}