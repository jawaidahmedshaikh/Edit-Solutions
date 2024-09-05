/**
 * Created by IntelliJ IDEA.
 * User: sdorman
 * Date: Dec 16, 2003
 * Time: 11:02:31 AM
 * To change this template use Options | File Templates.
 */

package edit.common.exceptions;

import edit.common.vo.ValidationVO;

/**
 * Used to throw specific exception messages when processing transactions
 */
 
public class EDITEventException extends EDITException
{
    public static final int CONSTANT_NO_DATA_FOUND = 1000;

    private Object failedTrx = null;

    private Object originalException = null;

    private ValidationVO[] validationVOs = null;

    private int errorNumber;
    
    /**
     * Set to true if this error has already been logged by the system.
     * Trying to prevent duplicate log entries for the same error.
     */
    private boolean logged = false;

    public EDITEventException()
    {
        super();
    }

    /**
     * EDITEventException constructor
     * <p>
     * @param message  Error Message
     */
    public EDITEventException(String message)
    {
        super(message);
    }
	
    /**
     * EDITEventException constructor
     * <p>
     * @param message     Error Message
     * @param failedTrx   Transaction object that failed
     */
    public EDITEventException(String message, Object failedTrx)
    {
        super(message);
        this.failedTrx = failedTrx;
    }

    /**
     * Returns failedTrx Object
     * <p>
     * @return  Returns failedTrx
     */
    public Object getFailedTrx()
    {
        return this.failedTrx;
    }

    /**
     * Returns Original Exception Object
     * <p>
     * @return Returns original exception
     */
    public Exception getOriginalException()
    {
        return (Exception) this.originalException;
    }

    /**
     *
     * @param validationVOs
     */
    public void setValidationVO(ValidationVO[] validationVOs)
    {
        this.validationVOs = validationVOs;
    }

    /**
     *
     * @return
     */
    public ValidationVO[] getValidationVO()
    {
        return validationVOs;
    }

    /**
     * Getter.
     * @return
     */
    public int getErrorNumber()
    {
        return errorNumber;
    }

    /**
     * Setter.
     * @param errorNumber
     */
    public void setErrorNumber(int errorNumber)
    {
        this.errorNumber = errorNumber;
    }
    
    /**
     * Set to true if this exception has been logged by some logging framework.
     * @see #logged
     * @param logged
     */
    public void setLogged(boolean logged)
    {
      this.logged = logged;
    }

    /**
     * True if this Exception has been logged by the logging framework.
     * @see #logged
     * @return
     */
    public boolean isLogged()
    {
      return logged;
    }
  
}