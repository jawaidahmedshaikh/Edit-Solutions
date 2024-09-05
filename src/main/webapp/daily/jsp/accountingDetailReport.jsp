 <!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="java.util.Map,
                 java.util.Iterator,
                 fission.beans.PageBean,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util,
                 edit.common.EDITBigDecimal"%>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] transCodeVOs = codeTableWrapper.getTRXCODE_CodeTableEntries();

    AccountingDetailVO[] accountingDetailVOs = (AccountingDetailVO[]) request.getAttribute("accountingDetailVOs");
    String fromDate = (String) request.getAttribute("fromDate");
    String toDate = (String) request.getAttribute("toDate");

%>

<html>
<head>
<title>Accounting Detail Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->
<script>
</script>
</head>

<!-- ************* HTML Code ************* -->
<body>

<h3 align="center">
    ACCOUNTING DETAIL REPORT
</h3>
<h5 align="right">
    From:&nbsp;<%= fromDate %>&nbsp;&nbsp;&nbsp;&nbsp;To:&nbsp;<%= toDate %>
</h5>

<hr size="3">

  <table align="left" cellpadding="2" cellspacing="4">

    <%
        boolean reinsuranceFound = false;
        String currentCompany = "";
        String currentAccount = "";
        String currentTransaction = "";
        String currentTrxDesc = "";
        String transactionCode = "";

        EDITBigDecimal companyDebits = new EDITBigDecimal();
        EDITBigDecimal companyCredits = new EDITBigDecimal();
        EDITBigDecimal accountDebits = new EDITBigDecimal();
        EDITBigDecimal accountCredits = new EDITBigDecimal();
        EDITBigDecimal transactionDebits = new EDITBigDecimal();
        EDITBigDecimal transactionCredits = new EDITBigDecimal();

        if (accountingDetailVOs != null)
        {
            for (int d = 0; d < accountingDetailVOs.length; d++)
            {
                String trxCodeCd = accountingDetailVOs[d].getTransactionCode();
                if (!trxCodeCd.equalsIgnoreCase("Reinsurance"))
                {
                    String companyName = accountingDetailVOs[d].getCompanyName();
                    String marketingPkgName = accountingDetailVOs[d].getMarketingPackageName();
                    String businessContractName = accountingDetailVOs[d].getBusinessContractName();
                    String qualNonQualInd = Util.initString(accountingDetailVOs[d].getQualNonQualCT(), "");
                    String qualifiedType = Util.initString(accountingDetailVOs[d].getQualifiedTypeCT(), "");
                    if (qualifiedType == null || qualifiedType.equalsIgnoreCase("Please Select"))
                    {
                        qualifiedType = "";
                    }

                    String optionCode = Util.initString(accountingDetailVOs[d].getOptionCodeCT(), "");
                    transactionCode = "";
                    for (int i = 0; i < transCodeVOs.length; i++)
                    {
                        String trxCode = transCodeVOs[i].getCode();
                        if (trxCodeCd.equalsIgnoreCase(trxCode))
                        {
                            transactionCode = transCodeVOs[i].getCodeDesc();
                            i = transCodeVOs.length;
                        }
                    }

                    if (transactionCode.equals(""))
                    {
                        transactionCode = trxCodeCd;
                    }

                    String reversalInd = Util.initString(accountingDetailVOs[d].getReversalInd(), "");
                    String memoCode = Util.initString(accountingDetailVOs[d].getMemoCode(), "");

                    String contractNumber = Util.initString(accountingDetailVOs[d].getContractNumber(), "");
                    String accountNumber = accountingDetailVOs[d].getAccountNumber();
                    String accountName = accountingDetailVOs[d].getAccountName();

                    //double amount = accountingDetailVOs[d].getAmount();
                    EDITBigDecimal amount = new EDITBigDecimal(accountingDetailVOs[d].getAmount());

                    String formattedAmt = Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(amount));
                    String debitCreditInd = accountingDetailVOs[d].getDebitCreditInd();
                    String fundNumber = Util.initString(accountingDetailVOs[d].getFundNumber(), "");
                    String outOfBalanceInd = Util.initString(accountingDetailVOs[d].getOutOfBalanceInd(), "");
                    if (outOfBalanceInd.equalsIgnoreCase("N"))
                    {
                        outOfBalanceInd = "";
                    }
                    String debitAmount = "";
                    String creditAmount = "";

                    if (currentCompany.equals(""))
                    {
                        currentCompany = companyName;
                        currentAccount = accountNumber;
                        currentTransaction = trxCodeCd;
                        currentTrxDesc = transactionCode;

                        if (debitCreditInd.equalsIgnoreCase("Debit"))
                        {
                            companyDebits       = companyDebits.addEditBigDecimal(amount);
                            accountDebits       = accountDebits.addEditBigDecimal(amount);
                            transactionDebits   = transactionDebits.addEditBigDecimal(amount);

                            debitAmount = formattedAmt;
                        }
                        else
                        {
                            companyCredits = companyCredits.addEditBigDecimal(amount);
                            accountCredits = accountCredits.addEditBigDecimal(amount);
                            transactionCredits = transactionCredits.addEditBigDecimal(amount);

                            creditAmount = formattedAmt;
                        }
    %>
      <tr>
        <td colspan="25">
            Company Name:&nbsp;&nbsp;&nbsp;<%= companyName %>
        </td>
      </tr>
      <tr>
        <th class="heading" width="18%">
           <br>
            Transaction
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Policy #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Fund
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            LOB
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Qual/<br>&nbsp;
            NQ
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Qual Type<br>
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Option
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Rev<br>
            Ind
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            &nbsp;&nbsp;Bus<br>
            Contract
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Memo<br>
            Code
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Debit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Credit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Out of<br>
            Balance
        </th>
      </tr>
      <tr>
        <td colspan="25">
           Account Number/Name:&nbsp;&nbsp;&nbsp;<%= accountNumber %>/<%= accountName %>
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
         <td class="data" width="18%">
            <%= transactionCode %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= contractNumber %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= fundNumber %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= marketingPkgName %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= qualNonQualInd %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= qualifiedType %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= optionCode %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= reversalInd %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= businessContractName %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= memoCode %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= debitAmount %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= creditAmount %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= outOfBalanceInd %>
         </td>
      </tr>
      <%
                    }

                    else {

                        if (!currentCompany.equals(companyName)) {

      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
       <tr>
         <td colspan="16">
         </td>
         <td colspan="3">
            Transaction Total
         </td>
         <td>
            <%= currentTrxDesc %>
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
         </td>
       </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
       <tr>
         <td colspan="17">
         </td>
         <td colspan="3">
            Account Total
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountDebits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountCredits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
         </td>
       </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Company Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(companyDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(companyCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <%
                            currentCompany = companyName;
                            currentAccount = accountNumber;
                            currentTransaction = trxCodeCd;
                            currentTrxDesc = transactionCode;

                            companyDebits   = new EDITBigDecimal();
                            companyCredits  = new EDITBigDecimal();
                            accountDebits   = new EDITBigDecimal();
                            accountCredits  = new EDITBigDecimal();
                            transactionDebits   = new EDITBigDecimal();
                            transactionCredits  = new EDITBigDecimal();

                            if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                companyDebits = companyDebits.addEditBigDecimal(amount);
                                accountDebits = accountDebits.addEditBigDecimal(amount);
                                transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                debitAmount = formattedAmt;
                            }

                            else {

                                companyCredits = companyCredits.addEditBigDecimal(amount);
                                accountCredits = accountCredits.addEditBigDecimal(amount);
                                transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                creditAmount = formattedAmt;
                            }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
          Company Name:&nbsp;&nbsp;&nbsp;<%= companyName %>
        </td>
      </tr>
      <tr>
        <th class="heading" width="18%">
          <br>
          Transaction
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Policy #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Fund
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          LOB
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Qual/<br>&nbsp;
          NQ
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Qual Type<br>
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Option
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Rev<br>
          Ind
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          &nbsp;&nbsp;Bus<br>
          Contract
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Memo<br>
          Code
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Debit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Credit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Out of<br>
          Balance
        </th>
      </tr>
      <tr>
        <td colspan="25">
          Account Number/Name:&nbsp;&nbsp;&nbsp;<%= accountNumber %>/<%= accountName %>
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td class="data" width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= fundNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= qualNonQualInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= qualifiedType %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                        }

                        else if (!currentAccount.equals(accountNumber)) {

      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="16">
        </td>
        <td colspan="3">
          Transaction Total
        </td>
        <td>
          <%= currentTrxDesc %>
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Account Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <%
                            currentAccount = accountNumber;
                            currentTransaction = trxCodeCd;
                            currentTrxDesc = transactionCode;

                            accountDebits   = new EDITBigDecimal();
                            accountCredits  = new EDITBigDecimal();
                            transactionDebits   = new EDITBigDecimal();
                            transactionCredits  = new EDITBigDecimal();

                            if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                companyDebits = companyDebits.addEditBigDecimal(amount);
                                accountDebits = accountDebits.addEditBigDecimal(amount);
                                transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                debitAmount = formattedAmt;
                            }

                            else {

                                companyCredits = companyCredits.addEditBigDecimal(amount);
                                accountCredits = accountCredits.addEditBigDecimal(amount);
                                transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                creditAmount = formattedAmt;
                            }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
          Account Number/Name:&nbsp;&nbsp;&nbsp;<%= accountNumber %>/<%= accountName %>
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td class="data" width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= fundNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= qualNonQualInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= qualifiedType %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                        }

                        else if (!currentTransaction.equals(trxCodeCd)) {

      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="16">
        </td>
        <td colspan="3">
          Transaction Total
        </td>
        <td>
          <%= currentTrxDesc %>
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <%

                            currentTransaction = trxCodeCd;
                            currentTrxDesc = transactionCode;
                            transactionDebits = new EDITBigDecimal();
                            transactionCredits = new EDITBigDecimal();

                            if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                companyDebits = companyDebits.addEditBigDecimal(amount);
                                accountDebits = accountDebits.addEditBigDecimal(amount);
                                transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                debitAmount = formattedAmt;
                            }

                            else {

                                companyCredits = companyCredits.addEditBigDecimal(amount);
                                accountCredits = accountCredits.addEditBigDecimal(amount);
                                transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                creditAmount = formattedAmt;
                            }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td class="data" width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= fundNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= qualNonQualInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= qualifiedType %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                        }

                        else {

                            if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                companyDebits = companyDebits.addEditBigDecimal(amount);
                                accountDebits = accountDebits.addEditBigDecimal(amount);
                                transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                debitAmount = formattedAmt;
                            }

                            else {

                                companyCredits = companyCredits.addEditBigDecimal(amount);
                                accountCredits = accountCredits.addEditBigDecimal(amount);
                                transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                creditAmount = formattedAmt;
                            }
      %>
      <tr>
        <td width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= fundNumber %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= qualNonQualInd %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= qualifiedType %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                        }
                    }
                }
                else
                {
                    reinsuranceFound = true;
                }
            }
        }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="16">
        </td>
        <td colspan="3">
          Transaction Total
        </td>
        <td>
          <%= currentTrxDesc %>
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Account Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <%
        if (reinsuranceFound)
        {
            currentCompany = "";
            accountDebits = new EDITBigDecimal();
            accountCredits = new EDITBigDecimal();
            transactionDebits = new EDITBigDecimal();
            transactionCredits = new EDITBigDecimal();

            if (accountingDetailVOs != null)
            {
                for (int d = 0; d < accountingDetailVOs.length; d++)
                {
                    String trxCodeCd = accountingDetailVOs[d].getTransactionCode();
                    if (trxCodeCd.equalsIgnoreCase("Reinsurance"))
                    {
                        String companyName = accountingDetailVOs[d].getCompanyName();
                        String marketingPkgName = accountingDetailVOs[d].getMarketingPackageName();
                        String businessContractName = accountingDetailVOs[d].getBusinessContractName();
                        String reinsurerNumber = Util.initString(accountingDetailVOs[d].getReinsurerNumber(), "");
                        String treatyGroupNumber = Util.initString(accountingDetailVOs[d].getTreatyGroupNumber(), "");

                        String optionCode = Util.initString(accountingDetailVOs[d].getOptionCodeCT(), "");
                        transactionCode = "";
                        for (int i = 0; i < transCodeVOs.length; i++)
                        {
                            String trxCode = transCodeVOs[i].getCode();
                            if (trxCodeCd.equalsIgnoreCase(trxCode))
                            {
                                transactionCode = transCodeVOs[i].getCodeDesc();
                                i = transCodeVOs.length;
                            }
                        }

                        if (transactionCode.equals(""))
                        {
                            transactionCode = trxCodeCd;
                        }

                        String reversalInd = Util.initString(accountingDetailVOs[d].getReversalInd(), "");
                        String memoCode = Util.initString(accountingDetailVOs[d].getMemoCode(), "");

                        String contractNumber = accountingDetailVOs[d].getContractNumber();
                        String accountNumber = accountingDetailVOs[d].getAccountNumber();
                        String accountName = accountingDetailVOs[d].getAccountName();

                        EDITBigDecimal amount = new EDITBigDecimal(accountingDetailVOs[d].getAmount());

                        String formattedAmt = Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(amount));
                        String debitCreditInd = accountingDetailVOs[d].getDebitCreditInd();
                        String outOfBalanceInd = Util.initString(accountingDetailVOs[d].getOutOfBalanceInd(), "");
                        if (outOfBalanceInd.equalsIgnoreCase("N"))
                        {
                            outOfBalanceInd = "";
                        }
                        String debitAmount = "";
                        String creditAmount = "";

                        if (currentCompany.equals(""))
                        {
                            currentCompany = companyName;
                            currentAccount = accountNumber;
                            currentTransaction = trxCodeCd;
                            currentTrxDesc = transactionCode;

                            if (debitCreditInd.equalsIgnoreCase("Debit"))
                            {
                                companyDebits       = companyDebits.addEditBigDecimal(amount);
                                accountDebits       = accountDebits.addEditBigDecimal(amount);
                                transactionDebits   = transactionDebits.addEditBigDecimal(amount);

                                debitAmount = formattedAmt;
                            }
                            else
                            {
                                companyCredits = companyCredits.addEditBigDecimal(amount);
                                accountCredits = accountCredits.addEditBigDecimal(amount);
                                transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                creditAmount = formattedAmt;
                            }
    %>
      <tr>
        <td colspan="25">
            Company Name:&nbsp;&nbsp;&nbsp;<%= companyName %>
        </td>
      </tr>
      <tr>
        <th class="heading" width="18%">
           <br>
            Transaction
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Policy #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Reinsurer #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Treaty<br>
            Group #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            LOB
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            &nbsp;
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Option
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Rev<br>
            Ind
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            &nbsp;&nbsp;Bus<br>
            Contract
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Memo<br>
            Code
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Debit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            <br>
            Credit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
            Out of<br>
            Balance
        </th>
      </tr>
      <tr>
        <td colspan="25">
           Account Number/Name:&nbsp;&nbsp;&nbsp;<%= accountNumber %>/<%= accountName %>
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
         <td class="data" width="18%">
            <%= transactionCode %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= contractNumber %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= reinsurerNumber %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= treatyGroupNumber %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= marketingPkgName %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            &nbsp;
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= optionCode %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= reversalInd %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= businessContractName %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= memoCode %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= debitAmount %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= creditAmount %>
         </td>
         <td width="2%">
         </td>
         <td class="data" width="18%">
            <%= outOfBalanceInd %>
         </td>
      </tr>
      <%
                        }

                        else {

                            if (!currentCompany.equals(companyName)) {
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
       <tr>
         <td colspan="16">
         </td>
         <td colspan="3">
            Transaction Total
         </td>
         <td>
            <%= currentTrxDesc %>
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
         </td>
       </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
       <tr>
         <td colspan="17">
         </td>
         <td colspan="3">
            Account Total
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountDebits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
            <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountCredits)) %>
         </td>
         <td width="2%">
         </td>
         <td width="18%">
         </td>
       </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Company Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(companyDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(companyCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <%
                                currentCompany = companyName;
                                currentAccount = accountNumber;
                                currentTransaction = trxCodeCd;
                                currentTrxDesc = transactionCode;

                                companyDebits   = new EDITBigDecimal();
                                companyCredits  = new EDITBigDecimal();
                                accountDebits   = new EDITBigDecimal();
                                accountCredits  = new EDITBigDecimal();
                                transactionDebits   = new EDITBigDecimal();
                                transactionCredits  = new EDITBigDecimal();

                                if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                    companyDebits = companyDebits.addEditBigDecimal(amount);
                                    accountDebits = accountDebits.addEditBigDecimal(amount);
                                    transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                    debitAmount = formattedAmt;
                                }

                                else {

                                    companyCredits = companyCredits.addEditBigDecimal(amount);
                                    accountCredits = accountCredits.addEditBigDecimal(amount);
                                    transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                    creditAmount = formattedAmt;
                                }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
          Company Name:&nbsp;&nbsp;&nbsp;<%= companyName %>
        </td>
      </tr>
      <tr>
        <th class="heading" width="18%">
          <br>
          Transaction
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Policy #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Reinsurer #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Treaty<br>
          &nbsp;Group #
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          LOB
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          &nbsp;
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Option
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Rev<br>
          Ind
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          &nbsp;&nbsp;Bus<br>
          Contract
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Memo<br>
          Code
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Debit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          <br>
          Credit
        </th>
        <th width="2%">
        </th>
        <th class="heading" width="18%">
          Out of<br>
          Balance
        </th>
      </tr>
      <tr>
        <td colspan="25">
          Account Number/Name:&nbsp;&nbsp;&nbsp;<%= accountNumber %>/<%= accountName %>
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td class="data" width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reinsurerNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= treatyGroupNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          &nbsp;
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                            }

                            else if (!currentAccount.equals(accountNumber)) {
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="16">
        </td>
        <td colspan="3">
          Transaction Total
        </td>
        <td>
          <%= currentTrxDesc %>
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Account Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <%
                                currentAccount = accountNumber;
                                currentTransaction = trxCodeCd;
                                currentTrxDesc = transactionCode;

                                accountDebits   = new EDITBigDecimal();
                                accountCredits  = new EDITBigDecimal();
                                transactionDebits   = new EDITBigDecimal();
                                transactionCredits  = new EDITBigDecimal();

                                if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                    companyDebits = companyDebits.addEditBigDecimal(amount);
                                    accountDebits = accountDebits.addEditBigDecimal(amount);
                                    transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                    debitAmount = formattedAmt;
                                }

                                else {

                                    companyCredits = companyCredits.addEditBigDecimal(amount);
                                    accountCredits = accountCredits.addEditBigDecimal(amount);
                                    transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                    creditAmount = formattedAmt;
                                }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
          Account Number/Name:&nbsp;&nbsp;&nbsp;<%= accountNumber %>/<%= accountName %>
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td class="data" width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reinsurerNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= treatyGroupNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          &nbsp;
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                            }

                            else if (!currentTransaction.equals(trxCodeCd)) {
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="16">
        </td>
        <td colspan="3">
          Transaction Total
        </td>
        <td>
          <%= currentTrxDesc %>
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <%
                                currentTransaction = trxCodeCd;
                                currentTrxDesc = transactionCode;
                                transactionDebits = new EDITBigDecimal();
                                transactionCredits = new EDITBigDecimal();

                                if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                    companyDebits = companyDebits.addEditBigDecimal(amount);
                                    accountDebits = accountDebits.addEditBigDecimal(amount);
                                    transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                    debitAmount = formattedAmt;
                                }

                                else {

                                    companyCredits = companyCredits.addEditBigDecimal(amount);
                                    accountCredits = accountCredits.addEditBigDecimal(amount);
                                    transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                    creditAmount = formattedAmt;
                                }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td class="data" width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reinsurerNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= treatyGroupNumber %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          &nbsp;
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td class="data" width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                            }

                            else {

                                if (debitCreditInd.equalsIgnoreCase("Debit")) {

                                    companyDebits = companyDebits.addEditBigDecimal(amount);
                                    accountDebits = accountDebits.addEditBigDecimal(amount);
                                    transactionDebits = transactionDebits.addEditBigDecimal(amount);

                                    debitAmount = formattedAmt;
                                }

                                else {

                                    companyCredits = companyCredits.addEditBigDecimal(amount);
                                    accountCredits = accountCredits.addEditBigDecimal(amount);
                                    transactionCredits = transactionCredits.addEditBigDecimal(amount);

                                    creditAmount = formattedAmt;
                                }
      %>
      <tr>
        <td width="18%">
          <%= transactionCode %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= contractNumber %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= reinsurerNumber %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= treatyGroupNumber %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= marketingPkgName %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          &nbsp;
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= optionCode %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= reversalInd %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= businessContractName %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= memoCode %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= debitAmount %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= creditAmount %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= outOfBalanceInd %>
        </td>
      </tr>
      <%
                            }
                        }
                    }
                }
            }
      %>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="16">
        </td>
        <td colspan="3">
          Transaction Total
        </td>
        <td>
          <%= currentTrxDesc %>
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(transactionCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Account Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(accountCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
      <tr>
        <td colspan="25">
        </td>
      </tr>
    </div>
      <%
        }
      %>
      <tr>
        <td colspan="17">
        </td>
        <td colspan="3">
          Company Total
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(companyDebits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
          <%= Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(companyCredits)) %>
        </td>
        <td width="2%">
        </td>
        <td width="18%">
        </td>
      </tr>
    </table>
<br>

</body>
</html>
