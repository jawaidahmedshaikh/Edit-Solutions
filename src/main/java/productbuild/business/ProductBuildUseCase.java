package productbuild.business;

import edit.services.component.IUseCase;
import edit.common.exceptions.*;
import productbuild.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 24, 2004
 * Time: 12:28:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ProductBuildUseCase extends IUseCase
{
    public void accessEditing();

    public void accessCodeTable();

    public void accessOnlineReport();

    public void accessCommissionContract();

    public void accessPRASE();

    public void accessTransactionTable();

    public void accessRequirementTable();

    public void accessQuestionnaire();
    
    public void accessBusinessCalendar();
    
    public void accessExport();

    public long saveQuestionnaire(Questionnaire questionnaire) throws EDITContractException;

    public void deleteQuestionnaire(Long questionnairePK) throws EDITContractException;

    public void saveFilteredQuestionnaire(Long productStructurePK, Long questionnairePK, int displayOrder) throws EDITContractException;

    public void deleteFilteredQuestionnaire(Long productStructurePK, Long questionnairePK) throws EDITContractException;

    public void  cloneQuestionnaires(Long productStructurePK, Long cloneToProductStructurePK) throws EDITContractException;;
}

