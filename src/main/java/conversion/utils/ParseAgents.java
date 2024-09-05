package conversion.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ParseAgents extends DefaultHandler {
	List<Record> records;
	Record record;
	StringBuffer tmpValue;
	String filename;
	StringBuffer prevField;
	HashMap<String, String> hm;

	public ParseAgents(String filename) {
		this.filename = filename;
		records = new ArrayList<>();
		hm = new HashMap<>();
		processXML();
		printRecords();
	}

	public static void main(String[] args) {
		String filename = null;

		for (int i = 0; i < args.length; i++) {
			filename = args[i];
			if (i != args.length - 1) {
				usage();
			}
		}

		if (filename == null) {
			usage();
		}

		new ParseAgents(filename);

	}

	private void processXML() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			// System.out.println("BillGroupID,PremiumShare,SituationCode,AgentNumber,ContractCode,AgentLevel,Unused");
			SAXParser saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(this);
			xmlReader.parse(convertToFileURL(filename));
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startDocument() throws SAXException {
		record = new Record();
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		tmpValue = new StringBuffer();
		if (qName.equalsIgnoreCase("RecordVO")) {
			record = new Record();
			isNull = true;
		}
	}

	private void printRecords() {
		Iterator<Record> it = records.iterator();
		while (it.hasNext()) {
			Record r = it.next();
			System.out.println(r.toString());
		}
	}

	boolean isNull = true;;
	boolean capture = false;
	String billGroupId;

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equalsIgnoreCase("RecordVO")) {
			if ((record != null) && !record.isNull()) {

				if (!hm.containsKey(record.toString())) {
					records.add(record);
				}
				hm.put(record.toString(), "");

			}
		}

		if (tmpValue.toString().equals("08WS087533")) {
			//System.out.print("");
		}

		if (qName.equalsIgnoreCase("Name")) {
			prevField = new StringBuffer(tmpValue.toString());
		}

		if (prevField.toString().equalsIgnoreCase("BillGroupId")) {
			billGroupId = tmpValue.toString();
			isNull = false;
		}

		if (qName.equalsIgnoreCase("Value")) {
			if (prevField.toString().equalsIgnoreCase("Type")) {
				if (tmpValue.toString().equals("48")) {
					capture = true;
				} else {
					capture = false;
				}
			}
			if (capture) {

				if (prevField.toString().equalsIgnoreCase("PremiumShare")) {
					record.setBillGroupID(billGroupId);
					record.setPremiumShare(tmpValue.toString());
					isNull = false;
				}

				if (prevField.toString().equalsIgnoreCase("SituationCode")) {
					record.setSituationCode(tmpValue.toString());
					isNull = false;
				}

				if (prevField.toString().equalsIgnoreCase("AgentNumber")) {
					record.setAgentNumber(tmpValue.toString());
					isNull = false;
				}

				if (prevField.toString().equalsIgnoreCase("ContractCode")) {
					record.setContractCode(tmpValue.toString());
					isNull = false;
				}

				if (prevField.toString().equalsIgnoreCase("AgentLevel")) {
					record.setAgentLevel(tmpValue.toString());
					isNull = false;
				}

				if (prevField.toString().equalsIgnoreCase("Unused")) {
					record.setUnused(tmpValue.toString());
					isNull = false;
				}
				if (prevField.toString().equalsIgnoreCase("ContractNumber")) {
					if (tmpValue.toString().length() < 5) {
						//System.out.print("");
					}
					record.setContractNumber(tmpValue.toString());
					isNull = false;
				}
			}
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tmpValue.append(new String(ch, start, length));
		//System.out.println(tmpValue.toString() + ":" + start + ":" + length);
	}

	private static String convertToFileURL(String filename) {
		String path = new File(filename).getAbsolutePath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "file:" + path;
	}

	private static void usage() {
		System.err.println("Usage: ParseAgent <file.xml>");
		System.err.println("       -usage or -help = this message");
		System.exit(1);
	}

}

class MyErrorHandler implements ErrorHandler {
	private PrintStream out;

	MyErrorHandler(PrintStream out) {
		this.out = out;
	}

	private String getParseExceptionInfo(SAXParseException spe) {
		String systemId = spe.getSystemId();

		if (systemId == null) {
			systemId = "null";
		}

		String info = "URI=" + systemId + " Line=" + spe.getLineNumber() + ": "
				+ spe.getMessage();

		return info;
	}

	public void warning(SAXParseException spe) throws SAXException {
		out.println("Warning: " + getParseExceptionInfo(spe));
	}

	public void error(SAXParseException spe) throws SAXException {
		String message = "Error: " + getParseExceptionInfo(spe);
		throw new SAXException(message);
	}

	public void fatalError(SAXParseException spe) throws SAXException {
		String message = "Fatal Error: " + getParseExceptionInfo(spe);
		throw new SAXException(message);
	}
}

class Record {
	String billGroupID;
	String premiumShare;
	String situationCode;
	String agentNumber;
	String contractCode;
	String agentLevel;
	String unused;
	String contractNumber;

	public Record() {
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getBillGroupID() {
		return billGroupID;
	}

	public void setBillGroupID(String billGroupID) {
		this.billGroupID = billGroupID;
	}

	public String getPremiumShare() {
		return premiumShare;
	}

	public void setPremiumShare(String premiumShare) {
		this.premiumShare = premiumShare;
	}

	public String getSituationCode() {
		return situationCode;
	}

	public void setSituationCode(String situationCode) {
		this.situationCode = situationCode;
	}

	public String getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	public String getUnused() {
		return unused;
	}

	public void setUnused(String unused) {
		this.unused = unused;
	}

	public String n(String s) {
		if (s == null) {
			s = "";
		}
		return s;
	}

	@Override
	public String toString() {
		/*
		return n(contractNumber) + "," + n(billGroupID) + "," + n(premiumShare)
				+ "," + n(situationCode) + "," + n(agentNumber) + ","
				+ n(contractCode) + "," + n(agentLevel) + "," + n(unused);
				*/
		return  n(billGroupID) + "," + n(premiumShare)
				+ "," + n(situationCode) + "," + n(agentNumber) + ","
				+ n(contractCode) + "," + n(agentLevel) + "," + n(unused);
	}

	public boolean isNull() {
		/*
		if ((billGroupID == null) && (premiumShare == null)
				&& (situationCode == null) && (agentNumber == null)
				&& (contractCode == null) && (agentLevel == null)
				&& (contractNumber == null) && (unused == null)) {
			return true;
		}
		*/
		if ((billGroupID == null) && (premiumShare == null)
				&& (situationCode == null) && (agentNumber == null)
				&& (agentLevel == null) && (unused == null)) {
			return true;
		}
		return false;
	}

}
