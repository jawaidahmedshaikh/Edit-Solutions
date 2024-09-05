/*
 * User: gfrosti
 * Date: Nov 17, 2004
 * Time: 10:42:13 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package reinsurance.component;

import reinsurance.business.*;
import reinsurance.*;
import reinsurance.dm.dao.ReinsurerDAO;
import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.services.db.*;
import contract.*;
import edit.services.db.hibernate.SessionHelper;
import group.ContractGroup;

public class ReinsuranceComponent implements Reinsurance
{
    /**
     * @see edit.services.component.ILockableElement#lockElement(long, String)
     * @param reinsurancePK
     * @param username
     * @return
     * @throws LockException
     */
    public ElementLockVO lockElement(long reinsurancePK, String username) throws EDITLockException
    {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.lockElement(reinsurancePK, username);
    }

    /**
     * @see edit.services.component.ILockableElement#unlockElement(long)
     * @param lockTablePK
     * @return
     */
    public int unlockElement(long lockTablePK)
    {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.unlockElement(lockTablePK);
    }

    /**
     * @see reinsurance.business.Reinsurance#findAllTreatyGroups()
     * @return
     */
    public TreatyGroupVO[] findAllTreatyGroups()
    {
        return (TreatyGroupVO[]) CRUDEntityImpl.mapEntityToVO(TreatyGroup.findAllTreatyGroups(), TreatyGroupVO.class);
    }

    /**
     * @see Reinsurance#findTreatiesBy_ProductStructurePK(long)
     * @param productStructurePK
     * @return
     */
    public TreatyVO[] findTreatiesBy_ProductStructurePK(long productStructurePK)
    {
        return (TreatyVO[]) CRUDEntityImpl.mapEntityToVO(Treaty.findBy_ProductStructurePK(productStructurePK), TreatyVO.class);
    }

    /**
     * @see Reinsurance#saveTreatyGroup(edit.common.vo.TreatyGroupVO)
     * @param treatyGroupVO
     * @throws EDITReinsuranceException
     */
    public void saveTreatyGroup(TreatyGroupVO treatyGroupVO) throws EDITReinsuranceException
    {
        TreatyGroup treatyGroup = new TreatyGroup(treatyGroupVO);

        treatyGroup.save();
    }

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public TreatyGroupVO findTreatyGroupBy_TreatyGroupPK(long treatyGroupPK)
    {
        return (TreatyGroupVO) new TreatyGroup(treatyGroupPK).getVO();
    }

    /**
     * @see Reinsurance#deleteTreatyGroup(long)
     * @param treatyGroupPK
     * @throws EDITReinsuranceException
     */
    public void deleteTreatyGroup(long treatyGroupPK) throws EDITReinsuranceException
    {
        TreatyGroup treatyGroup = new TreatyGroup(treatyGroupPK);

        treatyGroup.delete();
    }

    /**
     * @see Reinsurance#saveReinsurer(edit.common.vo.ReinsurerVO)
     * @param reinsurerVO
     * @return
     */
    public ReinsurerVO saveReinsurer(ReinsurerVO reinsurerVO) throws EDITReinsuranceException
    {
        Reinsurer reinsurer = new Reinsurer(reinsurerVO);

        reinsurer.save();

        return (ReinsurerVO) reinsurer.getVO();
    }

    /**
     * @see Reinsurance#saveTreaty(edit.common.vo.TreatyVO)
     * @param treatyVO
     * @return
     */
    public TreatyVO saveTreaty(TreatyVO treatyVO)
    {
        Treaty treaty = new Treaty(treatyVO);

        treaty.save();

        return (TreatyVO) treaty.getVO();
    }

    /**
     * @see Reinsurance#findTreatiesBy_ReinsurerNumber(String)
     * @param reinsurerNumber
     * @return
     */
    public TreatyVO[] findTreatiesBy_ReinsurerNumber(String reinsurerNumber)
    {
        Treaty[] treaties = Treaty.findBy_ReinsurerNumber(reinsurerNumber);

        return (TreatyVO[]) CRUDEntityImpl.mapEntityToVO(treaties, TreatyVO.class);
    }

    /**
     * @see Reinsurance#findReinsurersBy_PartialCorporateName(String)
     * @param partialCorporateName
     * @return
     */
    public ReinsurerVO[] findReinsurersBy_PartialCorporateName(String partialCorporateName)
    {
        Reinsurer[] reinsurers = Reinsurer.findBy_PartialCorporateName(partialCorporateName);

        return (ReinsurerVO[]) CRUDEntityImpl.mapEntityToVO(reinsurers, ReinsurerVO.class);
    }

    /**
     * @see Reinsurance#findReinsurerBy_ReinsurerNumber(String)
     * @param reinsurerNumber
     * @return
     */
    public ReinsurerVO findReinsurerBy_ReinsurerNumber(String reinsurerNumber)
    {
        ReinsurerVO reinsurerVO = null;

        Reinsurer reinsurer = Reinsurer.findBy_ReinsurerNumber(reinsurerNumber);

        if (reinsurer != null)
        {
            reinsurerVO = (ReinsurerVO) reinsurer.getVO();
        }

        return reinsurerVO;
    }

    /**
     * @see Reinsurance#findReinsurerBy_TaxIdentication(String)
     * @param taxIdentification
     * @return
     */
    public ReinsurerVO findReinsurerBy_TaxIdentication(String taxIdentification)
    {
        ReinsurerVO reinsurerVO = null;

        Reinsurer reinsurer = Reinsurer.findBy_TaxIdentification(taxIdentification);

        if (reinsurer != null)
        {
            reinsurerVO = (ReinsurerVO) reinsurer.getVO();
        }

        return reinsurerVO;
    }

    /**
     * @see Reinsurance#findReinsurerBy_ReinsurerPK(long)
     * @param reinsurerPK
     * @return
     */
    public ReinsurerVO findReinsurerBy_ReinsurerPK(long reinsurerPK)
    {
        Reinsurer reinsurer = new Reinsurer(reinsurerPK);

        return (ReinsurerVO) reinsurer.getVO();
    }

    /**
     * @see Reinsurance#findTreatyGroupsBy_ProductStructurePK(long)
     * @param productStructurePK
     * @return
     */
    public TreatyGroupVO[] findTreatyGroupsBy_ProductStructurePK(long productStructurePK)
    {
        TreatyGroup[] treatyGroups = TreatyGroup.findBy_ProductStructurePK(productStructurePK);

        return (TreatyGroupVO[]) CRUDEntityImpl.mapEntityToVO(treatyGroups, TreatyGroupVO.class);
    }

    /**
     * @see Reinsurance#attachTreatyGroupToProductStructure(long, long)
     * @param treatyGroupPK
     * @param productStructurePK
     */
    public void attachTreatyGroupToProductStructure(long treatyGroupPK, long productStructurePK) throws EDITReinsuranceException
    {
        FilteredTreatyGroup filteredTreatyGroup = new FilteredTreatyGroup(treatyGroupPK, productStructurePK);

        filteredTreatyGroup.save();
    }

    /**
     * @see Reinsurance#detachTreatyGroupFromProductStructure(long, long)
     * @param treatyGroupPK
     * @param productStructurePK
     */
    public void detachTreatyGroupFromProductStructure(long treatyGroupPK, long productStructurePK) throws EDITReinsuranceException
    {
        FilteredTreatyGroup filteredTreatyGroup = FilteredTreatyGroup.findBy_ProductStructurePK_TreatyGroupPK(productStructurePK, treatyGroupPK);

        if (filteredTreatyGroup == null)
        {
            throw new EDITReinsuranceException("Association Does Not Exist For Specified Product Structure And Treaty Group");
        }

        filteredTreatyGroup.delete();
    }

    /**
     * @see Reinsurance#attachTreatyGroupToSegment(long,long)
     * @param treatyGroupPK
     * @param segmentPK
     */
    public void attachTreatyGroupToSegment(long treatyGroupPK, long segmentPK) throws EDITReinsuranceException
    {
        TreatyGroup treatyGroup = new TreatyGroup(treatyGroupPK);

        Segment segment = new Segment(segmentPK);

        treatyGroup.associateSegment(segment);
    }

    /**
     * @see Reinsurance#attachTreatyGroupToSegment(long,long)
     * @param treatyGroupPK
     * @param segmentPK
     */
    public void attachTreatyGroupToContractGroup(long treatyGroupPK, long contractGroupPK) throws EDITReinsuranceException
    {
        try
        {
            TreatyGroup treatyGroup = TreatyGroup.findBy_TreatyGroupPK(new Long(treatyGroupPK));

            ContractGroup contractGroup = ContractGroup.findByPK(new Long(contractGroupPK));

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            treatyGroup.associateContractGroup(contractGroup);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (EDITReinsuranceException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }


    }

    /**
     * Finder.
     * @see Reinsurance#findContractTreatyBy_CasePK_SegmentPK_TreatyPK(long, long, long)
     * @param casePK
     * @param segmentPK
     * @param treatyPK
     * @return
     */
//    public ContractTreatyVO findContractTreatyBy_CasePK_SegmentPK_TreatyPK(long casePK, long segmentPK, long treatyPK)
//    {
//        ContractTreatyVO contractTreatyVO = null;
//
//        ContractTreaty contractTreaty = ContractTreaty.findBy_CasePK_SegmentPK_TreatyPK(casePK, segmentPK, treatyPK);
//
//        if (contractTreaty != null)
//        {
//            contractTreatyVO = (ContractTreatyVO) contractTreaty.getVO();
//        }
//
//        return contractTreatyVO;
//    }

    public void detachTreatyGroupFromSegment(long treatyGroupPK, long segmentPK) throws EDITReinsuranceException
    {
        TreatyGroup treatyGroup = new TreatyGroup(treatyGroupPK);

        Segment segment = new Segment(segmentPK);

        treatyGroup.disassociateFromSegment(segment);
    }

    /**
     * Finder.
     * @see Reinsurance#findTreatiesBy_TreatyGroupPK(long)
     * @param treatyGroupPK
     * @return
     */
    public TreatyVO[] findTreatiesBy_TreatyGroupPK(long treatyGroupPK)
    {
        return (TreatyVO[]) CRUDEntityImpl.mapEntityToVO(Treaty.findBy_TreatyGroupPK(treatyGroupPK), TreatyVO.class);
    }

    /**
     * @see Reinsurance#saveContractTreatyOverrides(edit.common.vo.ContractTreatyVO)
     * @param contractTreatyVO
     * @return
     */
    public ContractTreatyVO saveContractTreatyOverrides(ContractTreatyVO contractTreatyVO) throws EDITReinsuranceException
    {
        ContractTreaty contractTreaty = new ContractTreaty(contractTreatyVO);

        contractTreaty.save();

        return (ContractTreatyVO) contractTreaty.getVO();
    }

    /**
     * @see ReinsuranceProcess#updateReinsuranceBalances(String, String)
     * @param productName
     * @param operator
     */
    public void updateReinsuranceBalances(String companyName, String operator)
    {
        new ReinsuranceProcess().updateReinsuranceBalances(companyName, operator);
    }

    /**
     * @see ReinsuranceProcess#setupReinsuranceChecks(String, String)
     * @param companyName
     * @param operator
     */
    public void createReinsuranceCheckTransactions(String companyName, String operator)
    {
        new ReinsuranceProcess().setupReinsuranceChecks(companyName, operator);
    }

    /**
     * @see Reinsurance#findTreatyGroupBy_TreatyGroupPK_SegmentPK(long, long)
     * @param treatyGroupPK
     * @param segmentPK
     * @return
     */
    public TreatyGroupVO findTreatyGroupBy_TreatyGroupPK_SegmentPK(long treatyGroupPK, long segmentPK)
    {
        TreatyGroupVO treatyGroupVO = null;

        TreatyGroup treatyGroup = TreatyGroup.findTreatyGroupBy_TreatyGroupPK_SegmentPK(treatyGroupPK, segmentPK);

        if (treatyGroup != null)
        {
            treatyGroupVO = (TreatyGroupVO) treatyGroup.getVO();
        }

        return treatyGroupVO;
    }

    /**
     * @see Reinsurance#findContractTreatyBy_SegmentPK_TreatyPK(long, long)
     * @param segmentPK
     * @param treatyPK
     * @return
     */
    public ContractTreatyVO findContractTreatyBy_SegmentPK_TreatyPK(long segmentPK, long treatyPK)
    {
        ContractTreatyVO contractTreatyVO = null;

        ContractTreaty contractTreaty = ContractTreaty.findBy_SegmentPK_TreatyPK(segmentPK, treatyPK);

        if (contractTreaty != null)
        {
            contractTreatyVO = (ContractTreatyVO) contractTreaty.getVO();
        }

        return contractTreatyVO;
    }

    /**
     * @see Reinsurance#attachTreatyToSegment(long, long)
     * @param treatyPK
     * @param segmentPK
     * @throws EDITReinsuranceException
     */
    public void attachTreatyToSegment(long treatyPK, long segmentPK) throws EDITReinsuranceException
    {
        ContractTreaty contractTreaty = new ContractTreaty();

        Segment segment = new Segment(segmentPK);

        Treaty treaty = new Treaty(treatyPK);

        contractTreaty.associateSegment(segment);

        contractTreaty.associateTreaty(treaty);

        contractTreaty.save();
    }

    /**
     * @see Reinsurance#detachTreatyFromSegment(long, long)
     * @param treatyPK
     * @param segentPK
     */
    public void detachTreatyFromSegment(long treatyPK, long segentPK) throws EDITReinsuranceException
    {
        ContractTreaty contractTreaty = ContractTreaty.findBy_SegmentPK_TreatyPK(segentPK, treatyPK);

        contractTreaty.delete();
    }

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ReinsuranceHistoryVO[] findReinsuranceHistoryBy_ReinsurerPK(long reinsurerPK)
    {
        return (ReinsuranceHistoryVO[]) CRUDEntityImpl.mapEntityToVO(ReinsuranceHistory.findReinsuranceHistoryBy_ReinsurerPK(reinsurerPK), ReinsuranceHistoryVO.class);
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public ReinsurerVO findReinsurerBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        ReinsurerVO reinsurerVO = null;

        Reinsurer reinsurer = Reinsurer.findBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        if (reinsurer != null)
        {
            reinsurerVO = (ReinsurerVO) reinsurer.getVO();
        }

        return reinsurerVO;
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public ReinsuranceHistoryVO findReinsuranceHistoryBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        ReinsuranceHistoryVO reinsuranceHistoryVO = null;

        ReinsuranceHistory reinsuranceHistory = new ReinsuranceHistory(reinsuranceHistoryPK);

        reinsuranceHistoryVO = (ReinsuranceHistoryVO) reinsuranceHistory.getVO();

        return reinsuranceHistoryVO;
    }

    /**
     * @see Reinsurance#deleteTreaty(long)
     * @param treatyPK
     * @throws EDITReinsuranceException
     */
    public void deleteTreaty(long treatyPK) throws EDITReinsuranceException
    {
        Treaty treaty = new Treaty(treatyPK);

        treaty.delete();
    }

    /**
     * @see reinsurance.business.Reinsurance#findAllResinsurers() 
     * @return
     */
    public ReinsurerVO[] findAllReinsurers()
    {
        return new ReinsurerDAO().findAllReinsurers();
    }
}
