/*
 * User: gfrosti
 * Date: Aug 27, 2004
 * Time: 1:23:24 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import engine.common.*;
import junit.framework.*;
import junit.framework.Assert;

import java.math.*;

import edit.common.*;

/**
 * Performs =, <, >, <=, >=, != comparisons between two doubles, strings, or dates in the
 * format of yyyy/MM/dd.
 */
public class Comparator
{
    /**
     * Equal.
     */
    public static final int EQ = 0;

    /**
     * Less than.
     */
    public static final int LT = 1;

    /**
     * Less than or equal to.
     */
    public static final int LTE = 2;

    /**
     * Greater than.
     */
    public static final int GT = 3;

    /**
     * Greater than or equal to.
     */
    public static final int GTE = 4;

    /**
     * Not equal to.
     */
    public static final int NEQ = 5;

    /**
     * Default constructor.
     */
    public Comparator()
    {
    }

    /**
     * Evaluates (a - tolerance <= b <= a + tolerance).
     * @param a
     * @param b
     * @param tolerance
     * @return
     */
    public boolean compareNumericEquality(EDITBigDecimal a, EDITBigDecimal b, EDITBigDecimal tolerance)
    {
        boolean validInequality;

        EDITBigDecimal minValue = a.subtractEditBigDecimal(tolerance);

        EDITBigDecimal maxValue = a.addEditBigDecimal(tolerance);

        validInequality = compareNumericInequality(minValue, b, Comparator.LTE);

        if (validInequality)
        {
            validInequality = compareNumericInequality(b, maxValue, Comparator.LTE);
        }

        return validInequality;
    }

    /**
     * Performs <, >, <=, >=, != comparisons between two doubles.
     * @param a
     * @param b
     * @param comparison
     * @return true if (a operator b) is valid
     */
    public boolean compareNumericInequality(EDITBigDecimal a, EDITBigDecimal b, int comparison)
    {
        BigDecimal bigA = a.getBigDecimal();

        BigDecimal bigB = b.getBigDecimal();

        boolean isTrue = false;

        switch (comparison)
        {
            case LT:
                isTrue = (bigA.compareTo(bigB) < 0);
                break;

            case LTE:
                isTrue = ((bigA.compareTo(bigB) == 0) || (bigA.compareTo(bigB) < 0));
                break;

            case GT:
                isTrue = (bigA.compareTo(bigB) > 0);
                break;

            case GTE:
                isTrue = ((bigA.compareTo(bigB) == 0) || (bigA.compareTo(bigB) > 0));
                break;

            case NEQ:
                isTrue = !(bigA.compareTo(bigB) == 0);
                break;

            default:
                throw new RuntimeException(Constants.ValidateErrorMsg.UNRECOGNIZED_REQUEST);
        }

        return isTrue;
    }

    /**
     * Performs =, <, >, <=, >=, != comparisons between two strings.
     * @param a
     * @param b
     * @param comparison
     * @return true if (a operator b) is valid
     */
    public boolean compareString(String a, String b, int comparison)
    {
        boolean isTrue = false;

        // NULLs can not participate in a string inequality other than NEQ of EQ
        if (a.equals(Constants.ScriptKeyword.NULL) || b.equals(Constants.ScriptKeyword.NULL))
        {
            if ((comparison != Comparator.EQ) & (comparison != Comparator.NEQ))
            {
                throw new RuntimeException(Constants.ValidateErrorMsg.NULL_ERROR);
            }
        }

        switch (comparison)
        {
            case EQ:
                isTrue = (a.compareTo(b) == 0);
                break;

            case LT:
                isTrue = (a.compareTo(b) < 0);
                break;

            case LTE:
                isTrue = ((a.compareTo(b) < 0) || (a.compareTo(b) == 0));
                break;

            case GT:
                isTrue = (a.compareTo(b) > 0);
                break;

            case GTE:
                isTrue = (a.compareTo(b) > 0 || (a.compareTo(b) == 0));
                break;

            case NEQ:
                isTrue = (!a.equals(b));
                break;

            default:
                throw new RuntimeException(Constants.ValidateErrorMsg.UNRECOGNIZED_REQUEST);
        }

        return isTrue;
    }

    /**
     * Performs =, <, >, <=, >=, != comparisons between two dates of the format yyyy/MM/dd. #NULL is a valid operand.
     * @param a
     * @param b
     * @param comparison
     * @return true if (a operator b) is valid
     */
    public boolean compareDate(String a, String b, int comparison)
    {
        boolean datesAreEqual;

        if (a.equals(Constants.ScriptKeyword.NULL) || b.equals(Constants.ScriptKeyword.NULL)) // #NULL is not a valid date
        {
            datesAreEqual = false;
        }
        else
        {
            datesAreEqual = compareString(a, b, comparison);
        }

        return datesAreEqual;
    }

    /**
     * Maps the equality and inequality operators of =, <, >, <=, >=, != to its Comparator.Foo equivalent.
     * @param operator
     * @return the corresponding Comparator.Foo value, or -1 if an unrecognized operator is requested
     */
    public static int getOperator(String operator)
    {
        int operatorInt;

        if (operator.equals("="))
            operatorInt = EQ;

        else if (operator.equals("<"))
            operatorInt = LT;

        else if (operator.equals(">"))
            operatorInt = GT;

        else if (operator.equals("<="))
            operatorInt = LTE;

        else if (operator.equals(">="))
            operatorInt = GTE;

        else if (operator.equals("!="))
            operatorInt = NEQ;

        else
            operatorInt = -1;

        return operatorInt;
    }
}
