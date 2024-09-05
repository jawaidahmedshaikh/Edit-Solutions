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
 * Validates the inequality relationship between two strings.
 */
public class Validatestringinequality extends ValidateInst
{
    private String actualValue;
    private String expectedValue;
    private String expression;

    public Validatestringinequality()
    {
        super();
    }

    /**
     * Validates the inequality relationship between two strings. The expression is
     * evaluated in the order that items were placed on the stack. For example:
     * push val:a
     * push val:b
     * push val:<
     * equates to the evaluation of "a < b"
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

            isTrue = c.compareString(actualValue, expectedValue, Comparator.getOperator(expression.trim()));

            if (!isTrue)
            {
                throw new SPException(Constants.ValidateErrorMsg.STRING_INEQUALITY_ERROR, SPException.VALIDATION_ERROR);
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
