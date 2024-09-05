/*
 * User: sprasad
 * Date: Oct 9, 2007
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package conversion;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.exceptions.SEGConversionException;
import edit.common.vo.EDITExport;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import engine.business.Calculator;
import engine.component.CalculatorComponent;
import engine.sp.SPOutput;
import engine.sp.custom.document.ConversionDocument;
import engine.sp.custom.document.PRASEDocBuilder;
import fission.utility.DateTimeUtil;

/**
 * Conversion Job to convert client data in to SEG Canonical Form/VOs that can
 * be readable by scripts
 */
public class ConversionJob extends HibernateEntity implements
		ConversionListener {
	public static final String RESULT_TYPE_GENERIC_ROOT_ELEMENT_NAME = "GenericXML";

	public static final String RESULT_TYPE_CONVERTED_ROOT_ELEMENT_NAME = "ConvertedXML";

	/**
	 * Conversions are a two-phase process. The first phase is to convert the
	 * flat-file to raw XML.
	 */
	public static final String PHASE_XML = "XML";

	/**
	 * Conversions are a two-phase process. The second phase is to convert the
	 * raw XML to Hibernate entities within PRASE.
	 */
	public static final String PHASE_PRASE = "PRASE";

	/**
	 * Users can specify a discrete number of Groups to process, or All of them
	 * which is signified by this value.
	 */
	public static int NUMBER_OF_GROUPS_ALL = 0;

	/**
	 * We don't allow any return sample to contain more than this many
	 * Group(ings) - simply too large to return effectively to a caller.
	 */
	public static int MAX_RETURN_SAMPLE = 1000;

	/**
	 * Identifier
	 */
	private Long conversionJobPK;

	/**
	 * Pointer to ConversionTemplate this ConversionJob belongs to
	 */
	private Long conversionTemplateFK;

	/**
	 * Conversion Job Name
	 */
	private String jobName;

	/**
	 * Time the Conversion Job is started
	 */
	private EDITDateTime startTime;

	/**
	 * Time the Conversion Job is finished
	 */
	private EDITDateTime endTime;

	/**
	 * The name of the flat file (located in a pre-defined directory) that
	 * contains the data to convert.
	 */
	private String fileName;

	/**
	 * The Conversion Template used by this Job
	 */
	private ConversionTemplate conversionTemplate;

	/**
	 * Target database for lookups
	 */
	public static final String DATABASE = SessionHelper.ENGINE;

	/**
	 * A system defined company for the conversion process.
	 */
	public static final String COMPANY = "Conversion";

	/**
	 * The system ProductStructure driving the conversion scripts.
	 */
	private ProductStructure productStructure;

	/**
	 * If true, then don't dispatch to PRASE - only generate the flat-file to
	 * raw XML. Default to true.
	 */
	private boolean genericXMLOnly;

	/**
	 * Many conversion attempts are simply to return a "sample". Set this flag
	 * to true if the results should be captured and returned to the caller.
	 * Defaults to true.
	 */
	private boolean returnSample;

	/**
	 * The number of Group(ings) to convert as specified by the caller.
	 * Groupings are defined by the set of lines of the flat-file between the
	 * header line.
	 */
	private int numberOfGroups;

	/**
	 * The number of Group(ings) actually converted. the user may set a ceiling
	 * for the number of Group(ings) to actually process - perhaps for sampling
	 * reasons.
	 */
	private int groupCount;

	private int fatalErrorCount = 0;

	/**
	 * Stores the results of the conversion process when possible.
	 */
	Element result;

	public ConversionJob() {
		setReturnSample(true); // default

		setGenericXMLOnly(true); // default
	}

	/**
	 * @see conversion.ConversionJob#conversionJobPK
	 * @warn Do not use this method explicitly. Hibernate uses this method
	 *       internally.
	 * @param conversionJobPK
	 */
	public void setConversionJobPK(Long conversionJobPK) {
		this.conversionJobPK = conversionJobPK;
	}

	/**
	 * @see conversion.ConversionJob#conversionJobPK
	 * @return
	 */
	public Long getConversionJobPK() {
		return conversionJobPK;
	}

	/**
	 * @see conversion.ConversionJob#conversionTemplateFK
	 * @warn Do not use this method explicitly. Hibernate uses this method
	 *       internally.
	 * @param conversionTemplateFK
	 */
	public void setConversionTemplateFK(Long conversionTemplateFK) {
		this.conversionTemplateFK = conversionTemplateFK;
	}

	/**
	 * @see conversion.ConversionJob#conversionTemplateFK
	 * @return
	 */
	public Long getConversionTemplateFK() {
		return conversionTemplateFK;
	}

	/**
	 * @see conversion.ConversionJob#jobName
	 * @param jobName
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @see conversion.ConversionJob#jobName
	 * @return
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @see conversion.ConversionJob#startTime
	 * @param startTime
	 */
	public void setStartTime(EDITDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @see conversion.ConversionJob#startTime
	 * @return
	 */
	public EDITDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @see conversion.ConversionJob#endTime
	 * @param endTime
	 */
	public void setEndTime(EDITDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @see conversion.ConversionJob#endTime
	 * @return
	 */
	public EDITDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @see conversion.ConversionJob#conversionTemplate
	 * @param conversionTemplate
	 */
	public void setConversionTemplate(ConversionTemplate conversionTemplate) {
		this.conversionTemplate = conversionTemplate;
	}

	/**
	 * @see conversion.ConversionJob#conversionTemplate
	 * @return
	 */
	public ConversionTemplate getConversionTemplate() {
		return conversionTemplate;
	}

	/**
	 * @see conversion.ConversionJob#DATABASE
	 * @return
	 */
	public String getDatabase() {
		return ConversionJob.DATABASE;
	}

	/**
	 * Persists to Database
	 */
	public void hSave() {
		SessionHelper.saveOrUpdate(this, this.getDatabase());
	}

	/**
	 * Deletes from Database
	 */
	public void hDelete() {
		SessionHelper.delete(this, this.getDatabase());
	}

	/**
	 * Uses the fileName and ConversionTemplate of this ConversionJob to
	 * generate converted data. It is possible that the caller wishes to see
	 * only the generic XML created from the flat file and fed to PRASE. In that
	 * case, they flag this with the sampleXMLOnly flag.
	 * 
	 * @throws SEGConversionException
	 * @return Element - depends on the state of the conversion - if
	 *         sampleXMLOnly is true, then only the generic XML will be returned
	 */
	public void runConversion() throws Exception {
		long startTime = System.currentTimeMillis();

		ConversionTemplate conversionTemplate = getConversionTemplate();

		String outputFile = getExportFile("ConversionResults.txt");
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(outputFile, true)))) {

			conversionTemplate.convert(this, getFileName(), out);

			long endTime = System.currentTimeMillis();

			long difference = (endTime - startTime);

			String processTime = DateTimeUtil.convertTimeToHHMMSSss(difference);

			int successCount = getGroupCount() - this.fatalErrorCount;

			out.println();
			out.println("-----------------------------------------------------");
			out.println("Job:  " + this.jobName);
			out.println("File:  " + this.fileName);
			out.println("XML Only (No PRASE):  " + this.genericXMLOnly);
			out.println();
			out.println("Processing Time:  " + processTime);

			if (!this.genericXMLOnly) {
				out.println();
				out.println("Total XML Elements Processed:  " + getGroupCount());
				out.println("Total Successes:  " + successCount);
				out.println("Total Fatal Errors:  " + this.fatalErrorCount);
			}

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}

	/**************************** Static Methods *********************************/

	/**
	 * If a job already exists by the specified conversionJob name, then that
	 * ConversionJob is returned unmodified. Otherwise, a new ConversionJob
	 * object is created and populated with the supplied data.
	 * 
	 * @param conversionJobName
	 * @param fileName
	 * @param conversionTemplatePK
	 * @return
	 */
	public static ConversionJob build_V1(String conversionJobName,
			String fileName, Long conversionTemplatePK) {
		// 9/28/2011 - Syam Lingamallu
		// This change is needed to run conversion on multiple machines
		// Instead of getting the existing job by job name, I am creating new
		// job everytime.
		ConversionJob conversionJob = null; // ConversionJob.findBy_JobName(conversionJobName);

		// if (conversionJob == null)
		// {
		conversionJob = new ConversionJob();

		conversionJob.setJobName(conversionJobName);

		conversionJob.setFileName(fileName);

		ConversionTemplate conversionTemplate = (ConversionTemplate) SessionHelper
				.get(ConversionTemplate.class, conversionTemplatePK, DATABASE);

		conversionJob.setConversionTemplate(conversionTemplate);
		// }

		return conversionJob;
	}

	/**
	 * Finder By ConversionTemplate. Retrieves all ConversionJobs belonging to a
	 * ConversionTemplate
	 * 
	 * @param conversionTemplateFK
	 * @return - an array ConversionJob objects
	 */
	public static final ConversionJob[] findByConversionTemplateFK(
			Long conversionTemplateFK) {
		String hql = " select conversionJob from ConversionJob conversionJob"
				+ " where conversionJob.ConversionTemplateFK = :conversionTemplateFK";

		EDITMap map = new EDITMap("conversionTemplateFK", conversionTemplateFK);

		List results = SessionHelper.executeHQL(hql, map, DATABASE);

		return (ConversionJob[]) results.toArray(new ConversionJob[results
				.size()]);
	}

	/**
	 * Finder.
	 * 
	 * @return
	 */
	public static final ConversionJob[] findAll() {
		String hql = "from ConversionJob";

		List results = SessionHelper.executeHQL(hql, null, DATABASE);

		return (ConversionJob[]) results.toArray(new ConversionJob[results
				.size()]);
	}

	/**
	 * Finder.
	 * 
	 * @param jobName
	 * @return
	 */
	public static ConversionJob findBy_JobName(String jobName) {
		ConversionJob conversionJob = null;

		String hql = " from ConversionJob conversionJob"
				+ " where conversionJob.JobName = :jobName";

		Map<String, String> params = new HashMap<String, String>();

		params.put("jobName", jobName);

		List results = SessionHelper.executeHQL(hql, params, DATABASE);

		if (!results.isEmpty()) {
			conversionJob = (ConversionJob) results.get(0); // Unique by
															// jobName.
		}

		return conversionJob;
	}

	/**
	 * @see #fileName
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @see #fileName
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Initiates the start time.
	 */
	public void flagStartConversion() {
		this.setStartTime(new EDITDateTime());

		getConversionTemplate().setContinueProcessing(true);
	}

	/**
	 * Initiates the stop time.
	 */
	public void flagStopConversion() {
		this.setEndTime(new EDITDateTime());

		getConversionTemplate().setContinueProcessing(false);
	}

	/**
	 * Raw XML is converted one Group at a time and then dispatched to PRASE for
	 * further processing.
	 * 
	 * @param groupNodeElement
	 * @return any results from PRASE - these results may not be meaninful since
	 *         the end-conversion (commits to the DB) are done in PRASE - not
	 *         here
	 */
	private SPOutput dispatchToPRASE(Element groupNodeElement) throws Exception {
		PRASEDocBuilder builder = new ConversionDocument(groupNodeElement);

		ProductStructure productStructure = getProductStructure();

		Calculator calculator = new CalculatorComponent();

		boolean abortOnHardValidationError = true;

		SPOutput spOutput = calculator.processScriptWithDocument(
				ConversionDocument.ROOT_ELEMENT_NAME, builder,
				getConversionTemplate().getTemplateName(), "*", "*",
				new EDITDate(EDITDate.DEFAULT_MAX_DATE).getFormattedDate(),
				productStructure.getProductStructurePK(),
				abortOnHardValidationError);

		return spOutput;
	}

	/**
	 * @see #productStructure
	 * @param productStructure
	 */
	public void setProductStructure(ProductStructure productStructure) {
		this.productStructure = productStructure;
	}

	/**
	 * @see #productStructure
	 * @return
	 */
	public ProductStructure getProductStructure() {
		if (this.productStructure == null) {
			productStructure = ProductStructure.findBy_CompanyName_V2(COMPANY);
		}

		return productStructure;
	}

	/**
	 * Callback method for the conversion process. Every converted Group will be
	 * called-back to this method with each successful flat-file to Group(ing)
	 * converted.
	 * 
	 * @param conversionTemplate
	 * @param rawXML
	 *            the raw XML to ultimately be converted to meaningful entities
	 *            within PRASE
	 */
	public void conversionResult(ConversionTemplate conversionTemplate,
			List<String> groupFlatFileLines, Element rawXML) {
		updateGroupCount();

		if (!isGenericXMLOnly()) {
			try {
				SPOutput spOutput = dispatchToPRASE(rawXML);

				if (shouldReturnSample()) {
					Element spOutputElement = spOutput.getSPOutputVODocument()
							.getRootElement();

					getResult().add(spOutputElement);
				}
			} catch (Exception e) {
				System.out.println("conversionResult: " + e);

				e.printStackTrace();

				if (shouldReturnSample()) {
					addResultMessage(e.toString());
				}

				logConversionFault(conversionTemplate, groupFlatFileLines,
						PHASE_PRASE, e);

				conversionTemplate.setContinueProcessing(true);

			} finally {
				SessionHelper.clearSessions();
				SessionHelper.clearThreadLocals();
				SessionHelper.closeSessions();
			}
		} else if (isGenericXMLOnly()) {
			if (shouldReturnSample()) {
				getResult().add(rawXML);
			}
		}

		if (!shouldContinueProcessing()) {
			conversionTemplate.setContinueProcessing(false);
		}
	}

	/**
	 * Method to send build elements of conversion xml and send to PRASE.
	 * 
	 * @param conversionTemplate
	 * @param filePath
	 *            - File where conversion XML has been built
	 */
	public void conversionResult(ConversionTemplate conversionTemplate,
			String filePath, PrintWriter out) throws Exception {
		if (!isGenericXMLOnly()) {
			Element groupElement = null;
			Element recordElement = null;
			Element columnElement = null;
			Element nameElement = null;
			Element valueElement = null;

			try {
				XMLInputFactory factory = XMLInputFactory.newInstance();
				XMLEventReader eventReader = factory
						.createXMLEventReader(new FileReader(filePath));

				boolean isName = false;
				boolean isValue = false;

				long startElapseTime = System.currentTimeMillis();
				long startTime = System.currentTimeMillis();
				int previousGroupCount = 0;
				int count = 1;

				int BATCH_SIZE = 749;
				int batchCount = 0;

				List<Element> groupElements = new ArrayList<>();
				List<Future> futures = new ArrayList<Future>();
				ExecutorService executorService;

				while (eventReader.hasNext()
						&& conversionTemplate.getContinueProcessing()) {
					try {
						XMLEvent event = eventReader.nextEvent();

						switch (event.getEventType()) {
						case XMLStreamConstants.START_ELEMENT:
							StartElement startElement = null;
							try {
								startElement = event.asStartElement();
							} catch (Exception ex) {
								System.out
										.println("****************************************************");
								System.out.println("count: " + count);
								System.out.println("exception: "
										+ ex.toString());
								System.out
										.println("****************************************************");
							}
							String qName = startElement.getName()
									.getLocalPart().trim();
							if (qName
									.equalsIgnoreCase(ConversionDocument.ROOT_ELEMENT_NAME)) {
								groupElement = new DefaultElement(qName);
							} else if (qName.equalsIgnoreCase("RecordVO")) {
								recordElement = new DefaultElement(qName);
							} else if (qName.equalsIgnoreCase("ColumnVO")) {
								columnElement = new DefaultElement(qName);
							} else if (qName.equalsIgnoreCase("Name")) {
								nameElement = new DefaultElement(qName);
								isName = true;
							} else if (qName.equalsIgnoreCase("Value")) {
								valueElement = new DefaultElement(qName);
								isValue = true;
							}
							break;

						case XMLStreamConstants.CHARACTERS:
							Characters characters = event.asCharacters();

							String data = characters.getData().trim();

							if (isName) {
								nameElement.setText(data);
								isName = false;
							} else if (isValue) {
								valueElement.setText(data);

								columnElement.add(nameElement);
								columnElement.add(valueElement);

								recordElement.add(columnElement);

								nameElement = null;
								valueElement = null;
								columnElement = null;
								isValue = false;
							}

							break;

						case XMLStreamConstants.END_ELEMENT:
							EndElement endElement = event.asEndElement();
							qName = endElement.getName().getLocalPart();
							if (endElement
									.getName()
									.getLocalPart()
									.equalsIgnoreCase(
											ConversionDocument.ROOT_ELEMENT_NAME)) {

								if (batchCount < BATCH_SIZE) {
									groupElements.add(groupElement);
									batchCount++;
								} else {
									groupElements.add(groupElement);
									executorService = Executors
											.newFixedThreadPool(7);
									Future future = executorService
											.submit(new PraseDispatcher(
													groupElements,
													getProductStructure(),
													getConversionTemplate(),
													out));
									futures.add(future);
									while (!future.isDone()) { }
									executorService.shutdown();
									groupElements = new ArrayList<>();
									batchCount = 0;
								}

								updateGroupCount();

								// elementHash.put(count, groupElement);

								count++;
								/*
								 * if ((count % 100) == 0) { long endTime =
								 * System.currentTimeMillis(); long difference =
								 * (endTime - startTime); long elapseDifference
								 * = (endTime - startElapseTime); String
								 * processTime =
								 * DateTimeUtil.convertTimeToHHMMSSss
								 * (difference); String processElapseTime =
								 * DateTimeUtil
								 * .convertTimeToHHMMSSss(elapseDifference);
								 * startTime = System.currentTimeMillis();
								 * //System.out.print("Line " + count + ": " +
								 * flatFileLine.substring(0,
								 * Math.min(flatFileLine.length(), 50)) +
								 * "...");
								 * //System.out.println(previousGroupCount +
								 * " thru " + count + " time: " + processTime +
								 * " - Elapse Time: " + processElapseTime +
								 * "..." ); //out.println(previousGroupCount +
								 * " thru " + count + " time: " + processTime +
								 * " - Elapse Time: " + processElapseTime +
								 * "..." ); //out.println(previousGroupCount +
								 * "," + count + "," + processTime + "," +
								 * processElapseTime); previousGroupCount =
								 * count; }
								 */

								// System.out.println("**********************************");
								// System.out.println(getGroupCount());

								// groupElement = null;
							} else if (endElement.getName().getLocalPart()
									.equalsIgnoreCase("RecordVO")) {

								groupElement.add(recordElement);

								recordElement = null;
							}
							break;
						}
					} catch (Exception e) {
						fatalErrorCount++;

						System.out.println("FatalError: " + e);

						e.printStackTrace();

						if (shouldReturnSample()) {
							addResultMessage(e.toString());
						}

						logConversionFault(conversionTemplate,
								groupElement.asXML(), PHASE_PRASE, e);

						conversionTemplate.setContinueProcessing(true);

						if (!shouldContinueProcessing()) {
							conversionTemplate.setContinueProcessing(false);
						}
					}
				}
				executorService = Executors.newFixedThreadPool(7);
				Future finalFuture = executorService
						.submit(new PraseDispatcher(groupElements,
								getProductStructure(), getConversionTemplate(),
								out));
				futures.add(finalFuture);
				executorService.shutdown();

				boolean threadsComplete = false;
				while (!threadsComplete) {

					Iterator<Future> futuresIt = futures.iterator();
					int nullCount = 0;
					while (futuresIt.hasNext()) {
						if (futuresIt.next().get() == null) {
							nullCount++;
						}
					}
					if (nullCount == futures.size()) {
						threadsComplete = true;
					}
				}
			} catch (Exception e) {
				System.out.println("conversionResult2: " + e);

				throw e;
			} finally {
				SessionHelper.clearSessions();
				SessionHelper.clearThreadLocals();
				SessionHelper.closeSessions();
			}
		}
	}

	/**
	 * Callback for the converion process. When called, there was an error for
	 * the current flat-file to Group conversion. We can decide to continue or
	 * abort the conversion process.
	 * 
	 * @see ConversionListener
	 */
	public void conversionFault(ConversionTemplate conversionTemplate,
			List<String> groupFlatFileLines, Exception e) {
		logConversionFault(conversionTemplate, groupFlatFileLines, PHASE_XML, e);

		conversionTemplate.setContinueProcessing(false);
	}

	/**
	 * Adds a message to the current result document as a Message Element as a
	 * direct child of the root Element.
	 * 
	 * e.g.
	 * 
	 * <FooResultDocument> <Message>Foo Message</Message> </FooResultDocument>
	 * 
	 * @param message
	 */
	private void addResultMessage(String message) {
		Element messageElement = DocumentHelper.createElement("Message");

		messageElement.setText(message);

		getResult().add(messageElement);
	}

	/**
	 * @see #genericXMLOnly
	 * @param genericXMLOnly
	 */
	public final void setGenericXMLOnly(boolean genericXMLOnly) {
		this.genericXMLOnly = genericXMLOnly;
	}

	/**
	 * @see #genericXMLOnly
	 * @return
	 */
	public final boolean isGenericXMLOnly() {
		return genericXMLOnly;
	}

	/**
	 * @see #returnSample
	 * @param returnSample
	 */
	public final void setReturnSample(boolean returnSample) {
		this.returnSample = returnSample;
	}

	/**
	 * @see #returnSample
	 * @return
	 */
	public final boolean isReturnSample() {
		return returnSample;
	}

	/**
	 * We can't blindly return samples back to the caller when the size of the
	 * return document is taken into consideration.
	 * 
	 * @return
	 */
	private boolean shouldReturnSample() {
		boolean shouldReturnSample = false;

		if (getGroupCount() < MAX_RETURN_SAMPLE) {
			if (isReturnSample()) {
				shouldReturnSample = true;
			}
		}

		return shouldReturnSample;
	}

	/**
	 * @see #numberOfGroups
	 * @param numberOfGroups
	 */
	public final void setNumberOfGroups(int numberOfGroups) {
		this.numberOfGroups = numberOfGroups;
	}

	/**
	 * @see #numberOfGroups
	 * @return
	 */
	public final int getNumberOfGroups() {
		return numberOfGroups;
	}

	/**
	 * Determines the proper type of result (generic XML or actual converted
	 * result) and returns it.
	 * 
	 * @return
	 */
	public Element getResult() {
		if (this.result == null) {
			if (isGenericXMLOnly()) {
				this.result = DocumentHelper
						.createElement(RESULT_TYPE_GENERIC_ROOT_ELEMENT_NAME);
			} else {
				this.result = DocumentHelper
						.createElement(RESULT_TYPE_CONVERTED_ROOT_ELEMENT_NAME);
			}
		}
		return result;
	}

	/**
	 * Updates the counter for the number of Group(ings) actually processed
	 * during a conversion run.
	 */
	private void updateGroupCount() {
		int currentGroupCount = getGroupCount();

		currentGroupCount++;

		setGroupCount(currentGroupCount);
	}

	/**
	 * @see #groupCount
	 * @param groupCount
	 */
	private void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	/**
	 * @see #groupCount
	 * @return
	 */
	public int getGroupCount() {
		return groupCount;
	}

	/**
	 * The caller may have set a limit on the number of Group(ings) to process
	 * and/or some other constraints. We evaluate the state of the conversion.
	 * 
	 * @return
	 */
	private boolean shouldContinueProcessing() {
		boolean shouldContinueProcessing = true;

		if (getNumberOfGroups() == NUMBER_OF_GROUPS_ALL) {
			shouldContinueProcessing = true;
		} else if (getGroupCount() >= getNumberOfGroups()) {
			shouldContinueProcessing = false;
		}

		return shouldContinueProcessing;
	}

	/**
	 * During a conversion run, we encounter unforseen faults - most likely from
	 * bad data in the flat file itself. The best we can do is to capture:
	 * 
	 * 1. The flat-file lines that failed as defined by the group(ing). 2. The
	 * Conversion Template. 3. The flat-file name containing the raw data. 4.
	 * The phase the error occured in (either XML phase or PRASE phase)
	 * 
	 * It is possible that the user will manually fix the data in this log file
	 * and then use the file itself as the source of the next conversion run.
	 * For this reason, the header record is modified with the meta information
	 * about the error. This way, the same Conversion Template could be used
	 * against the error log file. e.g.
	 * 
	 * MY_HEADER_RECORD [phase = XML] [conversion-template =
	 * MyConversionTemplate] [exception-message = my exception message].
	 * 
	 * @param conversionTemplate
	 * @param groupFlatFileLines
	 * @param e
	 */
	private void logConversionFault(ConversionTemplate conversionTemplate,
			List<String> groupFlatFileLines, String phase, Exception e) {
		String metaData = buildConversionFaultMetaData(phase,
				conversionTemplate.getTemplateName(), e.toString());

		ConversionData conversionData = new ConversionData(getFileName());

		BufferedWriter logWriter = null;

		try {
			logWriter = conversionData
					.openOutputDataWriter(getConversionTemplate()
							.getTemplateName());

			for (int i = 0; i < groupFlatFileLines.size(); i++) {
				String flatFileLine = groupFlatFileLines.get(i);

				if (i == 0) // the first line is always the header record - we
							// append the meta-data to it
				{
					flatFileLine += " " + metaData;
				}

				if (flatFileLine != null) {
					logWriter.write(flatFileLine);
					logWriter.newLine();
					logWriter.flush();
				}
			}
		} catch (IOException f) {
			System.out.println("logConversionFault: " + e);

			e.printStackTrace();
		} finally {
			if (logWriter != null) {
				conversionData.closeOutputDataWriter();
			}
		}
	}

	private void logConversionFault(ConversionTemplate conversionTemplate,
			String xml, String phase, Exception e) throws Exception {
		String metaData = buildConversionFaultMetaData(phase,
				conversionTemplate.getTemplateName(), e.toString());

		EDITDate currentDate = new EDITDate();

		String logFilePath = getExportFile("Conversion_" + this.jobName
				+ "_ERRORLOG_" + currentDate.getFormattedYear()
				+ currentDate.getFormattedMonth()
				+ currentDate.getFormattedDay() + ".txt");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(logFilePath, true)))) {
			out.println();
			out.println("-----------------------------------------------------");
			out.println("XML ELEMENT # " + getGroupCount() + " - "
					+ e.toString().toUpperCase());
			out.println();
			out.println(metaData);
			out.println("");
			out.println(xml);
			out.println();

		} catch (IOException ie) {
			System.out.println("IOException: " + ie.getMessage());
		}

	}

	/**
	 * Pretty-formats the specified information.
	 * 
	 * @param phase
	 * @param templateName
	 * @param exceptionMessage
	 * @return
	 */
	private String buildConversionFaultMetaData(String phase,
			String templateName, String exceptionMessage) {
		return "[time = " + new EDITDateTime().getFormattedDateTime()
				+ "] [phase = " + phase + "] [conversion-template = "
				+ templateName + "] [conversion-file = " + getFileName()
				+ "] [exception-message = " + exceptionMessage + "]";
	}

	/**
	 * @return exportFile Path to write XML file
	 * 
	 *         Currently set to write "Conversion" file to EDITExports/EAS
	 *         folder
	 */
	private String getExportFile(String fileName) {
		EDITDateTime runDate = new EDITDateTime();
		String dateString = runDate.getFormattedDateTime();

		EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

		String exportFile = export1.getDirectory() + fileName;

		return exportFile;
	}
}

class PraseDispatcher implements Runnable {
	private List<Element> elementList;
	private ProductStructure productStructure;
	private ConversionTemplate conversionTemplate;
	private PrintWriter out;
	private long startTime;
	SPOutput spOutput;

	PraseDispatcher(List<Element> eArray, ProductStructure productStructure,
			ConversionTemplate conversionTemplate, PrintWriter out) {
		this.elementList = eArray;
		this.productStructure = productStructure;
		this.conversionTemplate = conversionTemplate;
		this.out = out;
		startTime = System.currentTimeMillis();
	}

	public void run() {
		try {
			int count = 0;
			final long startElapseTime = System.currentTimeMillis();
			int previousGroupCount = 0;
			Iterator<Element> it = elementList.iterator();
			while (it.hasNext()) {
				Element e = it.next();
				spOutput = dispatchToPRASE(e);
				count++;
				if ((count % 100) == 0) {
					long endTime = System.currentTimeMillis();
					long difference = (endTime - startTime);
					long elapseDifference = (endTime - startElapseTime);
					String processTime = DateTimeUtil
							.convertTimeToHHMMSSss(difference);
					String processElapseTime = DateTimeUtil
							.convertTimeToHHMMSSss(elapseDifference);
					out.println(previousGroupCount + "," + count + ","
							+ processTime + "," + processElapseTime);
					previousGroupCount = count;

				}
			}
			elementList = null;
		} catch (Exception e) {
			System.out.println("dispatchToPrase error: " + e.toString());
		}
	}

	private SPOutput dispatchToPRASE(Element groupNodeElement) throws Exception {
		PRASEDocBuilder builder = new ConversionDocument(groupNodeElement);

		Calculator calculator = new CalculatorComponent();

		boolean abortOnHardValidationError = true;

		try {
			SPOutput spOutput = calculator.processScriptWithDocument(
					ConversionDocument.ROOT_ELEMENT_NAME, builder,
					conversionTemplate.getTemplateName(), "*", "*",
					new EDITDate(EDITDate.DEFAULT_MAX_DATE).getFormattedDate(),
					productStructure.getProductStructurePK(),
					abortOnHardValidationError);
		} catch (Exception ex) {
			System.out.println("conversion exception: "
					+ conversionTemplate.getTemplateName());
			System.out.println("continue conversion: "
					+ conversionTemplate.getContinueProcessing());
		}

		return spOutput;
	}

}
