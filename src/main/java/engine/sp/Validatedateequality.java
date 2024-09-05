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
 * Verifies that two dates are equivalent.
 */
public class Validatedateequality extends ValidateInst
{
    private String expectedValue;
    private String actualValue;

    public Validatedateequality()
    {
        super();
    }

    /**
     * Validates that an expected date and and an actual date are equivalent. #NULL can never be a valid date, but is an
     * acceptable value for either the expected or actual values. If the expected and actual dates are not #NULL, then
     * they are assumed valid dates (as validated by the Validatedate instruction), so only a character equality is
     * performed.
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        expectedValue = sp.popFromStack();
        actualValue = sp.peekFromStack();

        boolean datesAreEqual;

        try
        {
            Comparator c = new Comparator();

            datesAreEqual = c.compareDate(actualValue, expectedValue, Comparator.EQ);

            if (!datesAreEqual)
            {
                throw new SPException(Constants.ValidateErrorMsg.DATE_EQUALITY_ERROR, SPException.VALIDATION_ERROR);
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
