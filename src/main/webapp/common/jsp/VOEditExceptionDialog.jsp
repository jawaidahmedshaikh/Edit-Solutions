<!-- VOEditExceptionDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.*,
                 edit.common.exceptions.*" %>

<%

    VOEditException voEditException = (VOEditException) request.getAttribute("VOEditException");
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String transaction  = initValue(voEditException.getTransaction());
    String action       = initValue(voEditException.getAction());
%>
<%!
    private String initValue(String value){

        if (value != null){

            return value;
        }
        else {

            return "";
        }
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
              <th align="left"><font color= "#000000">Message</font></th>

		   </tr>
		<%

                String[] voEditErrors = voEditException.getVOErrors();

                if (voEditErrors != null) {

                    for (int i = 0; i < voEditErrors.length; i++){

                        String errorMessage     = voEditErrors[i];

                %>
                    <tr class="dummy">
                        <td nowrap>
                           #<%= i + 1 %>&nbsp;&nbsp;
                        </td>
                        <td nowrap>
                            <%= initValue(errorMessage) %>
                        </td>
                    </tr>
            <%
                    }// end for
                }// end if
		%>


        <tr>
            <td colspan="7" align="right">
                <input type="button" value="Close" onClick="closeWindow()">
            </td>
        </tr>


		</table>
</span>

 <!-- ****** HIDDEN FIELDS ***** //-->


</form>
</body>
</html>