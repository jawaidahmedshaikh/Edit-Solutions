<!-- ***** JAVA CODE ***** -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.net.*, java.util.*, fission.global.*, fission.beans.*" %>

<jsp:useBean id="companyStructureLists"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<%

	String[] companyList = companyStructureLists.getValues("companyList");
	if(companyList == null){companyList = new String[0];}

	String[] marketingPackageList = companyStructureLists.getValues("marketingPackageList");
	if(marketingPackageList == null) {marketingPackageList = new String[0];}

	String[] groupProductList = companyStructureLists.getValues("groupProductList");
	if(groupProductList == null) {groupProductList = new String[0];}

	String[] areaList = companyStructureLists.getValues("areaList");
	if(areaList == null) {areaList = new String[0];}

	String[] businessContractList = companyStructureLists.getValues("businessContractList");
	if(businessContractList == null) {businessContractList = new String[0];}

	String company          = formBean.getValue("company");
	String marketingPackage = formBean.getValue("marketingPackage");
	String groupProduct     = formBean.getValue("groupProduct");
	String areaValue        = formBean.getValue("area");
	String businessContract = formBean.getValue("businessContract");
%>

<html>
<head>
<title>companyStructure.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- ****** JAVASCRIPT ***** //-->

<script language="Javascript1.2">
var selectedCompanyValue 	= "*";
var selectedMPValue 		= "*";
var selectedGPValue 		= "*";
var selectedAreaValue 		= "*";
var selectedBCValue 		= "*";

function sendTransactionAction(tempTransaction, tempAction, target) {

	document.companyStructureForm.transaction.value=tempTransaction;
	document.companyStructureForm.action.value=tempAction;
	document.companyStructureForm.submit();
}

function applyCompanyStructure(){

	document.companyStructureForm.companyStructure.value = document.companyStructureForm.newCompanyStructure.value;

	sendTransactionAction("AccountingDetailTran","applyCompanyStructure","main");
}

function saveCompanyStructure() {

	document.companyStructureForm.companyStructure.value = document.companyStructureForm.newCompanyStructure.value;

	sendTransactionAction("AccountingDetailTran","saveCompanyStructure","main");
}

function cancelCompanyStructure(){

	document.companyStructureForm.companyStructure.value = document.companyStructureForm.newCompanyStructure.value;

	sendTransactionAction("AccountingDetailTran","cancelCompanyStructure","main");
}

function clearCompanyStructure(){

	document.companyStructureForm.companyStructure.value = document.companyStructureForm.newCompanyStructure.value;

	sendTransactionAction("AccountingDetailTran","clearCompanyStructure","main");
}

function init(disableInputFields) {

	top.frames["main"].setActiveTab("structureTab");

	if (disableInputFields == false) {

		selectedCompanyValue = document.companyStructureForm.companySelect.options[document.companyStructureForm.companySelect.selectedIndex].text;
		selectedMPValue = document.companyStructureForm.marketingPackageSelect.options[document.companyStructureForm.marketingPackageSelect.selectedIndex].text;
		selectedGPValue = document.companyStructureForm.groupProductSelect.options[document.companyStructureForm.groupProductSelect.selectedIndex].text;
		selectedAreaValue = document.companyStructureForm.areaSelect.options[document.companyStructureForm.areaSelect.selectedIndex].text;
		selectedBCValue = document.companyStructureForm.businessContractSelect.options[document.companyStructureForm.businessContractSelect.selectedIndex].text;

		document.companyStructureForm.newCompanyName.disabled = true;
		document.companyStructureForm.newMarketingPackage.disabled = true;
		document.companyStructureForm.newGroupProduct.disabled = true;
		document.companyStructureForm.newArea.disabled = true;
		document.companyStructureForm.newBusinessContract.disabled = true;
		document.companyStructureForm.newCompanyStructure.disabled = true;
	}
}

function setCompanySelection() {

	selectedCompanyValue = document.companyStructureForm.companySelect.options[document.companyStructureForm.companySelect.selectedIndex].text;


	if (selectedCompanyValue == "<new>") {
		document.companyStructureForm.newCompanyName.disabled = false;
		document.companyStructureForm.newCompanyName.focus();
		selectedCompanyValue = document.companyStructureForm.newCompanyName.value;
	}
	else {
		document.companyStructureForm.newCompanyName.disabled = true;
		document.companyStructureForm.newCompanyName.value = "";
	}

	 document.companyStructureForm.newCompanyStructure.value = buildCompanyStructure();


}

function setMPSelection() {

	selectedMPValue = document.companyStructureForm.marketingPackageSelect.options[document.companyStructureForm.marketingPackageSelect.selectedIndex].text;

	if (selectedMPValue == "<new>") {
		document.companyStructureForm.newMarketingPackage.disabled = false;
		document.companyStructureForm.newMarketingPackage.focus();
		selectedMPValue = document.companyStructureForm.newMarketingPackage.value;
	}
	else {

		document.companyStructureForm.newMarketingPackage.disabled = true;
		document.companyStructureForm.newMarketingPackage.value = "";
	}

	document.companyStructureForm.newCompanyStructure.value = buildCompanyStructure();
}

function setGPSelection() {

	selectedGPValue = document.companyStructureForm.groupProductSelect.options[document.companyStructureForm.groupProductSelect.selectedIndex].text;

	if (selectedGPValue == "<new>") {
		document.companyStructureForm.newGroupProduct.disabled = false;
		document.companyStructureForm.newGroupProduct.focus();
		selectedGPValue = document.companyStructureForm.newGroupProduct.value;

	}
	else {
		document.companyStructureForm.newGroupProduct.disabled = true;
		document.companyStructureForm.newGroupProduct.value = "";
	}

	document.companyStructureForm.newCompanyStructure.value = buildCompanyStructure();
}

function setAreaSelection() {

	selectedAreaValue = document.companyStructureForm.areaSelect.options[document.companyStructureForm.areaSelect.selectedIndex].text;

	if (selectedAreaValue == "<new>") {
		document.companyStructureForm.newArea.disabled = false;
		document.companyStructureForm.newArea.focus();
		selectedAreaValue = document.companyStructureForm.newArea.value;
	}
	else {

		document.companyStructureForm.newArea.disabled = true;
		document.companyStructureForm.newArea.value = "";
	}

	document.companyStructureForm.newCompanyStructure.value = buildCompanyStructure();
}

function setBCSelection() {

	selectedBCValue = document.companyStructureForm.businessContractSelect.options[document.companyStructureForm.businessContractSelect.selectedIndex].text;

	if (selectedBCValue == "<new>") {
		document.companyStructureForm.newBusinessContract.disabled = false;
		document.companyStructureForm.newBusinessContract.focus();
		selectedBCValue = document.companyStructureForm.newBusinessContract.value;
	}
	else {
		document.companyStructureForm.newBusinessContract.disabled = true;
		document.companyStructureForm.newBusinessContract.value = "";
	}

	document.companyStructureForm.newCompanyStructure.value = buildCompanyStructure();
}

function filterSelection(val) {

	if (val == "Please Select") {
		return "*";
	}
	else  {
		return val;
	}
}

function buildCompanyStructure() {

	var companyStruct =

	filterSelection(selectedCompanyValue) + "," +
	filterSelection(selectedMPValue)      + "," +
	filterSelection(selectedGPValue)      + "," +
	filterSelection(selectedAreaValue)    + "," +
	filterSelection(selectedBCValue)   ;

	return companyStruct;
}

</script>
</head>

<!-- ***** HTML CODE *****  //-->

<body bgcolor="#DDDDDD"  onLoad="init(false)"  style="border-style:solid; border-width:1;">

<form name="companyStructureForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="95%" border="0" cellspacing="0" cellpadding="0" height="378">
    <tr>
    <td>
        <table width="101%" border="0" cellspacing="0" cellpadding="0" height="349">
          <tr>
            <td width="31%" align="right" valign="top" height="31">Company: </td>
            <td width="22%" align="center" valign="top" height="31">
              <select name="companySelect" onChange="setCompanySelection()">
                <%
        	        out.println("<option>Please Select</option>");
					out.println("<option>&lt;new&gt;</option>");
					for(int i=0; i<companyList.length; i++){
						if(company.equalsIgnoreCase(companyList[i]))
						{
							out.println("<option selected>"+companyList[i]+"</option>");
						}
						else
						{
							out.println("<option>"+companyList[i]+"</option>");
						}
					}
				%>
			 </select>
            </td>
            <td width="47%" align="left" valign="top" height="31">
              <input type="text" name="newCompanyName" onChange="setCompanySelection()" size="15" maxlength="15">
            </td>
          </tr>
          <tr>
            <td width="31%" align="right" height="33" valign="top">Marketing Package:
            </td>
            <td width="22%" align="center" valign="top" height="33">
              <select name="marketingPackageSelect" onChange="setMPSelection()">
                 <%
        	     	out.println("<option>Please Select</option>");
					out.println("<option>&lt;new&gt;</option>");
					for(int i=0; i<marketingPackageList.length; i++){
						if(marketingPackage.equalsIgnoreCase(marketingPackageList[i]))
						{
							out.println("<option selected>"+marketingPackageList[i]+"</option>");
						}
						else
						{
							out.println("<option>"+marketingPackageList[i]+"</option>");
						}
					}
				%>
              </select>
            </td>
            <td width="47%" align="left" valign="top" height="33">
              <input type="text" name="newMarketingPackage" onChange="setMPSelection()" size="15" maxlength="15">
            </td>
          </tr>
          <tr>
            <td width="31%" align="right" valign="top" height="37">Group Product:
            </td>
            <td width="22%" align="center" valign="top" height="37">
              <select name="groupProductSelect" onChange="setGPSelection()">
                <%
       				out.println("<option>Please Select</option>");
					out.println("<option>&lt;new&gt;</option>");
					for(int i=0; i<groupProductList.length; i++){
						if(groupProduct.equalsIgnoreCase(groupProductList[i]))
						{
							out.println("<option selected>"+groupProductList[i]+"</option>");
						}
						else
						{
							out.println("<option>"+groupProductList[i]+"</option>");
						}
					}
       			 %>
              </select>
            </td>
            <td width="47%" align="left" valign="top" height="37">
              <input type="text" name="newGroupProduct" onChange="setGPSelection()" size="15" maxlength="15">
            </td>
          </tr>
          <tr>
            <td width="31%" align="right" valign="top" height="35"> Area: </td>
            <td width="22%" align="center" valign="top" height="35">
              <select name="areaSelect" onChange="setAreaSelection()">
                <%
 			        out.println("<option>Please Select</option>");
					out.println("<option>&lt;new&gt;</option>");
					for(int i=0; i<areaList.length; i++){
						if(areaValue.equalsIgnoreCase(areaList[i]))
						{
							out.println("<option selected>"+areaList[i]+"</option>");
						}
						else
						{
							out.println("<option>"+areaList[i]+"</option>");
						}
					}
        		%>
              </select>
            </td>
            <td width="47%" align="left" valign="top" height="35">
              <input type="text" name="newArea" onChange="setAreaSelection()" size="15" maxlength="15">
            </td>
          </tr>
          <tr>
            <td width="31%" align="right" valign="top" height="32">Business Contract:
            </td>
            <td width="22%" align="center" valign="top" height="32">
              <select name="businessContractSelect" onChange="setBCSelection()">
                <%
			        out.println("<option>Please Select</option>");
					out.println("<option>&lt;new&gt;</option>");
					for(int i=0; i<businessContractList.length; i++){
						if(businessContract.equalsIgnoreCase(businessContractList[i]))
						{
							out.println("<option selected>"+businessContractList[i]+"</option>");
						}
						else
						{
							out.println("<option>"+businessContractList[i]+"</option>");
						}
					}
				%>
              </select>
            </td>
            <td width="47%" align="left" valign="top" height="32">
              <input type="text" name="newBusinessContract" onChange="setBCSelection()" size="15" maxlength="15">
            </td>
          </tr>
          <tr>
            <td colspan="3" align="left" valign="top" height="45">New Company
              Structure:
              <input type="text" name="newCompanyStructure" size="75">
            </td>
          </tr>
          <tr>
            <td width="31%" height="49" valign="top">Mode: Company Structure</td>
            <td width="22%" height="49">&nbsp;</td>
            <td width="47%" height="49" align="center" valign="middle">
              <input type="button" name="apply"  value="  Apply  " onClick="applyCompanyStructure();">
              <input type="button" name="save"   value="  Save   " onClick="saveCompanyStructure();">
              <input type="button" name="cancel" value=" Cancel  " onClick="cancelCompanyStructure();">
              <input type="button" name="clear"  value="  Clear  " onClick="clearCompanyStructure();">
            </td>
          </tr>
        </table>
    </td>
  </tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->

<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="companyStructure">
</form>
<p>&nbsp;</p>
</body>
</html>
