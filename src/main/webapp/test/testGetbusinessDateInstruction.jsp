<%@ page import="engine.sp.*,
                 group.*"%>
<!--
 * User: sdorman
 * Date: Sept 11, 2008
 * Time: 12:56:06 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
     ScriptProcessor sp = new ScriptProcessorImpl();

     //sp.addWSEntry("Restriction", "Unrestricted");
     sp.addWSEntry("CalendarDate", "2008/06/01");

     Getbusinessdate inst = new Getbusinessdate();

     inst.setInstAsEntered("getbusinessdate");
     inst.compile(sp);
     inst.exec(sp);

     System.out.println("Finished business date instruction");
 %>
