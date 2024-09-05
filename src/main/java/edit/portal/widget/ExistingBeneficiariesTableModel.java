/*
 * User: sprasad
 * Date: May 17, 2005
 * Time: 2:13:48 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.ContractClient;
import contract.ContractClientAllocation;
import contract.Segment;
import edit.common.EDITBigDecimal;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;



public class ExistingBeneficiariesTableModel extends TableModel
{
    public static final String COLUMN_TAX_ID = "Tax ID";
    public static final String COLUMN_NAME= "Name";
    public static final String COLUMN_DOB = "DOB";
    public static final String COLUMN_ROLE = "Role";

    private static final String[] COLUMN_NAMES = {COLUMN_TAX_ID, COLUMN_NAME, COLUMN_DOB, COLUMN_ROLE};

    private Segment segment;
    private Map allocationsByRole;
    private boolean validSplitEqual = true;

    private static final List columnNames = new ArrayList();

    public ExistingBeneficiariesTableModel(Segment segment, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.segment = segment;
    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        ContractClient[] beneficiaries = segment.getBeneficiaryContractClients();

        beneficiaries = (ContractClient[]) Util.sortObjects(beneficiaries, new String [] {"getContractClientPK"});

        for (int i = 0; i < beneficiaries.length; i++)
        {
            ContractClient contractClient = beneficiaries[i];

            TableRow tableRow = new ExistingBeneficiariesTableRow(contractClient);

            if (tableRow.getRowId().equals(this.getSelectedRowId()))
            {
                tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            super.addRow(tableRow);
        }
    }


    /**
     * It is all or nothing for the allocations verses the splitEqualInd.  When the splitEqualInd is checked the
     * total of the allocations will be zero.
     * @return
     */
    public void editAllocations()
    {
        setupAllocations();

        boolean validAllocationPct = editAllocationPct();
        String validPctValue = "false";
        if (validAllocationPct)
        {
            validPctValue = "true";
        }

        String validSplitEqualInd = "false";
        if (validSplitEqual)
        {
            validSplitEqualInd = "true";
        }

        this.getAppReqBlock().getHttpServletRequest().setAttribute("validAllocationPct", validPctValue);
        this.getAppReqBlock().getHttpServletRequest().setAttribute("validSplitEqual", validSplitEqualInd);
    }

    public boolean editAllocationPct()
    {

        boolean validAllocationPct = true;
        for (Iterator iterator = allocationsByRole.values().iterator(); iterator.hasNext();)
        {
            EDITBigDecimal allocPct =  (EDITBigDecimal) iterator.next();
            if (!allocPct.isEQ("0.00") && !allocPct.isEQ("1.00"))
            {
                validAllocationPct = false;
            }
        }

        return validAllocationPct;
    }

    public void setupAllocations()
    {
        EDITBigDecimal allocationPct = null;
        String saveRoleType = null;
        allocationsByRole = new HashMap();
        validSplitEqual = true;
        String saveSplitEqualInd = null;
        String splitEqualInd = null;

        Map sortedRows = sortRoleColumn();

        for (Iterator iterator = sortedRows.values().iterator(); iterator.hasNext();)
        {
            TableRow existingBeneTableRow = (TableRow) iterator.next();

            String roleType = (String) existingBeneTableRow.getCellValue(COLUMN_ROLE);
            if (!roleType.equalsIgnoreCase(saveRoleType))
            {
                if (allocationPct != null)
                {
                    allocationsByRole.put(saveRoleType, allocationPct);
                }

                saveRoleType = roleType;
                allocationPct = new EDITBigDecimal();
            }

            String rowId = existingBeneTableRow.getRowId();
            ContractClient contractClient = ContractClient.findByPK(new Long(rowId));
            Set contractClientAllocations = contractClient.getContractClientAllocations();

            for (Iterator ccaIterator = contractClientAllocations.iterator(); ccaIterator.hasNext();)
            {
                ContractClientAllocation contractClientAllocation = (ContractClientAllocation) ccaIterator.next();
                if (contractClientAllocation.getOverrideStatus().equalsIgnoreCase("P"))
                {
                    allocationPct = allocationPct.addEditBigDecimal(contractClientAllocation.getAllocationPercent());

                    //only edit splitEqual if pct = zero and role is "PBE"
                    if (allocationPct.isEQ("0") && saveRoleType.equalsIgnoreCase("PBE"))
                    {
                        splitEqualInd = contractClientAllocation.getSplitEqual();
                        if (saveSplitEqualInd == null)
                        {
                            saveSplitEqualInd = splitEqualInd;
                        }

                        if (!saveSplitEqualInd.equalsIgnoreCase(splitEqualInd))
                        {
                            validSplitEqual = false;
                        }
                    }
                }
            }
        }

        if (saveRoleType != null)
        {
            allocationsByRole.put(saveRoleType, allocationPct);
        }
    }

    private TreeMap sortRoleColumn()
    {
		TreeMap sortedRows = new TreeMap();

		Iterator enumer = this.getRows().iterator();

		while (enumer.hasNext())
        {
            TableRow existingBeneTableRow = (TableRow)enumer.next();

            String rowId = existingBeneTableRow.getRowId();
            String roleValue = (String) existingBeneTableRow.getCellValue(COLUMN_ROLE);

            sortedRows.put(roleValue + "_" + rowId, existingBeneTableRow);
		}

		return sortedRows;
    }
}
