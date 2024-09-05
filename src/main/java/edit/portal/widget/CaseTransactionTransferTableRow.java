/*
 * User: sdorman
 * Date: 1/26/2006
 * Time: 10:40:18
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;

import event.*;
import engine.*;
import contract.*;



public class CaseTransactionTransferTableRow extends TableRow
{
    private Fund fund;
    private InvestmentAllocation investmentAllocation;
    private InvestmentAllocationOverride investmentAllocationOverride;


    /**
     * Empty constructor to satisfy java bean restrictions which say you must have a default constructor
     * that takes no arguments so the JSP page can instantiate the bean.
     * DO NOT USE THIS CONSTRUCTOR!
     */
    public CaseTransactionTransferTableRow()
    {
        //  Do not use this method.  Exists purely for bean use in jsp pages.
    }

    /**
     * Instantiates the object and populates all the rows and cells
     * @param fund
     * @param investmentAllocation
     * @param investmentAllocationOverride
     */
    public CaseTransactionTransferTableRow(Fund fund, InvestmentAllocation investmentAllocation, InvestmentAllocationOverride investmentAllocationOverride)
    {
        super();

        this.fund = fund;
        this.investmentAllocation = investmentAllocation;
        this.investmentAllocationOverride = investmentAllocationOverride;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        String fundName = fund.getName();
        String fromTo   = investmentAllocationOverride.getToFromStatus();
        String percent  = investmentAllocation.getAllocationPercent().toString();
        String dollars  = investmentAllocation.getDollars().toString();
        String units    = investmentAllocation.getUnits().toString();

        getCellValues().put(CaseTransactionTransferTableModel.COLUMN_FUND_NAME, fundName);
        getCellValues().put(CaseTransactionTransferTableModel.COLUMN_FROM_TO, fromTo);
        getCellValues().put(CaseTransactionTransferTableModel.COLUMN_PERCENT, percent);
        getCellValues().put(CaseTransactionTransferTableModel.COLUMN_DOLLARS, dollars);
        getCellValues().put(CaseTransactionTransferTableModel.COLUMN_UNITS, units);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return fund.getFundPK() + "";
    }


    //          Getters and Setters to support bean functionality

    public String getFundID()
    {
        String fundPK = "";

        if (fund != null)
        {
            fundPK = this.fund.getFundPK().toString();
        }

        return fundPK;
    }

    public String getFundName()
    {
        return (String) this.getCellValue(CaseTransactionTransferTableModel.COLUMN_FUND_NAME);
    }

    public String getFromTo()
    {
        return (String) this.getCellValue(CaseTransactionTransferTableModel.COLUMN_FROM_TO);
    }

    public String getPercent()
    {
        return (String) this.getCellValue(CaseTransactionTransferTableModel.COLUMN_PERCENT);
    }

    public String getDollars()
    {
        return (String) this.getCellValue(CaseTransactionTransferTableModel.COLUMN_DOLLARS);
    }

    public String getUnits()
    {
        return (String) this.getCellValue(CaseTransactionTransferTableModel.COLUMN_UNITS);
    }

    public void setFundID(String fundID)
    {
        //  Should not be using this method, just here for bean support
    }

    public void setFundName(String fundName)
    {
        this.setCellValue(CaseTransactionTransferTableModel.COLUMN_FUND_NAME, fundName);
    }

    public void setFromTo(String fromTo)
    {
        this.setCellValue(CaseTransactionTransferTableModel.COLUMN_FROM_TO, fromTo);
    }

    public void setPercent(String percent)
    {
        this.setCellValue(CaseTransactionTransferTableModel.COLUMN_PERCENT, percent);
    }

    public void setDollars(String dollars)
    {
        this.setCellValue(CaseTransactionTransferTableModel.COLUMN_DOLLARS, dollars);
    }

    public void setUnits(String units)
    {
        this.setCellValue(CaseTransactionTransferTableModel.COLUMN_UNITS, units);
    }
}
