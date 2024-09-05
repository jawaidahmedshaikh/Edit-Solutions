/*
 * User: vuppalapati
 * Date: May 18, 2007
 * Time: 10:10:10 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;

import edit.common.exceptions.EDITContractException;
import edit.common.vo.MasterContractVO;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.ProductStructure;

import java.util.ArrayList;
import java.util.List;

import group.*;
import fission.utility.DateTimeUtil;
import java.util.Set;
import staging.IStaging;
import staging.StagingContext;

/**
 * This class is created as similar to FilteredProduct , for each FilteredProduct there will be set of masterContracts associated liked to Segment
 * in ContractGroup.
 */
public class MasterContract extends HibernateEntity {

    /**
     * The PK.
     */
    private Long masterContractPK;
    /**
     * The grouping instance (a ContractGroup) for which
     * this FilteredProduct defines an allowable Product.
     */
    private Long contractGroupFK;
    /**
     * The associated ContractGroup.
     */
    private ContractGroup contractGroup;
    /**
     * The allowable Product association.  ProductStructure is in a different database so we only have the FK here, not the parent object
     */
    private Long filteredProductFK;
    private Set<Segment> segments;
    private FilteredProduct filteredProduct;
    /** Indicates if interim coverage is available */
    private boolean noInterimCoverage;
    /**
     * The start date for which this product association is valid.
     */
    private EDITDate masterContractEffectiveDate;
    private EDITDate masterContractTerminationDate;
    private EDITDate terminationDate;
    private EDITDate creationDate;
    /**
     * Informational number only.
     */
    private String masterContractNumber;
    private String masterContractName;
    private String stateCT;
    private String creationOperator;
    private String brandingCompanyCT;
    
    /**
     * Database to be used for this object
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    /** Creates a new instance of FilteredProduct */
    public MasterContract() {
    }

    /**
     * @see #filteredProductPK
     * @return Long
     */
    public Long getMasterContractPK() {
        return masterContractPK;
    }

    /**
     * @param masterContractPK
     * @see #masterContractPK
     */
    public void setMasterContractPK(Long masterContractPK) {
        this.masterContractPK = masterContractPK;
    }

    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }
    
    /**
     * @see #contractGroupFK
     * @return Long
     */
    public Long getContractGroupFK() {
        return contractGroupFK;
    }
    /**
    * Getter.
    * @return
    */
    public EDITDate getCreationDate()
    {
        return creationDate;
    }
    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUICreationDate()
    {
        String date = null;

        if (getCreationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getCreationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUICreationDate(String uiCreationDate)
    {
        if (uiCreationDate != null)
        {
            setCreationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiCreationDate));
        }
    }

    /**
     * @see #contractGroupFK
     * @param contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK) {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * @see #segments
     * @param segments
     */
    public void setSegments(Set<Segment> segments) {
        this.segments = segments;
    }

    /**
     * @see #segments
     * @return
     */
    public Set<Segment> getSegments() {
        return segments;
    }

    /**
     * @see #contractGroup
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup) {
        this.contractGroup = contractGroup;
    }

    /**
     * @see #contractGroup
     * @return ContractGroup
     */
    public ContractGroup getContractGroup() {
        return contractGroup;
    }

    /**
     * @see #filteredProductFK
     * @return Long
     */
    public Long getFilteredProductFK() {
        return filteredProductFK;
    }

    /**
     * @see #filteredProductFK
     * @param filteredProductFK
     */
    public void setFilteredProductFK(Long filteredProductFK) {
        this.filteredProductFK = filteredProductFK;
    }

    public void setFilteredProduct(FilteredProduct filteredProduct) {
        this.filteredProduct = filteredProduct;
    }

    /**
     * @see #contractGroup
     * @return ContractGroup
     */
    public FilteredProduct getFilteredProduct() {
        return filteredProduct;
    }

    /**
     * This getter supports SessionHelper's calling convention
     * @return
     */
    public boolean getNoInterimCoverage() {
    	return noInterimCoverage;
    }
    
    /**
     * This getter supports Hibernate's calling convention
     * @return
     */
    public boolean isNoInterimCoverage() {
		return noInterimCoverage;
	}

	public void setNoInterimCoverage(boolean noInterimCoverage) {
		this.noInterimCoverage = noInterimCoverage;
	}

	/**
     * @see #StateCT
     * @return
     */
    public String getStateCT() {
        return stateCT;
    }

    /**
     * @see # StateCT
     * @param  StateCT
     */
    public void setStateCT(String stateCT) {
        this.stateCT = stateCT;
    }

    /**
     * @see #BrandingCompanyCT
     * @return
     */
    public String getBrandingCompanyCT() {
        return brandingCompanyCT;
    }

    /**
     * @see #BrandingCompanyCT
     * @param  BrandingCompanyCT
     */
    public void setBrandingCompanyCT(String brandingCompanyCT) {
        this.brandingCompanyCT = brandingCompanyCT;
    }
    
    /**
     * @see #effectiveDate
     * @return
     */
    public EDITDate getMasterContractEffectiveDate() {
        return masterContractEffectiveDate;
    }

     /**
     * @see #effectiveDate
     * @return
     */
    public EDITDate getMasterContractTerminationDate() {
        return masterContractTerminationDate;
    }
    /**
     * @see #effectiveDate
     * @param effectiveDate
     */
    public void setMasterContractEffectiveDate(EDITDate masterContractEffectiveDate) {
        this.masterContractEffectiveDate = masterContractEffectiveDate;
    }
    /**
     * @see #effectiveDate
     * @param effectiveDate
     */
    public void setMasterContractTerminationDate(EDITDate masterContractTerminationDate) {
        this.masterContractTerminationDate = masterContractTerminationDate;
    }
    /**
     * @see #terminationDate
     * @return EDITDate
     */
    public EDITDate getTerminationDate() {
        return terminationDate;
    }

    /**
     * @see #terminationDate
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    /**
     * @see #masterContractNumber
     * @return the masterContractNumber
     */
    public String getMasterContractNumber() {
        return masterContractNumber;
    }

    /**
     * @param masterContractNumber
     * @see #masterContractNumber
     */
    public void setMasterContractNumber(String masterContractNumber) {
        this.masterContractNumber = masterContractNumber;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIMCEffectiveDate() {
        String date = null;

        if (getMasterContractEffectiveDate() != null) {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getMasterContractEffectiveDate());
        }

        return date;
    }
        /**
     * @see #creationOperator
     * @return
     */
    public String getCreationOperator()
    {
        return creationOperator;
    }

    /**
     * @param creationOperator
     * @see #creationOperator
     */
    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIMCTerminationDate() {
        String date = null;

        if (getMasterContractTerminationDate() != null) {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getMasterContractTerminationDate());
        }

        return date;
    }
    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIMCEffectiveDate(String uiEffectiveDate) {
        if (uiEffectiveDate != null) {
            setMasterContractEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiEffectiveDate));
        }
    }

     /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIMCTerminationDate(String uIMCTerminationDate) {
        if (uIMCTerminationDate != null) {
            setMasterContractTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uIMCTerminationDate));
        }
    }
    public String getUITerminationDate() {
        String date = null;

        if (getTerminationDate() != null) {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUITerminationDate(String uiTerminationDate) {
        if (uiTerminationDate != null) {
            setTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiTerminationDate));
        }
    }

    /**
     * Getter.
     * @return
     */
    public String getMasterContractName() {
        return masterContractName;
    }

    /**
     * Setter.
     * @param masterContractName
     */
    public void setMasterContractName(String masterContractName) {
        this.masterContractName = masterContractName;
    }

    /**
     * Builds a new MasterContract entity from the specified FilteredProduct and ContractGroup
     * @param productStructurePK
     * @param contractGroup
     * @param effectiveDate
     * @param masterContractNumber
     * @param operator
     * @throws EDITContractException if the association already exists (avoiding a duplicate association)
     */
    public static void build(Long filteredProductPK, Long ContractGroupPK,
            EDITDate masterContractEffectiveDate,EDITDate masterContractTerminationDate, String masterContractNumber,EDITDate creationDate, String masterContractName, String creationOperator) throws EDITContractException {
        if (filteredProductPK != null) {
            MasterContract masterContract = (MasterContract) SessionHelper.newInstance(MasterContract.class, SessionHelper.EDITSOLUTIONS);

            if (masterContractEffectiveDate != null) {
                masterContract.setMasterContractEffectiveDate(masterContractEffectiveDate);
            }

            if (masterContractTerminationDate != null) {
                masterContract.setMasterContractTerminationDate(masterContractTerminationDate);
            }
            masterContract.setMasterContractNumber(masterContractNumber);

            masterContract.setMasterContractName(masterContractName);
            masterContract.setCreationOperator(creationOperator);
            masterContract.setFilteredProductFK(filteredProductPK);
            masterContract.setContractGroupFK(ContractGroupPK);
            masterContract.setCreationDate(creationDate);


        } else {
            throw new EDITContractException("Duplicate FilteredProduct!");
        }
    }

    public String getDatabase() {
        return MasterContract.DATABASE;
    }

    public static MasterContract findByPK(Long masterContractPK) {
        MasterContract masterContract = null;

        String hql = " from  MasterContract masterContract"
                + " where masterContract.MasterContractPK = :masterContractPK";

        EDITMap params = new EDITMap();
        params.put("masterContractPK", masterContractPK);

        List<MasterContract> results = SessionHelper.executeHQL(hql, params, MasterContract.DATABASE);

        if (!results.isEmpty()) {
            masterContract = results.get(0);
        }

        return masterContract;
    }

    public static MasterContract[] findBy_FilteredProductPK(Long filteredProductPK) {
        String hql = " from MasterContract masterContract" + " where masterContract.FilteredProductFK = :filteredProductFK";

        EDITMap params = new EDITMap("filteredProductFK", filteredProductPK);

        List<MasterContract> masterContracts = SessionHelper.executeHQL(hql, params, MasterContract.DATABASE);

        return masterContracts.toArray(new MasterContract[masterContracts.size()]);
    }

    public static MasterContract findByFilteredPK(Long filteredProductPK) {
        MasterContract masterContract = null;

        String hql = " from MasterContract masterContract" + " where masterContract.FilteredProductFK = :filteredProductFK";

        EDITMap params = new EDITMap("filteredProductFK", filteredProductPK);

        List<MasterContract> results = SessionHelper.executeHQL(hql, params, MasterContract.DATABASE);

        if (!results.isEmpty()) {
            masterContract = results.get(0);
        }

        return masterContract;
    }

    public void removeSelectedMasterContract(Long masterContractPK) throws EDITContractException {
        MasterContract masterContract = MasterContract.findByPK(masterContractPK);

        try {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.delete(masterContract, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        } finally {
            SessionHelper.clearSessions();
        }
    }

    public void addSegment(Segment segment) {
        this.getSegments().add(segment);

        segment.setMasterContract(this);

        SessionHelper.saveOrUpdate(segment, MasterContract.DATABASE);
    }
    public static MasterContract findbySegmentDetails(Segment segment) {
        MasterContract masterContract = null;
        Long productStructFK = segment.getProductStructureFK();
        Long contractGroupFK = segment.getContractGroup().getContractGroupFK();
        EDITDate effectiveDate = segment.getEffectiveDate();
        String IssueStateCT = segment.getIssueStateCT();
        String all = "*";

        String hql = " select masterContract"
                + " from MasterContract masterContract"
                + " left join masterContract.Segments segment"
                + " join masterContract.FilteredProduct filteredProduct"
                + " where (masterContract.StateCT = :IssueStateCT"
                + " or masterContract.StateCT like :all)"
                + " and filteredProduct.ProductStructureFK = :productStructureFK"
                + " and filteredProduct.ContractGroupFK = :contractGroupFK"
                + " and masterContract.MasterContractEffectiveDate <= :effectiveDate"
                + " and masterContract.MasterContractTerminationDate >= :effectiveDate";

        EDITMap params = new EDITMap();

        params.put("IssueStateCT", IssueStateCT);
        params.put("productStructureFK", productStructFK);
        params.put("contractGroupFK", contractGroupFK);
        params.put("effectiveDate", effectiveDate);
        params.put("all", all);

        List<MasterContract> results = SessionHelper.executeHQL(hql, params, MasterContract.DATABASE);

        if (!results.isEmpty()) {

            for (int i = 0; i < results.size(); i++) 
            {
                 masterContract = results.get(i);
                if ( masterContract.getStateCT().equals(IssueStateCT)) { 
                    return masterContract;
                } 
                else  
                {
                     continue;
                }
            }
        }

        return masterContract;
    }
}
