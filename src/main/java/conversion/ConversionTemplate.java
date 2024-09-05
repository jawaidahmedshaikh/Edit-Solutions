/*
 * User: sprasad
 * Date: Oct 9, 2007
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package conversion;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logging.LogEvent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.xml.sax.helpers.AttributesImpl;

import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.exceptions.SEGConversionException;
import edit.common.vo.EDITExport;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import fission.utility.DOMUtil;
import fission.utility.DateTimeUtil;


/**
 * A ConversionTemplate is represents an XML document representing the structure that 
 * a flat-file should be converted to.
 * 
 * A ConversionTemplate is supplied a flat-file. Each line in the flat-file is
 * processed (one-by-one) to yield a raw XML equivalent.
 */
public class ConversionTemplate extends HibernateEntity
{
    /**
     * The caller has the ability to abort processing at any point. 
     */
    private boolean continueProcessing;

    /**
     * Identifier
     */
    private Long conversionTemplatePK;

    /**
     * The name of the template
     */
    private String templateName;

    /**
     * The text describing Template
     */
    private String templateDescription;

    /**
     * XFlat Template 
     */
    private String templateText;

    /**
     * The Date and Time this Template is created/updated
     */
    private EDITDateTime maintDateTime;

    /**
     * All ConversionJobs using this ConversionTemplate
     */
    private Set conversionJobs;
    
    private boolean debug = false;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.ENGINE;

    /**
     * Uses the specified file found in the configured Conversion directory (most
     * likely EDITServicesConfig.xml as of this writing) to convert a sample amount
     * of this ConversionTemplate. Since the ConversionTemplate defines a Group(ing)
     * of lines in the flat file, we only convert the minimum required lines.
     */
    public ConversionTemplate()
    {

    }

    /**
     * @see conversion.ConversionTemplate#conversionTemplatePK
     * @warn Do not call this method explicitly. Hibernate uses this method internally.
     * @param conversionTemplatePK
     */
    public void setConversionTemplatePK(Long conversionTemplatePK)
    {
        this.conversionTemplatePK = conversionTemplatePK;
    }

    /**
     * @see conversion.ConversionTemplate#conversionTemplatePK
     * @return
     */
    public Long getConversionTemplatePK()
    {
        return conversionTemplatePK;
    }

    /**
     * @see conversion.ConversionTemplate#templateName
     * @param templateName
     */
    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    /**
     * @see conversion.ConversionTemplate#templateName
     * @return
     */
    public String getTemplateName()
    {
        return templateName;
    }

    /**
     * @see conversion.ConversionTemplate#templateDescription
     * @param templateDescription
     */
    public void setTemplateDescription(String templateDescription)
    {
        this.templateDescription = templateDescription;
    }

    /**
     * @see conversion.ConversionTemplate#templateDescription
     * @return
     */
    public String getTemplateDescription()
    {
        return templateDescription;
    }

    /**
     * @see conversion.ConversionTemplate#templateText
     * @param templateText
     */
    public void setTemplateText(String templateText)
    {
        this.templateText = templateText;
    }

    /**
     * @see conversion.ConversionTemplate#templateText
     * @return
     */
    public String getTemplateText()
    {
        return templateText;
    }

    /**
     * @see conversion.ConversionTemplate#maintDateTime
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    /**
     * @see conversion.ConversionTemplate#maintDateTime
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    /**
     * @see conversion.ConversionTemplate#conversionJobs
     * @param conversionJobs
     */
    public void setConversionJobs(Set conversionJobs)
    {
        this.conversionJobs = conversionJobs;
    }

    /**
     * @see conversion.ConversionTemplate#conversionJobs
     * @return
     */
    public Set getConversionJobs()
    {
        return conversionJobs;
    }

    /**
     * @see conversion.ConversionTemplate#DATABASE
     * @return
     */
    public String getDatabase()
    {
        return ConversionTemplate.DATABASE;
    }

    /**
     * Persists to Database
     */
    public void hSave()
    {
        // Update MaintDateTime
        this.setMaintDateTime(new EDITDateTime());

        SessionHelper.saveOrUpdate(this, this.getDatabase());
    }

    /**
     * Deletes from Database
     */
    public void hDelete()
    {
        SessionHelper.delete(this, this.getDatabase());
    }

    /************************* Static Methods *************************************/

    /**
     * Returns all Conversion Templates.
     * @return - an array of ConversionTemplate objects
     */
    public static final ConversionTemplate[] findAll()
    {
        String hql = "select conversionTemplate from ConversionTemplate conversionTemplate";

        List results = SessionHelper.executeHQL(hql, null, DATABASE);

        return (ConversionTemplate[]) results.toArray(new ConversionTemplate[results.size()]);
    }

    /**
     * Begins the conversion process. The process can be aborted via a user-selected flag
     * @see #continueProcessing
     * @throws SEGConversionException
     */
    public void convert(ConversionListener listener, String fileName, PrintWriter out) throws Exception
    {  
    	try
    	{
    		ConversionData conversionData = new ConversionData(fileName);

    		List<String> flatFileLines = new ArrayList<String>();

    		BufferedReader br = null;

    		String flatFileLine = null;

    		Element groupNodeElement = null;

    		GroupNode groupNode = getGroupNode();

    		String headerRecordType = groupNode.getHeaderRecordNode().getType();

    		XMLWriter writer = null;

    		String xmlFilePath = getExportFile("Conversion_" + this.getTemplateName() + ".xml");

    		try
    		{
    			boolean groupBegan = false;

    			boolean groupFinished = false;

    			br = conversionData.openInputDataReader();

    			int count = 1;

    			writer = new XMLWriter(new FileWriter(xmlFilePath));

    			writer.startElement(this.getTemplateName(),this.getTemplateName(),this.getTemplateName(), new AttributesImpl());

    			

    			while (((flatFileLine = br.readLine()) != null) && isContinueProcessing())
    			{             	
    				if (debug)
    				{
    					count++;
  
    				}
    				try
    				{
    					String recordType = groupNode.getRecordType(flatFileLine);

    					// Case 1. Just started converting the very first grouping.
    					if (groupBegan == false)
    					{
    						groupBegan = true;

    						groupFinished = false;

    						flatFileLines.add(flatFileLine);

    						groupNodeElement = new DefaultElement(GroupNode.DEFAULT_GROUP_NAME);

    						groupNode.convert(flatFileLine, groupNodeElement);
    					}

    					// Case 2. The conversion process has started, so we keep reading until the next header record is reached
    					else if ((groupBegan == true) && !headerRecordType.equals(recordType))
    					{
    						flatFileLines.add(flatFileLine);

    						groupNode.convert(flatFileLine, groupNodeElement);
    					}

    					// Case 3a. We finished the current Group and need to make some decision(s) about what to do with the generic XML just created
    					// We also have the first header record of the next Group.
    					else
    					{   
    						groupBegan = false;

    						groupFinished = true;

    						// Write group to XML file
    						writer.write((Element)groupNodeElement);

    						writer.flush();

    						if (isContinueProcessing())
    						{
    							groupBegan = true;

    							groupFinished = false;

    							flatFileLines.clear();;

    							flatFileLines.add(flatFileLine);                             

    							// Begin next grouping
    							groupNodeElement = new DefaultElement(GroupNode.DEFAULT_GROUP_NAME);

    							groupNode.convert(flatFileLine, groupNodeElement);                        
    						}
    						else
    						{
    							break;
    						}
    					}
    				}
    				catch (Exception e)
    				{
    					System.out.println(e);

    					e.printStackTrace();

    					flatFileLines.add(flatFileLine);

    					listener.conversionFault(this, flatFileLines, e);
    				}

    			}

    			// Case 3b. (to accommodate the last grouping in the file if required)(file could have had only 1 grouping so didn't reach the Case 3a)
    			if (!groupFinished)
    			{
    				groupBegan = false;

    				groupFinished = true;

    				flatFileLines.add(flatFileLine);

    				writer.write((Element)groupNodeElement);

    				writer.flush();
    			}

    			writer.endElement(this.getTemplateName(),this.getTemplateName(),this.getTemplateName());
    			writer.close();

    			listener.conversionResult(this, xmlFilePath, out); 

    		}
    		catch (IOException e)
    		{
    			System.out.println(e);

    			e.printStackTrace();

    			listener.conversionFault(this, flatFileLines, e);

    			setContinueProcessing(false); // fatal exception
    		}
    		finally
    		{
    			conversionData.closeInputDataReader();

    			if (writer != null)
    			{
    				writer.close();
    			}
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println(e);
    		
    		throw e;
    	}
    }

    /**
     * The associated templateText is to in well-formed XML. This
     * merely maps it to an equivalent Document.
     * @return
     */
    public GroupNode getGroupNode()
    {
        Document document = DOMUtil.buildDocument(getTemplateText());

        GroupNode groupNode = new GroupNode();

        groupNode.unmarshal(document.getRootElement());

        return groupNode;
    }

    /**
     * Checks if there are any ConversionTemplate.TemplateName exist with the specified name.
     * This is done is a separate Hibernate Session.
     * @param templateName
     * @return true if the specified templateName does not yet exist in persistence
     */
    public static boolean templateNameUnique(String templateName)
    {
        return (findBy_TemplateName(templateName) == null);
    }

    /**
     * Finder. There should only ever be one
     * ConversionTemplate by the specified name.
     * @param templateName
     */
    public static ConversionTemplate findBy_TemplateName(String templateName)
    {
        ConversionTemplate conversionTemplate = null;

        String hql = " from ConversionTemplate conversionTemplate" + " where conversionTemplate.TemplateName = :templateName";

        Map params = new EDITMap("templateName", templateName);

        List<ConversionTemplate> results = SessionHelper.executeHQL(hql, params, DATABASE);

        if (!results.isEmpty())
        {
            conversionTemplate = results.get(0);
        }

        return conversionTemplate;
    }

    /**
     * @see #continueProcessing
     * @param continueProcessing
     */
    public void setContinueProcessing(boolean continueProcessing)
    {
        this.continueProcessing = continueProcessing;
    }
   
    public boolean getContinueProcessing()
    {
        return this.continueProcessing;
    }

    /**
     * @see #continueProcessing
     * @return
     */
    public boolean isContinueProcessing()
    {
        return continueProcessing;
    }
    
    /**
     *	@return exportFile Path to write XML file
     *
     *	Currently set to write "Conversion" file to EDITExports/EAS folder
     */
    private String getExportFile(String fileName)
    {
    	EDITDateTime runDate = new EDITDateTime();
        String dateString = runDate.getFormattedDateTime();

        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String exportFile = export1.getDirectory() + fileName;

        return exportFile;
    }
}
