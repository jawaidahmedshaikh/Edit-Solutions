package staging.component;

import staging.business.Staging;
import edit.common.vo.ElementLockVO;
import edit.common.exceptions.EDITStagingException;
import edit.common.exceptions.EDITLockException;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 17, 2005
 * Time: 11:11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class StagingComponent implements Staging
{
    /**
     * @see Staging#stageTables(String)
     * @param processDate
     * @throws EDITStagingException
     */ 
    public void stageTables(String processDate) throws EDITStagingException
    {
        staging.Staging staging = new staging.Staging();

//        staging.stageTables(processDate);
    }


    public ElementLockVO lockElement(long segmentPK, String username) throws EDITLockException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int unlockElement(long lockTablePK)
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
