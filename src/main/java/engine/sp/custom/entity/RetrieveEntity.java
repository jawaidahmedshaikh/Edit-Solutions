package engine.sp.custom.entity;

import edit.common.EDITDate;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import engine.sp.Activateentity;
import engine.sp.SPException;
import engine.sp.ScriptProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.hibernate.Session;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.type.Type;

/**
 * Scripters may want to query the underlying database in a generic way.
 * 
 * The most obvious candidate for a query language would by SQL due to its ubiquity.
 * However, we have solidified on HQL and the new EJB 3 (should we ever use it)
 * is largely based on HQL as well. Additionally, HQL is simply more intuitive than SQL.
 * 
 * When the hql is executed, the results are to be made available within a standard
 * document; specifically the ResultDocVO. The scripter can then access the document
 * to access and manipulate the results.
 * 
 * The syntax is as follows:
 * 
 * Activateentity (Retrieve, from Foo foo where foo.Bar = :value, FOODATABASE)
 * 
 * Acceptable databases are those that are configured in our Hibernate configuration as
 * constants in the SessionHelper class.
 * 
 * The 'value' of :value is expected to be in WS.
 * 
 * @see SessionHelper
 */
public class RetrieveEntity extends ActivateEntityCommand
{

    public RetrieveEntity()
    {
    }

    public void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException
    {
        String hql = parameterTokens[1]; // according to the syntax.
        
        String database = parameterTokens[2]; // according to the syntax
        
        Session session = null;
        
        try
        {
            session = SessionHelper.getSeparateSession(database);
            
            Map parameters = buildNamedParameters(sp, hql, session);

            hql = parseForNull(parameters, hql);
            
            List<HibernateEntity> results = SessionHelper.executeHQL(session, hql, parameters, 0);
            
            mapResultsToResultDocVO(results, sp, database);            
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }
    
    /**
     * Parses the specified hql and looks for all named parameters. 
     * It then matches the named parameters with values placed withing WS and then
     * converts the obtained Strings to their proper data type using Hibernate meta data.
     * @param sp
     * @param hql
     * @return a Map populated with the WS entries matching the names of the named parameters within the hql
     */
    private Map buildNamedParameters(ScriptProcessor sp, String hql, Session session) throws SPException
    {
        Map namedParameters = new HashMap();

        try
        {
            SessionFactoryImpl factoryImpl = (SessionFactoryImpl) session.getSessionFactory();
            
            QueryTranslatorImpl q = new QueryTranslatorImpl("foo", hql, null, factoryImpl);
            
            q.compile(new HashMap(), false);
            
            ParameterTranslations p = q.getParameterTranslations();
            
            for (Object namedParameterName: p.getNamedParameterNames())
            {
                String namedParameterStringValue = sp.getWSEntry(namedParameterName.toString());

                Type namedParameterType = p.getNamedParameterExpectedType(namedParameterName.toString());

                Class returnedClass = namedParameterType.getReturnedClass();

                Constructor stringBasedConstructor = returnedClass.getConstructor(new Class[]
                        { String.class });

                Object namedParameterValue = stringBasedConstructor.newInstance(new Object[]
                        { namedParameterStringValue });

                namedParameters.put(namedParameterName, namedParameterValue);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new SPException("Unable to map HQL named parameters in order to execute query.", SPException.INSTRUCTION_PROCESSING_ERROR, e);
        }
        
        return namedParameters;
    }

    /**
     * Iterates the specified List placing each entity found as a DOM4J Element in the
     * ResultDocVO document.
     * @param results
     */
    private void mapResultsToResultDocVO(List<HibernateEntity> results, ScriptProcessor sp, String targetDB)
    {
        for (HibernateEntity hibernateEntity:results)
        {
            Element hibernateEntityElement = SessionHelper.mapToElement(hibernateEntity, targetDB, false, false);
            
            sp.getResultDocVO().getRootElement().add(hibernateEntityElement);
        }
    }

    /**
     * Some parameters values may come in as #NULL. This is a reserved word in our
     * system and implies that the named parameter has a value of null in the DB.
     * In this case, we need to rewrite the hql from "fooName := fooValue" to "fooName is null".
     * I was hoping that hql had some way to automatically identify null values and dynamically
     * re-write the hql; I could find no such thing in the Hibernate API.
     * @param parameters
     * @param hql
     * @return
     */
    private String parseForNull(Map<String, Object> parameters, String hql)
    {
        List<String> parametersToRemove = null;

        for (String parameterName:parameters.keySet())
        {
            String parameterValue = parameters.get(parameterName).toString();

            if (parameterValue.toString().equals(Constants.ScriptKeyword.NULL))
            {
                // Look for "= :fooNamedParameter" and make it "is null".
                String re = "= *?:\\b" + parameterName + "\\b";

                Pattern p = Pattern.compile(re);

                Matcher m = p.matcher(hql);

                if (m.find())
                {
                    hql = m.replaceFirst(" is null");

                    if (parametersToRemove == null)
                    {
                        parametersToRemove = new ArrayList<String>();
                    }

                    parametersToRemove.add(parameterName);
                }
            }
        }

        // Remove the #NULL valued named parameters since we just parsed them out of the hql.
        if (parametersToRemove != null)
        {
            for (String parameterToRemove:parametersToRemove)
            {
                parameters.remove(parameterToRemove);
            }
        }

        return hql;
    }
}
