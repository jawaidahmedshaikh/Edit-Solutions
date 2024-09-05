/*
 * User: gfrosti
 * Date: May 18, 2007
 * Time: 10:29:37 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package group;

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.services.db.hibernate.SessionHelper;
import edit.services.db.hibernate.HibernateEntity;

import java.security.Policy.Parameters;
import java.util.*;

import fission.utility.DateTimeUtil;
import org.dom4j.*;
import org.apache.commons.collections.list.*;
import contract.*;

/**
 * @todo definition?
 * @author gfrosti
 */
public class DepartmentLocation extends HibernateEntity
{
    /**
     * The PK.
     */
    private Long departmentLocationPK;
    
    /**
     * The FK for the associated grouping element.
     */
    private Long contractGroupFK;
    
    /**
     * A unique business identifier.
     */
    private String deptLocCode;

    /**
     * The name of the department/location
     */
    private String deptLocName;

    /**
     * The date upon which this department becomes active.
     */
    private EDITDate effectiveDate;
    
    /**
     * The date upon which this department is no longer active.
     */
    private EDITDate terminationDate;

    /**
     * The associated grouping element.
     */
    private ContractGroup contractGroup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    private Set segments;


    /** Creates a new instance of DepartmentLocation */
    public DepartmentLocation()
    {
        // Set defaults
        terminationDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);

        if (segments == null)
        {
            segments = new HashSet();
        }

    }

    /**
     * @see #departmentLocationPK
     * @return Long
     */
    public Long getDepartmentLocationPK()
    {
        return departmentLocationPK;
    }

    /**
     * @see #departmentLocationPK
     * @param departmentLocationPK
     */
    public void setDepartmentLocationPK(Long departmentLocationPK)
    {
        this.departmentLocationPK = departmentLocationPK;
    }

    /**
     * @see #contractGroupFK
     * @return Long
     */
    public Long getContractGroupFK()
    {
        return contractGroupFK;
    }

    /**
     * @see #contractGroupFK
     * @param contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * @see #contractGroup
     * @return
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * @see #contractGroup
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    /**
     * @see #deptLocCode
     * @return
     */
    public String getDeptLocCode()
    {
        return deptLocCode;
    }

    /**
     * @see #deptLocCode
     * @param deptLocCode
     */
    public void setDeptLocCode(String deptLocCode)
    {
        this.deptLocCode = deptLocCode;
    }

    /**
     * @see #deptLocName
     * @return
     */
    public String getDeptLocName()
    {
        return deptLocName;
    }

    /**
     * @see #deptLocName
     * @param deptLocName
     */
    public void setDeptLocName(String deptLocName)
    {
        this.deptLocName = deptLocName;
    }

    /**
     * @see #effectiveDate
     * @return EDITDate
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * @see #effectiveDate
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIDeptLocEffectiveDate()
    {
        String date = null;

        if (getEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIDeptLocEffectiveDate(String uiDeptLocEffectiveDate)
    {
        if (uiDeptLocEffectiveDate != null)
        {
            setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiDeptLocEffectiveDate));
        }
    }

    /**
     * @see #terminationDate
     * @return EDITDate
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * @see #terminationDate
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIDeptLocTerminationDate()
    {
        String date = null;

        if (getTerminationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIDeptLocTerminationDate(String uiDeptLocTerminationDate)
    {
        if (uiDeptLocTerminationDate != null)
        {
            setTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiDeptLocTerminationDate));
        }
    }

    public String getDatabase()
    {
        return DepartmentLocation.DATABASE;
    }

    /**
     * Finder.
     * @param contractGroupFK
     * @return
     */
    public static DepartmentLocation[] findBy_ContractGroupFK(Long contractGroupFK)
    {
        String hql = " from DepartmentLocation departmentLocation" +
                    " where departmentLocation.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        return results.toArray(new DepartmentLocation[results.size()]);
    }
    
    /**
     * Reorer the department locations based on the column name
     * @param contractGroupFK
     * @param columnName
     * @param descInd 
     * @return
     */
    public static DepartmentLocation[] findBy_ContractGroupFK_OrderBy(Long contractGroupFK, String columnName, String descInd)
    {
        String hql = " from DepartmentLocation departmentLocation" +
                " where departmentLocation.ContractGroupFK = :contractGroupFK" +
        		" order by " + columnName + " " + descInd;

	    EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);
	
	    List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);
	
	    return results.toArray(new DepartmentLocation[results.size()]);
    }

    /**
     * Reorer the department locations based on the column name
     * @param contractGroupFK
     * @param columnName
     * @return
     */
    public static DepartmentLocation[] findActiveBy_ContractGroupFK(Long contractGroupFK)
    {
        String hql = " from DepartmentLocation departmentLocation" +
                " where departmentLocation.ContractGroupFK = :contractGroupFK" +
        		" and departmentLocation.EffectiveDate <= :todaysDate" +
                " and (departmentLocation.TerminationDate >= :todaysDate or departmentLocation.TerminationDate is null)";

        
        EDITDate todaysDate = new EDITDate();

        EDITMap params = new EDITMap();
        params.put("contractGroupFK", contractGroupFK);
        params.put("todaysDate", todaysDate);
	
	    List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);
	
	    return results.toArray(new DepartmentLocation[results.size()]);
    }

    /**
     * Reorer the department locations based on the column name
     * @param contractGroupFK
     * @param columnName
     * @return
     */
    public static DepartmentLocation[] findActiveBy_ContractGroupFK_OrderBy(Long contractGroupFK, String columnName, String descInd)
    {
        String hql = " from DepartmentLocation departmentLocation" +
                " where departmentLocation.ContractGroupFK = :contractGroupFK" +
        		" and departmentLocation.EffectiveDate <= :todaysDate" +
                " and (departmentLocation.TerminationDate >= :todaysDate or departmentLocation.TerminationDate is null)" +                 
        		" order by " + columnName + " " + descInd;

        
        EDITDate todaysDate = new EDITDate();

        EDITMap params = new EDITMap();
        params.put("contractGroupFK", contractGroupFK);
        params.put("todaysDate", todaysDate);
	
	    List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);
	
	    return results.toArray(new DepartmentLocation[results.size()]);
    }

    /**
     * Finds all DepartmentLocations
     * @return
     */
    public static DepartmentLocation[] findAll()
    {
        String hql = " from DepartmentLocation departmentLocation";

        EDITMap params = new EDITMap();

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        return results.toArray(new DepartmentLocation[results.size()]);
    }

    /**
     * Finds all the active DepartmentLocations.  "Active" is defined by today's date being within the EffectiveDate
     * and TerminationDate ranges.
     *
     * @return  array of active DepartmentLocations
     */
    public static DepartmentLocation[] findAllActive()
    {
        String hql = " from DepartmentLocation departmentLocation" +
                    " where departmentLocation.EffectiveDate <= :todaysDate" +
                    " and departmentLocation.TerminationDate >= :todaysDate";

        EDITDate todaysDate = new EDITDate();

        EDITMap params = new EDITMap("todaysDate", todaysDate);

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        return results.toArray(new DepartmentLocation[results.size()]);
    }

    /**
     * Finds all the active DepartmentLocations for the given BatchContractSetup.  "Active" is defined by today's date
     * being within the EffectiveDate and TerminationDate ranges.  A null TerminationDate is valid and means that the
     * DepartmentLocation has not been terminated.
     *
     * @return  array of active DepartmentLocations for the specified BatchContractSetup
     */
    public static DepartmentLocation[] findActiveByBatchContractSetupPK(Long batchContractSetupPK)
    {
        String hql = " select departmentLocation from DepartmentLocation departmentLocation" +
                     " join fetch departmentLocation.ContractGroup contractGroup" +
                     " join fetch contractGroup.BatchContractSetups batchContractSetup" +
                     " where departmentLocation.EffectiveDate <= :todaysDate" +
                     " and (departmentLocation.TerminationDate >= :todaysDate or departmentLocation.TerminationDate is null)" +
                     " and batchContractSetup.BatchContractSetupPK = :batchContractSetupPK";

        EDITDate todaysDate = new EDITDate();

        EDITMap params = new EDITMap();
        params.put("todaysDate", todaysDate);
        params.put("batchContractSetupPK", batchContractSetupPK);

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        return results.toArray(new DepartmentLocation[results.size()]);
    }

    /**
     * Finder.
     * @param deptLocPK
     * @return
     */
    public static DepartmentLocation findBy_DepartmentLocationPK(Long deptLocPK)
    {
        return (DepartmentLocation) SessionHelper.get(DepartmentLocation.class, deptLocPK, DepartmentLocation.DATABASE);
    }

    /**
     * Finder.  Finds all DepartmentLocations for a given contractGroupNumber (a Group contractGroup)
     *
     * @param contractGroupNumber
     * 
     * @return array of DepartmentLocations whose Group ContractGroup has the given contractGroupNumber
     */
    public static DepartmentLocation[] findBy_ContractGroupNumber(String contractGroupNumber)
    {
        String hql = " select departmentLocation from DepartmentLocation departmentLocation" +
                     " join departmentLocation.ContractGroup contractGroup" +
                     " where contractGroup.ContractGroupNumber = :contractGroupNumber" +
                     " and contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap();
        params.put("contractGroupNumber", contractGroupNumber);
        params.put("contractGroupTypeCT", ContractGroup.CONTRACTGROUPTYPECT_GROUP);

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        return results.toArray(new DepartmentLocation[results.size()]);
    }
    
    /**
     * Finds a department location using the department location code and contract group
     * @param contractGroupFK
     * @return
     */
    public static DepartmentLocation findBy_ContractGroupFKAndDeptLocCode(Long contractGroupFK, String deptLocCode)
    {
    	DepartmentLocation departmentLocation = null;
    	
        String hql = " from DepartmentLocation departmentLocation" +
                    " where departmentLocation.ContractGroupFK = :contractGroupFK and departmentLocation.DeptLocCode = :deptLocCode";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);
        params.put("deptLocCode", deptLocCode);

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        if(!results.isEmpty()) {
        	departmentLocation = (DepartmentLocation) results.get(0);
        }
        return departmentLocation;
    }

    /**
     * Finds the DepartmentLocation by the supplied deptLocCode
     * @param deptLocCode
     * @return
     */
    public static DepartmentLocation findBy_DeptLocCode(String deptLocCode)
    {
        DepartmentLocation departmentLocation = null;

        String hql = " from DepartmentLocation departmentLocation" +
                    " where departmentLocation.DeptLocCode = :deptLocCode";


        EDITMap params = new EDITMap("deptLocCode", deptLocCode);

        List<DepartmentLocation> results = SessionHelper.executeHQL(hql, params, DepartmentLocation.DATABASE);

        if (!results.isEmpty())
        {
            departmentLocation = (DepartmentLocation) results.get(0);
        }

        return departmentLocation;
    }

    public void removeSegment(Segment segment)
    {
        this.getSegments().remove(segment);

        segment.setDepartmentLocation(null);

        SessionHelper.saveOrUpdate(segment, DepartmentLocation.DATABASE);
    }

    public Set getSegments()
    {
        return segments;
    }

    public void setSegments(Set segments)
    {
        this.segments = segments;
    }
}
