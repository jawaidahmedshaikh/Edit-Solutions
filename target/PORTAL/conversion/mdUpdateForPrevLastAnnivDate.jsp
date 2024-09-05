<%--
 *
 * User: dlataill
 * Date: Aug 18, 2006
 * Time: 8:01:30 AM
 *
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="fission.utility.Util,
                 java.util.List,
                 java.util.ArrayList,
                 edit.common.EDITDate,
                 edit.common.vo.*,
                 java.util.Hashtable,
                 java.util.Enumeration,
                 java.sql.Connection,
                 java.sql.PreparedStatement,
                 java.sql.SQLException,
                 java.sql.ResultSet,
                 edit.services.db.*,
                 java.lang.reflect.Array,
                 java.io.*,
                 edit.common.EDITBigDecimal,
                 event.dm.dao.DAOFactory,
                 contract.Segment,
                 event.FinancialHistory,
                 event.EDITTrxHistory"%>
<%
    String startProcess = Util.initString(request.getParameter("startProcess"), "false");

    CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

    String[] statusCodes = new String[] {"Active", "FSHedgeFundPend", "Death", "DeathPending", "Frozen", "DeathHedgeFundPend", "Lapse", "LapsePending", "Surrendered", "NotTaken"};

    List voInclusionList = new ArrayList();
    voInclusionList.add(EDITTrxVO.class);
    voInclusionList.add(EDITTrxHistoryVO.class);
    voInclusionList.add(FinancialHistoryVO.class);

    int financialHistoryUpdateCount = 0;

    if (startProcess.equals("true"))
    {
        SegmentVO[] segmentVOs = Segment.findBySegmentStatus(statusCodes);
        
        for (int i = 0; i < segmentVOs.length; i++)
        {
            long segmentPK = segmentVOs[i].getSegmentPK();
            EDITTrxVO[] editTrxVOs = retrieveMDTransactions(segmentPK, voInclusionList);
            EDITDate contractEffDate = new EDITDate(segmentVOs[i].getEffectiveDate());
            EDITDate firstAnnivDate = new EDITDate(segmentVOs[i].getEffectiveDate()).addYears(1);
            EDITDate secondAnnivDate = new EDITDate(segmentVOs[i].getEffectiveDate()).addYears(2);
            EDITDate thirdAnnivDate = new EDITDate(segmentVOs[i].getEffectiveDate()).addYears(3);
            if (editTrxVOs != null)
            {
                for (int j = 0; j < editTrxVOs.length; j++)
                {
                    EDITDate trxEffDate = new EDITDate(editTrxVOs[j].getEffectiveDate());
                    EDITTrxHistoryVO[] editTrxHistoryVOs = editTrxVOs[j].getEDITTrxHistoryVO();
                    if (editTrxHistoryVOs != null)
                    {
                        for (int k = 0; k < editTrxHistoryVOs.length; k++)
                        {
                            // FinancialHistoryVO financialHistoryVO = editTrxHistoryVOs[k].getFinancialHistoryVO(0);
                            SegmentHistoryVO segmentHistoryVO = editTrxHistoryVOs[k].getSegmentHistoryVO(0);
                            /*if (segmentHistoryVO.getPrevLastAnniversaryDate() == null)
                            {
                                if (trxEffDate.before(firstAnnivDate) || trxEffDate.equals(firstAnnivDate))
                                {
                                	segmentHistoryVO.setPrevLastAnniversaryDate(contractEffDate.getFormattedDate());
                                }
                                else if (trxEffDate.after(firstAnnivDate) && (trxEffDate.before(secondAnnivDate) || trxEffDate.equals(secondAnnivDate)))
                                {
                                	segmentHistoryVO.setPrevLastAnniversaryDate(firstAnnivDate.getFormattedDate());
                                }
                                else if (trxEffDate.after(secondAnnivDate) && (trxEffDate.before(thirdAnnivDate) || trxEffDate.equals(thirdAnnivDate)))
                                {
                                	segmentHistoryVO.setPrevLastAnniversaryDate(secondAnnivDate.getFormattedDate());
                                }
                                else if (trxEffDate.after(thirdAnnivDate))
                                {
                                	segmentHistoryVO.setPrevLastAnniversaryDate(thirdAnnivDate.getFormattedDate());
                                }

                                financialHistoryUpdateCount += 1;
                                crud.createOrUpdateVOInDB(segmentHistoryVO);
                            }*/
                        }
                    }
                }
            }
        }
        
        if (crud != null) crud.close();

        showSuccess(out, "MD PrevLastAnniversaryDate Update Complete - " + financialHistoryUpdateCount + " FinancialHistory records updated");
    }
%>

<%!
    private EDITTrxVO[] retrieveMDTransactions(long segmentPK, List voInclusionList) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();
        
        return eventComponent.composeEDITTrxVOBySegmentPKAndTrxType(segmentPK, new String[] {"MD"}, voInclusionList);
    }

    private void showSuccess(Writer out, String message) throws Exception
    {
        out.write("<span style='background-color:lightskyblue; width:100%'>");

        out.write("<hr>");

        out.write("<font face='' color='red'>");

        out.write(message);

        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>MD Update for PrevLastAnniversaryDate</h1>

        <p>
        This process will:
        <br>
        <br>1. Get all the processed MD transactions where the PrevLastAnniversaryDate is NULL
        <br>2. For each of these MDs, the PrevLastAnniversaryDate will be set to:
        <br>&nbsp;&nbsp;&nbsp;a. The contract effective date if the MD effective date is <= 1st anniversary date
        <br>&nbsp;&nbsp;&nbsp;b. The 1st anniversary date if the MD effective date > 1st anniversary date and <= 2nd anniversary date
        <br>&nbsp;&nbsp;&nbsp;c, The 2nd anniversary date if the MD effective date > 2nd anniversary date and <= 3rd anniversary date
        <br>&nbsp;&nbsp;&nbsp;c, The 3rd anniversary date if the MD effective date > 3rd anniversary date
        </p>

        <form name="theForm">
            <hr>
            <table border="0">
                <thead>

                </thead>
                <tbody>

                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="Start Process" name="btnStartProcess" onClick="theForm.startProcess.value='true'"/></td>
                    </tr>
                </tbody>
            </table>

            <input type="hidden" name="startProcess" value="false" />

        </form>


    </body>
</html>
