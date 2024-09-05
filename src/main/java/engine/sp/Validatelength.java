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
 * Verifies that an actual float value is within the accepted tolerance of an expected float value.
 */
public class Validatelength extends ValidateInst
{
    private String aliasValue;
    private EDITBigDecimal minValue;
    private EDITBigDecimal maxValue;

    public Validatelength()
    {
        super();
    }

    /**
     * Valdates that a numeric value satisfies the supplied inequality expression.
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        maxValue = new EDITBigDecimal(sp.popFromStack());
        minValue = new EDITBigDecimal(sp.popFromStack());
        aliasValue = sp.peekFromStack();

        EDITBigDecimal aliasLength = new EDITBigDecimal(String.valueOf(aliasValue.length()));

        boolean validInequality;

        Comparator c = new Comparator();

        validInequality = c.compareNumericInequality(minValue, aliasLength, Comparator.getOperator("<="));
        
        if (validInequality)
        {
            validInequality = c.compareNumericInequality(aliasLength, maxValue, Comparator.getOperator("<="));
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
        return "actual:[" + aliasValue.toString() + "] expected:[" + minValue.toString() + ", " + maxValue.toString() + "]";
    }
}
