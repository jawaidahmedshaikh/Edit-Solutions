/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 21, 2002
 * Time: 10:35:07 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.component;

import edit.common.exceptions.EDITLockException;
import edit.common.vo.ElementLockVO;

public interface ILockableElement {

    public ElementLockVO lockElement(long segmentPK, String username) throws EDITLockException;

    public int unlockElement(long lockTablePK);
}
