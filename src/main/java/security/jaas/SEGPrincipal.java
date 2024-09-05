package security.jaas;


import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import security.Operator;
import security.Role;
import security.FilteredRole;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 15, 2003
 * Time: 4:09:21 PM
 * To change this template use Options | File Templates.
 */
public class SEGPrincipal implements Principal, Serializable
{
    private String name;

    private int type;

    public static final int TYPE_ROLE = 0;

    public static final int TYPE_SESSION_ID = 1;

    public static final int TYPE_PK = 2;

    /** The list of FilteredRoles for a Role type SEGPrincipal */
    private List filteredRoles;

    public SEGPrincipal(String name, int type)
    {
        this.name = name;

        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public int getType()
    {
        return type;
    }


    /**
     * Ensures that certain methods can only be called/used by
     * Role type Principals.
     */
    private void ensureRoleTypePrincipal()
    {
       if (this.type != TYPE_ROLE)
        {
            throw new IllegalStateException(
                "CompanyStructure method called for a SEGPrincipal" +
                "that is not type=ROLE: " +
                "name=" + this.name + " type=" + this.type);
        }
    }

    /**
     * Lazily get the List of FilteredRole's that are used by this SEGPrincipal (role).
     * This corresponds to all of the role X company structures that are
     * attached.
     * @return
     */
    public List getFilteredRoles()
    {
        ensureRoleTypePrincipal();

        if (this.filteredRoles == null)
        {
            Role[] theRole = Role.findByRoleName(this.name);

            this.filteredRoles = new ArrayList();

            if (theRole != null && theRole.length > 0)
            {

                FilteredRole[] fRoles =
                        FilteredRole.findByRole(theRole[0].getRolePK());

                if (fRoles != null)
                {
                    this.filteredRoles = Arrays.asList(fRoles);
                }
            }
        }

        return this.filteredRoles;

    }

}
