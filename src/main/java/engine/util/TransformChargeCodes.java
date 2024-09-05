package engine.util;

import edit.common.vo.ChargeCodeVO;
import edit.common.vo.FilteredFundVO;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import engine.ChargeCode;
import engine.FilteredFund;

/**
 * A utility for converting Client Fund Numbers into Filtered Fund and Charge
 * Code combinations, and also for converting the other direction.
 * <p>
 * This class is used for input and output conversions.  As such, it should
 * be instantiated once by the client process and then reused.
 * <p>
 * Usage Note: this class will load all of the data lazily when requested.
 * It will load the specific maps up using that data only when a map
 * is needed. There are no indexes on the Client Fund Number so it would
 * require full table scans without loading the data at once for that
 * lookup.
 * <p>
 * User: mcassidy
 * Date: Mar 31, 2005
 * <p>
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
public class TransformChargeCodes
{

    /** Keeps all of the data when it is loaded.
     * The maps will reference this data
     */
    private ChargeCodeVO[] _theChargeCodeData = null;

    /** We will get all the data once from the DB but will
     * put load references to it into these based lazily based
     * on what the user wants.
     */
    private Map _mapChargeCodesByClientFundNumbers = null;

    /** another map, lazily loaded from data it needed */
    private Map _mapChargeCodesByFundAndChargeCodeNumbers = null;

    /**
     * default constructor.
     */
    public TransformChargeCodes()
    {
    }

    /**
     * Get the ChargeCodeVO for a given client fund number.  Note -
     * this should return back only one but the client is not limited
     * to making the client fund number unique.
     * @param clientFundNumber
     * @return array of ChargeCodeVOs.  null if there is no match.
     */
    public ChargeCodeVO[] getChargeCodeVOsForClientFundNumber(String clientFundNumber)
    {
        String key = clientFundNumber;
        if (key == null || key.trim().length() == 0)
        {
            return null;   // don't try to match null or empty space
        }

        if (getMapByFundNums().containsKey(key))
        {
            // technically there can be more than one charge code row
            // for a single client fund number -- tho shouldn/'
            List theChargeCodeVOs = (List) getMapByFundNums().get(key);
            return (ChargeCodeVO[]) theChargeCodeVOs.toArray(new ChargeCodeVO[0]);
        }
        else
        {
            return null;
        }
    }

    /**
     * Get the client fund number for a given chargeCode.
     * @param chargeCodeFK
     * @return the client fund number or null if there is no match
     */
    public String getClientFundNumber(long chargeCodeFK)
    {
        if (chargeCodeFK == 0)
        {
            return null;
        }
        // for this loop thru the array of data directly.
        // can put in another Map if desired but this
        // should be fast for now.  The data is
        // loaded only once the first time it is used.
        ChargeCodeVO[] theArray = getData();
        for (int i = 0; i < theArray.length; i++)
        {
            ChargeCodeVO chargeCodeVO = theArray[i];
            if (chargeCodeVO.getChargeCodePK() == chargeCodeFK)
            {
                String clientFundNumber = chargeCodeVO.getClientFundNumber();
                if (clientFundNumber == null || clientFundNumber.trim().length()== 0)
                {
                    return null;
                }
                else
                {
                    return clientFundNumber;
                }
            }

        }
        return null;
    }

    /**
     * Convenience method. Get the client fund number for a given
     * fund number and chargecodeFK or
     * return just the fund number if the chargeCodeFK is 0.
     * @param filteredFundNumber
     * @param chargeCodeFK
     * @return the matching fund number that was set.  null if no match or
     * an empty string stored for the client fund number.
     */
    public String getClientFundNumberOrRealFundNumber(
            String filteredFundNumber, long chargeCodeFK)
    {
        if (chargeCodeFK ==0)
        {
            return filteredFundNumber;
        }
        String clientFundNumber = getClientFundNumber(chargeCodeFK);
        if (clientFundNumber == null)
        {
            return filteredFundNumber;    // NO CLIENT FUND NUMBER SET
        }
        else
        {
            return clientFundNumber;
        }
    }

    /**
     * Get the client fund number for a given fund number and charge code
     * number.  There can be at most one client fund number set.
     * @param filteredFundNumber
     * @param chargeCodeNumber
     * @return the matching fund number that was set.  null if no match or
     * an empty string stored for the client fund number.
     */
    public String getClientFundNumber(
            String filteredFundNumber, String chargeCodeNumber)
    {
        String key = filteredFundNumber + "|" + chargeCodeNumber;
        if (getMapByFFundAndChargeCodes().containsKey(key))
        {
            ChargeCodeVO chargeCodeVO =
                    (ChargeCodeVO) getMapByFFundAndChargeCodes().get(key);
            String clientFundNumber = chargeCodeVO.getClientFundNumber();
            if ("".equals(clientFundNumber))
            {
                return null;   // if empty string stored, return null - no match.
            }
            else
            {
                return clientFundNumber;
            }
        }
        else
        {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////// PRIVATE METHODS /////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    /**
     * Called from the routines when they try to access map data first time.
     * If already loaded, just returns reference.
     * @return the array of ChargeCodeVOs
     */
    private ChargeCodeVO[] getData()
    {
        if (_theChargeCodeData == null)
        {
            // load the data once
            ChargeCode[] chargeCodes = ChargeCode.findAll();
            List chargeCodeVOList = new ArrayList();
            for (int i = 0; i < chargeCodes.length; i++)
            {
                ChargeCode chargeCode = chargeCodes[i];
                ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
                chargeCodeVOList.add(chargeCodeVO);
            }
             _theChargeCodeData =
                     (ChargeCodeVO[])
                        chargeCodeVOList.toArray(new ChargeCodeVO[0]);
        }
        return _theChargeCodeData;
    }

    /**
     * Used to get the map keyed by Fund Numbers.
     * @return
     */
    private Map getMapByFundNums()
    {
        if (_mapChargeCodesByClientFundNumbers == null)
        {
            _mapChargeCodesByClientFundNumbers = new HashMap();
            // load it up once
            ChargeCodeVO[] chargeCodeVOs = getData();
            for (int i = 0; i < chargeCodeVOs.length; i++)
            {
                ChargeCodeVO chargeCodeVO = chargeCodeVOs[i];
                String clientFundNumber = chargeCodeVO.getClientFundNumber();

                if (clientFundNumber == null || clientFundNumber.trim().length() == 0)
                    continue;  // skip it

                if (_mapChargeCodesByClientFundNumbers.containsKey(clientFundNumber))
                {
                    List fundList =
                            (List) _mapChargeCodesByClientFundNumbers.
                                get(clientFundNumber);
                    fundList.add(chargeCodeVO);
                }
                else
                {
                    List newList = new ArrayList();
                    newList.add(chargeCodeVO);
                    _mapChargeCodesByClientFundNumbers.put(clientFundNumber, newList);
                }
            }
        }
        return _mapChargeCodesByClientFundNumbers;
    }

    /**
     * Used to get the Map keyed by filtered Fund and charge codes
     * @return
     */
    private Map getMapByFFundAndChargeCodes()
    {
        if (_mapChargeCodesByFundAndChargeCodeNumbers == null)
        {
             _mapChargeCodesByFundAndChargeCodeNumbers = new HashMap();
            // Load it up once if they want this
            // Note - this one does extra database IO
            ChargeCodeVO[] chargeCodeVOs = getData();
            for (int i = 0; i < chargeCodeVOs.length; i++)
            {
                ChargeCodeVO chargeCodeVO = chargeCodeVOs[i];
                long filteredFundFK = chargeCodeVO.getFilteredFundFK();
                // now do the lookup
                String filteredFundNumber = getFilteredFundNumber(filteredFundFK);
                String chargeCode = chargeCodeVO.getChargeCode();
                String key = filteredFundNumber + "|" + chargeCode;
                _mapChargeCodesByFundAndChargeCodeNumbers.put(key, chargeCodeVO);
            }
        }
        return _mapChargeCodesByFundAndChargeCodeNumbers;
    }

    /**
     * Used to look up a filtered fund number if it is needed for one
     * of the maps.
     * @param filteredFundPK
     * @return
     */
    private String getFilteredFundNumber(long filteredFundPK)
    {
        try
        {
            FilteredFund filteredFund = new FilteredFund(filteredFundPK);
            FilteredFundVO filteredFundVO = (FilteredFundVO)filteredFund.getVO();
            return filteredFundVO.getFundNumber();
        }
        catch (Exception e)
        {
            throw new RuntimeException("TransformChargeCodes had problem getting " +
                    "FilteredFund for filteredFundPK = " + filteredFundPK);
        }
    }


    // TESTING!!!!
    //    engine.util.TransformChargeCodes t = new engine.util.TransformChargeCodes();
    //    ChargeCodeVO[] chargeCodeVOs = t.getChargeCodeVOsForClientFundNumber("joe");
    //    if (chargeCodeVOs != null)
    //    {
    //        System.out.println("fundpk = " + chargeCodeVOs[0].getFilteredFundFK() +
    //                " chargeCode = " + chargeCodeVOs[0].getChargeCode() +
    //                " chargeCodePK = " + chargeCodeVOs[0].getChargeCodePK());
    //    }
    //    String s = t.getClientFundNumber("8501", "mark1");
    //    System.out.println("Client fund number is " + s);

}
