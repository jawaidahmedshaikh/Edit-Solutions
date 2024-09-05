package com.selman.calcfocus.util;

import java.util.List;

import com.selman.calcfocus.request.Policy;

public class BadDataException extends RuntimeException {

	private static final long serialVersionUID = -6573989350360334733L;
	Policy policy;
	
    public BadDataException(List<String> messages) {
	    super(messages.toString());
    }

    public BadDataException(List<String> messages, Policy policy)  {
	    super(messages.toString());
	    this.policy = policy;
    }
    public BadDataException(List<String> messages, Throwable cause) {
	    super(messages.toString(), cause);
    }

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
    
    


}
