package billing.ui.servlet;

import edit.portal.common.transactions.Transaction;

import fission.global.AppReqBlock;

/**
 * Captures all UI requests related to Billing operations.
 * Once captures, the script-like method will delegate to the targeted
 * business service, gather the results, and then render the appropriate
 * view page.
 */
public class BillingTran  extends Transaction
{
    // ACTIONS
    private static String GROUPBILL_ADJUSTMENT_SERVICE = "groupBillAdjustmentService";
    
    // PAGES
    private static String BILLING_SERVICE_JSP = "/billing/jsp/billingService.jsp";

    /**
     * Identifies the "action" in each of the "transaction/action" requests
     * coming from the UI and forwards it to the targeted action method.
     * @param appReqBlock
     * @return
     */
    public String execute(AppReqBlock appReqBlock)
    {
        String action = null;
        
        if (action.equals(GROUPBILL_ADJUSTMENT_SERVICE))
        {
            return billingService(appReqBlock);
        }
        else
        {
            String msg = "The transaction/action of [BillingTran/" + action + "] was not recognized";
        
            System.out.println(msg);
            
            throw new RuntimeException(msg);
        }
    }

    
    /**
     * In the absence of webservices (a // TODO at for this iteration), a JSP
     * page is rendered that is able to respond to the simulated web-service
     * requests. The rendered JSP will determine the "service" (supplied in 
     * the request params), and then invoke the targeted service on
     * the BillingComponent. The results are "always?" XML. The reader
     * of this Javadoc is encourage to look at the JSP and BillingComponent.
     * @param appReqBlock
     * @see billing.business.Billing
     * @see billing.component.BillingComponent
     * @return
     */
    private String billingService(AppReqBlock appReqBlock)
    {
        return BILLING_SERVICE_JSP;
    }
}
