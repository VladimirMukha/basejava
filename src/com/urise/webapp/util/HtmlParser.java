package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class HtmlParser {
    public static  String htmlDateHelper(Organization.Experience localDate){
        return localDate.getStartDate()+" по "+ localDate.getEndDate();
    }
}
