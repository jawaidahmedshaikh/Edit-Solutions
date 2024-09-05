<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 edit.common.*,
                 fission.utility.*"%>
<%
    CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) request.getAttribute("commissionHistoryVOs");
%>
<html>

<head>
<title>Commission History</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	function init() {

	}

</script>
</head>
<body  class="mainTheme" onLoad="init()">

    <span class="tableHeading">Commission History</span><br>

    <table class="summary" width="100%" height="80%" border="0" cellspacing="0" cellpadding="0">

        <tr class="heading">

            <th>
                Contract No
            </th>
            <th>
                Trx Type
            </th>
            <th>
                Prcss Date
            </th>
            <th>
                Expense Amt
            </th>
            <th>
                ADA Amt
            </th>
            <th>
                Comm Amt
            </th>
             <th>
                Deb Bal Payoff
            </th>
            <th>
                Comm Type
            </th>
            <th>
                Release Date
            </th>
        </tr>

        <tr>

            <td colspan="9" height="99%">

                <span class="scrollableContent">

                    <table class="summary" width="100%" height="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0" >
<%
                        if (commissionHistoryVOs != null){

                            for (int i = 0; i < commissionHistoryVOs.length; i++){

                                CommissionHistoryVO commissionHistoryVO = commissionHistoryVOs[i];
                                EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class);
                                EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);

                                String trxType = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());

                                if (!trxType.equalsIgnoreCase("check"))
                                {
                                    ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                                    ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                                    SegmentVO segmentVO = null;
                                    if (contractSetupVO.getParentVOs() != null)
                                    {
                                        segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                                    }
                                    String contractNumber = "";
                                    if (segmentVO != null)
                                    {
                                        contractNumber = segmentVO.getContractNumber();
                                    }
    
                                    EDITDate editProcessDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();
                                    String processDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(editProcessDate);
    
                                    String expenseAmount = commissionHistoryVO.getExpenseAmount().toString();
                                    String adaAmount = commissionHistoryVO.getADAAmount().toString();
                                    String commissionAmount = commissionHistoryVO.getCommissionAmount().toString();
                                    String commissionType = commissionHistoryVO.getCommissionTypeCT();
                                    String debitBalanceAmount = commissionHistoryVO.getDebitBalanceAmount().toString();
                                    String releaseDate = commissionHistoryVO.getCommHoldReleaseDate();
                                    String commHoldReleaseDate = null;
                                    if (releaseDate != null)
                                    {
                                        commHoldReleaseDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(releaseDate));
                                    }
    %>
                                    <tr class="default">
    
                                        <td width="10%">
                                            <%= contractNumber %>
                                        </td>
                                        <td width="10%">
                                            <%= trxType %>
                                        </td>
                                        <td width="10%">
                                            <%= processDate %>
                                        </td>
                                        <td width="10%">
                                            <script>document.write(formatAsCurrency(<%= expenseAmount %>))</script>
                                        </td>
                                        <td width="10%">
                                            <script>document.write(formatAsCurrency(<%= adaAmount %>))</script>
                                        </td>
                                        <td width="10%">
                                            <script>document.write(formatAsCurrency(<%= commissionAmount %>))</script>
                                        </td>
                                        <td width="10%">
                                            <script>document.write(formatAsCurrency(<%= debitBalanceAmount %>))</script>
                                        </td>
                                        <td width="10%">
                                            <%= commissionType %>
                                        </td>
                                        <td width="10%">
                                            <%= Util.initString(commHoldReleaseDate, "") %>
                                        </td>
                                    </tr>
<%
                                }
                            }
                        }
%>
                    <tr class="filler">
                        <td colspan="8">
                            &nbsp;
                        </td>
                    </tr>
                    </table>

                </span>

            </td>
        </tr>

    </table>

<table width="100%">
    <tr>
         <td align="right" valign="bottom">
            <input type="button" name="clone" value="Close" onClick="closeWindow()">
        </td>
    </tr>
</table>

</body>
</html>
