/*
 * User: gfrosti
 * Date: Dec 31, 2004
 * Time: 1:14:11 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class ContributingProfile extends HibernateEntity
{
//    private CRUDEntityImpl crudEntityImpl;
//    private ContributingProfileVO contributingProfileVO;
    private Long contributingProfilePK;
    private BonusProgram bonusProgram;
    private CommissionProfile commissionProfile;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


//    /**
//     * Instantiates a ContributingProfile entity with a default ContributingProfileVO.
//     */
//    public ContributingProfile()
//    {
//        init();
//    }
//
//    /**
//     * Instantiates a ContributingProfile entity with a supplied ContributingProfileVO.
//     *
//     * @param contributingProfileVO
//     */
//    public ContributingProfile(ContributingProfileVO contributingProfileVO)
//    {
//        init();
//
//        this.contributingProfileVO = contributingProfileVO;
//    }
//
//    /**
//     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
//     */
//    private final void init()
//    {
//        if (contributingProfileVO == null)
//        {
//            contributingProfileVO = new ContributingProfileVO();
//        }
//
//        if (crudEntityImpl == null)
//        {
//            crudEntityImpl = new CRUDEntityImpl();
//        }
//    }
//
//    /**
//     * @see edit.services.db.CRUDEntity#save()
//     */
//    public void save() throws EDITAgentException
//    {
//        checkForDuplicates();
//
//        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
//    }
//
//    private void checkForDuplicates() throws EDITAgentException
//    {
//        long commissionProfileFK = contributingProfileVO.getCommissionProfileFK();
//
//        long bonusProgramPK = contributingProfileVO.getBonusProgramFK();
//
//        ContributingProfile contributingProfile = ContributingProfile.findBy_CommissionProfilePK_BonusProgramPK(commissionProfileFK, bonusProgramPK);
//
//        if (contributingProfile != null)
//        {
//            throw new EDITAgentException("A CommissionProfile Can Not Participate In A Bonus Program More Than Once - Duplicates Not Allowed");
//        }
//    }
//
//    /**
//     * @see edit.services.db.CRUDEntity#delete()
//     */
//    public void delete()
//    {
//        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
//    }
//
//    /**
//     * @return
//     * @see edit.services.db.CRUDEntity#getVO()
//     */
//    public VOObject getVO()
//    {
//        return contributingProfileVO;
//    }
//
//    /**
//     * @return
//     * @see edit.services.db.CRUDEntity#getPK()
//     */
//    public long getPK()
//    {
//        return contributingProfileVO.getContributingProfilePK();
//    }
//
//    /**
//     * @param voObject
//     */
//    public void setVO(VOObject voObject)
//    {
//        this.contributingProfileVO = (ContributingProfileVO) voObject;
//    }
//
//    /**
//     * @return
//     * @see edit.services.db.CRUDEntity#isNew()
//     */
//    public boolean isNew()
//    {
//        return crudEntityImpl.isNew(this);
//    }
//
//    /**
//     * @return
//     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
//     */
//    public CRUDEntity cloneCRUDEntity()
//    {
//        return crudEntityImpl.cloneCRUDEntity(this);
//    }
//
//    /**
//     * Finder.
//     *
//     * @param contributingProfilePK
//     */
//    public static final ContributingProfile findByPK(long contributingProfilePK)
//    {
//        ContributingProfile contributingProfile = null;
//
//        ContributingProfileVO[] contributingProfileVOs = new ContributingProfileDAO().findByPK(contributingProfilePK);
//
//        if (contributingProfileVOs != null)
//        {
//            contributingProfile = new ContributingProfile(contributingProfileVOs[0]);
//        }
//
//        return contributingProfile;
//    }
//
    /**
     * Finder.
     * @param commissionProfilePK
     * @param bonusProgramPK
     * @return
     */
    public static ContributingProfile findBy_CommissionProfilePK_BonusProgramPK(Long commissionProfilePK, Long bonusProgramPK)
    {
        ContributingProfile contributingProfile = null;

        CommissionProfile commissionProfile = CommissionProfile.findBy_PK(commissionProfilePK);

        BonusProgram bonusProgram = BonusProgram.findByPK(bonusProgramPK);

        String hql = "select contributingProfile from ContributingProfile contributingProfile " +
                     "where contributingProfile.CommissionProfile = :commissionProfile " +
                     "and contributingProfile.BonusProgram = :bonusProgram";

        Map params = new HashMap();

        params.put("commissionProfile", commissionProfile);

        params.put("bonusProgram", bonusProgram);

        List results = SessionHelper.executeHQL(hql, params, ContributingProfile.DATABASE);

        if (!results.isEmpty())
        {
            contributingProfile = (ContributingProfile) results.get(0);
        }

        return contributingProfile;
    }
//
//    /**
//     * Associates the specified CommissionProfile with this ContributingProfile.
//     * @param commissionProfile
//     */
//    public void associate(CommissionProfile commissionProfile)
//    {
//        this.contributingProfileVO.setCommissionProfileFK(commissionProfile.getPK());
//    }
//
//    /**
//     * Associates the specified BonusProgram with this ContributingProfile.
//     * @param bonusProgram
//     */
//    public void associate(BonusProgram bonusProgram)
//    {
//        this.contributingProfileVO.setBonusProgramFK(bonusProgram.getBonusProgramPK().longValue());
//    }
//
//    /**
//     * Finder.
//     * @param bonusProgramPK
//     * @return
//     */
//    public static final ContributingProfile[] findBy_BonusProgramPK(long bonusProgramPK)
//    {
//        ContributingProfile[] contributingProfiles = null;
//
//        ContributingProfileVO[] contributingProfileVOs = new ContributingProfileDAO().findBy_BonusProgramPK(bonusProgramPK);
//
//        if (contributingProfileVOs != null)
//        {
//            contributingProfiles = (ContributingProfile[]) CRUDEntityImpl.mapVOToEntity(contributingProfileVOs, ContributingProfile.class);
//        }
//
//        return contributingProfiles;
//    }

    /**
     * Setter.
     * @param contributingProfilePK
     */
    public void setContributingProfilePK(Long contributingProfilePK)
    {
        this.contributingProfilePK = contributingProfilePK;
    }

    /**
     * Setter.
     * @param bonusProgram
     */
    public void setBonusProgram(BonusProgram bonusProgram)
    {
        this.bonusProgram = bonusProgram;
    }

    /**
     * Setter.
     * @param commissionProfile
     */
    public void setCommissionProfile(CommissionProfile commissionProfile)
    {
        this.commissionProfile = commissionProfile;
    }

    /**
     * Getter.
     * @return
     */
    public Long getContributingProfilePK()
    {
        return contributingProfilePK;
    }

    /**
     * Getter.
     * @return
     */
    public BonusProgram getBonusProgram()
    {
        return bonusProgram;
    }

    /**
     * Getter.
     * @return
     */
    public CommissionProfile getCommissionProfile()
    {
        return commissionProfile;
    }

    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContributingProfile.DATABASE);
    }

    /**
     * @see interface#hDelete()
     */
    public void hDelete()
    {
        BonusProgram bonusProgram = getBonusProgram();

        bonusProgram.getContributingProfiles().remove(this);

        setBonusProgram(null);

        SessionHelper.saveOrUpdate(bonusProgram, ContributingProfile.DATABASE);

        CommissionProfile commissionProfile = getCommissionProfile();

        commissionProfile.getContributingProfiles().remove(this);

        setCommissionProfile(null);

        SessionHelper.saveOrUpdate(commissionProfile, ContributingProfile.DATABASE);

        SessionHelper.delete(this, ContributingProfile.DATABASE);
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContributingProfile.DATABASE;
    }
}
