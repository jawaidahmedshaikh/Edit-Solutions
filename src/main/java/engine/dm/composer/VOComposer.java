package engine.dm.composer;

import edit.common.vo.*;
import engine.dm.dao.DAOFactory;
import engine.dm.dao.FeeDAO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 13, 2003
 * Time: 11:19:13 AM
 * To change this template use Options | File Templates.
 */
public class VOComposer
{
    public AreaVO[] composeAreaVOByOverrideStatus(List voInclusionList, String overrideStatus) throws Exception
    {
//        AreaVO[] areaVO = DAOFactory.getAreaDAO().findByOverrideStatus(overrideStatus);
//
//        if (areaVO != null)
//        {
//            for (int i = 0; i < areaVO.length; i++)
//            {
//                new AreaComposer(voInclusionList).compose(areaVO[i]);
//            }
//        }
//
//        return areaVO;
        return null;
    }

    public FilteredAreaVO[] composeFilteredAreaByProductStructurePK(long productStructurePK, List voInclusionList) throws Exception
    {
//        FilteredAreaVO[] filteredAreaVO = DAOFactory.getFilteredAreaDAO().findByProductStructurePK(productStructurePK);
//
//        if (filteredAreaVO != null)
//        {
//            for (int i = 0; i < filteredAreaVO.length; i++)
//            {
//                new FilteredAreaComposer(voInclusionList).compose(filteredAreaVO[i]);
//            }
//        }
//
//        return filteredAreaVO;

        return null;
    }

    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK(long productStructurePK, List voInclusionList) throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findByProductStructureId(productStructurePK, false, null);

        if (filteredFundVO != null)
        {
            for (int i = 0; i < filteredFundVO.length; i++)
            {
                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        return filteredFundVO;
    }

    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK_AND_FundType(long productStructurePK, List voInclusionList, String fundType) throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findByProductStructureId_AND_FundType(productStructurePK, fundType, false, null);

        if (filteredFundVO != null)
        {
            for (int i = 0; i < filteredFundVO.length; i++)
            {
                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        return filteredFundVO;
    }

    /**
     * Composer. (see engine.component.LookupComponent#composeFilteredFundVOByCoStructurePK_And_FundPK
     * @param productStructurePK
     * @param fundPK
     * @param voInclusionList
     * @return
     */
    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK_And_FundPK(long productStructurePK, long fundPK, List voInclusionList)
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findByCSIdFundId(productStructurePK, fundPK);

        if (filteredFundVO != null)
        {
            for (int i = 0; i < filteredFundVO.length; i++)
            {
                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        return filteredFundVO;
    }

    public FilteredFundVO[] composeFilteredFundVOByFilteredFundPK(long filteredFundPK, List voInclusionList) throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findByFilteredFundPK(filteredFundPK, false, null);

        if (filteredFundVO != null)
        {
            for (int i = 0; i < filteredFundVO.length; i++)
            {
                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        return filteredFundVO;
    }

    /**
     * Composes FilteredFundVOs, retrieving filtered funds show FundFK is equal to the fundFK parameter
     * @param fundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] composeFilteredFundVOByFundFK(long fundFK, List voInclusionList) throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findByFundId(fundFK);

        if (filteredFundVO != null)
        {
            for (int i = 0; i < filteredFundVO.length; i++)
            {
                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        return filteredFundVO;
    }

    /**
     * Composes all FilteredFundVOs for the given fundNumber
     * @param fundNumber
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] composeFilteredFundVOByFundNumber(String fundNumber, List voInclusionList) throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findByFundNumber(fundNumber);

        if (filteredFundVO != null)
        {
            for (int i = 0; i < filteredFundVO.length; i++)
            {
                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        return filteredFundVO;
    }

    /**
     * Composes ProductFilteredFundStructureVOs, retrieving those records whose ProductStructureFK and FilteredFundFK
     * match the productStructureFK and filteredFundFK parameters
     * @param productStructureFK
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public ProductFilteredFundStructureVO composeProductFilteredFundStructureByProdStructFKAndFltrdFundFK(long productStructureFK,
                                                                                                        long filteredFundFK,
                                                                                                        List voInclusionList)
    {
        ProductFilteredFundStructureVO[] productFilteredFundStructureVO = DAOFactory.getProductFilteredFundStructureDAO().findByCoStructFKAndFltrdFundFK(productStructureFK, filteredFundFK);

        if (productFilteredFundStructureVO != null && productFilteredFundStructureVO.length > 0)
        {
            new ProductFilteredFundStructureComposer(voInclusionList).compose(productFilteredFundStructureVO[0]);
        }

        return productFilteredFundStructureVO[0];
    }

    public ProductFilteredFundStructureVO composeProductFilteredFundStructureByPK(long productFilteredFundStructurePK,
                                                                                  List voInclusionList)
    {
        ProductFilteredFundStructureVO[] productFilteredFundStructureVO = DAOFactory.getProductFilteredFundStructureDAO().findByPK(productFilteredFundStructurePK);

        if (productFilteredFundStructureVO != null && productFilteredFundStructureVO.length > 0)
        {
            new ProductFilteredFundStructureComposer(voInclusionList).compose(productFilteredFundStructureVO[0]);
        }

        return productFilteredFundStructureVO[0];
    }

    public FundVO[] composeAllFundVOs(List voInclusionList)
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findAll(false, null);

        if (fundVO != null)
        {
            for (int i = 0; i < fundVO.length; i++)
            {
                new FundComposer(voInclusionList).compose(fundVO[i]);
            }
        }

        return fundVO;
    }

    public FundVO[] composeAllFixedFundVOs(List voInclusionList)
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findAllFixedFunds();

        if (fundVO != null)
        {
            for (int i = 0; i < fundVO.length; i++)
            {
                new FundComposer(voInclusionList).compose(fundVO[i]);
            }
        }

        return fundVO;
    }

    public FundVO[] composeAllVariableFundVOs(List voInclusionList)
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findAllVariableFunds();

        if (fundVO != null)
        {
            for (int i = 0; i < fundVO.length; i++)
            {
                new FundComposer(voInclusionList).compose(fundVO[i]);
            }
        }

        return fundVO;
    }

    public FundVO composeFundVOByFilteredFundPK(long filteredFundPK, List voInclusionList)
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findFundByFilteredFundFK(filteredFundPK, false, null);

        if (fundVO != null)
        {
            new FundComposer(voInclusionList).compose(fundVO[0]);
            return fundVO[0];
        }
        else
        {
            return null;
        }
    }

    public FundVO[] composeFundVOsByActivityFileInd(List voInclusionList, String activityFileInd)
    {
        FundVO[] fundVOs = DAOFactory.getFundDAO().findAllFundsByActivityFileInd(activityFileInd);

        if (fundVOs != null)
        {
            for (int i = 0; i < fundVOs.length; i++)
            {
                new FundComposer(voInclusionList).compose(fundVOs[i]);
            }
        }

        return fundVOs;
    }

    /**
     * Composes UnitValue VOs for the given filtered fund and to/from dates
     * @param filteredFundPK
     * @param fromDate
     * @param toDate
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public UnitValuesVO[] composeUnitValuesByFilteredFundAndDates(long filteredFundPK,
                                                                  long chargeCodeFK,
                                                                  String fromDate,
                                                                  String toDate,
                                                                  List voInclusionList)
    {
        UnitValuesVO[] unitValuesVOs = DAOFactory.getUnitValuesDAO().findByFilteredFund_ChargeCode_DateRange(filteredFundPK, chargeCodeFK, fromDate, toDate);

        if (unitValuesVOs != null)
        {
            for (int i = 0; i < unitValuesVOs.length; i++)
            {
                new UnitValuesComposer(voInclusionList).compose(unitValuesVOs[i]);
            }
        }

        return unitValuesVOs;
    }

    public FeeVO[] composeAllFeesByAccountingPendingStatus_Date(List voInclusionList, String accountingProcessDate)
    {
        FeeVO[] feeVOs = new FeeDAO().findAllAccountingPendingByDate(accountingProcessDate);

        if (feeVOs != null)
        {
            for (int i = 0; i < feeVOs.length; i++)
            {
                new FeeComposer(voInclusionList).compose(feeVOs[i]);
            }
        }

        return feeVOs;
    }
}
