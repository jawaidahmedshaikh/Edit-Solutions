package contract.report;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.CodeTableWrapper;

import java.util.*;

import event.EDITTrxHistory;
import event.EDITTrx;
import event.InvestmentHistory;
import contract.Investment;
import engine.FilteredFund;
import engine.Fund;
import engine.ChargeCode;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Nov 8, 2007
 * Time: 3:43:29 PM
 * To change this template use File | Settings | File Templates.
 * Helper class to create Transfer Memorandum Report.
 */
public class TransferMemorandum
{
    /**
     * Date for which the report has to be run.
     */
    EDITDate inputDate;

    /**
     * Date when report is prepared.
     */
    EDITDate reportDate;

    /**
     * Report From Name
     */
    String fromName;

    /**
     * Report To Name
     */
    String toName;

    /**
     * Subject of the Report.
     */
    String subject;

    /**
     * Collection of funds that are involved in HedegeFund series transfer for the given date.
     */
    Map funds;

    /**
     * Variable to determine if the report is empty or not.
     */
    boolean isReportEmpty;

    /**
     * Net Amount
     */
    EDITBigDecimal netAmount;

    /**
     * Series Transfer transaction types.
     */
    private static final String[] transactionTypeCTs = new String[] {EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT, EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT};

    /**
     * Constructor
     * @param date - for which the report has to be run.
     */
    public TransferMemorandum(EDITDate date)
    {
        this.inputDate = date;

        this.reportDate = new EDITDate();

        this.subject = "SEPERATE ACCOUNT - Series VII";

        this.fromName = CodeTableWrapper.getSingleton().getCodeTableEntries("TMFROMNAME")[0].getCodeDesc();

        this.toName = CodeTableWrapper.getSingleton().getCodeTableEntries("TMTONAME")[0].getCodeDesc();

        this.funds = new HashMap();

        this.isReportEmpty = false;

        this.netAmount = new EDITBigDecimal("0");
    }

    /**
     * @see contract.report.TransferMemorandum#inputDate
     * @return
     */
    public EDITDate getInputDate()
    {
        return inputDate;
    }

    /**
     * @see contract.report.TransferMemorandum#inputDate
     * @param inputDate
     */
    public void setInputDate(EDITDate inputDate)
    {
        this.inputDate = inputDate;
    }

    /**
     * @see contract.report.TransferMemorandum#reportDate
     * @return
     */
    public EDITDate getReportDate()
    {
        return reportDate;
    }

    /**
     * @see contract.report.TransferMemorandum#reportDate
     * @param reportDate
     */
    public void setReportDate(EDITDate reportDate)
    {
        this.reportDate = reportDate;
    }

    /**
     * @see contract.report.TransferMemorandum#fromName
     * @return
     */
    public String getFromName()
    {
        return fromName;
    }

    /**
     * @see contract.report.TransferMemorandum#fromName
     * @param fromName
     */
    public void setFromName(String fromName)
    {
        this.fromName = fromName;
    }

    /**
     * @see contract.report.TransferMemorandum#toName
     * @return
     */
    public String getToName()
    {
        return toName;
    }

    /**
     * @see contract.report.TransferMemorandum#toName
     * @param toName
     */
    public void setToName(String toName)
    {
        this.toName = toName;
    }

    /**
     * @see contract.report.TransferMemorandum#subject
     * @return
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @see contract.report.TransferMemorandum#subject
     * @param subject
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * @see contract.report.TransferMemorandum#funds
     * @return
     */
    public Map getFunds()
    {
        return funds;
    }

    /**
     * @see contract.report.TransferMemorandum#funds
     * @param funds
     */
    public void setFunds(Map funds)
    {
        this.funds = funds;
    }

    /**
     * @see TransferMemorandum#isReportEmpty()
     * @return
     */
    public boolean isReportEmpty()
    {
        return isReportEmpty;
    }

    /**
     * @see TransferMemorandum#isReportEmpty()
     * @param reportEmpty
     */
    public void setReportEmpty(boolean reportEmpty)
    {
        isReportEmpty = reportEmpty;
    }

    /**
     * @see contract.report.TransferMemorandum#netAmount
     * @return
     */
    public EDITBigDecimal getNetAmount()
    {
        return netAmount;
    }

    /**
     * @see contract.report.TransferMemorandum#netAmount
     * @param netAmount
     */
    public void setNetAmount(EDITBigDecimal netAmount)
    {
        this.netAmount = netAmount;
    }

    /**
     * Runs the report for given date.
     * All the funds involved in series transfer transactions are placed in 'funds' map by key (FilteredFundFK_ChargeCodeFK which is unique)
     * The From funds amount is represented as negative.
     */
    public void generateReport()
    {
        EDITTrxHistory[] editTrxHistories = EDITTrxHistory.findByEffectiveDate_TransactionTypeCT(this.inputDate, transactionTypeCTs);

        if (editTrxHistories.length == 0)
        {
            setReportEmpty(true);
        }

        for (int i = 0; i < editTrxHistories.length; i++)
        {
            EDITTrxHistory editTrxHistory = editTrxHistories[i];

            Set investmentHistories = editTrxHistory.getInvestmentHistories();

            for (Iterator iterator = investmentHistories.iterator(); iterator.hasNext();)
            {
                InvestmentHistory investmentHistory = (InvestmentHistory) iterator.next();

                Investment investment = investmentHistory.getInvestment();

                long filteredFundFK = investment.getFilteredFundFK();

                long chargeCodeFK = investment.getChargeCodeFK().longValue();

                String key = filteredFundFK + "_" + chargeCodeFK;

                FundDetail fundDetail = null;

                if (this.getFunds().containsKey(key))
                {
                    fundDetail = (FundDetail) this.getFunds().get(key);

                    EDITBigDecimal amount = fundDetail.getAmount();

                    if (investmentHistory.getToFromStatus().equals("F"))
                    {
                        amount = amount.subtractEditBigDecimal(investmentHistory.getInvestmentDollars());
                    }
                    else
                    {
                        amount = amount.addEditBigDecimal(investmentHistory.getInvestmentDollars());
                    }

                    fundDetail.setAmount(amount);
                }
                else
                {
                    fundDetail = new FundDetail();

                    FilteredFund filteredFund = FilteredFund.findByPK(new Long(filteredFundFK));

                    Fund fund = Fund.findByPK(filteredFund.getFundFK());

                    ChargeCode chargeCode = ChargeCode.findByPK(chargeCodeFK);

                    EDITBigDecimal amount = investmentHistory.getInvestmentDollars();

                    if (investmentHistory.getToFromStatus().equals("F"))
                    {
                        amount = amount.multiplyEditBigDecimal("-1");
                    }

                    fundDetail.setClientFundNumber(chargeCode.getClientFundNumber());
                    fundDetail.setAmount(amount);
                    fundDetail.setFundName(fund.getName());

                    this.getFunds().put(key, fundDetail);
                }
            }
        }

        // Calculate the net amount
        for(Iterator iterator = this.getFunds().values().iterator(); iterator.hasNext();)
        {
            FundDetail fundDetail = (FundDetail) iterator.next();

            this.setNetAmount(this.getNetAmount().addEditBigDecimal(fundDetail.getAmount()));
        }

        // Delete zero dollar funds.
        for(Iterator iterator = this.getFunds().values().iterator(); iterator.hasNext();)
        {
            FundDetail fundDetail = (FundDetail) iterator.next();

            if (fundDetail.getAmount().isEQ("0"))
            {
                iterator.remove();
            }
        }
    }

    /**
     * Holds Fund information FundNumber, FundName and Amount
     */
    public class FundDetail
    {
        /**
         * Holds value from ChargeCode.ClientFundNumber
         */
        String clientFundNumber;

        /**
         * Holds value from Fund.Name
         */
        String fundName;

        /**
         * Holds cumulative amount of all HFSA and HFSP transactions involved on the given date
         */
        EDITBigDecimal amount;

        /**
         * @see contract.report.TransferMemorandum.FundDetail#clientFundNumber
         * @return
         */
        public String getClientFundNumber()
        {
            return clientFundNumber;
        }

        /**
         * @see contract.report.TransferMemorandum.FundDetail#clientFundNumber
         * @param clientFundNumber
         */
        public void setClientFundNumber(String clientFundNumber)
        {
            this.clientFundNumber = clientFundNumber;
        }

        /**
         * @see contract.report.TransferMemorandum.FundDetail#fundName
         * @return
         */
        public String getFundName()
        {
            return fundName;
        }

        /**
         * @see contract.report.TransferMemorandum.FundDetail#fundName
         * @param fundName
         */
        public void setFundName(String fundName)
        {
            this.fundName = fundName;
        }

        /**
         * @see contract.report.TransferMemorandum.FundDetail#amount
         * @return
         */
        public EDITBigDecimal getAmount()
        {
            return amount;
        }

        /**
         * @see contract.report.TransferMemorandum.FundDetail#amount
         * @param amount
         */
        public void setAmount(EDITBigDecimal amount)
        {
            this.amount = amount;
        }
    }
}