package edit.portal.widget;

import agent.AgentGroup;
import agent.ContributingProduct;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.*;
import edit.services.db.hibernate.SessionHelper;
import fission.global.AppReqBlock;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 11, 2006
 * Time: 1:42:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContributingProductTableModel extends TableModel
{
    public static final String COLUMN_COMPANY_STRUCTURE = "Company Structure";
    public static final String COLUMN_START_DATE = "Start Date";
    public static final String COLUMN_STOP_DATE = "Stop Date";
    private static final String[] COLUMN_NAMES = {COLUMN_COMPANY_STRUCTURE, COLUMN_START_DATE, COLUMN_STOP_DATE};
    private AgentGroup agentGroup;

    /**
     * ContributingProduct(s) are defined by their parent AgentGroup.
     *
     * @param appReqBlock
     * @param scope
     * @param agentGroup
     */
    public ContributingProductTableModel(AppReqBlock appReqBlock, int scope, AgentGroup agentGroup)
    {
        super(appReqBlock);

        this.agentGroup = agentGroup;

        // Set column names
        getColumnNames().add(COLUMN_COMPANY_STRUCTURE);
        getColumnNames().add(COLUMN_START_DATE);
        getColumnNames().add(COLUMN_STOP_DATE);
    }

    /**
     * A convenience constructor so that the selected row can be acquired.
     *
     * @param appReqBlock
     * @param scope
     * @param companyStructure
     */
    public ContributingProductTableModel(AppReqBlock appReqBlock, int scope)
    {
        this(appReqBlock, scope, null);
    }

    /**
     * Builds the set of rows based on the ContributingProducts that have been mapped to the currently selected
     * AgentGroup.
     */
    protected void buildTableRows()
    {
        Set contributingProducts = agentGroup.getContributingProducts();

        for (Iterator iterator = contributingProducts.iterator(); iterator.hasNext();)
        {
            ContributingProduct contributingProduct = (ContributingProduct) iterator.next();

            ContributingProductTableRow tableRow = new ContributingProductTableRow(contributingProduct);

            getRows().add(tableRow);
        }

        Collections.sort(getRows());
    }

    /**
     * Convenience method to convert the selected pks as String(s) to Long(s).
     * @return Long[]
     */
    public Long getSelectedCompanyStructurePK()
    {
        String selectedPK = getSelectedRowIdFromRequestScope();

        return new Long(selectedPK);
    }
}
