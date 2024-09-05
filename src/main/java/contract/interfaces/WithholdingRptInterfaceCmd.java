/*
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 2:07:03 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.interfaces;

import edit.common.CodeTableWrapper;
import edit.common.EDITDate;
import edit.common.vo.*;
import edit.services.command.Command;
import edit.services.db.ConnectionFactory;
import edit.services.interfaces.AbstractInterface;

import java.util.ArrayList;
import java.util.List;

import role.dm.dao.DAOFactory;

public class WithholdingRptInterfaceCmd extends AbstractInterface implements Command {

    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
    private static final String fileName = "WithholdingReport";
    private static final String WITHHOLDING_REPORT_COMPLETE = "/daily/jsp/withholdingReportComplete.jsp";
    private static final String WITHHOLDING_REPORT_ERROR    = "/daily/jsp/withholdingReportError.jsp";
    private final static String oneSpace = " ";

    private SegmentVO[] segmentVOs;
    private ProductStructureVO[] productStructureVOs;
    private String fromDate;
    private String toDate;
    private StringBuffer fileData;
    private CodeTableWrapper codeTableWrapper;

    public void setWithholdingRptInformation(SegmentVO[] segmentVOs,
                                              ProductStructureVO[] productStructureVOs,
                                               String fromDate,
                                                String toDate) {

        this.segmentVOs = segmentVOs;
        this.productStructureVOs = productStructureVOs;
        this.fromDate = fromDate;
        this.toDate = toDate;
        codeTableWrapper = CodeTableWrapper.getSingleton();
        fileData = new StringBuffer();
    }

    public Object exec()
    {
        String returnPage = "";

        CodeTableVO[] trxTypes = codeTableWrapper.getCodeTableEntries("TRXTYPE");

        int i = 0;
        int s = 0;
        int t = 0;

        for (i = 0; i < segmentVOs.length; i++) {

            String contractNumber = segmentVOs[i].getContractNumber();
            String taxReportingGroup = segmentVOs[i].getTaxReportingGroup();
            if (taxReportingGroup == null) {

                taxReportingGroup = "";
            }
            List stateNames = getStateName(segmentVOs[i]);

//            SegmentActivityHistoryVO[] segmentActivityHistoryVOs =
//                    DAOFactory.getSegmentActivityHistoryDAO().
//                     findRecordsWithholdingReport(segmentVOs[i].getSegmentPK(),
//                                                   fromDate,
//                                                    toDate,
//                                                     false,
//                                                      null);
//
//            if (segmentActivityHistoryVOs != null) {
//
//                for (s = 0; s < segmentActivityHistoryVOs.length; s++) {
//
//                    String processDate = segmentActivityHistoryVOs[s].getProcessDate();
//                    String effectiveDate = segmentActivityHistoryVOs[s].getEffectiveDate();
//                    double fedWithholdingAmt = segmentActivityHistoryVOs[s].getFederalWithholding();
//                    double stateWithholdingAmt = segmentActivityHistoryVOs[s].getStateWithholding();
//
//                    String fedWithholdingString = Util.formatDecimal("###,###,##0.00",
//                                                                      Util.roundDollars(fedWithholdingAmt));
//                    String stateWithholdingString = Util.formatDecimal("###,###,##0.00",
//                                                                        Util.roundDollars(stateWithholdingAmt));
//
//                    String stateName = "";
//                    long clientId = 0;
//
//                    for (int k = 0; k < stateNames.size(); k++) {
//
//                        stateName = (String) stateNames.get(k);
//                        k++;
//                        String client  = (String) stateNames.get(k);
//                        clientId = Long.parseLong(client);
//                        if (clientId == segmentActivityHistoryVOs[s].getClientFK()) {
//
//                            break;
//                        }
//                    }
//
//                    if (fileData.length() == 0) {
//
//                        fileData.append("                    WITHHOLDING REPORT");
//                        fileData.append("\n");
//                        fileData.append("             FROM: " + fromDate + "    TO: " + toDate);
//                        fileData.append("\n");
//                        fileData.append("\n");
//                        fileData.append("\n");
//                    }
//
//                    fileData.append("\n");
//                    fileData.append(contractNumber);
//
//                    int fieldLen = contractNumber.length();
//                    int numSpaces = 15 - fieldLen;
//                    for (t = 1; t <= numSpaces; t++) {
//
//                        fileData.append(oneSpace);
//                    }
//
//                    fileData.append(stateName);
//                    fileData.append(oneSpace);
//                    fileData.append(processDate);
//                    fileData.append(oneSpace);
//                    fileData.append(effectiveDate);
//                    fileData.append(oneSpace);
//                    fileData.append(fedWithholdingString);
//
//                    fieldLen = fedWithholdingString.length();
//                    numSpaces = 15 - fieldLen;
//                    for (t = 1; t <= numSpaces; t++) {
//
//                        fileData.append(oneSpace);
//                    }
//
//                    fileData.append(stateWithholdingString);
//
//                    fieldLen = stateWithholdingString.length();
//                    numSpaces = 15 - fieldLen;
//                    for (t = 1; t <= numSpaces; t++) {
//
//                        fileData.append(oneSpace);
//                    }
//
//                    fileData.append(taxReportingGroup);
//                }
//            }
        }

        if (returnPage.equals("")) {

            if (fileData.length() > 0) {

                EDITDate currentDate = new EDITDate();

                exportWithholdingReport(fileName + currentDate.getFormattedMonth() + currentDate.getFormattedDay() + currentDate.getFormattedYear());

                returnPage = WITHHOLDING_REPORT_COMPLETE;
            }

            else {

                returnPage = WITHHOLDING_REPORT_ERROR;
            }
        }

        return returnPage;
    }

    private List getStateName(SegmentVO segmentVO) {

        List stateNames = new ArrayList();
        String stateName = null;

        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        String typeValue = "PAY";
        List voExclusionList = new ArrayList();
        voExclusionList.add(ClientRoleVO.class);

        if (contractClientVOs != null) {
            //get codetable payout value
            for (int i = 0; i < contractClientVOs.length; i++) {

                long clientRoleFK = contractClientVOs[i].getClientRoleFK();

                ClientRoleVO[] clientRoleVOs = DAOFactory.getClientRoleDAO().findByClientRolePK(clientRoleFK, false, new ArrayList());

                if (clientRoleVOs != null) {

                    String roleType = clientRoleVOs[0].getRoleTypeCT();

                    if (roleType.equalsIgnoreCase(typeValue)) {

                        long clientDetailFK = clientRoleVOs[0].getClientDetailFK();;
                        ClientDetailVO[] clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findByClientPK(clientDetailFK, true, voExclusionList);

                        if (clientDetailVO == null || clientDetailVO.length == 0){

                            stateName = "N/A";
                        }
                        else {
                            ClientAddressVO[] clientAddressVO = clientDetailVO[0].getClientAddressVO();

                            if (clientAddressVO == null || (clientAddressVO.length == 0)) {

                                stateName = "N/A";
                            }
                            else {

                                stateName = clientAddressVO[0].getStateCT();
                                if (stateName.equals("")) {

                                    stateName = "N/A";
                                }
                            }
                        }

                        stateNames.add(stateName);
                        stateNames.add(clientDetailFK + "");
                    }
                }
            }
        }

        return stateNames;
    }

    private void exportWithholdingReport(String exportFileName) {

        super.exportData(fileData.toString(), exportFileName);
    }
}
