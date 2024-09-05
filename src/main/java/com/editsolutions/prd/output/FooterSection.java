package com.editsolutions.prd.output;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.editsolutions.prd.util.OutputUtils;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.PRDSettings;

public class FooterSection extends FileTemplateSection {
	
	private Object[] footerData;;

	public FooterSection(PRDSettings prdSettings, DataHeader dataHeader) {
		super(prdSettings, dataHeader);
	}

	public int getRecordCount() {
		return dataHeader.getPrdCount();
	}

	public Double getTotalDeductionAmount() {
		return dataHeader.getDeductionTotal();
	}
	
	public Object[] getFooterData() {
		return footerData;
	}

	@Override
	public StringBuilder getSection() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df2 = new DecimalFormat(".##");
		
		footerData = new Object[prdSettings.getFooterTemplate().getFileTemplateFields().size()];
		int i = 0;
		Iterator<FileTemplateField> footerIt = prdSettings.getFooterTemplate().getFileTemplateFields().iterator();
		while(footerIt.hasNext()) {
			FileTemplateField fileTemplateField = footerIt.next();
			String value = "";
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("CurrentDate")) {
				java.sql.Date currentDate = this.getCurrentDate();
				value = this.transformField(fileTemplateField, "'" + currentDate.toString() + "'");
			}  
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("RecordCount")) {
				Integer recordCount= this.getRecordCount();
				value = this.transformField(fileTemplateField, recordCount.toString());
			}  
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("RunningCount")) {
				Integer runningCount= this.getPrdSettings().getRunningCount();
				value = this.transformField(fileTemplateField, runningCount.toString());
			}  
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("TotalDeductionAmount")) {
				Double totalDeductionAmount = this.getTotalDeductionAmount();
				value = this.transformField(fileTemplateField, df2.format(totalDeductionAmount));
			}  
			if (fileTemplateField.getSourceField().getSqlFieldName().equals("FILLER")) {
				String defaultValue = "";
				if (fileTemplateField.getDefaultValue() != null) {
					defaultValue = fileTemplateField.getDefaultValue();
				}
				value = this.transformField(fileTemplateField, defaultValue);
			}
			footerData[i] = value;
			i++;
			if (fileTemplateField.getFieldLength() > 0) {
			    value = OutputUtils.padField(value, fileTemplateField);
			    sb.append(value);
			}
		}
		sb.append(System.getProperty("line.separator"));
		return sb;
	}

}
