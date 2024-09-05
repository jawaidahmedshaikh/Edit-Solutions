package edit.services.db;

import edit.common.vo.VOObject;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 10, 2003
 * Time: 3:02:48 PM
 * To change this template use Options | File Templates.
 */
public interface CRUDEntity
{
    /**
     * Persist the targeted entity to permanent storage.
     * @throws Throwable
     */
    public void save() throws Throwable;

    /**
     * Delete the targeted entity from permanent storage.
     * @throws Throwable
     */
    public void delete() throws Throwable;

    /**
     * Return the value object representation of target entity.
     * @return
     */
    public VOObject getVO();

    /**
     * Return the unique identifier for the target entity.
     * @return
     */
    public long getPK();

    /**
     * Associate the value object representation of this entity. Any previous association is lost.
     * @param voObject
     */
    public void setVO(VOObject voObject);

    /**
     * Flags whether this entity has been persisted or not.
     * @return true if the entity has not been persisted, false otherwise.
     */
    public boolean isNew();

    /**
     * Clones the the VO
     * @return
     */
    public CRUDEntity cloneCRUDEntity();
}
