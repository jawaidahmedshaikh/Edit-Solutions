package edit.portal.widget;

import agent.PremiumLevel;
import agent.BonusProgram;
import agent.ParticipatingAgent;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Feb 3, 2006
 * Time: 9:11:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class ParticipantPremiumLevelTableModel extends TableModel
{
    public static final String COLUMN_PREMIUM_LEVEL = "Premium Level";
    public static final String COLUMN_PROD_LEVEL_INCR_PCT = "Prod Level Increase Pct";
    public static final String COLUMN_PROD_LEVEL_INCR_AMT = "Prod Level Increase Amt";
    public static final String COLUMN_INCR_STOP_AMT = "Increase Stop Amount";

    private String[] COLUMN_NAMES =
    {
        COLUMN_PREMIUM_LEVEL, COLUMN_PROD_LEVEL_INCR_PCT, COLUMN_PROD_LEVEL_INCR_AMT, COLUMN_INCR_STOP_AMT
    };

    private ParticipatingAgent participatingAgent;

    public ParticipantPremiumLevelTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.participatingAgent = (ParticipatingAgent) appReqBlock.getHttpServletRequest().getAttribute("selectedParticipatingAgent");
    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        if (participatingAgent != null)
        {
            PremiumLevel[] premiumLevels = (PremiumLevel[]) participatingAgent.getPremiumLevels().toArray(new PremiumLevel[participatingAgent.getPremiumLevels().size()]);

            premiumLevels = (PremiumLevel[]) Util.sortObjects(premiumLevels, new String[] {"getPremiumLevelPK"});

            for (int i = 0; i < premiumLevels.length; i++)
            {
                TableRow tableRow = new ParticipantPremiumLevelTableRow(premiumLevels[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
