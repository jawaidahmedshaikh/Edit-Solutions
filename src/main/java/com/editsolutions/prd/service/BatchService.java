package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Batch;

public interface BatchService {
	
	
	public boolean batchExists(String name) throws DAOException;
	public Batch getBatch(String name) throws DAOException;
	public Batch getBatch(Long prdSettingsPK) throws DAOException; 
	public List<Batch> getBatches() throws DAOException;
	public Batch saveBatch(Batch batch) throws DAOException;



}
