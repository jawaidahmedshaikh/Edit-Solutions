<!--
 * User: cgleason
 * Date: Jan. 6, 2006
 * Time: 11:24:17 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->


<%@ page import="fission.utility.Util,
                 engine.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.portal.taglib.*,
                 edit.portal.exceptions.*"%>
<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%-- ****************************** BEGIN Java Code ******************************--%>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String responseMessage = (String) request.getAttribute("responseMessage");

    String pageName = request.getParameter("pageName");
    String pageHeader = "Case Add";
    if (pageName.equals("group"))
    {
        pageHeader = "Group Add";
    }

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = editingException == null ? "false" : "true";

    CodeTableVO[] taxTypes = codeTableWrapper.getCodeTableEntries("TAXIDTYPE");
    CodeTableVO[] contactTypes = codeTableWrapper.getCodeTableEntries("CONTACTTYPE");
    CodeTableVO[] states            = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] sicCodes            = codeTableWrapper.getCodeTableEntries("SICCODE");

    CodeTableVO[] trustTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("TRUSTTYPE");

    String corporateName = Util.initString((String) request.getAttribute("corporateName"), "");
    String quickAddTaxId = Util.initString((String) request.getAttribute("quickAddTaxId"), "");
    String taxTypeId     = Util.initString((String) request.getAttribute("taxTypeId"), "");
    String trustTypeId   = Util.initString((String) request.getAttribute("trustTypeId"), "");

    String addressLine1  = Util.initString((String) request.getAttribute("addressLine1"), "");
    String addressLine2  = Util.initString((String) request.getAttribute("addressLine2"), "");
    String addressLine3  = Util.initString((String) request.getAttribute("addressLine3"), "");
    String addressLine4  = Util.initString((String) request.getAttribute("addressLine4"), "");
    String city          = Util.initString((String) request.getAttribute("city"), "");
    String areaId        = Util.initString((String) request.getAttribute("areaId"), "");
    String zipCode       = Util.initString((String) request.getAttribute("zipCode"), "");

    String contactName   = Util.initString((String) request.getAttribute("contactName"), "");
    String contactTypeId = Util.initString((String) request.getAttribute("contactTypeId"), "");
    String phoneEmail    = Util.initString((String) request.getAttribute("phoneEmail"), "");
    String sicCodeId     = Util.initString((String) request.getAttribute("sicCodeId"), "");
    String groupID     = Util.initString((String) request.getAttribute("groupID"), "");


%>

<%-- ****************************** End Java Code ******************************--%>
<html>
    <head>
        <title>EDIT SOLUTIONS - <%= pageHeader %></title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
        <meta http-equiv="Cache-Control" content="no-store"></meta>
        <meta http-equiv="Pragma" content="no-cache"></meta>
        <meta http-equiv="Expires" content="0"></meta>
        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet"
              type="text/css"></link>
        <%-- ****************************** BEGIN JavaScript
             ******************************--%>
        <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
        <script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
        <script src="/PORTAL/common/javascript/scrollTable.js"></script>
        <script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var editingExceptionExists = "<%= editingExceptionExists %>";


    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        // This should never default to anything but nothing!
        f.contractGroupNumber.value = "";
        f.groupID.value = "";

        checkForResponseMessage();

        checkForEditingException();

        // Initialize scroll tables
        initScrollTable(document.getElementById("CaseClientSearchTableModelScrollTable"));
    }

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            sendTransactionAction("CaseDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function showClientAfterSearch(clientDetailPK)
    {
        f.clientDetailPK.value = clientDetailPK;

        f.companyStructure.value = f.companyStructureId.options[f.companyStructureId.selectedIndex].text;

        sendTransactionAction("CaseDetailTran", "showClientAfterSearch", "_self");

        window.close();
    }

    function doSearch()
    {
        var tdSearchMessage = document.getElementById("searchMessage");

        sendTransactionAction("CaseDetailTran", "searchClients", "_self");
    }

    /**
    * Accepts the specified Client and Case # to begin buildng a new Case.
    */
    function saveAddDialog()
    {
        if (validateForm(f, 'REQUIRED'))
        {
          sendTransactionAction("CaseDetailTran", "saveAddDialog", "main");

          window.close();
        }
    }

    function checkForEnter()
    {
        var eventObj = window.event;

        if (eventObj.keyCode == 13)
        {
            doSearch();
        }
    }

    /**
    * While adding a Case, the desired Client may not be in the system.
    * This will open our standard Client pages for a quick add.
    */
    function quickAddClient()
    {
        sendTransactionAction("CaseDetailTran", "addClientThroughQuickAdd", "_self");
    }

</script>
<%-- ****************************** END JavaScript ******************************--%>
    </head>
    <body class="mainTheme" onload="init()"><form name="theForm" method="post"
                                                  action="/PORTAL/servlet/RequestManager">

   <%-- ****************************** BEGIN Form Data ******************************--%>
            <table class="formData" width="100%" border="0" cellspacing="0"
                   cellpadding="5">
                <tr>
                    <td align="left" nowrap="nowrap">Tax Identification:&nbsp;</td>
                    <td align="left" nowrap="nowrap" colspan="3">
                        <input type="text" name="taxId" size="20" maxlength="11"
                               onkeypress="checkForEnter()"
                               onfocus="f.caseName.value=''"></input>
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap="nowrap">Name:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="caseName" size="30"
                               maxlength="30" onkeypress="checkForEnter()"
                               onfocus="f.taxId.value=''"></input>
                    </td>
                    <td align="right" nowrap="nowrap">&nbsp;
<% 
    if (pageName.equals("group"))
    {
%>    
                        <input type="checkbox" name="defaultClientFromCase"/> Default Client from 'Case'
<%
    }
%>
                    </td>
                    <td align="right" nowrap="nowrap">
                        <input type="button" value=" Search  "
                               onclick="doSearch()"/>
                    </td>
                </tr>
            </table>
         <%-- ****************************** END Form Data ******************************--%>
            <br></br>
         <%-- ****************************** BEGIN Summary Area ******************************--%>
         <%-- Summary--%>
            <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="CaseClientSearchTableModel"/>
                <jsp:param name="tableHeight" value="20"/>
                <jsp:param name="multipleRowSelect" value="false"/>
                <jsp:param name="singleOrDoubleClick" value="single"/>
            </jsp:include>
         <%-- ****************************** END Summary Area ******************************--%>
            <br></br>
            <table class="formData" width="100%" border="0" cellspacing="0"
                   cellpadding="5">
                <tr>
                    <td align="right" nowrap="nowrap">Corporate Name:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="corporateName" value="<%= corporateName %>" TABINDEX=1 size="35" maxlength="60"/>
                    </td>
                    <td align="left" nowrap="nowrap">Tax Identification:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="quickAddTaxId" value="<%= quickAddTaxId %>" TABINDEX=9 size="11" maxlength="11"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap="nowrap">Address (line 1):&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="addressLine1" value="<%= addressLine1 %>" TABINDEX=2 size="35" maxlength="35"/>
                    </td>
                    <td align="left" nowrap="nowrap">Tax Id Type:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <select name="taxTypeId" TABINDEX=10 >
                        <option value="null">Please Select</option>
                          <%
                              for(int i = 0; i < taxTypes .length; i++) {

                                  String codeTablePK = taxTypes[i].getCodeTablePK() + "";
                                  String codeDesc    = taxTypes[i].getCodeDesc();
                                  String code        = taxTypes[i].getCode();

                                  if (taxTypeId.equals(code))
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
                    <td align="right" nowrap="nowrap">(line 2):&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="addressLine2" value="<%= addressLine2 %>" TABINDEX=3 size="35" maxlength="35"/>
                    </td>
                    <td align="left" nowrap="nowrap">&nbspType:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <select name="trustTypeId" TABINDEX=10 >
                        <option value="null">Please Select</option>
                          <%
                              for(int i = 0; i < trustTypes .length; i++) {

                                  String codeTablePK = trustTypes[i].getCodeTablePK() + "";
                                  String codeDesc    = trustTypes[i].getCodeDesc();
                                  String code        = trustTypes[i].getCode();

                                  if (trustTypeId.equals(code))
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
                    <td align="right" nowrap="nowrap">(line 3):&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="addressLine3" value="<%= addressLine3 %>" TABINDEX=4 size="35" maxlength="35"/>
                    </td>
                    <td align="left" nowrap="nowrap">Contact Name:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="contactName" value="<%= contactName %>" TABINDEX=12 size="50" maxlength="50"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap="nowrap">(line 4):&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="addressLine4" value="<%= addressLine4 %>" TABINDEX=5 size="35" maxlength="35"/>
                    </td>
                    <td align="left" nowrap="nowrap">Contact Type:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <select name="contactTypeId" TABINDEX=13>
                        <option value="null">Please Select</option>
                          <%

                            for(int i = 0; i < contactTypes.length; i++) {

                                String codeTablePK = contactTypes[i].getCodeTablePK() + "";
                                String codeDesc    = contactTypes[i].getCodeDesc();
                                String code    = contactTypes[i].getCode();

                                if (contactTypeId.equals(code))
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
                    <td align="right" nowrap="nowrap">City:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                        <input type="text" name="city" value="<%= city %>" TABINDEX=6 size="35" maxlength="35"/>
                    </td>
                    <td align="left" nowrap="nowrap">Phone/Email:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <input type="text" name="phoneEmail" value="<%= phoneEmail %>" TABINDEX=14 maxlength="65" size="50"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap="nowrap">State:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <select name="areaId" TABINDEX=7>
                        <option value="null">Please Select</option>
                          <%

                            for(int i = 0; i < states.length; i++) {

                                String codeTablePK = states[i].getCodeTablePK() + "";
                                String codeDesc    = states[i].getCodeDesc();
                                String code        = states[i].getCode();

                                if (areaId.equals(code))
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

<%
    if (!pageName.equals("group"))
    {
%>
                    <td align="left" nowrap="nowrap">SICCode:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <select name="sicCodeId" TABINDEX=15>
                        <option value="null">Please Select</option>
                          <%

                            for(int i = 0; i < sicCodes.length; i++) {

                                String codeTablePK = sicCodes[i].getCodeTablePK() + "";
                                String codeDesc    = sicCodes[i].getCodeDesc();
                                String code        = sicCodes[i].getCode();

                                if (sicCodeId.equals(code))
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
<%
    }
    else
    {
%>
                    <td align="left" nowrap="nowrap" colspan="2">&nbsp;</td>

<%
    }
%>

                </tr>
                <tr>
                    <td align="right" nowrap="nowrap">Zip Code:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <input type="text" name="zipCode" value="<%= zipCode %>" TABINDEX=8 maxlength="15" size="15"/>
                    </td>
                    <td align="left" nowrap="nowrap" colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td align="right" nowrap="nowrap" colspan="4">
                        <input type="button" value="Quick Add" onclick="quickAddClient()"/>
                    </td>
                </tr>
            </table>
            <br></br>
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                    <td align="right" nowrap="nowrap" width="15%">
                        <span class="requiredField">*</span>
                        Case/Group Number:
                    </td>
                    <td align="left" nowrap="nowrap" >
                        <input:text name="contractGroupNumber"
                                    attributesText="id=\'contractGroupNumber\' REQUIRED"
                                    size="20"/>
                    </td>
                    <td align="left">
                        <span class="requiredField">* = required fields</span>
                    </td>
                    <td align="right" nowrap="nowrap" >
                        Group ID:
                    </td>
                    <td align="left" nowrap="nowrap" >
                        <input:text name="groupID"
                                    attributesText="id=\'groupID\'"
                                    size="20"/>
                    </td>
                    <td align="right">
                        <input type="button" value=" Enter  "
                               onclick="saveAddDialog()"/>
                        <input type="button" value=" Cancel  "
                               onclick="window.close();"/>
                        &nbsp;
                    </td>
                </tr>
            </table>

      <%-- ****************************** BEGIN Hidden Variables ******************************--%>
            <input type="hidden" name="transaction"></input>
            <input type="hidden" name="action"></input>
<%--            <input type="hidden" name="effective"></input>--%>
            <input type="hidden" name="clientDetailPK" value=""></input>
            <input type="hidden" name="pageName" value="<%= pageName %>"></input>
      <%-- ****************************** END Hidden Variables ******************************--%>
        </form></body>
</html>