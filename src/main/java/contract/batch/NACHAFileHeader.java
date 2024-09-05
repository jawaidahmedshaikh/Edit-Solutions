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


public class NACHAFileHeader
{
    private static final String FILEHEADER_RECORD_TYPE = "1";
    private static final String PRIORITY_CODE = "01";
    private static final String FILEID_MODIFER = "A";
    private static final String RECORD_SIZE = "094";
    private static final String BLOCKING_FACTOR = "10";
    private static final String FORMAT_CODE = "1";
    private static final String COMPANY_NAME = "Combined Insurance Comp";

    private String corporateName;
    private String routingNumber;
    private String YYMMDD;
    private String HHMM;
    private String paddingSpaces = "        "; //need 8 spaces

    private StringBuffer fileData   = new StringBuffer();


    public NACHAFileHeader()
    {
        try
        {
            init();

            buildFileHeader();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("");
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the Client Bank record for the file header information about the bank.
     * @throws Exception
     */
    private void init()  throws Exception
    {
        ClientDetail  bankClientDetail = ClientDetail.findByTrustType(ClientDetail.TRUSTTYPECT_BANK);

        if (bankClientDetail == null)
        {
            throw new Exception("Client Bank Record Not Found for File Creation");
        }

        corporateName = bankClientDetail.getCorporateName();
        if (corporateName.length() < 23)
        {
            corporateName = Util.addOnTrailingSpaces(corporateName, 24);
        }
        else if (corporateName.length() > 23)
        {
            corporateName = corporateName.substring(0, 23);
        }

        //Should only be one
        Preference preference =  bankClientDetail.getPreference();

        routingNumber = preference.getBankRoutingNumber();
        if (routingNumber.length() < 10)
        {
            routingNumber = Util.addOnTrailingSpaces(routingNumber, 11);
        }

        EDITDateTime currentDateTime = new EDITDateTime();
        EDITDate currentDate = currentDateTime.getEDITDate();

        YYMMDD = currentDate.getYearAsYY() + currentDate.getFormattedMonth() + currentDate.getFormattedDay();
        HHMM = currentDateTime.getFormattedHour() + currentDateTime.getFormattedMinute();
    }

    /**
     * Getter.
     * @return
     */
    public String getRoutingNumber()
    {
        return this.routingNumber;
    }

    /**
     * File Control record - fixed length 0f 94
     */
    private void buildFileHeader()
    {
        fileData.append(FILEHEADER_RECORD_TYPE);        //length = 1
        fileData.append(PRIORITY_CODE);                 //length = 2
        fileData.append(routingNumber);                 //length = 10
        fileData.append(routingNumber);                 //length = 10
        fileData.append(YYMMDD);                        //length = 6  year/2, month,day
        fileData.append(HHMM);                          //length = 4  hour, minutes
        fileData.append(FILEID_MODIFER);                //length = 1
        fileData.append(RECORD_SIZE);                   //length = 3
        fileData.append(BLOCKING_FACTOR);               //length = 2
        fileData.append(FORMAT_CODE);                   //length = 1
        fileData.append(corporateName);                 //length = 23
        fileData.append(COMPANY_NAME);                  //length = 23
        fileData.append(paddingSpaces);                 //length = 8
    }

    /**
     * Getter.
     * @return
     */
    public StringBuffer getFileData()
    {
        return fileData;
    }
}
