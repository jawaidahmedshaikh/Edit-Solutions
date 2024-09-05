/**
 * NewBusinessQuoteStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.1 Nov 13, 2006 (07:31:44 LKT)
 */
package test.webservice.quote.rpc.client;

/*
 *  NewBusinessQuoteStub java implementation
 */

public class NewBusinessQuoteStub extends org.apache.axis2.client.Stub {
	protected org.apache.axis2.description.AxisOperation[] _operations;

	// hashmaps to keep the fault mapping
	private java.util.HashMap faultExeptionNameMap = new java.util.HashMap();
	private java.util.HashMap faultExeptionClassNameMap = new java.util.HashMap();
	private java.util.HashMap faultMessageMap = new java.util.HashMap();

	private void populateAxisService() throws org.apache.axis2.AxisFault {

		// creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService(
				"NewBusinessQuote" + this.hashCode());

		// creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[1];

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("",
				"getNewBusinessQuote"));
		_service.addOperation(__operation);

		_operations[0] = __operation;

	}

	// populates the faults
	private void populateFaults() {

		faultExeptionNameMap
				.put(new javax.xml.namespace.QName(
						"http://rpc.service.webservice/xsd",
						"getNewBusinessQuoteFault"),
						"test.webservice.quote.rpc.client.GetNewBusinessQuoteFaultException");
		faultExeptionClassNameMap
				.put(new javax.xml.namespace.QName(
						"http://rpc.service.webservice/xsd",
						"getNewBusinessQuoteFault"),
						"test.webservice.quote.rpc.client.GetNewBusinessQuoteFaultException");
		faultMessageMap
				.put(new javax.xml.namespace.QName(
						"http://rpc.service.webservice/xsd",
						"getNewBusinessQuoteFault"),
						"test.webservice.quote.rpc.client.NewBusinessQuoteStub$GetNewBusinessQuoteFault");

	}

	/**
	 * Constructor that takes in a configContext
	 */
	public NewBusinessQuoteStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient = new org.apache.axis2.client.ServiceClient(
				configurationContext, _service);

		configurationContext = _serviceClient.getServiceContext()
				.getConfigurationContext();

		_serviceClient.getOptions().setTo(
				new org.apache.axis2.addressing.EndpointReference(
						targetEndpoint));

	}

	/**
	 * Default Constructor
	 */
	public NewBusinessQuoteStub() throws org.apache.axis2.AxisFault {

		this("http://syam:8988/PORTAL/services/NewBusinessQuote");

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public NewBusinessQuoteStub(java.lang.String targetEndpoint)
			throws org.apache.axis2.AxisFault {
		this(null, targetEndpoint);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see test.webservice.quote.rpc.client.NewBusinessQuote#getNewBusinessQuote
	 * @param param2
	 */
	public test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse getNewBusinessQuote(

			test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote param2)
			throws java.rmi.RemoteException

			,
			test.webservice.quote.rpc.client.GetNewBusinessQuoteFaultException {
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("urn:getNewBusinessQuote");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			// Style is Doc.

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), param2,
					optimizeContent(new javax.xml.namespace.QName("",
							"getNewBusinessQuote")));

			// adding SOAP headers
			_serviceClient.addHeadersToEnvelope(env);
			// create message context with that soap envelope
			org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse.class,
					getEnvelopeNamespaces(_returnEnv));
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
			return (test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse) object;

		} catch (org.apache.axis2.AxisFault f) {
			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExeptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExeptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						if (ex instanceof test.webservice.quote.rpc.client.GetNewBusinessQuoteFaultException) {
							throw (test.webservice.quote.rpc.client.GetNewBusinessQuoteFaultException) ex;
						}

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see test.webservice.quote.rpc.client.NewBusinessQuote#startgetNewBusinessQuote
	 * @param param2
	 */
	public void startgetNewBusinessQuote(

			test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote param2,

			final test.webservice.quote.rpc.client.NewBusinessQuoteCallbackHandler callback)

	throws java.rmi.RemoteException {

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("urn:getNewBusinessQuote");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions()
				.getSoapVersionURI()), param2,
				optimizeContent(new javax.xml.namespace.QName("",
						"getNewBusinessQuote")));

		// adding SOAP headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
		_messageContext.setEnvelope(env);

		// add the message contxt to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient
				.setCallback(new org.apache.axis2.client.async.Callback() {
					public void onComplete(
							org.apache.axis2.client.async.AsyncResult result) {
						java.lang.Object object = fromOM(
								result.getResponseEnvelope().getBody()
										.getFirstElement(),
								test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse.class,
								getEnvelopeNamespaces(result
										.getResponseEnvelope()));
						callback.receiveResultgetNewBusinessQuote((test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse) object);
					}

					public void onError(java.lang.Exception e) {
						callback.receiveErrorgetNewBusinessQuote(e);
					}
				});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[0].getMessageReceiver() == null
				&& _operationClient.getOptions().isUseSeparateListener()) {
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[0].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 */
	private java.util.Map getEnvelopeNamespaces(
			org.apache.axiom.soap.SOAPEnvelope env) {
		java.util.Map returnMap = new java.util.HashMap();
		java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		while (namespaceIterator.hasNext()) {
			org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator
					.next();
			returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		}
		return returnMap;
	}

	private javax.xml.namespace.QName[] opNameArray = null;

	private boolean optimizeContent(javax.xml.namespace.QName opName) {

		if (opNameArray == null) {
			return false;
		}
		for (int i = 0; i < opNameArray.length; i++) {
			if (opName.equals(opNameArray[i])) {
				return true;
			}
		}
		return false;
	}

	// http://seg-wkst20:8080/PORTAL/services/NewBusinessQuote
	public static class ExtensionMapper {

		public static java.lang.Object getTypeObject(
				java.lang.String namespaceURI, java.lang.String typeName,
				javax.xml.stream.XMLStreamReader reader)
				throws java.lang.Exception {

			if ("http://rpc.service.webservice/xsd".equals(namespaceURI)
					&& "NewBusinessQuoteInput".equals(typeName)) {

				return NewBusinessQuoteInput.Factory.parse(reader);

			}

			if ("http://rpc.service.webservice/xsd".equals(namespaceURI)
					&& "NewBusinessQuoteOutput".equals(typeName)) {

				return NewBusinessQuoteOutput.Factory.parse(reader);

			}

			throw new java.lang.RuntimeException("Unsupported type "
					+ namespaceURI + " " + typeName);
		}

	}

	public static class GetNewBusinessQuote implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://rpc.service.webservice/xsd", "getNewBusinessQuote",
				"ns1");

		/**
		 * field for NewBusinessQuoteInput
		 */

		protected NewBusinessQuoteInput localNewBusinessQuoteInput;

		/**
		 * Auto generated getter method
		 * 
		 * @return NewBusinessQuoteInput
		 */
		public NewBusinessQuoteInput getNewBusinessQuoteInput() {
			return localNewBusinessQuoteInput;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NewBusinessQuoteInput
		 */
		public void setNewBusinessQuoteInput(NewBusinessQuoteInput param) {

			this.localNewBusinessQuoteInput = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					java.lang.String prefix = parentQName.getPrefix();
					java.lang.String namespace = parentQName.getNamespaceURI();

					if (namespace != null) {
						java.lang.String writerPrefix = xmlWriter
								.getPrefix(namespace);
						if (writerPrefix != null) {
							xmlWriter.writeStartElement(namespace,
									parentQName.getLocalPart());
						} else {
							if (prefix == null) {
								prefix = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();
							}

							xmlWriter.writeStartElement(prefix,
									parentQName.getLocalPart(), namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);
						}
					} else {
						xmlWriter.writeStartElement(parentQName.getLocalPart());
					}

					if (localNewBusinessQuoteInput == null) {

						java.lang.String namespace2 = "http://rpc.service.webservice/xsd";

						if (!namespace2.equals("")) {
							java.lang.String prefix2 = xmlWriter
									.getPrefix(namespace2);

							if (prefix2 == null) {
								prefix2 = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();

								xmlWriter.writeStartElement(prefix2,
										"newBusinessQuoteInput", namespace2);
								xmlWriter.writeNamespace(prefix2, namespace2);
								xmlWriter.setPrefix(prefix2, namespace2);

							} else {
								xmlWriter.writeStartElement(namespace2,
										"newBusinessQuoteInput");
							}

						} else {
							xmlWriter
									.writeStartElement("newBusinessQuoteInput");
						}

						// write the nil attribute
						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);
						xmlWriter.writeEndElement();
					} else {
						localNewBusinessQuoteInput.getOMElement(
								new javax.xml.namespace.QName(
										"http://rpc.service.webservice/xsd",
										"newBusinessQuoteInput"), factory)
								.serialize(xmlWriter);
					}

					xmlWriter.writeEndElement();

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					MY_QNAME, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd",
					"newBusinessQuoteInput"));

			elementList.add(localNewBusinessQuoteInput == null ? null
					: localNewBusinessQuoteInput);

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetNewBusinessQuote parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				GetNewBusinessQuote object = new GetNewBusinessQuote();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = fullTypeName.substring(
									0, fullTypeName.indexOf(":"));
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);
							if (!"getNewBusinessQuote".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (GetNewBusinessQuote) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"newBusinessQuoteInput").equals(reader
									.getName())) {

						if ("true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {
							object.setNewBusinessQuoteInput(null);
							reader.next();

							reader.next();

						} else {

							object.setNewBusinessQuoteInput(NewBusinessQuoteInput.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class NewBusinessQuoteOutput1 implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://rpc.service.webservice/xsd", "NewBusinessQuoteOutput",
				"ns1");

		/**
		 * field for NewBusinessQuoteOutput
		 */

		protected NewBusinessQuoteOutput localNewBusinessQuoteOutput;

		/**
		 * Auto generated getter method
		 * 
		 * @return NewBusinessQuoteOutput
		 */
		public NewBusinessQuoteOutput getNewBusinessQuoteOutput() {
			return localNewBusinessQuoteOutput;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NewBusinessQuoteOutput
		 */
		public void setNewBusinessQuoteOutput(NewBusinessQuoteOutput param) {

			this.localNewBusinessQuoteOutput = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					// We can safely assume an element has only one type
					// associated with it

					if (localNewBusinessQuoteOutput == null) {
						throw new RuntimeException("Property cannot be null!");
					}
					localNewBusinessQuoteOutput.getOMElement(MY_QNAME, factory)
							.serialize(xmlWriter);

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					MY_QNAME, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			// We can safely assume an element has only one type associated with
			// it
			return localNewBusinessQuoteOutput.getPullParser(MY_QNAME);

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static NewBusinessQuoteOutput1 parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				NewBusinessQuoteOutput1 object = new NewBusinessQuoteOutput1();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					while (!reader.isEndElement()) {
						if (reader.isStartElement()) {

							if (reader.isStartElement()
									&& new javax.xml.namespace.QName(
											"http://rpc.service.webservice/xsd",
											"NewBusinessQuoteOutput")
											.equals(reader.getName())) {

								object.setNewBusinessQuoteOutput(NewBusinessQuoteOutput.Factory
										.parse(reader));

							} // End of if for expected property start element

							else {
								// A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new java.lang.RuntimeException(
										"Unexpected subelement "
												+ reader.getLocalName());
							}

						} else
							reader.next();
					} // end of while loop

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class NewBusinessQuoteInput implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * NewBusinessQuoteInput Namespace URI =
		 * http://rpc.service.webservice/xsd Namespace Prefix = ns1
		 */

		/**
		 * field for AnnuityOption
		 */

		protected java.lang.String localAnnuityOption;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getAnnuityOption() {
			return localAnnuityOption;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            AnnuityOption
		 */
		public void setAnnuityOption(java.lang.String param) {

			this.localAnnuityOption = param;

		}

		/**
		 * field for BirthDate
		 */

		protected java.util.Calendar localBirthDate;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.util.Calendar
		 */
		public java.util.Calendar getBirthDate() {
			return localBirthDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            BirthDate
		 */
		public void setBirthDate(java.util.Calendar param) {

			this.localBirthDate = param;

		}

		/**
		 * field for CertainDuration
		 */

		protected int localCertainDuration;

		/**
		 * Auto generated getter method
		 * 
		 * @return int
		 */
		public int getCertainDuration() {
			return localCertainDuration;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CertainDuration
		 */
		public void setCertainDuration(int param) {

			this.localCertainDuration = param;

		}

		/**
		 * field for CompanyStructureId
		 */

		protected java.lang.String localCompanyStructureId;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getCompanyStructureId() {
			return localCompanyStructureId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CompanyStructureId
		 */
		public void setCompanyStructureId(java.lang.String param) {

			this.localCompanyStructureId = param;

		}

		/**
		 * field for CostBasis
		 */

		protected double localCostBasis;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getCostBasis() {
			return localCostBasis;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CostBasis
		 */
		public void setCostBasis(double param) {

			this.localCostBasis = param;

		}

		/**
		 * field for EffectiveDate
		 */

		protected java.util.Calendar localEffectiveDate;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.util.Calendar
		 */
		public java.util.Calendar getEffectiveDate() {
			return localEffectiveDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EffectiveDate
		 */
		public void setEffectiveDate(java.util.Calendar param) {

			this.localEffectiveDate = param;

		}

		/**
		 * field for Frequency
		 */

		protected java.lang.String localFrequency;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getFrequency() {
			return localFrequency;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Frequency
		 */
		public void setFrequency(java.lang.String param) {

			this.localFrequency = param;

		}

		/**
		 * field for Gender
		 */

		protected java.lang.String localGender;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getGender() {
			return localGender;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Gender
		 */
		public void setGender(java.lang.String param) {

			this.localGender = param;

		}

		/**
		 * field for IssueAge
		 */

		protected int localIssueAge;

		/**
		 * Auto generated getter method
		 * 
		 * @return int
		 */
		public int getIssueAge() {
			return localIssueAge;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            IssueAge
		 */
		public void setIssueAge(int param) {

			this.localIssueAge = param;

		}

		/**
		 * field for IssueState
		 */

		protected java.lang.String localIssueState;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getIssueState() {
			return localIssueState;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            IssueState
		 */
		public void setIssueState(java.lang.String param) {

			this.localIssueState = param;

		}

		/**
		 * field for PaymentAmount
		 */

		protected double localPaymentAmount;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getPaymentAmount() {
			return localPaymentAmount;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PaymentAmount
		 */
		public void setPaymentAmount(double param) {

			this.localPaymentAmount = param;

		}

		/**
		 * field for PostJune1986InvestmentInd
		 */

		protected java.lang.String localPostJune1986InvestmentInd;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getPostJune1986InvestmentInd() {
			return localPostJune1986InvestmentInd;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PostJune1986InvestmentInd
		 */
		public void setPostJune1986InvestmentInd(java.lang.String param) {

			this.localPostJune1986InvestmentInd = param;

		}

		/**
		 * field for PurchaseAmount
		 */

		protected double localPurchaseAmount;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getPurchaseAmount() {
			return localPurchaseAmount;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PurchaseAmount
		 */
		public void setPurchaseAmount(double param) {

			this.localPurchaseAmount = param;

		}

		/**
		 * field for QuoteDate
		 */

		protected java.util.Calendar localQuoteDate;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.util.Calendar
		 */
		public java.util.Calendar getQuoteDate() {
			return localQuoteDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            QuoteDate
		 */
		public void setQuoteDate(java.util.Calendar param) {

			this.localQuoteDate = param;

		}

		/**
		 * field for Scenario
		 */

		protected int localScenario;

		/**
		 * Auto generated getter method
		 * 
		 * @return int
		 */
		public int getScenario() {
			return localScenario;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Scenario
		 */
		public void setScenario(int param) {

			this.localScenario = param;

		}

		/**
		 * field for StartDate
		 */

		protected java.util.Calendar localStartDate;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.util.Calendar
		 */
		public java.util.Calendar getStartDate() {
			return localStartDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StartDate
		 */
		public void setStartDate(java.util.Calendar param) {

			this.localStartDate = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					java.lang.String prefix = parentQName.getPrefix();
					java.lang.String namespace = parentQName.getNamespaceURI();

					if (namespace != null) {
						java.lang.String writerPrefix = xmlWriter
								.getPrefix(namespace);
						if (writerPrefix != null) {
							xmlWriter.writeStartElement(namespace,
									parentQName.getLocalPart());
						} else {
							if (prefix == null) {
								prefix = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();
							}

							xmlWriter.writeStartElement(prefix,
									parentQName.getLocalPart(), namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);
						}
					} else {
						xmlWriter.writeStartElement(parentQName.getLocalPart());
					}

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"annuityOption", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"annuityOption");
						}

					} else {
						xmlWriter.writeStartElement("annuityOption");
					}

					if (localAnnuityOption == null) {
						// write the nil attribute

						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localAnnuityOption));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "birthDate",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "birthDate");
						}

					} else {
						xmlWriter.writeStartElement("birthDate");
					}

					if (localBirthDate == null) {
						// write the nil attribute

						throw new RuntimeException("birthDate cannot be null!!");

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localBirthDate));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"certainDuration", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"certainDuration");
						}

					} else {
						xmlWriter.writeStartElement("certainDuration");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localCertainDuration));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"companyStructureId", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"companyStructureId");
						}

					} else {
						xmlWriter.writeStartElement("companyStructureId");
					}

					if (localCompanyStructureId == null) {
						// write the nil attribute

						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localCompanyStructureId));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "costBasis",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "costBasis");
						}

					} else {
						xmlWriter.writeStartElement("costBasis");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localCostBasis));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"effectiveDate", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"effectiveDate");
						}

					} else {
						xmlWriter.writeStartElement("effectiveDate");
					}

					if (localEffectiveDate == null) {
						// write the nil attribute

						throw new RuntimeException(
								"effectiveDate cannot be null!!");

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localEffectiveDate));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "frequency",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "frequency");
						}

					} else {
						xmlWriter.writeStartElement("frequency");
					}

					if (localFrequency == null) {
						// write the nil attribute

						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localFrequency));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "gender",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "gender");
						}

					} else {
						xmlWriter.writeStartElement("gender");
					}

					if (localGender == null) {
						// write the nil attribute

						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localGender));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "issueAge",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "issueAge");
						}

					} else {
						xmlWriter.writeStartElement("issueAge");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localIssueAge));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "issueState",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter
									.writeStartElement(namespace, "issueState");
						}

					} else {
						xmlWriter.writeStartElement("issueState");
					}

					if (localIssueState == null) {
						// write the nil attribute

						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localIssueState));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"paymentAmount", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"paymentAmount");
						}

					} else {
						xmlWriter.writeStartElement("paymentAmount");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localPaymentAmount));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"postJune1986InvestmentInd", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"postJune1986InvestmentInd");
						}

					} else {
						xmlWriter
								.writeStartElement("postJune1986InvestmentInd");
					}

					if (localPostJune1986InvestmentInd == null) {
						// write the nil attribute

						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localPostJune1986InvestmentInd));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"purchaseAmount", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"purchaseAmount");
						}

					} else {
						xmlWriter.writeStartElement("purchaseAmount");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localPurchaseAmount));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "quoteDate",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "quoteDate");
						}

					} else {
						xmlWriter.writeStartElement("quoteDate");
					}

					if (localQuoteDate == null) {
						// write the nil attribute

						throw new RuntimeException("quoteDate cannot be null!!");

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localQuoteDate));

					}

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "scenario",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "scenario");
						}

					} else {
						xmlWriter.writeStartElement("scenario");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localScenario));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "startDate",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "startDate");
						}

					} else {
						xmlWriter.writeStartElement("startDate");
					}

					if (localStartDate == null) {
						// write the nil attribute

						throw new RuntimeException("startDate cannot be null!!");

					} else {

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localStartDate));

					}

					xmlWriter.writeEndElement();

					xmlWriter.writeEndElement();

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					parentQName, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "annuityOption"));

			elementList.add(localAnnuityOption == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localAnnuityOption));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "birthDate"));

			if (localBirthDate != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localBirthDate));
			} else {
				throw new RuntimeException("birthDate cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "certainDuration"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCertainDuration));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "companyStructureId"));

			elementList.add(localCompanyStructureId == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localCompanyStructureId));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "costBasis"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCostBasis));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "effectiveDate"));

			if (localEffectiveDate != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localEffectiveDate));
			} else {
				throw new RuntimeException("effectiveDate cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "frequency"));

			elementList.add(localFrequency == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localFrequency));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "gender"));

			elementList.add(localGender == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localGender));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "issueAge"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localIssueAge));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "issueState"));

			elementList.add(localIssueState == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localIssueState));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "paymentAmount"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localPaymentAmount));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd",
					"postJune1986InvestmentInd"));

			elementList.add(localPostJune1986InvestmentInd == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localPostJune1986InvestmentInd));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "purchaseAmount"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localPurchaseAmount));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "quoteDate"));

			if (localQuoteDate != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localQuoteDate));
			} else {
				throw new RuntimeException("quoteDate cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "scenario"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localScenario));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "startDate"));

			if (localStartDate != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localStartDate));
			} else {
				throw new RuntimeException("startDate cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static NewBusinessQuoteInput parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				NewBusinessQuoteInput object = new NewBusinessQuoteInput();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = fullTypeName.substring(
									0, fullTypeName.indexOf(":"));
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);
							if (!"NewBusinessQuoteInput".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (NewBusinessQuoteInput) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"annuityOption").equals(reader.getName())) {

						if (!"true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {

							java.lang.String content = reader.getElementText();

							object.setAnnuityOption(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {
							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"birthDate").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setBirthDate(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDateTime(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"certainDuration").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setCertainDuration(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToInt(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"companyStructureId").equals(reader
									.getName())) {

						if (!"true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {

							java.lang.String content = reader.getElementText();

							object.setCompanyStructureId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {
							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"costBasis").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setCostBasis(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"effectiveDate").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setEffectiveDate(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDateTime(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"frequency").equals(reader.getName())) {

						if (!"true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {

							java.lang.String content = reader.getElementText();

							object.setFrequency(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {
							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"gender").equals(reader.getName())) {

						if (!"true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {

							java.lang.String content = reader.getElementText();

							object.setGender(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {
							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"issueAge").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setIssueAge(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToInt(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"issueState").equals(reader.getName())) {

						if (!"true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {

							java.lang.String content = reader.getElementText();

							object.setIssueState(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {
							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"paymentAmount").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setPaymentAmount(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"postJune1986InvestmentInd").equals(reader
									.getName())) {

						if (!"true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {

							java.lang.String content = reader.getElementText();

							object.setPostJune1986InvestmentInd(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {
							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"purchaseAmount").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setPurchaseAmount(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"quoteDate").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setQuoteDate(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDateTime(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"scenario").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setScenario(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToInt(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"startDate").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setStartDate(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDateTime(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class NewBusinessQuoteOutput implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * NewBusinessQuoteOutput Namespace URI =
		 * http://rpc.service.webservice/xsd Namespace Prefix = ns1
		 */

		/**
		 * field for CertainDuration
		 */

		protected int localCertainDuration;

		/**
		 * Auto generated getter method
		 * 
		 * @return int
		 */
		public int getCertainDuration() {
			return localCertainDuration;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CertainDuration
		 */
		public void setCertainDuration(int param) {

			this.localCertainDuration = param;

		}

		/**
		 * field for CommutedValue
		 */

		protected double localCommutedValue;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getCommutedValue() {
			return localCommutedValue;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CommutedValue
		 */
		public void setCommutedValue(double param) {

			this.localCommutedValue = param;

		}

		/**
		 * field for ExclusionRatio
		 */

		protected double localExclusionRatio;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getExclusionRatio() {
			return localExclusionRatio;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ExclusionRatio
		 */
		public void setExclusionRatio(double param) {

			this.localExclusionRatio = param;

		}

		/**
		 * field for Fees
		 */

		protected double localFees;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getFees() {
			return localFees;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Fees
		 */
		public void setFees(double param) {

			this.localFees = param;

		}

		/**
		 * field for FinalDistributionAmount
		 */

		protected double localFinalDistributionAmount;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getFinalDistributionAmount() {
			return localFinalDistributionAmount;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            FinalDistributionAmount
		 */
		public void setFinalDistributionAmount(double param) {

			this.localFinalDistributionAmount = param;

		}

		/**
		 * field for FrontEndLoads
		 */

		protected double localFrontEndLoads;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getFrontEndLoads() {
			return localFrontEndLoads;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            FrontEndLoads
		 */
		public void setFrontEndLoads(double param) {

			this.localFrontEndLoads = param;

		}

		/**
		 * field for PaymentAmount
		 */

		protected double localPaymentAmount;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getPaymentAmount() {
			return localPaymentAmount;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PaymentAmount
		 */
		public void setPaymentAmount(double param) {

			this.localPaymentAmount = param;

		}

		/**
		 * field for PremiumTaxes
		 */

		protected double localPremiumTaxes;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getPremiumTaxes() {
			return localPremiumTaxes;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PremiumTaxes
		 */
		public void setPremiumTaxes(double param) {

			this.localPremiumTaxes = param;

		}

		/**
		 * field for PurchaseAmount
		 */

		protected double localPurchaseAmount;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getPurchaseAmount() {
			return localPurchaseAmount;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PurchaseAmount
		 */
		public void setPurchaseAmount(double param) {

			this.localPurchaseAmount = param;

		}

		/**
		 * field for TotalProjectedAnnuity
		 */

		protected double localTotalProjectedAnnuity;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getTotalProjectedAnnuity() {
			return localTotalProjectedAnnuity;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TotalProjectedAnnuity
		 */
		public void setTotalProjectedAnnuity(double param) {

			this.localTotalProjectedAnnuity = param;

		}

		/**
		 * field for YearlyTaxableBenefit
		 */

		protected double localYearlyTaxableBenefit;

		/**
		 * Auto generated getter method
		 * 
		 * @return double
		 */
		public double getYearlyTaxableBenefit() {
			return localYearlyTaxableBenefit;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            YearlyTaxableBenefit
		 */
		public void setYearlyTaxableBenefit(double param) {

			this.localYearlyTaxableBenefit = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					java.lang.String prefix = parentQName.getPrefix();
					java.lang.String namespace = parentQName.getNamespaceURI();

					if (namespace != null) {
						java.lang.String writerPrefix = xmlWriter
								.getPrefix(namespace);
						if (writerPrefix != null) {
							xmlWriter.writeStartElement(namespace,
									parentQName.getLocalPart());
						} else {
							if (prefix == null) {
								prefix = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();
							}

							xmlWriter.writeStartElement(prefix,
									parentQName.getLocalPart(), namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);
						}
					} else {
						xmlWriter.writeStartElement(parentQName.getLocalPart());
					}

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"certainDuration", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"certainDuration");
						}

					} else {
						xmlWriter.writeStartElement("certainDuration");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localCertainDuration));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"commutedValue", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"commutedValue");
						}

					} else {
						xmlWriter.writeStartElement("commutedValue");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localCommutedValue));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"exclusionRatio", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"exclusionRatio");
						}

					} else {
						xmlWriter.writeStartElement("exclusionRatio");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localExclusionRatio));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "fees",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace, "fees");
						}

					} else {
						xmlWriter.writeStartElement("fees");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localFees));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"finalDistributionAmount", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"finalDistributionAmount");
						}

					} else {
						xmlWriter.writeStartElement("finalDistributionAmount");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localFinalDistributionAmount));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"frontEndLoads", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"frontEndLoads");
						}

					} else {
						xmlWriter.writeStartElement("frontEndLoads");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localFrontEndLoads));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"paymentAmount", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"paymentAmount");
						}

					} else {
						xmlWriter.writeStartElement("paymentAmount");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localPaymentAmount));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix, "premiumTaxes",
									namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"premiumTaxes");
						}

					} else {
						xmlWriter.writeStartElement("premiumTaxes");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localPremiumTaxes));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"purchaseAmount", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"purchaseAmount");
						}

					} else {
						xmlWriter.writeStartElement("purchaseAmount");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localPurchaseAmount));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"totalProjectedAnnuity", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"totalProjectedAnnuity");
						}

					} else {
						xmlWriter.writeStartElement("totalProjectedAnnuity");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localTotalProjectedAnnuity));

					xmlWriter.writeEndElement();

					namespace = "http://rpc.service.webservice/xsd";
					if (!namespace.equals("")) {
						prefix = xmlWriter.getPrefix(namespace);

						if (prefix == null) {
							prefix = org.apache.axis2.databinding.utils.BeanUtil
									.getUniquePrefix();

							xmlWriter.writeStartElement(prefix,
									"yearlyTaxableBenefit", namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);

						} else {
							xmlWriter.writeStartElement(namespace,
									"yearlyTaxableBenefit");
						}

					} else {
						xmlWriter.writeStartElement("yearlyTaxableBenefit");
					}

					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(localYearlyTaxableBenefit));

					xmlWriter.writeEndElement();

					xmlWriter.writeEndElement();

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					parentQName, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "certainDuration"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCertainDuration));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "commutedValue"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localCommutedValue));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "exclusionRatio"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localExclusionRatio));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "fees"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localFees));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd",
					"finalDistributionAmount"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localFinalDistributionAmount));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "frontEndLoads"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localFrontEndLoads));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "paymentAmount"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localPaymentAmount));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "premiumTaxes"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localPremiumTaxes));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "purchaseAmount"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localPurchaseAmount));

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd",
					"totalProjectedAnnuity"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localTotalProjectedAnnuity));

			elementList
					.add(new javax.xml.namespace.QName(
							"http://rpc.service.webservice/xsd",
							"yearlyTaxableBenefit"));

			elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
					.convertToString(localYearlyTaxableBenefit));

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static NewBusinessQuoteOutput parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				NewBusinessQuoteOutput object = new NewBusinessQuoteOutput();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = fullTypeName.substring(
									0, fullTypeName.indexOf(":"));
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);
							if (!"NewBusinessQuoteOutput".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (NewBusinessQuoteOutput) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"certainDuration").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setCertainDuration(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToInt(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"commutedValue").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setCommutedValue(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"exclusionRatio").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setExclusionRatio(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd", "fees")
									.equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setFees(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"finalDistributionAmount").equals(reader
									.getName())) {

						java.lang.String content = reader.getElementText();

						object.setFinalDistributionAmount(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"frontEndLoads").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setFrontEndLoads(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"paymentAmount").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setPaymentAmount(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"premiumTaxes").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setPremiumTaxes(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"purchaseAmount").equals(reader.getName())) {

						java.lang.String content = reader.getElementText();

						object.setPurchaseAmount(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"totalProjectedAnnuity").equals(reader
									.getName())) {

						java.lang.String content = reader.getElementText();

						object.setTotalProjectedAnnuity(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"yearlyTaxableBenefit").equals(reader
									.getName())) {

						java.lang.String content = reader.getElementText();

						object.setYearlyTaxableBenefit(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDouble(content));

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class GetNewBusinessQuoteFault implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://rpc.service.webservice/xsd",
				"getNewBusinessQuoteFault", "ns1");

		/**
		 * field for GetNewBusinessQuoteFault
		 */

		protected org.apache.axiom.om.OMElement localGetNewBusinessQuoteFault;

		/**
		 * Auto generated getter method
		 * 
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getGetNewBusinessQuoteFault() {
			return localGetNewBusinessQuoteFault;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GetNewBusinessQuoteFault
		 */
		public void setGetNewBusinessQuoteFault(
				org.apache.axiom.om.OMElement param) {

			this.localGetNewBusinessQuoteFault = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					java.lang.String prefix = parentQName.getPrefix();
					java.lang.String namespace = parentQName.getNamespaceURI();

					if (namespace != null) {
						java.lang.String writerPrefix = xmlWriter
								.getPrefix(namespace);
						if (writerPrefix != null) {
							xmlWriter.writeStartElement(namespace,
									parentQName.getLocalPart());
						} else {
							if (prefix == null) {
								prefix = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();
							}

							xmlWriter.writeStartElement(prefix,
									parentQName.getLocalPart(), namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);
						}
					} else {
						xmlWriter.writeStartElement(parentQName.getLocalPart());
					}

					if (localGetNewBusinessQuoteFault != null) {
						// write null attribute
						java.lang.String namespace2 = "http://rpc.service.webservice/xsd";
						if (!namespace2.equals("")) {
							java.lang.String prefix2 = xmlWriter
									.getPrefix(namespace2);

							if (prefix2 == null) {
								prefix2 = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();

								xmlWriter.writeStartElement(prefix2,
										"getNewBusinessQuoteFault", namespace2);
								xmlWriter.writeNamespace(prefix2, namespace2);
								xmlWriter.setPrefix(prefix2, namespace2);

							} else {
								xmlWriter.writeStartElement(namespace2,
										"getNewBusinessQuoteFault");
							}

						} else {
							xmlWriter
									.writeStartElement("getNewBusinessQuoteFault");
						}
						localGetNewBusinessQuoteFault.serialize(xmlWriter);
						xmlWriter.writeEndElement();
					} else {

						throw new RuntimeException(
								"getNewBusinessQuoteFault cannot be null!!");

					}

					xmlWriter.writeEndElement();

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					MY_QNAME, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd",
					"getNewBusinessQuoteFault"));

			if (localGetNewBusinessQuoteFault == null) {
				throw new RuntimeException(
						"getNewBusinessQuoteFault cannot be null!!");
			}
			elementList.add(localGetNewBusinessQuoteFault);

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetNewBusinessQuoteFault parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				GetNewBusinessQuoteFault object = new GetNewBusinessQuoteFault();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = fullTypeName.substring(
									0, fullTypeName.indexOf(":"));
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);
							if (!"getNewBusinessQuoteFault".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (GetNewBusinessQuoteFault) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"getNewBusinessQuoteFault").equals(reader
									.getName())) {

						boolean loopDone1 = false;
						javax.xml.namespace.QName startQname1 = new javax.xml.namespace.QName(
								"http://rpc.service.webservice/xsd",
								"getNewBusinessQuoteFault");

						while (!loopDone1) {
							if (reader.isStartElement()
									&& startQname1.equals(reader.getName())) {
								loopDone1 = true;
							} else {
								reader.next();
							}
						}

						// We need to wrap the reader so that it produces a fake
						// START_DOCUEMENT event
						// this is needed by the builder classes
						org.apache.axis2.databinding.utils.NamedStaxOMBuilder builder1 = new org.apache.axis2.databinding.utils.NamedStaxOMBuilder(
								new org.apache.axis2.util.StreamWrapper(reader),
								startQname1);
						object.setGetNewBusinessQuoteFault(builder1
								.getOMElement().getFirstElement());

						reader.next();

					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class NewBusinessQuoteInput0 implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://rpc.service.webservice/xsd", "NewBusinessQuoteInput",
				"ns1");

		/**
		 * field for NewBusinessQuoteInput
		 */

		protected NewBusinessQuoteInput localNewBusinessQuoteInput;

		/**
		 * Auto generated getter method
		 * 
		 * @return NewBusinessQuoteInput
		 */
		public NewBusinessQuoteInput getNewBusinessQuoteInput() {
			return localNewBusinessQuoteInput;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NewBusinessQuoteInput
		 */
		public void setNewBusinessQuoteInput(NewBusinessQuoteInput param) {

			this.localNewBusinessQuoteInput = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					// We can safely assume an element has only one type
					// associated with it

					if (localNewBusinessQuoteInput == null) {
						throw new RuntimeException("Property cannot be null!");
					}
					localNewBusinessQuoteInput.getOMElement(MY_QNAME, factory)
							.serialize(xmlWriter);

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					MY_QNAME, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			// We can safely assume an element has only one type associated with
			// it
			return localNewBusinessQuoteInput.getPullParser(MY_QNAME);

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static NewBusinessQuoteInput0 parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				NewBusinessQuoteInput0 object = new NewBusinessQuoteInput0();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					while (!reader.isEndElement()) {
						if (reader.isStartElement()) {

							if (reader.isStartElement()
									&& new javax.xml.namespace.QName(
											"http://rpc.service.webservice/xsd",
											"NewBusinessQuoteInput")
											.equals(reader.getName())) {

								object.setNewBusinessQuoteInput(NewBusinessQuoteInput.Factory
										.parse(reader));

							} // End of if for expected property start element

							else {
								// A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new java.lang.RuntimeException(
										"Unexpected subelement "
												+ reader.getLocalName());
							}

						} else
							reader.next();
					} // end of while loop

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class GetNewBusinessQuoteResponse implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://rpc.service.webservice/xsd",
				"getNewBusinessQuoteResponse", "ns1");

		/**
		 * field for _return
		 */

		protected NewBusinessQuoteOutput local_return;

		/**
		 * Auto generated getter method
		 * 
		 * @return NewBusinessQuoteOutput
		 */
		public NewBusinessQuoteOutput get_return() {
			return local_return;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            _return
		 */
		public void set_return(NewBusinessQuoteOutput param) {

			this.local_return = param;

		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory) {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {

					java.lang.String prefix = parentQName.getPrefix();
					java.lang.String namespace = parentQName.getNamespaceURI();

					if (namespace != null) {
						java.lang.String writerPrefix = xmlWriter
								.getPrefix(namespace);
						if (writerPrefix != null) {
							xmlWriter.writeStartElement(namespace,
									parentQName.getLocalPart());
						} else {
							if (prefix == null) {
								prefix = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();
							}

							xmlWriter.writeStartElement(prefix,
									parentQName.getLocalPart(), namespace);
							xmlWriter.writeNamespace(prefix, namespace);
							xmlWriter.setPrefix(prefix, namespace);
						}
					} else {
						xmlWriter.writeStartElement(parentQName.getLocalPart());
					}

					if (local_return == null) {

						java.lang.String namespace2 = "http://rpc.service.webservice/xsd";

						if (!namespace2.equals("")) {
							java.lang.String prefix2 = xmlWriter
									.getPrefix(namespace2);

							if (prefix2 == null) {
								prefix2 = org.apache.axis2.databinding.utils.BeanUtil
										.getUniquePrefix();

								xmlWriter.writeStartElement(prefix2, "return",
										namespace2);
								xmlWriter.writeNamespace(prefix2, namespace2);
								xmlWriter.setPrefix(prefix2, namespace2);

							} else {
								xmlWriter.writeStartElement(namespace2,
										"return");
							}

						} else {
							xmlWriter.writeStartElement("return");
						}

						// write the nil attribute
						writeAttribute("xsi",
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil", "true", xmlWriter);
						xmlWriter.writeEndElement();
					} else {
						local_return.getOMElement(
								new javax.xml.namespace.QName(
										"http://rpc.service.webservice/xsd",
										"return"), factory)
								.serialize(xmlWriter);
					}

					xmlWriter.writeEndElement();

				}

				/**
				 * Util method to write an attribute with the ns prefix
				 */
				private void writeAttribute(java.lang.String prefix,
						java.lang.String namespace, java.lang.String attName,
						java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (xmlWriter.getPrefix(namespace) == null) {
						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);

					}

					xmlWriter.writeAttribute(namespace, attName, attValue);

				}

				/**
				 * Util method to write an attribute without the ns prefix
				 */
				private void writeAttribute(java.lang.String namespace,
						java.lang.String attName, java.lang.String attValue,
						javax.xml.stream.XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					if (namespace.equals("")) {
						xmlWriter.writeAttribute(attName, attValue);
					} else {
						registerPrefix(xmlWriter, namespace);
						xmlWriter.writeAttribute(namespace, attName, attValue);
					}
				}

				/**
				 * Register a namespace prefix
				 */
				private java.lang.String registerPrefix(
						javax.xml.stream.XMLStreamWriter xmlWriter,
						java.lang.String namespace)
						throws javax.xml.stream.XMLStreamException {
					java.lang.String prefix = xmlWriter.getPrefix(namespace);

					if (prefix == null) {
						prefix = createPrefix();

						while (xmlWriter.getNamespaceContext().getNamespaceURI(
								prefix) != null) {
							prefix = createPrefix();
						}

						xmlWriter.writeNamespace(prefix, namespace);
						xmlWriter.setPrefix(prefix, namespace);
					}

					return prefix;
				}

				/**
				 * Create a prefix
				 */
				private java.lang.String createPrefix() {
					return "ns" + (int) Math.random();
				}
			};

			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					MY_QNAME, factory, dataSource);

		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName) {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName(
					"http://rpc.service.webservice/xsd", "return"));

			elementList.add(local_return == null ? null : local_return);

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetNewBusinessQuoteResponse parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				GetNewBusinessQuoteResponse object = new GetNewBusinessQuoteResponse();
				int event;
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = fullTypeName.substring(
									0, fullTypeName.indexOf(":"));
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);
							if (!"getNewBusinessQuoteResponse".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (GetNewBusinessQuoteResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					boolean isReaderMTOMAware = false;

					try {
						isReaderMTOMAware = java.lang.Boolean.TRUE
								.equals(reader
										.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
					} catch (java.lang.IllegalArgumentException e) {
						isReaderMTOMAware = false;
					}

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://rpc.service.webservice/xsd",
									"return").equals(reader.getName())) {

						if ("true".equals(reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil"))) {
							object.set_return(null);
							reader.next();

							reader.next();

						} else {

							object.set_return(NewBusinessQuoteOutput.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new java.lang.RuntimeException(
								"Unexpected subelement "
										+ reader.getLocalName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	private org.apache.axiom.om.OMElement toOM(
			test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote param,
			boolean optimizeContent) {

		return param
				.getOMElement(
						test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote.MY_QNAME,
						org.apache.axiom.om.OMAbstractFactory.getOMFactory());

	}

	private org.apache.axiom.om.OMElement toOM(
			test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse param,
			boolean optimizeContent) {

		return param
				.getOMElement(
						test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse.MY_QNAME,
						org.apache.axiom.om.OMAbstractFactory.getOMFactory());

	}

	private org.apache.axiom.om.OMElement toOM(
			test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault param,
			boolean optimizeContent) {

		return param
				.getOMElement(
						test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault.MY_QNAME,
						org.apache.axiom.om.OMAbstractFactory.getOMFactory());

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote param,
			boolean optimizeContent) {
		org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
				.getDefaultEnvelope();

		emptyEnvelope
				.getBody()
				.addChild(
						param.getOMElement(
								test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote.MY_QNAME,
								factory));

		return emptyEnvelope;
	}

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			java.lang.Class type, java.util.Map extraNamespaces) {

		try {

			if (test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote.class
					.equals(type)) {

				return test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuote.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse.class
					.equals(type)) {

				return test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault.class
					.equals(type)) {

				return test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private void setOpNameArray() {
		opNameArray = null;
	}

}
