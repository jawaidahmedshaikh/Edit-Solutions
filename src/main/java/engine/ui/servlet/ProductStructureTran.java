/*
 * User: unknown
 * Date: Jun 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.ui.servlet;

import edit.common.*;
import edit.common.vo.*;
import edit.common.vo.user.UIRulesVO;
import edit.portal.common.transactions.Transaction;
import engine.business.Calculator;
import engine.business.Lookup;
import engine.component.CalculatorComponent;
import engine.component.LookupComponent;
import engine.*;
import fission.beans.PageBean;
import fission.dm.SMException;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;

import codetable.component.CodeTableComponent;
import codetable.business.CodeTable;

import edit.common.exceptions.EDITDeleteException;


/**
 * ProductStructureTran is used to handle Product related requests
 */
public class ProductStructureTran extends Transaction {

//****************************************
//           VARIABLES
//****************************************

    // Screen Names
	public static final String TREE_HEADING		 = "Product Structure";

    private static final String SHOW_CLONE_SECURITY_DIALOG = "showCloneSecurityDialog";

    private static final String CLONE_SECURITY_DIALOG = "/engine/jsp/cloneSecurityDialog.jsp";


//****************************************
//           METHODS
//****************************************

	/**
     * retrieves action and calls appropriate method
     * <p>
     * @param appReqBlock AppReqBlock retrieves action
     * @return returns String for screen display
     */
    public String execute(AppReqBlock appReqBlock) throws Exception   {

        String action = appReqBlock.getReqParm("action");

        if (action.equalsIgnoreCase("showMain"))  {

        	return showMain(appReqBlock);
       	}
        else if (action.equalsIgnoreCase("saveProductStructure"))
        {
        	return saveProductStructure(appReqBlock);
        }
//        else if (action.equalsIgnoreCase("applyProductStructure"))
//        {
//        	return applyProductStructure(appReqBlock);
//
//        }
        else if (action.equalsIgnoreCase("clearProductStructure"))
        {
        	return clearProductStructure(appReqBlock);

        }
        else if (action.equalsIgnoreCase("cancelProductStructure"))
        {
        	return cancelProductStructure(appReqBlock);

        }
		else if (action.equals("showProductStructureDialog")) {

			showProductStructureDialog(appReqBlock);

			return "/engine/jsp/companyselectiondialog.jsp";
		}

        else if (action.equals("deleteProductStructure")) {

            return deleteProductStructure(appReqBlock);
        }
        else if (action.equals("addProductStucture"))
        {
            return addProductStucture(appReqBlock);
        }
        else if (action.equals("showProductStructureDetail"))
        {
            return showProductStructureDetail(appReqBlock);
        }
        else if (action.equals("cancelProductStructureEdits"))
        {
            return cancelProductStructureEdits(appReqBlock);
        }
        else if (action.equals("showBuildCompanyStructureDialog"))
        {
            return showBuildCompanyStructureDialog(appReqBlock);
        }
        else if (action.equals("cloneAllFilteredValues"))
        {
            return cloneAllFilteredValues(appReqBlock);
        }
        else if (action.equals("showAttachedRules"))
        {
            return showAttachedRules(appReqBlock);
        }
        else if (action.equals("showToolkitSelection"))
        {
            return showToolkitSelection(appReqBlock);
        }
        else if (action.equals("changeProductStructure"))
        {
            return changeProductStructure(appReqBlock);
        }
        else if (action.equals(SHOW_CLONE_SECURITY_DIALOG))
        {
            return showCloneSecurityDialog(appReqBlock);
        }
        else
        {
            throw new Exception("ProductStructure Tran: Invalid action " + action);
        }
    }

//****************************************
//           PROTECTED METHODS
//****************************************

    protected String showToolkitSelection(AppReqBlock appReqBlock)
    {
        return "/engine/jsp/toolkitselection.jsp";
    }


    protected String showAttachedRules(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("activeProductStructurePK"), "0");

        Lookup calcLookup = new LookupComponent();

        RulesVO[] rulesVOs = calcLookup.findAttachedRulesVOsByProductStructurePK(Long.parseLong(productStructurePK), false, null);

        UIRulesVO[] uiRulesVOs = this.buildUIRulesVOs(rulesVOs);

        ProductStructureVO[] productStructureVOs = calcLookup.findProductStructureVOByPK(Long.parseLong(productStructurePK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("uiRulesVOs", uiRulesVOs);
        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        return "/engine/jsp/attachedRulesDialog.jsp";
    }

    protected UIRulesVO[] buildUIRulesVOs(RulesVO[] rulesVOs) throws Exception
    {
        List uiRulesVOs = new ArrayList();
        Lookup calcLookup = new LookupComponent();

        for (int i = 0; i < rulesVOs.length; i++)
        {
            UIRulesVO uiRulesVO = new UIRulesVO();
            uiRulesVO.setRulesVO(rulesVOs[i]);

            long scriptPK = rulesVOs[i].getScriptFK();
            long tableDefPK = rulesVOs[i].getTableDefFK();

            ScriptVO[] scriptVOs = null;

            if (scriptPK != 0)
            {
                scriptVOs = calcLookup.findScriptVOByPK(scriptPK, false, null);
                uiRulesVO.setScriptVO(scriptVOs[0]);
            }

            TableDefVO[] tableDefVOs = null;

            if (tableDefPK != 0)
            {
                tableDefVOs = calcLookup.findTableDefVOByPK(tableDefPK, false, null);
                uiRulesVO.setTableDefVO(tableDefVOs[0]);
            }

            uiRulesVOs.add(uiRulesVO);
        }

        if (uiRulesVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (UIRulesVO[]) uiRulesVOs.toArray(new UIRulesVO[uiRulesVOs.size()]);
        }
    }

    protected String cancelProductStructureEdits(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showMain(appReqBlock);
    }

    protected String showBuildCompanyStructureDialog(AppReqBlock appReqBlock) throws Exception
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        Lookup calculatorLookup = new LookupComponent();

        ProductStructureVO cloneFromProductStructureVO = calculatorLookup.findProductStructureVOByPK(Long.parseLong(activeProductStructurePK), false, null)[0];

        ProductStructureVO[] productStructureVOs = calculatorLookup.findByTypeCode("Product",false, null);

        appReqBlock.getHttpServletRequest().setAttribute("cloneFromProductStructureVO", cloneFromProductStructureVO);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        return "/engine/jsp/buildCompanyStructureDialog.jsp";
    }

    protected String cloneAllFilteredValues(AppReqBlock appReqBlock) throws Exception
    {
        Long activeProductStructurePK = new Long(appReqBlock.getReqParm("activeProductStructurePK"));

        Long cloneToProductStructurePK = new Long(appReqBlock.getReqParm("cloneToProductStructurePK"));

        CodeTable codeTable = new CodeTableComponent();

        codeTable.cloneAllFilteredRelations(activeProductStructurePK, cloneToProductStructurePK);

        return showBuildCompanyStructureDialog(appReqBlock);
    }

    protected String showProductStructureDetail(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("activeProductStructurePK"), "0");

        Lookup calcLookup = new LookupComponent();

        ProductStructureVO productStructureVO =  calcLookup.findProductStructureVOByPK(Long.parseLong(productStructurePK), false, null)[0];

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVO", productStructureVO);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "CHANGE");

        FundVO[] fundVOs = calcLookup.getFundsBYCSId(Long.parseLong(productStructurePK));

        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", fundVOs);

        return showMain(appReqBlock);
    }

	/**
     * retrieves session data, sets up bean, retrieves data, sets tree heading,
     * and loads the session with data.
     * <p>
     * @param AppReqBlock AppReqBlock retrieves session data
     * <p>
     * @return returns String for main screen
     */
	protected String showMain(AppReqBlock appReqBlock)throws Exception {

        Lookup calcLookup = new LookupComponent();
        CompanyStructureNameVO companytStructureNameVO = calcLookup.findCompanyStructureNameVO();

        ProductStructureVO[] productStructureVOs = calcLookup.findAllProductTypeStructureVOs(false, null);

        // Find Product Structures that are attached to Rules
        long[] productStructurePKs = calcLookup.findAttachedProductStructurePKs();

        List attachedProductStructurePKs = new ArrayList();

        if (productStructurePKs != null)
        {
            for (int i = 0; i < productStructurePKs.length; i++)
            {
                attachedProductStructurePKs.add(new Long(productStructurePKs[i]));
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("companyStructureNameVO", companytStructureNameVO);
        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);
        appReqBlock.getHttpServletRequest().setAttribute("attachedProductStructurePKs", attachedProductStructurePKs);

		return "/engine/jsp/companyStructure.jsp";
    }

    protected String addProductStucture(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "ADD");

        return showMain(appReqBlock);
    }

    protected String changeProductStructure(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");

        Lookup calcLookup = new LookupComponent();

        ProductStructureVO productStructureVO =  calcLookup.findProductStructureVOByPK(Long.parseLong(productStructurePK), false, null)[0];

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVO", productStructureVO);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "CHANGE");

        return showMain(appReqBlock);
    }

    protected String saveProductStructure(AppReqBlock appReqBlock) throws SMException,Exception {

        Calculator calcComponent = new CalculatorComponent();

        String companyName = initParam(appReqBlock.getReqParm("companyName"), null);
        String policyNumberPrefix = initParam(appReqBlock.getReqParm("policyNumberPrefix"), null);
        String policyNumberSuffix = initParam(appReqBlock.getReqParm("policyNumberSuffix"), null);
        int policyNumberSequenceNumber = Integer.parseInt(appReqBlock.getReqParm("policyNumberSequenceNumber"));
        int policySequenceLength = Integer.parseInt(appReqBlock.getReqParm("policySequenceLength"));
        String billingCompanyNumber = initParam(appReqBlock.getReqParm("billingCompanyNumber"), null);
        String marketingPackageName = initParam(appReqBlock.getReqParm("marketingPackageName"), null);
        String groupProductName = initParam(appReqBlock.getReqParm("groupProductName"), null);
        String areaName = initParam(appReqBlock.getReqParm("areaName"), null);
        String businessContractName = initParam(appReqBlock.getReqParm("businessContractName"), null);
        String typeCodeCT = initParam(appReqBlock.getReqParm("typeCodeSelect"), null);
        String productStructurePK = appReqBlock.getFormBean().getValue("activeProductStructurePK");
        String externalProductName = initParam(appReqBlock.getReqParm("externalProductName"), null);
        String groupType = initParam(appReqBlock.getReqParm("groupTypeSelect"), null);
        if (groupType.equals("-1"))
        {
            groupType = null;
        }

        String productType = initParam(appReqBlock.getReqParm("productTypeSelect"), null);
        if (productType.equals("-1"))
        {
            productType = null;
        }

        String cloneFromProductStructurePKStr =
                Util.initString(appReqBlock.getFormBean().getValue("cloneFromProductStructurePK"), "0");
        long cloneFromProductStructurePK = Long.parseLong(cloneFromProductStructurePKStr);
        // if this is non-zero, will will also clone security from this product strhedgeFundInterimAccountFKucture
        String hedgeFundInterimAccountFK = appReqBlock.getFormBean().getValue("hedgeFundInterimAccountFK");

        long companyPK = calcComponent.saveCompany(companyName, policyNumberPrefix, policyNumberSuffix, policyNumberSequenceNumber, 
        		policySequenceLength, billingCompanyNumber);

        ProductStructureVO productStructureVO = new ProductStructureVO();
        productStructureVO.setProductStructurePK(Long.parseLong(productStructurePK));
        productStructureVO.setMarketingPackageName(marketingPackageName);
        productStructureVO.setGroupProductName(groupProductName);
        productStructureVO.setAreaName(areaName);
        productStructureVO.setBusinessContractName(businessContractName);
        productStructureVO.setTypeCodeCT(typeCodeCT);
        productStructureVO.setExternalProductName(externalProductName);
        productStructureVO.setOperator(appReqBlock.getUserSession().getUsername());
        productStructureVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        productStructureVO.setGroupTypeCT(groupType);
        productStructureVO.setProductTypeCT(productType);

        if (Util.isANumber(hedgeFundInterimAccountFK) &&
            Long.parseLong(hedgeFundInterimAccountFK) > 0L)
        {
            productStructureVO.setHedgeFundInterimAccountFK(Long.parseLong(hedgeFundInterimAccountFK));
        }
        productStructureVO.setCompanyFK(companyPK);

        String message = "Company Structure Successfully Saved";

        if (productStructurePK.equalsIgnoreCase("0"))
        {
            long newPK = calcComponent.saveProductStructure(productStructureVO);

            if (cloneFromProductStructurePK != 0)
            {
                ProductStructure cloneFromProductStructure =
                        ProductStructure.findByPK(cloneFromProductStructurePK);

                ProductStructure toProductStructure =
                        ProductStructure.findByPK(newPK);

                cloneFromProductStructure.cloneAllSecurity(toProductStructure);

                message += " and Cloned";

            }
        }
        else
        {
            calcComponent.saveChangeProductStructure(productStructureVO);
        }

        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        if (productStructurePK.equalsIgnoreCase("0"))
        {
            calcComponent.saveProductStructure(productStructureVO);
        }
        else
        {
            calcComponent.saveChangeProductStructure(productStructureVO);
        }

        return this.showMain(appReqBlock);
    }

//   	protected String applyProductStructure(AppReqBlock appReqBlock) throws SMException, Exception {
//
//		String newProductStructure;
//		PageBean pageBean = new PageBean();
//
//		//get product Structure from request
//   	    newProductStructure = appReqBlock.getReqParm("productStructure");
//
//        Calculator calcComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");
//        calcComponent.saveProductStructure(newProductStructure);
//
//		//get list of Companies,MarketingPackage,Group Product,
//		//Area and Business contract
//        Lookup lookupComponent = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");
//        GenericListWrapper genericListWrapper = new GenericListWrapper();
//
//		ProductStructureVO[] productStructureList = lookupComponent.getAllProductStructureNames();
//
//        String[] nameList = new String[]{"getProductName", "getMarketingPackageName",
//                                           "getGroupProductName", "getAreaName", "getBusinessContractName"};
//
//        genericListWrapper.initializeLists(productStructureList, nameList);
//
//        String[] productNameList = genericListWrapper.getNameList("ProductName");
//        String[] marketingNameList = genericListWrapper.getNameList("MarketingPackageName");
//        String[] groupNameList = genericListWrapper.getNameList("GroupProductName");
//        String[] areaNameList = genericListWrapper.getNameList("AreaName");
//        String[] businessNameList = genericListWrapper.getNameList("BusinessContractName");
//
//		pageBean.putValues("productList", productNameList,
//		                   new String [] {"toString"},
//		                   null);
//		pageBean.putValues("marketingPackageList", marketingNameList,
//		                   new String [] {"toString"},
//		                   null);
//		pageBean.putValues("groupProductList", groupNameList,
//		                   new String [] {"toString"},
//		                   null);
//		pageBean.putValues("areaList", areaNameList,
//		                   new String [] {"toString"},
//		                   null);
//		pageBean.putValues("businessContractList", businessNameList,
//		                   new String [] {"toString"},
//		                   null);
//
//		//Selected Values for Product structure page
//		StringTokenizer productStructure = new StringTokenizer(newProductStructure,",");
//		String product = productStructure.nextToken();
//		String marketing = productStructure.nextToken();
//		String groupProduct = productStructure.nextToken();
//		String area = productStructure.nextToken();
//		String business = productStructure.nextToken();
//
//        pageBean.putValue("product",product);
//		pageBean.putValue("marketingPackage",marketing);
//		pageBean.putValue("groupProduct",groupProduct);
//		pageBean.putValue("area",area);
//		pageBean.putValue("businessContract",business);
//
//		//add page Bean to request
//		appReqBlock.getHttpServletRequest().setAttribute("pageBean",pageBean);
//
//		return "/engine/jsp/productStructure.jsp";
//    }

    protected String clearProductStructure(AppReqBlock appReqBlock) throws SMException, Exception {

		PageBean pageBean = new PageBean();

		//get list of Companies,MarketingPackage,Group Product,
		//Area and Business contract
        Lookup lookupComponent = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");
        GenericListWrapper genericListWrapper = new GenericListWrapper();

		ProductStructureVO[] productStructureList = lookupComponent.getAllProductStructureNames();

        String[] nameList = new String[]{"getProductName", "getMarketingPackageName",
                                           "getGroupProductName", "getAreaName", "getBusinessContractName"};

        genericListWrapper.initializeLists(productStructureList, nameList);

        String[] productNameList = genericListWrapper.getNameList("ProductName");
        String[] marketingNameList = genericListWrapper.getNameList("MarketingPackageName");
        String[] groupNameList = genericListWrapper.getNameList("GroupProductName");
        String[] areaNameList = genericListWrapper.getNameList("AreaName");
        String[] businessNameList = genericListWrapper.getNameList("BusinessContractName");

		pageBean.putValues("productList", productNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("marketingPackageList",marketingNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("groupProductList", groupNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("areaList", areaNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("businessContractList", businessNameList,
		                   new String [] {"toString"},
		                   null);

		//Default values for Product structure page
		pageBean.putValue("product","Please Select");
		pageBean.putValue("marketingPackage","Please Select");
		pageBean.putValue("groupProduct","Please Select");
		pageBean.putValue("area","Please Select");
		pageBean.putValue("businessContract","Please Select");

		//add page Bean to request
		appReqBlock.getHttpServletRequest().setAttribute("pageBean",pageBean);

		return "/engine/jsp/productStructure.jsp";
    }

	 protected String cancelProductStructure(AppReqBlock appReqBlock) throws SMException,Exception {

		PageBean pageBean = new PageBean();

		//get list of Companies,MarketingPackage,Group Product,
		//Area and Business contract
        Lookup lookupComponent = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");
        GenericListWrapper genericListWrapper = new GenericListWrapper();

		ProductStructureVO[] productStructureList = lookupComponent.getAllProductStructureNames();

        String[] nameList = new String[]{"getProductName", "getMarketingPackageName",
                                           "getGroupProductName", "getAreaName", "getBusinessContractName"};

        genericListWrapper.initializeLists(productStructureList, nameList);

        String[] productNameList = genericListWrapper.getNameList("ProductName");
        String[] marketingNameList = genericListWrapper.getNameList("MarketingPackageName");
        String[] groupNameList = genericListWrapper.getNameList("GroupProductName");
        String[] areaNameList = genericListWrapper.getNameList("AreaName");
        String[] businessNameList = genericListWrapper.getNameList("BusinessContractName");

		pageBean.putValues("productList", productNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("marketingPackageList", marketingNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("groupProductList", groupNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("areaList", areaNameList,
		                   new String [] {"toString"},
		                   null);
		pageBean.putValues("businessContractList", businessNameList,
		                   new String [] {"toString"},
		                   null);

		//Default values for Product structure page
		pageBean.putValue("product","Please Select");
		pageBean.putValue("marketingPackage","Please Select");
		pageBean.putValue("groupProduct","Please Select");
		pageBean.putValue("area","Please Select");
		pageBean.putValue("businessContract","Please Select");

		//add page Bean to request
		appReqBlock.getHttpServletRequest().setAttribute("pageBean",pageBean);

		return appReqBlock.getReqParm("returnPage");
    }

	protected void showProductStructureDialog(AppReqBlock appReqBlock) throws Exception {

		PageBean pageBean = new PageBean();

		//get list of Companies
        Lookup lookupComponent = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

		ProductStructureVO[] productStructureList = lookupComponent.getAllProductStructuresAttachedToRules();

		pageBean.putValues("productStructureList",
							 productStructureList,
							  new String[] {"getProductName", "getMarketingPackageName", "getGroupProductName", "getAreaName", "getBusinessContractName"},
							   null);

		pageBean.putValues("productStructureIdList",
						     productStructureList,
						      new String[] {"getProductStructurePK"},
						       null);

	   appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
	}

    protected String deleteProductStructure(AppReqBlock appReqBlock) throws Exception {

        String productStructurePK = initParam(appReqBlock.getReqParm("activeProductStructurePK"), "0");

        Calculator calcComponent = new CalculatorComponent();

        try
        {
            calcComponent.deleteProductStructure(Long.parseLong(productStructurePK));

            String message = "Product Structure Successfully Deleted";
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (EDITDeleteException e)
        {
            Lookup calcLookup = new LookupComponent();

            String message = e.getMessage();

            ProductStructureVO productStructureVO = calcLookup.findProductStructureVOByPK(Long.parseLong(productStructurePK), false, null)[0];

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.getHttpServletRequest().setAttribute("productStructureVO", productStructureVO);
        }

        return showMain(appReqBlock);
    }

    /**
     * Displays the cloneSecurityDialog.  Puts the needed parameters from the calling page into request scope so they
     * won't be lost.
     *
     * @param appReqBlock
     *
     * @return the page to be displayed
     */
    protected String showCloneSecurityDialog(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("companyName", appReqBlock.getReqParm("companyName"));
        appReqBlock.getHttpServletRequest().setAttribute("policyNumberPrefix", appReqBlock.getReqParm("policyNumberPrefix"));
        appReqBlock.getHttpServletRequest().setAttribute("policyNumberSuffix", appReqBlock.getReqParm("policyNumberSuffix"));
        appReqBlock.getHttpServletRequest().setAttribute("policyNumberSequenceNumber", appReqBlock.getReqParm("policyNumberSequenceNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("policySequenceLength", appReqBlock.getReqParm("policySequenceLength"));
        appReqBlock.getHttpServletRequest().setAttribute("billingCompanyNumber", appReqBlock.getReqParm("billingCompanyNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("marketingPackageName", appReqBlock.getReqParm("marketingPackageName"));
        appReqBlock.getHttpServletRequest().setAttribute("groupProductName", appReqBlock.getReqParm("groupProductName"));
        appReqBlock.getHttpServletRequest().setAttribute("areaName", appReqBlock.getReqParm("areaName"));
        appReqBlock.getHttpServletRequest().setAttribute("businessContractName", appReqBlock.getReqParm("businessContractName"));
        appReqBlock.getHttpServletRequest().setAttribute("typeCodeSelect", appReqBlock.getReqParm("typeCodeSelect"));
        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", appReqBlock.getReqParm("activeProductStructurePK"));
        appReqBlock.getHttpServletRequest().setAttribute("externalProductName", appReqBlock.getReqParm("externalProductName"));
        appReqBlock.getHttpServletRequest().setAttribute("groupTypeSelect", appReqBlock.getReqParm("groupTypeSelect"));
        appReqBlock.getHttpServletRequest().setAttribute("productTypeSelect", appReqBlock.getReqParm("productTypeSelect"));
        appReqBlock.getHttpServletRequest().setAttribute("hedgeFundInterimAccountFK", appReqBlock.getReqParm("hedgeFundInterimAccountFK"));

        return CLONE_SECURITY_DIALOG;
    }
}
