package com.editsolutions.web.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ExposablePropertyPaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private Map<String, String> resolvedProps;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		resolvedProps = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			resolvedProps.put(keyStr, (String)props.get(keyStr));
		}
	}

	public Map<String, String> getResolvedProps() {
		return Collections.unmodifiableMap(resolvedProps);
	}

	public String getProperty(String key) {
		return (resolvedProps.containsKey(key)) ? resolvedProps.get(key)
				: "null";
	}

}
