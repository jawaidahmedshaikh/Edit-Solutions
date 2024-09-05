
    /**
     * NewBusinessQuoteCallbackHandler.java
     *
     * This file was auto-generated from WSDL
     * by the Apache Axis2 version: 1.1 Nov 13, 2006 (07:31:44 LKT)
     */
    package test.webservice.quote.rpc.client;

    /**
     *  NewBusinessQuoteCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class NewBusinessQuoteCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public NewBusinessQuoteCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public NewBusinessQuoteCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getNewBusinessQuote method
            *
            */
           public void receiveResultgetNewBusinessQuote(
                    test.webservice.quote.rpc.client.NewBusinessQuoteStub.GetNewBusinessQuoteResponse param1) {
           }

          /**
           * auto generated Axis2 Error handler
           *
           */
            public void receiveErrorgetNewBusinessQuote(java.lang.Exception e) {
            }
                


    }
    