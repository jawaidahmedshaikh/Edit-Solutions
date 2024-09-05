<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="group.*, client.*, edit.portal.widgettoolkit.*, edit.common.vo.*, edit.common.*, edit.portal.taglib.*, role.*"%>
      <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
          <jsp:param name="tableId"
                     value="EnrollmentLeadServiceAgentTableModel"/>
          <jsp:param name="tableHeight" value="93"/>
          <jsp:param name="multipleRowSelect" value="false"/>
          <jsp:param name="singleOrDoubleClick"
                     value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
        </jsp:include>
      <table width="100%">
            <tr>
                <td align="right">
                    <input type="button" value="Close" onclick="window.close()"/>        
                </td>
            </table>
