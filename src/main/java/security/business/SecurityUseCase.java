package security.business;

import edit.services.component.IUseCase;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:36:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SecurityUseCase extends IUseCase
{
    public void accessSecurity();

    public void viewAllClients();

    public void viewAllAgents();

    public void viewAllSuspense();
}
