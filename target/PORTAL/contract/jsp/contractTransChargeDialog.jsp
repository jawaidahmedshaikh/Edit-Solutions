<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 fission.utility.Util,
                 edit.common.EDITDate" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="contractChargeOverrides"
    class="fission.beans.SessionBean" scope="session"/>

 <jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

	CodeTableVO[] chargeTypes = codeTableWrapper.getCodeTableEntries("CHARGETYPE", Long.parseLong(companyStructureId));

    String key          = formBean.getValue("key");
	String chargeType 	= formBean.getValue("chargeType");
	String chargeAmount	= formBean.getValue("chargeAmount");
    String chargePK     = formBean.getValue("chargePK");
    String oneTimeOnlyInd = Util.initString(formBean.getValue("oneTimeOnlyInd"), "N");
    if (oneTimeOnlyInd.equalsIgnoreCase("N"))
    {
        oneTimeOnlyInd = "unchecked";
    }
    else
    {
        oneTimeOnlyInd = "checked";
    }

    String oneTimeOnlyDate = Util.initString(formBean.getValue("oneTimeOnlyDate"), "");
    String trxEffDate = Util.initString(formBean.getValue("trxEffDate"), "");

    String rowToMatchBase = key;
%>

<html>
<head>
<title>Charge Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.chargeForm;
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		sendTransactionAction("ContractDetailTran", "showChargeDetailSummary", "_self");
	}

	function addNewChargeOverride()
    {
		clearForm();
	}

	function cancelChargeOverride()
    {
		var key           = f.key.value;
		var transactionId = f.transactionId.value;

		clearForm();

		f.key.value = key;
		f.transactionId.value = transactionId;

		sendTransactionAction("ContractDetailTran","cancelChargeOverride","_self")
	}

	function updateChargeOverride()
    {
        if (f.chargeType.value == "Please Select")
        {
            alert("Please Select Charge Type");
        }
        else if (f.chargeAmount.value == "")
        {
            alert("Please Enter Charge Amount");
        }
        else
        {
            if (f.oneTimeOnlyInd.checked = true)
            {
                f.oneTimeOnlyInd.value = "Y";
            }
            else
            {
                f.oneTimeOnlyInd.value = "N";
            }

            sendTransactionAction("ContractDetailTran", "updateChargeOverride", "_self");
        }
	}

	function deleteChargeOverride()
    {
        if (f.chargeType.value == "Please Select")
        {
            alert("Please Select Charge for Deletion");
        }
        else
        {
            sendTransactionAction("ContractDetailTran","deleteChargeOverride","_self")
        }
	}

	function clearForm()
    {
		f.chargeAmount.value = "";
        f.key.value = "";
		f.chargeType.selectedIndex = 0;
	}

</script>
</head>
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="chargeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left" nowrap>Charge Type:&nbsp;
          <select name="chargeType">
            <option selected>Please Select</option>
            <%

                for(int i = 0; i < chargeTypes.length; i++) {

                    String codeTablePK = chargeTypes[i].getCodeTablePK() + "";
                    String codeDesc    = chargeTypes[i].getCodeDesc();
                    String code        = chargeTypes[i].getCode();

                    if (chargeType.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }

            %>
          </select>
      </td>
      <td align="left" nowrap>Amount:&nbsp;
        <input type="text" name="chargeAmount" size="9" maxlength="9" value="<%= chargeAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap>One Time Only (For Scheduled Events):&nbsp;
        <input type="checkbox" name="oneTimeOnlyInd" <%= oneTimeOnlyInd %>>
      </td>
    </tr>
    <tr>
      	<td nowrap align="left">
      		<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewChargeOverride()">
      		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="updateChargeOverride()">
      		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelChargeOverride()">
      		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteChargeOverride()">
      	</td>
    </tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left">Charge Type</th>
      <th align="left">Amount</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:55%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="chargesSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
  	  <%
        String rowToMatch = "";
        String trClass = "";
        boolean trSelected = false;

        Iterator it = contractChargeOverrides.getPageBeans().values().iterator();

        while (it.hasNext())
        {
            PageBean chargeBean = (PageBean) it.next();

            String iChargeType   = "";
            String iChargeAmount = "";
            if (!chargeBean.getValue("deleteInd").equalsIgnoreCase("Y"))
            {
                iChargeType = chargeBean.getValue("chargeType");
                iChargeAmount = chargeBean.getValue("chargeAmount");
            String iTrxEffDate = chargeBean.getValue("trxEffDate");
            String iOneTimeOnlyDate = chargeBean.getValue("oneTimeOnlyDate");
            if ((!iOneTimeOnlyDate.equals("") &&
                new EDITDate(iOneTimeOnlyDate).equals(new EDITDate(iTrxEffDate))) ||
                (iOneTimeOnlyDate.equals("")))
            {
                // Store behind the scenes...
                String iKey    = chargeBean.getValue("key");

                // Store behind the scenes...
                iKey = chargeBean.getValue("key");

                rowToMatch = iKey;

                if (rowToMatch.equals(rowToMatchBase))
                {
                    trClass  = "highlighted";
                    trSelected = true;
                }
                else
                {
                    trClass  = "default";
                    trSelected = false;
                }
        %>
		<tr class="<%= trClass %>" id="<%= iKey %>" isSelected="<%= trSelected %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
			<td nowrap>
				<%= iChargeType %>
			</td>
			<td nowrap>
                <script>document.write(formatAsCurrency(<%= iChargeAmount %>))</script>
			</td>
		</tr>
	  	<%
             }  //end if
           } // end if
         }// end while
		%>
    </table>
  </span>

  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
	<tr>
		<td align="right" nowrap colspan="4">
			<input type="button" name="cancel" value="Close" onClick ="closeWindow()">
		</td>
	</tr>
  </table>

  <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"  value="">
  <input type="hidden" name="action"       value="">

  <input type="hidden" name="key" value="<%= key %>">
  <input type="hidden" name="chargePK" value="<%= chargePK %>">
  <input type="hidden" name="oneTimeOnlyInd" value="">
  <input type="hidden" name="oneTimeOnlyDate" value="<%= oneTimeOnlyDate %>">
  <input type="hidden" name="trxEffDate" value="<%= trxEffDate %>">

</form>

</body>
</html>
