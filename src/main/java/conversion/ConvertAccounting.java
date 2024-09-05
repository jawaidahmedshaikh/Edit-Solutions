/*
 * 
 * User: cgleason
 * Date: Jan 26, 2007
 * Time: 11:45:30 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package conversion;

import edit.common.vo.*;
import edit.common.*;
import edit.services.db.*;

import java.util.*;
import java.sql.*;

import fission.utility.*;

public class ConvertAccounting
{
    CRUD crud;
    Connection conn;
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    public ConvertAccounting(Connection conn, CRUD crud) throws Exception
    {
        this.conn = conn;
        this.crud = crud;

        //Use this boolean to Insert rows with Keys
        crud.setRestoringRealTime(true);
    }


    public void convertAccountingDetailTable(HashMap countTable)  throws Exception
    {
        int countIn = 0;
        int countOut = 0;
        int commitCount = 0;

        AccountingDetailVO accountingDetailVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM AccountingDetail";

        ResultSet rs = s.executeQuery(sql);


        while (rs.next())
        {
            if (commitCount == 0)
            {
                crud.startTransaction();
            }

            countIn++;
            accountingDetailVO = new AccountingDetailVO();
            accountingDetailVO.setAccountingDetailPK(new Long(rs.getLong("AccountingDetailPK")));
            accountingDetailVO.setCompanyName(Util.initString((String)rs.getString("CompanyName"), null));
            accountingDetailVO.setMarketingPackageName(Util.initString((String)rs.getString("MarketingPackageName"), null));
            accountingDetailVO.setBusinessContractName(Util.initString((String)rs.getString("BusinessContractName"), null));
            accountingDetailVO.setContractNumber(Util.initString((String)rs.getString("ContractNumber"), null));

            String qualNonQualInd = Util.initString((String)rs.getString("QualNonQualInd"), null);
            if (qualNonQualInd.equalsIgnoreCase("N"))
            {
                accountingDetailVO.setQualNonQualCT("Nonqualified");
            }
            else if (qualNonQualInd.equalsIgnoreCase("Q"))
            {
                accountingDetailVO.setQualNonQualCT("Qualified");
            }
            else
            {
                accountingDetailVO.setQualNonQualCT(qualNonQualInd);
            }

            accountingDetailVO.setOptionCodeCT(Util.initString((String)rs.getString("OptionCode"), null));
            accountingDetailVO.setTransactionCode(Util.initString((String)rs.getString("TransactionCode"), null));
            accountingDetailVO.setReversalInd(Util.initString((String)rs.getString("ReversalInd"), null));
            accountingDetailVO.setMemoCode(Util.initString((String)rs.getString("MemoCode"), null));
            accountingDetailVO.setStateCodeCT(Util.initString((String)rs.getString("StateCode"), null));
            accountingDetailVO.setAccountNumber(Util.initString((String)rs.getString("AccountNumber"), null));
            accountingDetailVO.setAccountName(Util.initString((String)rs.getString("AccountName"), null));
            accountingDetailVO.setAmount(rs.getBigDecimal("Amount"));

            String debitCreditInd = Util.initString((String)rs.getString("DebitCreditInd"), null);
            if (debitCreditInd.equalsIgnoreCase("D"))
            {
                accountingDetailVO.setDebitCreditInd("Debit");
            }
            else
            {
                accountingDetailVO.setDebitCreditInd("Credit");
            }

            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate != null)
            {
                CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                accountingDetailVO.setEffectiveDate(effectiveDate);
            }

            String processDate = Util.initString((String)rs.getString("ProcessDate"), null);
            if (processDate != null)
            {
                CommonDatabaseConversionFunctions.convertDate(processDate);
                accountingDetailVO.setProcessDate(processDate);
            }

            String fundNumber = Util.initString((String)rs.getString("FundNumber"), null);
            if (fundNumber.equals(""))
            {
                fundNumber = null;
            }

            accountingDetailVO.setFundNumber(fundNumber);

            String outOfBalanceInd = Util.initString((String)rs.getString("OutOfBalanceInd"), null);
            if (outOfBalanceInd.equals(""))
            {
                outOfBalanceInd = null;
            }
            
            accountingDetailVO.setOutOfBalanceInd(outOfBalanceInd);

            crud.createOrUpdateVOInDB(accountingDetailVO);

            if (commitCount == 100)
            {
                crud.commitTransaction();
                commitCount = 0;
            }

            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("ADcountIN", countIn);
        countTable.put("ADcountOut", countOut);
    }


    public void convertElementTable(HashMap countTable)  throws Exception
    {
        int countIn = 0;
        int countOut = 0;
        countTable.put("ELcountIn", countIn);
        countTable.put("ELcountOut", countOut);
        countTable.put("EScountIn", countIn);
        countTable.put("EScountOut", countOut);
        countTable.put("ECRcountIn", countIn);
        countTable.put("ECRcountOut", countOut);
        countTable.put("ACcountIn", countIn);
        countTable.put("ACcountOut", countOut);

        ElementVO elementVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Element";

        ResultSet rs = s.executeQuery(sql);

        crud.startTransaction();

        while (rs.next())
        {
            countIn++;
            elementVO = new ElementVO();
            elementVO.setElementPK(new Long(rs.getLong("ElementPK")));

            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate != null)
            {
                CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                elementVO.setEffectiveDate(effectiveDate);
            }

            elementVO.setSequenceNumber(rs.getInt("SequenceNumber"));
            elementVO.setProcess(Util.initString((String)rs.getString("Process"), null));
            elementVO.setEvent(Util.initString((String)rs.getString("Event"), null));
            elementVO.setEventType(Util.initString((String)rs.getString("EventType"), null));

            String elementName = Util.initString((String)rs.getString("ElementName"), null);
            if (elementName != null && elementName.equalsIgnoreCase("FedWithholding"))
            {
                elementName = "FedWithhldng";
            }

            elementVO.setElementName(elementName);
            elementVO.setOperator(Util.initString((String)rs.getString("Operator"), null));
            elementVO.setMaintDateTime(Util.initString((String)rs.getString("MaintDateTime"), null));

            convertElementStructure(elementVO, countTable);
            convertElementCompanyRelation(elementVO, countTable);

            crud.createOrUpdateVOInDBRecursively(elementVO);

            countOut++;
        }

        crud.commitTransaction();
        s.close();
        rs.close();

        countTable.put("ELcountIn", countIn);
        countTable.put("ELcountOut", countOut);
    }


    private void convertElementStructure(ElementVO elementVO, HashMap countTable)   throws Exception
    {
        int countIn = (Integer)countTable.get("EScountIn");
        int countOut = (Integer)countTable.get("EScountOut");

        ElementStructureVO elementStructureVO = null;
        long elementPK = elementVO.getElementPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM ElementStructure WHERE ElementFK = " + elementPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            elementStructureVO = new ElementStructureVO();
            elementStructureVO.setElementStructurePK(new Long(rs.getLong("ElementStructurePK")));
            elementStructureVO.setElementFK(elementPK);
            elementStructureVO.setMemoCode(Util.initString((String)rs.getString("MemoCode"), null));
            elementStructureVO.setCertainPeriod(rs.getInt("CertainPeriod"));

            String qualNonQual = Util.initString((String)rs.getString("QualNonQual"), null);
            if (qualNonQual.equalsIgnoreCase("N"))
            {
                elementStructureVO.setQualNonQualCT("Nonqualified");
            }
            else if (qualNonQual.equalsIgnoreCase("Q"))
            {
                elementStructureVO.setQualNonQualCT("Qualified");
            }
            else
            {
                elementStructureVO.setQualNonQualCT(qualNonQual);
            }

            elementStructureVO.setFundFK(new Long(rs.getLong("FundFK")));
            elementStructureVO.setSwitchEffectInd(Util.initString((String)rs.getString("SwitchEffectInd"), null));
            elementStructureVO.setSuppressAccountingInd(Util.initString((String)rs.getString("SuppressAccountingInd"), null));

            convertAccountTable(elementStructureVO, countTable);

            elementVO.addElementStructureVO(elementStructureVO);

            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("EScountIn", countIn);
        countTable.put("EScountOut", countOut);
    }


    private void convertElementCompanyRelation(ElementVO elementVO, HashMap countTable)   throws Exception
    {
        int countIn = (Integer) countTable.get("ECRcountIn");
        int countOut = (Integer) countTable.get("ECRcountOut");

        ElementCompanyRelationVO elementCompanyVO = null;
        long elementPK = elementVO.getElementPK();

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM ElementCompanyRelation WHERE ElementFK = " + elementPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            elementCompanyVO = new ElementCompanyRelationVO();
            elementCompanyVO.setElementCompanyRelationPK(new Long(rs.getLong("ElementCompanyRelationPK")));
            elementCompanyVO.setProductStructureFK(new Long(rs.getLong("ProductStructureFK")));
            elementCompanyVO.setElementFK(elementPK);
            elementVO.addElementCompanyRelationVO(elementCompanyVO);

            countOut++;
        }

        countTable.put("ECRcountIn", countIn);
        countTable.put("ECRcountOut", countOut);

        s.close();
        rs.close();
    }

    private void convertAccountTable(ElementStructureVO elementStructureVO, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("ACcountIn");
        int countOut = (Integer)countTable.get("ACcountOut");

        AccountVO accountVO = null;
        long elementStructurePK = elementStructureVO.getElementStructurePK();
        Statement s = conn.createStatement();

        String sql = "SELECT * FROM Account WHERE ElementStructureFK = " + elementStructurePK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            accountVO = new AccountVO();
            accountVO.setAccountPK(new Long(rs.getLong("AccountPK")));
            accountVO.setElementStructureFK(new Long(rs.getLong("ElementStructureFK")));
            accountVO.setAccountNumber(Util.initString((String)rs.getString("AccountNumber"), null));
            accountVO.setAccountName(Util.initString((String)rs.getString("AccountName"), null));
            String effect = Util.initString((String)rs.getString("Effect"), null);
            if (effect.equalsIgnoreCase("D"))
            {
                accountVO.setEffect("Debit");
            }
            else
            {
                accountVO.setEffect("Credit");
            }

            accountVO.setAccountDescription(Util.initString((String)rs.getString("AccountDescription"), null));

            elementStructureVO.addAccountVO(accountVO);

            countOut++;
        }

        countTable.put("ACcountIn", countIn);
        countTable.put("ACcountOut", countOut);

        s.close();
        rs.close();
    }
}
