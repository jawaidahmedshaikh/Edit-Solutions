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


public class NACHABatchControl
{
    private static final String BATCHCONTROL_RECORD_TYPE = "8";
    private static final String SERVICE_CLASS_CODE = "200";

    private String batchSequenceNumber;
    private String sequenceNumberTotal;
    private EDITBigDecimal routingNumberTotal;
    private EDITBigDecimal totalAmount;
    private String bankRoutingNumber;
    private String stringTotalAmount;
    private String stringRoutingNumberTotal;
    private String employerId;
    private String processType;
    private String paddingSpaces = " ";
    private String zeroAmount = "000";


    private StringBuffer fileData   = new StringBuffer();

    public NACHABatchControl()
    {

    }

    /**
     * BatchControl create - the total for the batch detail just processed
     * @param batchSequenceNumber
     * @param detailSequenceNumber
     * @param bankRoutingNumber
     * @param routingNumberTotal
     * @param totalAmount
     * @param employerId
     */
    public NACHABatchControl(String batchSequenceNumber, String detailSequenceNumber, String bankRoutingNumber, EDITBigDecimal routingNumberTotal, EDITBigDecimal totalAmount, String employerId, String processType)
    {
        this.batchSequenceNumber = batchSequenceNumber;
        this.sequenceNumberTotal = detailSequenceNumber.substring(1, 7);
        this.bankRoutingNumber = bankRoutingNumber;
        this.routingNumberTotal = routingNumberTotal;
        this.totalAmount = totalAmount;
        this.employerId = employerId;
        this.processType = processType;
    }


    public void createBatchControl()
    {
        try
        {
            init();

            buildBatchControl();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("");
            throw new RuntimeException(e);
        }
    }
    /**
     * Get the Client Company record for the file header information about the bank.
     * @throws Exception
     */
    private void init()
    {
        sequenceNumberTotal = sequenceNumberTotal.substring(0, 6);

        stringTotalAmount = totalAmount.toString();

        stringTotalAmount = Util.removeNonNumericChars(stringTotalAmount);
        if (stringTotalAmount.length() < 12)
        {
            stringTotalAmount = Util.padWithZero(stringTotalAmount, 12);
        }

        zeroAmount = Util.padWithZero(zeroAmount, 12);
        stringRoutingNumberTotal = routingNumberTotal.toString();
        
        stringRoutingNumberTotal = Util.removeNonNumericChars((stringRoutingNumberTotal));
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

        if (employerId.length() < 10)
        {
            employerId = Util.addOnTrailingSpaces(employerId, 11);
        }
        else if (employerId.length() > 10)
        {
            employerId = employerId.substring(0, 10);
        }
        bankRoutingNumber = bankRoutingNumber.substring(0, 8);
    }

    /**
     * Batch Control record  - Fixed length of 94
     */
    private void buildBatchControl()
    {
        fileData.append(BATCHCONTROL_RECORD_TYPE);                       //length = 1
        fileData.append(SERVICE_CLASS_CODE);                             //length = 3
        fileData.append(sequenceNumberTotal);                            //length = 6
        fileData.append(stringRoutingNumberTotal);                       //length = 10
        fileData.append(stringTotalAmount);                              //length = 12  credit
        fileData.append(stringTotalAmount);                              //length = 12  debit

        fileData.append(employerId);                                     //length = 10

        paddingSpaces = Util.addOnTrailingSpaces(paddingSpaces, 20);     //length = 19
        fileData.append(paddingSpaces);

        paddingSpaces = paddingSpaces.substring(0, 6);                   //length = 6
        fileData.append(paddingSpaces);

        fileData.append(bankRoutingNumber);                              //length = 8
        fileData.append(batchSequenceNumber);                            //length = 7
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

        Integer seqNumber = 1;
        String batchSequenceNumber = Util.padWithZero(seqNumber.toString(), 7);
        String detailSequenceNumber = "0000003";
        String bankRoutingNumber = "011400071";
        EDITBigDecimal routingNumberTotal = new EDITBigDecimal("56789");
        EDITBigDecimal totalAmount = new EDITBigDecimal("8003.40");
        String employerId = "234567891";
        String processType = "Billing";   //"Transaction";"Check";

        NACHABatchControl nachaBatchControl = new NACHABatchControl(batchSequenceNumber, detailSequenceNumber, bankRoutingNumber, routingNumberTotal, totalAmount, employerId, processType);

        nachaBatchControl.createBatchControl();

        System.out.println(nachaBatchControl.getFileData().toString());

    }
}
