<%@ page import="java.util.List"%>
<!--
 * User: sprasad
 * Date: May 16, 2005
 * Time: 12:16:28 PM
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
    BigDecimal widthOfCell = null;

    String tableId = request.getParameter(TableModel.TABLE_ID);
    
    Map tableModels = (Map) request.getAttribute(TableModel.TABLE_MODELS);
    
    TableModel tableModel = getTableModel(tableModels, tableId);

    String tableHeight = getTableHeight(request.getParameter(TableModel.PROPERTY_TABLE_HEIGHT));

    String tableWidth = getTableWidth(request.getParameter(TableModel.PROPERTY_TABLE_WIDTH));

    String multipleRowSelect = getMultipleRowSelect(request.getParameter(TableModel.PROPERTY_MULTIPLE_ROW_SELECT), tableModel);
    
    String singleOrDblClick = getSingleOrDblClick(request.getParameter(TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK), tableModel);

    String whichClick = getWhichClick(singleOrDblClick, multipleRowSelect, tableId);
%>

<%-- Column Headings --%>

<div class="scrollTable" id="<%= tableId %>ScrollTable" style="padding:0px; margin:0px; border-style: solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:<%= (new Integer(tableHeight).intValue() - 3) %>%; top:0; left:0;">
    <span class="scrollTableHead">
    <table class="widgetSummary" width="<%= tableWidth + "%" %>" height="2%" border="0" cellspacing="0" cellpadding="0">
        <tr class="heading">
            <%
            if (tableModel != null)
            {
                List columnNames = tableModel.getColumnNames();

                int currentColumn = 0;

                for (Iterator iterator = columnNames.iterator(); iterator.hasNext();)
                {
                    String columnName = (String) iterator.next();

                    widthOfCell = getWidthOfCell(tableModel, columnName, currentColumn);
            %>
            <td width="<%= widthOfCell + "%" %>" nowrap>
                <%= columnName %>
            </td>
            <%
                  currentColumn++;
                } // end for
            } // end if
            %>
        </tr>
    </table>
    </span>

<%--Summary--%>
<%--<div class="scrollableContent" style="padding:0px; margin:0px; border-style: solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:<%= (new Integer(tableHeight).intValue() - 3) %>%; top:0; left:0;">--%>
    <span class="scrollTableBody">
    <table style="position:relative; left:0; top:0;" id="<%= tableId %>" class="widgetSummary" width="<%= tableWidth + "%" %>" border="0" cellspacing="0" cellpadding="2">
        <%
            if (tableModel != null)
            {
                int totalRowsInTable = 0;
                
                try
                {
                    totalRowsInTable = tableModel.getRowCount();
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                for (int i = 0; i < totalRowsInTable; i++)
                {
                    TableRow currentRow = tableModel.getRow(i);
                    
                    String rowId = currentRow.getRowId();

                    boolean isSelected = false;

                    boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

                    String className = "default";

                    if (TableRow.ROW_STATUS_SELECTED.equalsIgnoreCase(currentRow.getRowStatus()))
                    {
                        className = "highlighted";
                        
                        isSelected = true;
                    }
                    else if (TableRow.ROW_STATUS_ASSOCIATED.equalsIgnoreCase(currentRow.getRowStatus()))
                    {
                        className = "associated";
                        
                        isAssociated = true;
                    }
        %>
        <tr class="<%= className %>" id="<%= rowId %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"<%= whichClick %>>
        <%
            List columnNames = tableModel.getColumnNames();
            
            int currentColumn = 0;

            for (Iterator iterator = columnNames.iterator(); iterator.hasNext();)
            {
                String columnName = (String) iterator.next();

                String cellValue =  render(tableModel, currentRow, columnName, currentColumn);

                widthOfCell = getWidthOfCell(tableModel, columnName, currentColumn);
            %>
            <td style="border-bottom:1px solid #A9A9A9" width="<%= widthOfCell + "%" %>" nowrap>
                <%= Util.initString(cellValue, "&nbsp;") %>&nbsp;
             </td>
         <%
                currentColumn++;
             } // end for
         %>
         </tr>
        <%
             }// end for
         } // end if
        %>
    </table>
    </span>
</div>
<input type="hidden" id="selectedRowIds_<%= tableId %>" name="selectedRowIds_<%= tableId %>" value="<%= (tableModel == null) ? "" : tableModel.printSelectedRowIds() %>"/>
<%!
  
  /**
   * The contents of a cell may be rendered as is, or wrapped in an html form element.
   */
  private String render(TableModel tableModel, TableRow tableRow, String currentColumnName, int currentColumn)
  {
    String renderedCellValue = "";
    
    String tableId = tableModel.getTableId();
    
    boolean isSelected = TableRow.ROW_STATUS_SELECTED.equals(tableRow.getRowStatus());
    
    Object cellValue = tableRow.getCellValue(currentColumnName);

    String nameId = tableId + ":" + tableRow.getRowId() + ":" + currentColumnName;
  
    if (isSelected && tableModel.isEditable())
    {
      if (currentColumn == 0)
      {
        renderedCellValue = "<INPUT type=\"radio\" CHECKED=\"CHECKED\" onClick=\"selectRow(false);onSingleClick('" + tableId + "')\" id=\"" + nameId + "\"/>";
      }
      else
      {
        if (tableModel.getRenderCellAs(currentColumnName).equals(TableModel.RENDER_CELL_AS_TEXTFIELD))
        {
          renderedCellValue = "<input type=\"text\" value=\"" + cellValue + "\" id=\"" + nameId + "\" style=\"width=100%\" onFocus=\"this.select()\"/>";
        }
        
        // The implementor of an editable TableModel using <select> should implement an "onSelectChange(id)" method
        // to act as a callback.
        else if (tableModel.getRenderCellAs(currentColumnName).equals(TableModel.RENDER_CELL_AS_SELECT))
        {
          renderedCellValue = "<select id=\"" + nameId + "\" style=\"width=100%\" onChange=\"onSelectChange('" + nameId + "')\">";
          
          String currentCellValue = (String) cellValue;
          
          List selectList = (List) tableRow.getCellValue(currentColumnName + "S"); 
          
          String selected = "";
          
          for (int i = 0; i < selectList.size(); i++)
          {
            String selectEntry = (String) selectList.get(i);
            
            if (selectEntry.equals(currentCellValue))
            {
              selected = "selected";
            }
            
            renderedCellValue += "<option value='" + selectEntry + "' " + selected + ">" + selectEntry;  
            
            selected = "";
          }
          
          renderedCellValue += "</select>";
        }
      }
    }
    else if (!isSelected && tableModel.isEditable() && currentColumn == 0)
    {
        renderedCellValue = "<INPUT type=\"radio\" onClick=\"selectRow(false);onSingleClick('" + tableId + "')\" id=\"" + nameId + "\"/>";
    }
    else
    {
        if (cellValue != null)
        {
            renderedCellValue = cellValue.toString();
        }
    }
    
    return renderedCellValue;
  }
  
  /**
   * Cell widths are evenly divided for each of the columns unless the TableModel is specifically
   * overriding the widths. If a radio/select button is to be rendered, then this is accounted for
   * by distributing this width "loss" to each of the columns.
   */
  private BigDecimal getWidthOfCell(TableModel tableModel, String columnName, int currentColumn)
  {
    String selectButtonColumnWidth = "7";

    double selectButtonColumnWidthAsDouble = new Double(selectButtonColumnWidth).doubleValue();


    int numberOfColumns = tableModel.getColumnCount();  // includes select button column if editable
    
    BigDecimal widthOfCell = null;

    if (tableModel != null)
    {
        if (!tableModel.hasCellWidthOverrides())      // calculate the cell widths by evenly spacing them
        {
            widthOfCell = new BigDecimal("100");

            if (currentColumn == 0 && tableModel.isEditable())  
            {
                widthOfCell = new BigDecimal(selectButtonColumnWidth);     // select button column
            }
            else
            {
                if (tableModel.isEditable())  // reduce to accomodate space needed by select button column
                {
                    widthOfCell = widthOfCell.divide(new BigDecimal((numberOfColumns-1)+""), 0, BigDecimal.ROUND_HALF_UP);
                    
                    widthOfCell = widthOfCell.subtract(new BigDecimal(selectButtonColumnWidthAsDouble/(numberOfColumns-1)));
                }
                else
                {
                    widthOfCell = widthOfCell.divide(new BigDecimal(numberOfColumns+""), 0, BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        else    // use the supplied (override) widths
        {
            if (currentColumn == 0 && tableModel.isEditable())
            {
                widthOfCell = new BigDecimal(selectButtonColumnWidth);     // select button column
            }
            else
            {
                widthOfCell = (BigDecimal) tableModel.getCellWidths().get(columnName);

                if (tableModel.isEditable())  // reduce to accomodate space needed by select button column
                {
                    widthOfCell = widthOfCell.subtract(new BigDecimal(selectButtonColumnWidthAsDouble/(numberOfColumns-1)));
                }
            }
        }
    }

    return widthOfCell;
  }

  /**
   * If renderAsRadio is true, the multipleRowSelect is not allowed and defaulted to false.
   * If multipleRowSelect is null, it is defaulted to false as well.
   */
  private String getMultipleRowSelect(String multipleRowSelect, TableModel tableModel)
  {
    String renderedMultipleRowSelect = null;
    
    if (tableModel.isEditable())
    {
      renderedMultipleRowSelect = TableModel.PROPERTY_MULTIPLE_ROW_SELECT_VALUE_FALSE;
    }
    else
    {
      if (multipleRowSelect == null)
      {
          renderedMultipleRowSelect = TableModel.PROPERTY_MULTIPLE_ROW_SELECT_VALUE_FALSE;
      }    
      else
      {
        renderedMultipleRowSelect = multipleRowSelect;
      }
    }
    
    return renderedMultipleRowSelect;
  }
  
  /**
   * If renderAsRadio is true, the singleOrDblClick is not allowed and defaulted to none.
   * If singleOrDblClick is null, it is defaulted to none as well.
   */  
  private String getSingleOrDblClick(String singleOrDblClick, TableModel tableModel)
  {
    String renderedSingleOrDblClick = null;
    
    if (tableModel.isEditable())
    {
      renderedSingleOrDblClick = TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_NONE;
    }
    else
    {
      if (singleOrDblClick == null)
      {
          renderedSingleOrDblClick = TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_NONE;
      }    
      else
      {
        renderedSingleOrDblClick = singleOrDblClick;
      }
    }
    
    return renderedSingleOrDblClick;  
  }
  
  /**
   * Establishes javascript onClick behavior.
   */
  private String getWhichClick(String singleOrDblClick, String multipleRowSelect, String tableId)
  {
    String whichClick = "";
    
   if (TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE.equalsIgnoreCase(singleOrDblClick))
    {
        whichClick = " onClick=\"selectRow(" + multipleRowSelect  + "); onSingleClick('"+ tableId + "')\"";
    }
    else if (TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_DOUBLE.equalsIgnoreCase(singleOrDblClick))
    {
        whichClick = " onDblClick=\"selectRow(" + multipleRowSelect + "); onDoubleClick('" + tableId + "')\"";
    }  
    
    return whichClick;
  }

  /**
   * If a radio/select is to be rendered, we add it to the current TableModel.
   */
  private void prepareTableModelAsEditable(TableModel tableModel)
  {
    tableModel.getColumnNames().add(0, "select");
  }
  
  /**
   * Defaults the table height if one is not specified.
   */
  private String getTableHeight(String tableHeight)
  {
    String renderedTableHeight = null;
  
    if (tableHeight == null)
    {
        renderedTableHeight = TableModel.PROPERTY_TABLE_HEIGHT_VALUE_DEFAULT;
    }  
    else
    {
      renderedTableHeight = tableHeight;
    }
    
    return renderedTableHeight;
  }

  /**
   * Defaults the table height if one is not specified.
   */
  private String getTableWidth(String tableWidth)
  {
    String renderedTableWidth = null;

    if (tableWidth == null)
    {
        renderedTableWidth = TableModel.PROPERTY_TABLE_WIDTH_VALUE_DEFAULT;
    }
    else
    {
      renderedTableWidth = tableWidth;
    }

    return renderedTableWidth;
  }

  /**
   * Finds/returns the specified TableModel in the Map of TableModels if it exists.
   * If the TableModel is to be rendered with radio buttons, it is also prepped for this.
   */
  private TableModel getTableModel(Map tableModels, String tableId)
  {
    TableModel tableModel = null;
  
    if (tableModels != null)
    {
        tableModel = (TableModel) tableModels.get(tableId);
        
        if (tableModel.isEditable())
        {
          prepareTableModelAsEditable(tableModel);
        }
    }  
    
    return tableModel;
  }
%>