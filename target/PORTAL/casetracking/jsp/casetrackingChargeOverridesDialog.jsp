<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 event.Charge,
                 fission.utility.Util,
                 edit.common.EDITBigDecimal" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    Long companyStructurePK = (Long) request.getAttribute("companyStructurePK");

	CodeTableVO[] chargeTypes = codeTableWrapper.getCodeTableEntries("CHARGETYPE", companyStructurePK.longValue());

    Charge charge = (Charge) session.getAttribute("chargeOverride");
    Charge[] charges = (Charge[]) session.getAttribute("charges");

    String chargeTypeCT = "";
    EDITBigDecimal chargeAmount = new EDITBigDecimal();
    Long chargePK = new Long(0);

    if (charge != null)
    {
        chargeTypeCT = (String) Util.initObject(charge, "ChargeTypeCT", "");
        chargeAmount = (EDITBigDecimal) Util.initObject(charge, "ChargeAmount", null);
        chargePK = charge.getChargePK();
    }

    String rowToMatchBase = chargePK + chargeTypeCT;

    String segmentPK = (String) request.getAttribute("segmentPK");
%>

<html>
<head>
<title>Charge Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.chargeForm;

        formatCurrency();
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		sendTransactionAction("CaseTrackingTran", "showChargeDetailSummary", "_self");
	}

	function addNewChargeOverride()
    {
		clearForm();
	}

	function cancelChargeOverride()
    {
        clearForm();
	}

	function updateChargeOverride()
    {
        if (f.chargeTypeCT.value == "Please Select")
        {
            alert("Please Select Charge Type");
        }
        else if (f.chargeAmount.value == "")
        {
            alert("Please Enter Charge Amount");
        }
        else
        {
            sendTransactionAction("CaseTrackingTran", "updateChargeOverride", "_self");
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
            sendTransactionAction("CaseTrackingTran","deleteChargeOverride","_self")
        }
	}

	function clearForm()
    {
		f.chargeAmount.value = "";
		f.chargeTypeCT.selectedIndex = 0;
	}

</script>
</head>
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="chargeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left" nowrap>Charge Type:&nbsp;
          <select name="chargeTypeCT">
            <option selected>Please Select</option>
            <%

                for(int i = 0; i < chargeTypes.length; i++) {

                    String codeTablePK = chargeTypes[i].getCodeTablePK() + "";
                    String codeDesc    = chargeTypes[i].getCodeDesc();
                    String code        = chargeTypes[i].getCode();

                    if (chargeTypeCT.equalsIgnoreCase(code)) {

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
        <input type="text" name="chargeAmount" size="9" maxlength="9" value="<%= chargeAmount.toString() %>" CURRENCY>
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
        String trClass = "";
        boolean trSelected = false;
        String rowToMatch = "";

        if (charges != null)
        {
            for (int i = 0; i < charges.length; i++)
            {
                String iChargeType = (String) Util.initObject(charges[i], "ChargeTypeCT", "");
                EDITBigDecimal iChargeAmount = (EDITBigDecimal) Util.initObject(charges[i], "ChargeAmount", new EDITBigDecimal());

                // Store behind the scenes...
                Long iChargePK = (Long) Util.initObject(charges[i], "ChargePK", new Long(0));

                rowToMatch = iChargePK + iChargeType;

                if (rowToMatchBase.equals(rowToMatch))
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
		<tr class="<%= trClass %>" id="<%= rowToMatch %>" isSelected="<%= trSelected %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
			<td nowrap>
				<%= iChargeType %>
			</td>
			<td nowrap>
                <script>document.write(formatAsCurrency(<%= iChargeAmount.toString() %>))</script>
			</td>
		</tr>
	  	<%
            } // end for
         }// end if
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

  <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
  <input type="hidden" name="chargePK" value="<%= chargePK.toString() %>">

</form>

</body>
</html>
