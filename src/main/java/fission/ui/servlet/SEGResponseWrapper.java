package fission.ui.servlet;

import edit.common.vo.ValidationVO;

import edit.services.db.hibernate.HibernateEntityDifferenceInterceptor;
import edit.services.db.hibernate.SessionHelper;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;

import java.util.List;


/**
 * In order to modify any response coming back from the servlet container, we
 * need to intervene the normal response via this SEGResponseWrapper.
 * 
 * This wrapper will have all responses written to it so that at the end, 
 * just before the final response is sent back to the client, we can
 * modify the response as we would modify any byte/String structure.
 */
public class SEGResponseWrapper extends HttpServletResponseWrapper
{
  /** the compressed output stream for the servlet response */
  private SEGServletOutputStream segServletOutputStream = null;

  /** the PrintWriter object to the compressed output stream */
  private PrintWriter printWriter = null;

  /**
   * This state variable holds the first stream object created.
   */
  private Object streamUsed = null;

  /**
   * The constructor performs the Decorator responsibility of storing
   * a reference to the object being decorated, in this case the HTTP
   * response object.
   */
  SEGResponseWrapper(HttpServletResponse resp)
  {
    super(resp);
  }


  /**
   * Provide access to a decorated servlet output stream.
   */
  public ServletOutputStream getOutputStream() throws IOException
  {
    return getSEGServletOutputStream();
  }

  /**
   *  Provide access to a decorated print writer.
   */
  public PrintWriter getWriter() throws IOException
  {
    return getPrintWriter();
  }
  
  /**
   * The conainer may want to write to the underlying stream as a
   * ServletOutputStream (we wrap it as a SEGServletOutputSream).
   * @return
   */
  private SEGServletOutputStream getSEGServletOutputStream()
  {
    if (segServletOutputStream == null)
    {
      segServletOutputStream = new SEGServletOutputStream();
    }
    
    return segServletOutputStream;
  }
  
  /**
   * The container may want to write to the underlying stream as a PrintWriter.
   * @return
   */
  private PrintWriter getPrintWriter()
  {
    if (printWriter == null)
    {
      printWriter = new PrintWriter(getSEGServletOutputStream());
    }
    
    return printWriter;
  }

  /**
   * Generates the set of Validation.Message(s) inside of an HTML Alert that will
   * be inlined in the current response.
   * 
   * @return the byte[] representation of the inlined Alert
   */
  private byte[] generateValidationAlert()
  {
    List<ValidationVO> validationVOs = getNonFinancialEdits();

    String alert = "<script type=\"text/javascript\">"; // begin the script

    alert += "alert(\""; // begin the alert

    for (ValidationVO validationVO: validationVOs)
    {
      alert += validationVO.getMessage() + " - " + validationVO.getStack() + "\\n"; // build the alert contents
    }

    alert += "\");"; // close the alert

    alert += "</script>"; // close the script

    return alert.getBytes();
  }

  /**
   * Commits the output stream unmodified to the calling client.
   * @throws IOException
   */
  public void renderAsNormal() throws IOException
  {
    ServletOutputStream out = getResponse().getOutputStream();

    out.write(segServletOutputStream.getData());

    out.flush();
  }
  
  /**
   * Having detected Non-Financial edits in ThreadLocal, the 
   * messages are rendered in the current output. This is accomplished
   * by remplacing the current stream's </body></html> with
   * something similar to <script>Alert(...)</script></body></html>.
   */
  public void renderNonFinancialEdits() throws IOException
  {
    String currentData = new String(segServletOutputStream.getData());

    segServletOutputStream.clear();

    int indexOfBody = currentData.lastIndexOf("</body>");

    byte[] upToBodyData = currentData.substring(0, indexOfBody).getBytes();

    byte[] validationAlert = generateValidationAlert();

    byte[] closingTags = "</body></html>".getBytes();

    segServletOutputStream.write(upToBodyData);
    
    segServletOutputStream.write(validationAlert);
    
    segServletOutputStream.write(closingTags);
    
    renderAsNormal();
  }  

  /**
   * True if:
   * 
   * 1. The Non Financial framework via the NonFinancialDifferenceInterceptor has placed
   * a list of ValidationVOs within the ThreadLocal of the calling client.
   * 
   * 2. The current page being rendered is of type text/html
   * 
   * 3. The current page contains the </body> tag lowercase.
   * @return
   */
  public boolean shouldRenderNonFinancialEdits() throws IOException
  {
    boolean shouldRenderNonFinancialEdits = false;

    if (nonFinancialEditsExist())
    {
      if (getResponse().getContentType().startsWith("text/html"))
      {
        String currentData = new String(getSEGServletOutputStream().getData());

        int indexOfBody = currentData.lastIndexOf("</body>");

        if (indexOfBody >= 0)
        {
          shouldRenderNonFinancialEdits = true;
        }
      }
    }

    return shouldRenderNonFinancialEdits;
  }
  
  /**
   * Closes the current response stream and all underlying
   * support streams.
   * @throws IOException
   */
  public void close() throws IOException
  {
    getResponse().getOutputStream().close();
    
    if (printWriter != null)
    {
      printWriter.close();
      
      printWriter = null;
    }
    
    if (segServletOutputStream != null) 
    {
      segServletOutputStream.close();
      
      segServletOutputStream = null;
    }
  }
  
  /**
     * Checks the current ThreadLocal to see if any hard-edits (via the Non-Financial Framework)
     * have been placed there.
     * @return true if hard-edits via the NF Framework have been found.
     */
  public static boolean nonFinancialEditsExist() 
  {
      return (SessionHelper.getFromThreadLocal(HibernateEntityDifferenceInterceptor.HARD_EDITS) != null);        
  }
  
  /**
     * Gets the List of ValidationVOs stored within ThreadLocal, if any.
     * @return the List of ValidationVOs within ThreadLocal, or null
     */
  public static List<ValidationVO> getNonFinancialEdits() 
  {
      return (List<ValidationVO>) SessionHelper.getFromThreadLocal(HibernateEntityDifferenceInterceptor.HARD_EDITS);      
  }
}
