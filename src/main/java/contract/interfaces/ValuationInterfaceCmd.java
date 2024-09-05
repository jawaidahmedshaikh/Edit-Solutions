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

import edit.services.command.Command;
import edit.services.interfaces.AbstractInterface;
import edit.common.vo.*;
import edit.common.*;
import fission.utility.Util;

import java.util.ArrayList;

public class ValuationInterfaceCmd extends AbstractInterface implements Command {

    private SegmentVO segmentVO;
    private StringBuffer fileData;

    public void setValuationInformation(SegmentVO segmentVO,
                                         StringBuffer fileData) {

        this.segmentVO = segmentVO;
        this.fileData = fileData;
    }

    public Object exec()
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String contractNumber = segmentVO.getContractNumber();

        long productStructureFK = segmentVO.getProductStructureFK();

        ProductStructureVO[] productStructureVOs = engineLookup.getByProductStructureId(productStructureFK);

        String businessContract = productStructureVOs[0].getBusinessContractName();

        if (businessContract.equalsIgnoreCase("GSN") ||
            businessContract.equalsIgnoreCase("GSW")) {

            PayoutVO[] payoutVO = segmentVO.getPayoutVO();

            String status = segmentVO.getSegmentStatusCT();

            EDITBigDecimal paymentAmount = new EDITBigDecimal( payoutVO[0].getPaymentAmount() );

            String paymentMode = payoutVO[0].getPaymentFrequencyCT();

            EDITDate periodCertainEndDate = new EDITDate(payoutVO[0].getCertainPeriodEndDate());

            if (!periodCertainEndDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
            {
                EDITDate cePerCertEndDate = new EDITDate(periodCertainEndDate);

                cePerCertEndDate = cePerCertEndDate.subtractMode(getEDITDateMode(paymentMode));

                periodCertainEndDate = cePerCertEndDate;
            }

            String issueDate = segmentVO.getEffectiveDate();
            EDITDate paymentStartDate = new EDITDate(payoutVO[0].getPaymentStartDate());
            String optionCode = segmentVO.getOptionCodeCT();
            EDITDate paymentStopDate = null;

            if (optionCode.equalsIgnoreCase("LOA") ||
                optionCode.equalsIgnoreCase("LPC") ||
                optionCode.equalsIgnoreCase("JSA") ||
                optionCode.equalsIgnoreCase("JPC") ||
                optionCode.equalsIgnoreCase("INT")) {

                paymentStopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
            }

            else {

                paymentStopDate = periodCertainEndDate;
            }

            EDITBigDecimal finalDistributionAmt = new EDITBigDecimal( payoutVO[0].getFinalDistributionAmount() );
            EDITBigDecimal reducePercent1 = new EDITBigDecimal( payoutVO[0].getReducePercent1() );

            int issueAgeANN = 0;
            int issueAgeJANN = 0;
            String sexANN = "";
            String sexJANN = "";
            String dobANN = "";
            String dobJANN = "";

            ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

            for (int c = 0; c < contractClientVOs.length; c++) {

                long clientRoleFK = contractClientVOs[c].getClientRoleFK();
                ClientRoleVO[] clientRoleVO =
                        role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(clientRoleFK, false, new ArrayList());

                if (clientRoleVO != null) {

                    String roleType = clientRoleVO[0].getRoleTypeCT();

                    if (roleType.equalsIgnoreCase("ANN")) {

                        long clientDetailFK = clientRoleVO[0].getClientDetailFK();
                        issueAgeANN = contractClientVOs[c].getIssueAge();

                        ClientDetailVO[] clientDetailVO = getClientDetailVO(clientDetailFK);

                        sexANN = clientDetailVO[0].getGenderCT();
                        dobANN = clientDetailVO[0].getBirthDate();
                    }
                }

                if (clientRoleVO != null) {

                    String roleType = clientRoleVO[0].getRoleTypeCT();

                    if (roleType.equalsIgnoreCase("SAN")) {

                        long clientDetailFK = clientRoleVO[0].getClientDetailFK();
                        issueAgeJANN = contractClientVOs[c].getIssueAge();

                        ClientDetailVO[] clientDetailVO = getClientDetailVO(clientDetailFK);

                        sexJANN = clientDetailVO[0].getGenderCT();
                        dobJANN = clientDetailVO[0].getBirthDate();
                    }
                }
            }

            if (fileData.length() > 0) {

                fileData.append("\n");
            }

            fileData.append(businessContract);
            fileData.append(" ");
            fileData.append(contractNumber);
            fileData.append(" ");
            fileData.append(status);
            fileData.append(" ");
            fileData.append( Util.formatDecimal("#,###,###,##0.00", paymentAmount) );
            fileData.append(" ");
            fileData.append(paymentMode);
            fileData.append(" ");
            fileData.append(issueDate);
            fileData.append(" ");
            fileData.append(paymentStartDate.getFormattedDate());
            fileData.append(" ");
            fileData.append(paymentStopDate.getFormattedDate());
            fileData.append(" ");
            fileData.append(periodCertainEndDate);
            fileData.append(" ");
            fileData.append( Util.formatDecimal("#,###,###,##0.00", finalDistributionAmt) );
            fileData.append(" ");
            fileData.append( Util.formatDecimal("#,###,###,##0.00", reducePercent1) );
            fileData.append(" ");
            fileData.append(dobANN);
            fileData.append(" ");
            fileData.append(issueAgeANN);
            fileData.append(" ");
            fileData.append(sexANN);
            fileData.append(" ");
            fileData.append(dobJANN);
            fileData.append(" ");
            fileData.append(issueAgeJANN);
            fileData.append(" ");
            fileData.append(sexJANN);
        }

        return null;
    }

    private ClientDetailVO[] getClientDetailVO(long clientDetailFK)
    {
        ClientDetailVO[] clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findByClientPK(clientDetailFK, false, new ArrayList());

        return clientDetailVO;
    }

    public void exportExtract(StringBuffer fileData, String fileName) throws Exception {

        super.exportData(fileData.toString(), fileName);
    }

    private String getEDITDateMode(String paymentMode)
    {
        String editDateMode = null;

        if (paymentMode.equalsIgnoreCase("AN"))
        {
            editDateMode = EDITDate.ANNUAL_MODE;
        }
        else if (paymentMode.equalsIgnoreCase("SA"))
        {
            editDateMode = EDITDate.SEMI_ANNUAL_MODE;
        }
        else if (paymentMode.equalsIgnoreCase("QU"))
        {
            editDateMode = EDITDate.QUARTERLY_MODE;
        }
        else if (paymentMode.equalsIgnoreCase("MO"))
        {
            editDateMode = EDITDate.MONTHLY_MODE;
        }

        return editDateMode;
    }
}
