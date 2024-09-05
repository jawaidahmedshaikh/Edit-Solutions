<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.vo.*,
                 edit.common.CodeTableWrapper,
                 edit.portal.common.session.UserSession,
                 edit.common.EDITDateTime,
                 fission.utility.*" %>
<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
    String message = (String) request.getAttribute("message");
    message = Util.initString(message, "");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] scriptTypes    = codeTableWrapper.getCodeTableEntries("SCRIPTTYPE");
    CodeTableVO[] scriptStatuses = codeTableWrapper.getCodeTableEntries("SCRIPTSTATUS");

    ScriptVO[] scriptVOs = (ScriptVO[]) request.getAttribute("scriptVOs");
    List attachedScriptPKs = (List) request.getAttribute("attachedScriptPKs");

    ScriptVO scriptVO = (ScriptVO) request.getAttribute("scriptVO");
    String scriptName = (String) request.getAttribute("scriptName");
    ScriptInstructionVO[] scriptInstructionVOs = (ScriptInstructionVO[]) request.getAttribute("scriptInstructionVOs");

    String scriptId = "";
    String scriptType = "";
    String scriptStatus = "";
    String maintDate = "";
    String operator = "";

    if (scriptVO != null)
    {
        scriptName      = scriptVO.getScriptName();
        scriptId        = scriptVO.getScriptPK() + "";
        scriptType      = scriptVO.getScriptTypeCT();
        scriptStatus    = scriptVO.getScriptStatusCT();
        maintDate       = Util.fastTokenizer(scriptVO.getMaintDateTime(), " ")[0];
        operator        = scriptVO.getOperator();
    }

    scriptName      = Util.initString(scriptName, "");
    scriptType      = Util.initString(scriptType, "");
    scriptStatus    = Util.initString(scriptStatus, "");
    maintDate       = Util.initString(maintDate, "");
    operator        = Util.initString(operator, "");

    ScriptLineVO[] scriptLineVOs = (ScriptLineVO[]) request.getAttribute("scriptLineVOs");

    StringBuffer text = new StringBuffer();
	if (scriptLineVOs != null) {

        for (int i = 0; i < scriptLineVOs.length; i++) {

            text.append(scriptLineVOs[i].getScriptLine().trim());
            text.append("\n");
        }
    }

    String action = (String) request.getAttribute("action");
%>
<html>
<head>

<script language="JavaScript1.2">

    var f = null;

    var colorHighlighted = "#FFFFCC";
    var colorAssocEntry = "#00BB00";
    var colorMainEntry = "#BBBBBB";

    var message = "<%= message %>";
    var shouldShowLockAlert = true;
    var scriptIsLocked = <%= userSession.getScriptIsLocked()%>;

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement
        }

        currentRow.style.backgroundColor = colorHighlighted;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "assocEntry") {

                currentRow.style.backgroundColor = colorAssocEntry;
            }

            else if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
    }

    function init() {

        var elementType;
        var elementName;

        f = document.scriptForm;

        top.frames["header"].updateLockState(<%= userSession.getScriptIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getScriptPK() %>");

        f.scriptText.style.width="100%";
        f.scriptText.style.height="100%";

        if (message.length > 0) {

            alert(message);
        }

        shouldShowLockAlert = !scriptIsLocked;


        for (var i = 0; i < f.elements.length; i++) {

            elementType = f.elements[i].type;
            elementName = f.elements[i].name;

            if ( (elementType == "text" || elementType == "select-one") &&
                 (shouldShowLockAlert == true) &&
                 (elementName != "scriptFilter") )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        f.scriptText.focus();

        var scrollToRow = document.getElementById("<%= scriptId %>");

        if (scrollToRow != null)
        {
            //the true value sets matching id at the top
            scrollToRow.scrollIntoView(true);
        }
    }

	function getMode() {

		return "Text";
	}

    function getScriptName() {

        return '<%= scriptName %>';
    }

    function addScript() {

        var width   = 0.40 * screen.width;
        var height  = 0.30 * screen.height;

        openDialog("","newScriptNameDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"ScriptTran", "addNewScript", "newScriptNameDialog");
    }

    function changeScript() {

        sendTransactionAction("ScriptTran", "lockScript", "main");
    }

    function saveScript() {

        sendTransactionAction("ScriptTran", "saveScript", "main");
    }

    function cancelScript() {

        sendTransactionAction("ScriptTran", "unlockScript", "main");
    }

    function deleteScript() {

        sendTransactionAction("ScriptTran", "deleteScript", "main");
    }

    function showScript() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (scriptIsLocked)
        {
            if (currentRow.isSelected != "true")
            {
                showCancelScriptChangesDialog();
            }
        }
        else
        {
            if (tdElement.tagName == "IMG" &&
                tdElement.id != "splashButton") {

                showAssociatedRulesForScript();
            }
            else {

                var scriptPK = currentRow.id;

                f.scriptId.value = scriptPK;

                sendTransactionAction("ScriptTran","showScript", "main");
            }
         }
    }

    function showCancelScriptChangesDialog() {

        var width   = 0.30 * screen.width;
        var height  = 0.20 * screen.height;

        openDialog("","cancelScriptDialog","resizable=yes,width=" + width + ",height=" + height, "ScriptTran", "showCancelScriptChangesDialog", "cancelScriptDialog");
    }

    function showAssociatedRulesForScript() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

        f.scriptId.value = currentRow.id;

        var width   = 0.90 * screen.width;
        var height  = 0.50 * screen.height;

        openDialog("","associatedRulesForScriptDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"ScriptTran", "showAssociatedRulesForScript", "associatedRulesForScriptDialog");
    }

	function sendTransactionAction(tmpTransaction, tmpAction, target) {

		f.transaction.value=tmpTransaction;
		f.action.value=tmpAction;

		f.target = target;

		f.submit();
	}

    function openDialog(theURL, winName, features, transaction, action, target) {

        dialog = window.open(theURL,winName,features);

        sendTransactionAction(transaction, action, target);
    }

    function exportAsXML() {

    	f.transaction.value = "ScriptTran";
	    f.action.value      = "exportToXML";

	    f.submit();
    }

    function showLockAlert(){

        var scriptName = '<%= scriptName %>';

        if (!scriptName.length > 0) {

            alert("Pl. Select the Script to be edited.");

            return false;
        }
        else
        {
            if (shouldShowLockAlert == true) {

                alert("The Script can not be edited.");

                return false;
            }
        }
    }


    function disableScript(bool) {

    	var sName = "<%= scriptName %>";

	    if ((bool == true) && (sName == "")) {

	    	f.scriptText.disabled = true;
			f.btnChange.disabled = true;
			f.btnSave.disabled = true;
			f.btnCancel.disabled = true;
            f.btnDelete.disabled = true;
    	}
	    else {

	    	f.scriptText.disabled = false;
			f.btnChange.disabled = false;
			f.btnSave.disabled = false;
			f.btnCancel.disabled = false;
            f.btnDelete.disabled = false;
	    }
    }

    function setButtonState() {
        var action = '<%= action %>';

        switch (action) {
            case 'setupForNewScript':
                f.scriptText.disabled = false;
                f.btnAdd.disabled = false;
                f.btnChange.disabled = true;
                f.btnSave.disabled = false;
                f.btnCancel.disabled = false;
                f.btnDelete.disabled = true;
                break;
            case 'lockScript':
                f.scriptText.disabled = false;
                f.btnAdd.disabled = true;
                f.btnChange.disabled = true;
                f.btnSave.disabled = false;
                f.btnCancel.disabled = false;
                f.btnDelete.disabled = true;
                break;
            case 'showScript':
                f.scriptText.disabled = false;
                f.btnChange.disabled = false;
                f.btnSave.disabled = true;
                f.btnCancel.disabled = true;
                f.btnDelete.disabled = false;
                break;
             case 'getSelectedScript':
                f.scriptText.disabled = false;
                f.btnChange.disabled = false;
                f.btnSave.disabled = true;
                f.btnCancel.disabled = true;
                f.btnDelete.disabled = false;
                break;
            default:
                f.scriptText.disabled = false;
                f.btnAdd.disabled = false;
                f.btnChange.disabled = true;
                f.btnSave.disabled = true;
                f.btnCancel.disabled = true;
                f.btnDelete.disabled = true;
        }
    }

    function filterScriptName()
    {
        sendTransactionAction("ScriptTran", "getSelectedScript", "main");
    }

</script>
<title>scripts.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init(); document.forms(0).scriptText.focus(); disableScript(true); setButtonState()">
<form method="post" action="/PORTAL/servlet/RequestManager" name="scriptForm"
    target="_top">

<tr>
  <table  width="100%" height="2%" border="0"  cellspacing="0" cellpadding="0">
    <tr height="5%">
        <td width="80%">
            <span class="tableHeading">Script</span><br>
        </td>
        <td align="center" width="20%">
            <span class="tableHeading">Instructions</span><br>
        </td>
    </tr>
  </table>

  <table class="contentArea" style="border-style:solid; border-width:1;" width="100%" height="60%" border="0"  cellspacing="0" cellpadding="0">
    <tr valign="top">
      <td  width="80%" height="100%">
        <textarea bgColor="#BBBBBB" name="scriptText" rows="16" value=""><%= text.toString() %></textarea>
       </td>
       <td width="20%" height="100%">

        <span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; top:0; left:0; z-index:0; overflow:scroll">
          <table  bgColor="#BBBBBB" height="100%" >

          <%
              if (scriptInstructionVOs != null) {

                  for (int i = 0; i < scriptInstructionVOs.length; i++) {

                      String currentInstruction = scriptInstructionVOs[i].getInstruction();
          %>
              <td>
                <%= currentInstruction %>
              </td>
            </tr>
          <%
                  }
              }
          %>
          </table>
        </span>
       </td>

    <tr>
      <td align="left" class="errorMessage">
		<font color="#000000" size="2">
		<b>
		<script>
 			document.write("Mode: Text");
			document.write("<br>");
			document.write("Script: <%= scriptName %>");
		</script>
		</b>
        </font><br>
      </td>
    </tr>
  </table>
</tr>

  <br>
  <table width="100%" height="2%" border="0" align="left" cellspacing="0" cellpadding="0">
      <tr>
        <td align="right" nowrap>Script Type:&nbsp;</td>
        <td align="left">
            <select name="scriptType">
                <option>Please Select</option>
                <%
                    for (int i = 0; i < scriptTypes.length; i++)
                    {
                        String codeTablePK = scriptTypes[i].getCodeTablePK() + "";
                        String codeDesc    = scriptTypes[i].getCodeDesc();
                        String code        = scriptTypes[i].getCode();

                        if (scriptType.equalsIgnoreCase(code))
                        {
                            out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                        }
                    }
                %>
            </select>
         </td>
         <td align="right" nowrap>Script Status:&nbsp;</td>
         <td align="left">
            <select name="scriptStatus">
                <option>Please Select</option>
                <%
                    for (int i = 0; i < scriptStatuses.length; i++)
                    {
                        String codeTablePK = scriptStatuses[i].getCodeTablePK() + "";
                        String codeDesc    = scriptStatuses[i].getCodeDesc();
                        String code        = scriptStatuses[i].getCode();

                        if (scriptStatus.equalsIgnoreCase(code))
                        {
                            out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                        }
                    }
                %>
            </select>
         </td>

        <td align="right" nowrap>Last Maint Date:&nbsp;</td>
        <td align="left">
            <input disabled type="text" name="maintDate" maxlength="15" size="15" value="<%= maintDate %>">
        </td>
        <td align="right" nowrap>Last Maint Operator:&nbsp;</td>
        <td align="left">
            <input disabled type="text" name="operator" maxlength="15" size="15" value="<%= operator %>">
        </td>
      </tr>
  </table>

  <br>
  <br>
  <br>
  <table>
     <tr >
        <td width="40%">
          <input type="button" id="btnAdd" name="add" value=" Add " onClick="addScript()">
          <input type="button" id="btnChange" name="change" value=" Change " onClick="changeScript()">
          <input type="button" id="btnSave" name="save" value=" Save " onClick="saveScript()">
          <input type="button" id="btnCancel" name="cancel" value=" Cancel " onClick="cancelScript()">
          <input type="button" id="btnDelete" name="delete" value=" Delete " onClick="deleteScript()">
        </td>
        <td width="33%" align="center">
            <span class="tableHeading">Script Summary</span>
         </td>
         <td>
         </td>
         <td align="right" width="33%">
            <input type="text" name="scriptFilter" maxlength="30" size="15" value="">
            <input type="button" id="btnFilter" name="filter" value=" Filter " onClick="filterScriptName()">
        </td>
      </tr>
  </table>

  <table width="100%" cellpadding="0" cellspacing="0" height="38%" align="center" class="summaryArea">
      <tr bgcolor="#30548E">
          <th class="dataHeading4">ScriptName</font></th>
            <th class="dataHeading4">
               &nbsp;
            </th>
           <th class="dataHeading4">
               &nbsp;
            </th>
          <th class="dataHeading4">Script Type</font></th>

          <th class="dataHeading4">Status</font></th>
          <th class="dataHeading4">Last Maint Date</font></th>
          <th class="dataHeading4">Operator</font></th>
     </tr>
     <tr>
      <td colspan="7" height="95%">
        <span class="scrollableContent">
          <table class="scrollableArea" width="100%" height="100%" cellspacing="0" cellpadding="0">

          <%
              if (scriptVOs != null) {

                  for (int s = 0; s < scriptVOs.length; s++) {

                      ScriptVO currentScriptVO = scriptVOs[s];
                      String currentScriptName = currentScriptVO.getScriptName();
                      String currentScriptPK   = currentScriptVO.getScriptPK() + "";
                      String currentScriptType = Util.initString(currentScriptVO.getScriptTypeCT(), "");
                      String currentScriptStatus = Util.initString(currentScriptVO.getScriptStatusCT(), "");
                      String currentMaintDateTime = DateTimeUtil.formatEDITDateTimeAsMMDDYYYY(new EDITDateTime(currentScriptVO.getMaintDateTime()));
                      String currentOperator = Util.initString(currentScriptVO.getOperator(), "");

                      String className = null;
                      String isSelected = "false";

                      if (currentScriptPK.equals(scriptId)) {

                          className = "highLighted";
                          isSelected = "true";
                      }

                      else {

                          className = "mainEntry";
                          isSelected = "false";
                      }
          %>
            <tr id="<%=currentScriptPK %>" class="<%= className %>" isSelected="<%= isSelected %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showScript()">
              <td width="4%">
              <%
                  if (attachedScriptPKs.contains(new Long(currentScriptPK))) {

                      out.println("<img src=\"/PORTAL/engine/images/repeating_b2.gif\" width=\"28\" height=\"15\" alt=\"Show Attached Company Structures\" onMouseOver=\"this.src='/PORTAL/engine/images/repeating_b2_over.gif'\" onMouseOut=\"this.src='/PORTAL/engine/images/repeating_b2.gif'\">");
                  }

                  else {

                      out.println("&nbsp;");
                  }
              %>
              </td>
              <td width="16%">
                <%= currentScriptName %>
              </td>
               <td width="16%">
                <%= currentScriptType %>
              </td>
              <td width="16%">
                <%= currentScriptStatus %>
              </td>
              <td width="16%">
                <%= currentMaintDateTime %>
              </td>
              <td width="16%">
                <%= currentOperator %>
              </td>
            </tr>
          <%
                  }
              }
          %>
          </table>
        </span>
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->

    <input type="hidden" name="scriptName"  value="<%= scriptName %>">
	<input type="hidden" name="scriptId"    value="<%= scriptId %>">
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="mode"        value="text">

</form>
</body>
</html>
