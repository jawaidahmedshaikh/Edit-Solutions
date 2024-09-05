<!-- editingExceptionDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.*,
                 edit.portal.exceptions.*" %>

<%

    PortalEditingException editingException = (PortalEditingException) request.getAttribute("portalEditingException");
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String transaction  = initValue(editingException.getTransaction());
    String action       = initValue(editingException.getAction());
%>
<%!
    private String initValue(String value){

        return (value == null ? "" : value);
    }
%>

<html>
<head>

<script language="Javascript1.2">

    var transaction = "<%= transaction %>";
    var action      = "<%= action %>";

    function closeWindow(){

        window.close();
    }

    function continueTransactionAction(){

        opener.continueTransactionAction(transaction, action);
        closeWindow();
    }

</script>

<title>Editing Exception</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<!-- ****** STYLE CODE ***** //-->
<style>

	.highLighted {

		background-color: #FFFFCC;
	}

	.dummy {
	}

	table {

		background-color: #DCDCDC;
	}
</style>
</head>

<body bgcolor="#DDDDDD">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">


<span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

        <table width="100%" border="0" cellspacing="0" cellpadding="4">
           <tr bgcolor="#FFFF6B" height="10">
              <th align="left"><font color= "#000000">&nbsp;</font></th>
              <th align="left"><font color= "#000000">Instruction Name</font></th>
              <th align="left"><font color= "#000000">Alias Name</font></th>
              <th align="left"><font color= "#000000">Stack</font></th>
			  <th align="left"><font color= "#000000">Severity</font></th>
		   </tr>
		<%
				String trClass = "";
                boolean hardErrorExists = false;

                ValidationVO[] validationVOs = editingException.getValidationVOs();

                String instructionName  = "";
                String messsage        = "";
                String stack            = "";
                String severity         = "";

                if (validationVOs != null) {

                    for (int i = 0; i < validationVOs.length; i++) {

                        instructionName  = validationVOs[i].getInstructionName();
                        messsage        = validationVOs[i].getMessage();
                        stack            = validationVOs[i].getStack();
                        severity         = validationVOs[i].getSeverity();

                        if (severity.toUpperCase().trim().equalsIgnoreCase("H")){

                            hardErrorExists = true;
                        }

//                        if ( (i % 2) == 0) {
//
//                            trClass = "highLighted";
//                        }
//                        else {
//
//                            trClass = "dummy";
//                        }
                %>
                    <tr class="dummy">
                        <td nowrap>
                           #<%= i + 1 %>&nbsp;&nbsp;
                        </td>
                        <td nowrap>
                           <%= initValue(instructionName) %>
                        </td>
                        <td nowrap>
                            <%= initValue(messsage) %>
                        </td>
                        <td nowrap>
                            <%= initValue(stack) %>
                        </td>
                        <td nowrap>
                            <%= initValue(severity) %>
                        </td>
                    </tr>
            <%
                    }// end for
                }// end if
		%>

        <tr>

                <td bgcolor="#FFFF6B" colspan="7">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="7" align="right">
            <% if (!hardErrorExists) { %>
                <input type="button" value="Continue?" onClick="continueTransactionAction()">
            <% } %>
                <input type="button" value="Close" onClick="closeWindow()">
            </td>
        </tr>


		</table>
</span>

 <!-- ****** HIDDEN FIELDS ***** //-->


</form>
</body>
</html>