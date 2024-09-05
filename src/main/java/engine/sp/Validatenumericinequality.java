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

import java.math.*;

import edit.common.*;

/**
 * Verifies that an actual float value is <, >, <=, >=, != of an expected float value.
 */
public class Validatenumericinequality extends ValidateInst
{
    private EDITBigDecimal actualValue;
    private EDITBigDecimal expectedValue;
    private String expression;

    public Validatenumericinequality()
    {
        super();
    }

    /**
     * Validates the inequality relationship between two numbers. The expression is evaluated in the order that items
     * were placed on the stack. For example:
     * push val:1
     * push val:2
     * push val:<
     * equates to the evaluation of "1 < 2"
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        expression = sp.popFromStack();
        expectedValue = new EDITBigDecimal(sp.popFromStack());
        actualValue = new EDITBigDecimal(sp.peekFromStack());

        try
        {
            boolean isTrue;

            Comparator c = new Comparator();

            isTrue = c.compareNumericInequality(actualValue, expectedValue, Comparator.getOperator(expression.trim()));

            if (!isTrue)
            {
                throw new SPException(Constants.ValidateErrorMsg.NUMERIC_INEQUALITY_ERROR, SPException.VALIDATION_ERROR);
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
