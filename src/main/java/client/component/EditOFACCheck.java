/*
 * User: sramamurthy
 * Date: Jul 26, 2004
 * Time: 3:07:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client.component;

import client.ClientAddress;
import client.ClientDetail;

import client.dm.StorageManager;

import edit.common.EDITDate;

import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.EDITLog;

import edit.services.config.ServicesConfig;
import edit.services.*;

import electric.proxy.IProxy;

import electric.registry.Registry;

import fission.utility.Util;

import java.io.FileWriter;
import java.io.PrintWriter;

import batch.business.*;



public class EditOFACCheck
{
    public static String OFAC_SERVICE_DOWN_MESSAGE = "OFAC webservice down! Please Bypass OFAC";
    private PrintWriter writer = null;
    private String ofacCheckURL;
    private String ofacCheckValue;
    private String ofacLogFile;
    private IProxy proxy = null;
    private StorageManager sm = null;
    private String ofacMethodName = null;

    /**
     *
     * @throws Exception
     */
    public EditOFACCheck() throws Exception
    {
        ofacCheckURL = ServicesConfig.getOFACConfig().getURL();
        ofacCheckValue = ServicesConfig.getOFACConfig().getOFACCheck();
        ofacMethodName = ServicesConfig.getOFACConfig().getOFACMethod();

        EDITLog ofacLog = ServicesConfig.getEDITLogByLogName("OFAC");
        ofacLogFile = ofacLog.getFile();

        /*      if(Boolean.valueOf(ofacCheckValue).booleanValue() == true)
                proxy = Registry.bind(ofacCheckURL); */

        //        if(Boolean.valueOf(ofacCheckValue).booleanValue() == true)
    }

    /**
     *
     * @param ofacCheckURL
     * @param connectAttempts
     * @param attemptIntTimeInMsecs
     * @throws Exception
     */
    private void enableProxy(String ofacCheckURL, int connectAttempts, long attemptIntTimeInMsecs) throws Exception
    {
        for (int i = 0; i < connectAttempts; i++)
        {
            try
            {
                proxy = Registry.bind(ofacCheckURL);

                return;
            }
            catch (electric.registry.RegistryException regExp)
            {
                writer = new PrintWriter(new FileWriter(ofacLogFile, true));
                writer.println(new java.util.Date().toString() + " WSDL BIND FAILED ATTEMPT:" + (i + 1) + " EXCEPTION MESSAGE:" + regExp.getMessage());
                writer.println(new java.util.Date().toString() + " ABOUT TO WAIT FOR:" + attemptIntTimeInMsecs);

                if ((i + 1) == connectAttempts)
                { // On last failed attempt, exception thrown
                    throw new OFACBindException(regExp.getMessage());
                }

                proxy = null;

                Thread.sleep(attemptIntTimeInMsecs);
            }
            catch (Exception exp)
            {
                proxy = null;

                System.out.println(exp);

                exp.printStackTrace();
            }
            finally
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
        }
    }

    /**
     *
     * @param clientDetailCE
     * @throws Exception
     */
    public void validateClient(ClientDetail clientDetailCE) throws Exception
    {
        if ((ofacCheckValue == null) || ((Boolean.valueOf(ofacCheckValue)).booleanValue() == false))
        {
            return;
        }

        if (proxy == null)
        {
            enableProxy(ofacCheckURL, ServicesConfig.getOFACConfig().getOFACConnectAttempts(), ServicesConfig.getOFACConfig().getOFACConnectIntervalTimeInMSecs());
        }

        String firstName = "";
        String lastName = "";
        String middleName = "";
        String corporateName = "";
        String trustType = "";
        String address1 = "";
        String address2 = "";
        String address3 = "";
        String address4 = "";
        String state = "";
        String city = "";
        String zip = "";

        ClientDetailVO clDetailVo = (ClientDetailVO) clientDetailCE.getVO();

        firstName = clDetailVo.getFirstName();
        lastName = clDetailVo.getLastName();
        middleName = clDetailVo.getMiddleName();
        corporateName = clDetailVo.getCorporateName();
        trustType = clDetailVo.getTrustTypeCT();

        ClientAddress clientAddressCE = client.ClientAddress.findByClientDetailPK_And_AddressTypeCT(clientDetailCE.getPK(), ClientAddress.CLIENT_PRIMARY_ADDRESS);

        if (clientAddressCE != null)
        {
            ClientAddressVO clientAddressVO = (ClientAddressVO) clientAddressCE.getVO();

            //  String Firstname, String Lastname, String Middlename, String CorporateName, String TrustType, String Address1, String Address2, String Address3, String Address4, String City, String State, String Zip );
            address1 = clientAddressVO.getAddressLine1();
            address2 = clientAddressVO.getAddressLine2();
            address3 = clientAddressVO.getAddressLine3();
            address4 = clientAddressVO.getAddressLine4();

            state = clientAddressVO.getStateCT();
            city = clientAddressVO.getCity();
            zip = clientAddressVO.getZipCode();
        }

        String[] ofacParms = new String[]
        {
            firstName,
            lastName,
            middleName,
            corporateName,
            trustType,
            address1,
            address2,
            address3,
            address4,
            state,
            city,
            zip
        };

        //SRAMAM 10/19/2004 Null init string
        for (int lc = 0; lc < ofacParms.length; lc++)
        {
            ofacParms[lc] = Util.initString(ofacParms[lc], "");
        }

        updateOFACCheckDateToDB(clDetailVo, launchOFACSOAPRequest(ofacParms));
    }

    /**
     *
     * @param clDetailVo
     * @param invokeStatus
     * @throws Exception
     */
    private void updateOFACCheckDateToDB(ClientDetailVO clDetailVo, boolean invokeStatus) throws Exception
    {
        if (sm == null)
        {
            sm = new StorageManager();
        }

        String lastOFACDate;

        if (invokeStatus)
        {
            lastOFACDate = new EDITDate().getFormattedDate();
        }
        else
        {
//            lastOFACDate = "88" + EDITDate.DATE_DELIMITER + "88" + EDITDate.DATE_DELIMITER + "8888";
            lastOFACDate = null;
        }

        clDetailVo.setLastOFACCheckDate(lastOFACDate);
        (new ClientDetail(clDetailVo)).save();

        //        sm.saveOrUpdateClient(clDetailVo);
    }

    /**
     *
     * @throws Exception
     */
    public void ofacCheckOnAllClients() throws Exception
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS).tagBatchStart(Batch.BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS, "OFAC Check");

        try
        {
            //ClientDetailVO[] clientDetailVO = (ClientDetailVO[])DAOFactory.getClientDetailDAO().findAll();
            if ((ofacCheckValue == null) || ((Boolean.valueOf(ofacCheckValue)).booleanValue() == false))
            {
                return;
            }

            long[] clientDetailPKs = (new client.dm.dao.FastDAO()).findALLClientDetailPK();

            for (int lc = 0; lc < clientDetailPKs.length; lc++)
            {
                validateClient(new ClientDetail(clientDetailPKs[lc]));

                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS).updateSuccess();
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS).updateFailure();

            System.out.println(e);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS).tagBatchStop();
        }
    }

    /**
     *
     * @param ofacParms
     * @return
     * @throws Exception
     */
    private boolean launchOFACSOAPRequest(String[] ofacParms) throws Exception
    {
        if ((ofacCheckValue != null) && (new Boolean(ofacCheckValue).booleanValue() == true))
        {
            try
            {
                writer = new PrintWriter(new FileWriter(ofacLogFile, true));
                writer.println(new java.util.Date().toString() + "Send OFAC check PARMS[0]~[11]:-");
                writer.println("    firstName       :" + ofacParms[0]);
                writer.println("    lastName        :" + ofacParms[1]);
                writer.println("    middleName      :" + ofacParms[2]);
                writer.println("    corporateName   :" + ofacParms[3]);
                writer.println("    trustType       :" + ofacParms[4]);
                writer.println("    address1        :" + ofacParms[5]);
                writer.println("    address2        :" + ofacParms[6]);
                writer.println("    address3        :" + ofacParms[7]);
                writer.println("    address4        :" + ofacParms[8]);
                writer.println("    state           :" + ofacParms[9]);
                writer.println("    city            :" + ofacParms[10]);
                writer.println("    zip             :" + ofacParms[11]);

                if ((ofacMethodName != null) && (ofacMethodName.length() > 0))
                {
                    Boolean offCheckRetValue;
                    offCheckRetValue = (Boolean) proxy.invoke(ofacMethodName, ofacParms);

                    /*                    try{
                                            offCheckRetValue = (Boolean)proxy.invoke(ofacMethodName,ofacParms);
                                        }catch(java.rmi.ConnectException rmiException){
                                           enableProxy(ofacCheckURL,ServicesConfig.getOFACConfig().getOFACConnectAttempts(),
                                                    ServicesConfig.getOFACConfig().getOFACConnectIntervalTimeInMSecs());

                                           offCheckRetValue = (Boolean)proxy.invoke(ofacMethodName,ofacParms);
                                        }
                     */
                    writer.println(new java.util.Date().toString() + "****Return from OFACCheck SOAP webservice:" + offCheckRetValue + "****");

                    return true;
                }
                else
                {
                    writer.println(new java.util.Date().toString() + "****OFACMethod name is not valid, and it is : " + ofacMethodName + "****");

                    return false;
                }
            }
            catch (Exception exp)
            {
                writer.println(new java.util.Date().toString() + " EXCEPTION DURING OFACCHECK SOAP CALL");
                exp.printStackTrace(writer);
                throw exp;
            }
            catch (Throwable exp)
            {
                writer.println(new java.util.Date().toString() + " THROWABLE ERR/EXCEPTION DURING OFACCHECK SOAP CALL");
                exp.printStackTrace(writer);
                throw (new Exception(exp.toString()));
            }
            finally
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
        }
        else
        {
            if (writer == null)
            {
                writer = new PrintWriter(new FileWriter(ofacLogFile, true));
            }

            writer.println(new java.util.Date().toString() + " OFACCheck PARAM IN EDITServicesConfig.xml is:- " + ServicesConfig.getOFACConfig().getOFACCheck());

            if (writer != null)
            {
                writer.close();
            }
        }

        return false;
    }
}
