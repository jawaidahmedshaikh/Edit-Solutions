package engine.sp.custom.entity;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.Activateentity;
import engine.sp.InstOneOperand;
import engine.sp.Retrieve;
import engine.sp.SPException;
import engine.sp.SPParams;
import engine.sp.ScriptProcessor;

import fission.utility.Util;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Element;

import org.dom4j.tree.DefaultElement;

import org.hibernate.metadata.ClassMetadata;

/**
 * Associates the specified "parent" to the specified "child" entity.
 * In actuality, we simply expect the backing Hibernate entities to 
 * be supporting a one-to-many association. If the assumption holds true, 
 * and it must, then given a parent A and a child B, we are able to perform the
 * following two operations:
 * 
 * B.setParentA(A)
 * A.getChildrenB().add(B)
 * 
 * The syntax is as follows:
 * 
 * Activateentity(Associate, a.b.c.Parent, a.b.c.d.Child)
 * 
 * From the above, it is assumed that there is a last-active a.b.c.Parent
 * and a last-active a.b.c.Child that have associated Hibernate Entities via
 * a previous "Create" 
 * 
 * The scripter may also specify XPath expressions to identify the Parent(s) and
 * Child(ren) Elements.  e.g.:
 * 
 * Activateentity(Associate, /a.b.c.Parent, /a.b.c.d.Child[Name=Foo])
 *
 * The above would blindly associate every a.b.c.Parent to every a.b.c.d.Child where
 * Child.Name = "Foo". Most likely the parent expression would only return one Parent.
 * 
 * The script may also specify an association attribute list to associate specific
 * Parent(s) with specific Child(ren) as defined by the attribute name list. e.g.:
 * 
 * Activateentity(Associate, /a.b.c.Parent, /a.b.c.d.Child[Name=Foo], {FooAttribute1, FooAttibute2})
 * 
 * The above would associate every Parent with a @FooAttribute1 and @FooAttribute2 that matches a Child
 * with the same attribute name/values. This approach allows the scripter to have created a "business" key
 * to make the assocations.
 *
 * Associations, as of this writing, are physically made by setting the parent on the child (e.g. child.setParent(parent)).
 * We avoid the adding of the child to the parent (e.g. parent.getChildren().add(child)) for performance reasons since
 * we know Hibernate performance degrades notably with this approach.
 *
 * There is the odd scenario that a child has a parent setter with a name different than the actual
 * parent (e.g. child.setParentA(parentB)). In this case, the scripter has the specify the name of the
 * parent from the perspective of the child. This is accomplished as follows:
 *
 * Activateentity(Associate, a.b.c.Parent as ParentB, a.b.c.d.Child)
 *
 * The above would ultimately be mapped to the following code:
 *
 * child.setParentB(parent) as opposed to the normally mapped approach of child.setParent(parent);
 *
 */
public class AssociateEntity extends ActivateEntityCommand
{
    public static int PARENT_ELEMENT_EXPRESSION = 1;

    public static int CHILD_ELEMENT_EXPRESSION = 2;

    public static int ASSOCATION_ATTRIBUTE_EXPRESSION = 3;

    public AssociateEntity()
    {
    }

    /**
     * Associates parent Hibernate Entity(s) to child Hibernate Entity(s). The scripter has determined
     * which parent/children to associate by the syntax of the instruction. In general, the association will
     * involve the last-active parent to the last-active child (as determined by the scripter). The scripter 
     * can also use XPath 1.0 to query for the Elements to associate as Hibernate Entities. The scripter
     * can also define Element.Attribute(s) to use as a "business" key to associate parent/children entities.
     * @param sp
     * @param parameterTokens
     * @throws SPException
     */
    public void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException
    {
        // Get the parent entity(s)...
        List<Element> parentElements = getHibernateElements(parameterTokens[PARENT_ELEMENT_EXPRESSION], sp);

        String alternativeParentName = getAlternativeParentName(parameterTokens[PARENT_ELEMENT_EXPRESSION]);

        // Get the child entity(s)
        List<Element> childElements = getHibernateElements(parameterTokens[CHILD_ELEMENT_EXPRESSION], sp);

        // Prepare for attribute-based assocations if required
        String[] associationAttributeNames = getAssociationAttributeNames(parameterTokens);

        try
        {
            for (Element parentElement: parentElements)
            {
                for (Element childElement: childElements)
                {
                    boolean associate = true;

                    if (associationAttributeNames != null) // identify matching "business" keys as defined by the Element.Attribute(s)
                    {
                        for (String attributeName: associationAttributeNames)
                        {
                            Attribute parentAttribute = parentElement.attribute(attributeName);

                            Attribute childAttribute = childElement.attribute(attributeName);
                            
                            if ((parentAttribute == null) || (childAttribute == null))
                            {
                                associate = false;
                                
                                break;
                            }
                            else 
                            {
                                String parentAttributeValue = parentAttribute.getText();

                                String childAttributeValue = childAttribute.getText();                                
                                
                                if (!parentAttributeValue.equals(childAttributeValue))
                                {
                                    associate = false;
    
                                    break;
                                }
                            }
                        }
                    }

                    if (associate)
                    {
                        HibernateEntity parentEntity = SessionHelper.getHibernateEntity(parentElement, sp.getHibernateSession());

                        HibernateEntity childEntity = SessionHelper.getHibernateEntity(childElement, sp.getHibernateSession());

                        associate(parentEntity, childEntity, alternativeParentName);

                        synchronizeFK(parentEntity, childElement);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            String message = "Unable to make association between the following two element expressions [" + parameterTokens[1] + "] [" + parameterTokens[2] + "]";

            throw new SPException(message, SPException.INSTRUCTION_PROCESSING_ERROR, e);
        }
    }

    /**
     * The scripter may not be able to rely on the "default" parent name
     * when dealing with a Parent/Child Hibernate Entity. Knowing this, the
     * scripter can assign a different name to the parent Entity using the " as " expression.
     * @param expression
     * @return
     */
    private String getAlternativeParentName(String expression)
    {
        String alternativeParentName = null;

        // Find the " as " part of the expression, if any.
        int asIndex = expression.toUpperCase().indexOf(" AS ");

        if (asIndex > 0)
        {
            alternativeParentName = expression.substring(asIndex, expression.length());

            alternativeParentName = Util.fastTokenizer(alternativeParentName, " ", true)[1];
        }

        return alternativeParentName;
    }

    /**
     * To keep the DOM model in sync with the Hibenate model, set the newly created FK back on the child DOM Element.
     * @param parentEntity
     * @param childElement
     */
    private void synchronizeFK(HibernateEntity parentEntity, Element childElement)
    {
        String fkFieldName = Util.getClassName(Util.getFullyQualifiedClassName(parentEntity.getClass())) + "FK";

        Long fkValue = SessionHelper.getPKValue(parentEntity);

        Element fkElement = childElement.element(fkFieldName);

        if (fkElement == null)
        {
            fkElement = new DefaultElement(fkFieldName);

            childElement.add(fkElement);
        }

        fkElement.setText(fkValue.toString());
    }


    /**
     * Associates the specified parent Hibernate entity to the specified child Hibernate entity.
     * If the alternativeParentName is not null, then a setter named "set" + alternativeParentName will be used
     * instead of the actual name of the supplied parentEntity.
     * @param parentEntity
     * @param childEntity
     */
    private void associate(HibernateEntity parentEntity, HibernateEntity childEntity, String alternativeParentName) throws Exception
    {
        // Set Parent on Child
        Method setParentOnChildMethod = getParentOnChildMethod(parentEntity, childEntity, alternativeParentName);

        setParentOnChildMethod.invoke(childEntity, new Object[]{ parentEntity });

        // NO - for performance, set Parent on Child only - collections too slow.
        // Add Child to Parent - 
        //Method getChildrenFromParentMethod = getChildrenFromParentMethod(parentEntity.getClass(), childEntity.getClass());

        //Set childEntities = (Set) getChildrenFromParentMethod.invoke(parentEntity, null);

        //childEntities.add(childEntity);
    }

    /**
     * Derives the Child.setParent() method from the specified Parent/Child entities.
     * @param parentEntity
     * @param childEntity
     * @return
     * @throws NoSuchMethodException
     */
    private Method getParentOnChildMethod(HibernateEntity parentEntity, HibernateEntity childEntity, String alternativeParentName) throws NoSuchMethodException
    {
        String setParentOnChildMethodName = null;

        if (alternativeParentName != null)
        {
            setParentOnChildMethodName = "set" + alternativeParentName;
        }
        else
        {
            setParentOnChildMethodName = "set" + Util.getClassName(parentEntity.getClass().getName());
        }

        Method setParentOnChildMethod = childEntity.getClass().getMethod(setParentOnChildMethodName, new Class[]
                {

                    parentEntity.getClass() });
        return setParentOnChildMethod;
    }

    /**
     * We seek a reasonable naming convention for what a child entity collection might be
     * called relative to its parent. Adding 's' isn't sufficient. As of this writing, 
     * I am assuming the following knowing that this isn't sufficient for all English
     * grammar rules, but hopefully sufficient for our system of table names.
     * 
     * 1. If the child entity does not end in "s" or "y", then add an "s" at the end.
     *      e.g. Parent.Children would yield Parent.getChildrens().
     *  
     * 2. If the child entity ends in a "ss", then add "es".
     *      e.g. Parent.Childrenss would yield Parent.getChildrensses().
     *      
     * 3. If the child entity ends in a "y", then convert to "ies".
     *      e.g. Parent.Childreny would yield Parent.getChildrenies().
     *      
     * Any other case will likely have to be hard-coded which is "acceptible" in that
     * this is a custom instruction.
     *      
     * @param childEntityClass
     * @return
     */
    private Method getChildrenFromParentMethod(Class parentEntityClass, Class childEntityClass) throws Exception
    {
        String childClassName = Util.getClassName(childEntityClass.getName());

        String childMethodName = null;

        if (childClassName.endsWith("y"))
        {
            childMethodName = childClassName.substring(0, childClassName.length() - 1);

            childMethodName += "ies";
        }
        else if (childClassName.endsWith("ss"))
        {
            childMethodName = childClassName + "es";
        }
        else
        {
            childMethodName = childClassName + "s";
        }

        Method childFromParentMethod = parentEntityClass.getMethod("get" + childMethodName, null);

        return childFromParentMethod;
    }

    /**
     * Gets the targeted Elements from the specified expression. If the expression
     * is an XPath expression, then an XPath query will be executed to retrieve the Elements.
     * If the expression is not an XPath expression, then the expression is to identify the 
     * last active Element. no plural).
     * @param elementExpression
     * @return
     */
    private List<Element> getHibernateElements(String elementExpression, ScriptProcessor sp) throws SPException
    {
        List<Element> elements = new ArrayList<Element>();
        
        // Parse-out the " as " part of the expression, if any.
        int asIndex = elementExpression.toUpperCase().indexOf(" AS ");

        if (asIndex > 0)
        {
            elementExpression = elementExpression.substring(0, asIndex);
        }

        if (isXPathExpression(elementExpression))
        {
            Retrieve retrieve = new Retrieve();

            retrieve.setScriptProcessor(sp);

            List<Element> results = retrieve.executeXPath(elementExpression);

            elements.addAll(results);
        }
        else
        {
            elementExpression = InstOneOperand.checkForAlias(elementExpression, sp);
            
            Element lastActiveParent = sp.getSPParams().getLastActiveElement(elementExpression);

            elements.add(lastActiveParent);
        }

        return elements;
    }

    /**
     * The scripter may have added association attribute names as part of 
     * the instruction call. They would resemble:
     * 
     * 
     * @param parameterTokens
     * @return
     */
    private String[] getAssociationAttributeNames(String[] parameterTokens)
    {
        String[] associationAttributeNames = null;

        if (parameterTokens.length == 4) // by definition, this means that there are attributes.
        {
            String associationAttributeExpression = parameterTokens[ASSOCATION_ATTRIBUTE_EXPRESSION];

            int leftBraceIndex = associationAttributeExpression.indexOf("{");

            int rightBraceIndex = associationAttributeExpression.indexOf("}");

            associationAttributeExpression = associationAttributeExpression.substring(leftBraceIndex + 1, rightBraceIndex);

            associationAttributeNames = Util.fastTokenizer(associationAttributeExpression, ",", true);
        }

        return associationAttributeNames;
    }
}
