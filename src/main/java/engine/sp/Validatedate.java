/*
 * User: gfrosti
 * Date: Aug 24, 2004
 * Time: 10:10:36 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */


package engine.sp;

import edit.common.*;
import engine.common.*;
import org.apache.commons.validator.*;

/**
 * Validate that a date is valid and in the format of EDITDate.DEFAULT_FORMAT
 */
public class Validatedate extends ValidateInst
{
    private String actualValue;

    /**
     * Default constructor.
     */
    public Validatedate()
    {
        super();
    }

    /**
     * Validates both the format of the date as yyyy/MM/dd, and the propriety of the date (e.g. 2004/13/01 represents
     * a faulty month).
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        actualValue = sp.peekFromStack();

        boolean isValidDate;

        try
        {
//            if (actualValue.equals(EDITDate.DEFAULT_MAX_DATE))
//            {
//                isValidDate = true;
//            }
//            else if (actualValue.equals(EDITDate.DEFAULT_MIN_DATE))
//            {
//                isValidDate = true;
//            }
//            else
//            {
//                isValidDate = GenericValidator.isDate(actualValue, EDITDate.DATE_FORMAT, true);
//            }

            isValidDate = EDITDate.isACandidateDate(actualValue);

            if (!isValidDate)
            {
                throw new SPException(Constants.ValidateErrorMsg.DATE_FORMAT_ERROR, SPException.VALIDATION_ERROR);
            }
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    /**
     * .
     * @see ValidateInst#getStack() ()
     * @return the expected value
     */
    String getStack()
    {
        return "actual:[" + actualValue + "] expected:[" + EDITDate.DATE_FORMAT  + "]";
    }
}
