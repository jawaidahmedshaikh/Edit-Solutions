/*
 * User: sdorman
 * Date: Jul 16, 2008
 * Time: 12:28:01 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import fission.utility.*;
import edit.services.db.hibernate.*;
import edit.services.command.*;
import edit.common.vo.*;
import edit.common.*;
import org.dom4j.*;
import org.dom4j.tree.*;

import java.util.*;


/**
 * Records the step-by-step processing of the PRASE script to aid in script debugging.
 */
public class SPRecorder
{
    /**
     * Run that is currently being recorded
     */
    private SPRecordedRun currentSPRecordedRun;

     /**
     * A flag to be placed in ThreadLocal denoting whether the script processing steps should be recorded or not.
     */
    public static final String RECORD_SP = "RECORD_SP";

     /**
      * A convenience variable to compliment "YES".
      */
    public static final String RECORD_SP_YES = "YES";

    /**
     * A convenience variable to compliment "YES".
     */
    public static final String RECORD_SP_NO = "NO";

    /**
     * Default operator if the operator can not be found
     */
    public static final String OPERATOR_NOT_AVAILABLE = "Operator N/A";


    public SPRecorder()
    {
        currentSPRecordedRun = new SPRecordedRun();
    }

    /**
     * Closes the current run and adds the results to the list of SPRecordedOperators
     *
     * @param spOutput          SPOutput object from ScriptProcessor.  Holds output information that will be placed
     *                          in the results
     */
    public void close(SPOutput spOutput)
    {
        String operator = getOperator();

        putRunIntoResults(operator, spOutput);

        //  Clear out the current run's results in case the same ScriptProcessorImpl gets used again from the pool
        this.currentSPRecordedRun = new SPRecordedRun();
    }

    /**
     * Loads the run information into the SPRecordedRun object
     *
     * @param dateTimeStamp                 date/time stamp for the start of the run
     * @param productRuleProcessor          ProductRuleProcessor object that will be executed
     */
    public void loadRunInformation(EDITDateTime dateTimeStamp, ProductRuleProcessor productRuleProcessor)
    {
        this.currentSPRecordedRun.loadRunInformation(dateTimeStamp, productRuleProcessor);
    }

    /**
     * Determines if the ThreadLocal has a flag denoting that the currently running script should be recorded
     *
     * @return true if the flag exists in ThreadLocal, false otherwise
     */
    public boolean shouldRecord()
    {
        String createTest = Util.initString((String) SessionHelper.getFromThreadLocal(RECORD_SP), RECORD_SP_NO);

        return createTest.equals(RECORD_SP_YES);
    }

    /**
     * Records the instruction
     *
     * @param inst              instruction to be recorded
     */
    public void recordInstruction(Inst inst)
    {
        currentSPRecordedRun.addScriptLine(SPRecordedScriptLine.SCRIPT_LINE_TYPE_INSTRUCTION, inst.getInstAsEntered());
    }

    /**
     * Records the results of executing the push
     *
     * @param pushElement       pushed object
     */
    public void recordPush(String pushElement)
    {
        if (pushElement == null)
        {
            currentSPRecordedRun.addScriptLine(SPRecordedScriptLine.SCRIPT_LINE_TYPE_PUSH, pushElement);
        }
        else
        {
            currentSPRecordedRun.addScriptLine(SPRecordedScriptLine.SCRIPT_LINE_TYPE_PUSH, pushElement.toString());
        }
    }

    /**
     * Records the results of executing the pop
     *
     * @param popElement        popped object
     */
    public void recordPop(String popElement)
    {
        if (popElement != null)
        {
            currentSPRecordedRun.addScriptLine(SPRecordedScriptLine.SCRIPT_LINE_TYPE_POP, popElement.toString());
        }
    }

    /**
     * Clones and then stores the specified Document. Cloning is necessary since the script may be modifying the state of
     * the Document later on. We need to capture the initial state.
     *
     * @param document          document used to execute the run
     */
    public void recordInputDocument(Document document)
    {
        this.currentSPRecordedRun.addInputDocument(document);
    }

    /**
     * Records a document that is an xml string
     *
     * @param xmlDocument       document used to execute the run, in xml format
     */
    public void recordInputDocument(String xmlDocument)
    {
        try
        {
            Document document = XMLUtil.parse(xmlDocument);

            recordInputDocument(document);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Records a document that is a VOObject
     *
     * @param voObject          document used to execute the run, as a VOObject
     */
    public void recordInputDocument(VOObject voObject)
    {
        recordInputDocument(Util.marshalVO(voObject));
    }

    /**
     * Gets the operator running the script.  Gets it from ThreadLocal because we don't have access to the front end
     * from here.
     *
     * @return  login name of operator running the script.  Default name is returned if user didn't log in.
     */
    private String getOperator()
    {
        return Util.initString((String) SessionHelper.getFromThreadLocal(SEGRequestDispatcher.OPERATOR_NAME), OPERATOR_NOT_AVAILABLE);
    }

    /**
     * Puts the current SPRecordedRun into the SPRecordedResults.
     *
     * @param operator          operator that processed the run
     * @param spOutput          SPOutput object from ScriptProcessor.  Holds output information that will be placed
     *                          in the results
     */
    private void putRunIntoResults(String operator, SPOutput spOutput)
    {
        SPRecordedResults spRecordedResults = SPRecordedResults.getSingleton();

        List<Element> calculationOutputElements = spOutput.getCalculationOutputs();

        List<Document> outputDocuments = convertToOutputDocuments(calculationOutputElements);

        this.currentSPRecordedRun.setOutputDocuments(outputDocuments);

        spRecordedResults.putRunIntoResults(operator, this.currentSPRecordedRun);
    }

    /**
     * Converts CalculationOutput Elements to output Documents
     *
     * @param calculationOutputElements         Element objects containing calculation output
     *
     * @return  List of Document objects containing the CalculationOutput Elements
     */
    private List<Document> convertToOutputDocuments(List<Element> calculationOutputElements)
    {
        List<Document> outputDocuments = new ArrayList<Document>();

        for (Iterator iterator = calculationOutputElements.iterator(); iterator.hasNext();)
        {
            Element element = (Element) iterator.next();

            //  Clone the Element because it already belongs to an existing document (SPOutputVO)
            Element clonedElement = (Element) element.clone();

            Document document = new DefaultDocument();

            document.setRootElement(clonedElement);

            outputDocuments.add(document);
        }

        return outputDocuments;
    }
}

