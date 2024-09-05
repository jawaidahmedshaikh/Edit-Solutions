<%@ page import="engine.sp.*,
                 engine.sp.custom.function.*,
                 java.util.List,
                 java.util.Map"%>
<!--
 * User: sprasad
 * Date: Jun 26, 2007
 * Time: 10:56:06 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
    // Case 1 when CommissionPhase is non zero value.

    out.println("Begin TestCase 1 ... when CommissionPhase has non zero value" + "<br>");

    ScriptProcessor sp = new ScriptProcessorImpl();
    
    String segmentPK = "1177446787234";
    String commissionPhase = "1";

    sp.addWSEntry("SegmentPK", segmentPK); 
    sp.addWSEntry("CommissionPhase", commissionPhase);
    
    out.println("Input parameters used for TestCase 1 ..." + "<br>");
    
    out.println("SegmentPK = " + segmentPK + "<br>");
    out.println("CommissionPhase = " + commissionPhase + "<br>");

    GetCommissionPhaseDetail gcpd = new GetCommissionPhaseDetail(sp);
    
    gcpd.exec();

    List<Map> commissionPhaseDetailVector = (List<Map>) sp.getWSVector("CommissionPhaseDetail");
    
    out.println("Output for TestCase 1 ..." + "<br>");

    for (Map commissionPhaseDetailMap : commissionPhaseDetailVector)
    {
       out.println("CommissionPhasePK = " + commissionPhaseDetailMap.get("CPDCommissionPhasePK") + "<br>");
       out.println("CommissionPhaseID = " + commissionPhaseDetailMap.get("CPDCommissionPhaseID") + "<br>");
       out.println("EffectiveDate = " + commissionPhaseDetailMap.get("CPDEffectiveDate") + "<br>");
       out.println("ExpectedMonthlyPremium = " + commissionPhaseDetailMap.get("CPDExpectedMonthlyPremium") + "<br>");
       out.println("PrevCumExpectedMonthlyPremium = " + commissionPhaseDetailMap.get("CPDPrevCumExpectedMonthlyPremium") + "<br>");
    }

    out.println("End TestCase 1" + "<br><br>");

    // Case2 when CommissionPhase value is zero.

    out.println("Begin TestCase 2 ... when CommissionPhase value is zero" + "<br>");

    sp = new ScriptProcessorImpl();
    
    segmentPK = "1177446787234";
    commissionPhase = "0";
    String numberCommPhases = "1";

    sp.addWSEntry("SegmentPK", segmentPK); 
    sp.addWSEntry("CommissionPhase", commissionPhase);
    sp.addWSEntry("NumberCommPhases", numberCommPhases);

    out.println("Input parameters used for TestCase 2 ..." + "<br>");
    
    out.println("SegmentPK = " + segmentPK + "<br>");
    out.println("CommissionPhase = " + commissionPhase + "<br>");
    out.println("NumberCommPhases = " + numberCommPhases + "<br>");

    gcpd = new GetCommissionPhaseDetail(sp);
    
    gcpd.exec();

    commissionPhaseDetailVector = (List<Map>) sp.getWSVector("CommissionPhaseDetail");

    out.println("Size of Vector = " + commissionPhaseDetailVector.size() + "<br>");

    out.println("Output for TestCase 2 ..." + "<br>");
    
    for (Map commissionPhaseDetailMap : commissionPhaseDetailVector)
    {
       out.println("CommissionPhasePK = " + commissionPhaseDetailMap.get("CPDCommissionPhasePK") + "<br>");
       out.println("CommissionPhaseID = " + commissionPhaseDetailMap.get("CPDCommissionPhaseID") + "<br>");
       out.println("EffectiveDate = " + commissionPhaseDetailMap.get("CPDEffectiveDate") + "<br>");
       out.println("ExpectedMonthlyPremium = " + commissionPhaseDetailMap.get("CPDExpectedMonthlyPremium") + "<br>");
       out.println("PrevCumExpectedMonthlyPremium = " + commissionPhaseDetailMap.get("CPDPrevCumExpectedMonthlyPremium") + "<br>");
    }

    out.println("End TestCase 2 .....");
 %>