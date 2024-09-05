package com.segsoftware.business
{
	import com.segsoftware.model.SEGModelLocator;
	import com.adobe.cairngorm.business.ServiceLocator;
	import mx.rpc.http.HTTPService;
	import com.segsoftware.model.SEGModelBuilder;
	import mx.rpc.IResponder;
	import mx.rpc.AsyncToken;
	import com.segsoftware.utility.Util;
	import com.segsoftware.command.SEGRequest;
	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;	

	
	/**
	 * A "Proxy" to remote Billing service(s). It is likely that this will serve
	 * as a proxy to a single BillingComponent service. By abstracting this service
	 * through a delegate, it is possible to:
	 * 
	 * 1. Stub-out services from the perspective of this RIA front-end until the 
	 * target service is fully functional.
	 * 
	 * 2.  Combine service(s) deployed as HTTP or SOAP under one conceptual umbrella.
	 */
	public class BillingDelegate extends SEGDelegate
	{
		public function BillingDelegate(resultFunction:Function, faultFunction:Function, requestParameters:XMLListCollection)
		{
			super(resultFunction, faultFunction, requestParameters);
		}
		
		/**
		 * The document of all BillGroups that have a BillGroup.TotalBilledAmount > BillGroup.TotalPaidAmount.
		 * The returned structure is as follows:
		 * <SEGResponseVO>
		 * 	<BillGroupVO>
		 * 		<BillVO/>
		 * 		...
		 * 		<BillVO/>
		 * 	</BillGroupVO>
		 *  ...
		 *  <BillGroupVO>
		 * </SEGResponseVO>
		 */ 
		public function getBillGroupsNotPaid():void
		{			
			var segRequest:SEGRequest = new SEGRequest("Billing", "getBillGroupsNotPaid", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		} 
		
		/**
		 * Gets all Payors (unique) for the currently selected
		 * BillGroup and their associated Bill total. 
		 * The ClientDetail will  have one summation
		 * Bill since the ClientDetail (as payor 'POR') may be 
		 * paying across multiple policies. The Bill shouldn't
		 * have any PK since it is a summation across multiple
		 * Bills. If it does have a PK, it will be ignored.
		 * 
		 * <SEGResponseVO>
		 * 	<PayorVO>
		 * 		<ClientDetailVO>
		 * 			<BillVO/> (a summation across all Bills)
		 * 		</ClientDetailVO>
		 * 	</PayorVO>
		 * </SEGResponseVO>
		 */ 
		public function getPayorsOfBillGroup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Billing", "getPayorsOfBillGroup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * The document that shows all Policies and their associated Bills for the
		 * currently selected Payor. The Payor was previously identified as a unique
		 * Payor within the currently selected BillGroup. I [believe] that there
		 * could be more than Bill for the Segment within a time period?
		 * 
		 * The expected document resembles:
		 * 
		 * <SEGResponseVO>
		 * 	<PayorPolicyBiilsVO>
		 * 		<CompanyName/> {from the Segment's associated ProductStructure}
		 * 		<SegmentVO/>
		 * 		<BillVO/>
		 * 		...
		 * 		<BillVO/>
		 * 	</PayorPolicyBillsVO>
		 *  ...
		 * </SEGResponseVO>
		 */ 
		public function getBillsForPayorInBillGroup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Billing", "getBillsForPayorInBillGroup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);					
		}
		
		/**
		 * The document that shows all Policies and their associated Bills for the
		 * currently selected BillGroup. 
		 * 
		 * The expected document resembles:
		 * 
		 * <SEGResponseVO>
		 * 	<PayorPolicyBiilsVO>
		 * 		<CompanyName/> {from the Segment's associated ProductStructure}
		 * 		<SegmentVO/>
		 * 		<BillVO/>
		 * 		...
		 * 		<BillVO/>
		 * 	</PayorPolicyBillsVO>
		 *  ...
		 * </SEGResponseVO>
		 */ 
		public function getBillsForContractInBillGroup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Billing", "getBillsForContractInBillGroup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);					
		}
				
		/**
		 * For the current PayorPolicyBillsVO, there have been Bill.PaidAmounts
		 * that were adjusted (possibly more than one). This collects all BillPKs and
		 * their associated BillAmount and sends it. The is a course action in that
		 * some of the BillAmounts may [not] have even changed. This is left to the
		 * implementing service to determine if it is necessary to make that distinction.
		 */
		public function adjustBillPaidAmounts():void
		{
			var segRequest:SEGRequest = new SEGRequest("Billing", "adjustBillPaidAmounts", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * The user can update the BillGroup.StatusCT and the BillGroup.[Overage, Shortage, Credit] funds amounts.
		 */  
		public function updateBillGroup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Billing", "updateBillGroup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		

	}
}




