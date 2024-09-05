<%@ page import="engine.sp.*"%>
<!--
 * User: sdorman
 * Date: Jul 29, 2008
 * Time: 12:56:06 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
     ScriptProcessor sp = new ScriptProcessorImpl();

     sp.addWSEntry("TrxType", "MA");
     sp.addWSEntry("SegmentPK", "1216055126981");

     Deletetransaction instruction = new Deletetransaction();

     instruction.setInstAsEntered("deletetransaction");
     instruction.compile(sp);
     instruction.exec(sp);

//     String successful = sp.popFromStack();

//     System.out.println("successful = " + trxExists);
 %>
