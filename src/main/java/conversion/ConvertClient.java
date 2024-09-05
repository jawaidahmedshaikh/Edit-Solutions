/*
 * 
 * User: cgleason
 * Date: Jan 26, 2007
 * Time: 11:45:30 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package conversion;

import edit.common.vo.*;
import edit.common.*;
import edit.services.db.*;

import java.util.*;
import java.sql.*;

import fission.utility.*;

public class ConvertClient
{
    CRUD crud;
    Connection clientConn;
    Connection contractConn;
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    public ConvertClient(Connection clientConn, Connection contractConn, CRUD crud)
    {
        this.clientConn = clientConn;
        this.contractConn = contractConn;
        this.crud = crud;

        //Use this boolean to Insert rows with Keys
        crud.setRestoringRealTime(true);
    }


    public void convertClientDetail(HashMap countTable) throws Exception
    {
        int countIn = 0;
        int countOut = 0;
        int BAcountOut = 0;
        int TIcountOut = 0;
        int commitCount = 0;

        countTable.put("CAcountIn", countIn);
        countTable.put("CAcountOut", countOut);
        countTable.put("PRcountIn", countIn);
        countTable.put("PRcountOut", countOut);

        ClientDetailVO clientDetailVO = null;
        TaxInformationVO taxInformationVO = null;
        TaxProfileVO taxProfileVO = null;

        Statement s = clientConn.createStatement();

        String sql = "SELECT * FROM ClientDetail";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
//            if (commitCount == 0 )
//            {
                crud.startTransaction();
//            }

            countIn++;
            clientDetailVO = new ClientDetailVO();
            long clientDetailPK = new Long(rs.getLong("ClientDetailPK"));

            clientDetailVO.setClientDetailPK(clientDetailPK);
            clientDetailVO.setClientIdentification(Util.initString((String)rs.getString("ClientIdentification"), null));
            clientDetailVO.setTaxIdentification(Util.initString((String)rs.getString("TaxIdentification"), null));

            String employeeId = Util.initString((String)rs.getString("EmployeeIdentification"), null);
            if (employeeId != null && employeeId.equals(""))
            {
                employeeId = null;
            }

            clientDetailVO.setLastName(Util.initString((String)rs.getString("LastName"), null));

            String firstName = Util.initString((String)rs.getString("FirstName"), null);
            if (firstName != null && firstName.equals(""))
            {
                firstName = null;
            }
            clientDetailVO.setFirstName(firstName);

            String middleName = Util.initString((String)rs.getString("MiddleName"), null);
            if (middleName != null && middleName.equals(""))
            {
                middleName = null;
            }
            clientDetailVO.setMiddleName(middleName);

            String namePrefix = Util.initString((String)rs.getString("NamePrefix"), null);
            if (namePrefix != null && namePrefix.equals(""))
            {
                namePrefix = null;
            }
            clientDetailVO.setNamePrefix(namePrefix);

            String nameSuffix = Util.initString((String)rs.getString("NameSuffix"), null);
            if (nameSuffix != null && nameSuffix.equals(""))
            {
                nameSuffix = null;
            }
            clientDetailVO.setNameSuffix(nameSuffix);

            String corpName = Util.initString((String)rs.getString("CorporateName"), null);
            if (corpName != null && corpName.equals(""))
            {
                corpName = null;
            }
            clientDetailVO.setCorporateName(corpName);

            String birthDate = Util.initString((String)rs.getString("BirthDate"), null);
            if (birthDate != null)
            {
                birthDate = CommonDatabaseConversionFunctions.convertDate(birthDate);
                if (birthDate != null)
                {
                    if (birthDate.equals("196/05/23"))
                    {
                        birthDate = "1960/05/23";
                        System.out.println("Invalid BirthDate converted to 1960/05/23 from 196/05/23 for client key = " + clientDetailPK);
                    }
                    if (birthDate.equals("60/06/04"))
                    {
                        birthDate = "1960/06/04";
                        System.out.println("Invalid BirthDate converted to 1960/06/04 from 60/06/04 for client key = " + clientDetailPK);
                    }
                    if (birthDate.equals("1964/2/2"))
                    {
                        birthDate = "1964/02/02";
                        System.out.println("Invalid BirthDate converted to 1964/02/02 from 1964/2/2 for client key = " + clientDetailPK);
                    }
                }
                clientDetailVO.setBirthDate(birthDate);
            }

            String maidenName = Util.initString((String)rs.getString("MothersMaidenName"), null);
            if (maidenName != null && maidenName.equals(""))
            {
                maidenName = null;
            }
            clientDetailVO.setMothersMaidenName(maidenName);

            String occupation = Util.initString((String)rs.getString("Occupation"), null);
            if (occupation != null && occupation.equals(""))
            {
                occupation = null;

            }
            clientDetailVO.setOccupation(occupation);

            String dateOfDeath = Util.initString((String)rs.getString("DateOFDeath"), null);
            if (dateOfDeath != null)
            {
                dateOfDeath = CommonDatabaseConversionFunctions.convertDate(dateOfDeath);
                if (dateOfDeath != null)
                {
                    if (dateOfDeath.equals("200/09/13"))
                    {
                        dateOfDeath = "2003/09/13";
                        System.out.println("Invalid DateOfDeath converted to 2003/09/13 from 200/09/13 for client key = " + clientDetailPK);
                    }

                    if (dateOfDeath.equals("2005/91/03"))
                    {
                        dateOfDeath = "2005/12/03";
                        System.out.println("Invalid DateOfDeath converted to 2005/12/03 from 2005/91/03 for client key = " + clientDetailPK);
                    }
                }
                clientDetailVO.setDateOfDeath(dateOfDeath);
            }

            clientDetailVO.setOperator(Util.initString((String)rs.getString("Operator"), null));

            String maintDateTime = Util.initString((String)rs.getString("MaintDateTime"), null);
            maintDateTime = CommonDatabaseConversionFunctions.checkMaintDateTimeFoClient(maintDateTime);
            clientDetailVO.setMaintDateTime(maintDateTime);

            Integer codeTableKey = new Integer(rs.getInt("GenderCT"));
            String gender = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            clientDetailVO.setGenderCT(gender);

            codeTableKey = new Integer(rs.getInt("TrustTypeCT"));
            String trustType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            clientDetailVO.setTrustTypeCT(trustType);

            //create TaxInformation and TaxProfile
            codeTableKey = new Integer(rs.getInt("TaxIdTypeCT"));
            String taxIdType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);

            if (taxIdType != null)
            {
                taxInformationVO = new TaxInformationVO();
                taxInformationVO.setTaxInformationPK(CRUD.getNextAvailableKey());
                taxInformationVO.setClientDetailFK(clientDetailPK);
                taxInformationVO.setTaxIdTypeCT(taxIdType);

                codeTableKey = new Integer(rs.getInt("ProofOfAgeIndCT"));
                String proofOfAge = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                taxInformationVO.setProofOfAgeIndCT(proofOfAge);

                codeTableKey = new Integer(rs.getInt("MaritalStatusCT"));
                String maritalStatus = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                taxInformationVO.setMaritalStatusCT(maritalStatus);

                codeTableKey = new Integer(rs.getInt("StateOfBirthCT"));
                String stateOfBirth = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                taxInformationVO.setStateOfBirthCT(stateOfBirth);

                codeTableKey = new Integer(rs.getInt("CountryOfBirthCT"));
                String countryOfBirth = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                taxInformationVO.setCountryOfBirthCT(countryOfBirth);

                codeTableKey = new Integer(rs.getInt("CitizenshipIndCT"));
                String citizenshipInd = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                taxInformationVO.setCitizenshipIndCT(citizenshipInd);

                //createTaxProfile
                taxProfileVO = new TaxProfileVO();
                taxProfileVO.setTaxProfilePK(CRUD.getNextAvailableKey());
                taxProfileVO.setTaxInformationFK(taxInformationVO.getTaxInformationPK());

                codeTableKey = new Integer(rs.getInt("TaxFilingStatusCT"));
                String taxFilingStatus = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
                taxProfileVO.setTaxFilingStatusCT(taxFilingStatus);

                taxInformationVO.addTaxProfileVO(taxProfileVO);
                clientDetailVO.addTaxInformationVO(taxInformationVO);
                TIcountOut++;

                getPreferenceInfoFromPayee(clientDetailVO, countTable);
            }

            //create BankInformation
            codeTableKey = new Integer(rs.getInt("BankAccountTypeCT"));
            String bankAccountType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            if (bankAccountType != null)
            {
                PreferenceVO primaryPreference = null;
                if (clientDetailVO.getPreferenceVOCount() > 0)
                {
                    PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();
                    for (int i = 0; i < preferenceVOs.length; i++)
                    {
                        if (preferenceVOs[i].getOverrideStatus().equalsIgnoreCase("P"))
                        {
                            primaryPreference = preferenceVOs[i];
                            clientDetailVO.removePreferenceVO(i);
                            break;
                        }
                    }

                }

                if (primaryPreference == null)
                {
                    primaryPreference = new PreferenceVO();
                    primaryPreference.setPreferencePK(CRUD.getNextAvailableKey());
                    primaryPreference.setClientDetailFK(clientDetailPK);
                    primaryPreference.setOverrideStatus("P");
                }

                String bankAcctNumber = Util.initString((String)rs.getString("BankAccountNumber"), null);
                if (bankAcctNumber != null && bankAcctNumber.equals(""))
                {
                    bankAcctNumber = null;
                }
                primaryPreference.setBankAccountNumber(bankAcctNumber);

                String routingNumber = Util.initString((String)rs.getString("BankRoutingNumber"), null);
                if (routingNumber != null && routingNumber.equals(""))
                {
                    routingNumber = null;
                }
                primaryPreference.setBankRoutingNumber(routingNumber);

                primaryPreference.setBankAccountTypeCT(bankAccountType);
                clientDetailVO.addPreferenceVO(primaryPreference);
                BAcountOut++;
            }

            convertClientAddress(clientDetailVO, countTable);

            countOut++;
            crud.createOrUpdateVOInDBRecursively(clientDetailVO);

//            if (commitCount == 20)
//            {
                crud.commitTransaction();
//                commitCount = 0;
//            }
        }

        countTable.put("CDcountIn", countIn);
        countTable.put("CDcountOut", countOut);
        countTable.put("TIcountOut", TIcountOut);
        countTable.put("BAcountOut", BAcountOut);

        s.close();
        rs.close();
    }

    private void getPreferenceInfoFromPayee(ClientDetailVO clientDetailVO, HashMap countTable) throws Exception
    {
        long clientDetailPK = clientDetailVO.getClientDetailPK();

        List roleKeys = getClientRelationshipPK(clientDetailPK);

        if (!roleKeys.isEmpty())
        {
            for (int i = 0; i < roleKeys.size(); i++)
            {
                long clientRelationshipPK = (Long)roleKeys.get(i);
                getPayeeData(clientRelationshipPK, clientDetailVO, countTable);
            }
        }
    }


    private List getClientRelationshipPK(long clientDetailPK)  throws Exception
    {
        long clientRelationShipPK = 0;
        List roleKeys = new ArrayList();

        Statement s = contractConn.createStatement();

        String sql = "SELECT ClientRelationshipPK FROM ClientRelationship WHERE ClientFK = " + clientDetailPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            clientRelationShipPK = new Long(rs.getLong("ClientRelationshipPK"));
            roleKeys.add(clientRelationShipPK);
        }

        s.close();
        rs.close();

        return roleKeys;
    }

   private void getPayeeData(long clientRelationshipPK, ClientDetailVO clientDetailVO, HashMap countTable) throws Exception
    {
        int countIn = (Integer) countTable.get("PRcountIn");
        int countOut = (Integer) countTable.get("PRcountOut");

        PreferenceVO preferenceVO = null;

        Statement s = contractConn.createStatement();

        String sql = "SELECT * FROM Payee WHERE ClientRelationshipFK = " + clientRelationshipPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            preferenceVO = new PreferenceVO();
            preferenceVO.setPreferencePK(CRUD.getNextAvailableKey());

            String printAs = Util.initString((String)rs.getString("PrintAs"), null);
            if (printAs != null && printAs.equals(""))
            {
                printAs = null;
            }
            preferenceVO.setPrintAs(printAs);

            String printAs2 = Util.initString((String)rs.getString("PrintAs2"), null);
            if (printAs2 != null && printAs2.equals(""))
            {
                printAs2 = null;
            }
            preferenceVO.setPrintAs2(printAs2);

            preferenceVO.setOverrideStatus("P");

            Integer codeTableKey = new Integer(rs.getInt("DisbursementSourceCT"));
            String disbursementSource = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            preferenceVO.setDisbursementSourceCT(disbursementSource);

            preferenceVO.setClientDetailFK(clientDetailVO.getClientDetailPK());
            clientDetailVO.addPreferenceVO(preferenceVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("PRcountIn", countIn);
        countTable.put("PRcountOut", countOut);
    }


    private void convertClientAddress(ClientDetailVO clientDetailVO, HashMap countTable)  throws Exception
    {
        int countIn = (Integer) countTable.get("CAcountIn");
        int countOut = (Integer) countTable.get("CAcountOut");

        ClientAddressVO clientAddressVO = null;
        long clientDetailPK = clientDetailVO.getClientDetailPK();

        Statement s = clientConn.createStatement();

        String sql = "SELECT * FROM ClientAddress WHERE ClientDetailFK = " + clientDetailPK;

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {

            countIn++;
            clientAddressVO = new ClientAddressVO();
            clientAddressVO.setClientAddressPK(new Long(rs.getLong("ClientAddressPK")));

            clientAddressVO.setClientDetailFK(clientDetailVO.getClientDetailPK());
            clientAddressVO.setSequenceNumber(rs.getInt("SequenceNumber"));

            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate != null)
            {
                effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                clientAddressVO.setEffectiveDate(effectiveDate);
            }

            String terminationDate = Util.initString((String)rs.getString("TerminationDate"), null);
            if (terminationDate == null)
            {
                terminationDate = EDITDate.DEFAULT_MAX_DATE;
            }
            else
            {
                terminationDate = CommonDatabaseConversionFunctions.convertDate(terminationDate);
            }
            clientAddressVO.setTerminationDate(terminationDate);

            clientAddressVO.setStartDate(Util.initString((String)rs.getString("StartDate"), null));
            clientAddressVO.setStopDate(Util.initString((String)rs.getString("StopDate"), null));

            String phoneNumber = Util.initString((String)rs.getString("PhoneNumber"), null);
            if (phoneNumber != null && phoneNumber.equals(""))
            {
                phoneNumber = null;
            }
//            clientDetailVO.setPhoneNumber(phoneNumber);

            String faxNumber = Util.initString((String)rs.getString("FaxNumber"), null);
            if (faxNumber != null && faxNumber.equals(""))
            {
                faxNumber = null;
            }
//            clientDetailVO.setFaxNumber(faxNumber);

            clientAddressVO.setZipCode(Util.initString((String)rs.getString("ZipCode"), null));

            String address1 = Util.initString((String)rs.getString("AddressLine1"), null);
            if (address1 != null && address1.equals(""))
            {
                address1 = null;
            }
            clientAddressVO.setAddressLine1(address1);

            String address2 = Util.initString((String)rs.getString("AddressLine2"), null);
            if (address2 != null && address2.equals(""))
            {
                address2 = null;
            }
            clientAddressVO.setAddressLine2(address2);

            String address3 = Util.initString((String)rs.getString("AddressLine3"), null);
            if (address3 != null && address3.equals(""))
            {
                address3 = null;
            }
            clientAddressVO.setAddressLine3(address3);

            String address4 = Util.initString((String)rs.getString("AddressLine4"), null);
            if (address4 != null && address4.equals(""))
            {
                address4 = null;
            }
            clientAddressVO.setAddressLine4(address4);

            String county =Util.initString((String)rs.getString("County"), null);
            if(county != null && county.equals(""))
            {
                county = null;
            }
            clientAddressVO.setCounty(county);

            clientAddressVO.setCity(Util.initString((String)rs.getString("City"), null));

            String email = Util.initString((String)rs.getString("EmailAddress"), null);
            if (email != null && email.equals(""))
            {
                email = null;
            }
//            clientDetailVO.setEmailAddress(email);

            Integer codeTableKey = new Integer(rs.getInt("AddressTypeCT"));
            String addressType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            clientAddressVO.setAddressTypeCT(addressType);

            codeTableKey = new Integer(rs.getInt("ZipCodePlacementCT"));
            String zipCodePlacement = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            clientAddressVO.setZipCodePlacementCT(zipCodePlacement);

            codeTableKey = new Integer(rs.getInt("StateCT"));
            String state = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            clientAddressVO.setStateCT(state);

            codeTableKey = new Integer(rs.getInt("CountryCT"));
            String country = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            clientAddressVO.setCountryCT(country);

            clientDetailVO.addClientAddressVO(clientAddressVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("CAcountIn", countIn);
        countTable.put("CAcountOut", countOut);
    }


    public void convertClientChangeHistory(HashMap countTable)  throws Exception
    {
        int countIn = 0;
        int countOut = 0;
//        int commitCount = 0;

        ChangeHistoryVO changeHistoryVO = null;

        Statement s = clientConn.createStatement();

        String sql = "SELECT * FROM ClientChangeHistory";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
//            if (commitCount == 0)
//            {
//                crud.startTransaction();
//            }
            countIn++;
            changeHistoryVO = new ChangeHistoryVO();
            changeHistoryVO.setChangeHistoryPK(new Long(rs.getLong("ClientChangeHistoryPK")));
            changeHistoryVO.setModifiedRecordFK(new Long(rs.getLong("ModifiedRecordFK")));
            String tableName = Util.initString((String)rs.getString("TableName"), null);
            changeHistoryVO.setTableName(tableName);
            changeHistoryVO.setBeforeValue(Util.initString((String)rs.getString("BeforeValue"), null));

            String afterValue = Util.initString((String)rs.getString("AfterValue"), null);
            if (afterValue != null && afterValue.equals(""))
            {
                afterValue = null;
            }
            changeHistoryVO.setAfterValue(afterValue);

            changeHistoryVO.setFieldName(Util.initString((String)rs.getString("FieldName"), null));
            changeHistoryVO.setOperator(Util.initString((String)rs.getString("Operator"), null));
            changeHistoryVO.setMaintDateTime(Util.initString((String)rs.getString("MaintDateTime"), null));

            crud.createOrUpdateVOInDB(changeHistoryVO);
            countOut++;

//            commitCount++;
//            if (commitCount == 100)
//            {
//                crud.commitTransaction();
//                commitCount = 0;
//            }
        }

        s.close();
        rs.close();

        countTable.put("CCHcountIn", countIn);
        countTable.put("CCHcountOut", countOut);
    }
}
