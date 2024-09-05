package edit.common.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 4, 2003
 * Time: 4:07:38 PM
 * To change this template use Options | File Templates.
 */


public class VOEditException extends Exception
{
    private String[]   VOErrors;
    private String     returnPage;
    private String     transaction;
    private String     action;

    public VOEditException(){

    }

   public void setVOErrors(String[] VOErrors){

        this.VOErrors = VOErrors;
    }

    public String[] getVOErrors(){

        return VOErrors;
    }

   public void setReturnPage(String returnPage){

        this.returnPage = returnPage;
    }

    public String getReturnPage(){

        return returnPage;
    }

   public void setTransaction(String transaction){

        this.transaction = transaction;
    }

    public String getTransaction(){

        return transaction;
    }

    public void setAction(String action){

        this.action = action;
    }

    public String getAction(){

        return action;
    }


    // MAIN - used only for unit testing
//    public static void main(String[] args)
//    {
//        EDITValidationException eve = new EDITValidationException("Hello");
//
//        EDITValidationException eve2 = new EDITValidationException("Number 2");
//        eve.setNextException(eve2);
//
//        EDITValidationException eve3 = new EDITValidationException("Number 3");
//        eve2.setNextException(eve3);
//
//        System.out.println("eve = " + eve.getMessage());
//        System.out.println("eve2 = " + eve.getNextException().getMessage());
//        System.out.println("eve3 = " + eve2.getNextException().getMessage());
//
//        System.out.println(eve.toString());
//    }
}
