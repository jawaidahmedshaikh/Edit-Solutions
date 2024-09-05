<!-- ****** JAVA CODE ***** //-->


<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="availableRidersBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="paramBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	String[] riders   = availableRidersBean.getValues("riders");
	if (riders == null) {riders = new String[0];}

	String[] riderIds = availableRidersBean.getValues("riderIds");
	if (riderIds == null) {riderIds = new String[0];}

	String[] selectedRiders = paramBean.getValues("selectedRiders");
	if (selectedRiders == null) {selectedRiders = new String[0];}

	String[] selectedRiderIds = paramBean.getValues("selectedRiderIds");
	if (selectedRiderIds == null) {selectedRiderIds = new String[0];}
%>

<html>
<head>
<title>ENGINE - Parameter Entry Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/ENGINE.css" rel="stylesheet" type="text/css">


<!-- ****** JAVAScript CODE ***** //-->

<script language="JavaScript">

	var f = null;

	function getMode() {

		return "Rider";
	}

	function showBaseParametersScreen() {

		f.mode.value = "Base Parameters";

		sendTransactionAction("ParamTran","showBaseParametersScreen", "main")
	}

	function showRiderSelectionScreen() {

		sendTransactionAction("ParamTran", "showRiderSelectionScreen", "main");
	}

	function addRider() {

		if (f.riderId.selectedIndex > -1) {

			f.rider.value = f.riderId.options[f.riderId.selectedIndex].text;

            f.financialType.value = f.riderId.options[f.riderId.selectedIndex].value;

			sendTransactionAction("ParamTran", "addRider", "main");
		}
	}

	function removeRider() {

		if (f.selectedRiderId.selectedIndex > -1) {

			f.rider.value = f.selectedRiderId.options[f.selectedRiderId.selectedIndex].text;

            f.financialType.value = f.selectedRiderId.options[f.selectedRiderId.selectedIndex].value;

			f.selectedIndex.value = f.selectedRiderId.selectedIndex;

			sendTransactionAction("ParamTran", "removeRider", "main");
		}
	}

	function editRider() {

		if ((f.selectedRiderId.selectedIndex > -1) &&
			(f.selectedRiderId.options[f.selectedRiderId.selectedIndex].text != 'No Riders Selected')) {

			f.rider.value = f.selectedRiderId.options[f.selectedRiderId.selectedIndex].text;

            f.financialType.value = f.selectedRiderId.options[f.selectedRiderId.selectedIndex].value;

			f.selectedIndex.value = f.selectedRiderId.selectedIndex;

			f.mode.value = "Rider";

			sendTransactionAction("ParamTran", "editRider", "main");
		}
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;
		f.target = target;

		f.submit();
	}

	function init() {

		f = document.riderSelectionForm;
	}

</script>

</head>


<!-- ****** HTML CODE ***** //-->

<body style="margin-top:5;margin-left:10" onLoad="init()" bgColor="#99BBBB">
<form method="post" name="riderSelectionForm" action="/PORTAL/servlet/RequestManager">

<div align="center">
<table  border="0" cellspacing="2" cellpadding="2">
    <tr>
        <td class="heading" colspan="7">
            Parameter Entry - Riders
        </td>
    </tr>
    <tr>
        <td width="20%" nowrap align="center">
            <a href="javascript:showBaseParametersScreen()"
            ><u>Base Params</u>
            </a>
        </td>
        <td>
        </td>
        <td width="20%" align="center">
            <a href="javascript:showRiderSelectionScreen()">
			<u>Riders</u>
            </a>
        </td>
        <td>
        </td>
        <td width="20%" align="center" title="Under Construction">
            <u>Lump Sum</u>
        </td>
        <td>
        </td>
        <td width="20%" align="center" title="Under Construction">
            <u>Income</u>
        </td>
    </tr>
</table>
</div>

<hr size="1">

<table border="0" align="center" cellspacing="1" cellpadding="1">
<tr>
    <td>
        Available Riders<br>
        <select name="riderId" size="10">
        	<%
         		for(int i = 0; i < riderIds.length; i++) {

        			out.println("<option name=\"id\" value=\"" + riderIds[i] + "\">" + riders[i] + "</option>");
        		}
        	%>
    	</select>
    </td>
    <td width="5%">
    </td>
    <td>
        <input type="button" name="add"  value="     Add >>    "
            title="Add Riders"
            onClick="addRider()"
        >
        <br><br>
        <input type="button" name="remove"  value="<< Remove"
            title="Remove Riders"
            onClick="removeRider()"
        >
    </td>
    <td width="5%">
    </td>
    <td>
        Selected Riders<br>
        <select name="selectedRiderId" size="10">

        <%
			if (selectedRiderIds.length == 0) {

				out.println("<option> No Riders Selected </option>");
			}
			else {
	        	for(int i = 0; i < selectedRiderIds.length; i++) {

	            	out.println("<option name=\"id\" value=\"" + selectedRiderIds[i] + "\">" + selectedRiders[i] + "</option>");
				}
			}
        %>
        </select>
    </td>
</tr>
</table>

<br>
<table align="right">
    <tr>
       	<td class="buttons" colspan="4">
            <input type="button" name="edit"  value="  Edit  "
           	     title="Edit Selected Rider"
                 onClick="editRider()"
            >
        </td>
    </tr>

</table>


<!-- ****** HIDDEN FIELDS ***** //-->

<input type="hidden" name="transaction"   value="">
<input type="hidden" name="action"        value="">
<input type="hidden" name="mode"          value="">
<input type="hidden" name="rider" 		  value="">
<input type="hidden" name="financialType" value="">
<input type="hidden" name="selectedIndex" value="">
</form>
</body>
</html>
