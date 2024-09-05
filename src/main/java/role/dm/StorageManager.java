/*
 * StorageManager.java      Version 1.1  09/24/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package role.dm;

import edit.common.exceptions.EDITLockException;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ElementLockVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.ElementLockManager;

import java.io.Serializable;

/**
 * StorageManager - Copyright Systems Engineering Group, LLC 2000
 * The StorageManager will handle the Object persistence.  There
 * are methods for adding, updating, deleting, and retrieving data
 * to the database.  The StorageManager will also establish and
 * maintain all database connections.
 *
 */
public class StorageManager implements Serializable {

    /**
     * StorageManager constructor.
     */
    public StorageManager() {

    }

    public long saveOrUpdateClientRole(ClientRoleVO clientRoleVO) throws Exception {

        CRUD crud = null;
        long pkValue = 0;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            pkValue = crud.createOrUpdateVOInDB(clientRoleVO);

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



