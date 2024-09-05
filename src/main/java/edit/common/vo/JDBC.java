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

public class JDBC extends VOObject
  implements Serializable
{
  private String _driverClassName;
  private String _URL;
  private String _schemaName;
  private String _username;
  private String _password;
  private int _minPoolSize;
  private boolean _has_minPoolSize;
  private int _maxPoolSize;
  private boolean _has_maxPoolSize;
  private String _dialect;

  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if ((obj instanceof JDBC))
    {
      JDBC temp = (JDBC)obj;
      if (this._driverClassName != null) {
        if (temp._driverClassName == null) return false;
        if (!this._driverClassName.equals(temp._driverClassName))
          return false;
      }
      else if (temp._driverClassName != null) {
        return false;
      }if (this._URL != null) {
        if (temp._URL == null) return false;
        if (!this._URL.equals(temp._URL))
          return false;
      }
      else if (temp._URL != null) {
        return false;
      }if (this._schemaName != null) {
        if (temp._schemaName == null) return false;
        if (!this._schemaName.equals(temp._schemaName))
          return false;
      }
      else if (temp._schemaName != null) {
        return false;
      }if (this._username != null) {
        if (temp._username == null) return false;
        if (!this._username.equals(temp._username))
          return false;
      }
      else if (temp._username != null) {
        return false;
      }if (this._password != null) {
        if (temp._password == null) return false;
        if (!this._password.equals(temp._password))
          return false;
      }
      else if (temp._password != null) {
        return false;
      }if (this._minPoolSize != temp._minPoolSize)
        return false;
      if (this._has_minPoolSize != temp._has_minPoolSize)
        return false;
      if (this._maxPoolSize != temp._maxPoolSize)
        return false;
      if (this._has_maxPoolSize != temp._has_maxPoolSize)
        return false;
      if (this._dialect != null) {
        if (temp._dialect == null) return false;
        if (!this._dialect.equals(temp._dialect))
          return false;
      }
      else if (temp._dialect != null) {
        return false;
      }return true;
    }
    return false;
  }

  public String getDialect()
  {
    return this._dialect;
  }

  public String getDriverClassName()
  {
    return this._driverClassName;
  }

  public int getMaxPoolSize()
  {
    return this._maxPoolSize;
  }

  public int getMinPoolSize()
  {
    return this._minPoolSize;
  }

  public String getPassword()
  {
    return this._password;
  }

  public String getSchemaName()
  {
    return this._schemaName;
  }

  public String getURL()
  {
    return this._URL;
  }

  public String getUsername()
  {
    return this._username;
  }

  public boolean hasMaxPoolSize()
  {
    return this._has_maxPoolSize;
  }

  public boolean hasMinPoolSize()
  {
    return this._has_minPoolSize;
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

  public void setDialect(String dialect)
  {
    this._dialect = dialect;

    super.setVoChanged(true);
  }

  public void setDriverClassName(String driverClassName)
  {
    this._driverClassName = driverClassName;

    super.setVoChanged(true);
  }

  public void setMaxPoolSize(int maxPoolSize)
  {
    this._maxPoolSize = maxPoolSize;

    super.setVoChanged(true);
    this._has_maxPoolSize = true;
  }

  public void setMinPoolSize(int minPoolSize)
  {
    this._minPoolSize = minPoolSize;

    super.setVoChanged(true);
    this._has_minPoolSize = true;
  }

  public void setPassword(String password)
  {
    this._password = password;

    super.setVoChanged(true);
  }

  public void setSchemaName(String schemaName)
  {
    this._schemaName = schemaName;

    super.setVoChanged(true);
  }

  public void setURL(String URL)
  {
    this._URL = URL;

    super.setVoChanged(true);
  }

  public void setUsername(String username)
  {
    this._username = username;

    super.setVoChanged(true);
  }

  public static JDBC unmarshal(Reader reader)
    throws MarshalException, ValidationException
  {
    return (JDBC)Unmarshaller.unmarshal(JDBC.class, reader);
  }

  public void validate()
    throws ValidationException
  {
    Validator validator = new Validator();
    validator.validate(this);
  }
}