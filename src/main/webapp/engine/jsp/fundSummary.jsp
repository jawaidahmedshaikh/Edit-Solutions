<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 edit.common.CodeTableWrapper"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    // User selected items...
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] fundTypeCTVOs = codeTableWrapper.getCodeTableEntries("FUNDTYPE");

    CodeTableVO[] portfolioCTVOs = codeTableWrapper.getCodeTableEntries("PORTFOLIO");

    CodeTableVO[] typeCodeCTs = codeTableWrapper.getCodeTableEntries("TYPECODE");
    CodeTableVO[] loanQualifierCTs = codeTableWrapper.getCodeTableEntries("LOANQUALIFIER");

    FundVO fundVO = (FundVO) request.getAttribute("selectedFundVO");

    String fundName = "";
    String fundType = "";
    String portfolio = "";
    String shortName = "";
    String excludeFromActivityFileInd = "";
    String typeCode = null;
    String reportingFundName = "";
    String loanQualifierCT = "";

    String fundPK   = "";

    if (fundVO != null)
    {
        fundPK   = fundVO.getFundPK() + "";
        fundName = fundVO.getName();
        shortName = Util.initString(fundVO.getShortName(), "");
        if (fundVO.getExcludeFromActivityFileInd() != null && fundVO.getExcludeFromActivityFileInd().equalsIgnoreCase("Y"))
        {
            excludeFromActivityFileInd = "checked";
        }
        else
        {
            excludeFromActivityFileInd = "unchecked";
        }

        fundType = fundVO.getFundType();
        portfolio = fundVO.getPortfolioNewMoneyStatusCT();
        if (portfolio == null)
        {
            portfolio = "";
        }

        typeCode = Util.initString(fundVO.getTypeCodeCT(), "");
        reportingFundName = Util.initString(fundVO.getReportingFundName(), "");
        loanQualifierCT = Util.initString(fundVO.getLoanQualifierCT(), "");
    }

    // Summary items...
    FundVO[] fundVOs = (FundVO[]) request.getAttribute("fundVOs");

    // Sort them...
    fundVOs = (FundVO[]) Util.sortObjects(fundVOs, new String[]{"getName"});

    if (fundVOs == null)
    {
        fundVOs = new FundVO[0];
    }
%>
<%!
    private String initValue(String value)
    {
        if (value != null)
        {
            return value;
        }
        else
        {
            return "";
        }
    }

    private String[] initValues(String[] values)
    {
        if (values != null)
        {
            return values;
        }
        else
        {
            return new String[0];
        }
    }
%>

<html>
<head>
<title>fundSummary.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f           = null;
    var previousRow = null;
    var tdElement = null;
    var currentRow = null;

    function init()
    {
	    f = document.theForm;

        previousRow = document.getElementById("<%= fundPK %>");

        if (previousRow != null)
        {
            previousRow.scrollIntoView(false);
        }
    }

    function showFundDetails()
    {
        tdElement = window.event.srcElement;
        currentRow = tdElement.parentElement;

        currentRow.style.backgroundColor="#FFFFCC";

        if (currentRow != null)
        {
            currentRow.scrollIntoView(false);
        }

        f.fundPK.value = currentRow.id;

        sendTransactionAction("FundTran", "showFundDetails", "main");
    }

    function addNewFund()
    {
        sendTransactionAction("FundTran", "addNewFund", "main");
    }

    function saveFund()
    {
        if (f.excludeFromActivityFileIndStatus.checked == true)
        {
            f.excludeFromActivityFileInd.value = "Y";
        }
        else
        {
            f.excludeFromActivityFileInd.value = "N";
        }

        if (f.typeCodeSelect.value  < 1)
        {
            alert("Type Code Required");
        }
        else
        {
            sendTransactionAction("FundTran", "saveFund", "main");
        }
    }

    function cancelFund()
    {
        sendTransactionAction("FundTran", "cancelFundChanges", "main");
    }

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>

<!-- ****** STYLE CODE ***** //-->
<style>

	.highLighted {

		background-color: #FFFFCC;
	}

	.dummy {
	}


</style>

</head>

<body class="mainTheme" onLoad="init()">

<br>

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table class="formData" width="100%" height="20%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="right" nowrap>Fund Name:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="fundName" size="50" maxSize="50" value="<%= fundName %>">
      </td>
      <td align="right" nowrap>Short Name:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="shortName" size="25" maxSize="25" value="<%= shortName %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Reporting Fund Name:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input type="text" name="reportingFundName" size="150" maxSize="150" value="<%= reportingFundName %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Fund Type:&nbsp;</td>
      <td align="left" nowrap>
  	    <select name="fundType">
		<%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < fundTypeCTVOs.length; i++)
            {
                String codeTablePK = fundTypeCTVOs[i].getCodeTablePK() + "";
                String codeDesc = fundTypeCTVOs[i].getCodeDesc();
                String code = fundTypeCTVOs[i].getCode();

                if (fundType.equalsIgnoreCase(code))
                {
                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else
                {
                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }
	    %>
		</select>
      </td>
      <td align="right" nowrap>Portfolio/New Money:&nbsp;</td>
      <td align="left" nowrap>
  		<select name="portfolio">
		<%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < portfolioCTVOs.length; i++)
            {
                String codeDesc = portfolioCTVOs[i].getCodeDesc();
                String code = portfolioCTVOs[i].getCode();

                if (portfolio.equalsIgnoreCase(code))
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
      <td align="right" nowrap>Type Code:&nbsp;</td>
      <td align="left" nowrap>
        <select name="typeCodeSelect" >
        <option name="id" value="-1">Please Select</option>
        <%
            if (typeCodeCTs != null)
            {
                for(int i = 0; i < typeCodeCTs.length; i++)
                {
                    String code = typeCodeCTs[i].getCode();
                    String codeDesc = typeCodeCTs[i].getCodeDesc();

                    if ( (typeCode != null) && (typeCode.equals(code)))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            }
        %>
        </select>
      </td>
      <td align="left" nowrap colspan="2">Exclude From Controls And Balances&nbsp;
        <input type="checkbox" name="excludeFromActivityFileIndStatus" <%= excludeFromActivityFileInd %>>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Loan Qualifier:&nbsp;</td>
      <td align="left" nowrap>
  	    <select name="loanQualifierCT">
		<%
            out.println("<option selected>Please Select</option>");

            for(int i = 0; i < loanQualifierCTs.length; i++)
            {
                String codeDesc = loanQualifierCTs[i].getCodeDesc();
                String code = loanQualifierCTs[i].getCode();

                if (loanQualifierCT.equalsIgnoreCase(code))
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
    </tr>
  </table>

  <br><br>

  <span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
      <tr height="5%">
        <td align="left" height="14">
          <input type="button" name="add" value= " Add  " onClick="addNewFund()">
          <input type="button" name="save" value=" Save " onClick="saveFund()">
		  <input type="button" name="cancel" value="Cancel" onClick="cancelFund()">
	    </td>
      </tr>
	</table>
  </span>

  <table id="summaryTable" class="summary" width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#30548E" height="10">
      <th align="left" width="25%"><font color= "#FFFFFF">Fund Name</font></th>
      <th align="left" width="25%"><font color= "#FFFFFF">Fund Type</font></th>
      <th align="left" width="25%"><font color= "#FFFFFF">Portfolio/New Money</font></th>
      <th align="left" width="25%"><font color= "#FFFFFF">Type Code</font></th>
    </tr>

    <tr>
      <td height="98%" colspan="4">
        <span class="scrollableContent">
          <table id="summaryTable" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
		    <%
		        String trClass = "";
			    String selected = "";
                String currentKey = "";

                if (fundVOs != null)
                {
                    for (int i = 0; i < fundVOs.length; i++)
                    {
                        currentKey = fundVOs[i].getFundPK() + "";

                        String sFundType = fundVOs[i].getFundType();
                        String sPortfolio = fundVOs[i].getPortfolioNewMoneyStatusCT();
                        String sTypeCode = Util.initString(fundVOs[i].getTypeCodeCT(), "");
                        if (sPortfolio == null)
                        {
                            sPortfolio = "";
                        }

                        if (currentKey.equals(fundPK))
                        {
                            trClass = "highLighted";
                            selected = "true";
                        }
                        else
                        {
                            trClass = "dummy";
                            selected="false";
                        }
            %>
            <tr class="<%= trClass %>" selected="<%= selected %>" id="<%= currentKey %>" onClick="showFundDetails()">
              <td nowrap width="25%">
                <%= fundVOs[i].getName() %>
              </td>
              <td nowrap width="25%">
                <%= sFundType %>
              </td>
              <td nowrap width="25%">&nbsp;
                <%= sPortfolio %>
              </td>
              <td nowrap width="25%">&nbsp;&nbsp;
               <%= sTypeCode %>
              </td>      </tr>
            <%
                    } // end for
                }// end if
		    %>
            <tr class="filler">
              <td colspan="4">&nbsp;</td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="fundPK" value="<%= fundPK %>">
<input type="hidden" name="excludeFromActivityFileInd" value="">

</form>
</body>
</html>
