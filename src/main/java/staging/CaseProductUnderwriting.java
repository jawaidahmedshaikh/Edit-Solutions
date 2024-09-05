/*
 * User: dlataill
 * Date: Sep 9, 2010
 * Time: 2:59:46 PM
 *
 * (c) 2000-2010 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.services.db.hibernate.*;

public class CaseProductUnderwriting extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long caseProductUnderwritingPK;

    private String grouping;
    private String field;
    private String qualifier;
    private String value;
    private String relationshipToEmployee;
    private String requiredOptional;

    //  Parents
    private Enrollment enrollment;
    private FilteredProduct filteredProduct;
    private Long enrollmentFK;
    private Long filteredProductFK;

    public static final String GROUPING_CASEBASE = "CASEBASE";
    public static final String GROUPING_CASERIDERS = "CASERIDERS";
    public static final String GROUPING_CASEOTHER = "CASEOTHER";

    public static final String GROUPING_CASE_ROOTNAME = "CASE";

    public static final String FIELD_ALLOWRIDERS = "ALLOWRIDERS";
    public static final String FIELD_RATEDGENDER = "RATEDGENDER";
    public static final String FIELD_UNDERWRITING_CLASS = "UWCLASS";
    public static final String FIELD_GIOOPTDATES = "GIOOPTDATES";

    public static final String REQUIREDOPTIONAL_REQUIRED = "Required";
    public static final String REQUIREDOPTIONAL_OPTIONAL = "Optional";

    public static final String VALUE_YES = "Y";
    public static final String VALUE_NO = "N";

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.STAGING;


    /**
     * Constructor
     */
    public CaseProductUnderwriting()
    {
    }

    public Long getCaseProductUnderwritingPK()
    {
        return caseProductUnderwritingPK;
    }

    public void setCaseProductUnderwritingPK(Long caseProductUnderwritingPK)
    {
        this.caseProductUnderwritingPK = caseProductUnderwritingPK;
    }

    public String getGrouping()
    {
        return this.grouping;
    }

    public void setGrouping(String grouping)
    {
        this.grouping = grouping;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getQualifier()
    {
        return qualifier;
    }

    public void setQualifier(String qualifier)
    {
        this.qualifier = qualifier;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getRelationshipToEmployee()
    {
        return relationshipToEmployee;
    }

    public void setRelationshipToEmployee(String relationshipToEmployee)
    {
        this.relationshipToEmployee = relationshipToEmployee;
    }

    public String getRequiredOptional()
    {
        return this.requiredOptional;
    }

    public void setRequiredOptional(String requiredOptional)
    {
        this.requiredOptional = requiredOptional;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    public Long getEnrollmentFK()
    {
        return enrollmentFK;
    }

    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    public FilteredProduct getFilteredProduct()
    {
        return filteredProduct;
    }

    public void setFilteredProduct(FilteredProduct filteredProduct)
    {
        this.filteredProduct = filteredProduct;
    }

    public Long getFilteredProductFK()
    {
        return filteredProductFK;
    }

    public void setFilteredProductFK(Long filteredProductFK)
    {
        this.filteredProductFK = filteredProductFK;
    }

    public String getDatabase()
    {
        return CaseProductUnderwriting.DATABASE;
    }
}
