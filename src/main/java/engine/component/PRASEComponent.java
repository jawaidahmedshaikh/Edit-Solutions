package engine.component;

import edit.common.EDITDate;
import edit.common.EDITDateTime;

import edit.services.db.hibernate.SessionHelper;

import engine.Company;
import engine.ProductStructure;

import engine.business.Calculator;
import engine.business.PRASE;

import engine.sp.*;

import fission.utility.*;

import java.util.*;
import java.io.*;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;

import webservice.SEGRequestDocument;
import webservice.SEGResponseDocument;

public class PRASEComponent implements PRASE
{
    public PRASEComponent()
    {
    }
    
    /**
     * @see #getAllPRASEDocuments
     * @param requestDocument
     * @return
     */
    public Document getAllPRASEDocuments(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
   
        PRASEDocument[] praseDocuments = PRASEDocument.findAll();
        
        for (PRASEDocument praseDocument:praseDocuments)
        {
            Element praseDocumentVOElement = SessionHelper.mapToElement(praseDocument, praseDocument.getDatabase(), true, true);
            
            responseDocument.addToRootElement(praseDocumentVOElement);
        }
        
        responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "PRASEDocuments successfully retrieved.");
        
        return responseDocument.getDocument();
    } 
    
    /**
     * @see #updatePRASEDocument(Document)
     * @param requestDocument
     * @return
     */
    public Document updatePRASEDocument(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseDocumentVOElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASEDocumentVO");

        PRASEDocument praseDocument = null;
        
        try
        {
            praseDocument = (PRASEDocument) SessionHelper.mapToHibernateEntity(PRASEDocument.class, praseDocumentVOElement, SessionHelper.ENGINE, true);
            
            praseDocument.normalizeDocumentContentXML();
            
            praseDocument.normalizeBuildingParameterXML();
            
            SessionHelper.beginTransaction(praseDocument.getDatabase());
            
            SessionHelper.save(praseDocument, praseDocument.getDatabase());
            
            SessionHelper.commitTransaction(praseDocument.getDatabase());            
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "PRASEDocument successfully updated.");
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "PRASEDocument failed to update [" + praseDocument.getRootElementName() + "]");            
        }
        
        return segResponseDocument.getDocument();
    }

    /**
     * @see #clonePRASEDocument(Document)
     * @param requestDocument
     * @return
     */
    public Document clonePRASEDocument(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseDocumentPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASEDocumentPK");        
        
        Element descriptionElement = SEGRequestDocument.getRequestParameter(requestDocument, "Description");        
        
        Long praseDocumentPK = new Long(praseDocumentPKElement.getText());
        
        PRASEDocument praseDocument = (PRASEDocument) SessionHelper.get(PRASEDocument.class, praseDocumentPK, SessionHelper.ENGINE);
        
        PRASEDocument clonedPRASEDocument = (PRASEDocument) SessionHelper.shallowCopy(praseDocument, praseDocument.getDatabase());
        
        clonedPRASEDocument.setCreationDateTime(new EDITDateTime());
        
        clonedPRASEDocument.setDescription(descriptionElement.getText());
        
        try
        {
            SessionHelper.beginTransaction(clonedPRASEDocument.getDatabase());
            
            SessionHelper.saveOrUpdate(clonedPRASEDocument, clonedPRASEDocument.getDatabase());
            
            SessionHelper.commitTransaction(clonedPRASEDocument.getDatabase());
            
            segResponseDocument.addToRootElement(clonedPRASEDocument.getAsElement());
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Successfully cloned PRASEDocument [" + clonedPRASEDocument.getRootElementName() + "]");
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(clonedPRASEDocument.getDatabase());
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Failed to clone PRASEDocument [" + e.getMessage() + "]");
        }
        
        return segResponseDocument.getDocument();
    }

    /**
     * @see #getAssociatedPRASETests(Document)
     * @param requestDocument
     * @return
     */
    public Document getAssociatedPRASETests(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseDocumentPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASEDocumentPK");  
        
        Long praseDocumentPK = new Long(praseDocumentPKElement.getText());
        
        PRASEDocument praseDocument = (PRASEDocument) SessionHelper.get(PRASEDocument.class, praseDocumentPK, SessionHelper.ENGINE);
        
        PRASETest[] praseTests = PRASETest.findBy_PRASEDocument_V1(praseDocument);
        
        for (PRASETest praseTest:praseTests)
        {
            Element praseTestElement = praseTest.getAsElement(true, true);
            
            ProductStructure productStructure = praseTest.getProductStructure();
            
            Element productStructureElement = productStructure.getAsElement(true, true);
            
            Company company = productStructure.getCompany();
            
            Element companyElement = company.getAsElement(true, true);
            
            // Assemble the Elements ...
            praseTestElement.add(productStructureElement);
            
            productStructureElement.add(companyElement);
            
            segResponseDocument.addToRootElement(praseTestElement);
        }

        if (praseTests.length == 0)
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, "No associated PRASETests found.");
        }
        else
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Associated PRASETests successfully retrieved.");
        }

        
        return segResponseDocument.getDocument();
    }
    
    /**
     * @see #getAllPRASETests(Document)
     * @param requestDocument
     * @return
     */
    public Document getAllPRASETests(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        PRASETest[] praseTests = PRASETest.findAll_V1();
        
        for (PRASETest praseTest:praseTests)
        {
            Element praseTestElement = praseTest.getAsElement(true, true);
            
            ProductStructure productStructure = praseTest.getProductStructure();
            
            Element productStructureElement = productStructure.getAsElement(true, true);
            
            Company company = productStructure.getCompany();
            
            Element companyElement = company.getAsElement(true, true);
            
            // Assemble the Elements...
            praseTestElement.add(productStructureElement);
            
            productStructureElement.add(companyElement);
            
            segResponseDocument.addToRootElement(praseTestElement);
        }

        if (praseTests.length == 0)
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, "No PRASETests found.");
        }
        else
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "All PRASETests successfully retrieved.");
        }

        
        return segResponseDocument.getDocument();
    }    
    
    /**
     * @see #getAllPRASETests(Document)
     * @param requestDocument
     * @return
     */
    public Document getAllProductStructures(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        ProductStructure[] productStructures = ProductStructure.find_All_V3();
        
        for (ProductStructure productStructure:productStructures)
        {
            Element productStructureElement = productStructure.getAsElement(true, true);
            
            Company company = productStructure.getCompany();
            
            Element companyElement = company.getAsElement(true, true);
            
            productStructureElement.add(companyElement);
            
            segResponseDocument.addToRootElement(productStructureElement);
        }

        if (productStructures.length == 0)
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, "No ProductStructures found.");
        }
        else
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "All ProductStructures successfully retrieved.");
        }

        
        return segResponseDocument.getDocument();
    }     
    
    /**
     * @see #getCandidatePRASEDocuments(Document)
     * @param requestDocument
     * @return
     */
    public Document getCandidatePRASEDocuments(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseTestPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASETestPK");  
        
        Long praseTestPK = new Long(praseTestPKElement.getText());
        
        PRASEDocument[] candidatePRASEDocuments = PRASEDocument.findBy_PRASETestPK_Not_Associated(praseTestPK);
        
        for (PRASEDocument candidatePRASEDocument:candidatePRASEDocuments)
        {
            Element candidatePRASEDocumentElement = candidatePRASEDocument.getAsElement(true, true);
            
            segResponseDocument.addToRootElement(candidatePRASEDocumentElement);
        }

        if (candidatePRASEDocuments.length == 0)
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, "No Canidate PRASEDocuments found.");
        }
        else
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Canidate PRASEDocuments found.");
        }

        
        return segResponseDocument.getDocument();
    }         
    
    /**
     * @see #getSelectedPRASEDocuments(Document)
     * @param requestDocument
     * @return
     */
    public Document getSelectedPRASEDocuments(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseTestPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASETestPK");  
        
        Long praseTestPK = new Long(praseTestPKElement.getText());
        
        PRASETestDocument[] praseTestDocuments = PRASETestDocument.findBy_PRASETestPK_V1(praseTestPK);
        
        for (PRASETestDocument praseTestDocument:praseTestDocuments)
        {
            Element praseTestDocumentElement = praseTestDocument.getAsElement(true, true);
            
            PRASEDocument praseDocument = praseTestDocument.getPRASEDocument();
            
            Element praseDocumentElement = praseDocument.getAsElement(true,true);
            
            praseTestDocumentElement.add(praseDocumentElement);
            
            segResponseDocument.addToRootElement(praseTestDocumentElement);
        }

        if (praseTestDocuments.length == 0)
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, "No Selected PRASEDocuments found.");
        }
        else
        {
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Selected PRASEDocuments found.");
        }

        
        return segResponseDocument.getDocument();
    }          
    
    /**
     * @see #createPRASETest(Document)
     * @param requestDocument
     * @return
     */
    public Document createPRASETest(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        PRASETest praseTest = new PRASETest();

        try
        {
            String description = SEGRequestDocument.getRequestParameterValue(requestDocument, "Description");

            EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(SEGRequestDocument.getRequestParameterValue(requestDocument, "EffectiveDate"));

            String process = SEGRequestDocument.getRequestParameterValue(requestDocument, "Process");

            String event = SEGRequestDocument.getRequestParameterValue(requestDocument, "Event");

            String eventType = SEGRequestDocument.getRequestParameterValue(requestDocument, "EventType");

            String productStructurePK = SEGRequestDocument.getRequestParameterValue(requestDocument, "ProductStructurePK");

            praseTest.setDescription(description);

            praseTest.setDriverEffectiveDate(effectiveDate);

            praseTest.setDriverEvent(event);

            praseTest.setDriverEventType(eventType);

            praseTest.setDriverProcess(process);

            ProductStructure productStructure = ProductStructure.findByPK(new Long(productStructurePK));

            praseTest.setProductStructure(productStructure);

            SessionHelper.beginTransaction(praseTest.getDatabase());

            SessionHelper.save(praseTest, praseTest.getDatabase());

            SessionHelper.commitTransaction(praseTest.getDatabase());
            
            // Assemble the Elements...
            Element praseTestElement = praseTest.getAsElement(true, true);
            
            Element productStructureElement = productStructure.getAsElement(true, true);
            
            Company company = productStructure.getCompany();
            
            Element companyElement = company.getAsElement(true, true);
            
            praseTestElement.add(productStructureElement);
            
            productStructureElement.add(companyElement);
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Successfully created PRAETest.");
            
            segResponseDocument.addToRootElement(praseTestElement);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(praseTest.getDatabase());
         
            System.out.println(e);
            
            e.printStackTrace();
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Failed to create PRAETest.");
        }
        
        return segResponseDocument.getDocument();
    }        
    
    /**
     * Updates information about the specified PRASETest most notably the
     * list of PRASEDocuments that are associated with PRASETest.
     * @param requestDocument
     * @return
     */
    public Document updatePRASETest(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseTestPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASETestPK");  
        
        Long praseTestPK = new Long(praseTestPKElement.getText());
        
        Element rootPRASEDocumentPKElement  = SEGRequestDocument.getRequestParameter(requestDocument, "PRASEDocumentPK"); 
        
        Long rootPRASEDocumentPK = new Long(rootPRASEDocumentPKElement.getText());
        
        List<Element> praseDocumentVOElements = SEGRequestDocument.getRequestParameters(requestDocument).elements("PRASEDocumentVO");
        
        List<PRASEDocument> praseDocuments = new ArrayList<PRASEDocument>();
        
        for (Element praseDocumentVOElement:praseDocumentVOElements)
        {
            PRASEDocument praseDocument = (PRASEDocument) SessionHelper.mapToHibernateEntity(PRASEDocument.class, praseDocumentVOElement, SessionHelper.ENGINE, true);
            
            praseDocuments.add(praseDocument);
        }
           
        PRASETest praseTest = (PRASETest) SessionHelper.get(PRASETest.class, praseTestPK, SessionHelper.ENGINE);

        try
        {
            SessionHelper.beginTransaction(praseTest.getDatabase());

            praseTest.updateAssociatedPRASEDocuments(praseDocuments, rootPRASEDocumentPK);

            SessionHelper.commitTransaction(praseTest.getDatabase());
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Successfully committed changes to PRASETest.");
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(praseTest.getDatabase());
            
            System.out.println(e);
            
            e.printStackTrace();
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Failed to commit changes to PRASETest.");
        }
        
        return segResponseDocument.getDocument();   
    }
    
    /**
     * @super #runPRASETest(Document)
     * @param requestDocument
     * @return
     */
    public Document runPRASETest(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();
        
        Element praseTestPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASETestPK");  
        
        Long praseTestPK = new Long(praseTestPKElement.getText());
        
        PRASETest praseTest = (PRASETest) SessionHelper.get(PRASETest.class, praseTestPK, SessionHelper.ENGINE);

        try
        {
            Document differenceReportVODocument = praseTest.run();
            
            segResponseDocument.addToRootElement(DOMUtil.stripFromDocument(differenceReportVODocument.getRootElement(), false));
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Successfully executed PRASETest.");
        }
        catch (SPException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Failed to execute PRASETest.");            
        }
        
        return segResponseDocument.getDocument();   
    } 
    
    
    /**
     * @super #runPRASETest(Document)
     * @param requestDocument
     * @return
     */
    public Document updatePRASETestExpectedResults(Document requestDocument)
    {
        SEGResponseDocument segResponseDocument = new SEGResponseDocument();

        Element spOutputVOElement = SEGRequestDocument.getRequestParameter(requestDocument, "SPOutputVO");
        
        Document spOutputVOElementDocument = new DefaultDocument();
        
        spOutputVOElementDocument.setRootElement(DOMUtil.stripFromDocument(spOutputVOElement, false));
        
        Element praseTestPKElement = SEGRequestDocument.getRequestParameter(requestDocument, "PRASETestPK");  
        
        Long praseTestPK = new Long(praseTestPKElement.getText());       
        
        PRASETest praseTest = (PRASETest) SessionHelper.get(PRASETest.class, praseTestPK, SessionHelper.ENGINE);
        
        try
        {
            praseTest.setExpectedPRASEOutputXML(spOutputVOElementDocument.asXML());
            
            praseTest.normalizeExpectedPRASEOutputXML();
            
            SessionHelper.beginTransaction(SessionHelper.ENGINE);
            
            SessionHelper.saveOrUpdate(praseTest, praseTest.getDatabase());
            
            SessionHelper.commitTransaction(praseTest.getDatabase());
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Successfully update PRASETest.");
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            segResponseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Failed to update PRASETest.");            
        }
        
        return segResponseDocument.getDocument();   
    }

    /**
     * @see PRASE#getScriptProcessorResults(org.dom4j.Document)
     */
    public Document getScriptProcessorResults(Document requestDocument)
    {
        SEGResponseDocument responseDocument = null;

        Calculator calculatorComponent = new CalculatorComponent();
        Element spResultsElement = calculatorComponent.getSPRecordedResults().getAsElement();

        responseDocument = new SEGResponseDocument();
        responseDocument.addToRootElement(spResultsElement);
        responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "ScriptProcessor results successfully retrieved.");

//        XMLUtil.printDocumentToSystemOut(responseDocument.getDocument());

        return responseDocument.getDocument();

        /*
        Document document = null;

        try
        {
            document = XMLUtil.readDocumentFromFile("C:/Temp/SPRecorder.xml");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        XMLUtil.printDocumentToSystemOut(document);

        return document;
        */
    }

    /**
     * @see PRASE#exportRecordedRunData(org.dom4j.Document)
     */
    public Document exportRecordedRunData(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element operatorElement = requestParametersElement.element("Operator");
        Element runInformationElement = requestParametersElement.element("RunInformation");

        String operator = operatorElement.getText();
        String runInformation = runInformationElement.getText();

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            calculatorComponent.exportSPRecordedRunData(operator, runInformation);

            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Run data successfully exported");
        }
        catch (Exception e)
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Error exporting data: " + e.getMessage());
        }

        return responseDocument.getDocument();
    }

    /**
     * @see PRASE#clearSPRecordedOperator(org.dom4j.Document)
     */
    public Document clearSPRecordedOperator(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element operatorElement = requestParametersElement.element("Operator");

        String operator = operatorElement.getText();

        Calculator calculatorComponent = new CalculatorComponent();
        SPRecordedOperator removedSPRecordedOperator = calculatorComponent.clearSPRecordedOperator(operator);

        //  Now reload the sp results so the front end gets the latest info
        Element spResultsElement = calculatorComponent.getSPRecordedResults().getAsElement();

        responseDocument.addToRootElement(spResultsElement);

        if (removedSPRecordedOperator != null)
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Operator " + operator + " was successfully removed");
        }
        else
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Error removing operator " + operator);
        }

        return responseDocument.getDocument();
    }
}
