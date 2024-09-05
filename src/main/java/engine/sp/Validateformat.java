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
import java.text.*;

import edit.common.*;
import fission.utility.*;

/**
 * Verifies that an actual float value is within the accepted tolerance of an expected float value.
 */
public class Validateformat extends ValidateInst
{
    private String aliasValue;
    private String formatPattern;

    public Validateformat()
    {
        super();
    }

    /**
     * Valdates that a numeric value is adheres to the specified format.
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        formatPattern = sp.popFromStack();
        aliasValue = sp.peekFromStack();

        boolean validFormat;

        try
        {
            DecimalFormat decimalFormat = new DecimalFormat(formatPattern);

            String parsedValue = decimalFormat.parse(aliasValue).toString();

            String formattedValue = decimalFormat.format(Double.parseDouble(parsedValue));

            validFormat = aliasValue.equals(formattedValue);

            if (!validFormat)
            {
                throw new SPException(Constants.ValidateErrorMsg.NUMERIC_FORMAT_ERROR, SPException.VALIDATION_ERROR);
            }
        }
        catch(Exception e)
        {
            if (!(e instanceof SPException))
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
            else
            {
                throw (SPException)e;
            }

        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    /**
     * @return
     * @see ValidateInst#getStack()
     */
    String getStack()
    {
        return "alias value:[" + aliasValue.toString() + "] format:[" + formatPattern.toString() + "]";
    }
}