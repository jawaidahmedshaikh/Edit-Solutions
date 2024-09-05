/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Mar 5, 2003
 * Time: 8:58:17 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.rp;

import accounting.dm.dao.DAOFactory;
import accounting.interfaces.ChartOfAccountsInterfaceCmd;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ElementVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingProcessor {

    public void generateChartOfAccountsReport() throws Exception {

        engine.business.Lookup engineLookup =  new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructures();

        Map allProductStructures = new HashMap(productStructureVOs.length);

        for (int c = 0; c < productStructureVOs.length; c++) {

            allProductStructures.put(productStructureVOs[c].getProductStructurePK() + "", productStructureVOs[c]);
        }

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);

        FundVO[] fundVOs = engineLookup.composeAllFundVOs(voInclusionList);

        Map allFunds = null;

        if (fundVOs != null)
        {
            allFunds = new HashMap(fundVOs.length);

            for (int h = 0; h < fundVOs.length; h++) {

                FilteredFundVO[] filteredFundVOs = fundVOs[h].getFilteredFundVO();

                if (filteredFundVOs != null && filteredFundVOs.length > 0)
                {
                    String fundNumber = filteredFundVOs[0].getFundNumber();

                    allFunds.put(fundVOs[h].getFundPK() + "", fundNumber);
                }
            }
        }
        else
        {
            allFunds = new HashMap(0);

        }

        ElementVO[] elementVOs = DAOFactory.getElementDAO().findAllElementsForReport();

        ChartOfAccountsInterfaceCmd chartOfAccountsInterface = new ChartOfAccountsInterfaceCmd();

        chartOfAccountsInterface.setAccountingInformation(allProductStructures,
                                                           allFunds,
                                                            elementVOs);

        chartOfAccountsInterface.exec();
    }
}
