/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Feb 5, 2004
 * Time: 3:46:48 PM
 * 
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package codetable.business;

import edit.services.component.ICRUD;
import edit.common.vo.*;
import edit.common.EDITDate;
import engine.sp.custom.document.GroupSetupDocument;
import event.EDITTrxCorrespondence;

import java.util.Map;

import org.dom4j.*;

import contract.Segment;


public interface CodeTable extends ICRUD
{

    public CodeTableDefVO[] getAllCodeTableDefVOs() throws Exception;

    public CodeTableVO[] getSelectedCodeTableEntries(long codeTableDefPK) throws Exception;

    public void detachCodeTableFromProductStructure(FilteredCodeTableVO filteredCodeTableVO) throws Exception;

    public CodeTableVO getSpecificCodeTableVO(long codeTablePK) throws Exception;

    public BIZCodeTableVO[] getCodeTableEntries(long selectedCodeTableDefPK, long selectedProductStructurePK) throws Exception;

    public FilteredCodeTableVO getFilteredCodeTableEntry(long selectedFilteredCodeTablePK) throws Exception;

    public CodeTableDefVO getCodeTableDef(long selectedCodeTableDefPK) throws Exception;

    public void cloneFilteredCodeTables(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws Exception;

    public CodeTableDefVO[] getFluffyCodeTableDefVOs() throws Exception;

    public FilteredCodeTableVO findByCodeTablePKAndProductStructure(long codeTablePK, long productStructureId);

    public void saveCodeTable(CodeTableVO codeTableVO) throws Exception;

    public void saveFilteredCodeTable(FilteredCodeTableVO filteredCodeTableVO) throws Exception;

    public void deleteCodeTable(long codeTablePK) throws Exception;

    public Map<String, CodeTableDefVO> getFilteredCodeTableEntries(CodeTableDefVO[] codeTableDefVOs) throws Exception;

    public IssueDocumentVO buildIssueDocument(long segmentPK, String issueDate);

    public NaturalDocVO buildNaturalDocWithHistory(String trxType,
                                                   String startingEffDate,
                                                   String endingEffDate,
                                                   EDITTrxVO[] allEditTrxVOs,
                                                   EDITTrxCorrespondence editTrxCorrespondence);

    public NaturalDocVO buildNaturalDocForAnalyzer(GroupSetupVO groupSetupVO,
                                                    EDITTrxVO editTrxVO,
                                                     String processName,
                                                      String optionCode,
                                                       long productStructureFK);

    public NaturalDocVO buildNaturalDocForDataWarehouse(Segment segment, EDITDate stagingDate);

    /**
   * Analyzer and normal trx-processing should work the same. Trx processing
   * uses PRASEDocKeys and PRASEDocBuilders for script processing.
   * @param editTrxPK the driving EDITTrx
   * @return
   */
    public GroupSetupDocument buildGroupSetupDocumentForAnalyzer(Long editTrxPK);
                                                       
                                                       

    public NaturalDocVO buildNaturalDocForTransactionProcess(EDITTrxVO editTrxVO);

    public NaturalDocVO buildNaturalDocWithoutHistory(EDITTrxVO editTrxVO);

//    public NaturalDocVO buildNaturalDocForEquityIndexHedge(SegmentVO segmentVO,
//                                                           ProductStructureVO productStructureVO,
//                                                           long equityIndexHedgeCounter,
//                                                           String runDate,
//                                                           String createSubBucketInd);

    public RedoDocVO buildRedoDocument(EDITTrxVO editTrxVO);

    public NaturalDocVO buildNaturalDocForHedgeFundNotification(EDITTrxCorrespondenceVO editTrxCorrVO);

    public OnlineReportVO getOnlineReport(long productStructureFK, String reportCategory) throws Exception;

    public OnlineReportVO[] getAllOnlineReportVOs() throws Exception;

    public OnlineReportVO getSpecificOnlineReportVO(long onlineReportPK) throws Exception;

    public void saveOnlineReport(OnlineReportVO onlineReportVO) throws Exception;

    public void deleteOnlineReport(long onlineReportPK) throws Exception;

    public BIZOnlineReportVO[] getOnlineReportEntries(long productStructurePK) throws Exception;

    public void saveFilteredOnlineReport(FilteredOnlineReportVO filteredOnlineReportVO) throws Exception;

    public void detachOnlineReportFromProductStructure(FilteredOnlineReportVO filteredOnlineReportVO) throws Exception;

    public void cloneFilteredOnlineReport(long cloneFromProductStructure, long cloneToProductStructure) throws Exception;

    public BIZOnlineReportVO[] getOnlineReportEntriesForProductStructure(long productStructurePK) throws Exception;

    /**
     * @see engine.ProductStructure#cloneFilteredRelations(engine.ProductStructure)
     * @param fromProductStructure
     * @param toProductStructure
     */
    public void cloneAllFilteredRelations(Long fromProductStructure, Long toProductStructure);

    /**
     * Builds a document containing all codeTable entries
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                      <SEGResponseVO>
     *                                         <CodeTableDefVO>
     *                                            ...
     *                                           <CodeTableVO>
     *                                                ...
     *                                           </CodeTableVO>
     *                                         </CodeTableDefVO>
     *                                      </SEGResponseVO>
     */
    public Document getCodeTableDocument();
    
    /**
     * Wrapper to the getCodeTableDocument() method accepting
     * a Document argument to support the SEGRequest/SEGResponse
     * paradigm.
     * 
     * @param segRequest
     * @return
     */
    public Document getCodeTableDocument(Document segRequest);

    /**
     * Finds all the filtered code table entries for a given filteredProductPK.
     *
     * @param requestDocument
     *
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <FilteredProductPK>1</FilteredProductPK>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the filtered code table entries using the following structure:
     *
     *                                  <SEGResponseVO>
     *                                      <SEGResponseVO>
     *                                          <CodeTableDefVO>    // repeats
     *                                            ...
     *                                           <CodeTableVO>      // repeats
     *                                                ...
     *                                           </CodeTableVO>
     *                                         </CodeTableDefVO>
     *                                      </SEGResponseVO>
     *                                      <ResponseMessageVO>
     *                                          ...
     *                                      </ResponseMessageVO>
     *                                  </SEGResponseVO>
     */
    public Document getFilteredCodeTableDocument(Document requestDocument);
}
