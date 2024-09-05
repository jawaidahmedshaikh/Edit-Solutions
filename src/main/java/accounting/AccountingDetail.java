/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 13, 2005
 * Time: 4:10:58 PM
 * To change this template use File | Settings | File Templates.
 */

package accounting;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import edit.services.db.hibernate.HibernateEntity;
import edit.common.vo.VOObject;
import edit.common.vo.AccountingDetailVO;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import accounting.dm.dao.AccountingDetailDAO;

import java.util.*;

import staging.IStaging;
import staging.StagingContext;
import staging.Accounting;
import contract.*;

import event.EDITTrx;

import group.ContractGroup;
import role.ClientRole;

public class AccountingDetail extends HibernateEntity implements CRUDEntity, IStaging
{
    public static final String DEBITCREDITIND_DEBIT = "Debit";
    public static final String DEBITCREDITIND_CREDIT = "Credit";

    public static final String SOURCE_MANUAL = "Manual";

    private CRUDEntityImpl crudEntityImpl;

    private AccountingDetailVO accountingDetailVO;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    private EDITTrx editTrx;

    /**
     * Instantiates a AccountingDetail entity with a default AccountingDetailVO.
     */
    public AccountingDetail()
    {
        init();
    }

    /**
     * Instantiates a AccountingDetail entity with a supplied AccountingDetailVO.
     *
     * @param accountingDetailVO
     */
    public AccountingDetail(AccountingDetailVO accountingDetailVO)
    {
        init();

        this.accountingDetailVO = accountingDetailVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (accountingDetailVO == null)
        {
            accountingDetailVO = new AccountingDetailVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return accountingDetailVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return accountingDetailVO.getAccountingDetailPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.accountingDetailVO = (AccountingDetailVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Getter.
     * @return
     */
    public Long getAccountingDetailPK()
    {
        return SessionHelper.getPKValue(accountingDetailVO.getAccountingDetailPK());
    }

    /**
     * Setter.
     * @param accountingDetailPK
     */
    public void setAccountingDetailPK(Long accountingDetailPK)
    {
        this.accountingDetailVO.setAccountingDetailPK(SessionHelper.getPKValue(accountingDetailPK));
    }

    /**
     * Getter.
     * @return
     */
    public String getCompanyName()
    {
        return accountingDetailVO.getCompanyName();
    }

    /**
     * Setter.
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.accountingDetailVO.setCompanyName(companyName);
    }

    /**
     * Getter.
     * @return
     */
    public String getMarketingPackageName()
    {
        return accountingDetailVO.getMarketingPackageName();
    }

    /**
     * Setter.
     * @param marketingPackageName
     */
    public void setMarketingPackageName(String marketingPackageName)
    {
        this.accountingDetailVO.setMarketingPackageName(marketingPackageName);
    }

    /**
     * Getter.
     * @return
     */
    public String getBusinessContractName()
    {
        return accountingDetailVO.getBusinessContractName();
    }

    /**
     * Setter.
     * @param businessContractName
     */
    public void setBusinessContractName(String businessContractName)
    {
        this.accountingDetailVO.setBusinessContractName(businessContractName);
    }

    /**
     * Getter.
     * @return
     */
    public String getContractNumber()
    {
        return accountingDetailVO.getContractNumber();
    }

    /**
     * Setter.
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber)
    {
        this.accountingDetailVO.setContractNumber(contractNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getQualNonQualCT()
    {
        return accountingDetailVO.getQualNonQualCT();
    }

    /**
     * Setter.
     * @param qualNonQualCT
     */
    public void setQualNonQualCT(String qualNonQualCT)
    {
        this.accountingDetailVO.setQualNonQualCT(qualNonQualCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getOptionCodeCT()
    {
        return accountingDetailVO.getOptionCodeCT();
    }

    /**
     * Setter.
     * @param optionCodeCT
     */
    public void setOptionCodeCT(String optionCodeCT)
    {
        this.accountingDetailVO.setOptionCodeCT(optionCodeCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupNumber()
    {
        return accountingDetailVO.getGroupNumber();
    }

    /**
     * Setter.
     * @param groupNumber
     */
    public void setGroupNumber(String groupNumber)
    {
        this.accountingDetailVO.setGroupNumber(groupNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupName()
    {
        return accountingDetailVO.getGroupName();
    }

    /**
     * Setter.
     * @param groupName
     */
    public void setGroupName(String groupName)
    {
        this.accountingDetailVO.setGroupName(groupName);
    }

    /**
     * Getter.
     * @return
     */
    public String getTransactionCode()
    {
        return accountingDetailVO.getTransactionCode();
    }

    /**
     * Setter.
     * @param transactionCode
     */
    public void setTransactionCode(String transactionCode)
    {
        this.accountingDetailVO.setTransactionCode(transactionCode);
    }

    /**
     * Getter.
     * @return
     */
    public String getReversalInd()
    {
        return accountingDetailVO.getReversalInd();
    }

    /**
     * Setter.
     * @param reversalInd
     */
    public void setReversalInd(String reversalInd)
    {
        this.accountingDetailVO.setReversalInd(reversalInd);
    }

    /**
     * Getter.
     * @return
     */
    public String getMemoCode()
    {
        return accountingDetailVO.getMemoCode();
    }

    /**
     * Setter.
     * @param memoCode
     */
    public void setMemoCode(String memoCode)
    {
        this.accountingDetailVO.setMemoCode(memoCode);
    }

    /**
     * Getter.
     * @return
     */
    public String getStateCodeCT()
    {
        return accountingDetailVO.getStateCodeCT();
    }

    /**
     * Setter.
     * @param stateCodeCT
     */
    public void setStateCodeCT(String stateCodeCT)
    {
        this.accountingDetailVO.setStateCodeCT(stateCodeCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountNumber()
    {
        return accountingDetailVO.getAccountNumber();
    }

    /**
     * Setter.
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber)
    {
        this.accountingDetailVO.setAccountNumber(accountNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountName()
    {
        return accountingDetailVO.getAccountName();
    }

    /**
     * Setter.
     * @param accountName
     */
    public void setAccountName(String accountName)
    {
        this.accountingDetailVO.setAccountName(accountName);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAmount()
    {
        return SessionHelper.getEDITBigDecimal(accountingDetailVO.getAmount());
    }

    /**
     * Setter.
     * @param amount
     */
    public void setAmount(EDITBigDecimal amount)
    {
        this.accountingDetailVO.setAmount(SessionHelper.getEDITBigDecimal(amount));
    }

    /**
     * Getter.
     * @return
     */
    public String getDebitCreditInd()
    {
        return accountingDetailVO.getDebitCreditInd();
    }

    /**
     * Setter.
     * @param debitCreditInd
     */
    public void setDebitCreditInd(String debitCreditInd)
    {
        this.accountingDetailVO.setDebitCreditInd(debitCreditInd);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return ((accountingDetailVO.getEffectiveDate() != null) ? new EDITDate(accountingDetailVO.getEffectiveDate()) : null);
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        accountingDetailVO.setEffectiveDate((effectiveDate != null) ? effectiveDate.getFormattedDate() : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getProcessDate()
    {
        return ((accountingDetailVO.getProcessDate() != null) ? new EDITDate(accountingDetailVO.getProcessDate()) : null);
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setProcessDate(EDITDate processDate)
    {
        accountingDetailVO.setProcessDate((processDate != null) ? processDate.getFormattedDate() : null);
    }

    /**
     * Getter.
     * @return
     */
    public String getFundNumber()
    {
        return accountingDetailVO.getFundNumber();
    }

    /**
     * Setter.
     * @param fundNumber
     */
    public void setFundNumber(String fundNumber)
    {
        this.accountingDetailVO.setFundNumber(fundNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getOutOfBalanceInd()
    {
        return accountingDetailVO.getOutOfBalanceInd();
    }

    /**
     * Setter.
     * @param outOfBalanceInd
     */
    public void setOutOfBalanceInd(String outOfBalanceInd)
    {
        this.accountingDetailVO.setOutOfBalanceInd(outOfBalanceInd);
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPendingStatus()
    {
        return accountingDetailVO.getAccountingPendingStatus();
    }

    /**
     * Setter.
     * @param accountingPendingStatus
     */
    public void setAccountingPendingStatus(String accountingPendingStatus)
    {
        this.accountingDetailVO.setAccountingPendingStatus(accountingPendingStatus);
    }

    /**
     * Getter.
     * @return
     */
    public String getQualifiedTypeCT()
    {
        return accountingDetailVO.getQualifiedTypeCT();
    }

    /**
     * Setter.
     * @param qualifiedTypeCT
     */
    public void setQualifiedTypeCT(String qualifiedTypeCT)
    {
        this.accountingDetailVO.setQualifiedTypeCT(qualifiedTypeCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getOriginalContractNumber()
    {
        return accountingDetailVO.getOriginalContractNumber();
    }

    /**
     * Setter.
     * @param originalContractNumber
     */
    public void setOriginalContractNumber(String originalContractNumber)
    {
        this.accountingDetailVO.setOriginalContractNumber(originalContractNumber);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getAccountingProcessDate()
    {
        return ((accountingDetailVO.getAccountingProcessDate() != null) ? new EDITDate(accountingDetailVO.getAccountingProcessDate()) : null);
    }

    /**
     * Setter.
     * @param accountingProcessDate
     */
    public void setAccountingProcessDate(EDITDate accountingProcessDate)
    {
        accountingDetailVO.setAccountingProcessDate((accountingProcessDate != null) ? accountingProcessDate.getFormattedDate() : null);
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPeriod()
    {
        return accountingDetailVO.getAccountingPeriod();
    }

    /**
     * Setter.
     * @param accountingPeriod
     */
    public void setAccountingPeriod(String accountingPeriod)
    {
        this.accountingDetailVO.setAccountingPeriod(accountingPeriod);
    }

    /**
     * Getter.
     * @return
     */
    public Long getEDITTrxFK()
    {
        return SessionHelper.getPKValue(accountingDetailVO.getEDITTrxFK());
    }

    /**
     * Setter.
     * @param editTrxFK
     */
    public void setEDITTrxFK(Long editTrxFK)
    {
        this.accountingDetailVO.setEDITTrxFK(SessionHelper.getPKValue(editTrxFK));
    }

    /**
     * Getter.
     * @return
     */
    public String getChargeCode()
    {
        return accountingDetailVO.getChargeCode();
    }

    /**
     * Setter.
     * @param chargeCode
     */
    public void setChargeCode(String chargeCode)
    {
        this.accountingDetailVO.setChargeCode(chargeCode);
    }

    /**
     * Getter.
     * @return
     */
    public String getDistributionCodeCT()
    {
        return accountingDetailVO.getDistributionCodeCT();
    }

    /**
     * Setter.
     * @param distributionCodeCT
     */
    public void setDistributionCodeCT(String distributionCodeCT)
    {
        this.accountingDetailVO.setDistributionCodeCT(distributionCodeCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getReinsurerNumber()
    {
        return accountingDetailVO.getReinsurerNumber();
    }

    /**
     * Setter.
     * @param reinsurerNumber
     */
    public void setReinsurerNumber(String reinsurerNumber)
    {
        this.accountingDetailVO.setReinsurerNumber(reinsurerNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getTreatyGroupNumber()
    {
        return accountingDetailVO.getTreatyGroupNumber();
    }

    /**
     * Setter.
     * @param treatyGroupNumber
     */
    public void setTreatyGroupNumber(String treatyGroupNumber)
    {
        this.accountingDetailVO.setTreatyGroupNumber(treatyGroupNumber);
    }

    /**
     * Getter.
     * @return
     */
    public int getCertainDuration()
    {
        return accountingDetailVO.getCertainDuration();
    }

    /**
     * Setter.
     * @param certainDuration
     */
    public void setCertainDuration(int certainDuration)
    {
        this.accountingDetailVO.setCertainDuration(certainDuration);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBatchAmount()
    {
        return SessionHelper.getEDITBigDecimal(accountingDetailVO.getBatchAmount());
    }

    /**
     * Setter.
     * @param batchAmount
     */
    public void setBatchAmount(EDITBigDecimal batchAmount)
    {
        this.accountingDetailVO.setBatchAmount(SessionHelper.getEDITBigDecimal(batchAmount));
    }

    /**
     * Getter.
     * @return
     */
    public String getBatchControl()
    {
        return accountingDetailVO.getBatchControl();
    }

    /**
     * Setter.
     * @param batchControl
     */
    public void setBatchControl(String batchControl)
    {
        this.accountingDetailVO.setBatchControl(batchControl);
    }

    /**
     * Getter.
     * @return
     */
    public String getVoucherSource()
    {
        return accountingDetailVO.getVoucherSource();
    }

    /**
     * Setter.
     * @param voucherSource
     */
    public void setVoucherSource(String voucherSource)
    {
        this.accountingDetailVO.setVoucherSource(voucherSource);
    }

    /**
     * Getter.
     * @return
     */
    public String getDescription()
    {
        return accountingDetailVO.getDescription();
    }

    /**
     * Setter.
     * @param description
     */
    public void setDescription(String description)
    {
        this.accountingDetailVO.setDescription(description);
    }

    /**
     * Getter.
     * @return
     */
    public String getAgentCode()
    {
        return accountingDetailVO.getAgentCode();
    }

    /**
     * Setter.
     * @param agentCode
     */
    public void setAgentCode(String agentCode)
    {
        this.accountingDetailVO.setAgentCode(agentCode);
    }

    /**
     * Getter.
     * @return
     */
    public String getSource()
    {
        return accountingDetailVO.getSource();
    }

    /**
     * Setter.
     * @param source
     */
    public void setSource(String source)
    {
        this.accountingDetailVO.setSource(source);
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractClientFK()
    {
        return SessionHelper.getPKValue(accountingDetailVO.getContractClientFK());
    }

    /**
     * Setter.
     * @param contractClientFK
     */
    public void setContractClientFK(Long contractClientFK)
    {
        this.accountingDetailVO.setContractClientFK(SessionHelper.getPKValue(contractClientFK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getPlacedAgentFK()
        {
        return SessionHelper.getPKValue(accountingDetailVO.getPlacedAgentFK());
        }

    /**
     * Setter.
     * @param placedAgentFK
     */
    public void setPlacedAgentFK(Long placedAgentFK)
    {
        this.accountingDetailVO.setPlacedAgentFK(SessionHelper.getPKValue(placedAgentFK));
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        AccountingDetailVO accountingDetailVO = (AccountingDetailVO) this.getVO();

        Accounting accounting = new Accounting();
        accounting.setStaging(stagingContext.getStaging());
        accounting.setCompanyName(accountingDetailVO.getCompanyName());
        accounting.setMarketingPackage(accountingDetailVO.getMarketingPackageName());
        accounting.setBusinessContract(accountingDetailVO.getBusinessContractName());
        accounting.setContractNumber(accountingDetailVO.getContractNumber());
        accounting.setQualNonQual(accountingDetailVO.getQualNonQualCT());
        accounting.setOptionCode(accountingDetailVO.getOptionCodeCT());
        accounting.setTransactionCode(accountingDetailVO.getTransactionCode());
        accounting.setReversalInd(accountingDetailVO.getReversalInd());
        accounting.setMemoCode(accountingDetailVO.getMemoCode());
        accounting.setStateCode(accountingDetailVO.getStateCodeCT());
        accounting.setAccountNumber(accountingDetailVO.getAccountNumber());
        accounting.setAccountName(accountingDetailVO.getAccountName());
        accounting.setAmount(new EDITBigDecimal(accountingDetailVO.getAmount()));
        accounting.setDebitCreditInd(accountingDetailVO.getDebitCreditInd());
        accounting.setEffectiveDate(new EDITDate(accountingDetailVO.getEffectiveDate()));
        accounting.setProcessDate(new EDITDate(accountingDetailVO.getProcessDate()));
        accounting.setFundNumber(accountingDetailVO.getFundNumber());
        accounting.setOutOfBalanceInd(accountingDetailVO.getOutOfBalanceInd());
        accounting.setAccountingPendingStatus(accountingDetailVO.getAccountingPendingStatus());
        accounting.setQualifiedType(accountingDetailVO.getQualifiedTypeCT());
        accounting.setOriginalContractNumber(accountingDetailVO.getOriginalContractNumber());
        accounting.setAccountingProcessDate(new EDITDate(accountingDetailVO.getAccountingProcessDate()));
        accounting.setAccountingPeriod(accountingDetailVO.getAccountingPeriod());
        accounting.setEDITTrxFK(accountingDetailVO.getEDITTrxFK());
        accounting.setChargeCode(accountingDetailVO.getChargeCode());
        accounting.setDistributionCode(accountingDetailVO.getDistributionCodeCT());
        accounting.setReinsurerNumber(accountingDetailVO.getReinsurerNumber());
        accounting.setTreatyGroupNumber(accountingDetailVO.getTreatyGroupNumber());
        accounting.setCertainDuration(accountingDetailVO.getCertainDuration());
        accounting.setBatchControl(accountingDetailVO.getBatchControl());
        accounting.setSource("VENS");
        accounting.setOperator(accountingDetailVO.getOperator());

        if (accountingDetailVO.getContractNumber() != null) {
            //need segPK, ContractGroupFK,
            Segment segment = Segment.getSegmentLimitedData(accountingDetailVO.getContractNumber());
	        if (segment != null) {
	            accounting.setIssueDate(segment.getIssueDate());
	
	//                Set<Deposits> deposits = segment.getDeposits();
	            Deposits[] deposits = Deposits.findBySuspenseFK(segment.getSegmentPK());
	            Iterator it = Arrays.asList(deposits).iterator();
	//                Iterator it = deposits.iterator();
	            EDITDate originalIssueDate = null;
	            while (it.hasNext()) {
	                Deposits deposit = (Deposits) it.next();
	                if (deposit.getExchangePolicyEffectiveDate() != null) {
	                    if (originalIssueDate == null ||
	                            deposit.getExchangePolicyEffectiveDate().before(originalIssueDate)) {
	                        originalIssueDate = deposit.getExchangePolicyEffectiveDate();
	                    }
	                }
	            }
	
	            accounting.setOriginalIssueDate(originalIssueDate);
	
	            ContractGroup group = ContractGroup.findBy_ContractGroupPK(segment.getContractGroupFK());
	            if (group != null)
	            {
	                accounting.setGroupNumber(group.getContractGroupNumber());
	                accounting.setGroupName(group.getClientRole().getClientDetail().getCorporateName());
	
	                ContractGroup caseContractGroup = group.getContractGroup();
	
	                accounting.setCaseName(caseContractGroup.getClientRole().getClientDetail().getCorporateName());
	                accounting.setCaseNumber(caseContractGroup.getContractGroupNumber());
	            }
	
	            List<ContractClient> clients = Arrays.asList(ContractClient.findBy_SegmentFK(segment.getSegmentPK()));
	            Set<ContractClient> contractClients = new HashSet<>();
	            for(ContractClient cc: clients)
	            {
	                contractClients.add(cc);
	            }
	
	//                Set<ContractClient> contractClients = segment.getContractClients();
	            it = contractClients.iterator();
	            while (it.hasNext())
	            {
	                ContractClient contractClient = (ContractClient) it.next();
	                ClientRole clientRole = contractClient.getClientRole();
	                if (clientRole.getRoleTypeCT().equalsIgnoreCase("Insured"))
	                {
	                    contractClient.stage(stagingContext, database);
	                }
	            }
	
	            List<AgentHierarchy> agentHierarchies= AgentHierarchy.findBy_SegmentPK(segment.getSegmentPK());
	            it = agentHierarchies.iterator();
	            while (it.hasNext())
	            {
	                AgentHierarchy agentHierarchy = (AgentHierarchy) it.next();
	                AgentSnapshot writingAgentSnapshot = AgentSnapshot.findWritingAgentSnapshotBy_AgentHierarchyPK(agentHierarchy.getAgentHierarchyPK());
	                stagingContext.setCurrentAgentHierarchy(agentHierarchy);
	
	                if(writingAgentSnapshot != null){
	                    agent.Agent agent = writingAgentSnapshot.getPlacedAgent().getAgentContract().getAgent();
	                    agent.stage(stagingContext, database);
	                }
	
	                if(writingAgentSnapshot != null && stagingContext.getCurrentAgent() != null){
	                    stagingContext.getCurrentAgent().setAdvancePercent(writingAgentSnapshot.getAdvancePercent());
	                    stagingContext.getCurrentAgent().setRecoveryPercent(writingAgentSnapshot.getRecoveryPercent());
	                    stagingContext.getCurrentAgent().setServicingAgentIndicator(writingAgentSnapshot.getServicingAgentIndicator());
	                }
	                AgentHierarchyAllocation agtHierAlloc = null;
	
	                if (accounting.getEffectiveDate().before(segment.getEffectiveDate()))
	                {
	                    agtHierAlloc = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), segment.getEffectiveDate());
	                }
	                else
	                {
	                    agtHierAlloc = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), accounting.getEffectiveDate());
	                }
	                // Added null check on agtHierAlloc - 9/13/2018 deck
	                if ((stagingContext.getCurrentAgent() != null) && (agtHierAlloc != null))
	                {
	                    stagingContext.getCurrentAgent().setSplitPercent(agtHierAlloc.getAllocationPercent());
	                    SessionHelper.saveOrUpdate(stagingContext.getCurrentAgent(), database);
	                }
	
	            }
	        }
        }

        stagingContext.setCurrentAccounting(accounting);

        if (stagingContext.getCurrentContractClient() != null)
        {
            stagingContext.getCurrentAccounting().setContractClient(stagingContext.getCurrentContractClient());
            stagingContext.getCurrentContractClient().addAccounting(stagingContext.getCurrentAccounting());
        }

        if (stagingContext.getCurrentAgent() != null)
        {
            stagingContext.getCurrentAccounting().setAgent(stagingContext.getCurrentAgent());
            stagingContext.getCurrentAgent().addAccounting(stagingContext.getCurrentAccounting());
        }

        SessionHelper.saveOrUpdate(accounting, database);

        return stagingContext;
    }
    
    public StagingContext stage(StagingContext stagingContext, String database, Segment segment)
    {
        AccountingDetailVO accountingDetailVO = (AccountingDetailVO) this.getVO();

        Accounting accounting = new Accounting();
        accounting.setStaging(stagingContext.getStaging());
        accounting.setCompanyName(accountingDetailVO.getCompanyName());
        accounting.setMarketingPackage(accountingDetailVO.getMarketingPackageName());
        accounting.setBusinessContract(accountingDetailVO.getBusinessContractName());
        accounting.setContractNumber(accountingDetailVO.getContractNumber());
        accounting.setQualNonQual(accountingDetailVO.getQualNonQualCT());
        accounting.setOptionCode(accountingDetailVO.getOptionCodeCT());
        accounting.setTransactionCode(accountingDetailVO.getTransactionCode());
        accounting.setReversalInd(accountingDetailVO.getReversalInd());
        accounting.setMemoCode(accountingDetailVO.getMemoCode());
        accounting.setStateCode(accountingDetailVO.getStateCodeCT());
        accounting.setAccountNumber(accountingDetailVO.getAccountNumber());
        accounting.setAccountName(accountingDetailVO.getAccountName());
        accounting.setAmount(new EDITBigDecimal(accountingDetailVO.getAmount()));
        accounting.setDebitCreditInd(accountingDetailVO.getDebitCreditInd());
        accounting.setEffectiveDate(new EDITDate(accountingDetailVO.getEffectiveDate()));
        accounting.setProcessDate(new EDITDate(accountingDetailVO.getProcessDate()));
        accounting.setFundNumber(accountingDetailVO.getFundNumber());
        accounting.setOutOfBalanceInd(accountingDetailVO.getOutOfBalanceInd());
        accounting.setAccountingPendingStatus(accountingDetailVO.getAccountingPendingStatus());
        accounting.setQualifiedType(accountingDetailVO.getQualifiedTypeCT());
        accounting.setOriginalContractNumber(accountingDetailVO.getOriginalContractNumber());
        accounting.setAccountingProcessDate(new EDITDate(accountingDetailVO.getAccountingProcessDate()));
        accounting.setAccountingPeriod(accountingDetailVO.getAccountingPeriod());
        accounting.setEDITTrxFK(accountingDetailVO.getEDITTrxFK());
        accounting.setChargeCode(accountingDetailVO.getChargeCode());
        accounting.setDistributionCode(accountingDetailVO.getDistributionCodeCT());
        accounting.setReinsurerNumber(accountingDetailVO.getReinsurerNumber());
        accounting.setTreatyGroupNumber(accountingDetailVO.getTreatyGroupNumber());
        accounting.setCertainDuration(accountingDetailVO.getCertainDuration());
        accounting.setBatchControl(accountingDetailVO.getBatchControl());
        accounting.setSource("VENS");
        accounting.setOperator(accountingDetailVO.getOperator());

        if (segment == null) {
            segment = Segment.getSegmentLimitedData(accountingDetailVO.getContractNumber());
        }
        
        if (segment != null) {
            accounting.setIssueDate(segment.getIssueDate());

            Deposits[] deposits = Deposits.findBySuspenseFK(segment.getSegmentPK());
            Iterator it = Arrays.asList(deposits).iterator();

            EDITDate originalIssueDate = null;
            while (it.hasNext()) {
                Deposits deposit = (Deposits) it.next();
                if (deposit.getExchangePolicyEffectiveDate() != null) {
                    if (originalIssueDate == null ||
                            deposit.getExchangePolicyEffectiveDate().before(originalIssueDate)) {
                        originalIssueDate = deposit.getExchangePolicyEffectiveDate();
                    }
                }
            }

            accounting.setOriginalIssueDate(originalIssueDate);

            ContractGroup group = ContractGroup.findBy_ContractGroupPK(segment.getContractGroupFK());
            if (group != null)
            {
                accounting.setGroupNumber(group.getContractGroupNumber());
                accounting.setGroupName(group.getClientRole().getClientDetail().getCorporateName());

                ContractGroup caseContractGroup = group.getContractGroup();

                accounting.setCaseName(caseContractGroup.getClientRole().getClientDetail().getCorporateName());
                accounting.setCaseNumber(caseContractGroup.getContractGroupNumber());
            }

            List<ContractClient> clients = Arrays.asList(ContractClient.findBy_SegmentFK(segment.getSegmentPK()));
            Set<ContractClient> contractClients = new HashSet<>();
            for(ContractClient cc: clients)
            {
                contractClients.add(cc);
            }

            it = contractClients.iterator();
            while (it.hasNext())
            {
                ContractClient contractClient = (ContractClient) it.next();
                ClientRole clientRole = contractClient.getClientRole();
                if (clientRole.getRoleTypeCT().equalsIgnoreCase("Insured"))
                {
                    contractClient.stage(stagingContext, database);
                }
            }

            List<AgentHierarchy> agentHierarchies= AgentHierarchy.findBy_SegmentPK(segment.getSegmentPK());
            it = agentHierarchies.iterator();
            while (it.hasNext())
            {
                AgentHierarchy agentHierarchy = (AgentHierarchy) it.next();
                AgentSnapshot writingAgentSnapshot = AgentSnapshot.findWritingAgentSnapshotBy_AgentHierarchyPK(agentHierarchy.getAgentHierarchyPK());
                stagingContext.setCurrentAgentHierarchy(agentHierarchy);

                if(writingAgentSnapshot != null){
                    agent.Agent agent = writingAgentSnapshot.getPlacedAgent().getAgentContract().getAgent();
                    agent.stage(stagingContext, database);
                }

                if(writingAgentSnapshot != null && stagingContext.getCurrentAgent() != null){
                    stagingContext.getCurrentAgent().setAdvancePercent(writingAgentSnapshot.getAdvancePercent());
                    stagingContext.getCurrentAgent().setRecoveryPercent(writingAgentSnapshot.getRecoveryPercent());
                    stagingContext.getCurrentAgent().setServicingAgentIndicator(writingAgentSnapshot.getServicingAgentIndicator());
                }
                
                AgentHierarchyAllocation agtHierAlloc = null;
                if (accounting.getEffectiveDate().before(segment.getEffectiveDate()))
                {
                    agtHierAlloc = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), segment.getEffectiveDate());
                }
                else
                {
                    agtHierAlloc = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), accounting.getEffectiveDate());
                }
                // Added null check on agtHierAlloc - 9/13/2018 deck
                if ((stagingContext.getCurrentAgent() != null) && (agtHierAlloc != null))
                {
                    stagingContext.getCurrentAgent().setSplitPercent(agtHierAlloc.getAllocationPercent());
                    SessionHelper.saveOrUpdate(stagingContext.getCurrentAgent(), database);
                }

            }
        }

        stagingContext.setCurrentAccounting(accounting);

        if (stagingContext.getCurrentContractClient() != null)
        {
            stagingContext.getCurrentAccounting().setContractClient(stagingContext.getCurrentContractClient());
            stagingContext.getCurrentContractClient().addAccounting(stagingContext.getCurrentAccounting());
        }

        if (stagingContext.getCurrentAgent() != null)
        {
            stagingContext.getCurrentAccounting().setAgent(stagingContext.getCurrentAgent());
            stagingContext.getCurrentAgent().addAccounting(stagingContext.getCurrentAccounting());
        }

        SessionHelper.saveOrUpdate(accounting, database);

        return stagingContext;
    }

    public String getDatabase()
    {
        return AccountingDetail.DATABASE;
    }

    /******************************************* Static Methods *******************************************************/
    /******************************************* Static Methods *******************************************************/
    /******************************************* Static Methods *******************************************************/

    /**
     * Finder.
     *
     * @param accountingDetailPK
     */
    public static final AccountingDetail findByPK(long accountingDetailPK)
    {
        AccountingDetail accountingDetail = null;

        AccountingDetailVO[] accountingDetailVOs = new AccountingDetailDAO().findByPK(accountingDetailPK);

        if (accountingDetailVOs != null)
        {
            accountingDetail = new AccountingDetail(accountingDetailVOs[0]);
        }

        return accountingDetail;
    }

    /**
     * Finder.
     * @param contractNumber
     * @param processDate
     * @return
     */
    public static final AccountingDetail[] findBy_ContractNumber_ProcessDateGTE(String contractNumber, String processDate)
    {
        AccountingDetailVO[] accountingDetailVOs = new AccountingDetailDAO().findBy_ContractNumber_ProcessDateGTE(contractNumber, processDate);

        return (AccountingDetail[]) CRUDEntityImpl.mapVOToEntity(accountingDetailVOs, AccountingDetail.class);
    }

    /**
     * Finds the AccountingDetails with the selected companyName and accountingProcessDate.
     * @param companyName
     * @param accountingProcessDate
     * @return
     */
    public static AccountingDetail[] findByCompanyName_AccountingProcessDate(String companyName, EDITDate extractDate)
    {
        String hql = "select accountingDetail from AccountingDetail accountingDetail" +
                     " where accountingDetail.CompanyName = :companyName" +
                     " and accountingDetail.AccountingProcessDate = :extractDate";

        Map params = new HashMap();

        params.put("companyName", companyName);
        params.put("extractDate", extractDate);

        List results = null;

        results = SessionHelper.executeHQL(hql, params, AccountingDetail.DATABASE);

        return (AccountingDetail[]) results.toArray(new AccountingDetail[results.size()]);
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }
}
