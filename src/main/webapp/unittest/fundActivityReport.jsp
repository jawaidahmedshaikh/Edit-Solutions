<%@ page import="org.dom4j.Document,
                 org.dom4j.tree.DefaultDocument,
                 org.dom4j.Element,
                 org.dom4j.tree.DefaultElement,
                 reporting.component.ReportingComponent,
                 fission.utility.XMLUtil"%>
<!--
 * User: sprasad
 * Date: Mar 10, 2008
 * Time: 2:25:05 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<!--

-->

<%
    String startCycleDate = request.getParameter("startCycleDate");
    String endCycleDate = request.getParameter("endCycleDate");

    Element startCycleDateElement = new DefaultElement("StartCycleDate");
    Element endCycleDateElement = new DefaultElement("EndCycleDate");

    startCycleDateElement.setText(startCycleDate);
    endCycleDateElement.setText(endCycleDate);

    Element requestParametersElement = new DefaultElement("RequestParameters");

    requestParametersElement.add(startCycleDateElement);
    requestParametersElement.add(endCycleDateElement);

    Element serviceElement = new DefaultElement("Service");
    Element operationElement = new DefaultElement("Operation");

    serviceElement.setText("Reporting");
    operationElement.setText("generateFundActivityReport");

    Element segRequestElement = new DefaultElement("SEGRequestVO");
    segRequestElement.add(serviceElement);
    segRequestElement.add(operationElement);
    segRequestElement.add(requestParametersElement);

    Document segRequestDocument = new DefaultDocument();
    segRequestDocument.setRootElement(segRequestElement);

    Document segResponseDocument = new ReportingComponent().generateFundActivityReport(segRequestDocument);
%>

<html>
<head>
<title>Fund Activity Report</title>
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body>
<form name= "fundActivityReportForm">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td align="center">
      <h5>Fund Activity Report</h5>
    </td>
  </tr>
  <tr>
    <td>
      <fieldset>
      <legend>Fund Activity Report&nbsp;</legend>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" width="25%" nowrap>Start Cycle Date:</td>
          <td align="left" width="25%">
            <%= startCycleDate %>
          </td>
          <td>&nbsp;</td>
          <td align="right" width="25%" nowrap>End Cycle Date:</td>
          <td align="left" width="25%">
            <%= endCycleDate %>
          </td>
        </tr>
      </table>
      </fieldset>
    </td>
  </tr>
  <tr>
    <td align="left">
      <textarea name="fundActivityReport" rows="30" cols="120">
        <%= XMLUtil.prettyPrint(segResponseDocument) %>
      </textarea>
    </td>
  </tr>
</table>
</form>
</body>
</html>
