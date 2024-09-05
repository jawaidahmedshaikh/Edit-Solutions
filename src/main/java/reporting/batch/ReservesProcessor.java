/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Mar 26, 2002
 * Time: 2:46:31 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package reporting.batch;

import edit.common.vo.ReservesBatchVO;
import edit.common.vo.SegmentVO;
import engine.business.Calculator;
import engine.component.CalculatorComponent;
import fission.global.AppReqBlock;
import reporting.business.Reporting;

public class ReservesProcessor implements Runnable {

    private Reporting reporting = null;

	private ReservesBatchVO reservesBatchVOOut;

	public boolean createReservesExtracts(AppReqBlock appReqBlock,
                                           SegmentVO[] segmentVOs,
                                            String effectiveDate)
                                         throws Exception {

        reservesBatchVOOut = new ReservesBatchVO();

        for (int i = 0; i < segmentVOs.length; i++) {

            reservesBatchVOOut.addSegmentVO(segmentVOs[i]);
        }

        reservesBatchVOOut.setEffectiveDate(effectiveDate);

		// Spawn the reserves Process in its own thread so that the user
		// can continue operating the client.
//		new Thread(this).start();
//
        try {

            long startTime = System.currentTimeMillis();

            System.out.println("Reserves Start Time = " + startTime);

            Calculator calcComp = (Calculator) appReqBlock.getWebService("engine-service");

//            calcComp.processReserves(reservesBatchVOOut);
        }

        catch(Exception e) {

            System.out.println(e);
        }

        long stopTime = System.currentTimeMillis();

        System.out.println("Reserves Stop Time = " + stopTime);

		return true;
	}

	public void run() {

		try  {

            long startTime = System.currentTimeMillis();

            engine.business.Calculator calcComp = (engine.component.CalculatorComponent)
                                                    new CalculatorComponent();

//            calcComp.processReserves(reservesBatchVOOut);

//			Map allExtracts = (Map) calcComp.processReserves(Util.marshalVO(batchVOOut));
//
//            Map statHash = (Map) allExtracts.get("STAT");
//            Map taxHash  = (Map) allExtracts.get("TAX");
//            Map gaapHash = (Map) allExtracts.get("GAAP");
//
//            ReservesVO[] reservesVO = new ReservesVO[statHash.size()];
//
//            Iterator keys = statHash.keySet().iterator();
//
//            int i = 0;
//
//            while (keys.hasNext()) {
//
//                String contractId = (String) keys.next();
//                reservesVO[i] = (ReservesVO) statHash.get(contractId);
//                reservesVO[i].setTaxReserveAmount(((ReservesVO) taxHash.get(contractId)).getTaxReserveAmount());
//                reservesVO[i].setGAAPReserveAmount(((ReservesVO) gaapHash.get(contractId)).getGAAPReserveAmount());
//
//                i++;
//            }
//
//            long stopTime = System.currentTimeMillis();
//
//            System.out.println("Total Time = " + (stopTime - startTime)/1000.0 + " seconds");
//
//			System.out.println("RESERVES COMPLETED");
		}

		catch(Exception e) {

			System.out.println(e);
		}
	}
}
