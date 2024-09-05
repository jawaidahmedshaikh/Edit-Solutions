<%@ page import="edit.common.vo.*,
                 edit.common.*, 
                 fission.utility.*,
                 security.Operator,
                 security.Password,
                 security.Role"%>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] operatorTypes = codeTableWrapper.getCodeTableEntries("OPERATORTYPE");

    String selectedOperatorPK = (String) request.getAttribute("selectedOperatorPK");

    Operator selectedOperator = (Operator) request.getAttribute("selectedOperator");

    String name = "";
    String firstName = "";
    String lastName = "";
    String middleInitial = "";
    String title = "";
    String dept = "";
    String eMail = "";
    String maintOperator = "";
    String maintDateTime = "";
    String lockedIndicator = "";
    String lockedIndStatus = "unchecked";
    String loggedInIndicator = "";
    String loggedInIndStatus = "unchecked";
    EDITDate terminationDate = null;
    String telephoneNumber = "";
    String telephoneExtension = "";
    String operatorType = "";
    EDITBigDecimal applyMax = new EDITBigDecimal("0.00");
    EDITBigDecimal removeMax = new EDITBigDecimal("0.00");
    EDITBigDecimal transferMax = new EDITBigDecimal("0.00");

    if (selectedOperator != null)
    {
        name = Util.initString(selectedOperator.getName(), "");
        firstName  = Util.initString(selectedOperator.getFirstName(), "");
        lastName = Util.initString(selectedOperator.getLastName(), "");
        middleInitial = Util.initString(selectedOperator.getMiddleInitial(), "");
        title = Util.initString(selectedOperator.getTitle(), "");
        dept = Util.initString(selectedOperator.getDept(), "");
        eMail = Util.initString(selectedOperator.getEMail(), "");
        maintOperator = Util.initString(selectedOperator.getMaintOperator(), "");
        if (selectedOperator.getMaintDateTime() != null)
        {
            maintDateTime = selectedOperator.getMaintDateTime().getFormattedDateTime();
        }
        lockedIndicator = Util.initString(selectedOperator.getLockedIndicator(), "");
        loggedInIndicator = Util.initString(selectedOperator.getLoggedInIndicator(), "");
        terminationDate = selectedOperator.getTerminationDate();
        telephoneNumber = Util.initString(selectedOperator.getTelephoneNumber(), "");
        telephoneExtension = Util.initString(selectedOperator.getTelephoneExtension(), "");
        operatorType = Util.initString(selectedOperator.getOperatorTypeCT(), "");
        applyMax = selectedOperator.getApplyMax();
        removeMax = selectedOperator.getRemoveMax();
        transferMax = selectedOperator.getTransferMax();
    }

    Password selectedPassword = (Password) request.getAttribute("selectedPassword");

    String password = "";

    if (selectedPassword != null)
    {
        password = Util.initString(selectedPassword.getEncryptedPassword(), "");
    }


    Role[] roles = (Role[]) request.getAttribute("roles");

    if (roles != null) // Sort by role name
    {
        roles = (Role[]) Util.sortObjects(roles, new String[]{"getName"});
    }

    BIZRoleVO[] operatorsRoleVOs = (BIZRoleVO[]) request.getAttribute("operatorsRoleVOs");

    Operator[] operators = (Operator[]) request.getAttribute("operators");

    if (operators != null)
    {
        operators = (Operator[]) Util.sortObjects(operators, new String[]{"getName"});
    }

    String message = (String) request.getAttribute("message");

    String newPasswordMessage = (String) request.getAttribute("newPasswordMessage");

    String pageMode = (String) request.getAttribute("pageMode");
    
    String includeTerminatedOperators = Util.initString((String) request.getAttribute("includeTerminatedOperators"), "N");
%>

<%!
    private BIZRoleVO findBIZRoleVOByRolePK(long rolePK, BIZRoleVO[] operatorsRoleVOs)
    {
        BIZRoleVO bizRoleVO = null;
        
        for (int i = 0; i < operatorsRoleVOs.length; i++)
        {
            long currentRolePK = operatorsRoleVOs[i].getRoleVO().getRolePK();   
            
            if (currentRolePK == rolePK)
            {
                bizRoleVO = operatorsRoleVOs[i];
            }
        }

        return bizRoleVO;
    }
    
    private boolean roleInSetOfMappedRoles(String rolePK, BIZRoleVO[] mappedRoleVOs)
    {
        boolean roleInSetOfImpliedRoles = false;

        if (mappedRoleVOs != null)
        {
            for (int i = 0; i < mappedRoleVOs.length; i++)
            {
                BIZRoleVO currentMappedRoleVO = mappedRoleVOs[i];

                String currentMappedRolePK = currentMappedRoleVO.getRoleVO().getRolePK() + "";

                if (rolePK.equals(currentMappedRolePK))
                {
                    roleInSetOfImpliedRoles = true;

                    break;
                }
            }
        }

        return roleInSetOfImpliedRoles;
    }
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<script language="Javascript1.2">

    var f = null;

    var selectedRolePK = "<%= selectedOperatorPK %>";

    var message = "<%= message %>";

    var newPasswordMessage = "<%= newPasswordMessage %>";

    var pageMode = "<%= pageMode %>";

    function init()
    {
    	f = document.theForm;
        
        setCheckBoxState(f.lockedIndStatus, "<%= lockedIndicator %>");
        setCheckBoxState(f.loggedInIndStatus, "<%= loggedInIndicator %>");
        
        setCheckBoxState(f.includeTerminatedOperatorsCheckBox, "<%= includeTerminatedOperators %>");

        setButtonState();

        scrollActiveEntriesIntoView();

        showMessage();

        if (newPasswordMessage != "null")
        {
            showNewPasswordDialog();
        }
    }

    function showNewPasswordDialog()
    {
        var width = 0.25 * screen.width;

        var height = 0.25 * screen.height;

        openDialog("","newPasswordDialog","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "SecurityAdminTran", "showNewPasswordDialog", "newPasswordDialog");
    }

    function setButtonState()
    {
        if (pageMode == "BROWSE")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = true;
            f.cancelButton.disabled = true;
            f.deleteButton.disabled = true;
            f.resetPwdButton.disabled = true;

            f.attachButton.disabled = true;
            f.detachButton.disabled = true;

            f.name.contentEditable = false;
            f.firstName.contentEditable = false;
            f.lastName.contentEditable = false;
            f.middleInitial.contentEditable = false;
            f.title.contentEditable = false;
            f.dept.contentEditable = false;
            f.eMail.contentEditable = false;
            f.telephoneNumber.contentEditable = false;
            f.telephoneExtension.contentEditable = false;
            f.operatorTypeCT.disabled = true;
            f.applyMax.contentEditable = false;
            f.removeMax.contentEditable = false;
            f.transferMax.contentEditable = false;
            f.maintOperator.contentEditable = false;
            f.maintDateTime.contentEditable = false;
        }

        else if (pageMode == "ADD")
        {
            f.addButton.disabled = true;
            f.saveButton.disabled = false;
            f.cancelButton.disabled = false;
            f.deleteButton.disabled = true;
            f.resetPwdButton.disabled = true;

            f.attachButton.disabled = true;
            f.detachButton.disabled = true;

            f.name.contentEditable = true;
            f.firstName.contentEditable = true;
            f.lastName.contentEditable = true;
            f.middleInitial.contentEditable = true;
            f.title.contentEditable = true;
            f.dept.contentEditable = true;
            f.eMail.contentEditable = true;
            f.telephoneNumber.contentEditable = true;
            f.telephoneExtension.contentEditable = true;
            f.operatorTypeCT.disabled = false;
            f.applyMax.contentEditable = true;
            f.removeMax.contentEditable = true;
            f.transferMax.contentEditable = true;
            f.maintOperator.contentEditable = false;
            f.maintDateTime.contentEditable = false;

            f.name.focus();
        }

        else if (pageMode == "SELECT")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = false;
            f.cancelButton.disabled = false;
            f.deleteButton.disabled = false;
            f.resetPwdButton.disabled = false;

            f.attachButton.disabled = false;
            f.detachButton.disabled = false;

            f.name.contentEditable = false;
            f.firstName.contentEditable = true;
            f.lastName.contentEditable = true;
            f.middleInitial.contentEditable = true;
            f.title.contentEditable = true;
            f.dept.contentEditable = true;
            f.eMail.contentEditable = true;
            f.telephoneNumber.contentEditable = true;
            f.telephoneExtension.contentEditable = true;
            f.operatorTypeCT.disabled = false;
            f.applyMax.contentEditable = true;
            f.removeMax.contentEditable = true;
            f.transferMax.contentEditable = true;
            f.maintOperator.contentEditable = false;
            f.maintDateTime.contentEditable = false;
        }
    }

    function showMessage()
    {
        if (message != "null")
        {
            alert(message);
        }
    }

    function scrollActiveEntriesIntoView()
    {
        if (selectedRolePK != "null")
        {
            document.getElementById(selectedRolePK).scrollIntoView(false);
        }
    }

    function sendTransactionAction(transaction, action, target)
    {
        setCheckBoxValue(f.includeTerminatedOperatorsCheckBox, f.includeTerminatedOperators);
    
        f.transaction.value = transaction;
        f.action.value = action;
        f.target = target;
        f.submit();
    }

    function highlightRow() {

        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true")
        {
            if (className == "associated")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highlighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else if (className == "default")
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
        }
    }

    function multiSelectDeselectRow()
    {
        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected == "false")
        {
            currentRow.style.backgroundColor = "#FFFFCC";

            currentRow.isSelected = "true";
        }
        else if (currentRow.isSelected == "true")
        {
            if (className == "default")
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
            else if (className == "associated")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }

            currentRow.isSelected = "false";
        }
    }

    function saveOperator()
    {
        if (f.name.value.length == 0)
        {
            alert("Userame Required");
        }
        else
        {
            setCheckBoxValue(f.lockedIndStatus, f.lockedIndicator);
            setCheckBoxValue(f.loggedInIndStatus, f.loggedInIndicator);

            if (f.terminationDate.value != "")
            {
                f.terminationDate.value = convertMMDDYYYYToYYYYMMDD(f.terminationDate.value);
            }

            sendTransactionAction("SecurityAdminTran", "saveOperator", "_self");
        }
    }
    
    function includeExcludeTerminatedOperators()
    {
        // to unselect the selected row if one is selected.
        // otherwise this function 'scrollActiveEntriesIntoView' throws error
        // if selected operator is not displayed.
        f.selectedOperatorPK.value = "";

        sendTransactionAction("SecurityAdminTran", "showOperatorsPage", "_self");
    }

    function addOperator()
    {
        sendTransactionAction("SecurityAdminTran", "addOperator", "_self");
    }

    function cancelOperator()
    {
        sendTransactionAction("SecurityAdminTran", "cancelOperator", "_self");
    }

    function showOperatorsRoles()
    {
        var tdElement = window.event.srcElement;

        var trElement = tdElement.parentElement;

        var selectedOperatorPK = trElement.operatorPK;

        f.selectedOperatorPK.value = selectedOperatorPK;
        
        sendTransactionAction("SecurityAdminTran", "showOperatorsRoles", "_self");
    }

    function deleteOperator()
    {
        if (f.selectedOperatorPK.value.length == 0)
        {
            alert("Operator Required");
        }
        else
        {
            sendTransactionAction("SecurityAdminTran", "deleteOperator", "_self");
        }
    }
    function resetPassword()
    {
        if (f.selectedOperatorPK.value.length == 0)
        {
            alert("Operator Required");
        }
        else
        {
            sendTransactionAction("SecurityAdminTran", "resetPassword", "_self");
        }
    }


    function attachRolesToOperator()
    {
        var selectedRolePKs = getSelectedRoles();

        if (selectedRolePKs != "")
        {
            f.selectedRolePKs.value = selectedRolePKs;

            sendTransactionAction("SecurityAdminTran", "attachRolesToOperator", "_self");
        }
        else
        {
            alert("A Role Must Be Selected");
        }
    }

    function detachRolesFromOperator()
    {
        var selectedRolePKs = getSelectedRoles();

        if (selectedRolePKs != "")
        {
            f.selectedRolePKs.value = selectedRolePKs;

    		sendTransactionAction("SecurityAdminTran", "detachRolesFromOperator", "_self");
        }
        else
        {
            alert("A Role Must Be Selected");
        }
    }

	function openDialog(theURL,winName,features,transaction,action) {

	    dialog = window.open(theURL,winName,features);

	    sendTransactionAction(transaction, action, winName);
	}

    function getSelectedRoles()
    {
        var mappedRolesTable = document.all.mappedRolesTable;

        var selectedRolePKs = "";

        for (var i = 0; i < mappedRolesTable.rows.length; i++)
        {
            if (mappedRolesTable.rows[i].isSelected == "true")
            {
                selectedRolePKs += mappedRolesTable.rows[i].rolePK + ",";
            }
        }

        return selectedRolePKs;
    }

    function showOperatorsInRole()
    {
        var trElement = window.event.srcElement.parentElement.parentElement;

        var selectedRolePK = trElement.rolePK;

        f.selectedRolePK.value = selectedRolePK;

        var width = 0.30 * screen.width;

        var height = 0.50 * screen.height;

        openDialog("","usersInRoleDialog","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "SecurityAdminTran", "showOperatorsInRole", "usersInRoleDialog");

        window.event.cancelBubble = true;
    }

    function showMappedRoles()
    {
        var trElement = window.event.srcElement.parentElement.parentElement;

        var selectedRolePK = trElement.rolePK;

        f.selectedRolePK.value = selectedRolePK;

        var width = 0.30 * screen.width;

        var height = 0.50 * screen.height;

        openDialog("","mappedRolesDialog","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "SecurityAdminTran", "showMappedRoles", "mappedRolesDialog");

        window.event.cancelBubble = true;
    }

</script>

<title>Operators</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>


<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table border="0" width="100%" height="90%">

    <tr height="25%">
        <td align="left" colspan="2">

        <span class="formData" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

                <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

                     <tr>
                        <td align="right">
                            Username &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="name" value="<%= name %>" size="20" maxlength="20" tabindex="1">
                        </td>
                        <td align="right">
                            Password &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="password" value="<%= password %>" size="25" maxlength="15" disabled>
                        </td>
                        <td align="right">
                            Operator Type &nbsp;
                        </td>
                        <td align="left">
                            <select name="operatorTypeCT" tabindex="2">
                              <option value="null">Please Select</option>
                                <%
                                  for(int i = 0; i < operatorTypes.length; i++)
                                  {
                                      String code        = operatorTypes[i].getCode();
                                      String codeDesc    = operatorTypes[i].getCodeDesc();

                                      if (operatorType.equalsIgnoreCase(code))
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
                    <tr>
                        <td align="right">
                            First Name &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="firstName" value="<%= firstName %>" size="15" maxlength="15" tabindex="3">
                        </td>
                        <td align="right">
                            Last Name &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="lastName" value="<%= lastName %>" size="30" maxlength="30" tabindex="4">
                        </td>
                        <td align="right">
                            Middle Init &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="middleInitial" value="<%= middleInitial %>" size="1" maxlength="1" tabindex="5">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Title &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="title" value="<%= title %>" size="20" maxlength="20" tabindex="6">
                        </td>
                        <td align="right">
                            Dept &nbsp;
                        </td>
                        <td align="left"> 
                            <input type="text" name="dept" value="<%= dept %>" size="20" maxlength="20" tabindex="7">
                        </td>
                        <td align="right">
                            E-mail &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="eMail" value="<%= eMail %>" size="30" maxlength="30" tabindex="8">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Telephone Number &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="telephoneNumber" value="<%= telephoneNumber %>" size="14" maxlength="14" tabindex="9">
                        </td>
                        <td align="right">
                            Telephone Ext &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="telephoneExtension" value="<%= telephoneExtension %>" size="6" maxlength="6" tabindex="10">
                        </td>
                        <td align="right" colspan="2">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Locked &nbsp;
                        </td>
                        <td align="left">                            
                    	    <input disabled type="checkbox" name="lockedIndStatus" <%= lockedIndStatus %>>
                        </td>
                        <td align="right">
                            Logged In &nbsp;
                        </td>
                        <td align="left">
                    	    <input disabled type="checkbox" name="loggedInIndStatus" <%= loggedInIndStatus %>>
                        </td>
                        <td align="right">
                            Termination Date &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="terminationDate" size='10' maxlength="10" value="<%= terminationDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(terminationDate) %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)" tabindex="11">
                            <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Apply Max &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="applyMax" size="20" maxlength="20" value="<%= applyMax == null? "": applyMax.toString() %>" CURRENCY tabindex="12">
                        </td>
                        <td align="right">
                            Remove Max &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="removeMax" size="20" maxlength="20" value="<%= removeMax == null? "": removeMax.toString() %>" CURRENCY tabindex="13">
                        </td>
                        <td align="right">
                            Transfer Max &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="transferMax" size="20" maxlength="20" value="<%= transferMax == null? "": transferMax.toString() %>" CURRENCY tabindex="14">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Maint Operator &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="maintOperator" value="<%= maintOperator %>" size="20" maxlength="15" disabled>
                        </td>
                        <td align="right">
                            Maint Date/Time &nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="maintDateTime" value="<%= maintDateTime %>" size="35" maxlength="35" disabled>
                        </td>
                        <td align="right">
                            &nbsp;
                        </td>
                        <td align="left">
                            &nbsp;
                        </td>
                    </tr>

                </table>

        </span>
        </td>
    </tr>

    <tr height="1%">
        <td align="left">
            <br>
            <input type="button" name="addButton" value="   Add   " onClick="addOperator()">
            <input type="button" name="saveButton" value=" Save " onClick="saveOperator()">
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelOperator()">
            <input type="button" name="deleteButton" value="Delete" onClick="deleteOperator()">
            <input type="button" name="resetPwdButton" value="Reset Pwd" onClick="resetPassword()">
            &nbsp;Include Terminated Operators:&nbsp;
            <input type="checkbox" name="includeTerminatedOperatorsCheckBox" onClick="includeExcludeTerminatedOperators()">
        </td>

        <td align="right">
            <span class="tableHeading">
                Roles
            </span>
        </td>
    </tr>

    <tr>
        <td width="75%" NOWRAP>

                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

                <tr class="heading">
                    <th width="25%">
                        Username
                    </th>
                    <th width="25%">
                        Last Name
                    </th>
                    <th width="25%">
                        First Name
                    </th>
                    <th width="25%">
                        MI
                    </th>
                </tr>
                <tr>
                    <td height="99%" colspan="4">
                        <span class="scrollableContent">
                            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                    if (operators != null)
                    {
                        for (int i = 0; i <operators.length; i++)
                        {
                            String currentOperatorPK = operators[i].getOperatorPK() + "";

                            String currentUserame = Util.initString(operators[i].getName(), "");

                            String currentLastName = Util.initString(operators[i].getLastName(), "");

                            String currentFirstName = Util.initString(operators[i].getFirstName(), "");

                            String currentMiddleInitial = Util.initString(operators[i].getMiddleInitial(), "");

                            String className = null;

                            String isSelected = null;

                            if (currentOperatorPK.equals(selectedOperatorPK))
                            {
                                className = "highlighted";

                                isSelected = "true";
                            }
                            else
                            {
                                className = "default";

                                isSelected = "false";
                            }
%>
                            <tr class="<%= className %>" id="<%= currentOperatorPK %>" operatorPK="<%= currentOperatorPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showOperatorsRoles()">
                                <td width="25%">
                                    <%= currentUserame %>
                                </td>
                                <td width="25%">
                                    <%
                                        if (! currentLastName.equals(""))
                                        {
                                            out.println(currentLastName);
                                        }
                                    %>
                                </td>
                                <td width="25%">
                                    <%
                                        if (! currentFirstName.equals(""))
                                        {
                                            out.println(currentFirstName);
                                        }
                                    %>
                                </td>
                                <td width="25%">
                                    <%
                                        if (! currentMiddleInitial.equals(""))
                                        {
                                            out.println(currentMiddleInitial);
                                        }
                                    %>
                                </td>
                            </tr>
<%
                        }
                    }
%>
                            <tr class="filler"> <!-- A dummy row to help with sizing -->
                                <td colspan="4">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                    </span>
                    </td>
                    </tr>
                </table>
        </td>

        <td width="25%" NOWRAP>
            <!-- Summary Table for Report To Hierarchies  -->
                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="heading">
                        <th width="40%">
                            Role Name
                        </th>
                        <th width="40%">
                            Mapping
                        </th>
                        <th>
                            &nbsp;
                        </th>
                    </tr>
                    <tr>
                        <td height="99%" colspan="3">
                            <span class="scrollableContent">
                                <table id="mappedRolesTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                    if (roles != null)
                    {
                        for (int i = 0; i < roles.length; i++)
                        {
                            String currentRolePK = roles[i].getRolePK() + "";

                            String currentRoleName = roles[i].getName();

                            String className = null;

                            String isSelected = "false";

                            String implication = "";

                            if (roleInSetOfMappedRoles(currentRolePK, operatorsRoleVOs))
                            {
                                className = "associated";

                                BIZRoleVO bizRoleVO = findBIZRoleVOByRolePK(Long.parseLong(currentRolePK), operatorsRoleVOs);

                                implication = bizRoleVO.getImplication();
                            }
                            else
                            {
                                className = "default";
                            }
%>
                            <tr class="<%= className %>" rolePK="<%= currentRolePK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="multiSelectDeselectRow()">
                                <td align="left" width="40%" nowrap>
                                    <%= currentRoleName %>
                                </td>
                                <td align="left" width="40%" nowrap>
                                    <%= implication %>
                                </td>
                                <td align="right" nowrap>
                                    <%
                                        out.println("<img src=\"/PORTAL/common/images/U.gif\" width=\"28\" height=\"15\" alt=\"Users In Role\" onMouseOver=\"this.src='/PORTAL/common/images/UOVER.gif'\" onMouseOut=\"this.src='/PORTAL/common/images/U.gif'\" onClick='showOperatorsInRole()'>");

                                        out.println("<img src=\"/PORTAL/common/images/I.gif\" width=\"28\" height=\"15\" alt=\"Implied Roles\" onMouseOver=\"this.src='/PORTAL/common/images/IOVER.gif'\" onMouseOut=\"this.src='/PORTAL/common/images/I.gif'\" onClick='showMappedRoles()'>");
                                    %>
                                </td>
                            </tr>
<%
                        }
                    }
%>
                            <tr class="filler"> <!-- A dummy row to help with sizing -->
                                <td colspan="3">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                    </span>
                    </td>
                    </tr>
                </table>
        </td>
    </tr>
    <tr height="5%">
        <td align="right">
            <input type="button" name="attachButton" value="Attach" onClick="attachRolesToOperator()">
        </td>
        <td align="left">
            <input type="button" name="detachButton" value="Detach" onClick="detachRolesFromOperator()">
        </td>
    </tr>
</table>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">
<input type="hidden" name="selectedOperatorPK" value="<%= selectedOperatorPK %>">
<input type="hidden" name="selectedRolePK" value="">
<input type="hidden" name="selectedRolePKs" value="">
<input type="hidden" name="pageMode" value="<%= pageMode %>">
<input type="hidden" name="impliedRolePKs" value="">
<input type="hidden" name="componentMethodPKs" value="">
<input type="hidden" name="newPasswordMessage" value="<%= newPasswordMessage %>">
<input type="hidden" name="lockedIndicator" value="">
<input type="hidden" name="loggedInIndicator" value="">
<input type="hidden" name="includeTerminatedOperators">


</form>

</body>
</html>
