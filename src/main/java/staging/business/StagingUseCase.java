package staging.business;

import edit.services.component.IUseCase;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 17, 2005
 * Time: 11:18:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface StagingUseCase extends IUseCase
{
    /**
     * Security check point.
     */
    public void stageTables();
}
