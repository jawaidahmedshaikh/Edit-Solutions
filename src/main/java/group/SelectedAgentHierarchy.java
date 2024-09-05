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

import edit.services.db.hibernate.*;
import edit.common.*;
import contract.*;
import org.dom4j.*;

import java.util.*;

public class SelectedAgentHierarchy extends HibernateEntity
{
    private Long selectedAgentHierarchyPK;

    //  Parents
    private Long batchContractSetupFK;
    private Long agentHierarchyFK;
    BatchContractSetup batchContractSetup;
    AgentHierarchy agentHierarchy;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public SelectedAgentHierarchy()
    {

    }

    public Long getSelectedAgentHierarchyPK()
    {
        return selectedAgentHierarchyPK;
    }

    public void setSelectedAgentHierarchyPK(Long selectedAgentHierarchyPK)
    {
        this.selectedAgentHierarchyPK = selectedAgentHierarchyPK;
    }

    public Long getBatchContractSetupFK()
    {
        return batchContractSetupFK;
    }

    public void setBatchContractSetupFK(Long batchContractSetupFK)
    {
        this.batchContractSetupFK = batchContractSetupFK;
    }

    public Long getAgentHierarchyFK()
    {
        return agentHierarchyFK;
    }

    public void setAgentHierarchyFK(Long agentHierarchyFK)
    {
        this.agentHierarchyFK = agentHierarchyFK;
    }

    public BatchContractSetup getBatchContractSetup()
    {
        return batchContractSetup;
    }

    public void setBatchContractSetup(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetup = batchContractSetup;
    }

    public AgentHierarchy getAgentHierarchy()
    {
        return agentHierarchy;
    }

    public void setAgentHierarchy(AgentHierarchy agentHierarchy)
    {
        this.agentHierarchy = agentHierarchy;
    }

    /**
     * Removes this object from the database.  Sets the parent references to null first.  Note: the BatchContractSetup
     * is the true parent, the AgentHierarchy is an association.
     */
    public void remove()
    {
        this.setBatchContractSetup(null);
        this.setAgentHierarchy(null);

        SessionHelper.delete(this, SelectedAgentHierarchy.DATABASE);
    }

    public String getDatabase()
    {
        return SelectedAgentHierarchy.DATABASE;
    }

    /**
     * Finds all SelectedAgentHierarchy that have a given BatchContractSetupFK
     *
     * @param batchContractSetupPK
     *
     * @return  array of SelectedAgentHierarchy objects
     */
    public static SelectedAgentHierarchy[] findByBatchContractSetupPK(Long batchContractSetupPK)
    {
        String hql = " select selectedAgentHierarchy from SelectedAgentHierarchy selectedAgentHierarchy" +
                     " where selectedAgentHierarchy.BatchContractSetupFK = :batchContractSetupPK";

        EDITMap params = new EDITMap();

        params.put("batchContractSetupPK", batchContractSetupPK);

        List<SelectedAgentHierarchy> results = SessionHelper.executeHQL(hql, params, SelectedAgentHierarchy.DATABASE);

        return results.toArray(new SelectedAgentHierarchy[results.size()]);
    }

    public static SelectedAgentHierarchy[] findByAgentHierarchyFK(Long AgentHierarchyPK)
    {
        SelectedAgentHierarchy[] selectedAgentHierarchies = null;

        String hql = " select selectedAgentHierarchy from SelectedAgentHierarchy selectedAgentHierarchy" +
                     " where selectedAgentHierarchy.AgentHierarchyFK = :AgentHierarchyPK";
                    

        EDITMap params = new EDITMap();

        params.put("AgentHierarchyPK", AgentHierarchyPK);

        List<SelectedAgentHierarchy> results = SessionHelper.executeHQL(hql, params, SelectedAgentHierarchy.DATABASE);

        if(!results.isEmpty())
        {
            selectedAgentHierarchies = results.toArray(new SelectedAgentHierarchy[results.size()]);
        }
        return selectedAgentHierarchies;
    }

}
