<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                 edit.portal.common.session.UserSession,
                 fission.utility.Util,
                 java.lang.reflect.Array,
                 event.Suspense,
                 client.*" %>

<%
    client.business.Lookup clientLookup = new client.component.LookupComponent();

    String selectClientPK = (String)session.getAttribute("selectedClientDetailPK");
    long selectedClientDetailPK = Long.parseLong(selectClientPK);
//    ClientDetailVO selectedClientDetailVO = (ClientDetailVO) request.getAttribute("selectedClientDetailVO");
//
//    long selectedClientDetailPK = 0;
//
//    if (selectedClientDetailVO != null)
//    {
//        selectedClientDetailPK = selectedClientDetailVO.getClientDetailPK();
//    }

    ClientDetailVO[] clientDetailVOs = (ClientDetailVO[]) session.getAttribute("clientDetailVOs");

    String suspenseMessage = Util.initString((String) request.getAttribute("suspenseMessage"), "");

    String suspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "0");

    Suspense suspense = (Suspense)session.getAttribute("suspense");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] countries = codeTableWrapper.getCodeTableEntries("COUNTRY");
    CodeTableVO[] zipCodePlacements = codeTableWrapper.getCodeTableEntries("ZIPCDPLACEMENT");
    CodeTableVO[] disbursementSources = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE");
    CodeTableVO[] trustTypes        = codeTableWrapper.getCodeTableEntries("TRUSTTYPE");
    CodeTableVO[] addressTypes      = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE");
    CodeTableVO[] bankAccountTypes  = codeTableWrapper.getCodeTableEntries("BANKACCTTYPE");

    String suspenseClientInfoPK = "0";
    String namePrefix = "";
    String nameSuffix = "";
    String firstName = "";
    String middleName = "";
    String lastName = "";
    String corporateName = "";
    String printAs = "";
    String printAs2 = "";
    String addressLine1 = "";
    String addressLine2 = "";
    String addressLine3 = "";
    String addressLine4 = "";
    String city = "";
    String county = "";
    String state = "";
    String country = "";
    String zipCode = "";
    String zipCodePlacement = "";
    String bankAccountNumber = "";
    String bankRoutingNumber = "";
    String bankName = "";
    String bankAddressLine1 = "";
    String bankAddressLine2 = "";
    String bankCity = "";
    String bankState = "";
    String bankZipCode = "";
    String disbursementSource = "";
    String trustType = "";
    String addressType = "";
    String bankAccountType = "";
    String overrideInd = "unchecked";
    String addressOverrideInd = "unchecked";
    String bankOverrideInd = "unchecked";

    if (suspense != null)
    {
         ClientDetail clientDetail = suspense.getClientDetail();
         ClientAddress clientAddress = suspense.getClientAddress();;
         Preference preference = suspense.getPreference();

        if (clientDetail != null )
        {
            suspenseClientInfoPK = Util.initString(clientDetail.getClientDetailPK() + "", "0");
            namePrefix = Util.initString(clientDetail.getNamePrefix(), "");
            nameSuffix = Util.initString(clientDetail.getNameSuffix(), "");
            firstName = Util.initString(clientDetail.getFirstName(), "");
            middleName = Util.initString(clientDetail.getMiddleName(), "");
            lastName = Util.initString(clientDetail.getLastName(), "");
            corporateName = Util.initString(clientDetail.getCorporateName(), "");
            trustType = Util.initString(clientDetail.getTrustTypeCT(), "");
        }

        if (clientAddress != null)
        {

            addressLine1 = Util.initString(clientAddress.getAddressLine1(), "");
            addressLine2 = Util.initString(clientAddress.getAddressLine2(), "");
            addressLine3 = Util.initString(clientAddress.getAddressLine3(), "");
            addressLine4 = Util.initString(clientAddress.getAddressLine4(), "");
            city = Util.initString(clientAddress.getCity(), "");
            county = Util.initString(clientAddress.getCounty(), "");
            state = Util.initString(clientAddress.getStateCT(), "");
            country = Util.initString(clientAddress.getCountryCT(), "");
            zipCode = Util.initString(clientAddress.getZipCode(), "");
            zipCodePlacement = Util.initString(clientAddress.getZipCodePlacementCT(), "");
            addressType = Util.initString(clientAddress.getAddressTypeCT(), "");
        }

        if (preference != null)
        {
            printAs = Util.initString(preference.getPrintAs(), "");
            printAs2 = Util.initString(preference.getPrintAs2(), "");
            disbursementSource = Util.initString(preference.getDisbursementSourceCT(), "");

            bankAccountType = Util.initString(preference.getBankAccountTypeCT(), "");
            bankAccountNumber = Util.initString(preference.getBankAccountNumber(), "");
            bankRoutingNumber = Util.initString(preference.getBankRoutingNumber(), "");
            bankName = Util.initString(preference.getBankName(), "");
            bankAddressLine1 = Util.initString(preference.getBankAddressLine1(), "");
            bankAddressLine2 = Util.initString(preference.getBankAddressLine2(), "");
            bankCity = Util.initString(preference.getBankCity(), "");
            bankState = Util.initString(preference.getBankStateCT(), "");
            bankZipCode = Util.initString(preference.getBankZipCode(), "");
        }
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/contract/javascript/suspense.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var suspenseMessage = "<%= suspenseMessage %>";

function init()
{
	f = document.suspenseClientInfoForm;

    if (suspenseMessage != "")
    {
        alert(suspenseMessage);
    }
}

function saveSuspenseClientInformation()
{
    if (f.lastName.value == "" && f.corporateName.value == "")
    {
        alert("Last Name or Corporate Name Must Be Entered For Save");
    }
    else if (f.addressLine1.value == "")
    {
        alert("At Least The First Address Line Must Be Completed For Save");
    }
    else
    {
        sendTransactionAction("ContractDetailTran", "saveSuspenseClientInfo", "suspenseDialog");
        window.close();
    }
}

function cancelSuspenseClientInformation()
{
    sendTransactionAction("ContractDetailTran", "cancelSuspenseClientInfo", "suspenseDialog");
    window.close();
}

function searchClients()
{
    if (f.name.value != "" && f.taxIdentification.value != "")
    {
        alert("Enter only one parameter");
    }

    if (f.name.value != "")
    {
        sendTransactionAction("ContractDetailTran", "findClientsByNameForSuspense", "clientInformation");
    }
    else if (f.taxIdentification.value != "")
    {
        sendTransactionAction("ContractDetailTran", "findClientByTaxIdForSuspense", "clientInformation");
    }
    else
    {
        alert("Invalid search parameters");
    }
}

function selectClientDetailForSuspense()
{
    var selectedRowId = getSelectedRowId("clientSummary");

    if (valueIsEmpty(selectedRowId))
    {
        alert("Value Is Empty");
    }
    else
    {
        f.selectedClientDetailPK.value = selectedRowId;

        sendTransactionAction("ContractDetailTran", "selectClientDetailForSuspense", "clientInformation");
    }
}

</script>
<head>
<title>Disbursement Client Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="suspenseClientInfoForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table class="formData" width="100%" height="2%" border="0" cellspacing="0" cellpadding="5">

  <tr colspan="6">
    <td align="right" nowrap>Tax Identification Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="taxIdentification" size="15" maxlength="20">
    </td>
    <td align="right" nowrap>Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="name" size="20" maxlength="20">
    </td>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>&nbsp;</td>
  </tr>
  <tr>
    <td align="right" colspan="3" nowrap>&nbsp;</td>
    <td align="right">
      <input type="button" value=" Enter  " onClick="searchClients()">
    </td>
  </tr>

<%-- Column Headings --%>
<table class="summary" width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td  nowrap>
            Name
        </td>
        <td  nowrap>
            Tax Id
        </td>
        <td  nowrap>
            Address Line 1
        </td>
        <td  nowrap>
            City
        </td>
        <td nowrap>
            State
        </td>
    </tr>
</table>


<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:10%; top:0; left:0;">
  <table id="clientSummary" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <%
    if (clientDetailVOs != null) // Test for the existence of the target VOs.
    {
        ClientAddressVO clientAddressVO = null;

        String cFirstName = "";
        String cLastName = "";
        String cCorporateName = "";
        String cName = "";
        String cTaxId = "";
        String cAddressLine1 = "";
        String cCity = "";
        String cState = "";

        for (int i = 0; i < clientDetailVOs.length; i++) // Loop through the target VOs.
        {
            long currentClientDetailPK = clientDetailVOs[i].getClientDetailPK();

            cFirstName = clientDetailVOs[i].getFirstName();

            cLastName = clientDetailVOs[i].getLastName();

            cCorporateName = clientDetailVOs[i].getCorporateName();

            if (cLastName == null)
            {
                cName = cCorporateName;
            }
            else
            {
                cName = cLastName + ", " + cFirstName;
            }

            cTaxId = clientDetailVOs[i].getTaxIdentification();

            clientAddressVO = clientLookup.findClientAddessBy_AddressType(currentClientDetailPK, "PrimaryAddress");

            if (clientAddressVO != null)
            {
                cAddressLine1 = clientAddressVO.getAddressLine1();

                cCity = clientAddressVO.getCity();

                cState = clientAddressVO.getStateCT();
            }

            boolean isSelected = false;

            String className = null;

            if (currentClientDetailPK == selectedClientDetailPK)
            {
                isSelected = true;

                className = "highlighted";
            }
            else
            {
                className = "default";
            }
    %>
    <tr class="<%= className %>" id="<%= currentClientDetailPK %>" isSelected="<%= isSelected %>"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);selectClientDetailForSuspense()">
      <td  nowrap>
        <%= cName %>
      </td>
      <td  nowrap>
        <%= cTaxId %>
      </td>
      <td  nowrap>
        <%= cAddressLine1 %>
      </td>
      <td  nowrap>
        <%= cCity %>
      </td>
      <td  nowrap>
        <%= cState %>
      </td>
    </tr>
    <%
            cFirstName = "";
            cLastName = "";
            cTaxId = "";
            cAddressLine1 = "";
            cCity = "";
            cState = "";
        }// end for
    } // end if
    %>
    <tr class="filler">
      <td colspan="3">
        &nbsp;
      </td>
    </tr>
  </table>
</span>
</table>

<br>
<br>


<table width="100%"  style="border:1px solid black" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" colspan="1" nowrap>
        Use Overrides:
      <input type="checkbox" name="overrideInd" <%= overrideInd %>>
    </td>
    <td align="left" colspan="4">
        &nbsp;
    </td>
  </tr>
</table>
<br>

<table width="100%"  style="border:1px solid black" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
        &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Disbursement Source:&nbsp;
      <select name="disbursementSource">
      <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < disbursementSources.length; i++)
          {
              String codeDesc    = disbursementSources[i].getCodeDesc();
              String code        = disbursementSources[i].getCode();

              if (disbursementSource.equals(code))
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
    <td align="right" nowrap>Trust Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="trustType">
        <option>Please Select</option>
          <%
            for(int i = 0; i < trustTypes.length; i++) {

                String codeDesc    = trustTypes[i].getCodeDesc();
                String code        = trustTypes[i].getCode();

                if (trustType.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Address Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="addressType">
        <option>Please Select</option>
          <%

            for(int i = 0; i < addressTypes.length; i++) {

                String codeDesc    = addressTypes[i].getCodeDesc();
                String code        = addressTypes[i].getCode();

                if (addressType.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
  </tr>
  <tr>
      <td>
        &nbsp;
    </td>
  </tr>
</table>
<br>

<table  width="100%" style="border:1px solid black" border="0" style="border:1px solid black">
  <tr>
    <td align="left" colspan="1" nowrap>
        Use Overrides:
      <input type="checkbox" name="overrideInd" <%= addressOverrideInd %>>
    </td>
    <td align="left" colspan="4">
        &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Name Prefix:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="namePrefix" maxlength="5"  size="5" value="<%= namePrefix %>">
    </td>
    <td align="right" nowrap>Name Suffix:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="nameSuffix" maxlength="4" size="4" value="<%= nameSuffix %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>First Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="firstName" maxlength="15" size="15" value="<%= firstName %>">
    </td>
    <td align="right" nowrap>MiddleName:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="middleName" maxlength="15" size="15"	value="<%=middleName%>">
    </td>
    <td align="right" nowrap>Last Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="lastName" maxlength="30" size="20" value="<%= lastName%>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Corporate Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="corporateName" maxlength="60" size="35" value="<%= corporateName%>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Print As:&nbsp;</td>
    <td align="left" nowrap colspan="5">
      <input type="text" name="printAs" maxlength="70" size="35" value="<%= printAs %>">
    </td>

    <td align="right" nowrap>Print As 2:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="printAs2" maxlength="70" size="35" value="<%= printAs2 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Address:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="addressLine1" maxlength="35" size="35" value="<%= addressLine1 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="addressLine2" maxlength="35" size="35" value="<%= addressLine2 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="addressLine3" maxlength="35" size="35" value="<%= addressLine3 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="addressLine4" maxlength="35" size="35" value="<%= addressLine4 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>City:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="city" maxlength="35" size="35" value="<%= city %>">
    </td>
    <td align="right" nowrap>County:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="county" maxlength="35" size="15" value="<%= county %>">
    </td>
    <td align="right" nowrap>State:&nbsp;</td>
    <td align="left" nowrap>
      <select name="state">
      <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < states.length; i++)
          {
              String codeTablePK = states[i].getCodeTablePK() + "";
              String codeDesc    = states[i].getCodeDesc();
              String code        = states[i].getCode();

              if (state.equals(code))
              {
                  out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
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
    <td align="right" nowrap>Country:&nbsp;</td>
    <td align="left" nowrap>
      <select name="country">
      <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < countries.length; i++)
          {
              String codeTablePK = countries[i].getCodeTablePK() + "";
              String codeDesc    = countries[i].getCodeDesc();
              String code        = countries[i].getCode();

              if (country.equals(code))
              {
                  out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
              }
              else
              {
                  out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
              }
          }

      %>
      </select>
    </td>
    <td align="right" nowrap>Zip Code:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="zipCode" maxlength="15" size="15" value="<%= zipCode %>">
    </td>
    <td align="right" nowrap>Zip Code Placement:&nbsp;</td>
    <td align="left" nowrap>
      <select name="zipCodePlacement">
      <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < zipCodePlacements.length; i++)
          {
              String codeTablePK = zipCodePlacements[i].getCodeTablePK() + "";
              String codeDesc    = zipCodePlacements[i].getCodeDesc();
              String code        = zipCodePlacements[i].getCode();

              if (zipCodePlacement.equals(code))
              {
                  out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
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
</table>
<br>

      <tr>
        <td height="2%" valign="top">
            Bank Information:
        </td>
      </tr>

<table border="0" style="border:1px solid black" width="100%">
  <tr>
    <td align="left" colspan="1" nowrap>
        Use Overrides:
      <input type="checkbox" name="overrideInd" <%= bankOverrideInd %>>
    </td>
    <td align="left" colspan="4">
        &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Account Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankAccountNumber" maxlength="17" size="17" value="<%= bankAccountNumber %>">
    </td>
    <td align="right" nowrap>Bank Account Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="bankAccountType">
        <option>Please Select</option>
          <%
            for(int i = 0; i < bankAccountTypes.length; i++) {

                String codeDesc    = bankAccountTypes[i].getCodeDesc();
                String code        = bankAccountTypes[i].getCode();

                if (bankAccountType.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Bank Routing Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankRoutingNumber" maxlength="9" size="9" value="<%= bankRoutingNumber %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Bank Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankName" maxlength="60" size="35" value="<%= bankName %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Address:&nbsp;</td>
    <td align="left" nowrap >
      <input type="text" name="bankAddressLine1" maxlength="35" size="35" value="<%= bankAddressLine1 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap >
      <input type="text" name="bankAddressLine2" maxlength="35" size="35" value="<%= bankAddressLine2 %>">
    </td>
  </tr>

  <tr>
      <td nowrap colspan="6">&nbsp;</td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap colspan="5">
    <td align="right" nowrap>Bank City:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankCity" maxlength="35" size="35" value="<%= bankCity %>">
    </td>
    <td align="right" nowrap>Bank State:&nbsp;</td>
    <td align="left" nowrap>
      <select name="bankState">
      <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < states.length; i++)
          {
              String codeTablePK = states[i].getCodeTablePK() + "";
              String codeDesc    = states[i].getCodeDesc();
              String code        = states[i].getCode();

              if (bankState.equals(code))
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
    <td align="right" nowrap>Bank Zip Code:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankZipCode" maxlength="15" size="15" value="<%= bankZipCode %>">
    </td>
  </tr>
</table>
<%--</span>--%>
<%--</span>--%>
  <br>

  <table  width="100%" border="0">
      <tr>
        <td align="right" colspan="4">
            &nbsp;
        </td>
        <td align="right" nowrap>
          <input type="button" name="enter" value="Save" onClick="saveSuspenseClientInformation()">
          <input type="button" name="cancel" value="Cancel" onClick="cancelSuspenseClientInformation()">
        </td>
      </tr>
</table>

</span>


<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="suspensePK" value="<%= suspensePK %>">
    <input type="hidden" name="suspenseClientInfoPK" value="<%= suspenseClientInfoPK %>">
    <input type="hidden" name="selectedClientDetailPK" value="<%= selectedClientDetailPK %>">

</form>
</body>

</html>
