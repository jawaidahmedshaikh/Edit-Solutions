/*
 * User: sdorman
 * Date: Jun 15, 2007
 * Time: 10:05:12 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package billing.component;

import billing.Bill;
import billing.BillGroup;
import billing.BillSchedule;

import client.ClientDetail;

import contract.ContractClient;
import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITCaseException;

import edit.services.db.hibernate.HibernateEntityDifference;
import edit.services.db.hibernate.SessionHelper;

import engine.Company;
import engine.ProductStructure;

import fission.utility.XMLUtil;

import group.ContractGroup;
import group.PayrollDeductionSchedule;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import role.ClientRole;

import webservice.SEGResponseDocument;
import businesscalendar.BusinessDay;
import businesscalendar.BusinessCalendar;

public class BillingComponent implements billing.business.Billing
{
    /**
     * @see #saveBillScheduleUpdate(BillSchedule, ContractGroup, EDITDate)
     * @param billSchedule
     * @param contractGroup
     * @param changeEffectiveDate
     * @throws EDITCaseException
     */
    public void saveBillScheduleUpdate(BillSchedule billSchedule, ContractGroup contractGroup, EDITDate changeEffectiveDate) throws EDITCaseException
    {
        int leadDays = billSchedule.getValueForBillingLeadDays(billSchedule);

        try
        {
            if (changeEffectiveDate != null) // flag the non-financial framework to use this date instead of the System date
            {
                SessionHelper.putInThreadLocal(HibernateEntityDifference.CHANGE_EFFECTIVE_DATE, changeEffectiveDate);
            }
            
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            BillSchedule.calculateAndSetBillScheduleDates(billSchedule, leadDays, contractGroup.getPayrollDeductionSchedules());

            billSchedule.addContractGroup(contractGroup);

            SessionHelper.saveOrUpdate(billSchedule, SessionHelper.EDITSOLUTIONS);

            PayrollDeductionSchedule payrollDeductionSchedule = PayrollDeductionSchedule.findByContractGroup(contractGroup.getContractGroupPK());

//            if (payrollDeductionSchedule != null &&
//                payrollDeductionSchedule.getNextPRDExtractDate() == null)
            if (payrollDeductionSchedule != null)
            {
                if (payrollDeductionSchedule.getNextPRDExtractDate() == null)
                {
                    PayrollDeductionSchedule.calculateAndSetPRDExtractDates(payrollDeductionSchedule, billSchedule.getBillSchedulePK());

                    SessionHelper.saveOrUpdate(payrollDeductionSchedule, SessionHelper.EDITSOLUTIONS);
                }
                else if (payrollDeductionSchedule.getPRDTypeCT().equalsIgnoreCase(PayrollDeductionSchedule.PRD_FIXED_SCHEDULE))
                {
                    EDITDate deductionChangeStartDate = null;
                    if (billSchedule.getBillingModeCT().equalsIgnoreCase(BillSchedule.VARIABLE_MONTHLY))
                    {
                        deductionChangeStartDate = billSchedule.getVarMonthDedChangeStartDate();
                    }
                    else
                    {
                        deductionChangeStartDate = billSchedule.getBillChangeStartDate();

                        if (billSchedule.getBillingModeCT().equalsIgnoreCase(BillSchedule.MONTHLY))
                        {
                            deductionChangeStartDate = deductionChangeStartDate.subtractMonths(1);
                        }

                        if (billSchedule.getBillingModeCT().equalsIgnoreCase(BillSchedule.THIRTEENTHLY))
                        {
                            deductionChangeStartDate = deductionChangeStartDate.subtractDays(28);
                        }
                    }

                    PayrollDeductionSchedule.updatePRDExtractDates(payrollDeductionSchedule, deductionChangeStartDate);

                    SessionHelper.saveOrUpdate(payrollDeductionSchedule, SessionHelper.EDITSOLUTIONS);
                }
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITCaseException(e.toString());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    //  ========================================  SERVICES  =============================================

    /**
     * @see Billing#getBillGroupsNotPaid(org.dom4j.Document)
     */
    public Document getBillGroupsNotPaid(Document requestDocument)
    {
        return this.getBillGroupsNotPaid();
    }

    /**
     * @see Billing#getBillGroupsNotPaid()
     */
    public Document getBillGroupsNotPaid()
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Found all BillGroups that have not been paid");

        BillGroup[] billGroups = BillGroup.findAllNotPaid();

        for (int i = 0; i < billGroups.length; i++)
        {
            BillSchedule billSchedule = billGroups[i].getBillSchedule();

            ContractGroup contractGroup = billSchedule.getContractGroups().iterator().next();

            Element billGroupElement = billGroups[i].getAsElement(true, true);
            Element billScheduleElement = billSchedule.getAsElement(true, true);
            Element contractGroupElement = contractGroup.getAsElement(true, true);

            billScheduleElement.add(contractGroupElement);
            billGroupElement.add(billScheduleElement);
            
            responseDocument.addToRootElement(billGroupElement);
        }

        return responseDocument.getDocument();
    }
    
   /**
    * @see Billing#getPayorsOfBillGroup(org.dom4j.Document)
    */
    public Document getPayorsOfBillGroup(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Found Payor of BillGroup");


        //  Get the input information from the request document
        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element billGroupPKElement = requestParametersElement.element("BillGroupPK");

        Long billGroupPK = new Long(billGroupPKElement.getText());

        ClientDetail[] clientDetails = ClientDetail.findAllPayorsByBillGroupFK(billGroupPK);

        for (int i = 0; i < clientDetails.length; i++)
        {
            Bill totalBill = getTotalBillForClientDetail(clientDetails[i], billGroupPK);

            //  Add ClientDetail and totalBill to the document elements
            Element clientDetailElement = clientDetails[i].getAsElement(true, true);

            Element totalBilledAmountElement = getTotalBilledAmountElement(totalBill);
            Element totalPaidAmountElement = getTotalPaidAmountElement(totalBill);

            clientDetailElement.add(totalBilledAmountElement);
            clientDetailElement.add(totalPaidAmountElement);

            responseDocument.addToRootElement(clientDetailElement);
        }

        return responseDocument.getDocument();
    }

    /**
     * @see Billing#getBillsForPayorInBillGroup(org.dom4j.Document)
     */
    public Document getBillsForPayorInBillGroup(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Found Bills for Payor in BillGroup");

        //  Get the input information from the request document
        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element billGroupPKElement = requestParametersElement.element("BillGroupPK");
        Element clientDetailPKElement = requestParametersElement.element("ClientDetailPK");

        Long billGroupPK = new Long(billGroupPKElement.getText());
        Long clientDetailPK = new Long(clientDetailPKElement.getText());

        Bill[] bills = Bill.findAllBillsForABillGroupByPayorClientDetail(clientDetailPK, billGroupPK);

        //  Add each Bill to the rootElement
        for (int i = 0; i < bills.length; i++)
        {
            Element billElement = bills[i].getAsElement(true, true);

            Segment segment = bills[i].getSegment();

            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

            Company company = Company.findByProductStructurePK(segment.getProductStructureFK());

            Element segmentElement = segment.getAsElement(true, true);
            Element productStructureElement = productStructure.getAsElement(true, true);
            Element companyElement = company.getAsElement(true, true);

            ContractClient[] insuredContractClients = segment.getContractClients(ClientRole.ROLETYPECT_INSURED);

            if (insuredContractClients.length > 0)
            {
                //  There should only be 1 insured client, get its ClientDetail
                ClientDetail insuredClientDetail = insuredContractClients[0].getClientDetail();

                Element insuredClientDetailElement = insuredClientDetail.getAsElement(true, true);
                segmentElement.add(insuredClientDetailElement);
            }

            productStructureElement.add(companyElement);
            segmentElement.add(productStructureElement);
            billElement.add(segmentElement);

            responseDocument.addToRootElement(billElement);
        }

        return responseDocument.getDocument();
    }
    
    /**
     * @see Billing#getBillsForContractInBillGroup(org.dom4j.Document)
     */
    public Document getBillsForContractInBillGroup(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Found Bills for Contract in BillGroup");

        //  Get the input information from the request document
        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element billGroupPKElement = requestParametersElement.element("BillGroupPK");

        Long billGroupPK = new Long(billGroupPKElement.getText());

        Bill[] bills = Bill.findAllBillsForABillGroup(billGroupPK);

        //  Add each Bill to the rootElement
        for (int i = 0; i < bills.length; i++)
        {
            Element billElement = bills[i].getAsElement(true, true);

            Segment segment = bills[i].getSegment();

            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

            Company company = Company.findByProductStructurePK(segment.getProductStructureFK());

            Element segmentElement = segment.getAsElement(true, true);
            Element productStructureElement = productStructure.getAsElement(true, true);
            Element companyElement = company.getAsElement(true, true);

            ContractClient[] insuredContractClients = segment.getContractClients(ClientRole.ROLETYPECT_INSURED);

            if (insuredContractClients.length > 0)
            {
                //  There should only be 1 insured client, get its ClientDetail
                ClientDetail insuredClientDetail = insuredContractClients[0].getClientDetail();

                Element insuredClientDetailElement = insuredClientDetail.getAsElement(true, true);
                segmentElement.add(insuredClientDetailElement);
            }

            productStructureElement.add(companyElement);
            segmentElement.add(productStructureElement);
            billElement.add(segmentElement);

            responseDocument.addToRootElement(billElement);
        }

        return responseDocument.getDocument();
    }    

    /**
     * @see Billing#adjustBillPaidAmounts(org.dom4j.Document)
     */ 
    public void adjustBillPaidAmounts(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Paid Amounts were successfully adjusted");

        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        List adjustmentElements = requestParametersElement.elements();

        try
        {
            for (Iterator iterator = adjustmentElements.iterator(); iterator.hasNext();)
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                Element adjustmentElement = (Element) iterator.next();

                Element billPKElement = adjustmentElement.element("BillPK");
                Element billedAmountElement = adjustmentElement.element("BilledAmount");
                Element paidAmountElement = adjustmentElement.element("PaidAmount");

                Long billPK = new Long(billPKElement.getText());
                EDITBigDecimal billedAmount = new EDITBigDecimal(billedAmountElement.getText());
                EDITBigDecimal paidAmount = new EDITBigDecimal(paidAmountElement.getText());

                Bill bill = Bill.findByPK(billPK);

                bill.updateBilledAmount(billedAmount);

                bill.updatePaidAmount(paidAmount);

                SessionHelper.saveOrUpdate(bill, SessionHelper.EDITSOLUTIONS);
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see Billing#updateBillGroup(org.dom4j.Document)
     */
    public void updateBillGroup(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "BillGroup was successfully updated");

        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element billGroupElement = requestParametersElement.element("BillGroupVO");

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            BillGroup billGroup = (BillGroup) SessionHelper.mapToHibernateEntity(BillGroup.class, billGroupElement, SessionHelper.EDITSOLUTIONS, true);

            SessionHelper.saveOrUpdate(billGroup, SessionHelper.EDITSOLUTIONS);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }



    // ========================================== PRIVATE METHODS ============================================


    /**
     * Calculates the total billedAmount and paidAmount for all Bills belonging to the given ClientDetail (payor).
     *
     * @param clientDetail                  ClientDetail for which to sum the amounts (as Payor only)
     * @param billGroupPK                   primary key for the BillGroup that the Bills belong to
     *
     * @return  Bill object which holds the summed billed and paid amounts.  Use of the Bill object is strictly for holding
     * the values.  It has no meaning, the Bill object is a dummy.
     */
    private Bill getTotalBillForClientDetail(ClientDetail clientDetail, Long billGroupPK)
    {
        Bill totalBill = new Bill();

        Bill[] bills = Bill.findAllBillsForABillGroupByPayorClientDetail(clientDetail.getClientDetailPK(), billGroupPK);

        EDITBigDecimal totalBilledAmount = new EDITBigDecimal();
        EDITBigDecimal totalPaidAmount = new EDITBigDecimal();

        for (int j = 0; j < bills.length; j++)
        {
            totalBilledAmount = totalBilledAmount.addEditBigDecimal(bills[j].getBilledAmount());
            totalPaidAmount = totalPaidAmount.addEditBigDecimal(bills[j].getPaidAmount());
        }

        //  Set fields for Bill
        totalBill.setBillGroupFK(billGroupPK);
        totalBill.setBilledAmount(totalBilledAmount);
        totalBill.setPaidAmount(totalPaidAmount);

        return totalBill;
    }

    /**
     * Creates an Element for the total billed amount
     *
     * @param totalBill                 Bill object that contains the billed amount value
     *
     * @return  Element whose tag name is TotalBilledAmount and whose value is the total billed amount
     */
    private Element getTotalBilledAmountElement(Bill totalBill)
    {
        Element totalBilledAmountElement = new DefaultElement("TotalBilledAmount");

        totalBilledAmountElement.setText(totalBill.getBilledAmount().toString());

        return totalBilledAmountElement;
    }

    /**
     * Creates an Element for the total paid amount
     *
     * @param totalBill                 Bill object that contains the paid amount value
     *
     * @return  Element whose tag name is TotalPaidAmount and whose value is the total paid amount
     */
    private Element getTotalPaidAmountElement(Bill totalBill)
    {
        Element totalPaidAmountElement = new DefaultElement("TotalPaidAmount");

        totalPaidAmountElement.setText(totalBill.getPaidAmount().toString());

        return totalPaidAmountElement;
    }
}