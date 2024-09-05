<%@ page import="fission.utility.Util,
                 edit.portal.widgettoolkit.*" %>
<%@ page import="java.util.*" %>
<%@ page import="edit.common.*" %>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>

<%
    String defaultTitle = "EDITSolutions";
    
    EDITList pageMessage = (EDITList) request.getAttribute("pageMessage") == null?new EDITList():(EDITList) request.getAttribute("pageMessage");

    String pageCommand = (String) request.getAttribute("pageCommand");
    
    String pageTitle = (String) request.getAttribute("pageTitle");    // specific title 
    
    String title = defaultTitle;
    
    if (pageTitle != null)
    {
        title = title + " - " + pageTitle;
    }

    //  Get the tableIds and put them into a single string separated by colons.  These ids will be used in the
    //  call to initScrollTable.
    Map tableModels = (Map) request.getAttribute(TableModel.TABLE_MODELS);

    String tableModelIds = "";

    if (tableModels != null)
    {
        String delimiter = ":";

        Collection tableModelValues = tableModels.values();

        int count = 0;

        for (Iterator iterator = tableModelValues.iterator(); iterator.hasNext();)
        {
            TableModel tableModel = (TableModel) iterator.next();

            String tableID = tableModel.getTableId();

            if (count != 0)
            {
                tableModelIds = tableModelIds + delimiter;
            }

            tableModelIds = tableModelIds + tableID;

            count++;
        }
    }
%>

<html>
<head>
    <title><%= title %></title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css"></link>
    <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>
    <script src="/PORTAL/common/javascript/widgetFunctions.js" type="text/javascript"></script>
    <script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
    <script src="/PORTAL/common/javascript/calendarSelector.js"></script>
    <script src="/PORTAL/common/javascript/scrollTable.js"></script>

    <script language="javascript" type="text/javascript">

        /**
         * TableModels that have a selected row should be scrolled into view. This will store them as
         * as supplied by the tableWidget.jsp.
         */
        var selectedRowIdsArray = new Array();   
        
        var pageCommand = "<%= Util.initString(pageCommand, "") %>";

        var f = null;

        var beforeFormValues = null;

        /**
         * Executed upon the initial load of the template.
         */
        function onBodyLoad()
        {
            try
            {
              setFormElement();
              
              //    If an preInit method has been supplied in the page, execute it
              if (self.preInit)
              { 
                self.preInit();
              }

              checkForPageCommand();

              checkForPageMessage();

              formatCurrency();
                
              initializeTables();

              //    If an init method has been supplied in the page, execute it
              if (self.init)
              {
                self.init();
              }
                
              beforeFormValues = captureFormValues(f);
                
              scrollSelectedRowsIntoView();
    
              //    If an postInit method has been supplied in the page, execute it              
              if (self.postInit)
              {
                self.postInit();
              }
            }
            catch (e)
            {
              //alert("onBodyLoad(): " + e);
            }
        }
        
        /*
            The tableWidget.jsp will store selected row ids so that they are scrolled into view.
        */
        function scrollSelectedRowsIntoView()
        {
            for (var i = 0; i < selectedRowIdsArray.length; i++)
            {
                var clientRow = document.getElementById(selectedRowIdsArray[i]);

                clientRow.scrollIntoView(true);
            }
        }

        /*
         When the template loads, there is no available form object until the page fully renders.
         The "main" included page may need to invoke certain behavior which can not happen until
         the page fully loads. Only "pre-defined" commands can be used.
        */
        function checkForPageCommand()
        {
            if (!valueIsEmpty(pageCommand))
            {
                if (pageCommand == "resetForm")
                {
                    resetForm();
                }
            }
        }

        /*
        Assigns the form element to 'f' as a convenience.
        */
        function setFormElement()
        {
            f = document.templateForm;
        }

        /*
        If there is a "responseMessage" attribute set in the request scope, it is displayed.
        */
        function checkForPageMessage()
        {
            <%
              if (!pageMessage.isEmpty())
              {
                String messageList = "";
                
                for (int i = 0; i < pageMessage.size(); i++)
                {
                  String message = (String) pageMessage.get(i);
                  
                  messageList += message;
                  
                  if (i < (pageMessage.size() - 1))
                  {
                    messageList += "\\n";
                  }
                }
              %>
                alert("<%= messageList %>");  
              <%
              }
            %>
        }

        /**
         * Initialize each table.  Determine the tableId by splitting the concatenated string of ids created
         * in the Java code above
         */
        function initializeTables()
        {
            var tableModelIdArray = new Array();

            var stringTableModelIds = "<%= tableModelIds %>";

            if (stringTableModelIds != "")
            {
                tableModelIdArray = stringTableModelIds.split(':');

                for (var i = 0; i < tableModelIdArray.length; i++)
                {
                    var tableObject = document.getElementById(tableModelIdArray[i] + "ScrollTable");

                    initScrollTable(tableObject);
                }
            }
        }

    </script>
</head>