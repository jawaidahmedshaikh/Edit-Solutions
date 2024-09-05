package edit.services.command;

import java.lang.reflect.Method;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import webservice.SEGRequestDocument;
import edit.services.db.hibernate.*;


/**
 * External requests that arrive that respect the SEGRequest/SEGRequestDispatcher
 * paradigm will always define three things [required] to invoke the service:
 * 
 * 1. The Service Name
 * 2. The Service Operation to be invoked on the service specified by the Service Name.
 * 3. The Request Parameters in an XML Document (DOM4J) format.
 * 
 * The above requirements permit a reflection-based approach where the following can be expected:
 * 
 * 1. The Service Name represents a SEG Component.
 * 2. The Service Operation represents a method in the SEG Component.
 * 3. The Request Parameters as a DOM4J document is the source for the XML parameters.
 * 
 * In future versions, the Service Name to SEG Component should be defined in a mapping
 * file. For no other reason, we can't guarantee the package names which is required
 * when performing reflection.
 */
public class SEGRequestDispatcher
{
    /**
     * The agreed upon service name for Billing.
     */
    public static final String SERVICE_NAME_BILLING = "Billing";
    
    public static final String SERVICE_NAME_GROUP = "Group";
    
    public static final String SERVICE_NAME_CODETABLE = "CodeTable";
    
    public static final String SERVICE_NAME_SEGMETADATA = "SEGMetaData";
    
    public static final String SERVICE_NAME_CONVERSION = "Conversion";
    
    public static final String SERVICE_NAME_PRASE = "PRASE";

    public static final String SERVICE_NAME_QUERY = "Query";

    public static final String OPERATOR_NAME = "Operator";

    /**
     * The request for which to invoke a service.
     */
    private SEGRequest segRequest;

    public SEGRequestDispatcher(SEGRequest segRequest)
    {
        this.segRequest = segRequest;
    }

    /**
     * Dispatches the constructor-specified SEGRequest.
     * @return the result of the specified SEGRequest as a DOM4J Document
     * @see #segRequest
     */
    public Document dispatch()
    {
        Document responseDocument = null;

        Object serviceComponent = getServiceComponent();
        
        Method serviceOperation = getServiceOperation(serviceComponent);

        putOperatorOnThreadLocal();

        try
        {
            Document segRequestVODocument = getSegRequest().getSegRequestVODocument();
            
            SEGRequestDocument.checkForGeneratePRASETest(segRequestVODocument);

            SEGRequestDocument.checkForRecordPRASE(segRequestVODocument);

            responseDocument = (Document) serviceOperation.invoke(serviceComponent, segRequestVODocument);
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        finally
        {
            if (responseDocument == null)
            {
                responseDocument = new DefaultDocument();
                
                Element segResponseVO = new DefaultElement("SEGResponseVO");
                
                responseDocument.setRootElement(segResponseVO);
            }
        }
        
        return responseDocument;
    }

    /**
     * An instance of the targeted service component.
     
     * @return
     */
    private Object getServiceComponent()
    {
        String serviceName = getSegRequest().getServiceName();
    
        Object serviceComponent = null;

        String serviceComponentName = null;

        if (serviceName.equals(SERVICE_NAME_BILLING))
        {
            serviceComponentName = "billing.component.BillingComponent";
        }
        else if (serviceName.equals(SERVICE_NAME_GROUP))
        {
            serviceComponentName = "group.component.GroupComponent";
        }
        else if (serviceName.equals(SERVICE_NAME_CODETABLE))
        {
            serviceComponentName = "codetable.component.CodeTableComponent";
        }
        else if (serviceName.equals(SERVICE_NAME_SEGMETADATA))
        {
            serviceComponentName = "edit.services.component.SEGMetaDataComponent";
        }
        else if (serviceName.equals(SERVICE_NAME_CONVERSION))
        {
            serviceComponentName = "conversion.component.ConversionComponent";
        }
        else if (serviceName.equals(SERVICE_NAME_PRASE))
        {
            serviceComponentName = "engine.component.PRASEComponent";
        }
        else if (serviceName.equals(SERVICE_NAME_QUERY))
        {
            serviceComponentName = "query.component.QueryComponent";
        }

        try
        {
            serviceComponent = Class.forName(serviceComponentName).newInstance();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return serviceComponent;
    }

    /**
     * @see #segRequest
     * @return
     */
    public SEGRequest getSegRequest()
    {
        return segRequest;
    }

    /**
     * The targeted Method for the targeted service component.
     * @param serviceComponent
     * @return
     */
    private Method getServiceOperation(Object serviceComponent)
    {
        String operationName = getSegRequest().getOperationName();
        
        Method operation = null;
        
        try
        {
            operation = serviceComponent.getClass().getMethod(operationName, new Class<?>[]{Document.class});
        }
        catch (NoSuchMethodException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        
        return operation;
    }

    /**
     * Places the operator (user) that is calling the service in the thread local for back end usage
     */
    private void putOperatorOnThreadLocal()
    {
        String operator = getSegRequest().getOperatorName();

        SessionHelper.putInThreadLocal(OPERATOR_NAME, operator);
    }
}
