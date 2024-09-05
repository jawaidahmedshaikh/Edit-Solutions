/*
 * User: sdorman
 * Date: Jun 19, 2007
 * Time: 12:45:46 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package contract;

import edit.common.*;
import edit.services.db.hibernate.*;

import productbuild.*;
import group.PayrollDeductionSchedule;

import java.util.List;


public class QuestionnaireResponse extends HibernateEntity
{
    private Long questionnaireResponsePK;

    private String responseCT;
    private EDITDate effectiveDate;
    private EDITDate followupDate;

    //  Parents
    private FilteredQuestionnaire filteredQuestionnaire;
    private ContractClient contractClient;
    private Long filteredQuestionnaireFK;
    private Long contractClientFK;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Constructor
     */
    public QuestionnaireResponse()
    {
    }

    public QuestionnaireResponse(FilteredQuestionnaire filteredQuestionnaire, String responseCT)
    {
        this.filteredQuestionnaire = filteredQuestionnaire;
        this.responseCT = responseCT;

        this.effectiveDate = new EDITDate();
        calculateFollowupDate();
    }


    public Long getQuestionnaireResponsePK()
    {
        return questionnaireResponsePK;
    }

    public void setQuestionnaireResponsePK(Long questionnaireResponsePK)
    {
        this.questionnaireResponsePK = questionnaireResponsePK;
    }

    public String getResponseCT()
    {
        return responseCT;
    }

    public void setResponseCT(String responseCT)
    {
        this.responseCT = responseCT;
    }

    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public EDITDate getFollowupDate()
    {
        return followupDate;
    }

    public void setFollowupDate(EDITDate followupDate)
    {
        this.followupDate = followupDate;
    }

    public ContractClient getContractClient()
    {
        return this.contractClient;
    }

    public void setContractClient(ContractClient contractClient)
    {
        this.contractClient = contractClient;
    }

    public Long getContractClientFK()
    {
        return this.contractClientFK;
    }

    public void setContractClientFK(Long contractClientFK)
    {
        this.contractClientFK = contractClientFK;
    }

    public FilteredQuestionnaire getFilteredQuestionnaire()
    {
        return this.filteredQuestionnaire;
    }

    public void setFilteredQuestionnaire(FilteredQuestionnaire filteredQuestionnaire)
    {
        this.filteredQuestionnaire = filteredQuestionnaire;
    }

    public Long getFilteredQuestionnaireFK()
    {
        return this.filteredQuestionnaireFK;
    }

    public void setFilteredQuestionnaireFK(Long filteredQuestionnaireFK)
    {
        this.filteredQuestionnaireFK = filteredQuestionnaireFK;
    }

    public String getDatabase()
    {
        return QuestionnaireResponse.DATABASE;
    }

    /**
     * Copies the object and its parent references
     * @return
     */
    public QuestionnaireResponse deepCopy()
    {
        QuestionnaireResponse newQuestionnaireResponse = (QuestionnaireResponse) SessionHelper.shallowCopy(this, QuestionnaireResponse.DATABASE);

        newQuestionnaireResponse.setFilteredQuestionnaire(this.getFilteredQuestionnaire());
        newQuestionnaireResponse.setContractClient(this.getContractClient());

        return newQuestionnaireResponse;
    }

    private void calculateFollowupDate()
    {
        int followupDays = this.getFilteredQuestionnaire().getQuestionnaire().getFollowupDays();

        this.followupDate = effectiveDate.addDays(followupDays);
    }

    public static QuestionnaireResponse[] findBy_ContractClientFK(Long contractClientFK)
    {
        String hql =  "select questionnaireResponse from QuestionnaireResponse questionnaireResponse" +
                      " where questionnaireResponse.ContractClientFK = :contractClientFK";

        EDITMap params = new EDITMap();

        params.put("contractClientFK", contractClientFK);

        List<QuestionnaireResponse> results = SessionHelper.executeHQL(hql, params, QuestionnaireResponse.DATABASE);

        return (QuestionnaireResponse[]) results.toArray(new QuestionnaireResponse[results.size()]);
    }
}
