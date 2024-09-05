package contract.business;

import edit.services.component.*;

/**
 * User: cgleason
 * Date: Jan 30, 2006
 * Time: 9:03:28 AM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public interface CaseUseCase  extends IUseCase
{
    public void accessCase();

    public void accessPRDSystem();

    public void addCaseDetail();

    public void updateCaseDetail();

    public void deleteCaseDetail();

    public void performCaseQuote();

    public void addGroupDetail();

    public void updateGroup();

    public void deleteGroup();

    public void accessGroupBilling();

    public void updateGroupBilling();

    public void accessPRD();

    public void updatePRD();

    public void updateRequirement();

    public void accessCaseSetup();
}
