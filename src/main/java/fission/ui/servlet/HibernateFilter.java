/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 20, 2005
 * Time: 4:09:36 PM
 * To change this template use File | Settings | File Templates.
 */
package fission.ui.servlet;

import edit.common.vo.ValidationVO;

import edit.portal.common.session.UserSession;

import edit.services.db.hibernate.*;

import org.hibernate.*;

import java.io.*;

import java.util.List;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.*;


/**
 * With the implementation decision to consolidate all logical layers of EDITSolutions into one physical layer, we
 * can capitalize on the unifification of the client layer to the business objects. In short, the application is no longer
 * dependant on the transportation of value objects between the layers.
 */
public class HibernateFilter implements Filter
{
  public void init(FilterConfig filterConfig) throws ServletException
  {
  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
  {
    boolean attemptingSecondaryRequest = false;

    HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession();

    Boolean userHasActiveRequest = (Boolean) httpSession.getAttribute("userHasActiveRequest");

    if (userHasActiveRequest == null) // Don't allow a second request to occur from a user until the first one has completed.
    {
      attemptingSecondaryRequest = false;
    }
    else
    {
      attemptingSecondaryRequest = true;
    }

    if (!attemptingSecondaryRequest)
    {
      httpSession.setAttribute("userHasActiveRequest", new Boolean(true));

      try
      {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        SEGResponseWrapper segResponseWrapper = new SEGResponseWrapper(response);
        
        placeOperatorInThreadLocal(httpSession);

        filterChain.doFilter(servletRequest, segResponseWrapper);
        
        if (segResponseWrapper.shouldRenderNonFinancialEdits())
        {
          segResponseWrapper.renderNonFinancialEdits();
        }
        else
        {
          segResponseWrapper.renderAsNormal();
        }
        
        segResponseWrapper.close();
      }
      catch (Throwable e)
      {
        System.out.println(e);

        e.printStackTrace();

        throw new RuntimeException(e);
      }
      finally
      {
        SessionHelper.clearSessions();

        SessionHelper.closeSessions();
        
        SessionHelper.clearThreadLocals();

        try
        {
          httpSession.removeAttribute("userHasActiveRequest"); // user has finished their request
        }
        catch (IllegalStateException e)
        {
          // Ignore this - the session was already invalidated in the logout.
        }
      }
    }
    else
    {
      ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);

      return; // The user's first request is still active - ignore any additional requests by just returning.            
    }
  }
  
  /**
   * Checks the current HttpSession to see if a UserSession has been placed in scope.
   * If so, the operator is extracted in placed in thread local for downstream
   * processing to have access to this information without have to explicitly
   * pass it as a parameter.
   * @param session
   */
  private void placeOperatorInThreadLocal(HttpSession session)
  {
    if (session.getAttribute("userSession") != null)
    {
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        String operator = userSession.getUsername();
        
        SessionHelper.putInThreadLocal(HibernateEntityDifference.OPERATOR, operator);
    }
  }

  public void destroy()
  {

  }
}