package engine.sp;

import engine.business.Calculator;

import engine.sp.custom.document.PRASEDocBuilder;

import fission.utility.Util;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * During the development of the Conversion API/Instructions, we came across the need
 * to be able to spawn a ScriptProcessor instance from within the currently running
 * ScriptProcessor instance. This is needed since during a Conversion run, we may
 * have to process/run calculations across multiple products. An instance of
 * ScriptProcessor, by itself, is only able to operate against a single product. Recall
 * that to begin ScriptProcessor, we need to identify the driver script which is always
 * product specific.
 * 
 * When considering this instruction, we also re-evaluated the idea to seek a universal
 * way to kick-off PRASE. This led to the following format instruction:
 * 
 * activateprase (Process, Event, EventType, EffectiveDate, ProductStructurePK, WSVO)
 * 
 * - Process, Event, EventType, and EffectiveDate: The business key elements used to identify the correct product-driven Driver.
 * - ProductStructurePK: The PK of the driving Product Structure.
 * - WSVO: The DOM4J Element that represents the working storage name/value pairs that will be auto-loaded into the new PRASE instance.
 * 
 * Note-1: wsVO is optional. When specified, it is expected to be in the following format:
 * <WSVO>
 *   <Param>
 *       <Name>FooName1</Name>
 *       <Value>FooValue1</Value>
 *   </Param>
 *   <Param...N>
 *       <Name>FooName...N</Name>
 *       <Value>FooValue...N</Value>
 *   </Param...N>
 * </WSVO>
 * 
 * The WSVO is expected to exist as a Document built from the normal create/update instructions for building and populating VOs (i.e. XML).
 * It is possible that we will also allow ResultDocVO elements to be used (e.g. /ResultDocVO/WSVO).
 *      
 * Note-3: The SPOutput of this acitivate PRASE instance is placed in ResultDocVO (e.g. /ResultDocVO/SPOutputVO).
 */
public class Activateprase extends Inst
{

    /**
     * Named element of the rule structure.
     */
    public static final String PARAMETER_PROCESS = "Process";
    /**
     * Named element of the rule structure.
     */
    public static final String PARAMETER_EVENT = "Event";
    /**
     * Named element of the rule structure.
     */
    public static final String PARAMETER_EVENTTYPE = "EventType";
    /**
     * Effective date of the rule structure.
     */
    public static final String PARAMETER_EFFECTIVEDATE = "EffectiveDate";
    /**
     * The targeted ProductStructure (via the PK).
     */
    public static final String PARAMETER_PRODUCTSTRUCTUREPK = "ProductStructurePK";
    /**
     * The name/value pairs to supply as WS values in the to-be-spawned instance of ScriptProcessor (PRASE).
     */
    public static final String PARAMETER_WSVO = "WSVO";

    public Activateprase()
    {
    }

    protected void compile(ScriptProcessor scriptProcessor)
    {
        sp = scriptProcessor;
    }

    /**
     * Executes a separate instance of ScriptProcessor using the expected parameters. The end result (SPOutput) is
     * added to the SPOutput of the calling ScriptProcessor. The scripter may examine the results and decide [to]/[not to] take
     * further action.
     * @param execSP
     * @throws SPException
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        try
        {
            String process = execSP.getWSEntry(PARAMETER_PROCESS);

            String event = execSP.getWSEntry(PARAMETER_EVENT);

            String eventType = execSP.getWSEntry(PARAMETER_EVENTTYPE);

            String effectiveDate = execSP.getWSEntry(PARAMETER_EFFECTIVEDATE);

            String productStructurePK = execSP.getWSEntry(PARAMETER_PRODUCTSTRUCTUREPK);

            Document wsVODocument = execSP.getSPParams().getDocumentByName(SPParams.WSVO);

            SPOutput spOutput = dispatchToPRASE(process, event, eventType, effectiveDate, productStructurePK, wsVODocument);

            if (spOutput != null)
            {
                Element spOutputElement = spOutput.getSPOutputVODocument().getRootElement().createCopy();

                spOutputElement.setDocument(null);

                execSP.getSPParams().getDocumentByName(SPParams.RESULTDOCVO).getRootElement().add(spOutputElement);
            }
        }
        finally
        {
            execSP.incrementInstPtr();
        }
    }

    /**
     * extracts "process, event, eventType, effectiveDate, productStructurePK, wsVO, initialDocumentVO)
     * @return
     */
    private String[] getParameters()
    {
        String line = super.getInstAsEntered();

        int openingParenthesis = line.indexOf("(");

        int closingParenthesis = line.lastIndexOf(")");

        String parameterLine = line.substring(openingParenthesis + 1, closingParenthesis);

        String[] parameters = Util.fastTokenizer(parameterLine, ",", true);

        return parameters;
    }

    /**
     * Uses the specified parameters as the "generic" method in which any client can invoke PRASE. Whether to go through the Calculator
     * interface was debated since that is how any client is expected to invoke PRASE. The fact that this activateprase instruction is
     * already within PRASE. It's odd to go out to PRASE from within PRASE. 
     * Note that we don't use a PRASEDocBuilder in any way to supply an initial document. This was deliberate. We don't see any reason to
     * supply an initial Document. It is expected that the activatedocument instruction(s) will always build from the DB.
     * @param process
     * @param event
     * @param eventType
     * @param effectiveDate
     * @param productStructurePK
     * @param wsVODocument optional - when supplied every name/value pair in the wsVODocument is loaded into the target SP's ws - this mostly supports
     *                                the use of the activatedocument instruction which relies on ws named values to identify the document building key
     * @return
     * @throws SPException
     */
    private SPOutput dispatchToPRASE(String process, String event, String eventType, String effectiveDate, String productStructurePK, Document wsVODocument) throws SPException
    {
        ScriptProcessor sp = null;

        SPOutput spOutput = null;

        try
        {
            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();

            loadWSParameters(sp, wsVODocument);

            // Execute
            sp.loadScript(process, event, eventType, new Long(productStructurePK).longValue(), effectiveDate);

            sp.setNoEditingException(false);

            sp.setAbortOnHardValidationError(false);

            sp.exec();
        }
        finally
        {
            if (sp != null)
            {
                spOutput = sp.getSPOutput();

                sp.close();
            }
        }

        return spOutput;
    }

    /**
     * Queries... 
     * <WSVO>
     *  <Param>
     *      <Name></Name>
     *      <Value></Value>
     *  </Param>
     *  </WSVO>
     *  
     *  ... and placed every Name/Value pair found as WS entries.
     * @param sp the separate ScriptProcessor instance
     * @param wsVODocument
     */
    private void loadWSParameters(ScriptProcessor sp, Document wsVODocument)
    {
        if (wsVODocument != null)
        {
            List<Element> paramElements = wsVODocument.getRootElement().elements("Param");

            for (Element paramElement : paramElements)
            {
                Element nameElement = paramElement.element("Name");

                String paramName = nameElement.getText();

                Element valueElement = paramElement.element("Value");

                String paramValue = valueElement.getText();

                sp.addWSEntry(paramName, paramValue);
            }
        }
    }
}
