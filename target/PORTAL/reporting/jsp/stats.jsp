<%@ page import="edit.services.db.Clock,
                 java.util.List"%>﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html>
  <head><title>Stats</title>


    <style type="text/css">
    <!--

    /* Rowcache tag styles */
    table.rctMainTable{
        height:400;
        width: 1000;
        border-collapse: collapse;
    }
    table.rctInnerTable1 {
        width: 1000;
        border-collapse: collapse;
        border: 1px solid #006699;
    }


     div.scroll {
            height: 600px;
            width: 990px;
            overflow: auto;
            border: 1px solid #666;
            background-color: #0099CC;
            font-weight: strong;
            padding: 8px;}

    table.rctInnerTable2 { height:400; width: 1000; background-color: white;border: 1px solid #006699; }
    table.rctInnerTable3 { background-color: white;border: 1px solid #006699; }
    table.rctInnerTable4 { background-color: white;border: 1px solid #006699; }
    tr.rctTblHeader    {
        font-family: Verdana, Geneva, Arial Helvetica, sans-serif;
        font-size: 20px;
        font-weight: bold;
        color: #006699;
        background-color: #FFFFFF;
        text-align: center;
    }

    td.rctTblTitle  {
        font-family: Verdana, Geneva, Arial Helvetica, sans-serif;
        font-size: 20px;
        font-weight: bold;
        color: #006699;
        background-color: #FFFFFF;
        text-align: center;
    }

    td.rctTblFooter    {
        font-family: Verdana, Geneva, Arial Helvetica, sans-serif;
        font-size: 20px;
        font-weight: bold;
        color: #006699;
        background-color: #FFFFFF;
        text-align: center;
    }

    tr.rctTblFooter    {
        font-family: Verdana, Geneva, Arial Helvetica, sans-serif;
        font-size: 20px;
        font-weight: bold;
        color: #006699;
        background-color: #FFFFFF;
        text-align: center;
    }
    tr.rctTblRowOdd td {
        font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
        background-color: #FFFFFF;
        font-size: 12px;
        padding: 15; }
    tr.rctTblRowEven td {
        font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
        background-color: #FFFFAA;
        font-size: 12px;
        padding: 15;}

     td.rctTblColHeader1 {
        width: 80%;
        text-align: center;
     }

     td.rctTblColHeader2 {
        width: 10%;
        text-align: center;
     }

     td.rctTblColHeader3 {
        width: 10%;
        text-align: center;
     }



    -->
    </style>


  </head>


  <body>


<%
        String threshhold = Clock.getThreshhold();
        List worstStats = Clock.getWorstStats();

        if (! Clock.isTurnedOn())
        {
             out.println("<h1>Database statistics are turned off</h1>");
        }
        else if (worstStats.size() == 0)
        {
            out.println(
                "<h1>No stats so far exceed threshold " + threshhold + " seconds </h1>");
        }
        else
        {   // MAKE THE TABLE



%>
    <table id="rctMainTable" class="rctMainTable">
      <tr class="rctTblTitle">
          <td class="rctTblTitle">Worst Database Performance</td>
          <td width="15%" id="rctTblFooter" class="rctTblFooter">Over <%= threshhold %> seconds<b></td>
      </tr>
      <tr>
          <td colspan="2">
              <table id="rctInnerTable2" class="rctInnerTable2">
                  <tr class="rctInnerTable3">
                      <td class="rctInnerTable3">
                          <table width="100%" class="rctInnerTable3">

                              <tr class="rctTblHeader" id="rctTblHeader">
                                  <td class="rctTblColHeader1">Query</td>
                                  <td class="rctTblColHeader2">Avg time</td>
                                  <td class="rctTblColHeader3">Count</td>
                              </tr>

                          </table>
                      </td>
                  </tr>
                  <tr class="rctInnerTable4">
                      <td class="rctInnerTable4">
                      <div class="scroll">
                          <table width="100%" class="rctInnerTable4">

<%


            int counter = 0;
            String rowClass = "";
            for (int i = 0; i < worstStats.size(); i++)
            {
                String[] stat =  (String[])worstStats.get(i);
                counter++;
                if (counter % 2 == 0)
                {
                    rowClass = "rctTblRowEven";
                }
                else
                {
                    rowClass = "rctTblRowOdd";
                }

                String sql =   stat[0];
                String avg =   stat[1];
                String statcount =  stat[2];



  %>

                              <tr  class="<%= rowClass %>" id="rctTblRow1">
                                  <td class="rctTblCol1"><%= sql %></td>
                                  <td class="rctTblCol2"><%= avg %></td>
                                  <td class="rctTblCol3"><%= statcount %></td>
                              </tr>
    
<%
            
            
                            }


%>


                          </table>
                      </div>
                      </td>
                  </tr>


              </table>
          </td>
      </tr>
  </table>

  <%


        }

  %>





  </body>
</html>