package org.example.bean;

import org.example.utils.PropertiesUtils;

public class Constants {
    public static Boolean IGNORE_TABLE_PERFIX;

    public static String SUFFIX_BEAN_PARAM;
    static{
        IGNORE_TABLE_PERFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
    }
}
