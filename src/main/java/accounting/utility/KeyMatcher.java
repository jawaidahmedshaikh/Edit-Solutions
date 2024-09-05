/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 17, 2002
 * Time: 2:41:23 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.utility;

import edit.common.vo.ProductStructureVO;
import edit.common.vo.ElementCompanyRelationVO;
import edit.common.vo.ElementStructureVO;
import edit.common.vo.ElementVO;
import fission.dm.valueobject.ValueObject;
import fission.utility.Util;

import java.util.Map;

import engine.*;

/**
 * This class uses a weighting algorithm to find the best possible
 * key match. The best match comes from supplying the algorithm two
 * things:
 */
public class KeyMatcher  {

//*******************************
//          Variables
//*******************************

    private static final int WEIGHT_LEVEL_13 = 14;
    private static final int WEIGHT_LEVEL_12 = 13;
    private static final int WEIGHT_LEVEL_11 = 12;
    private static final int WEIGHT_LEVEL_10 = 11;
    private static final int WEIGHT_LEVEL_9  = 10;
    private static final int WEIGHT_LEVEL_8  = 9;
    private static final int WEIGHT_LEVEL_7  = 8;
    private static final int WEIGHT_LEVEL_6  = 7;
	private static final int WEIGHT_LEVEL_5  = 6;
	private static final int WEIGHT_LEVEL_4  = 5;
	private static final int WEIGHT_LEVEL_3  = 4;
	private static final int WEIGHT_LEVEL_2  = 3;
	private static final int WEIGHT_LEVEL_1  = 2;
	private static final int WEIGHT_LEVEL_0  = 0;

	private static final int WEIGHT_LEVEL_WILD_CARD_MATCH = 1;


//*******************************
//          Public Methods
//*******************************

	/**
     * Returns the best matching ElementStructureVO using a weighted algorithm
     * @param drivingElement
     * @param drivingProductInfo
     * @param drivingStructure
     * @param elementPool
     * @param productStructures
     * @return
     * @throws Exception
     */
	public static ElementStructureVO getBestMatch(ElementVO drivingElement,
                                                   Map drivingProductInfo,
                                                    ElementStructureVO drivingStructure,
                                                     ElementVO[] elementPool,
                                                       Map productStructures)
                                                 throws Exception {

		double bestWeight = 0;
        double newWeight  = 0;
		ElementStructureVO bestKey  = null;

		for (int index = 0; index < elementPool.length; index++) {

            ElementStructureVO[] elementStructureVOs = elementPool[index].getElementStructureVO();

            for (int z = 0; z < elementStructureVOs.length; z++) {

                newWeight = weighAllCompanies(drivingElement,
                                               drivingProductInfo,
                                                drivingStructure,
                                                 elementPool[index],
                                                  elementStructureVOs[z],
                                                   productStructures);

                if (newWeight > 0) {

                    if (bestWeight <= newWeight) {

                        bestWeight = newWeight;
                        bestKey = elementStructureVOs[z];
                    }
                }
            }
		}

		return bestKey;
	}


//*******************************
//          Private Methods
//*******************************

	/**
     *  Weights a single, candidateKey against the drivingKey
     * @param drivingElement
     * @param drivingProductInfo
     * @param drivingStructure
     * @param candidateElement
     * @param candidateStructure
     * @param productStructures
     * @return
     * @throws Exception
     */
	private static double weighAllCompanies(ElementVO drivingElement,
                                             Map drivingProductInfo,
                                              ElementStructureVO drivingStructure,
 	    						               ElementVO candidateElement,
                                                ElementStructureVO candidateStructure,
                                                 Map productStructures)
                                           throws Exception {

		double bestWeight = 0;
        ProductStructureVO productStructureVO = null;

        ElementCompanyRelationVO[] elementCompanyRelationVOs = candidateElement.getElementCompanyRelationVO();

        for (int index = 0; index < elementCompanyRelationVOs.length; index++) {

            productStructureVO = (ProductStructureVO) productStructures.
                                  get(new Long(elementCompanyRelationVOs[index].getProductStructureFK()));

            if (productStructureVO == null)
            {
                productStructureVO = (ProductStructureVO) ProductStructure.findByPK(elementCompanyRelationVOs[index].getProductStructureFK()).getVO();
            }

			bestWeight = Math.max(bestWeight,
								  weightKey(drivingElement,
                                             drivingProductInfo,
                                              drivingStructure,
                                               candidateElement,
                                                candidateStructure,
                                                 productStructureVO));
		}

		return bestWeight;
	}

    private static double weightKey(ElementVO drivingElement,
                                     Map drivingProductInfo,
                                      ElementStructureVO drivingStructure,
 	    						       ElementVO candidateElement,
                                        ElementStructureVO candidateStructure,
                                        ProductStructureVO productStructureVO)
                                   throws Exception {

		double keyWeight = 1;

        Company company = Company.findByPK(new Long(productStructureVO.getCompanyFK()));

		keyWeight *= weightParam((String)drivingProductInfo.get("companyName"),
								 company.getCompanyName(),
								 WEIGHT_LEVEL_13);

		keyWeight *= weightParam((String)drivingProductInfo.get("marketingPackage"),
								 productStructureVO.getMarketingPackageName(),
								 WEIGHT_LEVEL_12);

		keyWeight *= weightParam((String)drivingProductInfo.get("groupProduct"),
								 productStructureVO.getGroupProductName(),
								 WEIGHT_LEVEL_11);

		keyWeight *= weightParam((String)drivingProductInfo.get("area"),
								 productStructureVO.getAreaName(),
								 WEIGHT_LEVEL_10);

		keyWeight *= weightParam((String)drivingProductInfo.get("businessContract"),
								 productStructureVO.getBusinessContractName(),
								 WEIGHT_LEVEL_9);

        keyWeight *= weightParam(drivingElement.getProcess(),
                                 candidateElement.getProcess(),
                                 WEIGHT_LEVEL_8);

        keyWeight *= weightParam(drivingElement.getEvent(),
                                 candidateElement.getEvent(),
                                 WEIGHT_LEVEL_7);

        keyWeight *= weightParam(drivingElement.getEventType(),
                                 candidateElement.getEventType(),
                                 WEIGHT_LEVEL_6);

        keyWeight *= weightParam(drivingStructure.getMemoCode(),
                                 candidateStructure.getMemoCode(),
                                 WEIGHT_LEVEL_5);

        keyWeight *= weightLongParam(drivingStructure.getCertainPeriod(),
                                    candidateStructure.getCertainPeriod(),
                                    WEIGHT_LEVEL_4);

        keyWeight *= weightParam(Util.initString(drivingStructure.getQualNonQualCT(),""),
                                 candidateStructure.getQualNonQualCT(),
                                 WEIGHT_LEVEL_3);

        keyWeight *= weightLongParam(drivingStructure.getFundFK(),
                                    candidateStructure.getFundFK(),
                                    WEIGHT_LEVEL_2);

        keyWeight *= weightLongParam(drivingStructure.getChargeCodeFK(),
                                    candidateStructure.getChargeCodeFK(),
                                    WEIGHT_LEVEL_1);

		return keyWeight;
	}

	/**
	 * Weights a single param of the candidateKey against the drivingKey
	 *
	 * @param drivingParam
	 * @param candidateParam
	 * @param weightLevel the current weight level (there are 5)
	 * @return the weight value of the candidateParam
	 */
	private static int weightParam(String drivingParam,
								   String candidateParam,
								   int weightLevel) {

		if (drivingParam.equals(candidateParam)) {

			return weightLevel;
		}
		else if (candidateParam.equals(ValueObject.DEFAULT_CHAR)) {

			return WEIGHT_LEVEL_WILD_CARD_MATCH;
		}
		else {

			return WEIGHT_LEVEL_0;
		}
	}

    private static int weightLongParam(long drivingParam,
								       long candidateParam,
								        int weightLevel) {

		if (drivingParam == candidateParam) {

			return weightLevel;
		}
		else if (candidateParam == (ValueObject.DEFAULT_INT)) {

			return WEIGHT_LEVEL_WILD_CARD_MATCH;
		}
		else {

			return WEIGHT_LEVEL_0;
		}
	}
}