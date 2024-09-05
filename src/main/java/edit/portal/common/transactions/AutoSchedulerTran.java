package edit.portal.common.transactions;

import fission.global.AppReqBlock;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 5, 2003
 * Time: 12:10:06 PM
 * To change this template use Options | File Templates.
 */
public class AutoSchedulerTran extends Transaction {

    private static final String PROCESS_BATCH = "processBatch";
    private static final String RUN_ACCOUNTING = "runAccounting";
    private static final String CREATE_BANK_EXTRACTS = "createBankExtracts";

    private static final String AUTO_SCHEDULER_RESPONSE = "/common/jsp/autoSchedulerResponse.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception {

        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(PROCESS_BATCH)){

            returnPage = processBatch(appReqBlock);
        }
        else if (action.equals(RUN_ACCOUNTING)){

            returnPage = runAccounting(appReqBlock);
        }
        else if (action.equals(CREATE_BANK_EXTRACTS)){

            returnPage = createBankExtracts(appReqBlock);
        }

        return returnPage;
    }

    protected String processBatch(AppReqBlock appReqBlock) throws Exception {

        appReqBlock.getHttpServletRequest().setAttribute("runAsBatch", "true");

//        String batchMessage = new DailyDetailTran().processBatch(appReqBlock);
//
//        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", batchMessage);

        return AUTO_SCHEDULER_RESPONSE;
    }

    protected String runAccounting(AppReqBlock appReqBlock) throws Exception {

        appReqBlock.getHttpServletRequest().setAttribute("runAsBatch", "true");

//        String batchMessage = new DailyDetailTran().runAccounting(appReqBlock);
//
//        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", batchMessage);

        return AUTO_SCHEDULER_RESPONSE;
    }

    protected String createBankExtracts(AppReqBlock appReqBlock) throws Exception {

        appReqBlock.getHttpServletRequest().setAttribute("runAsBatch", "true");

//        String batchMessage = new DailyDetailTran().createBankExtracts(appReqBlock);
//
//        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", batchMessage);

        return AUTO_SCHEDULER_RESPONSE;
    }
}
