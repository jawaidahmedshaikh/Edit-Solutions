/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 17, 2002
 * Time: 1:00:05 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.business;

//import fission.dm.valueobject.*;
//import fission.global.*;
//import fission.services.*;

//import edit.common.vo.RuleStructureVO;
import edit.common.vo.AccountingDetailVO;
import edit.common.vo.ElementCompanyRelationVO;
import edit.common.vo.ElementStructureVO;
import edit.common.vo.ElementVO;
import edit.services.component.ICRUD;

import java.util.List;

public interface Accounting extends ICRUD {


    public void attachElement(long productStructureId, long[] elementId) throws Exception;

    public void detachElement(long[] elementId, long productStructureId) throws Exception;

    public String createAccountingExtract(String processDate, boolean suppressExtract) throws Exception;

    public void runChartOfAccountsReport() throws Exception;

    public AccountingDetailVO[] composeAccountingDetailByPendingStatus(String accountingPendingStatus,
                                                                        List voInclusionList) throws Exception;

    /**
     * The ElementCompanyRelation records of the fromProductStructure will be used to create ElementCompanyStructure
     * records for the toProductStructure.
     * @param fromProductStructure
     * @param toProductStructure
     * @throws Exception
     */
    public void cloneCompanyStructure(long fromCompanyStructure, long toCompanyStructure) throws Exception;

    public ElementVO[] verifyElementVO(long elementPK) throws Exception;

    ElementCompanyRelationVO[] findRelationByElementPK(long elementPK) throws Exception;

    ElementStructureVO[] findStructuresByElementPK(long elementPK) throws Exception;
}
