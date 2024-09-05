<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="fission.utility.*, group.*, edit.portal.common.session.*"%>
<%
  UserSession userSession = (UserSession) session.getAttribute("userSession");

  String activeCasePK = userSession.getParameter("activeCasePK");

  ContractGroup activeCase = null;
  
  String caseName = "N/A";
  
  String caseNumber = "N/A";
  
  if (activeCasePK != null)
  {
    activeCase = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));
    
    caseName = activeCase.getOwnerClient().getName();
    
    caseNumber = activeCase.getContractGroupNumber();
  }
%>
<script type="text/javascript">

  /**
   * Performs requirements checking first (inlined in the calling event). Upon passing,
   * executes a generic save to save the current page
   * by bypassing any backend business rules. The intention
   * us to allow the user to benefit from a partial save.
   */
<%--  function saveCurrentPage()--%>
<%--  {   --%>
<%--    sendTransactionAction("CaseDetailTran", "saveCurrentPage", "main");--%>
<%--  }--%>
</script>
<table cellspacing="0" cellpadding="0" width="100%"
       style="border-style:solid; border-color:rgb(0,0,0); border-width:1.0px;">
  <tr>
    <td align="right">Case Name:</td>
    <td align="left">
      <%= caseName %>
    </td>
    <td align="right">Case Number:</td>
    <td align="left">
      <%= caseNumber %>
    </td>
<%--    <td align="right">--%>
<%--      <input type="button" value="Save"--%>
<%--             title="Save the current tab."--%>
<%--             onclick="if (validateForm(this.form, 'REQUIRED')){saveCurrentPage()}"/>--%>
<%--    </td>--%>
  </tr>
</table>