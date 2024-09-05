<%@ page import="java.util.List"%>
<!--
 * User: sprasad
 * Date: Apr 19, 2005
 * Time: 2:38:15 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.portal.widgettoolkit.DoubleTableModel,
                 edit.portal.widgettoolkit.TableRow,
                 edit.portal.widgettoolkit.TableModel,
                 java.math.BigDecimal,
                 edit.common.EDITBigDecimal,
                 fission.utility.Util,
                 java.util.*"%>


<%
//    DoubleTableModel doubleTableModel = (DoubleTableModel) session.getAttribute("doubleTableModel");

    String httpSessionIdentifier = request.getParameter(DoubleTableModel.TABLE_ID);

    DoubleTableModel doubleTableModel = (DoubleTableModel) session.getAttribute(httpSessionIdentifier);

    TableModel fromTableModel = null;
    TableModel toTableModel = null;
    BigDecimal widthOfCell = new BigDecimal("100");
    int numberOfColumns = 0;

    String multipleRowSelect = (String) request.getParameter(DoubleTableModel.PROPERTY_MULTIPLE_ROW_SELECT);
    if (multipleRowSelect == null)
    {
        multipleRowSelect = DoubleTableModel.PROPERTY_MULTIPLE_ROW_SELECT_VALUE_FALSE;
    }

    if (doubleTableModel != null)
    {
        // value of 1 for table on the left side
        fromTableModel = doubleTableModel.getTableModel(DoubleTableModel.FROM_TABLEMODEL);

        // value of 2 for table on the right side
        toTableModel = doubleTableModel.getTableModel(DoubleTableModel.TO_TABLEMODEL);

        numberOfColumns = fromTableModel.getColumnCount(); // should be same as toTableModel Columns

        widthOfCell = (new BigDecimal("100")).divide(new BigDecimal(numberOfColumns+""), 0, BigDecimal.ROUND_HALF_UP);
    }
%>

 <table width="100%" cellspacing="0" cellpadding="2" border="0">
   <tr>
     <td align="center">
         <span class="tableHeading">Available Items</span>
     </td>
     <td>
         &nbsp;
     </td>
     <td align="center" class="heading">
         <span class="tableHeading">Selected Items</span>
     </td>
   </tr>
   <tr>
     <td height="100%" align="right">
         <%-- Column Headings --%>
         <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr class="heading">
                <%
                    if (fromTableModel != null)
                    {
                        List fromColumnNames = fromTableModel.getColumnNames();

                        for (Iterator iterator = fromColumnNames.iterator(); iterator.hasNext();)
                        {
                            String fromColumnName = (String) iterator.next();
                %>
                         <td width="<%= widthOfCell + "%" %>" nowrap>
                             <%= fromColumnName %>
                         </td>
                 <%
                        } // end for
                    } // end if
                 %>
             </tr>
         </table>

         <%--Summary--%>
         <div class="scrollableContent" style="border-style:solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:85%; top:0; left:0;">
             <table id="fromTableSummary" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
         <%
             if (fromTableModel != null)
             {
                 int totalRowsInFromTable = fromTableModel.getRowCount();

                 for (int i = 0; i < totalRowsInFromTable; i++)
                 {
                     TableRow currentFromRow = fromTableModel.getRow(i);

                     String rowId = currentFromRow.getRowId();

                     boolean isSelected = false;

                     boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

                     String className = "default";
         %>
                 <tr class="<%= className %>" id="<%= rowId %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectUnselectAllRowsInTable('toTableSummary', 'false');selectRow(<%= multipleRowSelect %>)">
                 <%
                    List columnNames = fromTableModel.getColumnNames();

                    for (Iterator iterator = columnNames.iterator(); iterator.hasNext();)
                    {
                        String columnName = (String) iterator.next();

                        String cellValue =  (String) currentFromRow.getCellValue(columnName);
                    %>
                    <td width="<%= widthOfCell + "%" %>" nowrap>
                        <%= Util.initString(cellValue, "&nbsp;") %>
                     </td>
                 <%
                     }
                 %>
                 </tr>
         <%
                 }// end for
             } // end if
         %>
             </table>
         </div>
     </td>
     <td height="100%" align="center">
       <table>
         <tr>
           <td>
             <input type="button" value="      Add      " onClick="moveRows('fromTableSummary')">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="   Remove   " onClick="moveRows('toTableSummary')">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="  Select All  " onClick="selectUnselectAllRowsInTable('fromTableSummary', 'true');moveRows('fromTableSummary')">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="Select None" onClick="selectUnselectAllRowsInTable('toTableSummary', 'true');moveRows('toTableSummary')">
           </td>
         </tr>
       </table>
     </td>
     <td height="100%" align="left">
         <%-- Column Headings --%>
         <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr class="heading">
                <%
                    if (toTableModel != null)
                    {
                        List toColumnNames = toTableModel.getColumnNames();

                        for (Iterator iterator = toColumnNames.iterator(); iterator.hasNext();)
                        {
                            String toColumnName = (String) iterator.next();
                %>
                         <td width="<%= widthOfCell + "%" %>" nowrap>
                             <%= toColumnName %>
                         </td>
                 <%
                        } // end for
                    } // end if
                 %>
             </tr>
         </table>

         <%--Summary--%>
         <div class="scrollableContent" style="border-style:solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:85%; top:0; left:0;">
             <table id="toTableSummary" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
         <%
             if (toTableModel != null)
             {
                 int totalRowsInToTable = toTableModel.getRowCount();

                 for (int i = 0; i < totalRowsInToTable; i++)
                 {
                     TableRow currentToRow = toTableModel.getRow(i);

                     String rowId = currentToRow.getRowId();

                     boolean isSelected = false;

                     boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

                     String className = "default";
         %>
                 <tr class="<%= className %>" id="<%= rowId %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectUnselectAllRowsInTable('fromTableSummary', 'false');selectRow(<%= multipleRowSelect %>)">
                     <%
                        List columnNames = fromTableModel.getColumnNames();

                        for (Iterator iterator = columnNames.iterator(); iterator.hasNext();)
                        {
                            String columnName = (String) iterator.next();
                            String cellValue =  (String) currentToRow.getCellValue(columnName);
                     %>
                         <td width="<%= widthOfCell + "%" %>" nowrap>
                             <%= Util.initString(cellValue, "&nbsp;") %>
                         </td>
                     <%
                         }
                     %>
                 </tr>
         <%
                 }// end for
             } // end if
         %>
             </table>
         </div>
     </td>
   </tr>
 </table>
<input type="hidden" id="selectedIds" name="selectedIds"/>