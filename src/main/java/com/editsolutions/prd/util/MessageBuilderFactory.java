package com.editsolutions.prd.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.editsolutions.prd.service.MessageTemplateFieldService;
import com.editsolutions.prd.service.MessageTemplateFieldServiceImpl;
import com.editsolutions.prd.vo.Message;
import com.editsolutions.prd.vo.MessageTemplateField;
import com.editsolutions.prd.vo.Transformation;

import edit.services.db.hibernate.SessionHelper;

public class MessageBuilderFactory {
	
	public static String testSectionTransformation(Transformation transformation, String value) {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		Statement statement;
		ResultSet resultSet;
        String fieldString = transformation.getSql().replaceFirst("\\[X\\]", "'" + value + "'");
		String sql = "SELECT " + fieldString; 
	
	    try {
	        statement = connection.createStatement();
	        resultSet = statement.executeQuery(sql);
	        if (resultSet.next()) {
	        	return resultSet.getString(1);
	        }
	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	    	throw new DAOException(e.toString());
	    }
		return null;
	}
	
	public static String testTransformation(MessageTemplateField messageTemplateField, Transformation transformation, String groupNumber) {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		Statement statement;
		ResultSet resultSet;
        String fieldString = transformation.getSql().replaceFirst("\\[X\\]", messageTemplateField.getFieldName());
		String sql = "SELECT " + fieldString + " FROM " + messageTemplateField.getTableName() + " WHERE GroupNumber = '" + groupNumber + "'" ;
	
	    if (messageTemplateField.getTableName().equals("vw_PRD_Setup")) {
	        fieldString = transformation.getSql().replaceFirst("\\[X\\]", "p." + messageTemplateField.getFieldName());
            sql = "SELECT " + fieldString + "  FROM " + messageTemplateField.getTableName() + " p, vw_PRD_Group g " + 
	        " WHERE p.Group_ContractGroupFK = g.Group_ContractGroupPK and g.groupNumber = '" + groupNumber + "'";
	    }
		
	    try {
	        statement = connection.createStatement();
	        resultSet = statement.executeQuery(sql);
	        if (resultSet.next()) {
	        	return resultSet.getString(1);
	        }
	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	    	throw new DAOException(e.toString());
	    }
		return null;
	}

	public static Message buildMessage(Message message, String groupNumber)  {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		Statement statement;
		ResultSet resultSet;
		
		MessageTemplateFieldService messageTemplateFieldService = new MessageTemplateFieldServiceImpl();
		String preMessage = message.getPreMessage();
		String postMessage = message.getPreMessage();
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
		Matcher matcher = pattern.matcher(preMessage);
		List<String> tableField = new ArrayList<>();
		List<String> toBeReplaced = new ArrayList<>();
		while (matcher.find()) {
		    tableField.add(matcher.group(1));	
		    toBeReplaced.add("\\{" + matcher.group(1) + "\\}");	
		}
		for (int i = 0; i < tableField.size(); i++) {
			String beforeSplit = tableField.get(i);
		    String[] tableFieldPair = beforeSplit.split("\\."); 	

		    MessageTemplateField messageTemplateField = 
		    		messageTemplateFieldService.getMessageTemplateField(tableFieldPair[0], tableFieldPair[1]);
		    
		    
		    String sql;
		    if (tableFieldPair[0].equals("vw_PRD_Group")) {
		        if (messageTemplateField.getTransformation() != null) {
		    	    tableFieldPair[1] = messageTemplateField.getTransformation().getSql().replaceFirst("\\[X\\]", tableFieldPair[1]);
		        } 
		        //else {
                     sql = "SELECT " + tableFieldPair[1] + " FROM " + tableFieldPair[0] + " WHERE groupNumber = '" + groupNumber + "'";
		        //}
		    } else { 
		        if (messageTemplateField.getTransformation() != null) {
		    	    tableFieldPair[1] = messageTemplateField.getTransformation().getSql().replaceFirst("\\[X\\]", "p." + tableFieldPair[1]);
                    sql = "SELECT " + tableFieldPair[1] + "  FROM " + tableFieldPair[0] + " p, vw_PRD_Group g " + 
		            " WHERE p.Group_ContractGroupFK = g.Group_ContractGroupPK and g.groupNumber = '" + groupNumber + "'";

		        }  else {
                    sql = "SELECT p." + tableFieldPair[1] + "  FROM " + tableFieldPair[0] + " p, vw_PRD_Group g " + 
		            " WHERE p.Group_ContractGroupFK = g.Group_ContractGroupPK and g.groupNumber = '" + groupNumber + "'";
		        }
		    }
            //System.out.println(sql);
		    
		    try {
		        statement = connection.createStatement();
		        resultSet = statement.executeQuery(sql);
		        if (resultSet.next()) {
		        	if (resultSet.getString(1) != null) {
		        	    postMessage = postMessage.replaceFirst(toBeReplaced.get(i), resultSet.getString(1));
		        	} else {
		        	    postMessage = postMessage.replaceFirst(toBeReplaced.get(i), "");
		        	}
		        }
		    } catch (SQLException e) {
		    	System.out.println(e.toString());
		    }


		}
		message.setPostMessage(postMessage);

		return message;
		
	}
	

}
