<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 edit.common.CodeTableWrapper,
                 java.util.StringTokenizer,
                 edit.common.EDITDate"%>

<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    // User selected items...
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] trxTypeCTs = codeTableWrapper.getTRXCODE_CodeTableEntries();
    CodeTableVO[] yesNoCTs   = codeTableWrapper.getCodeTableEntries("YESNO");
    CodeTableVO[] bonusChargebackCTs = codeTableWrapper.getCodeTableEntries("BONUSCHARGEBACK");
    

    // Summary items
    TransactionPriorityVO[] uiTransactionPriorityVOs = (TransactionPriorityVO[]) request.getAttribute("uiTransactionPriorityVOs");

    if (uiTransactionPriorityVOs == null)
    {
        uiTransactionPriorityVOs = new TransactionPriorityVO[0];
    }

    String transactionPriorityPK = "";
    String transactionTypePK = "";
    String effectiveMonth = "";
    String effectiveDay   = "";
    String effectiveYear  = "";
    String priority = "";
    String confirmPK = "";
    String commissionablePK = "";
    String reinsurablePK = "";
    String bonusChargebackPK = "";
    boolean correspondenceInd = false;

    // Selected Transaction Priority
    TransactionPriorityVO transactionPriorityVO = (TransactionPriorityVO) request.getAttribute("transactionPriorityVO");
    if (transactionPriorityVO != null)
    {
        if (transactionPriorityVO.getEffectiveDate() != null)
        {
            String[] effectiveTokens = DateTimeUtil.initDate(transactionPriorityVO.getEffectiveDate());
            effectiveMonth = effectiveTokens[0];
            effectiveDay = effectiveTokens[1];
            effectiveYear = effectiveTokens[2];
        }

        transactionPriorityPK = Util.initString(transactionPriorityVO.getTransactionPriorityPK() + "", "");
        priority = Util.initString(transactionPriorityVO.getPriority() + "", "");

        String transactionTypeCT = transactionPriorityVO.getTransactionTypeCT();
        String confirmCT         = transactionPriorityVO.getConfirmEventInd();
        String commissionableCT  = transactionPriorityVO.getCommissionableEventInd();
        String reinsurableCT = transactionPriorityVO.getReinsuranceInd();
        String bonusChargebackCT = transactionPriorityVO.getBonusChargebackCT();
        
        if (transactionTypeCT != null)
        {
            transactionTypePK = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("TRXTYPE", transactionTypeCT) + "";
        }

        if (confirmCT != null)
        {
            confirmPK         = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("YESNO", confirmCT) + "";
        }

        if (commissionableCT != null)
        {
            commissionablePK  = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("YESNO", commissionableCT) + "";
        }

        if (reinsurableCT != null)
        {
            reinsurablePK  = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("YESNO", reinsurableCT) + "";
        }
        
        if (bonusChargebackCT != null)
        {
          bonusChargebackPK = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("BONUSCHARGEBACK", bonusChargebackCT) + ""; 
        }

        if (transactionPriorityVO.getTransactionCorrespondenceVOCount() > 0)
        {
            correspondenceInd = true;   // checked, there are correspondences for this transaction priority
        }
    }

    String message = (String) request.getAttribute("message");
    message = Util.initString(message, "");

    String pageMode = (String) request.getAttribute("pageMode");
    pageMode = Util.initString(pageMode, "VIEW");
%>

<html>
 
<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f           = null;
    var previousRow = null;
    var tdElement = null;
    var currentRow = null;

    var colorMouseOver   = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    var message = "<%= message %>";

    var pageMode = "<%= pageMode %>";

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (className == "highLighted") {

            currentRow.style.backgroundColor = colorHighlighted;
        }

        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }

    function init()
    {
        f = document.theForm;

        if (message.length > 0)
        {
            alert(message);
        }

        initForPageMode(pageMode);

        var scrollToRow = document.getElementById("<%= transactionPriorityPK %>");
        if (scrollToRow != null)
        {
            scrollToRow.scrollIntoView(false);
        }

        f.correspondenceInd.checked = <%= correspondenceInd %>;
    }

    function initForPageMode(pageMode)
    {
        if (pageMode == "VIEW")
        {
            disableDetails(false);

            f.btnAdd.disabled = false;
            f.btnSave.disabled = true;
            f.btnCancel.disabled = false;
            f.btnDelete.disabled = true;
        }
        else if (pageMode == "ADD")
        {
            disableDetails(false);

            f.btnAdd.disabled = false;
            f.btnSave.disabled = false;
            f.btnCancel.disabled = false;
            f.btnDelete.disabled = false;
        }
    }

    function disableDetails(shouldDisable)
    {
        if (shouldDisable)
        {
            for (var i = 0; i < f.elements.length; i++)
            {
                var elementType = f.elements[i].type;

                if ( (elementType == "text" || elementType == "button" || elementType == "select-one") && (f.elements[i].id.indexOf("btn") < 0))
                {
                    f.elements[i].disabled = true;
                }
            }
        }
    }

    function showCorrespondenceDialog()
    {
        openDialog("","transactionCorrespondenceDialog","top=0,left=0,resizable=no,width=" + .50 * screen.width + ",height=" + .32 *screen.height,"EventAdminTran", "showTransactionCorrespondenceDialog");
	}

    <%-- If correspondences were added in the correspondence dialog, update the check box--%>
    function setCorrespondenceCheck(shouldCheck)
    {
        if (shouldCheck)
        {
            <%= correspondenceInd = true%>
            f.correspondenceInd.checked = <%= correspondenceInd %>;
        }
    }

    function showDetail()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        f.transactionPriorityPK.value = currentRow.transactionPriorityPK;

        sendTransactionAction("EventAdminTran", "showTransactionPriorityDetails", "main");
    }

    function addTransactionPriority()
    {
        sendTransactionAction("EventAdminTran", "addTransactionPriority", "main");
    }

    function saveTransactionPriority()
    {
        sendTransactionAction("EventAdminTran", "saveTransactionPriority", "main");
    }

    function cancelTransactionPriorityEdits()
    {
        sendTransactionAction("EventAdminTran", "cancelTransactionPriorityEdits", "main");
    }

    function deleteTransactionPriority()
    {
        sendTransactionAction("EventAdminTran", "deleteTransactionPriority", "main");
    }

    function selectTransactionType(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.transactionTypePK.value = selector.options[selector.selectedIndex].value;
        }
    }

    function selectConfirm(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.confirmPK.value = selector.options[selector.selectedIndex].value;
        }
    }

    function selectCommissionable(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.commissionablePK.value = selector.options[selector.selectedIndex].value;
        }
    }
    
    function selectBonusChargebackCT(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.bonusChargebackPK.value = selector.options[selector.selectedIndex].value;
        }
    }    
    

    function selectReinsurable(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.reinsurablePK.value = selector.options[selector.selectedIndex].value;
        }
    }

<%--    function showRelation()--%>
<%--    {--%>
<%--        sendTransactionAction("EventAdminTran", "showFilteredTransactionRelations", "main");--%>
<%--    }--%>

    function selectDeselectRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var containingTable = currentRow.parentElement;

        for (var i = 0; i < containingTable.rows.length; i++)
        {
            containingTable.rows[i].style.backgroundColor = "#BBBBBB";
            containingTable.rows[i].isSelected = "false";
        }

        // Deactivate the selection in the "other" table
        var otherTable = null;
        if (containingTable.parentElement.id == "scriptsTable")
        {
            otherTable = document.all.tablesTable;
        }
        else
        {
            otherTable = document.all.scriptsTable;
        }

        for (var i = 0; i < otherTable.rows.length; i++)
        {
            otherTable.rows[i].style.backgroundColor = "#BBBBBB";
            otherTable.rows[i].isSelected = "false";
        }

        currentRow.style.backgroundColor = "#FFFFCC";
        currentRow.isSelected = "true";
    }

    function openDialog(theURL,winName,features,transaction,action)
    {
	    dialog = window.open(theURL,winName,features);

	    sendTransactionAction(transaction, action, winName);
	}

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>

<head>
<title>Transaction Priority Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" class="formdata" style="border-style:solid; border-width:1;  position:relative; width:100%; height:43%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="100%" border="0"  cellspacing="5" cellpadding="5">
    <tr>
      <td nowrap align="right">Transaction:&nbsp;</td>
      <td nowrap align="left">
        <select name="trxType" onChange="selectTransactionType(this)">
          <%
            out.println("<option>Please Select</option>");

            for(int i = 0; i < trxTypeCTs.length; i++)
            {
                String codeTablePK = trxTypeCTs[i].getCodeTablePK() + "";
                String codeDesc    = trxTypeCTs[i].getCodeDesc();

                if (codeTablePK.equals(transactionTypePK))
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
    </tr>
    <tr>
      <td nowrap align="right">EffectiveDate:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="effectiveMonth" size="2" maxSize="2" value="<%= effectiveMonth %>">
        /
        <input type="text" name="effectiveDay" size="2" maxSize="2" value="<%= effectiveDay %>">
        /
        <input type="text" name="effectiveYear" size="4" maxSize="4" value="<%= effectiveYear %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Priority:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="priority" size="6" maxSize="6" value="<%= priority %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Confirm:&nbsp;</td>
      <td nowrap align="left">
        <select name="confirm" onChange="selectConfirm(this)">
		  <%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < yesNoCTs.length; i++)
            {
                String codeTablePK = yesNoCTs[i].getCodeTablePK() + "";
                String codeDesc    = yesNoCTs[i].getCodeDesc();

                if (codeTablePK.equals(confirmPK))
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
    </tr>
    <tr>
      <td nowrap align="right">Commissionable:&nbsp;</td>
      <td nowrap align="left">
  	    <select name="commissionable" onChange="selectCommissionable(this)">
		  <%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < yesNoCTs.length; i++)
            {
                String codeTablePK = yesNoCTs[i].getCodeTablePK() + "";
                String codeDesc    = yesNoCTs[i].getCodeDesc();

                if (codeTablePK.equals(commissionablePK))
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
    </tr>
          <tr>
            <td nowrap="nowrap" align="right">Bonus Chargeback:&nbsp;</td>
            <td nowrap="nowrap" align="left">
            
  	    <select name="bonusChargebackCT" onChange="selectBonusChargebackCT(this)">
		  <%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < bonusChargebackCTs.length; i++)
            {
                String codeTablePK = bonusChargebackCTs[i].getCodeTablePK() + "";
                String codeDesc    = bonusChargebackCTs[i].getCodeDesc();

                if (codeTablePK.equals(bonusChargebackPK))
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
          </tr>
          <tr>
      <td nowrap align="right">Reinsurable:&nbsp;</td>
      <td nowrap align="left">
  	    <select name="reinsurable" onChange="selectReinsurable(this)">
		  <%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < yesNoCTs.length; i++)
            {
                String codeTablePK = yesNoCTs[i].getCodeTablePK() + "";
                String codeDesc    = yesNoCTs[i].getCodeDesc();

                if (codeTablePK.equals(reinsurablePK))
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
    </tr>
    <tr>
      <td nowrap align="right">&nbsp;</td>
      <td align="left" nowrap>
        <input type="checkbox" disabled="true" name="correspondenceInd" <%=correspondenceInd %>><a href ="javascript:showCorrespondenceDialog()">Correspondence</a>
      </td>
    </tr>
  </table>
</span>

<br><br>

<span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">
      <td align="left" height="14">
        <input type="button" id="btnAdd" name="add" value= " Add  " onClick="addTransactionPriority()">
        <input type="button" id="btnSave" name="save" value=" Save " onClick="saveTransactionPriority()">
		<input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelTransactionPriorityEdits()">
		<input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteTransactionPriority()">
	  </td>

<%--      <td align="right">--%>
<%--        <input type="button" id="btnRelateFilteredTransactions" name="relateTransactions" value="Filtered Transaction Relation" onClick="showRelation()">--%>
<%--      </td>--%>
  </table>
</span>


  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="33%" nowrap>
            Transaction
        </td>
        <td width="33%" nowrap>
            Priority
        </td>
        <td width="33%" nowrap>
            Effective Date
        </td>
    </tr>
    </table>

    <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:45%; top:0; left:0;">

    <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td colspan="3" height="100%%">
          <table class="scrollableArea" id="prioritySummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              String currentPK = "";
              String sTransactionType  = "";
              String sPriority = "";
              String sEffectiveDate = "";

              if (uiTransactionPriorityVOs != null)
              {
                uiTransactionPriorityVOs = (TransactionPriorityVO[]) Util.sortObjects(uiTransactionPriorityVOs, new String[]{"getPriority"});                  

                  for (int i = 0; i < uiTransactionPriorityVOs.length; i++)
                  {
                      currentPK = uiTransactionPriorityVOs[i].getTransactionPriorityPK() + "";

                      String className = null;

                      if (currentPK.equals(transactionPriorityPK))
                      {
                          className = "highLighted";
                      }
                      else
                      {
                          className = "mainEntry";
                      }

                      sTransactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", uiTransactionPriorityVOs[i].getTransactionTypeCT());
                      sPriority = uiTransactionPriorityVOs[i].getPriority() + "";
                      sEffectiveDate = Util.initString(uiTransactionPriorityVOs[i].getEffectiveDate(), "");
                      if (!sEffectiveDate.equals(""))
                      {
                          sEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(sEffectiveDate);
                      }

            %>
            <tr class="<%= className %>" id="<%= currentPK %>" transactionPriorityPK="<%= currentPK %>" isSelected="false"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showDetail()">
              <td align="left" nowrap width="33%">
                <%= sTransactionType %>
              </td>
              <td align="left" nowrap width="33%">
                <%= sPriority %>
              </td>
              <td align="left" nowrap width="33%">
                <%= sEffectiveDate %>
              </td>
            </tr>
            <%
                  } // end for
              }// end if
        	%>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction"    value="">
<input type="hidden" name="action"         value="">

<input type="hidden" name="transactionTypePK" value="<%= transactionTypePK %>">
<input type="hidden" name="confirmPK" value="<%= confirmPK %>">
<input type="hidden" name="commissionablePK" value="<%= commissionablePK %>">
<input type="hidden" name="reinsurablePK" value="<%= reinsurablePK %>">
<input type="hidden" name="transactionPriorityPK" value="<%= transactionPriorityPK %>">
<input type="hidden" name="bonusChargebackPK" value="<%= bonusChargebackPK %>">


</form>
</body>
</html>
