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

/**
 * Verifies that a string a is character equavalent to string b.
 */
public class Validatestringequality extends ValidateInst
{
    private String expectedValue;
    private String actualValue;

    public Validatestringequality()
    {
        super();
    }

    /**
     * Validates that an expected character string and and an actual character string are equivalent. #NULL is an
     * acceptable value for either the expected or actual values.
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        expectedValue = sp.popFromStack();
        actualValue = sp.peekFromStack();

        boolean areEqual;

        Comparator c = new Comparator();

        areEqual = c.compareString(actualValue, expectedValue, Comparator.EQ);

        try
        {
            if (!areEqual)
            {
                throw new SPException(Constants.ValidateErrorMsg.STRING_EQUALITY_ERROR, SPException.VALIDATION_ERROR);
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
        return "actual:[" + actualValue + "] expected:[" + expectedValue + "]";
    }
}
