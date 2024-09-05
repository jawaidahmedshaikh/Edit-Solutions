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

<jsp:useBean id="clientAddressSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null)
    {
        editingExceptionExists = "true";
    }

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null)
    {
        voEditExceptionExists = "true";
    }

    String clientMessage  = (String) request.getAttribute("clientMessage");
    if (clientMessage == null)
    {
        clientMessage = "";
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] states            = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] zipCDPlacements   = codeTableWrapper.getCodeTableEntries("ZIPCDPLACEMENT");
    CodeTableVO[] countries         = codeTableWrapper.getCodeTableEntries("COUNTRY");
    CodeTableVO[] addressTypes      = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE");

    String areaId	            = pageBean.getValue("areaId");
	String zipCodePositionId    = pageBean.getValue("zipCodePositionId");

    String addressTypeId        = pageBean.getValue("addressTypeId");
    String sequenceNumber       = pageBean.getValue("sequenceNumber");

    String effectiveDate        = pageBean.getValue("effectiveDate");
    
    String terminationDate      = Util.initString(pageBean.getValue("terminationDate"), DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE));;
    
	String startMonth  	        = pageBean.getValue("startMonth");
    String startDate = ClientAddress.defaultStartDate();

	if (startMonth.equals(""))
    {
		startMonth = ClientAddress.getStartDateMonth(startDate);
	}

	String startDay    	        = pageBean.getValue("startDay");
	if (startDay.equals(""))
    {
		startDay = ClientAddress.getStartDateDay(startDate);
	}

	String stopMonth   	        = pageBean.getValue("stopMonth");
    String stopDate = ClientAddress.defaultStopDate();

	if (stopMonth.equals(""))
    {
		stopMonth = ClientAddress.getStopDateMonth(stopDate);
	}

	String stopDay   	        = pageBean.getValue("stopDay");
	if (stopDay.equals(""))
    {
		stopDay = ClientAddress.getStopDateDay(stopDate);
	}

	String addressLine1         = pageBean.getValue("addressLine1");
	String addressLine2         = pageBean.getValue("addressLine2");
	String addressLine3			= pageBean.getValue("addressLine3");
	String addressLine4  	    = pageBean.getValue("addressLine4");
	String county        	    = pageBean.getValue("county");
	String city				   	= pageBean.getValue("city");
	String countryId     	    = pageBean.getValue("countryId");
	String zipCode       	    = pageBean.getValue("zipCode");
    
    String operator             = pageBean.getValue("operator");
    String maintDateTime        = pageBean.getValue("maintDateTime");

    String clientDetailPK       = pageBean.getValue("clientDetailFK");
    String clientAddressPK      = pageBean.getValue("clientAddressPK");

    String rowToMatchBase  = clientAddressPK + addressTypeId;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private TreeMap sortAddressesByType(SessionBean clientAddresses)
    {
		Map transactionBeans = clientAddresses.getPageBeans();

		TreeMap sortedTransactions = new TreeMap();

		Iterator enumer  = transactionBeans.values().iterator();

		while (enumer.hasNext())
        {
			PageBean addressBean = (PageBean) enumer.next();

			String addressType = addressBean.getValue("addressTypeId");
            String clientAddressPK = addressBean.getValue("clientAddressPK");
            String addressLine1 = addressBean.getValue("addressLine1");

            if (!addressType.equals(""))
            {
                sortedTransactions.put(addressType + addressLine1 + clientAddressPK, addressBean);
            }
		}

		return sortedTransactions;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script language="JavaScript1.2">

var f = null;

var shouldShowLockAlert = true;

var errorMessage = "<%= errorMessage %>";
var clientMessage = "<%= clientMessage %>";
var isDragging = false;
var voEditExceptionExists = "<%= voEditExceptionExists %>";
var editingExceptionExists = "<%= editingExceptionExists %>";


function init()
{
	top.frames["main"].setActiveTab("addressTab");

	f = document.clientAddressForm;

    if (clientMessage != "" && clientMessage != null)
    {
        alert(clientMessage);
    }

    if (errorMessage != "" && errorMessage != null)
    {
        alert("<%= errorMessage %>");
    }

    var clientIsLocked = <%= userSession.getClientDetailIsLocked()%>;
    var username = "<%= userSession.getUsername() %>";
    var elementPK = "<%= userSession.getClientDetailPK() %>";
    top.frames["header"].updateLockState(clientIsLocked, username, elementPK);

    shouldShowLockAlert = !clientIsLocked;

    for (var i = 0; i < f.elements.length; i++)
    {
        var elementType = f.elements[i].type;

        if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
        {
            f.elements[i].onclick = showLockAlert;
            f.elements[i].onkeydown = showLockAlert;
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

function checkForEditingException()
{
    if (editingExceptionExists == "true")
    {
        openDialog("", "exceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3);
	    sendTransactionAction("ClientDetailTran", "showEditingExceptionDialog", "exceptionDialog");
    }
}

function checkForVOEditException()
{
    if (voEditExceptionExists == "true")
    {
        openDialog("", "voEditExceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3);
	    sendTransactionAction("ClientDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
    }
}

function addNewAddress()
{
    sendTransactionAction("ClientDetailTran", "clearAddressForAddOrCancel", "contentIFrame");
}

function cancelAddress()
{
    sendTransactionAction("ClientDetailTran", "clearAddressForAddOrCancel", "contentIFrame");
}

function saveAddressToSummary()
{
    if (f.addressTypeId.value == "")
    {
        alert("Please Enter Address Type");
    }
    else
    {
	    sendTransactionAction("ClientDetailTran", "saveAddressToSummary", "contentIFrame");
    }
}

function deleteSelectedAddress()
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

	var addressTypeTd  = document.getElementById("addressType_" + parsedRowId);
	var addressType  = addressTypeTd.innerText;

	var addressLine1  = trElement.addressLine1;

	f.selectedAddressType.value     = addressType;
	f.selectedAddressLine1.value    = addressLine1;
    f.selectedClientAddressPK.value = rowId;

	sendTransactionAction("ClientDetailTran", "deleteSelectedAddress", "contentIFrame");
}

function selectRow()
{
	var tdElement = window.event.srcElement;
	var trElement = tdElement.parentElement;

	var rowId     = trElement.id;
	var parsedRowId = (rowId.split("_"))[1];

	var addressTypeTd  = document.getElementById("addressType_" + parsedRowId);
	var addressType  = addressTypeTd.innerText;

	var addressLine1 = trElement.addressLine1;

	f.selectedAddressType.value  = addressType;
    f.selectedAddressLine1.value = addressLine1;
    f.selectedClientAddressPK.value = rowId;

	sendTransactionAction("ClientDetailTran", "showAddressDetailSummary", "contentIFrame");
}

</script>

<head>

<title>Client Address Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name="clientAddressForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table width="100%" height="73%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Address Type:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <select name="addressTypeId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < addressTypes.length; i++) {

                String codeTablePK = addressTypes[i].getCodeTablePK() + "";
                String codeDesc    = addressTypes[i].getCodeDesc();
                String code        = addressTypes[i].getCode();

                if (addressTypeId.equalsIgnoreCase(code)) {

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
    <td align="right" nowrap>Effective Date:&nbsp;</td>
    <td align="left" nowrap>
            <input type="text" name="effectiveDate" size='10' maxlength="10" value="<%= effectiveDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
     </td>
    <td align="right" nowrap>Termination Date:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="terminationDate" size='10' maxlength="10" value="<%= terminationDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
      <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Sequence:&nbsp;</td>
    <td colspan="3" align="left" nowrap>
      <input disabled type="text" name="sequenceNumber" value="<%=sequenceNumber %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Start Date:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="startMonth" size="2" maxlength="2" value="<%= startMonth %>">
        /
      <input type="text" name="startDay" size="2" maxlength="2" value="<%= startDay %>">
    </td>
    <td align="right" nowrap>Stop Date:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="stopMonth" size="2" maxlength="2" value="<%= stopMonth %>">
        /
      <input type="text" name="stopDay" size="2" maxlength="2" value="<%= stopDay %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Address (line 1):&nbsp;</td>
    <td align="left" colspan="3" nowrap>
      <input type="text" name="addressLine1" maxlength="35" size="40" value="<%= addressLine1 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>(line 2):&nbsp;</td>
    <td align="left" colspan="3" nowrap>
      <input type="text" name="addressLine2" maxlength="35" size="40" value="<%= addressLine2 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>(line 3):&nbsp;</td>
    <td align="left" colspan="3" nowrap>
      <input type="text" name="addressLine3" maxlength="35" size="40" value="<%= addressLine3 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>(line 4):&nbsp;</td>
    <td align="left" colspan="3" nowrap>
      <input type="text" name="addressLine4" maxlength="35" size="40" value="<%= addressLine4 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>City:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="city" maxlength="35" size="20" value="<%= city %>">
    </td>
    <td align="right" nowrap>County:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="county" maxlength="35" size="20" value="<%= county %>" >
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>State:&nbsp;</td>
    <td align="left" nowrap>
      <select name="areaId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < states.length; i++) {

                String codeTablePK = states[i].getCodeTablePK() + "";
                String codeDesc    = states[i].getCodeDesc();
                String code        = states[i].getCode();

                if (areaId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Country:&nbsp;</td>
    <td align="left" nowrap>
      <select name="countryId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < countries.length; i++) {

                String codeTablePK = countries[i].getCodeTablePK() + "";
                String codeDesc    = countries[i].getCodeDesc();
                String code        = countries[i].getCode();

                if (countryId.equalsIgnoreCase(code)) {

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
    <td align="right" nowrap>Zip Code:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="zipCode" maxlength="15" size="15" value="<%= zipCode %>">
    </td>
    <td align="right" nowrap>Zip code Position:&nbsp;</td>
    <td align="left" nowrap>
      <select name="zipCodePositionId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < zipCDPlacements.length; i++) {

                String codeTablePK = zipCDPlacements[i].getCodeTablePK() + "";
                String codeDesc    = zipCDPlacements[i].getCodeDesc();
                String code        = zipCDPlacements[i].getCode();

                if (zipCodePositionId.equalsIgnoreCase(code)) {

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
    <td align="right">Operator:&nbsp;</td>
    <td align="left"><input type="text" name="operator" maxlength="10" size="20" value="<%= operator %>" readonly='readonly'></td>
    <td align="right">Maint Date/Time:&nbsp;</td>
    <td align="left"><input type="text" name="maintDateTime" maxlength="10" size="20" value="<%= maintDateTime %>" readonly='readonly'></td>
  </tr>
        </table>
<br>
<table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td nowrap align="left">
      <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewAddress()">
      <input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveAddressToSummary()">
      <input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelAddress()">
    </td>
  </tr>
</table>
<table class="summaryArea" id="summaryTable" width="100%" height="25%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="left" width="16%">Type</th>
    <th align="left" width="16%">Sequence</th>
    <th align="left" width="16%">Effective Date</th>
    <th align="left" width="16%">Start Date</th>
    <th align="left" width="16%">Stop Date</th>
    <th align="left" width="16%">Termination Date</th>
  </tr>
  <tr width="100%" height="99%">
    <td colspan="6">
      <span class="scrollableContent">
        <table class="scrollableArea" id="addressSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              int rowId   = 0;
              int columnId = 0;

              String rowToMatch = "";
              String className = "";
              String selected = "";
              String clientAddressKey = "";
              String summaryAddressType = "";
              String summarySeqNumber = "";
              String sAddressLine1 = "";

              Map sortedTransactions = sortAddressesByType(clientAddressSessionBean);

              Iterator it = sortedTransactions.values().iterator();

              while (it.hasNext())  {

                  PageBean address = (PageBean)it.next();

                  clientAddressKey     = address.getValue("clientAddressPK");
                  summarySeqNumber     = address.getValue("sequenceNumber");
                  summaryAddressType   = address.getValue("addressTypeId");
                  sAddressLine1        = address.getValue("addressLine1");

                  String summaryEffDate  = address.getValue("effectiveDate");
                  String summaryTermDate = address.getValue("terminationDate");

                  String summaryStartDate  = address.getValue("startDate");
                  String summaryStopDate   = address.getValue("stopDate");

                  rowToMatch = clientAddressKey + summaryAddressType;

                  if (rowToMatch.equals(rowToMatchBase)) {

                      className = "highLighted";

                      selected = "true";
                  }
                  else {

                      className = "mainEntry";

                      selected="false";
                  }
            %>

            <tr class="<%= className %>" isSelected="<%= selected %>"
                id="<%= clientAddressKey %>_<%= rowId++ %>" addressLine1="<%= sAddressLine1 %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
              <td width="16%" nowrap id="addressType_<%= columnId %>">
                <%= summaryAddressType %>
              </td>
              <td width="16%" nowrap id="sequenceNumber_<%= columnId %>">
                <%= summarySeqNumber %>
              </td>
              <td width="16%" nowrap id="effectiveDate_<%= columnId %>">
                <%= summaryEffDate %>
              </td>
              <td width="16%" nowrap id="startDate_<%= columnId %>">
                <%= summaryStartDate %>
              </td>
              <td width="16%" nowrap id="stopDate_<%= columnId %>">
                <%= summaryStopDate %>
              </td>
              <td width="16%" nowrap id="terminationDate_<%= columnId++ %>">
                <%= summaryTermDate %>
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
</span>
<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
	<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
    <input type="hidden" name="clientAddressPK" value="<%= clientAddressPK %>">
    <input type="hidden" name="selectedAddressType" value="">
    <input type="hidden" name="selectedAddressLine1" value="">
    <input type="hidden" name="selectedClientAddressPK" value="">
    <input type="hidden" name="sequenceNumber" value="<%= sequenceNumber %>">

<!-- recordPRASEEvents is set by the toolbar when saving the client -->
    <input type="hidden" name="recordPRASEEvents" value="false">

</body>
</form>
</html>
