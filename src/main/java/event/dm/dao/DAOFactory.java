package event.dm.dao;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 8:41:40 AM
 * To change this template use Options | File Templates.
 */
public class DAOFactory
{
    private static GroupSetupDAO groupSetupDAO;
    private static ChargeDAO chargeDAO;
    private static OutSuspenseDAO outSuspenseDAO;
    private static SuspenseDAO suspenseDAO;
    private static CashBatchContractDAO cashBatchContractDAO;
    private static ContractSetupDAO contractSetupDAO;
    private static InvestmentAllocationOverrideDAO investmentAllocationOverrideDAO;
    private static ClientSetupDAO clientSetupDAO;
    private static WithholdingOverrideDAO withholdingOverrideDAO;
    private static ContractClientAllocationOverrideDAO contractClientAllocationOverrideDAO;
    private static EDITTrxDAO editTrxDAO;
    private static ScheduledEventDAO scheduledEventDAO;
    private static EDITTrxHistoryDAO editTrxHistoryDAO;
    private static BucketHistoryDAO bucketHistoryDAO;
    private static ChargeHistoryDAO chargeHistoryDAO;
    private static WithholdingHistoryDAO withholdingHistoryDAO;
    private static TransactionPriorityDAO transactionPriorityDAO;
    private static TransactionCorrespondenceDAO transactionCorrespondenceDAO;
    private static InSuspenseDAO inSuspenseDAO;
    private static BucketChargeHistoryDAO bucketChargeHistoryDAO;
    private static RealTimeActivityDAO realTimeActivityDAO;
    private static FinancialHistoryDAO financialHistoryDAO;
    private static CommissionHistoryDAO commissionHistoryDAO;
    private static EDITTrxCorrespondenceDAO editTrxCorrespondenceDAO;
    private static InvestmentHistoryDAO investmentHistoryDAO;
    private static OverdueChargeDAO overdueChargeDAO;
    private static SegmentHistoryDAO segmentHistoryDAO;

    static
    {

        chargeDAO = new ChargeDAO();
        outSuspenseDAO = new OutSuspenseDAO();
        editTrxHistoryDAO = new EDITTrxHistoryDAO();
        bucketHistoryDAO = new BucketHistoryDAO();
        groupSetupDAO = new GroupSetupDAO();
        editTrxDAO = new EDITTrxDAO();
        investmentAllocationOverrideDAO = new InvestmentAllocationOverrideDAO();
        clientSetupDAO = new ClientSetupDAO();
        withholdingOverrideDAO = new WithholdingOverrideDAO();
        contractClientAllocationOverrideDAO = new ContractClientAllocationOverrideDAO();
        contractSetupDAO = new ContractSetupDAO();
        chargeHistoryDAO = new ChargeHistoryDAO();
        withholdingHistoryDAO = new WithholdingHistoryDAO();
        scheduledEventDAO = new ScheduledEventDAO();
        transactionPriorityDAO = new TransactionPriorityDAO();
        transactionCorrespondenceDAO = new TransactionCorrespondenceDAO();
        inSuspenseDAO = new InSuspenseDAO();
        bucketChargeHistoryDAO = new BucketChargeHistoryDAO();
        suspenseDAO = new SuspenseDAO();
        cashBatchContractDAO = new CashBatchContractDAO();
        realTimeActivityDAO = new RealTimeActivityDAO();
        financialHistoryDAO = new FinancialHistoryDAO();
        commissionHistoryDAO = new CommissionHistoryDAO();
        editTrxCorrespondenceDAO = new EDITTrxCorrespondenceDAO();
        investmentHistoryDAO = new InvestmentHistoryDAO();
        overdueChargeDAO = new OverdueChargeDAO();
        segmentHistoryDAO = new SegmentHistoryDAO();
    }


//*******************************
//          Public Methods
//*******************************

    public static EDITTrxCorrespondenceDAO getEDITTrxCorrespondenceDAO()
    {
        return editTrxCorrespondenceDAO;
    }

    public static BucketChargeHistoryDAO getBucketChargeHistoryDAO()
    {
        return bucketChargeHistoryDAO;
    }

    public static InSuspenseDAO getInSuspenseDAO()
    {
        return inSuspenseDAO;
    }

        public static OutSuspenseDAO getOutSuspenseDAO()
    {
        return outSuspenseDAO;
    }
    public static SuspenseDAO getSuspenseDAO()
    {
        return suspenseDAO;
    }

    public static CashBatchContractDAO getCashBatchContractDAO()
    {
        return cashBatchContractDAO;
    }

    public static RealTimeActivityDAO getRealTimeActivityDAO()
    {
        return realTimeActivityDAO;
    }

    public static TransactionPriorityDAO getTransactionPriorityDAO()
    {
        return transactionPriorityDAO;
    }

    public static TransactionCorrespondenceDAO getTransactionCorrespondenceDAO()
    {
        return transactionCorrespondenceDAO;
    }

    public static ScheduledEventDAO getScheduledEventDAO()
    {
        return scheduledEventDAO;
    }

    public static WithholdingHistoryDAO getWithholdingHistoryDAO()
    {
        return withholdingHistoryDAO;
    }

    public static ChargeHistoryDAO getChargeHistoryDAO()
    {
        return chargeHistoryDAO;
    }

    public static ContractSetupDAO getContractSetupDAO()
    {
        return contractSetupDAO;
    }

    public static ContractClientAllocationOverrideDAO getContractClientAllocationOverrideDAO()
    {
        return contractClientAllocationOverrideDAO;
    }

    public static WithholdingOverrideDAO getWithholdingOverrideDAO()
    {
        return withholdingOverrideDAO;
    }

    public static ClientSetupDAO getClientSetupDAO()
    {
        return clientSetupDAO;
    }

    public static EDITTrxDAO getEDITTrxDAO()
    {
        return editTrxDAO;
    }

    public static GroupSetupDAO getGroupSetupDAO()
    {
        return groupSetupDAO;
    }
    public static InvestmentAllocationOverrideDAO getInvestmentAllocationOverrideDAO()
    {
        return investmentAllocationOverrideDAO;
    }

    public static BucketHistoryDAO getBucketHistoryDAO()
    {
        return bucketHistoryDAO;
    }

    public static EDITTrxHistoryDAO getEDITTrxHistoryDAO()
    {
        return editTrxHistoryDAO;
    }

   public static FinancialHistoryDAO getFinancialHistoryDAO()
    {
        return financialHistoryDAO;
    }

    public static ChargeDAO getChargeDAO()
    {
        return chargeDAO;
    }

    public static CommissionHistoryDAO getCommissionHistoryDAO()
    {
        return commissionHistoryDAO;
    }

    public static InvestmentHistoryDAO getInvestmentHistoryDAO()
    {
        return investmentHistoryDAO;
    }

    public static OverdueChargeDAO getOverdueChargeDAO()
    {
        return overdueChargeDAO;
    }

    public static SegmentHistoryDAO getSegmentHistoryDAO()
    {
        return segmentHistoryDAO;
    }
}
