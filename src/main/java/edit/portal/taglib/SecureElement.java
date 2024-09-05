package edit.portal.taglib;

import edit.portal.common.session.*;
import fission.utility.*;
import security.business.*;
import security.component.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 30, 2003
 * Time: 4:24:47 PM
 * To change this template use Options | File Templates.
 */
public class SecureElement implements BodyTag
{
    private BodyContent bodyContent;

    private PageContext pageContext;

    private Tag parent;

    private List requiredRoles;

    public SecureElement()
    {
        super();

        requiredRoles = new ArrayList();
    }

    public void setBodyContent(BodyContent bodyContent)
    {
        this.bodyContent = bodyContent;
    }

    public void doInitBody() throws JspException
    {
    }

    public int doAfterBody() throws JspException
    {
        return BodyTag.SKIP_BODY;
    }

    public void setPageContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    public void setParent(Tag parent)
    {
        this.parent = parent;
    }

    public Tag getParent()
    {
        return parent;
    }

    public int doStartTag() throws JspException
    {
        return BodyTag.EVAL_BODY_TAG;
    }

    public int doEndTag() throws JspException
    {
        String spanEnd = "</span>";

        String spanStart = null;

        if (userIsInRole())
        {
            spanStart = "<span>";
        }
        else
        {
            spanStart = "<span disabled onClick='return false'>";
        }

        String htmlElement = bodyContent.getString();

        try
        {
            bodyContent.clearBody();

            bodyContent.print(spanStart + htmlElement + spanEnd);

            bodyContent.writeOut(bodyContent.getEnclosingWriter());
        }
        catch (IOException e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new JspException(e);
        }
        finally
        {
            this.requiredRoles.clear();
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * Clear roles set from any prior tag.
     */
    public void release()
    {
        this.requiredRoles.clear();
    }

    /**
     * A comma delimited set of roles that have access to the specified element.
     * @param requiredRoles
     */
    public void setRequiredRoles(String requiredRoles)
    {
        mapToList(requiredRoles);
    }

    /**
     * Maps the comma dilemeted set of Roles to a List.
     * @param requiredRoles
     * @return
     */
    private void mapToList(String requiredRoles)
    {
        String[] roleTokens = Util.fastTokenizer(requiredRoles, ",");

        for (int i = 0; i < roleTokens.length; i++)
        {
            String token = roleTokens[i].trim();

            this.requiredRoles.add(token);
        }
    }

    /**
     * True if the logged-in User has a role within the set of roles required to access the specified element. If there is
     * no
     * @return
     */
    private boolean userIsInRole()
    {
        Security securityComponent = new SecurityComponent();

        boolean userIsInRole = false;

        if (securityComponent.isSecurityEnabled())
        {
            UserSession userSession = (UserSession) pageContext.getSession().getAttribute("userSession");

            String sessionId = userSession.getSessionId();

            String[] roles = securityComponent.getRoleNames(sessionId);

            for (int i = 0; i < roles.length; i++)
            {
                if (requiredRoles.contains(roles[i]))
                {
                    userIsInRole = true;

                    break;
                }
            }
        }
        else
        {
            userIsInRole = true; // Security is turned-off - allow total access
        }

        return userIsInRole;
    }
}
