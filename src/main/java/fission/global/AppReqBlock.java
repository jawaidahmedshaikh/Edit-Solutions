/*
* AppReqBlock.java      Version 2.00  06/04/2001
*
* Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
*
* This program is the confidential and proprietary information of
* Systems Engineering Group, LLC and may not be copied in whole or in part
* without the written permission of Systems Engineering Group, LLC.
*/
package fission.global;

import edit.portal.common.session.*;
import edit.portal.config.ConfigPortalMain;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import fission.beans.FormBean;
import fission.beans.SessionBean;

import fission.utility.Util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import org.hibernate.EntityMode;
import org.hibernate.metadata.ClassMetadata;

import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import reinsurance.ui.*;
//import electric.registry.Registry;
//import electric.util.*;

/*==== JavaDoc =======================================================================*/
/**
 AppReqBlock Class (AppReqBlock.java)
 This class is instanciated in response to an HTML Request (doXXX on the main servlet).
 All request parameters and processing aids will be stored in the Application Request
 Block. This storage is cleared when a response is returned to the client and the
 request (good or bad) is complete.
 */
/*====================================================================================*/
public class AppReqBlock
{
    private HttpServletRequest  request;
    private HttpServletResponse response;
    private HttpSession         session;
    private FormBean            formBean;
    private ConfigPortalMain configPortalMain;
    private ServletContext servletContext;
    private HashMap reqParms;

    /**
     Constructor will extract all application request parameters out of Http blocks and initialize
     any general constructs required to process a single servlet request.
     */
    public AppReqBlock(HttpServletRequest request,
                       HttpServletResponse response,
                       ServletContext servletContext,
                       FormBean formBean,
                       ConfigPortalMain configPortalMain) throws Exception{

        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.formBean = formBean;
        this.configPortalMain = configPortalMain;

        session = request.getSession(true);

        reqParms = new HashMap();
    }

    public FormBean getFormBean() {

        return formBean;
    }

    public ConfigPortalMain getConfigPortalMain(){

        return configPortalMain;
    }

    /**
     Get the HTTPServletResponse
     */
    public HttpServletResponse getHttpServletResponse()
    {
        return response;
    }

    /**
     Get the HTTPServletRequest
     */
    public HttpServletRequest getHttpServletRequest()
    {
        return request;
    }

    public ServletContext getServletContext(){

        return servletContext;
    }

    /**
     Get a HTTPServletRequest Parameter
     */
    public String getReqParm(String asParmName)
    {
        String parmValue = null;

        if (reqParms.containsKey(asParmName))
        {
            parmValue = (String) reqParms.get(asParmName);
        }
        else
        {
            parmValue = request.getParameter(asParmName);
        }

        return parmValue;
    }

    public void setReqParm(String parmName, String parmValue)
    {
        reqParms.put(parmName, parmValue);
    }

    /**
     Get the HTTPSession
     */
    public HttpSession getHttpSession()
    {
        return session;
    }

    public void addSessionBean(String key, SessionBean bean) {

        session.setAttribute(key, bean);
    }

    public SessionBean getSessionBean(String key) {

        return (SessionBean) session.getAttribute(key);
    }

//    public void addWebService(String componentName, Object component){
//
//        session.setAttribute(componentName, component);
//    }

    public Object getWebService(String componentName){

        Object service = null;

        if (componentName.equals("client-service"))
        {
            service =  new client.component.ClientComponent();
        }

        else if (componentName.equals("client-lookup"))
        {
            service = new client.component.LookupComponent();
        }

        else if (componentName.equals("codetable-service"))
        {
            service = new codetable.component.CodeTableComponent();
        }

        else if (componentName.equals("contract-service"))
        {
            service = new contract.component.ContractComponent();
        }

        else if (componentName.equals("contract-lookup"))
        {
            service = new contract.component.LookupComponent();
        }

        else if (componentName.equals("role-service"))
        {
            service = new role.component.RoleComponent();
        }

        else if (componentName.equals("role-lookup"))
        {
            service = new role.component.LookupComponent();
        }

        else if (componentName.equals("engine-service"))
        {
            service = new engine.component.CalculatorComponent();
        }

        else if (componentName.equals("engine-lookup"))
        {
            service = new engine.component.LookupComponent();
        }

        else if (componentName.equals("accounting-lookup"))
        {
            service = new accounting.component.LookupComponent();
        }

        else if (componentName.equals("accounting-service"))
        {
            service = new accounting.component.AccountingComponent();
        }

        else if (componentName.equals("reporting-service"))
        {
            service = new reporting.component.ReportingComponent();
        }

        else if (componentName.equals("reportingadmin-service"))
        {
            service = new reportingadmin.component.ReportingAdminComponent();
        }

        return service;
    }

    public UserSession getUserSession()
    {
        return (UserSession) session.getAttribute("userSession");
    }

    /**
     * Convenenience method to return an instance of a "cloud", if one exists.
     * @param cloudClass
     * @return
     */
    public Cloud getCloud(Class cloudClass)
    {
        return getUserSession().getCloudland().getCloud(reinsurance.ui.ReinsuranceCloud.class);
    }

    public void addWebService(String url, String type, String urn, String location) throws Exception {

//        if (location.equals("remote")) {
//
//            session.setAttribute(urn, Registry.bind(url, Class.forName(type)));
//        }
//        else if (location.equals("local")) {
//
//            session.setAttribute(urn, Registry.bind(urn, Class.forName(type)));
//        }
    }

    /**
     * Convenience method for HttpServletRequest.setAttribute(name, value)
     * @param name
     * @param value
     */
    public void putInRequestScope(String name, Object value)
    {
        request.setAttribute(name, value);
    }

    /**
     * Convenience method for HttpSession.setAttribute(name, value)
     * @param name
     * @param value
     */
    public void putInSessionScope(String name, Object value)
    {
        session.setAttribute(name, value);        
    }

    /**
     * Convenience method for HttpServletRequest.getAttribute(name)
     * @param name
     */
    public Object getFromRequestScope(String name)
    {
        return request.getAttribute(name);
    }

    /**
     * Convenience method for HttpSession.getAttribute(name)
     * @param name
     */
    public Object getFromSessionScope(String name)
    {
        return session.getAttribute(name);
    }
    
    /**
     * Defaults every Hibernate null-valued attribute (those as defined by getters and
     * setters) to a dummy/empty value. For example, a String would default to "".
     * This is necessary when the "input-tags" wants to render the request parameters
     * of a page A onto page B just because they happen you share the same bean class, 
     * but not the same bean instance.
     * @param hibernateEntity
     */
    public void defaultValues(HibernateEntity hibernateEntity, String targetDB)
    {
        ClassMetadata metadata = SessionHelper.getClassMetadata(hibernateEntity.getClass(), targetDB);        
        
        EntityMode entityMode = SessionHelper.getSession(targetDB).getEntityMode();
        
        String[] propertyNames = metadata.getPropertyNames();
        
        for (String propertyName:propertyNames)
        {
            Type propertyType = metadata.getPropertyType(propertyName);
            
            if (isSimpleType(propertyType))
            {
                Object propertyValue = metadata.getPropertyValue(hibernateEntity, propertyName, entityMode);

                if (propertyValue == null)
                {
                    metadata.setPropertyValue(hibernateEntity, propertyName, "", entityMode);
                }
            }
        }
    }
    
    /**
     * True if the specified type is that of:
     * Long, long
     * Integer, int
     * String string
     * @param type
     * @return
     */
    private boolean isSimpleType(Type type)
    {
        boolean isSimpleType = false;
        
        if (type instanceof StringType)
        {
            isSimpleType = true;
        }
        
        return isSimpleType;
    }
}