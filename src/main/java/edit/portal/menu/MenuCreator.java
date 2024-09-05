package edit.portal.menu;

import fission.utility.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Helper class to create dynamic menu based on the role.
 * Purpose: Reads javascript menu file generated by Xara Menu Maker and verifies authorization of each menu item
 * and displays menu options if authorized.
 * Assumptions: 1) Expects the javascript menu file in the format that is generated by Xara Menu Maker.
 * Typical format of javascript menu file generated by Xara Menu Maker. That contains Main menu items and submenu items.
 * startMainMenu("",0,0,1,0,0)
 * mainMenuItem("mainmenu_b1",".gif",41,341,"javascript:showIndividuals();","","Client Administration",2,2,"mainmenu_plain"); //UseCaseComponentName=client.component.ClientUseCaseComponent&MethodName=accessClient
 * mainMenuItem("mainmenu_b2",".gif",41,341,"javascript:;","","Contract Administration",1,2,"mainmenu_plain");
 * endMainMenu("",0,0);
 * startSubmenu("mainmenu_b2","mainmenu_menu",177);
 * submenuItem("New Business","javascript:showQuote();","","mainmenu_plain"); //UseCaseComponentName=contract.component.NewBusinessUseCaseComponent&MethodName=accessNewBusiness
 * submenuItem("Contract","javascript:showContract();","","mainmenu_plain"); //UseCaseComponentName=contract.component.InforceUseCaseComponent&MethodName=accessInforceContract
 * endSubmenu("mainmenu_b2");
 * 2) Each menu item should be followed by comment defining the UseCaseComponentName (like property-value pair) and MethodName except
 * menu items that have submenu items. Ex: UseCaseComponentName=contract.component.NewBusinessUseCaseComponent&MethodName=accessNewBusiness
 * As per the current business process, if the use case component and method name are not defined,
 * displays the menu option.
 * It uses UseCaseComponentName and MethodName to determine the authorization.
 */
public class MenuCreator
{
    private static final String MENU_FILE = "C:\\Projects\\JDeveloper\\EDITSolutions\\BaseChangesFixes\\webapps\\common\\navigation\\gardner.js";
    
    private static final String PROPERTIES_DELIMITER = ",";
    private static final String COMMENT = "//";
    private static final String AMPERSAND = "&";
    private static final String ISEQUALTO = "=";
    
    private static final String START_MAIN_MENU = "startMainMenu";
    private static final String END_MAIN_MENU = "endMainMenu";
    private static final String MAIN_MENU_ITEM = "mainMenuItem";

    private static final String START_SUB_MENU = "startSubmenu";
    private static final String END_SUB_MENU = "endSubmenu";
    private static final String SUB_MENU_ITEM = "submenuItem";
    
    /**
     * List of all the menu items that are between 'startMainMenu' line and 'endMainMenu' line and 
     * menus containing submenu items.
     */
    private List<MainMenuItem> highestMainMenuItems = new ArrayList<MainMenuItem>();
    
    /**
     * Map of all the menu items that are between 'startMainMenu' line and 'endMainMenu' line and 
     * menus containing submenu items.
     */
    private Map<String, MainMenuItem> mainMenuItemsMap = new TreeMap<String, MainMenuItem>();
    
    /**
     * StartMainMenu line.
     */
    private String startMainMenu;
    
    /**
     * EndMainMenu line.
     */
    private String endMainMenu;
    
    /**
     * MenuId.
     */
    private String currentMainMenuId = null;
    
    /**
     * List of all the menu lines to be displayed.
     */
    private List<String> menuLines = null;
    
    /**
     * Property to determine the first menu item. All the menu items that are contained between
     * 'startMainMenu' line and 'endMainMenu' line.
     */
    private boolean highestMainMenuItem = false;
    
    /**
     * HttpSession 
     */
    private HttpSession session = null;
    
    /**
     * Constructor.
     * @param request - HttpServletRequest
     */
    public MenuCreator(HttpServletRequest request, InputStream inputStream)
    {
        this.session = request.getSession();
        
        menuLines = new ArrayList<String>();

        buildMenu(inputStream);       
    }

    /**
     * Performs menu building.
     */
    private void buildMenu(InputStream inputStream)
    {
        readMenuFile(inputStream);
    
        createMainMenuItems();
        
        createSubmenuAndAttachMainMenuItems();
        
        // submenu items
        for (MainMenuItem mainMenuItem : mainMenuItemsMap.values())
        {
            if (mainMenuItem.hasSubmenus())
            {
                List<MenuItem> menuItems = mainMenuItem.getMenuItems();

                for(MenuItem menuItem : menuItems)
                {
                    // submainmenu items are taken care in next step.
                    if (menuItem instanceof SubmenuItem)
                    {
                        // some submenus does not have use cases built. -- temp solution
                        if (menuItem.hasUseCaseComponentNameAndMethodName)
                        {
                            if (isCompiledWithSecurity() && !menuItem.isAuthorized())
                            {
                                menuItem.setIsRendered(false);
                            }
                        }
                    }
                }
            }
        }
        
        // mainmenu items and submainmenu items.
        for (MainMenuItem mainMenuItem : mainMenuItemsMap.values())
        {
            // if has UseCaseComponentName & MethodName check for authorization
            if (mainMenuItem.hasUseCaseComponentNameAndMethodName)
            {
                if (isCompiledWithSecurity() && !mainMenuItem.isAuthorized())
                {
                    mainMenuItem.setIsRendered(false);
                }
            }
            else
            {
                // does not have UseCaseComponentName & MethodName and does not have submenus
                if (!mainMenuItem.hasAtleastOneSubmenuOrSubMainMenuToRender())
                {
                    mainMenuItem.setIsRendered(false);
                }
            }
        }

        // main mainmenu items        
        for (MainMenuItem mainMenuItem : getHighestMainMenuItems())
        {
            // if has UseCaseComponentName & MethodName check for authorization
            if (mainMenuItem.hasUseCaseComponentNameAndMethodName)
            {
                if (isCompiledWithSecurity() && !mainMenuItem.isAuthorized())
                {
                    mainMenuItem.setIsRendered(false);
                }
            }
            else
            {
                // does not have UseCaseComponentName & MethodName and does not have submenus
                if (!mainMenuItem.hasAtleastOneSubmenuOrSubMainMenuToRender())
                {
                    mainMenuItem.setIsRendered(false);
                }
            }
        }
    }

    /**
     * Reads the contents from Xara Menu Maker generated javascript file.
     */
    private void readMenuFile(InputStream inputStream)
    {
        BufferedReader br = null;
        
        try
        {
            br = new BufferedReader(new InputStreamReader(inputStream));
            
            String line = null;
            
            while ((line = br.readLine()) != null)
            {
                if (line.length() > 0)
                {
                    if (isMenuLine(line))
                    {
                        menuLines.add(line);
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException e)
            {
                System.out.println(e);
                
                e.printStackTrace();
                
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Determines if the line is menu line. Interested only in lines starting with
     * 'startMainMenu', 'mainMenuItem', 'endMainMenu', 'startSubmenu', 'submenuItem', 'endSubmenu'.
     * @param line
     * @return - true or false.
     */
    private boolean isMenuLine(String line)
    {
        boolean isMenuLine = false;
        
        if (isStartMainMenu(line) || isMainMenuItem(line) || isEndMainMenu(line) ||
            isStartSubmenu(line) || isSubmenuItem(line) || isEndSubmenu(line))
            {
                isMenuLine = true;
            }
            
        return isMenuLine;
    }
    
    /**
     * Reads all the menu lines and builds main menu items.
     */
    private void createMainMenuItems()
    {
        for (String menuLine : menuLines)
        {
            if (isStartMainMenu(menuLine))
            {
                setStartMainMenu(menuLine);
            }
            else if (isMainMenuItem(menuLine))
            {
                createMainMenuItem(menuLine);
            }
            else if (isEndMainMenu(menuLine))
            {
                setEndMainMenu(menuLine);
            }            
        }
    }
    
    /**
     * Creates main menu items.
     * @param menuLine
     */
    private void createMainMenuItem(String menuLine)
    {
        MainMenuItem mainMenuItem = new MainMenuItem(menuLine);

        String[] properties = retrieveProperties(menuLine);
        
        String menuId = properties[0];

        if (isHighestMainMenuItem())
        {
            highestMainMenuItems.add(mainMenuItem);
        }
        
        mainMenuItem.setSession(this.session);

        mainMenuItemsMap.put(menuId, mainMenuItem);
    }
    
    /**
     * Creates submenu items and adds to the parent menu item as child.
     */
    private void createSubmenuAndAttachMainMenuItems()
    {
        for (String menuLine : menuLines)
        {
            if (isStartSubmenu(menuLine))
            {
                setStartSubmenu(menuLine);
            }
            else if (isSubmenuItem(menuLine))
            {
                createSubmenuItem(menuLine);
            }
            else if (isMainMenuItem(menuLine))
            {
                attachMainMenuItem(menuLine);                
            }
            else if (isEndSubmenu(menuLine))
            {
                setEndSubmenuItem(menuLine);
            }
        }
    }
    
    /**
     * Returns true if line starts with 'startMainMenu'
     * @param line
     * @return
     */
    private boolean isStartMainMenu(String line)
    {
        return line.startsWith(START_MAIN_MENU);
    }
    
    /**
     * Returns true if line starts with 'mainMenuItem'
     * @param line
     * @return
     */
    private boolean isMainMenuItem(String line)
    {
        return line.startsWith(MAIN_MENU_ITEM);    
    }
    
    /**
     * Returns true if line starts with 'endMainMenu'
     * @param line
     * @return
     */
    private boolean isEndMainMenu(String line)
    {
        return line.startsWith(END_MAIN_MENU);
    }
    
    /**
     * Returns true if line starts with 'startSubmenu'
     * @param line
     * @return
     */
    private boolean isStartSubmenu(String line)
    {
        return line.startsWith(START_SUB_MENU);
    }
    
    /**
     * Returns true if line starts with 'submenuItem'
     * @param line
     * @return
     */
    private boolean isSubmenuItem(String line)
    {
        return line.startsWith(SUB_MENU_ITEM);
    }
    
    /**
     * Returns true if line starts with 'endSubmenu'
     * @param line
     * @return
     */
    private boolean isEndSubmenu(String line)
    {
        return line.startsWith(END_SUB_MENU);
    }
    
    /**
     * Creates submenu items. 
     * @param menuLine
     */
    private void createSubmenuItem(String menuLine)
    {
        SubmenuItem submenuItem = new SubmenuItem(menuLine);
        
        submenuItem.setSession(this.session);
        
        MainMenuItem mainMenuItem = mainMenuItemsMap.get(getCurrentMainMenuId());
        
        mainMenuItem.addMenuItem(submenuItem);
    }
    
    /**
     * Adds to the corresponding parent if submenu item is main menu item.
     * @param menuLine
     */
    private void attachMainMenuItem(String menuLine)
    {
        // To make sure we attach SubMainMenu items to MainMenuItems
        if (getCurrentMainMenuId() != null)
        {
            MainMenuItem parentMainMenuItem = mainMenuItemsMap.get(getCurrentMainMenuId());
            
            String[] properties = retrieveProperties(menuLine);
            
            String thisMainMenuId = properties[0];
            
            MainMenuItem thisMainMenuItem = mainMenuItemsMap.get(thisMainMenuId);
            
            parentMainMenuItem.addMenuItem(thisMainMenuItem);
        }
    }
    
    /**
     * Marks start of submenu.
     * @param menuLine
     */
    private void setStartSubmenu(String menuLine)
    {
        String[] properties = retrieveProperties(menuLine);
        
        String mainMenuId = properties[0];
        
        setCurrentMainMenuId(mainMenuId);
        
        MainMenuItem mainMenuItem = mainMenuItemsMap.get(mainMenuId);
        
        mainMenuItem.setStartSubmenu(menuLine);
    }
    
    /**
     * Marks end of submenu.
     * @param menuLine
     */
    private void setEndSubmenuItem(String menuLine)
    {
        MainMenuItem mainMenuItem = mainMenuItemsMap.get(getCurrentMainMenuId());
        
        mainMenuItem.setEndSubmenu(menuLine);
    
        setCurrentMainMenuId(null);
    }
    
    /**
     * Returns main menu items.
     * @return
     */
    private List<MainMenuItem> getHighestMainMenuItems()
    {
        return highestMainMenuItems;
    }

    /**
     * Marks start of main menu.
     * @param startMainMenu
     */
    private void setStartMainMenu(String startMainMenu)
    {
        this.startMainMenu = startMainMenu;
        
        setHighestMainMenuItem(true);
    }

    /**
     * Returns startMainMenu string.
     * @return
     */
    private String getStartMainMenu()
    {
        return startMainMenu;
    }

    /**
     * Marks the end of main menu.
     * @param endMainMenu
     */
    private void setEndMainMenu(String endMainMenu)
    {
        this.endMainMenu = endMainMenu;
        
        setHighestMainMenuItem(false);
    }

    /**
     * Returns endMainMenu string.
     * @return
     */
    private String getEndMainMenu()
    {
        return endMainMenu;
    }

    /**
     * Setter.
     * @param currentMainMenuId
     */
    private void setCurrentMainMenuId(String currentMainMenuId)
    {
        this.currentMainMenuId = currentMainMenuId;
    }

    /**
     * Getter.
     * @return
     */
    private String getCurrentMainMenuId()
    {
        return currentMainMenuId;
    }
    
    /**
     * Setter.
     * @param highestMainMenuItem
     */
    private void setHighestMainMenuItem(boolean highestMainMenuItem)
    {
        this.highestMainMenuItem = highestMainMenuItem;
    }

    /**
     * Getter.
     * @return
     */
    private boolean isHighestMainMenuItem()
    {
        return highestMainMenuItem;
    }    

    /**
     * Displays menu.
     * @return
     */
    public String[] displayMenu()
    {
        List<String> displayLines = new ArrayList<String>();
        
        displayLines.add(getStartMainMenu());
    
        for (MainMenuItem mainMenuItem : getHighestMainMenuItems())
        {
            if (mainMenuItem.isRendered())
            {
                displayLines.add(mainMenuItem.getDisplayText());
            }
        }
        
        displayLines.add(getEndMainMenu());
        
        displayLines.add("");
        
        for (MainMenuItem mainMenuItem : mainMenuItemsMap.values())
        {
            if (mainMenuItem.isRendered() && mainMenuItem.hasSubmenus())
            {
                displayLines.add(mainMenuItem.getStartSubmenu());    
            
                for(MenuItem menuItem : mainMenuItem.getMenuItems())                    
                {
                    if (menuItem.isRendered())
                    {
                        displayLines.add(menuItem.getDisplayText());
                    }
                }
                
                displayLines.add(mainMenuItem.getEndSubmenu());
                
                displayLines.add("");
            }
        }
        
        displayLines.add("");
        
        return displayLines.toArray(new String[displayLines.size()]);
    }

    /**
     * Retrieves Properties.
     * @param line
     * @return
     */
    public static String[] retrieveProperties(String line)
    {
        String propertiesString = line.substring(line.indexOf("(")+1, line.length()-2);
        
        String[] properties = Util.fastTokenizer(propertiesString, PROPERTIES_DELIMITER);
        
        return properties;
    }

    /**
     * Returs UseCaseComponentName.
     * @param menuLine
     * @return
     */
    public static String getUseCaseComponentName(String menuLine)
    {
        String useCaseComponentName = null;
        
        String commentString = getCommentString(menuLine);
    
        if (commentString != null)
        {
            useCaseComponentName = commentString.substring(0, commentString.indexOf(AMPERSAND));
            
            useCaseComponentName = useCaseComponentName.substring(useCaseComponentName.indexOf(ISEQUALTO)+1);
        }
        
        return useCaseComponentName;
    }
    
    /**
     * Returns MethodName
     * @param menuLine
     * @return
     */
    public static String getMethodName(String menuLine)
    {
        String methodName = null;
        
        String commentString = getCommentString(menuLine);
    
        if (commentString != null)
        {
            methodName = commentString.substring(commentString.indexOf(AMPERSAND)+1);
            
            methodName = methodName.substring(methodName.indexOf(ISEQUALTO)+1);
        }
        
        return methodName;
    }
    
    /**
     * Returns the comment string.
     * @param menuLine
     * @return
     */
    public static String getCommentString(String menuLine)
    {
        String comment = null;
        
        if (menuLine.indexOf(COMMENT) > 0)
        {
            comment = menuLine.substring(menuLine.indexOf(COMMENT)+2);
        }
        
        return comment;
    }    
    
    /**
     * A helper method to determine if the code is compiled with aspectJ.
     * Identified if the code is compiled with aspectJ it adds some static variables with names containing ajc$.
     * Checks for any field names with ajc$. If found returns true. 
     * @return true if code is compiled with security.
     */
 
    public static boolean isCompiledWithSecurity() 
    {
        boolean isCompiledWithSecurity = false;

        try
        {
            Class clazz = Class.forName("contract.component.InforceUseCaseComponent");
            
            Field[] fields = clazz.getDeclaredFields();
            
            for (Field field : fields)
            {
                String fieldName = field.getName();
                
                if (fieldName.contains("ajc$"))
                {
                    isCompiledWithSecurity = true;
                    
                    break;
                }
            }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        
        return isCompiledWithSecurity;
    }
}