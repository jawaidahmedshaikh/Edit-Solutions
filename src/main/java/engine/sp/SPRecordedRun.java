/*
 * User: sdorman
 * Date: Jul 18, 2008
 * Time: 10:37:31 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import org.dom4j.*;
import org.dom4j.tree.*;

import java.util.*;

import engine.sp.custom.document.*;
import edit.common.*;
import fission.utility.*;


/**
 * Data structure defining the results recorded by SPRecorder when processing a script in ScriptProcessor
 */
public class SPRecordedRun
{
    /**
     * Information unique to this run of the script processor.  At a minimum, it will contain a date/time stamp
     */
    private String runInformation;

    /**
     * All the script lines recorded as part of this run
     */
    private List<SPRecordedScriptLine> scriptLines = new ArrayList<SPRecordedScriptLine>();

    /**
     * The list of input PRASE documents used during the running of the scripts.
     */
    private List<Document> inputDocuments = new ArrayList<Document>();

    /**
     * The list of output documents created by running the script.  This information comes from SPOutput.CalculationOutputs
     */
    private List<Document> outputDocuments = new ArrayList<Document>();

    /**
     * For each PRASE document, we need to capture the Document and the building parameters used for each
     * Document. We do this in a name/value association.  Building parameters may not exist if the script was
     * kicked off via the "old way" of processing (i.e. not document based - validations are done the old way)
     */
    private Map<Document, Map> buildingParameters = new HashMap<Document, Map>();



   /**
    * Constructor that does nothing.  We don't default the run information here because it explicitly gets set
    * when the loadRunInformation method is called.  The ScriptProcessor does not have all the necessary data for the
    * run information when SPRecordedRun is instantiated.  It calls the load method when it is ready.
    */
    public SPRecordedRun()
    {
    }

    /**
     * Loads the run information with the specified date/time stamp and script processor's run parameters.
     * <P>
     * Sets the date in mm/dd/yyyy format.  This information will only be viewed by a user interface and never stored
     * in the database, therefore, we are justified in setting the front end format here instead of converting it
     * in the getAsElement function.
     *
     * @param dateTimeStamp                 date/time stamp for the start of the run
     * @param productRuleProcessor          ProductRuleProcessor object that will be executed
     */
    public void loadRunInformation(EDITDateTime dateTimeStamp, ProductRuleProcessor productRuleProcessor)
    {
        this.runInformation = DateTimeUtil.formatEDITDateTimeAsMMDDYYYY(dateTimeStamp) + " " +
                productRuleProcessor.getProcessName() + " " +
                productRuleProcessor.getEventName() + " " +
                productRuleProcessor.getEventTypeName();
    }

    public String getRunInformation()
    {
        return runInformation;
    }

    public void setRunInformation(String runInformation)
    {
        this.runInformation = runInformation;
    }

    public List<SPRecordedScriptLine> getScriptLines()
    {
        return scriptLines;
    }

    public void setScriptLines(List<SPRecordedScriptLine> scriptLines)
    {
        this.scriptLines = scriptLines;
    }

    public List<Document> getInputDocuments()
    {
        return inputDocuments;
    }

    public void setInputDocuments(List<Document> inputDocuments)
    {
        this.inputDocuments = inputDocuments;
    }

    public List<Document> getOutputDocuments()
    {
        return outputDocuments;
    }

    public void setOutputDocuments(List<Document> outputDocuments)
    {
        this.outputDocuments = outputDocuments;
    }

    public Map<Document, Map> getBuildingParameters()
    {
        return buildingParameters;
    }

    public void setBuildingParameters(Map<Document, Map> buildingParameters)
    {
        this.buildingParameters = buildingParameters;
    }

    /**
     * Adds a new script line to the run
     *
     * @param type              the type of the line to be added (ex. instruction, push, etc)
     * @param result            the value of the type
     */
    public void addScriptLine(String type, String result)
    {
        //  If the result is null, set it to the String "null".  This prevents XML from complaining when the
        //  information is displayed.
        if (result == null)
        {
            result = "null";
        }

        scriptLines.add(new SPRecordedScriptLine(type, result));
    }

    /**
     * Adds an input document to the run
     *
     * @param inputDocument          document used in the script's processing
     */
    public void addInputDocument(Document inputDocument)
    {
        if (inputDocument instanceof PRASEDocBuilder)
        {
            addPRASEDocBuilderDocument((PRASEDocBuilder)inputDocument);
        }
        else
        {
            Document clonedDocument = (Document) inputDocument.clone();

            getInputDocuments().add(clonedDocument);
        }
    }

    /**
     * Maps this object as an Element
     *
     * @return  Element populated with this object's data
     */
    public Element getAsElement()
    {
        Element element = new DefaultElement("SPRecordedRun");

        Element runInformationElement = new DefaultElement("RunInformation");
        runInformationElement.setText(this.getRunInformation());
        element.add(runInformationElement);

        // SPRecordedScriptLines

        List<SPRecordedScriptLine> scriptLines = this.getScriptLines();

        for (Iterator iterator = scriptLines.iterator(); iterator.hasNext();)
        {
            SPRecordedScriptLine spRecordedScriptLine = (SPRecordedScriptLine) iterator.next();

            Element scriptLineElement = spRecordedScriptLine.getAsElement();

            element.add(scriptLineElement);
        }

        // Input Documents

        List<Document> inputDocuments = this.getInputDocuments();

        for (Iterator iterator = inputDocuments.iterator(); iterator.hasNext();)
        {
            Document document = (Document) iterator.next();

            Element documentElement = getDocumentAsElement(document, "InputDocument");

            element.add(documentElement);
        }

        //  Output Documents

        List<Document> outputDocuments = this.getOutputDocuments();

        for (Iterator documentIterator = outputDocuments.iterator(); documentIterator.hasNext();)
        {
            Document document = (Document) documentIterator.next();

            Element documentElement = getDocumentAsElement(document, "OutputDocument");

            element.add(documentElement);
        }

        return element;
    }

    /**
     * Gets a Document as an Element with the specified Element name
     *
     * @param document                  Document to be converted to an Element
     * @param elementName               name of Element
     *
     * @return Document as an Element object
     */
    private Element getDocumentAsElement(Document document, String elementName)
    {
         Element documentElement = new DefaultElement(elementName);

        //  Must "convert" the Document to an Element, parse out the declaration then put the string to an Element
        String parsedXML = XMLUtil.parseOutXMLDeclaration(document.asXML());

        Element parsedElement = XMLUtil.getElementFromXMLString(parsedXML);

        documentElement.add(parsedElement);

        return documentElement;
    }

    /**
     *
     * @param praseDocBuilder
     */
    private void addPRASEDocBuilderDocument(PRASEDocBuilder praseDocBuilder)
    {
        Document clonedPRASEDocument = (Document) praseDocBuilder.getDocument().clone();

        Map buildingParameters = praseDocBuilder.getBuildingParameters();

//        getBuildingParameters().put(clonedPRASEDocument, buildingParameters);

        getInputDocuments().add(clonedPRASEDocument);
    }
}
