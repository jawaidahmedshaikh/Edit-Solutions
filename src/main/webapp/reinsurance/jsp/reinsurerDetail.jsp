<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 edit.portal.common.session.*,
                 client.*"%>
 <%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 12:16:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = Util.initString((String) request.getAttribute("responseMessage"), null);

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    ClientDetail clientDetail = (ClientDetail) request.getAttribute("clientDetail");

    ReinsurerVO reinsurerVO = (ReinsurerVO) request.getAttribute("reinsurerVO");
    
    String contactInfoIndStatus = "";

    long clientDetailFK = 0;

    long reinsurerPK = 0;

    String reinsurerNumber = "";
    String corporateName = "";
    String phoneNumber = "";
    String addressLine1 = "";
    String addressLine2 = "";
    String addressLine3 = "";
    String addressLine4 = "";
    String city = "";

    if (reinsurerVO != null)
    {
        reinsurerNumber = Util.initString(reinsurerVO.getReinsurerNumber(), "");

        reinsurerPK = reinsurerVO.getReinsurerPK();
    }

    if (clientDetail != null)
    {
        corporateName = Util.initString(clientDetail.getCorporateName(), "");
        clientDetailFK = clientDetail.getClientDetailPK();
        
        ClientAddress clientAddress = clientDetail.getPrimaryAddress();
        
        if (clientAddress != null)
        {
            addressLine1 = Util.initString(clientAddress.getAddressLine1(), "");
            addressLine2 = Util.initString(clientAddress.getAddressLine2(), "");
            addressLine3 = Util.initString(clientAddress.getAddressLine3(), "");
            addressLine4 = Util.initString(clientAddress.getAddressLine4(), "");
            city = Util.initString(clientAddress.getCity(), "");
        }
        
        ContactInformation workContactInformation = clientDetail.getWorkContactInformation();        
        if (workContactInformation != null)
        {
            phoneNumber = Util.initString(workContactInformation.getPhoneEmail(), "");
        }

        // Is there contact information?
        ContactInformation[] someContactInformation = ContactInformation.findBy_ClientDetailFK(clientDetailFK);

        if (someContactInformation.length > 0)
        {
            contactInfoIndStatus = "CHECKED";
        }
    }
%>
<%-- ****************************** End Java Code ****************************** --%>
<html>
<head>
<title>Reinsurer Detail</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>
    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var shouldShowLockAlert = true;

    var pageIsLocked = <%= userSession.getReinsurerIsLocked()%>;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        var contractIsLocked = <%= userSession.getReinsurerIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getReinsurerPK() %>";
		top.frames["header"].updateLockState(contractIsLocked, username, elementPK);

        setLockState();

        checkForResponseMessage();
    }

    /**
     *  Determines if the page should be locked.
     */
    function setLockState()
    {
        shouldShowLockAlert = !pageIsLocked;

        for (var i = 0; i < f.elements.length; i++) {

            elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "select-one") &&
                 (shouldShowLockAlert == true) ) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
    }

    /**
     * Shows a read-only view of all contact information for this Reinsurer.
     */
    function showReinsurerContactInformationDialog()
    {
        var width = getScreenWidth() * 0.50;

        var height = getScreenHeight() * 0.50;

        openDialog("reinsurerContactInformationDialog", null, width, height);

        sendTransactionAction("ReinsuranceTran", "showReinsurerContactInformationDialog", "reinsurerContactInformationDialog");    }

    /**
     * Certain events can be assigned to this function to test for lock status. E.g - a mouse click.
     */
    function showLockAlert()
    {
        if (shouldShowLockAlert == true) {

            alert("The Page Can Not Be Edited");

            return false;
        }
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Reinsurer #:
        </td>
        <td align="left" nowrap>
            <input type="text" name="reinsurerNumber" value="<%= reinsurerNumber %>" size="15" maxlength="50">
        </td>
        <td align="right" nowrap width="20%">
              <a name="contactLink" href ="javascript:showReinsurerContactInformationDialog()">Contact Information&nbsp;</a>
              <input type="checkbox" name="contactInfoIndStatus" <%= contactInfoIndStatus %>>
        </td>
        <td align="left" nowrap width="20%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Corporate Name:
        </td>
        <td align="left" nowrap>
            <input type="text" name="corporateName" value="<%= corporateName %>" size="30" maxlength="60" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Telephone:
        </td>
        <td align="left" nowrap>
            <input type="text" name="phoneNumber" value="<%= phoneNumber %>" size="14" maxlength="14" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="4">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Primary Address (Line1):
        </td>
        <td align="left" nowrap>
            <input type="text" name="addressLine1" value="<%= addressLine1 %>" size="35" maxlength="35" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            (Line2):
        </td>
        <td align="left" nowrap>
            <input type="text" name="addressLine2" value="<%= addressLine2 %> "size="35" maxlength="35" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            (Line3):
        </td>
        <td align="left" nowrap>
            <input type="text" name="addressLine3" value="<%= addressLine3 %>" size="35" maxlength="35" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            (Line4):
        </td>
        <td align="left" nowrap>
            <input type="text" name="addressLine4" value="<%= addressLine4 %>" size="35" maxlength="35" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            City:
        </td>
        <td align="left" nowrap>
            <input type="text" name="city" value="<%= city %>" size="35" maxlength="35" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageName" value="reinsurerDetail">
<input type="hidden" name="clientDetailFK" value="<%= clientDetailFK %>">
<input type="hidden" name="clientDetailPK" value="<%= clientDetailFK %>">
<input type="hidden" name="reinsurerPK" value="<%= reinsurerPK %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>