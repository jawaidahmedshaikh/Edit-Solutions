package edit.portal.widget;

import agent.PremiumLevel;
import agent.BonusCriteria;
import agent.BonusProgram;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Feb 8, 2006
 * Time: 8:35:16 AM
 * To change this template use File | Settings | File Templates.
 */

public class ParticipantBonusCriteriaTableModel extends TableModel
{
    public static final String COLUMN_BONUS_AMOUNT = "Bonus Amount";
    public static final String COLUMN_EXCESS_BONUS_AMOUNT = "Excess Bonus Amount";
    public static final String COLUMN_BONUS_BASIS_POINTS = "Bonus Basis Points";
    public static final String COLUMN_EXCESS_BONUS_BASIS_POINTS = "Excess Bonus Basis Points";

    private String[] COLUMN_NAMES =
    {
        COLUMN_BONUS_AMOUNT, COLUMN_EXCESS_BONUS_AMOUNT, COLUMN_BONUS_BASIS_POINTS, COLUMN_EXCESS_BONUS_BASIS_POINTS
    };

    private PremiumLevel premiumLevel;

    public ParticipantBonusCriteriaTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.premiumLevel = (PremiumLevel) appReqBlock.getHttpServletRequest().getAttribute("selectedPremiumLevel");
    }


    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        if (premiumLevel != null)
        {
            BonusCriteria[] bonusCriterias = (BonusCriteria[]) premiumLevel.getBonusCriterias().toArray(new BonusCriteria[premiumLevel.getBonusCriterias().size()]);

            bonusCriterias = (BonusCriteria[]) Util.sortObjects(bonusCriterias, new String[] {"getBonusCriteriaPK"});

            for (int i = 0; i < bonusCriterias.length; i++)
            {
                TableRow tableRow = new BonusCriteriaTableRow(bonusCriterias[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
