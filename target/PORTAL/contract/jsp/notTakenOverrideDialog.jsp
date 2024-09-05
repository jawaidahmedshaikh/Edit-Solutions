<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="fission.utility.Util" %>

<%
    String quoteMonth = Util.initString((String) request.getAttribute("quoteMonth"), "");
    String quoteDay = Util.initString((String) request.getAttribute("quoteDay"), "");
    String quoteYear = Util.initString((String) request.getAttribute("quoteYear"), "");
    
    String quoteType = (String) request.getAttribute("quoteType");
%>

<html>
  <head>
    <title>Not Taken Override Dialog</title>
    
    <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
    
    <!-- ***** JAVASCRIPT *****  -->

    <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
    
    <script type="text/javascript" language="Javascript1.2">
    
        var f = null;
        
        function init()
        {
            f = document.notTakenOverrideForm;
        }
        
        function saveNotTakenOverrideIndicator()
        {
            if (f.notTakenOverrideIndCheckBox.checked == true) 
            {
                f.notTakenOverrideInd.value = "Y";
            }
            else
            {
                f.notTakenOverrideInd.value = "N";
            }
        
            sendTransactionAction("ContractDetailTran", "saveNotTakenOverrideIndicator", "inforceQuoteDialog");
            window.close();
        }
    
    </script>
  </head>
  <body class="mainTheme" onLoad="init()">
    <form  name="notTakenOverrideForm" method="post" action="/PORTAL/servlet/RequestManager">
      <span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
        <table width="50%" border="0" cellspacing="0" cellpadding="4">
          <tr>
            <td align="right" nowrap>NotTaken Override Indicator:&nbsp;</td>
            <td align="left">
              <input type="checkbox" name="notTakenOverrideIndCheckBox">
            </td>
          </tr>
        </table>        
        <table width="100%" border="0" cellspacing="0" cellpadding="4">
          <tr>
            <td align="right">
              <input type="button" name="Ssubmit" value="  Save  " onClick="saveNotTakenOverrideIndicator()">
              <input type="button" name="cancel" value=" Cancel " onClick="javascript:window.close()">
            </td>
          </tr>
        </table>
      </span>  

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="quoteMonth" value="<%= quoteMonth %>">
<input type="hidden" name="quoteDay" value="<%= quoteDay %>">
<input type="hidden" name="quoteYear" value="<%= quoteYear %>">
<input type="hidden" name="quoteType" value="<%= quoteType %>">        

<input type="hidden" name="notTakenOverrideInd">

    </form>  
  </body>
</html>