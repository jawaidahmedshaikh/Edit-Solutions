package com.editsolutions.prd.service;


import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.BatchDetail;
import com.editsolutions.prd.vo.Batch;
import com.editsolutions.prd.vo.PRDSettings;

public interface BatchDetailService {
	
	public BatchDetail getBatchDetail(PRDSettings prdSettings) throws DAOException;
	public List<BatchDetail> getBatchDetails(Batch batch) throws DAOException;
	public List<BatchDetail> saveBatchDetails(Batch batch) throws DAOException;
	public List<BatchDetail> getDefaultBatchDetails() throws DAOException;
	public List<PRDSettings> getBatchPRDSettings(Date extractDate) throws DAOException;
	public boolean isInBatch(PRDSettings prdSettings) throws DAOException;

}
