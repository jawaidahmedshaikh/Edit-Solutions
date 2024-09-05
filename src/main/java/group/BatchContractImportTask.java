package group;

import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edit.common.EDITDateTime;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import group.component.ImportNewBusinessBatch;

@Component
@Scope("batchContractImport")
public class BatchContractImportTask implements Runnable {
	
	private Long importId;
	private byte[] fileContent;
	private BatchContractImportFile importRecord;
	
	public BatchContractImportTask(Long importId, byte[] fileContent)
	{
		this.importId = importId;
		this.fileContent = fileContent;
	}
	
	@Override
	public void run() {
		Session sess = SessionHelper.getNewSession(SessionHelper.EDITSOLUTIONS);
		BatchContractImportFile importRec = (BatchContractImportFile) 
					sess.createCriteria(BatchContractImportFile.class)
					.add(Restrictions.eq("batchContractImportFilePk", importId)).list().get(0);
		Transaction trans = sess.beginTransaction();		
		try {
			// represent the file contents (hopefully XML) as a dom4j document
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new ByteArrayInputStream(fileContent));
			
			ImportNewBusinessBatch importer = new ImportNewBusinessBatch(doc, 
					importRec.getBatchContractSetupFk(), importRec.getOperator());
			importer.execute(sess, importRec);
			
            importRec.setCompletedTime(new EDITDateTime());
		} catch (Exception e) {
			trans.rollback();
			// if a failure occurs, try to update the database with an error status
			Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);
			Transaction trans2 = sess.beginTransaction();
			try {
				importRec.setStatus(BatchContractImportFile.STATUS_ERROR);
				String stackTrace = org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e);
				importRec.setMessage(stackTrace);
				importRec.setModifiedTime(new EDITDateTime());
				sess.update(importRec);
				trans2.commit();
			} catch(Exception e2) {
				trans2.rollback();
				logger.error(new logging.LogEvent("Failed to save changes to the import record " + importId, e2));
			}
			
			logger.error(new logging.LogEvent("An error occurred while running the NB import for record " + importId, e));
			throw new RuntimeException(e);
		}
	}

}
