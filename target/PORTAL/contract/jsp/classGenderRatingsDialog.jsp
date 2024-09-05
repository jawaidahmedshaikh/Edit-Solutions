<!--
 * User: cgleason
 * Date: Jan 31, 2008
 * Time: 9:07:16 PM
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

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractClients"
    class="fission.beans.SessionBean" scope="session"/>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");
	PageBean formBean     = contractMainSessionBean.getPageBean("clientFormBean");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] classTypes = codeTableWrapper.getCodeTableEntries("CLASS", Long.parseLong(companyStructureId));
    CodeTableVO[] tableRatings = codeTableWrapper.getCodeTableEntries("TABLERATING", Long.parseLong(companyStructureId));
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    CodeTableVO[] underwritingClasses = codeTableWrapper.getCodeTableEntries("UNDERWRITINGCLASS", Long.parseLong(companyStructureId));

    String classType        = formBean.getValue("classType");
    String originalClassCT        = formBean.getValue("classType");
    String flatExtra        = formBean.getValue("flatExtra");
    String flatExtraAge     = formBean.getValue("flatExtraAge");
    String flatExtraDur     = formBean.getValue("flatExtraDur");
    String percentExtra     = formBean.getValue("percentExtra");
    String percentExtraAge  = formBean.getValue("percentExtraAge");
    String percentExtraDur  = formBean.getValue("percentExtraDur");
    String tableRating      = formBean.getValue("tableRating");
    String ratedGender     = formBean.getValue("ratedGender");
    String underwritingClass = formBean.getValue("underwritingClass");
    String classChangeEffectiveDate = new EDITDate().getMMDDYYYYDate();
    
    String contractClientPK = formBean.getValue("contractClientPK");
    String contractClientAllocationPK = formBean.getValue("contractClientAllocationPK");
    String clientRoleFK     = formBean.getValue("clientRoleFK");
    String segmentFK        = formBean.getValue("segmentFK");
	String taxId     	    = formBean.getValue("taxId");
	String employeeIdentification = formBean.getValue("employeeIdentification");
	String lastName   	    = formBean.getValue("lastName");
	String firstName  	    = formBean.getValue("firstName");
	String middleName 	    = formBean.getValue("middleName");
    String corporateName    = formBean.getValue("corporateName");
	String dob   	        = formBean.getValue("dob");
	String issueAge         = formBean.getValue("issueAge");
	String genderId         = formBean.getValue("genderId");
    String prefix         = formBean.getValue("prefix");
    String suffix         = formBean.getValue("suffix");
    String operator            = formBean.getValue("operator");
    String maintDateTime            = formBean.getValue("maintDateTime");
    String phoneAuth        = formBean.getValue("phoneAuth");
    String disbAddressType  = formBean.getValue("disbAddressType");
    String corrAddressType  = formBean.getValue("corrAddressType");
    String effectiveDate    = formBean.getValue("effectiveDate");
    String terminationReason = formBean.getValue("terminationReason");
    String pendingClassChangeIndStatus = formBean.getValue("pendingClassChangeIndStatus");
    String relationToEmp   = formBean.getValue("relationToEmp");
    String riderNumber = formBean.getValue("riderNumber");
	String terminationDate   = formBean.getValue("terminationDate");


    UserSession userSession = (UserSession) session.getAttribute("userSession");


%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Class/Gender Ratings</title>
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

    var shouldShowLockAlert = false;
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
      //  if (shouldShowLockAlert == true)
      // {
       //     alert("The Contract Cannot Be Edited.");
        //    return;
            
      // } else if (editableContractStatus == false) {
       // 	alert("This Contract Cannot Be Edited Due to Terminated Status.");
        //    return;
            
       // } else {
        	document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";
            <%= pendingClassChangeIndStatus = "true" %> ;
            sendTransactionAction("ContractDetailTran", "saveClassGenderRatingsDialog", "contentIFrame");
            window.close();
        //}
    }

    function closeAndShowContractMainPage()
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
      <td align="left" nowrap colspan="2">Class Change Effective Date:&nbsp;
           <input type="text" name="classChangeEffectiveDate" value="<%= classChangeEffectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.classChangeEffectiveDate', f.classChangeEffectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
       <td></td>
    </tr>
    <tr>
      <td align="left" nowrap>Class:&nbsp;
        <select name="classType" >
	      <option value="Please Select">Please Select</option>
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
        <select disabled name="tableRating">
	      <option value="Please Select">Please Select</option>
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
	      <option value="Please Select">Please Select</option>
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
        <select disabled  name="underwritingClass">
	      <option value="Please Select">Please Select</option>
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
	    <input type="text" disabled name="flatExtra"  maxlength="13" size="13" value="<%= flatExtra %>">
      </td>
	  <td align="left"  nowrap>Flat Extra Age:&nbsp;
	    <input type="text" disabled name="flatExtraAge" maxlength="3" size="3" value="<%= flatExtraAge %>">
      </td>
	  <td align="left" nowrap >Flat Extra Duration:&nbsp;
	    <input type="text" disabled name="flatExtraDur" maxlength="3" size="3" value="<%= flatExtraDur %>">
      </td>
	</tr>
    <tr>
	  <td align="left" nowrap>Percent Extra:&nbsp;
	    <input type="text" disabled name="percentExtra"  maxlength="13" size="13" value="<%= percentExtra %>">
      </td>
	  <td align="left" nowrap>Percent Extra Age:&nbsp;
	    <input type="text" disabled name="percentExtraAge"  maxlength="3" size="3" value="<%= percentExtraAge %>">
      </td>
	  <td align="left" nowrap>Percent Extra Duration:&nbsp;
	    <input type="text" disabled name="percentExtraDur" maxlength="3" size="3" value="<%= percentExtraDur %>">
      </td>
	</tr>
<%--    END Form Content --%>

    <tr>

        <td align="left" colspan="2">
            &nbsp;
        </td>
        <td id="trxMessage">
            &nbsp;
        </td>
        <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveClassGenderRatingsDialog()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="closeAndShowContractMainPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

 <input type="hidden" name="contractClientPK" value="<%= contractClientPK %>">
 <input type="hidden" name="terminationDate" value="<%= terminationDate %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="genderId" value="<%= genderId %>">
 <input type="hidden" name="prefix" value="<%= prefix %>">
 <input type="hidden" name="suffix" value="<%= suffix %>">
 <input type="hidden" name="issueAge" value="<%= issueAge %>">
 <input type="hidden" name="taxId" value="<%= taxId %>">
 <input type="hidden" name="employeeIdentification" value="<%= employeeIdentification %>">
 <input type="hidden" name="lastName" value="<%= lastName %>">
 <input type="hidden" name="firstName" value="<%= firstName %>">
 <input type="hidden" name="middleName" value="<%= middleName %>">
 <input type="hidden" name="corporateName" value="<%= corporateName %>">
 <input type="hidden" name="dob" value="<%= dob %>">
 <input type="hidden" name="contractClientAllocationPK" value="<%= contractClientAllocationPK %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">
 <input type="hidden" name="pendingClassChangeIndStatus" value="<%= pendingClassChangeIndStatus %>">
 <input type="hidden" name="operator" value="<%= operator %>">
 <input type="hidden" name="maintDateTime" value="<%= maintDateTime %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>