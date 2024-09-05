/*
 * DAOFactory.java      Version 1.10  07/31/2001

 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */
package engine.dm.dao;

import java.io.*;


/**
 *  This class follows the Factory pattern by building individual Data Access Objects (DAO)
 *  For example: The method getRuleStructureDAO returns a RuleStructureDAo object.
 *
 */
public class DAOFactory implements Serializable
{
    //*******************************
    //          Variables
    //*******************************
    private static AreaKeyDAO areaKeyDAO;
    private static AreaValueDAO areaValueDAO;
    private static ProductFilteredFundStructureDAO productFilteredFundStructureDAO;

    private static ProductRuleStructureDAO productRuleStructureDAO;
    private static ProductStructureDAO productStructureDAO;
    private static FilteredAreaValueDAO filteredAreaValueDAO;
    private static FilteredFundDAO filteredFundDAO;
    private static FundDAO fundDAO;
    private static InterestRateDAO interestRateDAO;
    private static InterestRateParametersDAO interestRateParametersDAO;
    private static RateTableDAO rateTableDAO;
    private static ControlBalanceDAO controlBalanceDAO;
    private static FeeCorrespondenceDAO feeCorrespondenceDAO;

    private static RulesDAO ruleDAO;
    private static ScriptDAO scriptDAO;
    private static ScriptInstructionDAO scriptInstructionDAO;
    private static ScriptLineDAO scriptLineDAO;
    private static TableDefDAO tableDefDAO;
    private static TableKeysDAO tableKeysDAO;
    private static UnitValuesDAO unitValuesDAO;

    //*******************************
    //          Constructors
    //*******************************
    static
    {
        ruleDAO = new RulesDAO();
        productStructureDAO = new ProductStructureDAO();
        productRuleStructureDAO = new ProductRuleStructureDAO();
        tableDefDAO = new TableDefDAO();
        tableKeysDAO = new TableKeysDAO();
        scriptDAO = new ScriptDAO();
        scriptLineDAO = new ScriptLineDAO();
        scriptInstructionDAO = new ScriptInstructionDAO();
        rateTableDAO = new RateTableDAO();
        filteredFundDAO = new FilteredFundDAO();
        unitValuesDAO = new UnitValuesDAO();
        interestRateDAO = new InterestRateDAO();
        interestRateParametersDAO = new InterestRateParametersDAO();
        fundDAO = new FundDAO();
        productFilteredFundStructureDAO = new ProductFilteredFundStructureDAO();
        areaKeyDAO = new AreaKeyDAO();
        areaValueDAO = new AreaValueDAO();
        filteredAreaValueDAO = new FilteredAreaValueDAO();
        controlBalanceDAO = new ControlBalanceDAO();
        feeCorrespondenceDAO = new FeeCorrespondenceDAO();
    }

    /************************************** Constructor Methods **************************************/
    /************************************** Public Methods **************************************/
    /************************************** Private Methods **************************************/
    /************************************** Static Methods **************************************/

    //*******************************
    //          Public Methods
    //*******************************
    public static AreaValueDAO getAreaValueDAO()
    {
        return areaValueDAO;
    }

    public static AreaKeyDAO getAreaKeyDAO()
    {
        return areaKeyDAO;
    }

    /**
     * Factory Method
     *
     * @return RulesDAO
     */
    public static RulesDAO getRulesDAO()
    {
        return ruleDAO;
    }

    /**
     * Factory Method
     *
     * @return ProductRuleStructureDAO

     */
    public static ProductRuleStructureDAO getProductRuleStructureDAO()
    {
        return productRuleStructureDAO;
    }

    /**
     * Factory Method
     *
     * @return ProductStructureDAO
     */
    public static ProductStructureDAO getProductStructureDAO()
    {
        return productStructureDAO;
    }

    /**
     * Factory Method
     *
     * @return TableDefDAO
     */
    public static TableDefDAO getTableDefDAO()
    {
        return tableDefDAO;
    }

    /**
     * Factory Method
     *
     * @return TableKeysDAO
     */
    public static TableKeysDAO getTableKeysDAO()
    {
        return tableKeysDAO;
    }

    /**
      * Factory Method
     *
     * @return ScriptDAO
     */
    public static ScriptDAO getScriptDAO()
    {
        return scriptDAO;
    }

    /**
     * Factory Method
     *
     * @return ScriptLineDAO
     */
    public static ScriptLineDAO getScriptLineDAO()
    {
        return scriptLineDAO;
    }

    /**
     * Factory Method
     *
     * @return RateTableDAO
     */
    public static RateTableDAO getRateTableDAO()
    {
        return rateTableDAO;
    }

    /**
     * Factory Method
     *
     * @return UserLoginDAO

     */
    public static FilteredFundDAO getFilteredFundDAO()
    {
        return filteredFundDAO;
    }

    public static UnitValuesDAO getUnitValuesDAO()
    {
        return unitValuesDAO;
    }

    /**
     * Factory Method
     *
     * @return ScriptInstructionDAO
     */
    public static ScriptInstructionDAO getScriptInstructionDAO()
    {
        return scriptInstructionDAO;
    }

    /**
     * Factory Method
     *
     * @return InterestRateDAO
     */
    public static InterestRateDAO getInterestRateDAO()
    {
        return interestRateDAO;
    }

    /**
    * Factory Method
    *
    * @return interestRateParametersDAO
    */
    public static InterestRateParametersDAO getInterestRateParametersDAO()
    {
        return interestRateParametersDAO;
    }

    /**
     * Factory Method
     *
     * @return FundDAO
     */
    public static FundDAO getFundDAO()
    {
        return fundDAO;
    }

    /**
     * Factory Method
     *
     * @return ProductFilteredFundStructureDAO
     */
    public static ProductFilteredFundStructureDAO getProductFilteredFundStructureDAO()
    {
        return productFilteredFundStructureDAO;
    }

    /**
     * Overrides default AreaValueDAO.
     * @param anAreaValueDAO
     */
    public static void setAreaValueDAO(AreaValueDAO anAreaValueDAO)
    {
        areaValueDAO = anAreaValueDAO;
    }

    /**
     * Getter.
     * @return
     */
    public static FilteredAreaValueDAO getFilteredAreaValueDAO()
    {
        return filteredAreaValueDAO;
    }

    /**
     * Return an instance of the ControlBalanceDAO
     * @return
     */
    public static ControlBalanceDAO getControlBalanceDAO()
    {
        return controlBalanceDAO;
    }

    /**
     * Return an instance of the FeeCorrespondenceDAO
     * @return
     */
    public static FeeCorrespondenceDAO getFeeCorrespondenceDAO()
    {
        return feeCorrespondenceDAO;
    }
}
