/*
 * DAOFactory.java      Version 1.10  09/24/2001

 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package role.dm.dao;

import java.io.Serializable;

/**
 *  This class follows the Factory pattern by building individual Data Access Objects (DAO)
 *  For example: The method getClientDetailDAO returns a ClientDetailDAO object.
 *
 */
public class DAOFactory implements Serializable
{

//*******************************
//          Variables
//*******************************

    private static ClientRoleDAO clientRoleDAO;
    private static ClientRoleFinancialDAO clientRoleFinancialDAO;

    static
    {
        clientRoleDAO = new ClientRoleDAO();
        clientRoleFinancialDAO = new ClientRoleFinancialDAO();
    }


//*******************************
//          Public Methods
//*******************************

    public static ClientRoleDAO getClientRoleDAO() {

        return clientRoleDAO;
    }

    public static ClientRoleFinancialDAO getClientRoleFinancialDAO() {

        return clientRoleFinancialDAO;
    }
}