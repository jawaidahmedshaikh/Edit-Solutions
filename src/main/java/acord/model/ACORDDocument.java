/*
 * User: sdorman
 * Date: Aug 16, 2006
 * Time: 8:19:33 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model;

import org.dom4j.*;
import org.dom4j.io.*;
import org.dom4j.tree.*;

import java.io.*;

import edit.services.logging.*;
import logging.*;
import acord.model.tabular.*;
import acord.model.search.*;
import fission.utility.*;

public class ACORDDocument
{
    private Document document = new DefaultDocument();

    private CriteriaExpression criteriaExpression;
    private XTbML xtbml;

    public ACORDDocument()
    {
    }

    public Document getDocument()
    {
        return document;
    }

    public void add(XTbML xtbml)
    {
        this.xtbml = xtbml;

        this.document.add(xtbml.getElement());
    }

    public void remove(XTbML xtbml)
    {
        this.xtbml = xtbml;

        this.document.remove(xtbml.getElement());
    }

    public void add(CriteriaExpression criteriaExpression)
    {
        this.criteriaExpression = criteriaExpression;

        this.document.add(criteriaExpression.getElement());
    }

    public void remove(CriteriaExpression criteriaExpression)
    {
        this.criteriaExpression = criteriaExpression;

        this.document.remove(criteriaExpression.getElement());
    }

    public void printDocument()
    {
        XMLUtil.printDocumentToSystemOut(this.document);
    }

    public String asXML()
    {
        return document.asXML();
    }
}
