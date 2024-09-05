<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                agent.*,
                edit.common.EDITDate"%>
<!-- ****** JAVA CODE ***** //-->

<%    
    CommissionProfile[] commissionProfiles = CommissionProfile.find_All();

    long activePlacedAgentPK2 = Long.parseLong(Util.initString((String) request.getParameter("activePlacedAgentPK2"), null));

    PlacedAgent placedAgent = PlacedAgent.findByPK(new Long(activePlacedAgentPK2));
    
    String startDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(placedAgent.getActivePlacedAgentCommissionProfile().getStartDate());
    
    String stopDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(placedAgent.getActivePlacedAgentCommissionProfile().getStopDate());
    
    long activeCommissionProfilePK = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile().getCommissionProfilePK().longValue();
    
    String activeContractCode = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile().getContractCodeCT();
    
    String pageMode = (String) request.getAttribute("pageMode");
    
    String pageMessage = Util.initString((String) request.getAttribute("pageMessage"), "");    
%>
<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>

<script language="Javascript1.2">

    var f = null;

    var pageMessage = "<%= pageMessage %>";
    
    function init() 
    {
        f = document.theForm;
        
        if (!valueIsEmpty(pageMessage))
        {
            alert(pageMessage);
        }
    }

    function associateCommissionProfile() 
    {
        if (f.activeCommissionProfilePK.value == "") 
        {
            alert("Please Select A Commission Level");
        }
        else 
        {
            if (validateForm(f))
            {
                sendTransactionAction("AgentDetailTran", "associateCommissionProfile", "_self");
            }
        }
     }

    function selectDeselectRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var containingTable = currentRow.parentElement;

        for (var i = 0; i < containingTable.rows.length; i++)
        {
            var trRow = containingTable.rows[i];

            var className = trRow.className;

            if (className == "associated")
            {
                trRow.style.backgroundColor = "#00BB00";
            }
            else
            {
                trRow.style.backgroundColor = "#BBBBBB";
            }

            trRow.isSelected = "false";
        }

        currentRow.style.backgroundColor = "#FFFFCC";

        currentRow.isSelected = "true";
    }

    function setCommissionProfile()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var activeCommissionProfilePK = currentRow.commissionProfilePK;

        f.activeCommissionProfilePK.value = activeCommissionProfilePK;
    }

    function closeCommissionLevelSummary()
    {
        sendTransactionAction("AgentDetailTran", "closeCommissionLevelSummary", "contentIFrame");
        closeWindow();
    }

</script>

<title>Commission Level Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<!-- Summary Table for CommissionProfiles  -->
    <table class="summary" width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
        <tr class="heading">
            <th width="50%">
                Commission Level
            </th>
            <th width="50%">
                Commission Option
            </th>
        </tr>
        <tr>
            <td height="99%" colspan="2">
                <span class="scrollableContent">
                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">


<%
        if (commissionProfiles != null)
        {
            for (int i = 0; i < commissionProfiles.length; i++)
            {
                CommissionProfile currentCommissionProfile = commissionProfiles[i];
                
                long commissionProfilePK = currentCommissionProfile.getCommissionProfilePK().longValue();
                
                String commissionLevelCT = currentCommissionProfile.getCommissionLevelCT();
                
                String commissionOptionCT = currentCommissionProfile.getCommissionOptionCT();

                String className = null;

                if (commissionProfilePK == activeCommissionProfilePK)
                {
                    className = "associated";
                }
                else{

                    className = "default";
                }
%>
                <tr class="<%= className %>" id="<%= commissionProfilePK %>" isSelected="false" commissionProfilePK="<%= commissionProfilePK %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow(); setCommissionProfile()">
                    <td width="50%">
                        <%= commissionLevelCT %>
                    </td>
                    <td width="50%">
                        <%= commissionOptionCT %>
                    </td>
                </tr>
<%
            }
        }
%>
                    <tr class="filler"> <!-- A dummy row to help with sizing -->
                        <td colspan="2">
                            &nbsp;
                        </td>
                    </tr>
                </table>
                </span>
            </td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
                <td width="10%" nowrap align="right"><span class="requiredField">*</span>&nbsp;Start Date:&nbsp;
                    <input type="text" name="startDate" REQUIRED value="<%= startDate %>" id="startDate" onBlur="DateFormat(this,this.value,event,true)" onKeyUp="DateFormat(this,this.value,event,false)" maxlength="10" size="10"/>
                    <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
                            src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                            alt="Select a date from the calendar"></a>                  
                </td>
                <td width="10%" nowrap align="left">&nbsp;&nbsp;&nbsp;&nbsp;<span class="requiredField">**</span>&nbsp;Stop Date:&nbsp;
                    <input type="text" name="stopDate" CONTENTEDITABLE="false" value="<%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE) %>" id="stopDate" maxlength="10" size="10"/>
                </td>
        </tr>
         <tr>
            <td align="left">
                <span class="requiredField">*</span>&nbsp;<font face="" style="font:italic normal; font-size: xx-small">required</font>
                &nbsp;&nbsp;&nbsp;
                <span class="requiredField">**</span>&nbsp;<font face="" style="font:italic normal; font-size: xx-small">fixed to <%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE) %></font>
            </td>
            
            <td align="right">
                <p>
                <input name="attach" value="Attach" type="button" onClick="associateCommissionProfile()">
                <input type="button" value="Close" onClick="closeCommissionLevelSummary()">
                </p>
            </td>
        </tr>
    </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="activePlacedAgentPK2" value="<%= activePlacedAgentPK2 %>">
  <input type="hidden" name="activeCommissionProfilePK" value="<%= activeCommissionProfilePK %>">
  <input type="hidden" name="activeContractCode" value="<%= activeContractCode %>">
  <input type="hidden" name="pageMode" value="<%= pageMode %>">

</form>
</body>
</html>
