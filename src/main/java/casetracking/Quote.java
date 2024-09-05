/*
 * User: cgleason
 * Date: Aug 5, 2005
 * Time: 8:28:09 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking;

import client.ClientDetail;

import codetable.PRASEDocHelper;
import codetable.PRASEDocResult;

import contract.Payout;
import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.CaseTrackingQuoteVO;
import edit.common.vo.SPOutputVO;
import edit.common.vo.VOObject;
import edit.common.vo.ValidationVO;

import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPException;
import engine.sp.SPOutput;

import logging.Log;
import logging.LogEvent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;


public class Quote
{
    private CaseTrackingQuoteVO quoteVO;

    public Quote()
    {

    }

    /**
     * Death quote process for CaseTracking
     * @param disbursementDate
     * @param beneAllocationPct
     * @param segmentPK
     * @return
     * @throws EDITEventException
     */
    public CaseTrackingQuoteVO performDeathQuote(EDITDate disbursementDate, EDITBigDecimal beneAllocationPct, Long segmentPK) throws EDITEventException
    {
        Segment segment = Segment.findByPK(segmentPK);

        // client for processing death quote.
        ClientDetail clientDetail = segment.getOwner();

        String ownerGender = clientDetail.getGenderCT();

        // the business rule is use annuitant(DFA) or insured(Life) if owner Gender is 'NonNatural' or 'NotApplicable'
        if (ClientDetail.GENDERCT_NONNATURAL.equals(ownerGender) ||
                ClientDetail.GENDERCT_NOTAPPLICABLE.equals(ownerGender))
        {
            if (segment.isDeferredAnnuityProduct())
            {
                clientDetail = segment.getAnnuitant();
            }
            else if (segment.isLifeProduct())
            {
                clientDetail = segment.getInsured();
            }
        }

        Document document = buildDocumentForDeathQuote(segmentPK, clientDetail, disbursementDate, beneAllocationPct);

        CaseTrackingQuoteVO caseTrackingQuoteVO = executeScript(document, "DeathQuote", "*", segment.getIssueStateCT(), disbursementDate.getFormattedDate(), segment.getProductStructureFK().longValue(), true);

        return caseTrackingQuoteVO;
    }

    /**
     * Not implemented yet
     * @param payout
     * @param segmentPK
     * @param clientDetail
     * @return
     * @throws EDITEventException
     */
    public CaseTrackingQuoteVO performPayoutQuote(Payout payout, Long segmentPK, ClientDetail clientDetail)   throws EDITEventException
    {
//        Document document = buildDocumentForPayoutQuote(segmentPK, clientDetail, payout);
//
//        Segment segment = Segment.findByPK(segmentPK);
//
//        CaseTrackingQuoteVO caseTrackingQuoteVO = executeScript(document, "PayoutQuote", "*", segment.getIssueStateCT(), segment.getEffectiveDate().getDateAsYYYYMMDD(), segment.getCompanyStructureFK(), true);
//
//        return caseTrackingQuoteVO;
        return null;
    }



    public VOObject getVO()
    {
        return quoteVO;
    }

    public void setVO(VOObject voObject)
    {
        this.quoteVO = (CaseTrackingQuoteVO) voObject;
    }

    /**
     * Assemble the quoteVO for the DeathQuote
     * @param segmentPK
     * @param clientDetail
     * @param disbursementDate
     * @param beneAllocationPct
     * @return
     */
    public Document buildDocumentForDeathQuote(Long segmentPK, ClientDetail clientDetail, EDITDate disbursementDate, EDITBigDecimal beneAllocationPct)
    {
        Document quoteDocument = new DefaultDocument();

        Element quoteElement = new DefaultElement("CaseTrackingQuoteVO");

        PRASEDocResult segmentElement = PRASEDocHelper.buildSegmentFromSegmentPK(segmentPK);
        Element segment = segmentElement.getElement();
        quoteElement.add(segment);

        Element clientDetailElement = SessionHelper.mapToElement(clientDetail, SessionHelper.EDITSOLUTIONS, false, false);
        quoteElement.add(clientDetailElement);

        Element disbursementDateElement = new DefaultElement("DisbursementDate");
        disbursementDateElement.setText(disbursementDate.getFormattedDate());
        quoteElement.add(disbursementDateElement);

        Element beneficiaryPctElement = new DefaultElement("BeneAllocationPct");
        beneficiaryPctElement.setText(beneAllocationPct.toString());
        quoteElement.add(beneficiaryPctElement);

        quoteDocument.setRootElement(quoteElement);

        return quoteDocument;
    }

    /**
     * Not implemented yet
     * @param segmentPK
     * @param clientDetail
     * @param payout
     * @return
     */
    private Document buildDocumentForPayoutQuote(Long segmentPK, ClientDetail clientDetail, Payout payout)
    {
//        Document quoteDocument = new DefaultDocument();
//
//        Element quoteElement = new DefaultElement("CaseTrackingQuoteVO");
//
//        Element client = new DefaultElement("ClientDetailVO");
//        Element clientDetailElement = SessionHelper.mapToElement(clientDetail);
//        client.add(clientDetailElement);
//        quoteElement.add(client);
//
//        quoteDocument.setRootElement(quoteElement);
//
//        return quoteDocument;
        return null;
    }

    /**
     * The casetracking quotes use this method to execute the quote requested
     * @param document
     * @param event
     * @param status
     * @param eventType
     * @param quoteDate
     * @param productKey
     * @param errorProcessing
     * @return
     * @throws EDITEventException
     */
    public CaseTrackingQuoteVO executeScript(Document document, String event, String status, String eventType,  String quoteDate, long productKey, boolean errorProcessing) throws EDITEventException
    {
        Calculator calcComponent = new CalculatorComponent();

        SPOutputVO spOutputVO = null;

        try
        {
            SPOutput spOutput = calcComponent.processScriptWithDocument("CaseTrackingQuoteVO",   document, event, status, eventType, quoteDate, productKey, errorProcessing);

            spOutputVO = spOutput.getSPOutputVO();

        }
        catch (SPException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            EDITEventException editEventException = new EDITEventException(e.getMessage());

            ValidationVO[] validationVOs = e.getValidationVO();

            editEventException.setValidationVO(validationVOs);

            throw editEventException;
        }
        catch (RuntimeException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            EDITEventException editEventException = new EDITEventException(e.getMessage());

            throw editEventException;
        }

        VOObject[] voObjects = spOutputVO.getVOObject();
        CaseTrackingQuoteVO caseTrackingQuoteVO = null;

        for (int i = 0; i < voObjects.length; i++)
        {
            VOObject voObject = (VOObject) voObjects[i];

            if (voObject instanceof CaseTrackingQuoteVO)
            {
                caseTrackingQuoteVO = (CaseTrackingQuoteVO)voObject;
            }
        }

        return caseTrackingQuoteVO;
    }
}
