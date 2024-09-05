<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="java.net.*, java.util.*, engine.dm.*, fission.global.*, fission.beans.*,
                 edit.common.vo.*,
                 fission.utility.*,
                 engine.*" %>

<%
    ProductStructureVO[] attachedProductStructureVOs = (ProductStructureVO[]) request.getAttribute("attachedProductStructureVOs");

    RulesVO[] attachedRulesVOs  =(RulesVO[]) request.getAttribute("attachedRulesVOs");

    String productStructurePK = (String) request.getAttribute("productStructurePK");


%>

<html>
<head>
<title>Please Select Company and Rule Structure</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">


<!-- ****** JAVAScript CODE ***** //-->

<script language="Javascript1.2">

    var f = null;

    function init() {

        f = document.selectKeyForm;
	}

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value = transaction;
        f.action.value 		= action;
    	f.target			= target;
	    f.submit();
    }

    function setKey() {

        if (f.rulesPK.selectedIndex == 0)
        {
            alert("Company Structure And Rule Required");

            return false;
        }

        sendTransactionAction("ParamTran", "setKey", "main");

        window.close();
	}

    function showAttachedRules()
    {
        sendTransactionAction("ParamTran", "showAttachedRules", "_self");
    }

    function bCancel() {

        window.close();
    }

</script>
</head>

<!-- ****** HTML CODE ***** //-->

<body class="dialog" onLoad="init()">
<form name="selectKeyForm" action="/PORTAL/servlet/RequestManager">

  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <td align="left">
      Company Structure: <br>
       <select name="productStructurePK" onChange="showAttachedRules()">
       <option value="-1">
          Please Select
       </option>

       <%
           if (attachedProductStructureVOs != null)
           {
                attachedProductStructureVOs = (ProductStructureVO[]) Util.sortObjects(attachedProductStructureVOs, new String[]{"getBusinessContractName"});

               for(int i = 0; i < attachedProductStructureVOs.length; i++) {

                   String currentProductStructurePK = attachedProductStructureVOs[i].getProductStructurePK() + "";
                   Company company = Company.findByPK(new Long(attachedProductStructureVOs[i].getCompanyFK()));

                   String companyName = company.getCompanyName();
                   String marketingPackageName = attachedProductStructureVOs[i].getMarketingPackageName();
                   String groupProductName = attachedProductStructureVOs[i].getGroupProductName();
                   String areaName = attachedProductStructureVOs[i].getAreaName();
                   String businessContractName = attachedProductStructureVOs[i].getBusinessContractName();

                   String companyStructure = companyName + "," + marketingPackageName + "," + groupProductName + "," + areaName + "," + businessContractName;

                   if (currentProductStructurePK.equals(productStructurePK)) {

                       out.println("<option selected value=\"" + currentProductStructurePK + "\">" + companyStructure + "</option>");
                   }
                   else  {

                       out.println("<option value=\"" + currentProductStructurePK + "\">" + companyStructure + "</option>");
                   }
               }
           }
       %>
	   </select>
	  </td>
   </tr>
  <tr>
      <td align="left">
      Rule Structure:<br>
        <select name="rulesPK">
		<option value="-1">
			Please Select
		</option>

		<%

            if (attachedRulesVOs != null)
            {
                attachedRulesVOs = (RulesVO[]) Util.sortObjects(attachedRulesVOs, new String[]{"getRuleName"});

                for(int i = 0; i < attachedRulesVOs.length; i++) {

                    String currentRulesPK = attachedRulesVOs[i].getRulesPK() + "";

                    String processName = attachedRulesVOs[i].getProcessName();
                    String eventName = attachedRulesVOs[i].getEventName();
                    String eventTypeName = attachedRulesVOs[i].getEventTypeName();
                    String ruleName = attachedRulesVOs[i].getRuleName();

                    String ruleStructure = processName + "," + eventName + "," + eventTypeName + "," + ruleName;

                    out.println("<option value=\"" + currentRulesPK + "\">" + ruleStructure + "</option>");
                }
            }
        %>

		</select>
      </td>
    </tr>
    <tr>
         <td colspan="2" align="right">
          <input type="button" name="enter" value="Enter" onClick="setKey()">
          <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
        </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

  </form>
</body>


</html>