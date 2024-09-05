package agent;

import client.*;

import edit.common.vo.*;

import role.*;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 12, 2004
 * Time: 10:40:38 AM
 * To change this template use Options | File Templates.
 */
public class HierarchyReportEntry
{
    private HierarchyReportEntryVO hierarchyReportEntryVO;
    private List childReportEntries = null;
    private HierarchyReportEntry parentEntry;
    private PlacedAgent placedAgent;

    public HierarchyReportEntry(PlacedAgent placedAgent)
    {
        this.hierarchyReportEntryVO = new HierarchyReportEntryVO();

        childReportEntries = new ArrayList();

        this.populateHierarchyReportEntry(placedAgent);
    }

    public String getAgentId()
    {
        return hierarchyReportEntryVO.getAgentId();
    }
     //-- java.lang.String getAgentId() 

    public String getAgentName()
    {
        return hierarchyReportEntryVO.getAgentName();
    }
     //-- java.lang.String getAgentName() 

    public void addChildEntry(HierarchyReportEntry childReportEntry)
    {
        childReportEntry.setParentEntry(this);

        this.childReportEntries.add(childReportEntry);
    }

    public void setParentEntry(HierarchyReportEntry parentEntry)
    {
        this.parentEntry = parentEntry;
    }

    public HierarchyReportEntry getParentEntry()
    {
        return this.parentEntry;
    }

    public HierarchyReportEntry[] getChildEntries()
    {
        HierarchyReportEntry[] childEntries = (HierarchyReportEntry[]) childReportEntries.toArray(new HierarchyReportEntry[childReportEntries.size()]);

        return childEntries;
    }

    public HierarchyReportEntryVO getVO()
    {
        return this.hierarchyReportEntryVO;
    }

    public HierarchyReportEntryVO getComposedVO()
    {
        HierarchyReportEntryVO composedVO = treeWalkVO(this);

        return composedVO;
    }

    private HierarchyReportEntryVO treeWalkVO(HierarchyReportEntry hierarchyReportEntry)
    {
        HierarchyReportEntryVO reportEntryVO = hierarchyReportEntry.getVO();

        HierarchyReportEntry[] childEntries = hierarchyReportEntry.getChildEntries();

        for (int i = 0; i < childEntries.length; i++)
        {
            HierarchyReportEntryVO childEntryVO = treeWalkVO(childEntries[i]);

            reportEntryVO.addHierarchyReportEntryVO(childEntryVO);
        }

        return reportEntryVO;
    }

    public HierarchyReportEntry[] linearizeReportTree()
    {
        List storage = new ArrayList();

        linearizeReportTree(this, storage);

        return (HierarchyReportEntry[]) storage.toArray(new HierarchyReportEntry[storage.size()]);
    }

    private void linearizeReportTree(HierarchyReportEntry hierarchyReportEntry, List storage)
    {
        storage.add(hierarchyReportEntry);

        HierarchyReportEntry[] childEntries = hierarchyReportEntry.getChildEntries();

        for (int i = 0; i < childEntries.length; i++)
        {
            linearizeReportTree(childEntries[i], storage);
        }
    }

    public void setHasError(boolean hasError)
    {
        hierarchyReportEntryVO.setHasError(hasError);
    }

    public boolean getHasError()
    {
        return hierarchyReportEntryVO.getHasError();
    }

    private void populateHierarchyReportEntry(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;

        Agent agent = placedAgent.getAgentContract().getAgent();

        ClientDetail clientDetail = placedAgent.getClientRole().getClientDetail();

        String clientName = clientDetail.getName();

        String agentId = agent.getAgentNumber();

        hierarchyReportEntryVO.setAgentId(agentId);
        hierarchyReportEntryVO.setAgentName(clientName);
        hierarchyReportEntryVO.setPlacedAgentVO((PlacedAgentVO) placedAgent.getVO());
    }

    /**
     * Getter.
     * @return
     */
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }
}
