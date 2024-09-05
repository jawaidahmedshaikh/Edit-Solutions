<%@ page import="engine.sp.*,
                 group.*"%>
<!--
 * User: sdorman
 * Date: Oct 18, 2006
 * Time: 12:56:06 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
//     ContractGroup[] contractGroups = ContractGroup.findAllGroupContractGroups();
//
//     for (int i = 0; i < contractGroups.length; i++)
//     {
//         ContractGroup contractGroup = contractGroups[i];
//
//         int count = contractGroup.getSegments().size();
//
//         System.out.println("contractGroup.getContractGroupPK() = " + contractGroup.getContractGroupPK() + ", segment count = " + count);
//     }
     ScriptProcessor sp = new ScriptProcessorImpl();

//     sp.addWSEntry("EffectiveDate", "2006/10/01");
     sp.addWSEntry("EffectiveDate", "2007/10/11");
//     sp.addWSEntry("TrxType", "BC");
     sp.addWSEntry("TrxType", "WI");
     sp.addWSEntry("TrxAmount", "500.00");
//     sp.addWSEntry("SegmentPK", "1144074046889");   // Contract IE04300001
     sp.addWSEntry("OriginatingTrxFK", "2");
     sp.addWSEntry("ComplexChangeTypeCT", "CONV");
     sp.addWSEntry("GroupContractGroupPK", "1190989633257");        // VH9281

     Createtransaction ct = new Createtransaction();

     ct.setInstAsEntered("createtransaction");
     ct.compile(sp);
     ct.exec(sp);

     System.out.println("Finished creating transaction");
 %>
