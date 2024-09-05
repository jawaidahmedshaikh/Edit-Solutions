/*
 * DAOFactory.java   Version 1.1   10/05/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC.  All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package accounting.dm.dao;

import java.io.Serializable;

public class DAOFactory implements Serializable {

//******************************
//          Variables
//******************************

    private static ElementDAO                elementDAO;
    private static ElementStructureDAO       elementStructureDAO;
    private static AccountDAO                accountDAO;
    private static ElementCompanyRelationDAO elementCompanyRelationDAO;
    private static AccountingDetailDAO       accountingDetailDAO;

//******************************
//         Constructors
//******************************

   static {

        elementDAO                = new ElementDAO();
        elementStructureDAO       = new ElementStructureDAO();
        accountDAO                = new AccountDAO();
        elementCompanyRelationDAO = new ElementCompanyRelationDAO();
        accountingDetailDAO       = new AccountingDetailDAO();
    }

    public static ElementDAO getElementDAO() {

        return elementDAO;
    }

    public static ElementStructureDAO getElementStructureDAO() {

        return elementStructureDAO;
    }

    public static AccountDAO getAccountDAO() {

        return accountDAO;
    }

    public static ElementCompanyRelationDAO getElementCompanyRelationDAO() {

        return elementCompanyRelationDAO;
    }

    public static AccountingDetailDAO getAccountingDetailDAO() {

        return accountingDetailDAO;
    }
}
