/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 2:15:52 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.interfaces;

import edit.common.vo.EDITExport;
import edit.services.config.ServicesConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractInterface {

    protected static String exportDirectory1 = null;
    protected static String exportDirectory2 = null;

    static {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");
        EDITExport export2 = ServicesConfig.getEDITExport("ExportDirectory2");

        exportDirectory1 = export1.getDirectory();
        exportDirectory2 = export2.getDirectory();
    }

    public void exportData2x(String fileData, String fileName) throws Exception {

        try {

            FileWriter fw1 = new FileWriter(exportDirectory1 + fileName);
            BufferedWriter fileWriter1 = new BufferedWriter(fw1);
            fileWriter1.write(fileData);
            fileWriter1.close();
            fw1.close();

            FileWriter fw2 = new FileWriter(exportDirectory2 + fileName);
            BufferedWriter fileWriter2 = new BufferedWriter(fw2);
            fileWriter2.write(fileData);
            fileWriter2.close();
            fw2.close();
        }

        catch (IOException e) {

          System.out.println(e);

            e.printStackTrace();

            throw e;
        }
    }

    public void exportData(String fileData, String fileName) {

        try {

            FileWriter fw = new FileWriter(exportDirectory1 + fileName);
            BufferedWriter fileWriter1 = new BufferedWriter(new FileWriter(exportDirectory1 + fileName));
            fileWriter1.write(fileData);
            fileWriter1.close();
            fw.close();
        }

        catch (IOException e) {

           System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}
