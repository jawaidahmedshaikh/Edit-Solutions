package edit.portal.taglib;

import edit.portal.common.session.UserSession;
import edit.common.vo.ProductStructureVO;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpSession;

import engine.*;

/**
 * Makes a Select pull-down for the ProductStructures that the
 * user has access to.
 * <p>
 * dynamicAttributes is optional and allows additional javascript or stylesheet stuff
 * to be passed thru onto the select tag as-is.
 *
 * <PRE>
 *     EXAMPLE:
 *     &lt;%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %&gt;
 *     .
 *     .
 *     &lt;security:selectForUserProductStructures
 *        name="productStructurePK"
 *        dynamicAttributes="tabindex='1' onFocus='clearFields(\"NameAndTax\")' onKeyDown='checkForEnter()'"
 *     /&gt;
 *
 * </PRE>
 */
public class UserCompanyStructureSelect extends TagSupport
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
            ProductStructureVO[] productStructureVOs =
                    userSession.getProductStructureVOsForUser();

             writer.println("<select  name=\""+ this.name + "\" "  + this.dynamicAttributes +  " >");
             writer.println("<option value=\"0\">Please Select &nbsp;&nbsp;&nbsp;&nbsp;</option>");

            if (productStructureVOs != null)
            {
                for (int i = 0; i < productStructureVOs.length; i++)
                {
                    if (productStructureVOs[i].getTypeCodeCT().equalsIgnoreCase("Product"))
                    {
                        Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                        String productStructurePK = productStructureVOs[i].getProductStructurePK() + "";

                        StringBuffer productStructure = new StringBuffer();

                        productStructure.append(company.getCompanyName());
                        productStructure.append(" ");
                        productStructure.append(productStructureVOs[i].getMarketingPackageName());
                        productStructure.append(" ");
                        productStructure.append(productStructureVOs[i].getGroupProductName());
                        productStructure.append(" ");
                        productStructure.append(productStructureVOs[i].getAreaName());
                        productStructure.append(" ");
                        productStructure.append(productStructureVOs[i].getBusinessContractName());

                        writer.println("<option value=\"" + productStructurePK + "\">" + productStructure.toString());
                    }
                }
            }
            writer.println("</select>");

        }
        catch (Exception ex)
        {
            throw new JspTagException("UserCompanyStructureSelect: " +
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

