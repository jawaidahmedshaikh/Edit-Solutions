package contract.business;

import edit.services.component.IUseCase;
import event.Suspense;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:21:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InforceUseCase extends IUseCase
{
    public void accessInforceContract();

    public void updateInforceContract();

    public void deleteInforceContract();

    public void performInforceQuote();

    public void updateInforceSuspense();

    public void updateInforceNotes();

    public void performProposal();

    public void adjustDeposit();

    public void accessQuestionnaireResponse();

    public void accessAgent();

    public void updateAgent();

    public void accessRequirements();

    public void updateRequirements();

    public void deleteRequirements();

    public void updateNFODBOChange();

    public void accessClassGenderRatings();

    public void updateClassGenderRatings();

    public void accessInforceQuoteNotTakenOverrides();

    public void accessBillingChangeDialog();
}
