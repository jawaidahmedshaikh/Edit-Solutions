<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                 edit.portal.common.session.UserSession,
                 fission.utility.*,
                 client.*" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="contactInfoSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] contactTypes = codeTableWrapper.getCodeTableEntries("CONTACTTYPE");

    String contactType = pageBean.getValue("contactTypeCT");
	String phoneEmail = pageBean.getValue("phoneEmail");
    String name = pageBean.getValue("name");
    String clientDetailPK = pageBean.getValue("clientDetailFK");
    String contactInformationPK = pageBean.getValue("contactInformationPK");

    String rowToMatchBase  = contactInformationPK + contactType;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private TreeMap sortContactsByType(SessionBean contactInfoSessionBean)
    {
		Map contactBeans = contactInfoSessionBean.getPageBeans();

		TreeMap sortedContacts = new TreeMap();

		Iterator it = contactBeans.values().iterator();

		while (it.hasNext())
        {
			PageBean contactBean = (PageBean) it.next();

			String contactType = contactBean.getValue("contactTypeCT");
            String contactInformationPK = contactBean.getValue("contactInformationPK");

            if (!contactType.equals(""))
            {
                sortedContacts.put(contactType + contactInformationPK, contactBean);
            }
		}

		return sortedContacts;
	}
%>
<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

var f = null;

function init()
{
    f = document.contactInfoForm;

    shouldShowLockAlert = <%= ! userSession.getClientDetailIsLocked() %>;

    for (var i = 0; i < f.elements.length; i++)
    {
        var elementType = f.elements[i].type;

        if (f.elements[i].name != "close")
        {
            if ((elementType == "text" || elementType == "button" || elementType == "select-one") &&
                (shouldShowLockAlert == true))
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
    }
}

function showLockAlert()
{
    if (shouldShowLockAlert == true)
    {
        alert("The Client can not be edited.");

        return false;
    }
}

function addNewContact()
{
    sendTransactionAction("ClientDetailTran", "clearContactForAddOrCancel", "_self");
}

function cancelContact()
{
    sendTransactionAction("ClientDetailTran", "clearContactForAddOrCancel", "_self");
}

function saveContactToSummary()
{
    if (f.contactTypeId.value == "")
    {
        alert("Please Select Contact Type");
    }
    else
    {
	    sendTransactionAction("ClientDetailTran", "saveContactToSummary", "_self");
    }
}

function deleteSelectedContact()
{
	var trElement = null;

	var rows = document.all.summaryTable.rows;

	for (var i = 0; i < rows.length; i++)
    {
		if (rows[i].selected == "true")
        {
			trElement = rows[i];
		}
    }

	var rowId     = trElement.id;

	var parsedRowId = (rowId.split("_"))[1];

	var contactTypeTd  = document.getElementById("sContactType_" + parsedRowId);
	var contactType  = contactTypeTd.innerText;

	f.selectedContactType.value = contactType;
    f.selectedContactInformationPK.value = rowId;

	sendTransactionAction("ClientDetailTran", "deleteSelectedContact", "_self");
}

function closeContactInfo()
{
	sendTransactionAction("ClientDetailTran", "closeContactInfoDialog", "contentIFrame");
    window.close();
}

function selectRow()
{
	var tdElement = window.event.srcElement;
	var trElement = tdElement.parentElement;

	var rowId     = trElement.id;
	var parsedRowId = (rowId.split("_"))[1];

	var contactTypeTd  = document.getElementById("sContactType_" + parsedRowId);
	var contactType  = contactTypeTd.innerText;

	f.selectedContactType.value  = contactType;
    f.selectedContactInformationPK.value = rowId;

	sendTransactionAction("ClientDetailTran", "showContactInfoDetailSummary", "_self");
}

</script>

<head>

<title>Client Contact Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="dialog" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="contactInfoForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Contact Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="contactTypeId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < contactTypes.length; i++) {

                String codeTablePK = contactTypes[i].getCodeTablePK() + "";
                String codeDesc    = contactTypes[i].getCodeDesc();
                String code        = contactTypes[i].getCode();

                if (contactType.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="name" maxlength="50" size="50" value="<%= name %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Phone Number/Email Address:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="phoneEmail" maxlength="65" size="65" value="<%= phoneEmail %>">
    </td>
  </tr>
</table>
<br>
<table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td nowrap align="left">
      <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewContact()">
      <input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveContactToSummary()">
      <input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelContact()">
    </td>
  </tr>
</table>
<table class="summaryArea" id="summaryTable" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="left" width="33%">Type</th>
    <th align="left" width="33%">Name</th>
    <th align="left" width="34%">Phone #/Email Address</th>
  </tr>
  <tr width="100%" height="99%">
    <td colspan="6">
      <span class="scrollableContent">
        <table class="scrollableArea" id="addressSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              int rowId   = 0;
              int columnId = 0;

              String rowToMatch = "";
              String className = "default";
              boolean selected = false;
              String contactInfoKey = "";
              String sContactType = "";
              String sContactName = "";
              String sPhoneEmail = "";

              Map sortedContacts = sortContactsByType(contactInfoSessionBean);

              Iterator it = sortedContacts.values().iterator();

              while (it.hasNext())  {

                  PageBean contact = (PageBean)it.next();

                  contactInfoKey = contact.getValue("contactInformationPK");
                  sContactType = contact.getValue("contactTypeCT");
                  sContactName = contact.getValue("name");
                  sPhoneEmail = contact.getValue("phoneEmail");

                  rowToMatch = contactInfoKey + sContactType;

                  if (rowToMatch.equals(rowToMatchBase)) {

                      className = "higlighted";

                      selected = true;
                  }
                  else {

                      className = "default";

                      selected = false;
                  }
            %>

            <tr class="<%= className %>" isSelected="<%= selected %>"
                id="<%= contactInfoKey %>_<%= rowId++ %>" contactType="<%= sContactType %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
              <td width="16%" nowrap id="sContactType_<%= columnId %>">
                <%= sContactType %>
              </td>
              <td width="16%" nowrap id="sContactName_<%= columnId %>">
                <%= sContactName %>
              </td>
              <td width="16%" nowrap id="sPhoneEmail_<%= columnId++ %>">
                <%= sPhoneEmail %>
              </td>
            </tr>
            <%
              }// end while
            %>
        </table>
      </span>
    </td>
  </tr>
</table>
<table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
  <tr>
    <td align="right" nowrap colspan="2">
      <input type="button" name="close" value="Close" onClick ="closeContactInfo()">
    </td>
  </tr>
</table>
</span>
<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
	<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
    <input type="hidden" name="contactInformationPK" value="<%= contactInformationPK %>">
    <input type="hidden" name="selectedContactType" value="">
    <input type="hidden" name="selectedContactInformationPK" value="">

</body>
</form>
</html>
