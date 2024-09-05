/*
 * Transaction.java      Version 1.10  09/25/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package fission.ui;

import fission.global.AppReqBlock;

/**
 * Transaction is the base class for all FISSION transactions
 */
public abstract class Transaction {

    // Constants
    public static final String UNDERCONSTRUCTION_SCREEN   = "construc.htm";
    public static final String DOWNLOADFILE_REQUEST = "FileDownLoad";

    /**
     * Used to execute transaction 
     */
    public abstract String execute(AppReqBlock aAppReqBlock)
        throws Exception;
    
}