/*
 * User: gfrosti
 * Date: Nov 17, 2004
 * Time: 10:40:38 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.business;

import edit.services.component.*;
import edit.common.vo.*;
import edit.common.exceptions.*;



public interface Reinsurance extends ILockableElement
{
    /**
     * Returns the current list of all TreatyGroups.
     * @return
     */
    public TreatyGroupVO[] findAllTreatyGroups();

    /**
     * Finds the set of Treaties that have been mapped to this specicied Product Structure.
     * @param productStructurePK
     * @return
     */
    public TreatyVO[] findTreatiesBy_ProductStructurePK(long productStructurePK);

    /**
     *
     * @param treatyGroupVO
     */
    public void saveTreatyGroup(TreatyGroupVO treatyGroupVO) throws EDITReinsuranceException;

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public TreatyGroupVO findTreatyGroupBy_TreatyGroupPK(long treatyGroupPK);

    /**
     * Deletes the specified TreatyGroup.
     * @see reinsurance.TreatyGroup#delete()
     * @param treatyGroupPK
     * @throws EDITReinsuranceException
     */
    public void deleteTreatyGroup(long treatyGroupPK) throws EDITReinsuranceException;

    /**
     * Saves or updates the supplied Reinsurer.
     * @param reinsurerVO
     * @return
     */
    public ReinsurerVO saveReinsurer(ReinsurerVO reinsurerVO) throws EDITReinsuranceException;

    /**
     * Saves or updates the supplied Treaty.
     * @param treatyVO
     * @return
     */
    public TreatyVO saveTreaty(TreatyVO treatyVO);

    /**
     * Finder.
     * @param reinsurerNumber
     * @return
     */
    public TreatyVO[] findTreatiesBy_ReinsurerNumber(String reinsurerNumber);

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
    public ReinsurerVO[] findReinsurersBy_PartialCorporateName(String partialCorporateName);

    /**
     * Finder.
     * @param reinsurerNumber
     * @return
     */
    public ReinsurerVO findReinsurerBy_ReinsurerNumber(String reinsurerNumber);

    /**
     * Finder.
     * @param taxIdentification
     * @return
     */
    public ReinsurerVO findReinsurerBy_TaxIdentication(String taxIdentification);

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ReinsurerVO findReinsurerBy_ReinsurerPK(long reinsurerPK);

    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public TreatyGroupVO[] findTreatyGroupsBy_ProductStructurePK(long productStructurePK);

    /**
     * Associates the specified Treaty Group to the specified Product Structure.
     * @param treatyGroupPK
     * @param productStructurePK
     */
    public void attachTreatyGroupToProductStructure(long treatyGroupPK, long productStructurePK) throws EDITReinsuranceException;

    /**
     * Disssociates the specified Treaty Group to the specified Product Structure.
     * @param treatyGroupPK
     * @param productStructurePK
     */
    public void detachTreatyGroupFromProductStructure(long treatyGroupPK, long productStructurePK) throws EDITReinsuranceException;

    /**
     * Associates the specified TreatyGroup and all of its Treaties to the specified Segment as ContractTreaties.
     * @param treatyGroupPK
     * @param segmentPK
     */
    public void attachTreatyGroupToSegment(long treatyGroupPK, long segmentPK) throws EDITReinsuranceException;

    /**
     * Associates the specified treatyGroup and all of its Treaties to the specified ContractGroup as ContractTreaties.
     * @param treatyGroupPK
     * @param contractGroupPK
     * @throws edit.common.exceptions.EDITReinsuranceException
     */
    public void attachTreatyGroupToContractGroup(long treatyGroupPK, long contractGroupPK) throws EDITReinsuranceException;

    /**
     * Finder.
     * @param casePK
     * @param segmentPK
     * @param treatyPK
     * @return
     */
//    public ContractTreatyVO findContractTreatyBy_CasePK_SegmentPK_TreatyPK(long casePK, long segmentPK, long treatyPK);

    /**
     * Disssociates the specified Treaty from the specified Segment.
     * @param treatyGroupPK
     * @param segmentPK
     */
    public void detachTreatyGroupFromSegment(long treatyGroupPK, long segmentPK) throws EDITReinsuranceException;

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public TreatyVO[] findTreatiesBy_TreatyGroupPK(long treatyGroupPK);

    /**
     * Saves the supplied Contract Treaty.
     * @param contractTreatyVO
     * @return
     */
    public ContractTreatyVO saveContractTreatyOverrides(ContractTreatyVO contractTreatyVO) throws EDITReinsuranceException;

    /**
     * @see reinsurance.ReinsuranceProcess#updateReinsuranceBalances(String, String)
     * @param productName
     * @param operator
     */
    public void updateReinsuranceBalances(String companyName, String operator);

    /**
     * @see reinsurance.ReinsuranceProcess#setupReinsuranceChecks(String, String)
     * @param companyName
     * @param operator
     */
    public void createReinsuranceCheckTransactions(String companyName, String operator);

    /**
     * Finds the specified TreatyGroup if, in fact, it has been associated with the specified Segment.
     * @param treatyGroupPK
     * @param segmentPK
     * @return
     */
    public TreatyGroupVO findTreatyGroupBy_TreatyGroupPK_SegmentPK(long treatyGroupPK, long segmentPK);

    /**
     * Finder.
     * @param segmentPK
     * @param treatyPK
     * @return
     */
    public ContractTreatyVO findContractTreatyBy_SegmentPK_TreatyPK(long segmentPK, long treatyPK);

    /**
     * Attaches the specified Treaty to the specified Segment
     * @param treatyPK
     * @param segmentPK
     * @throws EDITReinsuranceException
     */
    public void attachTreatyToSegment(long treatyPK, long segmentPK) throws EDITReinsuranceException;

    /**
     * Detaches the specified Treaty to the specified Segment
     * @param treatyPK
     * @param segentPK
     */
    public void detachTreatyFromSegment(long treatyPK, long segentPK) throws EDITReinsuranceException;

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ReinsuranceHistoryVO[] findReinsuranceHistoryBy_ReinsurerPK(long reinsurerPK);

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public ReinsurerVO findReinsurerBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK);

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public ReinsuranceHistoryVO findReinsuranceHistoryBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK);

    /**
     * Deletes the specified Treaty.
     * @param treatyPK
     */
    public void deleteTreaty(long treatyPK) throws EDITReinsuranceException;

    /**
     * Finder.
     * @return
     */
    public ReinsurerVO[] findAllReinsurers();
}
