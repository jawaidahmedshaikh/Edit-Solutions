package group.component;

import edit.common.EDITDateTime;
import edit.services.db.hibernate.SessionHelper;
import group.BatchContractImportFile;
import group.BatchContractImportTask;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Provides an interface to submit and report on 
 * status of batch contract jobs
 */
@RestController
@RequestMapping("/batch-contract")
public class BatchContractImportService {

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	
	private String reportServerRoot;
	
	public BatchContractImportService() {
		objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		this.reportServerRoot = System.getProperty("ReportServerRoot");
	}
	
	
	/**
	 * Retrieves 
	 * @param importId
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value="/{importId}", method=RequestMethod.GET, produces={"application/json", "text/plain"})
	public ResponseEntity<String> file(@PathVariable("importId") Long importId) throws JsonProcessingException
	{
		Session esSess = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
		BatchContractImportFile rec = (BatchContractImportFile) esSess.createCriteria(BatchContractImportFile.class)
				.add(Restrictions.eq("batchContractImportFilePk", importId))
				.setFetchMode("batchContractImportRecords", FetchMode.JOIN)
				.setFetchMode("batchContractImportRecords.batchContractImportRecordLogs", FetchMode.JOIN)
				.uniqueResult();
		
		if(rec == null)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("status", "error");
			map.put("message", "No batch contract import record with primary key of " + importId + " found.");
			String errorResp = objectMapper.writeValueAsString(map);
			return new ResponseEntity<String>(errorResp, HttpStatus.NOT_FOUND);
		}
		
		String baseUrl = this.reportServerRoot + "/Pages/ReportViewer.aspx?%2fReports%2fRNB0045-BatchContractLog&rs:Command=Render&ImportId=" + 
			rec.getBatchContractImportFilePk() + "&ReportType=";
		rec.setSuccessReportUrl(baseUrl + "SUCCESS");
		rec.setErrorReportUrl(baseUrl + "ERROR");
		rec.setWarningReportUrl(baseUrl + "WARNING");
		
		ObjectWriter objectWriter = objectMapper.writerWithView(BatchContractImportFile.Views.Full.class);
		String jsonResp = objectWriter.writeValueAsString(rec);
		esSess.close();
		return new ResponseEntity<String>(jsonResp, HttpStatus.OK);
	}
	
	/**
	 * List the most recent 100 jobs
	 * @return A list of 100 of the most recent BatchContractImportFile records
	 * @throws IOException 
	 * @throws JsonMappingException  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, produces={"application/json", "text/plain"})
	public ResponseEntity<String> files() throws JsonMappingException, IOException {
		Session esSess = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
		List<BatchContractImportFile> recs = esSess.createCriteria(BatchContractImportFile.class)
				.addOrder(Order.desc("modifiedTime"))
				.setMaxResults(100).list();
		
	      for (BatchContractImportFile rec : recs)
	      {
	        String baseUrl = this.reportServerRoot + "/Pages/ReportViewer.aspx?%2fReports%2fRNB0045-BatchContractLog&rs:Command=Render&ImportId=" + 
	          rec.getBatchContractImportFilePk() + "&ReportType=";
	        rec.setSuccessReportUrl(baseUrl + "SUCCESS");
	        rec.setErrorReportUrl(baseUrl + "ERROR");
	        rec.setWarningReportUrl(baseUrl + "WARNING");
	      }
		
		ObjectWriter objectWriter = objectMapper.writerWithView(BatchContractImportFile.Views.Condensed.class);
		String jsonResp = objectWriter.writeValueAsString(recs);
		esSess.close();
		return new ResponseEntity<String>(jsonResp, HttpStatus.OK);		
	}	
	
	private ResponseEntity<String> createBadRequest(String message) throws JsonMappingException, IOException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "error");
		map.put("message", message);
		String errorResp = objectMapper.writeValueAsString(map);
		return new ResponseEntity<String>(errorResp, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Submit a new job with an XML job specification in the POST payload
	 * @return Details about the submitted job
	 * @throws IOException 
	 * @throws JsonMappingException  
	 * @throws NoSuchAlgorithmException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.POST, produces={"application/json", "text/plain"})
	public ResponseEntity<String> issueJob(@RequestParam("file") MultipartFile file, 
			@RequestParam(required=true, value="batchContractSetupPk") Long batchContractSetupPK,
			@RequestParam(required=true, value="operator") String operator) 
			throws JsonMappingException, IOException, NoSuchAlgorithmException {		
		
		if(file.isEmpty()) {
			return createBadRequest("The upload file was not present or empty.");
		}
		
		BatchContractImportFile fileImport = new BatchContractImportFile();
		fileImport.setCreationTime(new EDITDateTime());
		fileImport.setModifiedTime(new EDITDateTime());
		fileImport.setOperator(operator);
		fileImport.setBatchContractSetupFk(batchContractSetupPK);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(file.getBytes());
		String hexDigest = String.copyValueOf(Hex.encodeHex(digest));
		fileImport.setFileMD5Hash(hexDigest);
		
		fileImport.setSourceFileName(file.getOriginalFilename());
		fileImport.setStatus(BatchContractImportFile.STATUS_PENDING);
		
		Session esSess = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS);
		esSess.save(fileImport);
		
		taskExecutor.execute(new BatchContractImportTask(fileImport.getBatchContractImportFilePk(), file.getBytes()));
		
		ObjectWriter objectWriter = objectMapper.writerWithView(BatchContractImportFile.Views.Condensed.class);
		String jsonResp = objectWriter.writeValueAsString(fileImport);
		esSess.close();
		return new ResponseEntity<String>(jsonResp, HttpStatus.OK);
	}	
}
