package contract.business;

import edit.services.component.IUseCase;
import contract.Deposits;
import event.Suspense;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:40:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface NewBusinessUseCase extends IUseCase
{
    public void accessNewBusiness();

    public void addNewBusinessContract();

    public void updateNewBusinessContract();

    public void deleteNewBusinessContract();

    public void performNewBusinessQuote();

    public void issueNewBusinessContract();

    public void accessCashBatch();

    public void updateCashBatch();

    public void changeKeyInNewBusiness();

    public void updateNewBusinessNotes();

    public void accessImportNewBusiness();

    public void saveDeposit(Deposits deposits);

    public void adjustSuspense(Suspense suspense);

    public void performProposal();

    public void accessQuestionnaireResponse();

    public void accessClassGenderRatings();

    public void updateClassGenderRatings();

    public void accessRequirements();

    public void updateRequirements();

    public void deleteRequirements();    
    
    public void accessAgent();

    public void updateAgent();    
}
