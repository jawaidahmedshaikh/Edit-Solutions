/*
 * User: dlataill
 * Date: Jun 22, 2007
 * Time: 9:04:21 AM
 * 
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package billing.business;

import billing.BillSchedule;

import edit.common.EDITDate;
import edit.common.exceptions.EDITCaseException;

import group.ContractGroup;

import org.dom4j.Document;

public interface Billing {
	/**
	 * Saves specified BillSchedule and PayrollDeductionSchedule associated with
	 * specified ContractGroup.
	 * 
	 * @param billSchedule
	 * @param contractGroup
	 * @param changeEffectiveDate if supplied, the ChangeHistory.EffectiveDate will
	 *                            be set to this value
	 * @throws EDITCaseException
	 */
	public void saveBillScheduleUpdate(BillSchedule billSchedule, ContractGroup contractGroup,
			EDITDate changeEffectiveDate) throws EDITCaseException;

	// SERVICES
	
	/**
	 * Dummy method that accepts a request document as an argument. This method just
	 * calls the method that does not accept an argument since it is not needed for
	 * the service. This method is needed to support a standard framework for the
	 * user interface. Its existence may be temporary.
	 *
	 * @param requestDocument document which is ignored
	 *
	 * @return response document
	 * @see Billing#getBillGroupsNotPaid()
	 */
	public Document getBillGroupsNotPaid(Document requestDocument);

	/**
	 * Finds all the BillGroups that were not paid. "Not paid" is when the
	 * BillGroup.ReleaseDate has not been set (is null). Returns the BillGroups and
	 * their associated BillSchedules and Group ContractGroups
	 *
	 * @return SEGResponseVO containing the following structure:
	 *
	 *         <SEGResponseVO> <BillGroupVO> ... <BillScheduleVO> ...
	 *         <ContractGroupVO> // Group ... <ContractGroupVO> </BillScheduleVO>
	 *         </BillGroupVO> </SEGResponseVO>
	 */
	public Document getBillGroupsNotPaid();

	/**
	 * Get all the Payors for a given BillGroup. For each Payor, return the
	 * ClientDetail and the calculated TotalBilledAmount and TotalPaidAmount for all
	 * of the Payor's contracts within the BillGroup.
	 *
	 * @param requestDocument SEGRequestVO containing the following structure:
	 *
	 *                        <SEGRequestVO> <RequestParameters>
	 *                        <BillGroupPK>1</BillGroupPK> </RequestParameters>
	 *                        </SEGRequestVO>
	 *
	 * @return SEGResponseVO containing the following structure:
	 *
	 *         <SEGResponseVO> <ClientDetailVO> ...
	 *         <TotalBilledAmount></TotalBilledAmount>
	 *         <TotalPaidAmount></TotalPaidAmount> </ClientDetailVO>
	 *         </SEGResponseVO>
	 */
	public Document getPayorsOfBillGroup(Document requestDocument);

	/**
	 * Get all the Bills for a given Payor within a given BillGroup
	 *
	 * @param requestDocument SEGRequestVO containing the following structure:
	 *
	 *                        <SEGRequestVO> <RequestParameters>
	 *                        <BillGroupPK>1</BillGroupPK>
	 *                        <ClientDetailPK>1</ClientDetailPK>
	 *                        </RequestParameters> </SEGRequestVO>
	 *
	 * @return SEGResponseVO containing the following structure:
	 *
	 *         <SEGResponseVO> <BillVO> // repeated for each Bill ... <SegmentVO>
	 *         ... <ProductStructureVO> ... <CompanyVO> ... </CompanyVO>
	 *         </ProductStructureVO> <ClientDetailVO> // insured client ...
	 *         </ClientDetailVO> </SegmentVO> </BillVO> </SEGResponseVO>
	 */
	public Document getBillsForPayorInBillGroup(Document requestDocument);

	/**
	 * Get all the Bills for all Contracts within a given BillGroup
	 *
	 * @param requestDocument SEGRequestVO containing the following structure:
	 *
	 *                        <SEGRequestVO> <RequestParameters>
	 *                        <BillGroupPK>1</BillGroupPK> </RequestParameters>
	 *                        </SEGRequestVO>
	 *
	 * @return SEGResponseVO containing the following structure:
	 *
	 *         <SEGResponseVO> <BillVO> // repeated for each Bill ... <SegmentVO>
	 *         ... <ProductStructureVO> ... <CompanyVO> ... </CompanyVO>
	 *         </ProductStructureVO> <ClientDetailVO> // insured client ...
	 *         </ClientDetailVO> </SegmentVO> </BillVO> </SEGResponseVO>
	 */
	public Document getBillsForContractInBillGroup(Document requestDocument);

	/**
	 * Changes the paidAmount for the Bills
	 *
	 * @param requestDocument
	 *
	 *                        <SEGRequestVO> <RequestParameters> <AdjustmentVO> //
	 *                        repeats for each adjustment <BillPK>1</BillPK>
	 *                        <PaidAmount>122.22</PaidAmount> </AdjustmentVO>
	 *                        </RequestParameters> </SEGRequestVO>
	 *
	 * @return SEGResponseVO containing the following structure:
	 *
	 *         <SEGResponseVO> <ResponseMessageVO> ... </ResponseMessageVO>
	 *         </SEGResponseVO>
	 */
	public void adjustBillPaidAmounts(Document requestDocument);

	/**
	 * Updates the BillGroup
	 *
	 * @param requestDocument
	 *
	 *                        <SEGRequestVO> <RequestParameters> <BillGroupVO> ...
	 *                        </BillGroupVO> </RequestParameters> </SEGRequestVO>
	 *
	 * @return SEGResponseVO containing the following structure:
	 *
	 *         <SEGResponseVO> <ResponseMessageVO> ... </ResponseMessageVO>
	 *         </SEGResponseVO>
	 */
	public void updateBillGroup(Document requestDocument);
}
