package edit.services.db.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * A custom dialect to bring date/time arithmetic to hibernate
 * @author Dan Garant
 */
public class SQLServer2008Dialect extends SQLServerDialect {

	public SQLServer2008Dialect() {
		super();
		this.registerFunction("dateadd", new StandardSQLFunction("dateadd", Hibernate.TIMESTAMP));
	}
	
}
