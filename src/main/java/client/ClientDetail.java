/*
 * User: sramamurthy
 * Date: Jul 22, 2004
 * Time: 1:30:51 PM
 *
 * 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client;

import casetracking.*;

import client.dm.dao.*;

import contract.*;

import edit.common.*;
import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;
import edit.portal.exceptions.*;

import fission.utility.*;

import java.util.*;
import java.util.Set;

import org.dom4j.*;

import role.*;
import event.*;
import org.dom4j.Element;

import group.*;
import staging.StagingContext;
import staging.IStaging;
import engine.sp.*;
import engine.business.*;
import engine.component.*;

import org.hibernate.Session;


public class ClientDetail extends HibernateEntity implements CRUDEntity, IStaging
{
    public static final String TRUSTTYPECT_CORP = "Corporate";
    public static final String TRUSTTYPECT_INDIVIDUAL = "Individual";
    public static final String TRUSTTYPECT_SPLITDOLLAR = "SplitDollar";
    public static final String TRUSTTYPECT_BANK = "Bank";
    public static final String TRUSTTYPECT_CLIENTCOMPANY = "ClientCompany";
    public static final String TRUSTTYPECT_LLC = "LLC";
    public static final String GENDERCT_MALE = "Male";
    public static final String GENDERCT_FEMALE = "Female";
    public static final String GENDERCT_NONNATURAL = "NonNatural";
    public static final String GENDERCT_NOTAPPLICABLE = "NotApplicable";
    public static final String STATUSCT_ACTIVE = "Active";
    public static final String STATUSCT_DECEASED = "Deceased";
    public static final String STATUSCT_TERMINATED = "Terminated";
    public static final String CASETRACKING_PROCESS_ANNUITIZATION = "Annuitization";
    public static final String CASETRACKING_PROCESS_CLAIMS = "Claims";

    public static final String DEFAULT_TAXID = "000000000";
    public static final String DEFAULT_TAXID_SSN = "999999999";

    public static final String TAXID_DASH = "-";

    private ClientDetailVO clientDetailVO;
    private CRUDEntityI crudEntityImpl;
    private boolean saveChangeHistory;
    private Set<ClientRole> clientRoles = new HashSet<ClientRole>();
    private Set casetrackingNotes = new HashSet();
    private Set caseRequirements = new HashSet();
    private Set<ClientAddress> clientAddresses = new HashSet<ClientAddress>();
    private Set<Preference> preferences = new HashSet<Preference>();
    private Set bankAccountInformations = new HashSet();
    private Set<TaxInformation> taxInformations = new HashSet<TaxInformation>();
    private Set suspenses = new HashSet();
    private Set<ContactInformation> contactInformations = new HashSet<ContactInformation>();
    private boolean naturalStatus = false;

    /**
     * "Standard Industrial Classfication" - broad industry classification of
     * a client.
     */
    private String sicCodeCT;

    /**
     * In support of the SEGElement interface.
     */
    private Element clientDetailElement;

    /**
     * Database used for ClientDetail
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /************************************** Constructor Methods **************************************/
    /**
     * Instantiates a ClientDetail entity with a default ClientDetailVO.
     */
    public ClientDetail()
    {
        init();
    }

    /**
     * Instantiates a ClientDetail entity with a ClientDetailVO retrieved from persistence.
     *
     * @param clientDetailPK
     */
    public ClientDetail(long clientDetailPK)
    {
        init();

        crudEntityImpl.load(this, clientDetailPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ClientDetail entity with a supplied ClientDetailVO.
     *
     * @param clientDetailVO
     */
    public ClientDetail(ClientDetailVO clientDetailVO)
    {
        init();

        this.clientDetailVO = clientDetailVO;
    }

    /**
     * Getter
     *
     * @return set of taxInformations
     */
    public Set<TaxInformation> getTaxInformations()
    {
        return taxInformations;
    }

    /**
     * Setter
     *
     * @param taxInformations set of taxInformations
     */
    public void setTaxInformations(Set<TaxInformation> taxInformations)
    {
        this.taxInformations = taxInformations;
    }

    /**
     * Adds a TaxInformation to the set of children
     *
     * @param taxInformation
     */
    public void addTaxInformation(TaxInformation taxInformation)
    {
        this.getTaxInformations().add(taxInformation);

        taxInformation.setClientDetail(this);

        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);
    }

    /**
     * Removes a TaxInformation from the set of children
     *
     * @param taxInformation
     */
    public void removeTaxInformation(TaxInformation taxInformation)
    {
        this.getTaxInformations().remove(taxInformation);

        taxInformation.setClientDetail(null);

        SessionHelper.saveOrUpdate(taxInformation, ClientDetail.DATABASE);
    }

    /**
     * Getter
     *
     * @return set of bankAccountInformations
     */
    public Set getBankAccountInformations()
    {
        return bankAccountInformations;
    }

    /**
     * Setter
     *
     * @param bankAccountInformations set of bankAccountInformations
     */
    public void setBankAccountInformations(Set bankAccountInformations)
    {
        this.bankAccountInformations = bankAccountInformations;
    }

    /**
     * Getter
     *
     * @return set of preferences
     */
    public Set<Preference> getPreferences()
    {
        return preferences;
    }

    /**
     * Setter
     *
     * @param preferences set of preferences
     */
    public void setPreferences(Set<Preference> preferences)
    {
        this.preferences = preferences;
    }

    /**
     * Removes a Preference from the set of children
     *
     * @param preference
     */
    public void removePreference(Preference preference)
    {
        this.getPreferences().remove(preference);

        preference.setClientDetail(null);

        SessionHelper.saveOrUpdate(preference, ClientDetail.DATABASE);
    }

    /**
      * Get a single Preference
      * @return
      */
     public Preference getPreference()
     {
         Preference preference =getPreferences().isEmpty() ? null : (Preference) getPreferences().iterator().next();

         return preference;
     }

    /**
     * Getter
     *
     * @return set of contactInformations
     */
    public Set<ContactInformation> getContactInformations()
    {
        return contactInformations;
    }

    /**
     * Setter
     *
     * @param contactInformations set of contactInformations
     */
    public void setContactInformations(Set<ContactInformation> contactInformations)
    {
        this.contactInformations = contactInformations;
    }

    /**
     * Adds a ContactInformation to the set of children
     *
     * @param contactInformation
     */
    public void addContactInformation(ContactInformation contactInformation)
    {
        this.getContactInformations().add(contactInformation);

        contactInformation.setClientDetail(this);

        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);
    }

    /**
     * Removes a ContactInformation from the set of children
     *
     * @param contactInformation
     */
    public void removeContactInformation(ContactInformation contactInformation)
    {
        this.getContactInformations().remove(contactInformation);

        contactInformation.setClientDetail(null);

        SessionHelper.saveOrUpdate(contactInformation, ClientDetail.DATABASE);
    }

    /************************************** Public Methods **************************************/
    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        try
        {
            crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    //-- java.lang.String getGenderCT()

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getBirthDate()
    {
        return ((clientDetailVO.getBirthDate() != null) ? new EDITDate(clientDetailVO.getBirthDate()) : null);
    }

    /**
     * Gets the business address, if any.
     *
     * @return
     */
    public ClientAddress getBusinessAddress()
    {
        return ClientAddress.findByClientDetail_And_AddressTypeCT(this, ClientAddress.CLIENT_BUSINESS_ADDRESS);
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getClientDetailPK()
    {
        return SessionHelper.getPKValue(clientDetailVO.getClientDetailPK());
    }

    /**
     * Setter.
     *
     * @param clientDetailPK
     */
    public void setClientDetailPK(Long clientDetailPK)
    {
        this.clientDetailVO.setClientDetailPK(SessionHelper.getPKValue(clientDetailPK));
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getClientIdentification()
    {
        return clientDetailVO.getClientIdentification();
    }

    //-- java.lang.String getLastName()

    /**
     * Getter.
     *
     * @return
     */
    public String getCorporateName()
    {
        return clientDetailVO.getCorporateName();
    }

    /**
     * Getter.
     *
     * @return
     */
    public java.lang.String getCaseTrackingProcess()
    {
        return clientDetailVO.getCaseTrackingProcess();
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getDateOfDeath()
    {
        return ((clientDetailVO.getDateOfDeath() != null) ? new EDITDate(clientDetailVO.getDateOfDeath()) : null);
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getFirstName()
    {
        return clientDetailVO.getFirstName();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getGenderCT()
    {
        return clientDetailVO.getGenderCT();
    }

    //-- java.lang.String getMiddleName()

    /**
     * Getter.
     *
     * @return
     */
    public String getLastName()
    {
        return clientDetailVO.getLastName();
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getLastOFACCheckDate()
    {
        return ((clientDetailVO.getLastOFACCheckDate() != null) ? new EDITDate(clientDetailVO.getLastOFACCheckDate()) : null);
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return ((clientDetailVO.getMaintDateTime() != null) ? new EDITDateTime(clientDetailVO.getMaintDateTime()) : null);
    }

    //-- java.lang.String getFirstName()

    /**
     * Getter.
     *
     * @return
     */
    public String getMiddleName()
    {
        return clientDetailVO.getMiddleName();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getMothersMaidenName()
    {
        return clientDetailVO.getMothersMaidenName();
    }

    //-- java.lang.String getTaxIdentification()

    /**
     * Getter.
     *
     * @return
     */
    public String getNamePrefix()
    {
        return clientDetailVO.getNamePrefix();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getNameSuffix()
    {
        return clientDetailVO.getNameSuffix();
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getNotificationReceivedDate()
    {
        return ((clientDetailVO.getNotificationReceivedDate() != null) ? new EDITDate(clientDetailVO.getNotificationReceivedDate()) : null);
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getOccupation()
    {
        return clientDetailVO.getOccupation();
    }

    /**
     * Getter
     *
     * @return
     */
    public String getOperator()
    {
        return clientDetailVO.getOperator();
    }

    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return clientDetailVO.getClientDetailPK();
    }

    /**
     * Gets the primary address, if any.
     *
     * @return
     */
    public ClientAddress getPrimaryAddress()
    {
        return ClientAddress.findByClientDetail_And_AddressTypeCT(this, ClientAddress.CLIENT_PRIMARY_ADDRESS);
    }

    /**
     * Gets the primary contact information, if any.
     *
     * @return
     */
    public ContactInformation getPrimaryContactInformation()
    {
        return ContactInformation.findByClientDetail_ContactTypeCT(this, ContactInformation.CONTACTTYPE_PRIMARY);
    }
    
    /**
     * Gets the home contact information, if any.
     *
     * @return
     */
    public ContactInformation getHomeContactInformation()
    {
        return ContactInformation.findByClientDetail_ContactTypeCT(this, ContactInformation.CONTACTTYPE_HOME);
    }
    
    /**
     * Gets the work contact information, if any.
     *
     * @return
     */
    public ContactInformation getWorkContactInformation()
    {
        return ContactInformation.findByClientDetail_ContactTypeCT(this, ContactInformation.CONTACTTYPE_WORK);
    }
    
    /**
     * Gets the mobile contact information, if any.
     *
     * @return
     */
    public ContactInformation getMobileContactInformation()
    {
        return ContactInformation.findByClientDetail_ContactTypeCT(this, ContactInformation.CONTACTTYPE_MOBILE);
    }
    
    /**
     * Gets the fax contact information, if any.
     *
     * @return
     */
    public ContactInformation getFaxContactInformation()
    {
        return ContactInformation.findByClientDetail_ContactTypeCT(this, ContactInformation.CONTACTTYPE_FAX);
    }
    
    /**
     * Gets the email contact information, if any.
     *
     * @return
     */
    public ContactInformation getEmailContactInformation()
    {
        return ContactInformation.findByClientDetail_ContactTypeCT(this, ContactInformation.CONTACTTYPE_EMAIL);
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getPrivacyInd()
    {
        return clientDetailVO.getPrivacyInd();
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getProofOfDeathReceivedDate()
    {
        return ((clientDetailVO.getProofOfDeathReceivedDate() != null) ? new EDITDate(clientDetailVO.getProofOfDeathReceivedDate()) : null);
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getResidentStateAtDeathCT()
    {
        return clientDetailVO.getResidentStateAtDeathCT();
    }

    /**
     * Getter
     *
     * @return
     */
    public boolean getSaveChangeHistory()
    {
        return saveChangeHistory;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getStateOfDeathCT()
    {
        return clientDetailVO.getStateOfDeathCT();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getStatusCT()
    {
        return clientDetailVO.getStatusCT();
    }

    //-- java.lang.String getTrustTypeCT()

    /**
     * Getter.
     *
     * @return
     */
    public String getTaxIdentification()
    {
        return clientDetailVO.getTaxIdentification();
    }

    //-- java.lang.String getBirthDate()

    /**
     * Getter.
     *
     * @return
     */
    public String getTrustTypeCT()
    {
        return clientDetailVO.getTrustTypeCT();
    }

    /**
     * Getter
     *
     * @return
     */
    public String getOverrideStatus()
    {
        return clientDetailVO.getOverrideStatus();
    } //-- java.lang.String getOverrideStatus()

    /**
     * Getter
     * @return
     */
    public String getSICCodeCT()
    {
        return clientDetailVO.getSICCodeCT();
    }

    /**
     * Getter
     * @return
     */
    public Long getCompanyFK()
    {
        return SessionHelper.getPKValue(clientDetailVO.getCompanyFK());
    }


    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return clientDetailVO;
    }

    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        try
        {
            SessionHelper.clearSessions();
            SessionHelper.beginTransaction(ClientDetail.DATABASE);

            if (clientDetailVO.getClientAddressVOCount() > 0)
            {
                ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();

                for (int i = 0; i < clientAddressVOs.length; i++)
                {
                    clientAddressVOs[i].setClientDetailFK(clientDetailVO.getClientDetailPK());

                    ClientAddress clientAddress = new ClientAddress(clientAddressVOs[i], this);
                    clientAddress.save();
                }
            }

            if (clientDetailVO.getTaxInformationVOCount() > 0)
            {
                TaxInformationVO[] taxInformationVOs = clientDetailVO.getTaxInformationVO();

                for (int i = 0; i < taxInformationVOs.length; i++)
                {
                    taxInformationVOs[i].setClientDetailFK(clientDetailVO.getClientDetailPK());

                    TaxInformation taxInformation = new TaxInformation(taxInformationVOs[i]);

                    if (taxInformationVOs[i].getTaxProfileVOCount() > 0)
                    {
                        TaxProfileVO[] taxProfileVOs = taxInformationVOs[i].getTaxProfileVO();

                        for (int j = 0; j < taxProfileVOs.length; j++)
                        {
                            TaxProfile taxProfile = new TaxProfile(taxProfileVOs[j]);

                            taxInformation.addTaxProfile(taxProfile);
                        }
                    }

                    addTaxInformation(taxInformation);
                }
            }

            if (clientDetailVO.getPreferenceVOCount() > 0)
            {
                PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();

                for (int i = 0; i < preferenceVOs.length; i++)
                {
                    preferenceVOs[i].setClientDetailFK(clientDetailVO.getClientDetailPK());

                    Preference preference = new Preference(preferenceVOs[i]);
//                    preference.save();
                    addPreference(preference);
                }
            }

            if (clientDetailVO.getContactInformationVOCount() > 0)
            {
                ContactInformationVO[] contactInformationVOs = clientDetailVO.getContactInformationVO();

                for (int i = 0; i < contactInformationVOs.length; i++)
                {
                    contactInformationVOs[i].setClientDetailFK(clientDetailVO.getClientDetailPK());

                    ContactInformation contactInformation = new ContactInformation(contactInformationVOs[i]);

//                    contactInformation.save();
                    addContactInformation(contactInformation);
                }
            }


            //CONVERT to SAVE using HIBERNATE
            this.hSave();
            SessionHelper.commitTransaction(ClientDetail.DATABASE);
            // need to do call the following method other wise the updates done via CRUD may not be reflected in the casetracking pages.
//            SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS);  GF - Is this still necessary?
        }
        catch (Throwable t)
        {
            System.out.println(t);

            t.printStackTrace();
        }
    }

    /**
     * Setter.
     */
    public void setBirthDate(EDITDate birthDate)
    {
        clientDetailVO.setBirthDate((birthDate != null) ? birthDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     */
    public void setClientDetailPK(long clientDetailPK)
    {
        clientDetailVO.setClientDetailPK(clientDetailPK);
    }

    /**
     * Setter.
     */
    public void setClientIdentification(String clientIdentification)
    {
        clientDetailVO.setClientIdentification(clientIdentification);
    }

    /**
     * Setter.
     */
    public void setCorporateName(String corporateName)
    {
        clientDetailVO.setCorporateName(corporateName);
    }

    /**
     * Setter.
     */
    public void setCaseTrackingProcess(java.lang.String caseTrackingProcess)
    {
        clientDetailVO.setCaseTrackingProcess(caseTrackingProcess);
    }

    /**
     * Setter.
     */
    public void setDateOfDeath(EDITDate dateOfDeath)
    {
        clientDetailVO.setDateOfDeath((dateOfDeath != null) ? dateOfDeath.getFormattedDate() : null);
    }

    /**
     * Setter.
     */
    public void setFirstName(String firstName)
    {
        clientDetailVO.setFirstName(firstName);
    }

    /**
     * Setter.
     */
    public void setGenderCT(String genderCT)
    {
        clientDetailVO.setGenderCT(genderCT);
    }

    /**
     * Setter.
     */
    public void setLastName(String lastName)
    {
        clientDetailVO.setLastName(lastName);
    }

    /**
     * Setter.
     */
    public void setLastOFACCheckDate(EDITDate lastOFACCheckDate)
    {
        clientDetailVO.setLastOFACCheckDate((lastOFACCheckDate != null) ? lastOFACCheckDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        clientDetailVO.setMaintDateTime((maintDateTime != null) ? maintDateTime.getFormattedDateTime() : null);
    }

    /**
     * Setter.
     */
    public void setMiddleName(String middleName)
    {
        clientDetailVO.setMiddleName(middleName);
    }

    /**
     * Setter.
     */
    public void setMothersMaidenName(String mothersMaidenName)
    {
        clientDetailVO.setMothersMaidenName(mothersMaidenName);
    }

    /**
     * Setter.
     */
    public void setNamePrefix(String namePrefix)
    {
        clientDetailVO.setNamePrefix(namePrefix);
    }

    /**
     * Setter.
     */
    public void setNameSuffix(String nameSuffix)
    {
        clientDetailVO.setNameSuffix(nameSuffix);
    }

    /**
     * Setter.
     */
    public void setNotificationReceivedDate(EDITDate notificationReceivedDate)
    {
        clientDetailVO.setNotificationReceivedDate((notificationReceivedDate != null) ? notificationReceivedDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     */
    public void setOccupation(String occupation)
    {
        clientDetailVO.setOccupation(occupation);
    }

    /**
     * Setter.
     */
    public void setOperator(String operator)
    {
        clientDetailVO.setOperator(operator);
    }

    /**
     * Setter.
     */
    public void setPrivacyInd(String privacyInd)
    {
        clientDetailVO.setPrivacyInd(privacyInd);
    }

    /**
     * Setter.
     */
    public void setProofOfDeathReceivedDate(EDITDate proofOfDeathReceivedDate)
    {
        clientDetailVO.setProofOfDeathReceivedDate((proofOfDeathReceivedDate != null) ? proofOfDeathReceivedDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     */
    public void setResidentStateAtDeathCT(String residentStateAtDeathCT)
    {
        clientDetailVO.setResidentStateAtDeathCT(residentStateAtDeathCT);
    }

    /**
     * Setter
     *
     * @param saveChangeHistory
     */
    public void setSaveChangeHistory(boolean saveChangeHistory)
    {
        this.saveChangeHistory = saveChangeHistory;
    }
    
    /**
     * Setter.
     */
    public void setStateOfDeathCT(String stateOfDeathCT)
    {
        clientDetailVO.setStateOfDeathCT(stateOfDeathCT);
    }

    /**
     * Setter.
     */
    public void setStatusCT(String statusCT)
    {
        clientDetailVO.setStatusCT(statusCT);
    }

    /**
     * Setter.
     */
    public void setTaxIdentification(String taxIdentification)
    {
        clientDetailVO.setTaxIdentification(taxIdentification);
    }

    /**
     * Setter.
     */
    public void setTrustTypeCT(String trustTypeCT)
    {
        clientDetailVO.setTrustTypeCT(trustTypeCT);
    }

    /**
     * Setter
     *
     * @param overrideStatus
     */
    public void setOverrideStatus(String overrideStatus)
    {
        clientDetailVO.setOverrideStatus(overrideStatus);
    } //-- void setOverrideStatus(java.lang.String)

    /**
     * Setter
     * @param sicCodeCT
     */
    public void setSICCodeCT(String sicCodeCT)
    {
        clientDetailVO.setSICCodeCT(sicCodeCT);
    }

    /**
     * Setter
     * @param companyFK
     */
    public void setCompanyFK(Long companyFK)
    {
        clientDetailVO.setCompanyFK(SessionHelper.getPKValue(companyFK));
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.clientDetailVO = (ClientDetailVO) voObject;
    }

    /**
     * Getter
     *
     * @return set of clientRoles
     */
    public Set<ClientRole> getClientRoles()
    {
        return clientRoles;
    }

    /**
     * Setter
     *
     * @param clientRoles set of clientRoles
     */
    public void setClientRoles(Set<ClientRole> clientRoles)
    {
        this.clientRoles = clientRoles;
    }

    /**
     * Adds a ClientRole to the set of children
     *
     * @param clientRole
     */
    public void addClientRole(ClientRole clientRole)
    {
        this.getClientRoles().add(clientRole);

        clientRole.setClientDetail(this);

        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);
    }

    /**
     * Removes a ClientRole from the set of children
     *
     * @param clientRole
     */
    public void removeClientRole(ClientRole clientRole)
    {
        this.getClientRoles().remove(clientRole);

        clientRole.setClientDetail(null);

        SessionHelper.saveOrUpdate(clientRole, ClientDetail.DATABASE);
    }

    /**
     * Getter
     *
     * @return set of casetrackingNotes
     */
    public Set getCasetrackingNotes()
    {
        return casetrackingNotes;
    }

    /**
     * Setter
     *
     * @param casetrackingNotes set of casetrackingNotes
     */
    public void setCasetrackingNotes(Set casetrackingNotes)
    {
        this.casetrackingNotes = casetrackingNotes;
    }

    /**
     * Adds a CasetrackingNote to the set of children
     *
     * @param caseTrackingNote
     */
    public void addCasetrackingNote(CasetrackingNote caseTrackingNote)
    {
        this.getCasetrackingNotes().add(caseTrackingNote);

        caseTrackingNote.setClientDetail(this);

        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);
    }

    /**
     * Removes a CaseTrackingNote from the set of children
     *
     * @param caseTrackingNote
     */
    public void removeCaseTrackingNote(CasetrackingNote caseTrackingNote)
    {
        this.getCasetrackingNotes().remove(caseTrackingNote);

        caseTrackingNote.setClientDetail(null);

        SessionHelper.saveOrUpdate(caseTrackingNote, ClientDetail.DATABASE);
    }

    /**
     * Getter
     *
     * @return set of caseRequirements
     */
    public Set getCaseRequirements()
    {
        return caseRequirements;
    }

    /**
     * Setter
     *
     * @param caseRequirements set of caseRequirements
     */
    public void setCaseRequirements(Set caseRequirements)
    {
        this.caseRequirements = caseRequirements;
    }

    /**
     * Adds a CaseRequirement to the set of children
     *
     * @param caseRequirement
     */
    public void addCaseRequirement(CaseRequirement caseRequirement)
    {
        this.getCaseRequirements().add(caseRequirement);

        caseRequirement.setClientDetail(this);

        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);
    }

    /**
     * Removes a CaseRequirement from the set of children
     *
     * @param caseRequirement
     */
    public void removeCaseRequirement(CaseRequirement caseRequirement)
    {
        this.getCaseRequirements().remove(caseRequirement);

        caseRequirement.setClientDetail(null);

        SessionHelper.saveOrUpdate(caseRequirement, ClientDetail.DATABASE);
    }

    /**
     * Getter.
     *
     * @return
     */
    public Set<ClientAddress> getClientAddresses()
    {
        return clientAddresses;
    }

    /**
     * Setter.
     *
     * @param clientAddresses
     */
    public void setClientAddresses(Set<ClientAddress> clientAddresses)
    {
        this.clientAddresses = clientAddresses;
    }


    /**
     * Getter.
     *
     * @return
     */
    public Set getSuspenses()
    {
        return suspenses;
    }

    /**
     * Setter.
     *
     * @param suspenses
     */
    public void setSuspenses(Set suspenses)
    {
        this.suspenses = suspenses;
    }

    /**
     * Determines whether the client is corporate client or not?
     *
     * @return
     */
    public boolean isCorporate()
    {
        return ((getCorporateName() != null) ? true : false);
    }

    /**
     * Gives the next sequence number for the notes.
     *
     * @return
     */
    public int getNextSequenceNumberForNote()
    {
        int highestSequenceNumber = 0;

        highestSequenceNumber = CasetrackingNote.findHighestSequenceNumber_ByClientDetail(this);

        return ++highestSequenceNumber;
    }

    /**
     * @return
     */
    public boolean hasCaseTrackingNotes()
    {
        boolean hasCaseTrackingNotes = false;

        hasCaseTrackingNotes = (getCasetrackingNotes().size() > 0);

        return hasCaseTrackingNotes;
    }

    /**
     * Gets the target name of this ClientDetail. The name is the CorporateName if the TrustTypeCT is 'Corporate'. The
     * name is LastName if the TrustTypeCT is 'Individual'.
     * <p/>
     * If it is undeterministic (e.g. java reflection is taking place before this ClientDetail is in a valid state),
     * the null is returned.
     *
     * @return the Invidual or Corporate client name or null if undeterministic
     */
    public String getName()
    {
        String name = null;

        if (getTrustTypeCT() != null && getTrustTypeCT().equalsIgnoreCase(TRUSTTYPECT_INDIVIDUAL))
        {
            name = getLastName();
        }
        else if (getTrustTypeCT() != null && getTrustTypeCT().equalsIgnoreCase(TRUSTTYPECT_SPLITDOLLAR))
        {
            if (getLastName() != null)
            {
                name = getLastName();
            }
            else
            {
                name = getCorporateName();
            }
        }
        else
        {
            name = getCorporateName();
        }
        return name;
    }
    
    /**
     * In the case of an Individual, formats as "LastName, FirstName".
     * In the case of a Corporation, formats as "CorporateName" (only).
     * @return a pretty version of "LastName, FirstName" or "CorporateName" depending on the Trust Type.
     * 
     */
    public String getPrettyName() 
    {
        String prettyName = getName();
        
        if (getTrustTypeCT() != null && getTrustTypeCT().equalsIgnoreCase(TRUSTTYPECT_INDIVIDUAL)) 
        {
            prettyName =  prettyName + ", " + getFirstName();    
        }

        return prettyName;
    }
    
    /**
     * A dummy compliment to getName for no other reason than to support
     * Javabeans conventions (otherwise some 3rd party APIs are being adversely
     * affected.
     *
     * @param name
     */
    public void setPrettyName(String prettyName)
    {
        // boo!
    }    

    /**
     * A dummy compliment to getName for no other reason than to support
     * Javabeans conventions (otherwise some 3rd party APIs are being adversely
     * affected.
     *
     * @param name
     */
    public void setName(String name)
    {
        // boo!
    }

    /**
     * Determines whether filteredRequirement is associated to client or not.
     *
     * @param filteredRequirement
     *
     * @return boolean value true or false
     */
    public boolean isFilteredRequirementAssociated(FilteredRequirement filteredRequirement)
    {
        CaseRequirement caseRequirement = CaseRequirement.findBy_ClientDetail_And_FilteredRequirement(this, filteredRequirement);

        if (caseRequirement == null)
        {
            return false;
        }

        return true;
    }

    /**
     * Deletes the casetrackingNote from collection.
     *
     * @param casetrackingNote
     */
    public void deleteCasetrackingNote(CasetrackingNote casetrackingNote)
    {
        for (Iterator iterator = casetrackingNotes.iterator(); iterator.hasNext();)
        {
            CasetrackingNote note = (CasetrackingNote) iterator.next();

            if (casetrackingNote.getCasetrackingNotePK().longValue() == note.getCasetrackingNotePK().longValue())
            {
                iterator.remove();
            }
        }
    }

    /**
     * Deletes the caseRequirement from collection.
     *
     * @param caseRequirement
     */
    public void deleteCaseRequirement(CaseRequirement caseRequirement)
    {
        for (Iterator iterator = caseRequirements.iterator(); iterator.hasNext();)
        {
            CaseRequirement requirement = (CaseRequirement) iterator.next();

            if (caseRequirement.getCaseRequirementPK().longValue() == requirement.getCaseRequirementPK().longValue())
            {
                iterator.remove();
            }
        }
    }

    /**
     * Adds Client Address.
     *
     * @param clientAddress
     */
    public void addClientAddress(ClientAddress clientAddress)
    {
        getClientAddresses().add(clientAddress);

        clientAddress.setClientDetail(this);

        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);        
    }

    /**
     * Deletes the clientAddress from collection.
     *
     * @param clientAddress
     */
    public void deleteClientAddress(ClientAddress clientAddress)
    {
        for (Iterator iterator = clientAddresses.iterator(); iterator.hasNext();)
        {
            ClientAddress address = (ClientAddress) iterator.next();

            if (clientAddress.getClientAddressPK().longValue() == address.getClientAddressPK().longValue())
            {
                iterator.remove();
            }
        }
    }


    /**
     * Adds Suspense.
     *
     * @param suspense
     */
    public void addSuspense(Suspense suspense)
    {
        getSuspenses().add(suspense);

        suspense.setClientDetail(this);

    }

    /**
     * Adds Preference.
     *
     * @param preference
     */
    public void addPreference(Preference preference)
    {
        getPreferences().add(preference);

        preference.setClientDetail(this);
    }

    /**
     * Returns the Priamry Preference for the client.
     *
     * @return
     */
    public Preference getPrimaryPreference()
    {
        Preference preference = null;

        Iterator iterator = preferences.iterator();

        while (iterator.hasNext())
        {
            preference = (Preference) iterator.next();

            if (Preference.PRIMARY.equalsIgnoreCase(preference.getOverrideStatus()))
            {
                break;
            }
        }

        return preference;
    }

    /************************************** Private Methods **************************************/
    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (clientDetailVO == null)
        {
            clientDetailVO = new ClientDetailVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /************************************** Static Methods **************************************/
    /**
     * Finder.
     *
     * @param partialCorporateName
     *
     * @return
     */
    public static final ClientDetail[] findBy_PartialCorporateName(String partialCorporateName)
    {
        ClientDetailVO[] clientDetailVOs = new ClientDetailDAO().findBy_PartialCorporateName(partialCorporateName);

        return (ClientDetail[]) CRUDEntityImpl.mapVOToEntity(clientDetailVOs, ClientDetail.class);
    }

    /**
     * Finder.
     *
     * @param reinsurerPK
     *
     * @return
     */
    public static final ClientDetail findBy_ReinsurerPK(long reinsurerPK)
    {
        ClientDetail clientDetail = null;

        ClientDetailVO[] clientDetailVOs = new ClientDetailDAO().findBy_ReinsurerPK(reinsurerPK);

        if (clientDetailVOs != null)
        {
            clientDetail = new ClientDetail(clientDetailVOs[0]);
        }

        return clientDetail;
    }

    /**
     * Finder.
     *
     * @param segmentPK
     * @param roleTypeCT
     *
     * @return
     */
    public static final ClientDetail[] findBy_SegmentPK_RoleType(long segmentPK, String roleTypeCT)
    {
        ClientDetailVO[] clientDetailVOs = new ClientDetailDAO().findBy_SegmentPK_RoleType(segmentPK, roleTypeCT);

        return (ClientDetail[]) CRUDEntityImpl.mapVOToEntity(clientDetailVOs, ClientDetail.class);
    }

    /**
     * finds clientDetails by TaxId
     *
     * @param taxId
     *
     * @return
     */
    public static final ClientDetail[] findBy_TaxId(String taxId)
    {
        ClientDetail[] clientDetails = null;

        ClientDetailVO[] clientDetailVOs = new ClientDetailDAO().findByTaxId(taxId, false, null);

        if (clientDetailVOs != null)
        {
            clientDetails = (ClientDetail[]) CRUDEntityImpl.mapVOToEntity(clientDetailVOs, ClientDetail.class);
        }

        return clientDetails;
    }

    /**
     * Finder.
     *
     * @param taxIdentification
     *
     * @return
     */
    public static final ClientDetail[] findBy_TaxIdentification(String taxIdentification)
    {
        String hql = " from ClientDetail clientDetail" +
                " where clientDetail.TaxIdentification = :taxIdentification";

        EDITMap params = new EDITMap("taxIdentification", taxIdentification);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    /**
     * Finder.
     *
     * @param taxIdentification
     * @param trustTypeCT
     *
     * @return
     */
    public static final ClientDetail[] findBy_TaxIdentification_And_TrustTypeCT(String taxIdentification, String trustTypeCT, String taxIdTypeCT)
    {
        String hql = " select clientDetail" +
                " from ClientDetail clientDetail" +
                " join clientDetail.TaxInformations taxInformation" +
                " where clientDetail.TaxIdentification = :taxIdentification" +
                " and clientDetail.TrustTypeCT = :trustTypeCT" +
                " and taxInformation.TaxIdTypeCT = :taxIdTypeCT";

        EDITMap params = new EDITMap("taxIdentification", taxIdentification);
        params.put("trustTypeCT", trustTypeCT);
        params.put("taxIdTypeCT", taxIdTypeCT);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    /**
     * Finds all with the given birthDate
     *
     * @param birthDate
     *
     * @return array of ClientDetails
     */
    public static final ClientDetail[] findBy_BirthDate(EDITDate birthDate)
    {
        String hql = " from ClientDetail clientDetail" +
                     " where clientDetail.BirthDate = :birthDate";

        EDITMap params = new EDITMap("birthDate", birthDate);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    /**
     * Finds all with the given partialName and birthDate
     *
     * @param partialName
     * @param birthDate
     *
     * @return  array of ClientDetails
     */
    public static ClientDetail[] findBy_PartialName_BirthDate(String partialName, EDITDate birthDate)
    {
        Map params = new HashMap();

        String hql = setupQueryForPartialName(partialName, params);

        hql = hql + " and cd.BirthDate = :birthDate";

        params.put("birthDate", birthDate);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    public static ClientDetail[] findByLastName_FirstName_BirthDate(String lastName, String firstName, EDITDate birthDate)
    {
        String hql = " from ClientDetail clientDetail" +
                     " where clientDetail.BirthDate = :birthDate" +
                     " and clientDetail.LastName = :lastName" +
                     " and clientDetail.FirstName = :firstName";

        EDITMap params = new EDITMap("birthDate", birthDate);
        params.put("lastName", lastName);
        params.put("firstName", firstName);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

     public static ClientDetail[] findByLastName_FirstName(String lastName, String firstName)
    {
        String hql = " from ClientDetail clientDetail" +
                     " where clientDetail.LastName = :lastName" +
                     " and clientDetail.FirstName = :firstName";

        EDITMap params = new EDITMap();
        params.put("lastName", lastName);
        params.put("firstName", firstName);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    public static final ClientDetail findByPK(long clientDetailPK)
    {
        ClientDetail clientDetail = null;

        ClientDetailVO[] clientDetailVOs = new ClientDetailDAO().findByClientDetailPK(clientDetailPK, false, null);

        if (clientDetailVOs != null)
        {
            clientDetail = new ClientDetail(clientDetailVOs[0]);
        }

        return clientDetail;
    }


    /**
     * Finds by partial lastName or partial corporateName.
     * Note: clients that have only roleTypeCT are eliminated.
     * Requirement for casetracking module.
     *
     * @param partialName
     * @param roleTypeCT
     *
     * @return
     */
    public static final ClientDetail[] findBy_PartialName_Not_Like_RoleTypeCT(String partialName, String roleTypeCT)
    {
        Map params = new HashMap();
        String hql = setupQueryForPartialName(partialName, params);

        hql = hql + " and cd not in (select cr.ClientDetail from ClientRole cr" + " where cr.RoleTypeCT like :roleTypeCT" +
                " and cr.ClientDetail in (select cr2.ClientDetail" + " from ClientRole cr2 group by cr2.ClientDetail" +
                " having count(*) = 1)) ";

        params.put("roleTypeCT", roleTypeCT);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    public static ClientDetail[] findBy_PartialName(String partialName)
    {
        Map params = new HashMap();
        String hql = setupQueryForPartialName(partialName, params);

//        hql = hql + " and cd not in (select cr.ClientDetail from ClientRole cr" + " where cr.RoleTypeCT like :roleTypeCT" +
//                " and cr.ClientDetail in (select cr2.ClientDetail" + " from ClientRole cr2 group by cr2.ClientDetail" +
//                " having count(*) = 1)) ";


        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }

    public static String setupQueryForPartialName(String partialName, Map params)
    {
        String hql = null;


        try
        {
            partialName = Util.substitute(partialName, "'", "''");
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        if (partialName.indexOf(",") > 0)
        {
            String[] nameParts = Util.fastTokenizer(partialName, ",");

            if (nameParts[1].equals(""))
            {
                hql = "select cd from ClientDetail cd where upper(cd.LastName) like upper(:lastName)";

                params.put("lastName", nameParts[0].trim());
            }
            else
            {
                hql = "select cd from ClientDetail cd where (upper(cd.LastName) like upper(:lastName) and upper(cd.FirstName) like upper(:firstName))";

                params.put("lastName", nameParts[0].trim() + "%");

                params.put("firstName", nameParts[1].trim() + "%");
            }
        }

        // search by partial last name or corporate name
        else
        {
            hql = "select cd from ClientDetail cd where (upper(cd.LastName) like upper(:lastName) or upper(cd.CorporateName) like upper(:corporateName))";

            params.put("lastName", partialName.trim() + "%");

            params.put("corporateName", partialName.trim() + "%");
        }

        return hql;
    }

    /**
     * Finder by taxId.
     * Note: clients that have only roleTypeCT are eliminated.
     * Requirement for casetracking module.
     *
     * @param taxId
     * @param roleTypeCT
     *
     * @return
     */
    public static final ClientDetail[] findBy_TaxId_Not_Like_RoleTypeCT(String taxId, String roleTypeCT)
    {
        String hql = " select cd from ClientDetail cd where cd.TaxIdentification like :taxId" +
                " and cd.OverrideStatus is null" +
                " and cd not in (select cr.ClientDetail from ClientRole cr" +
                " where cr.RoleTypeCT like :roleTypeCT and cr.ClientDetail in (select cr2.ClientDetail" +
                " from ClientRole cr2 group by cr2.ClientDetail" + " having count(*) = 1)) ";

        Map params = new HashMap();

        params.put("taxId", taxId);
        params.put("roleTypeCT", roleTypeCT);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        return results.toArray(new ClientDetail[results.size()]);
    }


    /**
     * Finder.
     *
     * @param clientDetailPK
     *
     * @return
     */
    public static final ClientDetail findByPK(Long clientDetailPK)
    {
        return (ClientDetail) SessionHelper.get(ClientDetail.class, clientDetailPK, ClientDetail.DATABASE);
    }

    /**
     * finds clientDetails by either first name or last name
     *
     * @param name
     *
     * @return
     */
    public static final ClientDetail[] findBy_Name(String name)
    {
        ClientDetail[] clientDetails = null;

        try
        {
            name = Util.substitute(name, "'", "''");
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        String[] nameElements = Util.fastTokenizer(name, ",");

        ClientDetailVO[] clientDetailVOs = null;

        if (nameElements.length == 2)
        {
            if (nameElements[1].equals(""))
            {
                clientDetailVOs = new ClientDetailDAO().findByLastName(nameElements[0].trim(), false, null);
            }
            else
            {
                clientDetailVOs = new ClientDetailDAO().findByLastNamePartialFirstName(nameElements[0].trim(), nameElements[1].trim(), false, null);
            }
        }
        else if (nameElements.length == 1)
        {
            clientDetailVOs = new ClientDetailDAO().findByPartialLastName(nameElements[0].trim(), false, null);
        }

        clientDetails = (ClientDetail[]) CRUDEntityImpl.mapVOToEntity(clientDetailVOs, ClientDetail.class);

        return clientDetails;
    }

    public static final ClientDetail findBy_Segment_RoleType(Segment segment, String roleType)
    {
        String hql = "select cd from ClientDetail cd join cd.ClientRoles cr join cr.ContractClients cc " +
                "join cc.Segment s where s = :segment and cr.RoleTypeCT like :roleTypeCT " +
        		"and cc.OverrideStatus != 'D' " +
        		"and cc.TerminationDate > :currentDate "  ;

        Map params = new HashMap();
        ClientDetail clientDetail = null;

        params.put("segment", segment);
        params.put("roleTypeCT", roleType);
        params.put("currentDate", new EDITDate());

        List results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);
        if (!results.isEmpty())
        {
            clientDetail = (ClientDetail) results.get(0);
        }


        return clientDetail;
    }

    public static final ClientDetail findBy_SegmentPK_ClientRoleFK(Long segmentPK, Long clientRoleFK)
    {
        String hql = "select cd from ClientDetail cd join cd.ClientRoles cr join cr.ContractClients cc " +
                "where cc.SegmentFK = :segmentPK and cc.ClientRoleFK like :clientRoleFK";

        Map params = new HashMap();
        ClientDetail clientDetail = null;

        params.put("segmentPK", segmentPK);
        params.put("clientRoleFK", clientRoleFK);

        List results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);
        if (!results.isEmpty())
        {
            clientDetail = (ClientDetail) results.get(0);
        }

        return clientDetail;
    }

    public static ClientDetail[] findAllPayorsByBillGroupFK(Long billGroupFK)
    {
        String hql = " select clientDetail" +
                " from ClientDetail clientDetail" +
                " join fetch clientDetail.ClientRoles clientRole" +
                " join fetch clientRole.ContractClients contractClient" +
                " join fetch contractClient.Segment segment" +
                " join fetch segment.Bills bill" +
                " where bill.BillGroupFK = :billGroupFK" +
                " and clientRole.RoleTypeCT = 'POR'";


        EDITMap params = new EDITMap();

        params.put("billGroupFK", billGroupFK);

        List<ClientDetail> results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE));

        return results.toArray(new ClientDetail[results.size()]);

    }

    public ClientRole findOrCreateRoles(String roleType)
    {
        ClientRole clientRole = ClientRole.findBy_ClientDetail_RoleTypeCT(this, roleType);

        if (clientRole == null)
        {
            clientRole = saveClientRole(roleType);
        }

        return clientRole;
    }

    /**
     * Save of Primary ClientRole records
     *
     * @param roleTypeCT
     *
     * @return
     */
    public ClientRole saveClientRole(String roleTypeCT)
    {
        ClientRole clientRole = new ClientRole();
        clientRole.setClientDetail(this);
        clientRole.setRoleTypeCT(roleTypeCT);
        clientRole.setOverrideStatus("P");

        SessionHelper.saveOrUpdate(clientRole, ClientDetail.DATABASE);

        return clientRole;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ClientDetail.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ClientDetail.DATABASE);
    }

    public boolean isStatusNatural()
    {
        String gender = this.getGenderCT();

        if (gender != null)
        {
            if (gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("male"))
            {
                naturalStatus = true;
            }
        }

        return naturalStatus;
    }


    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDefaults()
    {
        this.setDateOfDeath(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
        this.setMaintDateTime(new EDITDateTime());
        this.setStatusCT("Active");
        this.setPrivacyInd("N");
        this.setProofOfDeathReceivedDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    }

    public void setDefaultTaxes(TaxInformation taxInformation)
    {
        TaxProfile taxProfile = TaxProfile.buildDefault();

        taxInformation.addTaxProfile(taxProfile);

        this.addTaxInformation(taxInformation);
    }

    public static ClientDetail[] findBy_NameForRefund(String name)
    {
        ClientDetail[] clientDetails = null;

        try
        {
            name = Util.substitute(name, "'", "''");
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        String[] nameElements = Util.fastTokenizer(name, ",");

        ClientDetailVO[] clientDetailVOs = null;

        if (nameElements.length == 2)
        {
            if (nameElements[1].equals(""))
            {
                clientDetailVOs = new ClientDetailDAO().findByLastNameWithoutOvrds(nameElements[0].trim(), false, null);
            }
            else
            {
                clientDetailVOs = new ClientDetailDAO().findByLastNamePartialFirstNameWithoutOvrds(nameElements[0].trim(), nameElements[1].trim(), false, null);
            }
        }
        else if (nameElements.length == 1)
        {
            clientDetailVOs = new ClientDetailDAO().findByPartialLastNameWithoutOvrds(nameElements[0].trim(), false, null);
        }

        clientDetails = (ClientDetail[]) CRUDEntityImpl.mapVOToEntity(clientDetailVOs, ClientDetail.class);

        return clientDetails;
    }

    public String getDatabase()
    {
        return ClientDetail.DATABASE;
    }

    /**
     * Finder.
     *
     * @param contractGroup
     *
     * @return
     */
    public static ClientDetail findBy_ContractGroup(ContractGroup contractGroup)
    {
        ClientDetail clientDetail = null;

        String hql = " select clientDetail from ClientDetail clientDetail" +
                " join clientDetail.ClientRoles clientRole" +
                " join clientRole.ContractGroups contractGroups" +
                " where contractGroups = :contractGroup";

        EDITMap params = new EDITMap("contractGroup", contractGroup);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        if (!results.isEmpty())
        {
            clientDetail = results.get(0);
        }

        return clientDetail;
    }


    /**
     * Finder.
     *
     * @param clientDetailPK
     *
     * @return
     */
    public static ClientDetail findBy_ClientDetailPK(Long clientDetailPK)
    {
        return (ClientDetail) SessionHelper.get(ClientDetail.class, clientDetailPK, ClientDetail.DATABASE);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        Set<ClientAddress> clientAddresses = this.getClientAddresses();
        staging.ContractClient stagingContractClient = new staging.ContractClient();
        stagingContractClient.setRole(stagingContext.getCurrentRoleType());
        stagingContractClient.setLastName(this.getLastName());
        stagingContractClient.setFirstName(this.getFirstName());
        stagingContractClient.setMiddleName(this.getMiddleName());
        stagingContractClient.setTaxIdentification(this.getTaxIdentification());
        stagingContractClient.setCorporateName(this.getCorporateName());
        stagingContractClient.setGender(this.getGenderCT());
        stagingContractClient.setDateOfBirth(this.getBirthDate());
        stagingContractClient.setNamePrefix(this.getNamePrefix());
        stagingContractClient.setNameSuffix(this.getNameSuffix());
        stagingContractClient.setDateOfDeath(this.getDateOfDeath());
        stagingContractClient.setNotificationReceivedDate(this.getNotificationReceivedDate());
        stagingContractClient.setProofOfDeathReceivedDate(this.getProofOfDeathReceivedDate());
        Iterator it = clientAddresses.iterator();

        while (it.hasNext())
        {
            ClientAddress clientAddress = (ClientAddress) it.next();

            if (clientAddress.getAddressTypeCT().equalsIgnoreCase(ClientAddress.CLIENT_PRIMARY_ADDRESS) &&
                clientAddress.getTerminationDate().equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
            {
                stagingContractClient.setResidentState(clientAddress.getStateCT());
                stagingContractClient.setAddressLine1(clientAddress.getAddressLine1());
                stagingContractClient.setAddressLine2(clientAddress.getAddressLine2());
                stagingContractClient.setAddressLine3(clientAddress.getAddressLine3());
                stagingContractClient.setAddressLine4(clientAddress.getAddressLine4());
                stagingContractClient.setCity(clientAddress.getCity());
                stagingContractClient.setState(clientAddress.getStateCT());
                stagingContractClient.setZip(clientAddress.getZipCode());
            }
        }

        stagingContractClient.setSegmentBase(stagingContext.getCurrentSegmentBase());
        stagingContext.getCurrentSegmentBase().addContractClient(stagingContractClient);

        SessionHelper.saveOrUpdate(stagingContractClient, database);

        return stagingContext;
    }

    /**
     * Validates the client information by calling a script.
     *
     * @return SPOutput containing script messages
     *
     * @throws SPException
     * @throws PortalEditingException
     */
    public SPOutput validateClient() throws SPException, PortalEditingException
    {
        Calculator calculatorComponent = new CalculatorComponent();
        String processName = "ClientSave";
        String effectiveDate = new EDITDate().getFormattedDate();

        ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().findProductStructureByNames("Client", "*", "*", "*", "*");

        long productStructurePK = productStructureVOs[0].getProductStructurePK();

        //  The processScript method expects a fluffy VO, get it from "this" object
        ClientDetailVO clientDetailVO = this.buildFluffyClientDetailVOFromHibernate();

        try
        {
            return calculatorComponent.processScript("ClientDetailVO", clientDetailVO, processName, "*", "*", effectiveDate, productStructurePK, false);
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
    }

    /**
     * Builds a fluffy ClientDetailVO from this Hibernate entity.  "Fluffy" means that it includes ClientAddress,
     * ContactInformation, Preference, TaxInformation, and TaxProfile
     *
     * @return  a fluffy ClientDetailVO
     */
    public ClientDetailVO buildFluffyClientDetailVOFromHibernate()
    {
        ClientDetailVO clientDetailVO = null;

        clientDetailVO = (ClientDetailVO) SessionHelper.map(this, ClientDetail.DATABASE);

        Set<ClientAddress> clientAddresses = this.getClientAddresses();
        Set<TaxInformation> taxInformations = this.getTaxInformations();
        Set<ContactInformation> contactInformations = this.getContactInformations();
        Set<Preference> preferences = this.getPreferences();

        for (Iterator iterator = clientAddresses.iterator(); iterator.hasNext();)
        {
            ClientAddress clientAddress = (ClientAddress) iterator.next();
            ClientAddressVO clientAddressVO = (ClientAddressVO) SessionHelper.map(clientAddress, ClientAddress.DATABASE);

            clientDetailVO.addClientAddressVO(clientAddressVO);
        }

        for (Iterator iterator = taxInformations.iterator(); iterator.hasNext();)
        {
            TaxInformation taxInformation = (TaxInformation) iterator.next();
            TaxInformationVO taxInformationVO = (TaxInformationVO) SessionHelper.map(taxInformation, TaxInformation.DATABASE);

            Set<TaxProfile> taxProfiles = taxInformation.getTaxProfiles();

            for (Iterator iterator1 = taxProfiles.iterator(); iterator1.hasNext();)
            {
                TaxProfile taxProfile = (TaxProfile) iterator1.next();
                TaxProfileVO taxProfileVO = (TaxProfileVO) SessionHelper.map(taxProfile, TaxProfile.DATABASE);

                taxInformationVO.addTaxProfileVO(taxProfileVO);
            }

            clientDetailVO.addTaxInformationVO(taxInformationVO);
        }

        for (Iterator iterator = contactInformations.iterator(); iterator.hasNext();)
        {
            ContactInformation contactInformation = (ContactInformation) iterator.next();
            ContactInformationVO contactInformationVO = (ContactInformationVO) SessionHelper.map(contactInformation, ContactInformation.DATABASE);

            clientDetailVO.addContactInformationVO(contactInformationVO);
        }

        for (Iterator iterator = preferences.iterator(); iterator.hasNext();)
        {
            Preference preference = (Preference) iterator.next();
            PreferenceVO preferenceVO = (PreferenceVO) SessionHelper.map(preference, Preference.DATABASE);

            clientDetailVO.addPreferenceVO(preferenceVO);
        }

        return clientDetailVO;
    }

    /**
     * Builds a fluffy hibernate ClientDetail from the specified ClientDetailVO.  "Fluffy" means that it includes ClientAddress,
     * ContactInformation, Preference, TaxInformation, and TaxProfile
     *
     * @param clientDetailVO                        fluffy ClientDetailVO from which to make fluffy hibernate ClientDetail
     *
     * @return  a fluffy hibernate ClientDetail
     */
    public static ClientDetail buildFluffyClientDetailFromVO(ClientDetailVO clientDetailVO)
    {
        ClientDetail clientDetail = (ClientDetail) SessionHelper.map(clientDetailVO, ClientDetail.DATABASE);

        ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();
        TaxInformationVO[] taxInformationVOs = clientDetailVO.getTaxInformationVO();
        ContactInformationVO[] contactInformationVOs = clientDetailVO.getContactInformationVO();
        PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();

        for (int i = 0; i < clientAddressVOs.length; i++)
        {
            ClientAddressVO clientAddressVO = clientAddressVOs[i];
            ClientAddress clientAddress = (ClientAddress) SessionHelper.map(clientAddressVO, ClientAddress.DATABASE);

            clientDetail.addClientAddress(clientAddress);
        }

        for (int i = 0; i < taxInformationVOs.length; i++)
        {
            TaxInformationVO taxInformationVO = taxInformationVOs[i];
            TaxInformation taxInformation = (TaxInformation) SessionHelper.map(taxInformationVO, TaxInformation.DATABASE);

            TaxProfileVO[] taxProfileVOs = taxInformationVO.getTaxProfileVO();

            for (int j = 0; j < taxProfileVOs.length; j++)
            {
                TaxProfileVO taxProfileVO = taxProfileVOs[j];

                TaxProfile taxProfile = (TaxProfile) SessionHelper.map(taxProfileVO, TaxProfile.DATABASE);

                taxInformation.addTaxProfile(taxProfile);
            }

            clientDetail.addTaxInformation(taxInformation);
        }

        for (int i = 0; i < contactInformationVOs.length; i++)
        {
            ContactInformationVO contactInformationVO = contactInformationVOs[i];
            ContactInformation contactInformation = (ContactInformation) SessionHelper.map(contactInformationVO, ContactInformation.DATABASE);

            clientDetail.addContactInformation(contactInformation);
        }

        for (int i = 0; i < preferenceVOs.length; i++)
        {
            PreferenceVO preferenceVO = preferenceVOs[i];
            Preference preference = (Preference) SessionHelper.map(preferenceVO, Preference.DATABASE);

            clientDetail.addPreference(preference);
        }

        return clientDetail;
    }

    /**
     * Removes dashes from the tax identification.  Dashes are okay on the front end but should not be included in
     * persistence.
     */
    public void stripTaxIdentificationOfDashes()
    {
        String taxIdentification = this.getTaxIdentification();

        if (taxIdentification != null)
        {
            taxIdentification = Util.stripString(this.getTaxIdentification(), ClientDetail.TAXID_DASH);

            this.setTaxIdentification(taxIdentification);
        }
    }
    
    /**
    * Uses a separate Session to build the folling composition:
    * 
     * ClientDetail 
     * ClientDetail.ClientRole 
     * ClientDetail.ClientRole.ContractClient
     * @param clientDetailPK
     * @return
     */
    public static ClientDetail findSeparateBy_ClientDetailPK_V1(Long clientDetailPK)
    {
        ClientDetail clientDetail = null;
        
        String hql = " select clientDetail" +
                    " from ClientDetail clientDetail" +
                    " join fetch clientDetail.ClientRoles clientRole" +
                    " join fetch clientRole.ContractClients" +
                    " where clientDetail.ClientDetailPK = :clientDetailPK";
        
        Map params = new HashMap();
        
        params.put("clientDetailPK", clientDetailPK);
        
        Session separateSession = null;
        
        try
        {
            separateSession = SessionHelper.getSeparateSession(DATABASE);
            
            List<ClientDetail> results = SessionHelper.executeHQL(separateSession, hql, params, 0);
            
            if (!results.isEmpty())
            {
                clientDetail = results.get(0); // Might be more than one result, but we don't care since we are to navigate the OO model.
            }
        }
        finally
        {
            if (separateSession != null)
            {
                separateSession.close();
            }
        }
        
        return clientDetail;
    }    
    
    /**
    * Uses a separate Session to build the folling composition:
    * 
     * ClientAddress
     * ClientAddress.ClientDetail 
     * ClientAddress.ClientDetail.ClientRole 
     * ClientAddress.ClientDetail.ClientRole.ContractClient
     * @param clientAddressPK
     * @return
     */
    public static ClientDetail findSeparateBy_ClientAddressPK_V1(Long clientAddressPK)
    {
        ClientDetail clientDetail = null;
        
        String hql = " select clientDetail" +
                    " from ClientDetail clientDetail" +
                    " join fetch clientDetail.ClientRoles clientRole" +
                    " join fetch clientRole.ContractClients" +
                    " join fetch clientDetail.ClientAddresses clientAddress" +
                    " where clientAddress.ClientAddressPK = :clientAddressPK";
        
        Map params = new HashMap();
        
        params.put("clientAddressPK", clientAddressPK);
        
        Session separateSession = null;
        
        try
        {
            separateSession = SessionHelper.getSeparateSession(DATABASE);
            
            List<ClientDetail> results = SessionHelper.executeHQL(separateSession, hql, params, 0);
            
            if (!results.isEmpty())
            {
                clientDetail = results.get(0); // Might be more than one result, but we don't care since we are to navigate the OO model.
            }
        }
        finally
        {
            if (separateSession != null)
            {
                separateSession.close();
            }
        }
        
        return clientDetail;
    }

    public static ClientDetail findByTrustType(String trustType)
    {
       ClientDetail clientDetail = null;

       String hql = "select clientDetail from ClientDetail clientDetail" +
                    " join fetch clientDetail.Preferences " +
                    " where clientDetail.TrustTypeCT = :trustType";

        EDITMap params = new EDITMap("trustType", trustType);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        if (!results.isEmpty())
        {
            clientDetail = results.get(0);
        }

        return clientDetail;
    }

    public static ClientDetail findByTrustType_CompanyFK(String trustType, Long companyFK)
    {
       ClientDetail clientDetail = null;

       String hql = "select clientDetail from ClientDetail clientDetail" +
                    " left join fetch clientDetail.Preferences " +
                    " where clientDetail.TrustTypeCT = :trustType" +
                    " and clientDetail.CompanyFK = :companyFK";

        EDITMap params = new EDITMap("trustType", trustType);
        params.put("companyFK", companyFK);

        List<ClientDetail> results = SessionHelper.executeHQL(hql, params, ClientDetail.DATABASE);

        if (!results.isEmpty())
        {
            clientDetail = results.get(0);
        }

        return clientDetail;
    }
}
