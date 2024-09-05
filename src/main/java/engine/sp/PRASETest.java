package engine.sp;

import edit.common.EDITDate;

import edit.common.EDITDateTime;
import edit.common.EDITMap;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import edit.services.logging.Logging;

import engine.ProductRuleStructure;
import engine.ProductStructure;
import engine.Rules;

import engine.sp.custom.document.ClientDocument;
import engine.sp.custom.document.GroupDocument;
import engine.sp.custom.document.PRASEDocBuilder;
import engine.sp.custom.document.RiderDocument;
import engine.sp.custom.document.SegmentDocument;

import fission.utility.DOMUtil;

import fission.utility.Util;

import fission.utility.XMLUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import logging.LogEvent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

/**
 * Stores pertinent information required to repeat a stored test at 
 * a later time. The information being:
 * 
 * 1. The EffectiveDate, Process, Event, and EventType.
 * 2. The associated ProductStructure.
 * 
 * Both 1 & 2 represent the normal information supplied for ScriptProcessor
 * to determine which Driver to load.
 * 
 * Additionally, association with the utilized PRASEDocuements will be captured.
 * 
 * The information captured in the entity should be sufficient to successfully re-run
 * the represented test at a later date.
 */
public class PRASETest extends HibernateEntity
{
    /**
     * PK.
     */
    private Long pRASETestPK;
    
    /**
     * FK.
     */
    private Long productStructureFK;
    
    /**
     * Free-form notes about the test.
     */
    private String description;
    
    /**
     * The date the test was first created.
     */
    private EDITDateTime creationDateTime;
    
    /**
     * The ProductStructure used to load the Driver to run the original test.
     */
    private ProductStructure productStructure;

    /**
     * The original Effective Date used to load the Driver.
     */
    private EDITDate driverEffectiveDate;
    
    /**
     * The original Process used to load the Driver.
     */
    private String driverProcess;
    
    /**
     * The original Event used to load the Driver.
     */
    private String driverEvent;
    
    /**
     * The original EventType used to load the Driver.
     */
    private String driverEventType;
    
    /**
     * The set of PRASETestDocuments (maps the
     * set of PRASEDocuemtns) associated to this PRASETest.
     */
    private java.util.Set<PRASETestDocument> pRASETestDocuments;
    
    /**
     * The stored results of a PRASE run that serve
     * as the control/expected results for future runs.
     */
    private String expectedPRASEOutputXML;
    
    public PRASETest()
    {
        setCreationDateTime(new EDITDateTime());
    }

    /**
     * @see PRASETest#pRASETestPK
     * @param newpraseTestPK
     */
    public void setPRASETestPK(Long newpraseTestPK)
    {
        this.pRASETestPK = newpraseTestPK;
    }

    /**
     * @see PRASETest#pRASETestPK
     */
    public Long getPRASETestPK()
    {
        return pRASETestPK;
    }

    /**
     * @see #description
     * @param newdescription
     */
    public void setDescription(String newdescription)
    {
        this.description = newdescription;
    }

    /**
     * @see #description
     * @return
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @see PRASETest#creationDateTime
     * @param newtestDateTime
     */
    public void setCreationDateTime(EDITDateTime newtestDateTime)
    {
        this.creationDateTime = newtestDateTime;
    }

    /**
     * @see PRASETest#creationDateTime
     * @return
     */
    public EDITDateTime getCreationDateTime()
    {
        return creationDateTime;
    }

    /**
     * @see PRASETest#productStructure
     * @param newproductRuleStructure
     */
    public void setProductStructure(ProductStructure newproductStructure)
    {
        this.productStructure = newproductStructure;
    }

    /**
     * @see PRASETest#productStructure
     * @return
     */
    public ProductStructure getProductStructure()
    {
        return productStructure;
    }

    /**
     * @see #driverEffectiveDate
     * @param newdriverEffectiveDate
     */
    public void setDriverEffectiveDate(EDITDate newdriverEffectiveDate)
    {
        this.driverEffectiveDate = newdriverEffectiveDate;
    }

    /**
     * @see #driverEffectiveDate
     * @return
     */
    public EDITDate getDriverEffectiveDate()
    {
        return driverEffectiveDate;
    }

    /**
     * @see #driverProcess
     * @param newdriverProcess
     */
    public void setDriverProcess(String newdriverProcess)
    {
        this.driverProcess = newdriverProcess;
    }

    /**
     * @see #driverProcess
     * @return
     */
    public String getDriverProcess()
    {
        return driverProcess;
    }

    /**
     * @see #driverEvent
     * @param newdriverEvent
     */
    public void setDriverEvent(String newdriverEvent)
    {
        this.driverEvent = newdriverEvent;
    }

    /**
     * @see #driverEvent
     * @return
     */
    public String getDriverEvent()
    {
        return driverEvent;
    }

    /**
     * @see #driverEventType
     * @param newdriverEventType
     */
    public void setDriverEventType(String newdriverEventType)
    {
        this.driverEventType = newdriverEventType;
    }

    /**
     * @see #driverEventType
     * @return
     */
    public String getDriverEventType()
    {
        return driverEventType;
    }

    /**
     * @see PRASETest#pRASETestDocuments
     * @param newpraseTestDocuments
     */
    public void setPRASETestDocuments(java.util.Set<PRASETestDocument> newpraseTestDocuments)
    {
        this.pRASETestDocuments = newpraseTestDocuments;
    }

    /**
     * @see PRASETest#pRASETestDocuments
     * @return
     */
    public java.util.Set<PRASETestDocument> getPRASETestDocuments()
    {
        return pRASETestDocuments;
    }

    public String getDatabase()
    {
        return SessionHelper.ENGINE;
    }

    /**
     * Uses the EffectiveDate, Process, Event, EventType to build an aesthetic key.
     * @return
     */
    public String getDriverKey()
    {
        String driverKey = "[" + getDriverEffectiveDate().getFormattedDate() + "] " +
            "[" + getDriverProcess()  + "] " +
            "[" + getDriverEvent() + "] " +
            "[" + getDriverEventType() + "]";
        
        return driverKey;
    }

    /**
     * Finder.
     * @param praseDocument
     * @return
     */
    public static PRASETest[] findBy_PRASEDocument(PRASEDocument praseDocument)
    {
        String hql = " select praseTest" +
                    " from PRASETest praseTest" +
                    " join praseTest.PRASETestDocuments praseTestDocument" +
                     " where praseTestDocument.PRASEDocument = :praseDocument";
        
        Map params = new EDITMap("praseDocument", praseDocument);
        
        List<PRASETest> results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);
        
        return results.toArray(new PRASETest[results.size()]);
    }
    
    /**
     * Finder. Finds the PRASETest.ProductStructure.Company composition.
     * @param praseDocument
     * @return
     */
    public static PRASETest[] findBy_PRASEDocument_V1(PRASEDocument praseDocument)
    {
        String hql = " select praseTest" +
                    " from PRASETest praseTest" +
                    " join praseTest.PRASETestDocuments praseTestDocument" +
                     " join fetch praseTest.ProductStructure productStructure" +
                     " join fetch productStructure.Company" +
                     " where praseTestDocument.PRASEDocument = :praseDocument";
        
        Map params = new EDITMap("praseDocument", praseDocument);
        
        List<PRASETest> results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);
        
        return results.toArray(new PRASETest[results.size()]);
    }    

    /**
     * Finder.
     * @return
     */
    public static PRASETest[] findAll()
    {
        String hql = "from PRASETest";
        
        List<PRASETest> results = SessionHelper.executeHQL(hql, null, SessionHelper.ENGINE);
        
        return results.toArray(new PRASETest[results.size()]);
    }
    
    /**
     * Finder. Finds the PRASETTest.ProductStructure.Company composition.
     * @return
     */
    public static PRASETest[] findAll_V1()
    {
        String hql = " select praseTest" +
                     " from PRASETest praseTest" +
                     " join fetch praseTest.ProductStructure productStructure" +
                     " join fetch productStructure.Company";
        
        List<PRASETest> results = SessionHelper.executeHQL(hql, null, SessionHelper.ENGINE);
        
        return results.toArray(new PRASETest[results.size()]);
    }    

    /**
     * @see #productStructureFK
     * @param newproductStructureFK
     */
    public void setProductStructureFK(Long newproductStructureFK)
    {
        this.productStructureFK = newproductStructureFK;
    }

    /**
     * @see #productStructureFK
     * @return
     */
    public Long getProductStructureFK()
    {
        return productStructureFK;
    }
    
    /**
     * This blindly deletes and then re-adds the associated PRASETestDocuments.
     * @param praseDocuments
     * @param rootPRASEDocumentPKElement the PK of the Document that should serve as root
     */
    public void updateAssociatedPRASEDocuments(List<PRASEDocument> praseDocuments, Long rootPRASEDocumentPK)
    {
        List<PRASETestDocument> praseTestDocumentList = new ArrayList(getPRASETestDocuments());
        
        for (int i = praseTestDocumentList.size() - 1; i >= 0; i--)
        {
            remove(praseTestDocumentList.get(i));
        }
        
        for (PRASEDocument praseDocument:praseDocuments)
        {
            PRASETestDocument praseTestDocument = new PRASETestDocument();
            
            if (praseDocument.getPRASEDocumentPK().longValue() == rootPRASEDocumentPK.longValue())
            {
                praseTestDocument.setIsRoot("Y");
            }
            else
            {
                praseTestDocument.setIsRoot("N");
            }
            
            praseTestDocument.setPRASETest(this);
            
            praseTestDocument.setPRASEDocument(praseDocument);
            
            SessionHelper.saveOrUpdate(praseTestDocument, getDatabase());
        }
    }
    
    /*
     * Remover convenience method.
     */
    public void remove(PRASETestDocument praseTestDocument)
    {
        getPRASETestDocuments().remove(praseTestDocument);
        
        praseTestDocument.setPRASETest(null);
        
        SessionHelper.saveOrUpdate(this, getDatabase());
    }
    
    /**
     * Runs this PRASETest against the specified ScriptProcessor.
     * @return a Document containing a difference report
     * @see #buildDifferenceReport(SPOutput)
     */
    public Document run() throws SPException
    {
        ScriptProcessor sp = null;
        
        SPOutput spOutput = null;
        
        Document differenceReportVODocument = null;
        
        try
        {
            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();
        
            java.util.Set<PRASETestDocument> praseTestDocuments = getPRASETestDocuments();
            
            for (PRASETestDocument praseTestDocument:praseTestDocuments)
            {
                PRASEDocBuilder praseDocBuilder = buildPRASEDocBuilder(praseTestDocument);                
                
                if (praseTestDocument.getIsRoot().equals(PRASETestDocument.IS_ROOT_YES))
                {
                    sp.loadRootDocument(praseDocBuilder);
                }
                else
                {
                    sp.loadDocument(praseDocBuilder);
                }
            }
            
            // Execute
            sp.loadScript(getDriverProcess(), getDriverEvent(), getDriverEventType(), getProductStructureFK().longValue(), getDriverEffectiveDate().getFormattedDate());

            sp.setNoEditingException(false);

            sp.setAbortOnHardValidationError(true);

            sp.exec();           
            
            spOutput = sp.getSPOutput();
            
            differenceReportVODocument = buildDifferenceReport(spOutput);
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            logging.Log.logGeneralExceptionToDatabase("New Business Quote failed to process.", e);

            throw new SPException("PRASE Test Failed", SPException.SCRIPT_LOADING_ERROR, e);
        }
        finally
        {
            if (sp != null) sp.close();
        }
        
        return differenceReportVODocument;
    }

    /**
     * Builds the PRASEDocBuilder represented by the specified PRASETestDocument.
     * @param praseTestDocument
     * @return PRASEDocBuilder
     */
    private PRASEDocBuilder buildPRASEDocBuilder(PRASETestDocument praseTestDocument)
    {
        PRASEDocument praseDocument = praseTestDocument.getPRASEDocument();
        
        Document document = praseDocument.getDocument();
        
        Element rootElement = document.getRootElement();
        
        document.setRootElement(null);
        
        rootElement.setParent(null);
        
        PRASEDocBuilder praseDocBuilder = PRASEDocBuilder.getPRASEDocBuilder(rootElement.getName());
        
        praseDocBuilder.setRootElement(rootElement);
        
        praseDocBuilder.setDocumentBuilt(true);
        
        praseDocBuilder.setBuildingParameters(praseDocument.getBuilderingParameters());
        
        return praseDocBuilder;
    }
    
    /**
     * Compares the actual SPOutput to the existing SPOutput and generates a 
     * difference report. If there is no existing expected SPOutput, then 
     * a Document is returned that contains just the actual SPOutput.
     * @param actualSPOutput
     * @return
     *  <DifferenceReportVO>
     *      <ExpectedPRASEOutputXML>
     *      ... SPOutputVO
     *      </ExpectedPRASEOutputXML>
     *      <ActualPRASEOutputXML>
     *      ... SPOutputVO
     *      </ActualPRASEOutputXML>
     *      <DifferenceXML>
     *          <Difference> // Repeated for every difference found between expected Document and the actual Document
     *              <ExpectedValue></ExpectedValue>
     *              <ActualValue></ActualValue>
     *              <Description></Description>
     *              <XPath></XPath>
     *          </Difference>
     *      </DifferenceXML>
     *  </DifferenceReportVO>
     *  
     *  ... or an empty Document if there were no differences found
     */
    private Document buildDifferenceReport(SPOutput actualSPOutput)
    {
        Document differenceReportVODocument = new DefaultDocument();
        
        Element differenceReportVOElement = new DefaultElement("DifferenceReportVO");
        
        differenceReportVODocument.setRootElement(differenceReportVOElement);
        
        Document actualPRASEOutputXMLDocument = actualSPOutput.getSPOutputVODocument();
        
        Document expectedPRASEOutoutXMLDocument = null;
        
        if (getExpectedPRASEOutputXML() == null)
        {
            Element spOutputVOElement = new DefaultElement("SPOutputVO");
            
            expectedPRASEOutoutXMLDocument = new DefaultDocument();
            
            expectedPRASEOutoutXMLDocument.setRootElement(spOutputVOElement);
        }
        else
        {
            expectedPRASEOutoutXMLDocument = DOMUtil.buildDocument(getExpectedPRASEOutputXML());
        }
            
        Document differenceXMLDocument = DOMUtil.generateDifferences(expectedPRASEOutoutXMLDocument, actualPRASEOutputXMLDocument);
        

        Element actualPRASEOutputXMLElement = new DefaultElement("ActualPRASEOutputXML");
        
        actualPRASEOutputXMLElement.add(DOMUtil.stripFromDocument(actualPRASEOutputXMLDocument.getRootElement(), false));
        
        
        Element expectedPRASEOutputXMLElement = new DefaultElement("ExpectedPRASEOutputXML");
        
        expectedPRASEOutputXMLElement.add(DOMUtil.stripFromDocument(expectedPRASEOutoutXMLDocument.getRootElement(), false));
        

        differenceReportVOElement.add(actualPRASEOutputXMLElement);
            
        differenceReportVOElement.add(expectedPRASEOutputXMLElement);
            
        differenceReportVOElement.add(DOMUtil.stripFromDocument(differenceXMLDocument.getRootElement(), false));
        
        return differenceReportVODocument;
    }

    /**
     * @see PRASETest#expectedPRASEOutputXML
     * @param newexpectedPRASEOutoutXML
     */
    public void setExpectedPRASEOutputXML(String newexpectedPRASEOutoutXML)
    {
        this.expectedPRASEOutputXML = newexpectedPRASEOutoutXML;
    }

    /**
     * @see PRASETest#expectedPRASEOutputXML
     * @return
     */
    public String getExpectedPRASEOutputXML()
    {
        return expectedPRASEOutputXML;
    }
    
    /**
     * It is possible that the ExpectedPRASEOutputXML has been set with
     * the formatting characters (/t /n etc) still in place. This
     * removes all such characters.
     */
    public void normalizeExpectedPRASEOutputXML() throws DocumentException
    {
        Document normalizedDocument = XMLUtil.parse(getExpectedPRASEOutputXML());
        
        String normalizedXML = normalizedDocument.getRootElement().asXML();
        
        setExpectedPRASEOutputXML(normalizedXML);
    }    
}
