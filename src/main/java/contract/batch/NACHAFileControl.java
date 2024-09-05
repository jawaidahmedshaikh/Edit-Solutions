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


public class NACHAFileControl
{
    private static final String FILECONTROL_RECORD_TYPE = "9";
    private static final String ADDENDA_COUNT = "00000000";

    private String batchSequenceNumber;
    private EDITBigDecimal routingNumberTotal;
    private EDITBigDecimal debitTotal;
    private EDITBigDecimal creditTotal;
    private Integer totalRecords;
    private Integer totalDetailRecords;
    private String stringRoutingNumberTotal;
    private String stringDebitTotal;
    private String stringCreditTotal;
    private String stringBlockCount;
    private String stringTotalRecords;
    private String stringTotalDetailRecords;
    private String paddingSpaces = " ";


    private StringBuffer fileData   = new StringBuffer();

    public NACHAFileControl()
    {

    }

    /**
     * FileControl the file ending record - containing totals for amounts, routing numbers and record count.
     * @param batchSequenceNumber
     * @param routingNumberTotal
     * @param debitTotal
     * @param creditTotal
     * @param totalRecords
     */
    public NACHAFileControl(String batchSequenceNumber, EDITBigDecimal routingNumberTotal, EDITBigDecimal debitTotal, EDITBigDecimal creditTotal, 
    		int totalRecords, int totalDetailRecords)
    {
        this.batchSequenceNumber = batchSequenceNumber;
        this.routingNumberTotal = routingNumberTotal;
        this.debitTotal = debitTotal;
        this.creditTotal = creditTotal;
        this.totalRecords = totalRecords;
        this.totalDetailRecords = totalDetailRecords;
    }

    public void createFileControl()
    {
        try
        {
            init();

            buildFileControl();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("");
            throw new RuntimeException(e);
        }
    }

    /**
     * Formatting for fixed lengths
     */
    private void init()
    {
        batchSequenceNumber = batchSequenceNumber.substring(1, 7);
        
        stringTotalDetailRecords = totalDetailRecords.toString();
        if (stringTotalDetailRecords.length() < 8) {
            stringTotalDetailRecords = Util.padWithZero(stringTotalDetailRecords, 8);
        }

        stringDebitTotal = debitTotal.toString();
        stringDebitTotal = Util.removeNonNumericChars(stringDebitTotal);
        if (stringDebitTotal.length() < 12)
        {
            stringDebitTotal = Util.padWithZero(stringDebitTotal, 12);
        }

        stringCreditTotal = creditTotal.toString();
        stringCreditTotal = Util.removeNonNumericChars(stringCreditTotal);
        if (stringCreditTotal.length() < 12)
        {
            stringCreditTotal = Util.padWithZero(stringCreditTotal, 12);
        }

        stringRoutingNumberTotal = routingNumberTotal.toString();
        stringRoutingNumberTotal = Util.removeNonNumericChars(stringRoutingNumberTotal);
        if (stringRoutingNumberTotal.length() < 10)
        {
            stringRoutingNumberTotal = Util.padWithZero(stringRoutingNumberTotal, 10);
        }
        if (stringRoutingNumberTotal.length() == 11)
        {
            stringRoutingNumberTotal = stringRoutingNumberTotal.substring(1,11);
        }
        if (stringRoutingNumberTotal.length() == 12)
        {
            stringRoutingNumberTotal = stringRoutingNumberTotal.substring(2,12);
        }


        stringTotalRecords  = totalRecords.toString();

        EDITBigDecimal totalRecordsED = new EDITBigDecimal(stringTotalRecords, 6);
        EDITBigDecimal blockCount = totalRecordsED.multiplyEditBigDecimal("94").divideEditBigDecimal("940");

        stringBlockCount =  blockCount.toString();
        stringBlockCount = Util.removeNonNumericChars(stringBlockCount);

        if (stringBlockCount.length() < 6)
        {
            stringBlockCount = Util.padWithZero(stringBlockCount, 6);
        }
        else if (stringBlockCount.length() > 6)
        {
            stringBlockCount = stringBlockCount.substring(0, 6);
        }

        if (stringTotalRecords.length() < 6)
        {
            stringTotalRecords = Util.padWithZero(stringTotalRecords, 6);
        }
    }

   /**
    * File Control record  - Fixed length of 94
    */
    private void buildFileControl()
    {
        fileData.append(FILECONTROL_RECORD_TYPE);                             //length = 1
        fileData.append(batchSequenceNumber);                                 //length = 6
        fileData.append(stringBlockCount);                                    //length = 6
        fileData.append(stringTotalDetailRecords);                            //length = 8
        fileData.append(stringRoutingNumberTotal);                            //length = 10
        fileData.append(stringDebitTotal);                                    //length = 12
        fileData.append(stringCreditTotal);                                   //length = 12

        paddingSpaces = Util.addOnTrailingSpaces(paddingSpaces, 40);          //length = 39
        fileData.append(paddingSpaces);
    }

    /**
     * Getter.
     * @return
     */
    public StringBuffer getFileData()
    {
        return fileData;
    }

    public static void main(String[] args)
    {
        int totalRecords = 3;
        Integer seqNumber = 1;
        String batchSequenceNumber = Util.padWithZero(seqNumber.toString(), 7);
        EDITBigDecimal routingNumberTotal = new EDITBigDecimal("567894567");
        EDITBigDecimal debitAmount = new EDITBigDecimal("8003.40");
        EDITBigDecimal creditAmount = new EDITBigDecimal("8003.40");

        NACHAFileControl nachaFileControl = new NACHAFileControl(batchSequenceNumber, routingNumberTotal, debitAmount, creditAmount, totalRecords, 0);

        nachaFileControl.createFileControl();

        System.out.println(nachaFileControl.getFileData().toString());

    }

}
