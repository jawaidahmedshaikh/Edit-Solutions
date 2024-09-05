/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extension.batch;

/**
 * The launching class for the BatchAgentProxy application.
 * 
 * This allows users to kick-off Batch Processes via HTTP 
 * requests. 
 * @author gfrosti
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if (shouldPrintTestMessage(args))
        {
            printTestMessage();
        }
        else if (shouldPrintHelpMenu(args))
        {
            printHelpMenu();
        }
        else
        {
            HttpBatchRequest request = new HttpBatchRequest(args);

            String batchResponse = request.executeHttpRequest();

            // Send response to std-out.
            System.out.println(batchResponse);
        }
    }

    /**
     * Prints usage and help information.
     */
    private static void printHelpMenu()
    {
        System.out.println(" *****************************************************************************");
        System.out.println(" *                                                                           *");
        System.out.println(" *                            -- HELP MENU --                                *");
        System.out.println(" *                                                                           *");
        System.out.println(" * BASIC OPERATION:                                                          *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   To launch a Batch Process, the basic syntax is as follows:              *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   c:>BatchAgentProxy.bat [machineName] [processName] [p1] [2] ... [pn]    *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   The [machineName] and [processName] are always required and must be in  *");
        System.out.println(" *   that order.                                                             *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   The [p1] ... [pn] may or may not be required depending on the           *");
        System.out.println(" *   [processName].                                                          *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   The [processName] [pn] requirements for each process are defined in the *");
        System.out.println(" *   SchedulingDocumentation.doc.                                            *");
        System.out.println(" *                                                                           *");
        System.out.println(" *                                                                           *");
        System.out.println(" * RESPONSE CODES:                                                           *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   There are four possible response codes. They are:                       *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   1) PROCESSED_WITHOUT_ERRORS                                             *");
        System.out.println(" *   2) PROCESSED_WITH_ERRORS                                                *");
        System.out.println(" *   3) PROCESSED_BUT_NO_ELEMENTS_FOUND                                      *");
        System.out.println(" *   4) UNEXPECTED_ERROR                                                     *");
        System.out.println(" *                                                                           *");
        System.out.println(" *                                                                           *");
        System.out.println(" * EXAMPLE:                                                                  *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   To launch the Daily Batch process, the command would look as follows:   *");
        System.out.println(" *                                                                           *");
        System.out.println(" *   c:>BatchAgentProxy.bat machine001 createListBillExtract 2008/03/14      *");
        System.out.println(" *                         fooUsername fooPassword SYNCHRONOUS               *");
        System.out.println(" *                                                                           *");
        System.out.println(" *****************************************************************************");
    }

    /**
     * Confirms basic setup is done correctly.
     */
    private static void printTestMessage()
    {
        System.out.println(" *****************************************************************************");
        System.out.println(" *                                                                           *");
        System.out.println(" *  TEST - BatchAgentProxy is properly configured.                           *");
        System.out.println(" *                                                                           *");
        System.out.println(" *****************************************************************************");
    }

    /**
     * True if the user has supplied invalid args or specifically requested the Help menu.
     * @param args
     * @return
     */
    private static boolean shouldPrintHelpMenu(String[] args)
    {
        return ((args == null) || (args.length == 0) || (args[0].equalsIgnoreCase("HELP")));
    }

    /**
     * True if the user has requested a TEST message to validate basic setup.
     * @param args
     * @return
     */
    private static boolean shouldPrintTestMessage(String[] args)
    {
        return ((args != null) && (args.length > 0) && (args[0].equalsIgnoreCase("TEST")));
    }
}
