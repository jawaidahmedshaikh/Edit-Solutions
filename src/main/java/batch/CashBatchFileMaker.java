package batch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.EDITExport;
import edit.services.config.ServicesConfig;
import event.Suspense;
import fission.utility.Util;
import group.ContractGroup;

public class CashBatchFileMaker {

	StringBuffer contents = new StringBuffer();
	public File eftNACHAFile;
	private BufferedWriter bufferedWriter;
	private int recordCount = 0;
	private EDITBigDecimal totalAmount = new EDITBigDecimal();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	
	public CashBatchFileMaker(EDITDate draftDateED) throws Exception {
		this.setEFTNACHAFile(draftDateED);
	}
	
	/*
	 * 	
	 *  final int BATCH_REC_TYPE_BEGIN = 0;
		final int BATCH_REC_TYPE_LENGTH = 1;
		final int USERDEF_NUMBER_BEGIN = 1;
		final int USERDEF_NUMBER_LENGTH = 10;
		final int BATCH_ID_BEGIN = 11;
		final int BATCH_ID_LENGTH = 3;
		final int EFFECTIVE_DATE_BEGIN = 14;
		final int EFFECTIVE_DATE_LENGTH = 8;
		final int SUSPENSE_AMOUNT_BEGIN = 22;
		final int SUSPENSE_AMOUNT_LENGTH = 9;
		final int DUE_DATE_BEGIN = 32;
		final int DUE_DATE_LENGTH = 8;
		final int GROUP_NUMBER_BEGIN = 40;
		final int GROUP_NUMBER_LENGTH = 10;
		final int DEPTLOC_BEGIN = 50;
		final int DEPTLOC_LENGTH = 2;
D VC0000846 6 122 09242021 000053670+ 09242021 DIRVC      99
	 */


	public void createPayments(String batchRecordType, String groupNumber, String contractNumber, EDITDate draftDateED, 
			EDITBigDecimal suspenseAmount, String operator, String batchID) {
		    contents.append(batchRecordType); //1
		    contents.append(contractNumber); //10
		    contents.append(Util.addOnTrailingSpaces(batchID, 3)); //3
		    contents.append(draftDateED.getMMDDYYYYDateNoDelimiter()); //8
		    contents.append(Util.padWithZero(suspenseAmount.toString().replace(".", ""), 9));//9
		    contents.append("+");
		    contents.append(draftDateED.getMMDDYYYYDateNoDelimiter()); //8
//		    contents.append(Util.addOnTrailingSpaces(groupNumber + (contractNumber.substring(0,2)), 11)); //10
		    contents.append(Util.addOnTrailingSpaces(groupNumber, 11)); //10
		    contents.append("99"); //2
		    contents.append(Util.getLineSeparator());
		    recordCount++;
		    totalAmount = totalAmount.addEditBigDecimal(suspenseAmount);
	}
		

	public void createTrailerFinishFile(EDITDate draftDateED) {
	    contents.append("T"); //1
	    contents.append("          "); //8
	    contents.append("   "); //3
	    contents.append(draftDateED.getMMDDYYYYDateNoDelimiter()); //8
	    contents.append(Util.padWithZero(totalAmount.toString().replace(".", ""), 9));//9
	    contents.append("+");
	    contents.append(draftDateED.getMMDDYYYYDateNoDelimiter()); //8
	    contents.append("          "); //10
	    contents.append("  "); //2
	    //contents.append(Util.getLineSeparator());
		writeToBufferedWriter(contents.toString());
	}
	/**
	 * Writing to the file through the buffered writer
	 * 
	 * @param data
	 */
	private void writeToBufferedWriter(String data) {
		try {
			bufferedWriter.write(data);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} 
	}
	
	private void setEFTNACHAFile(EDITDate draftDateED) throws Exception {
		EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

		String draftDate = draftDateED.getMMDDYYYYDateNoDelimiter();
		eftNACHAFile = new File(export1.getDirectory() + "EFTCashbatch" + draftDate + ".txt");

		bufferedWriter = new BufferedWriter(new FileWriter(eftNACHAFile));

	}
}
