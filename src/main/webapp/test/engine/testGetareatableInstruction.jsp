<%@ page import="engine.sp.*,
                 group.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Set" %>
<!--
 * User: sdorman
 * Date: Jan 22, 2009
 * Time: 12:56:06 PM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
     /*
        Tests the Getareatable instruction's many different forms of execution
     */
     
     ScriptProcessor sp = new ScriptProcessorImpl();

     Getareatable inst = new Getareatable();

     inst.setInstAsEntered("getareatable");


     //================  Test getting all areaTable values ===============================
     sp.addWSEntry("ProductStructurePK", "1183390018285");
     sp.addWSEntry("Area", "*");
     sp.addWSEntry("Qualifier", "*");
     sp.addWSEntry("AreaGroup", "CASEBASE");
     sp.addWSEntry("AreaDate", "2008/01/01");
     sp.addWSEntry("RelationshipToEmployeeCT", "EE");

     inst.compile(sp);
     inst.exec(sp);

     System.out.println("AreaTable values are:");

     printWorkingStorageContents(sp);


     //================  Test getting a specific areaTable value ===============================

     sp.clear();

     sp.addWSEntry("ProductStructurePK", "1183390018285");
     sp.addWSEntry("Area", "*");
     sp.addWSEntry("Qualifier", "*");
     sp.addWSEntry("AreaGroup", "CASEBASE");
     sp.addWSEntry("AreaDate", "2008/01/01");
     sp.addWSEntry("RelationshipToEmployeeCT", "EE");
     sp.addWSEntry("AreaField", "MAXFACEAMOUNT");

     inst.compile(sp);
     inst.exec(sp);

     System.out.println("Specific areaTable value is");

     printStackContents(sp);

     //====================  Test overriding areaTable values with BatchContractSetup underwriting (new business) ===============

     sp.clear();

     sp.addWSEntry("ProductStructurePK", "1183390018285");
     sp.addWSEntry("Area", "*");
     sp.addWSEntry("Qualifier", "*");
     sp.addWSEntry("AreaGroup", "CASEBASE");
     sp.addWSEntry("AreaDate", "2008/01/01");
     sp.addWSEntry("BatchContractSetupPK", "1210691045935");
     sp.addWSEntry("RelationshipToEmployeeCT", "EE");

     inst.compile(sp);
     inst.exec(sp);

     System.out.println("Area values with BatchContractSetup override are");

     printWorkingStorageContents(sp);

     //====================  Test overriding areaTable values with Group ContractGroup underwriting (in-force) ===============

     sp.clear();

     sp.addWSEntry("ProductStructurePK", "1183390018285");
     sp.addWSEntry("Area", "*");
     sp.addWSEntry("Qualifier", "*");
     sp.addWSEntry("AreaGroup", "CASEBASE");
     sp.addWSEntry("AreaDate", "2008/01/01");
     sp.addWSEntry("ContractGroupPK", "1210850792897");
     sp.addWSEntry("RelationshipToEmployeeCT", "EE");

     inst.compile(sp);
     inst.exec(sp);

     System.out.println("Area values with Group ContractGroup override are");
     printWorkingStorageContents(sp);

 %>
<%!
    private void printWorkingStorageContents(ScriptProcessor sp)
    {
        Map ws = sp.getWS();

        Set wsKeys = ws.keySet();

        for (Iterator iterator = wsKeys.iterator(); iterator.hasNext();)
        {
            String key = (String)iterator.next();

            String value = (String) ws.get(key);

            System.out.println(key + ", " + value);
        }

        System.out.println();
    }

    private void printStackContents(ScriptProcessor sp)
    {
        String[] stackContents = sp.getDataStack();

        for (int i = 0; i < stackContents.length; i++)
        {
            System.out.println(stackContents[i]);
        }

        System.out.println();
    }
%>
