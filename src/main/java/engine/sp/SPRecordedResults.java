/*
 * User: sdorman
 * Date: Nov 14, 2008
 * Time: 12:28:01 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import fission.utility.*;
import edit.services.config.*;
import edit.common.vo.*;
import org.dom4j.*;
import org.dom4j.tree.*;

import java.util.*;
import java.io.*;


/**
 * "Holding" object that contains the results of processing scripts in ScriptProcessor.  SPRecorder records the
 * information, SPRecordedResults hangs onto that information.  It is a singleton and holds results for every call
 * to ScriptProcessor.  Results are only cleared if the application server is restarted or if the user calls the
 * clearSPRecordedOperator method.
 */
public class SPRecordedResults
{
    /**
     * Singleton
     */
    private static SPRecordedResults spRecordedResults = null;

    /**
     * List of final results, each run is associated with its operator
     */
    private List<SPRecordedOperator> spRecordedOperators = new ArrayList<SPRecordedOperator>();



    public SPRecordedResults()
    {
    }

    public static SPRecordedResults getSingleton()
    {
        if (spRecordedResults == null)
        {
            spRecordedResults = new SPRecordedResults();
        }

        return spRecordedResults;
    }

    /**
     * Gets the list of SPRecordedOperator objects
     *
     * @return  list of SPRecordedOperator objects
     */
    public List<SPRecordedOperator> getSPRecordedOperators()
    {
        return spRecordedOperators;
    }

    /**
     * Maps this object as an Element
     *
     * @return  Element populated with this object's data
     */
    public Element getAsElement()
    {
        Element element = new DefaultElement("SPRecordedResults");

        List<SPRecordedOperator> spRecordedOperators = this.getSPRecordedOperators();

        for (Iterator iterator = spRecordedOperators.iterator(); iterator.hasNext();)
        {
            SPRecordedOperator spRecordedOperator = (SPRecordedOperator) iterator.next();

            Element spRecordedOperatorElement = spRecordedOperator.getAsElement();

            element.add(spRecordedOperatorElement);
        }

        return element;
    }

    /**
     * Exports the recorded results as an xml file for the specified operator and run.  The file is written to the
     * exports directory defined in the config file.  The filename is auto-generated using the operator and run
     * information.
     *
     * @param operator                  operator whose run data will be exported
     * @param runInformation            runInformation portion of the SPRecordedRun object.  Defines the single run
     *                                  that will be exported
     *
     * @throws Exception if there is a problem processing the file
     */
    public void exportRunData(String operator, String runInformation) throws Exception
    {
        SPRecordedOperator spRecordedOperator = getSPRecordedOperator(operator);

        Element spRecordedOperatorElement = spRecordedOperator.getSPRecordedRunAsElement(runInformation);

        Document document = new DefaultDocument();
        document.add(spRecordedOperatorElement);

        String prettyPrintXML = XMLUtil.prettyPrint(document);

        try
        {
            File file = getExportFile(operator, runInformation);

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            bw.write(prettyPrintXML);

            bw.flush();

            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw e;
        }
    }

    /**
     * Gets the File object to write to when exporting run data
     *
     * @param operator              operator name for the SPRecordedOperator - used as part of the filename
     * @param runInformation        run information for the SPRecordedRun - used as part of the filename
     *
     * @return  File object
     *
     * @throws Exception if there is a problem processing the file
     */
    private File getExportFile(String operator, String runInformation) throws Exception
    {
        EDITExport editExport = ServicesConfig.getEDITExport("ExportDirectory1");

        String directoryPath = editExport.getDirectory();

        String fileName = operator + "_" + runInformation + ".xml";

        try
        {
            //  Replace characters that are not allowed in filenames

            //  Replace slashes with dashes
            fileName = Util.substitute(fileName, "/", "");

            //  Replace colons with nothing
            fileName = Util.substitute(fileName, ":", "");

            //  Replace asterisks with nothing
            fileName = Util.substitute(fileName, "*", "");

            //  Replace spaces with underscore as a separator
            fileName = Util.substitute(fileName, " ", "_");
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw e;
        }

        String fullFileName = directoryPath + fileName;

        if (UtilFile.fileExists(fullFileName))
        {
            throw new Exception("File " + fullFileName + " already exists");
        }

        return new File(fullFileName);
    }

    /**
     * Clears the SPRecordedOperator defined by the operator name from the list of operators
     *
     * @param operator              name of the operator whose corresponding SPRecordedOperator will be removed
     *
     * @return SPRecordedOperator object that is removed
     */
    public SPRecordedOperator clearSPRecordedOperator(String operator)
    {
        SPRecordedOperator spRecordedOperator = this.getSPRecordedOperator(operator);

        this.spRecordedOperators.remove(spRecordedOperator);

        return spRecordedOperator;
    }

    /**
     * Puts the current SPRecordedRun into the list of spRecordedOperators.  Tries to match the specified operator
     * to one already in the list.  If found, it adds the run to that SPRecordedOperator.  If not found, creates
     * a new SPRecordedOperator and adds the run to it.
     *
     * @param operator          operator that processed the run
     * @param spRecordedRun     SPRecordedRun object to be added to the results
     */
    public void putRunIntoResults(String operator, SPRecordedRun spRecordedRun)
    {
        SPRecordedOperator spRecordedOperator = spRecordedResults.getSPRecordedOperator(operator);

        if (spRecordedOperator == null)
        {
            //  This operator is new in the list, create a new SPRecordedOperator and add it to the list
            spRecordedOperator = new SPRecordedOperator(operator);

            this.spRecordedOperators.add(spRecordedOperator);
        }

        spRecordedOperator.addSPRecordedRun(spRecordedRun);
    }

    /**
     * Finds the SPRecordedOperator object that matches the specified operator in the list of SPRecordedOperator objects
     *
     * @param operator          operator name to match in the SPRecordedOperator object
     *
     * @return  found SPRecordedOperator or null if not found
     */
    private SPRecordedOperator getSPRecordedOperator(String operator)
    {
        for (Iterator iterator = spRecordedOperators.iterator(); iterator.hasNext();)
        {
            SPRecordedOperator spRecordedOperator = (SPRecordedOperator) iterator.next();

            if (spRecordedOperator.getOperator().equals(operator))
            {
                return spRecordedOperator;
            }
        }

        return null;
    }
}