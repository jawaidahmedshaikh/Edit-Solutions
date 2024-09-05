package agent;

import edit.services.db.hibernate.*;
import edit.common.*;

import java.util.*;

import fission.utility.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 6, 2005
 * Time: 4:40:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateHierarchy
{
    public static final Integer REPORT_ASSOCIATIONS = new Integer(0);

    public static final Integer REPORT_REPORT_TOS = new Integer(1);

    public static final Integer REPORT_BOUNDARIES = new Integer(2);

    public static final Integer REPORT_LEVELS = new Integer(3);

    private Map hierarchyReports = new HashMap();

    private long startTimeMillis;

    private long stopTimeMillis;

    List results = null;

    /**
     * Start Time.
     */
    public void tagStartTime()
    {
        startTimeMillis = System.currentTimeMillis();
    }

    public void tagStopTime()
    {
        stopTimeMillis = System.currentTimeMillis();
    }

    /**
     * Runs 4 validations processes to verify the integrity of the hierarchy.
     */
    public ValidateHierarchyReport run(Integer reportId)
    {
        results = getPlacedAgents();

        if (reportId.equals(REPORT_ASSOCIATIONS))
        {
            validateAssociations(results);
        }

        else if (reportId.equals(REPORT_REPORT_TOS))
        {
            validateReportTos(results);
        }

        else if (reportId.equals(REPORT_BOUNDARIES))
        {
            validateBoundaries();
        }

        else if (reportId.equals(REPORT_LEVELS))
        {
            validateHierarchyLevels(results);
        }



        return (ValidateHierarchyReport) getHierarchyReports().get(reportId);
    }

    /**
     *
     * @return
     */
    private List getPlacedAgents()
    {
        String hql = "from PlacedAgent";

        if (results == null)
        {
            results = SessionHelper.executeHQL(hql, null, SessionHelper.EDITSOLUTIONS);
        }

        return results;
    }

    /**
     * The total report time in minutes.
     * @return
     */
    public String getTotalReportTimeMins()
    {
        double totalTime = stopTimeMillis - startTimeMillis; // Millis

        totalTime = totalTime/60.0; // Seconds

        totalTime = totalTime/60.0; // Minutes

        String totalTimeStr = Util.formatDecimal("0.##", new EDITBigDecimal(String.valueOf(totalTime)));

        return totalTimeStr;
    }

    /**
     * Every branch (starting from a lowest-level PlacedAgent) should have ordered hierarchy levels without gaps.
     * @param results
     */
    private void validateHierarchyLevels(List results)
    {
        String description = "Hierarchy Levels Must Be Sequencial And Step By One";

        ValidateHierarchyReport hierarchyReport = new ValidateHierarchyReport("Validate Hierarchy Levels", description, REPORT_LEVELS);

        for (Iterator iterator = results.iterator(); iterator.hasNext();)
        {
            PlacedAgent currentPlacedAgent = (PlacedAgent) iterator.next();

            PlacedAgentBranch currentPlacedAgentBranch = new PlacedAgentBranch(currentPlacedAgent);

            currentPlacedAgentBranch.sort(PlacedAgentBranch.ASCENDING);

            PlacedAgent[] branchElements = currentPlacedAgentBranch.getPlacedAgents();

            PlacedAgent previousBranchElement = null;

            for (int i = 0; i < branchElements.length; i++)
            {
                PlacedAgent currentBranchElement = branchElements[i];

                int currentHierarchyLevel = currentBranchElement.getHierarchyLevel();

                if (previousBranchElement == null)
                {
                    previousBranchElement = currentBranchElement;
                }
                else
                {
                    int previousHierarchyLevel = previousBranchElement.getHierarchyLevel();

                    if ((currentHierarchyLevel - previousHierarchyLevel) != 1)
                    {
                        hierarchyReport.addReportEntry("Level Skipped (1 of 2)", previousBranchElement);

                        hierarchyReport.addReportEntry("Level Skipped (2 of 2)", currentBranchElement);
                    }

                    previousBranchElement = currentBranchElement;
                }
            }
        }

        addHierarchyReport(hierarchyReport);
    }

    /**
     * The immediate children of its parent must have left and right boundaries that fall within the range of their
     * parent's left and right boundaries. Additionally, the left-most child boundary should equal the parent's left
     * boundary, and the right-most child's boundary should equal the parent's right boundary.
     */
    private void validateBoundaries()
    {
        String description = "PlacedAgents At A HierarchyLevel of N Cannot Have Overlapping Boundaries";

        ValidateHierarchyReport hierarchyReport = new ValidateHierarchyReport("Validate Left/Right Boundaries", description, REPORT_BOUNDARIES);

        List hierarchyLevels = SessionHelper.executeHQL("select distinct placedAgent.HierarchyLevel from PlacedAgent placedAgent order by placedAgent.HierarchyLevel asc", null, SessionHelper.EDITSOLUTIONS);

        for (Iterator iterator = hierarchyLevels.iterator(); iterator.hasNext();)
        {
            Integer currentHierarchyLevel = (Integer) iterator.next();

            Map params = new HashMap();

            params.put("hierarchyLevel", currentHierarchyLevel);

            List placedAgents = SessionHelper.executeHQL("from PlacedAgent placedAgent where placedAgent.HierarchyLevel = :hierarchyLevel", params, SessionHelper.EDITSOLUTIONS);

            sortChildren(placedAgents);

            PlacedAgent previousPlacedAgent = null;

            for (Iterator iterator1 = placedAgents.iterator(); iterator1.hasNext();)
            {
                PlacedAgent currentPlacedAgent = (PlacedAgent) iterator1.next();

                long currentChildLeftBoundary = currentPlacedAgent.getLeftBoundary();

                if (previousPlacedAgent == null)
                {
                    previousPlacedAgent = currentPlacedAgent;
                }
                else
                {
                    long previousLeftBoundary = previousPlacedAgent.getLeftBoundary();

                    long previousRightBoundary = previousPlacedAgent.getRightBoundary();

                    if (!(previousLeftBoundary < currentChildLeftBoundary) || !(previousRightBoundary <= currentChildLeftBoundary))
                    {
                        hierarchyReport.addReportEntry("Overlapping Boundaries (1 of 2)", previousPlacedAgent);

                        hierarchyReport.addReportEntry("Overlapping Boundaries (2 of 2)", currentPlacedAgent);
                    }

                    previousPlacedAgent = currentPlacedAgent;
                }
            }
        }

        addHierarchyReport(hierarchyReport);
    }

    /**
     * Sorts the specified child PlacedAgents by left-boundary increasing.
     * @param childPlacedAgents
     */
    private void sortChildren(List childPlacedAgents)
    {
        Collections.sort(childPlacedAgents,
            new Comparator()
            {
                public int compare(Object object, Object object1)
                {
                    PlacedAgent placedAgent1 = (PlacedAgent) object;

                    PlacedAgent placedAgent2 = (PlacedAgent) object1;

                    Long leftBoundary1 = new Long(placedAgent1.getLeftBoundary());

                    Long leftBoundary2 = new Long(placedAgent2.getLeftBoundary());

                    return leftBoundary1.compareTo(leftBoundary2);
                }
            });
    }

    /**
     * Every PlacedAgent should be associated with an AgentContract and a CommissionProfile.
     */
    private void validateAssociations(List results)
    {
        String reportDescription = "Every PlacedAgent Needs An Associated AgentContract and CommissionProfile";

        ValidateHierarchyReport hierarchyReport = new ValidateHierarchyReport("Validate Entity Associations (AgentContract/CommissionProfile)", reportDescription, REPORT_ASSOCIATIONS);

        for (Iterator iterator = results.iterator(); iterator.hasNext();)
        {
            PlacedAgent placedAgent = (PlacedAgent) iterator.next();

            AgentContract agentContract = placedAgent.getAgentContract();

            CommissionProfile commissionProfile = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();

            if (agentContract == null)
            {
                hierarchyReport.addReportEntry("Missing AgentContract", placedAgent);
            }

            if (commissionProfile == null)
            {
                hierarchyReport.addReportEntry("Missing CommissionProfile", placedAgent);
            }
        }

        addHierarchyReport(hierarchyReport);
    }

    /**
     * Every PlacedAgent must have a parent PlacedAgent unless the PlacedAgent is root (HierarchyLevel = 0)
     */
    private void validateReportTos(List results)
    {
        String description = "Every PlacedAgent Requires a Report-To-Parent With The Exception Of Root";

        ValidateHierarchyReport hierarchyReport = new ValidateHierarchyReport("Validate Child Has Parent", description, REPORT_REPORT_TOS);

        for (Iterator iterator = results.iterator(); iterator.hasNext();)
        {
            PlacedAgent placedAgent = (PlacedAgent) iterator.next();

            PlacedAgent parentPlacedAgent = placedAgent.get_Parent();

            if (parentPlacedAgent == null)
            {
                int hierarchyLevel = placedAgent.getHierarchyLevel();

                if (hierarchyLevel != 0)
                {
                    hierarchyReport.addReportEntry("No Parent", placedAgent);
                }
            }
        }

        addHierarchyReport(hierarchyReport);
    }

    private void output(String message, PlacedAgent placedAgent)
    {
        Long placedAgentPK = null;

        Integer hierarchyLevel = null;

        Long leftBoundary = null;

        Long rightBoundary = null;

        String agentNumber = null;

        if (placedAgent != null)
        {
            placedAgentPK = placedAgent.getPlacedAgentPK();

            hierarchyLevel = new Integer(placedAgent.getHierarchyLevel());

            leftBoundary = new Long(placedAgent.getLeftBoundary());

            rightBoundary = new Long(placedAgent.getRightBoundary());

            AgentContract agentContract = placedAgent.getAgentContract();

            if (agentContract != null)
            {
                agentNumber = agentContract.getAgent().getAgentNumber();
            }
            else
            {
                agentNumber = "N/A";
            }
        }

        message = "[" + message + "]";

        agentNumber = (agentNumber != null) ? "[#: " + agentNumber + "]" : "";

        String placedAgentPKString = (placedAgentPK != null) ? "[pk: " + placedAgentPK.toString() + "]" : "";

        String hierarchyLevelString = (hierarchyLevel != null) ? "[level: " + hierarchyLevel.toString() + "]" : "";

        String leftBoundaryString = (leftBoundary != null) ? "[left: " + leftBoundary.toString() + "]" : "";

        String rightBoundaryString = (rightBoundary != null) ? "[right: " + rightBoundary.toString() + "]" : "";

        System.out.println(message + "\t" + agentNumber + "\t" + hierarchyLevelString + "\t" + leftBoundaryString + "\t" + rightBoundaryString + "\t" + placedAgentPKString);
    }

    /**
     * Adder.
     * @param validateHierarchyReport
     */
    private void addHierarchyReport(ValidateHierarchyReport validateHierarchyReport)
    {
        this.hierarchyReports.put(validateHierarchyReport.getReportIdentifier(), validateHierarchyReport);
    }

    /**
     * Getter.
     * @return
     */
    public Map getHierarchyReports()
    {
        return this.hierarchyReports;
    }
}
