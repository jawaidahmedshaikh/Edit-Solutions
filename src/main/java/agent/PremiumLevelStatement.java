/*
 * User: cgleason
 * Date: Mar 17, 2006
 * Time: 9:24:23 AM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent;

import edit.common.*;
import edit.common.vo.*;

import java.util.*;
import java.math.*;

import engine.*;
import event.*;



public class PremiumLevelStatement
{
    private PremiumLevel premiumLevel;

    private EDITBigDecimal issueLevelPremium;

    private EDITBigDecimal productLevelIncreasePercent;

    private EDITBigDecimal productLevelIncreaseAmount;

    private EDITBigDecimal increaseStopAmount;

    private BonusCriteriaStatement[] bonusCriteriaStatements;

    private BonusCriteriaStatement bonusCriteriaStatement;

    private ParticipatingAgent participatingAgent;

    public PremiumLevelStatement(ParticipatingAgent participatingAgent)
    {
        this.participatingAgent = participatingAgent;
    }

    public EDITBigDecimal getIncreaseStopAmount()
    {
        return increaseStopAmount;
    }

    public EDITBigDecimal getIssueLevelPremium()
    {
        return issueLevelPremium;
    }

    public EDITBigDecimal getProductLevelIncreaseAmount()
    {
        return productLevelIncreaseAmount;
    }

    public EDITBigDecimal getProductLevelIncreasePercent()
    {
        return productLevelIncreasePercent;
    }

    /**
     * Setup the statement fields from the premiumLevel.  BonusCriterias must have Bonus Commission checks to be included.
     * This means that BonusCommissionHisotry was updated for the checks produced, the update status has
     * been changed to "B".
     */
    public void generatePremiumLevelStatement(PremiumLevel premiumLevel, ProductStructure[] productStructures)
    {
        issueLevelPremium = premiumLevel.getIssuePremiumLevel();

        productLevelIncreasePercent = premiumLevel.getProductLevelIncreasePercent();

        productLevelIncreaseAmount = premiumLevel.getProductLevelIncreaseAmount();

        increaseStopAmount = premiumLevel.getIncreaseStopAmount();

        Set bonusCriterias = premiumLevel.getBonusCriterias();

        List bonusCriteriaStatementsList = new ArrayList();

        if (!bonusCriterias.isEmpty())
        {
            for (Iterator iterator1 = bonusCriterias.iterator(); iterator1.hasNext();)
            {
                BonusCriteria bonusCriteria = (BonusCriteria) iterator1.next();
                Set bonusContributingProducts = bonusCriteria.getBonusContributingProducts();

                for (Iterator iterator2 = bonusContributingProducts.iterator(); iterator2.hasNext();)
                {
                    BonusContributingProduct bonusContributingProduct = (BonusContributingProduct)iterator2.next();

                    BonusCommissionHistory[] bonusCommissionHistories  = BonusCommissionHistory.findBy_PremiumLevel_ProductStructure(premiumLevel, bonusContributingProduct.getProductStructureFK(), participatingAgent);

                    if (bonusCommissionHistories.length > 0)
                    {
                        bonusCriteriaStatement = new BonusCriteriaStatement(bonusCriteria, bonusCommissionHistories);

                        bonusCriteriaStatement.generateBonusCriteriaStatement();

                        bonusCriteriaStatementsList.add(bonusCriteriaStatement);
                    }
                }
            }

            bonusCriteriaStatements = (BonusCriteriaStatement[])bonusCriteriaStatementsList.toArray(new BonusCriteriaStatement[bonusCriteriaStatementsList.size()]);
        }
    }


    /**
     * Format entity fields for the output VO
     * @return
     */
    public PremiumLevelStatementVO getPremiumLevelStatementVO()
    {
        PremiumLevelStatementVO premiumLevelStatementVO = new PremiumLevelStatementVO();
        premiumLevelStatementVO.setIssueLevelPremium(issueLevelPremium.getBigDecimal());
        premiumLevelStatementVO.setProductLevelIncreasePercent(productLevelIncreasePercent.getBigDecimal());
        premiumLevelStatementVO.setProductLevelIncreaseAmount(productLevelIncreaseAmount.getBigDecimal());
        premiumLevelStatementVO.setIncreaseStopAmount(increaseStopAmount.getBigDecimal());

        for (int i = 0; i < bonusCriteriaStatements.length; i++)
        {
            BonusCriteriaStatementVO bonusCriteriaStatementVO = bonusCriteriaStatements[i].getBonusCriteriaStatementVO();

            premiumLevelStatementVO.addBonusCriteriaStatementVO(bonusCriteriaStatementVO);
        }

        return premiumLevelStatementVO;
    }
}
