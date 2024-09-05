<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*"%>
<%
    FilteredFundVO[] filteredFundVOs = (FilteredFundVO[]) request.getAttribute("filteredFundVOs");

    FilteredAreaVO filteredAreaVO = (FilteredAreaVO) request.getAttribute("filteredAreaVO");

    long filteredAreaPK = filteredAreaVO.getFilteredAreaPK();
    long filteredFundPK = filteredAreaVO.getFilteredFundFK();
    long renewalFundToPK = filteredAreaVO.getRenewalFundToFK();

    AreaVO activeAreaVO = (AreaVO) filteredAreaVO.getParentVO(AreaVO.class);

    long areaPK = activeAreaVO.getAreaPK();

    int payoutLeadDaysCheck = (activeAreaVO != null)?activeAreaVO.getPayoutLeadDaysCheck():0;
    int payoutLeadDaysEFT = (activeAreaVO != null)?activeAreaVO.getPayoutLeadDaysEFT():0;
    int lookBackDays = (activeAreaVO != null)?activeAreaVO.getLookBackDays():0;

    int freeLookDaysNB = (activeAreaVO != null)?activeAreaVO.getFreeLookDaysNB():0;
    int freeLookDaysInternal = (activeAreaVO != null)?activeAreaVO.getFreeLookDaysInternal():0;
    int freeLookDaysExternal = (activeAreaVO != null)?activeAreaVO.getFreeLookDaysExternal():0;

    int freeLookAgeNB = (activeAreaVO != null)?activeAreaVO.getFreeLookAgeNB():0;
    int freeLookAgeInternal = (activeAreaVO != null)?activeAreaVO.getFreeLookAgeInternal():0;
    int freeLookAgeExternal = (activeAreaVO != null)?activeAreaVO.getFreeLookAgeExternal():0;

    int freeLookAgeBasedNB = (activeAreaVO != null)?activeAreaVO.getFreeLookAgeBasedNB():0;
    int freeLookAgeBasedInternal = (activeAreaVO != null)?activeAreaVO.getFreeLookAgeBasedInternal():0;
    int freeLookAgeBasedExternal = (activeAreaVO != null)?activeAreaVO.getFreeLookAgeBasedExternal():0;

    int licenseSolicitGuideline = (activeAreaVO != null)?activeAreaVO.getLicenseSolicitGuideline():0;

//    double totalPremPercentAllowed = (activeAreaVO != null)?activeAreaVO.getTotalPremPercentAllowed():0;
     String totalPremPercentAllowed = (activeAreaVO != null)?Util.initString(activeAreaVO.getTotalPremPercentAllowed() + "", "0"):"0";

    String activeStatementModeCT = (activeAreaVO != null)?activeAreaVO.getStatementModeCT():null;
    String activeFreeLookTypeCT = (activeAreaVO != null)?activeAreaVO.getFreeLookTypeCT():null;
    String activeAreaCT = (activeAreaVO != null)?activeAreaVO.getAreaCT():null;

    CodeTableVO[] statementModeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STATEMENTMODE");
    CodeTableVO[] freeLookTypeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("FREELOOKTYPE");

    String message = (String) request.getAttribute("message");

%>

<html>
<head>

<script language="Javascript1.2">

    var f = null;

    var message = "<%= message %>";

    function init()
    {
    	f = document.theForm;

        alertMessage();
    }

    function alertMessage()
    {
        if (message != "null")
        {
            alert(message);
        }
    }

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value = transaction;
        f.action.value = action;
        f.target = target;
        f.submit();
    }

    function saveFilteredArea()
    {
        sendTransactionAction("TableTran", "saveFilteredArea", "_self");
    }

    function cancelFilteredAreaEdits()
    {
        sendTransactionAction("TableTran", "cancelFilteredAreaEdits", "_self");
    }

    function closeDialog()
    {
        window.close();
    }



</script>

<title>Filtered Area</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>


<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table width="100%" border="0" cellspacing="0" cellpadding="0">

    <tr>
        <td align="center" width="33%">
            <span class="tableHeading">Filtered Area Info</span>
        </td>
    </tr>

</table>

<table class="formData" border="0" width="100%" height="80%">

    <tr>
        <td colspan="2" align="right">

<%
                String areaDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("STATE", activeAreaCT);
%>

            Area <input type="text" size="15" DISABLED value="<%= areaDesc %>">
        </td>
        <td colspan="2" align="right">
            Statement Mode
            <select name="statementModeCT">
            <option name="id" value="-1">Please Select</option>
<%
                if (statementModeCTs != null){

                    for (int i = 0; i < statementModeCTs.length; i++){

                        String code = statementModeCTs[i].getCode();
                        String codeDesc = statementModeCTs[i].getCodeDesc();

                        if ( (activeStatementModeCT != null) && (activeStatementModeCT.equals(code)))
                        {
                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }
                }
%>
            </select>

        </td>
        <td colspan="2" align="right">
            Free Look Type

            <select name="freeLookTypeCT">
            <option name="id" value="-1">Please Select</option>
<%
                if (freeLookTypeCTs != null){

                    for (int i = 0; i < freeLookTypeCTs.length; i++){

                        String code = freeLookTypeCTs[i].getCode();
                        String codeDesc = freeLookTypeCTs[i].getCodeDesc() + "";

                        if ( (activeFreeLookTypeCT != null) && (activeFreeLookTypeCT.equals(code)))
                        {
                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }
                }
%>
            </select>

        </td>
    </tr>
    <tr>
        <td align="right" colspan="3">
            Free Look Fund
            <select name="filteredFundPK">
            <option name="id" value="0">Please Select</option>
<%
                if (filteredFundVOs != null){

                    for (int i = 0; i < filteredFundVOs.length; i++){

                        long currentFilteredFundPK = filteredFundVOs[i].getFilteredFundPK();

                        FundVO currentFundVO = (FundVO) filteredFundVOs[i].getParentVO(FundVO.class);

                        String fundName = currentFundVO.getName();

                        if (currentFilteredFundPK == filteredFundPK )
                        {
                            out.println("<option selected name=\"id\" value=\"" + currentFilteredFundPK + "\">" + fundName + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + currentFilteredFundPK + "\">" + fundName + "</option>");
                        }
                    }
                }
%>
            </select>
        </td>
        <td align="right" colspan="3">
            Renewal Fund To
            <select name="renewalFundToPK">
            <option name="id" value="0">Please Select</option>
<%
                if (filteredFundVOs != null){

                    for (int i = 0; i < filteredFundVOs.length; i++){

                        long currentFilteredFundPK = filteredFundVOs[i].getFilteredFundPK();

                        FundVO currentFundVO = (FundVO) filteredFundVOs[i].getParentVO(FundVO.class);

                        String fundName = currentFundVO.getName();

                        if (currentFilteredFundPK == renewalFundToPK )
                        {
                            out.println("<option selected name=\"id\" value=\"" + currentFilteredFundPK + "\">" + fundName + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + currentFilteredFundPK + "\">" + fundName + "</option>");
                        }
                    }
                }
%>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right">
            Payout Lead Days Check
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="payoutLeadDaysCheck" value="<%= payoutLeadDaysCheck %>">
        </td>
        <td align="right">
            Payout Lead Days EFT
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="payoutLeadDaysEFT" value="<%= payoutLeadDaysEFT %>">
        </td>
        <td align="right">
            Look Back Days
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="lookBackDays" value="<%= lookBackDays %>">
        </td>
    </tr>
    <tr>
        <td align="right">
            Free Look Days NB
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookDaysNB" value="<%= freeLookDaysNB %>">
        </td>
        <td align="right">
            Free Look Age NB
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookAgeNB" value="<%= freeLookAgeNB %>">
        </td>
        <td align="right">
            Free Look Age Based NB
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookAgeBasedNB" value="<%= freeLookAgeBasedNB %>">
        </td>
    </tr>
    <tr>
        <td align="right">
            Free Look Days Internal
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookDaysInternal" value="<%= freeLookDaysInternal %>">
        </td>
        <td align="right">
            Free Look Age Internal
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookAgeInternal" value="<%= freeLookAgeInternal %>">
        </td>
        <td align="right">
            Free Look Age Based Internal
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookAgeBasedInternal" value="<%= freeLookAgeBasedInternal %>">
        </td>
    </tr>
    <tr>
        <td align="right">
            Free Look Days External
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookDaysExternal" value="<%= freeLookDaysExternal %>">
        </td>

        <td align="right">
            Free Look Age External
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookAgeExternal" value="<%= freeLookAgeExternal %>">
        </td>
        <td align="right">
            Free Look Age Based External
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="freeLookAgeBasedExternal" value="<%= freeLookAgeBasedExternal %>">
        </td>
    </tr>
    <tr>
        <td align="right">&nbsp;</td>
        <td align="left">&nbsp;</td>
        <td align="right">
            License Solicit Guideline
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="licenseSolicitGuideline" value="<%= licenseSolicitGuideline %>">
        </td>
        <td align="right">
            Total Premium Percent Allowed
        </td>
        <td align="left">
            <input type="text" size="3" maxlength="3" name="totalPremPercentAllowed" value="<%= totalPremPercentAllowed %>">
        </td>        <td align="right">&nbsp;</td>
        <td align="left">&nbsp;</td>
    </tr>
</table>

<table width="100%">

    <tr>
        <td align="right" valign="bottom">
            <input type="button" name="btnSave" value="Save" onClick="saveFilteredArea()">
            <input type="button" name="btnCancel" value="Cancel" onClick="cancelFilteredAreaEdits()">
            <input type="button" name="btnClose" value="Close" onClick="closeDialog()">
        </td>
    </tr>
</table>

<br>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">
<input type="hidden" name="filteredAreaPK" value="<%= filteredAreaPK %>">
<input type="hidden" name="activeAreaPK" value="<%= areaPK %>">
<input type="hidden" name="areaCT" value="<%= activeAreaCT %>">

</form>

</body>
</html>
