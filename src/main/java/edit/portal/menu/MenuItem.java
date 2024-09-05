package edit.portal.menu;

import edit.portal.common.session.UserSession;

import javax.servlet.http.HttpSession;

import security.business.Security;

import security.component.SecurityComponent;

/**
 * Abstract class defining menu item.
 */
public abstract class MenuItem
{
    /**
     * The source javascript line.
     */
    protected String menuLine;

    /**
     * The javascript text that is rendered in JSP.
     */
    protected String displayText;

    /**
     * UseCaseComponentName.
     */
    protected String useCaseComponentName;

    /**
     * MethodName
     */
    protected String methodName;
    
    /**
     * True if both UseCaseComponentName and MethodName exists.
     */
    protected boolean hasUseCaseComponentNameAndMethodName = false;

    /**
     * HttpSession.
     */
    protected HttpSession session;
    
    /**
     * Boolean value to determine this menu item should be rendered?
     */
    protected boolean isRendered = true;

    /**
     * Setter.
     * @param menuLine
     */
    public void setMenuLine(String menuLine)
    {
        this.menuLine = menuLine;
    }

    /**
     * Getter.
     * @return
     */
    public String getMenuLine()
    {
        return menuLine;
    }


    /**
     * Setter.
     * @param displayText
     */
    public void setDisplayText(String displayText)
    {
        this.displayText = displayText;
    }

    /**
     * Getter.
     * @return
     */
    public String getDisplayText()
    {
        return displayText;
    }

    /**
     * Setter.
     * @param useCaseComponentName
     */
    public void setUseCaseComponentName(String useCaseComponentName)
    {
        this.useCaseComponentName = useCaseComponentName;
    }

    /**
     * Getter.
     * @return
     */
    public String getUseCaseComponentName()
    {
        return useCaseComponentName;
    }

    /**
     * Setter.
     * @param methodName
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    /**
     * Getter.
     * @return
     */
    public String getMethodName()
    {
        return methodName;
    }

    /**
     * Determines the user logged in has authorization to view this menu item.
     * @return
     */
    public boolean isAuthorized()
    {
        boolean isAuthorized = false;

        UserSession userSession = (UserSession) getSession().getAttribute("userSession");

        String sessionId = userSession.getSessionId();

        Security security = new SecurityComponent();

        isAuthorized = security.authorize(sessionId, getUseCaseComponentName(), getMethodName());
        
        return isAuthorized;
    }
    

    /**
     * Setter.
     * @param hasUseCaseComponentNameAndMethodName
     */
    public void setHasUseCaseComponentNameAndMethodName(boolean hasUseCaseComponentNameAndMethodName)
    {
        this.hasUseCaseComponentNameAndMethodName = hasUseCaseComponentNameAndMethodName;
    }

    /**
     * Returns true if this menu item was defined with UseCaseComponentName and MethodName.
     * @return
     */
    public boolean hasUseCaseComponentNameAndMethodName()
    {
        return hasUseCaseComponentNameAndMethodName;
    }

    /**
     * Setter.
     * @param session
     */
    public void setSession(HttpSession session)
    {
        this.session = session;
    }

    /**
     * Getter.
     * @return
     */
    public HttpSession getSession()
    {
        return session;
    }

    /**
     * Setter.
     * @param isRendered
     */
    public void setIsRendered(boolean isRendered)
    {
        this.isRendered = isRendered;
    }

    /**
     * Getter.
     * @return
     */
    public boolean isRendered()
    {
        return isRendered;
    }
}
