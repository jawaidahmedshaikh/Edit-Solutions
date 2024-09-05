package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.FundVO;
import fission.global.AppReqBlock;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Jan 15, 2008
 * Time: 1:29:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FundActivityFundNameTableModel extends TableModel
{
    public FundActivityFundNameTableModel(AppReqBlock appReqBlock, String[] columnNames)
    {
        super(appReqBlock);
    
        getColumnNames().addAll(Arrays.asList(columnNames));
    }

    protected void buildTableRows()
    {
        String selectedMarketingPackage = getAppReqBlock().getReqParm("marketingPackage");

        FundVO[] fundVOs = null;

        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        if (selectedMarketingPackage != null)
        {
            if (selectedMarketingPackage.equalsIgnoreCase("All"))
            {
                fundVOs = calcLookup.getAllFunds();
            }
            else
            {
                fundVOs = calcLookup.getFundsByMarketingPackage(selectedMarketingPackage);
            }
        }

        if (fundVOs != null)
        {
            for (int i = 0; i < fundVOs.length; i++)
            {
                FundVO fundVO = fundVOs[i];

                TableRow tableRow = new FundActivityFundNameTableRow(fundVO);

                super.addRow(tableRow);
            }
        }
    }
}