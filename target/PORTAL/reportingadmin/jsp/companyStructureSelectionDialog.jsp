<%@ page import="edit.common.vo.*,
                 engine.*"%>
 <!-- ***** JAVA CODE ***** -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");
%>
<html>
<head>
<title>Select CompanyStructure.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- ****** JAVASCRIPT ***** //-->

<script language="Javascript1.2">

	var f 				= null;

	function init() {

		f = document.theForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

    function cancelDialog(){

        window.close();
    }

    function getPreferredWidth() {

		return 1.50 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.75 * document.all.table1.offsetHeight;
	}

    function filterByProductStructure(){

        var filteredProductStructurePK = f.filteredProductStructurePK.options[f.filteredProductStructurePK.selectedIndex].value;

        opener.top.frames["main"].window.frames["contentIFrame"].filterByProductStructure(filteredProductStructurePK);

        window.close();
    }

</script>
</head>

<body bgcolor="#FFFFFF" onLoad="init()">

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <table id="table1" width="100%" border="0" align="left">

        <tr>
            <td colspan="2">
                Company Structure:
            </td>
        </tr>

        <tr>
            <td colspan="2">
              <select name="filteredProductStructurePK">
              <option value="0" > Remove Filters</option>

              <%
                if (productStructureVOs != null){

                    for (int i = 0; i < productStructureVOs.length; i++){

                        String productStructurePK = productStructureVOs[i].getProductStructurePK() + "";

                        StringBuffer companyStructure = new StringBuffer();

                        Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
                        companyStructure.append(company.getCompanyName());
                        companyStructure.append(" ");
                        companyStructure.append(productStructureVOs[i].getMarketingPackageName());
                        companyStructure.append(" ");
                        companyStructure.append(productStructureVOs[i].getGroupProductName());
                        companyStructure.append(" ");
                        companyStructure.append(productStructureVOs[i].getAreaName());
                        companyStructure.append(" ");
                        companyStructure.append(productStructureVOs[i].getBusinessContractName());

                        out.println("<option value=" + productStructurePK + ">" + companyStructure.toString());
                    }
                }
              %>



              </select>
            </td>
        </tr>


        <tr align="center" valign="top">
            <td>
                <input type="button" name="enter" value="Enter" onClick="filterByProductStructure()">
                <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
            </td>
        </tr>
    </table>

<!-- ****** HIDDEN FIELDS ***** //-->

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">

</form>
</body>
</html>