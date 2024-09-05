<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, java.sql.*, edit.services.db.*, edit.common.*"%>
<%@ page import="java.util.*, edit.services.db.hibernate.*, event.*, agent.*, contract.*"%>
 <%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, edit.services.db.*, edit.common.*,
                 edit.common.vo.*,
                 java.util.*,
                 event.dm.dao.*,
                 contract.*,
                 engine.*" %>
<%
    String startConversion = Util.initString(request.getParameter("startConversion"), "false");

    if (startConversion.equals("true"))
    {
        long startTime = System.currentTimeMillis();
        FilteredProduct fp = null;
        long stopTime = 0;
         String stateCT = "*";
    
        try
        {
            List filteredProductPKs = getFilteredProductPKs();
            
            int size = filteredProductPKs.size();
            
            for (int i = 0; i < size; i++)
            {
              SessionHelper.clearSessions();
              
              SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            
              Long currentFilteredProductPK = (Long) filteredProductPKs.get(i);
              
              System.out.println("Processing [" + (i + 1) + " of " + size + "] FilteredProducts - PK [" + currentFilteredProductPK + "]");

              fp = FilteredProduct.findByPK(currentFilteredProductPK);
              
              MasterContract mc= new MasterContract();
              mc.setMasterContractName(fp.getMasterContractName());
              mc.setMasterContractNumber(fp.getMasterContractNumber());
              mc.setCreationOperator(fp.getOperator());
              mc.setMasterContractEffectiveDate(fp.getEffectiveDate());
              mc.setCreationDate(fp.getCreationDate());
              mc.setMasterContractTerminationDate(fp.getTerminationDate());
              mc.setFilteredProduct(fp);
              mc.setStateCT(stateCT);
              SessionHelper.saveOrUpdate(mc, SessionHelper.EDITSOLUTIONS);
              SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
            showSuccess(out, "Mapping of FilteredProduct Details to MasterContract Table is Complete");
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
       
            System.out.println(e);
            
            e.printStackTrace();

            showError(out, e);
        }
        finally
        {
            SessionHelper.clearSessions();
        
            stopTime = System.currentTimeMillis();
            
            double totalTime = 1.0 * (stopTime - startTime) / 1000.0;
            
            System.out.println("Total Time: " + totalTime + " seconds.");
        }
    }
%>
<%!
    

  /**
   * Segments with CommissionHistories that can be associated with an AgentSnapshot
   * must have CommissionHistories associated with an AgentSnapshot via the
   * CommissionHistory.PlacedAgentFK
   */
  private List getFilteredProductPKs() throws Exception
  {
    String hql = " select distinct (filteredProduct.FilteredProductPK)" +
                " from FilteredProduct filteredProduct";

    List filteredProductPKs = null; 

    try
    {
      filteredProductPKs = SessionHelper.executeHQL(hql, null, SessionHelper.EDITSOLUTIONS);
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {

    }
    
    return filteredProductPKs;
  }

    /**
     * Convenience method - Displays any successes from the conversion.
     */
    private void showSuccess(Writer out, String message) throws Exception
    {
        out.write("<span style='background-color:lightskyblue; width:100%'>");

        out.write("<hr>");

        out.write("The conversion was successful in completing the following:<br><br>");

        out.write("<font face='' color='blue'>");

        out.write(message);

        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }

    /**
     * Convenience method - Displays any errors from the conversion.
     */
    private void showError(Writer out, Exception e) throws Exception
    {
        out.write("<span style='background-color:yellow; width:100%'>");

        out.write("<hr>");

        out.write("The conversion was aborted for the following reason(s):<br><br>");

        out.write("<font face='' color='red'>");
     
        e.printStackTrace(new java.io.PrintWriter(out));
        
        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }

    /**
     * Convenience method to display conversion status.
     */
    private void attempting(Writer out, String message) throws Exception
    {
        out.write("<font face='' color='blue'>");

        out.write("Attempting...<br>");

        out.write("</font>");

        out.write(message);

        out.write("<br><br>");
    }
%>
<%-- The taglib directive below imports the JSTL library. If you uncomment it,
     you must also add the JSTL library to the project. The Add Library...
     action on Libraries node in Projects view can be used to add the JSTL 1.1
     library.--%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <title>JSP Page</title>
    </head>
    <body><h1>Copy FilteredProduct Details To MasterContract Table</h1>
    
<form name="theForm" action="">

<p>
  Copy filteredProduct details like MasterContractName,MasterContractNumber,EffectiveDate,TerminationDate,Operator,CreationDate to masterContract Table with appropriate Columns.
  
  <br><br>
</p>
<p>
  In order to map  filteredProduct details to its corresponding masterContract table, the 
  following approach was used:
</p>
<p>
 1. Retrive all filteredProductPK's from FilteredProduct Table.
</p>
<p>
 2. Map each filteredProductPK's from FilteredProduct to MasterContract's FilteredProductFK's.
</p>
<p>
    
  3. Map all other details from FilteredProduct to MasterContract respectively.
</p>




                            <input type="submit" value="Start Mapping"
                                   name="btnStartConversion"
                                   onclick="theForm.startConversion.value='true'"/>            
            <input type="hidden" name="startConversion" value="false"/>
        </form></body>
</html>