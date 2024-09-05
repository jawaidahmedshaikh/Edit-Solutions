package edit.services.db.hibernate;

import org.dom4j.Element;

/**
 * Hibernate entities (those that implement HibernateEntity) may wish to 
 * map their state to that of a DOM4J element. The DOM4J API is used since
 * it is the same DOM API used by Hibernate and is ubiquitous in the 
 * industry.
 */
public interface SEGElement
{
  /**
   * The implementing class is to map its state to that of a DOM4J Element.
   * Values should be mapped as elements versus attributes. Attributes may be
   * created if needed, and are potentially useful as meta-data, but will be 
   * ignored by SEG code. e.g.:
   * Investment.Amount = 100.00 should be mapped as:
   * 
   * <Investment>
   *  <Amount>100.00</Amount>
   * </Investment>
   * 
   * .. and not:
   * 
   * <Investment Amount="100"></Investment>.
   * 
   * The returned Element object should be the same reference retrieved between
   * multiple calls even though the state of the POJO (and thus the Element)
   * may change between the same multiple calls.
   * 
   * The returned Element should have a name of EntityName + "VO" to respect
   * EDITSolution's convention of adding "VO" to represent a by-value-only object.
   * For example:
   * Investement would have a Element with the name of "InvestmentVO".
   * 
   * @return the state of the implementing Hibernate entity as a DOM4J Element
   */
  public Element getAsElement();
}
