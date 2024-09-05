<%@ page import="edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.ProductStructureVO,
                 fission.utility.Util,
                 fission.beans.PageBean"%>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<html>

<head>
<title>Change Coverage/Company Structure</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<%
	    PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

        String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        CodeTableVO[] options = codeTableWrapper.getCodeTableEntries("SEGMENTNAME");

        String   optionId	  = formBean.getValue("optionId");

        for (int o = 0; o < options.length; o++) {

            String ctCode = options[o].getCode();
            if (ctCode.equalsIgnoreCase(optionId)) {

                optionId = options[o].getCodeDesc();
                break;
            }
        }

        ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().getProductStructuresByTypeCode("Product");


%>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.coverageSelectionForm;
	}

    function selectCoverage(){

        if (f.optionId.value == "" ||
            f.optionId.value == "Please Select")
        {

            alert ("Coverage Must Be Selected");
            f.optionId.focus();
        }
        else
            if (f.companyStructureId.value == "" ||
                     f.companyStructureId.value ==  "Please Select")
            {
                     alert ("Company Structure Must Be Selected");
                     f.companyStructureId.focus();
            }

        else {

            sendTransactionAction("QuoteDetailTran", "changeCompanyStructureCoverage", "contentIFrame");

            window.close();
        }
    }

	function cancelDialog() {

		window.close();
	}

	function setCompanyStructure() {

		f.companyStructure.value = f.companyStructureId.options[f.companyStructureId.selectedIndex].text;
	}

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value= transaction;
        f.action.value= action;

        f.target = target;

        f.submit();
    }

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="coverageSelectionForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td nowrap align="left">Select Segment Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <select name="optionId">
          <option selected value="Please Select">Please Select</option>
            <%

                  for(int i = 0; i < options.length; i++) {

                      String codeTablePK = options[i].getCodeTablePK() + "";
                      String codeDesc    = options[i].getCodeDesc();
                      String code        = options[i].getCode();

                      if (optionId.equals(codeDesc))
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
      <td align="left" nowrap>Select Company Structure:&nbsp;
        <select name="companyStructureId" tabindex="1" onClick="setCompanyStructure()">
	        <option value="Please Select"> Please Select </option>
	        <%

			for(int i=0; i< productStructureVOs.length; i++){

                String currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";

			if (companyStructureId.equals(currentProductStructurePK)) {

				out.println("<option selected value=\""+ currentProductStructurePK + "\">" + Util.getProductStructure(productStructureVOs[i], ",") + "</option>");
			}else

				out.println("<option value=\""+ currentProductStructurePK + "\">" + Util.getProductStructure(productStructureVOs[i], ",") + "</option>");
			}
	       %>

        </select>
      </td>


    </tr>
    <tr>
      <td align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="selectCoverage()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="companyStructure" value="">

</form>
</body>
</html>
