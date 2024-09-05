/*
 * User: gfrosti
 * Date: Sep 13, 2004
 * Time: 2:31:55 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */


package agent.ui;

import edit.common.vo.*;
import edit.common.*;

import java.io.*;

import fission.utility.*;
import agent.*;

public class ReportViewHelper
{
    private int agentCount;

    /**
     * Helps normalize the indent level since the root node may have a hierarchy leve > 0/
     */
    private int indentOffset;

    public ReportViewHelper()
    {
        
    }

    /**
     * Launching point to recursively print the specified HierarchyEntry (the root entry) and its children.
     *
     */
    public void treePrintHierarchyReportEntry(HierarchyReportEntry hierarchyEntry, int entryNumber, Writer writer, boolean showFullDetail, long placedAgentPK, boolean includeExpiredAgents)
    {
        int hierarchyLevel = hierarchyEntry.getPlacedAgent().getHierarchyLevel();
        
        indentOffset = hierarchyLevel;
        
        PrintWriter out = new PrintWriter(writer);

        treePrint(hierarchyEntry, out, showFullDetail, placedAgentPK, includeExpiredAgents, --hierarchyLevel);
    }

    /**
     * Recursively prints the specified reportEntry as HTML.
     */
    private void treePrint(HierarchyReportEntry reportEntry, PrintWriter out, boolean showFullDetail, long placedAgentPK, boolean includeExpiredAgents, int recursionLevel)
    {
        recursionLevel++;
        
        if (includeExpiredAgents)
        {
            agentCount++;
            
            out.println(buildRow(reportEntry, showFullDetail, placedAgentPK, includeExpiredAgents, recursionLevel));                
        }
        else if (!reportEntry.getPlacedAgent().isExpired())
        {
            agentCount++;
            
            out.println(buildRow(reportEntry, showFullDetail, placedAgentPK, includeExpiredAgents, recursionLevel));                            
        }
        
        HierarchyReportEntry[] childEntries = reportEntry.getChildEntries();

        childEntries = (HierarchyReportEntry[]) Util.sortObjects(childEntries, new String[]{"getAgentName"});

        for (int i = 0; i < childEntries.length; i++)
        {
            treePrint(childEntries[i], out, showFullDetail, placedAgentPK, includeExpiredAgents, recursionLevel);
        }
    }

    /**
     * Renders the specified parameters  as a <tr> in an HTML table.
     */
    private String buildRow(HierarchyReportEntry reportEntry, boolean showFullDetail, long activePlacedAgentPK, boolean includeExpiredAgents, int recursionLevel)
    {
        String agentId = reportEntry.getAgentId();
        String agentName = reportEntry.getAgentName();
        PlacedAgent placedAgent = reportEntry.getPlacedAgent();

        int hierarchyLevel = placedAgent.getHierarchyLevel();
        long leftBoundary = placedAgent.getLeftBoundary();
        EDITDateTime maintDateTime = placedAgent.getMaintDateTime();
        String modifyingEvent = placedAgent.getModifyingEvent();
        String operator = placedAgent.getOperator();
        Long placedAgentPK = placedAgent.getPlacedAgentPK();
        long rightBoundary = placedAgent.getRightBoundary();
        
        boolean isExpired = placedAgent.isExpired();

        String className = "default";

        if (activePlacedAgentPK != 0)
        {
            if (placedAgentPK.longValue() == activePlacedAgentPK)
            {
                className = "highlighted";
            }
        }
        
        if (isExpired)
        {
            className = className + " disabled";
        }

        String line = "<tr class='" + className + "' id='" + placedAgentPK + "'><td colspan='10' nowrap>";

        line += indent(recursionLevel, reportEntry, includeExpiredAgents);

        if (reportEntry.getHasError())
        {
            line += "<font face='' color='red'>";
        }

        line += "[name: " + agentName + "] ";
        line += "[id:" + agentId + "] ";
        line += "[level: " + hierarchyLevel + "] ";
        if (showFullDetail)
        {
            line += "[leftB: " + leftBoundary + "] ";
            line += "[rightB: " + rightBoundary + "] ";
            line += "[pk: " + placedAgentPK + "] ";
            line += "[operator: " + operator + "] ";
            line += "[maintDate: " + maintDateTime + "] ";
            line += "[modEvent: " + modifyingEvent + "] ";
        }

        if (reportEntry.getHasError())
        {
            line += "</font>";
        }

        line += "</td></tr>";

        return line;
    }

    /**
     * Establishes an index based on the current recursion level.
     */
    private String indent(int recursionLevel, HierarchyReportEntry reportEntry, boolean includeExpiredAgents)
    {
        int indentLevel = recursionLevel - indentOffset;
        
        if (!includeExpiredAgents)
        {
            int numOfExpiredParents = getNumberOfExpiredParents(reportEntry);       
            
            indentLevel -= numOfExpiredParents;
        }
        
        String indent = "";

        for (int i = 0; i < indentLevel; i++)
        {
            if (i <= (indentLevel - 1))
            {
                indent += "|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            }
        }

        return indent;
    }
    
    /**
     * Drills up the parent ancestry incrementing a counter for every
     * ancestor that is expired.
     */
    private int getNumberOfExpiredParents(HierarchyReportEntry hierarchyEntry)
    {
        int numOfExpiredParents = 0;
        
        HierarchyReportEntry currentEntry = hierarchyEntry;
        
        HierarchyReportEntry parentEntry = null;
        
        while ((parentEntry = currentEntry.getParentEntry()) != null)
        {
            if (parentEntry.getPlacedAgent().isExpired())
            {
                numOfExpiredParents++;
            }
            
            currentEntry = parentEntry;
        }
        
        return numOfExpiredParents;
    }

    public int getAgentCount()
    {
        return agentCount;
    }
}
