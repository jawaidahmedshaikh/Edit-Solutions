package edit.portal.menu;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents SubmenuItem.
 */
public class SubmenuItem extends MenuItem
{
    private String menuId;   
    
    /**
     * Constructor.
     */
    public SubmenuItem(String menuLine)
    {
        setMenuLine(menuLine);

        init();
    }
    
    private void init()
    {
        setIsRendered(true);        

        String[] properties = MenuCreator.retrieveProperties(this.menuLine);

        String menuId = properties[0];

        this.setMenuId(menuId);

        this.setDisplayText(menuLine);

        String useCaseComponentName = Util.initString(MenuCreator.getUseCaseComponentName(menuLine), null);

        String methodName = Util.initString(MenuCreator.getMethodName(menuLine), null);

        this.setUseCaseComponentName(useCaseComponentName);

        this.setMethodName(methodName);

        if (useCaseComponentName != null && methodName != null)
        {
            this.setHasUseCaseComponentNameAndMethodName(true);
        }
    }

    /**
     * Setter.
     * @param menuId
     */
    public void setMenuId(String menuId)
    {
        this.menuId = menuId;
    }

    /**
     * Getter.
     * @return
     */
    public String getMenuId()
    {
        return menuId;
    }
}
