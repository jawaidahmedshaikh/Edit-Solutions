/*
 * User: gfrosti
 * Date: Nov 4, 2003
 * Time: 10:49:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package role;

import edit.common.*;
import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;

import role.dm.dao.*;
import contract.Check;

import client.*;
import agent.*;

import edit.common.EDITDateTime;
import event.CommissionHistory;



public class ClientRoleFinancial extends HibernateEntity implements CRUDEntity
{
    private ClientRoleFinancialImpl clientRoleFinancialImpl;
    private ClientRoleFinancialVO clientRoleFinancialVO;

    private EDITDate estimatedNextCheckDate;

    private ClientRole clientRole;


    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public ClientRoleFinancial()
    {
        this.clientRoleFinancialImpl = new ClientRoleFinancialImpl();
        this.clientRoleFinancialVO = new ClientRoleFinancialVO();
    }

    public ClientRoleFinancial(long clientRoleFinancialPK)
    {
        this.clientRoleFinancialImpl.load(this, clientRoleFinancialPK);
    }

    public ClientRoleFinancial(ClientRoleFinancialVO clientRoleFinancialVO)
    {
        this();
        this.clientRoleFinancialVO = clientRoleFinancialVO;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastCheckDate()
    {
        EDITDate lastCheckDate = null;

        if (getLastCheckDateTime() != null)
        {
            lastCheckDate = getLastCheckDateTime().getEDITDate();
        }

        return lastCheckDate;
    }

    public void save()
    {
        clientRoleFinancialImpl.save(this);
    }

    public void delete()
    {
        clientRoleFinancialImpl.delete(this);
    }

    public long getPK()
    {
        return clientRoleFinancialVO.getClientRoleFinancialPK();
    }

    public void setVO(VOObject voObject)
    {
        this.clientRoleFinancialVO = (ClientRoleFinancialVO) voObject;
    }

    public boolean isNew()
    {
        return clientRoleFinancialImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return clientRoleFinancialImpl.cloneCRUDEntity(this);
    }

    public VOObject getVO()
    {
        return clientRoleFinancialVO;
    }

    public void updateAdvanceAndRecovery(EDITBigDecimal advanceAmount,
                                         String commissionTypeCT) throws Exception
    {
        if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE))
        {
            clientRoleFinancialVO.setAdvanceAmt((new EDITBigDecimal(clientRoleFinancialVO.getAdvanceAmt()).addEditBigDecimal(advanceAmount)).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
        {
            clientRoleFinancialVO.setAdvRecoveredAmt((new EDITBigDecimal(clientRoleFinancialVO.getAdvRecoveredAmt()).addEditBigDecimal(advanceAmount)).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK))
        {
            clientRoleFinancialVO.setAdvanceAmt((new EDITBigDecimal(clientRoleFinancialVO.getAdvanceAmt()).subtractEditBigDecimal(advanceAmount)).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL))
        {
            clientRoleFinancialVO.setAdvanceAmt((new EDITBigDecimal(clientRoleFinancialVO.getAdvanceAmt()).addEditBigDecimal(advanceAmount)).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
        {
            clientRoleFinancialVO.setAdvRecoveredAmt((new EDITBigDecimal(clientRoleFinancialVO.getAdvRecoveredAmt()).subtractEditBigDecimal(advanceAmount)).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
        {
            clientRoleFinancialVO.setAdvanceAmt(new EDITBigDecimal(clientRoleFinancialVO.getAdvanceAmt()).subtractEditBigDecimal(advanceAmount).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
        {
            clientRoleFinancialVO.setAdvanceAmt(new EDITBigDecimal(clientRoleFinancialVO.getAdvanceAmt()).addEditBigDecimal(advanceAmount).getBigDecimal());
        }

        save();
    }

    public void addToCommissionBalance(EDITBigDecimal amount, String commissionTypeCT) throws Exception
    {
        if (!commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY) &&
            !commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
        {
            if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_NEGATIVE_EARNINGS) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN)  ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
            {
                clientRoleFinancialVO.setCommBalance((new EDITBigDecimal(clientRoleFinancialVO.getCommBalance()).subtractEditBigDecimal(amount)).getBigDecimal());
                clientRoleFinancialVO.setLifetimeCommBalance((new EDITBigDecimal(clientRoleFinancialVO.getLifetimeCommBalance()).subtractEditBigDecimal(amount)).getBigDecimal());
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
                {
                    clientRoleFinancialVO.setLifetimeAdvanceBalance((new EDITBigDecimal(clientRoleFinancialVO.getLifetimeAdvanceBalance()).subtractEditBigDecimal(amount)).getBigDecimal());
                }
            }
            else
            {
                clientRoleFinancialVO.setCommBalance((new EDITBigDecimal(clientRoleFinancialVO.getCommBalance()).addEditBigDecimal(amount)).getBigDecimal());
                clientRoleFinancialVO.setLifetimeCommBalance((new EDITBigDecimal(clientRoleFinancialVO.getLifetimeCommBalance()).addEditBigDecimal(amount)).getBigDecimal());
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK_REVERSAL) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_ADJUSTMENT) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    clientRoleFinancialVO.setLifetimeAdvanceBalance((new EDITBigDecimal(clientRoleFinancialVO.getLifetimeAdvanceBalance()).addEditBigDecimal(amount)).getBigDecimal());
                }
            }
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
        {
            clientRoleFinancialVO.setLifetimeAdvanceBalance((new EDITBigDecimal(clientRoleFinancialVO.getLifetimeAdvanceBalance()).subtractEditBigDecimal(amount)).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
        {
            clientRoleFinancialVO.setLifetimeAdvanceBalance((new EDITBigDecimal(clientRoleFinancialVO.getLifetimeAdvanceBalance()).addEditBigDecimal(amount)).getBigDecimal());
        }

        save();
    }

    public void updateAmountTaxable(EDITBigDecimal amountTaxable, String commissionTypeCT) throws Exception
    {
        if (!commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY) &&
            !commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
        {
            if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_NEGATIVE_EARNINGS) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
            {
                clientRoleFinancialVO.setAmountTaxableYTD((new EDITBigDecimal(clientRoleFinancialVO.getAmountTaxableYTD()).subtractEditBigDecimal(amountTaxable)).getBigDecimal());
            }
            else
            {
                clientRoleFinancialVO.setAmountTaxableYTD((new EDITBigDecimal(clientRoleFinancialVO.getAmountTaxableYTD()).addEditBigDecimal(amountTaxable)).getBigDecimal());
            }

            save();
        }
    }

    public void updateCumFields(EDITBigDecimal commissionAmount, String commissionTypeCT)
    {
        if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR))
        {
            clientRoleFinancialVO.setFirstYearCum(new EDITBigDecimal(clientRoleFinancialVO.getFirstYearCum()).addEditBigDecimal(commissionAmount).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RENEWAL))
        {
            clientRoleFinancialVO.setRenewalCum(new EDITBigDecimal(clientRoleFinancialVO.getRenewalCum()).addEditBigDecimal(commissionAmount).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN))
        {
            clientRoleFinancialVO.setFirstYearCum(new EDITBigDecimal(clientRoleFinancialVO.getFirstYearCum()).subtractEditBigDecimal(commissionAmount).getBigDecimal());
        }
        else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN))
        {
            clientRoleFinancialVO.setRenewalCum(new EDITBigDecimal(clientRoleFinancialVO.getRenewalCum()).subtractEditBigDecimal(commissionAmount).getBigDecimal());
        }
    }

    public void addToBonusCommissionBalance(EDITBigDecimal amount, String commissionTypeCT) throws Exception
    {
        clientRoleFinancialVO.setBonusBalance((new EDITBigDecimal(clientRoleFinancialVO.getBonusBalance()).addEditBigDecimal(amount)).getBigDecimal());

        save();
    }

    public void addToRedirectBalance(EDITBigDecimal amount, String commissionTypeCT) throws Exception
    {
        if (!commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY) &&
            !commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
        {
            if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_NEGATIVE_EARNINGS) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
            {
                clientRoleFinancialVO.setRedirectBalance((new EDITBigDecimal(clientRoleFinancialVO.getCommBalance()).subtractEditBigDecimal(amount)).getBigDecimal());
            }
            else
            {
                clientRoleFinancialVO.setRedirectBalance((new EDITBigDecimal(clientRoleFinancialVO.getCommBalance()).addEditBigDecimal(amount)).getBigDecimal());
            }
        }

        save();
    }

    public void addToAmountTaxableYTD(EDITBigDecimal amountTaxable) throws Exception
    {
        clientRoleFinancialVO.setAmountTaxableYTD(new EDITBigDecimal(clientRoleFinancialVO.getAmountTaxableYTD()).addEditBigDecimal(amountTaxable).getBigDecimal());

        save();
    }

    public void subtractToDBAmount(EDITBigDecimal debitBalanceAmount)
    {
        clientRoleFinancialVO.setDBAmount(new EDITBigDecimal(clientRoleFinancialVO.getDBAmount()).subtractEditBigDecimal(debitBalanceAmount).getBigDecimal());

        save();
    }

    public void setupCheckTransaction(String forceOutMinBal, String operator) throws Exception
    {
        clientRoleFinancialImpl.setupCheckTransaction(this, forceOutMinBal, operator);
    }

    public void associateClientRole(ClientRole clientRole) throws Exception
    {
        clientRoleFinancialImpl.associateClientRole(this, clientRole);
    }

    public static ClientRoleFinancial[] findByClientRolePK(long clientRolePK)
    {
        return ClientRoleFinancialImpl.findByClientRolePK(clientRolePK);
    }

    public static void runYearEndClientBalance() throws Exception
    {
        ClientRoleFinancialImpl.runYearEndClientBalance();
    }

    /**
     * Finder.
     * @param agentContractPK
     * @return
     */
    public static ClientRoleFinancial findByAgentContractPK(long agentContractPK)
    {
        ClientRoleFinancial clientRoleFinancial = null;

        ClientRoleFinancialVO[] clientRoleFinancialVOs = new ClientRoleFinancialDAO().findByAgentContractPK(agentContractPK);

        if (clientRoleFinancialVOs != null)
        {
            clientRoleFinancial = new ClientRoleFinancial(clientRoleFinancialVOs[0]);
        }

        return clientRoleFinancial;
    }


    public static ClientRoleFinancial findByClientRole(long clientRoleFK)
    {
        ClientRoleFinancial clientRoleFinancial = null;

        ClientRoleFinancialVO[] clientRoleFinancialVO = DAOFactory.getClientRoleFinancialDAO().findByClientRolePK(clientRoleFK);

        if (clientRoleFinancialVO != null)
        {
            clientRoleFinancial = new ClientRoleFinancial(clientRoleFinancialVO[0]);
        }

        return clientRoleFinancial;

    }
    /**
     * Updates the NextCheckDate.
     */
    public EDITDate generateNextCheckDate()
    {
        EDITDate nextCheckDate = new Check().generateNextCheckDate(this);

        return nextCheckDate;
    }

    /**
     * Getter.
     */
    // Do not change the method name. If you have to change in agentFinancial.jsp also.
    public EDITDate getEstimatedNextCheckDate()
    {
        String paymentModeCT = null;

        ClientRole clientRole = ClientRole.findByPK(this.getClientRoleFK());
        Preference preference = clientRole.getPreference();
        if (preference != null)
        {
            paymentModeCT = preference.getPaymentModeCT();
        }

        // there will be always one agent.
        Agent agent = clientRole.getAgent();

        if (paymentModeCT != null)
        {
            if (paymentModeCT.equals(Preference.AUTO))
            {
                this.estimatedNextCheckDate = agent.getHireDate();
            }
            else if (getLastCheckDate() == null)
            {
                this.estimatedNextCheckDate = agent.getHireDate();

                this.estimatedNextCheckDate.addMode(paymentModeCT);
            }
            else
            {
                EDITDate lastCheckDate = getLastCheckDate();

                this.estimatedNextCheckDate = lastCheckDate.addMode(paymentModeCT);
            }
        }
        else
        {
            if (getLastCheckDate() == null)
            {
                this.estimatedNextCheckDate = agent.getHireDate();
            }
            else
            {
                this.estimatedNextCheckDate = getLastCheckDate();
            }
        }

        return this.estimatedNextCheckDate;
    }

    /**
     * The associated ClientRole.
     * @return
     */
    public ClientRole getClientRole()
    {
        return clientRole;
    }
    
    /**
   * Getter.
   * @return
   */
    public Long getClientRoleFK()
    {
      return SessionHelper.getPKValue(clientRoleFinancialVO.getClientRoleFK());
    }
    
    /**
   * Setter.
   * @param clientRoleFK
   */
    public void setClientRoleFK(Long clientRoleFK)
    {
      clientRoleFinancialVO.setClientRoleFK(SessionHelper.getPKValue(clientRoleFK));
    }
    
    public void setClientRole(ClientRole clientRole)
    {
        this.clientRole = clientRole;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdvRecoveredAmt()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getAdvRecoveredAmt());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdvanceAmt()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getAdvanceAmt());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAmountTaxableYTD()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getAmountTaxableYTD());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBonusBalance()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getBonusBalance());
    }

    /**
     * Getter.
     * @return
     */
    public Long getClientRoleFinancialPK()
    {
        return SessionHelper.getPKValue(clientRoleFinancialVO.getClientRoleFinancialPK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCommBalance()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getCommBalance());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDBAmount()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getDBAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFirstYearCum()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getFirstYearCum());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIDBAmount()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getIDBAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getIDBLastValDate()
    {
        return SessionHelper.getEDITDate(clientRoleFinancialVO.getIDBLastValDate());
    }


    /**
     * Getter.
     * @return
     */
    public EDITDateTime getLastBonusCheckDateTime()
    {
        return SessionHelper.getEDITDateTime(clientRoleFinancialVO.getLastBonusCheckDateTime());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLastCheckAmount()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getLastCheckAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getLastCheckDateTime()
    {
        return SessionHelper.getEDITDateTime(this.clientRoleFinancialVO.getLastCheckDateTime());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLastStatementAmount()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getLastStatementAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNYComm()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getNYComm());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNYPrem()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getNYPrem());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRedirectBalance()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getRedirectBalance());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRenewalCum()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getRenewalCum());
    }

    /**
     * Getter.
     * @return
     */
    public String getStatementProducedInd()
    {
        return clientRoleFinancialVO.getStatementProducedInd();
    }

    /**
     * Setter.
     * @param advRecoveredAmt
     */
    public void setAdvRecoveredAmt(EDITBigDecimal advRecoveredAmt)
    {
        clientRoleFinancialVO.setAdvRecoveredAmt(SessionHelper.getEDITBigDecimal(advRecoveredAmt));
    }

    /**
     * Setter.
     * @param advanceAmt
     */
    public void setAdvanceAmt(EDITBigDecimal advanceAmt)
    {
        clientRoleFinancialVO.setAdvanceAmt(SessionHelper.getEDITBigDecimal(advanceAmt));
    }

    /**
     * Setter.
     * @param amountTaxableYTD
     */
    public void setAmountTaxableYTD(EDITBigDecimal amountTaxableYTD)
    {
        clientRoleFinancialVO.setAmountTaxableYTD(SessionHelper.getEDITBigDecimal(amountTaxableYTD));
    }

    /**
     * Setter.
     * @param bonusBalance
     */
    public void setBonusBalance(EDITBigDecimal bonusBalance)
    {
        clientRoleFinancialVO.setBonusBalance(SessionHelper.getEDITBigDecimal(bonusBalance));
    }

    /**
     * Setter.
     * @param clientRoleFinancialPK
     */
    public void setClientRoleFinancialPK(Long clientRoleFinancialPK)
    {
        clientRoleFinancialVO.setClientRoleFinancialPK(SessionHelper.getPKValue(clientRoleFinancialPK));
    }

    /**
     * Setter.
     * @param commBalance
     */
    public void setCommBalance(EDITBigDecimal commBalance)
    {
        clientRoleFinancialVO.setCommBalance(SessionHelper.getEDITBigDecimal(commBalance));
    }

    /**
     * Setter.
     * @param DBAmount
     */
    public void setDBAmount(EDITBigDecimal DBAmount)
    {
        clientRoleFinancialVO.setDBAmount(SessionHelper.getEDITBigDecimal(DBAmount));
    }

    /**
     * Setter.
     * @param firstYearCum
     */
    public void setFirstYearCum(EDITBigDecimal firstYearCum)
    {
        clientRoleFinancialVO.setFirstYearCum(SessionHelper.getEDITBigDecimal(firstYearCum));
    }

    /**
     * Setter.
     * @param IDBAmount
     */
    public void setIDBAmount(EDITBigDecimal IDBAmount)
    {
        clientRoleFinancialVO.setIDBAmount(SessionHelper.getEDITBigDecimal(IDBAmount));
    }

    /**
     * Setter.
     * @param IDBLastValDate
     */
    public void setIDBLastValDate(EDITDate IDBLastValDate)
    {
        clientRoleFinancialVO.setIDBLastValDate(SessionHelper.getEDITDate(IDBLastValDate));
    }


    /**
     * Setter.
     * @param lastBonusCheckDateTime
     */
    public void setLastBonusCheckDateTime(EDITDateTime lastBonusCheckDateTime)
    {
        clientRoleFinancialVO.setLastBonusCheckDateTime(SessionHelper.getEDITDateTime(lastBonusCheckDateTime));
    }

    /**
     * Setter.
     * @param lastCheckAmount
     */
    public void setLastCheckAmount(EDITBigDecimal lastCheckAmount)
    {
        clientRoleFinancialVO.setLastCheckAmount(SessionHelper.getEDITBigDecimal(lastCheckAmount));
    }

    /**
     * Setter.
     * @param lastCheckDateTime
     */
    public void setLastCheckDateTime(EDITDateTime lastCheckDateTime)
    {
        clientRoleFinancialVO.setLastCheckDateTime(SessionHelper.getEDITDateTime(lastCheckDateTime));
    }

    /**
     * Setter.
     * @param lastStatementAmount
     */
    public void setLastStatementAmount(EDITBigDecimal lastStatementAmount)
    {
        clientRoleFinancialVO.setLastStatementAmount(SessionHelper.getEDITBigDecimal(lastStatementAmount));
    }

    /**
     * Setter.
     * @param lastStatementDateTime
     */
    public void setLastStatementDateTime(EDITDateTime lastStatementDateTime)
    {
        clientRoleFinancialVO.setLastStatementDateTime(SessionHelper.getEDITDateTime(lastStatementDateTime));
    }

    /**
     * Setter.
     * @param NYComm
     */
    public void setNYComm(EDITBigDecimal NYComm)
    {
        clientRoleFinancialVO.setNYComm(SessionHelper.getEDITBigDecimal(NYComm));
    }

    /**
     * Setter.
     * @param NYPrem
     */
    public void setNYPrem(EDITBigDecimal NYPrem)
    {
        clientRoleFinancialVO.setNYPrem(SessionHelper.getEDITBigDecimal(NYPrem));
    }

    /**
     * Setter.
     * @param redirectBalance
     */
    public void setRedirectBalance(EDITBigDecimal redirectBalance)
    {
        clientRoleFinancialVO.setRedirectBalance(SessionHelper.getEDITBigDecimal(redirectBalance));
    }

    /**
     * Setter.
     * @param renewalCum
     */
    public void setRenewalCum(EDITBigDecimal renewalCum)
    {
        clientRoleFinancialVO.setRenewalCum(SessionHelper.getEDITBigDecimal(renewalCum));
    }

    /**
     * Setter.
     * @param statementProducedInd
     */
    public void setStatementProducedInd(String statementProducedInd)
    {
        clientRoleFinancialVO.setStatementProducedInd(statementProducedInd);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLifetimeCommBalance()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getLifetimeCommBalance());
    }

    /**
     * Setter.
     * @param lifetimeCommBalance
     */
    public void setLifetimeCommBalance(EDITBigDecimal lifetimeCommBalance)
    {
        clientRoleFinancialVO.setLifetimeCommBalance(SessionHelper.getEDITBigDecimal(lifetimeCommBalance));
    }

    /**
     * Getter.
     */
    public EDITBigDecimal getLifetimeAdvanceBalance()
    {
        return SessionHelper.getEDITBigDecimal(clientRoleFinancialVO.getLifetimeAdvanceBalance());
    }

    /**
     * Setter.
     * @param lifetimeAdvanceBalance
     */
    public void setLifetimeAdvanceBalance(EDITBigDecimal lifetimeAdvanceBalance)
    {
        clientRoleFinancialVO.setLifetimeAdvanceBalance(SessionHelper.getEDITBigDecimal(lifetimeAdvanceBalance));
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getPriorLastStatementDateTime()
    {
        return SessionHelper.getEDITDateTime(clientRoleFinancialVO.getPriorLastStatementDateTime());
    }

    /**
     * Setter.
     * @param priorLastStatementDateTime
     */
    public void setPriorLastStatementDateTime(EDITDateTime priorLastStatementDateTime)
    {
        clientRoleFinancialVO.setPriorLastStatementDateTime(SessionHelper.getEDITDateTime(priorLastStatementDateTime));
    }

    public EDITDateTime getLastStatementDateTime()
	{
        return SessionHelper.getEDITDateTime(clientRoleFinancialVO.getLastStatementDateTime());
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ClientRoleFinancial.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ClientRoleFinancial.DATABASE);
	}

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ClientRoleFinancial.DATABASE;
    }

    /************************************ Static Methods ********************************************/

    /**
     * Finder by PK.
     * @param clientRoleFinancialPK
     * @return
     */
    public static final ClientRoleFinancial findByPK(Long clientRoleFinancialPK)
    {
        return (ClientRoleFinancial) SessionHelper.get(ClientRoleFinancial.class, clientRoleFinancialPK, ClientRoleFinancial.DATABASE);
    }
}
