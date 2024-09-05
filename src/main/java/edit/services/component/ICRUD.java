/*
 * Component.java      Version 1.1  10/26/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package edit.services.component;

import java.io.Serializable;
import java.util.List;
    
public interface ICRUD extends Serializable {

    public abstract long createOrUpdateVO(Object voObject, boolean recursively) throws Exception;

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception;

    public abstract Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception;
}