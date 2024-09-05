/*
 * User: sdorman
 * Date: Aug 29, 2006
 * Time: 8:51:20 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model.search;

import org.dom4j.*;
import org.dom4j.tree.*;

import java.util.*;

import fission.utility.*;


/**
 * A collection of Logical Operators and Criterias to generate complex selections.  Removes the restriction that search
 * criteria must be constrained to a single type level object type
 */
public class CriteriaExpression
{
    private Element element = new DefaultElement("CriteriaExpression");

    /**
     * @see acord.search.Constants
     */
    private Element criteriaOperator = new DefaultElement("CriteriaOperator");

    private List criterias = new ArrayList();     // either 1 or more criterias or 1 or more criteriaExpressions, not both

    private List criteriaExpressions = new ArrayList();


     /**
     * Instantiates the object with the minimum required information
     *
     * @param criteriaOperator
     */
    public CriteriaExpression(int criteriaOperator)
    {
        this.element.add(this.criteriaOperator);

        this.setCriteriaOperator(criteriaOperator);
    }

    /**
     * Instantiates the object with the minimum required information when containing Criteria
     *
     * @param criteria
     * @param criteriaOperator
     */
    public CriteriaExpression(Criteria criteria, int criteriaOperator)
    {
        this(criteriaOperator);

        this.add(criteria);
    }

    /**
     * Instantiates the object with the minimum required information when containing CriteriaExpression
     *
     * @param criteriaExpression
     * @param criteriaOperator
     */
    public CriteriaExpression(CriteriaExpression criteriaExpression, int criteriaOperator)
    {
        this(criteriaOperator);

        this.add(criteriaExpression);
    }



    /**
     * Returns the XML element for this object
     * @return
     */
    public Element getElement()
    {
        return this.element;
    }

    public int getCriteriaOperator()
    {
        return Integer.parseInt(criteriaOperator.getText());
    }

    public void setCriteriaOperator(int criteriaOperator)
    {
        if (this.criteriaOperator.content() != null)
        {
            this.criteriaOperator.clearContent();
        }

        String text = Util.getResourceMessage("criteriaOperator." + criteriaOperator);
        
        this.criteriaOperator.add(new DefaultText(text));
        this.criteriaOperator.addAttribute("tc", criteriaOperator + "");
    }

    /**
     * Returns the list of Criteria
     * @return
     */
    public List getCriteria()
    {
        return criterias;
    }

    /**
     * Adds a Criteria
     * @param criteria
     */
    public void add(Criteria criteria)
    {
        this.criterias.add(criteria);

        this.element.add(criteria.getElement());
    }

    /**
     * Removes a Criteria
     * @param criteria
     */
    public void remove(Criteria criteria)
    {
        this.criterias.remove(criteria);

        this.element.remove(criteria.getElement());
    }

    /**
     * Returns the list of CriteriaExpressions
     * @return
     */
    public List getCriteriaExpressions()
    {
        return criteriaExpressions;
    }

    /**
     * Adds a CriteriaExpression
     * @param criteriaExpression
     */
    public void add(CriteriaExpression criteriaExpression)
    {
        this.criteriaExpressions.add(criteriaExpression);

        this.element.add(criteriaExpression.getElement());
    }

    /**
     * Removes a CriteriaExpression
     * @param criteriaExpression
     */
    public void remove(CriteriaExpression criteriaExpression)
    {
        this.criterias.remove(criteriaExpression);

        this.element.remove(criteriaExpression.getElement());
    }
}
