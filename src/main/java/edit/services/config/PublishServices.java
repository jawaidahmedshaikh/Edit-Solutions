/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 12, 2002
 * Time: 6:10:20 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.config;

//import edit.services.db.ConnectionPool;
//import edit.services.db.ConnectionFactory;
//
//import java.util.Properties;
//import java.util.Enumeration;
//import java.io.FileReader;
//import java.net.InetAddress;
//
//import electric.registry.Registry;
//import electric.util.Context;
//import electric.server.http.*;
//import test.glueserver.*;

public class PublishServices {

//    public static void publish(String pathToServicesConfig) throws Exception {
//
//        FileReader fileReader = new FileReader(pathToServicesConfig);
//
//        ConfigServicesMain configMain = ConfigServicesMain.unmarshal(fileReader);
//
//        ConfigServicesWebservice[] configWebservices = configMain.getConfigServicesWebservice();
//
//        ConfigServicesJdbc[] configServicesJdbc = configMain.getConfigServicesJdbc();
//
//        System.out.println("*************************************************");
//
//        // Publish WebServices
//        if (configWebservices != null) {
//
//            System.out.println("START publishing services...");
//
//            for (int i = 0; i < configWebservices.length; i++){
//
//                String urn      = configWebservices[i].getUrn();
//                String object   = configWebservices[i].getObject();
//                String type     = configWebservices[i].getType();
//                String scope    = configWebservices[i].getScope();
//
//                Context context = new Context();
//                context.addProperty( "activation", scope );
//
//                Registry.publish(urn,
//                                  Class.forName(object).newInstance(),
//                                   Class.forName(type),
//                                    context);
//
//                // **** START Display Purposes Only ****
//                System.out.print((i + 1) + ". ");
//                System.out.println("Service...");
//                System.out.println("\t urn: " + urn);
//                System.out.println("\t object: " + object);
//                System.out.println("\t type: " + type);
//                System.out.println("\t scope: " + scope);
//                System.out.println("\t endpoint: " + "http://host:port/" + urn + ".wsdl");
//                System.out.println("\n");
//                // **** END Display Purposes Only ****
//            }
//
//            System.out.println("END publishing services!!!");
//        }
//
//        System.out.println("\n");
//
//        // Load Connection Pools
//        if (configServicesJdbc != null){
//
//            System.out.println("START loading connection pools...");
//
//            for (int i = 0; i < configServicesJdbc.length; i++){
//
//                // Create Connection Pools
//                ConnectionFactory.addConnectionPool(new ConnectionPool(configServicesJdbc[i]));
//
//                // **** START Display Purposes Only ****
//                System.out.print((i + 1) + ". ");
//                System.out.println("JDBC...");
//                System.out.println("\t driver: " + configServicesJdbc[i].getDriver());
//                System.out.println("\t url: " + configServicesJdbc[i].getUrl());
//                System.out.println("\t pool-name: " + configServicesJdbc[i].getPoolName());
//                System.out.println("\t pool-size: " + configServicesJdbc[i].getPoolSize());
//                System.out.println("\t username: *****");
//                System.out.println("\t password: *****");
//                System.out.println("\n");
//                // **** END Display Purposes Only ****
//            }
//
//            System.out.println("END loading connection pools...");
//        }
//
//
//        System.out.println("*************************************************");
//
//
//        System.out.println("Test publish the Exhange service");
//        Context context = new Context();
//        context.addProperty("activation", "application");
//
//        Registry.publish("exchange", Class.forName("test.glueserver.Exchange").newInstance(), Class.forName("test.glueserver.IExchange"), context);
//
//
//    }
//
//    public static void main(String[] args) throws Throwable{
//
//        PublishServices.publish("/jakarta-tomcat/glue/WEB-INF/ServicesConfig.xml");
//
//    }
}
