/*
 * User: unknown
 * Date: Sep 24, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package client.component;

import client.business.Client;
import client.dm.StorageManager;
import client.*;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ElementLockVO;
import edit.common.vo.*;
import edit.common.*;
import edit.common.exceptions.EDITDeleteException;

import edit.common.exceptions.EDITLockException;

import edit.services.component.AbstractComponent;
import edit.services.db.ConnectionFactory;
import edit.services.db.CRUD;
import edit.services.db.hibernate.*;
import edit.services.EditServiceLocator;

import java.util.*;


/**
 * The Client Engine request controller
 */
public class ClientComponent extends AbstractComponent implements Client {


    // Member variables
    private StorageManager  sm;



    /**
     * ClientController constructor.
     */
    public ClientComponent() {

        init();
    }

    /**
     *
     * @param clientDetailVO
     * @param bypassOFAC
     * @return
     * @throws Exception
     */
    public long saveOrUpdateClient(ClientDetailVO clientDetailVO, boolean bypassOFAC) throws Exception
    {
        ClientDetail clientDetail = saveClient(clientDetailVO, bypassOFAC);

        return clientDetail.getPK();
    }

    /**
     * Save Client when new and eligibility fileds to Role of Owner
     * @param clientDetailVO
     * @param eligibilityStatus
     * @param eligibilityDate
     * @param bypassOFAC
     * @return
     * @throws Exception
     */
    public long saveOrUpdateClient(ClientDetailVO clientDetailVO, String eligibilityStatus, EDITDate eligibilityDate, boolean bypassOFAC) throws Exception
    {
       ClientDetail clientDetail = saveClient(clientDetailVO, bypassOFAC);

        updateRoleForeignKeys(clientDetail, eligibilityStatus, eligibilityDate);

        return clientDetail.getPK();
    }

    private ClientDetail saveClient(ClientDetailVO clientDetailVO, boolean bypassOFAC) throws Exception
    {
        ClientDetail clientDetail = new  ClientDetail(clientDetailVO);

        if (clientDetailVO.getClientDetailPK() == 0)
        {
            clientDetail.setSaveChangeHistory(false);
        }
        else
        {
            clientDetail.setSaveChangeHistory(true);
        }
        //sramamurthy 07/28/2004 added code to check the OFAC-SOAP service
        if(!bypassOFAC){   //sramamurthy added on 08/27/2004
            EditOFACCheck editOFACCheck = EditServiceLocator.getSingleton().getOFACValidateService();

            clientDetail.save();

            editOFACCheck.validateClient(clientDetail);
        }
        else
        {
           clientDetail.save();
        }
        return clientDetail;
    }

    /**
     *
     * @param clientDetailPK
     * @return
     * @throws DeletionException
     * @throws Exception
     */
	public long deleteClient(long clientDetailPK) throws EDITDeleteException, Exception {
    
        return sm.deleteClient(clientDetailPK);
	}
    /**
     *
     * @param clientDetailVOs
     * @param bypassOFAC
     * @return
     * @throws Exception
     */
    public long[] saveOrUpdateClients(ClientDetailVO[] clientDetailVOs, boolean bypassOFAC) throws Exception{
//        public long[] saveOrUpdateClients(String[] clientDetailsAsXML) throws Exception{

//        ClientDetailVO[] clientDetailVOs = (ClientDetailVO[]) Util.unmarshalVOs(ClientDetailVO.class, clientDetailsAsXML);
        long[] clientDetailPKs = sm.saveOrUpdateClients(clientDetailVOs);
        //sramamurthy 07/28/2004 added code to check the OFAC-SOAP service
        ClientDetail clientDetailCE = null;
        for( int i = 0; i < clientDetailPKs.length; i++ ){
            clientDetailCE = new ClientDetail(clientDetailPKs[i]);
            EditServiceLocator.getSingleton().getOFACValidateService().validateClient(clientDetailCE);
        }
        return clientDetailPKs;
    }

    /**
     *
     * @param preferenceVO
     * @param bypassOFAC
     * @return
     * @throws Exception
     */
    public long saveOrUpdatePreference(PreferenceVO preferenceVO, boolean bypassOFAC) throws Exception {

		long preferencePK = sm.saveOrUpdatePreference(preferenceVO);
        long clientDetailFK = preferenceVO.getClientDetailFK();
        //sramamurthy 07/28/2004 added code to check the OFAC-SOAP service
        if(!bypassOFAC){ //sramamurthy added on 08/27/2004
            ClientDetail clientDetailCE = null;
            clientDetailCE = new ClientDetail(clientDetailFK);
            EditServiceLocator.getSingleton().getOFACValidateService().validateClient(clientDetailCE);
        }
		return preferencePK;
    }

     public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception{

       // sm.saveVO(valueObject);
         return 0;
    }

    public String[] findVOs(){return null;}


    private final void init(){

        sm = new StorageManager();
    }

    public ElementLockVO lockElement(long segmentPK, String username) throws EDITLockException {

        return sm.lockElement(segmentPK, username);
    }

    public int unlockElement(long lockTablePK) {

        return sm.unlockElement(lockTablePK);
    }

     public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    public long getNextAvailableKey()
    {
        return CRUD.getNextAvailableKey();
    }


    private void updateRoleForeignKeys(ClientDetail clientDetail, String eligibilityStatus, EDITDate eligibilityStartDate) throws Exception
    {
        role.business.Lookup roleLookup = new role.component.LookupComponent();
        role.business.Role roleComp = new role.component.RoleComponent();

        ClientDetailVO clientDetailVO = (ClientDetailVO)clientDetail.getVO();
        TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();
        PreferenceVO[] preferenceVO = clientDetailVO.getPreferenceVO();
        TaxProfileVO[] taxProfileVO = null;

        if ((taxInformationVO != null) && (taxInformationVO.length > 0))
        {
            taxProfileVO = taxInformationVO[0].getTaxProfileVO();
        }

        long preferenceFK = 0;
        long taxProfileFK = 0;

        if (preferenceVO != null && preferenceVO.length > 0)
        {
            preferenceFK = preferenceVO[0].getPreferencePK();
        }

        if (taxProfileVO != null && taxProfileVO.length > 0)
        {
            taxProfileFK = taxProfileVO[0].getTaxProfilePK();
        }

        long clientDetailPK = clientDetail.getPK();
//        String eligibilityDate = null;
        ClientRoleVO[] clientRoleVOs = roleLookup.getRolesByClientDetailFK(clientDetailPK);

        if (clientRoleVOs != null)
        {
            boolean roleHasChanged = false;
            List clientRoleArray = new ArrayList();
//            if (eligibilityStartDate != null)
//            {
//                eligibilityDate = eligibilityStartDate.getDateAsYYYYMMDD();
//            }

            for (int r = 0; r < clientRoleVOs.length; r++)
            {
                roleHasChanged = false;

                if (clientRoleVOs[r].getPreferenceFK() == 0 && preferenceFK != 0)
                {
                    clientRoleVOs[r].setPreferenceFK(preferenceFK);
                    roleHasChanged = true;
                }

                if (clientRoleVOs[r].getTaxProfileFK() == 0 && taxProfileFK != 0)
                {
                    clientRoleVOs[r].setTaxProfileFK(taxProfileFK);
                    roleHasChanged = true;
                }

                if (eligibilityStatus != null)
                {
                    clientRoleVOs[r].setNewIssuesEligibilityStatusCT(eligibilityStatus);
                    roleHasChanged = true;
                }

                if (eligibilityStartDate != null)
                {
                    clientRoleVOs[r].setNewIssuesEligibilityStartDate(eligibilityStartDate.getFormattedDate());
                    roleHasChanged = true;
                }

                if (roleHasChanged)
                {
                    clientRoleArray.add(clientRoleVOs[r]);
                }
            }

            roleComp.saveClientRole((ClientRoleVO[]) clientRoleArray.toArray(new ClientRoleVO[clientRoleArray.size()]), clientDetailVO.getOperator());
        }

        else
        {
            if (eligibilityStatus != null && eligibilityStartDate != null)
            {
                ClientRoleVO clientRoleVO = new ClientRoleVO();
                clientRoleVO.setClientRolePK(0);
                clientRoleVO.setClientDetailFK(clientDetailPK);
                clientRoleVO.setPreferenceFK(preferenceFK);
                clientRoleVO.setTaxProfileFK(taxProfileFK);
                clientRoleVO.setRoleTypeCT("OWN");
                clientRoleVO.setNewIssuesEligibilityStatusCT(eligibilityStatus);
                clientRoleVO.setNewIssuesEligibilityStartDate(eligibilityStartDate.getFormattedDate());
                clientRoleVO.setOverrideStatus("P");
                roleComp.saveOrUpdateClientRole(clientRoleVO);
            }
        }
    }

    public void updateClientInfoForSuspense(ClientDetail clientDetail, ClientAddress clientAddress, Preference preference)
    {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
        if (clientDetail != null)
        {
            SessionHelper.saveOrUpdate(clientDetail, SessionHelper.EDITSOLUTIONS);
        }

        if (clientAddress != null)
        {
            SessionHelper.saveOrUpdate(clientAddress, SessionHelper.EDITSOLUTIONS);
        }

        if (preference != null)
        {
            SessionHelper.saveOrUpdate(preference, SessionHelper.EDITSOLUTIONS);
        }

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }
}
	






