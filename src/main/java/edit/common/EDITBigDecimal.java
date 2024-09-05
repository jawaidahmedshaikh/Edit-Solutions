/*
 * User: sramamurthy
 * Date: Sep 22, 2004
 * Time: 2:04:20 PM
 *
 * 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.common;

import java.math.BigDecimal;
import java.io.*;


public class EDITBigDecimal implements Serializable
{
    /**
     * _bigDecimal is the BigDecimal
     */
    private BigDecimal _bigDecimal;
    /**
     * EDIT_DECIMAL_SCALE_2 is a constant representation for 2
     */
    public static int EDIT_DECIMAL_SCALE_2 = 2;
    /**
     * EDIT_DECIMAL_SCALE_10 is a constant representation for 10
     */
    public static int EDIT_DECIMAL_SCALE_10 = 10;
    /**
     * EDIT_DECIMAL_ZILCH_STR is a constant representation for "0"
     */
    public static String EDIT_DECIMAL_ZILCH_STR = "0";


    public static int EDIT_DEFAULT_DIVIDE_DECIMAL_SCALE = 12;

    public EDITBigDecimal(String val)
    {
        if (val == null)
        {
            val = EDIT_DECIMAL_ZILCH_STR;
        }
        _bigDecimal = new BigDecimal(val);
/*     if ( scale != EDIT_DECIMAL_SCALE_2 &&
          scale != EDIT_DECIMAL_SCALE_10)
     throw new EDITScaleNotValidException("Scale value not valid");
*/
        //  _bigDecimal = _bigDecimal.setScale(scale,BigDecimal.ROUND_HALF_UP);

    }

    /**
     * Constructor with the BigDecimal value
     */
    public EDITBigDecimal(java.math.BigDecimal bigDecimal)
    {
        if (bigDecimal == null)
        {
            _bigDecimal = new BigDecimal(EDIT_DECIMAL_ZILCH_STR);
        }
        else
        {
            _bigDecimal = bigDecimal;
        }
    }

    /**
     * Constructor with BigDecimal and scale Value
     *
     * @param bigDecimal
     * @param scaleValue
     */
    public EDITBigDecimal(BigDecimal bigDecimal, int scaleValue)
    {
        if (bigDecimal == null)
        {
            _bigDecimal = new BigDecimal(EDIT_DECIMAL_ZILCH_STR);
        }
        else
        {
            _bigDecimal = bigDecimal;
        }

        _bigDecimal = _bigDecimal.setScale(scaleValue, BigDecimal.ROUND_HALF_UP);

    }

    /**
     * Constructor with String and scale Value
     *
     * @param val
     * @param scaleValue
     */
    public EDITBigDecimal(String val, int scaleValue)
    {
        if (val == null)
        {
            val = EDIT_DECIMAL_ZILCH_STR;
        }
        _bigDecimal = new BigDecimal(val);

        _bigDecimal = _bigDecimal.setScale(scaleValue, BigDecimal.ROUND_HALF_UP);

    }

    /**
     * Empty parameter constructor
     */
    public EDITBigDecimal()
    {
        _bigDecimal = new BigDecimal(EDIT_DECIMAL_ZILCH_STR);

    }


    public void setScale(int num)
    {
        try
        {
            _bigDecimal = _bigDecimal.setScale(num);
        }
        catch (java.lang.ArithmeticException exp)
        {
            throw exp;
        }
    }
    
    public void setValue(EDITBigDecimal value)
    {
    	if (value != null)
        {
            _bigDecimal = value.getBigDecimal();
        }
    }

    /**
     * returns the doublevalue equivalent of the bigDecimal <p>
     * This double value should be only used for compare like the (e.g.) and <p>
     * NOT FOR MATH OPERATIONS
     * (e.g.) <p>
     * //old usage <p>
     * if (elementAmount != 0 ) //elementAmount is a double value
     * <p/>
     * //New usage <p>
     * if (EDITBigDecimalObjOfelementAmount.doubleValue() != 0 )
     *
     * @return the doubleValue of the bigDecimal
     */
    public double doubleValue()
    {
        return _bigDecimal.doubleValue();
    }

    /**
     * @return the string equivalent for the BigDecimal value
     */
    public String toString()
    {
        return _bigDecimal.toPlainString();
    }

    /**
     * This method should be called ONLY  when settting the value to VO.
     * <p/>
     * (e.g.)
     * //New usage
     * inSuspenseVO.setAmount(EDITBigDecimalObjOfelementAmount.getBigDecimal());
     *
     * @return
     */
    public BigDecimal getBigDecimal()
    {
        return _bigDecimal;
    }

    /**
     * Adds the addend and the BigDecimal of val
     * (e.g.)
     * <p/>
     * //old usage
     * <p/>
     * suspenseAmount = suspenseAmount + outSuspenseVO[o].getAmount();
     * <p/>
     * //New usage
     * <p/>
     * suspenseAmount = suspenseAmount.addEditBigDecimal("0.000099922131121");
     *
     * @param val
     *
     * @return EDITBigDecimal
     */

    public EDITBigDecimal addEditBigDecimal(String val)
    {
        BigDecimal result = this._bigDecimal.add(new BigDecimal(val));
        return new EDITBigDecimal(result.toString());

    }

    /**
     * Adds the addend and the BigDecimal
     *
     * @param bigDecimal
     *
     * @return EDITBigDecimal
     */

    public EDITBigDecimal addEditBigDecimal(BigDecimal bigDecimal)
    {
        BigDecimal result = this._bigDecimal.add(bigDecimal);
        return new EDITBigDecimal(result.toString());

    }

    /**
     * Adds the addend and the EDITBigDecimal's BigDecimal
     *
     * @param editBigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal addEditBigDecimal(EDITBigDecimal editBigDecimal)
    {
        BigDecimal result = this._bigDecimal.add(editBigDecimal.getBigDecimal());
        return new EDITBigDecimal(result.toString());

    }

    /**
     * Subtracts the subtrahend from the minuend
     * (e.g.)
     * <p/>
     * //old usage
     * <p/>
     * suspenseAmount = suspenseAmount - outSuspenseVO[o].getAmount();
     * <p/>
     * //New usage
     * <p/>
     * suspenseAmount = suspenseAmount.subtractEditBigDecimal("0.000099922131121");
     *
     * @param val
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal subtractEditBigDecimal(String val)
    {
        BigDecimal result = this._bigDecimal.subtract(new BigDecimal(val));
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Subtracts the subtrahend from the minuend
     *
     * @param bigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal subtractEditBigDecimal(BigDecimal bigDecimal)
    {
        BigDecimal result = this._bigDecimal.subtract(bigDecimal);
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Subtracts the subtrahend from the minuend
     *
     * @param editBigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal subtractEditBigDecimal(EDITBigDecimal editBigDecimal)
    {
        BigDecimal result = this._bigDecimal.subtract(editBigDecimal.getBigDecimal());
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Multiplies the multiplier with the multiplicand
     * (e.g.)
     * <p/>
     * //old usage
     * <p/>
     * suspenseAmount =  suspenseAmount * outSuspenseVO[o].getAmount();
     * <p/>
     * //New usage
     * <p/>
     * suspenseAmount = suspenseAmount.multiplyEditBigDecimal("0.000099922131121");
     *
     * @param val
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal multiplyEditBigDecimal(String val)
    {
        BigDecimal result = this._bigDecimal.multiply(new BigDecimal(val));
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Multiplies the multiplier with the multiplicand
     *
     * @param bigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal multiplyEditBigDecimal(BigDecimal bigDecimal)
    {
        BigDecimal result = this._bigDecimal.multiply(bigDecimal);
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Multiplies the multiplier with the multiplicand
     *
     * @param editBigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal multiplyEditBigDecimal(EDITBigDecimal editBigDecimal)
    {
        BigDecimal result = this._bigDecimal.multiply(editBigDecimal.getBigDecimal());
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Divides the dividend with the divisor i.e val's BigDecimal
     * (e.g.)
     * <p/>
     * //old usage
     * <p/>
     * suspenseAmount = suspenseAmount / outSuspenseVO[o].getAmount();
     * <p/>
     * //New usage
     * <p/>
     * suspenseAmount = suspenseAmount.divideEditBigDecimal("0.000099922131121");
     *
     * @param val
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal divideEditBigDecimal(String val)
    {
        if (this._bigDecimal.scale() == 0)
        {
            _bigDecimal = this._bigDecimal.setScale(EDIT_DEFAULT_DIVIDE_DECIMAL_SCALE);
        }
        BigDecimal result = this._bigDecimal.divide(new BigDecimal(val), BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Divides the dividend with the divisor i.e val's BigDecimal and rounds the result to
     * dividend
     *
     * @param bigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal divideEditBigDecimal(BigDecimal bigDecimal)
    {
        if (this._bigDecimal.scale() == 0)
        {
            _bigDecimal = this._bigDecimal.setScale(EDIT_DEFAULT_DIVIDE_DECIMAL_SCALE);
        }
        BigDecimal result = this._bigDecimal.divide(bigDecimal, BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Divides the dividend with the divisor i.e val's BigDecimal and rounds the result to
     * dividend
     *
     * @param editBigDecimal
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal divideEditBigDecimal(EDITBigDecimal editBigDecimal)
    {
        if (this._bigDecimal.scale() == 0)
        {
            _bigDecimal = this._bigDecimal.setScale(EDIT_DEFAULT_DIVIDE_DECIMAL_SCALE);
        }
        BigDecimal result = this._bigDecimal.divide(editBigDecimal.getBigDecimal(), BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(result.toString());
    }

    /**
     * Multiplies the bigdecimal value with -1 and returns the wrapper to it
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal negate()
    {
        _bigDecimal = this._bigDecimal.negate();
        return new EDITBigDecimal(_bigDecimal);

    }

    /**
     * Returns Rounded EDITBigDecimal value
     *
     * @return
     */
    public EDITBigDecimal round()
    {
        _bigDecimal = this._bigDecimal.setScale(_bigDecimal.scale(), BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(_bigDecimal);
    }

    public EDITBigDecimal round(int scale) throws java.lang.ArithmeticException
    {
        _bigDecimal = this._bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(_bigDecimal);
    }


    public static EDITBigDecimal round(EDITBigDecimal ebigDecimal, int scale)
    {
        BigDecimal bigDecimal = ebigDecimal.getBigDecimal().setScale(scale, BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(bigDecimal);
    }

    public static EDITBigDecimal round(BigDecimal bigDecimal, int scale)
    {
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return new EDITBigDecimal(bigDecimal);
    }

    /**
     * Returns the wrapper with bigdecimal set to zero
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal resetValue()
    {
        _bigDecimal = (new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)).getBigDecimal();
        return new EDITBigDecimal(_bigDecimal);
    }

    /**
     * The scale of bigdecimal is returned
     *
     * @return
     */
    public int getScale()
    {
        return _bigDecimal.scale();
    }

    /**
     * <p/>
     * regardless of the scale the traling zeroes are removed from the decimal part and the string
     * eqivalent of the bigdecimal will be returned
     *
     * @return String value
     */

    public String trim()
    {
        BigDecimal bigDecimal = _bigDecimal;

        bigDecimal = bigDecimal.stripTrailingZeros();

        return bigDecimal.toPlainString();
    }

    /**
     * returns a scaled equivalent
     *
     * @param scale int value
     *
     * @return EDITBigDecimal
     */
    public EDITBigDecimal getScaled(int scale)
    {
        BigDecimal result = this._bigDecimal.setScale(scale);
        return new EDITBigDecimal(result.toString());
    }

    public boolean isEQ(String val)
    {
        int resultCmp = this._bigDecimal.compareTo(new BigDecimal(val));
        return (resultCmp == 0);

    }

    public boolean isEQ(EDITBigDecimal val)
    {
        return (getBigDecimal().compareTo(val.getBigDecimal()) == 0);
    }


    public boolean isEQ(String val, String toleranceVal)
    {
        int resultCmp = this._bigDecimal.compareTo(new BigDecimal(val));
        BigDecimal tolerance = new BigDecimal(toleranceVal);
        if (tolerance.signum() == -1)
            throw new RuntimeException("Tolerance can't be Negative");
        switch (resultCmp)
        {
            case 0:
                return true;
            case -1:
                resultCmp = _bigDecimal.add(tolerance).compareTo(new BigDecimal(val));
                return (resultCmp == 0 || resultCmp == 1);
            case 1:
                resultCmp = new BigDecimal(val).add(tolerance).compareTo(this._bigDecimal);
                return (resultCmp == 0 || resultCmp == 1);
        }
        return false;
    }

    public boolean isEQ(EDITBigDecimal val, EDITBigDecimal tolerance)
    {
        return isEQ(val.getBigDecimal().toString(), tolerance.toString());
    }

    public boolean isLT(String val)
    {
        int resultCmp = this._bigDecimal.compareTo(new BigDecimal(val));
        return (resultCmp == -1);
    }

    public boolean isLT(EDITBigDecimal val)
    {
        return (getBigDecimal().compareTo(val.getBigDecimal()) < 0);
    }

    public boolean isGT(String val)
    {
        int resultCmp = this._bigDecimal.compareTo(new BigDecimal(val));
        return (resultCmp == 1);
    }

    public boolean isGT(EDITBigDecimal val)
    {
        return ((getBigDecimal().compareTo(val.getBigDecimal())) > 0);
    }

    public boolean isLTE(String val)
    {
        int resultCmp = this._bigDecimal.compareTo(new BigDecimal(val));
        return (resultCmp == -1 || resultCmp == 0);
    }

    public boolean isLTE(EDITBigDecimal val)
    {
        boolean valuesAreLTOrEqual = false;
        
        if (isEQ(val) || isLT(val)) 
        {
            valuesAreLTOrEqual = true;
        }
        
        return valuesAreLTOrEqual;
    }


    public boolean isGTE(String val)
    {
        int resultCmp = this._bigDecimal.compareTo(new BigDecimal(val));
        return (resultCmp == 1 || resultCmp == 0);
    }

    public boolean isGTE(EDITBigDecimal val)
    {
        boolean valuesAreGTOrEqual = false;
        
        if (isEQ(val) || isGT(val)) 
        {
            valuesAreGTOrEqual = true;
        }
        
        return valuesAreGTOrEqual;
    }


    /**
     * @param obj
     *
     * @return
     *
     * @see #equals(Object)
     */
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof EDITBigDecimal))
        {
            return false;
        }

        return this.isEQ((EDITBigDecimal) obj);
    }

    /**
     * @return
     *
     * @see #hashCode()
     */
    public int hashCode()
    {
        return this._bigDecimal.hashCode();
    }

    /**
     * Returns the smaller of two EDITBigDecimals or the same value should both EDITBigDecimals have the same value.
     *
     * @return the smaller of a and b
     */
    public static EDITBigDecimal min(EDITBigDecimal a, EDITBigDecimal b)
    {
        EDITBigDecimal smaller;

        if (a.isLT(b))
        {
            smaller = a;
        }
        else if (b.isLT(a))
        {
            smaller = b;
        }
        else
        {
            smaller = a; // or b - they are the same
        }

        return smaller;
    }
}
