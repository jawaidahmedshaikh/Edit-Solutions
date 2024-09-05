<!--
 * User: cgleason
 * Date: Feb 04, 2008
 * Time: 9:07:16 AM
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
                 edit.portal.common.session.*"%>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="quoteClients"
    class="fission.beans.SessionBean" scope="session"/>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String companyStructureId = quoteMainSessionBean.getPageBean("formBean").getValue("companyStructureId");
	PageBean formBean     = quoteMainSessionBean.getPageBean("clientFormBean");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] classTypes = codeTableWrapper.getCodeTableEntries("CLASS", Long.parseLong(companyStructureId));
    CodeTableVO[] tableRatings = codeTableWrapper.getCodeTableEntries("TABLERATING", Long.parseLong(companyStructureId));
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    CodeTableVO[] underwritingClasses = codeTableWrapper.getCodeTableEntries("UNDERWRITINGCLASS", Long.parseLong(companyStructureId));

    String classType        = formBean.getValue("classType");
    String flatExtra        = formBean.getValue("flatExtra");
    String flatExtraAge     = formBean.getValue("flatExtraAge");
    String flatExtraDur     = formBean.getValue("flatExtraDur");
    String percentExtra     = formBean.getValue("percentExtra");
    String percentExtraAge  = formBean.getValue("percentExtraAge");
    String percentExtraDur  = formBean.getValue("percentExtraDur");
    String tableRating      = formBean.getValue("tableRating");
    String ratedGender     = formBean.getValue("ratedGender");
    String underwritingClass = formBean.getValue("underwritingClass");

    String riderNumber = formBean.getValue("riderNumber");


    UserSession userSession = (UserSession) session.getAttribute("userSession");


%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Class Ratings</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var shouldShowLockAlert = true;
    var editableContractStatus = true;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        checkForResponseMessage();
    }


    function saveClassGenderRatingsDialog()
    {
    	if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return;
            
        } else {
            sendTransactionAction("QuoteDetailTran", "saveClassGenderRatingsDialog", "contentIFrame");
            window.close();
        }
    }

    function closeAndShowQuoteMainPage()
    {
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
      <td align="left" nowrap>Class:&nbsp;
        <select name="classType" >
	      <option value="null">Please Select</option>
	        <%
                for(int i = 0; i < classTypes.length; i++)
                {
                    String codeTablePK = classTypes[i].getCodeTablePK() + "";
                    String codeDesc    = classTypes[i].getCodeDesc();
                    String code        = classTypes[i].getCode();

                    if (classType.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
        </select>
      </td>
      <td align="left" nowrap>Table Rating:&nbsp;
        <select name="tableRating">
	      <option value="null">Please Select</option>
	        <%
                for(int i = 0; i < tableRatings.length; i++)
                {
                    String codeTablePK = tableRatings[i].getCodeTablePK() + "";
                    String codeDesc    = tableRatings[i].getCodeDesc();
                    String code        = tableRatings[i].getCode();

                    if (tableRating.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
      </td>
    </tr>
    <tr>
<!--       <td align="left" nowrap>Rated Gender:&nbsp;
        <select  name="ratedGender">
	      <option value="null">Please Select</option>
	        <%
                for(int i = 0; i < ratedGenders.length; i++)
                {
                    String codeTablePK = ratedGenders[i].getCodeTablePK() + "";
                    String codeDesc    = ratedGenders[i].getCodeDesc();
                    String code        = ratedGenders[i].getCode();

                    if (ratedGender.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
      </td>-->
      <td align="left" nowrap>Underwriting Class:&nbsp;
        <select  name="underwritingClass">
	      <option value="null">Please Select</option>
	        <%
                for(int i = 0; i < underwritingClasses.length; i++)
                {
                    String codeTablePK = underwritingClasses[i].getCodeTablePK() + "";
                    String codeDesc    = underwritingClasses[i].getCodeDesc();
                    String code        = underwritingClasses[i].getCode();

                    if (underwritingClass.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
      </td>
    </tr>
    <tr>
	  <td align="left" nowrap>Flat Extra:&nbsp;
	    <input type="text" name="flatExtra"  maxlength="13" size="13" value="<%= flatExtra %>">
      </td>
	  <td align="left" nowrap>Flat Extra Age:&nbsp;
	    <input type="text" name="flatExtraAge" maxlength="3" size="3" value="<%= flatExtraAge %>">
      </td>
	  <td align="left" nowrap >Flat Extra Duration:&nbsp;
	    <input type="text" name="flatExtraDur" maxlength="3" size="3" value="<%= flatExtraDur %>">
      </td>
	</tr>
    <tr>
	  <td align="left" nowrap>Percent Extra:&nbsp;
	    <input type="text" name="percentExtra"  maxlength="13" size="13" value="<%= percentExtra %>">
      </td>
	  <td align="left" nowrap>Percent Extra Age:&nbsp;
	    <input type="text" name="percentExtraAge"  maxlength="3" size="3" value="<%= percentExtraAge %>">
      </td>
	  <td align="left" nowrap>Percent Extra Duration:&nbsp;
	    <input type="text" name="percentExtraDur" maxlength="3" size="3" value="<%= percentExtraDur %>">
      </td>
	</tr>
<%--    END Form Content --%>

    <tr>

        <td align="left" colspan="2">
            &nbsp;
        </td>
        <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveClassGenderRatingsDialog()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="closeAndShowQuoteMainPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="riderNumber" value="<%= riderNumber %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>