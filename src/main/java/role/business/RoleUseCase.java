package role.business;

import edit.services.component.IUseCase;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:35:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RoleUseCase extends IUseCase
{
    public void accessRole();

    public void updateRole();    
}
