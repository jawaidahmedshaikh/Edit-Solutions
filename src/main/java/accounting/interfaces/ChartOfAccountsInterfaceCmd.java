/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 2:07:03 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.interfaces;

import edit.common.vo.*;
import edit.services.command.Command;
import edit.services.interfaces.AbstractInterface;

import java.util.Map;

import engine.*;

public class ChartOfAccountsInterfaceCmd extends AbstractInterface implements Command {

    private Map allProductStructures;
    private Map allFunds;
    private ElementVO[] elementVOs;
    private StringBuffer fileData;

    private final static String COA_REPORT = "ChartOfAccountsReport";

    public void setAccountingInformation(Map allProductStructures,
                                          Map allFunds,
                                           ElementVO[] elementVOs) {

        this.allProductStructures = allProductStructures;
        this.allFunds             = allFunds;
        this.elementVOs           = elementVOs;

        fileData = new StringBuffer();
    }

    public Object exec()
    {
        for (int e = 0; e < elementVOs.length; e++) {

            int seqNumber        = elementVOs[e].getSequenceNumber();
            String process       = elementVOs[e].getProcess();
            String event         = elementVOs[e].getEvent();
            String eventType     = elementVOs[e].getEventType();
            String element       = elementVOs[e].getElementName();
            String effectiveDate = elementVOs[e].getEffectiveDate();

            fileData.append(effectiveDate + " " + seqNumber + " " + process +
                            " " + event + " " + eventType + " " + element);
            fileData.append("\n");
            fileData.append("\n");

            ElementStructureVO[] elementStructureVOs = elementVOs[e].getElementStructureVO();

            for (int s = 0; s < elementStructureVOs.length; s++) {

                String fundNumber   = (String) allFunds.get(elementStructureVOs[s].getFundFK() + "");
                String switchEffect = elementStructureVOs[s].getSwitchEffectInd();
                String suppressInd   = elementStructureVOs[s].getSuppressAccountingInd();

                fileData.append("  Fund = " + fundNumber + ", Switch Effect = " + switchEffect +
                                ", Suppress Indicator = " + suppressInd);
                fileData.append("\n");
                fileData.append("\n");

                AccountVO[] accountVOs = elementStructureVOs[s].getAccountVO();

                for (int a = 0; a < accountVOs.length; a++) {

                    String accountNumber = accountVOs[a].getAccountNumber();
                    String accountName   = accountVOs[a].getAccountName();
                    String accountEffect = accountVOs[a].getEffect();

                    fileData.append("    Account Information");
                    fileData.append("\n");
                    fileData.append("      Number = " + accountNumber +
                                    ", Name = " + accountName +
                                    ", Effect = " + accountEffect);
                    fileData.append("\n");
                    fileData.append("\n");
                }
            }

            ElementCompanyRelationVO[] elementCompanyRelationVOs = elementVOs[e].getElementCompanyRelationVO();

            fileData.append("        Attached to:");
            fileData.append("\n");

            if (elementCompanyRelationVOs != null) {

                for (int c = 0; c < elementCompanyRelationVOs.length; c++) {

                    String productStructureFK = elementCompanyRelationVOs[c].getProductStructureFK() + "";
                    ProductStructureVO productStructureVO = (ProductStructureVO)
                                                              allProductStructures.get(productStructureFK);

                    if (productStructureVO != null)
                    {
                        Company company = Company.findByPK(productStructureVO.getCompanyFK());
                        String companyName      = company.getCompanyName();
                        String businessContract = productStructureVO.getBusinessContractName();

                        fileData.append("          " + companyName + "   " + businessContract);
                        fileData.append("\n");
                    }
                }

                fileData.append("\n");
                fileData.append("\n");
            }
        }

        exportCOAReport();

        return null;
    }

    public void exportCOAReport() {

        super.exportData(fileData.toString(), COA_REPORT);
    }
}
