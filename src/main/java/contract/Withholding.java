/*
 * User: cgleason
 * Date: Jan 20, 2005
 * Time: 10:09:23 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import contract.dm.dao.WithholdingDAO;

import edit.common.EDITBigDecimal;

import edit.common.EDITMap;
import edit.common.vo.VOObject;
import edit.common.vo.WithholdingVO;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;

import event.*;

import java.util.*;


import org.hibernate.Session;


public class Withholding extends HibernateEntity implements CRUDEntity, SEGElement
{
    private CRUDEntityImpl crudEntityImpl;
    private WithholdingVO withholdingVO;
    private ContractClient contractClient;
//    private WithholdingOverride withholdingOverride;
    private Long withholdingPK;
    private Set withholdingOverrides;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a Withholding entity with a default WithholdingVO.
     */
    public Withholding()
    {
        init();
    }

    /**
     * Instantiates a Withholding entity with a supplied WithholdingVO.
     *
     * @param withholdingVO
     */
    public Withholding(WithholdingVO withholdingVO)
    {
        init();

        this.withholdingVO = withholdingVO;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCityWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getCityWithholdingAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCityWithholdingPercent()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getCityWithholdingPercent());
    }

    /**
     * Getter.
     * @return
     */
    public String getCityWithholdingTypeCT()
    {
        return withholdingVO.getCityWithholdingTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractClientFK()
    {
        return SessionHelper.getPKValue(withholdingVO.getContractClientFK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCountyWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getCountyWithholdingAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCountyWithholdingPercent()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getCountyWithholdingPercent());
    }

    /**
     * Getter.
     * @return
     */
    public String getCountyWithholdingTypeCT()
    {
        return withholdingVO.getCountyWithholdingTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFederalWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getFederalWithholdingAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFederalWithholdingPercent()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getFederalWithholdingPercent());
    }

    /**
     * Getter.
     * @return
     */
    public String getFederalWithholdingTypeCT()
    {
        return withholdingVO.getFederalWithholdingTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getStateWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getStateWithholdingAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getStateWithholdingPercent()
    {
        return SessionHelper.getEDITBigDecimal(withholdingVO.getStateWithholdingPercent());
    }

    /**
     * Getter.
     * @return
     */
    public String getStateWithholdingTypeCT()
    {
        return withholdingVO.getStateWithholdingTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public Long getWithholdingPK()
    {
        return SessionHelper.getPKValue(withholdingVO.getWithholdingPK());
    }

    /**
     * Setter.
     * @param cityWithholdingAmount
     */
    public void setCityWithholdingAmount(EDITBigDecimal cityWithholdingAmount)
    {
        withholdingVO.setCityWithholdingAmount(SessionHelper.getEDITBigDecimal(cityWithholdingAmount));
    }

    /**
     * Setter.
     * @param cityWithholdingPercent
     */
    public void setCityWithholdingPercent(EDITBigDecimal cityWithholdingPercent)
    {
        withholdingVO.setCityWithholdingPercent(SessionHelper.getEDITBigDecimal(cityWithholdingPercent));
    }

    /**
     * Setter.
     * @param cityWithholdingTypeCT
     */
    public void setCityWithholdingTypeCT(String cityWithholdingTypeCT)
    {
        withholdingVO.setCityWithholdingTypeCT(cityWithholdingTypeCT);
    }

    /**
     * Setter.
     * @param contractClientFK
     */
    public void setContractClientFK(Long contractClientFK)
    {
        withholdingVO.setContractClientFK(SessionHelper.getPKValue(contractClientFK));
    }

    /**
     * Setter.
     * @param countyWithholdingAmount
     */
    public void setCountyWithholdingAmount(EDITBigDecimal countyWithholdingAmount)
    {
        withholdingVO.setCountyWithholdingAmount(SessionHelper.getEDITBigDecimal(countyWithholdingAmount));
    }

    /**
     * Setter.
     * @param countyWithholdingPercent
     */
    public void setCountyWithholdingPercent(EDITBigDecimal countyWithholdingPercent)
    {
        withholdingVO.setCountyWithholdingPercent(SessionHelper.getEDITBigDecimal(countyWithholdingPercent));
    }

    /**
     * Setter.
     * @param countyWithholdingTypeCT
     */
    public void setCountyWithholdingTypeCT(String countyWithholdingTypeCT)
    {
        withholdingVO.setCountyWithholdingTypeCT(countyWithholdingTypeCT);
    }

    /**
     * Setter.
     * @param federalWithholdingAmount
     */
    public void setFederalWithholdingAmount(EDITBigDecimal federalWithholdingAmount)
    {
        withholdingVO.setFederalWithholdingAmount(SessionHelper.getEDITBigDecimal(federalWithholdingAmount));
    }

    /**
     * Setter.
     * @param federalWithholdingPercent
     */
    public void setFederalWithholdingPercent(EDITBigDecimal federalWithholdingPercent)
    {
        withholdingVO.setFederalWithholdingPercent(SessionHelper.getEDITBigDecimal(federalWithholdingPercent));
    }

    /**
     * Setter.
     * @param federalWithholdingTypeCT
     */
    public void setFederalWithholdingTypeCT(String federalWithholdingTypeCT)
    {
        withholdingVO.setFederalWithholdingTypeCT(federalWithholdingTypeCT);
    }

    /**
     * Setter.
     * @param stateWithholdingAmount
     */
    public void setStateWithholdingAmount(EDITBigDecimal stateWithholdingAmount)
    {
        withholdingVO.setStateWithholdingAmount(SessionHelper.getEDITBigDecimal(stateWithholdingAmount));
    }

    /**
     * Setter.
     * @param stateWithholdingPercent
     */
    public void setStateWithholdingPercent(EDITBigDecimal stateWithholdingPercent)
    {
        withholdingVO.setStateWithholdingPercent(SessionHelper.getEDITBigDecimal(stateWithholdingPercent));
    }

    /**
     * Setter.
     * @param stateWithholdingTypeCT
     */
    public void setStateWithholdingTypeCT(String stateWithholdingTypeCT)
    {
        withholdingVO.setStateWithholdingTypeCT(stateWithholdingTypeCT);
    }

    /**
     * Setter.
     * @param withholdingPK
     */
    public void setWithholdingPK(Long withholdingPK)
    {
        withholdingVO.setWithholdingPK(SessionHelper.getPKValue(withholdingPK));
    }

    /**
     * Getter.
     * @return
     */
    public ContractClient getContractClient()
    {
        return contractClient;
    }

    /**
     * Setter.
     * @param contractClient
     */
    public void setContractClient(ContractClient contractClient)
    {
        this.contractClient = contractClient;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (withholdingVO == null)
        {
            withholdingVO = new WithholdingVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        if (withholdingOverrides == null)
        {
            withholdingOverrides = new HashSet();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return withholdingVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return withholdingVO.getWithholdingPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.withholdingVO = (WithholdingVO) voObject;
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
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Withholding.DATABASE;
    }

    /**
     * Finder.
     *
     * @param withholdingPK
     */
    public static final Withholding findByPK(long withholdingPK)
    {
        Withholding withholding = null;

        WithholdingVO[] withholdingVOs = new WithholdingDAO().findByPK(withholdingPK);

        if (withholdingVOs != null)
        {
            withholding = new Withholding(withholdingVOs[0]);
        }

        return withholding;
    }
    
    /**
     * Finder by PK. Gets the entity using seperate session.
     * @param withholdingPK
     * @return Withholding entity.
     */
    public static final Withholding findSeperateByPK(Long withholdingPK)
    {
        Withholding withholding = null;

        String hql = " select withholding " +
                     " from Withholding withholding " +
                     " where withholding.WithholdingPK = :withholdingPK";

        EDITMap params = new EDITMap("withholdingPK", withholdingPK);

        Session session = null;   

        try
        {
          session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

          List<Withholding> results = SessionHelper.executeHQL(session, hql, params, 0);

          if (!results.isEmpty())
          {
            withholding = results.get(0);
          }        

        }
        finally
        {
          if (session != null) session.close();
        }

        return withholding;
    }

    /**
     * Getter
     * @return  set of withholdingOverrides
     */
    public Set getWithholdingOverrides()
    {
        return withholdingOverrides;
    }

    /**
     * Setter
     * @param withholdingOverrides      set of withholdingOverrides
     */
    public void setWithholdingOverrides(Set withholdingOverrides)
    {
        this.withholdingOverrides = withholdingOverrides;
    }

    /**
     * Adds a WithholdingOverride to the set of children
     * @param withholdingOverride
     */
    public void addWithholdingOverride(WithholdingOverride withholdingOverride)
    {
        this.getWithholdingOverrides().add(withholdingOverride);

        withholdingOverride.setWithholding(this);

        SessionHelper.saveOrUpdate(withholdingOverride, Withholding.DATABASE);
    }

    /**
     * Removes a WithholdingOverride from the set of children
     * @param withholdingOverride
     */
    public void removeWithholdingOverride(WithholdingOverride withholdingOverride)
    {
        this.getWithholdingOverrides().remove(withholdingOverride);

        withholdingOverride.setWithholding(null);

        SessionHelper.saveOrUpdate(withholdingOverride, Withholding.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Withholding.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Withholding.DATABASE);
    }
}
