package edit.portal.widget;

import agent.PremiumLevel;
import agent.BonusProgram;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Feb 1, 2006
 * Time: 2:40:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class AgentBonusPremiumLevelTableModel extends TableModel
{
    public static final String COLUMN_PREMIUM_LEVEL = "Premium Level";
    public static final String COLUMN_PROD_LEVEL_INCR_PCT = "Prod Level Increase Pct";
    public static final String COLUMN_PROD_LEVEL_INCR_AMT = "Prod Level Increase Amt";
    public static final String COLUMN_INCR_STOP_AMT = "Increase Stop Amount";

    private String[] COLUMN_NAMES =
    {
        COLUMN_PREMIUM_LEVEL, COLUMN_PROD_LEVEL_INCR_PCT, COLUMN_PROD_LEVEL_INCR_AMT, COLUMN_INCR_STOP_AMT
    };

    private BonusProgram bonusProgram;

    public AgentBonusPremiumLevelTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        if (appReqBlock.getReqParm("bonusProgramPK") != null)
        {
            Long bonusProgramPK = new Long(appReqBlock.getReqParm("bonusProgramPK"));

            this.bonusProgram = BonusProgram.findByPK(bonusProgramPK);
        }

    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        if (bonusProgram != null)
        {
            PremiumLevel[] premiumLevels = (PremiumLevel[]) bonusProgram.getPremiumLevels().toArray(new PremiumLevel[bonusProgram.getPremiumLevels().size()]);

            premiumLevels = (PremiumLevel[]) Util.sortObjects(premiumLevels, new String[] {"getPremiumLevelPK"});

            for (int i = 0; i < premiumLevels.length; i++)
            {
                TableRow tableRow = new AgentBonusPremiumLevelTableRow(premiumLevels[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
