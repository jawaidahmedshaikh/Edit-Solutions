package com.selman.calcfocus.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.EventCycleConsequencesCoverageVO;
import edit.common.vo.EventCycleConsequencesVO;
import edit.common.vo.LifeVO;
import edit.common.vo.PremiumDueVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.TransactionCorrespondenceVO;
import edit.common.vo.VOObject;
import edit.common.vo.SegmentDocVO;
import edit.common.vo.BillScheduleVO;
import edit.common.vo.PolicySummaryVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ContractSetupVO;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.hibernate.SessionHelper;
import engine.Company;
import engine.sp.InstOneOperand;
import engine.sp.SPException;
import engine.sp.ScriptProcessor;
import engine.sp.custom.entity.*;
import event.EDITTrx;
import event.TransactionPriority;
import fission.utility.DOMUtil;
import fission.utility.Util;
import java.math.BigDecimal;
import java.sql.Connection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.dom4j.Element;

import com.selman.calcfocus.factory.CalcFocusRequestBuilder;
import com.selman.calcfocus.response.CalculateResponse;
import com.selman.calcfocus.response.EventCycleConsequences;
import com.selman.calcfocus.response.EventCycleConsequencesCoverage;
import com.selman.calcfocus.response.PolicySummary;
import com.selman.calcfocus.response.Error;
import com.selman.calcfocus.service.CalcFocusLoggingService;
import com.selman.calcfocus.service.CalculateRequestService;

import contract.Life;
import contract.PremiumDue;
import contract.Segment;
import contract.dm.dao.DAOFactory;
import contract.dm.dao.SegmentDAO;


public class RequestMapper
{
	private Map<String, Object> map = new HashMap<>();
    
    public RequestMapper(ScriptProcessor sp) throws Exception {
    	mapDataForRequest(sp);
    }

    /**
     * Pops the key/value pair stored on the stack and stores them in the dataMap
     * with name provided in the instruction
     * 
     * @param execSP
     * @throws Exception 
     */
    protected void mapDataForRequest(ScriptProcessor sp) throws Exception
    {
        	 
        	// EDITTrxVO
        	Segment drivingSegment = null;
        	Element editTrxElement = null;
        	EDITTrxVO editTrxVO = null;
        	EDITTrxVO[] editTrxVOs = null;
    		int priority = 0;
    		TransactionPriority[] transactionPriority = TransactionPriority
    				.findBy_TrxType(EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY);
    		if (transactionPriority != null && transactionPriority.length > 0) {
    			priority = transactionPriority[0].getPriority();
    		}
        	
        	try {
        		editTrxElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&EDITTrxVO", sp));
        	} catch (SPException e) {
        		// try again
        		try {
        			editTrxElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&EDITTrx", sp));
            	} catch (SPException e2) {
            		// return error??
                    throw new SPException("Calltocalcfocus could not locate associated EDITTrx record", SPException.INSTRUCTION_PROCESSING_ERROR);
            	}
        	}
            
            if (editTrxElement != null) {
                editTrxVO = (EDITTrxVO) DOMUtil.mapElementToVO(editTrxElement);
                
            	// set driving segment (base vs rider)
            	drivingSegment = Segment.findBy_EDITTrxPK(editTrxVO.getEDITTrxPK());
            	if (drivingSegment != null) {
            		map.put("drivingSegmentPK", drivingSegment.getSegmentPK());
            		
            		if (drivingSegment.getSegmentFK() == null) {
                    	map.put("baseIndicator", "true");  // is a base-coverage trx
            		} else {
                    	map.put("baseIndicator", "false"); // is a rider trx
            		}
            	}
            	
            	// get complexChangeType
                ContractSetupVO[] contractSetupVO = event.dm.dao.DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());
                if (contractSetupVO != null && contractSetupVO.length > 0) {
            		map.put("complexChangeType", contractSetupVO[0].getComplexChangeTypeCT());
                }
            }
            
        	map.put("EDITTrxVO", editTrxVO);
        	
        	// base SegmentVO
        	Element segmentElement = null;
        	SegmentVO segmentVO = null;
        	
        	try {
        		segmentElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&SegmentVO", sp));
            	segmentVO = (SegmentVO) DOMUtil.mapElementToVO(segmentElement);
        	} catch (SPException e) {
        		// try again
        		try {
        			segmentElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&Segment", sp));
                	segmentVO = (SegmentVO) DOMUtil.mapElementToVO(segmentElement);
            	} catch (SPException e2) {
            		// try again
            		if (drivingSegment != null) {
                		if (drivingSegment.getSegmentFK() == null) {
                			segmentVO = (SegmentVO) drivingSegment.getVO();
                		} else {
                			segmentVO = (SegmentVO) Segment.findByPK(drivingSegment.getSegmentFK()).getVO();
                		}
                	}
            	}
        	}
        	
        	map.put("SegmentVO", segmentVO);
        	
        	// Riders
        	List<Element> riderElements = new ArrayList<>();
        	SegmentVO[] riderVOs = null;
        	
        	try {
        		        		
        		String operandName = "&Riders";
        		boolean stopLoop = false;
        		
        		while (!stopLoop) {
	                boolean lastActiveSet = false;
	
	                //determine if there is an alias
	                operandName = checkForAlias(operandName, sp);
	
	                boolean forEachFoundInTable = false;
	
	                //stopLoop process needs to know if there ever was any of the type requested
	                if (sp.forEachHTContainsKey(operandName))
	                {
	                    forEachFoundInTable = true;
	                }
	
	                lastActiveSet = sp.setLastActiveElement(operandName);
	
	                if (!lastActiveSet)
	                {
	                    if (forEachFoundInTable)
	                    {
	                        sp.removeForeachEntry(operandName);
	                    }
	                    
	                    stopLoop = true;
	                } else {
	                	String segmentPath = checkForAlias("&RiderSegment", sp);
	                	riderElements.add(sp.getSPParams().getLastActiveElement(segmentPath));

	                }
        		}
        		
        		riderVOs = new SegmentVO[riderElements.size()];
        		for (int x = 0; x < riderElements.size(); x++) {
        			riderVOs[x] = (SegmentVO) DOMUtil.mapElementToVO(riderElements.get(x));
        		}
                
        	} catch (SPException e) {
                riderVOs = new SegmentDAO().findRidersBySegmentPK(drivingSegment.getSegmentPK());
        	}
        	
        	map.put("RiderVOs", riderVOs);
        	
        	// BillSchedule
        	Element billScheduleElement = null;
        	BillScheduleVO billScheduleVO = null;
        	
        	try {
        		billScheduleElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&BillSchedule", sp));
        		billScheduleVO = (BillScheduleVO) DOMUtil.mapElementToVO(billScheduleElement);
        	} catch (SPException e) {
        		// try again
                billScheduleVO = (BillScheduleVO) segmentVO.getParentVO(BillScheduleVO.class);
        	}
            
        	map.put("BillScheduleVO", billScheduleVO);
        	
        	// Life
        	Element lifeElement = null;
        	LifeVO lifeVO = null;
        	
        	try {
        		lifeElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&Life", sp));
        		lifeVO = (LifeVO) DOMUtil.mapElementToVO(lifeElement);
        	} catch (SPException e) {
        		// try again
        		try {
        			lifeElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&LifeVO", sp));
        			lifeVO = (LifeVO) DOMUtil.mapElementToVO(lifeElement);
            	} catch (SPException e2) {
            		lifeVO = contract.dm.dao.DAOFactory.getLifeDAO().findLifeBySegmentFK(segmentVO.getSegmentPK(), false, null)[0];
            	}
        	}
        	
        	map.put("LifeVO", lifeVO);
        	
        	// PremiumDue
        	Element premiumDueElement = null;
        	PremiumDueVO premiumDueVO = null;
        	PremiumDue premiumDue = null;
        	
        	try {
        		premiumDueElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&PremiumDue", sp));
        		premiumDueVO = (PremiumDueVO) DOMUtil.mapElementToVO(premiumDueElement);
        		premiumDue = PremiumDue.findByPK(premiumDueVO.getPremiumDuePK());
        	} catch (SPException | NullPointerException e) {
        		// try again
        		try {
        			premiumDueElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&PremiumDueVO", sp));
        			premiumDueVO = (PremiumDueVO) DOMUtil.mapElementToVO(premiumDueElement);
            	} catch (SPException | NullPointerException e2) {
            		if (segmentVO.getSegmentStatusCT().equals("SubmitPend")) {
                        premiumDue = 
                    		PremiumDue.findActiveSeparateBySegmentPK(segmentVO.getSegmentPK(),
                    				new EDITDate(segmentVO.getEffectiveDate()))[0];
            			
            		} else {
                        premiumDue = 
                    		PremiumDue.findActiveSeparateBySegmentPK(segmentVO.getSegmentPK(), 
                    				new EDITDate(editTrxVO.getEffectiveDate()))[0];
            		}
            	}
        	}
        	
        	map.put("PremiumDueVO", premiumDueVO);
        	map.put("PremiumDue", premiumDue);
        	
        	// ContractClients
            ContractClientVO[] contractClientVOs = contract.dm.dao.DAOFactory.getContractClientDAO().findBySegmentFK(segmentVO.getSegmentPK(), false, null);

        	map.put("ContractClientVOs", contractClientVOs);
        	
        	// ClientRole
        	List<ClientRoleVO> clientRoleVOs = new ArrayList<>();;
        	
            role.business.Lookup roleLookup = new role.component.LookupComponent();
            List<Object> clientVOInclusionList = new ArrayList<>();
            clientVOInclusionList.add(ClientDetailVO.class);
            clientVOInclusionList.add(ClientAddressVO.class);
            
            for (ContractClientVO contractClientVO : contractClientVOs) {
                ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(contractClientVO.getClientRoleFK(), clientVOInclusionList);
                clientRoleVOs.add(clientRoleVO);
            }
        	
        	map.put("ClientRoleVOs", clientRoleVOs.toArray(new ClientRoleVO[0]));

        	// ProductStructure
        	ProductStructureVO productStructureVO = null;

        	try {
        		Element productStructureElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias("&ProductStructure", sp));
        		productStructureVO = (ProductStructureVO) DOMUtil.mapElementToVO(productStructureElement);

        	} catch (SPException e) {
	            	engine.business.Lookup engineLookup = new engine.component.LookupComponent();
	                productStructureVO = engineLookup.getByProductStructureId(segmentVO.getProductStructureFK())[0];
	        }
        	
        	map.put("ProductStructureVO", productStructureVO);

        	Company company = Company.findByPK(productStructureVO.getCompanyFK());
        	map.put("Company", company);
        	
        	
        	// calculated fields:
        	
	        EDITTrxVO[] mvTrxs =  event.dm.dao.DAOFactory.getEDITTrxDAO().findBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(
	        		   segmentVO.getSegmentPK(),
	        		   EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY,
	        		   editTrxVO.getEffectiveDate(),
	        		   EDITTrx.PENDINGSTATUS_HISTORY);
	        
			if (mvTrxs != null && mvTrxs.length > 0) {
				// send all trx run since that MV
				editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO().findAll_MonthlyValTrx(
						segmentVO.getSegmentPK(), mvTrxs[0].getEffectiveDate(), priority);
            	map.put("effectiveDate", mvTrxs[0].getEffectiveDate());
				map.put("mvEditTrxVO", mvTrxs[0]);
			} else {
				// send all trx
				editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO()
						.findAllBySegmentPKAndProcessed(segmentVO.getSegmentPK());
            	map.put("effectiveDate", segmentVO.getEffectiveDate());
			}
			
			// if transaction is a CC we need to include in transactionRequests in the CF request
			if (editTrxVO.getTransactionTypeCT().equals("CC") || editTrxVO.getTransactionTypeCT().equals("WI")) {
				int numOfEditTrxVOs;
				if (editTrxVOs == null) {
					numOfEditTrxVOs = 0;
				} else {
					numOfEditTrxVOs = editTrxVOs.length;
				}
				EDITTrxVO[] editTrxVOsWithCC = new EDITTrxVO[numOfEditTrxVOs + 1];
				if (editTrxVOsWithCC.length ==1) {
				    editTrxVOsWithCC[numOfEditTrxVOs] = editTrxVO;
				} else {
				    for (int e = 0; e < editTrxVOsWithCC.length - 1; e++) {
					    editTrxVOsWithCC[e] = editTrxVOs[e];
				    }
				    editTrxVOsWithCC[editTrxVOsWithCC.length - 1] = editTrxVO;
				}
			    map.put("EDITTrxVOs", editTrxVOsWithCC);
//			    map.put("EDITTrxVOs", editTrxVOs);
			} else {
			    map.put("EDITTrxVOs", editTrxVOs);
			}
			if (editTrxVOs != null) {
			    map.put("EDITTrxVO", editTrxVO); 
			}
        	
        	// processDate
        	map.put("processDate", new EDITDate().getFormattedDate());
        	
        	//WORKING STORAGE VALUES:
        	
        	// Fields with default values when not in working storage
        	map.put("carrierAdminSystem", Util.initString((String) sp.getWSEntry("carrierAdminSystem"), "VENUS"));
        	map.put("allowMEC", Util.initString((String) sp.getWSEntry("allowMEC"), "true"));
        	map.put("loanInterestAssumption", Util.initString((String) sp.getWSEntry("loanInterestAssumption"), "Capitalize"));
        	map.put("lastMonthaversaryCharge", Util.initString((String) sp.getWSEntry("lastMonthaversaryCharge"), "0"));
        	map.put("dboSwitch", Util.initString((String) sp.getWSEntry("dboSwitch"), "false"));
        	map.put("sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce", Util.initString((String) sp.getWSEntry("carrierAdminSystem"), "0"));
        	map.put("unnecessaryPremiumIndicator", Util.initString((String) sp.getWSEntry("unnecessaryPremiumIndicator"), "false"));
        	map.put("benefitIncreaseIndicator", Util.initString((String) sp.getWSEntry("benefitIncreaseIndicator"), "false"));
        	
        	// CoverageUpdate/Solve
        	map.put("specifiedAmount", sp.getWSEntry("specifiedAmount"));
        	map.put("goalAmount", sp.getWSEntry("goalAmount"));
        	map.put("scenarioName", sp.getWSEntry("scenarioName"));
        	map.put("transactionRequestType", sp.getWSEntry("transactionRequestType"));
        	map.put("startDateType", sp.getWSEntry("startDateType"));
        	map.put("faceSolveBasedOnPremium", sp.getWSEntry("faceSolveBasedOnPremium"));
        	map.put("faceAmountRule", sp.getWSEntry("faceAmountRule"));
        	map.put("facePremiumSolve", sp.getWSEntry("facePremiumSolve"));
        	map.put("solveGoalType", sp.getWSEntry("solveGoalType"));
        	map.put("facePremiumSolveType", sp.getWSEntry("facePremiumSolveType"));
        	map.put("goalAmountExpressedAs", sp.getWSEntry("goalAmountExpressedAs"));
        	map.put("solveEnd", sp.getWSEntry("solveEnd"));
        	map.put("goalYear", sp.getWSEntry("goalYear"));
        	map.put("transactionRequestEffectiveDate", sp.getWSEntry("transactionRequestEffectiveDate"));
        	map.put("specifiedDate", sp.getWSEntry("specifiedDate"));
        	map.put("trxEffectiveDate", sp.getWSEntry("trxEffectiveDate"));
        	map.put("maturityDate", sp.getWSEntry("maturityDate"));
        	map.put("goalEndDate", sp.getWSEntry("goalEndDate"));
        	map.put("annualPremium", sp.getWSEntry("annualPremium"));
        	map.put("goalPercent", sp.getWSEntry("goalPercent"));
        	map.put("subdays", sp.getWSEntry("subdays"));
        	map.put("goalAge", sp.getWSEntry("goalAge"));
        	

        	// Fields that send NULL when not in working storage
        	map.put("newFaceAmount", sp.getWSEntry("NewFaceAmount"));
        	map.put("totalFaceAmount", sp.getWSEntry("TotalFaceAmount"));
        	map.put("newRiderUnits", sp.getWSEntry("NewRiderUnits"));
        	map.put("SelectedRiderPK", sp.getWSEntry("SelectedRiderPK"));
        	map.put("CFprocessType", (String) sp.getWSEntry("CFprocessType"));
        	map.put("CFtransactionType", (String) sp.getWSEntry("CFtransactionType"));
        	map.put("transactionType", (String) sp.getWSEntry("TrxType"));
        	map.put("CFMAPAccum", sp.getWSEntry("CFMAPAccum"));
        	map.put("CFoutputInstruction", (String) sp.getWSEntry("CFoutputInstruction"));
        	map.put("policyStatus", (String) sp.getWSEntry("policyStatus"));
        	map.put("fundType", sp.getWSEntry("fundType")); // called 'type' in doc but there is more than one type???
        	map.put("subType", sp.getWSEntry("subType"));
        	map.put("principal", sp.getWSEntry("principal"));
        	map.put("lastPrincipalChangeDate", sp.getWSEntry("lastPrincipalChangeDate"));
        	map.put("currentYearUncapitalizedInterest", sp.getWSEntry("currentYearUncapitalizedInterest"));
        	map.put("currentYearUncapitalizedCredit", sp.getWSEntry("currentYearUncapitalizedCredit"));
        	map.put("faceAmount", sp.getWSEntry("faceAmount"));
        	map.put("legacyPlanCode", sp.getWSEntry("legacyPlanCode"));
        	map.put("issueAge", sp.getWSEntry("ageAtIssue")); // is this right??
        	map.put("tobaccoUse", sp.getWSEntry("tobaccoUse"));
        	map.put("partyGUID", sp.getWSEntry("partyGUID"));
        	map.put("roleType", sp.getWSEntry("roleType"));
        	map.put("interestAssumption", sp.getWSEntry("interestAssumption"));
        	map.put("mortalityAssumption", sp.getWSEntry("mortalityAssumption"));
        	map.put("financialEventType", sp.getWSEntry("financialEventType")); // called 'type' in doc
        	map.put("amount", sp.getWSEntry("amount")); //trx amount ? edittrxVo?
        	map.put("mode", sp.getWSEntry("mode"));
        	map.put("historicalIndicator", sp.getWSEntry("historicalIndicator"));        	
        	map.put("startDate", sp.getWSEntry("startDate"));
        	map.put("endDate", sp.getWSEntry("endDate"));
        	map.put("policyAccumulationValueAtStartDate", sp.getWSEntry("policyAccumulationValueAtStartDate"));
        	map.put("policySurrenderValueAtStartDate", sp.getWSEntry("policySurrenderValueAtStartDate"));
        	map.put("policyLoanedAccountValueAtStartDate", sp.getWSEntry("policyLoanedAccountValueAtStartDate"));
        	map.put("faceAmountAtStartDate", sp.getWSEntry("faceAmountAtStartDate"));
        	map.put("policyAccumulationValueAtEndDate", sp.getWSEntry("policyAccumulationValueAtEndDate"));
        	map.put("policyLoanInterestAccruedAtEndDate", sp.getWSEntry("policyLoanInterestAccruedAtEndDate"));
			map.put("surrenderChargeAtEndDate", sp.getWSEntry("surrenderChargeAtEndDate"));
			map.put("deathBenefitAtEndDate", sp.getWSEntry("deathBenefitAtEndDate"));
			map.put("faceAmountAtEndDate", sp.getWSEntry("faceAmountAtEndDate"));
			map.put("sumOfPremiumsPaid", sp.getWSEntry("sumOfPremiumsPaid"));
			map.put("sumOfLoans", sp.getWSEntry("sumOfLoans"));
			map.put("sumOfLoanRepayments", sp.getWSEntry("sumOfLoanRepayments"));
			map.put("sumOfWithdrawals", sp.getWSEntry("sumOfWithdrawals"));
			map.put("sumOfRiderCOI", sp.getWSEntry("sumOfRiderCOI"));
			map.put("sumOfCOI", sp.getWSEntry("sumOfCOI"));
			map.put("interestEarned", sp.getWSEntry("interestEarned"));
			map.put("mortalityChargeAndRiderCOI", sp.getWSEntry("mortalityChargeAndRiderCOI"));
			map.put("baseCOI", sp.getWSEntry("baseCOI"));
			map.put("riderCOI", sp.getWSEntry("riderCOI"));
			map.put("sumOfAdminFees", sp.getWSEntry("sumOfAdminFees"));
			map.put("interestRate", sp.getWSEntry("interestRate"));
			map.put("sumOfPremiumLoad", sp.getWSEntry("sumOfPremiumLoad"));
			map.put("insuranceCompany", sp.getWSEntry("insuranceCompany"));

       /*     try {
                FileOutputStream fos = new FileOutputStream("c:\\venus_map.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(map);
                oos.reset();
                oos.close();
                fos.close();
                System.out.printf("Serialized HashMap data is saved in hashmap.ser");
			} catch (IOException ioe) {
			    ioe.printStackTrace();
			} */
            
  	      CalcFocusRequestBuilder builder = new CalcFocusRequestBuilder(null, map);
  		  Connection connection = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS)
				.connection();
  	      Object responseObj = null;
          try {
  	          responseObj = CalculateRequestService.calcFocusRequest(connection, builder.getReq());
          } catch (Exception e) {
        	  throw new SPException("Call to CalcFocus Failed", SPException.PARAMETER_INVALID_ERROR, e);
          }
  	     
  	      CalculateResponse response = null;
  	      Error error = null;


		  if (responseObj instanceof CalculateResponse) {
			    System.out.println("Success");	
			    response = (CalculateResponse)responseObj;
		  } else {
			    System.out.println("Failure");	
			    error = (Error)responseObj;
		  }

  	      if (response != null && response.getPolicySummary() != null) {
  	    	  Element policySummaryElement = createPolicySummaryElement(response.getPolicySummary().get(0));
  	    	  sp.getResultDocVO().getRootElement().add(policySummaryElement);
  	      } else if (error != null) {
  	    	  // what should we do here?
  	      }
  	      
    }
    
    public static String checkForAlias(String fieldPath, ScriptProcessor scriptProcessor) throws SPException
    {
        String[] names = Util.fastTokenizer(fieldPath, ".");

        String operandName = "";

        for (int i = 0; i < names.length; i++)
        {
            if (i > 0)
            {
                operandName = operandName + ".";
            }

            if (names[i].startsWith("&"))
            {
                operandName = operandName + scriptProcessor.getAliasFullyQualifiedName(names[i]);
            }
            else
            {
                operandName = operandName + names[i];
            }
        }

        return operandName;
    }
    
    public Element createPolicySummaryElement(PolicySummary pol) {

    	PolicySummaryVO policySummaryVO = new PolicySummaryVO();
    
    	policySummaryVO.setGUID(pol.getGUID());
    	policySummaryVO.setEffectiveDate(pol.getEffectiveDate().toString());
    	policySummaryVO.setPolicyNumber(pol.getPolicyNumber());

		EventCycleConsequences ecc = pol.getEventCycleConsequences();
		
		EventCycleConsequencesVO eventCycleConsequencesVO = new EventCycleConsequencesVO();
		eventCycleConsequencesVO.setDefinitionOfLifeInsurance(ecc.getDefinitionOfLifeInsurance().value());
		eventCycleConsequencesVO.setCommissionTarget(new BigDecimal(ecc.getCommissionTarget()));
		eventCycleConsequencesVO.setMinimumPremiumTarget(new BigDecimal(ecc.getMinimumPremiumTarget()));
		eventCycleConsequencesVO.setSurrenderChargeTarget(new BigDecimal(ecc.getSurrenderChargeTarget()));
		eventCycleConsequencesVO.setSevenPayPremium(new BigDecimal(ecc.getSevenPayPremium()));
		eventCycleConsequencesVO.setGuidelineLevelPremium(new BigDecimal(ecc.getGuidelineLevelPremium()));
		eventCycleConsequencesVO.setGuidelineSinglePremium(new BigDecimal(ecc.getGuidelineSinglePremium()));
		eventCycleConsequencesVO.setStatCRVM(new BigDecimal(ecc.getStatCRVM()));
		eventCycleConsequencesVO.setFitCRVM(new BigDecimal(ecc.getFITCRVM()));
		policySummaryVO.setEventCycleConsequencesVO(eventCycleConsequencesVO);
		
        for (EventCycleConsequencesCoverage cov : pol.getEventCycleConsequencesCoverage()) {
        	EventCycleConsequencesCoverageVO coverageVO = new EventCycleConsequencesCoverageVO();
        	coverageVO.setGUID(cov.getGUID());
        	coverageVO.setSourceCoverageID(cov.getSourceCoverageID());
    		coverageVO.setProductCode(cov.getProductCode());
    		coverageVO.setEffectiveDate(cov.getEffectiveDate().toString());
    		coverageVO.setQualifiedBenefit(cov.isQualifiedBenefit());
    		coverageVO.setCommissionTarget(new BigDecimal(cov.getCommissionTarget()));
    		coverageVO.setMinimumPremiumTarget(new BigDecimal(cov.getMinimumPremiumTarget()));

    		policySummaryVO.addEventCycleConsequencesCoverageVO(coverageVO);

    	}
        
        return VOObject.map(policySummaryVO);
    }
    
}

