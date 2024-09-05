/*
 * User: gfrosti
 * Date: Nov 7, 2003
 * Time: 9:32:34 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.vo.*;
import edit.common.vo.VOObject;
import edit.common.*;
import edit.services.db.*;
import edit.services.db.hibernate.*;

import java.util.*;

import event.dm.dao.*;


public class TransactionCorrespondence extends HibernateEntity implements CRUDEntity
{
    private TransactionCorrespondenceVO transactionCorrespondenceVO;

    private TransactionCorrespondenceImpl transactionCorrespondenceImpl;

    private Set editTrxCorrespondences;

    private TransactionPriority transactionPriority;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public TransactionCorrespondence()
    {
        init();
        this.transactionCorrespondenceImpl = new TransactionCorrespondenceImpl();
        this.transactionCorrespondenceVO = new TransactionCorrespondenceVO();
    }

    public TransactionCorrespondence(long transactionCorrespondencePK) throws Exception
    {
        this();

        init();
        this.transactionCorrespondenceImpl.load(this, transactionCorrespondencePK);
    }

    public TransactionCorrespondence(TransactionCorrespondenceVO transactionCorrespondenceVO)
    {
        this();

        init();
        this.transactionCorrespondenceVO = transactionCorrespondenceVO;
    }

   /**
     * Guarantees that HashSets are properly instantiated.
     */
    private final void init()
    {
        if (editTrxCorrespondences == null)
        {
            editTrxCorrespondences = new HashSet();
        }
    }

    public String getCorrespondenceTypeCT()
    {
        return transactionCorrespondenceVO.getCorrespondenceTypeCT();
    }

    public int getNumberOfDays()
    {
        return transactionCorrespondenceVO.getNumberOfDays();
    }

    public String getPriorPostCT()
    {
        return transactionCorrespondenceVO.getPriorPostCT();
    }

    public Long getTransactionCorrespondencePK()
    {
        return SessionHelper.getPKValue(transactionCorrespondenceVO.getTransactionCorrespondencePK());
    }

    public Long getTransactionPriorityFK()
    {
        return SessionHelper.getPKValue(transactionCorrespondenceVO.getTransactionPriorityFK());
    }

    public void setCorrespondenceTypeCT(String correspondenceTypeCT)
    {
        transactionCorrespondenceVO.setCorrespondenceTypeCT(correspondenceTypeCT);
    }

    public void setNumberOfDays(int numberOfDays)
    {
        transactionCorrespondenceVO.setNumberOfDays(numberOfDays);
    }

    public void setPriorPostCT(String priorPostCT)
    {
        transactionCorrespondenceVO.setPriorPostCT(priorPostCT);
    }

    public String getTransactionTypeQualifierCT()
    {
        return transactionCorrespondenceVO.getTransactionTypeQualifierCT();
    }

    public void setTransactionTypeQualifierCT(String transactionTypeQualifierCT)
    {
        transactionCorrespondenceVO.setTransactionTypeQualifierCT(transactionTypeQualifierCT);
    }

    public String getIncludeHistoryIndicator()
    {
        return transactionCorrespondenceVO.getIncludeHistoryIndicator();
    }

    public void setIncludeHistoryIndicator(String includeHistoryIndicator)
    {
        transactionCorrespondenceVO.setIncludeHistoryIndicator(includeHistoryIndicator);
    }

    /**
     * Getter
     * @return
     */
    public EDITDate getStartDate()
    {
        return SessionHelper.getEDITDate(transactionCorrespondenceVO.getStartDate());
    } //-- java.lang.String getStartDate()

    /**
     * Getter
     */
    public EDITDate getStopDate()
    {
        return SessionHelper.getEDITDate(transactionCorrespondenceVO.getStopDate());
    } //-- java.lang.String getStopDate()

    /**
     * Setter
     * @param startDate
     */
    public void setStartDate(EDITDate startDate)
    {
        transactionCorrespondenceVO.setStartDate(SessionHelper.getEDITDate(startDate));
    } //-- void setStartDate(java.lang.String)

    /**
     * Setter
     * @param stopDate
     */
    public void setStopDate(EDITDate stopDate)
    {
        transactionCorrespondenceVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    } //-- void setStopDate(java.lang.String)

    public void setTransactionCorrespondencePK(Long transactionCorrespondencePK)
    {
        transactionCorrespondenceVO.setTransactionCorrespondencePK(SessionHelper.getPKValue(transactionCorrespondencePK));
    }

    public void setTransactionPriorityFK(Long transactionPriorityFK)
    {
        transactionCorrespondenceVO.setTransactionPriorityFK(SessionHelper.getPKValue(transactionPriorityFK));
    }

    public Set getEDITTrxCorrespondences()
    {
        return editTrxCorrespondences;
    }

    public void setEDITTrxCorrespondences(Set editTrxCorrespondences)
    {
        this.editTrxCorrespondences = editTrxCorrespondences;
    }

    public VOObject getVO()
    {
        return transactionCorrespondenceVO;
    }

    public void save() throws Exception
    {
        this.transactionCorrespondenceImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.transactionCorrespondenceImpl.delete(this);
    }

    public long getPK()
    {
        return transactionCorrespondenceVO.getTransactionCorrespondencePK();
    }

    public void setVO(VOObject voObject)
    {
        this.transactionCorrespondenceVO = (TransactionCorrespondenceVO) voObject;
    }

    public boolean isNew()
    {
        return transactionCorrespondenceImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return transactionCorrespondenceImpl.cloneCRUDEntity(this);
    }

    /**
     * Adds a EDITTrxCorrespondence to the set of children
     * @param editTrxCorrespondence
     */
    public void addEDITTrxCorrespondence(EDITTrxCorrespondence editTrxCorrespondence)
    {
        this.getEDITTrxCorrespondences().add(editTrxCorrespondence);

        editTrxCorrespondence.setTransactionCorrespondence(this);

        SessionHelper.saveOrUpdate(editTrxCorrespondence, TransactionCorrespondence.DATABASE);
    }

    /**
     * Removes a EDITTrxCorrespondence from the set of children
     * @param editTrxCorrespondence
     */
    public void removeEDITTrxCorrespondence(EDITTrxCorrespondence editTrxCorrespondence)
    {
        this.getEDITTrxCorrespondences().remove(editTrxCorrespondence);

        editTrxCorrespondence.setTransactionCorrespondence(null);

        SessionHelper.saveOrUpdate(editTrxCorrespondence, TransactionCorrespondence.DATABASE);
    }

   /**
     * Setter.
     * @param transactionPriority
     */
    public void setTransactionPriority(TransactionPriority transactionPriority)
    {
        this.transactionPriority = transactionPriority;
    }

   /**
     * Getter.
     * @return
     */
    public TransactionPriority getTransactionPriority()
    {
        return this.transactionPriority;
    }

    public static TransactionCorrespondence findBy_PK(Long transactionCorrespondencePK)
    {
        TransactionCorrespondence transactionCorrespondence = (TransactionCorrespondence) SessionHelper.get(TransactionCorrespondence.class, transactionCorrespondencePK, TransactionCorrespondence.DATABASE);

        return transactionCorrespondence;
    }
    
    /**
     * Finder by PK.
     * @param transactionCorrespondencePK
     * @return TransactionCorrespondence object
     */
    public static final TransactionCorrespondence findByPK(Long transactionCorrespondencePK)
    {
        return (TransactionCorrespondence) SessionHelper.get(TransactionCorrespondence.class, transactionCorrespondencePK, TransactionCorrespondence.DATABASE);
    }

    /**
     * Get the TransactionCorrespondence table rows for the TransactionPriorityKey passed in
     * @param transactionPriorityPK
     * @return
     */
    public static final Set findByTransactionPriorityPK(Long transactionPriorityPK)
    {
        TransactionCorrespondenceVO[] transactionCorrespondenceVOs = new TransactionCorrespondenceDAO().findByTransactionPriorityPK(transactionPriorityPK.longValue());

        Set transactionCorrespondenceArray = null;
        if (transactionCorrespondenceVOs != null)
        {
            transactionCorrespondenceArray = new HashSet();
            for (int i = 0; i < transactionCorrespondenceVOs.length; i++)
            {
                transactionCorrespondenceArray.add(new TransactionCorrespondence(transactionCorrespondenceVOs[i]));
            }
        }

        return transactionCorrespondenceArray;
    }

    /**
     * Originally from TransactionCorrespondenceDAO.findByTransactionType
     * @param transactionType
     * @return
     */

    public static TransactionCorrespondence[] findBy_TransactionType(String transactionType)
    {
        String hql = "select transactionCorrespondence from TransactionCorrespondence transactionCorrespondence " +
                     " where transactionCorrespondence.TransactionTypeCT = :transactionType";

        Map params = new HashMap();
        params.put("transactionType", transactionType);

        List results = SessionHelper.executeHQL(hql, params, TransactionCorrespondence.DATABASE);

        return (TransactionCorrespondence[]) results.toArray(new TransactionCorrespondence[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, TransactionCorrespondence.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, TransactionCorrespondence.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return TransactionCorrespondence.DATABASE;
    }
}
