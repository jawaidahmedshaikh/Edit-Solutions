/*
 * User: sdorman
 * Date: Sep 5, 2006
 * Time: 9:17:11 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.services;


import fission.utility.*;
import client.*;
import edit.common.vo.*;

/**
 * Handles all ACORD services for Client-based functions
 */
public class ClientServices
{
//    public String getClientAddressByTaxIDAndAddressType(String xmlString)
    public String getClientAddressByTaxIDAndAddressType(String taxID, String addressTypeCT)
    {
        ClientAddress clientAddress = ClientAddress.findByTaxID_And_AddressTypeCT(taxID, addressTypeCT);

        ClientAddressVO clientAddressVO = (ClientAddressVO) clientAddress.getVO();

        return Util.marshalVO(clientAddressVO);
    }
}
