/*
 * User: sprasad
 * Date: Mar 2, 2005
 * Time: 5:12:57 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.EDITBigDecimal;
import edit.common.exceptions.EDITEngineException;
import edit.common.vo.ChargeCodeVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.dm.dao.ChargeCodeDAO;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class ChargeCode extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private ChargeCodeVO chargeCodeVO;

    private FilteredFund filteredFund;  // parent
    private Set unitValues;      // child

    private String chargeCode;
    private EDITBigDecimal accumulatedPremium;
    private String clientFundNumber;
    private String newIssuesIndicatorCT;

    private Set controlBalances;

    /**  dup charge error msg */
    private static final String DUP_CHARGE_CODE_ERROR_MSG =
            "This filtered fund already has this charge code: ";

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    /**
     * Instantiates a ChargeCode entity with a default ChargeCodeVO.
     */
    public ChargeCode()
    {
        init();
    }

    /**
     * Instantiates a ChargeCode entity with a supplied ChargeCodeVO.
     *
     * @param chargeCodeVO
     */
    public ChargeCode(ChargeCodeVO chargeCodeVO)
    {
        init();

        this.chargeCodeVO = chargeCodeVO;
    }

    /**
     * Getter.
     * @return
     */
    public Long getChargeCodePK()
    {
        return SessionHelper.getPKValue(this.chargeCodeVO.getChargeCodePK());
    }

    /**
     * Setter.
     * @param chargeCodePK
     */
    public void setChargeCodePK(Long chargeCodePK)
    {
        this.chargeCodeVO.setChargeCodePK(SessionHelper.getPKValue(chargeCodePK));
    }

    /**
     * Setter.
     * @param filteredFundFK
     */
    public void setFilteredFundFK(Long filteredFundFK)
    {
        this.chargeCodeVO.setFilteredFundFK(SessionHelper.getPKValue(filteredFundFK));
    }

    /**
     * Getter.
     * @return
     */
    public String getChargeCode()
    {
        return this.chargeCodeVO.getChargeCode();
    }

    /**
     * Setter.
     * @param chargeCode
     */
    public void setChargeCode(String chargeCode)
    {
        this.chargeCodeVO.setChargeCode(chargeCode);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAccumulatedPremium()
    {
        return SessionHelper.getEDITBigDecimal(this.chargeCodeVO.getAccumulatedPremium());
    }

    /**
     * Setter.
     * @param accumulatedPremium
     */
    public void setAccumulatedPremium(EDITBigDecimal accumulatedPremium)
    {
        this.chargeCodeVO.setAccumulatedPremium(SessionHelper.getEDITBigDecimal(accumulatedPremium));
    }

    /**
     * Getter.
     * @return
     */
    public String getClientFundNumber()
    {
        return this.chargeCodeVO.getClientFundNumber();
    }

    /**
     * Setter.
     * @param clientFundNumber
     */
    public void setClientFundNumber(String clientFundNumber)
    {
        this.chargeCodeVO.setClientFundNumber(clientFundNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getNewIssuesIndicatorCT()
    {
        return this.chargeCodeVO.getNewIssuesIndicatorCT();
    }

    /**
     * Setter.
     * @param newIssuesIndicatorCT
     */
    public void setNewIssuesIndicatorCT(String newIssuesIndicatorCT)
    {
        this.chargeCodeVO.setNewIssuesIndicatorCT(newIssuesIndicatorCT);
    }

    /**
     * Getter.
     * @return
     */
    public Set getControlBalances()
    {
        return controlBalances;
    }

    /**
     * Setter.
     * @param controlBalances
     */
    public void setControlBalances(Set controlBalances)
    {
        this.controlBalances = controlBalances;
    }

    /**
     * Getter.
     * @return
     */
    public FilteredFund getFilteredFund()
    {
        return this.filteredFund;
    }

    /**
     * Setter.
     * @param filteredFund
     */
    public void setFilteredFund(FilteredFund filteredFund)
    {
        this.filteredFund = filteredFund;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (chargeCodeVO == null)
        {
            chargeCodeVO = new ChargeCodeVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws EDITEngineException
    {

        checkForRequiredValues();

        try
        {
            crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);

            FilteredFund filteredFund = getFilteredFundOld();

            updateFilteredFund("SAVE", filteredFund);

        }
        catch (Exception e)
        {
            throw new EDITEngineException(e.getMessage());
        }
    }

    /**
     * If we are saving a ChargeCode, we make sure that the FilteredFund's
     * charge code indicator is set to Y.  If we are deleting a charge code
     * we make sure that if no charge codes are left for the filtered fund,
     * then we set the indicator = N.
     * @param updateType String SAVE or DELETE
     * @throws Exception
     */
    private void updateFilteredFund(String updateType, FilteredFund filteredFund) throws Exception
    {

        FilteredFundVO filteredFundVO = (FilteredFundVO) filteredFund.getVO();

        long filteredFundPK = filteredFundVO.getFilteredFundPK();

        String chargeCodeInd = filteredFundVO.getChargeCodeIndicator();

        if ("SAVE".equals(updateType))
        {
            if (! "Y".equals(chargeCodeInd))  // handles both null and N
            {

                filteredFundVO.setChargeCodeIndicator("Y");

                filteredFund.save();
            }
        }
        else if ("DELETE".equals(updateType))
        {
            ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundPK(filteredFundPK);

            if (chargeCodes == null || chargeCodes.length == 0)
            {
                filteredFundVO.setChargeCodeIndicator("N");

                filteredFund.save();
            }

        }
        else
        {
            throw new RuntimeException("bad updateType in ChargeCode.updateFilteredFund");
        }

    }


    /**
     * Return true only if doing an insert.  Needed because charge code must
     * be unique for its filtered fund.  Will see if it already exists
     * only if we are inserting for the first time.
     * @return
     */
    private boolean isAnInsert()
    {
       return (getPK() == 0);
    }


    /**
     * See if the charge code string already is being used for the filtered fund.
     * @param chargeCodeString
     * @param filteredFundFK
     * @return
     */
    private boolean chargeCodeExistsForFilteredFund(String chargeCodeString, Long filteredFundFK)
    {
        ChargeCode[] chargeCodes = findByFilteredFundPK(filteredFundFK.longValue());

        if (chargeCodes == null) return false;

        boolean isADuplicate = false;

        for (int i = 0; i < chargeCodes.length; i++)
        {
            ChargeCode aChargeCode = chargeCodes[i];

            ChargeCodeVO ccVO = (ChargeCodeVO) aChargeCode.getVO();

            String aCCString = ccVO.getChargeCode();

            if (aCCString.equalsIgnoreCase(chargeCodeString))
            {
                return true;
            }
        }

        return false;

    }

    /**
      * Get the filtered fund FK.
      * @return long - the filtered fund FK
      */
    public Long getFilteredFundFK()
    {
        return SessionHelper.getPKValue(this.chargeCodeVO.getFilteredFundFK());
    }


    /**
     * Return the FilteredFund for this ChargeCode.
     */
    private FilteredFund getFilteredFundOld() throws EDITEngineException
    {
        FilteredFund filteredFund = null;

        Long ffundFK = new Long(0);

        try
        {
            ffundFK = getFilteredFundFK();

            filteredFund = new FilteredFund(ffundFK.longValue());

            return filteredFund;
        }
        catch (Exception e)
        {
            throw new EDITEngineException(
                    "! Cannot get the FilteredFund for ffundPK = " +
                    ffundFK);
        }
    }

    /**
     * Does business rule checks when saving a ChargeCode.  The chargecode
     * plus the filtered fund FK together is supposed to be unique.
     * @throws EDITEngineException
     */
    private void checkForRequiredValues() throws EDITEngineException
    {
        if (isAnInsert())    // dup problem only makes sense for insert
        {
            String theChargeCodeString = chargeCodeVO.getChargeCode();

            Long ffFK = getFilteredFundFK();

            if (chargeCodeExistsForFilteredFund(theChargeCodeString, ffFK))
            {
                throw new EDITEngineException(DUP_CHARGE_CODE_ERROR_MSG + theChargeCodeString);
            }
        }

    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITEngineException
    {
        try
        {

            FilteredFund filteredFund = getFilteredFundOld();

            crudEntityImpl.delete(this, ConnectionFactory.ENGINE_POOL);

            updateFilteredFund("DELETE", filteredFund);

        }
        catch (Exception e)
        {
            throw new EDITEngineException(e.getMessage());
        }
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return chargeCodeVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return chargeCodeVO.getChargeCodePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        chargeCodeVO = (ChargeCodeVO) voObject;
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

    public Set getUnitValues()
    {
        return this.unitValues;
    }

    public void setUnitValues(Set unitValues)
    {
        this.unitValues = unitValues;
    }

    public void addUnitValues(UnitValues unitValues)
    {
        getUnitValues().add(unitValues);

        unitValues.setChargeCode(this);

        SessionHelper.saveOrUpdate(unitValues, ChargeCode.DATABASE);
    }

    public void removeUnitValues(UnitValues unitValues)
    {
        getUnitValues().remove(unitValues);

        unitValues.setChargeCode(null);

        SessionHelper.saveOrUpdate(unitValues, ChargeCode.DATABASE);
    }

    /**
     * Finder by PK.
     * @param chargeCodePK
     * @return
     */
    public static final ChargeCode findByPK(Long chargeCodePK)
    {
        return (ChargeCode) SessionHelper.get(ChargeCode.class, chargeCodePK, ChargeCode.DATABASE);
    }

    /**
     * Finder.
     *
     * @param chargeCodePK
     */
    public static final ChargeCode findByPK(long chargeCodePK)
    {
        ChargeCode chargeCode = null;

        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeDAO().findByPK(chargeCodePK);

        if (chargeCodeVOs != null)
        {
            chargeCode = new ChargeCode(chargeCodeVOs[0]);
        }

        return chargeCode;
    }

    public static final ChargeCode[] findByFilteredFundPK(long filteredFundPK)
    {
        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeDAO().findByFilteredFundPK(filteredFundPK);

        return (ChargeCode[]) CRUDEntityImpl.mapVOToEntity(chargeCodeVOs, ChargeCode.class);
    }

    /**
     * Get the ChargeCode for a given filtered fund FK and charge code.
     * @param filteredFundPK
     * @param chargeCode
     * @return the ChargeCode CRUDEntity
     */
    public static final ChargeCode findByFilteredFundPK(long filteredFundPK, String chargeCode)
    {
        if (chargeCode == null || chargeCode.trim().length()==0)
        {
            return null;
        }

        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeDAO().findByFilteredFundPK(filteredFundPK);

        for (int i = 0; i < chargeCodeVOs.length; i++)
        {
            ChargeCodeVO chargeCodeVO = chargeCodeVOs[i];

            if (chargeCode.equals(chargeCodeVO.getChargeCode()))
            {

                return new ChargeCode(chargeCodeVO);

            }

        }
        return null;
    }

    /**
     * Find the ChargeCode record that is the first whose accumulated premium is
     * greater than or equal to the accumlated premium passed in.
     *
     * @param filteredFundPK    the foreign key
     * @param accumulatedPremium   the accumlated premium
     * @return
     */
    public static final ChargeCode findByFilteredFund(long filteredFundPK, String newIssuesEligibilityStatus)
     {
         if (filteredFundPK == 0L)
         {
             throw new IllegalArgumentException("need a filteredFundPK");
         }

         ChargeCodeVO[] chargeCodeVOs =
                  new ChargeCodeDAO().findByFilteredFunds(filteredFundPK);

         // this CRUDEntity now does the comparison logic instead of the DAO
         if (chargeCodeVOs == null || chargeCodeVOs.length == 0)
         {
             return null;
         }

         ChargeCodeVO chargeCodeVO = null;

         if (newIssuesEligibilityStatus != null)
         {
             chargeCodeVO = checkForNewIssues(chargeCodeVOs, newIssuesEligibilityStatus);
         }


         return new ChargeCode(chargeCodeVO);
     }

    /**
     * Find the ChargeCode record that is the first whose accumulated premium is
     * greater than or equal to the accumlated premium passed in.
     *
     * @param filteredFundPK    the foreign key
     * @param accumulatedPremium   the accumlated premium
     * @return
     */
    public static final ChargeCode findByFilteredFundAndAccumPremium(long filteredFundPK, EDITBigDecimal accumulatedPremium, String newIssuesEligibilityStatus)
     {
         // not assertions - always check
         if (accumulatedPremium == null)
         {
             throw new IllegalArgumentException("need an accumulated premium");
         }

         if (filteredFundPK == 0L)
         {
             throw new IllegalArgumentException("need a filteredFundPK");
         }

         ChargeCodeVO[] chargeCodeVOs =
                  new ChargeCodeDAO().findByFilteredFundsOrderedOnAccumPremium(filteredFundPK);

         // this CRUDEntity now does the comparison logic instead of the DAO
         if (chargeCodeVOs == null || chargeCodeVOs.length == 0)
         {
             return null;
         }

         ChargeCodeVO chargeCodeVO = null;

         if (newIssuesEligibilityStatus != null)
         {
             chargeCodeVO = checkForNewIssues(chargeCodeVOs, newIssuesEligibilityStatus);
         }

         if (chargeCodeVO == null)
         {
             for (int i = 0; i < chargeCodeVOs.length; i++)
             {
                 chargeCodeVO = chargeCodeVOs[i];

                 BigDecimal accum = chargeCodeVO.getAccumulatedPremium();

                 EDITBigDecimal EDITaccum = new EDITBigDecimal(accum);

                 if (EDITaccum.isGTE(accumulatedPremium))
                 {
                     return new ChargeCode(chargeCodeVO);
                 }

             }
         }
         else
         {
            return new ChargeCode(chargeCodeVO);
         }

         // Just in case ... if the accumulated premium they have is greater
         // than all of the breakpoint accum premiums, choose the chargecode
         // with the highest.

         // Note the length is > 0 at this point
         return new ChargeCode(chargeCodeVOs[chargeCodeVOs.length - 1]);

     }

    private static ChargeCodeVO checkForNewIssues(ChargeCodeVO[] chargeCodeVOs, String newIssuesEligibilityStatus)
    {
        ChargeCodeVO chargeCodeVO = null;

        for (int i = 0; i < chargeCodeVOs.length; i++)
        {
            if (chargeCodeVOs[i].getNewIssuesIndicatorCT() != null)
            {
                if (chargeCodeVOs[i].getNewIssuesIndicatorCT().equalsIgnoreCase(newIssuesEligibilityStatus))
                {
                    chargeCodeVO = chargeCodeVOs[i];
                    break;
                }
            }
        }

        return chargeCodeVO;
    }


    //        /// FOR TESTING
        //
        //        EDITBigDecimal accumulatedPremium = new EDITBigDecimal("1500000");
        //        ChargeCode chargeCodeTest =
        //                ChargeCode.findByFilteredFundAndAccumPremium(filteredFundPK, accumulatedPremium);
        //        ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCodeTest.getVO();
        //        String chargeCodeNum = chargeCodeVO.getChargeCode();
        //        long chargeCodePK = chargeCodeVO.getChargeCodePK();
        //        BigDecimal chargeCodeAccumPremium = chargeCodeVO.getAccumulatedPremium();
        //        System.out.println("charge code num is " + chargeCodeNum);
        //        System.out.println("accum found is " + chargeCodeAccumPremium);

    /**
     * Return back an array of Charge Codes each of which is in one of
     * the array of fund numbers passed.
     * @param fundNames an Array of fund Names
     * @return array of ChargeCode objects
     */
    public static ChargeCode[] findByFilteredFundNames(String[] fundNames)
    {

        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeDAO().findByFilteredFundNames(fundNames);

        return (ChargeCode[]) CRUDEntityImpl.mapVOToEntity(chargeCodeVOs, ChargeCode.class);

    }

    /**
     * Return back all charge codes
     * @return
     */
    public static ChargeCode[] findAll()
    {
        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeDAO().findAll();

        return (ChargeCode[]) CRUDEntityImpl.mapVOToEntity(chargeCodeVOs, ChargeCode.class);
    }

   /**
    * This utility is needed in multiple places.
    * Get array of all chargeCodePKs for the filtered fund.
    * Always include 0 in the list (no chargecode case).
    * @return
    */
   public static long[] getAllChargeCodePKsIncludingZero(long filteredFundPK)
   {
       ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundPK(filteredFundPK);
       if (chargeCodes == null)
       {
           return new long[] { 0L };
       }

       Set uniqueChargeCodeKeys = new HashSet();

       for (int i = 0; i < chargeCodes.length; i++)
       {
           ChargeCode chargeCode = chargeCodes[i];
           ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
           long chargeCodePK = chargeCodeVO.getChargeCodePK();
           uniqueChargeCodeKeys.add(new Long(chargeCodePK));
       }

       long[] chargeCodeArray = new long[uniqueChargeCodeKeys.size()];

       Iterator it = uniqueChargeCodeKeys.iterator();
       int ix = 0;
       while (it.hasNext())
       {
           Long chgCodePKLong = (Long) it.next();
           chargeCodeArray[ix] = chgCodePKLong.longValue();
           ix++;
       }
       return chargeCodeArray;
   }

    public static List getUniqueClientFundNumbers(long filteredFundPK)
    {
        List clientFundNumbers = new ArrayList();

        ChargeCode[] chargeCodes = findByFilteredFundPK(filteredFundPK);

        if (chargeCodes != null)
        {
            for (int i = 0; i < chargeCodes.length; i++)
            {
                String clientFundNumber = ((ChargeCodeVO) chargeCodes[i].getVO()).getClientFundNumber();
                if (clientFundNumber != null && !clientFundNumber.equals(""))
                {
                    if (!clientFundNumbers.contains(clientFundNumber))
                    {
                        clientFundNumbers.add(clientFundNumber);
                    }
                }
            }
        }

        return clientFundNumbers;
    }

   /**
    * Save the entity using Hibernate
    */
   public void hSave()
   {
       SessionHelper.saveOrUpdate(this, ChargeCode.DATABASE);
   }

   /**
    * Delete the entity using Hibernate
    */
   public void hDelete()
   {
       SessionHelper.delete(this, ChargeCode.DATABASE);
   }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ChargeCode.DATABASE;
    }
}
