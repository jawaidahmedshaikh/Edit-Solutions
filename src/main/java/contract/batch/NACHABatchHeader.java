/*
 * 
 * User: cgleason
 * Date: Jul 16, 2008
 * Time: 11:36:39 AM
 * 
 * (c) 2000 - 2008 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package contract.batch;

import client.*;

import java.util.*;

import edit.common.*;
import fission.utility.*;
import businesscalendar.*;


public class NACHABatchHeader
{
    private static final String BATCHHEADER_RECORD_TYPE = "5";
    private static final String SERVICE_CLASS_CODE = "200";
    private static final String ENTRY_CLASS = "PPD";
    private static final String STATUS = "1";
    private static final String SELMAN_TIN = "1362136262";

    private Long companyFK;
    private ClientDetail clientCompanyDetail;
    private String batchSequenceNumber;
    private String bankRoutingNumber;
    private String corporateName;
    private String employerId;
    private EDITDate draftDateED;
    private String YYMMDD;
    private String monthName;
    private String paddingSpaces = " ";

    private StringBuffer fileData   = new StringBuffer();

    public NACHABatchHeader()
    {

    }

    /**
     * Get the Client Company for creating the BatchHeader record.
     * @param companyFK
     * @param batchSequenceNumber
     * @param bankRoutingNumber
     * @param processDate
     */
    public NACHABatchHeader(Long companyFK, ClientDetail clientCompanyDetail, String batchSequenceNumber, String bankRoutingNumber, EDITDate draftDateED)
    {
        this.companyFK = companyFK;
        this.batchSequenceNumber = batchSequenceNumber;
        this.bankRoutingNumber = bankRoutingNumber;
        this.draftDateED = draftDateED;
        this.clientCompanyDetail = clientCompanyDetail;
    }


    public void createBatchHeader()
    {
        try
        {
                init();
                buildBatchHeader();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * Get the Client Company record for the batch header information about the company being processed.
     * @throws Exception
     */
    private void init()  throws Exception
    {

        if (clientCompanyDetail == null)
        {
            throw new Exception("Client Company Record Not Found for File Creation");
        }

        corporateName = clientCompanyDetail.getCorporateName();
        if (corporateName.length() < 16)
        {
            corporateName = Util.addOnTrailingSpaces(corporateName, 17);
        }
        else if (corporateName.length() > 16)
        {
            corporateName = corporateName.substring(0, 16);
        }

        employerId = clientCompanyDetail.getTaxIdentification();
        if (employerId.length() < 10)
        {
            employerId = Util.addOnTrailingSpaces(employerId, 11);
        }
        else if (employerId.length() > 10)
        {
            employerId = employerId.substring(0, 10);
        }

        YYMMDD = draftDateED.getYearAsYY() + draftDateED.getFormattedMonth() + draftDateED.getFormattedDay();

        monthName = EDITDate.getMonthName(draftDateED.getFormattedMonth());
        monthName = (monthName.substring(0, 3)).toUpperCase() + " PYMT  " ;

        bankRoutingNumber = bankRoutingNumber.substring(0, 8);

    }

    /**
     * getter
     * @return
     */
    public String getEmployerId()
    {
        return employerId;
    }

    /**
     * Getter
     * @return
     */
    public String getBatchsequenceNumber()
    {
        return batchSequenceNumber;
    }

    /**
     * Getter
     * @return
     */
    public ClientDetail getClientDetail()
    {
        return clientCompanyDetail;
    }

    /**
     * Batch Header record - Fixed length of 94
     */
    private void buildBatchHeader()
    {
        fileData.append(BATCHHEADER_RECORD_TYPE);                          //length = 1
        fileData.append(SERVICE_CLASS_CODE);                               //length = 3
        fileData.append(corporateName);                                    //length = 16

        paddingSpaces = Util.addOnTrailingSpaces(paddingSpaces, 21);       //length = 20
        fileData.append(paddingSpaces);

        fileData.append(SELMAN_TIN);                                       //length = 10 
        fileData.append(ENTRY_CLASS);                                      //length = 3
        fileData.append(monthName);                                        //length = 10

        paddingSpaces = paddingSpaces.substring(0, 6);                     //length =6
        fileData.append(paddingSpaces);

        fileData.append(YYMMDD);                                           //length = 6

        paddingSpaces = paddingSpaces.substring(0, 3);                     //length = 3

        fileData.append(paddingSpaces);

        fileData.append(STATUS);                                           //length = 1
        fileData.append(bankRoutingNumber);                                //length = 8
        fileData.append(batchSequenceNumber);                              //length = 7
    }

    /**
     * Getter
     * @return
     */
    public StringBuffer getFileData()
    {
        return fileData;
    }
}
