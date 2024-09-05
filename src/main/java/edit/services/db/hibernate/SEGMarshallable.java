/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edit.services.db.hibernate;

import org.dom4j.Element;

/**
 * Basic framework for entities that wish to participate in some
 * marshalling framework. No doubt that the 99% rule will be
 * marhsalling/unmarshalling of HibernateEntities to and from
 * XML from the flex front-end. However, other entities may
 * also by marshalled/unmarshalled that are not HibernateEntity(s).
 * @see HibernateEntity
 * @author gfrosti
 */
public interface SEGMarshallable
{
    /**
     * Implementors map the xml-based state to
     * its corresponding object state.
     *
     * @param entityAsElement the target entity with its state represented as a DOM4J Element
     * @param version it is possible that implementors will want to have multiple
     * 			ways to unmarshal an entity - the version is a user-defined "flag"
     * 			that allows the implementor to vary the process as needed.
     */
    public void unmarshal(Element entityAsElement, String version);

    /**
     * Implmentors map the object state to its
     * corresponding Element state.
     *
     * @param version it is possible that implementors will want to have multiple
     * 			ways to unmarshal an entity - the version is a user-defined "flag"
     * 			that allows the implementor to vary the process as needed.
     */
    public Element marshal(String version);
}
