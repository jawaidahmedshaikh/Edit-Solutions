/*
 * User: cgleason
 * Date: Jun 15, 2007
 * Time: 12:54:58 PM
 * 
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package productbuild;

import edit.services.db.hibernate.*;

import java.util.*;

public class Questionnaire extends HibernateEntity
{

    private Long questionnairePK;
    private String questionnaireId;
    private String questionnaireDescription;
    private int followupDays;
    private String manualInd;
    private String areaCT;

    private Set<FilteredQuestionnaire> filteredQuestionnaires;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Questionnaire()
    {
        if (filteredQuestionnaires == null)
        {
            filteredQuestionnaires = new HashSet<FilteredQuestionnaire>();
        }
    }

    /**
     * Getter
     * @return
     */
    public Long getQuestionnairePK()
    {
        return questionnairePK;
    }

    /**
     * Getter
     * @return
     */
    public String getQuestionnaireId()
    {
        return questionnaireId;
    }

    /**
     * Getter
     * @return
     */
    public String getQuestionnaireDescription()
    {
        return questionnaireDescription;
    }

    /**
     * Getter
     * @return
     */
    public int getFollowupDays()
    {
        return followupDays;
    }

    /**
     * Getter
     * @return
     */
    public String getManualInd()
    {
        return manualInd;
    }

    /**
     * Getter
     * @return
     */
    public String getAreaCT()
    {
        return this.areaCT;
    }

    /**
     * Getter
     * @return  set of filteredQuestionnaires
     */
    public Set getFilteredQuestionnaires()
    {
        return filteredQuestionnaires;
    }

    /**
     * Getter.
     * @return
     */
    public FilteredQuestionnaire getFilteredQuestionnaire()
    {
        FilteredQuestionnaire filteredQuestionnaire = getFilteredQuestionnaires().isEmpty()?null:(FilteredQuestionnaire) getFilteredQuestionnaires().iterator().next();

        return filteredQuestionnaire;
    }

    /**
     * Setter
     * @param questionnairePK
     */
    public void setQuestionnairePK(Long questionnairePK)
    {
        this.questionnairePK = questionnairePK;
    }

    /**
     * Setter
     * @param questionnaireId
     */
    public void setQuestionnaireId(String questionnaireId)
    {
        this.questionnaireId = questionnaireId;
    }

    /**
     * Setter
     * @param questionnaireDescription
     */
    public void setQuestionnaireDescription(String questionnaireDescription)
    {
        this.questionnaireDescription = questionnaireDescription;
    }

    /**
     * Setter
     * @param followupDays
     */
    public void setFollowupDays(int followupDays)
    {
        this.followupDays = followupDays;
    }

    /**
     * Setter
     * @param manualInd
     */
    public void setManualInd(String manualInd)
    {
        this.manualInd = manualInd;
    }

    /**
     * Setter
     * @param areaCT
     */
    public void setAreaCT(String areaCT)
    {
        this.areaCT = areaCT;
    }

    /**
     * Setter
     * @param filteredQuestionnaires
     */
    public void setFilteredQuestionnaires(Set<FilteredQuestionnaire> filteredQuestionnaires)
    {
        this.filteredQuestionnaires = filteredQuestionnaires;
    }

    /**
     * Adds a filteredQuestionnaire to the set of FilteredQuestionnaires associated with this Questionnaire.
     * @param contractClient
     */
    public void addFilteredQuestionnaire(FilteredQuestionnaire filteredQuestionnaire)
    {
        getFilteredQuestionnaires().add(filteredQuestionnaire);

        filteredQuestionnaire.setQuestionnaire(this);
    }

    public void removeFilteredQuestionnaire(FilteredQuestionnaire filteredQuestionnaire)
    {
        this.getFilteredQuestionnaires().remove(filteredQuestionnaire);

        filteredQuestionnaire.setQuestionnaire(null);
    }

    /**
      * Finder.
      * @param QuestionnairePK
      * @return
      */
     public static Questionnaire findByPK(Long questionnairePK)
     {
         return (Questionnaire) SessionHelper.get(Questionnaire.class, questionnairePK, Questionnaire.DATABASE);
     }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Questionnaire.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Questionnaire.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Questionnaire.DATABASE;
    }

    /**
     * Find questionnaire for a FilteredQuestionnairePK
     * @param filteredQuestionnaireFK
     * @return
     */
    public static Questionnaire findByFilteredQuestionnairePK(Long filteredQuestionnairePK)
    {
        String hql = "select questionnaire from Questionnaire questionnaire " +
                     "join questionnaire.FilteredQuestionnaires filteredQues " +
                     "where filteredQues.FilteredQuestionnairePK = :filteredQuestionnairePK";


        Map params = new HashMap();

        params.put("filteredQuestionnairePK", filteredQuestionnairePK);

        List results = SessionHelper.executeHQL(hql, params, Questionnaire.DATABASE);

        Questionnaire questionnaire = null;

        if (!results.isEmpty())
        {
            questionnaire = (Questionnaire)results.get(0);
        }

        return questionnaire;
    }

    public static Questionnaire[] findAllQuestionnaires()
    {
        Questionnaire[] questionnaires = null;

        String hql = "from Questionnaire";

        List results = SessionHelper.executeHQL(hql, null, Questionnaire.DATABASE);

        if (!results.isEmpty())
        {
            questionnaires = (Questionnaire[]) results.toArray(new Questionnaire[results.size()]);
        }

        return questionnaires;
    }

    public static Questionnaire[] findAllAssociated(Long productStructurePK)
    {
        Questionnaire[] questionnaires = null;

        String hql = "select ques from Questionnaire ques " +
                     "join fetch ques.FilteredQuestionnaires filteredQues " +
                     "where filteredQues.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, Questionnaire.DATABASE);

        if (!results.isEmpty())
        {
            questionnaires = (Questionnaire[]) results.toArray(new Questionnaire[results.size()]);
        }

        return questionnaires;
    }

    public static Questionnaire findQuestionnairesWithProductStructure(Long productStructurePK, Long questionnairePK)
    {
        Questionnaire questionnaire = null;

        String hql = "select ques from Questionnaire ques " +
                     "join fetch ques.FilteredQuestionnaires filteredQues " +
                     "where filteredQues.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("questionnaireFK", questionnairePK);

        List results = SessionHelper.executeHQL(hql, params, Questionnaire.DATABASE);

        if (!results.isEmpty())
        {
            questionnaire = (Questionnaire) results.get(0);
        }

        return questionnaire;
    }

    public static Questionnaire[] findAllQuestionnairesWithProductStructure(Long productStructurePK)
    {
        Questionnaire[] questionnaires = null;

        String hql = "select ques from Questionnaire ques " +
                     "left join fetch ques.FilteredQuestionnaires filteredQues " +
                     "where filteredQues.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, Questionnaire.DATABASE);

        if (!results.isEmpty())
        {
            questionnaires = (Questionnaire[]) results.toArray(new Questionnaire[results.size()]);
        }

        return questionnaires;
    }
}
