package edit.portal.taglib;

import edit.portal.common.session.UserSession;
import edit.common.vo.ProductStructureVO;
import edit.common.exceptions.EDITSecurityException;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpSession;

import engine.ProductStructure;

/**
 * Makes a display of the current product structure.
 * <p>
 * dynamicAttributes is optional and allows additional javascript or stylesheet stuff
 * to be passed thru onto the select tag as-is.  It makes a span tag so you can
 * put something on it if you want.
 *
 * <PRE>
 *     EXAMPLE:
 *     &lt;%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %&gt;
 *     .
 *     .
 *     &lt;security:currentUserProductStructure /&gt;
 *
 * </PRE>
 */
public class CurrentUserCompanyStructure extends TagSupport
{

    protected String name;

    /** Extra attributes for javascript etc.  This will just be included in the select definition */
    protected String dynamicAttributes;

    public int doStartTag() throws JspException
    {
        try
        {
            JspWriter writer = pageContext.getOut();

            HttpSession session = pageContext.getSession();
            UserSession userSession = (UserSession) session.getAttribute("userSession");

            ProductStructure productStructure = null;
            String businessContractName = null;

            productStructure = userSession.getCurrentProductStructure();
            if (productStructure == null)
            {
                businessContractName = "not set";
            }
            else
            {
                ProductStructureVO productStructureVO =
                    (ProductStructureVO) productStructure.getVO();
                businessContractName = productStructureVO.getBusinessContractName();
            }

            writer.println("<span  " + this.dynamicAttributes + " >");
            writer.println("Current Product Structure: <i>" + businessContractName);
            writer.println("</i></span>");
        }
        catch (Exception ex)
        {
            throw new JspTagException("UserProductStructureSelect: " +
                    ex.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag()
    {
        return EVAL_PAGE;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String aName) {
      this.name = aName;
    }

    public String getDynamicAttributes() {
      return this.dynamicAttributes;
    }

    public void setDynamicAttributes(String aString) {
      this.dynamicAttributes = aString;
    }





}

