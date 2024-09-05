package fission.beans;


import java.util.HashMap;
import java.util.Map;

public class SessionBean extends PageBean {

	private Map pageBeans;
	private Map pageBeanGroup;
	
	public SessionBean() {
	
		init();
	}
	
	private final void init() {
	
		pageBeans = new HashMap();
		pageBeanGroup = new HashMap();
	}

	

	public void removeValue(String key) {
	
		getDisplayValues().remove(key);	
	}
	
	public void removeValues(String key) {
	
		getDisplayValues().remove(key);			
	}

	public void removeAllValues() {
	
		getDisplayValues().clear();
	}
	
	public PageBean getPageBean(String key) {	
		
		if (pageBeans.get(key) == null) {
		
			pageBeans.put(key, new PageBean());
		}
		
		return (PageBean) pageBeans.get(key);
	}
	
	public boolean pageBeanExists(String key) {
	
		return pageBeans.containsKey(key);
	}
	
	public void putPageBean(String key, PageBean pageBean) {
	
		pageBeans.put(key, pageBean);
	}
	
//	public void putPageBeans(String key, PageBean pageBean) {
//	
//		List group = (List) pageBeanGroup.get(key);
//		
//		if (group == null) {
//		
//			group = new ArrayList();
//			
//			pageBeanGroup.put(key, group);
//		}
//		
//		group.add(pageBean);	
//	}
	
//	public PageBean[] getPageBeans(String key) {	
//		
//		List group = (List) pageBeanGroup.get(key);
//		
//		if (group != null) {
//		
//			return (PageBean[]) group.toArray(new PageBean[group.size()]);
//		}	
//		else {
//		
//			return null;
//		}		
//	}
	
	public void removePageBean(String key) {
	
		pageBeans.remove(key);
	}
	
	public Map getPageBeans() {
	
		return pageBeans;	
	}
	
	public void clearState() {
	
		super.clearState();	
		pageBeans.clear();
		pageBeanGroup.clear();	
	}
	
	public boolean hasPageBeans() {
	
		return ! pageBeans.isEmpty();
	}


}