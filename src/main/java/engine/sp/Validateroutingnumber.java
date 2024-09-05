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

/**
 * Verifies that the supplied routing number is correct for EFT processing.
 */
public class Validateroutingnumber extends ValidateInst
{
    private int[] weights = {3, 7, 1, 3, 7, 1, 3, 7};

    private int VALID_ROUTING_NUMBER_LENGTH = 9;

    private char[] routingNumber;

    public Validateroutingnumber()
    {
        super();
    }

    /**
     * Valdates that the supplied routing number is valid.
     * @param scriptProcessor
     * @throws SPException
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        routingNumber = new String(sp.peekFromStack()).toCharArray();

        boolean validRoutingNumber = true;

        try
        {
            if (routingNumber.length != VALID_ROUTING_NUMBER_LENGTH)
            {
                validRoutingNumber = false;
            }
            else
            {
                validRoutingNumber = validateRoutingNumber();
            }

            if (!validRoutingNumber)
            {
                throw new SPException(Constants.ValidateErrorMsg.NUMERIC_FORMAT_ERROR, SPException.VALIDATION_ERROR);
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
        return "routing number: " + new String(sp.peekFromStack());
    }

    /**
     * Weights the first 8 digits of the routing number by a predetermined weighted average.
     * @return
     */
    private int weightRoutingNumber()
    {
        int currentNumber;

        int totalWeight = 0;

        for (int i = 0; i < routingNumber.length - 1; i++)
        {
            currentNumber = Integer.parseInt(String.valueOf(routingNumber[i]));

            totalWeight += currentNumber * weights[i];
        }

        return totalWeight;
    }

    /**
     * Finds the next highest integer that is a multiple of 10 higher than the supplied integer.
     * @param weightedRoutingNumber
     * @return
     */
    private int nextMultipleOfTen(int weightedRoutingNumber)
    {
        int nextMultipleOfTen;

        int remainder = weightedRoutingNumber % 10;

        if (remainder == 0)
        {
            nextMultipleOfTen = weightedRoutingNumber;
        }
        else
        {
            nextMultipleOfTen = weightedRoutingNumber + (10 - remainder);
        }

        return nextMultipleOfTen;
    }

    /**
     * Validates the routing number using a weighing system / check digit.
     * @return
     */
    private boolean validateRoutingNumber()
    {
        boolean validRoutingNumber = false;

        int weightedRoutingNumber = weightRoutingNumber();

        int nextMultipleOfTen = nextMultipleOfTen(weightedRoutingNumber);

        int checkDigit = getCheckDigit();

        if ((nextMultipleOfTen - weightedRoutingNumber) == checkDigit)
        {
            validRoutingNumber = true;
        }

        return validRoutingNumber;
    }

    /**
     * Returns the 9th digit of the routing number - the check digit.
     * @return
     */
    private int getCheckDigit()
    {
        char lastCharacter = routingNumber[8];

        return Integer.parseInt(String.valueOf(lastCharacter));
    }
}