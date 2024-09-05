/*
 * User: gfrosti
 * Date: Nov 29, 2004
 * Time: 10:43:31 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package reinsurance;

import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.exceptions.EDITReinsuranceException;
import edit.common.vo.ContractTreatyVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import group.ContractGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reinsurance.dm.dao.ContractTreatyDAO;


public class ContractTreaty extends HibernateEntity implements CRUDEntity
{
    public static final int MASTER_ELEMENT = 1;

    public static final int SEGMENT_ELEMENT = 0;

    private ContractTreatyVO contractTreatyVO;
    private CRUDEntityImpl crudEntityImpl;

    private Long segmentFK;

    //  parents
    private Segment segment;
    private Treaty treaty;
    private ContractGroup contractGroup;

    private String status;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Instantiates a ContractTreaty entity with a default ContractTreatyVO.
     */
    public ContractTreaty()
    {
        init();

        this.contractTreatyVO.setTreatyOverrideInd("N");

        this.contractTreatyVO.setPolicyOverrideInd("N");
    }

    /**
     * Instantiates a ContractTreaty entity with a ContractTreatyVO retrieved from persistence.
     * @param contractTreatyPK
     */
    public ContractTreaty(long contractTreatyPK)
    {
        init();

        crudEntityImpl.load(this, contractTreatyPK, ConnectionFactory.EDITSOLUTIONS_POOL);

        setContractTreatyValues();
    }

    /**
     * Instantiates a ContractTreaty entity with a supplied ContractTreatyVO.
     * @param contractTreatyVO
     */
    public ContractTreaty(ContractTreatyVO contractTreatyVO)
    {
        init();

        this.contractTreatyVO = contractTreatyVO;

        setContractTreatyValues();
    }

    /**
     * Associates this ContractTreaty with the specified Segment.
     * @param segment
     */
    public void associateSegment(Segment segment)
    {
        contractTreatyVO.setSegmentFK(segment.getPK());
    }

    /**
     * Associates this ContractTreaty with the specified Treaty.
     * @param treaty
     */
    public void associateTreaty(Treaty treaty)
    {
        contractTreatyVO.setTreatyFK(treaty.getPK());
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITReinsuranceException
    {
        boolean shouldDelete = checkForConstraints();

        if (shouldDelete)
        {
            crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
        }
        else
        {
            this.save();
        }
    }

    /**
     * True if the values of this ContractTreaty override the associated values of Treaty.
     * @return
     */
    public boolean getOverrideTreaty()
    {
        boolean overrideTreaty = false;

        if (this.contractTreatyVO.getTreatyOverrideInd().equals("Y"))
        {
            overrideTreaty = true;
        }

        return overrideTreaty;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return contractTreatyVO.getContractTreatyPK();
    }

    /**
     * Getter.
     * @return
     */
    public Treaty getTreaty()
    {
        return treaty;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return contractTreatyVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws EDITReinsuranceException
    {
        checkForSegmentDuplicates();

        setContractTreatyValues();

        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * If true, the ContractTreaty will use its own values as opposed to defaulting to the values currently
     * in its associated Treaty. If false, its own value will be emptied and deferred to its associated Treaty.
     * @param overrideTreaty
     */
    public void setOverrideTreaty(boolean overrideTreaty)
    {
        if (overrideTreaty)
        {
            this.contractTreatyVO.setTreatyOverrideInd("Y");
        }
        else if (!overrideTreaty)
        {
            this.contractTreatyVO.setTreatyOverrideInd("N");
        }
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.contractTreatyVO = (ContractTreatyVO) voObject;
    }

    public Long getContractGroupFK()
    {
        return SessionHelper.getPKValue(this.contractTreatyVO.getContractGroupFK());
    }

    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractTreatyVO.setContractGroupFK(SessionHelper.getPKValue(contractGroupFK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractTreatyPK()
    {
        return SessionHelper.getPKValue(contractTreatyVO.getContractTreatyPK());
    } //-- long getContractTreatyPK()

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(contractTreatyVO.getEffectiveDate());
    } //-- java.lang.String getEffectiveDate()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFlatExtra()
    {
        return SessionHelper.getEDITBigDecimal(contractTreatyVO.getFlatExtra());
    } //-- java.math.BigDecimal getFlatExtra()

    /**
     * Getter.
     * @return
     */
    public int getFlatExtraAge()
    {
        return contractTreatyVO.getFlatExtraAge();
    } //-- int getFlatExtraAge()

    /**
     * Getter.
     * @return
     */
    public int getFlatExtraDuration()
    {
        return contractTreatyVO.getFlatExtraDuration();
    } //-- int getFlatExtraDuration()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMaxReinsuranceAmount()
    {
        return SessionHelper.getEDITBigDecimal(contractTreatyVO.getMaxReinsuranceAmount());
    } //-- java.math.BigDecimal getMaxReinsuranceAmount()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPercentExtra()
    {
        return SessionHelper.getEDITBigDecimal(contractTreatyVO.getPercentExtra());
    } //-- java.math.BigDecimal getPercentExtra()

    /**
     * Getter.
     * @return
     */
    public int getPercentExtraAge()
    {
        return contractTreatyVO.getPercentExtraAge();
    } //-- int getPercentExtraAge()

    /**
     * Getter.
     * @return
     */
    public int getPercentExtraDuration()
    {
        return contractTreatyVO.getPercentExtraDuration();
    } //-- int getPercentExtraDuration()

    /**
     * Getter.
     * @return
     */
    public String getPolicyOverrideInd()
    {
        return contractTreatyVO.getPolicyOverrideInd();
    } //-- java.lang.String getPolicyOverrideInd()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPoolPercentage()
    {
        return SessionHelper.getEDITBigDecimal(contractTreatyVO.getPoolPercentage());
    } //-- java.math.BigDecimal getPoolPercentage()

    /**
     * Getter.
     * @return
     */
    public String getReinsuranceClassCT()
    {
        return contractTreatyVO.getReinsuranceClassCT();
    } //-- java.lang.String getReinsuranceClassCT()

    /**
     * Getter.
     * @return
     */
    public String getReinsuranceIndicatorCT()
    {
        return contractTreatyVO.getReinsuranceIndicatorCT();
    } //-- java.lang.String getReinsuranceIndicatorCT()

    /**
     * Getter.
     * @return
     */
    public String getReinsuranceTypeCT()
    {
        return contractTreatyVO.getReinsuranceTypeCT();
    } //-- java.lang.String getReinsuranceTypeCT()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRetentionAmount()
    {
        return SessionHelper.getEDITBigDecimal(contractTreatyVO.getRetentionAmount());
    } //-- java.math.BigDecimal getRetentionAmount()

    /**
     * Getter.
     * @return
     */
    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(contractTreatyVO.getSegmentFK());
    } //-- long getSegmentFK()

    /**
     * Getter.
     * @return
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Getter.
     * @return
     */
    public String getTableRatingCT()
    {
        return contractTreatyVO.getTableRatingCT();
    } //-- java.lang.String getTableRatingCT()

    /**
     * Getter.
     * @return
     */
    public Long getTreatyFK()
    {
        return SessionHelper.getPKValue(contractTreatyVO.getTreatyFK());
    } //-- long getTreatyFK()

    /**
     * Getter.
     * @return
     */
    public String getTreatyOverrideInd()
    {
        return contractTreatyVO.getTreatyOverrideInd();
    } //-- java.lang.String getTreatyOverrideInd()

    /**
     * Getter.
     * @return
     */
    public String getTreatyTypeCT()
    {
        return contractTreatyVO.getTreatyTypeCT();
    } //-- java.lang.String getTreatyTypeCT()

    /**
     * Setter.
     * @param contractTreatyPK
     */
    public void setContractTreatyPK(Long contractTreatyPK)
    {
        contractTreatyVO.setContractTreatyPK(SessionHelper.getPKValue(contractTreatyPK));
    } //-- void setContractTreatyPK(long)

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        contractTreatyVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    } //-- void setEffectiveDate(java.lang.String)

    /**
     * Setter.
     * @param flatExtra
     */
    public void setFlatExtra(EDITBigDecimal flatExtra)
    {
        contractTreatyVO.setFlatExtra(SessionHelper.getEDITBigDecimal(flatExtra));
    } //-- void setFlatExtra(java.math.BigDecimal)

    /**
     * Setter.
     * @param flatExtraAge
     */
    public void setFlatExtraAge(int flatExtraAge)
    {
        contractTreatyVO.setFlatExtraAge(flatExtraAge);
    } //-- void setFlatExtraAge(int)

    /**
     * Setter.
     * @param flatExtraDuration
     */
    public void setFlatExtraDuration(int flatExtraDuration)
    {
        contractTreatyVO.setFlatExtraDuration(flatExtraDuration);
    } //-- void setFlatExtraDuration(int)

    /**
     * Setter.
     * @param maxReinsuranceAmount
     */
    public void setMaxReinsuranceAmount(EDITBigDecimal maxReinsuranceAmount)
    {
        contractTreatyVO.setMaxReinsuranceAmount(SessionHelper.getEDITBigDecimal(maxReinsuranceAmount));
    } //-- void setMaxReinsuranceAmount(java.math.BigDecimal)

    /**
     * Setter.
     * @param percentExtra
     */
    public void setPercentExtra(EDITBigDecimal percentExtra)
    {
        contractTreatyVO.setPercentExtra(SessionHelper.getEDITBigDecimal(percentExtra));
    } //-- void setPercentExtra(java.math.BigDecimal)

    /**
     * Setter.
     * @param percentExtraAge
     */
    public void setPercentExtraAge(int percentExtraAge)
    {
        contractTreatyVO.setPercentExtraAge(percentExtraAge);
    } //-- void setPercentExtraAge(int)

    /**
     * Setter.
     * @param percentExtraDuration
     */
    public void setPercentExtraDuration(int percentExtraDuration)
    {
        contractTreatyVO.setPercentExtraDuration(percentExtraDuration);
    } //-- void setPercentExtraDuration(int)

    /**
     * Setter.
     * @param policyOverrideInd
     */
    public void setPolicyOverrideInd(String policyOverrideInd)
    {
        contractTreatyVO.setPolicyOverrideInd(policyOverrideInd);
    } //-- void setPolicyOverrideInd(java.lang.String)

    /**
     * Setter.
     * @param poolPercentage
     */
    public void setPoolPercentage(EDITBigDecimal poolPercentage)
    {
        contractTreatyVO.setPoolPercentage(SessionHelper.getEDITBigDecimal(poolPercentage));
    } //-- void setPoolPercentage(java.math.BigDecimal)

    /**
     * Setter.
     * @param reinsuranceClassCT
     */
    public void setReinsuranceClassCT(String reinsuranceClassCT)
    {
        contractTreatyVO.setReinsuranceClassCT(reinsuranceClassCT);
    } //-- void setReinsuranceClassCT(java.lang.String)

    /**
     * Setter.
     * @param reinsuranceIndicatorCT
     */
    public void setReinsuranceIndicatorCT(String reinsuranceIndicatorCT)
    {
        contractTreatyVO.setReinsuranceIndicatorCT(reinsuranceIndicatorCT);
    } //-- void setReinsuranceIndicatorCT(java.lang.String)

    /**
     * Setter.
     * @param reinsuranceTypeCT
     */
    public void setReinsuranceTypeCT(String reinsuranceTypeCT)
    {
        contractTreatyVO.setReinsuranceTypeCT(reinsuranceTypeCT);
    } //-- void setReinsuranceTypeCT(java.lang.String)

    /**
     * Setter.
     * @param retentionAmount
     */
    public void setRetentionAmount(EDITBigDecimal retentionAmount)
    {
        contractTreatyVO.setRetentionAmount(SessionHelper.getEDITBigDecimal(retentionAmount));
    } //-- void setRetentionAmount(java.math.BigDecimal)

    /**
     * Setter.
     * @param segmentFK
     */
    public void setSegmentFK(Long segmentFK)
    {
        contractTreatyVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    } //-- void setSegmentFK(long)

    /**
     * Setter.
     * @param status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * Setter.
     * @param tableRatingCT
     */
    public void setTableRatingCT(String tableRatingCT)
    {
        contractTreatyVO.setTableRatingCT(tableRatingCT);
    } //-- void setTableRatingCT(java.lang.String)

    /**
     * Setter.
     * @param treatyFK
     */
    public void setTreatyFK(Long treatyFK)
    {
        contractTreatyVO.setTreatyFK(SessionHelper.getPKValue(treatyFK));
    } //-- void setTreatyFK(long)

    /**
     * Setter.
     * @param treatyOverrideInd
     */
    public void setTreatyOverrideInd(String treatyOverrideInd)
    {
        contractTreatyVO.setTreatyOverrideInd(treatyOverrideInd);
    } //-- void setTreatyOverrideInd(java.lang.String)

    /**
     * Setter.
     * @param treatyTypeCT
     */
    public void setTreatyTypeCT(String treatyTypeCT)
    {
        contractTreatyVO.setTreatyTypeCT(treatyTypeCT);
    } //-- void setTreatyTypeCT(java.lang.String)

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * Setter.
     * @param treaty
     */
    public void setTreaty(Treaty treaty)
    {
        this.treaty = treaty;
    }

    /**
     *  Checks to see if this ContractTreaty has associations with ReinsuranceHistory preventing its deletion.
     */
    private boolean checkForConstraints() throws EDITReinsuranceException
    {
        boolean shouldDelete = true;

        ReinsuranceHistory[] reinsuranceHistory = ReinsuranceHistory.findBy_ContractTreatyPK(getPK());

        if (reinsuranceHistory != null)
        {
            this.setStatus("D");
            shouldDelete = false;
        }

        return shouldDelete;
    }

    /**
     * Checks for pre-existing associations between Segment and Treaty.
     * @throws edit.common.exceptions.EDITReinsuranceException if the CompanyStructure/TreatyGroup association has already been made
     */
    private void checkForSegmentDuplicates() throws EDITReinsuranceException
    {
        if (contractTreatyVO.getSegmentFK() != 0 && isNew())
        {
            ContractTreaty contractTreaty = findBy_SegmentPK_TreatyPK(contractTreatyVO.getSegmentFK(), contractTreatyVO.getTreatyFK());

            if (contractTreaty != null)
            {
                throw new EDITReinsuranceException("WARNING: Treaty Has Already Been Mapped To Segment");
            }
        }
    }

    /**
     * Checks for pre-existing associations between Segment and Treaty.
     * @throws edit.common.exceptions.EDITReinsuranceException if the CompanyStructure/TreatyGroup association has already been made
     */
    public void checkForContractGroupDuplicates() throws EDITReinsuranceException
    {
        ContractTreaty contractTreaty = findBy_ContractGroup_Treaty(getContractGroup(), getTreaty());

        if (contractTreaty != null)
        {
            throw new EDITReinsuranceException("WARNING: Treaty Has Already Been Mapped To ContractGroup");
        }
    }

    /**
     * Finder.
     * @param contractGroupFK
     * @param treatyFK
     * @return
     */
    private ContractTreaty findBy_ContractGroup_Treaty(ContractGroup contractGroup, Treaty treaty)
    {
        ContractTreaty contractTreaty = null;

        String hql = " from ContractTreaty contractTreaty" +
                     " where contractTreaty.ContractGroup = :contractGroup" +
                     " and contractTreaty.Treaty = :treaty";

        Map params = new HashMap();

        params.put("treaty", treaty);

        params.put("contractGroup", contractGroup);

        List<ContractTreaty> results = SessionHelper.executeHQL(hql, params, DATABASE);

        if (!results.isEmpty())
        {
            contractTreaty = results.get(0); // should only ever be one
        }

        return  contractTreaty;
    }

    /**
     * Finder.
     * @param contractGroupPK
     * @param treatyPK
     * @return
     */
    public static ContractTreaty findBy_ContractGroupPK_TreatyPK(Long contractGroupPK, Long treatyPK)
    {
        ContractTreaty contractTreaty = null;

        String hql = " from ContractTreaty contractTreaty" +
                     " where contractTreaty.ContractGroupFK = :contractGroupPK" +
                     " and contractTreaty.TreatyFK = :treatyPK";

        Map params = new HashMap();

        params.put("treatyPK", treatyPK);

        params.put("contractGroupPK", contractGroupPK);

        List<ContractTreaty> results = SessionHelper.executeHQL(hql, params, DATABASE);

        if (!results.isEmpty())
        {
            contractTreaty = results.get(0); // should only ever be one
        }

        return  contractTreaty;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (contractTreatyVO == null)
        {
            contractTreatyVO = new ContractTreatyVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * The ContractTreaty either represents its own values if the TreatyOverrideInd = 'Y', or it represents the values
     * of its associated Treaty if TreatyOverrideInd = 'N'. The possible overrides are:
     * 1) PoolPercentage
     * 2) RetentionAmount
     */
    private void setContractTreatyValues()
    {
        if (!isNew() && !getOverrideTreaty()) // The default is to use Treaty's value.
        {
            Treaty treaty = Treaty.findBy_ContractTreatyPK(getPK());

            setPoolPercentage(treaty.getPoolPercentage());

            setRetentionAmount(treaty.getRetentionAmount());
        }
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContractTreaty.DATABASE;
    }

    /**
     * Finder.
     * @param segmentPK
     * @param treatyPK
     * @return
     */
    public static final ContractTreaty findBy_SegmentPK_TreatyPK(long segmentPK, long treatyPK)
    {
        ContractTreaty contractTreaty = null;

        ContractTreatyVO[] contractTreatyVOs = new ContractTreatyDAO().findBy_SegmentPK_TreatyPK(segmentPK, treatyPK);

        if (contractTreatyVOs != null)
        {
            contractTreaty = new ContractTreaty(contractTreatyVOs[0]);
        }

        return contractTreaty;
    }

//    /**
//     * Finder.
//     * @param casePK
//     * @param segmentPK
//     * @param treatyPK
//     * @return
//     */
//    public static final ContractTreaty findBy_CasePK_SegmentPK_TreatyPK(long casePK, long segmentPK, long treatyPK)
//    {
//        ContractTreaty contractTreaty = null;
//
//        ContractTreatyVO[] contractTreatyVOs = new ContractTreatyDAO().findBy_CasePK_SegmentPK_TreatyPK(casePK, segmentPK, treatyPK);
//
//        if (contractTreatyVOs != null)
//        {
//            contractTreaty = new ContractTreaty(contractTreatyVOs[0]);
//        }
//
//        return contractTreaty;
//    }

    /**
     * Finder.
     * @param casePK
     * @param segmentPK
     * @return
     */
//    public static final ContractTreaty findBy_CasePK_SegmentPK(long casePK, long segmentPK)
//    {
//        ContractTreaty contractTreaty = null;
//
//        ContractTreatyVO[] contractTreatyVOs = new ContractTreatyDAO().findBy_CasePK_SegmentPK(casePK, segmentPK);
//
//        if (contractTreatyVOs != null)
//        {
//            contractTreaty = new ContractTreaty(contractTreatyVOs[0]);
//        }
//
//        return contractTreaty;
//    }

    /**
     * Finder. SegmentFK is assumed NULL.
     * @param casePK
     * @param treatyPK
     * @return
     */
//    public static final ContractTreaty findBy_CasePK_SegmentPK_IS_NULL_TreatyPK(long casePK, long treatyPK)
//    {
//        ContractTreaty contractTreaty = null;
//
//        ContractTreatyVO[] contractTreatyVOs = new ContractTreatyDAO().findBy_CasePK_SegmentPK_IS_NULL_TreatyPK(casePK, treatyPK);
//
//        if (contractTreatyVOs != null)
//        {
//            contractTreaty = new ContractTreaty(contractTreatyVOs[0]);
//        }
//
//        return contractTreaty;
//    }

    /**
     * Finder.
     * @param segmentPK
     * @return
     */ 
    public static final ContractTreaty[] findBy_SegmentPK(long segmentPK)
    {
        return (ContractTreaty[]) CRUDEntityImpl.mapVOToEntity(new ContractTreatyDAO().findBy_SegmentPK(segmentPK), ContractTreaty.class);
    }

    /**
     * Finder.
     * @param treatyPK
     * @return
     */
    public static final ContractTreaty[] findBy_TreatyPK(long treatyPK)
    {
        return (ContractTreaty[]) CRUDEntityImpl.mapVOToEntity(new ContractTreatyDAO().findBy_TreatyPK(treatyPK), ContractTreaty.class);
    }

	/**
     * Finder.
     * @param contractTreatyPK
     * @return
     */
    public static final ContractTreaty findBy_ContractTreatyPK(long contractTreatyPK)
    {
        ContractTreaty contractTreaty = null;

        ContractTreatyVO[] contractTreatyVOs = new ContractTreatyDAO().findBy_PK(contractTreatyPK);

        if (contractTreatyVOs != null)
        {
            contractTreaty = new ContractTreaty(contractTreatyVOs[0]);
        }

        return contractTreaty;
	}

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContractTreaty.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ContractTreaty.DATABASE);
    }
    
  /**
   * Constructs composite ContractTreaty(s) with the following entities fetched:
   * Segment.ContractTreaty
   * Segment.ContractTreaty.Treaty
   * Segment.ContractTreaty.Treaty.TreatyGroup
   * Segment.ContractTreaty.Treaty.Reinsurer
   * @param segmentPK
   * @return
   */
  public static ContractTreaty[] findBy_SegmentPK_V1(Long segmentPK)
  {
    String hql = " select contractTreaty" +
                " from ContractTreaty contractTreaty" +
                " join fetch contractTreaty.Treaty treaty" +
                " join fetch treaty.TreatyGroup" +
                " join fetch treaty.Reinsurer" +
                " join contractTreaty.Segment segment" +
                " where segment.SegmentPK = :segmentPK";
                
    Map params = new EDITMap("segmentPK", segmentPK);                
                
    List<ContractTreaty> results = SessionHelper.executeHQL(hql, params, ContractTreaty.DATABASE);  
    
    return results.toArray(new ContractTreaty[results.size()]);
  }

    /**
     * @return the contraccontractGrouptTreaty
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * @param contractGroup the contractGroup to set
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }
}

