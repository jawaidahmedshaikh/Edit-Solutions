package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SetupContact;

public interface SetupContactService {
	
	public List<SetupContact> getSetupContacts(PRDSettings prdSettings) throws DAOException;
	public SetupContact saveSetupContact(SetupContact setupContact) throws DAOException;
	public void deleteSetupContact(SetupContact setupContact) throws DAOException;

}
