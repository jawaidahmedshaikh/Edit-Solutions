/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 17, 2002
 * Time: 1:01:52 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.component;

import accounting.ap.AccountingProcessor;
import accounting.business.Accounting;
import accounting.dm.StorageManager;
import accounting.dm.composer.VOComposer;
import accounting.dm.dao.DAOFactory;
import accounting.rp.ReportingProcessor;
import edit.common.vo.AccountingDetailVO;
import edit.common.vo.ElementCompanyRelationVO;
import edit.common.vo.ElementStructureVO;
import edit.common.vo.ElementVO;
import edit.services.component.AbstractComponent;
import edit.services.db.ConnectionFactory;

import java.util.List;

public class AccountingComponent extends AbstractComponent implements Accounting {

    //Member variables
    private StorageManager        sm;

    public AccountingComponent() {

        init();
    }

	private final void init() {

        sm = new StorageManager();
    }


    public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception {

        long primaryKey = sm.saveVO(valueObject);

        return primaryKey;
    }
    public void deleteVO(String voName,  long primaryKey) throws Exception {

        sm.deleteVO(voName, primaryKey);
    }

    public String[] findVOs(){ return null;}

    public void attachElement(long productStructureId, long[] elementId) throws Exception {

        sm.saveElementCompanyRelation(productStructureId, elementId);
    }

    public void detachElement(long[] elementId, long productStructureId) throws Exception {

        sm.deleteElementCompanyRelation(elementId, productStructureId);
    }

    public String createAccountingExtract(String processDate, boolean suppressExtract) throws Exception {

        return null;// new AccountingProcessor().createAccountingExtract_XML(processDate, suppressExtract);
    }

    public void runChartOfAccountsReport() throws Exception {

        new ReportingProcessor().generateChartOfAccountsReport();
    }

     public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    public AccountingDetailVO[] composeAccountingDetailByPendingStatus(String accountingPendingStatus,
                                                                        List voInclusionList) throws Exception
    {
        return new VOComposer().composeAccountingDetailVOByPendingStatus(accountingPendingStatus, voInclusionList);
    }

    public void cloneCompanyStructure(long fromCompanyStructure, long toCompanyStructure) throws Exception
    {
        sm.cloneCompanyStructure(fromCompanyStructure, toCompanyStructure);
    }

    public ElementVO[] verifyElementVO(long elementPK) throws Exception
    {
        return DAOFactory.getElementDAO().findByElementId(elementPK);
    }

    public ElementCompanyRelationVO[] findRelationByElementPK(long elementPK) throws Exception
    {
        return DAOFactory.getElementCompanyRelationDAO().findRelationsByElementId(elementPK);
    }

    public ElementStructureVO[] findStructuresByElementPK(long elementPK) throws Exception
    {
        return DAOFactory.getElementStructureDAO().findStructureByElementPK(elementPK);
    }

}
