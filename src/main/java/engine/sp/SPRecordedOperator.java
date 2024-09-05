/*
 * User: sdorman
 * Date: Aug 27, 2008
 * Time: 11:30:30 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import org.dom4j.*;
import org.dom4j.tree.*;

import java.util.*;

/**
 * Data structure defining the operator and all the runs recorded for that operator when processing a script in ScriptProcessor
 */
public class SPRecordedOperator
{
    /**
     *  Operator logged in at the time the script was processed
     */
    private String operator;

    /**
     *  List of runs executed by the operator.  Each time ScriptProcessor is called, a new run is created.
     */
    private List<SPRecordedRun> spRecordedRuns = new ArrayList<SPRecordedRun>();


    /**
     * Constructor takes an operator as an argument
     *
     * @param operator          operator processing the script
     */
    public SPRecordedOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public List<SPRecordedRun> getSPRecordedRuns()
    {
        return spRecordedRuns;
    }

    public void setSPRecordedRuns(List<SPRecordedRun> spRecordedRuns)
    {
        this.spRecordedRuns = spRecordedRuns;
    }

    public void addSPRecordedRun(SPRecordedRun spRecordedRun)
    {
        this.spRecordedRuns.add(spRecordedRun);
    }

    /**
     * Maps this object as an Element
     *
     * @return  Element populated with this object's data
     */
    public Element getAsElement()
    {
        Element element = new DefaultElement("SPRecordedOperator");

        Element operatorElement = new DefaultElement("Operator");
        operatorElement.setText(this.getOperator());
        element.add(operatorElement);

        List<SPRecordedRun> spRecordedRuns = this.getSPRecordedRuns();

        for (Iterator iterator = spRecordedRuns.iterator(); iterator.hasNext();)
        {
            SPRecordedRun spRecordedRun = (SPRecordedRun) iterator.next();

            Element spRecordedRunElement = spRecordedRun.getAsElement();

            element.add(spRecordedRunElement);
        }

        return element;
    }

    /**
     * Maps a single SPRecordedRun within the SPRecordedOperator as an Element
     *
     * @param runInformation        runInformation section of an SPRecordedRun.  Identifies which run to get as an Element
     *
     * @return  Element populated with the SPRecordedRun's and operator's data
     */
    public Element getSPRecordedRunAsElement(String runInformation)
    {
        Element element = new DefaultElement("SPRecordedOperator");

        Element operatorElement = new DefaultElement("Operator");
        operatorElement.setText(this.getOperator());
        element.add(operatorElement);

       SPRecordedRun spRecordedRun = getSPRecordedRun(runInformation);

       Element spRecordedRunElement = spRecordedRun.getAsElement();

       element.add(spRecordedRunElement);

        return element;
    }

    /**
     * Finds the SPRecordedRun object that matches the specified operator in the list of SPRecordedRun objects
     *
     * @param runInformation    string of run information in the SPRecordedRun object
     *
     * @return  found SPRecordedRun or null if not found
     */
    public SPRecordedRun getSPRecordedRun(String runInformation)
    {
        for (Iterator iterator = spRecordedRuns.iterator(); iterator.hasNext();)
        {
            SPRecordedRun spRecordedRun = (SPRecordedRun) iterator.next();

            if (spRecordedRun.getRunInformation().equals(runInformation))
            {
                return spRecordedRun;
            }
        }

        return null;
    }
}
