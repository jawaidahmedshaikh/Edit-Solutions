 <!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="edit.common.vo.*,
                 edit.common.EDITBigDecimal,
                 engine.Fee,
                 java.util.*,
                 edit.common.EDITDate,
                 fission.utility.*"%>

<%
    EDITDate date = DateTimeUtil.getEDITDateFromMMDDYYYY((String) request.getAttribute("date"));
    String month = date.getMonthName();
    String day = date.getFormattedDay();
    String year = date.getFormattedYear();

    String displayDate = month + " " + day + ", " + year;

    Hashtable assetsLiabilitiesReportInfo = (Hashtable) request.getAttribute("assetsLiabilitiesReportInfo");

    Map filteredFundMap = (Map) assetsLiabilitiesReportInfo.get("funds");

    Hashtable assets = (Hashtable) assetsLiabilitiesReportInfo.get("assets");

    Hashtable liabilities = (Hashtable) assetsLiabilitiesReportInfo.get("liabilities");
%>

<html>
<head>
<title>Asset - Liabilities Report (ALR)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

<!-- Formats a fee as currency, or &nsbp; if the fee is zilch. -->
function formatFee(theFee)
{
    var formattedFee = null;

    if (theFee == "0")
    {
        formattedFee = "&nbsp;";
    }
    else
    {
        formattedFee = formatAsCurrency(theFee);
    }

    return formattedFee;
}

</script>

</head>

<!-- ************* HTML Code ************* -->
<body>

  <h4 align="center">
    Asset - Liabilities Report (ALR) for <%= displayDate %>
  </h4>

  <table align="left" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="left" width="20%">Product Line</td>
      <td align="left" width="20%">Fund Code</td>
      <td align="left" width="20%">Asset - Cash</td>
      <td align="left" width="20%">Liabilities - Accruals</td>
      <td align="left" width="20%">Current Over/(Under) Investment Balance</td>
    </tr>
    <tr>
      <td align="left" colspan="17"><hr size="2" color="black" noshade></hr></td>
    </tr>
  <%
      String marketingPackage = "";
      String prevFundNumber = "";
      String fundNumber = "";
      String chargeCode = "";

      EDITBigDecimal totalAssets = new EDITBigDecimal();
      EDITBigDecimal totalLiabilities = new EDITBigDecimal();

      Set ffMapKeySet = filteredFundMap.keySet();
      Iterator it = ffMapKeySet.iterator();

      while (it.hasNext())
      {
          String key = (String) it.next();
          FilteredFundVO filteredFundVO = (FilteredFundVO) filteredFundMap.get(key);
          marketingPackage = key.substring(0, key.indexOf("_"));
          chargeCode = key.substring(key.indexOf("__") + 2);

          String filteredFundPK = filteredFundVO.getFilteredFundPK() + "";
          fundNumber = filteredFundVO.getFundNumber();
          String fundKey = fundNumber + "_" + filteredFundPK + "__" + chargeCode;

          if (prevFundNumber.equals(""))
          {
              prevFundNumber = fundNumber;
          }

          if (!fundNumber.equals(prevFundNumber))
          {
              totalAssets = totalAssets.round(2);
              totalLiabilities = totalLiabilities.round(2);
              EDITBigDecimal difference = totalAssets.subtractEditBigDecimal(totalLiabilities);
  %>
    <tr>
      <td align="left" width="20%"><%= marketingPackage %></td>
      <td align="left" width="20%"><%= prevFundNumber %></td>
      <td align="left" width="20%"><script>document.write(formatFee(<%= totalAssets %>))</script></td>
  <%
              if (totalLiabilities.isGT("0"))
              {
  %>
      <td align="left" width="20%">(<script>document.write(formatFee(<%= totalLiabilities %>))</script>)</td>
  <%
              }
              else
              {
                  totalLiabilities = totalLiabilities.multiplyEditBigDecimal("-1");
  %>
      <td align="left" width="20%"><script>document.write(formatFee(<%= totalLiabilities %>))</script></td>
  <%
              }

              if (difference.isLT("0"))
              {
                  difference = difference.multiplyEditBigDecimal("-1");
  %>
      <td align="left" width="20%">(<script>document.write(formatFee(<%= difference %>))</script>)</td>
  <%
      }
      else
      {
  %>
      <td align="left" width="20%"><script>document.write(formatFee(<%= difference %>))</script></td>
  <%
      }
  %>
    </tr>
  <%
              totalAssets = new EDITBigDecimal();
              totalLiabilities = new EDITBigDecimal();
              prevFundNumber = fundNumber;
          }

          totalAssets = totalAssets.addEditBigDecimal((EDITBigDecimal) assets.get(fundKey));
          totalLiabilities = totalLiabilities.addEditBigDecimal((EDITBigDecimal) liabilities.get(fundKey));
      }

      totalAssets = totalAssets.round(2);
      totalLiabilities = totalLiabilities.round(2);
      EDITBigDecimal difference = totalAssets.subtractEditBigDecimal(totalLiabilities);
  %>
    <tr>
      <td align="left" width="20%"><%= marketingPackage %></td>
      <td align="left" width="20%"><%= prevFundNumber %></td>
  <%
              if (totalAssets.isLT("0"))
              {
                  totalAssets = totalAssets.multiplyEditBigDecimal("-1");
  %>
      <td align="left" width="20%">(<script>document.write(formatFee(<%= totalAssets %>))</script>)</td>
  <%
              }
              else
              {
  %>
      <td align="left" width="20%"><script>document.write(formatFee(<%= totalAssets %>))</script></td>
  <%
              }

              if (totalLiabilities.isGT("0"))
              {
  %>
      <td align="left" width="20%">(<script>document.write(formatFee(<%= totalLiabilities %>))</script>)</td>
  <%
              }
              else
              {
                  totalLiabilities = totalLiabilities.multiplyEditBigDecimal("-1");
  %>
      <td align="left" width="20%"><script>document.write(formatFee(<%= totalLiabilities %>))</script></td>
  <%
              }

              if (difference.isLT("0"))
              {
                  difference = difference.multiplyEditBigDecimal("-1");
  %>
      <td align="left" width="20%">(<script>document.write(formatFee(<%= difference %>))</script>)</td>
  <%
      }
      else
      {
  %>
      <td align="left" width="20%"><script>document.write(formatFee(<%= difference %>))</script></td>
  <%
      }
  %>
    </tr>
  </table>

</body>
</html>
