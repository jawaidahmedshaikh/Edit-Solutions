package productbuild.component;

import productbuild.business.ProductBuildUseCase;
import productbuild.*;
import edit.services.db.hibernate.*;
import edit.common.exceptions.*;
import engine.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 24, 2004
 * Time: 12:29:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductBuildUseCaseComponent implements ProductBuildUseCase
{
    public void accessEditing()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void accessCodeTable()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

     public void accessOnlineReport()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    public void accessCommissionContract()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void accessPRASE()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void accessTransactionTable()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void accessRequirementTable()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void accessQuestionnaire()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Security check point.
     */
    public void accessBusinessCalendar()
    {

    }

    /**
     * Security
     */
    public void accessExport()
    {

    }

    public long saveQuestionnaire(Questionnaire questionnaire) throws EDITContractException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            questionnaire.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }

        long questionnairePK = questionnaire.getQuestionnairePK().longValue();

        return questionnairePK;
    }

    public void deleteQuestionnaire(Long questionnairePK) throws EDITContractException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            Questionnaire questionnaire = Questionnaire.findByPK(questionnairePK);

            questionnaire.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void saveFilteredQuestionnaire(Long productStructurePK, Long questionnairePK, int displayOrder) throws EDITContractException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            Questionnaire questionnaire = Questionnaire.findByPK(questionnairePK);

            FilteredQuestionnaire newFilteredQuestionnaire = (FilteredQuestionnaire)SessionHelper.newInstance(FilteredQuestionnaire.class, SessionHelper.EDITSOLUTIONS);
            newFilteredQuestionnaire.setProductStructureFK(productStructurePK);
            newFilteredQuestionnaire.setDisplayOrder(displayOrder);

            questionnaire.addFilteredQuestionnaire(newFilteredQuestionnaire);

            questionnaire.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void deleteFilteredQuestionnaire(Long productStructurePK, Long questionnairePK) throws EDITContractException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            Questionnaire questionnaire = Questionnaire.findQuestionnairesWithProductStructure(productStructurePK, questionnairePK);
            FilteredQuestionnaire filteredQuestionnaire = questionnaire.getFilteredQuestionnaire();
            questionnaire.removeFilteredQuestionnaire(filteredQuestionnaire);

            filteredQuestionnaire.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void cloneQuestionnaires(Long productStructurePK, Long cloneToProductStructurePK) throws EDITContractException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            FilteredQuestionnaire[] filteredQuestionnaires = FilteredQuestionnaire.findByProductStructure(productStructurePK);

            for (int i = 0; i < filteredQuestionnaires.length; i++)
            {
                FilteredQuestionnaire filteredQuestionnaire = (FilteredQuestionnaire)SessionHelper.newInstance(FilteredQuestionnaire.class, SessionHelper.EDITSOLUTIONS);
                filteredQuestionnaire.setProductStructureFK(cloneToProductStructurePK);
                filteredQuestionnaire.setDisplayOrder(filteredQuestionnaires[i].getDisplayOrder());

                filteredQuestionnaires[i].getQuestionnaire().addFilteredQuestionnaire(filteredQuestionnaire);
                filteredQuestionnaire.hSave();
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }
}
