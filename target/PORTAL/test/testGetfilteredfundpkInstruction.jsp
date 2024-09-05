<%@ page import="engine.sp.*"%>
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
     String companyStructurePK = "1117630332383";
//    long filteredFundPK = 1117630332705L;
//    long fundPK = 1117630332697L;
//    String fundNumber = "8203";
    String fundType = "Hedge";

     ScriptProcessor sp = new ScriptProcessorImpl();

     sp.addWSEntry("fundType", fundType);
     sp.addWSEntry("companyStructurePK", companyStructurePK);

     Getfilteredfundpk gf = new Getfilteredfundpk();

     gf.setInstAsEntered("getfilteredfundpk");
     gf.compile(sp);
     gf.exec(sp);

     String filteredFundPK = (String) sp.getWSEntry("filteredFundPK");

     System.out.println("filteredFundPK = " + filteredFundPK);
 %>

