/*
 * User: sdorman
 * Date: Sep 5, 2006
 * Time: 9:17:11 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package webservice.service;


import fission.utility.*;
import client.*;
import edit.common.vo.*;
import org.apache.axiom.om.*;
import org.apache.axis2.*;
import org.dom4j.*;
import webservice.*;

/**
 * Gets the current client address based on the taxID, addressTypeCT, and trustTypeCT
 */
public class GetCurrentClientAddress implements EDITWebService
{
    private OMElement soapRequest = null;
    private OMElement soapResponse = null;

    private static final String MESSAGE_TYPE = "GetCurrentClientAddress";

    /**
     * Gets the input from the request's attachment, finds the address, and returns the results
     * 
     * @param soapRequest
     * @return
     * @throws org.apache.axis2.AxisFault
     */
    public OMElement execute(OMElement soapRequest) throws AxisFault
    {
        this.soapRequest = soapRequest;

        try
        {
            Document input = WebServiceUtil.getAttachment(soapRequest);

            ClientAddressVO clientAddressVO = getCurrentClientAddress(input);

            buildResponse(Util.marshalVO(clientAddressVO));
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

            throw new AxisFault(e);
        }

        return soapResponse;
    }

    /**
     * Performs the business logic for getting the current client address
     * @param document
     * @return
     * @throws Exception
     */
    public ClientAddressVO getCurrentClientAddress(Document document) throws Exception
    {
        Element rootElement = document.getRootElement();

        String taxID = rootElement.selectSingleNode("TaxID").getText();
        String addressTypeCT = rootElement.selectSingleNode("AddressTypeCT").getText();
        String trustTypeCT = rootElement.selectSingleNode("TrustTypeCT").getText();

        ClientAddress clientAddress = ClientAddress.findByTaxID_And_AddressTypeCT(taxID, addressTypeCT);

        return (ClientAddressVO) clientAddress.getVO();
    }

    /**
     * Builds response message from result.
     * @param results
     */
    private void buildResponse(String results)
    {
        soapResponse = WebServiceUtil.buildSOAPResponse(soapRequest, MESSAGE_TYPE, results);
    }
}
