/*
 * SPException.java      Version 2.00  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may n
 be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */
package engine.sp;

import edit.common.exceptions.*;
import edit.common.vo.ValidationVO;


/**
 * Used to throw ScriptProcessor specific exception messages
 */
public class SPException extends EDITNestedException
{
    public static final int INSTRUCTION_PROCESSING_ERROR = 1;
    public static final int INSTRUCTION_SYNTAX_ERROR = 5;
    public static final int PARAMETER_INVALID_ERROR = 2;
    public static final int SCRIPT_LOADING_ERROR = 3;
    public static final int TABLE_ACCESS_ERROR = 4;
    public static final int GENERAL_MESSAGE = 5;

    // SPException error codes
    public static final int VALIDATION_ERROR = 0;
    private int errorCode = 0;
    private Inst inst;
    
    /**
     * Set to true if this error has already been logged by the system.
     * Trying to prevent duplicate log entries for the same error.
     */
    private boolean logged = false;

    private ValidationVO[] validationVOs;

    /************************************** Constructor Methods **************************************/
    /**
     * SPException constructor
     * <p>
     * @param message  Error Message
     * @param code     Error code
     */
    public SPException(String message, int code)
    {
        super(message);
        errorCode = code;
    }

    public SPException(String message, int code, Exception exception)
    {
        this(message, code);

        super.setNestedException(exception);
    }

    /************************************** Public Methods **************************************/
    /**
     * Returns errorCode member variable
     * <p>
     * @return  Returns error code
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * Getter - if the instruction was available at the moment of the exception.
     * @return
     */
    public Inst getInst()
    {
        return inst;
    }

    /**
     * Setter - if available at the moment of the exception. This will not replace the existing Inst.
     * @param inst
     */
    public void setInst(Inst inst)
    {
        if (inst == null)
        {
            this.inst = inst;
        }
    }

    public void setValidationVO(ValidationVO[] validationVOs)
    {
        this.validationVOs = validationVOs;
    }

    public ValidationVO[] getValidationVO()
    {
        return validationVOs;
    }

  /************************************** Private Methods **************************************/
  /************************************** Static Methods **************************************/

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
