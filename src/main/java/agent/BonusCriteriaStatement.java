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

import event.*;


public class BonusCriteriaStatement
{
    private BonusCriteria bonusCriteria;
    private EDITBigDecimal bonusAmount;
    private EDITBigDecimal bonusBasisPoint;
    private EDITBigDecimal excessBonusAmount;
    private EDITBigDecimal excessBonusBasisPoint;
    private EDITBigDecimal excessPremiumLevel;
    private BonusStatementLine[] bonusStatementLines;
    private BonusCommissionHistory[] bonusCommissionHistories;

    public BonusCriteriaStatement(BonusCriteria bonusCriteria, BonusCommissionHistory[] bonusCommissionHistories)
    {
        this.bonusCriteria = bonusCriteria;

        this.bonusCommissionHistories = bonusCommissionHistories;

    }

    public EDITBigDecimal getBonusAmount()
    {
        return bonusAmount;
    }

    public EDITBigDecimal getBonusBasisPoint()
    {
        return bonusBasisPoint;
    }

    public EDITBigDecimal getExcessBonusAmount()
    {
        return excessBonusAmount;
    }

    public EDITBigDecimal getExcessBonusBasisPoint()
    {
        return excessBonusBasisPoint;
    }

    /**
     * BonusCommissionHistory must have been processed for Bonus Checks to have the BonusCriteria
     * part of the Statement
     */
    public void generateBonusCriteriaStatement()
    {
        bonusAmount = bonusCriteria.getBonusAmount();

        bonusBasisPoint = bonusCriteria.getBonusBasisPoint();

        excessBonusAmount = bonusCriteria.getExcessBonusAmount();

        excessBonusBasisPoint = bonusCriteria.getExcessBonusBasisPoint();

        excessPremiumLevel = bonusCriteria.getExcessPremiumLevel();

        buildBonusStatementLines(bonusCommissionHistories);
    }


    /**
      * The set of BonusStatementLines for this BonusStatement.
      */
     private void buildBonusStatementLines(BonusCommissionHistory[] bonusCommissionHistories)
     {
         List statementLines = new ArrayList();

         for (int i = 0; i < bonusCommissionHistories.length; i++)
         {
             BonusCommissionHistory bonusCommissionHistory = bonusCommissionHistories[i];

             BonusStatementLine bonusStatementLine = new BonusStatementLine(bonusCommissionHistory);

             bonusStatementLine.generateBonusStatementLine();

             statementLines.add(bonusStatementLine);
         }

         bonusStatementLines = (BonusStatementLine[]) statementLines.toArray(new BonusStatementLine[statementLines.size()]);
     }

    public BonusCriteriaStatementVO getBonusCriteriaStatementVO()
    {
        BonusCriteriaStatementVO bonusCriteriaStatementVO = new BonusCriteriaStatementVO();

        bonusCriteriaStatementVO.setBonusAmount(bonusAmount.getBigDecimal());
        bonusCriteriaStatementVO.setBonusBasisPoint(bonusBasisPoint.getBigDecimal());
        bonusCriteriaStatementVO.setExcessBonusAmount(excessBonusAmount.getBigDecimal());
        bonusCriteriaStatementVO.setExcessBonusBasisPoint(excessBonusBasisPoint.getBigDecimal());
        bonusCriteriaStatementVO.setExcessPremiumLevel(excessPremiumLevel.getBigDecimal());

        if (bonusStatementLines != null)
        {
            for (int i = 0; i < bonusStatementLines.length; i++)
            {
                bonusCriteriaStatementVO.addBonusStatementLineVO(bonusStatementLines[i].getBonusStatementLineVO());
            }
        }

        return bonusCriteriaStatementVO;
    }
}
