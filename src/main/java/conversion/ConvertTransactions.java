/*
 * 
 * User: cgleason
 * Date: Jan 31, 2007
 * Time: 3:54:25 PM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package conversion;

import edit.services.db.*;
import edit.common.*;
import edit.common.vo.*;

import java.sql.*;
import java.util.*;

import fission.utility.*;
import contract.dm.dao.*;
import contract.*;
import role.*;
import event.common.*;
import event.*;

public class ConvertTransactions
{
    CRUD crud;
    Connection conn;
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    public ConvertTransactions(Connection conn, CRUD crud)
    {
        this.conn = conn;
        this.crud = crud;

        //Use this boolean to Insert rows with Keys
        crud.setRestoringRealTime(true);
    }



    public void convertTransactions(HashMap countTable) throws Exception
    {
        int countIn = 0;
        int countOut = 0;

        countTable.put("SEcountIn", countIn);
        countTable.put("SEcountOut", countOut);
        countTable.put("CHcountIn", countIn);
        countTable.put("CHcountOut", countOut);
        countTable.put("SUcountIn", countIn);
        countTable.put("SUcountOut", countOut);

        GroupSetupVO groupSetupVO = null;
        ClientSetupVO clientSetupVO = null;
        ContractSetupVO contractSetupVO = null;
        EDITTrxVO editTrxVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Transactions";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            //GroupSetupVO
            countIn++;
            long trxKey = new Long(rs.getLong("TransactionsPK"));

            Integer codeTableKey = new Integer(rs.getInt("TransactionTypeCT"));
            if (codeTableKey == 0)
            {
                System.out.println("Unable to convert Transaction, TransactionType is Null, TransactionKey = " + trxKey);
            }
            else
            {
                groupSetupVO = new GroupSetupVO();

                groupSetupVO.setGroupSetupPK(CRUD.getNextAvailableKey());
                String memoCode = Util.initString((String)rs.getString("MemoCode"), null);
                if (memoCode != null && memoCode.equals(""))
                {
                    memoCode = null;
                }
                groupSetupVO.setMemoCode(memoCode);

                codeTableKey = new Integer(rs.getInt("PremiumTypeCT"));
                String premiumType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                groupSetupVO.setPremiumTypeCT(premiumType);

                codeTableKey = new Integer(rs.getInt("GrossNetIndCT"));
                String grossNetInd = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                groupSetupVO.setGrossNetStatusCT(grossNetInd);

                codeTableKey = new Integer(rs.getInt("DistributionCodeCT"));
                String distributionInd = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                groupSetupVO.setDistributionCodeCT(distributionInd);
                //**********

                EDITBigDecimal amount = new EDITBigDecimal(rs.getBigDecimal("Amount"));

                //ContractSetupVO
                contractSetupVO = new ContractSetupVO();
                contractSetupVO.setContractSetupPK(CRUD.getNextAvailableKey());
                long segmentFK = new Long(rs.getLong("SegmentFK"));
                contractSetupVO.setSegmentFK(segmentFK);
                contractSetupVO.setGroupSetupFK(groupSetupVO.getGroupSetupPK());

                contractSetupVO.setPolicyAmount(amount.getBigDecimal());
                contractSetupVO.setCostBasis(rs.getBigDecimal("CostBasis"));

                codeTableKey = new Integer(rs.getInt("DeathStatusCT"));
                String deathStatus = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                if (deathStatus != null && deathStatus.equals(""))
                {
                    deathStatus = null;
                }

                contractSetupVO.setDeathStatusCT(deathStatus);

                //**********
                String trxType = null;
                codeTableKey = new Integer(rs.getInt("TransactionTypeCT"));
                if (codeTableKey == 169)
                {
                    trxType = "Death";
                }
                else
                {
                    trxType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                }

                long[] contractClientPKs = new CommonDatabaseConversionFunctions().getActiveClients(segmentFK, trxType, crud);
                String dueDate = null;
                int sequenceNumber = 0;

                if (contractClientPKs != null)
                {
                    for (int i = 0; i < contractClientPKs.length; i++)
                    {
                        //ClientSetupVO
                        clientSetupVO = new ClientSetupVO();
                        clientSetupVO.setClientSetupPK(CRUD.getNextAvailableKey());
                        clientSetupVO.setContractSetupFK(contractSetupVO.getContractSetupPK());
                        clientSetupVO.setContractClientFK(contractClientPKs[i]);

                        ContractClientVO[] contractClientVOs = DAOFactory.getContractClientDAO().findByContractClientPK(contractClientPKs[i], false, null);
                        if (contractClientVOs != null)
                        {
                            clientSetupVO.setClientRoleFK(contractClientVOs[0].getClientRoleFK());
                        }

                        //EDITTrxVO
                        editTrxVO = new EDITTrxVO();
                        if (i > 0)
                        {
                            editTrxVO.setEDITTrxPK(CRUD.getNextAvailableKey());
                            editTrxVO.setDueDate(dueDate);
                            sequenceNumber++;
                        }
                        else
                        {
                            editTrxVO.setEDITTrxPK(trxKey);
                            sequenceNumber = rs.getInt("SequenceNumber");
                            getScheduledEvent(groupSetupVO, editTrxVO, countTable);
                            dueDate = editTrxVO.getDueDate();
                        }
                        editTrxVO.setClientSetupFK(clientSetupVO.getClientSetupPK());
                        editTrxVO.setSequenceNumber(sequenceNumber);
                        editTrxVO.setTransactionTypeCT(trxType);

                        String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
                        effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                        editTrxVO.setEffectiveDate(effectiveDate);

                        editTrxVO.setStatus(Util.initString((String)rs.getString("Status"), null));
                        editTrxVO.setPendingStatus("P");
                        editTrxVO.setTaxYear(rs.getInt("TaxYear"));
                        editTrxVO.setTrxAmount(amount.getBigDecimal());
                        editTrxVO.setTrxIsRescheduledInd("N");
                        editTrxVO.setCommissionStatus("N");
                        editTrxVO.setLookBackInd("N");
                        editTrxVO.setNoAccountingInd("N");
                        editTrxVO.setNoCommissionInd("N");
                        editTrxVO.setZeroLoadInd("N");
                        editTrxVO.setNoCorrespondenceInd("N");
                        editTrxVO.setPremiumDueCreatedIndicator("N");

                        String maintDateTime = Util.initString((String)rs.getString("MaintDateTime"), null);
                        if (maintDateTime.length() == 10)
                        {
                            maintDateTime = maintDateTime + " " + EDITDateTime.DEFAULT_MIN_TIME;
                        }
                        editTrxVO.setMaintDateTime(maintDateTime);

                        editTrxVO.setOperator(Util.initString((String)rs.getString("Operator"), null));

                        clientSetupVO.addEDITTrxVO(editTrxVO);
                        contractSetupVO.addClientSetupVO(clientSetupVO);
                    }
                }

                getChargeVO(groupSetupVO, editTrxVO, countTable);
                getSuspenseVO(contractSetupVO, segmentFK, countTable);


                groupSetupVO.addContractSetupVO(contractSetupVO);

                crud.createOrUpdateVOInDBRecursively(groupSetupVO);
                countOut++;
            }
        }

        s.close();
        rs.close();

        countTable.put("EDcountIn", countIn);
        countTable.put("EDcountOut", countOut);
    }


    private void getScheduledEvent(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("SEcountIn");
        int countOut = (Integer) countTable.get("SEcountOut");

        ScheduledEventVO scheduledEventVO = null;
        long editTrxPK = editTrxVO.getEDITTrxPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM ScheduledEvent WHERE TransactionsFK = " + editTrxPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            scheduledEventVO = new ScheduledEventVO();
            long scheduledEventPK = new Long(rs.getLong("ScheduledEventPK"));
            scheduledEventVO.setScheduledEventPK(scheduledEventPK);

            // Set dueDate in the EDITTRX
            String dueDate = Util.initString((String)rs.getString("DueDate"), null);
            dueDate = CommonDatabaseConversionFunctions.convertDate(dueDate);
            editTrxVO.setDueDate(dueDate);

            String startDate = Util.initString((String)rs.getString("StartDate"), null);
            startDate = CommonDatabaseConversionFunctions.convertDate(startDate);
            scheduledEventVO.setStartDate(startDate);

            String stopDate = Util.initString((String)rs.getString("StopDate"), null);
            stopDate = CommonDatabaseConversionFunctions.convertDate(stopDate);

            if (stopDate != null)
            {
                if (stopDate.equals("2010/06/31"))
                {
                    stopDate = "2010/06/30";
                    System.out.println("StopDate  on ScheduledEvent set to 2010/06/30 from 2010/06/31 for ScheduledEvent key = " + scheduledEventPK);
                }
            }
            scheduledEventVO.setStopDate(stopDate);

            scheduledEventVO.setLastDayOfMonthInd(Util.initString((String)rs.getString("LastDayOfMonthIndicator"), "N"));

            Integer codeTableKey = new Integer(rs.getInt("FrequencyCT"));
            String frequency = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            scheduledEventVO.setFrequencyCT(frequency);

            scheduledEventVO.setCostOfLivingInd("N");

            codeTableKey = new Integer(rs.getInt("LifeContingentCT"));
            String lifeContingent = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            scheduledEventVO.setLifeContingentCT(lifeContingent);

            groupSetupVO.addScheduledEventVO(scheduledEventVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("SEcountIn", countIn);
        countTable.put("SEcountOut", countOut);
    }

    private void getChargeVO(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("CHcountIn");
        int countOut = (Integer) countTable.get("CHcountOut");

        ChargeVO chargeVO = null;
        long editTrxPK = editTrxVO.getEDITTrxPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM TrxChargeOverride WHERE TransactionsFK = " + editTrxPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            chargeVO = new ChargeVO();
            chargeVO.setChargePK(new Long(rs.getLong("TrxChargeOverridePK")));
            chargeVO.setChargeAmount(rs.getBigDecimal("Amount"));

            Integer codeTableKey = new Integer(rs.getInt("TypeCT"));
            String type = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            chargeVO.setChargeTypeCT(type);

            groupSetupVO.addChargeVO(chargeVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("CHcountIn", countIn);
        countTable.put("CHcountOut", countOut);
    }

    private void getSuspenseVO(ContractSetupVO contractSetupVO, long segmentFK, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("SUcountIn");
        int countOut = (Integer) countTable.get("SUcountOut");

        SuspenseVO suspenseVO = null;
        OutSuspenseVO outSuspenseVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Suspense WHERE SegmentFK = " + segmentFK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            Integer codeTableKey = new Integer(rs.getInt("DirectionCT"));
            String direction = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);

            if (direction.equalsIgnoreCase("Apply"))
            {
                countIn++;
                suspenseVO = new SuspenseVO();
                long suspensePK = new Long(rs.getLong("SuspensePK"));
                suspenseVO.setSuspensePK(suspensePK);
                suspenseVO.setDirectionCT(direction);

                String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
                effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                suspenseVO.setEffectiveDate(effectiveDate);

                String contractNumber = getContractNumber(new Long(rs.getLong("SegmentFK")));
                suspenseVO.setUserDefNumber(contractNumber);

                EDITBigDecimal suspenseAmount = new EDITBigDecimal(rs.getBigDecimal("SuspenseAmount"));
                suspenseVO.setSuspenseAmount(suspenseAmount.getBigDecimal());
                suspenseVO.setMemoCode(Util.initString((String)rs.getString("MemoCode"), null));
                suspenseVO.setOriginalContractNumber(Util.initString((String)rs.getString("OriginalContractNumber"), null));
                suspenseVO.setOriginalMemoCode(Util.initString((String)rs.getString("OriginalMemoCode"), null));
                suspenseVO.setOriginalAmount(rs.getBigDecimal("OriginalAmount"));
                suspenseVO.setPendingSuspenseAmount(rs.getBigDecimal("PendingSuspenseAmount"));
                suspenseVO.setAccountingPendingInd(Util.initString((String)rs.getString("AccountingPendingInd"), null));
                suspenseVO.setMaintenanceInd(Util.initString((String)rs.getString("MaintenanceInd"), null));
                suspenseVO.setOperator(Util.initString((String)rs.getString("Operator"), null));
                suspenseVO.setMaintDateTime(Util.initString((String)rs.getString("MaintDateTime"), null));

                String processDate = Util.initString((String)rs.getString("ProcessDate"), null);
                processDate = CommonDatabaseConversionFunctions.convertDate(processDate);
                suspenseVO.setProcessDate(processDate);

                codeTableKey = new Integer(rs.getInt("PremiumTypeCT"));
                String premiumType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                suspenseVO.setPremiumTypeCT(premiumType);
                crud.createOrUpdateVOInDB(suspenseVO);

                outSuspenseVO = new OutSuspenseVO();
                outSuspenseVO.setOutSuspensePK(CRUD.getNextAvailableKey());
                outSuspenseVO.setContractSetupFK(contractSetupVO.getContractSetupPK());
                outSuspenseVO.setAmount(suspenseAmount.getBigDecimal());
                outSuspenseVO.setSuspenseFK(suspensePK);

//                suspenseVO.addOutSuspenseVO(outSuspenseVO);
                contractSetupVO.addOutSuspenseVO(outSuspenseVO);

                countOut++;
            }
        }


        s.close();
        rs.close();

        countTable.put("SUcountIn", countIn);
        countTable.put("SUcountOut", countOut);
    }

    private String getContractNumber(long segmentPK)   throws Exception
    {
        String contractNumber = null;

        Statement s = conn.createStatement();

        String sql = "SELECT Segment.ContractNumber FROM Segment WHERE SegmentPK = " + segmentPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            contractNumber = Util.initString((String)rs.getString("ContractNumber"), null);
        }

        s.close();
        rs.close();

        return contractNumber;
    }
}
