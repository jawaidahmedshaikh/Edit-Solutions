package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.SetupContactDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SetupContact;

public class SetupContactServiceImpl implements SetupContactService {
	private SetupContactDao setupContactDao;

	public SetupContactServiceImpl() {
		super();
		setupContactDao = new SetupContactDao();
	}
	public List<SetupContact> getSetupContacts(PRDSettings prdSettings) throws DAOException {
        return setupContactDao.getSetupContacts(prdSettings); 
	}

	public SetupContact saveSetupContact(SetupContact setupContact) throws DAOException {
		if (setupContact.getSetupContactPK() == null) {
		    return setupContactDao.createSetupContact(setupContact);
		} else {
		    return setupContactDao.updateSetupContact(setupContact);
		}
	}
	public void deleteSetupContact(SetupContact setupContact) throws DAOException {
        setupContactDao.deleteSetupContact(setupContact);
	}

}
