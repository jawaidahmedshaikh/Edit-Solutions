package com.editsolutions.prd.output;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SourceField;
import com.editsolutions.prd.vo.Transformation;

import edit.services.db.hibernate.SessionHelper;

public abstract class FileTemplateSection {

	public FileTemplateSection(PRDSettings prdSettings, DataHeader dataHeader) {
		super();
		this.prdSettings = prdSettings;
		this.dataHeader = dataHeader;
	}

	PRDSettings prdSettings;
	DataHeader dataHeader;

	public PRDSettings getPrdSettings() {
		return prdSettings;
	}

	public void setPrdSettings(PRDSettings prdSettings) {
		this.prdSettings = prdSettings;
	}

	public DataHeader getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}

	public Date getCurrentDate() {
		return new Date(Calendar.getInstance().getTimeInMillis());
	}


	public Date getDeductionDate() {
		return dataHeader.getPrdDate();
	}
	
	public String transformField(FileTemplateField fileTemplateField, String field) {
		if (fileTemplateField.getTransformation() == null) {
			return field;
		}
		Transformation transformation = fileTemplateField.getTransformation();
		SourceField sourceField = fileTemplateField.getSourceField();
		String transformSQL = transformation.getSql().replaceAll("\\[X\\]", field );
		StringBuilder sql = new StringBuilder();
		sql.append("Select ").append(transformSQL);

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		Statement statement;
		ResultSet resultSet;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e.toString());
		}
		return sourceField.getSqlFieldName();
	}
	
	public abstract StringBuilder getSection();

}
