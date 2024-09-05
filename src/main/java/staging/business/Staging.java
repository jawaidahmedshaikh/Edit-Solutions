package staging.business;

import edit.services.component.ILockableElement;
import edit.common.exceptions.EDITStagingException;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 17, 2005
 * Time: 11:10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Staging extends ILockableElement
{
    /**
     * Initiates the staging process which maps data from EDITSolutions to an external set of tables.
     * @param processDate
     * @throws EDITStagingException
     */
    public void stageTables(String processDate) throws EDITStagingException;
}
