package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.exolab.castor.xml.FieldValidator;
import org.exolab.castor.xml.NodeType;
import org.exolab.castor.xml.XMLFieldHandler;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;
import org.exolab.castor.xml.validators.DecimalValidator;
import org.exolab.castor.xml.validators.StringValidator;

/**
 * A utility class for building commonly used objects.
 * 
 * @author tperez
 *
 */
public final class XmlFieldDescriptorBuilder {
	//This is a utility class. Should never get instantiated.
	private XmlFieldDescriptorBuilder() {}

	/**
	 * Create an XMLFieldDescriptorImpl for a field of type String with validators attached.
	 * <p>
	 * This "should" create an XMLFieldDescriptorImpl for any String field as long as the getters/setters follow the naming 
	 * standards. If the method signature doesn't match "get + xmlName" or "set + xmlName", this will break. This should only be used on basic getters/setters.
	 * <p>
	 * Defaults: setWhiteSpace("preserve"), setMinOccurs(1), setImmutable(true), setRequired(true), setMultivalued(false)  
	 * 
	 * @param <T>
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param clazz
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param fieldName
	 * 			- the VO field name. (e.g. _MAPEndDate)
	 * @param xmlName
	 * 			- the XML name. (e.g. MAPEndDate). This is providing two uses, first for the descriptor, second for the method construction
	 * @param maxLength
	 * 			- the MAX length of the field
	 * @return - XMLFieldDescriptorImpl - the built XML descriptor.
	 */
	public static <T> XMLFieldDescriptorImpl createStringXmlFieldDescriptor(Class<T> clazz, String fieldName, final String xmlName, int maxLength)
	{
		XMLFieldDescriptorImpl desc = createXmlFieldDescriptor(clazz, String.class, fieldName, xmlName);
		
        //-- validation code
		FieldValidator fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(1);
        StringValidator typeValidator = new StringValidator();
        typeValidator.setMaxLength(maxLength);
        typeValidator.setWhiteSpace("preserve");
        fieldValidator.setValidator(typeValidator);
        desc.setValidator(fieldValidator);
		
		return desc;
	}

	/**
	 * Create an XMLFieldDescriptorImpl for a field of type BigDecimal with validators attached. This will not set a max number of digits.
	 * <p>
	 * This "should" create an XMLFieldDescriptorImpl for any BigDecimal field as long as the getters/setters follow the naming 
	 * standards. If the method signature doesn't match "get + xmlName" or "set + xmlName", this will break. This should only be used on basic getters/setters.
	 * <p>
	 * Defaults: setMinOccurs(1), setImmutable(true), setRequired(true), setMultivalued(false)  
	 * 
	 * @param <T>
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param clazz
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param fieldName
	 * 			- the VO field name. (e.g. _MAPEndDate)
	 * @param xmlName
	 * 			- the XML name. (e.g. MAPEndDate). This is providing two uses, first for the descriptor, second for the method construction
	 * @return - XMLFieldDescriptorImpl - the built XML descriptor.
	 * 
	 */
	public static <T> XMLFieldDescriptorImpl createBigDecimalXmlFieldDescriptor(Class<T> clazz, String fieldName, final String xmlName)
	{
		XMLFieldDescriptorImpl desc = createXmlFieldDescriptor(clazz, BigDecimal.class, fieldName, xmlName);
		
		FieldValidator fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(1);
        DecimalValidator typeValidator = new DecimalValidator();
        fieldValidator.setValidator(typeValidator);
        desc.setValidator(fieldValidator);

        return desc;
	}

	/**
	 * Create an XMLFieldDescriptorImpl for a field of type BigDecimal with validators attached. 
	 * <p>
	 * This "should" create an XMLFieldDescriptorImpl for any BigDecimal field as long as the getters/setters follow the naming 
	 * standards. If the method signature doesn't match "get + xmlName" or "set + xmlName", this will break. This should only be used on basic getters/setters.
	 * <p>
	 * The parameters may need to change if MinOccurs needs to be variable.
	 * <p>
	 * Defaults: setMinOccurs(1), setImmutable(true), setRequired(true), setMultivalued(false)
	 * 
	 * @param <T>
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param clazz
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param fieldName
	 * 			- the VO field name. (e.g. _MAPEndDate)
	 * @param xmlName
	 * 			- the XML name. (e.g. MAPEndDate). This is providing two uses, first for the descriptor, second for the method construction
	 * @param totalDigits
	 * 			- the total number of digits allowed
	 * @param fractionDigits
	 * 			- the number of fraction digits allowed
	 * @return - XMLFieldDescriptorImpl - the built XML descriptor.
	 */
	public static <T> XMLFieldDescriptorImpl createBigDecimalXmlFieldDescriptor(Class<T> clazz, String fieldName, final String xmlName, int totalDigits, int fractionDigits)
	{
		XMLFieldDescriptorImpl desc = createXmlFieldDescriptor(clazz, BigDecimal.class, fieldName, xmlName);
		
        //-- validation
		FieldValidator fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(1);
        DecimalValidator typeValidator = new DecimalValidator();
        typeValidator.setTotalDigits(totalDigits);
        typeValidator.setFractionDigits(fractionDigits);
        fieldValidator.setValidator(typeValidator);
        desc.setValidator(fieldValidator);
		
		return desc;
	}
	
	/**
	 * This "should" create an XMLFieldDescriptorImpl for any type of field as long as the getters/setters follow the naming 
	 * standards (i.e. getABCValue() is not the same as getAbcValue()). If the method signature doesn't match "get + xmlName" or "set + xmlName", this will break.
	 * <p>
	 * A validator will need to be set on the returned descriptor as it's not done here.  Wrappers are created for the commonly used validators (e.g. createStringXmlFieldDescriptor(...)).
	 * <p>
	 * Defaults: setImmutable(true), setRequired(true), setMultivalued(false)
	 * 
	 * @param <T>
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param clazz
	 * 			- the class the descriptor is being created for. (e.g. LifeVO.class)
	 * @param fieldClazz
	 * 			- the class of the field the descriptor is being created for. (e.g. String.class)
	 * @param fieldName
	 * 			- the VO field name. (e.g. _MAPEndDate)
	 * @param xmlName
	 * 			- the XML name. (e.g. MAPEndDate). This is providing two uses, first for the descriptor, second for the method construction
	 * @return - XMLFieldDescriptorImpl - the built XML descriptor.
	 */
	public static <T> XMLFieldDescriptorImpl createXmlFieldDescriptor(Class<T> clazz, final Class fieldClazz, String fieldName, final String xmlName) 
	{
        XMLFieldDescriptorImpl desc = null;
        XMLFieldHandler handler = null;

        desc = new XMLFieldDescriptorImpl(fieldClazz, fieldName, xmlName, NodeType.Element);
        handler = (new XMLFieldHandler() {
            public Object getValue(Object object) throws IllegalStateException
            {
                T target = (T) object;
                try {
					Method method = target.getClass().getMethod("get" + xmlName);
					return method.invoke(target);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
                return null;
            }
            public void setValue( Object object, Object value) throws IllegalStateException, IllegalArgumentException
            {
                try {
                    T target = (T) object;
					Method method = target.getClass().getMethod("set" + xmlName, fieldClazz);
					method.invoke(target, value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance(Object parent) {
            	if (fieldClazz == BigDecimal.class) {
            		return new BigDecimal(0);
            	} else {
            		return null;
            	}
            }
        } );
        desc.setHandler(handler);
        desc.setImmutable(true);
        desc.setRequired(true);
        desc.setMultivalued(false);
        
		return desc;
	}
		
}
