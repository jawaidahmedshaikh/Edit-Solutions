/*
 * User: unknown
 * Date: Oct 20, 2003
 * Time: unknown
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.*;
import engine.Area;
import engine.AreaKey;
import engine.AreaValue;
import engine.common.Constants;
import fission.utility.Util;
import group.*;
import java.util.Map;

/**
 * This is the implementation for the Getareatable instruction.
 * It performs the best match for the Area table based on the
 * values of the driving rule set when the script is loaded.
 * All the table values are setup as working storage fields
 * for access by the script.
 * <P>
 * The script writer also has the ability to target a specific value within
 * all the values of an Area's Grouping. If so, then WS will contain
 * an AreaField value that tells this instruction which field value to get.
 * <P>
 * In addition, there are 2 ways that the area values can be overwritten:
 * <P>
 * In new business, the script writer provides the BatchContractSetupPK.  Its Enrollment is found and the
 * CaseProductUnderwriting is used to overwrite any fields/values that exist.
 * <P>
 * In in-force, the script writer provides the group's ContractGroupPK.  Its Enrollment is found by looking for the
 * Case's Enrollment with the greatest beginningPolicyDate that is <= the areaDate.  The CaseProductUnderwriting is used
 * to overwrite any fields/values that exist.
 */
public final class Getareatable extends InstOneOperand
{
    private Area area;

    /**
     * Getareatable constructor
     * <p/>
     * @throws SPException
     */
    public Getareatable() throws SPException
    {
        super();
    }

    /**
     * Compiles the instruction
     * <p/>
     * @param aScriptProcessor Instance of ScriptProcessor
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Note: No compiling is required for this instruction
    }

    /**
     * Executes the instruction
     * <p/>
     * @throws SPException If there is an error while executing the instruction
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        //  Get input
        long productStructurePK = determineProductStructurePK();
        
        String areaCT = sp.getWSEntry("Area");
        String qualifierCT = Util.initString(sp.getWSEntry("Qualifier"), "*");
        String areaGroup = sp.getWSEntry("AreaGroup");
        EDITDate areaDate = new EDITDate(sp.getWSEntry("AreaDate"));
        String batchContractSetupPKString = sp.getWSEntry("BatchContractSetupPK");
        String groupContractGroupPKString = sp.getWSEntry("ContractGroupPK");
        String relationshipToEmployeeCT = sp.getWSEntry("RelationshipToEmployeeCT");
        String areaField = sp.getWSEntry("AreaField");

        if (batchContractSetupPKString != null && batchContractSetupPKString.equalsIgnoreCase("#NULL")) {
        	batchContractSetupPKString = null;
        }
        
        runAreaTableLookup(productStructurePK, areaCT, areaGroup, areaDate, qualifierCT, areaField, batchContractSetupPKString, groupContractGroupPKString, relationshipToEmployeeCT, sp.getWS());
        
        // Increment instruction pointer
        sp.incrementInstPtr();
    }
    
    public void runAreaTableLookup(long productStructurePK, String areaCT, String areaGroup, EDITDate areaDate, String qualifierCT, String areaField, String batchContractSetupPKString, String groupContractGroupPKString, String relationshipToEmployeeCT, Map results)
    {
        //  Get the area values from the Area table
        getAreaValue(productStructurePK, areaCT, areaGroup, areaDate, qualifierCT, areaField, results);

        //  Over-write the areaValues with CaseProductUnderwriting values for new business or in-force, if
        //  script writer requested
        if (shouldOverwriteWithNewBusinessUnderwriting(batchContractSetupPKString, areaGroup))
        {
            overwriteWithNewBusinessUnderwriting(batchContractSetupPKString, areaGroup, qualifierCT, relationshipToEmployeeCT, productStructurePK, results);
        }
        else if (shouldOverwriteWithInForceUnderwriting(groupContractGroupPKString, areaGroup))
        {
            overwriteWithInForceUnderwriting(groupContractGroupPKString, areaGroup, qualifierCT, relationshipToEmployeeCT, areaDate, productStructurePK, results);
        }        
    }

    /**
     * Getter.
     * @param productStructurePK
     * @param areaCT
     * @param grouping
     * @param effectiveDate
     * @return
     */
    private Area getArea(long productStructurePK, String areaCT, String grouping, EDITDate effectiveDate, String qualifierCT)
    {
        area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        return area;
    }

    /**
     * Overrides default Area.
     * @param area
     */
    public void setArea(Area area)
    {
        this.area = area;
    }

    /**
     * Determines and returns the productStructurePK.  If the script writer specified the ProductStructurePK, that value
     * is used.  Otherwise, the value from the script's ProductRule is used.
     *
     * @return appropriate productStructurePK
     */
    private long determineProductStructurePK()
    {
        ProductRuleProcessor pr = sp.getProductRule();

        long productStructurePK = pr.getProductStructurePK();

        String productStructurePKString = sp.getWSEntry("ProductStructurePK");

        // script writers can override the default driver's ProductStructure should they choose to
        if (productStructurePKString != null)
        {
            productStructurePK = Long.parseLong(productStructurePKString);
        }

        return productStructurePK;
    }

    /**
     * Gets the areaValues and pushes the appropriate one(s) to working storage or the stack
     * If the script writer asked for a specific areaValue (by specifying the areaField), only that value is pushed and
     * it is pushed to the stack. Otherwise, all found areaValues are pushed to working storage
     *
     * @param productStructurePK                criteria used to find areaValues
     * @param areaCT                            criteria used to find areaValues
     * @param areaGroup                         criteria used to find areaValues
     * @param areaDate                          criteria used to find areaValues
     * @param qualifierCT                       criteria used to find areaValues
     * @param areaField                         field specified by script writer when wanting only a specific areaValue
     */
    private void getAreaValue(long productStructurePK, String areaCT, String areaGroup, EDITDate areaDate,
                              String qualifierCT, String areaField, Map<String, Object> results)
    {
        Area area = getArea(productStructurePK, areaCT, areaGroup, areaDate, qualifierCT);

        AreaValue[] areaValues = area.getAreaValues();

        if (wantSpecificAreaValue(areaField))
        {
            pushSpecificAreaValue(areaValues, areaField);
        }
        else
        {
            pushAllAreaValues(areaValues, results);
        }
    }

    /**
     * Pushes a single areaValue whose field name corresponds to the requested areaField.  The value is pushed to
     * the stack, allowing the script writer to assign the parameter name.
     *
     * @param areaValues        array of areaValues found in persistence
     * @param areaField         field name requested by script writer
     */
    private void pushSpecificAreaValue(AreaValue[] areaValues, String areaField)
    {
        // This instruction has been modified to allow the script-writer to
        // target a specific AreaValue as opposed to loading all of the AreaValues
        // for a specific AreaGroup. If the AreaField is not null, then that is the
        // clue that the script-writer is targeting a specific value. GF
        for (int i = 0; i < areaValues.length; i++)
        {
            String areaValue = areaValues[i].getAreaValue();

            AreaKey areaKey = areaValues[i].getAreaKey();

            String field = areaKey.getField();

            if (isRequestedAreaField(areaField, field))
            {
                //  Push the value only so the script writer can assign the name
                sp.push(areaValue);

                break;
            }
        }
    }

    /**
     * Pushes all areaValues found in persistence to working storage
     *
     * @param areaValues        array of areaValues found in persistence
     */
    private void pushAllAreaValues(AreaValue[] areaValues, Map<String, Object> results)
    {
        for (int i = 0; i < areaValues.length; i++)
        {
            String areaValue = areaValues[i].getAreaValue();

            AreaKey areaKey = areaValues[i].getAreaKey();

            String field = areaKey.getField();

            //  Push the field name and the value
            results.put(field, areaValue);
        }
    }

    /**
     * Determines whether a specific areaValue should be pushed out or not (versus all of the areaValues)
     *
     * @param areaField             field specified by script writer when wanting only a specific areaValue
     *
     * @return  true if a specific areaValue is desired, false otherwise
     */
    private boolean wantSpecificAreaValue(String areaField)
    {
        if (areaField != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines if the field name in the AreaKey is the field requested via the areaField ws parameter
     *
     * @param areaField                 working storage parameter defined by the script writer for a specific areaValue
     * @param field                     AreaKey field name
     *
     * @return  true if the AreaKey field matches the requested areaField, false otherwise
     */
    private boolean isRequestedAreaField(String areaField, String field)
    {
        if (areaField.equalsIgnoreCase(field))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines whether the areaTable entries should be overwritten with the CaseProductUnderwriting values for
     * new business.
     * <P>
     * If the batchContractSetupPK is not null AND the areaGroup is one of the possible CASE... groupings, the
     * underwritings should be used.
     *
     * @param batchContractSetupPKString                BatchContractSetupPK as a String.  If it is null,
     *                                                  underwriting should not be used
     * @param areaGroup                                 grouping field on underwriting.  If it does not start with
     *                                                  'CASE...', underwriting should not be used
     *
     * @return true if the undewriting should be used, false otherwise
     */
    private boolean shouldOverwriteWithNewBusinessUnderwriting(String batchContractSetupPKString, String areaGroup)
    {
        if ((batchContractSetupPKString != null) && (!batchContractSetupPKString. equals(Constants.ScriptKeyword.NULL)) 
        		&& areaGroup.startsWith(CaseProductUnderwriting.GROUPING_CASE_ROOTNAME))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines whether the areaTable entries should be overwritten with the CaseProductUnderwriting values for
     * in-force.
     * <P>
     * If the group ContractGroupPK is not null AND the areaGroup is one of the possible CASE... groupings, the
     * underwritings should be used.
     *
     * @param groupContractGroupPKString                Group ContractGroupPKString as a String.  If it is null,
     *                                                  underwriting should not be used
     * @param areaGroup                                 grouping field on underwriting.  If it does not start with
     *                                                  'CASE...', underwriting should not be used
     *
     * @return true if the undewriting should be used, false otherwise
     */
    private boolean shouldOverwriteWithInForceUnderwriting(String groupContractGroupPKString, String areaGroup)
    {
        if (groupContractGroupPKString != null && areaGroup.startsWith(CaseProductUnderwriting.GROUPING_CASE_ROOTNAME))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Finds the proper CaseProductUnderwriting information for new business and overwrites the fields/values in
     * working storage (the area table values are already in ws at this time, if any underwriting fields are found,
     * they will simply overwrite the area values).
     *
     * @param batchContractSetupPKString            primary key of the BatchContractSetup to be used to find the Enrollment
     * @param areaGroup                             grouping field on underwriting (corresponds to areaTable grouping)
     * @param qualifierCT                           qualifier field on underwriting (corresponds to the areaTable qualifier)
     * @param relationshipToEmployeeCT              field to match on underwriting
     */
    private void overwriteWithNewBusinessUnderwriting(String batchContractSetupPKString, String areaGroup, String qualifierCT,
                                                     String relationshipToEmployeeCT, long productStructurePK, Map<String, Object> results)
    {
        Long batchContractSetupPK = new Long(batchContractSetupPKString);

        BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

        if (batchContractSetup != null)
        {
            Long enrollmentFK = batchContractSetup.getEnrollmentFK();

            pushUnderwritingValues(enrollmentFK, areaGroup, qualifierCT, relationshipToEmployeeCT, productStructurePK, results);
        }
    }

    /**
     * Finds the proper CaseProductUnderwriting information for in-force and overwrites the fields/values in working
     * storage (the area table values are already in ws at this time, if any underwriting fields are found, they will
     * simply overwrite the area values).
     * <P>
     * The Case has many Enrollments.  The Enrollment used to get the proper CaseProductUnderwriting is the one
     * with the greatest beginningPolicyDate that is <= the specified areaDate
     *
     * @param groupContractGroupPKString            primary key of the group ContractGroup to be used to find the Enrollment
     * @param areaGroup                             grouping field on underwriting (corresponds to areaTable grouping)
     * @param qualifierCT                           qualifier field on underwriting (corresponds to the areaTable qualifier)
     * @param relationshipToEmployeeCT              field to match on underwriting
     * @param areaDate                              date used to find the proper enrollment
     */
    private void overwriteWithInForceUnderwriting(String groupContractGroupPKString, String areaGroup, String qualifierCT,
                                                     String relationshipToEmployeeCT, EDITDate areaDate, long productStructurePK, Map<String, Object> results)
    {
        Long groupContractGroupPK = new Long(groupContractGroupPKString);

        ContractGroup groupContractGroup = ContractGroup.findByPK(groupContractGroupPK);

        ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

        Enrollment enrollment = Enrollment.findByContractGroup_GreatestBeginningPolicyDate(caseContractGroup.getContractGroupPK(), areaDate);

        pushUnderwritingValues(enrollment.getEnrollmentPK(), areaGroup, qualifierCT, relationshipToEmployeeCT, productStructurePK, results);
    }

    /**
     * Pushes the CaseProductUnderwriting fields into working storage
     *
     * @param enrollmentFK                          Enrollment containing the desired CaseProductUnderwriting
     * @param areaGroup                             grouping field on underwriting (corresponds to areaTable grouping)
     * @param qualifierCT                           qualifier field on underwriting (corresponds to the areaTable qualifier)
     * @param relationshipToEmployeeCT              field to match on underwriting
     */
    private void pushUnderwritingValues(Long enrollmentFK, String areaGroup, String qualifierCT, String relationshipToEmployeeCT, long productStructurePK, Map<String, Object> results)
    {
        CaseProductUnderwriting[] caseProductUnderwritings =
                CaseProductUnderwriting.findBestMatchForAreaValue(enrollmentFK, areaGroup, qualifierCT, relationshipToEmployeeCT, productStructurePK);

        for (int i = 0; i < caseProductUnderwritings.length; i++)
        {
            CaseProductUnderwriting caseProductUnderwriting = caseProductUnderwritings[i];

            String field = caseProductUnderwriting.getField();
            String value = caseProductUnderwriting.getValue();

            results.put(field, value);
        }
    }
}