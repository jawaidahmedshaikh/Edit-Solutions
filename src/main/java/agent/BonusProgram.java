/*
 * User: gfrosti
 * Date: Jan 4, 2005
 * Time: 1:26:51 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import contract.*;

import edit.common.*;

import edit.common.exceptions.*;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.services.EditServiceLocator;

import java.util.*;

import batch.business.Batch;
import engine.ProductStructure;


public class BonusProgram extends HibernateEntity
{
    public static final String SPECIFICPARTICIPANTSTATUS_ALL = "All";
    public static final String SPECIFICPARTICIPANTSTATUS_SELECT = "Select";
    public static final String SPECIFICHIERARCHYLEVELSTATUS_ALL = "All";
    public static final String SPECIFICHIERARCHYLEVELSTATUS_DIRECT = "Direct";
    public static final String SPECIFICCOMMSSIONPROFILESTATUS_ALL = "All";
    public static final String SPECIFICCOMMSSIONPROFILESTATUS_SELECT = "Select";
    public static final String SPECIFICPRODUCTSTATUS_ALL = "All";
    public static final String INCLUDEADDITIONALPREMIUMIND_YES = "Y";
    public static final String INCLUDEADDITIONALPREMIUMIND_NO = "N";

    public static final String BONUSFREQUENCY_ANNUAL = "Annual";
    public static final String BONUSFREQUENCY_SEMIANNUAL = "SemiAnnual";
    public static final String BONUSFREQUENCY_QUARTERLY = "Quarterly";
    public static final String BONUSFREQUENCY_MONTHLY = "Monthly";
//    public static final String BONUSFREQUENCY_WEEKLY = "Weekly";
    public static final String BONUSFREQUENCY_ONETIME = "OneTime";

    private Long bonusProgramPK;
    private String contractCodeCT;
    private String commissionLevelCT;
    private String bonusName;
    private String produceCheckInd;
    private String frequencyCT;
    private EDITDate bonusStartDate;
    private EDITDate bonusStopDate;
    private EDITDate applicationReceivedStopDate;
    private EDITDate premiumStopDate;
    private String includeAdditionalPremiumInd;
    private String specificParticipantStatus;
    private String specificProductStatus;
    private EDITBigDecimal productLevelIncreasePercent;
    private String specificCommissionProfStatus;
    private String specificHierarchyLevelStatus;
    private EDITDate applicationReceivedStartDate;
    private EDITDate nextCheckDate;
    private String baseProgramCompleteInd;
    private Set premiumLevels;
    private Set participatingAgents;
    private Set contributingProfiles;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public BonusProgram()
    {
        // Default Stop Dates to Max Dates.
        bonusStopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        premiumStopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        applicationReceivedStopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }

    /**
     * Setter.
     *
     * @param bonusProgramPK
     */
    public void setBonusProgramPK(Long bonusProgramPK)
    {
        this.bonusProgramPK = bonusProgramPK;
    }

    /**
     * Setter.
     *
     * @param contractCodeCT
     */
    public void setContractCodeCT(String contractCodeCT)
    {
        this.contractCodeCT = contractCodeCT;
    }

    /**
     * Setter.
     *
     * @param commissionLevelCT
     */
    public void setCommissionLevelCT(String commissionLevelCT)
    {
        this.commissionLevelCT = commissionLevelCT;
    }

    /**
     * Setter.
     *
     * @param bonusName
     */
    public void setBonusName(String bonusName)
    {
        this.bonusName = bonusName;
    }

    /**
     * Setter.
     *
     * @param produceCheckInd
     */
    public void setProduceCheckInd(String produceCheckInd)
    {
        this.produceCheckInd = produceCheckInd;
    }

    /**
     * Setter.
     *
     * @param frequencyCT
     */
    public void setFrequencyCT(String frequencyCT)
    {
        this.frequencyCT = frequencyCT;
    }

    /**
     * Setter.
     *
     * @param bonusStartDate
     */
    public void setBonusStartDate(EDITDate bonusStartDate)
    {
        this.bonusStartDate = bonusStartDate;
    }

    /**
     * Setter.
     *
     * @param bonusStopDate
     */
    public void setBonusStopDate(EDITDate bonusStopDate)
    {
        this.bonusStopDate = bonusStopDate;
    }

    /**
     * Setter.
     *
     * @param applicationReceivedStopDate
     */
    public void setApplicationReceivedStopDate(EDITDate applicationReceivedStopDate)
    {
        this.applicationReceivedStopDate = applicationReceivedStopDate;
    }

    /**
     * Setter.
     *
     * @param premiumStopDate
     */
    public void setPremiumStopDate(EDITDate premiumStopDate)
    {
        this.premiumStopDate = premiumStopDate;
    }

    /**
     * Setter.
     *
     * @param includeAdditionalPremiumInd
     */
    public void setIncludeAdditionalPremiumInd(String includeAdditionalPremiumInd)
    {
        this.includeAdditionalPremiumInd = includeAdditionalPremiumInd;
    }

    /**
     * Setter.
     *
     * @param specificParticipantStatus
     */
    public void setSpecificParticipantStatus(String specificParticipantStatus)
    {
        this.specificParticipantStatus = specificParticipantStatus;
    }

    /**
     * Setter.
     *
     * @param specificProductStatus
     */
    public void setSpecificProductStatus(String specificProductStatus)
    {
        this.specificProductStatus = specificProductStatus;
    }

    /**
     * Setter.
     *
     * @param productLevelIncreasePercent
     */
    public void setProductLevelIncreasePercent(EDITBigDecimal productLevelIncreasePercent)
    {
        this.productLevelIncreasePercent = productLevelIncreasePercent;
    }

    /**
     * Setter.
     *
     * @param specificCommissionProfStatus
     */
    public void setSpecificCommissionProfStatus(String specificCommissionProfStatus)
    {
        this.specificCommissionProfStatus = specificCommissionProfStatus;
    }

    /**
     * Setter.
     *
     * @param specificHierarchyLevelStatus
     */
    public void setSpecificHierarchyLevelStatus(String specificHierarchyLevelStatus)
    {
        this.specificHierarchyLevelStatus = specificHierarchyLevelStatus;
    }

    /**
     * Setter.
     *
     * @param applicationReceivedStartDate
     */
    public void setApplicationReceivedStartDate(EDITDate applicationReceivedStartDate)
    {
        this.applicationReceivedStartDate = applicationReceivedStartDate;
    }

    /**
     * Setter.
     *
     * @param nextCheckDate
     */
    public void setNextCheckDate(EDITDate nextCheckDate)
    {
        this.nextCheckDate = nextCheckDate;
    }

    /**
     * Setter.
     *
     * @param baseProgramCompleteInd
     */
    public void setBaseProgramCompleteInd(String baseProgramCompleteInd)
    {
        this.baseProgramCompleteInd = baseProgramCompleteInd;
    }

    /**
     * Setter.
     *
     * @param premiumLevels
     */
    public void setPremiumLevels(Set premiumLevels)
    {
        this.premiumLevels = premiumLevels;
    }

    /**
     * Setter;
     *
     * @param participatingAgents
     */
    public void setParticipatingAgents(Set participatingAgents)
    {
        this.participatingAgents = participatingAgents;
    }

    /**
     * Setter.
     *
     * @param contributingProfiles
     */
    public void setContributingProfiles(Set contributingProfiles)
    {
        this.contributingProfiles = contributingProfiles;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getBonusProgramPK()
    {
        return bonusProgramPK;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getContractCodeCT()
    {
        return contractCodeCT;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getCommissionLevelCT()
    {
        return commissionLevelCT;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getBonusName()
    {
        return bonusName;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getProduceCheckInd()
    {
        return produceCheckInd;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getFrequencyCT()
    {
        return frequencyCT;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getBonusStartDate()
    {
        return bonusStartDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getBonusStopDate()
    {
        return bonusStopDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getApplicationReceivedStopDate()
    {
        return applicationReceivedStopDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getPremiumStopDate()
    {
        return premiumStopDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getIncludeAdditionalPremiumInd()
    {
        return includeAdditionalPremiumInd;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getSpecificParticipantStatus()
    {
        return specificParticipantStatus;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getSpecificProductStatus()
    {
        return specificProductStatus;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getProductLevelIncreasePercent()
    {
        return productLevelIncreasePercent;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getSpecificCommissionProfStatus()
    {
        return specificCommissionProfStatus;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getSpecificHierarchyLevelStatus()
    {
        return specificHierarchyLevelStatus;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getApplicationReceivedStartDate()
    {
        return applicationReceivedStartDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getNextCheckDate()
    {
        return nextCheckDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getBaseProgramCompleteInd()
    {
        return baseProgramCompleteInd;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Set getPremiumLevels()
    {
        return premiumLevels;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Set getParticipatingAgents()
    {
        return participatingAgents;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Set getContributingProfiles()
    {
        return contributingProfiles;
    }

    /**
     * Adder.
     *
     * @param premiumLevel
     */
    public void addPremiumLevel(PremiumLevel premiumLevel)
    {
        getPremiumLevels().add(premiumLevel);

        premiumLevel.setBonusProgram(this);

        SessionHelper.saveOrUpdate(this, BonusProgram.DATABASE);
    }

    /**
     * Adder.
     *
     * @param participatingAgent
     */
    public void addParticipatingAgent(ParticipatingAgent participatingAgent)
    {
        getParticipatingAgents().add(participatingAgent);

        participatingAgent.setBonusProgram(this);

        SessionHelper.saveOrUpdate(this, BonusProgram.DATABASE);
    }

//    /**
//     * Instantiates a BonusProgram entity with a default BonusProgramVO.
//     */
//    public BonusProgram()
//    {
//        init();
//    }
//
//    /**
//     * Instantiates a BonusProgram entity with a supplied BonusProgramVO.
//     *
//     * @param bonusProgramVO
//     */
//    public BonusProgram(BonusProgramVO bonusProgramVO)
//    {
//        init();
//
//        this.bonusProgramVO = bonusProgramVO;
//    }
//
//    /**
//     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
//     */
//    private final void init()
//    {
//        if (bonusProgramVO == null)
//        {
//            bonusProgramVO = new BonusProgramVO();
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
//        checkForRequiredValues();
//
//        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
//
//        buildParticipatingAgents();
//
//        buildContributingProducts();
//
//        buildContributingProfiles();
//    }
//
//    /**
//     * Builds the set of ContributingProfiles if the SpecificCommissionProfStatus = 'All.' Duplicate entries for a
//     * BonusProgram, ProductStructure are not allowed.
//     * @throws EDITAgentException
//     */
//    private void buildContributingProfiles() throws EDITAgentException
//    {
//        if (getSpecificCommissionProfStatus().equals(BonusProgram.SPECIFICCOMMSSIONPROFILESTATUS_ALL))
//        {
//            CommissionProfile[] commissionProfiles = CommissionProfile.findAll();
//
//            if (commissionProfiles != null)
//            {
//                for (int i = 0; i < commissionProfiles.length; i++)
//                {
//                    CommissionProfile commissionProfile = commissionProfiles[i];
//
//                    ContributingProfile contributingProfile = ContributingProfile.findBy_CommissionProfilePK_BonusProgramPK(commissionProfile.getPK(), getPK());
//
//                    if (contributingProfile == null)
//                    {
//                        contributingProfile = new ContributingProfile();
//
//                        contributingProfile.associate(commissionProfile);
//
//                        contributingProfile.associate(this);
//
//                        contributingProfile.save();
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Builds the set of ContributingProduct(s) if the SpecificProductStatus is set to 'All'. Duplicate entries for
//     * the ProductStructure and BonusProgram are not allowed.
//     */
//    private void buildContributingProducts() throws EDITAgentException
//    {
//        if (getSpecificProductStatus().equals(BonusProgram.SPECIFICPRODUCTSTATUS_ALL))
//        {
//            ProductStructure[] companyStructures = ProductStructure.findAll();
//
//            if (companyStructures != null)
//            {
//                for (int i = 0; i < companyStructures.length; i++)
//                {
//                    ProductStructure companyStructure = companyStructures[i];
//
//                    ContributingProduct contributingProduct = ContributingProduct.findBy_ProductStructurePK_BonusProgramPK(companyStructure.getPK(), getPK());
//
//                    if (contributingProduct == null)
//                    {
//                        contributingProduct = new ContributingProduct();
//
//                        contributingProduct.associate(companyStructure);
//
//                        contributingProduct.associate(this);
//
//                        contributingProduct.save();
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getSpecificProductStatus()
//    {
//        return this.bonusProgramVO.getSpecificProductStatus();
//    }
//
//    /**
//     * The ContractCodeCT and CommissionLevelCT establishes a "key". Any PlacedAgent that shares the same ContractCodeCT
//     * and CommissionLevelCT will become a ParticipatingAgent in this BonusProgram. If the PlacedAgent is already
//     * participating, a new ParticipatingAgent will not be built. The SpecificParticipantStatus must be set to 'All'.
//     */
//    private void buildParticipatingAgents() throws EDITAgentException
//    {
//        String specificParticipantStatus = getSpecificParticipantStatus();
//
//        if (specificParticipantStatus.equals(BonusProgram.SPECIFICPARTICIPANTSTATUS_ALL))
//        {
//            String contractCodeCT = getContractCodeCT();
//
//            String commissionLevelCT = getCommissionLevelCT();
//
//            PlacedAgent[] placedAgents = PlacedAgent.findBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(contractCodeCT, commissionLevelCT, getBonusStartDate().getDateAsYYYYMMDD(), getBonusStopDate().getDateAsYYYYMMDD());
//
//            if (placedAgents != null)
//            {
//                ParticipatingAgent participatingAgent = null;
//
//                for (int i = 0; i < placedAgents.length; i++)
//                {
//                    try
//                    {
//                        if (!placedAgents[i].isParticipating(this))
//                        {
//                            participatingAgent = new ParticipatingAgent();
//
//                            PlacedAgent placedAgent = placedAgents[i];
//
//                            participatingAgent.setBonusProgram(this);
//
//                            participatingAgent.setPlacedAgent(placedAgent);
//
//                            participatingAgent.save();
//                        }
//                    }
//                    catch (Exception e)
//                    {
//                        System.out.println(e);
//
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Checks for required values.
//     * @throws EDITAgentException
//     */
//    private void checkForRequiredValues() throws EDITAgentException
//    {
//        String contractCodeCT = getContractCodeCT();
//        String commissionLevelCT = getCommissionLevelCT();
//        String bonusBame = getBonusName();
//        EDITDate bonusStartDate = getBonusStartDate();
//        EDITBigDecimal bonusAmount = getBonusAmount();
//        EDITBigDecimal bonusBasisPoint = getBonusBasisPoint();
//        String produceCheckInd = getProduceCheckInd();
//        String frequencyCT = getFrequencyCT();
//
//        try
//        {
//            Assert.assertNotNull("Contract Code Is A Required Value", contractCodeCT);
//            Assert.assertNotNull("Commission Code Is A Required Value", commissionLevelCT);
//            Assert.assertNotNull("Bonus Name Is A Required Value", bonusBame);
//            Assert.assertNotNull("Bonus Start Date Is A Required Value", bonusStartDate);
//            Assert.assertNotNull("Produce Check Is A Required Value", produceCheckInd);
//            Assert.assertNotNull("Mode Is A Required Value", frequencyCT);
//
//            // Both can't be null.
//            if ((bonusAmount == null) && (bonusBasisPoint == null))
//            {
//                Assert.assertNotNull("Bonus Amount And Bonus Basis Point Can't Both Be Null", null);
//            }
//
//            // If one is null, the other has to be > 0.00
//            else if ((bonusAmount != null) && (bonusBasisPoint == null))
//            {
//                Assert.assertTrue("Bonus Amount Must Be > 0.00 If Bonus Basis Point Is Null", bonusAmount.doubleValue() > 0.00);
//            }
//
//            // If one is null, the other has to be > 0.00
//            else if ((bonusAmount == null) && (bonusBasisPoint != null))
//            {
//                Assert.assertTrue("Bonus Basis Point Must Be > 0.00 If Bonus Amount Is Null", bonusBasisPoint.doubleValue() > 0.00);
//            }
//
//            // If both are not null, at least one has to be > 0.00.
//            else
//            {
//                Assert.assertTrue("Bonus Amount And Bonus Basis Point Can't Be Be 0.00", (bonusAmount.doubleValue() + bonusBasisPoint.doubleValue()) > 0.00);
//            }
//
//            // Make sure stop dates are set.
//            bonusProgramVO.setBonusStopDate(getBonusStopDate().toStringYYYYMMDD());
//
//            bonusProgramVO.setPremiumStopDate(getPremiumStopDate().toStringYYYYMMDD());
//
//            bonusProgramVO.setApplicationReceivedStopDate(getApplicationReceivedStopDate().toStringYYYYMMDD());
//        }
//        catch (AssertionFailedError e)
//        {
//            String errorMessage = e.getMessage();
//
//            throw new EDITAgentException(errorMessage);
//        }
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getFrequencyCT()
//    {
//        return bonusProgramVO.getFrequencyCT();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITBigDecimal getBonusBasisPoint()
//    {
//        return new EDITBigDecimal(bonusProgramVO.getBonusBasisPoint());
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITBigDecimal getBonusAmount()
//    {
//        return new EDITBigDecimal(bonusProgramVO.getBonusAmount());
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITDate getBonusStartDate()
//    {
//        EDITDate editDate = null;
//
//        String bonusStartDate = bonusProgramVO.getBonusStartDate();
//
//        if (bonusStartDate != null)
//        {
//            editDate = new EDITDate(bonusStartDate);
//        }
//
//        return editDate;
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getBonusName()
//    {
//        return bonusProgramVO.getBonusName();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getCommissionLevelCT()
//    {
//        return bonusProgramVO.getCommissionLevelCT();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getContractCodeCT()
//    {
//        return bonusProgramVO.getContractCodeCT();
//    }
//
//    /**
//     * @throws EDITAgentException
//     * @see edit.services.db.CRUDEntity#delete()
//     */
//    public void delete() throws EDITAgentException
//    {
//        deleteChildren();
//
//        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
//    }
//
//    private void deleteChildren() throws EDITAgentException
//    {
//        ParticipatingAgent[] participatingAgents = ParticipatingAgent.findBy_BonusProgramPK(getPK());
//
//        if (participatingAgents != null)
//        {
//            for (int i = 0; i < participatingAgents.length; i++)
//            {
//                ParticipatingAgent participatingAgent = participatingAgents[i];
//
//                participatingAgent.delete();
//            }
//        }
//
//        ContributingProfile[] contributingProfiles = ContributingProfile.findBy_BonusProgramPK(getPK());
//
//        if (contributingProfiles != null)
//        {
//            for (int i = 0; i < contributingProfiles.length; i++)
//            {
//                ContributingProfile contributingProfile = contributingProfiles[i];
//
//                contributingProfile.delete();
//            }
//        }
//
//        ContributingProduct[] contributingProducts = ContributingProduct.findBy_BonusProgramPK(getPK());
//
//        if (contributingProducts != null)
//        {
//            for (int i = 0; i < contributingProducts.length; i++)
//            {
//                ContributingProduct contributingProduct = contributingProducts[i];
//
//                contributingProduct.delete();
//            }
//        }
//    }
//
//    /**
//     * @return
//     * @see edit.services.db.CRUDEntity#getVO()
//     */
//    public VOObject getVO()
//    {
//        return bonusProgramVO;
//    }
//
//    /**
//     * @return
//     * @see edit.services.db.CRUDEntity#getPK()
//     */
//    public long getPK()
//    {
//        return bonusProgramVO.getBonusProgramPK();
//    }
//
//    /**
//     * @param voObject
//     */
//    public void setVO(VOObject voObject)
//    {
//        this.bonusProgramVO = (BonusProgramVO) voObject;
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

    /**
     * Finder.
     *
     * @param bonusProgramPK
     */
    public static final BonusProgram findByPK(Long bonusProgramPK)
    {
        return (BonusProgram) SessionHelper.get(BonusProgram.class, bonusProgramPK, BonusProgram.DATABASE);
    }

    /**
     * Finder.
     *
     * @return
     */
    public static final BonusProgram[] findAll()
    {
        String hql = "from BonusProgram";

        List results = SessionHelper.executeHQL(hql, null, BonusProgram.DATABASE);

        return (BonusProgram[]) results.toArray(new BonusProgram[results.size()]);
    }

    /**
     * Returns the PKs of all the existing BonusProgram(s) instead of the BonusProgram(s) themselves.
     *
     * @return
     */
    public static Long[] findAllPKs()
    {
        String hql = "select bonusProgram.BonusProgramPK from BonusProgram bonusProgram";

        List results = SessionHelper.executeHQL(hql, null, BonusProgram.DATABASE);

        return (Long[]) results.toArray(new Long[results.size()]);
    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getProduceCheckInd()
//    {
//        return bonusProgramVO.getProduceCheckInd();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITBigDecimal getIssuePremiumLevel()
//    {
//        return new EDITBigDecimal(bonusProgramVO.getIssuePremiumLevel());
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getIncludeAdditionalPremiumInd()
//    {
//        return bonusProgramVO.getIncludeAdditionalPremiumInd();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITBigDecimal getExcessBonusAmount()
//    {
//        return new EDITBigDecimal(bonusProgramVO.getExcessBonusAmount());
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITBigDecimal getExcessBonusBasisPoint()
//    {
//        return new EDITBigDecimal(bonusProgramVO.getExcessBonusBasisPoint());
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITBigDecimal getProductLevelIncreasePercent()
//    {
//        return new EDITBigDecimal(bonusProgramVO.getProductLevelIncreasePercent());
//    }
//

    /**
     * Updates the bonus amounts for all ParticipatingAgents
     */
    public static void updateAgentBonus(Long bonusProgramPK) throws EDITAgentException
    {
        // There could be thousands of ParticipatingAgents. We use the PKs instead of actual
        // ParticipatingAgent objects to limit resource consumption.
        Long[] participatingAgentPKs = ParticipatingAgent.findParticipatingAgentPKsBy_BonusProgramPK(bonusProgramPK);

        for (int i = 0; i < participatingAgentPKs.length; i++)
        {
            Long participatingAgentPK = participatingAgentPKs[i];
            
            ParticipatingAgent participatingAgent = ParticipatingAgent.findByPK(participatingAgentPK);

            try
            {
                SessionHelper.beginTransaction(BonusProgram.DATABASE);

                participatingAgent.updateAgentBonus();
              
                participatingAgent.hSave();

                SessionHelper.commitTransaction(BonusProgram.DATABASE);
            }
             catch (Exception e)
            {
                SessionHelper.rollbackTransaction(BonusProgram.DATABASE);

                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            finally
            {
                // Keep the Session clean and mean.
                SessionHelper.clearSessions();
            }
        }
    }

    /**
     * The kick-off process to update the specified Agent Bonus programs.
     *
     * @param bonusNameCT
     * @throws EDITAgentException
     */
    public static final void updateAgentBonuses()
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_BONUSES).tagBatchStart(Batch.BATCH_JOB_UPDATE_AGENT_BONUSES, "Agent Bonus Update");

        Long[] bonusProgramPKs = null;

        try
        {
            bonusProgramPKs = BonusProgram.findAllPKs();

            for (int i = 0; i < bonusProgramPKs.length; i++)
            {
                Long bonusProgramPK = bonusProgramPKs[i];

                BonusProgram.updateAgentBonus(bonusProgramPK);

                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_BONUSES).updateSuccess();
            }
        }
        catch (EDITAgentException e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_BONUSES).updateFailure();

            System.out.println(e);

            e.printStackTrace(); // Don't rethrow

//            logErrorToDabase(e);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); // Don't rethrow
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_BONUSES).tagBatchStop();
        }
    }

    /**
     * Finder.
     *
     * @param bonusName
     * @return
     */
    public static final BonusProgram findBy_BonusName(String bonusName)
    {
        String hql = "select bp from BonusProgram bp " +
                "where bp.BonusName = :bonusName";

        Map params = new HashMap();

        params.put("bonusName", bonusName);

        List results = SessionHelper.executeHQL(hql, params, BonusProgram.DATABASE);

        BonusProgram bonusProgram = null;

        if (results.size() > 0)
        {
            bonusProgram = (BonusProgram) results.get(0);
        }

        return bonusProgram;
    }
//
//    /**
//     * Returns the set of ParticipatingAgents according to the following rules:
//     * 1. If BonusProgram.SpecificParticipantStatus = "All", then find all PlacedAgents that share the same ContractCodeCT and
//     * CommissionLevelCT as this BonusProgram.
//     * 2. If BonusProgram.SpecificParticipantStatus = "Select", then there are directly mapped ParticipatingAgent(s)
//     * @return
//     */
//    private ParticipatingAgent[] getParticipatingAgents()
//    {
//        ParticipatingAgent[] participatingAgents = ParticipatingAgent.findBy_BonusProgramPK(getPK());
//
//        if (participatingAgents != null)
//        {
//            for (int i = 0; i < participatingAgents.length; i++)
//            {
//                ParticipatingAgent participatingAgent = participatingAgents[i];
//
//                participatingAgent.setBonusProgram(this);
//            }
//        }
//        else
//        {
//            participatingAgents = new ParticipatingAgent[0];
//        }
//
//        return participatingAgents;
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    private String getSpecificParticipantStatus()
//    {
//        return this.bonusProgramVO.getSpecificParticipantStatus();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITDate getBonusStopDate()
//    {
//        EDITDate editDate = null;
//
//        String bonusStopDate = bonusProgramVO.getBonusStopDate();
//
//        if (bonusStopDate != null)
//        {
//            editDate = new EDITDate(bonusStopDate);
//        }
//        else
//        {
//            editDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
//        }
//
//        return editDate;
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public EDITDate getApplicationReceivedStopDate()
//    {
//        EDITDate editDate = null;
//
//        String applicationReceivedStopDate = bonusProgramVO.getApplicationReceivedStopDate();
//
//        if (applicationReceivedStopDate != null)
//        {
//            editDate = new EDITDate(applicationReceivedStopDate);
//        }
//        else
//        {
//            editDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
//        }
//
//        return editDate;
//    }
//
//    /**
//     * Getter
//     * @return
//     */
//    public EDITDate getPremiumStopDate()
//    {
//        EDITDate editDate = null;
//
//        String premiumStopDate = bonusProgramVO.getPremiumStopDate();
//
//        if (premiumStopDate != null)
//        {
//            editDate = new EDITDate(premiumStopDate);
//        }
//        else
//        {
//            editDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
//        }
//
//        return editDate;
//    }
//
//    /**
//     * Specific ProductStructures may have been mapped to this BonusProgram. Otherwise it is assumed that all ProductStructures
//     * may contribute to this BonusProgram.
//     * @return true if there are specific ProductStructures mapped to this BonusProgram.
//     */
//    public boolean hasSpecificContributingProducts()
//    {
//        boolean hasSpecificContributingProducts = false;
//
//        hasSpecificContributingProducts = (getContributingProducts() != null);
//
//        return hasSpecificContributingProducts;
//    }
//
//    /**
//     * The products that have been specifically mapped to this BonusProgram.
//     * @return
//     */
//    public ContributingProduct[] getContributingProducts()
//    {
//        if (contributingProducts == null)
//        {
//            contributingProducts = ContributingProduct.findBy_BonusProgramPK(getPK());
//
//            if (contributingProducts == null)
//            {
//                contributingProducts = new ContributingProduct[0];
//            }
//        }
//
//        return contributingProducts;
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getSpecificHierarchyLevelStatus()
//    {
//        return this.bonusProgramVO.getSpecificHierarchyLevelStatus();
//    }
//
//    /**
//     * Getter.
//     * @return
//     */
//    public String getSpecificCommissionProfStatus()
//    {
//        return this.bonusProgramVO.getSpecificCommissionProfStatus();
//    }
//
//

    /**
     * True if Premiums beyond the initial Premium should be counted toward the bonus.
     *
     * @return
     */
    public boolean includeAdditionalPremium()
    {
        boolean includeAdditionalPremium = false;

        String includeAdditionalPremiumInd = getIncludeAdditionalPremiumInd();

        includeAdditionalPremium = (includeAdditionalPremiumInd.equals(BonusProgram.INCLUDEADDITIONALPREMIUMIND_YES));

        return includeAdditionalPremium;
    }

    /**
     * An Agent, as placed within an AgentHierachy, can contribute to a ParticipatingAgent under the auspices of this
     * BonusProgram. There are two conditions to meet:
     * 1. The contributing Agent (via its AgentSnapshot) must be the lowest level Agent within the AgentHierarchy.
     * 2. The contributing Agent (via its PlacedAgent) must have a CommissionLevelCT within the set of allowable
     * 3. The AgentHierchy's associated Segment must have a ProductStructure within the set of allowable ProductStructure's
     * for this BonusProgram.
     * 4. The Lowest Level PlacedAgent can't be the same as the ParticipatingAgent's PlacedAgent.
     * CommissionLevelCTs of this BonusProgram.
     *
     * @param agentHierarchy
     * @param participatingAgent
     * @return
     */
    public AgentSnapshot getCandidateAgentSnapshot(AgentHierarchy agentHierarchy, ParticipatingAgent participatingAgent)
    {
        AgentSnapshot candidateAgentSnapshot = null;

        AgentSnapshot lowestLevelAgentSnapshot = agentHierarchy.getLowestLevelAgent();

        PlacedAgent lowestLevelPlacedAgent = lowestLevelAgentSnapshot.getPlacedAgent();

        if (validContributingCommissionProfile(lowestLevelPlacedAgent))
        {
            if (validContributingProduct(agentHierarchy))
            {
                if (agentsAreMutuallyExclusive(lowestLevelPlacedAgent, participatingAgent))
                {
                    if (getSpecificHierarchyLevelStatus().equals(BonusProgram.SPECIFICHIERARCHYLEVELSTATUS_DIRECT))
                    {
                        if (agentHierarchy.directlyReportsTo(lowestLevelPlacedAgent, participatingAgent.getPlacedAgent()))
                        {
                            candidateAgentSnapshot = lowestLevelAgentSnapshot;
                        }
                    }
                    else
                    {
                        candidateAgentSnapshot = lowestLevelAgentSnapshot;
                    }
                }
            }
        }

        return candidateAgentSnapshot;
    }

    /**
     * True if the lowestLevelPlacedAgent and the ParticipatingAgent don't share the same PlacedAgent.
     *
     * @param lowestLevelPlacedAgent
     * @param participatingAgent
     * @return
     */
    private boolean agentsAreMutuallyExclusive(PlacedAgent lowestLevelPlacedAgent, ParticipatingAgent participatingAgent)
    {
        return !lowestLevelPlacedAgent.equals(participatingAgent.getPlacedAgent());
    }

    /**
     * True if the specified AgentHiearchy's associated ProductStructure is in the
     * set of ContributingProducts for this BonusProgram.
     *
     * @param agentHierarchy
     * @return
     */
    private boolean validContributingProduct(AgentHierarchy agentHierarchy)
    {
        boolean validContributingProduct = false;

        Long productStructureFK = agentHierarchy.getSegment().getProductStructureFK();

        ProductStructure[] productStructures = getProductStructures();

        for (int i = 0; i < productStructures.length; i++)
        {
            Long currentProductStructurePK = productStructures[i].getProductStructurePK();

            if (productStructureFK.equals(currentProductStructurePK))
            {
                validContributingProduct = true;

                break;
            }
        }

        return validContributingProduct;
    }

    /**
     * True if the associated CommissionProfile of the specified PlacedAgent is within the set of represented
     * CommissionProfile(s) of this BonusProgram.
     *
     * @param placedAgent
     * @return
     */
    private boolean validContributingCommissionProfile(PlacedAgent placedAgent)
    {
        boolean validContributingProfile = false;

        Set contributingProfiles = getContributingProfiles();

        Iterator it = contributingProfiles.iterator();

        while (it.hasNext())
        {
            ContributingProfile contributingProfile = (ContributingProfile) it.next();

            CommissionProfile commissionProfile = contributingProfile.getCommissionProfile();

            Long commissionProfilePK = commissionProfile.getCommissionProfilePK();

            Long commissionProfileFK = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile().getCommissionProfilePK();

            if (commissionProfilePK.equals(commissionProfileFK))
            {
                validContributingProfile = true;

                break;
            }
        }

        return validContributingProfile;
    }

    /**
     * A convenience method for performance reasons. The ProductStructure PKs that are participating in this BonusProgram are found indirectly. It's a drill-down process
     * from BonusProgram to PremiumLevel to BonusCriteria to BonusContributingProduct. Business-wise, it is only necessary
     * to get the 1st PremiumLevel and its first BonusCriteria; Every BonusCriteria for this BonusProgram will have the
     * same set of associated ProductStructures.
     *
     * @return
     */
    public ProductStructure[] getProductStructures()
    {
        List productStructures = new ArrayList();

        Set premiumLevels = this.getPremiumLevels();

        // we need only the one since all premiumLevels/bonusCriteria are associated with same product structures.
        if (!premiumLevels.isEmpty())
        {
            PremiumLevel premiumLevel = (PremiumLevel) premiumLevels.iterator().next();

            Set bonusCriterias = premiumLevel.getBonusCriterias();

            Iterator iterator = bonusCriterias.iterator();

            // get all product structures of bonus criteria that are associated to.
            // ideally there should not be duplicate product structures becasue business-wise
            // it doesn't make sense to attach two bonus criteria for a premium level
            // to same product structure/product.
            while (iterator.hasNext())
            {
                BonusCriteria bonusCriteria = (BonusCriteria) iterator.next();

                Set bonusContributingProducts = bonusCriteria.getBonusContributingProducts();

                Iterator iterator2 = bonusContributingProducts.iterator();

                while (iterator2.hasNext())
                {
                    BonusContributingProduct contributingProduct = (BonusContributingProduct) iterator2.next();

                    ProductStructure productStructure = ProductStructure.findByPK(contributingProduct.getProductStructureFK());

                    productStructures.add(productStructure);
                }
            }
        }

        return (ProductStructure[]) productStructures.toArray(new ProductStructure[productStructures.size()]);
    }

    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        if (getSpecificCommissionProfStatus().equals(BonusProgram.SPECIFICCOMMSSIONPROFILESTATUS_ALL))
        {
            buildContributingProfiles();
        }

        if (getNextCheckDate() == null)
        {
            generateNextCheckDate(new EDITDate(EDITDate.DEFAULT_MIN_DATE)); // We always want to attempt the nextCheckDate generation.
        }


        SessionHelper.saveOrUpdate(this, BonusProgram.DATABASE);
    }

    /**
     * Deletes this ContributingProduct and removes all associations with all parents.
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BonusProgram.DATABASE);
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
        return BonusProgram.DATABASE;
    }

    /**
     * Builds the set of ContributingProfiles if the SpecificCommissionProfStatus = 'All.' Duplicate entries for a
     * BonusProgram, ProductStructure are not allowed.
     */
    private void buildContributingProfiles()
    {
        CommissionProfile[] commissionProfiles = CommissionProfile.find_All();

        for (int i = 0; i < commissionProfiles.length; i++)
        {
            CommissionProfile commissionProfile = commissionProfiles[i];

            ContributingProfile contributingProfile = ContributingProfile.findBy_CommissionProfilePK_BonusProgramPK(commissionProfiles[i].getCommissionProfilePK(), getBonusProgramPK());

            // add contributing profiles only when not exitsting.
            if (contributingProfile == null)
            {
                contributingProfile = new ContributingProfile();

                contributingProfile.setCommissionProfile(commissionProfile);

                contributingProfile.setBonusProgram(this);

                contributingProfile.hSave();
            }
        }
    }

    /**
     * Converts the specified CodeTableDef.BONUSFREQUENCY to its numeric equivalent.
     *
     * @param bonusFrequency
     * @return
     */
    public static int getMode(String bonusFrequency)
    {
        int mode = 0;

        if (bonusFrequency.equalsIgnoreCase(BONUSFREQUENCY_ANNUAL))
        {
            mode = 12;
        }
        else if (bonusFrequency.equalsIgnoreCase(BONUSFREQUENCY_SEMIANNUAL))
        {
            mode = 6;
        }
        else if (bonusFrequency.equalsIgnoreCase(BONUSFREQUENCY_QUARTERLY))
        {
            mode = 3;
        }
        else if (bonusFrequency.equalsIgnoreCase(BONUSFREQUENCY_MONTHLY))
        {
            mode = 1;
        }
        else
        {
            throw new IllegalArgumentException("Parameter [" + bonusFrequency + "] Not Recognized");
        }

        return mode;
    }

    /**
     * For FrequencyCT's of 'Annual', 'Quarterly', and 'Monthy':
     * ... BonusProgram.NextCheckDate = BonusProgram.NextCheckDate + BonusProgram.FrequencyCT
     * <p/>
     * For FrequencyCT of 'One Time':
     * ... Do Nothing
     * Note: The specified frequencyCT must match this BonusProgram's FrequencyCT, or the request is ignored.
     * Note:  NextCheckDate = BonusProgram.StartDate + 1 BonusProgram.FrequencyCT/Mode - 1 day. (For all modes except 'Weekly')
     * if the nextCheckDate is initially null.
     * Note: If the specified processDate >= this BonusProgram.BonusStopDate, then the NextCheckDate is NOT generated.
     */
    public void generateNextCheckDate(EDITDate processDate)
    {
        EDITDate theNextCheckDate = null;

        if (shouldGenerateNextCheckDate(processDate))
        {
            if (getNextCheckDate() == null)
            {
                theNextCheckDate = new EDITDate(this.getBonusStartDate().getYear(),
                        getBonusStartDate().getMonth(),
                        getBonusStartDate().getDay());

                theNextCheckDate = theNextCheckDate.addMode(getFrequencyCT());
                
                theNextCheckDate = theNextCheckDate.subtractDays(1);
            }
            else
            {
                theNextCheckDate = getNextCheckDate();

                theNextCheckDate = theNextCheckDate.addMode(getFrequencyCT());
            }

            int theNextYear = theNextCheckDate.getYear();

            int theNextMonth = theNextCheckDate.getMonth();

            int theNextDay = theNextCheckDate.getNumberOfDaysInMonth();

            theNextCheckDate = new EDITDate(theNextYear, theNextMonth, theNextDay);

            setNextCheckDate(theNextCheckDate);
        }
    }

    /**
     * True if:
     * 1) The BonusProgram.FrequencyCT != BonusProgram.BONUSFREQUENCY_ONETIME and
     * 2) The specified processDate < BonusProgram.BonusStopDate.
     *
     * @param processDate
     * @return
     */
    private boolean shouldGenerateNextCheckDate(EDITDate processDate)
    {
        return (!getFrequencyCT().equalsIgnoreCase(BonusProgram.BONUSFREQUENCY_ONETIME)) && (processDate.before(getBonusStopDate()));
    }

    /**
     * Finder.
     *
     * @param frequencyCT
     * @return
     */
    public static List findBy_FrequencyCT(String frequencyCT)
    {
        String hql = "from BonusProgram bonusProgram" +
                " where bonusProgram.FrequencyCT = :frequencyCT";

        Map params = new HashMap();

        params.put("frequencyCT", frequencyCT);

        List results = SessionHelper.executeHQL(hql, params, BonusProgram.DATABASE);

        return results;
    }

    /**
     * Generates NextCheckDate for all BonusPrograms with the specified frequencyCT if the program
     * is still active.
     *
     * @param processDate
     * @param frequencyCT
     */
    public static void generateNextCheckDate(EDITDate processDate, String frequencyCT)
    {
        // Update the NextCheckDates for all BonusProgram's that participated in this job (via its mode).
        List bonusPrograms = BonusProgram.findBy_FrequencyCT(frequencyCT);

        for (int i = 0; i < bonusPrograms.size(); i++)
        {
            BonusProgram bonusProgram = (BonusProgram) bonusPrograms.get(i);

            bonusProgram.generateNextCheckDate(processDate);
        }

        try
        {
            SessionHelper.beginTransaction(BonusProgram.DATABASE);

            SessionHelper.commitTransaction(BonusProgram.DATABASE);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(BonusProgram.DATABASE);
        }
    }
}
