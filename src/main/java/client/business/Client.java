/*
 * User: unknown
 * Date: Sep 24, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package client.business;

import edit.common.vo.ClientDetailVO;
import edit.common.vo.PreferenceVO;
import edit.common.*;
import edit.common.exceptions.EDITDeleteException;

import edit.services.component.ICRUD;
import edit.services.component.ILockableElement;
import client.*;
/**
 * The Engine request controller
 */
public interface Client extends ICRUD, ILockableElement  {

    /**
     * Save a client
     * <p>
     * @param id  Client Id
     * @param client data
     */
    public long saveOrUpdateClient(ClientDetailVO clientDetailVO, boolean bypassOFAC) throws Exception;

    /**
     * Save a client
     * <p>
     * @param id  Client Id
     * @param client data
     */
    public long saveOrUpdateClient(ClientDetailVO clientDetailVO, String eligibilityStatus, EDITDate eligibilityDate, boolean bypassOFAC) throws Exception;

    /**
     * Delete a Client
     * <p>
     * @param id Client Id
     */
    public long deleteClient(long clientDetailPK) throws EDITDeleteException, Exception;

//    public long[] saveOrUpdateClients(String[] clientDetailsAsXML) throws Exception;
    public long[] saveOrUpdateClients(ClientDetailVO[] clientDetailVOs,  boolean bypassOFAC) throws Exception;

    public long saveOrUpdatePreference(PreferenceVO preferenceVO,  boolean bypassOFAC) throws Exception;

    public long getNextAvailableKey();

    public void updateClientInfoForSuspense(ClientDetail clientDetail, ClientAddress clientAddress, Preference preference);
}
