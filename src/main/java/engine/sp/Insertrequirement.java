/*
 * User: gfrosti
 * Date: Oct 4, 2004
 * Time: 9:58:53 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import edit.common.vo.*;
import fission.utility.*;
import org.dom4j.*;
import org.dom4j.io.*;

import java.io.*;

public class Insertrequirement extends Inst
{
    private ScriptProcessor sp;

    private InsertRequirementVO insertRequirementVO;

    public Insertrequirement()
    {

    }

    protected void compile(ScriptProcessor theProcessor) throws SPException
    {
        this.sp = theProcessor;
    }

    /**
     * Adds a Insertrequirement to the output stack.
     * @param execSP
     * @throws SPException
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        InsertRequirementVO[] insertRequirementVOs = parseForInsertRequirementVOs();

        for (int i = 0; i < insertRequirementVOs.length; i++)
        {
            Element insertRequirementElement = buildInsertRequirementElement(insertRequirementVOs[i]);

            sp.addCalculationOutput(insertRequirementElement);    
        }

        sp.incrementInstPtr();
    }

    /**
     * Builds the InsertRequirementVOs in response to each requirementId supplied.
     * @return
     */
    private InsertRequirementVO[] parseForInsertRequirementVOs()
    {
        InsertRequirementVO[] insertRequirementVOs = null;

        String[] requirementIds = getRequirementIds();

        insertRequirementVOs = new InsertRequirementVO[requirementIds.length];

        for (int i = 0; i < requirementIds.length; i++)
        {
            insertRequirementVOs[i] = new InsertRequirementVO();

            insertRequirementVOs[i].setRequirementId(requirementIds[i]);
        }

        return insertRequirementVOs;
    }

    /**
     * Parses the instruction line for the set of requirementIds to insert against.
     * @return
     */
    private String[] getRequirementIds()
    {
        String[] requirementIds = null;

        String line = super.getInstAsEntered();

        final int openingParenthesis = line.indexOf("(");

        final int closingParenthesis = line.indexOf(")", openingParenthesis);

        line = line.substring(openingParenthesis + 1, closingParenthesis);

        final String[] tokens = Util.fastTokenizer(line, ",");

        requirementIds = new String[tokens.length];

        for (int i = 0; i < tokens.length; i++)
        {
            requirementIds[i] = tokens[i].trim();
        }

        return requirementIds;
    }

    /**
     * Maps the InsertRequirementVO to its Element equivalent.
     * @param insertRequirementVO
     * @return
     */
    private Element buildInsertRequirementElement(InsertRequirementVO insertRequirementVO)
    {
        Element insertRequirementElement = null;

        Document document = null;

        try
        {
            String xml = Util.marshalVO(insertRequirementVO);

            SAXReader reader = new SAXReader();

            document = reader.read(new StringReader(xml));

            insertRequirementElement = document.getRootElement();
        }
        catch (DocumentException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return insertRequirementElement;
    }
}
