<%@ page import="edit.common.CodeTableWrapper,
                 casetracking.CasetrackingNote,
                 edit.common.EDITDateTime,
                 fission.utility.Util,
                 edit.common.vo.*,
                 engine.*"%>
<!--
 * User: sdorman
 * Date: Jun 5, 2008
 * Time: 4:17:04 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

 <!--
 This dialog is displayed when the user creates a new ProductStructure.  It allows the user clone security using the
 settings from an existing ProductStucture or to save the new ProductStucture without cloning.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ProductStructure[] productStructures = ProductStructure.findAllProductType();

    String cloneFromProductStructurePK = "";

    //  Variables from calling page that need to be preserved
    String companyName = (String) request.getAttribute("companyName");
    String policyNumberPrefix = (String) request.getAttribute("policyNumberPrefix");
    String policyNumberSuffix = (String) request.getAttribute("policyNumberSuffix");
    String policyNumberSequenceNumber = (String) request.getAttribute("policyNumberSequenceNumber");
    String policySequenceLength = (String) request.getAttribute("policySequenceLength");
    String billingCompanyNumber = (String) request.getAttribute("billingCompanyNumber");
    String marketingPackageName = (String) request.getAttribute("marketingPackageName");
    String groupProductName = (String) request.getAttribute("groupProductName");
    String areaName = (String) request.getAttribute("areaName");
    String businessContractName = (String) request.getAttribute("businessContractName");
    String typeCodeSelect = (String) request.getAttribute("typeCodeSelect");
    String activeProductStructurePK = (String) request.getAttribute("activeProductStructurePK");
    String externalProductName = (String) request.getAttribute("externalProductName");
    String groupTypeSelect = (String) request.getAttribute("groupTypeSelect");
    String productTypeSelect = (String) request.getAttribute("productTypeSelect");
    String hedgeFundInterimAccountFK = (String) request.getAttribute("hedgeFundInterimAccountFK");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - New Product Structure Save</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    function saveProductStructure()
    {
        sendTransactionAction("ProductStructureTran", "saveProductStructure", "main");

        window.close();
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table width="100%" border="0" cellspacing="0">
<%--    BEGIN Form Content --%>
    <tr>
      <td>
        <table width="100%" cellspacing="0">
          <tr>
            <td>
            To clone security settings from an existing Product Structure, select one before saving:
            </td>
          </tr>
          <tr>
            <td> &nbsp; </td>
          </tr>
          <tr>
            <td align="left" nowrap >
              <select name="cloneFromProductStructurePK" >
                <%
                  out.println("<option>Please Select</option>");

                  for(int i=0; i< productStructures.length; i++)
                  {
                      String currentProductStructurePK = productStructures[i].getProductStructurePK() + "";

                      if (cloneFromProductStructurePK.equals(currentProductStructurePK))
                      {
                          out.println("<option selected value=\""+ currentProductStructurePK + "\">" + Util.getProductStructure((ProductStructureVO) productStructures[i].getVO(), ",") + "</option>");
                      }
                      else
                      {
                          out.println("<option value=\""+ currentProductStructurePK + "\">" + Util.getProductStructure((ProductStructureVO) productStructures[i].getVO(), ",") + "</option>");
                      }
                  }
               %>
              </select>
            </td>
          </tr>
        </table>
      </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value=" Save " onClick="saveProductStructure()">
        </td>
    </tr>
</table>


<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="cloneFromProductStructurePK" value="<%= cloneFromProductStructurePK %>">

<input type="hidden" name="companyName" value="<%= companyName %>">
<input type="hidden" name="policyNumberPrefix" value="<%= policyNumberPrefix %>">
<input type="hidden" name="policyNumberSuffix" value="<%= policyNumberSuffix %>">
<input type="hidden" name="policyNumberSequenceNumber" value="<%= policyNumberSequenceNumber %>">
<input type="hidden" name="policySequenceLength" value="<%= policySequenceLength %>">
<input type="hidden" name="billingCompanyNumber" value="<%= billingCompanyNumber %>">
<input type="hidden" name="marketingPackageName" value="<%= marketingPackageName %>">
<input type="hidden" name="groupProductName" value="<%= groupProductName %>">
<input type="hidden" name="areaName" value="<%= areaName %>">
<input type="hidden" name="businessContractName" value="<%= businessContractName %>">
<input type="hidden" name="typeCodeSelect" value="<%= typeCodeSelect %>">
<input type="hidden" name="activeProductStructurePK" value="<%= activeProductStructurePK %>">
<input type="hidden" name="externalProductName" value="<%= externalProductName %>">
<input type="hidden" name="groupTypeSelect" value="<%= groupTypeSelect %>">
<input type="hidden" name="productTypeSelect" value="<%= productTypeSelect %>">
<input type="hidden" name="hedgeFundInterimAccountFK" value="<%= hedgeFundInterimAccountFK %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>