/*
 * User: sprasad
 * Date: Oct 15, 2007
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package conversion.component;

import conversion.ConversionData;
import conversion.ConversionJob;
import conversion.ConversionTemplate;

import conversion.business.Conversion;

import edit.common.exceptions.SEGConversionException;

import edit.services.db.hibernate.SessionHelper;

import fission.utility.ConversionUtil;
import fission.utility.DOMUtil;
import fission.utility.XMLUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import webservice.SEGResponseDocument;


public class ConversionComponent implements Conversion
{
    /**
     * @see conversion.business.Conversion#createConversionTemplate 
     * @param requestDocument
     * @return
     */
    public Document createConversionTemplate(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();    

        // Get Request Parameters
        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
        
        Element conversionTemplateElement = requestParameterElement.element("ConversionTemplateVO");

        ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper.mapToHibernateEntity(ConversionTemplate.class, conversionTemplateElement, ConversionTemplate.DATABASE, false);
        
        if  (ConversionTemplate.templateNameUnique(conversionTemplate.getTemplateName()))
        {
            try
            {
                SessionHelper.beginTransaction(ConversionTemplate.DATABASE);

                // Save
                conversionTemplate.hSave();

                SessionHelper.commitTransaction(ConversionTemplate.DATABASE);
                
                responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "New Template Added Successfully");
            }
            catch (Exception e)
            {
                responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

                SessionHelper.rollbackTransaction(ConversionTemplate.DATABASE);
            }
            finally
            {
                SessionHelper.clearSessions();
            }            
        }
        else
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Template Name Needs to be Unique");
        }
        

        return responseDocument.getDocument();
    }
    
    /**
     * @see conversion.business.Conversion#getConversionTemplate
     * @param requestDocument
     * @return
     */
    public Document getConversionTemplate(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Long conversionTemplatePK = new Long(requestParameterElement.element("ConversionTemplatePK").getText());

        ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper.get(ConversionTemplate.class, conversionTemplatePK, ConversionTemplate.DATABASE);

        Element conversionTemplateElement = conversionTemplate.getAsElement(true, true);

        responseDocument.addToRootElement(conversionTemplateElement);

        return responseDocument.getDocument();
    }

    /**
     * @see conversion.business.Conversion#updateConversionTemplate
     * @param requestDocument
     * @return
     */
    public Document updateConversionTemplate(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        // Get Request Parameters
        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
        
        Element conversionTemplateElement = requestParameterElement.element("ConversionTemplateVO");

        ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper.mapToHibernateEntity(ConversionTemplate.class, conversionTemplateElement, ConversionTemplate.DATABASE, false);
        
        try
        {
            SessionHelper.beginTransaction(ConversionTemplate.DATABASE);

            // Save new changes
            conversionTemplate.hSave();

            SessionHelper.commitTransaction(ConversionTemplate.DATABASE);
        }
        catch (Exception e)
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

            SessionHelper.rollbackTransaction(ConversionTemplate.DATABASE);
        }
        finally
        {
            SessionHelper.clearSessions();
        }

        responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Template Updated Successfully");
        
        return responseDocument.getDocument();
    }

    /**
     * @see conversion.business.Conversion#deleteConversionTemplate
     * @param requestDocument
     * @return
     */
    public Document deleteConversionTemplate(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        // Get Request Parameters
        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Long conversionTemplatePK = new Long(requestParameterElement.element("ConversionTemplatePK").getText());

        ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper.get(ConversionTemplate.class, conversionTemplatePK, ConversionTemplate.DATABASE);

        try
        {
            SessionHelper.beginTransaction(ConversionTemplate.DATABASE);

            // Delete
            conversionTemplate.hDelete();

            SessionHelper.commitTransaction(ConversionTemplate.DATABASE);
        }
        catch (Exception e)
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

            SessionHelper.rollbackTransaction(ConversionTemplate.DATABASE);
        }
        finally
        {
            SessionHelper.clearSessions();
        }

        responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Template Deleted Successfully");

        return responseDocument.getDocument();
    }

    /**
     * @see conversion.business.Conversion#getAllConversionTemplates
     * @param requestDocument
     * @return
     */
    public Document getAllConversionTemplates(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        // Find all the existing Conversion Templates
        ConversionTemplate[] conversionTemplates = ConversionTemplate.findAll();
        
        for (int i = 0; i < conversionTemplates.length; i++)
        {
            ConversionTemplate conversionTemplate = conversionTemplates[i];
            
            Element conversionTemplateElement = conversionTemplate.getAsElement(true, true);
            
            responseDocument.addToRootElement(conversionTemplateElement);
        }
        
        return responseDocument.getDocument();
    }
    
    /**
     * @see conversion.business.Conversion#cloneConversionTemplate
     * @param requestDocument
     * @return
     */
    public Document cloneConversionTemplate(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        
        // Get Request Parameters
        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
        
        Long conversionTemplatePKToCloneFrom = new Long(requestParameterElement.element("ConversionTemplatePK").getText());
        
        Element conversionTemplateElement = requestParameterElement.element("ConversionTemplateVO");

        ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper.mapToHibernateEntity(ConversionTemplate.class, conversionTemplateElement, ConversionTemplate.DATABASE, false);
        
        ConversionTemplate conversionTemplateToCloneFrom = (ConversionTemplate) SessionHelper.get(ConversionTemplate.class, conversionTemplatePKToCloneFrom, ConversionTemplate.DATABASE);
        
        conversionTemplate.setTemplateText(conversionTemplateToCloneFrom.getTemplateText());
        
        try
        {
            SessionHelper.beginTransaction(ConversionTemplate.DATABASE);
            
            conversionTemplate.hSave();
            
            SessionHelper.commitTransaction((ConversionTemplate.DATABASE));
        }
        catch (Exception e)
        {
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

            SessionHelper.rollbackTransaction(ConversionTemplate.DATABASE);            
        }
        finally
        {
            SessionHelper.clearSessions();
        }
        
        responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Template Cloned Successfully");
        
        return responseDocument.getDocument();
    }
    
    /**
     * @see conversion.business.Conversion#loadTestConversionData
     * @param requestDocument
     * @return
     */
    public Document loadTestConversionData(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        
        try
        {
            Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
            
            Element sampleConversionDataElement = new DefaultElement("SampleConversionDataVO");

            String fileName = requestParameterElement.element("FileName").getText();
            
            //ConversionData conversionData = new ConversionData(fileName);
            
            //Reader sampleData = conversionData.getSampleData(5);
            
            //XMLUtil.setText(sampleConversionDataElement, sampleData);
            
            responseDocument.addToRootElement(sampleConversionDataElement);
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
        }
        
        return responseDocument.getDocument();
    }

    /**
     * @see conversion.business.Conversion#testConversionTemplate
     * @param requestDocument
     * @return
     */
    public Document testConversionTemplate(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        
        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
        
        String conversionTemplatePK = requestParameterElement.element("ConversionTemplatePK").getText();
        
        String fileName = requestParameterElement.element("FileName").getText();
        
        ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper.get(ConversionTemplate.class, new Long(conversionTemplatePK), ConversionTemplate.DATABASE);
        
//        try
//        {
//            Element groupElement = null;//conversionTemplate.convertSampleXML(fileName);
//            
//            Element sampleConvertedDataElement = new DefaultElement("SampleConvertedData");
//            
//            sampleConvertedDataElement.add(groupElement);
//            
//            responseDocument.addToRootElement(sampleConvertedDataElement);
//        }
//        catch (SEGConversionException e)
//        {
//            System.out.println(e);
//            
//            e.printStackTrace();
//            
//            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
//        }
        
        return responseDocument.getDocument();
    }

    /**
     * @see conversion.business.Conversion#getConversionJobs
     * @param requestDocument
     * @return
     */
    public Document getConversionJobs(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        
        ConversionJob[] conversionJobs = ConversionJob.findAll();
        
        for (int i = 0; i < conversionJobs.length; i++)
        {
            ConversionJob conversionJob = conversionJobs[i];
            
            Element conversionJobElement = conversionJob.getAsElement(true, true);
            
            responseDocument.addToRootElement(conversionJobElement);
        }
        
        return responseDocument.getDocument();
    }
    
    /**
     * @see conversion.business.Conversion#runConversion
     * @param requestDocument
     * @return
     */
    public Document runConversion(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        
        Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
        
        Long conversionTemplatePK = Long.parseLong(requestParameterElement.element("ConversionTemplatePK").getText());
        
        String fileName = requestParameterElement.element("FileName").getText();
        
        String jobName = requestParameterElement.element("JobName").getText();
        
        boolean genericXMLOnly = (requestParameterElement.element("GenericXMLOnly").getText().equals("Y"))?true:false;

        boolean returnSample = (requestParameterElement.element("ReturnSample").getText().equals("Y"))?true:false;

        int numberOfGroups = new Integer(requestParameterElement.element("NumberOfGroups").getText()).intValue();
        
        ConversionJob conversionJob = null;
        
        Element conversionResult = null;
        
        boolean conversionSuccessful = false;
        
        try
        {
            // 1. Quickly capture the start of the conversion.
            SessionHelper.beginTransaction(ConversionTemplate.DATABASE);
            
            conversionJob = ConversionJob.build_V1(jobName, fileName, conversionTemplatePK);
            
            conversionJob.flagStartConversion();
            
            conversionJob.hSave(); // We save now because we want to capture the attempt, even if it should fail.

            SessionHelper.commitTransaction(ConversionTemplate.DATABASE);
            
            // 2. Run the conversion with its running parameters
            conversionJob.setGenericXMLOnly(genericXMLOnly);
            
            conversionJob.setReturnSample(returnSample);
            
            conversionJob.setNumberOfGroups(numberOfGroups);
            
            conversionJob.runConversion();

            conversionJob.flagStopConversion();

            conversionSuccessful = true;
            
            conversionResult = conversionJob.getResult();
        }
        catch (SEGConversionException e)
        {
            SessionHelper.rollbackTransaction(ConversionTemplate.DATABASE);
        
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
        }
        catch (Exception e)
        {
        	System.out.println(e);

            e.printStackTrace();
            
        	responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
        }
        finally
        {
            // 3. Capture the end of the conversion.
            SessionHelper.beginTransaction(ConversionTemplate.DATABASE);
            
            conversionJob.hSave(); // We save now because we want to capture the attempt, even if it should fail.

            SessionHelper.commitTransaction(ConversionTemplate.DATABASE);
            
            // 4. Format the response
            if (conversionResult != null)
            {
                responseDocument.addToRootElement(conversionResult);
            }
            
            if (conversionSuccessful)
            {
                responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Conversion Successful/In Progress");            
            }
        }
        
        return responseDocument.getDocument();       
    }
    
    /**
     * @see conversion.business.Conversion#runConversion
     * @param requestDocument
     * @return
     */
    public Document getAllTestFiles(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        
        try
        {
            ConversionData[] conversionDataFiles = ConversionData.getAllConversionDataFiles();
            
            for (ConversionData conversionDataFile:conversionDataFiles)
            {
                Element conversionDataFileElement = conversionDataFile.getAsElement();
                
                responseDocument.addToRootElement(conversionDataFileElement);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        
            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Unable to retrieve all data files");
        }
        
        return responseDocument.getDocument();       
    }
}