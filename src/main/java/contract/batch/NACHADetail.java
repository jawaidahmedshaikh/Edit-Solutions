/*
 * 
 * User: cgleason
 * Date: Jul 16, 2008
 * Time: 11:36:39 AM
 * 
 * (c) 2000 - 2008 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package contract.batch;

import client.*;
import contract.Segment;

import java.util.*;

import edit.common.*;
import fission.utility.*;

public class NACHADetail {
	private static final String DETAIL_RECORD_TYPE = "6";
	public static final String CHECKIMG_BILL = "27";
	private static final String SAVINGS_BILL = "37";
	public static final String CHECKIMG_DISBURSEMENT = "22";
	private static final String SAVINGS_DISBURSMENT = "32";
	private static final String RECORD_IND = "0";

	// used these when preference settings were not complete...  They are now.
	//public static final String CHUBB_ROUTING_NUMBER = "221172186";
	//private static final String CHUBB_ACCOUNT = "6500364459";

	private EDITBigDecimal amount;
	private String stringAmount;
	private String processType;
	private ClientDetail clientDetail;
	private String routingNumber;
	private String checkDigit;
	private String clientName;
	private Segment segment;
	private String bankAccount;
	private String sequenceNumber;
	private String transCode;
	private String paddingSpaces = " ";
	private String message = "";

	private StringBuffer fileData = new StringBuffer();

	public NACHADetail() {

	}

	/**
	 * Bill or Suspense data will be input for the Detail record - each account to
	 * be impacted
	 * 
	 * @param sequenceNumber
	 * @param amount
	 * @param clientDetail
	 * @param processType
	 */
	public NACHADetail(Segment segment, String sequenceNumber, EDITBigDecimal amount, ClientDetail clientDetail, String processType) {
		this.segment = segment;
		this.sequenceNumber = sequenceNumber;
		this.amount = amount;
		this.clientDetail = clientDetail;
		this.processType = processType;

	}

	public void createDetail() {
		try {
			init();
			buildDetail();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Used for the detail record of the ClientCompany. This record is created after
	 * the all the other batch detail and before the Batch Control record. It will
	 * have the total amount of the detail for the ClientCompany preference.
	 */
	public void createClientCompanyDetail() {
		try {
			setAmount();

			clientName = clientDetail.getCorporateName();
			if (clientName.length() < 22) {
				clientName = Util.addOnTrailingSpaces(clientName, 23);
			} else if (clientName.length() > 22) {
				clientName = clientName.substring(0, 22);
			}

			try {
				this.checkDigit = clientDetail.getPreference().getBankRoutingNumber().substring(8, 9);
				this.routingNumber = clientDetail.getPreference().getBankRoutingNumber().substring(0, 8);
				this.bankAccount = clientDetail.getPreference().getBankAccountNumber();
				buildDetail(true);
			} catch (Exception ex) {
				System.out.println("createClientDetail: " + clientDetail.getClientDetailPK());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void init() {
		setAmount();
		setPreferenceFields();

		clientName = clientDetail.getLastName();
		if (clientName.length() < 22) {
			clientName = clientName + ", " + clientDetail.getFirstName();
			if (clientName.length() < 22) {
				clientName = clientName + " " + Util.initString(clientDetail.getMiddleName(), "");
			}
		}

		if (clientName.length() < 22) {
			clientName = Util.addOnTrailingSpaces(clientName, 23);
		} else if (clientName.length() > 22) {
			clientName = clientName.substring(0, 22);
		}

	}

	/**
	 * The ClientCompany preference, payor or owner preference records will be input
	 * to this
	 * 
	 * @param preference
	 */
	private void setPreferenceFields() {
		try {
		Preference preference = Preference
				.findByClientDetailPK_EFTPrimary_PreferenceType(clientDetail.getClientDetailPK(), "billing");
		if (preference != null) {
			String bankAccountType = preference.getBankAccountTypeCT();
			if (bankAccountType == null) {
				message = message + "Missing Preference.BankAccountTypeCT()";
			} 
			if (processType == null) {
				message = message + " ProcessType is null";
			}
			if (processType.equalsIgnoreCase(BankProcessorForNACHA.PROCESS_TYPE_BILL)) {
				if (bankAccountType.equalsIgnoreCase(Preference.BANK_ACCOUNT_TYPE_CHECKING)) {
					transCode = CHECKIMG_BILL; // 27
				} else {
					transCode = SAVINGS_BILL; // 37
				}
			} else {
				// for disbursements
				if (bankAccountType.equalsIgnoreCase(Preference.BANK_ACCOUNT_TYPE_CHECKING)) {
					transCode = CHECKIMG_DISBURSEMENT; // 22
				} else {
					transCode = SAVINGS_DISBURSMENT; // 32
				}
			}

			if ((preference.getBankRoutingNumber() == null) || (preference.getBankRoutingNumber().length() < 9)) {
				message = message + " Invalid Peference.BankRoutingNumber";
			}
			routingNumber = preference.getBankRoutingNumber().substring(0, 8);

			if (stringAmount == null) {
				stringAmount = "00000000";
			}
			if (stringAmount.length() < 8) {
				stringAmount = Util.padWithZero(stringAmount, 8);
			}

			checkDigit = preference.getBankRoutingNumber().substring(8, 9);

			bankAccount = preference.getBankAccountNumber();
			if (bankAccount == null) {
				message = message + " Missing Preference.BankAccountNumber";
			}

			if (bankAccount.length() < 17) {
				bankAccount = Util.addOnTrailingSpaces(bankAccount, 18);
			}
		} 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void setAmount() {
		stringAmount = amount.toString();
		stringAmount = Util.removeNonNumericChars(stringAmount);
		if (stringAmount.length() < 10) {
			stringAmount = Util.padWithZero(stringAmount, 10);
		}
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public EDITBigDecimal getAmount() {
		return amount;
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public String getRoutingNumber() {
		return routingNumber;
	}

	/**
	 * Detail record - Fixed length of 94
	 * 
	 * @throws Exception
	 */
	private void buildDetail() throws Exception {
		buildDetail(false);
	}

	private void buildDetail(boolean isOffsetRecord) throws Exception {
			// New bank does not use the offset record. It's automatic, so we'll removed it.
			// 2022-09-15 DE
		if (!isOffsetRecord) {
			fileData.append(DETAIL_RECORD_TYPE); // length = 1
			if (transCode == null) {
				message = message + "Missing transCode";
			}
			fileData.append(transCode); // length = 2
			fileData.append(routingNumber); // length = 8
			fileData.append(checkDigit); // length = 1
			fileData.append(bankAccount); // length = 17
//		} else {
//			fileData.append("22"); // length = 2
//			fileData.append(routingNumber.substring(0, 8)); // length = 8
//			fileData.append(checkDigit); // length = 1
//			bankAccount = Util.addOnTrailingSpaces(CHUBB_ACCOUNT, 18);
//			fileData.append(bankAccount); // length = 17
			fileData.append(stringAmount); // length = 10

			if (segment.getContractNumber() != null) {
			    paddingSpaces = Util.addOnTrailingSpaces(segment.getContractNumber(), 16);
			} else {
			    paddingSpaces = Util.addOnTrailingSpaces(paddingSpaces, 16); // length = 15
			}
			fileData.append(paddingSpaces);
			fileData.append(clientName); // length = 22

			paddingSpaces = "  "; // length = 2
			fileData.append(paddingSpaces);

			fileData.append(RECORD_IND); // length = 1
			//if (!isOffsetRecord) {
			fileData.append(routingNumber + sequenceNumber); // length = 15
			//} else {
				// New bank does no use the offset record. It's automatic, so we'll removed it.
				// 2022-09-15 DE
				// fileData.append(CHUBB_ROUTING_NUMBER.substring(0, 8) + sequenceNumber); //
				// length = 15
			//}
		}
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public StringBuffer getFileData() {
		return fileData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
