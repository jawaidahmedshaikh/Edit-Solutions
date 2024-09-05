package com.editsolutions.prd.service;


import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.dao.BatchDetailDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.BatchDetail;
import com.editsolutions.prd.vo.Batch;
import com.editsolutions.prd.vo.PRDSettings;

public class BatchDetailServiceImpl implements BatchDetailService {
	private BatchDetailDao batchDetailDao;
	
	public BatchDetailServiceImpl() {
		super();
		batchDetailDao = new BatchDetailDao();
	}
	
	public BatchDetail getBatchDetail(PRDSettings prdSettings) throws DAOException {
		return batchDetailDao.getBatchDetail(prdSettings);
	}
	
	public List<BatchDetail> getDefaultBatchDetails() throws DAOException {
		return batchDetailDao.getDefaultBatchDetails();
	}

	public List<BatchDetail> saveBatchDetails(Batch batch) throws DAOException {
		return batchDetailDao.saveBatchDetails(batch);
	}

	public List<BatchDetail> getBatchDetails(Batch batch) throws DAOException {
		return batchDetailDao.getBatchDetails(batch);
	}
	
	public List<PRDSettings> getBatchPRDSettings(Date extractDate) throws DAOException {
		return batchDetailDao.getBatchPRDSettings(extractDate);
	}
	
	public boolean isInBatch(PRDSettings prdSettings) throws DAOException {
		return batchDetailDao.isInBatch(prdSettings);
	}

}