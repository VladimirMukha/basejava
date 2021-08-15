package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class HtmlUtil {
    public static String htmlDateHelper(Organization.Experience localDate) {
        return DateUtil.format(localDate.getStartDate()) + " по " + DateUtil.format(localDate.getEndDate());
    }
    public static boolean isEmpty(String value){
       return value==null||value.trim().length()==0;
    }
}