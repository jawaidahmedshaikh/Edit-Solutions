/*
 * User: gfrosti
 * Date: Aug 24, 2004
 * Time: 10:10:36 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */


package engine.sp;

import engine.common.*;
import junit.framework.*;

import java.math.*;

import edit.common.*;
import fission.utility.*;

/**
 * Verifies that a numeric value is within a specified min/max range.
 */
public class Validateminmax extends ValidateInst
{
    private EDITBigDecimal actualValue;
    private EDITBigDecimal expectedMinValue;
    private EDITBigDecimal expectedMaxValue;
    private String expression;
    private EDITBigDecimal tolerance;

    public Validateminmax()
    {
        super();
    }

    /**
     * Valdates that a numeric value satisfies the supplied min/max expression.
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        expression = sp.popFromStack();

        tolerance = new EDITBigDecimal(sp.popFromStack());

        expectedMaxValue = new EDITBigDecimal(sp.popFromStack());
        expectedMaxValue = expectedMaxValue.addEditBigDecimal(tolerance);

        expectedMinValue = new EDITBigDecimal(sp.popFromStack());
        expectedMinValue = expectedMinValue.subtractEditBigDecimal(tolerance);

        actualValue = new EDITBigDecimal(sp.peekFromStack());

        boolean validInequality;

        Comparator c = new Comparator();

        String[] inequalityTokens = Util.fastTokenizer(expression, ",");

        String leftToken = inequalityTokens[0].trim();
        String rightToken = inequalityTokens[1].trim();

        validInequality = c.compareNumericInequality(expectedMinValue, actualValue, Comparator.getOperator(leftToken));
        
        if (validInequality)
        {
            validInequality = c.compareNumericInequality(actualValue, expectedMaxValue, Comparator.getOperator(rightToken));
        }

        try
        {
            if (!validInequality)
            {
                throw new SPException(Constants.ValidateErrorMsg.NUMERIC_EQUALITY_ERROR, SPException.VALIDATION_ERROR);
            }
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    /**
     * @see ValidateInst#getStack()
     * @return
     */
    String getStack()
    {
        return "actual:[" + actualValue.toString() + "] expected:[" + expectedMinValue.toString() + ", " + expectedMaxValue.toString() + "] tolerance:[" + tolerance + "]";
    }
}
