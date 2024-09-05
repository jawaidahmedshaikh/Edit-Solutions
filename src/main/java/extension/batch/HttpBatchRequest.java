/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extension.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Batch processes within SEGSoftware are accessible via Http requests. 
 * Currently SEGSoftware uses the available MBean HttpServer that is included with
 * the MBean API. The HttpServer can accept Http requests to kick-off any
 * batch process available within SEEGSoftware. An example request looks as follows:
 * 
 * http://foomachinename:9092/InvokeAction//segllc.com:name=Batch/action=processDailyBatch?action=processDailyBatch&p1+java.lang.String=2008/02/22&p2+java.lang.String=FOOCOMPANYNAME&p3+java.lang.String=FOOOPERATOR&p4+java.lang.String=FOOUSERNAME&p5+java.lang.String=FOOPASSWORD&p6+java.lang.String=SYNCHRONOUS
 * 
 * The above URL can be re-written to emphasize the user-supplied parameters using [] as follows:
 * 
 * http://[foomachinename]:9092/InvokeAction//segllc.com:name=Batch/action=[processDailyBatch]?action=[processDailyBatch]&p1+java.lang.String=[2008/02/22]&p2+java.lang.String=[FOOCOMPANYNAME]&p3+java.lang.String=[FOOOPERATOR]&p4+java.lang.String=[FOOUSERNAME]&p5+java.lang.String=[FOOPASSWORD]&p6+java.lang.String=SYNCHRONOUS
 * 
 * The user-supplied parameters are thus:
 * 
 * 1. The machine name (e.g. [foomachinename]) 
 * 2. The process name (e.g. [processDailyBatch])
 * 3. The ordered parameters required by the process (e.g. [2008/02/22], [FOOCOMPANYNAME], [FOOOPERATOR], [FOOUSERNAME], [FOOPASSWORD])
 * 
 * 
 * @author gfrosti
 */
public class HttpBatchRequest
{
    /**
     * One of the response messages.
     */
    public static String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    
    /**
     * We don't consider this configurable by the user - the MBean Http Server
     * is always listening at this port.
     */
    public static final String PORT = "9092";

    /**
     * The user-supplied ordered parameters required to kick-off a Batch process.
     */
    private String[] processParameters;

    /**
     * The target machine hosting the MBean Http Server.
     */
    private String machineName;

    /**
     * The name of the batch process to execute.
     */
    private String processName;
    
    /**
     * The original/unparsed paramters.
     */
    private String[] originalParameters;

    /**
     * Constructor taking the user-supplied ordered parameters.
     * @param parameters
     */
    public HttpBatchRequest(String[] parameters)
    {
        this.originalParameters = parameters;
    }

    /**
     * Executes the batch request and returns a code. The codes are established
     * in a separate of SEGSoftware - namely Batch.java. The goal was to allow
     * this HttpBatchRequest to run independently of SEGSoftware. For this reason,
     * the codes are duplicated (they have been quite stable over the years).
     * @see Batch#PROCESSED_WITHOUT_ERRORS
     * @see Batch#PROCESSED_WITH_ERRORS
     * @see Batch#PROCESSED_BUT_NO_ELEMENTS_FOUND
     * @see Batch#UNEXPECTED_ERROR
     * @return the result of the batch process as a single code
     */
    public String executeHttpRequest()
    {
        String batchStatus = null;

        BufferedReader in = null;

        try
        {
            parseParameters();            
            
            URL batchURL = buildBatchURL();

            URLConnection connection = batchURL.openConnection();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            batchStatus = validateBatchStatus(in.readLine());
        }
        catch (Exception ex)
        {
            batchStatus = UNEXPECTED_ERROR;
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException ex)
                {
                    batchStatus = UNEXPECTED_ERROR;
                }
            }
        }

        return batchStatus;
    }

    /**
     * Constructs a Http URL (representing a Get) from the user-supplied parameters in the form of:
     * http://[FOOMACHINENAME]:9092/InvokeAction//segllc.com:name=Batch/action=[PROCESSNAME]?action=[PROCESSNAME]&p1+java.lang.String=[P1VALUE]&p2+java.lang.String=[P2VALUE]&pn+java.lang.String=[PNVALUE]&pn+java.lang.String=SYNCHRONOUS
     * UTF-8 Encoding is manually performed on the URL since its format is well known in the context
     * of this context.
     * @return
     */
    private URL buildBatchURL() throws UnsupportedEncodingException, MalformedURLException
    {
        String urlString = "http://" + getMachineName() + ":" + PORT + "/InvokeAction//segllc.com" +
                "%3Aname%3DBatch/action=" + getProcessName() +
                "?action=" + getProcessName();

        for (int i = 0; i < getProcessParameters().length; i++)
        {
            String nextParameter = "&p" + (i + 1);

            nextParameter += "%2Bjava.lang.String=" + getProcessParameters()[i];

            urlString += nextParameter;
        }
        
        URL url = new URL(urlString);

        return url;
    }

    /**
     * Parses the supplied parameters to extract:
     * 
     * parameters[0] = the machine name
     * parameters[1] = the process name
     * parameters[2 - (n-1)] the parameter values required to execute the batch process.
     */
    private void parseParameters()
    {
        setMachineName(getOriginalParameters()[0]);

        setProcessName(getOriginalParameters()[1]);

        String[] processParametersArray = new String[getOriginalParameters().length - 2];

        for (int i = 0; i < processParametersArray.length; i++)
        {
            processParametersArray[i] = getOriginalParameters()[i + 2];
        }

        setProcessParameters(processParametersArray);
    }

    /**
     * @see #machineName
     * @return
     */
    public String getMachineName()
    {
        return machineName;
    }

    /**
     * @see #machineName
     * @param machineName
     */
    public void setMachineName(String machineName)
    {
        this.machineName = machineName;
    }

    /**
     * @see #processName
     * @return
     */
    public String getProcessName()
    {
        return processName;
    }

    /**
     * @see #processName
     * @param processName
     */
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    /**
     * @see #processParameters
     * @return
     */
    public String[] getProcessParameters()
    {
        return processParameters;
    }

    /**
     * @see #processParameters
     * @param processParameters
     */
    public void setProcessParameters(String[] processParameters)
    {
        this.processParameters = processParameters;
    }
    
    /**
     * @see #originalParameters
     * @return
     */
    public String[] getOriginalParameters()
    {
        return originalParameters;
    }

    /**
     * @see #originalParameters
     * @param originalParameters
     */
    public void setOriginalParameters(String[] originalParameters)
    {
        this.originalParameters = originalParameters;
    }    

    /**
     * We are only going to allow a small set of batchStatus'. If 
     * we get something we didn't expect, then simply mark it 
     * as an error.
     * @param batchStatus
     * @return
     */
    private String validateBatchStatus(String batchStatus)
    {
        String validBatchStatus  = batchStatus;
        
        if (!batchStatus.startsWith("PROCESSED_"))
        {
            validBatchStatus = UNEXPECTED_ERROR;
        }
                
        return validBatchStatus;
    }
}
