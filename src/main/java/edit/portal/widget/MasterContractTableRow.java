/*
 * User: cgleason
 * Date: Dec 13, 2005
 * Time: 2:33:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import contract.*;

import fission.utility.DateTimeUtil;


public class MasterContractTableRow extends TableRow
{
    /**
     * The entity whose column values are displayed.
     */
    private MasterContract masterContract;

    public MasterContractTableRow(MasterContract masterContract)
    {
        super();

        this.masterContract = masterContract;

        populateCellValues(masterContract);
    }

    /**
     * Maps values to the Table row.
     */
    private void populateCellValues(MasterContract masterContract)
    {
        
        String masterContractEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(masterContract.getMasterContractEffectiveDate());

        String masterContractTerminationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(masterContract.getMasterContractTerminationDate());

        getCellValues().put(MasterContractTableModel.COLUMN_MASTER_CONTRACT_NAME, masterContract.getMasterContractName());

        getCellValues().put(MasterContractTableModel.COLUMN_MASTER_CONTRACT_NUMBER, masterContract.getMasterContractNumber());

        getCellValues().put(MasterContractTableModel.STATE, masterContract.getStateCT());
        getCellValues().put(MasterContractTableModel.NO_INTERIM_COVERAGE, masterContract.isNoInterimCoverage() ? "No" : "Yes");

         getCellValues().put(MasterContractTableModel.COLUMN_MASTER_EFFECTIVE_DATE, masterContractEffectiveDate);

         getCellValues().put(MasterContractTableModel.COLUMN_MASTER_TERMINATION_DATE, masterContractTerminationDate);

    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return getMasterContract().getMasterContractPK().toString();
    }

    /**
     * @see #filteredProduct
     * @return
     */
    private MasterContract getMasterContract()
    {
        return masterContract;


    }
}
