package contract.ui.servlet;

import billing.BillSchedule;
import edit.portal.common.transactions.Transaction;

public abstract class AbstractContractTran extends Transaction {

    protected static final String UNIVERSAL_LIFE = "UL";

	/**
	 * Determines which billing dialog to display depending on the bill method and
	 * product type.
	 *
	 * @param billMethodCT
	 * @param productType
	 *
	 * @return returns the List dialog if the billMethodCT is List, otherwise,
	 *         returns the individual dialog
	 */
	protected String determineBillingDialogToDisplay(String billMethodCT, String productType) 
	{
		if (!UNIVERSAL_LIFE.equalsIgnoreCase(productType)) 
		{
			if (billMethodCT.equals(BillSchedule.BILL_METHOD_LISTBILL)) 
			{
				return getListBillingDialog();
			} 
			else 
			{
				return getIndividualBillingDialog();
			}
		} 
		else 
		{
			if (billMethodCT.equals(BillSchedule.BILL_METHOD_LISTBILL)) 
			{
				return getListULBillingDialog();
			} 
			else 
			{
				return getIndividualULBillingDialog();
			}
		}
	}
	
	protected abstract String getListBillingDialog();
	protected abstract String getListULBillingDialog();
	protected abstract String getIndividualBillingDialog();
	protected abstract String getIndividualULBillingDialog();

}
