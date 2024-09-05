<%@ page import="contract.report.TransferMemorandum,
                 java.util.Iterator,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDate,
                 java.text.SimpleDateFormat,
                 java.util.Date,
                 java.text.NumberFormat,
                 java.text.FieldPosition"%>
<!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
    TransferMemorandum transferMemorandum = (TransferMemorandum) request.getAttribute("transferMemorandum");
%>

<%!
    String formatDate(EDITDate date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
            
        return sdf.format(new Date(date.getTimeInMilliseconds()));
    }

    String formatAsCurrency(EDITBigDecimal amount)
    {
        StringBuffer amountAsCurrency = new StringBuffer();

        NumberFormat.getCurrencyInstance().format(amount.getBigDecimal(), amountAsCurrency, new FieldPosition(NumberFormat.Field.CURRENCY));

        return amountAsCurrency.toString();
    }
%>

<html>
<head>
<title>Transfer Memorandum</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

</head>

<!-- ************* HTML Code ************* -->
<body>

  <h4 align="center">
    TRANSFER MEMORANDUM
  </h4>
  <table align="left" width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td align="left" colspan="4">DATE:&nbsp;
      <%= formatDate(transferMemorandum.getReportDate()) %>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">FROM:&nbsp;<%= transferMemorandum.getFromName()%></td>
    </tr>
    <tr>
      <td colspan="4">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">TO:&nbsp;<%= transferMemorandum.getToName()%></td>
    </tr>
    <tr>
      <td colspan="4">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" width="200"><nobr>RE: SEPARATE ACCOUNT - Series VII<nobr></td>
      <td align="right" colspan="3">
        <table align="left" width="300" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td valign="top">
              From:
      </td>
            <td>
              <table align="left" width="100%" cellpadding="0" cellspacing="0" border="0">
  <%
                    Iterator iterator1 = transferMemorandum.getFunds().values().iterator();

                    while (iterator1.hasNext())
                      {
                        TransferMemorandum.FundDetail fundDetail = (TransferMemorandum.FundDetail) iterator1.next();

                        EDITBigDecimal fundAmount = fundDetail.getAmount();

                        if (fundAmount.isLT(new EDITBigDecimal("0")))
                          {
  %>
    <tr>
                    <td>
                      <%= fundDetail.getClientFundNumber() %>
                    </td>
                    <td>
                      <%= fundDetail.getFundName() %>
                    </td>
    </tr>
  <%
                          }
                    }
  %>
              </table>
            </td>
          </tr>
    <tr>
            <td colspan="2">&nbsp;</td>
    </tr>
          <tr>
            <td valign="top">
              To:
            </td>
            <td>
              <table align="left" width="100%" cellpadding="0" cellspacing="0" border="0">
  <%
                    Iterator iterator2 = transferMemorandum.getFunds().values().iterator();
                          
                    while (iterator2.hasNext())
                          {
                        TransferMemorandum.FundDetail fundDetail = (TransferMemorandum.FundDetail) iterator2.next();

                        EDITBigDecimal fundAmount = fundDetail.getAmount();
                      
                        if (fundAmount.isGT(new EDITBigDecimal("0")))
                      {
  %>
    <tr>
                    <td>
                      <%= fundDetail.getClientFundNumber() %>
                    </td>
                    <td>
                      <%= fundDetail.getFundName() %>
                    </td>
    </tr>
  <%
                      }
                    }
  %>
              </table>
            </td>
    </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
        Please transfer the following amounts <b>from</b> and <b>(to)</b> the Separate Account/Division noted above.
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
        These Amounts are based on values as of&nbsp;<%= formatDate(transferMemorandum.getInputDate()) %>.
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
        Shares should be transferred based on NAVs as of&nbsp;<%= formatDate(transferMemorandum.getInputDate()) %>.
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Amounts to be transferred:
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
        <table align="left" width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="50">&nbsp;</td>
            <td valign="top">
              From:
            </td>
            <td>
              <table align="left" width="200" cellpadding="0" cellspacing="0" border="0">
  <%
                    Iterator iterator3 = transferMemorandum.getFunds().values().iterator();

                    while (iterator3.hasNext())
              {
                        TransferMemorandum.FundDetail fundDetail = (TransferMemorandum.FundDetail) iterator3.next();

                        EDITBigDecimal fundAmount = fundDetail.getAmount();

                        if (fundAmount.isLT(new EDITBigDecimal("0")))
                        {
  %>
    <tr>
                    <td>
                      <%= fundDetail.getClientFundNumber() %>
                    </td>
                    <td align="right">
                      <%= formatAsCurrency(fundDetail.getAmount().multiplyEditBigDecimal("-1")) %>
                    </td>
    </tr>
  <%
              }
          }
  %>
              </table>
            </td>
    </tr>
    <tr>
            <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
            <td width="50">&nbsp;</td>
            <td valign="top">
              To:
            </td>
            <td>
              <table align="left" width="200" cellpadding="0" cellspacing="0" border="0">
  <%
                    Iterator iterator4 = transferMemorandum.getFunds().values().iterator();

                    while (iterator4.hasNext())
                    {
                        TransferMemorandum.FundDetail fundDetail = (TransferMemorandum.FundDetail) iterator4.next();

                        EDITBigDecimal fundAmount = fundDetail.getAmount();

                        if (fundAmount.isGT(new EDITBigDecimal("0")))
          {
  %>
    <tr>
                    <td>
                        <%= fundDetail.getClientFundNumber() %>
                    </td>
                    <td align="right">
                      <%= "(" + formatAsCurrency(fundDetail.getAmount()) + ")"%>
                    </td>
    </tr>
  <%
          }
      }
  %>
              </table>
            </td>
          </tr>
    <tr>
            <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
            <td width="50">&nbsp;</td>
            <td valign="top">
              Net:
            </td>
            <td>
              <table align="left" width="200" cellpadding="0" cellspacing="0" border="0">
    <tr>
                  <td>
                    &nbsp;
                  </td>
                  <td align="right">
                    <%= formatAsCurrency(transferMemorandum.getNetAmount()) %>
                  </td>
    </tr>
              </table>
            </td>
    </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="4">
          <b>No Cash is to Be Moved</b>
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="4">
          &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="4">
          Sent by _______________________________________________
      </td>
    </tr>
  </table>
</body>
</html>
