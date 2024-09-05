package event.batch;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;

import org.dom4j.Element;

import com.selman.calcfocus.correspondence.builder.CalcFocusCorrespondenceRequestBuilder;
import com.selman.calcfocus.correspondence.builder.PlanCode;
import com.selman.calcfocus.correspondence.builder.PolicyAndBaseCoverageValues;
import com.selman.calcfocus.correspondence.db.JdbcConnection;
import com.selman.calcfocus.correspondence.db.Query;
import com.selman.calcfocus.factory.CalcFocusRequestBuilder;
import com.selman.calcfocus.factory.VenusValues;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.request.Policy;
import com.selman.calcfocus.response.CalculateResponse;
import com.selman.calcfocus.service.CalcFocusLoggingService;
import com.selman.calcfocus.service.CalculateRequestService;
import com.selman.calcfocus.util.BadDataException;
import com.selman.calcfocus.util.CalcFocusUtils;

import batch.business.*;
import billing.BillSchedule;
import contract.CommissionPhase;
import contract.PremiumDue;
import contract.Segment;
import contract.dm.composer.SegmentComposer;
import contract.dm.dao.SegmentDAO;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.BillScheduleVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.LifeVO;
import edit.common.vo.PremiumDueVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentVO;
import edit.services.EditServiceLocator;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import engine.Company;
import engine.sp.InstOneOperand;
import engine.sp.SPException;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.TransactionPriority;
import fission.utility.DOMUtil;
import fission.utility.Util;
import group.ContractGroup;
import logging.Log;

public class PolicyPagesProcessor implements Serializable
{

	private static final long serialVersionUID = -8591563280679286692L;

	public PolicyPagesProcessor()
    {
        super();
    }

    public void runPolicyPages(EDITDate extractDate) throws Exception
    {
    	
        EditServiceLocator.getSingleton().getBatchAgent()
                          .getBatchStat(Batch.BATCH_JOB_POLICY_PAGES).tagBatchStart(Batch.BATCH_JOB_POLICY_PAGES,
            "Update UnitValues");
        System.out.println("policy pages");
		Connection connection = null;
		Connection engineConnection = null;
		Connection esConnection = null;
		System.out.println("Setting variables...");
		String dbURL = null;
		String user = null; 
		String pass = null; 
		
		

		try {

			PolicyAndBaseCoverageValues policyValues = new PolicyAndBaseCoverageValues();
			policyValues.setCFoutputInstruction("Illustration");
			policyValues.setCFprocessType("IllustrateLife");
			policyValues.setReportType("PDF");
			policyValues.setReportName("Ledger");
			policyValues.setResultsWithReport(Boolean.TRUE);

			esConnection = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);
			connection = SessionHelper.getSession(SessionHelper.STAGING).connection();
			engineConnection = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.ENGINE_POOL);

			long[] baseSegmentPks = Query.getCorrepondencePKs(connection, "PolPage");
			for (int i = 0; i < baseSegmentPks.length; i++) {
				try {
					Query.buildBaseCoverage(connection, baseSegmentPks[i], policyValues);
					System.out.println(policyValues.getContractNumber());
					policyValues.setSegmentStatus(CalcFocusUtils.getCFStatus(policyValues.getSegmentStatus()));
					System.out.println(policyValues.getSegmentStatus());
					Query.setRoleAddressValues(connection, baseSegmentPks[i], policyValues);
					policyValues.setRiderCoverageValues(Query.buildRiderCoverage(connection, baseSegmentPks[i]));
					PlanCode planCode = CalcFocusUtils.getCalcFocusPlanCode(engineConnection,
							policyValues.getRatedGenderCT(), policyValues.getUnderwritingClass(),
							 policyValues.getOptionCodeCT(), policyValues.getGroupPlan(), policyValues.getCompanyName());
					policyValues.setProductCode(planCode.getCalcFocusProductCode());

					CalcFocusCorrespondenceRequestBuilder builder;
					;
					builder = new CalcFocusCorrespondenceRequestBuilder(policyValues);
					Object response = CalculateRequestService.calcFocusRequest(esConnection, builder.getReq());
					if (response instanceof CalculateResponse) {
						EditServiceLocator.getSingleton().getBatchAgent()
                          .getBatchStat(Batch.BATCH_JOB_POLICY_PAGES).updateSuccess(); 
						System.out.println("Success");
					} else {
						EditServiceLocator.getSingleton().getBatchAgent()
                          .getBatchStat(Batch.BATCH_JOB_POLICY_PAGES).updateFailure(); 
						System.out.println("Failure");
					}
				} catch (BadDataException bda) {
					Policy policy = bda.getPolicy();
					CalcFocusLoggingService.writeLogEntry(esConnection, policy.getPolicyNumber(), policy.getCompanyCode(),
							Long.valueOf(Long.parseLong(policy.getPolicyGUID())),
							policy.getPolicyAdminStatus().toString(), bda.getMessage(), null, "calcFocus", 0);
				}
			}
						EditServiceLocator.getSingleton().getBatchAgent()
                          .getBatchStat(Batch.BATCH_JOB_POLICY_PAGES).tagBatchStop(); 

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (esConnection != null && !esConnection.isClosed()) {
					esConnection.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

        
    }
    

 
}
