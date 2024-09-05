package com.selman.calcfocus.service;

import javax.xml.parsers.DocumentBuilder;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import javax.xml.bind.Unmarshaller;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.Marshaller;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.ws.rs.client.Client;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import com.selman.calcfocus.request.Policy;
import com.selman.calcfocus.response.Error;
import com.selman.calcfocus.response.MonthEndValues;

import javax.xml.bind.JAXBIntrospector;
import org.w3c.dom.Node;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import com.selman.calcfocus.response.AsyncExtract;
import com.selman.calcfocus.response.AsyncExtractType;
import com.selman.calcfocus.response.CalculateResponse;
import com.selman.calcfocus.util.BadDataException;
import com.selman.calcfocus.util.CalcFocusUtils;

import edit.common.exceptions.EDITCaseException;

import javax.ws.rs.client.Entity;
import java.io.Writer;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import com.editsolutions.web.utility.CommonConstants;
import com.selman.calcfocus.request.CalculateRequest;
import java.sql.Connection;

public class CalculateRequestService {

	public static Object calcFocusRequest(final Connection connection, final CalculateRequest request) {
		return calcFocusRequest(connection, request, "off");
	}

	public static Object calcFocusRequest(final Connection connection, final CalculateRequest request,
			final String isYev) {
		Boolean debug = false;
		Object objectResponse = null;
		String responseString = new String();
		System.setProperty("https.protocols", "TLSv1.2");

		try {
			System.out.println("Processing: " + request.getPolicy().get(0).getPolicyNumber());
//			final String restService = "https://service.selman.calcfocus.net/restService/calculate";
//            final String restService = "https://dev.selman.calcfocus.net/service/restService/calculate";
//            final String restService = "https://test.selman.calcfocus.net/service/restService/calculate";
			final String restService = System.getProperty(CommonConstants.CalcFocusService).trim();

			final Client client = ClientBuilder.newClient((Configuration) new ClientConfig());

			final JAXBContext jaxbContextRequest = JAXBContext.newInstance(new Class[] { CalculateRequest.class });
			final Marshaller marshaller = jaxbContextRequest.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", (Object) Boolean.TRUE);
			final StringWriter stringWriter = new StringWriter();
			marshaller.marshal((Object) request, (Writer) stringWriter);
			final String requestString = stringWriter.toString();
			System.out.println(requestString);

			final WebTarget webTarget = client.target(restService);
			final Response response = webTarget.request(new String[] { "application/xml" })
					.post(Entity.entity((Object) request, "application/xml"));
			responseString = (String) response.readEntity((Class) String.class);
			System.out.println(responseString);
			if (!debug) {
				JAXBContext responseContext;
				if ((response.getStatus() != 200) && (response.getStatus() != 500)) {
					System.out.println("Status: " + response.getStatus());
					responseContext = JAXBContext.newInstance(new Class[] { Error.class });
				} else {
					responseContext = JAXBContext.newInstance(new Class[] { CalculateResponse.class });
				}
				final Unmarshaller unmarshaller = responseContext.createUnmarshaller();
				unmarshaller.setEventHandler((ValidationEventHandler) new DefaultValidationEventHandler());
				final Document document = convertStringToDocument(responseString);
				String errorMessage = null;
				int success = 0;
				if (response.getStatus() == 400) {
					objectResponse = JAXBIntrospector.getValue(unmarshaller.unmarshal((Node) document));
					final Error error = (Error) objectResponse;
					errorMessage = error.getMessage();
				} else {
					objectResponse = JAXBIntrospector.getValue(unmarshaller.unmarshal((Node) document));
					final CalculateResponse cfResponse = (CalculateResponse) objectResponse;
					if (request.getContext().getProcessType().equals("MonthEndValuation")
							&& !request.getHeader().isAsync()) {
						List<AsyncExtract> asyncExtracts = cfResponse.getPolicySummary().get(0).getAsyncExtract();
						// String contractNumber =
						// cfResponse.getPolicySummary().get(0).getPolicyNumber();
						Date extractDate = cfResponse.getPolicySummary().get(0).getEffectiveDate().toGregorianCalendar()
								.getTime();
						if (isYev.equals("on")) {
							CalcFocusUtils.saveYearEndValues(connection, cfResponse.getPolicySummary().get(0));
						} 
						/* else {
							// String batchId = cfResponse.getPolicySummary().get(0).getGUID();
							String extractRecord;
							for (AsyncExtract ae : asyncExtracts) {
								if (ae.getType().equals(AsyncExtractType.POL_60)) {
									extractRecord = ae.getExtractRecord().get(0);
									System.out.println("ExtractRecord: " + extractRecord);
									String[] fields = extractRecord.split("\\|");
									CalcFocusLoggingService.writeMevValues(connection,
											request.getHeader().getBatchRefGUID(),
											new java.sql.Date(extractDate.getTime()), "POL_60", fields[0], fields[1],
											Double.parseDouble(fields[2]), // beginningReserve
											Double.parseDouble(fields[3]), // netPremiums
											Double.parseDouble(fields[4]), // eopInterestAjustment
											Double.parseDouble(fields[5]), // expenseCharge
											Double.parseDouble(fields[6]), // costOfInsurance
											Double.parseDouble(fields[7]), // nonDeath
											Double.parseDouble(fields[8]), // death
											Double.parseDouble(fields[9])); // endAV
								}
							}
						} */
					}
					success = 1;
				}
				if (errorMessage != null) {
					responseString = errorMessage;
				}

				// check for errors
				List<String> badDataMessages = new ArrayList<>();
				if (request.getPolicy().get(0).getPolicyNumber() == null) {
					badDataMessages.add("PolicyNumber is missing.");
				}
				if (request.getPolicy().get(0).getCompanyCode() == null) {
					badDataMessages.add("CompanyCode is missing");
				}
				if (request.getPolicy().get(0).getPolicyGUID() == null) {
					badDataMessages.add("PolicyGUID is missing");
				}
				if (request.getPolicy().get(0).getPolicyAdminStatus() == null) {
					badDataMessages.add("PolicyAdminStatus is missing");
				}
				if (badDataMessages.size() > 0) {
					throw new BadDataException(badDataMessages);
				}

				CalcFocusLoggingService.writeLogEntry(connection, request.getPolicy().get(0).getPolicyNumber(),
						request.getPolicy().get(0).getCompanyCode(),
						Long.valueOf(Long.parseLong(request.getPolicy().get(0).getPolicyGUID())),
						request.getPolicy().get(0).getPolicyAdminStatus().toString(), requestString, responseString,
						"calcFocus", success);
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		return objectResponse;
	}

	private static Document convertStringToDocument(final String xmlStr) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}