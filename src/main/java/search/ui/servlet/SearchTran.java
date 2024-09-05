/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Mar 22, 2002
 * Time: 2:48:11 PM
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 *
 */
package search.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.vo.*;
import edit.portal.common.transactions.Transaction;
import fission.global.AppReqBlock;
import fission.utility.Util;
import search.business.AgentLookup;
import search.business.Lookup;
import search.component.AgentLookupComponent;
import search.component.LookupComponent;

import java.util.*;

import security.*;
import engine.ProductStructure;
import engine.Company;
import agent.AgentContract;
import agent.PlacedAgent;
import antlr.StringUtils;
import role.ClientRole;

public class SearchTran extends Transaction {

    //These are the Actions of the Dialog Screen
    private static final String FIND_BY_COMPANY_STRUCTURE_CONTRACT_NUMBER = "findByCompanyStructureContractNumber";
    private static final String FIND_BY_CONTRACT_NUMBER_ONLY = "findByContractNumberOnly";
    private static final String FIND_BY_CLIENT_NAME = "findByClientName";
    private static final String FIND_AGENT_BY_CLIENT_NAME = "findAgentByClientName";
    private static final String FIND_AGENT_BY_CLIENT_NAME_DOB = "findAgentByClientNameDOB";
    private static final String FIND_BY_TAX_ID = "findByTaxId";
    private static final String FIND_AGENT_BY_TAX_ID = "findAgentByTaxId";
    private static final String FIND_AGENT_BY_AGENT_ID = "findAgentByAgentId";
    private static final String SHOW_SEARCH_DIALOG = "showSearchDialog";
    private static final String SHOW_AGENT_SEARCH_DIALOG = "showAgentSearchDialog";
    private static final String FIND_AGENT_FOR_CONTRACT_BY_NAME = "findAgentForContractByName";
    private static final String FIND_AGENT_FOR_CASE_BY_NAME = "findAgentForCaseByName";
    private static final String SHOW_AGENT_CONTRACT_SEARCH_DIALOG = "showAgentSearchDialog";
    private static final String SEARCH_FOR_REDIRECT_CLIENTS = "searchForRedirectClients";

    // Pages to return
    private static final String SEARCH = "/search/jsp/search.jsp";
    private static final String AGENT_SEARCH = "/search/jsp/agentSearch.jsp";
    private static final String AGENT_SEARCH_CONTRACT_SCREEN = "/quote/jsp/agentSelectionDialog.jsp";
    private static final String AGENT_SEARCH_CASE_SCREEN = "/contract/jsp/caseAgentSelectionDialog.jsp";
    private static final String AGENT_REDIRECT_SEARCH_DIALOG = "/agent/jsp/agentRedirectSearchDialog.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception  {

        String action = appReqBlock.getReqParm("action");
        appReqBlock.getHttpServletRequest().setAttribute("lastAction", action);

        if (action.equals(FIND_BY_COMPANY_STRUCTURE_CONTRACT_NUMBER)){

            return findByCompanyStructureContractNumber(appReqBlock);
        }
        else if (action.equals(FIND_BY_CONTRACT_NUMBER_ONLY)) {

        	//adding this for build
            return findByContractNumberOnly(appReqBlock);
        }
        else if (action.equals(FIND_BY_CLIENT_NAME)) {

            return findByClientName(appReqBlock);
        }
        else if (action.equals(FIND_AGENT_BY_CLIENT_NAME)) {

            return findAgentByClientName(appReqBlock);
        }
        else if (action.equals(FIND_AGENT_BY_CLIENT_NAME_DOB)) {

            return findAgentByClientNameDOB(appReqBlock);
        }
        else if (action.equals(FIND_BY_TAX_ID)){

            return findByTaxId(appReqBlock);
        }
        else if (action.equals(FIND_AGENT_BY_TAX_ID)){

            return findAgentByTaxId(appReqBlock);
        }
        else if (action.equals(FIND_AGENT_BY_AGENT_ID)) {

            return findAgentByAgentId(appReqBlock);
        }
        else if (action.equals(SHOW_SEARCH_DIALOG)){

            return showSearchDialog(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_SEARCH_DIALOG)) {

            return showAgentSearchDialog(appReqBlock);
        }
        else if (action.equals(FIND_AGENT_FOR_CONTRACT_BY_NAME)) {

            return findAgentForContractByName(appReqBlock);
        }
        else if (action.equals(FIND_AGENT_FOR_CASE_BY_NAME)) {

            return findAgentForCaseByName(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_CONTRACT_SEARCH_DIALOG)) {
            return showAgentSearchContractDialog(appReqBlock);
        }
        else if (action.equals(SEARCH_FOR_REDIRECT_CLIENTS))
        {
            return searchForRedirectClients(appReqBlock);
        }
        else {

            throw new Exception("Page not found.");
        }
    }

    protected String showSearchDialog(AppReqBlock appReqBlock) throws Exception {

    	SearchResponseVO[] searchClientContractVOs = buildSearchHistory(appReqBlock, Lookup.FIND_BY_CONTRACT_NUMBER_ONLY);

        searchClientContractVOs = filterForCompanySecurity(appReqBlock, searchClientContractVOs);

        if (searchClientContractVOs != null && searchClientContractVOs.length > 0)
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchClientContractVOs", searchClientContractVOs);
        }
    	
        loadDefaultSearchPageData(appReqBlock);

        return SEARCH;
    }

    protected SearchResponseVO[] buildSearchHistory(AppReqBlock appReqBlock, String searchCommand)
    {
    	String[] searchHistoryContracts = appReqBlock.getUserSession().getSearchHistory();
    	
    	List<SearchResponseVO> searchClientContractVOs = new ArrayList();
    	
    	if (searchHistoryContracts != null && searchHistoryContracts.length > 0)
    	{
    		
    		for (int n = searchHistoryContracts.length; n > 0; n--)
    		{
    			String contractNumber = searchHistoryContracts[n-1];
    			
		    	SearchRequestVO searchRequestVO = new SearchRequestVO();
		
		        if ((contractNumber != null) && ((contractNumber.length() > 2) && (contractNumber.length() < 10))) {
		        	contractNumber = search.util.StringUtils.addZeroesToContract(contractNumber);
		        }
		        searchRequestVO.setContractNumber(contractNumber);
		        
		        Lookup searchLookup = new LookupComponent();
		
		        SearchResponseVO[] searchResponseVO = searchLookup.executeSearch(searchCommand, searchRequestVO);
		
		        if (searchResponseVO !=  null)
		        {
		            translateCodeTableEntries(searchResponseVO);
		        }
		        
		        searchClientContractVOs.add(searchResponseVO[0]);
		    }
    	}
        return searchClientContractVOs.toArray(new SearchResponseVO[searchClientContractVOs.size()]);
    }

    protected String showAgentSearchDialog(AppReqBlock appReqBlock) throws Exception {

        return AGENT_SEARCH;
    }

    protected String findByCompanyStructureContractNumber(AppReqBlock appReqBlock) throws Exception {

        // Gets the named form variable from the request and sets the
        // current product in the UserSession.  If it is null or 0,
        // it leaves the current product structure setting alone.
        ProductStructure.setSecurityCurrentProdStructInSession(
                appReqBlock,
                "companyStructurePK");

        SearchResponseVO[] searchResponseVOs =
                 buildSearchClientContractVOs(appReqBlock,
                                                 Lookup.FIND_BY_PRODUCT_STRUCTURE_CONTRACT_NUMBER);

        searchResponseVOs = filterForCompanySecurity(appReqBlock, searchResponseVOs);

        if (searchResponseVOs != null && searchResponseVOs.length > 0)
        {
            if (searchResponseVOs[0].getSearchResponseContractInfoCount() > 0)
            {
                // There should only ever be one.
                String statusCode = searchResponseVOs[0].getSearchResponseContractInfo(0).getSegmentStatus();
                String segmentPK = searchResponseVOs[0].getSearchResponseContractInfo(0).getSegmentFK() + "";

                appReqBlock.getHttpServletRequest().setAttribute("segmentStatus", statusCode);

                appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");

                loadDefaultSearchPageData(appReqBlock);
            }
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");

            loadDefaultSearchPageData(appReqBlock);
        }

        return SEARCH;
    }

    protected String findByContractNumberOnly(AppReqBlock appReqBlock) throws Exception {

        // Gets the named form variable from the request and sets the
        // current product in the UserSession.  If it is null or 0,
        // it leaves the current product structure setting alone.
        ProductStructure.setSecurityCurrentProdStructInSession(
                appReqBlock,
                "companyStructurePK");

        SearchResponseVO[] searchClientContractVOs =
                buildSearchClientContractVOs(appReqBlock,
                                                Lookup.FIND_BY_CONTRACT_NUMBER_ONLY);

        searchClientContractVOs = filterForCompanySecurity(appReqBlock, searchClientContractVOs);

        if (searchClientContractVOs != null && searchClientContractVOs.length > 0)
        {
            if (searchClientContractVOs[0].getSearchResponseContractInfoCount() > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("searchClientContractVOs", searchClientContractVOs);

                // set the found contract's product structure in the session as the current one
                ProductStructure.setSecurityCurrentProdStructInSession(
                                    appReqBlock,
                                    searchClientContractVOs[0].getSearchResponseContractInfo(0).getProductStructureFK());
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
            }
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        loadDefaultSearchPageData(appReqBlock);

        return SEARCH;
    }

    protected String findByClientName(AppReqBlock appReqBlock) throws Exception
    {

        // Gets the named form variable from the request and sets the
        // current product in the UserSession.  If it is null or 0,
        // it leaves the current product structure setting alone.
        ProductStructure.setSecurityCurrentProdStructInSession(
                appReqBlock,
                "companyStructurePK");

        String clientSearchName = (String) appReqBlock.getReqParm("clientName").trim();

        if (clientSearchName.length() == 0)
        {
            appReqBlock.getHttpServletRequest().setAttribute("message", "Invalid Search Selection For Client Name");
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        else
        {
            SearchResponseVO[] searchClientContractVOs = buildSearchClientContractVOs(appReqBlock, Lookup.FIND_BY_CLIENT_NAME);

            searchClientContractVOs = filterForCompanySecurity(appReqBlock, searchClientContractVOs);

            if (searchClientContractVOs != null && searchClientContractVOs.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("searchClientContractVOs", searchClientContractVOs);
            }
        }

        loadDefaultSearchPageData(appReqBlock);

        return SEARCH;
    }

    protected String findByClientName(AppReqBlock appReqBlock, String returnPage) throws Exception
    {

        // Gets the named form variable from the request and sets the
        // current product in the UserSession.  If it is null or 0,
        // it leaves the current product structure setting alone.
        ProductStructure.setSecurityCurrentProdStructInSession(
                appReqBlock,
                "companyStructurePK");

        String clientSearchName = (String) appReqBlock.getReqParm("clientName").trim();

        if (clientSearchName.length() == 0)
        {
            appReqBlock.getHttpServletRequest().setAttribute("message", "Invalid Search Selection For Client Name");
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        else
        {
            SearchResponseVO[] searchClientContractVOs = buildSearchClientContractVOs(appReqBlock, Lookup.FIND_BY_CLIENT_NAME);

            searchClientContractVOs = filterForCompanySecurity(appReqBlock, searchClientContractVOs);

            if (searchClientContractVOs != null && searchClientContractVOs.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("searchClientContractVOs", searchClientContractVOs);
            }
        }

        return returnPage;
    }

    protected String searchForRedirectClients(AppReqBlock appReqBlock) throws Exception
    {
        String redirectType = appReqBlock.getFormBean().getValue("redirectType");
        appReqBlock.getHttpServletRequest().setAttribute("redirectType", redirectType);

        if (redirectType.equalsIgnoreCase("Assignee"))
        {
            return findByClientName(appReqBlock, AGENT_REDIRECT_SEARCH_DIALOG);
        }
        else
        {
            return findReversionAgentByClientName(appReqBlock, AGENT_REDIRECT_SEARCH_DIALOG);
        }
    }

    public String findAgentForContractByName(AppReqBlock appReqBlock) throws Exception {

        String nextPage = findAgentByClientName(appReqBlock, AGENT_SEARCH_CONTRACT_SCREEN);

//        if(clearPlacedAgentBranchVOs)
//        {
//            appReqBlock.getHttpSession().removeAttribute("placedAgentBranchVOs");
//        }
        return nextPage;
    }

    public String findAgentForCaseByName(AppReqBlock appReqBlock) throws Exception {

        String nextPage = findAgentByClientName(appReqBlock, AGENT_SEARCH_CASE_SCREEN);

        return nextPage;
    }

    protected String findAgentByClientName(AppReqBlock appReqBlock) throws Exception
    {
        return findAgentByClientName(appReqBlock, AGENT_SEARCH);
    }

    protected String findAgentByClientName(AppReqBlock appReqBlock, String nextPage) throws Exception
    {
        UISearchClientAgentVO[] uiSearchClientAgentVOs = buildUISearchClientAgentVOs(appReqBlock, AgentLookup.FIND_AGENT_BY_CLIENT_NAME);

        if (uiSearchClientAgentVOs != null) {

            appReqBlock.getHttpServletRequest().setAttribute("uiSearchClientAgentVOs", uiSearchClientAgentVOs);
        }
        else {

            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return nextPage;
    }

    protected String findReversionAgentByClientName(AppReqBlock appReqBlock, String nextPage) throws Exception
    {
    	String clientSearchName = (String) appReqBlock.getReqParm("clientName");
    	appReqBlock.getHttpServletRequest().setAttribute("clientName", clientSearchName);

    	String clientSearchLetter = "";
    	if (clientSearchName != null && !clientSearchName.equals(""))
    	{
    		clientSearchLetter = clientSearchName.substring(0, 1);
    	}

    	AgentLookup agentSearchLookup = new AgentLookupComponent();

    	SearchRequestVO searchRequestVO = buildSearchRequestVO(appReqBlock);

    	AgentSearchResponseVO agentSearchResponseVO = agentSearchLookup.executeSearch(AgentLookup.FIND_AGENT_BY_CLIENT_NAME, searchRequestVO);

    	agentSearchResponseVO = filterAgentForCompanySecurity(appReqBlock, agentSearchResponseVO);

    	List uiSearchVOs = new ArrayList();

    	if (agentSearchResponseVO != null)
    	{
    		ClientAgentVO[] clientAgentVOs = agentSearchResponseVO.getClientAgentVO();
    		if (clientAgentVOs != null)
    		{
    			for (int i = 0; i < clientAgentVOs.length; i++)
    			{
    				UISearchClientAgentVO uiSearchClientAgentVO = new UISearchClientAgentVO();
    				ClientDetailVO clientDetailVO = clientAgentVOs[i].getClientDetailVO();

    				// Process the client info (if any)
    				if (clientDetailVO != null)
    				{
    					UISearchClientVO uiSearchClientVO = new UISearchClientVO();
    					String clientLastName = clientDetailVO.getLastName();
    					if (clientLastName != null)
    					{
    						clientLastName.trim();
    					}
    					if (clientLastName == null ||
    							(!clientLastName.substring(0, 1).equalsIgnoreCase(clientSearchLetter) &&
    									!clientSearchLetter.equals("")))
    					{
    						uiSearchClientVO.setName(clientDetailVO.getCorporateName());
    					}
    					else
    					{
    						// Elementool issue 871 AMA 9/4/2014
    						String middleName = clientDetailVO.getMiddleName();
    						if (middleName != null && !middleName.equals("")) {   
    							uiSearchClientVO.setName(clientDetailVO.getLastName().trim() + ", " + clientDetailVO.getFirstName().trim() +  ", " + middleName.trim());
    						} else {
    							uiSearchClientVO.setName(clientDetailVO.getLastName().trim() + ", " + clientDetailVO.getFirstName().trim());
    						}
    					}

    					uiSearchClientVO.setClientId(clientDetailVO.getClientIdentification());
    					uiSearchClientVO.setClientDetailPK(clientDetailVO.getClientDetailPK());

    					uiSearchClientAgentVO.setUISearchClientVO(uiSearchClientVO);
    				}

    				AgentVO[] agentVOs = clientAgentVOs[i].getAgentVO();

    				if (agentVOs != null && agentVOs.length > 0)
    				{
    					for (int j = 0; j < agentVOs.length; j++)
    					{
    						UISearchAgentVO uiSearchAgentVO = new UISearchAgentVO();

    						uiSearchAgentVO.setAgentPK(agentVOs[j].getAgentPK());
    						uiSearchAgentVO.setCompanyName(Company.findByPK(new Long(agentVOs[j].getCompanyFK())).getCompanyName());

    						uiSearchClientAgentVO.addUISearchAgentVO(uiSearchAgentVO);
    					}
    				}

    				uiSearchVOs.add(uiSearchClientAgentVO);
    			}
    		}
    	}

    	if (uiSearchVOs != null)
    	{
    		UISearchClientAgentVO[] uiSearchClientAgentVOs = (UISearchClientAgentVO[]) uiSearchVOs.toArray(new UISearchClientAgentVO[uiSearchVOs.size()]);
    		appReqBlock.getHttpServletRequest().setAttribute("uiSearchClientAgentVOs", uiSearchClientAgentVOs);
    	}
    	else {

    		appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
    	}

    	return nextPage;
    }

    protected String findAgentByClientNameDOB(AppReqBlock appReqBlock) throws Exception {

        UISearchClientAgentVO[] uiSearchClientAgentVOs = buildUISearchClientAgentVOs(appReqBlock, AgentLookup.FIND_AGENT_BY_CLIENT_NAME_DOB);

        if (uiSearchClientAgentVOs != null) {

            appReqBlock.getHttpServletRequest().setAttribute("uiSearchClientAgentVOs", uiSearchClientAgentVOs);
        }
        else {

            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return AGENT_SEARCH;
    }

    protected String findAgentByTaxId(AppReqBlock appReqBlock) throws Exception {

        UISearchClientAgentVO[] uiSearchClientAgentVOs = buildUISearchClientAgentVOs(appReqBlock, AgentLookup.FIND_AGENT_BY_TAX_ID);

        if (uiSearchClientAgentVOs != null) {

            appReqBlock.getHttpServletRequest().setAttribute("uiSearchClientAgentVOs", uiSearchClientAgentVOs);
        }
        else {

            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return AGENT_SEARCH;
    }

    protected String findByTaxId(AppReqBlock appReqBlock) throws Exception {

        SearchResponseVO[] searchClientContractVOs = buildSearchClientContractVOs(appReqBlock, Lookup.FIND_BY_TAX_ID);

        searchClientContractVOs = filterForCompanySecurity(appReqBlock, searchClientContractVOs);

        if (searchClientContractVOs != null && searchClientContractVOs.length > 0) {

            appReqBlock.getHttpServletRequest().setAttribute("searchClientContractVOs", searchClientContractVOs);

        }
        else
        {

            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        loadDefaultSearchPageData(appReqBlock);

        return SEARCH;
    }

    protected String findAgentByAgentId(AppReqBlock appReqBlock) throws Exception {

        UISearchClientAgentVO[] uiSearchClientAgentVOs = buildUISearchClientAgentVOs(appReqBlock, Lookup.FIND_BY_AGENT_ID);

        if (uiSearchClientAgentVOs != null) {

            appReqBlock.getHttpServletRequest().setAttribute("uiSearchClientAgentVOs", uiSearchClientAgentVOs);
        }
        else {

            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return AGENT_SEARCH;
    }

    /**
     * Filter for only allowed productType product structures.
     * @param appReqBlock
     * @param searchResponseVOsIn
     * @return
     */
    private SearchResponseVO[] filterForCompanySecurity(AppReqBlock appReqBlock,
                                                        SearchResponseVO[] searchResponseVOsIn)
    {
        boolean viewAllClients = false;

        if (searchResponseVOsIn == null)
            return null;

        ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();

        if (productStructures == null || productStructures.length == 0)
        {
            return null;
        }

        // we will filter the results by productStructures allowed
        // Make a Set by business contract name for quick checks
        Long securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);

        Set productStructuresAllowedSet = ProductStructure.checkForAuthorizedStructures(productStructures);

        if (securityProductStructurePK > 0L)
        {
            Operator operator = Operator.findByOperatorName(appReqBlock.getUserSession().getUsername());

            if (operator != null)
            {
                viewAllClients = operator.checkViewAllAuthorization(securityProductStructurePK, "Clients");
            }
            else
            {
                viewAllClients = true;
            }
        }

        List filteredClientList = new ArrayList();
        List filteredContractList = new ArrayList();

        for (int i = 0; i < searchResponseVOsIn.length; i++)
        {
            filteredContractList.clear();
            SearchResponseVO searchResponseVO = searchResponseVOsIn[i];

            if (searchResponseVO.getSearchResponseContractInfoCount() > 0)
            {
                SearchResponseContractInfo[] searchRespContractInfo = searchResponseVO.getSearchResponseContractInfo();
                for (int j = 0; j < searchRespContractInfo.length; j++)
                {
                    SearchResponseContractInfo contractInfo = searchRespContractInfo[j];

                    String businessContractName = contractInfo.getBusinessContractName();

                    if (productStructuresAllowedSet.contains(businessContractName))
                    {
                        filteredContractList.add(contractInfo);
                    }
                }

                searchResponseVO.removeAllSearchResponseContractInfo();

                if (filteredContractList.size() > 0)
                {
                    searchRespContractInfo = (SearchResponseContractInfo[]) filteredContractList.toArray(new SearchResponseContractInfo[filteredContractList.size()]);
                    searchResponseVO.setSearchResponseContractInfo(searchRespContractInfo);
                    filteredClientList.add(searchResponseVO);
                }
                else if (viewAllClients)
                {
                    filteredClientList.add(searchResponseVO);
                }
            }
            else
            {
                if (viewAllClients)
                {
                    filteredClientList.add(searchResponseVO);
                    continue;
                }
            }
        }

        if (filteredClientList.size() == 0)
        {
            return null;
        }

        return (SearchResponseVO[]) filteredClientList.toArray(new SearchResponseVO[filteredClientList.size()]);
    }

    /**
     * Filter for only allowed productType product structures.
     * @param appReqBlock
     * @param searchResponseVOsIn
     * @return UISearchClientAgentVO[]
     */
    private AgentSearchResponseVO filterAgentForCompanySecurity(AppReqBlock appReqBlock,
                                                                 AgentSearchResponseVO agentSearchResponseVO)
    {
        boolean viewAllAgents = false;
        boolean viewAllClients = false;

        if (agentSearchResponseVO == null)
            return null;

        ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();

        if (productStructures == null || productStructures.length == 0)
        {
            return null;
        }

        // we will filter the results by productStructures allowed
        // Make a Set by business contract name for quick checks

        Long securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);

        List companiesAllowed = ProductStructure.checkForAuthorizedCompanies(productStructures);

        if (securityProductStructurePK > 0L)
        {
            Operator operator = Operator.findByOperatorName(appReqBlock.getUserSession().getUsername());

            if (operator != null)
            {
                viewAllAgents = operator.checkViewAllAuthorization(securityProductStructurePK, "Agents");
                viewAllClients = operator.checkViewAllAuthorization(securityProductStructurePK, "Clients");
            }
            else if (!appReqBlock.getUserSession().userLoggedIn())
            {
                viewAllAgents = true;
                viewAllClients = true;
            }
        }

        List filteredAgentList = new ArrayList();

        ClientAgentVO[] clientAgentVOs = agentSearchResponseVO.getClientAgentVO();

        agentSearchResponseVO.removeAllClientAgentVO();

        for (int i = 0; i < clientAgentVOs.length; i++)
        {
            AgentVO[] agentVOs = clientAgentVOs[i].getAgentVO();

            clientAgentVOs[i].removeAllAgentVO();

            for (int j = 0; j < agentVOs.length; j++)
            {
                String companyName = "";

                long companyFK = agentVOs[j].getCompanyFK();

                Company company =  Company.findByPK(new Long(companyFK));

                if (company != null)
                {
                    companyName = company.getCompanyName();
                }

                if (companiesAllowed.contains(companyName))
                {
                    clientAgentVOs[i].addAgentVO(agentVOs[j]);
                }
                else if (viewAllAgents)
                {
                    clientAgentVOs[i].addAgentVO(agentVOs[j]);
                }
            }

            if (clientAgentVOs[i].getAgentVOCount() > 0 || viewAllClients)
            {
                filteredAgentList.add(clientAgentVOs[i]);
            }
        }

        if (filteredAgentList.size() > 0)
        {
            clientAgentVOs = (ClientAgentVO[]) filteredAgentList.toArray(new ClientAgentVO[filteredAgentList.size()]);

            agentSearchResponseVO.setClientAgentVO(clientAgentVOs);
        }

        return agentSearchResponseVO;
    }

    private SearchResponseVO[] buildSearchClientContractVOs(AppReqBlock appReqBlock, String searchCommand) throws Exception
    {
//        String clientSearchName = (String) appReqBlock.getReqParm("clientName").trim();
//
//        String clientSearchLetter = "";
//        if (clientSearchName != null && !clientSearchName.equals(""))
//        {
//            clientSearchLetter = clientSearchName.substring(0, 1);
//        }

        Lookup searchLookup = new LookupComponent();

        SearchRequestVO searchRequestVO = buildSearchRequestVO(appReqBlock);

        SearchResponseVO[] searchResponseVO = searchLookup.executeSearch(searchCommand, searchRequestVO);

        if (searchResponseVO !=  null)
        {
            translateCodeTableEntries(searchResponseVO);
        }

        return searchResponseVO;

    }

    private void translateCodeTableEntries(SearchResponseVO[] searchResponseVO)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        for (int i = 0; i < searchResponseVO.length; i++)
        {
            if (searchResponseVO[i].getSearchResponseContractInfoCount() > 0)
            {
                SearchResponseContractInfo[] searchResponseContractInfo = searchResponseVO[i].getSearchResponseContractInfo();

                for (int j = 0; j < searchResponseContractInfo.length; j++)
                {
                    String status = searchResponseContractInfo[j].getSegmentStatus();
                    if (status != null)
                    {
                        String statusDesc = codeTableWrapper.getCodeDescByCodeTableNameAndCode("SEGMENTSTATUS", status);
                        searchResponseContractInfo[j].setSegmentStatus(statusDesc);
                    }
                    String option = searchResponseContractInfo[j].getOptionCode();
                    if (option != null)
                    {
                        String optionDesc = codeTableWrapper.getCodeDescByCodeTableNameAndCode("OPTIONCODE", option);
                        searchResponseContractInfo[j].setOptionCode(optionDesc);
                    }

                    String roleType = searchResponseContractInfo[j].getRoleType();
                    if (roleType != null)
                    {
                        String roleTypeDesc = codeTableWrapper.getCodeDescByCodeTableNameAndCode("LIFERELATIONTYPE", roleType);
                        if (roleTypeDesc != null)
                        {
                            searchResponseContractInfo[j].setRoleType(roleTypeDesc);
                        }
                    }
                }
            }
        }
    }

    private UISearchClientAgentVO[] buildUISearchClientAgentVOs(AppReqBlock appReqBlock, String searchCommand) throws Exception
    {
    	String clientSearchName = (String) appReqBlock.getReqParm("clientName");
    	appReqBlock.getHttpServletRequest().setAttribute("clientName", clientSearchName);

    	String clientSearchLetter = "";
    	if (clientSearchName != null && !clientSearchName.equals(""))
    	{
    		clientSearchLetter = clientSearchName.substring(0, 1);
    	}

    	AgentLookup agentSearchLookup = new AgentLookupComponent();

    	SearchRequestVO searchRequestVO = buildSearchRequestVO(appReqBlock);

    	AgentSearchResponseVO agentSearchResponseVO = agentSearchLookup.executeSearch(searchCommand, searchRequestVO);

    	agentSearchResponseVO = filterAgentForCompanySecurity(appReqBlock, agentSearchResponseVO);

    	List uiSearchVOs = new ArrayList();

    	if (agentSearchResponseVO != null)
    	{
    		ClientAgentVO[] clientAgentVOs = agentSearchResponseVO.getClientAgentVO();

    		if (clientAgentVOs != null)
    		{
    			for (int i = 0; i < clientAgentVOs.length; i++)
    			{
    				UISearchClientAgentVO uiSearchClientAgentVO = new UISearchClientAgentVO();

    				ClientDetailVO clientDetailVO = clientAgentVOs[i].getClientDetailVO();

    				// Process the client info (if any)
    				if (clientDetailVO != null)
    				{
    					UISearchClientVO uiSearchClientVO = new UISearchClientVO();
    					String clientLastName = clientDetailVO.getLastName();
    					if (clientLastName != null)
    					{
    						clientLastName.trim();
    					}
    					if (clientLastName == null ||
    							(!clientLastName.substring(0, 1).equalsIgnoreCase(clientSearchLetter) &&
    									!clientSearchLetter.equals("")))
    					{
    						uiSearchClientVO.setName(clientDetailVO.getCorporateName());
    					}
    					else
    					{
    						// Elementool issue 871 AMA 9/3/2014
    						String middleName = clientDetailVO.getMiddleName();
    						if (middleName != null && !middleName.equals("")) {              	
    							uiSearchClientVO.setName(clientDetailVO.getLastName().trim() + ", " + clientDetailVO.getFirstName().trim() +  ", " + middleName.trim());
    						} else {
    							uiSearchClientVO.setName(clientDetailVO.getLastName().trim() + ", " + clientDetailVO.getFirstName().trim());
    						}
    					}
    					uiSearchClientVO.setClientId(clientDetailVO.getClientIdentification());
    					uiSearchClientVO.setClientDetailPK(clientDetailVO.getClientDetailPK());

    					uiSearchClientAgentVO.setUISearchClientVO(uiSearchClientVO);
    				}

    				// Process the segment info (if any)
    				AgentVO[] agentVOs = clientAgentVOs[i].getAgentVO();

    				if (agentVOs != null && agentVOs.length > 0)
    				{
    					boolean agentIsPlaced = false;
    					List agentNumbers = new ArrayList();

    					for (int j = 0; j < agentVOs.length; j++)
    					{
    						agentIsPlaced = false;

    						UISearchAgentVO uiSearchAgentVO = new UISearchAgentVO();

    						uiSearchAgentVO.setAgentPK(agentVOs[j].getAgentPK());
    						uiSearchAgentVO.setAgentTypeCT(agentVOs[j].getAgentTypeCT());
    						uiSearchAgentVO.setAgentStatusCT(agentVOs[j].getAgentStatusCT());

    						AgentContract[] agentContracts = AgentContract.findBy_AgentFK(new Long(agentVOs[j].getAgentPK()));

    						if (agentContracts.length > 0)
    						{
    							for (int k = 0; k < agentContracts.length; k++)
    							{
    								if (!agentContracts[k].getPlacedAgents().isEmpty())
    								{
    									agentIsPlaced = true;

    									Set<PlacedAgent> placedAgents = agentContracts[k].getPlacedAgents();

    									Iterator it = placedAgents.iterator();
    									while (it.hasNext())
    									{
    										PlacedAgent placedAgent = (PlacedAgent) it.next();
    										ClientRole clientRole = placedAgent.getClientRole();

    										if (!agentNumbers.contains(clientRole.getReferenceID()))
    										{
    											agentNumbers.add(clientRole.getReferenceID());
    										}
    									}
    								}
    							}
    						}

    						if (!agentIsPlaced && agentVOs[j].getAgentNumber() != null)
    						{
    							agentNumbers.add(agentVOs[j].getAgentNumber());
    						}

    						AgentNumberList[] agentNumberList = new AgentNumberList[agentNumbers.size()];

    						for (int k = 0; k < agentNumbers.size(); k++)
    						{
    							AgentNumberList agentNumber = new AgentNumberList();
    							agentNumber.setAgentNumber((String) agentNumbers.get(k));
    							agentNumberList[k] = agentNumber;
    						}

    						uiSearchAgentVO.setAgentNumberList(agentNumberList);
    						uiSearchClientAgentVO.addUISearchAgentVO(uiSearchAgentVO);
    					}
    				}

    				uiSearchVOs.add(uiSearchClientAgentVO);
    			}
    		}
    	}

    	return (UISearchClientAgentVO[]) uiSearchVOs.toArray(new UISearchClientAgentVO[uiSearchVOs.size()]);
    }

    private void loadDefaultSearchPageData(AppReqBlock appReqBlock) throws Exception {

        // Load product structures -  custom tag gets this now
        // engine.business.Lookup lookupComp = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");
        // ProductStructureVO[] productStructureVOs = lookupComp.getProductStructuresByTypeCode("Product");
        // appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        // Store last action
        appReqBlock.getHttpServletRequest().setAttribute("lastAction", appReqBlock.getReqParm("action"));
        String clientSearchName = appReqBlock.getReqParm("clientName");
        if (clientSearchName != null && !clientSearchName.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("lastClientSearchLetter", clientSearchName.substring(0,1));
        }
    }

    private SearchRequestVO buildSearchRequestVO(AppReqBlock appReqBlock) throws Exception {

        SearchRequestVO searchRequestVO = new SearchRequestVO();

        String productStructurePK = appReqBlock.getReqParm("companyStructurePK");
        if (productStructurePK == null || !Util.isANumber(productStructurePK)) {

            productStructurePK = "0";
        }
        searchRequestVO.setProductStructurePK(Long.parseLong(productStructurePK));
        String cn = appReqBlock.getReqParm("contractNumber");
        if ((cn != null) && ((cn.length() > 2) && (cn.length() < 10))) {
        	cn = search.util.StringUtils.addZeroesToContract(cn);
        }
        searchRequestVO.setContractNumber(cn);
        String clientName = appReqBlock.getReqParm("clientName");
        clientName = Util.substitute(clientName, "'", "''");
        searchRequestVO.setName(clientName);
        searchRequestVO.setTaxId(appReqBlock.getReqParm("taxId"));
        searchRequestVO.setAgentId(appReqBlock.getReqParm("agentId"));

        return searchRequestVO;
    }

    protected String showAgentSearchContractDialog(AppReqBlock appReqBlock) throws Exception {

        return AGENT_SEARCH_CONTRACT_SCREEN;
    }
}
