<%@ page import="engine.sp.*"%>
<!--
 * User: sdorman
 * Date: Nov 28, 2006
 * Time: 12:56:06 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
     ScriptProcessor sp = new ScriptProcessorImpl();

     sp.addWSEntry("TrxType", "ML");
     sp.addWSEntry("EffectiveDate", "2005/02/03");
     sp.addWSEntry("PendingStatus", "P");
     sp.addWSEntry("OriginatingTrxFK", "null");

     Transactionexists instruction = new Transactionexists();

     instruction.setInstAsEntered("transactionexists");
     instruction.compile(sp);
     instruction.exec(sp);

     String trxExists = sp.popFromStack();

     System.out.println("trxExists = " + trxExists);
 %>
