/*
 * User: sprasad
 * Date: Apr 11, 2006
 * Time: 9:01:49 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import edit.common.vo.ProductStructureVO;
import edit.common.vo.EDITExport;
import edit.common.vo.VOObject;
import edit.common.*;
import edit.services.config.ServicesConfig;
import edit.services.EditServiceLocator;
import edit.services.logging.Logging;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

import fission.utility.Util;
import batch.business.Batch;
import logging.*;
import org.apache.logging.log4j.Logger;

public class TaxExtractProcessor
{
    private static final String TAX_REPORT_TYPE_ALL = "All";
    private static final String TAX_REPORT_TYPE_1099R = "1099-R";
    private static final String TAX_REPORT_TYPE_1099MISC = "1099-Misc";
    private static final String TAX_REPORT_TYPE_5498 = "5498";

    private static final String[] TAX_REPORT_1099_TYPES = { TAX_REPORT_TYPE_1099R, TAX_REPORT_TYPE_1099MISC };

    protected ProductStructureVO[] productStructureVOs = null;

    // fieldNames for rounding.
    protected String[] fieldNames = null;

    public TaxExtractProcessor()
    {
        super();
    }

    /**
     * For the parameters entered, create the TaxExtract
     *
     * @param startDate
     * @param endDate
     * @param productStructure
     * @param taxReportType
     * @param taxYear
     * @param fileType
     */
    public void createTaxReservesExtract(String startDate, String endDate, String productStructure, String taxReportType, String taxYear, String fileType)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).tagBatchStart(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS, "Tax Reserve Extract");

        ProductStructureVO[] productStructureVOs = getProductStructures(productStructure);

        try
        {
            if (taxReportType.equalsIgnoreCase(TAX_REPORT_TYPE_ALL))
            {
                TaxExtract1099Processor extract1099Processor = new TaxExtract1099Processor(productStructureVOs);

                for (int i = 0; i < TAX_REPORT_1099_TYPES.length; i++)
                {
                    extract1099Processor.setTaxReportFilter(TAX_REPORT_1099_TYPES[i]);

                    extract1099Processor.extract(startDate, endDate, taxYear, fileType);
                }

                TaxExtract5498Processor extract5498Processor = new TaxExtract5498Processor(productStructureVOs);

                extract5498Processor.extract(startDate, endDate, taxYear, fileType);
            }
            else if (taxReportType.equalsIgnoreCase(TAX_REPORT_TYPE_1099R) || taxReportType.equalsIgnoreCase(TAX_REPORT_TYPE_1099MISC))
            {
                TaxExtract1099Processor extract1099Processor = new TaxExtract1099Processor(productStructureVOs);

                extract1099Processor.setTaxReportFilter(taxReportType);

                extract1099Processor.extract(startDate, endDate, taxYear, fileType);
            }
            else if (taxReportType.equalsIgnoreCase(TAX_REPORT_TYPE_5498))
            {
                TaxExtract5498Processor extract5498Processor = new TaxExtract5498Processor(productStructureVOs);

                extract5498Processor.extract(startDate, endDate, taxYear, fileType);
            }

//            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).updateSuccess();
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).updateFailure();

            System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Tax Extract Failed", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).tagBatchStop();
        }
    }

    /**
     * The set of ProductStructureVOs relating to the specified productStrucure(PK). All ProductStructureVOs are returned if
     * the productStructure name is "All".
     * @param productStructure
     * @return
     */
    protected ProductStructureVO[] getProductStructures(String productStructure)
    {
        ProductStructureVO[] productStructureVOs = null;

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (productStructure.equalsIgnoreCase("All"))
        {
            productStructureVOs = engineLookup.getAllProductStructures();
        }
        else
        {
            productStructureVOs = engineLookup.findProductStructureVOByPK(Long.parseLong(productStructure), false, null);
        }

        return productStructureVOs;
    }

    protected void setProductStructures(ProductStructureVO[] productStructureVOs)
    {
        this.productStructureVOs = productStructureVOs;
    }

    /**
     * Set up the export file
     *
     * @return File - define name
     */
    protected File getExportFile(String name, String fileType)
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = null;

        if (fileType.equalsIgnoreCase("X"))
        {
            exportFile = new File(export1.getDirectory() + "SEGTAXEXT_" + name + "_" + System.currentTimeMillis() + ".xml");
        }
        else
        {
            exportFile = new File(export1.getDirectory() + "SEGTAXEXT_" + name + "_" + System.currentTimeMillis() + ".tsv");
        }

        return exportFile;
    }

    /**
     * Each new extract record gets appended to the file.
     *
     * @param exportFile
     * @param data
     * @throws Exception
     */
    protected void appendToFile(File exportFile, String data) throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));
        bw.write(data);
        bw.flush();
        bw.close();
    }

    /**
     * Set up the initial identify for the export file.
     *
     * @param exportFile
     * @throws Exception
     */
    protected void insertStartTaxExtracts(File exportFile, String startTag) throws Exception
    {
        appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        appendToFile(exportFile, startTag + "\n");
    }

    /**
     * Ending reocrding for the xml document, export file.
     *
     * @param exportFile
     * @throws Exception
     */
    protected void insertEndTaxExtracts(File exportFile, String endTag) throws Exception
    {
        appendToFile(exportFile, "\n" + endTag);
    }

    /**
     * Round the dollars fields for the those specified.
     *
     * @param voObject
     * @return
     * @throws Exception
     */
    protected String roundDollarFields(VOObject voObject) throws Exception
    {
        return Util.roundDollarTextFields(voObject, fieldNames);
    }
}
