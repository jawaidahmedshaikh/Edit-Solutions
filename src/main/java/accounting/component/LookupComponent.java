/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 18, 2002
 * Time: 9:14:12 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.component;

import accounting.business.Lookup;
import accounting.dm.dao.DAOFactory;
import edit.common.vo.*;
import edit.services.component.AbstractLookupComponent;

import java.util.ArrayList;
import java.util.List;

public class LookupComponent extends AbstractLookupComponent implements Lookup {

    public ElementVO[] getAllElements() throws Exception {

        ElementVO[] elementVO = DAOFactory.getElementDAO().findAllElements();

        return elementVO;
    }

    public ElementVO[] getAllElementsByProductStructure(long productStructureId) throws Exception {

        ElementCompanyRelationVO[] elementCompanyRelationVO =
                DAOFactory.getElementCompanyRelationDAO().findRelationsByProductStructureId(productStructureId);

		if (elementCompanyRelationVO != null) {

	        List elementIds = new ArrayList();

			for (int i = 0; i < elementCompanyRelationVO.length; i++) {

	            elementIds.add(new Long(elementCompanyRelationVO[i].getElementFK()));
	        }

	        //String[] elementIdArray = (String[]) elementIds.toArray(new String[elementIds.size()]);
            Long[] elementIdArray = (Long[]) elementIds.toArray(new Long[elementIds.size()]);

	        ElementVO[] elementVO = DAOFactory.getElementDAO().findAllElementsInElementIdArray(elementIdArray);

            return elementVO;
		}

		return null;
    }

    public ElementVO[] getAllElementNames() throws Exception {

        ElementVO[] elementVO = DAOFactory.getElementDAO().findAllNames();

        return  elementVO;
    }

	public ElementStructureVO[] getAllElementStructureNames() throws Exception {

    	ElementStructureVO[] elementStructureVO = DAOFactory.getElementStructureDAO().findAllNames();

        return elementStructureVO;
    }

    public ElementVO[] getElementById(long elementId) throws Exception {

    	ElementVO[] elementVO = DAOFactory.getElementDAO().findByElementId(elementId);

        return  elementVO;
    }

    public ElementCompanyRelationVO[] getAllProductStructureIds() throws Exception {

        ElementCompanyRelationVO[] elementCompanyRelationVO = DAOFactory.getElementCompanyRelationDAO().
                                findAllProductStructureIds();

        return  elementCompanyRelationVO;
    }
	
    public AccountVO[] getAccountByAccountId(long accountId) throws Exception {
    
		AccountVO[] accountVO = DAOFactory.getAccountDAO().findByAccountId(accountId);

        return  accountVO;

	}

    public AccountVO[] getAccountsByElementStructureId(long elementStructureId) throws Exception {

        AccountVO[] accountVO = DAOFactory.getAccountDAO().findAccountsByElementStructureId(elementStructureId);

        return accountVO;
    }

    public ElementStructureVO[] getElementStructureByElementStructureId(long elementStructureId) throws Exception {
    
		ElementStructureVO[] elementStructureVO = DAOFactory.getElementStructureDAO().
                    findStructureByElementStructureId(elementStructureId);
        return  elementStructureVO;
	}

    public ElementStructureVO[] getElementStructureByNames(long elementId,
                                                String optionCode,
                                                 int certainPeriod,
                                                  String qualNonQual,
                                                   long fundId,
                                                    long chargeCodeFK)
                                                throws Exception {

        ElementStructureVO[] elementStructureVO = DAOFactory.getElementStructureDAO().
                                findStructureByNames(elementId,
                                                      optionCode,
                                                       certainPeriod,
                                                        qualNonQual,
                                                         fundId,
                                                          chargeCodeFK);
        return elementStructureVO;
    }

    public AccountingDetailVO[] getAccountingDetailByCoAndFromToDates(String companyName,
                                                                      String fromDate,
                                                                      String toDate,
                                                                      String dateType) throws Exception {

        AccountingDetailVO[] accountingDetailVOs = DAOFactory.getAccountingDetailDAO().
                                                    findDetailByCOandFromToDates(companyName, fromDate, toDate, dateType);

        return accountingDetailVOs;
    }
}
