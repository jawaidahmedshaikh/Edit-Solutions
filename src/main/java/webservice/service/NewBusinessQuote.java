/*
 * User: sprasad
 * Date: Aug 16, 2006
 * Time: 3:31:48 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice.service;

import edit.common.EDITBigDecimal;
import edit.common.vo.*;
import event.business.Event;
import event.component.EventComponent;
import fission.utility.*;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import webservice.WebServiceUtil;

import java.math.BigDecimal;

import role.ClientRole;
import engine.dm.dao.ProductStructureDAO;
import contract.Segment;

public class NewBusinessQuote implements EDITWebService
{
    private OMElement soapRequest = null;
    private OMElement soapResponse = null;


    /**
     *
     * @param soapRequest
     * @return
     * @throws AxisFault
     */
    public OMElement execute(OMElement soapRequest) throws AxisFault
    {
        this.soapRequest = soapRequest;

        try
        {
            Document inputDocument = WebServiceUtil.getAttachment(soapRequest);

            QuoteVO quoteVO = buildQuoteVO(inputDocument);

            quoteVO = executeQuote(quoteVO);

            buildResponse(quoteVO);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new AxisFault(e);
        }

        return soapResponse;
    }

    /**
     * Builds QuoteVO from input.
     * @param document
     * @return
     */
    private QuoteVO buildQuoteVO(Document document)
    {
        Element rootElement = document.getRootElement();

        String quoteDate = rootElement.selectSingleNode("QuoteDate").getText();

        QuoteVO quoteVO = new QuoteVO();
        quoteVO.setQuoteDate(DateTimeUtil.getEDITDateFromMMDDYYYY(quoteDate).getFormattedDate());

        // Segment
        String companyStructureId = rootElement.selectSingleNode("ProductStructureId").getText();
        String[] companyStructureTokens = Util.fastTokenizer(companyStructureId, ",");
        String companyName = companyStructureTokens[0];
        String marketingPackageName = companyStructureTokens[1];
        String groupProductName = companyStructureTokens[2];
        String areaName = companyStructureTokens[3];
        String businessContractName = companyStructureTokens[4];
        ProductStructureVO companyStructureVO = new ProductStructureDAO().
                findProductStructureByNames(companyName, marketingPackageName, groupProductName, areaName, businessContractName)[0];
        long companyStructurePK = companyStructureVO.getProductStructurePK();
        String optionId = rootElement.selectSingleNode("AnnuityOption").getText();
        String areaId = rootElement.selectSingleNode("IssueState").getText();
        String costBasis = rootElement.selectSingleNode("CostBasis").getText();
        String purchaseAmount = rootElement.selectSingleNode("PurchaseAmount").getText();
        String effectiveDate = rootElement.selectSingleNode("EffectiveDate").getText();
        SegmentVO segmentVO = new SegmentVO();
        segmentVO.setProductStructureFK(companyStructurePK);
        segmentVO.setOptionCodeCT(optionId);
        segmentVO.setSegmentNameCT(Segment.SEGMENTNAMECT_PAYOUT);
        segmentVO.setIssueStateCT(areaId);
        segmentVO.setCostBasis(new BigDecimal(costBasis));
        segmentVO.setAmount(new BigDecimal(purchaseAmount));
        segmentVO.setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(effectiveDate).getFormattedDate());

        // Payout
        String certainDuration = rootElement.selectSingleNode("CertainDuration").getText();
        String frequencyId = rootElement.selectSingleNode("Frequency").getText();
        String paymentAmount = rootElement.selectSingleNode("PaymentAmount").getText();
        String startDate = rootElement.selectSingleNode("StartDate").getText();
        String postJune1986InvestmentInd = rootElement.selectSingleNode("PostJune1986InvestmentInd").getText();
        PayoutVO payoutVO = new PayoutVO();
        payoutVO.setCertainDuration(Integer.parseInt(certainDuration));
        payoutVO.setPaymentFrequencyCT(frequencyId);
        payoutVO.setPaymentAmount(new BigDecimal(paymentAmount));
        payoutVO.setPaymentStartDate(DateTimeUtil.getEDITDateFromMMDDYYYY(startDate).getFormattedDate());
        payoutVO.setPostJune1986Investment(postJune1986InvestmentInd == null ? "N" : "Y");

        // ContractClient
        String issueAge = rootElement.selectSingleNode("IssueAge").getText();
        ContractClientVO contractClientVO = new ContractClientVO();
        contractClientVO.setIssueAge(Integer.parseInt(issueAge));

        // ClientRole
        ClientRoleVO clientRoleVO = new ClientRoleVO();
        clientRoleVO.setRoleTypeCT(ClientRole.ROLETYPECT_ANNUITANT);

        // ClientDetail
        String birthDate = Util.initString(rootElement.selectSingleNode("BirthDate").getText(), null);
        String genderId = rootElement.selectSingleNode("GenderType").getText();
        ClientDetailVO clientDetailVO = new ClientDetailVO();
        if (birthDate != null)
        {
            clientDetailVO.setBirthDate(DateTimeUtil.getEDITDateFromMMDDYYYY(birthDate).getFormattedDate());
        }
        clientDetailVO.setGenderCT(genderId);

        clientDetailVO.addClientRoleVO(clientRoleVO);

        segmentVO.addContractClientVO(contractClientVO);
        segmentVO.addPayoutVO(payoutVO);

        quoteVO.addSegmentVO(segmentVO);
        quoteVO.addClientDetailVO(clientDetailVO);

        return quoteVO;
    }

    /**
     * Executes the quote.
     * @param quoteVO
     * @return
     * @throws Exception
     */
    private QuoteVO executeQuote(QuoteVO quoteVO) throws Exception
    {
        Event eventComponent = new EventComponent();

        quoteVO = eventComponent.getNewBusinessQuote(quoteVO);

        return quoteVO;
    }

    /**
     * Builds response message from quote result.
     * @param quoteVO
     */
    private void buildResponse(QuoteVO quoteVO)
    {
        String resultAsXML = populateQuoteResults(quoteVO);

        soapResponse = WebServiceUtil.buildSOAPResponse(soapRequest, "NewBusinessQuoteRs", resultAsXML);
    }

    /**
     * Helper method to extract the necessary field values from QuoteVO.
     * @param quoteVO
     * @return
     */
    private String populateQuoteResults(QuoteVO quoteVO)
    {
        Document resultDocument = DocumentHelper.createDocument();

        Element rootElement = resultDocument.addElement("NewBusinessQuoteOutput");

        SegmentVO segmentVO = quoteVO.getSegmentVO(0);

        PayoutVO payoutVO = segmentVO.getPayoutVO(0);

        rootElement.addElement("CertainDuration").setText(payoutVO.getCertainDuration()+"");

        rootElement.addElement("FinalDistributionAmount").setText(new EDITBigDecimal(payoutVO.getFinalDistributionAmount()).toString());

        rootElement.addElement("PremiumTaxes").setText(new EDITBigDecimal(segmentVO.getCharges()).toString());

        rootElement.addElement("FrontEndLoads").setText(new EDITBigDecimal(segmentVO.getLoads()).toString());

        rootElement.addElement("Fees").setText(new EDITBigDecimal(segmentVO.getFees()).toString());

        rootElement.addElement("TotalProjectedAnnuity").setText(new EDITBigDecimal(payoutVO.getTotalExpectedReturnAmount()).toString());

        rootElement.addElement("ExclusionRatio").setText(new EDITBigDecimal(payoutVO.getExclusionRatio()).toString());

        rootElement.addElement("YearlyTaxableBenefit").setText(new EDITBigDecimal(payoutVO.getYearlyTaxableBenefit()).toString());

        rootElement.addElement("CommutedValue").setText(new EDITBigDecimal(quoteVO.getCommutedValue()).toString());

        rootElement.addElement("PurchaseAmount").setText(new EDITBigDecimal(segmentVO.getAmount()).toString());

        rootElement.addElement("PaymentAmount").setText(new EDITBigDecimal(payoutVO.getPaymentAmount()).toString());

        return resultDocument.asXML();
    }
}