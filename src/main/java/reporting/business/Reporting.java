/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Mar 22, 2002
 * Time: 2:18:29 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package reporting.business;

import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.YearEndTaxVO;
import edit.services.component.ICRUD;
import fission.global.AppReqBlock;
import org.dom4j.Document;

public interface Reporting extends ICRUD {

    public void createReservesExtracts(AppReqBlock appReqBlock,
                                        SegmentVO[] segmentVOs,
                                         String effectiveDate) throws Exception;

    public YearEndTaxVO[] processTaxes(SegmentVO[] segmentVOs,
                                        String fromDate,
                                         String toDate,
                                          String taxYear,
                                           ProductStructureVO[] productStructureVOs)
                                      throws Exception;

    public void createValuationExtracts(String companyName,
                                         String valuationDate) throws Exception;
    
    public void closeAccounting(String marketingPackageName,
                                String accountingPeriod) throws Exception;

    public void runAccountingExtract(String accountingPeriod) throws Exception;

        /**
     * Updates the BatchContractSetup object to the database with the information provided.
     *
     * @param requestDocument      SEGRequestVO containing BatchContractSetup information using the following structure:
     *
     *                                      <SEGRequestVO>
     *                                          <RequestParameters>
     *                                              <StartCycleDate>
     *                                                  MM/DD/YYYY
     *                                              </StartCycleDate>
     *                                              <EndCycleDate>
     *                                                  MM/DD/YYYY
     *                                              </EndCycleDate>
     *                                          </RequestParameters>
     *                                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the updated BatchContractSetup using the following structure:
     *
     *                                      <SEGResponseVO>
     *                                          <fundactivityreport>
     *                                              <fund fundnumber="9999">
     *                                                  <data cycledate="mm/dd/yyyy" .... />  .... repeats
     *                                                      .........
     *                                              </fund>                 .... repeats
     *                                          </fundactivityreport>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     */
    public Document generateFundActivityReport(Document requestDocument);
}
