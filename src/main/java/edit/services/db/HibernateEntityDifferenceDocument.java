package edit.services.db;

import edit.services.db.hibernate.HibernateEntityDifference;

import fission.utility.DOMUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

/**
 * Builds the Document that represents the list of Hibernate Entity Difference(s).
 * 
 * This class should not be confused with the FooDocuments in PRASE which are
 * designed to be part of the document-building framework in PRASE.
 * 
 * This builder is not business-centric, but part of a generic framework that
 * notifies PRASE of non financial changes.
 */
public class HibernateEntityDifferenceDocument extends DefaultDocument
{
    /**
     * The list of Hibernate Entity differences represented by this document.
     */
    private List<HibernateEntityDifference> hibernateEntityDifferences;
    
    /**
     * Dictates that this Document will be sent to PRASE
     * before its contents have been committed to the DB. PRASE
     * has the ability to veto this Document preventing any changes
     * from being committed.
     */
    public static String PHASE_PRE_COMMIT = "PreCommit";
    
    /**
     * Dictates that this Document will be sent to PRASE after its
     * contents have been committed to the DB. PRASE no longer has
     * the ability to veto it, but PRASE can count on its contents
     * having been committed to the DB should PRASE want to 
     * assemble documents from the DB.
     */
    public static String PHASE_POST_COMMIT = "PostCommit";
    
    /**
     * The root element name of this Document.
     */
    public static String ROOT_ELEMENT_NAME = "EntityDifferenceDocVO";

    public HibernateEntityDifferenceDocument(List<HibernateEntityDifference> hibernateEntityDifferences)
    {
        this.hibernateEntityDifferences = hibernateEntityDifferences;
    }

    /**
     * Builds the DOM4J document representing the constructor-specified
     * HibernateEntityDifference(s). The final DOM looks as follows:
     * 
     * <EntityDifferenceDocVO>
     *      <Phase>PreCommit/PostCommit</Phase>	
     *      <EntityDifference>
     *           <EntityName>ClientDetailVO</EntityName>
     *           <EntityPK>
     *              <Name>ClientDetailPK</Name>
     *              <Value>123456789</Value>
     *           </EntityPK>
     *           <EntityFK> // Repeated for every FK found
     *              <Name>ClientRoleFK</Name>
     *              <Value>123456789</Value> '#NULL' if there is no value
     *           </EntityFK>
     *           <Operator>FooOperator</Operator>
     *           <NonFinancialDateTime>6/25/2007 8:59:18 AM</NonFinancialDateTime>
     *           <FieldDifference>
     *               <FieldName>StatusCT</FieldName>
     *               <OldValue>Pending</OldValue>
     *               <NewValue>Active</NewValue>
     *           </FieldDifference>
     *       </EntityDifference>
     *  </EntityDifferenceDocVO>
     *  
     *  @param phase either PRE or POST commit.
     *  @see #PHASE_PRE_COMMIT
     *  @see #PHASE_POST_COMMIT
     */
    public void build(String phase)
    {
        if (!hasContent()) // Don't build it twice - it doesn't change anyway except for the veto and phase values
        {
            Element entityDifferenceDocVOElement = new DefaultElement(ROOT_ELEMENT_NAME);

            Element vetoElement = new DefaultElement("Veto");

            Element phaseElement = new DefaultElement("Phase");

            for (HibernateEntityDifference hibernateEntityDifference: getHibernateEntityDifferences())
            {
                Element hiberanteEntityDifferenceElement = hibernateEntityDifference.getAsElement();
                
                entityDifferenceDocVOElement.add(hiberanteEntityDifferenceElement);
            }

            // Associate the remaining elements...
            entityDifferenceDocVOElement.add(vetoElement);

            entityDifferenceDocVOElement.add(phaseElement);

            setRootElement(entityDifferenceDocVOElement);
        }
        
        setPhase(phase);
        
        clearVeto();
    }
    
    /**
     * Sets the value of the Phase element as specified.
     * @param phase
     */
    private void setPhase(String phase)
    {
        getRootElement().element("Phase").setText(phase);        
    }
    
    /**
     * Makes sure there is no veto value set.
     */
    private void clearVeto()
    {
        getRootElement().element("Veto").setText("");
    }

    /**
     * @see #hibernateEntityDifferences
     * @return
     */
    public List<HibernateEntityDifference> getHibernateEntityDifferences()
    {
        return hibernateEntityDifferences;
    }
}
