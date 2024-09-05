/*
 * User: sdorman
 * Date: Jun 2, 2004
 * Time: 12:30:58 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package fission.ui.servlet;

import electric.registry.Registry;

import fission.ui.servlet.BaseRequestManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;

import edit.services.config.ServicesConfig;
import edit.common.vo.SOAPConfig;


/**
 * Manages all requests from a SOAP client to the back end of the EDITSolutions system
 */

public class SOAPRequestManager extends BaseRequestManager
{
    private static Object soapAdapter = null;


    public void init(ServletConfig sc) throws ServletException
    {
        super.init(sc);

        System.out.println("\n*** Start Of SOAPRequestManager.init() ***");

        try
        {
            //  Get SOAP configuration info for binding
            SOAPConfig soapConfig = ServicesConfig.getSOAPConfig();

            if (soapConfig != null)
            {
                final String url = soapConfig.getURL();

                String adapterClassName = soapConfig.getAdapterClass();

                final Class adapterClass = Class.forName(adapterClassName);

                //  Bind via a new thread to prevent servlet from hanging
                System.out.println("Binding service...");

                new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            getSOAPAdapter(url, adapterClass);
                        }
                        catch (ServletException e)
                        {
                          System.out.println(e);

                            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                        }
                    }
                }).start();

                System.out.println("Service bound!");
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw new ServletException(e);
        }

        System.out.println("*** End of SOAPRequestManager.init() ***\n");
    }

    public Object getSOAPAdapter(String url, Class soapAdapterClass) throws ServletException
    {
        if (soapAdapter == null)
        {
            try
            {
                soapAdapter = Registry.bind(url, soapAdapterClass);
            }
            catch (Throwable e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new ServletException(e);
            }
        }

        return soapAdapter;
    }
}