/*
 * SMException.java      Version 2.00  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package fission.dm;

/**
 * SMException class - Storage Manager specific
 * exception messages
 */
public class SMException extends Exception {

	// SMException error codes
	public static final int DUPSNOTALLOWED = 1;
	public static final int ENTRYNOTINLIST = 2;
    
    
	private Object originalException = null;
	private int            errorCode = 0;



    /**
     * SMException constructor
     */
    public SMException() {
        super();	
    }
    
    /**
     * SMException constructor comment.
     * <p>
     * @param message  Exception message
     */
    public SMException(String message) {
        super(message);
    }

    /**
     * SMException constructor 
     * <p>
     * @param message  Exception message
     * @param code     Exception message code
     */
    public SMException(String message, int code) {
        super(message);
        errorCode = code;
    }

    /**
     * SMException constructor comment.
     * <p>
     * @param message      Exception message
     * @param code         Exception message code
     * @param exceptionIn  Original Exception
     */
    public SMException(String message, int code, Object exceptionIn) {
        super(message);
        errorCode = code;
        originalException = exceptionIn;
    }

    /**
     * SMException constructor 
     * <p>
     * @param message      Exception message
     * @param exceptionIn  Original Exception
     */
    public SMException(String message, Object exceptionIn) {
        super(message);
        originalException = exceptionIn;
    }

}