package edit.portal.menu;

import java.util.ArrayList;
import java.util.List;
import edit.portal.menu.MenuCreator;

import fission.utility.Util;

/**
 * Represents MainMenuItem.
 */
public class MainMenuItem extends MenuItem
{
    private String menuId;
    
    private String startSubmenu;
    private String endSubmenu;
    
    List<MenuItem> menuItems = null;
    
    public MainMenuItem(String menuLine)
    {
        menuItems = new ArrayList<MenuItem>();
        
        setMenuLine(menuLine);
        
        init();
    }
    
    private void init()
    {
        setIsRendered(true);
    
        String[] properties = MenuCreator.retrieveProperties(this.menuLine);
        
        String menuId = properties[0];

        this.setDisplayText(menuLine);

        this.setMenuId(menuId);

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

    /**
     * Returns all the child menu items.
     * @return
     */
    public List<MenuItem> getMenuItems()
    {
        return menuItems;
    }
    
    /**
     * Adds child menu items.
     * @param menuItem
     */
    public void addMenuItem(MenuItem menuItem)
    {
        menuItems.add(menuItem);
    }

    /**
     * Setter.
     * @param startSubmenu
     */
    public void setStartSubmenu(String startSubmenu)
    {
        this.startSubmenu = startSubmenu;
    }

    /**
     * Getter.
     * @return
     */
    public String getStartSubmenu()
    {
        return startSubmenu;
    }

    /**
     * Setter.
     * @param endSubmenu
     */
    public void setEndSubmenu(String endSubmenu)
    {
        this.endSubmenu = endSubmenu;
    }

    /**
     * Geter.
     * @return
     */
    public String getEndSubmenu()
    {
        return endSubmenu;
    }
    
    /**
     * Returns true if it has children.
     * @return
     */
    public boolean hasSubmenus()
    {
        return menuItems.size() == 0 ? false : true;
    }
    
    /**
     * Returns true if MainMenuItem contains atleast one SubmenuItem or SubmainmenuItem.
     * @return
     */
    public boolean hasAtleastOneSubmenuOrSubMainMenuToRender()
    {
        boolean hasAtleastOneSubmenuOrSubMainMenuToDisplay = false;
    
        for(MenuItem menuItem : getMenuItems())
        {
            if (menuItem.isRendered())
            {
                hasAtleastOneSubmenuOrSubMainMenuToDisplay = true;
                
                break;
            }
        }    
        
        return hasAtleastOneSubmenuOrSubMainMenuToDisplay;
    }
}
