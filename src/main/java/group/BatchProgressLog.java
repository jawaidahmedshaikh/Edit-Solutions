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

import contract.FilteredRequirement;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

import org.dom4j.*;

public class BatchProgressLog extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long batchProgressLogPK;
    
    private Long filteredRequirementFK;

    private int daysAdded;
    private String description;

    //  Parents
    private BatchContractSetup batchContractSetup;
    private Long batchContractSetupFK;
    private FilteredRequirement filteredRequirement;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Constructor
     */
    public BatchProgressLog()
    {
    }

    public Long getBatchProgressLogPK()
    {
        return batchProgressLogPK;
    }

    public void setBatchProgressLogPK(Long batchProgressLogPK)
    {
        this.batchProgressLogPK = batchProgressLogPK;
    }

    public int getDaysAdded()
    {
        return daysAdded;
    }

    public void setDaysAdded(int daysAdded)
    {
        this.daysAdded = daysAdded;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
     * Removes this object from the database.  Sets the parent references to null first.
     */ 
    public void remove()
    {
        this.setBatchContractSetup(null);

        SessionHelper.delete(this, BatchProgressLog.DATABASE);
    }

    public String getDatabase()
    {
        return BatchProgressLog.DATABASE;
    }

    /**
     * Finds the BatchProgressLog with the given primary key
     *
     * @param batchProgressLogPK
     *
     * @return
     */
    public static BatchProgressLog findByPK(Long batchProgressLogPK)
    {
        String hql = " select batchProgressLog from BatchProgressLog batchProgressLog" +
                     " where batchProgressLog.BatchProgressLogPK = :batchProgressLogPK";

        EDITMap params = new EDITMap();

        params.put("batchProgressLogPK", batchProgressLogPK);

        List<BatchProgressLog> results = SessionHelper.executeHQL(hql, params, BatchProgressLog.DATABASE);

        if (results.size() > 0)
        {
            return (BatchProgressLog) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all BatchProgressLogs
     *
     * @return array of BatchProgressLog objects      ;
     */
    public static BatchProgressLog[] findAll()
    {
        String hql = " from BatchProgressLog batchProgressLog";

        EDITMap params = new EDITMap();

        List<BatchProgressLog> results = SessionHelper.executeHQL(hql, params, BatchProgressLog.DATABASE);

        return results.toArray(new BatchProgressLog[results.size()]);
    }

    /**
     * Finds all BatchProgressLogs that have a given BatchContractSetupFK
     *
     * @param batchContractSetupPK
     *
     * @return  array of BatchProgressLog objects
     */
    public static BatchProgressLog[] findByBatchContractSetupPK(Long batchContractSetupPK)
    {
        String hql = " select batchProgressLog from BatchProgressLog batchProgressLog" +
                     " where batchProgressLog.BatchContractSetupFK = :batchContractSetupPK";

        EDITMap params = new EDITMap();

        params.put("batchContractSetupPK", batchContractSetupPK);

        List<BatchProgressLog> results = SessionHelper.executeHQL(hql, params, BatchProgressLog.DATABASE);

        return results.toArray(new BatchProgressLog[results.size()]);
    }

    public void setFilteredRequirement(FilteredRequirement filteredRequirement)
    {
        this.filteredRequirement = filteredRequirement;
    }

    public FilteredRequirement getFilteredRequirement()
    {
        return filteredRequirement;
    }

    public void setFilteredRequirementFK(Long filteredRequirementFK)
    {
        this.filteredRequirementFK = filteredRequirementFK;
    }

    public Long getFilteredRequirementFK()
    {
        return filteredRequirementFK;
    }
}
