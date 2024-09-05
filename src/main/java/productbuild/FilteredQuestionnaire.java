/*
 * 
 * User: cgleason
 * Date: Jun 15, 2007
 * Time: 12:55:18 PM
 * 
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package productbuild;

import edit.services.db.hibernate.*;

import java.util.*;

import contract.*;

public class FilteredQuestionnaire extends HibernateEntity
{
    private Long filteredQuestionnairePK;
    private Long questionnaireFK;
    private Long productStructureFK;
    private int displayOrder;

    private Set<QuestionnaireResponse> questionnaireResponses;

    private Questionnaire questionnaire;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public FilteredQuestionnaire()
    {
        if (questionnaireResponses == null)
        {
            questionnaireResponses = new HashSet<QuestionnaireResponse>();
        }
    }

    /**
     * Getter
     * @return
     */
    public Long getFilteredQuestionnairePK()
    {
        return filteredQuestionnairePK;
    }

    /**
     * Getter
     * @return
     */
    public Long getQuestionnaireFK()
    {
        return questionnaireFK;
    }

    /**
     * Getter
     * @return
     */
    public Long getProductStructureFK()
    {
        return productStructureFK;
    }

    /**
     * Getter
     * @return
     */
    public int getDisplayOrder()
    {
        return displayOrder;
    }

    /**
     * Getter
     * @return
     */
    public Questionnaire getQuestionnaire()
    {
        return questionnaire;
    }
    /**
     * Getter
     * @return
     */
    public Set getQuestionnaireResponses()
    {
        return questionnaireResponses;
    }

    /**
     * Setter
     * @param filteredQuestionnairePK
     */
    public void setFilteredQuestionnairePK(Long filteredQuestionnairePK)
    {
        this.filteredQuestionnairePK = filteredQuestionnairePK;
    }

    /**
     * Setter
     * @param questionnaireFK
     */
    public void setQuestionnaireFK(Long questionnaireFK)
    {
        this.questionnaireFK = questionnaireFK;
    }

    /**
     * Setter
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
    }

    /**
     * Setter
     * @param displayOrder
     */
    public void setDisplayOrder(int displayOrder)
    {
        this.displayOrder = displayOrder;
    }

    /**
     * Setter
     * @param Questionnaire
     */
    public void setQuestionnaire(Questionnaire questionnaire)
    {
        this.questionnaire = questionnaire;
    }

    /**
     * Setter
     * @param questionnaireResponses
     */
    public void setQuestionnaireResponses(Set<QuestionnaireResponse> questionnaireResponses)
    {
        this.questionnaireResponses = questionnaireResponses;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, FilteredQuestionnaire.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, FilteredQuestionnaire.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FilteredQuestionnaire.DATABASE;
    }

    /**
      * Finder.
      * @param FilteredQuestionnairePK
      * @return
      */
     public static FilteredQuestionnaire findByPK(Long filteredQuestionnairePK)
     {
         return (FilteredQuestionnaire) SessionHelper.get(FilteredQuestionnaire.class, filteredQuestionnairePK, FilteredQuestionnaire.DATABASE);
     }

    public static FilteredQuestionnaire[] findByProductStructure(Long productStructurePK)
    {
        FilteredQuestionnaire[] filteredQuestionnaries = null;

        String hql = "select filteredQues from FilteredQuestionnaire filteredQues " +
                     "join fetch filteredQues.Questionnaire questionnaire " +
                     "where filteredQues.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, FilteredQuestionnaire.DATABASE);

        if (!results.isEmpty())
        {
            filteredQuestionnaries = (FilteredQuestionnaire[]) results.toArray(new FilteredQuestionnaire[results.size()]);
        }

        return filteredQuestionnaries;
    }

    public static FilteredQuestionnaire[] findByProductStructure_AreaCT_SortedByDisplayOrder(Long productStructurePK, String areaCT)
    {
        FilteredQuestionnaire[] filteredQuestionnaries = null;

        String hql = "select filteredQues from FilteredQuestionnaire filteredQues " +
                     "join fetch filteredQues.Questionnaire questionnaire " +
                     "where filteredQues.ProductStructureFK = :productStructurePK " +
                     "and (questionnaire.AreaCT = :areaCT OR questionnaire.AreaCT = :asterisk) " +
                     "order by filteredQues.DisplayOrder";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("areaCT", areaCT);
        params.put("asterisk", "*");

        List results = SessionHelper.executeHQL(hql, params, FilteredQuestionnaire.DATABASE);

        if (!results.isEmpty())
        {
            filteredQuestionnaries = (FilteredQuestionnaire[]) results.toArray(new FilteredQuestionnaire[results.size()]);
        }

        return filteredQuestionnaries;
    }

    public static FilteredQuestionnaire findByQuestionnaire_ProductStructure(Long questionnairePK, Long productStructurePK)
    {
        FilteredQuestionnaire filteredQuestionnaire = null;

        String hql = "select filteredQues from FilteredQuestionnaire filteredQues " +
                     "where filteredQues.ProductStructureFK = :productStructurePK " +
                     "and filteredQues.QuestionnaireFK = :questionnairePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("questionnairePK", questionnairePK);

        List results = SessionHelper.executeHQL(hql, params, FilteredQuestionnaire.DATABASE);

        if (!results.isEmpty())
        {
            filteredQuestionnaire = (FilteredQuestionnaire) results.get(0);
        }

        return filteredQuestionnaire;
    }

    public static FilteredQuestionnaire findByQuestionnaireDescription_ProductStructure(String questionnaireDescription, Long productStructurePK)
    {
        FilteredQuestionnaire filteredQuestionnaire = null;

        String hql = "select filteredQues from FilteredQuestionnaire filteredQues " +
                     "join filteredQues.Questionnaire questionnaire " +
                     "where filteredQues.ProductStructureFK = :productStructurePK " +
                     "and questionnaire.QuestionnaireDescription = :questionnaireDescription";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("questionnaireDescription", questionnaireDescription);

        List results = SessionHelper.executeHQL(hql, params, FilteredQuestionnaire.DATABASE);

        if (!results.isEmpty())
        {
            filteredQuestionnaire = (FilteredQuestionnaire) results.get(0);
        }

        return filteredQuestionnaire;
    }

    /**
     * Finds the FilteredQuestionnaire for the given QuestionnaireId and productStructurePk
     *
     * @param questionnaireId
     * @param productStructurePK
     * @return
     */
    public static FilteredQuestionnaire findByQuestionnaireId_ProductStructure(String questionnaireId, Long productStructurePK)
    {
        FilteredQuestionnaire filteredQuestionnaire = null;

        String hql = "select filteredQues from FilteredQuestionnaire filteredQues " +
                     "join filteredQues.Questionnaire questionnaire " +
                     "where filteredQues.ProductStructureFK = :productStructurePK " +
                     "and questionnaire.QuestionnaireId = :questionnaireId";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("questionnaireId", questionnaireId);

        List results = SessionHelper.executeHQL(hql, params, FilteredQuestionnaire.DATABASE);

        if (!results.isEmpty())
        {
            filteredQuestionnaire = (FilteredQuestionnaire) results.get(0);
        }

        return filteredQuestionnaire;
    }
}

