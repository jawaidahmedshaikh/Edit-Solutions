package client.business;

import edit.services.component.IUseCase;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:20:26 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientUseCase extends IUseCase
{
    public void accessClient();

    public void addNewClient();

    public void updateClient();

    public void deleteClient();

    public void updateDOBGenderChange();

    public void accessTaxes();

    public void updateTaxes();

    public void accessPreference();

    public void updatePreference();
}
