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
public class ActiveMIFMembersGenerator extends ConversionAudit
{
    private static String FILE_NAME = "ActiveMIFMembers";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "ContractNumber" + DEMARCATOR + "InsuredName" ;

    private File file = null;

    public ActiveMIFMembersGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select segment.ContractNumber, clientDetail.FirstName, clientDetail.LastName, clientDetail.CorporateName" +
                     " from Segment segment" +
                     " join segment.ContractClients contractClient" +
                     " join contractClient.ClientRole clientRole" +
                     " join clientRole.ClientDetail clientDetail" +
                     " where segment.SegmentStatusCT = 'Active'" +
                     " and clientRole.RoleTypeCT = 'Insured'";

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

                String contractNumber = Util.initString((String) columnValues[0], "");

                String firstName = (String) columnValues[1];

                String lastName = (String) columnValues[2];

                String corporateName = (String) columnValues[3];

                String insuredName = Util.initString(corporateName == null ? firstName + "," + lastName : corporateName, "");

                String aLine = contractNumber + DEMARCATOR + insuredName;

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