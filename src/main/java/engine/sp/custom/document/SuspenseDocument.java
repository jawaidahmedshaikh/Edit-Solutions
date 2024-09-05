/*
 * User: sprasad
 * Date: Aug 30, 2007
 * Time: 12:00:00 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp.custom.document;

import java.util.Map;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import event.*;
import edit.common.*;

public class SuspenseDocument extends PRASEDocBuilder
{

    public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";

    public static final String BUILDING_PARAMETER_NAME_CONTRACT_NUMBER = "ContractNumber";

    protected Suspense[] suspenses;

    private String contractNumber;

    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_SEGMENTPK,
                                                            BUILDING_PARAMETER_NAME_CONTRACT_NUMBER};


 public SuspenseDocument(String contractNumber)
  {
    super(new EDITMap(BUILDING_PARAMETER_NAME_CONTRACT_NUMBER, contractNumber));

    this.contractNumber = contractNumber;
  }

    /**
     * Constructor.
     * @param buildingParams
     */
    public SuspenseDocument(Map<String, String> buildingParams)
    {
        super (buildingParams);

        this.contractNumber = buildingParams.get(BUILDING_PARAMETER_NAME_CONTRACT_NUMBER);        
    }

    /**
     * Document build.
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element suspenseDocVOElement = new DefaultElement(getRootElementName());

            // At this point the business people may need to create new Suspense record in certain scenarios.
            //This will happen if no suspense if found for the contractNumber.
            suspenses = Suspense.findApplyByContractNumber(getContractNumber());

            if (suspenses != null)
            {
                for (int i = 0; i < suspenses.length; i++)
                {
                    buildSuspenseDocument(suspenses[i], suspenseDocVOElement);
                }
            }

            setRootElement(suspenseDocVOElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * The building of the root document includes only the suspense itself
     * @param suspense
     * @param suspenseDocVOElement
     */
    private void buildSuspenseDocument(Suspense suspense, Element suspenseDocVOElement)
    {
        Element suspenseElement = suspense.getAsElement();

        suspenseDocVOElement.add(suspenseElement);
    }

    private String getContractNumber()
    {
        return contractNumber;
    }

    /**
     * 
     * @return
     */
    public String getRootElementName()
    {
        return "SuspenseDocVO";
    }
    
    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
