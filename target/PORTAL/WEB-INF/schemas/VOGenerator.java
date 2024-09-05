package edit.common.schemas;

import org.exolab.castor.builder.SourceGenerator;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: sdorman
 * Date: Mar 6, 2003
 * Time: 11:54:38 AM
 * To change this template use Options | File Templates.
 */
public class VOGenerator
{
    //  This program will run Castor's SourceGenerator on all schema files in a specified directory. SourceGenerator will
    //  then create all necessary VOs (and their descriptors) and store them in the specified destination directory.

    //  To run this program with arguments, use the following example, otherwise, accept the defaults below
    //  -i C:\FISSION\edit\common\schemas -dest C:\FISSION -ext xsd -verbose -f
    //      where:
    //      -i      inputPath       specifies the input path for the input schema files
    //      -dest   destPath        specifies the destination directory for the VO and Descriptor files
    //      -ext    extension       specifies the file extension for the schema files
    //      -verbose                turns messages on
    //      -f                      suppresses non-fatal warning messages

    //  If run without arguments, the following defaults are used
    private static String  SCHEMA_PATH       = "C:\\Projects\\JDeveloper\\EDITSolutions\\VisionDevelopment\\src\\edit\\common\\schemas";
    private static String  FILE_EXTENSION    = "xsd";
    private static String  DESTINATION_DIR   = "C:\\Projects\\NetBeans\\EDITSolutions\\Vision\\src";
    private static boolean VERBOSE           = true;
    private static boolean SUPPRESS_WARNINGS = true;
    private static String PACKAGE            = "edit.common.vo";
    
    // This overrides the processing of all files in the specified schema directory.
    private static String SCHEMA_FILE        = "C:\\Projects\\NetBeans\\EDITSolutions\\Vision\\src\\edit\\common\\schemas\\EDITSolutions.xsd";

    private static void processArgs(String[] args)
    {
        //  If arguments are specified at runtime, user must specifically ask for verbose and warning suppression to
        //  be turned on
        if (args.length > 0)
        {
            VERBOSE = false;
            SUPPRESS_WARNINGS = false;
        }

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equalsIgnoreCase("-verbose"))
            {
                VERBOSE = true;
            }
            if (args[i].equalsIgnoreCase("-f"))
            {
                SUPPRESS_WARNINGS = true;
            }
            if (args[i].equalsIgnoreCase("-ext"))
            {
                FILE_EXTENSION = args[i+1];
            }
            if (args[i].equalsIgnoreCase("-i"))
            {
                SCHEMA_PATH = args[i+1];
            }
            if (args[i].equalsIgnoreCase("-dest"))
            {
                DESTINATION_DIR = args[i+1];
            }
            if (args[i].equalsIgnoreCase("-package"))
            {
                PACKAGE = args[i+1];
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length > 0)
        {
            processArgs(args);
        }

        System.out.println("Using the following specifications:");
        System.out.println("SCHEMA_PATH = " + SCHEMA_PATH);
        System.out.println("DESTINATION_DIR = " + DESTINATION_DIR);
        System.out.println("FILE_EXTENSION = " + FILE_EXTENSION);
        System.out.println("VERBOSE = " + VERBOSE);
        System.out.println("SUPPRESS_WARNINGS = " + SUPPRESS_WARNINGS);
        System.out.println("PACKAGE = " + PACKAGE);


        File dir = new File(SCHEMA_PATH);

        File[] allFiles = null;
        
        if (SCHEMA_FILE == null)
        {
            allFiles = dir.listFiles();
        }
        else
        {
            allFiles = new File[]{new File(SCHEMA_FILE)};
        }   

        for (int i = 0; i < allFiles.length; i++)
        {
            String absolutePath = allFiles[i].getAbsolutePath();

            if (absolutePath.endsWith(FILE_EXTENSION))
            {
                SourceGenerator sg = new SourceGenerator();

                System.out.println("------->" + absolutePath);

                sg.setVerbose(VERBOSE);
                sg.setDestDir(DESTINATION_DIR);
                sg.setSuppressNonFatalWarnings(SUPPRESS_WARNINGS);
                sg.generateSource(absolutePath, PACKAGE);
            }
        }
    }
}




