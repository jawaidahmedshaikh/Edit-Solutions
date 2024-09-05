package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.BatchDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Batch;

public class BatchServiceImpl implements BatchService {
	private BatchDao batchDao;
	
	public BatchServiceImpl() {
		super();
		batchDao = new BatchDao();
	}

	public boolean batchExists(String name) throws DAOException {
		if (batchDao.getBatch(name) != null) {
		    return true;
		} else {
		    return false;
		}
	}

	public Batch getBatch(Long prdSettingsPK) throws DAOException {
		return batchDao.getBatch(prdSettingsPK);
	}

	public Batch getBatch(String name) throws DAOException {
		return batchDao.getBatch(name);
	}

	public Batch saveBatch(Batch batch) throws DAOException {
		return batchDao.saveBatch(batch);
	}

	public List<Batch> getBatches() throws DAOException {
		return batchDao.getBatches();
	}

}