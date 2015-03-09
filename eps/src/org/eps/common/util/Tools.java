package org.eps.common.util;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class Tools {
	
    public static Long[] split2Array(String str, String delimiter) {
    	if (StringUtils.isBlank(str)) {
    		return null;
    	}
    	
    	StringTokenizer st = new StringTokenizer(str, delimiter);
    	
    	Long[] ids = new Long[st.countTokens()];
    	
    	int index = 0;
    	
    	while (st.hasMoreElements()) {
    		long value = Long.parseLong(st.nextElement().toString());
    		
    		ids[index] = value;
    		
    		index++;
    	}
    	
    	return ids;
    }
    
    public static Long[] split2ArrayDistinct(String str, String delimiter) {
    	Long[] ids = split2Array(str, delimiter);
    	
    	Set<Long> set = new HashSet<Long>();
    	
    	for (Long id : ids) {
    		set.add(id);
    	}
    	
    	return set.toArray(new Long[0]);
    }

    public static String formatSkillLevel(Long level) {
    	String str = StringUtils.EMPTY;
    	
    	switch (level.intValue()) {
    		case 1:
    			str = "һ��";
    			break;
    		case 2:
    			str = "����";
    			break;
    		case 3: 
    			str = "����";
    			break;
    		case 4:
    			str = "�ļ�";
    			break;
    		case 5:
    			str = "�弶";
    			break;
    	}
    	
    	return str;
    }
    
    public static String formatEvaluateLevel(Long level) {
    	String str = StringUtils.EMPTY;
    	
    	switch (level.intValue()) {
    		case 1:
    			str = "��";
    			break;
    		case 2:
    			str = "��";
    			break;
    		case 3:
    			str = "��";
    			break;
    	}
    	
    	return str;
    }

}
