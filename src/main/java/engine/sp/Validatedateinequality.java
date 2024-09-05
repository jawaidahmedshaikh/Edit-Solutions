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

import com.javathings.math.*;
import engine.common.*;
import edit.common.*;

/**
 * Verifies that an actual date in the format of yyyy/DD/mm is <, >, <=, >=, != of an expected date.
 */
public class Validatedateinequality extends ValidateInst
{
    private String actualValue;
    private String expectedValue;
    private String expression;

    public Validatedateinequality()
    {
        super();
    }

    /**
     * Validates the inequality relationship between two dates in the format of yyyy/DD/mmmm. The expression is
     * evaluated in the order that items were placed on the stack. For example:
     * push val:2000/01/01
     * push val:2002/02/02
     * push val:<
     * equates to the evaluation of "2000/01/01 < 20002/02/02"
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        expression = sp.popFromStack();
        expectedValue = sp.popFromStack();
        actualValue = sp.peekFromStack();

        try
        {
            boolean isTrue;

            Comparator c = new Comparator();

            isTrue = c.compareDate(actualValue, expectedValue, Comparator.getOperator(expression.trim()));

            if (!isTrue)
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
        return "actual:[" + actualValue.toString() + "] expected:[" + expectedValue + "] expression:[" + expression + "]";
    }
}
