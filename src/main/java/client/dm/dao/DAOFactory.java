/*
 * DAOFactory.java      Version 1.10  09/24/2001

 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package client.dm.dao;

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

    private static ClientDetailDAO clientDetailDAO;
    private static PreferenceDAO preferenceDAO;
    private static TaxInformationDAO taxInformationDAO;
    private static ClientAddressDAO clientAddressDAO;
    private static TaxProfileDAO taxProfileDAO;

    static
    {
        clientDetailDAO = new ClientDetailDAO();
        preferenceDAO = new PreferenceDAO();
        taxInformationDAO = new TaxInformationDAO();
        clientAddressDAO = new ClientAddressDAO();
        taxProfileDAO = new TaxProfileDAO();
    }


//*******************************
//          Public Methods
//*******************************
    public static TaxProfileDAO getTaxProfileDAO()
    {
        return taxProfileDAO;
    }

    public static ClientAddressDAO getClientAddressDAO()
    {
        return clientAddressDAO;
    }

    public static TaxInformationDAO getTaxInformationDAO()
    {
        return taxInformationDAO;
    }

    public static PreferenceDAO getPreferenceDAO()
    {
        return preferenceDAO;
    }

    /**
     * Factory Method
     *
     * @return ClientDetailDAO
     */
    public static ClientDetailDAO getClientDetailDAO()
    {

        return clientDetailDAO;
    }
}