/*
 * Created by IntelliJ IDEA.
 * Date: Jan 18, 2002
 * Time: 9:11:54 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.business;

import edit.common.vo.*;
import edit.services.component.ILookup;

public interface Lookup extends ILookup {

    public abstract ElementVO[] getAllElements() throws Exception;

    public abstract ElementVO[] getAllElementsByProductStructure(long productStructureId) throws Exception;

  	public abstract ElementVO[] getAllElementNames() throws Exception;
	
	public abstract ElementStructureVO[] getAllElementStructureNames() throws Exception;
	
	public abstract ElementVO[] getElementById(long elementId) throws Exception;

    public abstract ElementCompanyRelationVO[] getAllProductStructureIds() throws Exception;
	
	public abstract AccountVO[] getAccountByAccountId(long accountId) throws Exception;

    public abstract AccountVO[] getAccountsByElementStructureId(long elementStructureId) throws Exception;

	public abstract ElementStructureVO[] getElementStructureByElementStructureId(long elementStructureId) throws Exception;

    public abstract ElementStructureVO[] getElementStructureByNames(long elementId,
                                                         String optionCode,
                                                          int certainPeriod,
                                                           String qualNonQual,
                                                            long fundId,
                                                             long chargeCodeFK)
                                                         throws Exception;

    public abstract AccountingDetailVO[] getAccountingDetailByCoAndFromToDates(String companyName,
                                                                               String fromDate,
                                                                               String toDate,
                                                                               String dateType) throws Exception;
}
