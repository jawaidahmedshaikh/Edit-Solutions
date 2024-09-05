/*
 * User: gfrosti
 * Date: Nov 21, 2002
 * Time: 11:18:37 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db;

import edit.common.EDITDateTime;
import edit.common.exceptions.EDITLockException;
import edit.common.vo.ElementLockVO;

public class ElementLockManager
{
    private final String POOLNAME;

    public ElementLockManager()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
    }

    /**
     * True if the element has been locked-out by an operator.
     * @param elementPK
     * @return
     * @throws Exception
     */
    public boolean elementIsLocked(long elementPK) throws Exception{

        ElementLockDAO elementLockDAO = new ElementLockDAO();

        ElementLockVO[] elementLockVOs = elementLockDAO.findByElementPK(elementPK, false, null);

        return (elementLockVOs != null);
    }

    /**
     * Locks the specified element by the specified user.
     * @param elementPK
     * @param username
     * @return
     * @throws LockException Thrown if this element has been previously locked by another user.
     */
    public ElementLockVO lockElement(long elementPK, String username) throws EDITLockException {

        CRUD crud = null;
        long elementLockPK = 0;

        // 1. A user can only lock one element at a time. Make sure that there is only one or none by this user.
        ElementLockVO[] elementLockVOs = null;

        ElementLockDAO elementLockDAO = new ElementLockDAO();

        elementLockVOs = elementLockDAO.findByUsername(username, false, null);

        if (elementLockVOs != null && elementLockVOs.length == 1){

            for (int i = 0; i < elementLockVOs.length; i++)
            {
                if (elementLockVOs[i].getElementFK() == elementPK){

                    String msg = "This record is already locked, elementPK =  " + elementPK + ".";

                    throw new EDITLockException(msg);
                }
//                else {
//
//                    return elementLockVOs[0];
//                }
            }
        }

        // 2. The element may have already been locked by another user.
        elementLockVOs = elementLockDAO.findByElementPK(elementPK, false, null);

        if (elementLockVOs != null && elementLockVOs.length == 1){

            if (!elementLockVOs[0].getUsername().equals(username)){

                String msg = "This element was already locked by " + elementLockVOs[0].getUsername() + " on " + elementLockVOs[0].getLockDateTime() + ".";

                throw new EDITLockException(msg);
            }
            else {

                return elementLockVOs[0];
            }
        }

        // 3. Otherwise, establish the lock.
        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            ElementLockVO elementLockVO = new ElementLockVO();
            elementLockVO.setElementFK(elementPK);
            elementLockVO.setUsername(username);
            elementLockVO.setLockDateTime(new EDITDateTime().getFormattedDateTime());

            elementLockPK = crud.createOrUpdateVOInDB(elementLockVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return elementLockDAO.findByElementLockPK(elementLockPK, false, null)[0];
    }

    /**
     * Unlocks a previously locked element.
     * @param elementPK
     * @return
     */
    public int unlockElement(long elementPK){

        CRUD crud = null;

        int numDeleted = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            ElementLockDAO dao = new ElementLockDAO();

            ElementLockVO[] elementLockVO = dao.findByElementPK(elementPK, false, null);

            long elementLockPK = elementLockVO[0].getElementLockPK();

            numDeleted = crud.deleteVOFromDB(ElementLockVO.class, elementLockPK);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return numDeleted;
    }
}
