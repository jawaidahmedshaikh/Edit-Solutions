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

     sp.addWSEntry("LogName", "CONVERSION");
     sp.addWSEntry("LogMessage", "Testing logging from a script instruction");
     sp.addWSEntry("RecordType", "89");
     sp.addWSEntry("Name", "ConversionJob");
     sp.addWSEntry("ContractNumber", "SD001");
     sp.addWSEntry("TrxType", "BC");
     //sp.addWSEntry("EffectiveDate", "2008/07/29");

     engine.sp.Loginfo instruction = new engine.sp.Loginfo();

     instruction.setInstAsEntered("loginfo");
     instruction.compile(sp);
     instruction.exec(sp);

//     String successful = sp.popFromStack();

//     System.out.println("successful = " + trxExists);
 %>
