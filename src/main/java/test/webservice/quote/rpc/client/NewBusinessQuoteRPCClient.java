/*
 * User: sprasad
 * Date: Nov 21, 2006
 * Time: 9:43:10 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package test.webservice.quote.rpc.client;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import webservice.service.rpc.NewBusinessQuoteInput;
import webservice.service.rpc.NewBusinessQuoteOutput;

import javax.xml.namespace.QName;
import java.util.Calendar;

public class NewBusinessQuoteRPCClient
{
    public static void main(String[] args) throws Exception
    {
        RPCServiceClient serviceClient = new RPCServiceClient();

        Options options = serviceClient.getOptions();

        EndpointReference targetEPR = new EndpointReference("http://syam:8988/PORTAL/services/NewBusinessQuote");

        options.setTo(targetEPR);

        QName opGetNewBusinessQuote = new QName("http://rpc.service.webservice/xsd", "getNewBusinessQuote");

        NewBusinessQuoteInput input = new NewBusinessQuoteInput();
        input.setScenario(4);
        input.setQuoteDate(Calendar.getInstance());
        // Commented while integrating code from BugFixesToBuild5230ForBase ... Vision does not have CompanyStructure
        // table .... this issue need to be addressed when we need to test services (Axis). -- SL
//      input.setCompanyStructureId("SEG,*,*,*,FixPayout");
        input.setAnnuityOption("LOA");
        input.setIssueState("CT");
        input.setFrequency("Monthly");
        input.setCostBasis((double) 50000.00);
        input.setCertainDuration(0);
        input.setPurchaseAmount((double) 0);
        input.setPaymentAmount((double) 500.00);
        input.setEffectiveDate(Calendar.getInstance());
        input.setStartDate(Calendar.getInstance());
        input.setPostJune1986InvestmentInd("N");
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(1800, 1, 1);
        input.setBirthDate(birthDate);
        input.setIssueAge(25);
        input.setGender("Male");

        Object[] opGetNewBusinessQuoteArgs = new Object[] { input };
        Class[] returnTypes = new Class[] { NewBusinessQuoteOutput.class };


        Object[] response = serviceClient.invokeBlocking(opGetNewBusinessQuote,
                opGetNewBusinessQuoteArgs, returnTypes);

        NewBusinessQuoteOutput output = (NewBusinessQuoteOutput) response[0];

        if (output == null) {
            System.out.println("Output is #NULL#");
            return;
        }

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
