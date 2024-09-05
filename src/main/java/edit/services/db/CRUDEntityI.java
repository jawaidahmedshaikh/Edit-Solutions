package edit.services.db;

import edit.common.vo.user.*;
import edit.common.Change;

/*
 * User: gfrosti
 * Date: Oct 27, 2004
 * Time: 2:20:33 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

public interface CRUDEntityI
{
    void setCRUD(CRUD crud) throws Exception;

    void save(CRUDEntity crudEntity, String poolName, boolean trackChanges);

    void load(CRUDEntity crudEntity, long pk, String poolName);

    void delete(CRUDEntity crudEntity, String poolName);

    CRUDEntity cloneCRUDEntity(CRUDEntity crudEntity);

    boolean isNew(CRUDEntity crudEntity);

    CRUDEntity[] getChildEntities(CRUDEntity parentEntity, Class childEntityClass, Class childVOClass, String poolName);
}
