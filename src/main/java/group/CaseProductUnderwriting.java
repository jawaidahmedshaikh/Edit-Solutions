/*
 * User: sdorman
 * Date: Jun 19, 2007
 * Time: 12:45:46 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package group;

import edit.common.*;
import edit.common.vo.AreaValueVO;
import edit.common.exceptions.EDITCaseException;
import edit.services.db.hibernate.*;

import java.util.*;

import contract.FilteredProduct;
import engine.AreaKey;
import engine.AreaValue;
import staging.IStaging;
import staging.StagingContext;

public class CaseProductUnderwriting extends HibernateEntity implements IStaging
{
    /**
     * Primary key
     */
    private Long caseProductUnderwritingPK;

    private String grouping;
    private String field;
    private String qualifier;
    private String value;
    private String relationshipToEmployeeCT;
    private String requiredOptionalCT;

    //  Parents
    private Enrollment enrollment;
    private DepartmentLocation departmentLocation;
    private FilteredProduct filteredProduct;
    private Long enrollmentFK;
    private Long departmentLocationFK;
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
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


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

    public String getRelationshipToEmployeeCT()
    {
        return relationshipToEmployeeCT;
    }

    public void setRelationshipToEmployeeCT(String relationshipToEmployeeCT)
    {
        this.relationshipToEmployeeCT = relationshipToEmployeeCT;
    }

    public String getRequiredOptionalCT()
    {
        return this.requiredOptionalCT;
    }

    public void setRequiredOptionalCT(String requiredOptionalCT)
    {
        this.requiredOptionalCT = requiredOptionalCT;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    public DepartmentLocation getDepartmentLocation()
    {
        return departmentLocation;
    }

    public void setDepartmentLocation(DepartmentLocation departmentLocation)
    {
        this.departmentLocation = departmentLocation;
    }

    public Long getEnrollmentFK()
    {
        return enrollmentFK;
    }

    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    public Long getDepartmentLocationFK()
    {
        return departmentLocationFK;
    }

    public void setDepartmentLocationFK(Long departmentLocationFK)
    {
        this.departmentLocationFK = departmentLocationFK;
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

    public static boolean underwritingValueSelected(Long filteredProductFK, Long enrollmentFK, Long areaValueFK)
    {
        boolean underwritingValueSelected = false;
    
        CaseProductUnderwriting caseProductUnderwriting = CaseProductUnderwriting.findBy_FilteredProduct_Enrollment_AreaValue(filteredProductFK, enrollmentFK, areaValueFK);

        if (caseProductUnderwriting != null)
        {
            underwritingValueSelected = true;
        }

        return underwritingValueSelected;
    }

    public static CaseProductUnderwriting findBy_FilteredProduct_Enrollment_AreaValue(Long filteredProductFK,
                                                                                      Long enrollmentFK,
                                                                                      Long areaValueFK)
    {
        String hql = " select caseProductUnderwriting from CaseProductUnderwriting caseProductUnderwriting" +
                     " where caseProductUnderwriting.FilteredProductFK = :filteredProductFK" +
                     " and caseProductUnderwriting.EnrollmentFK = :enrollmentFK" +
                     " and caseProductUnderwriting.AreaValueFK = :areaValueFK";

        EDITMap params = new EDITMap();

        params.put("filteredProductFK", filteredProductFK);
        params.put("enrollmentFK", enrollmentFK);
        params.put("areaValueFK", areaValueFK);

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        if (results.size() > 0)
        {
            return (CaseProductUnderwriting) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds the CaseProductUnderwriting with the given primary key
     *
     * @param caseProductUnderwritingPK
     *
     * @return
     */
    public static CaseProductUnderwriting findByPK(Long caseProductUnderwritingPK)
    {
        String hql = " select caseProductUnderwriting from CaseProductUnderwriting caseProductUnderwriting" +
                     " where caseProductUnderwriting.CaseProductUnderwritingPK = :caseProductUnderwritingPK";

        EDITMap params = new EDITMap();

        params.put("caseProductUnderwritingPK", caseProductUnderwritingPK);

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        if (results.size() > 0)
        {
            return (CaseProductUnderwriting) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all CaseProductUnderwritings
     *
     * @return array of CaseProductUnderwriting objects      ;
     */
    public static CaseProductUnderwriting[] findAll()
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting";

        EDITMap params = new EDITMap();

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return results.toArray(new CaseProductUnderwriting[results.size()]);
    }

    /**
     * Finder.
     * @param enrollmentFK
     * @return
     */
    public static CaseProductUnderwriting[] findByEnrollmentFK(Long enrollmentFK)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.EnrollmentFK = :enrollmentFK";

        EDITMap params = new EDITMap("enrollmentFK", enrollmentFK);

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return (CaseProductUnderwriting[]) results.toArray(new CaseProductUnderwriting[results.size()]);
    }

    /**
     * Finder.
     * @param enrollmentFK
     * @return
     */
    public static Long[] findUniqueFilteredProductFKsByEnrollmentFK(Long enrollmentFK)
    {
        String hql = " select distinct caseProductUnderwriting.FilteredProductFK from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.EnrollmentFK = :enrollmentFK";

        EDITMap params = new EDITMap("enrollmentFK", enrollmentFK);

        List results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return (Long[]) results.toArray(new Long[results.size()]);
    }

    /**
     * Finder.
     * @param filteredProductFK
     * @return
     */
    public static CaseProductUnderwriting[] findByFilteredProductFK_EnrollmentFK(Long filteredProductFK, Long enrollmentFK)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.FilteredProductFK = :filteredProductFK" +
                    " and caseProductUnderwriting.EnrollmentFK = :enrollmentFK";

        EDITMap params = new EDITMap();
        params.put("filteredProductFK", filteredProductFK);
        params.put("enrollmentFK", enrollmentFK);

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return (CaseProductUnderwriting[]) results.toArray(new CaseProductUnderwriting[results.size()]);
        }
    }

    /**
     * Builds a new CaseProductUnderwriting entity from the specified AreaValue and Enrollment
     * if and only if the association doesn't already exist.
     * @param areaValuePK
     * @param enrollment
     * @param filteredProduct
     * @param relToEE
     * @param deptLocFK
     * @param value
     * @param requiredOptionalCT
     * @throws edit.common.exceptions.EDITCaseException if the association already exists (avoiding a duplicate association)
     */
    public static CaseProductUnderwriting build(Long areaValuePK,
                             Enrollment enrollment,
                             FilteredProduct filteredProduct,
                             String relToEE,
                             Long departmentLocationFK,
                             String value,
                             String requiredOptionalCT) throws EDITCaseException
    {
        CaseProductUnderwriting caseProductUnderwriting = null;

        AreaValue[] areaValue = AreaValue.findBy_AreaValuePK(areaValuePK.longValue());

        AreaKey areaKey = AreaKey.findBy_AreaKeyPK(new Long(((AreaValueVO) areaValue[0].getVO()).getAreaKeyFK()));

        if (value.equals(""))
        {
            value = areaValue[0].getAreaValue();
        }

        if (!caseProductUnderwritingExists(areaValue[0], areaKey, enrollment, filteredProduct, relToEE, departmentLocationFK, value, areaKey.getGrouping(), requiredOptionalCT))
        {
            caseProductUnderwriting = (CaseProductUnderwriting) SessionHelper.newInstance(CaseProductUnderwriting.class, SessionHelper.EDITSOLUTIONS);

            caseProductUnderwriting.setEnrollment(enrollment);

            caseProductUnderwriting.setFilteredProduct(filteredProduct);

            if (departmentLocationFK != null)
            {
                DepartmentLocation departmentLocation = DepartmentLocation.findBy_DepartmentLocationPK(departmentLocationFK);

                caseProductUnderwriting.setDepartmentLocation(departmentLocation);
            }

            caseProductUnderwriting.setGrouping(areaKey.getGrouping());
            caseProductUnderwriting.setField(areaKey.getField());
            caseProductUnderwriting.setQualifier(areaValue[0].get_QualifierCT());
            caseProductUnderwriting.setRelationshipToEmployeeCT(relToEE);
            caseProductUnderwriting.setValue(value);
            caseProductUnderwriting.setRequiredOptionalCT(requiredOptionalCT);
        }
        else
        {
            throw new EDITCaseException("Duplicate Underwriting!");
        }

        return caseProductUnderwriting;
    }

    /**
     * The the existence of a CaseProductUnderwriting as defined by its areaValue/Enrollment associations.
     * @param areaValuePK
     * @param enrollment
     * @return
     */
    private static boolean caseProductUnderwritingExists(AreaValue areaValue, AreaKey areaKey,
                                                         Enrollment enrollment,
                                                         FilteredProduct filteredProduct, String relToEE, 
                                                         Long departmentLocationFK, String value,
                                                         String grouping, String requiredOptionalCT)
    {
        boolean caseProductUnderwritingExists = false;

        CaseProductUnderwriting caseProductUnderwriting = CaseProductUnderwriting.findExactMatch(areaValue, areaKey, enrollment, filteredProduct, relToEE, departmentLocationFK, value, grouping, requiredOptionalCT);

        if (caseProductUnderwriting != null)
        {
            caseProductUnderwritingExists = true;
        }

        return caseProductUnderwritingExists;
    }

    /**
     * Finds a CaseProductUnderwriting in persistence using an exact match of all the fields
     *
     * @param areaValue
     * @param areaKey
     * @param enrollment
     * @param filteredProduct
     * @param relToEE
     * @param departmentLocationFK
     * @param value
     * @param grouping
     * @param requiredOptionalCT
     *
     * @return found CaseProductUnderwriting
     */
    private static CaseProductUnderwriting findExactMatch(AreaValue areaValue, AreaKey areaKey,
                                                          Enrollment enrollment,
                                                          FilteredProduct filteredProduct, String relToEE, 
                                                          Long departmentLocationFK, String value,
                                                          String grouping, String requiredOptionalCT)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.Enrollment = :enrollment" +
                    " and caseProductUnderwriting.FilteredProduct = :filteredProduct" +
                    " and caseProductUnderwriting.Field = :field" +
                    " and caseProductUnderwriting.Qualifier = :qualifier" +
                    " and caseProductUnderwriting.Grouping = :grouping" +
                    " and caseProductUnderwriting.Value = :value";

        EDITMap params = new EDITMap();
        params.put("enrollment", enrollment);
        params.put("filteredProduct", filteredProduct);
        params.put("field", areaKey.getField());
        params.put("qualifier", areaValue.getQualifierCT());
        params.put("grouping", grouping);
        params.put("value", value);

        if  (requiredOptionalCT != null)
        {
            hql = hql + " and caseProductUnderwriting.RequiredOptionalCT = :requiredOptionalCT";
            params.put("requiredOptionalCT", requiredOptionalCT);
        }

        if (relToEE != null)
        {
            hql = hql + " and caseProductUnderwriting.RelationshipToEmployeeCT = :relToEE";
            params.put("relToEE", relToEE);
        }

        if (departmentLocationFK != null)
        {
            hql = hql + " and caseProductUnderwriting.DepartmentLocationFK = :departmentLocationFK";
            params.put("departmentLocationFK", departmentLocationFK);
        }

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return (CaseProductUnderwriting) results.get(0);
        }
    }

    public static CaseProductUnderwriting[] findByEnrollment_Grouping_Field_Value(Enrollment enrollment,
                                                             String grouping, String field, String value, FilteredProduct filteredProduct)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.Enrollment = :enrollment" +
                    " and caseProductUnderwriting.Grouping = :grouping" +
                    " and caseProductUnderwriting.Field = :field" +
                    " and caseProductUnderwriting.Value = :value" +
                     " and caseProductUnderwriting.FilteredProduct = :filteredProduct";

        EDITMap params = new EDITMap();
        params.put("enrollment", enrollment);
        params.put("grouping", grouping);
        params.put("field", field);
        params.put("value", value);
        params.put("filteredProduct", filteredProduct);

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return results.toArray(new CaseProductUnderwriting[results.size()]);
    }

    /**
     * Finds the CaseProductUnderwriting for a given Enrollment, FilteredProduct, Grouping, Field, and
     * RelationshipToEmployeeCT
     *
     * @param enrollment
     * @param filteredProduct
     * @param grouping
     * @param field
     * @param relationshipToEmployeeCT
     * @return
     */
    public static CaseProductUnderwriting[] findByEnrollment_FilteredProduct_Grouping_Field_RelationshipToEmployeeCT(Enrollment enrollment,
                                                             FilteredProduct filteredProduct, String grouping,
                                                             String field, String relationshipToEmployeeCT)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.Enrollment = :enrollment" +
                    " and caseProductUnderwriting.FilteredProduct = :filteredProduct" +
                    " and caseProductUnderwriting.Grouping = :grouping" +
                    " and caseProductUnderwriting.Field = :field";

        EDITMap params = new EDITMap();
        params.put("enrollment", enrollment);
        params.put("filteredProduct", filteredProduct);
        params.put("grouping", grouping);
        params.put("field", field);

        if (relationshipToEmployeeCT == null)
        {
            hql = hql.concat(" and caseProductUnderwriting.RelationshipToEmployeeCT is null");
        }
        else
        {
            hql = hql.concat(" and caseProductUnderwriting.RelationshipToEmployeeCT = :relationshipToEmployeeCT");

            params.put("relationshipToEmployeeCT", relationshipToEmployeeCT);
        }

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return results.toArray(new CaseProductUnderwriting[results.size()]);
    }

    /**
     * Finds the CaseProductUnderwriting for a given EnrollmentFK, FilteredProductFK, Grouping, Field, and
     * RelationshipToEmployeeCT
     *
     * @param enrollmentFK
     * @param filteredProductFK
     * @param grouping
     * @param field
     * @param relationshipToEmployeeCT
     * @return
     */
    public static CaseProductUnderwriting[] findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(Long enrollmentFK,
                                                             Long filteredProductFK, String grouping,
                                                             String field, String relationshipToEmployeeCT)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.EnrollmentFK = :enrollmentFK" +
                    " and caseProductUnderwriting.FilteredProductFK = :filteredProductFK" +
                    " and caseProductUnderwriting.Grouping = :grouping" +
                    " and caseProductUnderwriting.Field = :field";

        EDITMap params = new EDITMap();
        params.put("enrollmentFK", enrollmentFK);
        params.put("filteredProductFK", filteredProductFK);
        params.put("grouping", grouping);
        params.put("field", field);

        if (relationshipToEmployeeCT == null)
        {
            hql = hql.concat(" and caseProductUnderwriting.RelationshipToEmployeeCT is null");
        }
        else
        {
            hql = hql.concat(" and caseProductUnderwriting.RelationshipToEmployeeCT = :relationshipToEmployeeCT");

            params.put("relationshipToEmployeeCT", relationshipToEmployeeCT);
        }

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return results.toArray(new CaseProductUnderwriting[results.size()]);
    }
    
    /**
     * Finds the CaseProductUnderwriting for a given EnrollmentFK, FilteredProductFK, Grouping, Field, and
     * RelationshipToEmployeeCT
     *
     * @param enrollmentFK
     * @param filteredProductFK
     * @param grouping
     * @param field
     * @param qualifier
     * @param relationshipToEmployeeCT
     * @return
     */
    public static CaseProductUnderwriting[] findByEnrollmentFK_FilteredProductFK_Grouping_Field_Qualifier_RelationshipToEmployeeCT(Long enrollmentFK,
                                                             Long filteredProductFK, String grouping,
                                                             String field, String qualifier, String relationshipToEmployeeCT)
    {
        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.EnrollmentFK = :enrollmentFK" +
                    " and caseProductUnderwriting.FilteredProductFK = :filteredProductFK" +
                    " and caseProductUnderwriting.Grouping = :grouping" +
                    " and caseProductUnderwriting.Field = :field" +
                    " and caseProductUnderwriting.Qualifier = :qualifier";
                    

        EDITMap params = new EDITMap();
        params.put("enrollmentFK", enrollmentFK);
        params.put("filteredProductFK", filteredProductFK);
        params.put("grouping", grouping);
        params.put("field", field);
        params.put("qualifier", qualifier);


        if (relationshipToEmployeeCT == null)
        {
            hql = hql.concat(" and caseProductUnderwriting.RelationshipToEmployeeCT is null");
        }
        else
        {
            hql = hql.concat(" and caseProductUnderwriting.RelationshipToEmployeeCT = :relationshipToEmployeeCT");

            params.put("relationshipToEmployeeCT", relationshipToEmployeeCT);
        }

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return results.toArray(new CaseProductUnderwriting[results.size()]);
    }

    public static CaseProductUnderwriting[] findBestMatchForAreaValue(Long enrollmentFK,
                                          String grouping, String qualifierCT, String relationshipToEmployeeCT, long productStructurePK)
    {
        CaseProductUnderwriting[] caseProductUnderwritings =
                findByEnrollmentFK_Grouping_Qualifier_RelationshipToEmployeeCT(enrollmentFK,
                        grouping, qualifierCT, relationshipToEmployeeCT, productStructurePK);

        if (caseProductUnderwritings.length == 0)
        {
            if (relationshipToEmployeeCT != null)
            {
                caseProductUnderwritings =  findByEnrollmentFK_Grouping_Qualifier_RelationshipToEmployeeCT(enrollmentFK,
                        grouping, qualifierCT, null, productStructurePK);
            }
        }

        return caseProductUnderwritings;
    }

    public static CaseProductUnderwriting[] findByEnrollmentFK_Grouping_Qualifier_RelationshipToEmployeeCT(Long enrollmentFK,
                                          String grouping, String qualifier, String relationshipToEmployeeCT, long productStructurePK)
    {
        String checkRelToEmployeeSQL = " and caseProductUnderwriting.RelationshipToEmployeeCT = :relationshipToEmployeeCT";

        if (relationshipToEmployeeCT == null)
        {
            checkRelToEmployeeSQL = " and caseProductUnderwriting.RelationshipToEmployeeCT is null";
        }

        String hql = " from CaseProductUnderwriting caseProductUnderwriting" +
                    " where caseProductUnderwriting.FilteredProductFK IN (select FilteredProductPK from FilteredProduct filteredProduct" +
                    " where filteredProduct.ProductStructureFK = :productStructurePK)" +
                    " and caseProductUnderwriting.EnrollmentFK = :enrollmentFK" +
                    " and caseProductUnderwriting.Grouping = :grouping" +
                    " and caseProductUnderwriting.Qualifier = :qualifier" +
                    checkRelToEmployeeSQL;

        EDITMap params = new EDITMap();
        params.put("productStructurePK", new Long(productStructurePK));
        params.put("enrollmentFK", enrollmentFK);
        params.put("grouping", grouping);
        params.put("qualifier", qualifier);
        if (relationshipToEmployeeCT != null)
        {
            params.put("relationshipToEmployeeCT", relationshipToEmployeeCT);
        }

        List<CaseProductUnderwriting> results = SessionHelper.executeHQL(hql, params, CaseProductUnderwriting.DATABASE);

        return results.toArray(new CaseProductUnderwriting[results.size()]);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.CaseProductUnderwriting stagingCaseProductUnderwriting = new staging.CaseProductUnderwriting();

        stagingCaseProductUnderwriting.setField(this.field);
        stagingCaseProductUnderwriting.setGrouping(this.grouping);
        stagingCaseProductUnderwriting.setQualifier(this.qualifier);
        stagingCaseProductUnderwriting.setRelationshipToEmployee(this.relationshipToEmployeeCT);
        stagingCaseProductUnderwriting.setRequiredOptional(this.requiredOptionalCT);
        stagingCaseProductUnderwriting.setValue(this.value);

        if (stagingContext.getCurrentEnrollment() != null)
        {
            stagingCaseProductUnderwriting.setEnrollment(stagingContext.getCurrentEnrollment());
            stagingContext.getCurrentEnrollment().addCaseProductUnderwriting(stagingCaseProductUnderwriting);
        }

        if (stagingContext.getCurrentFilteredProduct() != null)
        {
            stagingCaseProductUnderwriting.setFilteredProduct(stagingContext.getCurrentFilteredProduct());
            stagingContext.getCurrentFilteredProduct().addCaseProductUnderwriting(stagingCaseProductUnderwriting);
        }

        SessionHelper.saveOrUpdate(stagingCaseProductUnderwriting, database);

        return stagingContext;
    }
}
