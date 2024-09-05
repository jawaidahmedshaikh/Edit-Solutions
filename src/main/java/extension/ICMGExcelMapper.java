/*
 * User: sdorman
 * Date: Dec 9, 2004
 * Time: 8:56:30 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package extension;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.logging.log4j.Logger;
import edit.common.vo.*;
import edit.common.*;
import edit.common.exceptions.EDITContractException;
import edit.services.logging.Logging;

import client.business.Client;
import client.component.ClientComponent;
import contract.Segment;
import role.ClientRole;

import java.util.*;
import java.text.*;

import logging.*;
import contract.ui.ImportNewBusinessResponse;
import engine.Company;
import fission.utility.Util;
import group.ContractGroup;

/**
 * Maps an Excel file to the proper VOs for importing. The Excel format is specific to our ICMG client.
 */

/*   The following contains the mapping information for New Business
        Cell Letter     Index       Variable
        A               0           MasterVO.MasterNumber
        B               1           SegmentVO.ContractNumber
        C, D            2, 3        -
        E               4           SegmentVO.IssueDate, SegmentVO.EffectiveDate, SegmentVO.ApplicationReceivedDate
        F               5           ClientDetailVO.LastName
        G               6           ClientDetailVO.FirstName
        H               7           ClientDetailVO.MiddleName
        I               8           ClientDetailVO.TaxID (Insured SSN)
        J               9           ClientDetailVO.BirthDate
        K               10          ClientDetailVO.GenderCT
        L               11          ContractClientVO.RatedGenderCT
        M               12          SegmentVO.LifeVO.MECStatusCT
        N               13          SegmentVO.AgentHierarchyVO.AgentSnapshotVO      ??
        O               14          Product (Plan Code)
        P               15          -
        Q               16          -
        R               17          SegmentVO.LifeVO.FaceAmount
        S               18          -
        T               19          SegmentVO.LifeVO.DeathBenefitOptionCT
        U               20          SegmentVO.LifeVO.Option7702CT
        V               21          SegmentVO.ContractClientVO.UnderwritingClassCT
        W               22          SegmentVO.ContractClientVO.ClassCT
        X               23          SegmentVO.ContractClientVO.PercentExtra
        Y               24          SegmentVO.ContractClientVO.FlatExtra
        Z               25          ???????????
        AA              26          SegmentVO.ContractClientVO.FlatExtraDur
        AB              27          ClientDetailVO.ClientAddressVO.AddressLines  (Insured's AddressTypeCT = PrimaryAddress, comma delimited)
        AC              28          -
        AD              29          ClientDetailVO.ClientAddressVO.City          (Insured's AddressTypeCT = PrimaryAddress)
        AE              30          ClientDetailVO.ClientAddressVO.StateCT       (Insured's AddressTypeCT = PrimaryAddress)
        AF              31          ClientDetailVO.ClientAddressVO.ZipCode       (Insured's AddressTypeCT = PrimaryAddress)
        AG              32          ClientDetailVO.ClientAddressVO.CountryCT     (Insured's AddressTypeCT = PrimaryAddress)
        AH              33          ClientDetailVO.ClientAddressVO.StateCT       (Insured's AddressTypeCT = BusinessAddress)
        AI              34          ClientDetailVO.ClientAddressVO.ZipCode       (Insured's AddressTypeCT = BusinessAddress)
        AJ              35          ClientDetailVO.TrustTypeCT                   (Owner TrustTypeCT)
        AK              36          ClientDetailVO.CorporateName                 (Owner Name - based on trust type could be corporate or individual)
        AL              37          ClientDetailVO.TaxInformationVO.TaxIdTypeCT  (Owner Tax Id Type)
        AM              38          ClientDetailVO.TaxIdentification             (Owner Tax Id)
        AN              39          -
        AO              40          ClientDetailVO.ClientAddressVO.AddressLines   (Owner's AddressTypeCT = PrimaryAddress, comma delimited)
        AP              41          ClientDetailVO.ClientAddressVO.City           (Owner's AddressTypeCT = PrimaryAddress)
        AQ              42          ClientDetailVO.ClientAddressVO.StateCT        (Owner's AddressTypeCT = PrimaryAddress)
        AR              43          ClientDetailVO.ClientAddressVO.ZipCode        (Owner's AddressTypeCT = PrimaryAddress)
        AS              44          ClientDetailVO.ClientAddressVO.CountryCT      (Owner's AddressTypeCT = PrimaryAddress)
        AT              45          -
        AU              46          SegmentVO.DepositsVO.OldPolicyNumber
        AV              47          SegmentVO.DepositsVO.ExchangePolicyEffectiveDate
        AW              48          SegmentVO.DepositsVO.OldCompany
        AX              49          1035 Cash Value (used To populate DepositsVO.AnticipatedAmount)
        AY              50          SegmentVO.DepositsVO.PriorCompanyMECStatusCT
        AZ              51          SegmentVO.PointInScaleIndicator
        BA              52          SegmentVO.IssueState
        BB              53          -
        BC              54          -
        BD              55          SegmentVO.ChargeDeductDivisionInd
*/

public class ICMGExcelMapper
{
    private static String STRING_FIELD_TYPE = "String";
    private static String NUMERIC_FIELD_TYPE = "Numeric";
    private static String DATE_FIELD_TYPE = "Date";

    public ImportNewBusinessResponse[] importNewBusiness(HSSFWorkbook hssfWorkbook, 
                                                         String productStructureId,
                                                         String operator) throws EDITContractException
    {
        List importResponses = new ArrayList();

        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        int startingRow = 3;    // data begins on the 4th row

        int lastRow = hssfSheet.getLastRowNum();

        boolean contractNumberExists;
        boolean insuredClientExists;
        boolean ownerClientExists;
        boolean checkOwner = true;

        //  Loop through rows, each row represents a new contract
        for (int i = startingRow; i <= lastRow; i++)
        {
            HSSFRow hssfRow = hssfSheet.getRow(i);
            if (hssfRow != null)
            {
                String product = getExcelCellValue(hssfRow, 14, STRING_FIELD_TYPE);
                String contractNumber = "";

                if (product.equals("821") || product.equals("831"))
                {
                    contractNumber = getExcelCellValue(hssfRow, 0, STRING_FIELD_TYPE) + getExcelCellValue(hssfRow, 1, STRING_FIELD_TYPE);
                }
                else
                {
                    contractNumber = getExcelCellValue(hssfRow, 0, STRING_FIELD_TYPE);
                }

                contractNumberExists = checkForDuplicateContractNumber(contractNumber);

                if (!contractNumberExists)
                {
                    String insuredLastName = getExcelCellValue(hssfRow, 5, STRING_FIELD_TYPE);
                    if (!insuredLastName.equals(""))
                    {
                        String insuredTaxId = getExcelCellValue(hssfRow, 8, STRING_FIELD_TYPE);
                        String insuredTaxIdType = "SocialSecurityNumber";
                        String ownerTaxId = getExcelCellValue(hssfRow, 38, STRING_FIELD_TYPE);
                        String ownerTaxIdType = getExcelCellValue(hssfRow, 37, STRING_FIELD_TYPE);
                        if (ownerTaxIdType.equalsIgnoreCase("T"))
                        {
                            ownerTaxIdType = "CorporateTaxId";
                        }
                        else if (ownerTaxIdType.equalsIgnoreCase("S"))
                        {
                            ownerTaxIdType = "SocialSecurityNumber";
                        }

                        insuredClientExists = checkForDuplicateClient(insuredTaxId, insuredTaxIdType, "insured", hssfRow);
                        ownerClientExists = checkForDuplicateClient(ownerTaxId, ownerTaxIdType, "owner", hssfRow);

                        if (!insuredClientExists && !ownerClientExists)
                        {
                            ContractClientVO insuredContractClientVO = buildInsuredClient(hssfRow, "Spreadsheet", operator, contractNumber);
                            if (insuredContractClientVO != null)
                            {
                                ContractClientVO ownerContractClientVO   = buildOwnerClient(hssfRow, "Spreadsheet", operator, checkOwner, contractNumber);
                                if (ownerContractClientVO != null)
                                {
                                    // Build SegmentVO with its attachments
                                    SegmentVO baseSegmentVO = buildBaseSegmentVO(contractNumber, hssfRow, productStructureId, operator);

                                    //  Attach insured and owner clients
                                    baseSegmentVO.addContractClientVO(insuredContractClientVO);
                                    baseSegmentVO.addContractClientVO(ownerContractClientVO);

                                    //  Calculate and set the issue age of the insured client
                        //            setIssueAge(baseSegmentVO, insuredContractClientVO);

                                    boolean validateSuccessful = validateContract(baseSegmentVO, insuredContractClientVO, ownerContractClientVO, importResponses);

                                    if (validateSuccessful)
                                    {
                                        saveContract(baseSegmentVO, importResponses);
                                    }
                                }
                                else
                                {
                                    ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(contractNumber, "Contract " + contractNumber + " Import Failed - Could Not Create Owner");
                                    importResponses.add(importNBResponse);
                                }
                            }
                            else
                            {
                                ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(contractNumber, "Contract " + contractNumber + " Import Failed - Could Not Create Insured");
                                importResponses.add(importNBResponse);
                            }
                        }
                        else
                        {
                            List clientTypes = new ArrayList();
                            String insuredSpreadsheetName = "";
                            String insuredDBName = "";
                            String ownerSpreadsheetName = "";
                            String ownerDBName = "";
                            if (insuredClientExists)
                            {
                                clientTypes.add("Insured");
                                String insuredFirstName = getExcelCellValue(hssfRow, 6, STRING_FIELD_TYPE);
                                String insuredMiddleName = getExcelCellValue(hssfRow, 7, STRING_FIELD_TYPE);
                                insuredSpreadsheetName = insuredLastName + ", " + insuredFirstName + " " + insuredMiddleName;
                                insuredDBName = getClientName(insuredTaxId, insuredTaxIdType, "insured", hssfRow);
                            }

                            if (ownerClientExists)
                            {
                                clientTypes.add("Owner");
                                ownerSpreadsheetName = getExcelCellValue(hssfRow, 36, STRING_FIELD_TYPE);
                                ownerDBName = getClientName(ownerTaxId, ownerTaxIdType, "owner", hssfRow);
                            }

                            ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(contractNumber, "Contract " + contractNumber + " Record Not Imported", clientTypes);
                            importNBResponse.setInsuredSpreadsheetName(insuredSpreadsheetName);
                            importNBResponse.setInsuredDBName(insuredDBName);
                            importNBResponse.setOwnerSpreadsheetName(ownerSpreadsheetName);
                            importNBResponse.setOwnerDBName(ownerDBName);
                            importResponses.add(importNBResponse);
                        }
                    }
                }
                else
                {
                    ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(contractNumber, "Contract " + contractNumber + " Already Exists - Record Not Imported");
                    importResponses.add(importNBResponse);
                }
            }
        }

        return (ImportNewBusinessResponse[]) importResponses.toArray(new ImportNewBusinessResponse[importResponses.size()]);
    }

    public ImportNewBusinessResponse[] importNewBusiness(HSSFWorkbook hssfWorkbook, 
                                                         String importValues, 
                                                         String productStructureId,
                                                         String operator) throws EDITContractException
    {
        List importResponses = new ArrayList();

        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        int startingRow = 3;    // data begins on the 4th row

        int lastRow = hssfSheet.getLastRowNum();

        String[] contractInfo = Util.fastTokenizer(importValues, "|");

        int insuredSourceIndex;
        int ownerSourceIndex;

        boolean checkOwner = false;

        for (int i = 0; i < contractInfo.length; i++)
        {
            insuredSourceIndex = contractInfo[i].indexOf("_") + 1;
            ownerSourceIndex = contractInfo[i].indexOf("__") + 2;

            String contractNumber = contractInfo[i].substring(0, contractInfo[i].indexOf("_"));
            String insuredSource = contractInfo[i].substring(insuredSourceIndex, contractInfo[i].indexOf("__"));
            String ownerSource = contractInfo[i].substring(ownerSourceIndex);

            //  Loop through rows, each row represents a new contract
            for (int j = startingRow; j <= lastRow; j++)
            {
                HSSFRow hssfRow = hssfSheet.getRow(j);
                if (hssfRow != null)
                {
                    String product = getExcelCellValue(hssfRow, 14, STRING_FIELD_TYPE);
                    String wkbkContractNumber = "";

                    if (product.equals("821") || product.equals("831"))
                    {
                        wkbkContractNumber = getExcelCellValue(hssfRow, 0, STRING_FIELD_TYPE) + getExcelCellValue(hssfRow, 1, STRING_FIELD_TYPE);
                    }
                    else
                    {
                        wkbkContractNumber = getExcelCellValue(hssfRow, 0, STRING_FIELD_TYPE);
                    }

                    if (wkbkContractNumber.equalsIgnoreCase(contractNumber))
                    {
                        String insuredLastName = getExcelCellValue(hssfRow, 5, STRING_FIELD_TYPE);
                        if (!insuredLastName.equals(""))
                        {
                            ContractClientVO insuredContractClientVO = buildInsuredClient(hssfRow, insuredSource, operator, contractNumber);
                            ContractClientVO ownerContractClientVO   = buildOwnerClient(hssfRow, ownerSource, operator, checkOwner, contractNumber);

                            // Build SegmentVO with its attachments
                            SegmentVO baseSegmentVO = buildBaseSegmentVO(contractNumber, hssfRow, productStructureId, operator);

                            //  Attach insured and owner clients
                            baseSegmentVO.addContractClientVO(insuredContractClientVO);
                            baseSegmentVO.addContractClientVO(ownerContractClientVO);

                            boolean validateSuccessful = validateContract(baseSegmentVO, insuredContractClientVO, ownerContractClientVO, importResponses);

                            if (validateSuccessful)
                            {
                                saveContract(baseSegmentVO, importResponses);
                            }
                        }
                    }
                }
            }
        }

        return (ImportNewBusinessResponse[]) importResponses.toArray(new ImportNewBusinessResponse[importResponses.size()]);
    }

    /**
     * This method gets the value of a single Excel cell and converts it to the desired field type.  Dates are read in
     * as java.util.Dates and converted to the proper format defined in EDITDate.
     *
     * @param hssfRow                   Excel row to be read
     * @param index                     cell index (or column number) of cell to be read
     * @param desiredFieldType          type the field "should" be, not necessarily the type the field is in the
     *                                  spreadsheet.  For example, taxid (SSN) should be a string but it is defined as
     *                                  a number in the spreadsheet.
     *
     * @return  string containing the cell value.  Even numbers are returned as strings but are still valid numbers.
     */
    private String getExcelCellValue(HSSFRow hssfRow, int index, String desiredFieldType)
    {
        String string = "";

        HSSFCell hssfCell = hssfRow.getCell((short) index);

        if (hssfCell != null)
        {
            if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                if (desiredFieldType.equals(STRING_FIELD_TYPE))
                {
                    //  The cell will be read as a number in decimal format.  If it should be a string, we want to
                    //  get rid of the decimals.  First convert it to an int then to a string.
                    double numeric = hssfCell.getNumericCellValue();
                    int integer = new Double(numeric).intValue();
                    string = integer + "";
                }
                else if (desiredFieldType.equals(DATE_FIELD_TYPE))
                {
                    Date date = hssfCell.getDateCellValue();

                    if (date != null)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat(EDITDate.DATE_FORMAT);
                        string = sdf.format(date);
                    }
                }
                else if (desiredFieldType.equals(NUMERIC_FIELD_TYPE))
                {
                    string = hssfCell.getNumericCellValue() + "";
                }
            }
            else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING)
            {
                string = hssfCell.getStringCellValue();
            }
        }

        return string;
    }

    private ContractClientVO buildInsuredClient(HSSFRow hssfRow, String dataSource, String operator, String contractNumber) throws EDITContractException
    {
        long insuredClientDetailPK = 0;

        //  Insured client
        ClientDetailVO insuredClientDetailVO = buildInsuredClientDetailVO(hssfRow, dataSource, operator);

        Client clientComponent = new ClientComponent();

        if (dataSource.equalsIgnoreCase("Spreadsheet"))
        {
            try
            {
                insuredClientDetailPK = clientComponent.saveOrUpdateClient(insuredClientDetailVO, true);
                                        // true = bypass OFAC service connection which would fail and not add client
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace();

                String errorMessage = "Error Processing Client" + insuredClientDetailVO.getCorporateName();

                logToDatabase(errorMessage, null, insuredClientDetailVO);
                
                throw new EDITContractException(e.getMessage());
            }
        }
        else
        {
            insuredClientDetailPK = insuredClientDetailVO.getClientDetailPK();
        }

        ContractClientVO contractClientVO = null;

        if (clientAddedSuccessfully(insuredClientDetailPK))
        {
            ClientRoleVO insuredClientRoleVO = new ClientRoleVO();
            insuredClientRoleVO.setRoleTypeCT("Insured");
            insuredClientRoleVO.setClientDetailFK(insuredClientDetailPK);
            insuredClientRoleVO.setOverrideStatus("P");         // null not allowed in db, default
            insuredClientRoleVO.setReferenceID(contractNumber);

            long clientRolePK = 0;

            try
            {
                clientRolePK = checkForClientRole(insuredClientRoleVO, contractNumber);
            }
            catch(Exception e)
            {
              System.out.println(e);

                e.printStackTrace();

                String errorMessage = "Error Processing Client" + insuredClientDetailVO.getCorporateName();

                logToDatabase(errorMessage, null, insuredClientDetailVO);

                throw new EDITContractException(e.getMessage());
            }

            if (clientRolePK == 0)
            {
                ClientRole insuredClientRole  = new ClientRole(insuredClientRoleVO);

                insuredClientRole.save();

                clientRolePK = insuredClientRole.getPK();
            }

            contractClientVO = buildContractClient(hssfRow);
            contractClientVO.setClientRoleFK(clientRolePK);
        }

        return contractClientVO;
    }

    private ContractClientVO buildOwnerClient(HSSFRow hssfRow, 
                                              String dataSource, 
                                              String operator,
                                              boolean checkOwner,
                                              String contractNumber) throws EDITContractException
    {
        long ownerClientDetailPK = 0;

        ClientDetailVO ownerClientDetailVO = buildOwnerClientDetailVO(hssfRow, dataSource, operator, checkOwner);

        Client clientComponent = new ClientComponent();

        if (dataSource.equalsIgnoreCase("Spreadsheet"))
        {
            try
            {
                ownerClientDetailPK = clientComponent.saveOrUpdateClient(ownerClientDetailVO, true);
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace();
                
                String errorMessage = "Error Processing Client" + ownerClientDetailVO.getCorporateName();
                
                logToDatabase(errorMessage, null, ownerClientDetailVO);

            }
        }
        else
        {
            ownerClientDetailPK = ownerClientDetailVO.getClientDetailPK();
        }

        ContractClientVO contractClientVO = null;

        if (clientAddedSuccessfully(ownerClientDetailPK))
        {
            ClientRoleVO ownerClientRoleVO = new ClientRoleVO();
            ownerClientRoleVO.setRoleTypeCT("OWN");
            ownerClientRoleVO.setClientDetailFK(ownerClientDetailPK);
            ownerClientRoleVO.setOverrideStatus("P");         // null not allowed in db, default
            ownerClientRoleVO.setReferenceID(contractNumber);

            long clientRolePK = 0;

            try
            {
                clientRolePK = checkForClientRole(ownerClientRoleVO, contractNumber);
            }
            catch(Exception e)
            {
              System.out.println(e);

                e.printStackTrace();

                String errorMessage = "Error Processing Client" + ownerClientDetailVO.getCorporateName();

                logToDatabase(errorMessage, null, ownerClientDetailVO);
                
                throw new EDITContractException(e.getMessage());
            }

            if (clientRolePK == 0)
            {
                ClientRole ownerClientRole  = new ClientRole(ownerClientRoleVO);

                ownerClientRole.save();

                clientRolePK = ownerClientRole.getPK();
            }

            contractClientVO = buildContractClient(hssfRow);
            contractClientVO.setClientRoleFK(clientRolePK);
        }

        return contractClientVO;
    }

    private SegmentVO buildBaseSegmentVO(String contractNumber, HSSFRow hssfRow, String productStructureId, String operator)
    {
//        MasterVO masterVO = new MasterVO();                   // Individual Masters are automatically created when the segment is saved
//        masterVO.setMasterNumber(getExcelCellValue(hssfRow, 0));

        SegmentVO segmentVO = new SegmentVO();

        segmentVO.setSegmentPK(0);

        segmentVO.setContractNumber(contractNumber);

        // Default the Company Structure

        //   if no productStructureVOs, let it fail cuz will fail on validation anyway.  Want to know it couldn't find any product structures
        segmentVO.setProductStructureFK(Long.parseLong(productStructureId));

        //  Default segment name to Life
        segmentVO.setSegmentNameCT("Life");

        //  Default optionCodeCT to VL
        segmentVO.setOptionCodeCT("VL");

        String issueDate = getExcelCellValue(hssfRow, 4, DATE_FIELD_TYPE);

        segmentVO.setIssueDate(issueDate);
        segmentVO.setEffectiveDate(issueDate);
        segmentVO.setApplicationSignedDate(issueDate);

        String pointInScaleIndicator = getExcelCellValue(hssfRow, 51, STRING_FIELD_TYPE);
        if (pointInScaleIndicator.equals("1"))
        {
            segmentVO.setPointInScaleIndicator("Y");
        }
        else
        {
            segmentVO.setPointInScaleIndicator("N");
        }

        segmentVO.setIssueStateCT(getExcelCellValue(hssfRow, 52, STRING_FIELD_TYPE));
        if (segmentVO.getIssueStateCT() != null)
        {
            segmentVO.setPremiumTaxSitusOverrideCT(segmentVO.getIssueStateCT());
        }        

//        String applicationReceivedDate = getExcelCellValue(hssfRow, 54, DATE_FIELD_TYPE);
//        segmentVO.setApplicationReceivedDate(applicationReceivedDate);
        segmentVO.setApplicationReceivedDate(issueDate);
//        if (!applicationReceivedDate.equals(""))
//        {
//            String formattedApplicationReceivedDate = convertToFormattedDate(applicationReceivedDate);
//            segmentVO.setApplicationReceivedDate(formattedApplicationReceivedDate);
//        }

        String chargeDeductDivision = getExcelCellValue(hssfRow, 55, STRING_FIELD_TYPE);
        if (chargeDeductDivision != null && !chargeDeductDivision.equals(""))
        {
            segmentVO.setChargeDeductDivisionInd("Y");
        }
        else
        {
            segmentVO.setChargeDeductDivisionInd("N");
        }

        segmentVO.setWaiveFreeLookIndicator("N");           // set default
        segmentVO.setSegmentStatusCT("Pending");

        segmentVO.setCreationDate(new EDITDateTime().getFormattedDateTime());
        segmentVO.setCreationOperator(operator);

        //  LifeVO
        LifeVO lifeVO = buildLifeVO(hssfRow);
        segmentVO.addLifeVO(lifeVO);

        //  DepositsVO
        // If there is no entry in the 1035 Cash Value field (field 49), don't add deposits
        String ten35CashAmount = getExcelCellValue(hssfRow, 49, NUMERIC_FIELD_TYPE);
        if (! ten35CashAmount.equalsIgnoreCase(""))
        {
            DepositsVO depositsVO = buildDepositsVO(hssfRow, segmentVO);
            segmentVO.addDepositsVO(depositsVO);
        }

        return segmentVO;
    }

    private ClientDetailVO buildInsuredClientDetailVO(HSSFRow hssfRow, String dataSource, String operator)
    {
        String taxId = getExcelCellValue(hssfRow, 8, STRING_FIELD_TYPE);
        String taxIdType = "SocialSecurityNumber";

        ClientDetailVO insuredClientDetailVO;

        if (dataSource.equalsIgnoreCase("Spreadsheet"))
        {
            insuredClientDetailVO = new ClientDetailVO();

            String insuredLastName = getExcelCellValue(hssfRow, 5, STRING_FIELD_TYPE);
            String insuredFirstName = getExcelCellValue(hssfRow, 6, STRING_FIELD_TYPE);
            String insuredMiddleName = getExcelCellValue(hssfRow, 7, STRING_FIELD_TYPE);

            if (insuredLastName.length() > 30)
            {
                insuredLastName = insuredLastName.substring(0, 30);
            }

            if (insuredFirstName.length() > 15)
            {
                insuredFirstName = insuredFirstName.substring(0, 15);
            }

            if (insuredMiddleName.length() > 15)
            {
                insuredMiddleName = insuredMiddleName.substring(0, 15);
            }

            insuredClientDetailVO.setLastName(insuredLastName);
            insuredClientDetailVO.setFirstName(insuredFirstName);
            insuredClientDetailVO.setMiddleName(insuredMiddleName);
            if (!taxId.equals("0"))
            {
                insuredClientDetailVO.setTaxIdentification(taxId);
            }

            String birthDate = getExcelCellValue(hssfRow, 9, DATE_FIELD_TYPE);       // comes in as YYYYMMDD
            insuredClientDetailVO.setBirthDate(birthDate);

            insuredClientDetailVO.setTrustTypeCT("Individual");
            insuredClientDetailVO.setDateOfDeath(EDITDate.DEFAULT_MAX_DATE);
            insuredClientDetailVO.setProofOfDeathReceivedDate(EDITDate.DEFAULT_MAX_DATE);
            insuredClientDetailVO.setOperator(operator);
            insuredClientDetailVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            insuredClientDetailVO.setPrivacyInd("N");

            String genderCT = getExcelCellValue(hssfRow, 10, STRING_FIELD_TYPE);

            if (genderCT.equals("F"))
            {
                insuredClientDetailVO.setGenderCT("Female");
            }
            else if (genderCT.equals("M"))
            {
                insuredClientDetailVO.setGenderCT("Male");
            }

            insuredClientDetailVO.setStatusCT("Active");

            String streetAddress = getExcelCellValue(hssfRow, 27, STRING_FIELD_TYPE);

            String[] streetAddressTokenized = Util.fastTokenizer(streetAddress, ",");

            int numberOfAddressLines = streetAddressTokenized.length;

            String effectiveDate = getExcelCellValue(hssfRow, 4, DATE_FIELD_TYPE);

            //  Insured's primary address
            ClientAddressVO insuredPrimaryClientAddressVO = new ClientAddressVO();
            insuredPrimaryClientAddressVO.setAddressTypeCT("PrimaryAddress");
            insuredPrimaryClientAddressVO.setEffectiveDate(effectiveDate);
            if (numberOfAddressLines == 1)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                insuredPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
            }
            if (numberOfAddressLines == 2)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                if (streetAddressTokenized[1].length() > 35)
                {
                    streetAddressTokenized[1] = streetAddressTokenized[1].substring(0, 35);
                }

                insuredPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
                insuredPrimaryClientAddressVO.setAddressLine2(streetAddressTokenized[1]);
            }
            else if (numberOfAddressLines == 3)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                if (streetAddressTokenized[1].length() > 35)
                {
                    streetAddressTokenized[1] = streetAddressTokenized[1].substring(0, 35);
                }

                if (streetAddressTokenized[2].length() > 35)
                {
                    streetAddressTokenized[2] = streetAddressTokenized[2].substring(0, 35);
                }

                insuredPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
                insuredPrimaryClientAddressVO.setAddressLine2(streetAddressTokenized[1]);
                insuredPrimaryClientAddressVO.setAddressLine3(streetAddressTokenized[2]);
            }
            else if (numberOfAddressLines == 4)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                if (streetAddressTokenized[1].length() > 35)
                {
                    streetAddressTokenized[1] = streetAddressTokenized[1].substring(0, 35);
                }

                if (streetAddressTokenized[2].length() > 35)
                {
                    streetAddressTokenized[2] = streetAddressTokenized[2].substring(0, 35);
                }

                if (streetAddressTokenized[3].length() > 35)
                {
                    streetAddressTokenized[3] = streetAddressTokenized[3].substring(0, 35);
                }

                insuredPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
                insuredPrimaryClientAddressVO.setAddressLine2(streetAddressTokenized[1]);
                insuredPrimaryClientAddressVO.setAddressLine3(streetAddressTokenized[2]);
                insuredPrimaryClientAddressVO.setAddressLine3(streetAddressTokenized[3]);
            }

            String city = getExcelCellValue(hssfRow, 29, STRING_FIELD_TYPE);
            if (city.length() > 35)
            {
                city = city.substring(0, 35);
            }

            insuredPrimaryClientAddressVO.setCity(city);
            insuredPrimaryClientAddressVO.setStateCT(getExcelCellValue(hssfRow, 30, STRING_FIELD_TYPE));
            insuredPrimaryClientAddressVO.setZipCode(getExcelCellValue(hssfRow, 31, STRING_FIELD_TYPE));
            insuredPrimaryClientAddressVO.setZipCodePlacementCT("AfterCountry");

            String countryCT = getExcelCellValue(hssfRow, 32, STRING_FIELD_TYPE);

            if (countryCT.equals("USA"))
            {
                insuredPrimaryClientAddressVO.setCountryCT("USA");     // Only concerned with USA at this time
            }

            insuredPrimaryClientAddressVO.setTerminationDate(EDITDate.DEFAULT_MAX_DATE);

            // Insured's business address
//            ClientAddressVO insuredBusinessClientAddressVO = new ClientAddressVO();
//            insuredBusinessClientAddressVO.setAddressTypeCT("BusinessAddress");
//            insuredBusinessClientAddressVO.setStateCT(getExcelCellValue(hssfRow, 33, STRING_FIELD_TYPE));
//            insuredBusinessClientAddressVO.setZipCode(getExcelCellValue(hssfRow, 34, STRING_FIELD_TYPE));
//            insuredBusinessClientAddressVO.setZipCodePlacementCT("AfterCountry");

            // Tax Information
            TaxInformationVO taxInformationVO = new TaxInformationVO();
            taxInformationVO.setTaxIdTypeCT(taxIdType);

            //  Attach VOs to insuredClientDetailVO
            insuredClientDetailVO.addClientAddressVO(insuredPrimaryClientAddressVO);
//            insuredClientDetailVO.addClientAddressVO(insuredBusinessClientAddressVO);
            insuredClientDetailVO.addTaxInformationVO(taxInformationVO);
        }
        else
        {
            insuredClientDetailVO = getClientFromDB(taxId, taxIdType, "insured", hssfRow);
        }

        return insuredClientDetailVO;
    }

    private ClientDetailVO buildOwnerClientDetailVO(HSSFRow hssfRow, String dataSource, String operator, boolean checkOwner)
    {
        String taxId = getExcelCellValue(hssfRow, 38, STRING_FIELD_TYPE);
        String taxIdType = getExcelCellValue(hssfRow, 37, STRING_FIELD_TYPE);
        String ownerCode = getExcelCellValue(hssfRow, 35, STRING_FIELD_TYPE);

        ClientDetailVO ownerClientDetailVO = null;

        if (checkOwner);
        {
            ownerClientDetailVO = getClientFromDB(taxId, taxIdType, "owner", hssfRow);
            if (ownerClientDetailVO  != null)
            {
                dataSource = "Retrieved";
            }
        }

        if (dataSource.equalsIgnoreCase("Spreadsheet"))
        {
            ownerClientDetailVO = new ClientDetailVO();

            String ownerName = getExcelCellValue(hssfRow, 36, STRING_FIELD_TYPE);
            String streetAddress = getExcelCellValue(hssfRow, 40, STRING_FIELD_TYPE);

            if (ownerCode.equalsIgnoreCase("I"))
            {
                String[] ownerNameTokenized = Util.fastTokenizer(ownerName, ",");

                if (ownerNameTokenized[2].length() > 30)
                {
                    ownerNameTokenized[2] = ownerNameTokenized[2].substring(0, 30);
                }

                if (ownerNameTokenized[0].length() > 15)
                {
                    ownerNameTokenized[0] = ownerNameTokenized[0].substring(0, 15);
                }

                if (ownerNameTokenized[1].length() > 15)
                {
                    ownerNameTokenized[1] = ownerNameTokenized[1].substring(0, 15);
                }

                ownerClientDetailVO.setLastName(ownerNameTokenized[2]);
                ownerClientDetailVO.setFirstName(ownerNameTokenized[0]);
                ownerClientDetailVO.setMiddleName(ownerNameTokenized[1]);
            }
            else
            {
                if (ownerName.length() > 60)
                {
                    ownerName = ownerName.substring(0, 60);
                }

                ownerClientDetailVO.setCorporateName(ownerName);
            }

            if (ownerCode.equalsIgnoreCase("C"))
            {
                ownerClientDetailVO.setTrustTypeCT("Corporate");
            }
            else if (ownerCode.equalsIgnoreCase("D"))
            {
                ownerClientDetailVO.setTrustTypeCT("Custodian");
            }
            else if (ownerCode.equalsIgnoreCase("I"))
            {
                ownerClientDetailVO.setTrustTypeCT("Individual");
                ownerClientDetailVO.setDateOfDeath(EDITDate.DEFAULT_MAX_DATE);
                ownerClientDetailVO.setProofOfDeathReceivedDate(EDITDate.DEFAULT_MAX_DATE);
                ownerClientDetailVO.setPrivacyInd("N");
            }
            else if (ownerCode.equalsIgnoreCase("L"))
            {
                ownerClientDetailVO.setTrustTypeCT("LLC");
            }
            else if (ownerCode.equalsIgnoreCase("R"))
            {
                ownerClientDetailVO.setTrustTypeCT("Reinsurer");
            }
            else if (ownerCode.equalsIgnoreCase("T"))
            {
                ownerClientDetailVO.setTrustTypeCT("Trust");
            }

            ownerClientDetailVO.setOperator(operator);
            ownerClientDetailVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

            //  if the owner is anything other than an Individual, set the gender to "Not Applicable"
            if (!ownerCode.equalsIgnoreCase("I"))
            {
                ownerClientDetailVO.setGenderCT("NotApplicable");
            }

            if (!taxId.equals("0"))
            {
                ownerClientDetailVO.setTaxIdentification(taxId);
            }

            ownerClientDetailVO.setStatusCT("Active");

            TaxInformationVO taxInformationVO = new TaxInformationVO();
            if (taxIdType.equalsIgnoreCase("T"))
            {
                taxInformationVO.setTaxIdTypeCT("CorporateTaxId");
            }
            else if (taxIdType.equalsIgnoreCase("S"))
            {
                taxInformationVO.setTaxIdTypeCT("SocialSecurityNumber");
            }

            String[] streetAddressTokenized = Util.fastTokenizer(streetAddress, ",");

            int numberOfAddressLines = streetAddressTokenized.length;

            String effectiveDate = getExcelCellValue(hssfRow, 4, DATE_FIELD_TYPE);

            //  Owners's primary address
            ClientAddressVO ownerPrimaryClientAddressVO = new ClientAddressVO();
            ownerPrimaryClientAddressVO.setAddressTypeCT("PrimaryAddress");
            ownerPrimaryClientAddressVO.setEffectiveDate(effectiveDate);
            if (numberOfAddressLines == 1)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                ownerPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
            }
            if (numberOfAddressLines == 2)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                if (streetAddressTokenized[1].length() > 35)
                {
                    streetAddressTokenized[1] = streetAddressTokenized[1].substring(0, 35);
                }

                ownerPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
                ownerPrimaryClientAddressVO.setAddressLine2(streetAddressTokenized[1]);
            }
            else if (numberOfAddressLines == 3)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                if (streetAddressTokenized[1].length() > 35)
                {
                    streetAddressTokenized[1] = streetAddressTokenized[1].substring(0, 35);
                }

                if (streetAddressTokenized[2].length() > 35)
                {
                    streetAddressTokenized[2] = streetAddressTokenized[2].substring(0, 35);
                }

                ownerPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
                ownerPrimaryClientAddressVO.setAddressLine2(streetAddressTokenized[1]);
                ownerPrimaryClientAddressVO.setAddressLine3(streetAddressTokenized[2]);
            }
            else if (numberOfAddressLines == 4)
            {
                if (streetAddressTokenized[0].length() > 35)
                {
                    streetAddressTokenized[0] = streetAddressTokenized[0].substring(0, 35);
                }

                if (streetAddressTokenized[1].length() > 35)
                {
                    streetAddressTokenized[1] = streetAddressTokenized[1].substring(0, 35);
                }

                if (streetAddressTokenized[2].length() > 35)
                {
                    streetAddressTokenized[2] = streetAddressTokenized[2].substring(0, 35);
                }

                if (streetAddressTokenized[3].length() > 35)
                {
                    streetAddressTokenized[3] = streetAddressTokenized[3].substring(0, 35);
                }

                ownerPrimaryClientAddressVO.setAddressLine1(streetAddressTokenized[0]);
                ownerPrimaryClientAddressVO.setAddressLine2(streetAddressTokenized[1]);
                ownerPrimaryClientAddressVO.setAddressLine3(streetAddressTokenized[2]);
                ownerPrimaryClientAddressVO.setAddressLine3(streetAddressTokenized[3]);
            }

            ownerPrimaryClientAddressVO.setCity(getExcelCellValue(hssfRow, 41, STRING_FIELD_TYPE));
            ownerPrimaryClientAddressVO.setStateCT(getExcelCellValue(hssfRow, 42, STRING_FIELD_TYPE));
            ownerPrimaryClientAddressVO.setZipCode(getExcelCellValue(hssfRow, 43, STRING_FIELD_TYPE));
            ownerPrimaryClientAddressVO.setCountryCT(getExcelCellValue(hssfRow, 44, STRING_FIELD_TYPE));
            ownerPrimaryClientAddressVO.setZipCodePlacementCT("AfterCountry");

            ownerPrimaryClientAddressVO.setTerminationDate(EDITDate.DEFAULT_MAX_DATE);

            //  Attach VOs to ownerClientDetailVO
            ownerClientDetailVO.addClientAddressVO(ownerPrimaryClientAddressVO);
            ownerClientDetailVO.addTaxInformationVO(taxInformationVO);
        }
        else if (!dataSource.equalsIgnoreCase("Retrieved"))
        {
            ownerClientDetailVO = getClientFromDB(taxId, taxIdType, "owner", hssfRow);
        }

        return ownerClientDetailVO;
    }

    private ContractClientVO buildContractClient(HSSFRow hssfRow)
    {
        ContractClientVO contractClientVO = new ContractClientVO();

        String ratedGenderCT = getExcelCellValue(hssfRow, 11, STRING_FIELD_TYPE);
        String underwritingClassCT = getExcelCellValue(hssfRow, 21, STRING_FIELD_TYPE);
        String classCT = getExcelCellValue(hssfRow, 22, STRING_FIELD_TYPE);
        String tablePct = getExcelCellValue(hssfRow, 23, NUMERIC_FIELD_TYPE);

        String issueDate = getExcelCellValue(hssfRow, 4, DATE_FIELD_TYPE);

        if (ratedGenderCT.equals("F"))
        {
            contractClientVO.setRatedGenderCT("Female");
        }
        else if (ratedGenderCT.equals("M"))
        {
            contractClientVO.setRatedGenderCT("Male");
        }
        else if (ratedGenderCT.equals("U"))
        {
            contractClientVO.setRatedGenderCT("Unisex");
        }

        if (underwritingClassCT.equals("PF"))
        {
            contractClientVO.setUnderwritingClassCT("Preferred");
        }
        else if (underwritingClassCT.equals("MI"))
        {
            contractClientVO.setUnderwritingClassCT("Standard");
        }
        else if (underwritingClassCT.equals("GI"))
        {
            contractClientVO.setUnderwritingClassCT("Standard");
        }
        else if (underwritingClassCT.equals("SI"))
        {
            contractClientVO.setUnderwritingClassCT("Standard");
        }

        if (classCT.equals("N"))
        {
            contractClientVO.setClassCT("NonSmoker");
        }
        else if (classCT.equals("U"))
        {
            contractClientVO.setClassCT("Unismoke");
        }
        else if (classCT.equals("S"))
        {
            contractClientVO.setClassCT("Smoker");
        }

        if (Util.isANumber(tablePct))
        {
            EDITBigDecimal ebdTablePct = new EDITBigDecimal(tablePct, 4);
            EDITBigDecimal percentExtra = EDITBigDecimal.round(ebdTablePct.divideEditBigDecimal("100"), 4);   // divide percentage by 100 to put fraction in db
            contractClientVO.setPercentExtra(percentExtra.getBigDecimal());
            contractClientVO.setPercentExtraDur(20);
        }

        String flatExtraString = getExcelCellValue(hssfRow, 24, NUMERIC_FIELD_TYPE);
        String flatExtraDurString = getExcelCellValue(hssfRow, 26, STRING_FIELD_TYPE);

        if (flatExtraString.equals(""))
        {
            flatExtraString = "0";
        }

        if (flatExtraDurString.equals(""))
        {
            flatExtraDurString = "0";
        }

        EDITBigDecimal flatExtra = new EDITBigDecimal(flatExtraString);

        contractClientVO.setFlatExtra(flatExtra.getBigDecimal());

        contractClientVO.setFlatExtraDur(Integer.parseInt(flatExtraDurString));

        contractClientVO.setEffectiveDate(issueDate);

        contractClientVO.setOverrideStatus("P");

        return contractClientVO;
    }

    private DepositsVO buildDepositsVO(HSSFRow hssfRow, SegmentVO segmentVO)
    {
        // If there is no entry in the 1035 Cash Value field (field 49), don't add deposits
        String ten35CashAmount = getExcelCellValue(hssfRow, 49, NUMERIC_FIELD_TYPE);
        EDITBigDecimal anticipatedAmount = new EDITBigDecimal();
        if (Util.isANumber(ten35CashAmount))
        {
            anticipatedAmount = new EDITBigDecimal(ten35CashAmount);
        }

        DepositsVO depositsVO = new DepositsVO();

        depositsVO.setOldPolicyNumber(getExcelCellValue(hssfRow, 46, STRING_FIELD_TYPE));

        String exchangePolicyEffectiveDate = getExcelCellValue(hssfRow, 47, DATE_FIELD_TYPE);
        if (!exchangePolicyEffectiveDate.equals(""))
        {
            String formattedExchangePolicyEffectiveDate = convertToFormattedDate(exchangePolicyEffectiveDate);
            depositsVO.setExchangePolicyEffectiveDate(formattedExchangePolicyEffectiveDate);
        }

        depositsVO.setOldCompany(getExcelCellValue(hssfRow, 48, STRING_FIELD_TYPE));

        String priorCompanyMECStatusCT = getExcelCellValue(hssfRow, 50, STRING_FIELD_TYPE);

        if (priorCompanyMECStatusCT.equals("N"))
        {
            depositsVO.setPriorCompanyMECStatusCT("NonMec");
        }
        else if (priorCompanyMECStatusCT.equals("M"))
        {
            depositsVO.setPriorCompanyMECStatusCT("Mec");
        }



        if (segmentVO.getPointInScaleIndicator().equalsIgnoreCase("Y"))
        {
            if (anticipatedAmount.isGT(new EDITBigDecimal()))
            {
                depositsVO.setDepositTypeCT("1035Exchange");
            }

            depositsVO.setAnticipatedAmount(anticipatedAmount.getBigDecimal());
            depositsVO.setExchangeDuration(1);
        }

        return depositsVO;
    }

    private LifeVO buildLifeVO(HSSFRow hssfRow)
    {
        LifeVO lifeVO = new LifeVO();

        EDITBigDecimal faceAmount = new EDITBigDecimal(getExcelCellValue(hssfRow, 17, NUMERIC_FIELD_TYPE));
        lifeVO.setFaceAmount(faceAmount.getBigDecimal());

        String mecStatusCT          = getExcelCellValue(hssfRow, 12, STRING_FIELD_TYPE);
        String deathBenefitOptionCT = getExcelCellValue(hssfRow, 19, STRING_FIELD_TYPE);
        String option7702CT         = getExcelCellValue(hssfRow, 20, STRING_FIELD_TYPE);


        if (mecStatusCT.equals("N"))
        {
            lifeVO.setMECStatusCT("NonMec");
        }
        else if (mecStatusCT.equals("M"))
        {
            lifeVO.setMECStatusCT("Mec");
        }

        if (deathBenefitOptionCT.equals("A"))
        {
            lifeVO.setDeathBenefitOptionCT("Level");
        }
        else if (deathBenefitOptionCT.equals("B"))
        {
            lifeVO.setDeathBenefitOptionCT("Increasing");
        }

        if (option7702CT.equals("C"))
        {
            lifeVO.setOption7702CT("CVAT");
        }
        else if (option7702CT.equals("G"))
        {
            lifeVO.setOption7702CT("Guideline");
        }

        return lifeVO;
    }

    /**
     * Converts a date string from yyyymmdd to yyyy/mm/dd
     *
     * @param yyyymmdd                  String containing a date in the format of yyyy/mm/dd
     *
     * @return  String in the format of yyyy/mm/dd
     */
    private String convertToFormattedDate(String yyyymmdd)
    {
        EDITDate date = new EDITDate(yyyymmdd);

        return date.getFormattedDate();
    }

    /**
     * Sets the segment's issueAge based on the effective date and the birth date
     *
     * @param baseSegmentVO
     * @param contractClientVO
     */
//    private void setIssueAge(SegmentVO segmentVO, ContractClientVO contractClientVO)
//    {
//        String effectiveDate = segmentVO.getEffectiveDate();
//
//        long clientRolePK = contractClientVO.getClientRoleFK();
//        ClientRole clientRole = new ClientRole(clientRolePK);
//        ClientDetail clientDetail = clientRole.getClientDetail();
//        ClientDetailVO clientDetailVO = (ClientDetailVO) clientDetail.getVO();
//        String birthDate = clientDetailVO.getBirthDate();
//
//        Contract contractComponent = new ContractComponent();
//        contractClientVO.setIssueAge(contractComponent.calculateIssueAge(birthDate, effectiveDate));
//    }

    private boolean checkForDuplicateContractNumber(String contractNumber)
    {
        boolean contractNumberFound = false;

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        SegmentVO segmentVO = contractLookup.composeSegmentVO(contractNumber, new ArrayList());

        if (segmentVO != null)
        {
            contractNumberFound = true;
        }

        return contractNumberFound;
    }

    private boolean checkForDuplicateClient(String taxId, String taxIdType, String clientType, HSSFRow hssfRow)
    {
        boolean clientExists = false;

        ClientDetailVO clientDetailVO = getClientFromDB(taxId, taxIdType, clientType, hssfRow);

        if (clientDetailVO != null)
        {
            clientExists = true;
        }

        return clientExists;
    }

    private String getClientName(String taxId, String taxIdType, String clientType, HSSFRow hssfRow)
    {
        String clientName = "";

        ClientDetailVO clientDetailVO = getClientFromDB(taxId, taxIdType, clientType, hssfRow);

        if (clientDetailVO != null)
        {
            if (clientDetailVO.getCorporateName() == null || clientDetailVO.getCorporateName().equals(""))
            {
                clientName = Util.initString(clientDetailVO.getLastName(), "") + ", " +
                             Util.initString(clientDetailVO.getFirstName(), "") + " " +
                             Util.initString(clientDetailVO.getMiddleName(), "");
            }
            else
            {
                clientName = clientDetailVO.getCorporateName();
            }
        }

        return clientName;
    }

    private ClientDetailVO getClientFromDB(String taxId, String taxIdType, String clientType, HSSFRow hssfRow)
    {
        List voExclusionList = new ArrayList();
        voExclusionList.add(ClientAddressVO.class);
        voExclusionList.add(ClientRoleVO.class);
        voExclusionList.add(PreferenceVO.class);

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByTaxId(taxId, true, voExclusionList);

        ClientDetailVO clientDetailToReturn = null;

        if (clientDetailVOs != null)
        {
            if (taxIdType.equalsIgnoreCase("T"))
            {
                taxIdType = "CorporateTaxId";
            }
            else if (taxIdType.equalsIgnoreCase("S"))
            {
                taxIdType = "SocialSecurityNumber";
            }

            for (int i = 0; i < clientDetailVOs.length; i++)
            {
                String trustType = null;
                if (clientType.equalsIgnoreCase("insured"))
                {
                    trustType = "Individual";
                }
                else
                {
                    String ownerCode = getExcelCellValue(hssfRow, 35, STRING_FIELD_TYPE);
                    if (ownerCode.equalsIgnoreCase("C"))
                    {
                        trustType = "Corporate";
                    }
                    else if (ownerCode.equalsIgnoreCase("D"))
                    {
                        trustType = "Custodian";
                    }
                    else if (ownerCode.equalsIgnoreCase("I"))
                    {
                        trustType = "Individual";
                    }
                    else if (ownerCode.equalsIgnoreCase("L"))
                    {
                        trustType = "LLC";
                    }
                    else if (ownerCode.equalsIgnoreCase("R"))
                    {
                        trustType = "Reinsurer";
                    }
                    else if (ownerCode.equalsIgnoreCase("T"))
                    {
                        trustType = "Trust";
                    }
                }

                if (clientDetailVOs[i].getTrustTypeCT().equalsIgnoreCase(trustType))
                {
                    TaxInformationVO[] taxInfoVO = clientDetailVOs[i].getTaxInformationVO();
                    if (taxInfoVO != null && taxInfoVO.length > 0)
                    {
                        if (taxInfoVO[0].getTaxIdTypeCT().equalsIgnoreCase(taxIdType))
                        {
                            clientDetailToReturn = clientDetailVOs[i];
                        }
                    }
                }
            }
        }

        return clientDetailToReturn;
    }

    private long checkForClientRole(ClientRoleVO clientRoleVO, String contractNumber) throws Exception
    {
        long clientDetailFK = clientRoleVO.getClientDetailFK();
        String roleType = clientRoleVO.getRoleTypeCT();
        String overrideStatus = clientRoleVO.getOverrideStatus();

        role.business.Lookup roleLookup = new role.component.LookupComponent();

        ClientRoleVO[] existingClientRoleVO = roleLookup.getRoleByRoleTypeClientDetailFKStatusReferenceID(roleType, clientDetailFK, overrideStatus, contractNumber);

        long clientRolePK = 0;

        if (existingClientRoleVO != null && existingClientRoleVO.length > 0)
        {
            clientRolePK = existingClientRoleVO[0].getClientRolePK();
        }

        return clientRolePK;
    }

    /**
     * Validates the contents of the contract by using the editing scripts.  Response messages (errors only) are logged.
     *
     * @param segmentVO                         Segment containing contract info
     * @param insuredContractClientVO           Insured client
     * @param ownerContractClientVO             Owner client
     * @param importResponses                   Informational messages about the failure of the validation
     *
     * @return boolean denoting whether validation succeeded or not.  True if no errors, false if errors.
     */
    private boolean validateContract(SegmentVO segmentVO, ContractClientVO insuredContractClientVO,
                                     ContractClientVO ownerContractClientVO, List importResponses)
    {
        boolean validateSuccessful = true;

        ValidationVO[] validationVOs = null;
        SPOutputVO spOutputVO        = null;

        //  Put the segmentVO and the clientDetailVOs (and its clientRoleVOs) into a quoteVO to allow for validation
        QuoteVO quoteVO = new QuoteVO();

        quoteVO.addSegmentVO(segmentVO);

        ClientRole insuredClientRole = new ClientRole(insuredContractClientVO.getClientRoleFK());
        ClientRole ownerClientRole   = new ClientRole(ownerContractClientVO.getClientRoleFK());

        ClientDetailVO insuredClientDetailVO = (ClientDetailVO) insuredClientRole.get_ClientDetail().getVO();
        ClientDetailVO ownerClientDetailVO = (ClientDetailVO) ownerClientRole.get_ClientDetail().getVO();

        insuredClientDetailVO.addClientRoleVO((ClientRoleVO) insuredClientRole.getVO());
        ownerClientDetailVO.addClientRoleVO((ClientRoleVO) ownerClientRole.getVO());

        quoteVO.addClientDetailVO(insuredClientDetailVO);
        quoteVO.addClientDetailVO((ownerClientDetailVO));


        //  Validate
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        try
        {
            spOutputVO = contractComponent.validateQuote(quoteVO, "NewBusSave");

            if (spOutputVO.getValidationVOCount() > 0)
            {
                validateSuccessful = false;

                String errorMessage = "Error validating contract number " + segmentVO.getContractNumber() + ": ";

                validationVOs = spOutputVO.getValidationVO();

                for (int j = 0; j < spOutputVO.getValidationVOCount(); j++)
                {
                    ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(segmentVO.getContractNumber(), errorMessage + validationVOs[j].getMessage() + ". See log files for details.");
                    importResponses.add(importNBResponse);

                    LogEvent logEvent = new LogEvent(errorMessage, new Throwable(validationVOs[j].getMessage()));

                    Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

                    logger.error(logEvent);

                    Log.logGeneralExceptionToDatabase(errorMessage, new Exception(validationVOs[j].getMessage()));
                }
            }
        }
        catch (Exception e)
        {
            String errorMessage = "Error validating contract number " + segmentVO.getContractNumber() + ".";

            ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(segmentVO.getContractNumber(), errorMessage + " See log files.");
            importResponses.add(importNBResponse);

            LogEvent logEvent = new LogEvent(errorMessage, e);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(logEvent);

            Log.logGeneralExceptionToDatabase(errorMessage, e);

            logToDatabase(errorMessage, segmentVO , null);
        }

        return validateSuccessful;
    }

    /**
     * Saves the contract to the database.  Response messages (errors and successes) are logged.
     *
     * @param segmentVO                 Segment containing contract info
     * @param importResponses           Informational messages about the success or failure of the save
     */
    private void saveContract(SegmentVO segmentVO, List importResponses)
    {
        try
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();

            contractComponent.saveSegment(segmentVO, "", false, null);

            ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(segmentVO.getContractNumber(), "Contract number " + segmentVO.getContractNumber() + " successfully imported as new business");
            importResponses.add(importNBResponse);
        }
        catch (Exception e)
        {
            String errorMessage = "Error importing contract number " + segmentVO.getContractNumber() + " as new business.";

            ImportNewBusinessResponse importNBResponse = new ImportNewBusinessResponse(segmentVO.getContractNumber(), errorMessage + " See log files.");
            importResponses.add(importNBResponse);

            LogEvent logEvent = new LogEvent(errorMessage, e);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(logEvent);

            Log.logGeneralExceptionToDatabase(errorMessage, e);

            logToDatabase(errorMessage,segmentVO, null);
        }
    }

    private boolean clientAddedSuccessfully(long insuredDetailPK)
    {
        boolean clientAddedSuccessfully = false;

        client.business.Lookup clientLookup = new client.component.LookupComponent();
        ClientDetailVO[] clientDetailVOs = clientLookup.findByClientPK(insuredDetailPK, false, new ArrayList());
        if (clientDetailVOs != null && clientDetailVOs.length > 0)
        {
            clientAddedSuccessfully = true;
        }

        return clientAddedSuccessfully;
    }
    
    
    /**
     *
     *
     * Logging to database
     * @param message
     * @param segment
     * @param clientDetail
     */
    
    
     private void logToDatabase(String message,SegmentVO segment, ClientDetailVO clientDetail)
    {
        String companyName = "";
        String groupNumber ="";
        String contractNumber="";
        String clientName ="";
        EDITMap columnInfo = new EDITMap();

        if(segment != null)
        {
            Company company = Company.findByProductStructurePK(segment.getProductStructureFK());
            companyName = company.getCompanyName();
            contractNumber = segment.getContractNumber();
            ContractGroup contractGroup = ContractGroup.findByPK(segment.getContractGroupFK());
            groupNumber = contractGroup.getContractGroupNumber();
        }

        if(clientDetail != null)
        {
            String lastName = clientDetail.getLastName();
            String firstName = clientDetail.getFirstName();
            String corpaorateName = clientDetail.getCorporateName();
            if(lastName != null)
            {
                clientName = lastName+","+firstName;
            }
            else
            {
                clientName = corpaorateName;
            }

        }
   
        if (message == null)
        {
            message = "NewBusiness Import Job Errored:";
        }

        columnInfo.put("CompanyName", companyName);
        columnInfo.put("GroupNumber", groupNumber);
        columnInfo.put("ContractNumber", contractNumber);
        columnInfo.put("ClientName", clientName);

        Log.logToDatabase(Log.NEW_BUSINESS_IMPORT, message, columnInfo);
    }
    
    
}
