/*
 * User: vuppalapati
 * Date: Dec 8, 2005
 * Time: 3:28:25 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import contract.*;



public class MasterContractTableModel extends TableModel
{
    
    public static final String COLUMN_MASTER_CONTRACT_NAME = "Master Contract Name";
    public static final String COLUMN_MASTER_CONTRACT_NUMBER = "Master Contract Number";
    public static final String COLUMN_MASTER_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_MASTER_TERMINATION_DATE = "Termination Date";
    public static final String NO_INTERIM_COVERAGE = "Interim Coverage?";
    public static final String STATE = "State";
    

    private static final String[] COLUMN_NAMES = {COLUMN_MASTER_CONTRACT_NAME,COLUMN_MASTER_CONTRACT_NUMBER, COLUMN_MASTER_EFFECTIVE_DATE,COLUMN_MASTER_TERMINATION_DATE,STATE, NO_INTERIM_COVERAGE};

    /**
     * The targeted ContractGroup.
     */
    private Long filteredProductPK;
    
    public MasterContractTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
        String filteredProductPKStr =appReqBlock.getUserSession().getParameter("filteredProductPK");
        if (filteredProductPKStr != null)
        {
          setFilteredProductPK(new Long(filteredProductPKStr));
        }
    }


    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        if (contractGroupIsPresent())
        {
            MasterContract[] masterContracts = MasterContract.findBy_FilteredProductPK(getFilteredProductPK());

            for (MasterContract masterContract:masterContracts)
            {
                MasterContractTableRow tableRow = new MasterContractTableRow(masterContract);
                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }

    /**
     * Setter.
     * @see #contractGroupPK
     * @param contractGroupPK
     */
    private void setFilteredProductPK(Long filteredProductPK)
    {
      this.filteredProductPK = filteredProductPK;
    }

    /**
     * Getter.
     * @see #contractGroupPK
     * @return
     */
    private Long getFilteredProductPK()
    {
      return filteredProductPK;
    }

    /**
     * True if a ContractGroup has been selected and available to
     * this TableModel.
     * @return
     */
    public boolean contractGroupIsPresent()
    {
      return (getFilteredProductPK() != null);
    }
}
