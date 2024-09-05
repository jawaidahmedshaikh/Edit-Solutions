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

public class ConvertSegmentActivityHistory
{
    CRUD crud;
    Connection conn = null;
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    public ConvertSegmentActivityHistory(Connection conn, CRUD crud)
    {
        this.conn = conn;
        this.crud = crud;

        //Use this boolean to Insert rows with Keys
        crud.setRestoringRealTime(true);
    }


    public void convertSegmentActivtyHistory(HashMap countTable) throws Exception
    {
        int countIn = 0;
        int countOut = 0;
        int FHcountOut = 0;
        int displayCount = 0;
        countTable.put("WIcountOut", countOut);
        countTable.put("SUcountIn", countIn);
        countTable.put("SUcountOut", countOut);
        countTable.put("INcountIn", countIn);
        countTable.put("INcountOut", countOut);
        countTable.put("BUcountIn", countIn);
        countTable.put("BUcountOut", countOut);
        countTable.put("CHcountIn", countIn);
        countTable.put("CHcountOut", countOut);
        countTable.put("IScountIn", countIn);
        countTable.put("IScountOut", countOut);

        convertSuspenseVO(countTable);

        GroupSetupVO groupSetupVO = null;
        ClientSetupVO clientSetupVO = null;
        ContractSetupVO contractSetupVO = null;
        EDITTrxVO editTrxVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM SegmentActivityHistory";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            displayCount++;
            //GroupSetupVO
            groupSetupVO = new GroupSetupVO();
            contractSetupVO = new ContractSetupVO();
            clientSetupVO = new ClientSetupVO();
            editTrxVO = new EDITTrxVO();
            long editTrxHistoryPK = new Long(rs.getLong("SegmentActivityHistoryPK"));
            Integer codeTableKey = new Integer(rs.getInt("TransactionTypeCT"));
            if (codeTableKey == 0)
            {
                System.out.println("Unable to convert History, TransactionType is Null, TransactionKey = " + editTrxHistoryPK);
            }
            else
            {
                EDITTrxHistoryVO[] editTrxHistoryVOs = event.dm.dao.DAOFactory.getEDITTrxHistoryDAO().findByEditTrxHistoryPK(editTrxHistoryPK);
                if (editTrxHistoryVOs  == null && codeTableKey != 0)
                {
                    crud.startTransaction();
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

                    codeTableKey = new Integer(rs.getInt("DistributionCodeCT"));
                    String distributionInd = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                    groupSetupVO.setDistributionCodeCT(distributionInd);
                    //**********

                    //ContractSetupVO
                    long segmentFK = new Long(rs.getLong("SegmentFK"));
                    contractSetupVO.setContractSetupPK(CRUD.getNextAvailableKey());
                    contractSetupVO.setGroupSetupFK(groupSetupVO.getGroupSetupPK());
                    contractSetupVO.setSegmentFK(segmentFK);

                    codeTableKey = new Integer(rs.getInt("DeathStatusCT"));
                    String deathStatus = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                    if (deathStatus != null && deathStatus.equals(""))
                    {
                        deathStatus = null;
                    }
                    contractSetupVO.setDeathStatusCT(deathStatus);
                    
                    //**********

                    codeTableKey = new Integer(rs.getInt("TransactionTypeCT"));
                    String trxType = null;
                    if (codeTableKey == 169)
                    {
                        trxType = "Death";
                    }
                    else
                    {
                        trxType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                    }
                    editTrxVO.setTransactionTypeCT(trxType);

                    long clientDetailFK = new Long(rs.getLong("ClientFK"));
                    ContractClientVO contractClientVO = findContractClientForHistory(trxType, clientDetailFK, segmentFK);

                    //ClientSetupVO
                    clientSetupVO.setClientSetupPK(CRUD.getNextAvailableKey());
                    clientSetupVO.setContractSetupFK(contractSetupVO.getContractSetupPK());

                    if (contractClientVO != null)
                    {
                        clientSetupVO.setContractClientFK(contractClientVO.getContractClientPK());
                        clientSetupVO.setClientRoleFK(contractClientVO.getClientRoleFK());
                    }

                    //EDITTrxVO
                    editTrxVO.setEDITTrxPK(CRUD.getNextAvailableKey());
                    editTrxVO.setClientSetupFK(clientSetupVO.getClientSetupPK());
                    editTrxVO.setSequenceNumber(rs.getInt("SequenceNumber"));

                    String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
                    effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                    editTrxVO.setEffectiveDate(effectiveDate);

                    editTrxVO.setStatus(Util.initString((String)rs.getString("Status"), null));
                    editTrxVO.setPendingStatus("H");
                    editTrxVO.setTaxYear(rs.getInt("TaxYear"));
                    editTrxVO.setTrxIsRescheduledInd("N");
                    editTrxVO.setCommissionStatus("N");
                    editTrxVO.setLookBackInd("N");
                    editTrxVO.setNoAccountingInd("N");
                    editTrxVO.setNoCommissionInd("N");
                    editTrxVO.setZeroLoadInd("N");
                    editTrxVO.setNoCorrespondenceInd("N");
                    editTrxVO.setPremiumDueCreatedIndicator("N");

                    String maintDateTime = Util.initString((String)rs.getString("MaintDateTime"), null);
                    if (maintDateTime != null)
                    {
                        if (maintDateTime.length() == 10)
                        {
                            maintDateTime = maintDateTime + " " + EDITDateTime.DEFAULT_MIN_TIME;
                        }
                    }
                    editTrxVO.setMaintDateTime(maintDateTime);

                    editTrxVO.setOperator(Util.initString((String)rs.getString("Operator"), null));
                    //*******

                    //EDITTrxHistory
                    EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryVO();
                    editTrxHistoryVO.setEDITTrxHistoryPK(editTrxHistoryPK);
                    editTrxHistoryVO.setEDITTrxFK(editTrxVO.getEDITTrxPK());
                    editTrxHistoryVO.setAccountingPendingStatus(Util.initString((String)rs.getString("AccountingPendingInd"), null));

                    String controlNumber = Util.initString((String)rs.getString("ControlNumber"), null);
                    if (controlNumber != null && controlNumber.equals(""))
                    {
                        controlNumber = null;
                    }
                    editTrxHistoryVO.setControlNumber(controlNumber);

                    String cycleDate = Util.initString((String)rs.getString("CycleDate"), null);
                    cycleDate = CommonDatabaseConversionFunctions.convertDate(cycleDate);
                    if (cycleDate != null)
                    {
                        cycleDate = checkCycleDateValues(cycleDate, editTrxHistoryPK);
                    }
                    editTrxHistoryVO.setCycleDate(cycleDate);

                    String releaseDate = Util.initString((String)rs.getString("ReleaseDate"), null);
                    releaseDate = CommonDatabaseConversionFunctions.convertDate(releaseDate);
                    editTrxHistoryVO.setReleaseDate(releaseDate);

                    String returnDate = Util.initString((String)rs.getString("ReturnedDate"), null);
                    returnDate = CommonDatabaseConversionFunctions.convertDate(returnDate);
                    editTrxHistoryVO.setReturnDate(returnDate);

                    String processDate = Util.initString((String)rs.getString("ProcessDate"), null);
                    processDate = CommonDatabaseConversionFunctions.convertDate(processDate);
                    if (processDate != null)
                    {
                        processDate = processDate + " " + EDITDateTime.DEFAULT_MIN_TIME;
                    }
                    editTrxHistoryVO.setProcessDateTime(processDate);

                    //******
                    //FinancialHistory
                    FinancialHistoryVO financialHistoryVO = new FinancialHistoryVO();
                    financialHistoryVO.setFinancialHistoryPK(CRUD.getNextAvailableKey());
                    financialHistoryVO.setEDITTrxHistoryFK(editTrxHistoryPK);
                    financialHistoryVO.setGrossAmount(rs.getBigDecimal("GrossAmount"));
                    financialHistoryVO.setNetAmount(rs.getBigDecimal("NetAmount"));
                    financialHistoryVO.setCheckAmount(rs.getBigDecimal("CheckAmount"));
                    financialHistoryVO.setFreeAmount(rs.getBigDecimal("FreeAmount"));
                    financialHistoryVO.setTaxableBenefit(rs.getBigDecimal("TaxableBenefit"));
                    FHcountOut++;

                    codeTableKey = new Integer(rs.getInt("DisbursementSourceCT"));
                    String disbursementSource = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                    financialHistoryVO.setDisbursementSourceCT(disbursementSource);

                    editTrxHistoryVO.addFinancialHistoryVO(financialHistoryVO);

                    ceateWithholdingHistory(rs, editTrxHistoryVO, countTable);

                    associateSuspenseVO(editTrxHistoryVO, countTable);
                    getAllocationActivityHistory(editTrxHistoryVO, countTable);
                    getChargeHistory(editTrxHistoryVO, countTable);

                    editTrxVO.addEDITTrxHistoryVO(editTrxHistoryVO);
                    clientSetupVO.addEDITTrxVO(editTrxVO);
                    contractSetupVO.addClientSetupVO(clientSetupVO);
                    groupSetupVO.addContractSetupVO(contractSetupVO);


                    try
                    {
                        crud.createOrUpdateVOInDBRecursively(groupSetupVO);
                        crud.commitTransaction();
                        countOut++;
                        if (displayCount == 25000)
                        {
                            System.out.println("Number of records processed = " + countOut);
                            displayCount = 0;
                        }
//                        System.gc();

                    }
                    catch (Exception e)
                    {
                        crud.rollbackTransaction();
                        System.out.println(e);
                        e.printStackTrace();
                        System.out.println("OFFENDING RECORD COUNT = " + countIn + "; editTrxHistoryPK = " + editTrxHistoryPK);
                    }

                    groupSetupVO = null;
                    contractSetupVO = null;
                    clientSetupVO = null;
                    editTrxVO = null;
                }
                else
                {
                    countOut++;
                    if (displayCount == 25000)
                    {
                        System.out.println("Number of records processed = " + countOut);
                        displayCount = 0;
                    }
                }
            }
        }

        s.close();
        rs.close();

        countTable.put("FIcountOut", FHcountOut);
        countTable.put("ETcountIn", countIn);
        countTable.put("ETcountOut", countOut);

    }

    private String checkCycleDateValues(String cycleDate, long editTrxHistoryPK)
    {
        if (cycleDate.equals("2006/11/00"))
        {
            cycleDate = "2006/11/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/11/01 from 2006/11/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2003/04/00"))
        {
            cycleDate = "2003/04/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2003/04/01 from 2003/04/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2004/03/00"))
        {
            cycleDate = "2004/03/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2004/03/01 from 2004/03/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2004/06/00"))
        {
            cycleDate = "2004/06/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2004/06/01 from 2004/06/00 for History key = " + editTrxHistoryPK);
        }

        if (cycleDate.equals("2004/07/00"))
        {
            cycleDate = "2004/07/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2004/07/01 from 2004/07/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2003/05/00"))
        {
            cycleDate = "2003/05/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2003/05/01 from 2003/05/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2004/09/00"))
        {
            cycleDate = "2004/09/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2004/09/01 from 2004/09/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2004/10/00"))
        {
            cycleDate = "2004/10/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2004/10/01 from 2004/10/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/02/00"))
        {
            cycleDate = "2005/02/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/02/01 from 2005/02/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/04/00"))
        {
            cycleDate = "2005/04/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/04/01 from 2005/04/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/06/00"))
        {
            cycleDate = "2005/06/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/06/01 from 2005/06/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/07/00"))
        {
            cycleDate = "2005/07/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/07/01 from 2005/07/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/08/00"))
        {
            cycleDate = "2005/08/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/08/01 from 2005/08/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/09/00"))
        {
            cycleDate = "2005/09/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/09/01 from 2005/09/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/11/00"))
        {
            cycleDate = "2005/11/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/11/01 from 2005/11/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2005/12/00"))
        {
            cycleDate = "2005/12/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2005/12/01 from 2005/12/00 for History key = " + editTrxHistoryPK);
        }

        if (cycleDate.equals("2006/02/00"))
        {
            cycleDate = "2006/02/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/02/01 from 2006/02/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2006/03/00"))
        {
            cycleDate = "2006/03/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/03/01 from 2006/03/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2006/05/00"))
        {
            cycleDate = "2006/05/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/05/01 from 2006/05/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2006/06/00"))
        {
            cycleDate = "2006/06/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/06/01 from 2006/06/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2006/08/00"))
        {
            cycleDate = "2006/08/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/08/01 from 2006/08/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2006/09/00"))
        {
            cycleDate = "2006/09/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/09/01 from 2006/09/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2006/11/00"))
        {
            cycleDate = "2006/11/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2006/11/01 from 2006/11/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/02/00"))
        {
            cycleDate = "2007/02/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/02/01 from 2007/02/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/03/00"))
        {
            cycleDate = "2007/03/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/03/01 from 2007/03/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/04/00"))
        {
            cycleDate = "2007/04/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/04/01 from 2007/04/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/05/00"))
        {
            cycleDate = "2007/05/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/05/01 from 2007/05/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/06/00"))
        {
            cycleDate = "2007/06/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/06/01 from 2007/06/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/07/00"))
        {
            cycleDate = "2007/07/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/07/01 from 2007/07/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/08/00"))
        {
            cycleDate = "2007/08/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/08/01 from 2007/08/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/09/00"))
        {
            cycleDate = "2007/09/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/09/01 from 2007/09/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/10/00"))
        {
            cycleDate = "2007/10/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/10/01 from 2007/10/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/11/00"))
        {
            cycleDate = "2007/11/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/11/01 from 2007/11/00 for History key = " + editTrxHistoryPK);
        }
        if (cycleDate.equals("2007/12/00"))
        {
            cycleDate = "2007/12/01";
            System.out.println("CycleDate on EDITTrxHistory set to 2007/12/01 from 2007/12/00 for History key = " + editTrxHistoryPK);
        }
        return cycleDate;
    }

    private void ceateWithholdingHistory(ResultSet rs, EDITTrxHistoryVO editTrxHistoryVO, HashMap countTable) throws Exception
    {
        int countOut = 0;

        EDITBigDecimal federalWithholding = new EDITBigDecimal(rs.getBigDecimal("FederalWithholding"));
        EDITBigDecimal stateWithholding = new EDITBigDecimal(rs.getBigDecimal("StateWithholding"));
        EDITBigDecimal countyWithholding = new EDITBigDecimal(rs.getBigDecimal("CountyWithholding"));
        EDITBigDecimal cityWithholding = new EDITBigDecimal(rs.getBigDecimal("CityWithholding"));

        if (federalWithholding.isGT("0") || stateWithholding.isGT("0") || countyWithholding.isGT("0") ||
            cityWithholding.isGT("0"))
        {
            countOut = (Integer) countTable.get("WIcountOut");
            countOut++;
            WithholdingHistoryVO withholdingHistoryVO = new WithholdingHistoryVO();
            withholdingHistoryVO.setWithholdingHistoryPK(CRUD.getNextAvailableKey());
            withholdingHistoryVO.setEDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
            withholdingHistoryVO.setFederalWithholdingAmount(federalWithholding.getBigDecimal());
            withholdingHistoryVO.setStateWithholdingAmount(stateWithholding.getBigDecimal());
            withholdingHistoryVO.setCountyWithholdingAmount(countyWithholding.getBigDecimal());
            withholdingHistoryVO.setCityWithholdingAmount(cityWithholding.getBigDecimal());

            editTrxHistoryVO.addWithholdingHistoryVO(withholdingHistoryVO);

            countTable.put("WIcountOut", countOut);
        }
    }

    private void convertSuspenseVO(HashMap countTable)  throws Exception
    {
        int countIn = (Integer) countTable.get("SUcountIn");
        int countOut = (Integer) countTable.get("SUcountOut");
        SuspenseVO suspenseVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Suspense";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {

            Integer codeTableKey = new Integer(rs.getInt("DirectionCT"));
            String direction = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);

            if (direction.equalsIgnoreCase("Remove"))
            {
                long suspensePK = new Long(rs.getLong("SuspensePK"));
                Suspense suspense = Suspense.findByPK(suspensePK);

                if (suspense == null)
                {
                    countIn++;
                    suspenseVO = new SuspenseVO();


                    suspenseVO.setDirectionCT(direction);
                    suspenseVO.setSuspensePK(suspensePK);

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

                    String maintDateTime = Util.initString((String)rs.getString("MaintDateTime"), null);
                    if (maintDateTime != null)
                    {
                        if (maintDateTime.equals("2004/06/00"))
                        {
                            maintDateTime = "2004/06/01";
                            System.out.println("MaintDateTime on Suspense set to 2004/06/01 from 2004/06/00 for Suspense key = " + suspensePK);
                        }
                        else if (maintDateTime.equals("2004/10/00"))
                        {
                            maintDateTime = "2004/10/01";
                            System.out.println("MaintDateTime on Suspense set to 2004/10/01 from 2004/10/00 for Suspense key = " + suspensePK);
                        }
                        else if (maintDateTime.equals("2005/04/00"))
                        {
                            maintDateTime = "2005/04/01";
                            System.out.println("MaintDateTime on Suspense set to 2005/04/01 from 2005/04/00 for Suspense key = " + suspensePK);
                        }
                        else if (maintDateTime.equals("2005/09/00"))
                        {
                            maintDateTime = "2005/09/01";
                            System.out.println("MaintDateTime on Suspense set to 2005/09/01 from 2005/09/00 for Suspense key = " + suspensePK);
                        }
                        else if (maintDateTime.equals("2005/11/00"))
                        {
                            maintDateTime = "2005/11/01";
                            System.out.println("MaintDateTime on Suspense set to 2005/11/01 from 2005/11/00 for Suspense key = " + suspensePK);
                        }
                        else if (maintDateTime.equals("2006/11/00"))
                        {
                            maintDateTime = "2006/11/01";
                            System.out.println("MaintDateTime on Suspense set to 2006/11/01 from 2006/11/00 for Suspense key = " + suspensePK);
                        }
                        if (maintDateTime.length() == 10)
                        {
                            maintDateTime = maintDateTime + " " + EDITDateTime.DEFAULT_MIN_TIME;
                        }
                    }
                    suspenseVO.setMaintDateTime(maintDateTime);

                    String processDate = Util.initString((String)rs.getString("ProcessDate"), null);
                    processDate = CommonDatabaseConversionFunctions.convertDate(processDate);
                    suspenseVO.setProcessDate(processDate);

                    codeTableKey = new Integer(rs.getInt("PremiumTypeCT"));
                    String premiumType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                    suspenseVO.setPremiumTypeCT(premiumType);
                    // save the suspense separately  - crud can't traverse two parents
                    crud.createOrUpdateVOInDB(suspenseVO);
                    countOut++;

                    suspenseVO = null;
                }
            }
        }

        s.close();
        rs.close();

        countTable.put("SUcountIn", countIn);
        countTable.put("SUcountOut", countOut);

    }

    private void associateSuspenseVO(EDITTrxHistoryVO editTrxHistoryVO, HashMap countTable)  throws Exception
    {
        int countIn = (Integer) countTable.get("IScountIn");
        int countOut = (Integer) countTable.get("IScountOut");

        InSuspenseVO inSuspenseVO = null;
        long editTrxHistoryPK = editTrxHistoryVO.getEDITTrxHistoryPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Suspense WHERE SegmentActivityHistoryFK = " + editTrxHistoryPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {

            long suspensePK = new Long(rs.getLong("SuspensePK"));
            EDITBigDecimal suspenseAmount = new EDITBigDecimal(rs.getBigDecimal("SuspenseAmount"));

            inSuspenseVO = new InSuspenseVO();
            inSuspenseVO.setInSuspensePK(CRUD.getNextAvailableKey());
            inSuspenseVO.setEDITTrxHistoryFK(editTrxHistoryPK);
            inSuspenseVO.setAmount(suspenseAmount.getBigDecimal());
            inSuspenseVO.setSuspenseFK(suspensePK);

            editTrxHistoryVO.addInSuspenseVO(inSuspenseVO);
        }


        s.close();
        rs.close();

        countTable.put("IScountIn", countIn);
        countTable.put("IScountOut", countOut);
    }

    private void getAllocationActivityHistory(EDITTrxHistoryVO editTrxHistoryVO, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("INcountIn");
        int countOut = (Integer) countTable.get("INcountOut");

        BucketHistoryVO bucketHistoryVO = null;
        InvestmentHistoryVO investmentHistoryVO = null;
        long editTrxHistoryPK = editTrxHistoryVO.getEDITTrxHistoryPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM AllocationActivityHistory WHERE SegmentActivityHistoryFK = " + editTrxHistoryPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            EDITBigDecimal dollars = new EDITBigDecimal(rs.getBigDecimal("Dollars"));
            EDITBigDecimal units = new EDITBigDecimal(rs.getBigDecimal("Units"));
            String toFromInd = Util.initString((String)rs.getString("ToFromIndicator"), null);

            bucketHistoryVO = new BucketHistoryVO();
            bucketHistoryVO.setBucketHistoryPK(CRUD.getNextAvailableKey());
            bucketHistoryVO.setEDITTrxHistoryFK(editTrxHistoryPK);
            bucketHistoryVO.setDollars(dollars.getBigDecimal());
            bucketHistoryVO.setUnits(units.getBigDecimal());

            String previousValDate = Util.initString((String)rs.getString("PrevValuationDate"), null);
            previousValDate = CommonDatabaseConversionFunctions.convertDate(previousValDate);
            bucketHistoryVO.setPreviousValuationDate(previousValDate);

            EDITBigDecimal prevValue = new EDITBigDecimal(rs.getBigDecimal("PreviousValue"));
            bucketHistoryVO.setPreviousValue(prevValue.getBigDecimal());
            bucketHistoryVO.setInterestEarnedGuaranteed(rs.getBigDecimal("InterestEarnedGuar"));
            bucketHistoryVO.setInterestEarnedCurrent(rs.getBigDecimal("InterestEarnedCurr"));
            bucketHistoryVO.setCumDollars(rs.getBigDecimal("CumulativeDollars"));
            bucketHistoryVO.setCumUnits(rs.getBigDecimal("CumulativeUnits"));
            bucketHistoryVO.setToFromStatus(toFromInd);

            countIn++;
            investmentHistoryVO = new InvestmentHistoryVO();
            investmentHistoryVO.setInvestmentHistoryPK(CRUD.getNextAvailableKey());
            investmentHistoryVO.setEDITTrxHistoryFK(editTrxHistoryPK);
            long allocationPK = new Long(rs.getLong("AllocationFK"));
            investmentHistoryVO.setInvestmentFK(allocationPK);
            investmentHistoryVO.setInvestmentDollars(dollars.getBigDecimal());
            investmentHistoryVO.setInvestmentUnits(units.getBigDecimal());
            investmentHistoryVO.setToFromStatus(toFromInd);
            investmentHistoryVO.setGainLoss(rs.getBigDecimal("GainLoss"));
            editTrxHistoryVO.addInvestmentHistoryVO(investmentHistoryVO);

            long bucketFK = getBucketKey(allocationPK);
            bucketHistoryVO.setBucketFK(bucketFK);

            editTrxHistoryVO.addBucketHistoryVO(bucketHistoryVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("INcountIn", countIn);
        countTable.put("INcountOut", countOut);
        countTable.put("BUcountIn", countIn);
        countTable.put("BUcountOut", countOut);

    }

    private void getChargeHistory(EDITTrxHistoryVO editTrxHistoryVO, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("CHcountIn");
        int countOut = (Integer) countTable.get("CHcountIn");

        ChargeHistoryVO chargeHistoryVO = null;
        long editTrxHistoryPK = editTrxHistoryVO.getEDITTrxHistoryPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Charge WHERE SegmentActivityHistoryFK = " + editTrxHistoryPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;

            chargeHistoryVO = new ChargeHistoryVO();
            chargeHistoryVO.setChargeHistoryPK(CRUD.getNextAvailableKey());
            chargeHistoryVO.setEDITTrxHistoryFK(editTrxHistoryPK);
            chargeHistoryVO.setChargeAmount(rs.getBigDecimal("Amount"));

            Integer codeTableKey = new Integer(rs.getInt("TypeCT"));
            String typeCT = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            chargeHistoryVO.setChargeTypeCT(typeCT);

            editTrxHistoryVO.addChargeHistoryVO(chargeHistoryVO);

            countOut++;

        }

        s.close();
        rs.close();

        countTable.put("CHcountIn", countIn);
        countTable.put("CHcountOut", countOut);

        chargeHistoryVO = null;
    }

    private long getBucketKey(long allocationPK) throws Exception
    {
        long bucketFK = 0;

        BucketVO[] bucketVOs = DAOFactory.getBucketDAO().findByInvestmentFK(allocationPK, false, null);

        if (bucketVOs != null)
        {
            bucketFK = bucketVOs[0].getBucketPK();
        }

        return bucketFK;
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

    private ContractClientVO findContractClientForHistory(String trxType, long clientDetailFK, long segmentFK)
    {
        String roleTypeCT = new CommonDatabaseConversionFunctions().getRoleTypeForTrx(trxType, segmentFK, crud);

        ContractClientVO[] contractClientVOs = DAOFactory.getContractClientDAO().findByClientDetailPK_SegmentPK_RoleType(clientDetailFK, segmentFK, roleTypeCT);

        ContractClientVO contractClientVO = null;
        if (contractClientVOs != null)
        {
            contractClientVO = contractClientVOs[0];
        }
        else
        {
            contractClientVOs = DAOFactory.getContractClientDAO().findByClientDetailPK_SegmentPK_RoleType(clientDetailFK, segmentFK, "OWN");
            if (contractClientVOs != null)
            {
                contractClientVO = contractClientVOs[0];
            }
        }

        contractClientVOs = null;

        return contractClientVO;
    }
}
