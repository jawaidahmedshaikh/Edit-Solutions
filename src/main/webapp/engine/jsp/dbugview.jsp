 <!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="fission.beans.*,
                 edit.portal.exceptions.*,
                 java.util.*,
                 edit.common.vo.UISearchClientContractVO,
                 edit.common.vo.UISearchClientVO,
                 edit.common.EDITBigDecimal" %>

<jsp:useBean id="paramBean"
	class="fission.beans.SessionBean" scope="session" />

<%-- <jsp:useBean id="pageBean"
    class="engine.beans.PageBean" scope="request"/> --%>


<%
	PageBean pageBean = paramBean.getPageBean("debugScriptBean");

	int viewerMode = 0;

	if(pageBean.getValue("viewerMode") != null && pageBean.getValue("viewerMode") != "")
		viewerMode = Integer.parseInt(pageBean.getValue("viewerMode"));

	String[] dataStackValues = pageBean.getValues("dataStackValues");

	int dataStackLength = (dataStackValues.length)-1;

    String[] workingStorageKeys = null;
    String[] workingStorageValues = null;

    Map workingStorage = (Map) session.getAttribute("workingStorage");

    if (workingStorage != null)
    {
        Map sortedWorkingStorageKeys = sortWorkingStorageKeys(workingStorage);

        Iterator it = sortedWorkingStorageKeys.values().iterator();

        List wsKeys = new ArrayList();
        List wsValues = new ArrayList();

        while (it.hasNext())
        {
            String currentKey = (String) it.next();
            String currentValue = (String) workingStorage.get(currentKey);

            wsKeys.add(currentKey);
            wsValues.add(currentValue);
        }

        workingStorageKeys = (String[]) wsKeys.toArray(new String[wsKeys.size()]);
        workingStorageValues = (String[]) wsValues.toArray(new String[wsValues.size()]);
    }
    else
    {
        workingStorageKeys = new String[0];
        workingStorageValues = new String[0];
    }

    int workingStorageLength = (workingStorageValues.length)-1;

	String[] functionTables = pageBean.getValues("functionTables");

	String[] functionEntries = null;
	String dataLength = dataStackValues.length+"";
	String dataLengthLast = ((dataStackValues.length)-1)+"";
	// check for null to show values only when function got selected;

	if((request.getParameter("function") != null) && (request.getParameter("function").length() > 0))
		functionEntries = pageBean.getValues("functionEntries");

    String[] vectorTables = pageBean.getValues("vectorTable");

    String[] vectorEntries = null;

    if((request.getParameter("vector") != null) && (request.getParameter("vector").length() > 0))
        vectorEntries = pageBean.getValues("vectorEntries");

	String[] outputData = pageBean.getValues("outputData");
	Map<String, String> documents = (Map<String, String>) session.getAttribute("documents");

	String instPtr = pageBean.getValue("instPtr");

	String lastInstPtr = pageBean.getValue("lastInstPtr");

	String currentRow = pageBean.getValue("currentRow");

	PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
      String editingExceptionExists = "false";
      if (editingException != null) {

		editingExceptionExists = "true";
      }
%>

<%!
	private TreeMap sortWorkingStorageKeys(Map workingStorageHT)
    {
		TreeMap sortedWorkingStorageKeys = new TreeMap();

        Iterator wsKeys = workingStorageHT.keySet().iterator();

        while (wsKeys.hasNext())
        {
            String key = (String) wsKeys.next();

            sortedWorkingStorageKeys.put(key, key);
		}

		return sortedWorkingStorageKeys;
	}
%>

<html>
<head>
<title>Analyzer Viewer</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">

<!-- ****** Java Script CODE ***** //-->
<script language="JavaScript">

	var f = null;
	var editingExceptionExists = "<%= editingExceptionExists %>";

	function init()	{

		f = document.viewerForm;
            checkForEditingException();
		selectRow('<%= instPtr %>','<%= lastInstPtr %>','<%= currentRow %>');

		selectDataStackRow('<%= dataStackLength %>','0','<%= dataStackLength %>');

		selectWorkingStorageRow('<%= workingStorageLength %>','0','<%= workingStorageLength %>');
		//scrollBehavior();
	}

	function selectRow (currInst, lastInst, currRow) {

    	var dbugscrp = parent.script.selectRow;
    	var dbugscrpdoc = parent.script;
    	if (dbugscrp != null) {
	        dbugscrpdoc.selectRow(currInst, lastInst, currRow);
    	}
	}

	function selectDataStackRow (currInst, lastInst, currRow) {

		currInstTD = document.getElementById("ln" + currInst);
    	lastInstTD = document.getElementById("ln" + lastInst);
    	currRowTD  = document.getElementById("ln" + currRow);
    	if (currInstTD != null) {
        	currInstTD.style.backgroundColor="#FFFFFF";//#D3D3D3
    	}
    	if ((lastInstTD != null) && (currInst > 0)) {
			//alert(lastInstTD.id);
			//alert(lastInstTD.style.backgroundColor);
        	lastInstTD.style.backgroundColor="#FFFFFF";
			//alert(lastInstTD.style.backgroundColor);

    	}
    	if (currRowTD != null) {
        	currRowTD.scrollIntoView(false);
    	}
	}

	function selectWorkingStorageRow (currInst, lastInst, currRow) {

		currInstTD = document.getElementById("lw" + currInst);
    	lastInstTD = document.getElementById("lw" + lastInst);
    	currRowTD  = document.getElementById("lw" + currRow);
    	if (currInstTD != null) {
        	currInstTD.style.backgroundColor="#FFFFFF";//#D3D3D3
    	}
    	if ((lastInstTD != null) && (currInst > 0)) {
			//alert(lastInstTD.id);
			//alert(lastInstTD.style.backgroundColor);
        	lastInstTD.style.backgroundColor="#FFFFFF";
			//alert(lastInstTD.style.backgroundColor);

    	}
    	if (currRowTD != null) {
        	currRowTD.scrollIntoView(false);
    	}
	}

	function showFunctionViewer() {

		sendTransactionAction("ScriptTran","showFunctionViewer","viewer");
	}

    function showVectorViewer() {

        sendTransactionAction("ScriptTran","showVectorViewer","viewer");
    }

      function checkForEditingException() {

          if (editingExceptionExists == "true") {

	        openDialog("", "exceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3,"ScriptTran", "showEditingExceptionDialog", "exceptionDialog");
          }
      }

      function openDialog(theURL, winName, features, transaction, action, target) {

          dialog = window.open(theURL, winName, features);

          sendTransactionAction(transaction, action, target);
      }

      function continueTransactionAction(transaction, action) {

          f.ignoreEditWarnings.value = "true";

          if (action == "debugStep") {

              sendTransactionAction(transaction, action, "viewer");
          }
          else {

              sendTransactionAction(transaction, action, "_parent");
          }
      }

	function sendTransactionAction(transaction, action, target)	{

		f.transaction.value = transaction;
		f.action.value = action;
		f.target = target;
		f.submit();
	}


//	function scrollBehavior()
//	{
//		//alert("page down1");
//		f.workingStorage.doScroll("down");
//		f.dataStack.doScroll("down");
//		//alert("page down2");
//	}

</script>
</head>


<!-- ****** HTML CODE ***** //-->
<body style="margin-top:0;" onload="init()" bgColor="#99BBBB">
<form method="post" action="/PORTAL/servlet/RequestManager" name="viewerForm">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="2" >

<% 	if (viewerMode == 0) { %>
	<tr>
		<td>
		  DataStack
		</td>
	</tr>
    <tr>
		<td height="50%">

			<div style="border-color:black; border-style:solid; border-width:1; background-color:white; position:relative; height:100%; top:0; left:0; overflow:scroll">

	   			<table>

		   			<% if((dataStackValues != null) && (dataStackValues.length > 0)) {

	   					for(int i=0; i<dataStackValues.length; i++) { %>


		  			<tr align="left" width="90%" nowrap id="ln<%= i %>" >
			    		<td>
		   					<%= dataStackValues[i] %>
		   			<% } } %>
			  			</td>
          			</tr>
				</table>
    	    </div>
		</td>

	</tr>
	<tr>
		<td>
		  WorkingStorage
		</td>
	</tr>
    <tr>
		<td height="50%">

			<div style="border-color:black; border-style:solid; border-width:1;  background-color: white; position:relative; height:100%; top:0; left:0; overflow:scroll">

	   			<table>

		   			<% if((workingStorageValues != null) && (workingStorageValues.length > 0)) {

	   					for(int i=0; i<workingStorageValues.length; i++) { %>


		  			<tr align="left" width="90%" nowrap id="lw<%= i %>" >
			    		<td>
		   					<%= workingStorageKeys[i] %>: <%= workingStorageValues[i] %>
		   			<% } } %>
			  			</td>
          			</tr>
				</table>
    	    </div>
		</td>

	</tr>
<% } else if (viewerMode == 1) { %>
    <tr>
      <td height="100%" title="Select a Rate table to display">
          Tables &nbsp;
          <select name="function"
              onChange="showFunctionViewer();">
          <option value="">Please Select</option>
			<% if((functionTables != null) && (functionTables.length > 0)) { %>
				<% for(int i=0; i<functionTables.length; i++) { %>
					<option value="<%= functionTables[i] %>"
						<% if(functionTables[i].equals(request.getParameter("function"))) { %>
							selected
						<% } %>><%= functionTables[i] %>
           			</option>
				<% } %>
			<% } %>
          </select><br>
          <table border="0" cellspacing="0" cellpadding="0"
                title="Rate table data">
            <th class="dataHeading" width="75">
                Index
            </th>
            <th class="dataHeading2" width="185">
                Rate
            </th>
          </table>
		  <textarea name="functionTable" cols="30" rows="16" READONLY wrap="off"
		  ><% if ((functionEntries != null) && (functionEntries.length > 0))  {
		  		int j;
				for(int i=0; i<functionEntries.length; i++) {
					j = i + 1;
					//out.print("   " + fission.utility.Util.formatDecimal("#00", j) + "    ");
                    out.print("   " + fission.utility.Util.formatDecimal("#00", new EDITBigDecimal(String.valueOf(j))) + "    ");
					out.println(fission.utility.Util.formatDecimal("##0.00######",new EDITBigDecimal(functionEntries[i])));
		   		}
			} %></textarea>
      </td>
    </tr>
<% } else if (viewerMode == 2) { %>
    <tr>
      <td height="100%" title="Select a Vector Table to display">
          Vectors &nbsp;
          <select name="vector"
              onChange="showVectorViewer();">
          <option value="">Please Select</option>
			<% if((vectorTables != null) && (vectorTables.length > 0)) { %>
				<% for(int i=0; i<vectorTables.length; i++) { %>
					<option value="<%= vectorTables[i] %>"
						<% if(vectorTables[i].equals(request.getParameter("vector"))) { %>
							selected
						<% } %>><%= vectorTables[i] %>
           			</option>
				<% } %>
			<% } %>
          </select><br>
          <table border="0" cellspacing="0" cellpadding="0"
                title="Vector table data">
            <th class="dataHeading" width="75">
                Index
            </th>
            <th class="dataHeading2" width="185">
                Rate
            </th>
          </table>
		  <textarea name="vectorTable" cols="30" rows="16" READONLY wrap="off"
		  ><% if ((vectorEntries != null) && (vectorEntries.length > 0))  {
		  		int j;
				for(int i=0; i<vectorEntries.length; i++) {
					j = i + 1;
//					out.print("   " + fission.utility.Util.formatDecimal("#00", j) + "    ");
//					out.println(fission.utility.Util.formatDecimal("##0.00######",Double.parseDouble(vectorEntries[i])));

					out.print("   " + fission.utility.Util.formatDecimal("#00", new EDITBigDecimal(j+"")) + "    ");
					out.println(fission.utility.Util.formatDecimal("##0.00######",new EDITBigDecimal(vectorEntries[i])));

		   		}
			} %></textarea>
      </td>
    </tr>
<% } else if (viewerMode == 3) { %>
    <tr>
      <td height="100%">
          Output<br>
          <textarea name="output"  READONLY cols="30" rows="18" wrap="off"
          title="Output data to be used in Illustration"
		  ><% if((outputData != null) && (outputData.length > 0)) {
				for(int i=0; i<outputData.length; i++) {
					out.println(outputData[i]);
				}
			} %></textarea>
      </td>
    </tr>
<% } else if(viewerMode == 4) { %>
	<% if(documents != null) {
		for(String docName : documents.keySet()) {
			out.println("<tr><td>" + docName + "</td>");
			out.println("<tr><td><div class=\"code-container\"><pre>" + 
				documents.get(docName).replace("<", "&lt;").replace(">", "&gt;") + 
				"</pre></div></td></tr>");
		}
	}%>
	</tr>
<% } %>
</table>

<!-- ****** Hidden Fields ***** //-->
<input id="transaction" type="hidden" name="transaction" value="">
<input id="action"      type="hidden" name="action" value="">
<input id="scriptId"    type="hidden" name="scriptId"
  value="<%-- <jsp:getProperty name="aScriptProcBean" property="scriptName"/> --%>">
<input type="hidden" name="ignoreEditWarnings" value="">
</form>
</body>
</html>