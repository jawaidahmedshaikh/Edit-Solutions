<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.CodeTableWrapper,
                 security.component.SecurityComponent,
                 security.FilteredRole,
                 security.Role,
                 engine.Company"%>
<%

    CodeTableVO[] securedComponents = CodeTableWrapper.getSingleton().getCodeTableEntries("SECURITYCOMPONENT");

    String selectedSecuredComponentCT = (String) request.getAttribute("selectedSecuredComponentCT");

    Role selectedRole = (Role) request.getAttribute("selectedRole");

    String roleName = Util.initString((String) request.getAttribute("roleName"), "");

    if (selectedRole != null)
    {
        roleName = Util.initString(selectedRole.getName(), "");
    }

    String selectedRolePK = (String) request.getAttribute("selectedRolePK");

    String selectedProductStructurePK =
            (String) request.getAttribute("selectedProductStructurePK");

    ProductStructureVO selectedProductStructureVO =
            (ProductStructureVO) request.getAttribute("selectedProductStructureVO");
    
    String companyName = "";
    
    if (selectedProductStructureVO != null)
    {
        Company company = Company.findByPK(new Long(selectedProductStructureVO.getCompanyFK()));
        companyName = company.getCompanyName();
    }

    long[] selectedProductStructurePKs =
            (long[]) request.getAttribute("selectedProductStructurePKs");

    String selectedProductStructurePKsStr = "";
    for(int i=0; i<selectedProductStructurePKs.length; i++)
    {
         if (i!= 0)
         {
            selectedProductStructurePKsStr += ",";
         }
         selectedProductStructurePKsStr += selectedProductStructurePKs[i];
    }

    Role[] roles = (Role[]) request.getAttribute("roles");

    if (roles != null) // Sort by role name
    {
        roles = (Role[]) Util.sortObjects(roles, new String[]{"getName"});
    }

    ProductStructureVO[] productStructureVOs =
            (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    if (productStructureVOs != null)
    {
       productStructureVOs = (ProductStructureVO[]) Util.sortObjects(productStructureVOs, new String[]{"getBusinessContractName"});
   }

    Set setOfProductStructurePKsAttachedToSelectedRole =
            (Set) request.getAttribute("setOfProductStructurePKsAttachedToSelectedRole");

    BIZRoleVO[] impliedBIZRoleVOs = (BIZRoleVO[]) request.getAttribute("impliedBIZRoleVOs");

    BIZComponentMethodVO[] bizComponentMethodVOs = (BIZComponentMethodVO[]) request.getAttribute("bizComponentMethodVOs");

    String message = (String) request.getAttribute("message");

    String pageMode = (String) request.getAttribute("pageMode");

    String cssClassForRoleHeading = (selectedRolePK == null) ? "formData" : "formDataSelected";

    String showUseCaseButtons = (String) request.getAttribute("showUseCaseButtons");

%>
     
<%!
    private boolean isEmpty(String aString)
    {
        if (aString == null) return true;
        if (aString.length() == 0) return true;
        if (aString.equals("null")) return true;
        return false;  
    }
    
    private boolean isNotEmpty(String aString)
    {
        return ! isEmpty(aString);        
    }
    
    private boolean roleInSetOfImpliedRoles(String impliedRolePK, BIZRoleVO[] impliedBIZRoleVOs)
    {
        boolean roleInSetOfImpliedRoles = false;

        if (impliedBIZRoleVOs != null)
        {
            for (int i = 0; i < impliedBIZRoleVOs.length; i++)
            {
                BIZRoleVO currentImpliedBIZRoleVO = impliedBIZRoleVOs[i];

                String currentImpliedRolePK = currentImpliedBIZRoleVO.getRoleVO().getRolePK() + "";

                if (impliedRolePK.equals(currentImpliedRolePK))
                {
                    roleInSetOfImpliedRoles = true;

                    break;
                }
            }
        }

        return roleInSetOfImpliedRoles;
    }

    private BIZComponentMethodVO[] sortComponentMethods(BIZComponentMethodVO[] bizComponentMethodVOs)
    {
        TreeMap map = new TreeMap();

        for (int i = 0; i < bizComponentMethodVOs.length; i++)
        {
            map.put(bizComponentMethodVOs[i].getComponentMethodVO().getMethodName(), bizComponentMethodVOs[i]);
        }

        int i = 0;

        BIZComponentMethodVO[] sortedComponentMethodVOs = new BIZComponentMethodVO[map.size()];

        for (Iterator it = map.keySet().iterator(); it.hasNext(); i++)
        {
            sortedComponentMethodVOs[i] = (BIZComponentMethodVO) map.get(it.next());
        }

        return sortedComponentMethodVOs;
    }

    private BIZRoleVO getAssociatedBIZRoleVO(Role role, BIZRoleVO[] impliedBIZRoleVOs)
    {
        long rolePK = role.getRolePK().longValue();

        BIZRoleVO bizRoleVO = null;

        if (impliedBIZRoleVOs != null)
        {
            for (int i = 0; i < impliedBIZRoleVOs.length; i++)
            {
                long bizRolePK = impliedBIZRoleVOs[i].getRoleVO().getRolePK();

                if (rolePK == bizRolePK)
                {
                    bizRoleVO = impliedBIZRoleVOs[i];
                }
            }
        }

        return bizRoleVO;
    }
%>


<html>
<head>

<script language="Javascript1.2">

    var f = null;

    var selectedRolePK = "<%= selectedRolePK %>";

    var selectedProductStructurePK = "<%= selectedProductStructurePK %>";

    var message = "<%= message %>";

    var pageMode = "<%= pageMode %>";

    var winConfirm = null;

    function init()
    {

     	f = document.theForm;

        setButtonState();

        scrollActiveEntriesIntoView();

        showMessage();
    }

    function setButtonState()
    {
        if (pageMode == "BROWSE")
        {
            f.addButton.disabled = false;

            f.saveButton.disabled = true;
            f.cancelButton.disabled = true;
            f.deleteButton.disabled = true;
            f.attachButtonImplied.disabled = true;
            f.detachButtonImplied.disabled = true;
            f.attachButtonProductStructures.disabled = true;
            f.detachButtonProductStructures.disabled = true;
            //f.saveSecuredMethodsButton.disabled = true;
            //f.cancelSecuredMethodsButton.disabled = true;
            //f.checkAllYButton.disabled = true;
            //f.checkAllNButton.disabled = true;
            f.roleName.contentEditable = false;
        }

        else if (pageMode == "ADD")
        {
            // THESE TWO ARE ACTIVE
            f.saveButton.disabled = false;
            f.cancelButton.disabled = false;

            f.addButton.disabled = true;
            f.deleteButton.disabled = true;
            f.attachButtonImplied.disabled = true;
            f.detachButtonImplied.disabled = true;
            f.attachButtonProductStructures.disabled = true;
            f.detachButtonProductStructures.disabled = true;
            //f.saveSecuredMethodsButton.disabled = true;
            //f.cancelSecuredMethodsButton.disabled = true;
            //f.checkAllYButton.disabled = true;
            //f.checkAllNButton.disabled = true;
            f.roleName.contentEditable = true;

            f.roleName.focus();
        }

        else if (pageMode == "SELECT")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = false;
            f.cancelButton.disabled = false;
            f.deleteButton.disabled = false;
            f.attachButtonImplied.disabled = false;
            f.detachButtonImplied.disabled = false;
            f.attachButtonProductStructures.disabled = false;
            f.detachButtonProductStructures.disabled = false;
            //f.saveSecuredMethodsButton.disabled = false;
            //f.cancelSecuredMethodsButton.disabled = false;
            //f.checkAllYButton.disabled = false;
            //f.checkAllNButton.disabled = false;
            f.roleName.contentEditable = false;
        }

        // IF THERE IS A SINGLE
        // SELECTED PRODUCT STRUCTURE AND IT IS
        // ATTACHED, THEN ENABLE THE USECASE CONTROLS
        // ELSE LOCK THEM ALL.  IN THE SECURITYADMINTRAN
        // WE DETERMINE THAT AND SET A SINGLE HIDDEN
        // ELEMENT CALLED showUseCaseButtons

        if (f.showUseCaseButtons.value == "true")
        {
            f.saveSecuredMethodsButton.disabled = false;
            f.cancelSecuredMethodsButton.disabled = false;
            f.checkAllYButton.disabled = false;
            f.checkAllNButton.disabled = false;
            f.selectedSecuredComponentCTOptions.disabled = false;
        }
        else
        {
            f.saveSecuredMethodsButton.disabled = true;
            f.cancelSecuredMethodsButton.disabled = true;
            f.checkAllYButton.disabled = true;
            f.checkAllNButton.disabled = true;
            f.selectedSecuredComponentCTOptions.disabled = true;
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

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value = transaction;
        f.action.value = action;
        f.target = target;
        f.submit();
    }

    function highlightRow() {

        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        var className = currentRow.className;

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

    function selectProductStructure(productStructurePK)
    {
        // first count the number of product structures that are highlighted
        var selectedPKs = getSelectedProductStructures();

        f.selectedProductStructurePKs.value = selectedPKs;

        // set the selectedProductPKs
        var arrayPKs = selectedPKs.split(",");

        if (arrayPKs.length == 1)
        {
            // if = 1 then set its PK and show methods
            f.selectedProductStructurePK.value = selectedPKs;
            showSecuredMethods();
        }
        else
        {
            f.selectedProductStructurePK.value = "";

            // if not = 1, then disable the use cases and the selectedProductStructure

            //f.selectedSecuredComponentCTOptions.disabled = true;
            //f.saveSecuredMethodsButton.disabled = true;
            //f.cancelSecuredMethodsButton.disabled = true;
            //f.checkAllYButton.disabled = true;
            //f.checkAllNButton.disabled = true;

            showSecuredMethods();
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

    function saveRole()
    {
        if (f.roleName.value.length == 0)
        {
            alert("Role Name Required");
        }
        else
        {
            sendTransactionAction("SecurityAdminTran", "saveRole", "_self");
        }
    }

    function addRole()
    {
        sendTransactionAction("SecurityAdminTran", "addRole", "_self");
    }

    function cancelRole()
    {
        sendTransactionAction("SecurityAdminTran", "cancelRole", "_self");
    }

    function showImpliedRoles()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var selectedRolePK = trElement.rolePK;

        f.selectedRolePK.value = selectedRolePK;

		sendTransactionAction("SecurityAdminTran", "showImpliedRoles", "_self");
    }

    function showSecuredMethods()
    {
        if (f.selectedRolePK.value != "null" && f.selectedProductStructurePK != "null")
        {
            var selected =  f.selectedSecuredComponentCTOptions.options.selectedIndex;
            f.selectedSecuredComponentCT.value =
                f.selectedSecuredComponentCTOptions.options[selected].value;
            sendTransactionAction("SecurityAdminTran", "showSecuredMethods", "_self");
        }
    }

    function deleteRole()
    {
        if (f.selectedRolePK.value.length == 0)
        {
            alert("Role Required");
        }
        else
        {
    		sendTransactionAction("SecurityAdminTran", "deleteRole", "_self");
        }
    }

    function attachImpliedRoles()
    {
        var impliedRolePKs = getSelectedImpliedRoles();

        if (impliedRolePKs != "")
        {
            f.impliedRolePKs.value = impliedRolePKs;

    		sendTransactionAction("SecurityAdminTran", "attachImpliedRoles", "_self");
        }
        else
        {
            alert("An Implied Role Must Be Selected");
        }
    }

    function detachProductStructures()
    {
        var selectedProductStructurePKs = getSelectedProductStructures();

        if (selectedProductStructurePKs == "")
        {
            alert("At least one product structure must be selected");
            return;
        }

        var message =
                "Are you sure you want to detach\n" +
                "all of the selected product structures\n" +
                "from the selected role?\n\n" +
                "(Cancel will skip the updates)\n";

        if (confirm(message))
        {

            f.selectedProductStructurePKs.value = selectedProductStructurePKs;

            sendTransactionAction(
                "SecurityAdminTran", "detachProductStructures", "_self");
        }
        else
        {
            alert("Update cancelled");
        }

    }

    function attachProductStructures()
    {
        var selectedProductStructurePKs = getSelectedProductStructures();

        if (selectedProductStructurePKs == "")
        {
            alert("At least one product structure must be selected");
            return;
        }

        f.selectedProductStructurePKs.value = selectedProductStructurePKs;

        var message =
                "When attaching the product structure(s)\n" +
                "do you want to clone the use case settings\n" +
                "from an existing product structure?\n\n" +
                "OK = Yes, let me choose and clone\n" +
                "Cancel = no, just attach the companies.\n";

        if (confirm(message))
        {
            productStructureFK = getCloneProductFK();
            // note - this is a modal dialog

            if (productStructureFK == undefined ||
                productStructureFK == null ||
                productStructureFK == 0)
            {
                alert("Clone of security cancelled");
                return;
            }
            else
            {
               f.cloneFromProductStructureFK.value = productStructureFK;
                sendTransactionAction(
                    "SecurityAdminTran", "attachProductStructures", "_self");
            }
        }
        else
        {
            f.cloneFromProductStructureFK.value = "";
            sendTransactionAction(
                "SecurityAdminTran", "attachProductStructures", "_self");
        }

    }

    function getCloneProductFK()
    {
        var theHTML =
            '<TABLE>' +
<%

            int counterForNumberOfProductStructuresOnDialog = 0;

            for (int i = 0; i < productStructureVOs.length; i++)
            {
                ProductStructureVO productStructureVO = productStructureVOs[i];

                long productStructurePK = productStructureVO.getProductStructurePK();

                 if (! setOfProductStructurePKsAttachedToSelectedRole.
                         contains(new Long(productStructurePK)))
                 {
                     continue;  // it is not attached so don't offer to clone its security
                 }

                String productStructureName =
                        productStructureVO.getBusinessContractName();

                counterForNumberOfProductStructuresOnDialog++;

%>
            '<TR ALIGN="CENTER">' +
            '   <TD>' +
            '       <INPUT TYPE="button" VALUE="<%= productStructureName %>"' +
            '           STYLE="color:white; background-color:#006600; font-weight:bolder;" ' +
            '           onclick="returnWinValue(<%= productStructurePK %>);">' +
            '   </TD>' +
            '</TR>' +

<%
            }

%>
            '<TR ALIGN="CENTER">' +
            '   <TD>' +
            '       <INPUT TYPE="button" VALUE="Cancel" ' +
            '           STYLE="color:white; background-color:#993300; font-weight:bolder;" ' +
            '           onclick="returnWinValue(0);">' +
            '   </TD>' +
            '</TR>' +
            '</TABLE>';


            var windowWidth = 350;

           // WE NEED THE HEADING AREA PLUS ROOM FOR EACH PRODUCT AND THE CANCEL BUTTON
            var windowHeight = 90 + (30 * <%= counterForNumberOfProductStructuresOnDialog + 1 %> );

           var windowFeatures = "edge=sunken;unadorned=no;help=no;" +
                "status=no;resizable=no;center=yes;dialogWidth=" + windowWidth +
                "px;dialogHeight=" + windowHeight + "px;scroll=no";


        winConfirm = window.showModalDialog(
                    "/PORTAL/security/html/securityClone.html",
                    theHTML,
                    windowFeatures);

        return winConfirm;
    }

    function buttonClicked(buttonChoice)
    {
        if (buttonChoice == 0)
        {
            f.cloneFromProductStructureFK.value = "";
            alert("Attach of Product Structure(s) cancelled");
            return;
        }
        else
        {
            f.cloneFromProductStructureFK.value = buttonChoice;
            sendTransactionAction(
                "SecurityAdminTran", "attachProductStructures", "_self");
        }
    }

    function detachImpliedRoles()
    {
        var impliedRolePKs = getSelectedImpliedRoles();

        if (impliedRolePKs != "")
        {
            f.impliedRolePKs.value = impliedRolePKs;

    		sendTransactionAction("SecurityAdminTran", "detachImpliedRoles", "_self");
        }
        else
        {
            alert("An Implied Role Must Be Selected");
        }
    }

    function getSelectedImpliedRoles()
    {
        var impliesTable = document.all.impliedRoles;

        var impliedRolePKs = "";

        for (var i = 0; i < impliedRoles.rows.length; i++)
        {
            if (impliedRoles.rows[i].isSelected == "true")
            {
                impliedRolePKs += -eval(impliedRoles.rows[i].impliedRolePK) + ",";
            }
        }

        return impliedRolePKs;
    }


    function getSelectedProductStructures()
    {
        var productStructures = document.all.productStructureTableId;

        var productStructurePKs = "";

        for (var i = 0; i < productStructures.rows.length; i++)
        {
            if (productStructures.rows[i].isSelected == "true")
            {
                productStructurePKs += eval(productStructures.rows[i].productStructurePK) + ",";
            }
        }

        if (productStructurePKs != "")
        {
           var leng = productStructurePKs.length;
           productStructurePKs = productStructurePKs.substring(0, leng - 1);
        }

        return productStructurePKs;
    }

    function saveSecuredMethods()
    {
        var componentMethodsTable = document.all.componentMethodsTable;

        var componentMethodPKs = "";

        var componentMethodYNs = "";

        for (var i = 0; i < componentMethodsTable.rows.length; i++)
        {
            if (componentMethodsTable.rows[i].isEditable == "true")
            {
                var componentMethodPK = componentMethodsTable.rows[i].componentMethodPK;

                componentMethodPKs += componentMethodPK + ",";

                if (document.getElementsByName(componentMethodPK + "_YN")[0].checked)
                {
                    componentMethodYNs += "Y,"
                }
                else
                {
                    componentMethodYNs += "N,"
                }
            }
        }

        if (componentMethodPKs != "")
        {
            f.componentMethodPKs.value = componentMethodPKs;

            f.componentMethodYNs.value = componentMethodYNs;

    		sendTransactionAction("SecurityAdminTran", "saveSecuredMethods", "_self");
        }
    }

    function markAccessToAllComponentMethods(yn)
    {
        var componentMethodsTable = document.all.componentMethodsTable;

        for (var i = 0; i < componentMethodsTable.rows.length; i++)
        {
            if (componentMethodsTable.rows[i].isEditable == "true")
            {
                var componentMethodPK = componentMethodsTable.rows[i].componentMethodPK;

                if (yn == 'Y')
                {
                    document.getElementsByName(componentMethodPK + "_YN")[0].checked = true;
                    document.getElementsByName(componentMethodPK + "_YN")[1].checked = false;
                }
                else if (yn == 'N')
                {
                    document.getElementsByName(componentMethodPK + "_YN")[0].checked = false;
                    document.getElementsByName(componentMethodPK + "_YN")[1].checked = true;
                }
            }
        }
    }

    function resetSelectedUseCase()
    {
         // zap the selected use case so none will be seleted
         f.selectedSecuredComponentCTOptions.options.selectedIndex = 0;
         f.selectedSecuredComponentCT.value = null;
    }

</script>

<title>Roles</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<style>

    <%-- for debugging tables --%>
    <%--    table {border: 10px solid yellow; padding: 10px;  }--%>
    <%--        td  {border: 5px solid blue; padding: 5px;   }--%>
    <%--        th  {border: 2px solid red; padding: 2px;  }--%>

    span.formDataSelected {
	    background-color: #FFFFCC;
	    color: Black;
    }


</style>


<!--    for debugging table layouts
        <style>
            table {border: 10px solid yellow; padding: 10px;  }
            td  {border: 5px solid blue; padding: 5px;   }
            th  {border: 2px solid red; padding: 2px;  }
        </style>
-->



</head>


<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table border="0" width="100%" height="90%">

    <tr height="5%">
        <td height="20px" align="left" colspan="2">
            <span class="<%= cssClassForRoleHeading %>" style="border-style:solid; border-width:2; position:relative; width:100%; height:25%; top:0; left:0; z-index:0; overflow:visible">
                <img src='/PORTAL/security/images/blank.gif'   width='0' height='25' >
                <b>Role <input type="text" size="20" maxsize="20" name="roleName" value="<%= roleName %>">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; and implied Roles</b>
            </span>
        </td>
<%

        String headingForProductStructures = "Products";

        String cssClassForProductStructureHeading = "formData";
        if (selectedProductStructurePKs != null && selectedProductStructurePKs.length > 0)
        {
            cssClassForProductStructureHeading = "formDataSelected";
        }

        String abbreviatedRoleName = roleName;

        if (roleName != null && roleName.length() > 0)
        {
           abbreviatedRoleName = roleName;
           if (roleName.length() > 10 )
           {
                abbreviatedRoleName = roleName.substring(0, 9) + "...";      
           }
           headingForProductStructures =  "Products for <b>" +
                   abbreviatedRoleName
                   + "</b>";
        }

        String productNameForUseCases = "";

        if (selectedProductStructureVO != null)
        {
            productNameForUseCases = selectedProductStructureVO.getBusinessContractName();

            if (productNameForUseCases.length() > 10)
            {
                productNameForUseCases =
                        productNameForUseCases.substring(0, 9) + "...";
            }
        }

        String headingForUseCases = "Use Cases";

        String cssClassForUseCasesHeading = "formData";

        if ("true".equals(showUseCaseButtons))
        {
            headingForUseCases = "Use Cases - <b>" + abbreviatedRoleName + "</b> with <b>" +
                productNameForUseCases + "</b>";

            cssClassForUseCasesHeading = "formDataSelected";
        }

%>
        
        <td height="20px" align="left" colspan="1">
            <span class="<%= cssClassForProductStructureHeading %>" style="border-style:solid; border-width:2; position:relative; width:100%; height:25%; top:0; left:0; z-index:0; overflow:visible">
                <img src='/PORTAL/security/images/blank.gif'   width='0' height='25' >
                <%= headingForProductStructures %>
            </span>
        </td>
        <td height="20px" align="left" colspan="1">
            <span class="<%= cssClassForUseCasesHeading %>" style="border-style:solid; border-width:2; position:relative; width:100%; height:25%; top:0; left:0; z-index:0; overflow:visible">
                <img src='/PORTAL/security/images/blank.gif'   width='0' height='25' >
                <%= headingForUseCases %>
            </span>
        </td>
    </tr>

    <tr height="1%">
        <td align="left" colspan="2">
            <input type="button" name="addButton" value="   Add   " onClick="addRole()">
            <input type="button" name="saveButton" value=" Save " onClick="saveRole()">
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelRole()">
            <input type="button" name="deleteButton" value="Delete" onClick="deleteRole()">
        </td>

<%--        <td align="left">--%>
<%--            &nbsp;--%>
<%--        </td>--%>

        <td align="left">
			&nbsp;
		</td>

        <td align="left">
        <span class="tableHeading">


<%
            if ("false".equals(showUseCaseButtons))
            {
%>
                <select name="selectedSecuredComponentCTOptions" disabled>
                <option name="id" value="null">Select only one attached Product struct</option>
                </select>
<%
            }
            else
            {
%>
            <select name="selectedSecuredComponentCTOptions" onChange="showSecuredMethods()">
            <option name="id" value="null">Please Select</option>
<%

                if (securedComponents != null)
                {

                    for (int i = 0; i < securedComponents.length; i++)
                    {
                        String code = securedComponents[i].getCode();

                        String codeDesc = securedComponents[i].getCodeDesc();

                        if ((companyName.equalsIgnoreCase("Security") && code.equalsIgnoreCase("SECURITY")) ||
                            (!companyName.equalsIgnoreCase("Security") && !code.equalsIgnoreCase("SECURITY")))
                        {                            
                            if ( (selectedSecuredComponentCT != null) && (selectedSecuredComponentCT.equals(code)))
                            {
                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                            }
                        }
                    }
                }
%>
            </select>
<%
            }
%>
        </span>

        </td>
    </tr>

    <tr>
        <td width="20%" NOWRAP>
            <!-- Summary Table for Roles  -->

                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

                <tr class="heading">
                    <th width="100%">
                        Role Name
                    </th>
                </tr>
                <tr>
                    <td height="99%">
                        <span class="scrollableContent">
                            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                    if (roles != null)
                    {
                        for (int i = 0; i <roles.length; i++)
                        {
                            String currentRolePK = roles[i].getRolePK() + "";

                            String currentRoleName = roles[i].getName();

                            String className = null;

                            String isSelected = null;

                            if (currentRolePK.equals(selectedRolePK))
                            {
                                className = "highlighted";

                                isSelected = "true";
                            }
                            else{

                                className = "default";

                                isSelected = "false";
                            }
%>
                            <tr class="<%= className %>"
                                id="<%= currentRolePK %>"
                                rolePK="<%= currentRolePK %>"
                                isSelected="<%= isSelected %>"
                                onMouseOver="highlightRow()"
                                onMouseOut="unhighlightRow()"
                                onClick="showImpliedRoles();">
                                <td width="100%">
                                    <%= currentRoleName %>
                                </td>
                            </tr>
<%
                        }
                    }
%>
                            <tr class="filler"> <!-- A dummy row to help with sizing -->
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                    </span>
                    </td>
                    </tr>
                </table>
        </td>

        <td width="30%" NOWRAP>
            <!-- Table for Implies  -->
                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="heading">
                        <th width="50%">
                            Implies
                        </th>
                        <th width="50%">
                            Mapping
                        </th>
                    </tr>
                    <tr>
                        <td height="99%" colspan="2">
                            <span class="scrollableContent">
                                <table id="impliedRoles" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                    if (roles != null)
                    {
                        for (int i = 0; i < roles.length; i++)
                        {
                            String currentRolePK = roles[i].getRolePK() + "";

                            if (currentRolePK.equals(selectedRolePK)) continue;

                            String currentRoleName = roles[i].getName();

                            String className = null;

                            String isSelected = "false";

                            String implication = "N/A";

                            if (roleInSetOfImpliedRoles(currentRolePK, impliedBIZRoleVOs))
                            {
                                className = "associated";

                                BIZRoleVO bizRoleVO = getAssociatedBIZRoleVO(roles[i], impliedBIZRoleVOs);

                                implication = bizRoleVO.getImplication();
                            }
                            else
                            {
                                className = "default";
                            }
%>
                            <tr class="<%= className %>" impliedRolePK="<%= -Long.parseLong(currentRolePK) %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="multiSelectDeselectRow()">
                                <td align="left" width="50%">
                                    <%= currentRoleName %>
                                </td>
                                <td align="left" width="50%">
                                    <%= implication %>
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
        </td>

        <td width="20%" NOWRAP>

                <!-- Table for Prorduct Structures  -->
                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="heading">
                        <th width="50%">
                            Product Structures
                        </th>
                    </tr>
                    <tr>
                        <td height="99%">
                            <span class="scrollableContent">
                                <table id="productStructureTableId" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                    // BUILD THE COLUMN SHOWING THE PRODUCT STRUCTURES
                    if (productStructureVOs != null)
                    {
                        for (int i = 0; i < productStructureVOs.length; i++)
                        {
                            long currentProductStructurePK = productStructureVOs[i].getProductStructurePK();

                            boolean isProductStructureAttached = false;

                            if (setOfProductStructurePKsAttachedToSelectedRole.contains(
                                    new Long(currentProductStructurePK)))
                            {
                                isProductStructureAttached = true;
                            }

                            String className = "default";
                            boolean isSelected = false;

                            // is it selected?
                            for (int j = 0; j < selectedProductStructurePKs.length; j++)
                            {
                                long productStructurePK = selectedProductStructurePKs[j];
                                if (currentProductStructurePK == productStructurePK)
                                {
                                    isSelected = true;
                                    className = "highlighted";
                                    break;
                                }
                            }
%>

                            <tr class="<%= className %>"
                                productStructurePK="<%= currentProductStructurePK %>"
                                isSelected="<%= isSelected %>"
                                onClick="multiSelectDeselectRow(); selectProductStructure(<%= currentProductStructurePK %>)" >
                                <td align="left" width="90%">
                                    <%= Util.getProductStructure(productStructureVOs[i], ",") %>
                                </td>
                                <td align="right">
<%
                                    if (isProductStructureAttached)
                                    {
                                        out.println(
                                        "<img src='/PORTAL/security/images/keygold.gif'  " +
                                                " width='59' height='22' " +
                                                " alt='attached' >");
                                    }
                                    else
                                    {
                                        out.println(
                                        "<img src='/PORTAL/security/images/blank.gif'  " +
                                                " width='59' height='22' >");
                                    }
%>
                                </td>
                            </tr>
<%
                        }
                    }
%>
                            <tr class="filler"> <!-- A dummy row to help with sizing -->
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                    </span>
                    </td>
                    </tr>
                </table>
        </td>

        <td width="30%" NOWRAP>

                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="heading">
                        <th width="5%">
                            Y
                        </th>
                        <th width="5%">
                            N
                        </th>
                        <th width="90%">
                            Use Case
                        </th>
                    </tr>
                    <tr>
                       <td height="99%" colspan="3">
                            <span class="scrollableContent">
                                <table id="componentMethodsTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">



                            <%-- <span class="scrollableContent">--%>


<%

                    if (bizComponentMethodVOs != null)
                    {
                        bizComponentMethodVOs = sortComponentMethods(bizComponentMethodVOs);


                        for (int i = 0; i < bizComponentMethodVOs.length; i++)
                        {

                            ComponentMethodVO currentComponentMethodVO = bizComponentMethodVOs[i].getComponentMethodVO();

                            long componentMethodPK = currentComponentMethodVO.getComponentMethodPK();

                            String methodName = currentComponentMethodVO.getMethodName();

                            if (methodName.equals("<init>"))
                            {
                                methodName = "Constructor";
                            }

                            boolean isEditable = bizComponentMethodVOs[i].getIsEditable();

                            boolean isAuthorized = bizComponentMethodVOs[i].getIsAuthorized();
%>
                            <tr class="default" id="<%= componentMethodPK %>" componentMethodPK="<%= componentMethodPK %>" isEditable="<%= isEditable %>" isAuthorized="<%= isAuthorized %>">
                                <td width="5%" >
                                    <input type="radio" name="<%= componentMethodPK %>_YN" <%= (isAuthorized)?"CHECKED":"" %> <%= (!isEditable)?"disabled":"" %> >
                                </td>
                                <td width="5%" >
                                    <input type="radio" name="<%= componentMethodPK %>_YN" <%= (! isAuthorized)?"CHECKED":"" %> <%= (!isEditable)?"disabled":"" %> >
                                </td>
                                <td width="90%" >
                                    <%= methodName %> <font face="" size="1" color="blue"><%= (! isEditable)?"(implied)":"" %></font>
                                </td>
                            </tr>
<%
                        }
                    }

%>
                                <tr class="filler"> <!-- A dummy row to help with sizing -->
                                    <td >
                                        &nbsp;
                                    </td>
                                    <td >
                                        &nbsp;
                                    </td>
                                    <td >
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
        <td>&nbsp;</td>

        <td>
            <table>
                <tr align="left">
                    <td>
                        <input type="button" name="attachButtonImplied"
                                value="Attach Implied Roles" onClick="attachImpliedRoles()">
                    </td>
                </tr>
                <tr align="left">
                    <td>
                        <input type="button" name="detachButtonImplied"
                                value="Detach Implied Roles" onClick="detachImpliedRoles()">
                    </td>
                </tr>
            </table>
        </td>
        <td>
		    <table>
                <tr align="left">
                    <td>
                        <input type="button" name="attachButtonProductStructures"
                                value="Attach Prod Strucs" onClick="attachProductStructures()">
                    </td>
                </tr>
                <tr align="left">
                    <td>
                        <input type="button" name="detachButtonProductStructures"
                                value="Detach Prod Strucs" onClick="detachProductStructures()">
                    </td>
                </tr>
		    </table>
		</td>

        <td align="left">

            <table width="100%">
                <tr>
                    <td align="left">
                        <input type="button" name="checkAllYButton" value="&#8730; All 'Y'"  onClick="markAccessToAllComponentMethods('Y')">
                        <input type="button" name="checkAllNButton" value="&#8730; All 'N'"  onClick="markAccessToAllComponentMethods('N')">
                    </td>
                    <td align="right">
                        <input type="button" name="saveSecuredMethodsButton" value=" Save " onClick="saveSecuredMethods()">
                        <input type="button" name="cancelSecuredMethodsButton"  value="Cancel" onClick="cancelRole()">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">
<input type="hidden" name="pageMode" value="<%= pageMode %>">
<input type="hidden" name="impliedRolePKs" value="">
<input type="hidden" name="componentMethodPKs" value="">
<input type="hidden" name="componentMethodYNs" value="">
<input type="hidden" name="selectedRolePK" value="<%= selectedRolePK %>">
<input type="hidden" name="selectedProductStructurePK" value="<%= selectedProductStructurePK %>">
<input type="hidden" name="selectedProductStructurePKs" value="<%= selectedProductStructurePKsStr %>">
<input type="hidden" name="selectedSecuredComponentCT" value="<%= selectedSecuredComponentCT %>">
<input type="hidden" name="cloneFromProductStructureFK" value="">
<input type="hidden" name="showUseCaseButtons" value="<%= showUseCaseButtons %>">
</form>

</body>
</html>
