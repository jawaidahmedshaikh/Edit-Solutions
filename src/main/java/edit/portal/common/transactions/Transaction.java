/*
 * Transaction.java      Version 1.10  09/25/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package edit.portal.common.transactions;

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
    public abstract String execute(AppReqBlock appReqBlock) throws Throwable;

    protected boolean paramIsEmpty(String param)
    {
        if ( (param == null) || (param.equals("")) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Initializes empty parameters to an empty String, or a passsed-in default value if non-null;
     * @param param
     * @param defaultValue
     * @return The original param value, or an empty String, or a non-null default value.
     */
    protected String initParam(String param, String defaultValue)
    {
        param = ( (param == null) || (param.equals("")) )?defaultValue:param;

        return param;
    }
}