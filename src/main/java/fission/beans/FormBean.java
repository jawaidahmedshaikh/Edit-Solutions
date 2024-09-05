package fission.beans;

import java.util.*;

public class FormBean extends PageBean {


//*******************************      
//          Variables            
//*******************************

	private Map parametersInError;
	
	private static final String ERROR_SYMBOL   = "!";
	private static final String NO_ERROR_SYMBOL = "";
	
	
//*******************************      
//          Constructors            
//*******************************	

	public FormBean() {
	
		init();
	}


//*******************************      
//          Public Methods            
//*******************************

	// This will ultimately be abstract... and must be implemented
	// by each Form submission (Java or Cobol client)
	public boolean validate() {
	
		return true;
	}

	public String[] getParameterNamesInError() {
	
		Iterator keys = parametersInError.keySet().iterator();
		
		List names = new ArrayList();
		
		while (keys.hasNext()) {
		
			names.add(keys.next().toString());
		}
		
		return (String[]) names.toArray(new String[names.size()]);			
	}
	
	public String checkForError(String key) {
	
		if (parametersInError.containsKey(key)) {
		
			return ERROR_SYMBOL;
		}
		else {
		
			return NO_ERROR_SYMBOL;
		}
	}	
	
	
//*******************************      
//          Private Methods            
//*******************************	
	
	private final void init() {
	
		parametersInError = new HashMap();
	}
	
	private void addParameterInError(String name, String value) {
	
		parametersInError.put(name, value);
	}
}