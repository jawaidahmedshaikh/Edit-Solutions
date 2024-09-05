<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 15, 2004
  Time: 11:58:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 reinsurance.component.*,
                 reinsurance.business.*,
                 client.business.*,
                 client.component.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    TreatyVO[] treatyVOs = (TreatyVO[]) request.getAttribute("treatyVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Treaty Group List</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;
    
    var responseMessage = "<%= responseMessage %>";
    
    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
        
        checkForResponseMessage();
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Treaty Summary ****************************** --%>
<%-- Column Headings --%>
<span class="tableHeading"> Treaty List</span>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="20%" nowrap>
            Name
        </td>
        <td width="20%" nowrap>
            Reinsurer #
        </td>
        <td width="20%" nowrap>
            Start Date
        </td>
        <td width="20%" nowrap>
            Stop Date
        </td>
        <td width="20%" nowrap>
            Pool %
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:91%; top:0; left:0;">
    <table id="fooSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (treatyVOs != null) // Test for the existence of the target VOs.
    {
        Reinsurance reinsurance = new ReinsuranceComponent();

        Lookup clientLookup = new LookupComponent();

        for (int i = 0; i < treatyVOs.length; i++) // Loop through the target VOs.
        {
            String currentTreatyPK = String.valueOf(treatyVOs[i].getTreatyPK());

            ReinsurerVO reinsurerVO = reinsurance.findReinsurerBy_ReinsurerPK(treatyVOs[i].getReinsurerFK());

            ClientDetailVO clientDetailVO = clientLookup.findClientDetailByClientPK(reinsurerVO.getClientDetailFK(), false, null)[0];

            String currentReinsurerNumber = Util.initString(reinsurerVO.getReinsurerNumber(), "&nbsp;");

            String currentCorporateName = Util.initString(clientDetailVO.getCorporateName(),  "&nbsp;");

            String currentStartDate = Util.initString(treatyVOs[i].getStartDate(),  null);

            currentStartDate = (currentStartDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentStartDate):"&nbsp;";

            String currentStopDate = Util.initString(treatyVOs[i].getStopDate(), null);

            currentStopDate = (currentStopDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentStopDate):"&nbsp;";

            String poolPercent = String.valueOf(treatyVOs[i].getPoolPercentage());

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = "default";
%>
        <tr class="<%= className %>" id="<%= currentTreatyPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>">
            <td width="20%" nowrap>
                <%= currentCorporateName %>
            </td>
            <td width="20%" nowrap>
                <%= currentReinsurerNumber %>
            </td>
            <td width="20%" nowrap>
                <%= currentStartDate %>
            </td>
            <td width="20%" nowrap>
                <%= currentStopDate %>
            </td>
            <td width="20%" nowrap>
                <%= poolPercent %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>      
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Treaty Summary ****************************** --%>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="100%">
            <input type="button" value="Close" onClick="closeWindow()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>