/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conversion.audit;

import java.util.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import fission.utility.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.hibernate.Session;

/**
 *
 * @author sprasad
 */
public class NameAddressRoleGenerator extends ConversionAudit
{
    private static String FILE_NAME = "NameAddressRole";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "FirstName" + DEMARCATOR + "LastName" + DEMARCATOR + "CorporateName" + DEMARCATOR + "RoleName" + DEMARCATOR +
                                              "AddressLine1" + DEMARCATOR + "AddressLine2" + DEMARCATOR + "AddressLine3" + DEMARCATOR + "AddressLine4" + DEMARCATOR + 
                                              "City" + DEMARCATOR + "State" + DEMARCATOR + "ZipCode" + DEMARCATOR + "Country" + DEMARCATOR + "ContractNumber";

    private File file = null;

    public NameAddressRoleGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select clientDetail.FirstName, clientDetail.LastName, clientDetail.CorporateName, clientRole.RoleTypeCT," +
                     " clientAddress.AddressLine1, clientAddress.AddressLine2, clientAddress.AddressLine3, clientAddress.AddressLine4," +
                     " clientAddress.City, clientAddress.StateCT, clientAddress.ZipCode, clientAddress.CountryCT, segment.ContractNumber" +
                     " from ClientDetail clientDetail" +
                     " join clientDetail.ClientRoles clientRole" +
                     " join clientDetail.ClientAddresses clientAddress" +
                     " join clientRole.ContractClients contractClient" +
                     " join contractClient.Segment segment";

        Session session = null;

        int count = 0;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List results = SessionHelper.executeHQL(session, hql, null, -1);

            FileWriter fw = new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(FILE_HEADER);

            bw.newLine();

            for (int i = 0; i < results.size(); i++)
            {
                Object[] columnValues = (Object[]) results.get(i);

                String firstName = Util.initString((String) columnValues[0], "");

                String lastName = Util.initString((String) columnValues[1], "");

                String corporateName = Util.initString((String) columnValues[2], "");

                String roleTypeCT = Util.initString((String) columnValues[3], "");

                String addressLine1 = Util.initString((String) columnValues[4], "");

                String addressLine2 = Util.initString((String) columnValues[5], "");

                String addressLine3 = Util.initString((String) columnValues[6], "");

                String addressLine4 = Util.initString((String) columnValues[7], "");

                String city = Util.initString((String) columnValues[8], "");

                String stateCT = Util.initString((String) columnValues[9], "");

                String zipCode = Util.initString((String) columnValues[10], "");

                String countryCT = Util.initString((String) columnValues[11], "");

                String contractNumber = Util.initString((String) columnValues[12], "");

                String aLine = firstName + DEMARCATOR + lastName + DEMARCATOR + corporateName + DEMARCATOR + roleTypeCT + DEMARCATOR + 
                               addressLine1 + DEMARCATOR + addressLine2 + DEMARCATOR + addressLine3 + DEMARCATOR + addressLine4 + DEMARCATOR +
                               city + DEMARCATOR + stateCT + DEMARCATOR + zipCode + DEMARCATOR + countryCT + DEMARCATOR + contractNumber;

                bw.write(aLine);

                bw.newLine();

                count += 1;
            }

            bw.write("TOTAL: " + count);

            bw.close();

            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (session != null) session.close();
        }
    }
}