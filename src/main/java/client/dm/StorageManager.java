/*
 * StorageManager.java      Version 1.1  09/24/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package client.dm;

import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITLockException;
import edit.common.vo.*;

import edit.services.db.*;

import java.io.*;

import java.util.*;


//import edit.common.vo.user.ChangeVO;


/**
 * StorageManager - Copyright Systems Engineering Group, LLC 2000
 * The StorageManager will handle the Object persistence.  There
 * are methods for adding, updating, deleting, and retrieving data
 * to the database.  The StorageManager will also establish and
 * maintain all database connections.
 *
 */
public class StorageManager implements Serializable {

    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    /**
     * StorageManager constructor.
     */
    public StorageManager() {

    }

    public long saveOrUpdateClient(ClientDetailVO clientDetailVO) throws Exception {

        CRUD crud = null;

        long pkValue = 0;

        List voExclusionList = null;

        voExclusionList = new ArrayList();

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            voExclusionList.add(ClientRoleVO.class);

            pkValue = crud.createOrUpdateVOInDBRecursively(clientDetailVO, voExclusionList);

        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return pkValue;
    }

    public long[] saveOrUpdateClients(ClientDetailVO[] clientDetailVOs) throws Exception {

        CRUD crud = null;

        long[] primaryKeys = new long[0];

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            primaryKeys = new long[clientDetailVOs.length];

            String operator = clientDetailVOs[0].getOperator();

            for (int i = 0; i < clientDetailVOs.length; i++){

                primaryKeys[i] = crud.createOrUpdateVOInDBRecursively(clientDetailVOs[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return primaryKeys;
    }
	/**
	 * Removes client from ClientDetail Table
	 */
	public long deleteClient(long clientDetailPK) throws EDITDeleteException, Exception {

        long pkValue = 0;

        CRUD crud = null;

        // Make sure there isn't a lock on the client.

        ElementLockManager elementLockManager = new ElementLockManager();

        boolean elementIsLocked = elementLockManager.elementIsLocked(clientDetailPK);

        if (elementIsLocked){

            throw new EDITDeleteException("Element is locked and can not be deleted.");
        }

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            pkValue = crud.deleteVOFromDBRecursively(ClientDetailVO.class, clientDetailPK);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return pkValue;
    }

    public long saveOrUpdatePreference(PreferenceVO preferenceVO) throws Exception {

        CRUD crud = null;

        long pkValue = 0;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            pkValue = crud.createOrUpdateVOInDBRecursively(preferenceVO);
        }
        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally {

            if (crud != null) crud.close();
        }

        return pkValue;
    }

    public ElementLockVO lockElement(long elementPK, String username) throws EDITLockException {

        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.lockElement(elementPK, username);
    }

    public int unlockElement(long lockTablePK) {

        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.unlockElement(lockTablePK);
    }
}



