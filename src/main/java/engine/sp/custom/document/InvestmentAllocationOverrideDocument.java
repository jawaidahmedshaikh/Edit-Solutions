package engine.sp.custom.document;

import edit.common.EDITMap;
import edit.common.vo.GroupSetupVO;
import edit.common.vo.InvestmentAllocationOverrideVO;
import edit.common.vo.VOObject;
import edit.services.db.hibernate.SessionHelper;

import edit.services.logging.Logging;
import engine.sp.ScriptProcessor;
import event.InvestmentAllocationOverride;

import fission.utility.DOMUtil;
import fission.utility.Util;
import fission.utility.XMLUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logging.Log;
import logging.LogEvent;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * A simple document that builds all InvestmentAllocationOverrides for the specified 
 * ContractSetupPK.
 * 
 * The structure is as follows:
 * 
 * InvestmentAllocationOverrideDocVO
 * InvestmentAllocationOverrideDocVO.InvestmentAllocationOverrideVO (1 for each InvestmentAllocationOverride)
 */
public class InvestmentAllocationOverrideDocument extends PRASEDocBuilder
{

    /**
     * The parameter that will be specified in WS for the building of this
     * Document.
     */
    public static String BUILDING_PARAMETER_NAME_CONTRACTSETUPPK = "ContractSetupPK";
    /**
     * The parameters that will be extraced from working storage to build this document.
     */
    private static final String[] buildingParameterNames =
    {
        BUILDING_PARAMETER_NAME_CONTRACTSETUPPK
    };
    /**
     * The driving ContractSetupPK.
     */
    private Long contractSetupPK;

    public InvestmentAllocationOverrideDocument()
    {
    }

    /**
     * Constructor. The specified
     * @param buildingParameters
     */
    public InvestmentAllocationOverrideDocument(Map<String, String> buildingParameters)
    {
        super(buildingParameters);

        this.contractSetupPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_CONTRACTSETUPPK));
    }

    public InvestmentAllocationOverrideDocument(Element investmentAllocationOverrideVOElement, String CONTRACTSETUPPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_CONTRACTSETUPPK, CONTRACTSETUPPK));
        this.contractSetupPK = new Long(CONTRACTSETUPPK);
        //Element rootElement = new DefaultElement(getRootElementName());

        //rootElement.add(investmentAllocationOverrideVOElement);

        setRootElement(investmentAllocationOverrideVOElement);

        setDocumentBuilt(true);
    }

    /**
     * Uses the supplied ConstractSetupPK to build the associated InvestmentAllocationOverrideVOs.
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            InvestmentAllocationOverride[] investmentAllocationOverrides = null;

            if (isNewTransaction())
            {
                investmentAllocationOverrides = buildEntitiesAndGetInvestmentAllocationOverridesIfAny();
            }
            else
            {
                investmentAllocationOverrides = InvestmentAllocationOverride.findSeparateBy_ContractSetupPK(getContractSetupPK());
            }

            // The InvestmentAllocationOverride elements should be sorted based on "ToFromStatus" because
            // The Overrides with 'F' status should be processed first.
            investmentAllocationOverrides = (InvestmentAllocationOverride[]) Util.sortObjects(investmentAllocationOverrides, new String[]
                    {
                        "getToFromStatus"
                    });

            Element investmentAllocationOverrideDocVO = new DefaultElement(getRootElementName());

            for (InvestmentAllocationOverride investmentAllocationOverride : investmentAllocationOverrides)
            {
                buildInvestmentAllocationOverrideElement(investmentAllocationOverride, investmentAllocationOverrideDocVO);
            }

            setDocumentBuilt(true);

            setRootElement(investmentAllocationOverrideDocVO);
        }
    }

    /**
     * InvestmentAllocationOverrideVO
     * @return
     */
    public String getRootElementName()
    {
        return "InvestmentAllocationOverrideDocVO";
    }

    public Long getContractSetupPK()
    {
        return contractSetupPK;
    }

    /**
     * Returns true if the driving transaction is new.
     * @return
     */
    private boolean isNewTransaction()
    {
        return this.contractSetupPK == 0 ? true : false;
    }

    /**
     * Builds and returns InvestmentAllocationOverrides if exists from GroupSetup document.
     * @return
     */
    private InvestmentAllocationOverride[] buildEntitiesAndGetInvestmentAllocationOverridesIfAny()
    {
        PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder) getSPParams().getDocumentByName("GroupSetupDocVO");

        List<InvestmentAllocationOverride> investmentAllocationOverrides = new ArrayList<InvestmentAllocationOverride>();

        List<Element> investmentAllocationOverrideElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.InvestmentAllocationOverrideVO", groupSetupDocument);

        for (Element investmentAllocationOverrideElement : investmentAllocationOverrideElements)
        {
            InvestmentAllocationOverride investmentAllocationOverride = (InvestmentAllocationOverride) SessionHelper.mapToHibernateEntity(InvestmentAllocationOverride.class, investmentAllocationOverrideElement, SessionHelper.EDITSOLUTIONS);

            investmentAllocationOverrides.add(investmentAllocationOverride);
        }

        return investmentAllocationOverrides.toArray(new InvestmentAllocationOverride[investmentAllocationOverrides.size()]);
    }

    /**
     * Builds the InvestmentAllocationOverrideVO Element.
     * @param investmentAllocationOverride the driving entity
     * @param investmentAllocationOverrideDocVO the containing Element
     */
    private void buildInvestmentAllocationOverrideElement(InvestmentAllocationOverride investmentAllocationOverride, Element investmentAllocationOverrideDocVO)
    {
        Element investmentAllocationOverrideVOElement = investmentAllocationOverride.getAsElement();

        investmentAllocationOverrideDocVO.add(investmentAllocationOverrideVOElement);
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }

    /*
     * this doc is built from cloundLand becos there may be some change which has not been saved yet
     * this occured in case of a transfer trx, the to trx was deleted and a new one was created
     * but in the override was missing for the one which was created new
     */
    public static void buildInvestmentAllocationOverrideDocFromCloudland(GroupSetupVO groupSetupVO, ScriptProcessor sp)
    {

        try
        {
            // build buildinvestmentAllocationOverrideDoc
            InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs = groupSetupVO.getContractSetupVO(0).getInvestmentAllocationOverrideVO();
            Element investmentAllocationOverrideElement = new DefaultElement("InvestmentAllocationOverrideDocVO");

            //from fund must be added before TO fund in the document, requirement in the PRASE. Sorting is needed for that
            InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOsSorted = (InvestmentAllocationOverrideVO[]) Util.sortObjects(investmentAllocationOverrideVOs, new String[]
                    {
                        "getToFromStatus"
                    });

            for (int i = 0; i < investmentAllocationOverrideVOsSorted.length; i++)
            {

                Element investElem = getElementFromVO(investmentAllocationOverrideVOsSorted[i]);
                investmentAllocationOverrideElement.add(investElem);

            }

            InvestmentAllocationOverrideDocument investmentAllocationOverrideDocumentBuilder =
                    new InvestmentAllocationOverrideDocument(investmentAllocationOverrideElement, groupSetupVO.getContractSetupVO(0).getContractSetupPK() + "");

            //System.out.println("investmentAllocationOverrideDocumentBuilder-" + investmentAllocationOverrideDocumentBuilder.asXML()); //??remove

            sp.loadRootDocument(investmentAllocationOverrideDocumentBuilder);

        }
        catch (Exception e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
    }

    private static Element getElementFromVO(VOObject vo) throws Exception
    {
        Document document = null;

        try
        {
            document = XMLUtil.parse(Util.marshalVO(vo));
        }
        catch (DocumentException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        Element voElement = document.getRootElement();

        return voElement;
    }
}
