package engine.sp.custom.document;

import edit.common.EDITMap;

import java.util.*;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import group.*;
import contract.*;
import engine.common.*;
import role.*;

/**
 * PRASE Document that contains the BatchContractSetup, the AgentHierarchy objects for the BatchContractSetup (i.e.
 * selected agent hierarchies) and their AgentHierarchyAllocations, the Enrollment and EnrollmentLeadServiceAgent
 * objects, and their ClientRole
 * <P>
 * The structure is as follows:
 *
 * <BatchContractSetupDocVO>
 *  <BatchContractSetupVO>
 *             ...
 *      <AgentHierarchyVO>                  (0 or more)
 *             ...
 *          <AgentHierarchyAllocationVO>    (0 or more)
 *                      ...
 *          </AgentHierarchyAllocationVO>
 *      </AgentHierarchyVO>
 *      <EnrollmentVO>
 *             ...
 *          <EnrollmentLeadServiceAgentVO>  (0 or more)
 *                  ...
 *              <ClientRoleVO>
 *                      ...
 *              </ClientRoleVO>
 *          </EnrollmentLeadServiceAgentVO>
 *      </EnrollmentVO>
 *  </BatchContractSetupVO>
 * </BatchContractSetupDocVO>
 */
public class BatchContractSetupDocument extends PRASEDocBuilder
{
    /**
     * The driving PK of the BatchContractSetup.
     */
    private Long entityPK;

    /**
     * The Working Storage-supplied parameter which drives this document.
     */
    public static final String BUILDING_PARAMETER_NAME_BATCHCONTRACTSETUPPK = "BatchContractSetupPK";

    /**
     * The parameter names to be extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_BATCHCONTRACTSETUPPK};

    public static final String ROOT_ELEMENT_NAME = "BatchContractSetupDocVO";


    public BatchContractSetupDocument()
    {
    }

    /**
     * Constructor that must be provided for Activatedocument instruction.
     *
     * @param buildingParams
     *
     * @see #BUILDING_PARAMETER_NAME_BATCHCONTRACTSETUPPK
     */
    public BatchContractSetupDocument(Map<String, String> buildingParams)
    {
        super(buildingParams);

        String entityPKStr = buildingParams.get(BUILDING_PARAMETER_NAME_BATCHCONTRACTSETUPPK);
        
        // Watch for #NULL should it come in.
        if (entityPKStr.equals(Constants.ScriptKeyword.NULL))
        {
            initEmptyDocument();
        }
        else
        {
            this.entityPK = new Long(entityPKStr);
        }
    }

    /**
     * Constructor that takes a BatchContractSetup entity.  This is used for "pre-building" the document before the 
     * Activatedocument instruction is called.  
     *
     * @param batchContractSetup        the BatchContractSetup object.  The object and its necessary children
     *                                  are "converted" to Elements and added to the document
     */
    public BatchContractSetupDocument(BatchContractSetup batchContractSetup)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_BATCHCONTRACTSETUPPK, "0"));

        buildDocument(batchContractSetup);
    }

    /**
     * Builds the document from the Activatedocument instruction
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            //  IMPLEMENTATION UNDEFINED!  At this time, the document will always be pre-built
            //  The following lines are just here as an example of what it might do if not pre-built
//            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(getEntityPK());
//            
//            buildDocument(batchContractSetup);
        }
    }

    /**
     * Builds the complete document from the entity object
     * 
     * @param batchContractSetup                    entity object to use for building document
     */
    private void buildDocument(BatchContractSetup batchContractSetup)
    {
        Element batchContractGroupDocVOElement = initEmptyDocument();

        buildBatchContractSetupElement(batchContractSetup, batchContractGroupDocVOElement);
    }

    /**
     * Root element name
     *
     * @return
     */
    public String getRootElementName()
    {
        return ROOT_ELEMENT_NAME;
    }

    /**
     * @return
     *
     * @see #entityPK
     */
    public Long getEntityPK()
    {
        return entityPK;
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }

    /**
     * An "empty" document is the default starting point for any document.
     * The document may be populated with contents, but it may not as well. For
     * example, the building parameter PK may be "#NULL". That flags a business
     * decision to build the empty document (only).
     *
     * @return the default empty document
     */
    private Element initEmptyDocument()
    {
        Element batchContractSetupDocVOElement = new DefaultElement(getRootElementName());

        setRootElement(batchContractSetupDocVOElement);

        setDocumentBuilt(true);

        return batchContractSetupDocVOElement;
    }

    /**
     * Builds the BatchContractSetup Element and it necessary children and adds it tot he root element
     *
     * @param batchContractSetup                            entity object to create Element out of
     * @param batchContractGroupDocVOElement                root element of the document
     */
    private void buildBatchContractSetupElement(BatchContractSetup batchContractSetup, Element batchContractGroupDocVOElement)
    {
        Element batchContractSetupElement = batchContractSetup.getAsElement();

        batchContractGroupDocVOElement.add(batchContractSetupElement);

        buildAgentHierarchyElements(batchContractSetup.getActualAgentHierarchies(), batchContractSetupElement);

        buildEnrollmentElement(batchContractSetup, batchContractSetupElement);
    }

    /**
     * Builds all of the AgentHierarchy Elements and their necessary children and adds them to the batchContractSetupElement
     *
     * @param agentHierarchies
     * @param batchContractSetupElement
     */
    private void buildAgentHierarchyElements(Set<AgentHierarchy> agentHierarchies, Element batchContractSetupElement)
    {
       for (Iterator iterator = agentHierarchies.iterator(); iterator.hasNext();)
       {
           AgentHierarchy agentHierarchy = (AgentHierarchy) iterator.next();

           buildAgentHierarchyElement(agentHierarchy, batchContractSetupElement);
       }
    }

    /**
     * Builds an individual AgentHierarchy Element and its allocations and adds them to the batchContractSetupElement
     *
     * @param agentHierarchy
     * @param batchContractSetupElement
     */
    private void buildAgentHierarchyElement(AgentHierarchy agentHierarchy, Element batchContractSetupElement)
    {
        Element agentHierarchyElement = agentHierarchy.getAsElement();

        buildAgentHiearchyAllocations(agentHierarchy.getAgentHierarchyAllocations(), agentHierarchyElement);

        batchContractSetupElement.add(agentHierarchyElement);
    }

    /**
     * Builds all of the AgentHierarchyAllocation Elements for a single AgentHierarchy and attaches them tot he agentHierarchyElement
     *
     * @param agentHierarchyAllocations
     * @param agentHierarchyElement
     */
    private void buildAgentHiearchyAllocations(Set<AgentHierarchyAllocation> agentHierarchyAllocations, Element agentHierarchyElement)
    {
        for (Iterator iterator = agentHierarchyAllocations.iterator(); iterator.hasNext();)
        {
            AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) iterator.next();

            buildAgentHierarchyAllocationElement(agentHierarchyAllocation, agentHierarchyElement);
        }
    }

    /**
     * Builds a single AgentHierarchyAllocation Element and attaches it to the agentHierarchyElement
     *
     * @param agentHierarchyAllocation
     * @param agentHierarchyElement
     */
    private void buildAgentHierarchyAllocationElement(AgentHierarchyAllocation agentHierarchyAllocation, Element agentHierarchyElement)
    {
        Element agentHierarchyAllocationElement = agentHierarchyAllocation.getAsElement();

        agentHierarchyElement.add(agentHierarchyAllocationElement);
    }

    /**
     * Builds a single Enrollment Element (with its children) and attaches it to the batchContractSetupElement
     *
     * @param batchContractSetup
     * @param batchContractSetupElement
     */
    private void buildEnrollmentElement(BatchContractSetup batchContractSetup, Element batchContractSetupElement)
    {
        //  Get the Enrollment from the BatchContractSetup.  The BatchContractSetup has not been saved yet but the
        //  Enrollment does exist on the database.
        Enrollment enrollment = batchContractSetup.getEnrollment();

        if (enrollment != null)
        {
            Element enrollmentElement = enrollment.getAsElement();

            buildEnrollmentLeadServiceAgentElement(enrollment, enrollmentElement);

            batchContractSetupElement.add(enrollmentElement);
        }
    }

    /**
     * Builds all of the EnrollmentLeadServiceAgent Elements for a given Enrollment and attaches them to the
     * enrollmentElement.
     *
     * @param enrollment
     * @param enrollmentElement
     */
    private void buildEnrollmentLeadServiceAgentElement(Enrollment enrollment, Element enrollmentElement)
    {
        //  Get the EnrollmentLeadServiceAgents from the database.  They
        EnrollmentLeadServiceAgent[] enrollmentLeadServiceAgents = EnrollmentLeadServiceAgent.findByEnrollmentFK(enrollment.getEnrollmentPK());

        for (int i = 0; i < enrollmentLeadServiceAgents.length; i++)
        {
            EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = enrollmentLeadServiceAgents[i];

            Element enrollmentLeadServiceAgentElement = enrollmentLeadServiceAgent.getAsElement();

            buildClientRoleElement(enrollmentLeadServiceAgent, enrollmentLeadServiceAgentElement);

            enrollmentElement.add(enrollmentLeadServiceAgentElement);
        }
    }

    /**
     * Builds a single ClientRole Element for a given EnrollmentLeadServiceAgent and attaches it to the
     * enrollmentLeadServiceAgentElement.
     *
     * @param enrollmentLeadServiceAgent
     * @param enrollmentLeadServiceAgentElement
     */
    private void buildClientRoleElement(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent, Element enrollmentLeadServiceAgentElement)
    {
        ClientRole clientRole = ClientRole.findByPK(enrollmentLeadServiceAgent.getClientRoleFK());

        if (clientRole != null)
        {
            Element clientRoleElement = clientRole.getAsElement();

            enrollmentLeadServiceAgentElement.add(clientRoleElement);
        }
    }
}
