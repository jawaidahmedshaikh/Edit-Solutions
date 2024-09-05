/*
 * StringBean.java
 *
 * Created on June 15, 2006, 10:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edit.common.ui;

/**
 * The Bean API requires naming conventions that use get/set. What if one would
 * like to use the String itself as a simple bean to use, for example, with the
 * Apache input/jstl tags? This class is a bean-ified String.
 * @author gfrosti
 */
public class StringBean
{
    private String string;
    
    public StringBean(){}
    
    /** Creates a new instance of StringBean */
    public StringBean(String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }

    public void setString(String string)
    {
        this.string = string;
    }
    
}
