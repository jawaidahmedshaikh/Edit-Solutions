package edit.common.vo;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.Validator;
import org.xml.sax.ContentHandler;

public class EDITService extends VOObject
  implements Serializable
{
  private JDBC _JDBC;
  private String _serviceName;
  private Hibernate _hibernate;

  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if ((obj instanceof EDITService))
    {
      EDITService temp = (EDITService)obj;
      if (this._JDBC != null) {
        if (temp._JDBC == null) return false;
        if (!this._JDBC.equals(temp._JDBC))
          return false;
      }
      else if (temp._JDBC != null) {
        return false;
      }if (this._serviceName != null) {
        if (temp._serviceName == null) return false;
        if (!this._serviceName.equals(temp._serviceName))
          return false;
      }
      else if (temp._serviceName != null) {
        return false;
      }if (this._hibernate != null) {
        if (temp._hibernate == null) return false;
        if (!this._hibernate.equals(temp._hibernate))
          return false;
      }
      else if (temp._hibernate != null) {
        return false;
      }return true;
    }
    return false;
  }

  public Hibernate getHibernate()
  {
    return this._hibernate;
  }

  public JDBC getJDBC()
  {
    return this._JDBC;
  }

  public String getServiceName()
  {
    return this._serviceName;
  }

  public boolean isValid()
  {
    try
    {
      validate();
    }
    catch (ValidationException localValidationException) {
      return false;
    }
    return true;
  }

  public void marshal(Writer out)
    throws MarshalException, ValidationException
  {
    Marshaller.marshal(this, out);
  }

  public void marshal(ContentHandler handler)
    throws IOException, MarshalException, ValidationException
  {
    Marshaller.marshal(this, handler);
  }

  public void setHibernate(Hibernate hibernate)
  {
    this._hibernate = hibernate;

    super.setVoChanged(true);
  }

  public void setJDBC(JDBC JDBC)
  {
    this._JDBC = JDBC;

    super.setVoChanged(true);
  }

  public void setServiceName(String serviceName)
  {
    this._serviceName = serviceName;

    super.setVoChanged(true);
  }

  public static EDITService unmarshal(Reader reader)
    throws MarshalException, ValidationException
  {
    return (EDITService)Unmarshaller.unmarshal(EDITService.class, reader);
  }

  public void validate()
    throws ValidationException
  {
    Validator validator = new Validator();
    validator.validate(this);
  }
}