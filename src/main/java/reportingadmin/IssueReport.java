/*
 * User: cgleason
 * Date: May 14, 2004
 * Time: 1:19:13 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package reportingadmin;

import codetable.business.CodeTable;

import codetable.component.CodeTableComponent;

import edit.common.vo.BucketVO;
import edit.common.vo.DepositsVO;
import edit.common.vo.EDITReport;
import edit.common.vo.EDITServicesConfig;
import edit.common.vo.InvestmentVO;
import edit.common.vo.IssueDocumentVO;
import edit.common.vo.IssueInvestmentVO;
import edit.common.vo.IssueReportVO;
import edit.common.vo.LifeVO;
import edit.common.vo.RatesVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.VOObject;

import edit.services.config.ServicesConfig;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPOutput;

import fission.utility.UtilFile;

import java.util.ArrayList;
import java.util.List;


public class IssueReport extends Report
{
    private long segmentPK;
    private String issueDate;

    private IssueReportVO issueReportVO;
    /**
     * Constructor: set the segmentPK and IssueReportVO
     * @param segmentPK
     */
    public IssueReport(long segmentPK, String issueDate)
    {
        this.segmentPK = segmentPK;

        this.issueDate = issueDate;

        this.issueReportVO = new IssueReportVO();
    }

    /**
     * IssueDocumentVO will be built with the data needed to process the Issue report script, in order to produce the report.
     * After script processing, the updates that occurred are placed into the IssueDocumentVO.  This VO aong with the file name of the
     * report are set into the IssueReportVO.
     */
    public void generateReport()
    {
        IssueDocumentVO issueDocumentVO = getIssueDocumentVO();

        long productStructurePK = issueDocumentVO.getProductStructureVO().getProductStructurePK();

        Calculator calculator = new CalculatorComponent();

        try
        {
            SPOutput spOutput = calculator.processScript("IssueDocumentVO", issueDocumentVO, "IssueReport", "*", "*", issueDate, productStructurePK, true);

            VOObject[] output = spOutput.getSPOutputVO().getVOObject();

            mapOutputToIssueDocVO(output, issueDocumentVO);

            issueReportVO.setIssueDocumentVO(issueDocumentVO);

            setFileName(issueDocumentVO);

        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * The issue report entity will return the IssueReportVO it contains.
     * @return
     */
    public IssueReportVO getReportAsVO()
    {
        return issueReportVO;
    }
    /**
     * The VO's updated during script processing are set into the original IssueDocumentVO
     * @param voObjects
     * @param issueDocumentVO
     */
    private void mapOutputToIssueDocVO(VOObject[] voObjects, IssueDocumentVO issueDocumentVO)
    {
        IssueDocumentVO newIssueDocumentVO = null;
        RatesVO newRatesVO = null;
        BucketVO newBucketVO = null;
        SegmentVO segmentVO = null;
        List lifeVO = new ArrayList();

        for (int i = 0; i < voObjects.length; i++)
        {
            VOObject voObject = voObjects[i];

            if (voObject instanceof IssueDocumentVO)
            {
                newIssueDocumentVO = (IssueDocumentVO) voObject;
                setIssueDocumentFields(newIssueDocumentVO, issueDocumentVO);
            }

            if (voObject instanceof RatesVO)
            {
                newRatesVO = (RatesVO)voObject;
                setRateVO(newRatesVO, issueDocumentVO);
            }

            if (voObject instanceof BucketVO)
            {
                newBucketVO = (BucketVO)voObject;
                setBucketVO(newBucketVO, issueDocumentVO);
            }

            if (voObject instanceof SegmentVO)
            {
                segmentVO = (SegmentVO) voObject;
            }

            if (voObject instanceof LifeVO)
            {
                lifeVO.add(voObject);
            }
        }

        if (segmentVO != null)
        {
            if (lifeVO.size() > 0)
            {
                segmentVO.setLifeVO((LifeVO[]) lifeVO.toArray(new LifeVO[lifeVO.size()]));
            }

            SegmentVO originalSegmentVO = issueDocumentVO.getSegmentVO();
            DepositsVO[] depositsVOs = originalSegmentVO.getDepositsVO();
            segmentVO.setDepositsVO(depositsVOs);

            issueDocumentVO.setSegmentVO(segmentVO);
        }
    }

    /**
     * Update individual IssueDocumentVO fields
     * @param newIssueDocumentVO
     * @param issueDocumentVO
     */
    private void setIssueDocumentFields(IssueDocumentVO newIssueDocumentVO, IssueDocumentVO issueDocumentVO)
    {
        if (newIssueDocumentVO.hasFreeLookDays())
        {
            issueDocumentVO.setFreeLookDays(newIssueDocumentVO.getFreeLookDays());
        }

        if (newIssueDocumentVO.getGuarMinCashSurrenderRate() != null )
        {
            issueDocumentVO.setGuarMinCashSurrenderRate(newIssueDocumentVO.getGuarMinCashSurrenderRate());
        }

        if (newIssueDocumentVO.getSevenPayRate() != null)
        {
            issueDocumentVO.setSevenPayRate(newIssueDocumentVO.getSevenPayRate());
        }
    }

    /**
     * The RatesVO is created during script processing, it is now set into the IssueDocumentVO.
     * @param newRatesVO
     * @param issueDocumentVO
     */
     private void setRateVO(RatesVO newRatesVO, IssueDocumentVO issueDocumentVO)
     {
         IssueInvestmentVO[] issueInvestmentVOs = issueDocumentVO.getIssueInvestmentVO();

         if (issueInvestmentVOs.length > 1)
         {
             for (int i = 0; i < issueInvestmentVOs.length; i++)
             {
                 InvestmentVO investmentVO = issueInvestmentVOs[i].getInvestmentVO();
                 if (newRatesVO.getFilteredFundFK() == investmentVO.getFilteredFundFK())
                 {
                     issueInvestmentVOs[i].setRatesVO(newRatesVO);
                     break;
                 }
             }
         }
         else
         {
            issueInvestmentVOs[0].setRatesVO(newRatesVO);
         }
     }

    /**
     * The BucketVO is created during script processing, it is now set into the IssueDocumentVO.
     * @param newBucketVO
     * @param issueDocumentVO
     */
     private void setBucketVO(BucketVO newBucketVO, IssueDocumentVO issueDocumentVO)
     {
        IssueInvestmentVO[] issueInvestmentVOs = issueDocumentVO.getIssueInvestmentVO();
        long newBucketInvestmentFK = newBucketVO.getInvestmentFK();

        for (int i = 0; i < issueInvestmentVOs.length; i++)
        {
            InvestmentVO investmentVO = issueInvestmentVOs[i].getInvestmentVO();
            BucketVO[] bucketVOs = investmentVO.getBucketVO();
            long investmentFK = 0;
            long investmentPK = investmentVO.getInvestmentPK();

            if (bucketVOs.length > 0)
            {
                for (int j = 0; j < bucketVOs.length; j++)
                {
                    investmentFK = bucketVOs[j].getInvestmentFK();
                    if (investmentFK == newBucketInvestmentFK)
                    {
                        investmentVO.setBucketVO(j, newBucketVO);
                        break;
                    }
                }
            }
            else
            {
                if (newBucketInvestmentFK == investmentPK)
                {
                    investmentVO.addBucketVO(newBucketVO);
                }
            }
        }
    }

    /**
     * Invoke codetable component, with the segementPK set in this entity, to build the IssueDocument.
     * @return IssueDocumentVO
     */
    private IssueDocumentVO getIssueDocumentVO()
    {
        CodeTable codeTableComponent = new CodeTableComponent();

        IssueDocumentVO issueDocumentVO = codeTableComponent.buildIssueDocument(segmentPK, issueDate);

        return issueDocumentVO;
    }

    /**
     * The FilteredOnlineReport and OnlineReport tables will provide the jsp page name for the report requested.  The product structure
     * of the contract and the report category of Issue will get the file name.  Using EDITServiceConfig area of EDITReport,
     * and the report name of "OnlineReport", the directory name to be used is retrieved.  The combination of the directory and
     * the fiel name are set in the IssueReportVO.
     * @param issueDocumentVO
     * @throws Exception
     */
    private void setFileName(IssueDocumentVO issueDocumentVO)  throws Exception
     {
         SegmentVO segmentVO = issueDocumentVO.getSegmentVO();

         //use productStructure to get reportId - could be more than one report?
         long productStructurePK = segmentVO.getProductStructureFK();
         String directoryName = null;

         //get filename from edit service config
         EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
         EDITReport[] editReport = editServicesConfig.getEDITReport();

         for (int i = 0; i < editReport.length; i++)
         {
             String reportName = editReport[i].getReportName();
             if (reportName.equalsIgnoreCase("OnlineReport"))
             {
                directoryName = editReport[i].getWebDirectory();
                break;
             }
         }

         String fileName = super.getFileName(productStructurePK, "Issue");
         fileName = directoryName + UtilFile.DIRECTORY_DELIMITER + fileName;
         issueReportVO.setFileName(fileName);
     }
}
