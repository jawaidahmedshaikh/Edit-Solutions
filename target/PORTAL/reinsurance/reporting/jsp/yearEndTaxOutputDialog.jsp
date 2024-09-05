<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.vo.*" %>

<html>
<head>
<script language="Javascript1.2">

	function init() {

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return 1.50 * document.all.summaryTable.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.75 * document.all.summaryTable.offsetHeight;
	}

	function closeDialog() {

		window.close();
	}

</script>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" onLoad="init()">
    <span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:48%; top:0; left:0; z-index:0; overflow:scroll">
        <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
         <tr>
            <%
                YearEndTaxVO[] yeTaxVOs = (YearEndTaxVO[]) request.getAttribute("yeTaxVO");

                for (int i = 0; i < yeTaxVOs.length; i ++) {

                    String clientAddressLine1 = yeTaxVOs[i].getClientAddressLine1();
                    String clientAddressLine2 = yeTaxVOs[i].getClientAddressLine2();
                    String clientAddressLine3 = yeTaxVOs[i].getClientAddressLine3();
                    String clientAddressLine4 = yeTaxVOs[i].getClientAddressLine4();
                    String clientCity         = yeTaxVOs[i].getCity();
                    String clientCountry      = yeTaxVOs[i].getCountry();
                    String clientFilingStatus = yeTaxVOs[i].getFilingStatus();
                    String clientFirstName    = yeTaxVOs[i].getClientFirstName();
                    String clientLastName     = yeTaxVOs[i].getClientLastName();
                    String clientMiddleName   = yeTaxVOs[i].getClientMiddleName();
                    String clientState        = yeTaxVOs[i].getState();
                    String clientZipCode      = yeTaxVOs[i].getZipCode();
                    String companyName        = yeTaxVOs[i].getCompanyName();
                    String contractId         = yeTaxVOs[i].getContractNumber();
                    String fromDate           = yeTaxVOs[i].getFromDate();
                    String taxQualifier       = yeTaxVOs[i].getTaxQualifier();
                    String taxYear            = yeTaxVOs[i].getTaxYear() + "";
                    String toDate             = yeTaxVOs[i].getToDate();

                    TaxFormVO[] taxFormVOs    = yeTaxVOs[i].getTaxFormVO();

                    if (taxFormVOs != null){

                      for (int j = 0; j < taxFormVOs.length; j ++) {

                        String cityWithholding    = taxFormVOs[j].getCityWithholding() + "";
                        String countyWithholding  = taxFormVOs[j].getCountyWithholding() + "";
                        String federalWithholding = taxFormVOs[j].getFederalWithholding() + "";
                        String stateWithholding   = taxFormVOs[j].getStateWithholding() + "";

                        String distributionCode   = taxFormVOs[j].getDistributionCode();
                        String grossAmount        = taxFormVOs[j].getGrossAmount() + "";
                        String marketValue        = taxFormVOs[j].getMarketValue() + "";
                        String premiumType        = taxFormVOs[j].getPremiumType() + "";
                        String taxableBenefit     = taxFormVOs[j].getTaxableBenefit() + "";
                        String taxFormName        = taxFormVOs[j].getTaxFormName();
            %>
                            <!-- Diplay the owner row no matter what //-->
                            <tr>
                                <td nowrap>
                                    <%= contractId %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= clientLastName + clientFirstName %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= clientAddressLine1 %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= clientAddressLine2 %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= clientAddressLine3 %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= clientAddressLine4 %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= clientCity + "," + clientState %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%=clientFilingStatus %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%=taxQualifier %>
                                </td>
                            <tr>
                                <td nowrap>
                                    <%= taxYear %>
                                </td>
                            </tr>
                            <tr>
                                <td nowrap>
                                    <%= fromDate %>
                                </td>
                            <tr>
                                <td nowrap>
                                    <%= toDate %>
                                </td>
                            </tr>
                                <tr>
                                    <td nowrap>
                                        <%= taxFormName %>
                                    </td>
                                    <td nowrap>
                                        <%= premiumType %>
                                    </td>
                                    <td nowrap>
                                        <%= distributionCode %>
                                    </td>
                                    <td nowrap>
                                        <%= grossAmount %>
                                    </td>
                                    <td nowrap>
                                        <%= taxableBenefit %>
                                    </td>
                                    <td nowrap>
                                        <%= federalWithholding%>
                                    </td>
                                     <td nowrap>
                                        <%= stateWithholding%>
                                    </td>
                                     <td nowrap>
                                        <%= countyWithholding %>
                                    </td>
                                     <td nowrap>
                                        <%= cityWithholding%>
                                    </td>
                                     <td nowrap>
                                        <%= marketValue %>
                                    </td>
                              </tr>

                          </tr>
                    <%
                        }  //ends for TaxForm
                     }//ends if
                     }// end for
                   %>
         <tr>
         <tr>
          <input type="button" name="close" value="Close" onClick="closeDialog()">
         </tr>
		</table>
       </span>

</body>
</html>
