/*
 * User: sdorman
 * Date: Jun 19, 2007
 * Time: 12:45:46 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package group;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

import contract.*;
import org.dom4j.*;

public class BatchProductLog extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long batchProductLogPK;

    //  Parents
    private BatchContractSetup batchContractSetup;
    private FilteredProduct filteredProduct;
    private Long batchContractSetupFK;
    private Long filteredProductFK;
    
    private int numberOfAppsReceived;
    

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Constructor
     */
    public BatchProductLog()
    {
    }

    public Long getBatchProductLogPK()
    {
        return batchProductLogPK;
    }

    public void setBatchProductLogPK(Long batchProductLogPK)
    {
        this.batchProductLogPK = batchProductLogPK;
    }

    public FilteredProduct getFilteredProduct()
    {
        return filteredProduct;
    }

    public void setFilteredProduct(FilteredProduct filteredProduct)
    {
        this.filteredProduct = filteredProduct;
    }

    public Long getFilteredProductFK()
    {
        return this.filteredProductFK;
    }

    public void setFilteredProductFK(Long filteredProductFK)
    {
        this.filteredProductFK = filteredProductFK;
    }

    public BatchContractSetup getBatchContractSetup()
    {
        return batchContractSetup;
    }

    public void setBatchContractSetup(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetup = batchContractSetup;
    }

    public Long getBatchContractSetupFK()
    {
        return batchContractSetupFK;
    }

    public void setBatchContractSetupFK(Long batchContractSetupFK)
    {
        this.batchContractSetupFK = batchContractSetupFK;
    }

    /**
     * Removes this object from the database.  Sets the parent references to null first.  Note: the BatchContractSetup
     * is the true parent, the FilteredProduct is an association.
     */
    public void remove()
    {
        this.setBatchContractSetup(null);
        this.setFilteredProduct(null);

        SessionHelper.delete(this, BatchProductLog.DATABASE);
    }

    public String getDatabase()
    {
        return BatchProductLog.DATABASE;
    }

    /**
     * Finds the BatchProductLog with the given primary key
     *
     * @param batchProductLogPK
     *
     * @return
     */
    public static BatchProductLog findByPK(Long batchProductLogPK)
    {
        String hql = " select batchProductLog from BatchProductLog batchProductLog" +
                     " where batchProductLog.BatchProductLogPK = :batchProductLogPK";

        EDITMap params = new EDITMap();

        params.put("batchProductLogPK", batchProductLogPK);

        List<BatchProductLog> results = SessionHelper.executeHQL(hql, params, BatchProductLog.DATABASE);

        if (results.size() > 0)
        {
            return (BatchProductLog) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all BatchProductLogs
     *
     * @return array of BatchProductLog objects      ;
     */
    public static BatchProductLog[] findAll()
    {
        String hql = " from BatchProductLog batchProductLog";

        EDITMap params = new EDITMap();

        List<BatchProductLog> results = SessionHelper.executeHQL(hql, params, BatchProductLog.DATABASE);

        return results.toArray(new BatchProductLog[results.size()]);
    }

    /**
     * Finds all BatchProductLogs that have a given BatchContractSetupFK
     *
     * @param batchContractSetupPK
     *
     * @return  array of BatchProductLog objects
     */
    public static BatchProductLog[] findByBatchContractSetupPK(Long batchContractSetupPK)
    {
        String hql = " select batchProductLog from BatchProductLog batchProductLog" +
                     " where batchProductLog.BatchContractSetupFK = :batchContractSetupPK";

        EDITMap params = new EDITMap();

        params.put("batchContractSetupPK", batchContractSetupPK);

        List<BatchProductLog> results = SessionHelper.executeHQL(hql, params, BatchProductLog.DATABASE);

        return results.toArray(new BatchProductLog[results.size()]);
    }
    
    public int getNumberOfAppsReceived()
    {
        return numberOfAppsReceived;
    }

    public void setNumberOfAppsReceived(int numberOfAppsReceived)
    {
        this.numberOfAppsReceived = numberOfAppsReceived;
    }    

}
