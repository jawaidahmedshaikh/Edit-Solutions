/*
 * User: sprasad
 * Date: Nov 16, 2006
 * Time: 4:49:39 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice.service.rpc;

import contract.Segment;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.vo.*;
import engine.dm.dao.ProductStructureDAO;
import event.business.Event;
import event.component.EventComponent;
import fission.utility.Util;
import role.ClientRole;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.axis2.AxisFault;

/**
 * Implementation class for the NewBusinessQuote (RPC version).
 */
public class NewBusinessQuoteImpl implements NewBusinessQuote
{
    /**
     * Service method to invoke to get New Business Quote.
     * @param newBusinessQuoteInput
     * @see NewBusinessQuote
     * @return
     */
    public NewBusinessQuoteOutput getNewBusinessQuote(NewBusinessQuoteInput newBusinessQuoteInput) throws AxisFault
    {
        QuoteVO quoteVO = new QuoteVO();

        try
        {
            quoteVO = buildQuoteVO(newBusinessQuoteInput);

            Event eventComponent = new EventComponent();

            quoteVO = eventComponent.getNewBusinessQuote(quoteVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new AxisFault(e);
        }

        return formatOutput(quoteVO);
    }

    /**
   * Since different quote scenarios required different input paramaters/values,
   * there are multiple ways to build the desired QuoteVO to send to PRASE. This
   * method builds the base QuoteVO, and then tailors it based on the currently
   * defined scenario (the client requests the specific scenario to run).
   * @see NewBusinessQuoteInput
   * @param newBusinessQuoteInput
   * @return
   */
    private QuoteVO buildQuoteVO(NewBusinessQuoteInput newBusinessQuoteInput)
    {
        int scenario = newBusinessQuoteInput.getScenario();
        java.util.Calendar quoteDate = newBusinessQuoteInput.getQuoteDate();
        String productStructureId = newBusinessQuoteInput.getProductStructureId();
        String annuityOption = newBusinessQuoteInput.getAnnuityOption();
        String issueState = newBusinessQuoteInput.getIssueState();
        String frequency = newBusinessQuoteInput.getFrequency();
        double costBasis = newBusinessQuoteInput.getCostBasis();
        int certainDuration = newBusinessQuoteInput.getCertainDuration();
        double purchaseAmount = newBusinessQuoteInput.getPurchaseAmount();
        double paymentAmount = newBusinessQuoteInput.getPaymentAmount();
        java.util.Calendar effectiveDate = newBusinessQuoteInput.getEffectiveDate();
        java.util.Calendar startDate = newBusinessQuoteInput.getStartDate();
        String postJune1986InvestmentInd = newBusinessQuoteInput.getPostJune1986InvestmentInd();
        java.util.Calendar birthDate = newBusinessQuoteInput.getBirthDate();
        int issueAge = newBusinessQuoteInput.getIssueAge();
        String gender = newBusinessQuoteInput.getGender();

        QuoteVO quoteVO = new QuoteVO();

        quoteVO.setQuoteDate(formatDateToYYYYMMDD(quoteDate));

        String[] productStructureTokens = Util.fastTokenizer(productStructureId, ",");
        String companyName = productStructureTokens[0];
        String marketingPackageName = productStructureTokens[1];
        String groupProductName = productStructureTokens[2];
        String areaName = productStructureTokens[3];
        String businessContractName = productStructureTokens[4];
        ProductStructureVO productStructureVO = new ProductStructureDAO().
                findProductStructureByNames(companyName, marketingPackageName, groupProductName, areaName, businessContractName)[0];
        long productStructurePK = productStructureVO.getProductStructurePK();

        // Segment
        SegmentVO segmentVO = new SegmentVO();
        segmentVO.setProductStructureFK(productStructurePK);
        segmentVO.setOptionCodeCT(annuityOption);
        segmentVO.setSegmentNameCT(Segment.SEGMENTNAMECT_PAYOUT);
        segmentVO.setIssueStateCT(issueState);
        segmentVO.setCostBasis(new BigDecimal(costBasis));
        segmentVO.setEffectiveDate(formatDateToYYYYMMDD(effectiveDate));

        // Payout
        PayoutVO payoutVO = new PayoutVO();
        payoutVO.setCertainDuration(certainDuration);
        payoutVO.setPaymentFrequencyCT(frequency);
        payoutVO.setPaymentStartDate(formatDateToYYYYMMDD(startDate));
        payoutVO.setPostJune1986Investment(postJune1986InvestmentInd == null ? "N" : "Y");

        // ContractClient
        ContractClientVO contractClientVO = new ContractClientVO();

        // ClientRole
        ClientRoleVO clientRoleVO = new ClientRoleVO();
        clientRoleVO.setRoleTypeCT(ClientRole.ROLETYPECT_ANNUITANT);

        // ClientDetail
        ClientDetailVO clientDetailVO = new ClientDetailVO();
        clientDetailVO.setGenderCT(gender);

        switch(scenario)
        {
            case NewBusinessQuoteInput.SCENARIO_ONE:
                setScenarioOne(segmentVO, clientDetailVO, contractClientVO, purchaseAmount, birthDate);
                break;

            case NewBusinessQuoteInput.SCENARIO_TWO:
                setScenarioTwo(segmentVO, contractClientVO, purchaseAmount, issueAge);
                break;

            case NewBusinessQuoteInput.SCENARIO_THREE:
                setScenarioThree(payoutVO, clientDetailVO, contractClientVO, paymentAmount, birthDate);
                break;

            case NewBusinessQuoteInput.SCENARIO_FOUR:
                setScenarioFour(payoutVO, contractClientVO, paymentAmount, issueAge);
        }

        clientDetailVO.addClientRoleVO(clientRoleVO);

        segmentVO.addContractClientVO(contractClientVO);
        segmentVO.addPayoutVO(payoutVO);

        quoteVO.addSegmentVO(segmentVO);
        quoteVO.addClientDetailVO(clientDetailVO);

        return quoteVO;
    }

    /**
   * Tailors the QuoteVO to scenario_one requirements.
   * @param segmentVO
   * @param clientDetailVO
   * @param contractClientVO
   * @param purchaseAmount
   * @param birthDate
   */
    private void setScenarioOne(SegmentVO segmentVO, ClientDetailVO clientDetailVO, ContractClientVO contractClientVO, double purchaseAmount, java.util.Calendar birthDate)
    {
        segmentVO.setAmount(new BigDecimal(purchaseAmount));
        clientDetailVO.setBirthDate(formatDateToYYYYMMDD(birthDate));
        contractClientVO.setIssueAge(0);
    }

/**
   * Tailors the QuoteVO to scenario_two requirements.
   * @param segmentVO
   * @param contractClientVO
   * @param purchaseAmount
   * @param issueAge
   */
    private void setScenarioTwo(SegmentVO segmentVO, ContractClientVO contractClientVO, double purchaseAmount, int issueAge)
    {
        segmentVO.setAmount(new BigDecimal(purchaseAmount));
        contractClientVO.setIssueAge(issueAge);
    }

/**
   * Tailors the QuoteVO to scenario_three requirements.
   * @param payoutVO
   * @param clientDetailVO
   * @param contractClientVO
   * @param paymentAmount
   * @param birthDate
   */
    private void setScenarioThree(PayoutVO payoutVO, ClientDetailVO clientDetailVO, ContractClientVO contractClientVO, double paymentAmount, java.util.Calendar birthDate)
    {
        payoutVO.setPaymentAmount(new EDITBigDecimal(paymentAmount+"").getBigDecimal());
        clientDetailVO.setBirthDate(formatDateToYYYYMMDD(birthDate));
        contractClientVO.setIssueAge(0);
    }

/**
   * Tailors the QuoteVO to scenario_four requirements.
   * @param payoutVO
   * @param contractClientVO
   * @param paymentAmount
   * @param issueAge
   */
    private void setScenarioFour(PayoutVO payoutVO, ContractClientVO contractClientVO, double paymentAmount, int issueAge)
    {
        payoutVO.setPaymentAmount(new EDITBigDecimal(paymentAmount+"").getBigDecimal());
        contractClientVO.setIssueAge(issueAge);
    }

    /**
   * The results of the quote as coming from PRASE need to be formatted in a
   * convenient return-type for the calling client; specifically, NewBusinessQuoteOutput.
   * @param quoteVO
   * @return
   */
    private NewBusinessQuoteOutput formatOutput(QuoteVO quoteVO)
    {
        NewBusinessQuoteOutput newBusinessQuoteOutput = new NewBusinessQuoteOutput();

        SegmentVO segmentVO = quoteVO.getSegmentVO(0);
        PayoutVO payoutVO = segmentVO.getPayoutVO(0);

        newBusinessQuoteOutput.setCertainDuration(payoutVO.getCertainDuration());
        newBusinessQuoteOutput.setFinalDistributionAmount(payoutVO.getFinalDistributionAmount() == null?0d:payoutVO.getFinalDistributionAmount().doubleValue());
        newBusinessQuoteOutput.setPremiumTaxes(segmentVO.getCharges()==null?0d:segmentVO.getCharges().doubleValue());
        newBusinessQuoteOutput.setFrontEndLoads(segmentVO.getLoads()==null?0d:segmentVO.getLoads().doubleValue());
        newBusinessQuoteOutput.setFees(segmentVO.getLoads()==null?0d:segmentVO.getFees().doubleValue());
        newBusinessQuoteOutput.setTotalProjectedAnnuity(payoutVO.getTotalExpectedReturnAmount()==null?0d:payoutVO.getTotalExpectedReturnAmount().doubleValue());
        newBusinessQuoteOutput.setExclusionRatio(payoutVO.getExclusionRatio()==null?0d:payoutVO.getExclusionRatio().doubleValue());
        newBusinessQuoteOutput.setYearlyTaxableBenefit(payoutVO.getYearlyTaxableBenefit()==null?0d:payoutVO.getYearlyTaxableBenefit().doubleValue());
        newBusinessQuoteOutput.setCommutedValue(quoteVO.getCommutedValue()==null?0d:quoteVO.getCommutedValue().doubleValue());
        newBusinessQuoteOutput.setPurchaseAmount(segmentVO.getAmount()==null?0d:segmentVO.getAmount().doubleValue());
        newBusinessQuoteOutput.setPaymentAmount(payoutVO.getPaymentAmount()==null?0d:payoutVO.getPaymentAmount().doubleValue());

        return newBusinessQuoteOutput;
    }

    /**
   * Convenience method to format the Calendar object to a date in our standard
   * EDITDate format.
   * @param calendar
   * @return
   */
    private String formatDateToYYYYMMDD(java.util.Calendar calendar)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        dateFormat.applyPattern(EDITDate.DATE_FORMAT);

        java.util.Date date = calendar.getTime();

        return dateFormat.format(date);
    }
}
