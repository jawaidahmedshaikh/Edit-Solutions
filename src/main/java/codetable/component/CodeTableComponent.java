/*
 * User: cgleason
 * Date: Feb 5, 2004
 * Time: 3:55:52 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package codetable.component;

import codetable.*;
import codetable.dm.dao.*;
import edit.common.*;
import edit.common.vo.*;
import edit.services.component.*;
import edit.services.db.*;
import edit.services.db.hibernate.*;
import edit.services.logging.*;
import engine.*;
import engine.dm.dao.ProductStructureDAO;
import engine.sp.custom.document.GroupSetupDocument;
import engine.sp.custom.document.PRASEDocBuilder;
import event.EDITTrxCorrespondence;
import fission.utility.*;

import java.sql.Connection;
import java.sql.Statement;

import logging.*;

import java.util.*;

import org.dom4j.*;
import org.dom4j.tree.*;

import webservice.*;
import contract.*;


public class CodeTableComponent extends AbstractComponent implements codetable.business.CodeTable
{
    /* ********************************** ICRUD Methods *************************************** */

    public long createOrUpdateVO(Object voObject, boolean recursively) throws Exception
    {
        return super.createOrUpdateVO(voObject, ConnectionFactory.EDITSOLUTIONS_POOL, recursively);
    }

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    /* ********************************** ICRUD Methods *************************************** */

    /**
     * Using the CodeTableDef entity, the CodeTableDef table entries are retrieved.
     *
     * @return CodeTableDefVO array
     *
     * @throws Exception
     */
    public CodeTableDefVO[] getAllCodeTableDefVOs() throws Exception
    {
        codetable.CodeTableDef codeTableDef = new codetable.CodeTableDef();

        return codeTableDef.getAllCodeTableDefVOs();
    }

    /**
     * Build an array of CodeTableDefVOs containing all codeTableDef records and their children
     *
     * @return
     *
     * @throws Exception
     */
    public CodeTableDefVO[] getFluffyCodeTableDefVOs() throws Exception
    {
        return DAOFactory.getCodeTableDefDAO().findAll();
    }

    /**
     * For the codeTable and the product structure keys provided, set the matching FilteredCodeTableVO into is entity of
     * FilteredCodeTable and retrieve from there.
     *
     * @param codeTablePK
     * @param productStructureId
     *
     * @return FilteredCodeTableVO
     */
    public FilteredCodeTableVO findByCodeTablePKAndProductStructure(long codeTablePK, long productStructureId)
    {
        FilteredCodeTable filteredCodeTable = new FilteredCodeTable();
        filteredCodeTable.findByCodeTablePKAndProductStructure(codeTablePK, productStructureId);

        FilteredCodeTableVO filteredCodeTableVO = (FilteredCodeTableVO) filteredCodeTable.getVO();

        return filteredCodeTableVO;
    }

    /**
     * Save the codeTableVO through its entity
     *
     * @param codeTableVO
     *
     * @throws Exception
     */
    public void saveCodeTable(CodeTableVO codeTableVO) throws Exception
    {
        codetable.CodeTable codeTable = new codetable.CodeTable(codeTableVO);

        codeTable.save();
    }

    /**
     * Save the filteredCodeTableVo through its entity
     *
     * @param filteredCodeTableVO
     *
     * @throws Exception
     */
    public void saveFilteredCodeTable(FilteredCodeTableVO filteredCodeTableVO) throws Exception
    {
        FilteredCodeTable filteredCodeTable = new FilteredCodeTable(filteredCodeTableVO);

        filteredCodeTable.save();
    }

    /**
     * Delete the requested code table record through its entity
     *
     * @param codeTablePK
     *
     * @throws Exception
     */
    public void deleteCodeTable(long codeTablePK) throws Exception
    {
        codetable.CodeTable codeTable = new codetable.CodeTable(codeTablePK);

        codeTable.deleteCodeTable();
    }

    /**
     * For the codeTableDef selected get its children, CodeTable records.  Use the codeTable entity to retrieve and build
     * CodeTableVO array.
     *
     * @param codeTableDefPK
     *
     * @return
     *
     * @throws Exception
     */
    public CodeTableVO[] getSelectedCodeTableEntries(long codeTableDefPK) throws Exception
    {
        codetable.CodeTable codeTable = new codetable.CodeTable();

        return codeTable.getSelectedCodeTableEntries(codeTableDefPK);
    }

    /**
     * Using the filteredCodeTable entity, delete the FilteredCodeTable record requested.
     *
     * @param filteredCodeTableVO
     *
     * @throws Exception
     */
    public void detachCodeTableFromProductStructure(FilteredCodeTableVO filteredCodeTableVO) throws Exception
    {
        FilteredCodeTable filteredCodeTable = new FilteredCodeTable(filteredCodeTableVO);

        filteredCodeTable.detachCodeTableFromProductStructure();
    }

    /**
     * For the CodeTable key passed in, retrieve the CodeTableVO through its entity of CodeTable.
     *
     * @param codeTablePK
     *
     * @return
     *
     * @throws Exception
     */
    public CodeTableVO getSpecificCodeTableVO(long codeTablePK) throws Exception
    {
        codetable.CodeTable codeTable = new codetable.CodeTable(codeTablePK);

        return codeTable.getSpecificCodeTable(codeTable);
    }

    /**
     * For the selected CodeTableDef and ProductStructure key, get the CodeTable entries that satisfy the request.  With
     * the array of CodeTables, build BIZCodeTableVO array. The BIZCodeTableVOs indicated which ones are attached and any
     * override description.
     *
     * @param selectedCodeTableDefPK
     * @param selectedProductStructurePK
     *
     * @return BIZCodeTableVO array
     *
     * @throws Exception
     */
    public BIZCodeTableVO[] getCodeTableEntries(long selectedCodeTableDefPK, long selectedProductStructurePK) throws Exception
    {
        ProductStructure productStructure = new ProductStructure(selectedProductStructurePK);
        CodeTableDef codeTableDef = new CodeTableDef(selectedCodeTableDefPK);

        CodeTable[] ctEntries = codeTableDef.getCodeTableEntries(productStructure);

        return CodeTable.mapEntityToBIZVO(ctEntries);
    }

    /**
     * Get a FilteredCodeTableVO for the FilteredCodeTable key passed in, using FilteredCodeTable entity.
     *
     * @param selectedFilteredCodeTablePK
     *
     * @return FilteredCodeTableVO
     *
     * @throws Exception
     */
    public FilteredCodeTableVO getFilteredCodeTableEntry(long selectedFilteredCodeTablePK) throws Exception
    {
        FilteredCodeTable filteredCodeTable = new FilteredCodeTable(selectedFilteredCodeTablePK);
        filteredCodeTable.findFilteredCodeTable();

        FilteredCodeTableVO filteredCodeTableVO = (FilteredCodeTableVO) filteredCodeTable.getVO();

        return filteredCodeTableVO;
    }

    /**
     * Get CodeTableDefVO for the selected CodeTableDef key, using the CodeTableDef  entity.
     *
     * @param selectedCodeTableDefPK
     *
     * @return CodeTableDefVO
     *
     * @throws Exception
     */
    public CodeTableDefVO getCodeTableDef(long selectedCodeTableDefPK) throws Exception
    {
        CodeTableDef codeTableDef = new CodeTableDef(selectedCodeTableDefPK);

        codeTableDef.getCodeTableName();

        CodeTableDefVO codeTableDefVO = (CodeTableDefVO) codeTableDef.getVO();

        return codeTableDefVO;
    }

    /**
     * For the selected TOProductStructure key, duplicate FilteredCodeTable records of the FromProductStructure key. Each
     * record created is save to the FilteredCodeTable table through its entity.
     *
     * @param cloneFromProductStructurePK
     * @param cloneToProductStructurePK
     *
     * @throws Exception
     */
    public void cloneFilteredCodeTables(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws Exception
    {
        FilteredCodeTableVO[] filteredCodeTableVOs = DAOFactory.getFilteredCodeTableDAO().findByProductStructure(cloneFromProductStructurePK);

        ProductStructure productStructure = new ProductStructure(cloneToProductStructurePK);

        if (filteredCodeTableVOs != null)
        {
            for (int i = 0; i < filteredCodeTableVOs.length; i++)
            {
                FilteredCodeTable filteredCodeTable = new FilteredCodeTable(filteredCodeTableVOs[i]);

                filteredCodeTable.cloneFilteredCodeTableVO(productStructure, filteredCodeTable);
            }
        }

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        codeTableWrapper.reloadCodeTables();
    }

    /**
     * Gets the FilteredCodeTables and stores the CodeTableDefVO and its CodeTableVO children in a map with the
     * productStructurePK and the codeTableName as a key.  This method is similar to the getBIZCodeTableEntries
     * except that it actually stores the CodeTableDefVO instead of making the "phoney" BIZCodeTableVO; it uses the
     * codeTableName as part of the key instead of the codeTableDefPK; and it sets the description in the CodeTableVO
     * according to the override rules.  Storing the filtered entries this way is much more useful, especially for
     * services that need to provide all CodeTableDefVO/CodeTableVOs for a given productStructure
     *
     * @return Map of CodeTableDefVOs with their CodeTableVO children
     *
     * @throws Exception
     */
    public Map<String, CodeTableDefVO> getFilteredCodeTableEntries(CodeTableDefVO[] codeTableDefVOs) throws Exception
    {
        Map codeTableMap = new HashMap();

        ProductStructureVO[] productStructureVOs = new ProductStructureDAO().findAllProductStructures();

        for (int i = 0; i < codeTableDefVOs.length; i++)
        {
            CodeTableDefVO currentCodeTableDefVO = codeTableDefVOs[i];

            if (productStructureVOs != null)
            {
                for (int j = 0; j < productStructureVOs.length; j++)
                {
                    ProductStructureVO currentProductStructureVO = productStructureVOs[j];

                    FilteredCodeTableVO[] filteredCodeTableVOs = new FilteredCodeTableDAO().findBy_CodeTableDefPK_ProductStructurePK(currentCodeTableDefVO.getCodeTableDefPK(), currentProductStructureVO.getProductStructurePK());

                    if (filteredCodeTableVOs != null)
                    {
                        String key = (currentProductStructureVO.getProductStructurePK() + "") + "_" + (currentCodeTableDefVO.getCodeTableName() + "");

                        //  Create a new CodeTableDefVO to hold the CodeTableVOs (need a new one for each new key)
                        CodeTableDefVO codeTableDefVO = new CodeTableDefVO();
                        codeTableDefVO.setCodeTableDefPK(currentCodeTableDefVO.getCodeTableDefPK());
                        codeTableDefVO.setCodeTableName(currentCodeTableDefVO.getCodeTableName());

                        for (int k = 0; k < filteredCodeTableVOs.length; k++)
                        {
                            FilteredCodeTableVO currentFilteredCodeTableVO = filteredCodeTableVOs[k];

                            CodeTable currentCodeTable = new CodeTable(currentFilteredCodeTableVO.getCodeTableFK());

                            //  Get the currentCodeTable as a CodeTableVO
                            CodeTableVO codeTableVO = (CodeTableVO) currentCodeTable.getVO();

                            //  If the filteredCodeTable has a description, use it, otherwise, use the CodeTableVO's
                            if (currentFilteredCodeTableVO.getCodeDesc() != null)
                            {
                                codeTableVO.setCodeDesc(currentFilteredCodeTableVO.getCodeDesc());
                            }

                            codeTableDefVO.addCodeTableVO(codeTableVO);
                        }

                        codeTableMap.put(key, codeTableDefVO);
                    }
                }
            }
        }

        return codeTableMap;
    }


    /**
     * Use the factory pattern to instantiate the IssueDocument entity, then execute build document in IssueDocument.
     *
     * @param segmentPK
     *
     * @return IssueDocumentVO
     */
    public IssueDocumentVO buildIssueDocument(long segmentPK, String issueDate)
    {
        IssueDocument issueDocument = PRASEDocumentFactory.getSingleton().getIssueDocument(segmentPK, issueDate);

        issueDocument.buildDocument();

        return (IssueDocumentVO) issueDocument.getDocumentAsVO();
    }

    /**
     * Use the factory pattern to instantiate the CorrespondenceDocument entity, then execute build document in CorrespondenceDocument.
     *
     * @param segmentPK
     *
     * @return IssueDocumentVO
     */
    public NaturalDocVO buildNaturalDocForStatements(String trxType, String startingEffDate, String endingEffDate, EDITTrxVO[] allEditTrxVOs, EDITTrxCorrespondence editTrxCorrespondence)
    {
        CorrespondenceDocument correspondenceDocument = PRASEDocumentFactory.getSingleton().getCorrespondenceDocument(trxType, startingEffDate, endingEffDate, allEditTrxVOs, editTrxCorrespondence);

        correspondenceDocument.buildDocumentWithHistory();

        return (NaturalDocVO) correspondenceDocument.getDocumentAsVO();
    }

    /**
     * Use the factory pattern to instantiate the AnalyzerDocument entity, then execute build document in AnalyzerDocument.
     *
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructureFK
     *
     * @return
     */
    public NaturalDocVO buildNaturalDocForAnalyzer(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName, String optionCode, long productStructureFK)
    {
        AnalyzerDocument analyzerDocument = PRASEDocumentFactory.getSingleton().getAnalyzerDocument(groupSetupVO, editTrxVO, processName, optionCode, productStructureFK);

        analyzerDocument.buildDocument();

        return (NaturalDocVO) analyzerDocument.getDocumentAsVO();
    }

    /**
     * Use the factory pattern to instantiate the TransactionProcessDocument entity, then execute build document in TransactionProcessDocument.
     *
     * @param editTrxVO
     *
     * @return IssueDocumentVO
     */
    public NaturalDocVO buildNaturalDocForTransactionProcess(EDITTrxVO editTrxVO)
    {
        TransactionProcessDocument transactionProcessDocument = PRASEDocumentFactory.getSingleton().getTransactionProcessDocument(editTrxVO);

        transactionProcessDocument.buildDocument();

        return (NaturalDocVO) transactionProcessDocument.getDocumentAsVO();
    }

    /**
     * Use the factory pattern to instantiate the CorrespondenceDocument entity, then execute build document in CorrespondenceDocument.
     *
     * @param editTrxVO
     *
     * @return IssueDocumentVO
     */
    public NaturalDocVO buildNaturalDocWithoutHistory(EDITTrxVO editTrxVO)
    {
        CorrespondenceDocument correspondenceDocument = PRASEDocumentFactory.getSingleton().getCorrespondenceDocument(editTrxVO);

        correspondenceDocument.buildDocumentWithoutHistory();

        return (NaturalDocVO) correspondenceDocument.getDocumentAsVO();
    }

    /**
     * Use the factory pattern to instantiate the CorrespondenceDocument entity, then execute build document in CorrespondenceDocument.
     *
     * @param segment
     *
     * @return NaturalDocVO
     */
    public NaturalDocVO buildNaturalDocForDataWarehouse(Segment segment, EDITDate stagingDate)
    {
        CorrespondenceDocument correspondenceDocument = PRASEDocumentFactory.getSingleton().getCorrespondenceDocument(segment, stagingDate);

        correspondenceDocument.buildDocumentForDataWarehouse();

        return (NaturalDocVO) correspondenceDocument.getDocumentAsVO();
    }

    //    /**
    //     * Use the factory pattern to instantiate the EquityIndexHedgeDocument entity, then execute build document in EquityIndexHedgeDocument.
    //     * @param segmentPK
    //     * @return IssueDocumentVO
    //     */
    //    public NaturalDocVO buildNaturalDocForEquityIndexHedge(Segment segment, ProductStructure productStructure, long equityIndexHedgeCounter, String runDate, String createSubBucketInd)
    //    {
    //        EquityIndexHedgeDocument equityIndexHedgeDocument = PRASEDocumentFactory.getSingleton().getEquityIndexHedgeDocument(segment, productStructure);
    //
    //        equityIndexHedgeDocument.buildDocument(equityIndexHedgeCounter, runDate, createSubBucketInd);
    //
    //        return (NaturalDocVO) equityIndexHedgeDocument.getDocumentAsVO();
    //    }

    /**
     * Use the factory pattern to instantiate the CorrespondenceDocument entity, then execute build document in CorrespondenceDocument.
     *
     * @param segmentPK
     *
     * @return IssueDocumentVO
     */
    public NaturalDocVO buildNaturalDocForHedgeFundNotification(EDITTrxCorrespondenceVO editTrxCorrespondenceVO)
    {
        HedgeFundNotificationDocument hedgeFundNotificationDocument = PRASEDocumentFactory.getSingleton().getHedgeFundNotificationDocument(editTrxCorrespondenceVO);

        hedgeFundNotificationDocument.buildDocument();

        return (NaturalDocVO) hedgeFundNotificationDocument.getDocumentAsVO();
    }

    public CoiReplenishmentVO buildCOIReplenishmentVO(String runDate, SegmentVO segmentVO)
    {
        COIReplenishmentDocument coiReplenishmentDoc = PRASEDocumentFactory.getSingleton().getCOIReplenishmentDocument(runDate, segmentVO);
        coiReplenishmentDoc.buildDocument();

        return (CoiReplenishmentVO) coiReplenishmentDoc.getDocumentAsVO();
    }

    /**
     * Use the factory patter to instantiate the RedoDcument entity, then execute the build document in RedoDocument
     *
     * @param editTrxVO
     *
     * @return
     */
    public RedoDocVO buildRedoDocument(EDITTrxVO editTrxVO)
    {
        RedoDocument redoDocument = PRASEDocumentFactory.getSingleton().getRedoDocument(editTrxVO);

        redoDocument.buildDocument();

        return (RedoDocVO) redoDocument.getDocumentAsVO();
    }

    /**
     * Get access to the OnlineReport entity, in order to access the Online Report record containing the file name of the
     * issue report.
     *
     * @param productStructureFK
     * @param reportCategory
     *
     * @return OnlineReportVO
     *
     * @throws Exception
     */
    public OnlineReportVO getOnlineReport(long productStructureFK, String reportCategory) throws Exception
    {
        ProductStructure productStructure = new ProductStructure(productStructureFK);
        OnlineReport onlineReport = new OnlineReport(reportCategory);

        OnlineReportVO onlineReportVO = onlineReport.getOnlineReportForCategory(productStructure);

        return onlineReportVO;
    }

    /**
     * Get all the OnlineReport records , return the data in an array of VOs
     *
     * @return OnlineReportVO array
     *
     * @throws Exception
     */
    public OnlineReportVO[] getAllOnlineReportVOs() throws Exception
    {
        OnlineReport onlineReport = new OnlineReport();

        return onlineReport.getAllOnlineReportVOs();
    }

    /**
     * Get the requested OnlineReport record
     *
     * @param onlineReportPK
     *
     * @return
     *
     * @throws Exception
     */
    public OnlineReportVO getSpecificOnlineReportVO(long onlineReportPK) throws Exception
    {
        OnlineReport onlineReport = new OnlineReport(onlineReportPK);

        return onlineReport.getSpecificOnlineReport(onlineReport);
    }

    /**
     * Save an OnlineReportVO to the database
     *
     * @param onlineReportVO
     *
     * @throws Exception
     */
    public void saveOnlineReport(OnlineReportVO onlineReportVO) throws Exception
    {
        OnlineReport onlineReport = new OnlineReport(onlineReportVO);

        onlineReport.save();
    }

    /**
     * Delete an OnlineReport table record selected
     *
     * @param onlineReportPK
     *
     * @throws Exception
     */
    public void deleteOnlineReport(long onlineReportPK) throws Exception
    {
        OnlineReport onlineReport = new OnlineReport(onlineReportPK);

        onlineReport.deleteOnlineReport();
    }

    /**
     * BIZOnlineReportVOs are build with the OnlineReportVO detail and a boolean to indicate if the OnlineReport record
     * is attached to the Product Structure requested.
     *
     * @param productStructurePK
     *
     * @return BIZOnlineReportVO array
     *
     * @throws Exception
     */
    public BIZOnlineReportVO[] getOnlineReportEntries(long productStructurePK) throws Exception
    {
        ProductStructure productStructure = new ProductStructure(productStructurePK);
        OnlineReport onlineReport = new OnlineReport();

        OnlineReport[] onlineReportEntries = onlineReport.getOnlineReportEntries(productStructure);

        return onlineReport.mapEntityToBIZVO(onlineReportEntries);
    }

    /**
     * Save the FilteredOnlineReport record created or updated to the database
     *
     * @param filteredOnlineReportVO
     *
     * @throws Exception
     */
    public void saveFilteredOnlineReport(FilteredOnlineReportVO filteredOnlineReportVO) throws Exception
    {
        FilteredOnlineReport filteredOnlineReport = new FilteredOnlineReport(filteredOnlineReportVO);

        filteredOnlineReport.save();
    }

    /**
     * Delete from the FilteredOnlineReport table the selected records.  This detaches them from a product.
     *
     * @param filteredOnlineReportVO
     *
     * @throws Exception
     */
    public void detachOnlineReportFromProductStructure(FilteredOnlineReportVO filteredOnlineReportVO) throws Exception
    {
        FilteredOnlineReport filteredOnlineReport = new FilteredOnlineReport(filteredOnlineReportVO);

        filteredOnlineReport.detachOnlineReportFromProductStructure();
    }

    /**
     * Create FilteredOnlineReport records for the TO product using the records of the FROM product.
     *
     * @param cloneFromProductStructurePK
     * @param cloneToProductStructurePK
     *
     * @throws Exception
     */
    public void cloneFilteredOnlineReport(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws Exception
    {
        FilteredOnlineReportVO[] filteredOnlineReportVOs = DAOFactory.getFilteredOnlineReportDAO().findByProductStructure(cloneFromProductStructurePK);

        ProductStructure productStructure = new ProductStructure(cloneToProductStructurePK);

        for (int i = 0; i < filteredOnlineReportVOs.length; i++)
        {
            FilteredOnlineReport filteredOnlineReport = new FilteredOnlineReport(filteredOnlineReportVOs[i]);

            filteredOnlineReport.cloneFilteredOnlineReportVO(productStructure, filteredOnlineReport);
        }
    }

    /**
     * @param productStructurePK
     *
     * @return
     *
     * @throws Exception
     */
    public BIZOnlineReportVO[] getOnlineReportEntriesForProductStructure(long productStructurePK) throws Exception
    {
        ProductStructure productStructure = new ProductStructure(productStructurePK);
        OnlineReport onlineReport = new OnlineReport();

        OnlineReport[] onlineReportEntries = onlineReport.getSpecificOnlineReportEntries(productStructure);

        return onlineReport.mapEntityToBIZVO(onlineReportEntries);
    }

    /**
     * @param fromProductStructure
     * @param toProductStructure
     *
     * @see business.CodeTable#cloneAllFilteredRelations(Long, Long)
     */
    public void cloneAllFilteredRelations(Long fromProductStructurePK, Long toProductStructurePK)
    {
        ProductStructure fromCompStructure = ProductStructure.findByPK(fromProductStructurePK);

        ProductStructure toCompStructure = ProductStructure.findByPK(toProductStructurePK);

        SessionHelper.beginTransaction(SessionHelper.ENGINE);
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try
        {
            toCompStructure.cloneFilteredRelations(fromCompStructure);

            SessionHelper.commitTransaction(SessionHelper.ENGINE);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            SessionHelper.rollbackTransaction(SessionHelper.ENGINE);

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * Use the factory pattern to instantiate the CorrespondenceDocument entity, then execute build document in CorrespondenceDocument.
     *
     * @param trxType
     * @param startingEffDate
     * @param endingEffDate
     * @param allEditTrxVOs
     * @param editTrxCorrespondence
     *
     * @return NaturalDocVO
     */
    public NaturalDocVO buildNaturalDocWithHistory(String trxType, String startingEffDate, String endingEffDate, EDITTrxVO[] allEditTrxVOs, EDITTrxCorrespondence editTrxCorrespondence)
    {
        CorrespondenceDocument correspondenceDocument = PRASEDocumentFactory.getSingleton().getCorrespondenceDocument(trxType, startingEffDate, endingEffDate, allEditTrxVOs, editTrxCorrespondence);

        correspondenceDocument.buildDocumentWithHistory();

        return (NaturalDocVO) correspondenceDocument.getDocumentAsVO();
    }

    /**
     * @param editTrxPK
     *
     * @return
     */
    public GroupSetupDocument buildGroupSetupDocumentForAnalyzer(Long editTrxPK)
    {
        GroupSetupDocument builder = new GroupSetupDocument(editTrxPK);

        return builder;
    }

    /**
     * @param segRequest
     *
     * @return
     *
     * @see codetable.business.CodeTable#getCodeTableDocument(Document)
     */
    public Document getCodeTableDocument(Document segRequest)
    {
        return getCodeTableDocument();
    }

    /**
     * @see codetable.business.CodeTable#getCodeTableDocument()
     */
    public Document getCodeTableDocument()
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableDefVO[] codeTableDefVOs = codeTableWrapper.getAllCodeTableDefVOs();

        for (int i = 0; i < codeTableDefVOs.length; i++)
        {
            CodeTableDefVO codeTableDefVO = codeTableDefVOs[i];

//            try
//            {
                //  Convert VO to Element and add to rootElement.  Note: children CodeTableVOs are included
                Element codeTableDefElement = Util.getVOAsElement(codeTableDefVO);
//                Document voDoc = XMLUtil.parse(Util.marshalVO(codeTableDefVO));
//
//                Element codeTableDefElement = (Element) DOMUtil.getElements("CodeTableDefVO", voDoc).get(0);

                responseDocument.addToRootElement(codeTableDefElement);
//            }
//            catch (DocumentException e)
//            {
//                System.out.println("Problem creating CodeTable document");
//
//                System.out.println(e);
//
//                e.printStackTrace();
//            }
        }

        return responseDocument.getDocument();
    }

    /**
     * @see codetable.business.CodeTable#getFilteredCodeTableDocument(Document)
     */
    public Document getFilteredCodeTableDocument(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();
        responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Filtered CodeTable Entries successfully retrieved");

        //  Get information from request document
        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element filteredProductPKElement = requestParametersElement.element("FilteredProductPK");

        Long filteredProductPK = new Long(filteredProductPKElement.getText());

        FilteredProduct filteredProduct = FilteredProduct.findByPK(filteredProductPK);

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableDefVO[] codeTableDefVOs = codeTableWrapper.getAllCodeTableEntries(filteredProduct.getProductStructureFK());

        if (codeTableDefVOs != null)
        {
            for (int i = 0; i < codeTableDefVOs.length; i++)
            {
                CodeTableDefVO codeTableDefVO = codeTableDefVOs[i];

                Element codeTableDefElement = Util.getVOAsElement(codeTableDefVO);

                responseDocument.addToRootElement(codeTableDefElement);
            }
        }

        return responseDocument.getDocument();
    }
}


