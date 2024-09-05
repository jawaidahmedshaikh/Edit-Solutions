/*
 * User: gfrosti
 * Date: Feb 7, 2005
 * Time: 2:01:42 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db.hibernate;

import edit.common.vo.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.*;

import java.util.*;

public class DBSessionFactory
{
	private static Logger logger = LogManager.getLogger(DBSessionFactory.class);
    private static edit.services.db.hibernate.DBSessionFactory thisDBSessionFactory;
    public static final String EDITSOLUTIONS = "EDITSOLUTIONS";
    public static final String PRASE = "PRASE";
    public static final String SECURITY = "SECURITY";
    public static final String STAGING = "STAGING";
    public static final String DATAWAREHOUSE = "DATAWAREHOUSE";
    public static final String MISCELLANEOUS = "Miscellaneous";
    public static final String PRD = "PRD";
    public static final String ODS = "ODS";
    public static final String CONTROL = "CONTROL";
    private Map<String, SessionFactory> hibernateSessionFactories;
    private Map<String, Configuration> hibernateSessionConfigurations;

    private DBSessionFactory()
    {
        hibernateSessionFactories = new HashMap<String, SessionFactory>();

        hibernateSessionConfigurations = new HashMap<String, Configuration>();

        buildSessionFactories();
    }

    /**
     * Singleton.
     *
     * @return
     */
    public static DBSessionFactory getInstance()
    {
        if (thisDBSessionFactory == null)
        {
            thisDBSessionFactory = new edit.services.db.hibernate.DBSessionFactory();
        }

        return thisDBSessionFactory;
    }
/*
    public static DBSessionFactory getInstance(EDITService[] editServices)
    {
        thisDBSessionFactory = getInstance();
        
        thisDBSessionFactory.initializeHibernateFromConfigXML();        
        
        return thisDBSessionFactory;
    }

*/
    
    /**
     * The Hibernate Session singleton.
     *
     * @return
     */
    public Session getSession(String targetDB)
    {
        Session session = null;

        try
        {
            SessionFactory hibernateSessionFactory = (SessionFactory) hibernateSessionFactories.get(targetDB);

            if (hibernateSessionFactory != null) 
            {
                session = hibernateSessionFactory.openSession(new HibernateEntityDifferenceInterceptor());

                session.setFlushMode(FlushMode.COMMIT);
            }
        }
        catch (HibernateException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return session;
    }

    /**
     * StatelessSessions allow for DB to Hibernate mappings that work outside of the Hibernate Session or Caching. This
     * is useful to bypass much of the Hibernate abstraction and have a more direct transfer between the DB and Hibernate.
     * @param targetDB
     * @return StatelessSession
     */
    public StatelessSession getStatelessSession(String targetDB)
    {
        StatelessSession statelessSession = null;

        try
        {
            SessionFactory hibernateSessionFactory = (SessionFactory) hibernateSessionFactories.get(targetDB);

            statelessSession = hibernateSessionFactory.openStatelessSession();
        }
        catch (HibernateException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return statelessSession;
    }

    /**
     * DB properties.
     *
     */
    private void buildSessionFactories()
    {
        try
        {            
            initializeHibernateFromConfigXML();
        }
        catch (HibernateException e)
        {
        	System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    private void initializeHibernateFromConfigXML() {
    	Configuration secCfg = new Configuration().configure("/security.cfg.xml");
    	SessionFactory secSessionFactory = secCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(SECURITY, secCfg);
    	hibernateSessionFactories.put(SECURITY, secSessionFactory);
    	logger.info("\t==> " + SECURITY + " SessionFactory initialized");
    	
    	Configuration esCfg = new Configuration().configure("/editsolutions.cfg.xml");
    	SessionFactory esSessionFactory = esCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(EDITSOLUTIONS, esCfg);
    	hibernateSessionFactories.put(EDITSOLUTIONS, esSessionFactory);
    	logger.info("\t==> " + EDITSOLUTIONS + " SessionFactory initialized");
    	
    	Configuration praseCfg = new Configuration().configure("/prase.cfg.xml");
    	SessionFactory praseSessionFactory = praseCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(PRASE, praseCfg);
    	hibernateSessionFactories.put(PRASE, praseSessionFactory);
    	logger.info("\t==> " + PRASE + " SessionFactory initialized");
    	
    	Configuration dataCfg = new Configuration().configure("/datawarehouse.cfg.xml");
    	SessionFactory dataSessionFactory = dataCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(DATAWAREHOUSE, dataCfg);
    	hibernateSessionFactories.put(DATAWAREHOUSE, dataSessionFactory);
    	logger.info("\t==> " + DATAWAREHOUSE + " SessionFactory initialized");
    	
    	Configuration stageCfg = new Configuration().configure("/staging.cfg.xml");
    	SessionFactory stageSessionFactory = stageCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(STAGING, stageCfg);
    	hibernateSessionFactories.put(STAGING, stageSessionFactory);
    	logger.info("\t==> " + STAGING + " SessionFactory initialized");

    	Configuration prdCfg = new Configuration().configure("/prd.cfg.xml");
    	SessionFactory prdSessionFactory = prdCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(PRD, prdCfg);
    	hibernateSessionFactories.put(PRD, prdSessionFactory);
    	logger.info("\t==> " + PRD + " SessionFactory initialized");

    	Configuration odsCfg = new Configuration().configure("/ods.cfg.xml");
    	SessionFactory odsSessionFactory = odsCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(ODS, odsCfg);
    	hibernateSessionFactories.put(ODS, odsSessionFactory);
    	logger.info("\t==> " + ODS + " SessionFactory initialized");

    	Configuration controlCfg = new Configuration().configure("/control.cfg.xml");
    	SessionFactory controlSessionFactory = controlCfg.buildSessionFactory();
    	hibernateSessionConfigurations.put(CONTROL, controlCfg);
    	hibernateSessionFactories.put(CONTROL, controlSessionFactory);
    	logger.info("\t==> " + CONTROL + " SessionFactory initialized");
    }

    /**
     * The Configuration used to generate the Sessions for the application.
     *
     * @return
     */
    public Configuration getConfiguration(String targetDB)
    {
        return (Configuration) hibernateSessionConfigurations.get(targetDB);
    }
    
    /**
   * The associated SessionFactory for the supplied targetDB.
   * @param targetDB
   * @return
   */
    public SessionFactory getSessionFactory(String targetDB)
    {
    	return (SessionFactory) hibernateSessionFactories.get(targetDB);
    }
}
