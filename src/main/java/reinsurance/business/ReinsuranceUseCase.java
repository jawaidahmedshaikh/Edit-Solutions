package reinsurance.business;

import edit.services.component.IUseCase;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:21:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ReinsuranceUseCase extends IUseCase
{
    /**
     * Security check point.
     */
    public void accessReinsurerInformation();

    /**
     * Security check point.
     */
    public void accessTreatyRelations();

    /**
     * Security check point.
     */
    public void accessContractTreatyRelations();

    /**
     * Security check point.
     */
    public void updateReinsurance();

    /**
     * Security check point.
     */
    public void deleteReinsurance();

    /**
     * Security check point.
     */        
    public void deleteTreaty();
}
