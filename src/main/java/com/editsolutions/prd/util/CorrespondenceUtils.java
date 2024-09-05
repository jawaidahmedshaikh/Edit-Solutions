package com.editsolutions.prd.util;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.editsolutions.prd.service.NotificationService;
import com.editsolutions.prd.service.NotificationServiceImpl;
import com.editsolutions.prd.service.SetupContactService;
import com.editsolutions.prd.service.SetupContactServiceImpl;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.Message;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.MessageTemplate;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SetupContact;


public class CorrespondenceUtils {
	
	public static void createTestCorrespondence(PRDSettings prdSettings, DataHeader dataHeader, String fileName) {
		String prdDirectory = System.getProperty("PRDDirectory");

		byte[] attachment;
		if (prdSettings.getFileTemplate().getOutputTypeCT().equals("TXT")) {
		    attachment = OutputUtils.createTXTExtract(prdSettings, dataHeader, prdDirectory + "\\" + fileName);
		} else if (prdSettings.getFileTemplate().getOutputTypeCT().equals("CSV")) {
		    attachment = OutputUtils.createCSVExtract(prdSettings, dataHeader, prdDirectory + "\\" + fileName);
		} else {
		    attachment = OutputUtils.createXLSExtract(prdSettings, dataHeader, prdDirectory + "\\" + fileName);
		}
		
		OutputUtils.createTXTExtract(prdSettings, dataHeader, fileName);
		
	}
	

	
	public static void createCorrespondence(PRDSettings prdSettings, DataHeader dataHeader, String statusCT) {
		String prdDirectory = System.getProperty("PRDDirectory");
		MessageTemplate subjectTemplate;
		MessageTemplate messageTemplate;

		if ((prdSettings.getLastPRDExtractDate() == null) || 
				(prdSettings.getFirstDeductionDate().equals(prdSettings.getLastPRDExtractDate()))) {
		    subjectTemplate = prdSettings.getInitialSubjectTemplate();
		    messageTemplate = prdSettings.getInitialMessageTemplate();
		} else {
		    subjectTemplate = prdSettings.getSubjectTemplate();
		    messageTemplate = prdSettings.getMessageTemplate();
		}
		Message subjectMessage = new Message();
		subjectMessage.setPreMessage(subjectTemplate.getMessageText());
		subjectMessage = MessageBuilderFactory.buildMessage(subjectMessage, prdSettings.getGroupSetup().getGroupNumber());
		Message bodyMessage = new Message();
		bodyMessage.setPreMessage(messageTemplate.getMessageText());
		bodyMessage = MessageBuilderFactory.buildMessage(bodyMessage, prdSettings.getGroupSetup().getGroupNumber());
		Notification notification = new Notification();
		notification.setDataHeaderFK(dataHeader.getDataHeaderPK());
		if (statusCT.equals("C")) {
		     notification.setMessageText("This PRD was not delivered by the system.  Delivery was handled by the operator.") ;
		     notification.setDateSent(new java.sql.Timestamp(System.currentTimeMillis()));
		} else {
		     notification.setMessageText(bodyMessage.getPostMessage());
		}
		notification.setMessageSubject(subjectMessage.getPostMessage());
		//if (prdSettings.getSystemCT().equals("ES")) {
		    notification.setDueDate(new java.sql.Date((DateUtils.addDays(prdSettings.getLastPRDExtractDate(), 1)).getTime()));
		//}  else {
		 //   notification.setDueDate(new java.sql.Date(prdSettings.getNextPRDDueDate().getTime()));
		//}

		// Get a fresh set of contacts in case a new one was added through UI

		notification.setEmailTo(null);
		notification.setEmailCC(null);
		notification.setEmailBCC(null);

		SetupContactService scs = new SetupContactServiceImpl();
		List<SetupContact> setupContacts = scs.getSetupContacts(prdSettings); 
		Iterator<SetupContact> it = setupContacts.iterator();
		while (it.hasNext()) {
			SetupContact setupContact = it.next();
			switch (setupContact.getRecipientTypeCT().toUpperCase()) {
			case "TO":
				if (notification.getEmailTo() != null) {
					notification.setEmailTo(notification.getEmailTo() + "; " + setupContact.getEmail());
				} else {
			        notification.setEmailTo(setupContact.getEmail());	
				}
				break;
			case "CC":
				if (notification.getEmailCC() != null) {
					notification.setEmailCC(notification.getEmailCC() + "; " + setupContact.getEmail());
				} else {
			        notification.setEmailCC(setupContact.getEmail());	
				}
				break;
			case "BCC":
				if (notification.getEmailBCC() != null) {
					notification.setEmailBCC(notification.getEmailBCC() + "; " + setupContact.getEmail());
				} else {
			       notification.setEmailBCC(setupContact.getEmail());	
				}
				break;
			}
		}
		
		if ((prdSettings.getFtpAddress() != null) && (prdSettings.getFtpAddress().length() > 3)) {
			notification.setFtpAddress(prdSettings.getFtpAddress());
			notification.setFtpUsername(prdSettings.getFtpUserName());
			notification.setFtpPassword(prdSettings.getFtpPassword());
		}
		if ((prdSettings.getNetworkLocation() != null) && (prdSettings.getNetworkLocation().length() > 3)) {
			notification.setExportDirectory(prdSettings.getNetworkLocation() + "\\" + prdSettings.getNetworkLocationDirectory());
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder fileNameBuilder = new StringBuilder();

		//fileNameBuilder.append(prdDirectory + "\\");
		fileNameBuilder.append(prdSettings.getGroupSetup().getGroupNumber().trim());
		fileNameBuilder.append("-");
		String groupName = prdSettings.getGroupSetup().getGroupName()
				.replaceAll("\\/", "").trim();
		groupName = groupName.replaceAll("[^a-zA-Z0-9]+","");
		groupName = groupName.replaceAll(" ", "_");
		
		fileNameBuilder.append(groupName);
		fileNameBuilder.append("-");
		if (prdSettings.getTypeCT().equals("All")) {
		    fileNameBuilder.append("ALL-");
		}
	    
	    // non ES prds not on hold have not date advanced yet so need to use nextPRDExtractDate
		if (!prdSettings.isHoldForReview() && !prdSettings.getSystemCT().equals("ES")) {
			fileNameBuilder.append(formatter.format(DateUtils.addDays(prdSettings.getNextPRDExtractDate(), 1)).trim());
		} else { 
            fileNameBuilder.append(formatter.format(DateUtils.addDays(prdSettings.getLastPRDExtractDate(), 1)).trim());
		}

		byte[] attachment;
		if (prdSettings.getFileTemplate().getOutputTypeCT().equals("TXT")) {
		    fileNameBuilder.append(".txt");
		    notification.setFileAttachmentName(fileNameBuilder.toString().trim());
		    attachment = OutputUtils.createTXTExtract(prdSettings, dataHeader, prdDirectory + "\\" + notification.getFileAttachmentName());
		} else if (prdSettings.getFileTemplate().getOutputTypeCT().equals("CSV")) {
		    fileNameBuilder.append(".csv");
		    notification.setFileAttachmentName(fileNameBuilder.toString().trim());
		    attachment = OutputUtils.createCSVExtract(prdSettings, dataHeader, prdDirectory + "\\" + notification.getFileAttachmentName());
		} else {
		    fileNameBuilder.append(".xlsx");
		    notification.setFileAttachmentName(fileNameBuilder.toString().trim());
		    attachment = OutputUtils.createXLSExtract(prdSettings, dataHeader, prdDirectory + "\\" + notification.getFileAttachmentName());
		}
		
		if ((attachment != null) && (attachment.length > 0)) {
			notification.setHasAttachment(true);
		} else {
			notification.setHasAttachment(false);
		}

		notification.setFileAttachment(attachment);
		notification.setPrdSetupFK(prdSettings.getPrdSetupPK());
		notification.setStatusCT(statusCT);
		NotificationService notificationService = new NotificationServiceImpl(); 
		notificationService.saveNotification(notification);
			
		
	}
	

}
