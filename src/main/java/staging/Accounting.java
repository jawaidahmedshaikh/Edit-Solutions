package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 9, 2005
 * Time: 8:38:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class Accounting extends HibernateEntity
{
    private Long accountingPK;
    private Long stagingFK;
    private String companyName;
    private String marketingPackage;
    private String businessContract;
    private String contractNumber;
    private String qualNonQual;
    private String optionCode;
    private String transactionCode;
    private String reversalInd;
    private String memoCode;
    private String stateCode;
    private String accountNumber;
    private String accountName;
    private EDITBigDecimal amount;
    private String debitCreditInd;
    private EDITDate effectiveDate;
    private EDITDate processDate;
    private String fundNumber;
    private String outOfBalanceInd;
    private String accountingPendingStatus;
    private String qualifiedType;
    private String originalContractNumber;
    private EDITDate accountingProcessDate;
    private String accountingPeriod;
    private Long editTrxFK;
    private String chargeCode;
    private String distributionCode;
    private String reinsurerNumber;
    private String treatyGroupNumber;
    private int certainDuration;
    private Long contractClientFK;
    private Long agentFK;
    private String caseNumber;
    private String caseName;
    private String groupNumber;
    private String groupName;
    private String batchControl;
    private String source;
    private EDITDate issueDate;
    private EDITDate originalIssueDate;
    private String checkNumber;
    private String operator;

    private Staging staging;
    private ContractClient contractClient;
    private Agent agent;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public Accounting()
    {

    }

    /**
     * Getter.
     * @return
     */
    public Long getAccountingPK()
    {
        return accountingPK;
    }

    /**
     * Setter.
     * @param accountingPK
     */
    public void setAccountingPK(Long accountingPK)
    {
        this.accountingPK = accountingPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getStagingFK()
    {
        return stagingFK;
    }

    /**
     * Setter.
     * @param stagingFK
     */
    public void setStagingFK(Long stagingFK)
    {
        this.stagingFK = stagingFK;
    }

    /**
     * Getter.
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Setter.
     * @param staging
     */
    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractClientFK()
    {
        return contractClientFK;
    }

    /**
     * Setter.
     * @param contractClientFK
     */
    public void setContractClientFK(Long contractClientFK)
    {
        this.contractClientFK = contractClientFK;
    }

    /**
     * Getter.
     * @return
     */
    public ContractClient getContractClient()
    {
        return contractClient;
    }

    /**
     * Setter.
     * @param contractClient
     */
    public void setContractClient(ContractClient contractClient)
    {
        this.contractClient = contractClient;
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentFK()
    {
        return agentFK;
    }

    /**
     * Setter.
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        this.agentFK = agentFK;
    }

    /**
     * Getter.
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     * @param agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
     * Getter.
     * @return
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * Setter.
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Getter.
     * @return
     */
    public String getMarketingPackage()
    {
        return marketingPackage;
    }

    /**
     * Setter.
     * @param marketingPackage
     */
    public void setMarketingPackage(String marketingPackage)
    {
        this.marketingPackage = marketingPackage;
    }

    /**
     * Getter.
     * @return
     */
    public String getBusinessContract()
    {
        return businessContract;
    }

    /**
     * Setter.
     * @param businessContract
     */
    public void setBusinessContract(String businessContract)
    {
        this.businessContract = businessContract;
    }

    /**
     * Getter.
     * @return
     */
    public String getContractNumber()
    {
        return contractNumber;
    }

    /**
     * Setter.
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getQualNonQual()
    {
        return qualNonQual;
    }

    /**
     * Setter.
     * @param qualNonQual
     */
    public void setQualNonQual(String qualNonQual)
    {
        this.qualNonQual = qualNonQual;
    }

    /**
     * Getter.
     * @return
     */
    public String getOptionCode()
    {
        return optionCode;
    }

    /**
     * Setter.
     * @param optionCode
     */
    public void setOptionCode(String optionCode)
    {
        this.optionCode = optionCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getTransactionCode()
    {
        return transactionCode;
    }

    /**
     * Setter.
     * @param transactionCode
     */
    public void setTransactionCode(String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getReversalInd()
    {
        return reversalInd;
    }

    /**
     * Setter.
     * @param reversalInd
     */
    public void setReversalInd(String reversalInd)
    {
        this.reversalInd = reversalInd;
    }

    /**
     * Getter.
     * @return
     */
    public String getMemoCode()
    {
        return memoCode;
    }

    /**
     * Setter.
     * @param memoCode
     */
    public void setMemoCode(String memoCode)
    {
        this.memoCode = memoCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getStateCode()
    {
        return stateCode;
    }

    /**
     * Setter.
     * @param stateCode
     */
    public void setStateCode(String stateCode)
    {
        this.stateCode = stateCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountNumber()
    {
        return accountNumber;
    }

    /**
     * Setter.
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountName()
    {
        return accountName;
    }

    /**
     * Setter.
     * @param accountName
     */
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAmount()
    {
        return amount;
    }

    /**
     * Setter.
     * @param amount
     */
    public void setAmount(EDITBigDecimal amount)
    {
        this.amount = amount;
    }

    /**
     * Getter.
     * @return
     */
    public String getDebitCreditInd()
    {
        return debitCreditInd;
    }

    /**
     * Setter.
     * @param debitCreditInd
     */
    public void setDebitCreditInd(String debitCreditInd)
    {
        this.debitCreditInd = debitCreditInd;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getProcessDate()
    {
        return processDate;
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setProcessDate(EDITDate processDate)
    {
        this.processDate = processDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getFundNumber()
    {
        return fundNumber;
    }

    /**
     * Setter.
     * @param fundNumber
     */
    public void setFundNumber(String fundNumber)
    {
        this.fundNumber = fundNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getOutOfBalanceInd()
    {
        return outOfBalanceInd;
    }

    /**
     * Setter.
     * @param outOfBalanceInd
     */
    public void setOutOfBalanceInd(String outOfBalanceInd)
    {
        this.outOfBalanceInd = outOfBalanceInd;
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPendingStatus()
    {
        return accountingPendingStatus;
    }

    /**
     * Setter.
     * @param accountingPendingStatus
     */
    public void setAccountingPendingStatus(String accountingPendingStatus)
    {
        this.accountingPendingStatus = accountingPendingStatus;
    }

    /**
     * Getter.
     * @return
     */
    public String getQualifiedType()
    {
        return qualifiedType;
    }

    /**
     * Setter.
     * @param qualifiedType
     */
    public void setQualifiedType(String qualifiedType)
    {
        this.qualifiedType = qualifiedType;
    }

    /**
     * Getter.
     * @return
     */
    public String getOriginalContractNumber()
    {
        return originalContractNumber;
    }

    /**
     * Setter.
     * @param originalContractNumber
     */
    public void setOriginalContractNumber(String originalContractNumber)
    {
        this.originalContractNumber = originalContractNumber;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getAccountingProcessDate()
    {
        return accountingProcessDate;
    }

    /**
     * Setter.
     * @param accountingProcessDate
     */
    public void setAccountingProcessDate(EDITDate accountingProcessDate)
    {
        this.accountingProcessDate = accountingProcessDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPeriod()
    {
        return accountingPeriod;
    }

    /**
     * Setter.
     * @param accountingPeriod
     */
    public void setAccountingPeriod(String accountingPeriod)
    {
        this.accountingPeriod = accountingPeriod;
    }

    /**
     * Getter.
     * @return
     */
    public Long getEDITTrxFK()
    {
        return editTrxFK;
    }

    /**
     * Setter.
     * @param editTrxFK
     */
    public void setEDITTrxFK(Long editTrxFK)
    {
        this.editTrxFK = editTrxFK;
    }

    /**
     * Getter.
     * @return
     */
    public String getChargeCode()
    {
        return chargeCode;
    }

    /**
     * Setter.
     * @param chargeCode
     */
    public void setChargeCode(String chargeCode)
    {
        this.chargeCode = chargeCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getDistributionCode()
    {
        return distributionCode;
    }

    /**
     * Setter.
     * @param distributionCode
     */
    public void setDistributionCode(String distributionCode)
    {
        this.distributionCode = distributionCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getReinsurerNumber()
    {
        return reinsurerNumber;
    }

    /**
     * Setter.
     * @param reinsurerNumber
     */
    public void setReinsurerNumber(String reinsurerNumber)
    {
        this.reinsurerNumber = reinsurerNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getTreatyGroupNumber()
    {
        return treatyGroupNumber;
    }

    /**
     * Setter.
     * @param treatyGroupNumber
     */
    public void setTreatyGroupNumber(String treatyGroupNumber)
    {
        this.treatyGroupNumber = treatyGroupNumber;
    }

    /**
     * Getter.
     * @return
     */
    public int getCertainDuration()
    {
        return certainDuration;
    }

    /**
     * Setter.
     * @param certainDuration
     */
    public void setCertainDuration(int certainDuration)
    {
        this.certainDuration = certainDuration;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseNumber()
    {
        return caseNumber;
    }

    /**
     * Setter.
     * @param caseNumber
     */
    public void setCaseNumber(String caseNumber)
    {
        this.caseNumber = caseNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseName()
    {
        return caseName;
    }

    /**
     * Setter.
     * @param caseName
     */
    public void setCaseName(String caseName)
    {
        this.caseName = caseName;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupNumber()
    {
        return groupNumber;
    }

    /**
     * Setter.
     * @param groupNumber
     */
    public void setGroupNumber(String groupNumber)
    {
        this.groupNumber = groupNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupName()
    {
        return groupName;
    }

    /**
     * Setter.
     * @param groupName
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
    /**
     * Setter.
     * @param batchControl
     */
    public void setBatchControl(String batchControl)
    {
        this.batchControl = batchControl;
    }

    /**
     * Getter.
     * @return
     */
    public String getBatchControl()
    {
        return batchControl;
    }

    /**
     * Setter.
     * @param source
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * Getter.
     * @return
     */
    public String getSource()
    {
        return source;
    }

    /**
     * Setter.
     * @param issueDate
     */
    public void setIssueDate(EDITDate issueDate)
    {
        this.issueDate = issueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getIssueDate()
    {
        return issueDate;
    }

    /**
     * Setter.
     * @param originalIssueDate
     */
    public void setOriginalIssueDate(EDITDate originalIssueDate)
    {
        this.originalIssueDate = originalIssueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getOriginalIssueDate()
    {
        return originalIssueDate;
    }

    /**
     * Setter.
     * @param checkNumber
     */
    public void setCheckNumber(String checkNumber)
    {
        this.checkNumber = checkNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getCheckNumber()
    {
        return checkNumber;
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return operator;
    }

    public String getDatabase()
    {
        return Accounting.DATABASE;
    }
}


