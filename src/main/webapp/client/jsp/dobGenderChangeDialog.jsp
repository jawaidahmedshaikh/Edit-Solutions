<!--
 * User: cgleason
 * Date: Jan 29, 2008
 * Time: 3:07:16 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 edit.common.CodeTableWrapper,
                 client.ClientDetail,
                 fission.utility.Util,
                 edit.common.EDITDate,
                 fission.beans.*,
                 fission.utility.*" %>

<jsp:useBean id="clientDetailSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] genders = codeTableWrapper.getCodeTableEntries("GENDER");

    PageBean pageBean = clientDetailSessionBean.getPageBean("pageBean");

	String genderId  	    = pageBean.getValue("genderId");
	
    String dob	= pageBean.getValue("dateOfBirth");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - DOB/Gender Change</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

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


    function saveDOBGenderChangeDialog()
    {
        sendTransactionAction("ClientDetailTran", "saveDOBGenderChangeDialog", "contentIFrame");
        window.close();
    }

    function closeAndShowClientPage()
    {
<%--        sendTransactionAction("ClientDetailTran", "showSelectedClient", "main");--%>

        window.close();
    }




</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>

    <tr>
        <td align="right" nowrap>
            Date of Birth:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="dob" value="<%= dob %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.dob', f.dob.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Gender:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="genderId">
              <option value="null">Please Select</option>
              <%
                for(int i = 0; i < genders.length; i++)
                {
                    String codeDesc    = genders[i].getCodeDesc();
                    String code        = genders[i].getCode();

                    if (genderId.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
              %>
            </select>
        </td>
     </tr>
<%--    END Form Content --%>

    <tr>
        <td align="left" colspan="3">
            &nbsp;
        </td>
        <td id="trxMessage">
            &nbsp;
        </td>
        <td align="left" colspan="1">
            &nbsp;
        </td>        <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveDOBGenderChangeDialog()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="closeAndShowClientPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="genderId" value="<%= genderId %>">
<input type="hidden" name="dateOfBirth" value="<%= dob %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>