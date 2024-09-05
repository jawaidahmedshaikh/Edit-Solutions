
/**
 * GetNewBusinessQuoteFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.1 Nov 13, 2006 (07:31:44 LKT)
 */
package test.webservice.quote.rpc.client;

public class GetNewBusinessQuoteFaultException extends java.lang.Exception{
    
    private test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault faultMessage;
    
    public GetNewBusinessQuoteFaultException() {
        super("GetNewBusinessQuoteFaultException");
    }
           
    public GetNewBusinessQuoteFaultException(java.lang.String s) {
       super(s);
    }
    
    public GetNewBusinessQuoteFaultException(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault msg){
       faultMessage = msg;
    }
    
    public test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteFault getFaultMessage(){
       return faultMessage;
    }
}
    