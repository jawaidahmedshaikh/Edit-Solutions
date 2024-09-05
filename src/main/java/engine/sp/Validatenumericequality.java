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

/**
 * Verifies that an actual float value is within the accepted tolerance of an expected float value.
 */
public class Validatenumericequality extends ValidateInst
{
    private EDITBigDecimal actualValue;
    private EDITBigDecimal expectedValue;
    private EDITBigDecimal tolerance;

    public Validatenumericequality()
    {
        super();
    }

    /**
     * Validates that two numbers are numerically equivalent within a specified tolerance.  
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        tolerance = new EDITBigDecimal(sp.popFromStack());
        expectedValue = new EDITBigDecimal(sp.popFromStack());
        actualValue = new EDITBigDecimal(sp.peekFromStack());

        boolean areEqual = false;

        Comparator c = new Comparator();

        areEqual = c.compareNumericEquality(actualValue, expectedValue, tolerance);

        try
        {
            if (!areEqual)
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
        return "actual:[" + actualValue.toString() + "] expected:[" + expectedValue + "] tolerance:[" + tolerance + "]";
    }
}
