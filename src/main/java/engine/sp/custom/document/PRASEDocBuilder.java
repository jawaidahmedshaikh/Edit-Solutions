package engine.sp.custom.document;

import engine.sp.SPException;
import engine.sp.SPParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;


public abstract class PRASEDocBuilder extends DefaultDocument
{
    /**
     * The current set of builders within PRASE. Arguably, this should
     * be configurable as this will need to be updated.
     */
    public static final PRASEDocBuilder[] PRASE_DOCUMENT_BUILDERS =
    { 
      new BatchContractSetupDocument(),  
      new CaseDocument(), 
      new ClientDocument(), 
      new CommissionDocument(), 
      new GroupDocument(), 
      new GroupSetupDocument(), 
      new InvestmentAllocationOverrideDocument(), 
      new InvestmentArrayDocument(), 
      new InvestmentDocument(), 
      new LoanSettlementDocument(), 
      new OverdueChargeRemainingDocument(),
      new ProductStructureDocument(),      
      new ReinsuranceDocument(), 
      new RiderDocument(), 
      new SegmentDocument(), 
      new TamraRetestDocument()};

    /**
     * True if this document has already been built. Upon a second call to
     * build this document, the request should be ignored.
     */
    private boolean documentBuilt = false;

    /**
     * The name/values used to build this PRASEDocBuilder.
     */
    private Map<String, String> buildingParameters = new HashMap<String, String>();
    
    /**
     * The Paramters of the Script.
     */
    // We need access to other documents while building the documents.
    // one such example is when a new transaction is being analyzed the investment overrides
    // are in groupsetup document those needs to be used while building investment document.
    // since each document is built sepetately and stored in SPParams this is the 
    // only way to have access to other documents.
    private SPParams spParams;

    /**
     * A unique identifier that is composed of the ClassName of the
     * PRASEDocBuilder and the elements that make up the building parameters.
     */
    private String praseDocKey;
    
    public PRASEDocBuilder(){}

    /**
     * A rigorous constructor that extracts from the specified
     * building parameters only those whose keys match the
     * building parameter names specified for this PRASEDocBuilder.
     * @see #getBuildingParameterNames()
     * @param buildingParameters
     */
    public PRASEDocBuilder(Map<String, String> buildingParameters)
    {
        extractBuildingParameters(buildingParameters);;

        buildPRASEDocKey();
    }

    /**
     * @see #buildingParameters
     * @return
     */
    public Map<String, String> getBuildingParameters()
    {
        return buildingParameters;
    }
    
    /**
     * @see #buildingParameters
     * @param newbuildingParameters
     */
    public void setBuildingParameters(Map<String, String> buildingParameters)
    {
        extractBuildingParameters(buildingParameters);;

        buildPRASEDocKey();
    }    

    /**
     * When called, the implementator will build itself as a DOM.
     * 
     */
    public abstract void build();

    /**
     * The DocumentType of the implementing document.
     * @return PRASEDocBuilder.DocumentType
     */
    public abstract String getRootElementName();

    /**
     * Adds the child-level content of the specified PRASEDocBuilder to 
     * this PRASEDocBuilder. 
     * 
     * For example, if the specified PRASEDocBuilder is Root1.A.B and this
     * PRASEDocBuilder is Root2.C.D, then the final result will be
     * Root2.A.B
     * Root2.C.D
     * 
     * where the contents now all exist under Root2.
     * @param praseDocBuilder
     */
    public void mergeContent(PRASEDocBuilder praseDocBuilder)
    {
        Element thisRoot = getRootElement();

        List<Element> childElements = praseDocBuilder.getRootElement().elements();

        for (Element childElement: childElements)
        {
            thisRoot.add(childElement);
        }
    }

    /**
     * A convenience method that returns the DocumentType of the specified document
     * type as String.
     * @param className
     * @return the converted String to DocumentType
     */
    public static Class<PRASEDocBuilder> getDocumentBuilderClass(String className) throws SPException
    {
        Class<PRASEDocBuilder> documentBuilderClass = null;

        try
        {
            documentBuilderClass = (Class<PRASEDocBuilder>) Class.forName("engine.sp.custom.document." + className);
        }
        catch (ClassNotFoundException e)
        {
            throw new SPException("Unable to identify required PRASEDocBuilder [" + className + "]", SPException.INSTRUCTION_PROCESSING_ERROR, e);
        }

        return documentBuilderClass;
    }

    /**
     * @see #documentBuilt
     * @return
     */
    public boolean isDocumentBuilt()
    {
        return documentBuilt;
    }

    /**
     * see #documentBuilt
     * @param documentBuilt
     */
    public void setDocumentBuilt(boolean documentBuilt)
    {
        this.documentBuilt = documentBuilt;
    }

    /**
     * Every PRASEDocBuilder's building process is driven by a set of
     * supplied Name/Value pairs. For example, is one were to build 
     * a SegmentDocument, it is reasonable to assume that a possible 
     * building parameter Name/Value would be SegmentPK/123456789.
     * 
     * This method is to be fulfilled by each implementor of PRASEDocBuilder
     * and supply the list of all Building Parameter Names used.
     * @return
     */
    public abstract String[] getBuildingParameterNames();

    public boolean equals(Object obj)
    {
        PRASEDocBuilder visitingPRASEDocBuilder = (PRASEDocBuilder) obj;

        String visitingPRASEDocKey = visitingPRASEDocBuilder.getPraseDocKey();

        return (getPraseDocKey().equals(visitingPRASEDocKey));
    }

    public int hashCode()
    {
        return getPraseDocKey().hashCode();
    }

    /**
     * Concatenates the name of the class along with its building parameters to 
     * build a unique key. If the building parameter is null, then a '*' is
     * used in its stead.
     */
    private void buildPRASEDocKey()
    {
        String praseDocKey = "[ClassName:" + getClass().getName() + "]";

        for (String buildingParameterName: getBuildingParameterNames())
        {
            String buildingParameterValue = getBuildingParameters().get(buildingParameterName);
    
            if (buildingParameterValue == null)
            {
                // OK This is flaky - but the VOs being fed into PRASE default all PKs/FKs to 0 if there is no value set.
                // I need to match this.
                if (buildingParameterName.endsWith("FK") || buildingParameterName.endsWith("PK"))
                {
                    buildingParameterValue = "0";
                }
                else
                {
                    buildingParameterValue = "#NULL"; // That's what scripts will do
                }
            }

            praseDocKey += "[" + buildingParameterName + ":" + buildingParameterValue + "]";
        } 

        setPraseDocKey(praseDocKey);
    }
    
    /**
     * Loops through the buildingParameterNames and tries to find its value in the
     * candidateBuildingParameters. If the value is not found, it is not used.
     * @param candidateBuildingParameters
     */
    private void extractBuildingParameters(Map<String, String> candidateBuildingParameters)
    {
        for (String buildingParameterName: getBuildingParameterNames())
        {
            String buildingParameterValue = candidateBuildingParameters.get(buildingParameterName);

            if (buildingParameterValue != null)
            {
                getBuildingParameters().put(buildingParameterName, buildingParameterValue);
            }
        }        
    }

    /**
     * @see #praseDocKey
     * @param praseDocKey
     */
    private void setPraseDocKey(String praseDocKey)
    {
        this.praseDocKey = praseDocKey;
    }

    /**
     * @see #praseDocKey
     */
    public String getPraseDocKey()
    {
        return praseDocKey;
    }
    
    /**
     * The set of dataDocuments that can sent to this service is bounded. That is,
     * the supplied root element name must be representable by one of the
     * PRASEDocBuilders recognized by PRASEWebService such as ClientDocument, GroupDocument,
     * InvestmentDocument, etc. Since this is a bounded set, the mapping
     * of the dataDocumentXML to its PRASEDocBuilder equivalent is a hard-coded mapping.
     * 
     * @param rootElementName
     * @return
     */
    public static PRASEDocBuilder getPRASEDocBuilder(String rootElementName)
    {
        PRASEDocBuilder praseDocBuilder = null;

        try
        {
            for (PRASEDocBuilder praseDocumentBuilder: PRASEDocBuilder.PRASE_DOCUMENT_BUILDERS)
            {
                String currentRootElementName = praseDocumentBuilder.getRootElementName();

                if (currentRootElementName.equals(rootElementName))
                {
                    praseDocBuilder = (PRASEDocBuilder) praseDocumentBuilder.getClass().newInstance();

                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        
        return praseDocBuilder;
    }

    /**
     * Setter.
     * @param spParams
     */
    public void setSPParams(SPParams spParams)
    {
        this.spParams = spParams;
    }

    /**
     * Getter.
     * @return
     */
    public SPParams getSPParams()
    {
        return spParams;
    }
}
