/*
 * User: sdorman
 * Date: Dec 2, 2004
 * Time: 9:29:17 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.common.ui;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Utility class to read and write an Excel file via Jakarta's POI-HSSF API.
 * <P>
 * Can also display the contents via the display methods. Access to individual sheets, rows, and cells is done
 * outside of this class.
 */
public class ExcelUtil
{
    /**
     * Reads an Excel Workbook from the input stream
     *
     * @param inputStream          input stream of workbook contents
     *
     * @return  The Workbook
     *
     * @throws Exception
     */
    public static HSSFWorkbook readWorkbook(InputStream inputStream) throws Exception
    {
        POIFSFileSystem fs = new POIFSFileSystem(inputStream);

        HSSFWorkbook wb = new HSSFWorkbook(fs);

        return wb;
    }

    /**
     * Reads an Excel Workbook from the file system.
     *
     * @param fileName          absolute path to the file
     *
     * @return  The Workbook
     *
     * @throws Exception
     */
    public static HSSFWorkbook readWorkbook(String fileName) throws Exception
    {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));

        HSSFWorkbook wb = new HSSFWorkbook(fs);

        return wb;
    }

    /**
     * Writes an Excel file to the file system
     *
     * @param wb                workbook to be written
     * @param fileName          absolute path to the file to be written
     *
     * @throws Exception
     */
    public static void writeWorkbook(HSSFWorkbook wb, String fileName) throws Exception
    {
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);

        wb.write(fileOut);

        fileOut.close();
    }

    /**
     * Dumps the contents of the workbook to the screen
     *
     * @param wb        workbook to be displayed
     */
    public static void displayWorkbook(HSSFWorkbook wb)
    {
        int numberOfSheets = wb.getNumberOfSheets();

        System.out.println("numberOfSheets = " + numberOfSheets);

        HSSFSheet sheet = null;

        for (int i = 0; i < numberOfSheets; i++)
        {
            sheet = wb.getSheetAt(i);

            if (sheet != null)
            {
                System.out.println("Worksheet #" + i);

                displayRows(sheet);
            }
        }
    }

    /**
     * Dumps the contents of all the rows within the specified sheet to the screen
     *
     * @param sheet         sheet to be displayed
     */
    public static void displayRows(HSSFSheet sheet)
    {
        // Iterate over each row in the sheet
        Iterator rows = sheet.rowIterator();

        while( rows.hasNext() )
        {
            HSSFRow row = (HSSFRow) rows.next();

            System.out.println( "Row #" + row.getRowNum() );

            displayCells(row);
        }
    }

    /**
     * Dumps the contents of all the cells within the specified row to the screen
     *
     * @param row           row to be displayed
     */
    public static void displayCells(HSSFRow row)
    {
        // Iterate over each cell in the row and print out the cell's content
        Iterator cells = row.cellIterator();

        while (cells.hasNext())
        {
            HSSFCell cell = (HSSFCell) cells.next();

            System.out.print( "Cell(" + cell.getCellNum() + ", " + row.getRowNum() + ") = " );

            switch (cell.getCellType())
            {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    System.out.println(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    System.out.println(cell.getStringCellValue());
                    break;
                default:
                    System.out.println("unsupported cell type");
                    break;
            }
        }
    }


    //  MAIN - For unit testing only
    public static void main(String[] args) throws Exception
    {
        String fileName = "C:\\JavaToExcelTest\\Proposed Revised Census Merrin AK 3-20-03.xls";

        HSSFWorkbook wb = ExcelUtil.readWorkbook(fileName);

        ExcelUtil.displayWorkbook(wb);
    }
}
