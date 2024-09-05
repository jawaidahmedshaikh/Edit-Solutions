/*
 * User: sprasad
 * Date: Nov 17, 2006
 * Time: 3:17:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package test.webservice.quote.rpc.client;

import java.util.Calendar;


public class NewBusinessQuoteClient
{
    public static void main(String[] args) throws Exception
    {
        NewBusinessQuoteStub stub = new NewBusinessQuoteStub();

        //Create the request
        NewBusinessQuoteStub.GetNewBusinessQuote request = new NewBusinessQuoteStub.GetNewBusinessQuote();

        NewBusinessQuoteStub.NewBusinessQuoteInput input = new NewBusinessQuoteStub.NewBusinessQuoteInput();
        input.setScenario(4);
        input.setQuoteDate(Calendar.getInstance());
        input.setCompanyStructureId("SEG,*,*,*,FixPayout");
        input.setAnnuityOption("LOA");
        input.setIssueState("CT");
        input.setFrequency("Monthly");
        input.setCostBasis((double) 50000.00);
        input.setCertainDuration(0);
        input.setPurchaseAmount((double) 50000.00);
        input.setPaymentAmount((double) 50000.00);
        input.setEffectiveDate(Calendar.getInstance());
        input.setStartDate(Calendar.getInstance());
        input.setPostJune1986InvestmentInd("N");
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(1981, 1, 1);
        input.setBirthDate(birthDate);
        input.setIssueAge(25);
        input.setGender("Male");

        request.setNewBusinessQuoteInput(input);

        //Invoke the service
        NewBusinessQuoteStub.GetNewBusinessQuoteResponse response = stub.getNewBusinessQuote(request);
        NewBusinessQuoteStub.NewBusinessQuoteOutput output = response.get_return();

        // Displaying the output
        System.out.println("CertainDuration: " + output.getCertainDuration());
        System.out.println("FinalDistributionAmount: " + output.getFinalDistributionAmount());
        System.out.println("PremiumTaxes: " + output.getPremiumTaxes());
        System.out.println("FrontEndLoads: " + output.getFrontEndLoads());
        System.out.println("Fees: " + output.getFees());
        System.out.println("TotalProjectionAnnuity: " + output.getTotalProjectedAnnuity());
        System.out.println("ExclusionRatio: " + output.getExclusionRatio());
        System.out.println("YearlyTaxableBenefit: " + output.getYearlyTaxableBenefit());
        System.out.println("CommutedValue: " + output.getCommutedValue());
        System.out.println("PurchaseAmount: " + output.getPurchaseAmount());
        System.out.println("PaymentAmount: " + output.getPaymentAmount());
    }
}
