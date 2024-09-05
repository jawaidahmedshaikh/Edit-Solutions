<!--
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 12:37:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="edit.portal.widgettoolkit.TableModel"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.portal.widget.*" %>

<script type="text/javascript">

/**
 * Callback method after the page is rendered.
 */
function init()
{
    var selectedLogRowId = getSelectedRowId("LogTableModel");

    var scrollToRow = document.getElementById(selectedLogRowId);

    if (scrollToRow != null)
    {
        //the true value sets matching id at the top
        scrollToRow.scrollIntoView(true);
    }

    enableLogColumnButtons();
}

/**
 * If a LogTable row has been selected, then enable the columns buttons, otherwise
 * disable them.
 */
function enableLogColumnButtons()
{
    var selectedRowId = getSelectedRowId("LogTableModel");

    var disableButtons = true;

    if (!valueIsEmpty(selectedRowId) && (selectedRowId != "0"))
    {
        disableButtons = false;
    }

    document.all.addNewLogColumnId.disabled = disableButtons;

    document.all.saveNewLogColumnId.disabled = disableButtons;

    document.all.cancelNewLogColumnId.disabled = disableButtons;

    document.all.deleteNewLogColumnId.disabled = disableButtons;
}

/**
 * Brings-up a "dummy" new log entry in the table.
 */
function addNewLog()
{
    sendTransactionAction("LoggingAdminTran", "addNewLog", "_self");
}

/**
 * Brings-up a "dummy" new log column entry in the table.
 */
function addNewLogColumn()
{
    sendTransactionAction("LoggingAdminTran", "addNewLogColumn", "_self");
}

/**
 * Cancels any current Log modification activity.
 */
function cancelLog()
{
    sendTransactionAction("LoggingAdminTran", "cancelLog", "_self");
}

/**
 * Cancels any current LogColumn modificatin activity.
 */
function cancelLogColumn()
{
    sendTransactionAction("LoggingAdminTran", "cancelLogColumn", "_self");
}

/**
 * Callback method for a/the TableModel on this page.
 * In this case, the targeted row is made active.
 */
function onTableRowSingleClick(tableId)
{
    sendTransactionAction("LoggingAdminTran", "selectLogRow", "_self");
}

/**
 * Saves the details of the currently selected log column row.
 */
function saveLogColumn()
{
    var selectedLogColumnRowId = getSelectedRowId("LogColumnTableModel");

    var selectedLogRowId = getSelectedRowId("LogTableModel");

    if (valueIsEmpty(selectedLogColumnRowId))
    {
        alert("No Log Column Row Selected");
    }
    else
    {
        mapLogColumnTableToHiddenValues(selectedLogColumnRowId)

        mapLogTableToHiddenValues(selectedLogRowId);

        validateForm(f, "REQUIRED1");

        if (validateForm(f, "REQUIRED2"))
        {
            sendTransactionAction("LoggingAdminTran", "saveLogColumn", "_self");
        }
  }
}

/**
 * Deletes the selected Log and its associated LogColumns.
 */
function deleteLog()
{
      var selectedLogRowId = getSelectedRowId("LogTableModel");

      if (valueIsEmpty(selectedLogRowId))
      {
        alert("No Log Row Selected");
      }

      else if (confirm("Delete the selected Log?"))
      {
        sendTransactionAction("LoggingAdminTran", "deleteLog", "_self");
      }
}

/**
 * Deletes the selected LogColumn.
 */
function deleteLogColumn()
{
      var selectedLogColumnRowId = getSelectedRowId("LogColumnTableModel");

      if (valueIsEmpty(selectedLogColumnRowId))
      {
        alert("No Log Column Row Selected");
      }
      else if (confirm("Delete the selected Log Column?"))
      {
        sendTransactionAction("LoggingAdminTran", "deleteLogColumn", "_self");
      }
}

/**
 * Saves the details of the currently selected log row.
 */
function saveLog()
{
    var selectedRowId = getSelectedRowId("LogTableModel");

    if (valueIsEmpty(selectedRowId))
    {
        alert("No Log Row Selected");
    }
    else
    {
        mapLogTableToHiddenValues(selectedRowId) ;

        if(validateForm(f, "REQUIRED1"))
        {
            sendTransactionAction("LoggingAdminTran", "saveLog", "_self");
        }
    }
}

/**
 * Maps the table elements to their hidden values.
 */
function mapLogColumnTableToHiddenValues(selectedRowId)
{
    var columnName = getTableElement("LogColumnTableModel", selectedRowId, "<%= LogColumnTableModel.COLUMN_COLUMNNAME %>").value;
    
    f.columnName.value = columnName;
    
    var columnLabel = getTableElement("LogColumnTableModel", selectedRowId, "<%= LogColumnTableModel.COLUMN_COLUMNLABEL %>").value;

    f.columnLabel.value = columnLabel;

    var columnDescription = getTableElement("LogColumnTableModel", selectedRowId, "<%= LogColumnTableModel.COLUMN_COLUMNDESCRIPTION %>").value;

    f.columnDescription.value = columnDescription;

    var sequence = getTableElement("LogColumnTableModel", selectedRowId, "<%= LogColumnTableModel.COLUMN_SEQUENCE %>").value;
    
    f.sequence.value = sequence;    
    
    f.logColumnPK.value = selectedRowId;
}

/**
 * Maps the table elements to their hidden values.
 */
function mapLogTableToHiddenValues(selectedRowId)
{
    var logName = getTableElement("LogTableModel", selectedRowId, "<%= LogTableModel.COLUMN_LOG_NAME %>").value;
    
    f.logName.value = logName;

    var logDescription = getTableElement("LogTableModel", selectedRowId, "<%= LogTableModel.COLUMN_LOG_DESCRIPTION %>").value;

    f.logDescription.value = logDescription;
    
    var active = getTableElement("LogTableModel", selectedRowId, "<%= LogTableModel.COLUMN_ACTIVE %>").value;

    f.active.value = active;

    f.logPK.value = selectedRowId;

    f.logFK.value = selectedRowId;
}

/**
 * Callback method for when a <select> is inovoked from an editable TableModel.
 */
function onTableRowSelectChange(selectId)
{
  // Only invoke for the Table select
  if (selectId.indexOf("<%= LogColumnTableModel.COLUMN_COLUMNNAME %>") >= 0)
  {
    var selectedLogTableRowId = getSelectedRowId("LogTableModel");
  
    mapLogTableToHiddenValues(selectedLogTableRowId);
    
    var selectedLogColumnTableRowId = getSelectedRowId("LogColumnTableModel");
    
    mapLogColumnTableToHiddenValues(selectedLogColumnTableRowId);
     
    if(validateForm(f, "REQUIRED1"))
    {
        // Shows the set of Column names that are associated with the currently selected Table.
        sendTransactionAction("LoggingAdminTran", "showAssociatedColumns", "_self");
    }
  }
}

</script>

<table cellspacing="0" cellpadding="0" border="0" width="100%">
  <tr>
    <td width="5%" nowrap="nowrap">
      <input type="button" id="addNewLogColumnId" value="Add" onclick="addNewLogColumn()"/>
      <input type="button" id="saveNewLogColumnId" value="Save" onclick="saveLogColumn()"/>
      <input type="button" id="cancelNewLogColumnId" value="Cancel" onclick="cancelLogColumn()"/>
      <input type="button" id="deleteNewLogColumnId" value="Delete" onclick="deleteLogColumn()"/>
    </td>
    <td align="center">
      <span class="tableHeading">Log Columns</span>
    </td>
  </tr>
</table>
<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="LogColumnTableModel"/>
  <jsp:param name="tableHeight" value="40"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<br></br>

<table cellspacing="0" cellpadding="0" border="0" width="100%">
  <tr>
    <td width="5%" nowrap="nowrap">
      <input type="button" value="Add" onclick="addNewLog()"/>
      <input type="button" value="Save" onclick="saveLog()"/>
      <input type="button" value="Cancel" onclick="cancelLog()"/>
      <input type="button" value="Delete" onclick="deleteLog()"/>
    </td>
    <td align="center">
      <span class="tableHeading">Logs</span>
    </td>
  </tr>
</table>
<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="LogTableModel"/>
  <jsp:param name="tableHeight" value="40"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<!-- Maps to Log -->
<input:hidden name="logPK"/>
<input:hidden name="logName" attributesText="REQUIRED1"/>
<input:hidden name="logDescription" attributesText="REQUIRED1"/>
<input:hidden name="active" attributesText="REQUIRED1"/>

<!-- Maps to LogColumn -->
<input:hidden name="logColumnPK"/>
<input:hidden name="logFK"/>
<input:hidden name="columnName" attributesText="REQUIRED2"/>
<input:hidden name="columnLabel" attributesText="REQUIRED2"/>
<input:hidden name="columnDescription" attributesText="REQUIRED2"/>
<input:hidden name="sequence" attributesText="REQUIRED2"/>