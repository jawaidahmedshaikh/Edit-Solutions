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
                 client.*,
                 event.*" %>

<%
    String selectClientPK = Util.initString((String)request.getAttribute("selectedClientDetailPK"), "");
    long selectedClientDetailPK = 0;
    if (!selectClientPK.equals(""))
    {
        selectedClientDetailPK = Long.parseLong(selectClientPK);
    }

    String refundRequested = Util.initString((String)session.getAttribute("refundRequested"), "");
    String clientRequested = Util.initString((String)session.getAttribute("clientRequested"), "");
    String addressRequested = Util.initString((String)request.getAttribute("addressRequested"), "");

    ClientDetailVO[] clientDetailVOs = (ClientDetailVO[]) session.getAttribute("clientDetailVOs");

    String suspenseMessage = Util.initString((String) request.getAttribute("suspenseMessage"), "");

    String suspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "0");
    Suspense suspense = (Suspense)request.getAttribute("suspense");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] countries = codeTableWrapper.getCodeTableEntries("COUNTRY");
    CodeTableVO[] zipCodePlacements = codeTableWrapper.getCodeTableEntries("ZIPCDPLACEMENT");
    CodeTableVO[] disbursementSources = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE");
    CodeTableVO[] trustTypes        = codeTableWrapper.getCodeTableEntries("TRUSTTYPE");
    CodeTableVO[] addressTypes      = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE");
    CodeTableVO[] bankAccountTypes  = codeTableWrapper.getCodeTableEntries("BANKACCTTYPE");
    CodeTableVO[]  reasonCodeCTVO = codeTableWrapper.getCodeTableEntries("SUSPENSEREASONCODE");

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
    String overrideInd = Util.initString((String)request.getAttribute("overrideInd"), "unchecked");
    String refundAmount = Util.initString((String)session.getAttribute("refundAmount"), "");
    
    String terminatedStatus = Util.initString((String)session.getAttribute("terminatedStatus"), "");
    String reasonCode = Util.initString(suspense.getReasonCodeCT(), "");
    if (reasonCode.equals("") && terminatedStatus.equalsIgnoreCase("true"))
    {
    	reasonCode = "TerminatedContract";
    }


    if (!selectClientPK.equals(""))
    {
         ClientDetail clientDetail = (ClientDetail)session.getAttribute("clientDetail");
         ClientAddress clientAddress = (ClientAddress)session.getAttribute("clientAddress");
         Preference preference = (Preference)session.getAttribute("preference");

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
            String disbursementSourceCT = suspense.getDisbursementSourceCT();
            if (disbursementSourceCT == null)
            {
                disbursementSource = Util.initString(preference.getDisbursementSourceCT(), "");
            }
            else
            {
                disbursementSource = disbursementSourceCT;
            }

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
        else
        {
            disbursementSource = Util.initString(suspense.getDisbursementSourceCT(), "");  
        }
    }

    if (addressRequested.equalsIgnoreCase("true"))
    {
        disbursementSource = (String)request.getAttribute("disbursementSource");
        addressType = (String)request.getAttribute("addressType");
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var suspenseMessage = "<%= suspenseMessage %>";
var selectedClientPK = "<%= selectClientPK %>";
var refundRequested = "<%= refundRequested %>";
var addressRequested = "<%= addressRequested %>";

function init()
{
	f = document.suspenseRefundForm;

    if (suspenseMessage != "")
    {
        alert(suspenseMessage);
    }

    formatCurrency();

    if (addressRequested == "true")
    {
        f.addressOverrideInd.disabled = false;
        f.bankOverrideInd.disabled = false;
    }

    var scrollToRow = document.getElementById("<%= selectClientPK %>");

    if (scrollToRow != null)
    {

        scrollToRow.scrollIntoView(false);
    }
}

function refundSuspense()
{
    if (refundRequested == "true")
    {
        if (valueIsZero(f.refundAmount.value))
        {
            alert("Please Enter Refund Amount");
            return;
        }
    }

    if (f.lastName.value == "" && f.corporateName.value == "")
    {
        alert("Last Name or Corporate Name Must Be Entered For Refund");
    }
    else if (f.addressLine1.value == "")
    {
        alert("At Least The First Address Line Must Be Completed For Refund");
    }
    else
    {
        f.disbursementSource.disabled = false;
        f.addressType.disabled = false;
        enableClientFields();
        sendTransactionAction("ContractDetailTran", "refundSuspense", "suspenseDialog");
        closeWindow();
    }
}

function cancelRefund()
{
   sendTransactionAction("ContractDetailTran", "cancelRefund", "suspenseDialog");
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
        sendTransactionAction("ContractDetailTran", "findClientsByNameForRefund", "_self");
    }
    else if (f.taxIdentification.value != "")
    {
        sendTransactionAction("ContractDetailTran", "findClientByTaxIdForRefund", "_self");
    }
    else
    {
        alert("Invalid search parameters");
    }
}

function selectClientDetailForRefund()
{
    var selectedRowId = getSelectedRowId("clientSummary");

    if (valueIsEmpty(selectedRowId))
    {
        alert("Value Is Empty");
    }
    else
    {
        f.selectedClientDetailPK.value = selectedRowId;

        sendTransactionAction("ContractDetailTran", "selectClientDetailForRefund", "_self");
    }
}

function getAddress()
{
    var selectedRowId = getSelectedRowId("clientSummary");

    if (selectedClientPK != "")
    {
        if (selectElementIsEmpty(f.addressType))
        {
            alert("Address Type Not Selected");
            return;
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "getAddressForType", "_self");
        }
    }
}

function enablePageFields()
{
    if (f.overrideInd.value == "on")
    {
        enableSuspenseFields();
        if (selectedClientPK == "")
        {
            f.trustType.disabled = false;
            enableClientFields();
            enableAddressFields();
            enableBankFields();
        }
    }
}

function enableSuspenseFields()
{
    f.disbursementSource.disabled = false;
    f.addressType.disabled = false;

    f.addressOverrideInd.disabled = false;
    f.bankOverrideInd.disabled = false;
}

function enableClientFields()
{
    f.trustType.disabled = false;
    f.namePrefix.disabled = false;
    f.nameSuffix.disabled = false;
    f.firstName.disabled = false;
    f.middleName.disabled = false;
    f.lastName.disabled = false;
    f.corporateName.disabled = false;
    f.printAs.disabled = false;
    f.printAs2.disabled = false;
}

function enableAddressFields()
{
    f.addressLine1.disabled = false;
    f.addressLine2.disabled = false;
    f.addressLine3.disabled = false;
    f.addressLine4.disabled = false;
    f.city.disabled = false;
    f.county.disabled = false;
    f.state.disabled = false;
    f.country.disabled = false;
    f.zipCode.disabled = false;
    f.zipCodePlacement.disabled = false;

    f.addressLine1.value = "";
    f.addressLine2.value = "";
    f.addressLine3.value = "";
    f.addressLine4.value = "";
    f.city.value = "";
    f.county.value = "";
    f.state.value = "Please Select";
    f.country.value = "Please Select";
    f.zipCode.value = "";
    f.zipCodePlacement.value = "Please Select";
}

function enableBankFields()
{
    f.bankAccountNumber.disabled = false;
    f.bankAccountType.disabled = false;
    f.bankRoutingNumber.disabled = false;
    f.bankName.disabled = false;
    f.bankAddressLine1.disabled = false;
    f.bankAddressLine2.disabled = false;
    f.bankCity.disabled = false;
    f.bankState.disabled = false;
    f.bankZipCode.disabled = false;

    f.bankAccountNumber.value = "";
    f.bankAccountType.value = "Please Select";
    f.bankRoutingNumber.value = "";
    f.bankName.value = "";
    f.bankAddressLine1.value = "";
    f.bankAddressLine2.value = "";
    f.bankCity.value = "";
    f.bankState.value = "Please Select";
    f.bankZipCode.value = "";
}

function enablePrintFields()
{
    f.printAs.disabled = false;
    f.printAs2.disabled = false;
}

</script>
<head>
<title>Suspense Refund/Client Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="suspenseRefundForm" method="post" action="/PORTAL/servlet/RequestManager">

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
        <td width="20%" nowrap>
            Name
        </td>
        <td width="20%" nowrap>
            Tax Id
        </td>
        <td width="20%" nowrap>
            Address Line 1
        </td>
        <td width="20%" nowrap>
            City
        </td>
        <td width="20%" nowrap>
            State
        </td>
    </tr>
</table>


<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:10%; top:0; left:0;">
  <table id="clientSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
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

            cTaxId = Util.initString(clientDetailVOs[i].getTaxIdentification(), "");

            if (clientDetailVOs[i].getClientAddressVOCount() > 0)
            {
                clientAddressVO = clientDetailVOs[i].getClientAddressVO(0);

                cAddressLine1 = Util.initString(clientAddressVO.getAddressLine1(), "");

                cCity = Util.initString(clientAddressVO.getCity(), "");

                cState = Util.initString(clientAddressVO.getStateCT(), "");
            }
            else
            {
                cAddressLine1 = "";
                cCity = "";
                cState = "";
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
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);selectClientDetailForRefund()">
      <td width="20%" nowrap>
        <%= cName %>
      </td>
      <td width="20%" nowrap>
        <%= cTaxId %>
      </td>
      <td width="20%" nowrap>
        <%= cAddressLine1 %>
      </td>
      <td width="20%" nowrap>
        <%= cCity %>
      </td>
      <td width="20%" nowrap>
        <%= cState %>
      </td>
    </tr>
    <%
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
    <td  nowrap>
        Use Overrides:
      <input type="checkbox" name="overrideInd" <%= overrideInd %> onClick="enablePageFields()" >
    </td>
    <%
        if (refundRequested == "true")
        {
    %>
        <td align="right" nowrap>Refund Amount:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="refundAmount" maxlength="15" size="15" value="<%= refundAmount %>" CURRENCY>
        </td>
          <td nowrap align="right">Reason Code:</td>
          <td nowrap align="left">
            <select name="reasonCode">
              <%
                  out.println("<option>Please Select</option>");

                  for(int i = 0; i < reasonCodeCTVO.length; i++) {

                      String codeDesc    = reasonCodeCTVO[i].getCodeDesc();
                      String code        = reasonCodeCTVO[i].getCode();

                     if (reasonCode.equals(code)) {

                         out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                  }

             %>
            </select>
      </td>
    <%
        }//END IF
    %>
  </tr>
</table>

<table width="100%"  style="border:1px solid black" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <br>
  <tr>
    <td>
        &nbsp;
    </td>
  </tr>
    <td align="right" nowrap>Disbursement Source:&nbsp;
      <select disabled="true"  name="disbursementSource" onChange="enablePrintFields()">
	  <option value="Please Select">Please Select</option>

      <%
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
      <select disabled="true" name="trustType">
	  <option value="Please Select">Please Select</option>
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
    <select disabled="true" name="addressType" onChange="getAddress()">
	  <option value="Please Select">Please Select</option>
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
        Use Address Overrides:
      <input type="checkbox" disabled="true" name="addressOverrideInd"  onClick="enableAddressFields()" >
    </td>
    <td align="left" colspan="4">
        &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Name Prefix:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="namePrefix" maxlength="5"  size="5" value="<%= namePrefix %>">
    </td>
    <td align="right" nowrap>Name Suffix:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input disabled="true" type="text" name="nameSuffix" maxlength="4" size="4" value="<%= nameSuffix %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>First Name:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="firstName" maxlength="15" size="15" value="<%= firstName %>">
    </td>
    <td align="right" nowrap>MiddleName:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="middleName" maxlength="15" size="15"	value="<%=middleName%>">
    </td>
    <td align="right" nowrap>Last Name:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="lastName" maxlength="30" size="20" value="<%= lastName%>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Corporate Name:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="corporateName" maxlength="60" size="35" value="<%= corporateName%>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Print As:&nbsp;</td>
    <td align="left" nowrap colspan="5">
      <input disabled="true" type="text" name="printAs" maxlength="70" size="35" value="<%= printAs %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Print As 2:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="printAs2" maxlength="70" size="35" value="<%= printAs2 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Address:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="addressLine1" maxlength="35" size="35" value="<%= addressLine1 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="addressLine2" maxlength="35" size="35" value="<%= addressLine2 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="addressLine3" maxlength="35" size="35" value="<%= addressLine3 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="addressLine4" maxlength="35" size="35" value="<%= addressLine4 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>City:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="city" maxlength="35" size="35" value="<%= city %>">
    </td>
    <td align="right" nowrap>County:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="county" maxlength="35" size="15" value="<%= county %>">
    </td>
    <td align="right" nowrap>State:&nbsp;</td>
    <td align="left" nowrap>
      <select disabled="true" name="state">
	  <option value="Please Select">Please Select</option>

      <%
          for(int i = 0; i < states.length; i++)
          {
              String codeDesc    = states[i].getCodeDesc();
              String code        = states[i].getCode();

              if (state.equals(code))
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
  </tr>

  <tr>
    <td align="right" nowrap>Country:&nbsp;</td>
    <td align="left" nowrap>
      <select disabled="true" name="country">
	  <option value="Please Select">Please Select</option>

      <%
          for(int i = 0; i < countries.length; i++)
          {
              String codeDesc    = countries[i].getCodeDesc();
              String code        = countries[i].getCode();

              if (country.equals(code))
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
    <td align="right" nowrap>Zip Code:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="zipCode" maxlength="15" size="15" value="<%= zipCode %>">
    </td>
    <td align="right" nowrap>Zip Code Placement:&nbsp;</td>
    <td align="left" nowrap>
      <select disabled="true" name="zipCodePlacement">
	  <option value="Please Select">Please Select</option>

      <%
          for(int i = 0; i < zipCodePlacements.length; i++)
          {
              String codeDesc    = zipCodePlacements[i].getCodeDesc();
              String code        = zipCodePlacements[i].getCode();

              if (zipCodePlacement.equals(code))
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
      <input type="checkbox" disabled="true" name="bankOverrideInd"  onClick="enableBankFields()" >
    </td>
    <td align="left" colspan="4">
        &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Account Number:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="bankAccountNumber" maxlength="17" size="17" value="<%= bankAccountNumber %>">
    </td>
    <td align="right" nowrap>Bank Account Type:&nbsp;</td>
    <td align="left" nowrap>
      <select disabled="true" name="bankAccountType">
	  <option value="Please Select">Please Select</option>

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
      <input disabled="true" type="text" name="bankRoutingNumber" maxlength="9" size="9" value="<%= bankRoutingNumber %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Bank Name:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="bankName" maxlength="60" size="35" value="<%= bankName %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Address:&nbsp;</td>
    <td align="left" nowrap >
      <input disabled="true" type="text" name="bankAddressLine1" maxlength="35" size="35" value="<%= bankAddressLine1 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>&nbsp;</td>
    <td align="left" nowrap >
      <input disabled="true" type="text" name="bankAddressLine2" maxlength="35" size="35" value="<%= bankAddressLine2 %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Bank City:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled="true" type="text" name="bankCity" maxlength="35" size="35" value="<%= bankCity %>">
    </td>
    <td align="right" nowrap>Bank State:&nbsp;</td>
    <td align="left" nowrap>
      <select disabled="true" name="bankState">
	  <option value="Please Select">Please Select</option>
      <%
          for(int i = 0; i < states.length; i++)
          {
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
      <input disabled="true" type="text" name="bankZipCode" maxlength="15" size="15" value="<%= bankZipCode %>">
    </td>
  </tr>
</table>

  <br>

 <table  width="100%" border="0">
      <tr>
        <td align="right" colspan="4">
            &nbsp;
        </td>
        <td align="right" nowrap>
          <input type="button" name="enter" value="Save" onClick="refundSuspense()">
          <input type="button" name="cancel" value="Cancel" onClick="cancelRefund()">
        </td>
      </tr>
</table>

</span>


<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="suspensePK" value="<%= suspensePK %>">
    <input type="hidden" name="suspenseClientInfoPK" value="<%= suspenseClientInfoPK %>">
    <input type="hidden" name="selectedClientDetailPK" value="<%= selectClientPK %>">

</form>
</body>

</html>
