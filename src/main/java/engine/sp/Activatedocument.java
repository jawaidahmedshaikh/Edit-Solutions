package engine.sp;

import engine.sp.custom.document.PRASEDocBuilder;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.Map;


/**
 * The basic syntax of this instruction is as follows:
 * 
 * activatedocument(FooDocument)
 * 
 * "activatedocument" is the instruction, and "FooDocument" is the name
 * of the document that should be activated.
 * 
 * Once the name of the document is identified, this instruction expects certain
 * value(s) to be in working storage to be able to load the document. For example:
 * 
 * push &SegmentPK
 * pop ws:SegmentPK
 * activatedocument(SegmentDocument)
 * 
 * The above script lines would allow proper activation of the SegmentDocument
 * since the required WS entry of SegmentPK has been made available.
 * 
 * This customized instruction identifies the type of document required via the
 * specified parameter.
 */
public class Activatedocument extends Inst
{
    public Activatedocument()
    {
    }

    protected void compile(ScriptProcessor scriptProcessor)
    {
        sp = scriptProcessor;
    }

    /**
     * Parses the script line for desired document to activate. The document is placed
     * into SPParams at the root level and is immediately available to the script writer.
     * @param scriptProcessor
     */
    protected void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        Class praseDocBuilderClass = PRASEDocBuilder.getDocumentBuilderClass(getDocumentName());

        try
        {
            Constructor constructor = praseDocBuilderClass.getConstructor(new Class[]{ Map.class });

            PRASEDocBuilder praseDocBuilder = (PRASEDocBuilder) constructor.newInstance(new Object[]{sp.getWS()});

            sp.loadDocument(praseDocBuilder);

            sp.incrementInstPtr();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new SPException("A document builder was attempted to be instantiated, but failed [class name =" + praseDocBuilderClass.getName() + "]", SPException.PARAMETER_INVALID_ERROR, e);
        }
    }

    /**
     * The specified praseDocBuildingElements generally contains the total sum of working storage - which is too many.
     * We want to extract only those values which relate to the building parameters of the corresponding document represented by the PRASEDocKey.
     * @param workingStorage
     * @param praseDocBuilderClass
     */
    private Map<String, String> extractPraseDocKeyElements(Map<String, String> workingStorage, Class praseDocBuilderClass)
    {
        Map<String, String> buildingParameterNameValues = new HashMap<String, String>();

        PRASEDocBuilder builder;

        try
        {
            builder = (PRASEDocBuilder) praseDocBuilderClass.newInstance();

            String[] buildingParameterNames = builder.getBuildingParameterNames();

            for (String buildingParameterName: buildingParameterNames)
            {
                String buildingParameterValue = workingStorage.get(buildingParameterName);

                if (buildingParameterValue != null)
                {
                    buildingParameterNameValues.put(buildingParameterName, buildingParameterValue);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return buildingParameterNameValues;
    }

    /**
     * Parses the general instruction syntax of:
     * activatedocument(FooDocument) to identify the "FooDocument" as a string.
     * @return the document name
     */
    private String getDocumentName()
    {
        String line = super.getInstAsEntered();

        int openingParenthesis = line.indexOf("(");

        int closingParenthesis = line.indexOf(")", openingParenthesis);

        String documentName = line.substring(openingParenthesis + 1, closingParenthesis);

        return documentName;
    }


}
