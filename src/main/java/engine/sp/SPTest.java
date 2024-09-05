package engine.sp;

import edit.common.EDITDate;

import edit.services.db.hibernate.SessionHelper;

import engine.ProductStructure;

import fission.utility.Util;

import org.dom4j.Document;

import engine.sp.*;

import engine.sp.custom.document.PRASEDocBuilder;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


import fission.utility.DOMUtil;

import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.tree.DefaultElement;

/**
 * PRASE has the ability to dynamically build and store test data for
 * future regression testing. This works as follows:
 * 
 * 1.   All PRASEDocuments being activated are stored in their virgin state. In essence,
 *      we are storing the "inputs". The first document activated is considered the "Root/Initial" document. 
 *      As such, its WS entries must also be stored since activation at a later date will need to know the
 *      building parameters.
 * 2.   All SPOutput(s) upon completion of PRASE's run are to be stored so that future test
 *      runs can be compared against the established base line.      
 * 3.   The Driver structure and date/time should be stored since the user will want to know
 *      the kind of scripts the test refects.
 *      
 * The above are automated. The final tests will be available online so that the user can add comments, or
 * copy the test to create additional tests.
 * 
 */
public class SPTest
{
    /**
     * The list of all PRASEDocuments that have been used during
     * the running of the scripts.
     */
    private List<Document> documents = new ArrayList<Document>();
    
    /**
     * For each PRASEDocument, we need to capture the Document, and
     * we need to capture the building parameters used for each
     * of the Documents. We do this is a name/value association.
     */
    private Map<Document, Map> buildingParameters = new HashMap();
    
    /**
     * A flag to be placed in ThreadLocal. If found, then
     * ScriptProcessor will be sure to build a test case
     * from the currently running script.
     */
    public static final String CREATE_SP_TEST = "CREATE_SP_TEST";
    
     /**
      * A convenience variable to compliment "YES".
      */    
    public static final String CREATE_SP_TEST_YES = "YES";
    
    /**
     * A convenience variable to compliment "YES".
     */
    public static final String CREATE_SP_TEST_NO = "NO";
    
    public SPTest()
    {
    }
    
    /**
     * True if ThreadLocal has a flag communicating that the currently running
     * script should be stored as a future test.
     * @return
     */
    public boolean shouldCaptureTestData()
    {
        String createTest = Util.initString((String) SessionHelper.getFromThreadLocal(CREATE_SP_TEST), CREATE_SP_TEST_NO);
        
        return createTest.equals(CREATE_SP_TEST_YES);
    }
    
    /**
     * Clones and then stores the specified Document. Cloning is 
     * necesary since the script may be modifying the state of 
     * the Document later on. We need to capture the initial 
     * state.
     * @param praseDocBuilder
     */
    public void storePRASEDocument(PRASEDocBuilder praseDocBuilder) throws SPException
    {
        Document clonedPRASEDocument = (Document) praseDocBuilder.getDocument().clone();
        
        Map buildingParameters = praseDocBuilder.getBuildingParameters();
                
        getBuildingParameters().put(clonedPRASEDocument, buildingParameters);
        
        getDocuments().add(clonedPRASEDocument);
    }
    
    /**
     * Stores the captured Documents, WS entries, and Driver structure.
     * The DB storage takes place in its own transaction to avoid
     * any conflict with any currently running business transactions.
     */
    public void storeTestResults(ScriptProcessor sp)
    {
        PRASETest praseTest = buildPRASETest(sp);
        
        List<PRASEDocument> praseDocuments = buildPRASEDocuments(praseTest.getDriverKey());
        
        List<PRASETestDocument> praseTestDocuments = buildPRASETestDocuments(praseTest, praseDocuments);

        Session session = null;

        Transaction t = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.ENGINE);
    
            t = session.beginTransaction();
            
            session.save(praseTest);
            
            for (PRASEDocument praseDocument:praseDocuments)
            {
                session.save(praseDocument);
            }
            
            for (PRASETestDocument praseTestDocument:praseTestDocuments)
            {
                session.save(praseTestDocument);
            }
            
            t.commit();
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            if (t != null)
            {
                if (t.isActive() && !t.wasCommitted())
                {
                    t.rollback();
                }
            }
            
            // Deliberately not re-throwing this error as this is not a business critical funtion.
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
        
    }
    
    /**
     * Clears the state of this test.
     */
    public void clearTestResults()
    {
        getDocuments().clear();        
        
        getBuildingParameters().clear();
    }
    
    /**
     * PRASETestDocument is fundamentally an association entity mapping a PRASETest to the set of PRASEDocument(s)
     * used. Additionally, each PRASETestDocument needs to establish whether it is the Root/Initial document
     * built during the run of ScriptProcessor. This is considered trivial in that the first document activated 
     * must be the Root/Initial document by definition.
     * @param praseTest
     * @param praseDocuments
     * @return
     */
    private List<PRASETestDocument> buildPRASETestDocuments(PRASETest praseTest, List<PRASEDocument> praseDocuments)
    {
        List<PRASETestDocument> praseTestDocuments = new ArrayList<PRASETestDocument>();
        
        boolean rootDocumentFound = false;
        
        for (PRASEDocument praseDocument:praseDocuments)
        {
            PRASETestDocument praseTestDocument = new PRASETestDocument();
            
            if (!rootDocumentFound) // the first one found is the root - booyaa!
            {
                praseTestDocument.setIsRoot("Y");
                
                rootDocumentFound = true;
            }
            else
            {
                praseTestDocument.setIsRoot("N");
            }
            
            praseTestDocument.setPRASEDocument(praseDocument); // uni-direction only for performance
            
            praseTestDocument.setPRASETest(praseTest); // uni-direction only for performance
            
            praseTestDocuments.add(praseTestDocument);
        }
        
        return praseTestDocuments;
    }
    
    /**
     * Uses the captures Documents(s) to build the corresponding
     * PRASEDocument(s) (actual Hibernate entities). 
     * @return
     */
    private List<PRASEDocument> buildPRASEDocuments(String driverKey)
    {
        List<PRASEDocument> praseDocuments = new ArrayList<PRASEDocument>();
        
        for (Document document:getDocuments())
        {
            PRASEDocument praseDocument = new PRASEDocument();
            
            praseDocument.setDescription("Dynamically Generated Document");

            StringWriter writer = new StringWriter();

            DOMUtil.compactPrint(document, writer);
            
            praseDocument.setDocumentContentXML(writer.toString()); // ignore the <?xml version="1.0"?> = 21 characters
            
            praseDocument.setRootElementName(document.getRootElement().getName());
            
            praseDocument.setCreationDriverKey(driverKey);
            
            praseDocument.setBuildingParameterXML(buildBuildingParameters(document));
            
            praseDocuments.add(praseDocument);
        }
        
        return praseDocuments;
    }

    /**
     * Extracts the relevant information to establish the Rule key and
     * the ProductStructure in order to build a PRASETest instance.
     * @param sp
     * @return
     */
    private PRASETest buildPRASETest(ScriptProcessor sp)
    {
        // Rule key ...
        String effectiveDate = sp.getProductRule().getEffectiveDate();
        
        String process = sp.getProductRule().getProcessName();
        
        String event = sp.getProductRule().getEventName();
        
        String eventType = sp.getProductRule().getEventTypeName();
        
        // ProductStructure...
        ProductStructure productStructure = ProductStructure.findByPK(new Long(sp.getProductRule().getProductStructurePK()));

        // PRASETest...
        PRASETest praseTest = new PRASETest();
        
        praseTest.setProductStructure(productStructure); // one way assocation for performance (avoid adder)
        
        praseTest.setDriverEffectiveDate(new EDITDate(effectiveDate));
        
        praseTest.setDriverProcess(process);
        
        praseTest.setDriverEvent(event);
        
        praseTest.setDriverEventType(eventType);
        
        praseTest.setDescription("Dynamically Generated Test");
        
        return praseTest;
    }

    /**
     * @see SPTest#documents
     * @return
     */
    public List<Document> getDocuments()
    {
        return documents;
    }

    /**
     * @see #buildingParameters
     * @return
     */
    public Map<Document, Map> getBuildingParameters()
    {
        return buildingParameters;
    }

    /**
     * The building params are contained in a Map that needs to be
     * converted to its XML equivalent.
     * 
     * @return
     *          <BuildingParameterXML>
     *              <Name1>Value1</Name1>
     *              <Name2>Value2</Name2>
     *              ...
     *          </BuildingParameterXML>
     */
    private String buildBuildingParameters(Document document)
    {
        Map<String, String> documentBuildingParameters = getBuildingParameters().get(document);
        
        Element buildingParameterXML = new DefaultElement("BuildingParameterXML");
        
        for (String name:documentBuildingParameters.keySet())
        {
            String value = (String) documentBuildingParameters.get(name);
            
            Element buildingParameterElement = Util.buildElement(name, value);
            
            buildingParameterXML.add(buildingParameterElement);
        }
        
        return buildingParameterXML.asXML();
    }
}
