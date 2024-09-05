<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="edit.services.db.*" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">    
    <title>indexCreator</title>
  </head>
  <body>
<%
  DBTable[] dbTables = DBDatabase.findAllDBTables();

  int tableCount = 0;

  for (DBTable dbTable:dbTables)
  {
    DBColumn[] dbColumns = dbTable.getDBColumns();
    
    for (DBColumn dbColumn:dbColumns)
    {
      String columnName = dbColumn.getColumnName();
      
      if (validIndexColumn(columnName))
      {
        String index = createIndex(dbTable, dbColumn, tableCount);
        
        System.out.println(index);
        
        out.println("<BR>" + index);
      }
    }
    
    tableCount++;
  }
%>
<%!

  /**
  * Creates an index (syntax of) in the form of:
  * CREATE INDEX "tableCount_columnName" ON BASE."tableName"("columnName" ASC);  
  */
  private String createIndex(DBTable dbTable, DBColumn dbColumn, int tableCount)
  {
    String columnName = dbColumn.getColumnName();
    
    String tableName = dbTable.getTableName();
    
    String indexName = createIndexName(columnName, tableCount);
    
    String index = "CREATE INDEX \"" + indexName + "\" ON BASE.\"" + tableName + "\"(\"" + columnName + "\" ASC);";
    
    return index;
  }
  
  /**
  * The maximum length of an index is 18 characters. If the columnName and
  * tableCount combination excedes 18, the index name is trimmed. For example:
  * 123_foocolumnfoocolumn (22 characters) would be trimmed to:
  * 123_foocolumnfooco (18 characters).
  */
  private String createIndexName(String columnName, int tableCount)
  {
    String indexName = "IX_" + tableCount + "_" + columnName;
    
    if (indexName.length() > 18)
    {
      indexName = indexName.substring(0, 18);
    }
    
    return indexName;
  }

  /**
  * True if:
  * 1. The column ends in "FK".
  * 2. There is a valid table "Foo" for the table-name of "Foo"FK.
  */
  private boolean validIndexColumn(String columnName)
  {
    boolean validIndexColumn = false;
    
    if (columnName.endsWith("FK"))  
    {
      String tableName = columnName.substring(0, columnName.length() - 2);
      
      DBTable dbTable = DBTable.getDBTableForTable(tableName);
      
      if (dbTable != null)
      {
        validIndexColumn = true;
      }
    }
    
    return validIndexColumn;
  }

%>
  
  
  </body>
</html>