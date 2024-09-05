/*
 * User: sramamurthy
 * Date: Jul 22, 2004
 * Time: 1:33:36 PM
 *
 * 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client;

import client.dm.dao.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;


import java.util.*;

import event.*;

import org.dom4j.Element;

import org.hibernate.Session;


public class ClientAddress extends HibernateEntity implements CRUDEntity
{
    public static final String CLIENT_PRIMARY_ADDRESS = "PrimaryAddress";
    public static final String CLIENT_SECONDARY_ADDRESS = "SecondaryAddress";
    public static final String CLIENT_BUSINESS_ADDRESS = "BusinessAddress";

    private static final String DEFAULT_START_DATE_MONTH = "00";
    private static final String DEFAULT_START_DATE_DAY   = "00";
    private static final String DEFAULT_STOP_DATE_MONTH  = "12";
    private static final String DEFAULT_STOP_DATE_DAY    = "99";

    private CRUDEntityI crudEntityImpl;
    private ClientAddressVO clientAddressVO;
    private ClientDetail clientDetail;
    private Set suspenses = new HashSet();

    /**
     * In support of the SEGElement interface.
     */
    private Element clientAddressElement;


    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a ClientAddress entity with a default ClientAddressVO.
     */
    public ClientAddress()
    {
        init();
    }

    /**
     * Instantiates a ClientAddress entity with a ClientAddressVO retrieved from persistence.
     *
     * @param clientAddressPK
     */
    public ClientAddress(long clientAddressPK)
    {
        init();

        crudEntityImpl.load(this, clientAddressPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ClientAddress entity with a supplied ClientAddressVO.
     *
     * @param clientAddressVO
     */
    public ClientAddress(ClientAddressVO clientAddressVO)
    {
        init();

        this.clientAddressVO = clientAddressVO;
    }

    /**
     * Instantiates a ClientAddress entity with a supplied ClientAddressVO and entity clientDetail.
     *
     * @param clientAddressVO
     */
    public ClientAddress(ClientAddressVO clientAddressVO, ClientDetail clientDetail)
    {
        init();

        this.clientAddressVO = clientAddressVO;
        this.clientDetail = clientDetail;
    }

    public Long getClientDetailFK()
    {
        return SessionHelper.getPKValue(clientAddressVO.getClientDetailFK());
    }
     //-- long getClientDetailFK() 

    public void setClientDetailFK(Long clientDetailFK)
    {
        clientAddressVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    }
     //-- void setClientDetailFK(long)

   /**
     * Adds Suspense.
     * @param suspense
     */
    public void addSuspense(Suspense suspense)
    {
        getSuspenses().add(suspense);

        suspense.setClientAddress(this);

//        SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);
    }       

    /**
     * Getter..
     * @return
     */
    public String getAddressLine1()
    {
        return clientAddressVO.getAddressLine1();
    }

    /**
     * Getter..
     * @return
     */
    public String getAddressLine2()
    {
        return clientAddressVO.getAddressLine2();
    }

    /**
     * Getter..
     * @return
     */
    public String getAddressLine3()
    {
        return clientAddressVO.getAddressLine3();
    }

    /**
     * Getter..
     * @return
     */
    public String getAddressLine4()
    {
        return clientAddressVO.getAddressLine4();
    }

    /**
     * Getter..
     * @return
     */
    public String getAddressTypeCT()
    {
        return clientAddressVO.getAddressTypeCT();
    }

    /**
     * Getter..
     * @return
     */
    public String getCity()
    {
        return clientAddressVO.getCity();
    }

    /**
     * Getter..
     * @return
     */
    public Long getClientAddressPK()
    {
        return SessionHelper.getPKValue(clientAddressVO.getClientAddressPK());
    }

    /**
     * Getter..
     * @return
     */
    public String getCountryCT()
    {
        return clientAddressVO.getCountryCT();
    }

    /**
     * Getter..
     * @return
     */
    public String getCounty()
    {
        return clientAddressVO.getCounty();
    }

    /**
     * Getter..
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(clientAddressVO.getEffectiveDate());
    }

    /**
     * Getter..
     * @return
     */
    public int getSequenceNumber()
    {
        return clientAddressVO.getSequenceNumber();
    }

    /**
     * Getter..
     * @return
     */
    public String getStartDate()
    {
        return clientAddressVO.getStartDate();
    }

    /**
     * Getter..
     * @return
     */
    public String getStateCT()
    {
        return clientAddressVO.getStateCT();
    }

    /**
     * Getter..
     * @return
     */
    public String getStopDate()
    {
        return clientAddressVO.getStopDate();
    }

    /**
     * Getter..
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return SessionHelper.getEDITDate(clientAddressVO.getTerminationDate());
    }

    /**
     * Getter..
     * @return
     */
    public String getZipCode()
    {
        return clientAddressVO.getZipCode();
    }
    
    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.clientAddressVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    }
    
    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(this.clientAddressVO.getMaintDateTime());
    }
    
    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.clientAddressVO.setOperator(operator);
    }
    
    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return this.clientAddressVO.getOperator();
    }

    /**
     * Getter.
     * @return
     */
    public String getZipCodePlacementCT()
    {
        return clientAddressVO.getZipCodePlacementCT();
    }

    /**
     * Getter
     * @return
     */
    public String getOverrideStatus()
    {
        return clientAddressVO.getOverrideStatus();
    } //-- java.lang.String getOverrideStatus()

    /**
     * Setter.
     * @param addressLine1
     */
    public void setAddressLine1(String addressLine1)
    {
        clientAddressVO.setAddressLine1(addressLine1);
    }

    /**
     * Setter.
     * @param addressLine2
     */
    public void setAddressLine2(String addressLine2)
    {
        clientAddressVO.setAddressLine2(addressLine2);
    }

    /**
     * Setter.
     * @param addressLine3
     */
    public void setAddressLine3(String addressLine3)
    {
        clientAddressVO.setAddressLine3(addressLine3);
    }

    /**
     * Setter.
     * @param addressLine4
     */
    public void setAddressLine4(String addressLine4)
    {
        clientAddressVO.setAddressLine4(addressLine4);
    }

    /**
     * Setter.
     * @param addressTypeCT
     */
    public void setAddressTypeCT(String addressTypeCT)
    {
        clientAddressVO.setAddressTypeCT(addressTypeCT);
    }

    /**
     * Setter.
     * @param city
     */
    public void setCity(String city)
    {
        clientAddressVO.setCity(city);
    }

    /**
     * Setter.
     * @param clientAddressPK
     */
    public void setClientAddressPK(Long clientAddressPK)
    {
        clientAddressVO.setClientAddressPK(SessionHelper.getPKValue(clientAddressPK));
    }

    /**
     * Setter.
     * @param countryCT
     */
    public void setCountryCT(String countryCT)
    {
        clientAddressVO.setCountryCT(countryCT);
    }

    /**
     * Setter.
     * @param county
     */
    public void setCounty(String county)
    {
        clientAddressVO.setCounty(county);
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        clientAddressVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    /**
     * Setter.
     * @param sequenceNumber
     */
    public void setSequenceNumber(int sequenceNumber)
    {
        clientAddressVO.setSequenceNumber(sequenceNumber);
    }

    /**
     * Setter.
     * @param startDate
     */
    public void setStartDate(String startDate)
    {
        clientAddressVO.setStartDate(startDate);
    }

    /**
     * Setter.
     * @param stateCT
     */
    public void setStateCT(String stateCT)
    {
        clientAddressVO.setStateCT(stateCT);
    }

    /**
     * Setter.
     * @param stopDate
     */
    public void setStopDate(String stopDate)
    {
        clientAddressVO.setStopDate(stopDate);
    }

    /**
     * Setter.
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        clientAddressVO.setTerminationDate(SessionHelper.getEDITDate(terminationDate));
    }

    /**
     * Setter.
     * @param zipCode
     */
    public void setZipCode(String zipCode)
    {
        clientAddressVO.setZipCode(zipCode);
    }

    /**
     * Setter.
     * @param zipCodePlacementCT
     */
    public void setZipCodePlacementCT(String zipCodePlacementCT)
    {
        clientAddressVO.setZipCodePlacementCT(zipCodePlacementCT);
    }

   /**
      * Setter
      * @param overrideStatus
      */
     public void setOverrideStatus(String overrideStatus)
     {
         clientAddressVO.setOverrideStatus(overrideStatus);
     } //-- void setOverrideStatus(java.lang.String)

    /**
     * Getter.
     * @return
     */
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Setter.
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

   /**
     * Getter.
     * @return
     */
    public Set getSuspenses()
    {
        return suspenses;
    }

    /**
     * Setter.
     * @param suspenses
     */
    public void setSuspenses(Set suspenses)
    {
        this.suspenses = suspenses;
    }


    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (clientAddressVO == null)
        {
            clientAddressVO = new ClientAddressVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws Throwable
    {
        try
        {
            if (clientDetail.getSaveChangeHistory())
            {
                checkForNonFinancialChanges();
            }
            clientDetail.addClientAddress(this);
        }
        catch (Throwable t)
        {
            System.out.println(t);

            t.printStackTrace();
        }
    }

    /**
     * Compare to existing data base table, using the ChangeProcessor.  For the ChangeVOs
     * returned, generate ChangeHistory.
     */
    private void checkForNonFinancialChanges()
    {
        ChangeProcessor changeProcessor = new ChangeProcessor();
        Change[] changes = changeProcessor.checkForChanges(clientAddressVO, clientAddressVO.getVoShouldBeDeleted(), ConnectionFactory.EDITSOLUTIONS_POOL, null);
        ChangeHistory changeHistory = null;

        String operator = clientDetail.getOperator();

        if (changes != null)
        {
            for (int i = 0; i < changes.length; i++)
            {
                 if (changes[i].getStatus() == (Change.DELETED))
                {
                    String property = "AddressLine1";
                    changeHistory = changeProcessor.processForDelete(changes[i], this, operator, property, clientDetail.getPK());
                }
            }
        }
    }
    
    private ChangeHistoryCorrespondenceVO setupCorrespondence(ChangeHistory changeHistory)
    {
        ChangeHistoryCorrespondenceVO changeHistoryCorrespondenceVO = new ChangeHistoryCorrespondenceVO();

        changeHistoryCorrespondenceVO.setChangeHistoryCorrespondencePK(0);
        changeHistoryCorrespondenceVO.setChangeHistoryFK(changeHistory.getPK());
        changeHistoryCorrespondenceVO.setStatus("P");
        changeHistoryCorrespondenceVO.setCorrespondenceDate(new EDITDateTime().getFormattedDateTime());
        changeHistoryCorrespondenceVO.setAddressTypeCT(this.getAddressTypeCT());

        return changeHistoryCorrespondenceVO;
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

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return clientAddressVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return clientAddressVO.getClientAddressPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.clientAddressVO = (ClientAddressVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /************************************** Static Methods **************************************/
    /**
     * Returns the default start date.  This should go in the constructor when the ClientAddress class is finally made
     * into a real object instead of a front for a VO.
     * @return
     */
    public static String defaultStartDate()
    {
        return ClientAddress.DEFAULT_START_DATE_MONTH + EDITDate.DATE_DELIMITER + ClientAddress.DEFAULT_START_DATE_DAY;
    }

    /**
     * Returns the default stop date.  This should go in the constructor when the ClientAddress class is finally made
     * into a real object instead of a front for a VO.
     * @return
     */
    public static String defaultStopDate()
    {
        return ClientAddress.DEFAULT_STOP_DATE_MONTH + EDITDate.DATE_DELIMITER + ClientAddress.DEFAULT_STOP_DATE_DAY;
    }

    /**
     * Returns the month portion of the start date.  This is a temporary method to encapsulate date manipulation in one
     * place.  StartDate is not a true date since it is only month and day and can have illegal months and days (like 00
     * or 99), therefore, it does not belong in EDITDate.  This should be turned into a more useful method when the
     * ClientAddress is made into a real object instead of a front for a VO.
     * <P>
     * NOTE: this method is repeated for stopDate simply because the signature was more useful to the calling program
     * than combining the methods.
     *
     * @param startDate                 month and day (mm/dd)
     *
     * @return month portion of startDate
     */
    public static String getStartDateMonth(String startDate)
    {
        StringTokenizer tokens = new StringTokenizer(startDate, EDITDate.DATE_DELIMITER);

        String month = tokens.nextToken();

        String day = tokens.nextToken();

        return month;
    }

    /**
     * Returns the day portion of the start date.  This is a temporary method to encapsulate date manipulation in one
     * place.  StartDate is not a true date since it is only month and day and can have illegal months and days (like 00
     * or 99), therefore, it does not belong in EDITDate.  This should be turned into a more useful method when the
     * ClientAddress is made into a real object instead of a front for a VO.
     * <P>
     * NOTE: this method is repeated for stopDate simply because the signature was more useful to the calling program
     * than combining the methods.
     *
     * @param startDate                 month and day (mm/dd)
     *
     * @return day portion of startDate
     */
    public static String getStartDateDay(String startDate)
    {
        StringTokenizer tokens = new StringTokenizer(startDate, EDITDate.DATE_DELIMITER);

        String month = tokens.nextToken();

        String day = tokens.nextToken();

        return day;
    }

    /**
     * Returns the month portion of the start date.  This is a temporary method to encapsulate date manipulation in one
     * place.  StopDate is not a true date since it is only month and day and can have illegal months and days (like 00
     * or 99), therefore, it does not belong in EDITDate.  This should be turned into a more useful method when the
     * ClientAddress is made into a real object instead of a front for a VO.
     * <P>
     * NOTE: this method is repeated for startDate simply because the signature was more useful to the calling program
     * than combining the methods.
     *
     * @param stopDate                 month and day (mm/dd)
     *
     * @return month portion of stopDate
     */
    public static String getStopDateMonth(String stopDate)
    {
        StringTokenizer tokens = new StringTokenizer(stopDate, EDITDate.DATE_DELIMITER);

        String month = tokens.nextToken();

        String day = tokens.nextToken();

        return month;
    }

    /**
     * Returns the day portion of the stop date.  This is a temporary method to encapsulate date manipulation in one
     * place.  StopDate is not a true date since it is only month and day and can have illegal months and days (like 00
     * or 99), therefore, it does not belong in EDITDate.  This should be turned into a more useful method when the
     * ClientAddress is made into a real object instead of a front for a VO.
     * <P>
     * NOTE: this method is repeated for startDate simply because the signature was more useful to the calling program
     * than combining the methods.
     *
     * @param stopDate                 month and day (mm/dd)
     *
     * @return day portion of stopDate
     */
    public static String getStopDateDay(String stopDate)
    {
        StringTokenizer tokens = new StringTokenizer(stopDate, EDITDate.DATE_DELIMITER);

        String month = tokens.nextToken();

        String day = tokens.nextToken();

        return day;
    }

    /**
     * Builds the start date by concatenating the given month and day separated by the date delimiter
     * <P>
     * NOTE: this method is repeated for stopDate simply because the signature was more useful to the calling program
     * than combining the methods.
     *
     * @param startMonth            month (mm)
     * @param startDay              day (dd)
     *
     * @return string containing the month and day separated by the date delimiter
     */
    public static String buildStartDate(String startMonth, String startDay)
    {
        return startMonth + EDITDate.DATE_DELIMITER + startDay;
    }

    /**
     * Builds the stop date by concatenating the given month and day separated by the date delimiter
     * <P>
     * NOTE: this method is repeated for startDate simply because the signature was more useful to the calling program
     * than combining the methods.
     *
     * @param stopMonth            month (mm)
     * @param stopDay              day (dd)
     *
     * @return string containing the month and day separated by the date delimiter
     */
    public static String buildStopDate(String stopMonth, String stopDay)
    {
        return stopMonth + EDITDate.DATE_DELIMITER + stopDay;
    }

    /**
     * Finder method for client address by address type
     * @param clientDetailPK
     * @param addressType
     * @return
     */
    public static ClientAddress findByClientDetailPK_And_AddressTypeCT(long clientDetailPK, String addressType)
    {
        ClientAddress clientAddress = null;

        ClientAddressVO[] clientAddressVOs = (ClientAddressVO[]) new ClientAddressDAO().findByClientDetailPK_AND_AddressTypeCT(clientDetailPK, addressType, false, null);

        if ((clientAddressVOs != null) && (clientAddressVOs.length > 0))
        {
            clientAddress = new ClientAddress(clientAddressVOs[0]);
        }

        return clientAddress;
    }

    public static ClientAddress findByPK(long clientAddressPK)
    {
        ClientAddress clientAddress = null;

        ClientAddressVO[] clientAddressVOs = (ClientAddressVO[]) new ClientAddressDAO().findByPK(clientAddressPK, false, null);

        if ((clientAddressVOs != null) && (clientAddressVOs.length > 0))
        {
            clientAddress = new ClientAddress(clientAddressVOs[0]);
        }

        return clientAddress;
    }


    public static ClientAddress[] findAllActiveByClientDetailPK(long clientDetailPK)
    {
        ClientAddress[] clientAddresses = null;

        ClientAddressVO[] clientAddressVOs = (ClientAddressVO[]) new ClientAddressDAO().findAllActiveByClientDetailPK(clientDetailPK);

        if ((clientAddressVOs != null) && (clientAddressVOs.length > 0))
        {
            clientAddresses = new ClientAddress[clientAddressVOs.length];

            for (int i = 0; i < clientAddressVOs.length; i++)
            {
                clientAddresses[i] = new ClientAddress(clientAddressVOs[i]);
            }
        }

        return clientAddresses;
    }

    /**
     * Finder by ClientDetail and AddressTypeCT.
     * @param clientDetail
     * @param addressTypeCT
     * @return
     */
    public static final ClientAddress findByClientDetail_And_AddressTypeCT(ClientDetail clientDetail, String addressTypeCT)
    {
        ClientAddress clientAddress = null;

        String hql = "select ca from ClientAddress ca where ca.ClientDetail = :clientDetail " +
                     " and ca.AddressTypeCT like :addressTypeCT" +
                     " and ca.TerminationDate = :editDateMaxDate";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);

        params.put("addressTypeCT", addressTypeCT);
        params.put("editDateMaxDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            clientAddress = (ClientAddress) results.get(0);
        }

        return clientAddress;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, SessionHelper.EDITSOLUTIONS);
    }

    public static ClientAddress findCurrentAddress(Long clientDetailFK)
    {
        ClientAddress clientAddress = null;

       String hql = "select clientAddress from ClientAddress clientAddress " +
                     " where clientAddress.ClientDetailFK = :clientDetailFK " +
                     " and (clientAddress.AddressTypeCT = :primaryValue " +
                     " or clientAddress.AddressTypeCT = :secondaryValue) " +
                     " and clientAddress.TerminationDate >= :currentDate and clientAddress.OverrideStatus is null" +
                     " and clientAddress.StopDate = (select min(clientAddress.StopDate) from clientAddress " +
                     " where clientAddress.ClientDetailFK = :clientDetailFK " +
                     " and (clientAddress.AddressTypeCT = :primaryValue " +
                     " or clientAddress.AddressTypeCT = :secondaryValue) " +
                     " and (clientAddress.TerminationDate >= :currentDate and clientAddress.OverrideStatus is null" +
                     " and clientAddress.StartDate <= :currentMonthDay " +
                     " and clientAddress.StopDate >= :currentMonthDay))";
        
        Map params = new HashMap();

        EDITDate currentDate = new EDITDate();
        String currentMonthDay = currentDate.getFormattedMonthAndDay();
        params.put("clientDetailFK", clientDetailFK);
        params.put("primaryValue", "PrimaryAddress");
        params.put("secondaryValue", "SecondaryAddress");
        params.put("currentDate", currentDate);
        params.put("currentMonthDay", currentMonthDay);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            clientAddress = (ClientAddress) results.get(0);
        }

        return clientAddress;
    }

    public static ClientAddress findByPK(Long clientAddressPK)
    {
        return (ClientAddress) SessionHelper.get(ClientAddress.class, clientAddressPK, SessionHelper.EDITSOLUTIONS);

    }

    public void setAddressDefaults(Long clientDetailPK)
    {
        int sequenceNumber = getMaxSequenceNumber(clientDetailPK) + 1;
        this.setSequenceNumber(sequenceNumber);
        this.setEffectiveDate(new EDITDate(EDITDate.DEFAULT_MIN_DATE));
        this.setStartDate(ClientAddress.defaultStartDate());
        this.setStopDate(ClientAddress.defaultStopDate());
        this.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    }

    public static ClientAddress findCurrentAddressForClientAndAddressType(Long clientDetailPK, String addressTypeCT)
    {
       ClientAddress clientAddress = null;

         String hql = "select clientAddress from ClientAddress clientAddress " +
                     " where clientAddress.ClientDetailFK = :clientDetailFK " +
                     " and clientAddress.AddressTypeCT = :addressTypeCT " +
                     " and clientAddress.TerminationDate >= :currentDate " +
                     " and clientAddress.StopDate = (select min(clientAddress.StopDate) from clientAddress " +
                     " where clientAddress.ClientDetailFK = :clientDetailFK " +
                     " and (clientAddress.TerminationDate >= :currentDate " +
                     " and clientAddress.StartDate <= :currentMonthDay " +
                     " and clientAddress.StopDate >= :currentMonthDay))";

        Map params = new HashMap();

        EDITDate currentDate = new EDITDate();
        String currentMonthDay = currentDate.getFormattedMonthAndDay();

        params.put("clientDetailFK", clientDetailPK);
        params.put("addressTypeCT", "addressTypeCT");
        params.put("currentDate", currentDate);
        params.put("currentMonthDay", currentMonthDay);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            clientAddress = (ClientAddress) results.get(0);
        }

        return clientAddress;
    }

    public static int getMaxSequenceNumber(Long clientDetailPK)
    {
        int sequenceNumber = 0;

        String hql = "select max(SequenceNumber) from ClientAddress clientAddress " +
                     "where clientAddress.ClientDetailFK = :clientDetailPK";

        Map params = new HashMap();
        params.put("clientDetailPK", clientDetailPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.contains(null))
        {
            sequenceNumber = ((Integer)results.get(0)).intValue();
        }

        return sequenceNumber;
    }

	/**
     * Finder by TaxID and AddressTypeCT.
     * @param clientDetail
     * @param addressTypeCT
     * @return
     */
    public static final ClientAddress findByTaxID_And_AddressTypeCT(String taxID, String addressTypeCT)
    {
        ClientAddress clientAddress = null;

        String hql = "select clientAddress from ClientAddress clientAddress " +
                     "join clientAddress.ClientDetail clientDetail " +
                     "where clientDetail.TaxIdentification = :taxID " +
                     "and clientAddress.AddressTypeCT like :addressTypeCT";

        Map params = new HashMap();

        params.put("taxID", taxID);
        params.put("addressTypeCT", addressTypeCT);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            clientAddress = (ClientAddress) results.get(0);
        }

        return clientAddress;

   }
 
    public String getDatabase()
    {
        return ClientAddress.DATABASE;
    }
}
