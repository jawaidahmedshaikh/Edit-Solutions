package com.editsolutions.prd.output;

import java.util.Iterator;

import com.editsolutions.prd.util.OutputUtils;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.PRDSettings;

public class HeaderSection extends FileTemplateSection {

	public HeaderSection(PRDSettings prdSettings, DataHeader dataHeader) {
		super(prdSettings, dataHeader);
	}

	String groupNumber;

	public String getGroupNumber() {
		return prdSettings.getGroupSetup().getGroupNumber();
	}
	
	public StringBuilder getSection() {
	    StringBuilder sb = new StringBuilder();
		Iterator<FileTemplateField> headerIt = prdSettings.getHeaderTemplate().getFileTemplateFields().iterator();
		while(headerIt.hasNext()) {
			FileTemplateField fileTemplateField = headerIt.next();
			String value = "";
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("CurrentDate")) {
				java.sql.Date currentDate = this.getCurrentDate();
			   value = this.transformField(fileTemplateField,"'" +  currentDate.toString() + "'" );
			} else 
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("RunningCount")) {
				Integer runningCount= this.getPrdSettings().getRunningCount();
				value = this.transformField(fileTemplateField, runningCount.toString());
			} else 
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("MarketCode")) {
				String marketCode = this.getGroupNumber();
				value = this.transformField(fileTemplateField, marketCode);
			} else 
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("DeductionDate")) {
				java.sql.Date deductionDate = this.getDeductionDate();
				value = this.transformField(fileTemplateField, "'" + deductionDate.toString() + "'");
			} else 
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("FILLER")) {
				String defaultValue = "";
				if (fileTemplateField.getDefaultValue() != null) {
					defaultValue = fileTemplateField.getDefaultValue();
				}
				value = this.transformField(fileTemplateField, defaultValue);
			}
			value = OutputUtils.padField(value, fileTemplateField);
			sb.append(value);
			if (prdSettings.getTypeCT().equals("CSV")) {
				sb.append(",");
			}
		}
		sb.append(System.getProperty("line.separator"));
	 return sb;
	}


}
