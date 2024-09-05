/**
 * PRASEDocumentFactory.java Version 2.0 05/03/2004
 *
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 3, 2004
 * Time: 1:40:06 PM
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 *
 */package codetable;

import edit.common.vo.*;
import edit.common.EDITDate;
import codetable.PRASEDocument;
import codetable.IssueDocument;
import event.EDITTrxCorrespondence;
import event.financial.client.trx.*;
import reinsurance.*;
import contract.*;
import engine.*;


public class PRASEDocumentFactory
{
    private static PRASEDocumentFactory praseDocumentFactory;

    private PRASEDocumentFactory(){}

    /**
     * Instantiate PRASEDocumentFactory only one time (Static)
     * @return
     */
    public static PRASEDocumentFactory getSingleton()
    {
        if (praseDocumentFactory == null)
        {
            praseDocumentFactory = new PRASEDocumentFactory();
        }

        return praseDocumentFactory;
    }

   /**
    * Factory pattern used to build the IssueDocument
    * @param segmentPK
    * @return
    */
    public IssueDocument getIssueDocument(long segmentPK, String issueDate)
    {
        return new IssueDocument(segmentPK, issueDate);
    }

    /**
     * Factory pattern used to build the CorrespondenceDocument
     * @param trxType
     * @param startingEffDate
     * @param endingEffDate
     * @param allEditTrxVOs
     * @return
     */
    public CorrespondenceDocument getCorrespondenceDocument(String trxType,
                                                            String startingEffDate,
                                                            String endingEffDate,
                                                            EDITTrxVO[] allEditTrxVOs,
                                                            EDITTrxCorrespondence editTrxCorrespondence)
    {
        return new CorrespondenceDocument(trxType, startingEffDate, endingEffDate, allEditTrxVOs, editTrxCorrespondence);
    }

    public CorrespondenceDocument getCorrespondenceDocument(EDITTrxVO editTrxVO)
    {
        return new CorrespondenceDocument(editTrxVO);
    }

    public CorrespondenceDocument getCorrespondenceDocument(Segment segment, EDITDate stagingDate)
    {
        return new CorrespondenceDocument(segment, stagingDate);
    }

    /**
     * Factory pattern used to build the HedgeFundNotificationDocument
     * @param trxType
     * @param startingEffDate
     * @param endingEffDate
     * @param allEditTrxVOs
     * @return
     */
    public HedgeFundNotificationDocument getHedgeFundNotificationDocument(EDITTrxCorrespondenceVO editTrxCorrVO)
    {
        return new HedgeFundNotificationDocument(editTrxCorrVO);
    }

    public COIReplenishmentDocument getCOIReplenishmentDocument(String runDate, SegmentVO segmentVO)
    {
        return new COIReplenishmentDocument(runDate, segmentVO);
    }

    /**
     * Factory pattern used to build the AnalyzerDocument
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructureFK
     * @return
     */
    public AnalyzerDocument getAnalyzerDocument(GroupSetupVO groupSetupVO,
                                                EDITTrxVO editTrxVO,
                                                String processName,
                                                String optionCode,
                                                long productStructureFK)
    {
        return new AnalyzerDocument(groupSetupVO, editTrxVO, processName, optionCode, productStructureFK);
    }

    /**
     * Factory pattern used to build the TransactionProcessDocument
     * @param clientTrx
     * @return
     */
    public TransactionProcessDocument getTransactionProcessDocument(ClientTrx clientTrx)
    {
        return new TransactionProcessDocument(clientTrx);
    }

    /**
     * Factory pattern used to build the TransactionProcessDocument
     * @param editTrxVO
     * @return
     */
    public TransactionProcessDocument getTransactionProcessDocument(EDITTrxVO editTrxVO)
    {
        return new TransactionProcessDocument(editTrxVO);
    }

    /**
     * Factory pattern used to build the EquityIndexHedgeDocument
     * @param segmentVO
     * @return
     */
    public EquityIndexHedgeDocument getEquityIndexHedgeDocument(Segment segment, ProductStructure productStructure)
    {
        return new EquityIndexHedgeDocument(segment, productStructure);
    }

    public RedoDocument getRedoDocument(EDITTrxVO editTrxVO)
    {
        return new RedoDocument(editTrxVO);
    }

    /**
     * NaturalDocs requires a Reinsurance addition for certain transaction types.
     * @param contractTreaty
     * @return
     */
    public ReinsuranceDocument getReinsuranceDocument(ContractTreaty contractTreaty)
    {
        return new ReinsuranceDocument(contractTreaty);
    }

    /**
     * Transactions of type ReinsuranceCheck 'RCK' use a ReinsuranceDoc for their financial calculations.
     * @param clientTrx
     * @return
     */
    public ReinsuranceCheckDocument getReinsuranceCheckDocument(ClientTrx clientTrx)
    {
        return new ReinsuranceCheckDocument(clientTrx);
    }

    /**
     * Factory pattern used to build the ProposalDocument
     * @return
     */
    public ProposalDocument getProposalDocument(SegmentVO segmentVO, String proposalDate)
    {
        return new ProposalDocument(segmentVO, proposalDate);
    }
}
