package fission.beans;


import java.lang.reflect.Method;
import java.util.*;

public class PageBean implements java.io.Serializable  {

	private Map displayValues = new HashMap();
								  
	private static final String DEFAULT_DELIMITER = " ";
	
	private static final String  DEFAULT_DISPLAY_VALUE   = "";	
	private static final String[] DEFAULT_DISPLAY_VALUES = new String[0];	

	public PageBean() {
	
		init();	
	}	
	

//*******************************      
//  	  Public Methods            
//*******************************	

	public void putValue(String key, String value) {

        if (value != null) {

            displayValues.put(key, value.trim());
        }
	}
	
	public void putValues(String key, Object[] recs, String[] methodNames, String delimiter) {
	
		String arrayEntry = "";
		String[] values = new String[recs.length];
	
		try  {
	
			for (int i = 0; i < recs.length; i++) {
			
				Class c = recs[i].getClass();
			
				for (int j = 0; j < methodNames.length; j++) {				
								
					Method m = c.getMethod(methodNames[j], null);
					
					Object obj = m.invoke(recs[i], null);
					
					if (obj != null) {
					
						arrayEntry += obj.toString();
						
						if (delimiter != null) {
						
							arrayEntry += delimiter;
						}
						else  {
						
							// default delimiter is a space
							arrayEntry += DEFAULT_DELIMITER;
						}								
					}
				}
				
				values[i] = arrayEntry.substring(0, arrayEntry.length() - 1);
				
				arrayEntry = "";
			}			
			
			displayValues.put(key, values);
		}
		catch(Exception e) {
		
			System.out.println(e.getMessage());			
		}	
	} 
	
	public void putValues(String key, String[] values) {
	
		displayValues.put(key, values);	
	}

	public String getValue(String key) {
	
		String value = (String) displayValues.get(key);
		
		
		return (value == null)? DEFAULT_DISPLAY_VALUE: value;
	}
	
	public String[] getValues(String key) {
	
		String[] values = (String[]) displayValues.get(key);
	
		return (values == null)? DEFAULT_DISPLAY_VALUES: values;
	}
	
	public boolean existsInValues(String key, String value) {
	
		String[] values = (String[]) displayValues.get(key);
		
		if (values != null)  {
		
			for (int i = 0; i < values.length; i++) {
				
				if (value.equals(values[i]) ) {
				
					return true;
				}		
			}		
		}
		
		return false;
	} 
	
	private final void init() {
	
	
		displayValues = new HashMap();
	}
	
	public Map getDisplayValues() {
	
		return displayValues;
	}

    public void addToValues(PageBean pageBean)
    {
        displayValues.putAll(pageBean.getDisplayValues());
    }

	public void addToValues(String key, String value) {
	
		String[] oldValues = getValues(key);
		
		if (oldValues == null) {
		
			oldValues =  new String[0];		
		}		
		
		/* we have to get rid of checking for entering duplicate values becos
		more than one rider instance can be added in the riderSelection */
		
    	String[] newValues = new String[oldValues.length + 1];
		
		System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
		
		newValues[newValues.length - 1] = value.trim();
		
		getDisplayValues().put(key, newValues);			
		
		
	}



	public void addToValues(String key, String value, int index) {
	
		List storage = new ArrayList();
		
		String[] values = getValues(key);
		
		for (int i = 0; i < values.length; i++) {
		
			storage.add(values[i]);
		}		
		
		storage.add(0, value);
		
		putValues(key, (String[]) storage.toArray(new String[storage.size()]));				
	}
		
	
	public void removeFromValues(String key, String value) {

		List storage = new ArrayList();
			
		String[] values = getValues(key);		
		
		for (int i = 0; i < values.length; i++) {
		
			storage.add(values[i]);
		}
		
		storage.remove(value);
		
		putValues(key, (String[]) storage.toArray(new String[storage.size()]));		
	}
	
	public void removeValue(String key) {
	
		if (key != null) {
		
			displayValues.remove(key);
		}	
	}
	
	public void removeFromValues(String key, int index)  {
	
		// index starts from 0 (zero).
	
		List storage = new ArrayList();
		
		String[] values = getValues(key);
		
		for (int i = 0; i < values.length; i++) {
		
			storage.add(values[i]);
		}
		
		if (index < storage.size()) {		
		
			storage.remove(index);
		
			putValues(key, (String[]) storage.toArray(new String[storage.size()]));	
		}
	}
	
	
	private boolean valueExists(String value, String[] values) {
	
		for (int i = 0; i < values.length; i++) {
		
			if (values[i].equals(value)) {
			
				return true;
			}
		}
		
		return false;	
	}
	
	public void clearState() {
	
		displayValues.clear();	
	}
	
	public String toString() {
	
		String s = "";		
		
		Iterator keys = displayValues.keySet().iterator();
		
		while (keys.hasNext()) {
		
			String key = (String) keys.next();
			
			Object obj = displayValues.get(key);
			
			if (obj instanceof String)  {
			
				String value = (String) obj;
			
				s += key + " : " + value + "\n";		
			}
			else if (obj instanceof Object[]) {
			
				String[] values = (String[]) obj;				
				
				s += key + ":" + "\n";
				
				for (int i = 0; i < values.length; i++) {
				
					s += "\t" + values[i] + "\n";				
				}				
			}
		}
		
		return s;	
	}	
	
	public boolean hasDisplayValues() {
	
		return ! displayValues.isEmpty();
	}
}