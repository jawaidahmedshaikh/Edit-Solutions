package edit.portal.widget;

import fission.global.AppReqBlock;
import fission.utility.Util;
import edit.portal.widgettoolkit.DoubleTableModel;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Jan 16, 2008
 * Time: 10:52:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class FundActivityFundNameDoubleTableModel extends DoubleTableModel
{
    public static final String COLUMN_FUND_NAME = "Fund Name";

    private FundActivityFundNameTableModel fromTableModel;
    private FundActivityFundNameTableModel toTableModel;

    private String selectedMarketingPackage;

    private final String[] COLUMN_NAMES =
    {
        COLUMN_FUND_NAME
    };

    private AppReqBlock appReqBlock;

    public FundActivityFundNameDoubleTableModel(AppReqBlock appReqBlock)
    {
        this.appReqBlock = appReqBlock;

        buildDoubleTableModel();
    }

    private void buildDoubleTableModel()
    {
        String selectedMarketingPackage = getAppReqBlock().getReqParm("marketingPackage");

        boolean needToRebuildDoubleTableModel = true;

        FundActivityFundNameDoubleTableModel doubleTableModelFromSession = null;

        if (isExistsInSessionScope())
        {
            doubleTableModelFromSession = (FundActivityFundNameDoubleTableModel) getDoubleTableModelFromSessionScope();

            if (selectedMarketingPackage != null)
            {
                if (selectedMarketingPackage.equals(doubleTableModelFromSession.getSelectedMarketingPackage()))
                {
                    needToRebuildDoubleTableModel = false;
                }
            }
        }

        if (needToRebuildDoubleTableModel)
        {
            this.fromTableModel = new FundActivityFundNameTableModel(this.appReqBlock, COLUMN_NAMES);

            this.toTableModel = new FundActivityFundNameTableModel(this.appReqBlock, COLUMN_NAMES);

            populateFromTableModel();
            
            //The toTableModel is never built from the input data only the selections of the fromTableModel
            this.toTableModel.setRowsPopulated(true);

            placeDoubleTableModelInSessionScope();
        }
        else
        {
            this.fromTableModel = (FundActivityFundNameTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.FROM_TABLEMODEL);

            this.toTableModel = (FundActivityFundNameTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.TO_TABLEMODEL);
        }

        setSelectedMarketingPackage(selectedMarketingPackage);
    }

    /**
     * Builds the superset TableModel driven by the Client
     */
    private void populateFromTableModel()
    {
        //Set the switch first or the from table model will place the rows twice
        this.fromTableModel.setRowsPopulated(true);
    
        this.fromTableModel.buildTableRows();
    }

    /**
     * Getter
     * @return
     */
    public String getSelectedMarketingPackage()
    {
        return selectedMarketingPackage;
    }

    /**
     * Setter
     * @param selectedMarketingPackage
     */
    public void setSelectedMarketingPackage(String selectedMarketingPackage)
    {
        this.selectedMarketingPackage = selectedMarketingPackage;
    }

    public void updateState()
    {
        String responseMessage = null;

        String[] selectedIds = Util.fastTokenizer(appReqBlock.getHttpServletRequest().getParameter("selectedIds"), ",");

        FundActivityFundNameTableModel fromTable = (FundActivityFundNameTableModel) getTableModel(FROM_TABLEMODEL);

        FundActivityFundNameTableModel toTable = (FundActivityFundNameTableModel) getTableModel(TO_TABLEMODEL);

        for (int i = 0; i < selectedIds.length; i++)
        {
            if (fromTable.getRow(selectedIds[i]) == null)
            {
                moveRow(DoubleTableModel.TO_TABLEMODEL, selectedIds[i]);
            }
            else if (toTable.getRow(selectedIds[i]) == null)
            {
                moveRow(DoubleTableModel.FROM_TABLEMODEL, selectedIds[i]);
            }
        }

        if (responseMessage != null)
        {
            responseMessage = responseMessage.substring(0, responseMessage.length()-2);
        }

        placeDoubleTableModelInSessionScope();

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
    }

    public TableModel getTableModel(int tableModelNumber)
    {
        FundActivityFundNameTableModel tableModel = null;

        switch (tableModelNumber)
        {
            case DoubleTableModel.FROM_TABLEMODEL:
                 tableModel = fromTableModel;
                 break;

            case DoubleTableModel.TO_TABLEMODEL:
                 tableModel = toTableModel;
                 break;
        }

        return tableModel;
    }

    public void moveRow(int tableModelNumber, String rowId)
    {
        FundActivityFundNameTableModel fromTable = (FundActivityFundNameTableModel) getTableModel(tableModelNumber);

        FundActivityFundNameTableModel toTable = (FundActivityFundNameTableModel) getTableModel((tableModelNumber == FROM_TABLEMODEL) ? TO_TABLEMODEL : FROM_TABLEMODEL);

        TableRow tableRow = fromTable.removeRow(rowId);

        toTable.addRow(tableRow);
    }

    public AppReqBlock getAppReqBlock()
    {
        return this.appReqBlock;
    }
}
