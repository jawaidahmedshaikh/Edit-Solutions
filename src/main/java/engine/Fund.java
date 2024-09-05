/*
 * User: sprasad
 * Date: May 12, 2005
 * Time: 11:18:38 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.services.db.hibernate.SessionHelper;

import edit.services.db.hibernate.*;
import edit.common.vo.*;

import java.util.*;

import engine.dm.dao.*;


public class Fund extends HibernateEntity
{
    public static final String FUNDTYPE_HEDGE = "Hedge";
    
    public static final String FUNDTYPE_SYSTEM = "System";
    public static final String PREFERRED_LOAN_QUALIFIER = "Preferred";
    public static final String NONPREFRRED_LOAN_QUALIFIER = "NonPreferred";
    public static final String FUNDTYPE_FIXED = "Fixed";
	public static String DATABASE = SessionHelper.ENGINE;

    private Long fundPK;
    private String name;
    private String fundType;
    private String portfolioNewMoneyStatusCT;
    private String shortName;
    private String excludeFromActivityFileInd;
    private String typeCodeCT;
    private String reportingFundName;
    private String loanQualifierCT;

    private Set filteredFunds = new HashSet();


    /**
     * Setter.
     * @param fundPK
     */
    public void setFundPK(Long fundPK)
    {
        this.fundPK = fundPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getFundPK()
    {
        return fundPK;
    }

    /**
     * Setter.
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Getter.
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter.
     * @param fundType
     */
    public void setFundType(String fundType)
    {
        this.fundType = fundType;
    }

    /**
     * Getter.
     * @return
     */
    public String getFundType()
    {
        return fundType;
    }

    /**
     * Setter.
     * @param portfolioNewMoneyStatusCT
     */
    public void setPortfolioNewMoneyStatusCT(String portfolioNewMoneyStatusCT)
    {
        this.portfolioNewMoneyStatusCT = portfolioNewMoneyStatusCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getPortfolioNewMoneyStatusCT()
    {
        return portfolioNewMoneyStatusCT;
    }


    /**
     * Setter.
     * @param shortName
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Getter.
     * @return
     */
    public String getShortName()
    {
        return shortName;
    }

    /**
     * Returns true if this is a hedge fund.
     * @return
     */
    public boolean isHedgeFund()
    {
        return FUNDTYPE_HEDGE.equals(getFundType());
    }

    /**
     * Setter.
     * @param excludeFromActivityFileInd
     */
    public void setExcludeFromActivityFileInd(String excludeFromActivityFileInd)
    {
        this.excludeFromActivityFileInd = excludeFromActivityFileInd;
    }

    /**
     * Getter.
     * @return
     */
    public String getExcludeFromActivityFileInd()
    {
        return excludeFromActivityFileInd;
    }

    /**
     * Setter.
     * @param typeCodeCT
     */
    public void setTypeCodeCT(String typeCodeCT)
    {
        this.typeCodeCT = typeCodeCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getTypeCodeCT()
    {
        return typeCodeCT;
    }

    /**
     * Setter
     * @param reportingFundName
     */
    public void setReportingFundName(String reportingFundName)
    {
        this.reportingFundName = reportingFundName;
    }

    /**
     * Getter
     * @return
     */
    public String getReportingFundName()
    {
        return reportingFundName;
    }

    /**
     * Setter
     */
    public void setLoanQualifierCT(String loanQualifierCT)
    {
        this.loanQualifierCT = loanQualifierCT;
    }

    /**
     * Getter
     */
    public String getLoanQualifierCT()
    {
        return loanQualifierCT;
    }


    /**
     * Getter
     * @return  set of filteredFunds
     */
    public Set getFilteredFunds()
    {
        return filteredFunds;
    }

    /**
     * Setter
     * @param filteredFunds      set of filteredFunds
     */
    public void setFilteredFunds(Set filteredFunds)
    {
        this.filteredFunds = filteredFunds;
    }

    /**
     * Adds a FilteredFund to the set of children
     * @param filteredFund
     */
    public void addFilteredFund(FilteredFund filteredFund)
    {
        this.getFilteredFunds().add(filteredFund);

        filteredFund.setFund(this);

//        SessionHelper.saveOrUpdate(filteredFund, Fund.DATABASE);
    }

    /**
     * Removes a FilteredFund from the set of children
     * @param filteredFund
     */
    public void removeFilteredFund(FilteredFund filteredFund)
    {
        this.getFilteredFunds().remove(filteredFund);

        filteredFund.setFund(null);

        SessionHelper.saveOrUpdate(filteredFund, Fund.DATABASE);
    }

    /**
     * Retrieve the Fund for the given fundPK
     * @param fundPK  - The primary key for the Fund that is to be retrieved.
     * @return the Fund
     */
    public static Fund findByPK(Long fundPK)
    {
        return (Fund) SessionHelper.get(Fund.class, fundPK, Fund.DATABASE);
    }

    /**
     * Originally in FundDAO.findFundByFilteredFundFK
     * @param filteredFundPK
     * @return
     */
    public static Fund[] findBy_FilteredFundPK(Long filteredFundPK)
    {
        String hql = "select fund from Fund fund " +
                     " join fund.FilteredFunds filteredFund" +
                     " where filteredFund.FilteredFundPK = :filteredFundPK";

        Map params = new HashMap();
        params.put("filteredFundPK", filteredFundPK);

        List results = SessionHelper.executeHQL(hql, params, Fund.DATABASE);

        return (Fund[]) results.toArray(new Fund[results.size()]);
	}


    /**
     * Originally in FundDAO.findFundsByCSId
     * Finds all Funds for a given ProductStructure
     * @param productStructurePK        the key of the ProductStructure
     * @return  all Funds
     */
    public static Fund[] findBy_ProductStructure(Long productStructurePK)
    {
        String hql = "select fund from Fund fund " +
                     " join fund.FilteredFunds filteredFund" +
                     " join filteredFund.ProductStructures productStructure" +
                     " where productStructure.ProductStructurePK = :productStructurePK";

        Map params = new HashMap();
        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, Fund.DATABASE);

        results = SessionHelper.makeUnique(results);

        return (Fund[]) results.toArray(new Fund[results.size()]);
	}

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Fund.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Fund.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Fund.DATABASE;
    }

    /**
     * Check fund type for fixed
     * @param filteredFundFK
     * @return
     */
    public static boolean checkForFixedFund(long filteredFundFK)
    {
        boolean fundIsFixed = false;

        FundVO fundVO = findByFilteredFundPK(filteredFundFK);

        if (fundVO.getFundType().equalsIgnoreCase(FUNDTYPE_FIXED))
        {
            fundIsFixed = true;
        }

        return fundIsFixed;
    }

    /**
     * get the fund for the FilteredFundFK
     * @param filteredFundFK
     * @return
     */
    public static FundVO findByFilteredFundPK(long filteredFundFK)
    {
        FundVO fundVO = null;
        FundVO[] fundVOs = DAOFactory.getFundDAO().findFundByFilteredFundFK(filteredFundFK, false, null);

        if (fundVOs != null)
        {
            fundVO = fundVOs[0];
        }

        return fundVO;
    }
}
