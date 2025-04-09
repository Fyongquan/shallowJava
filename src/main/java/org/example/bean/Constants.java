package org.example.bean;

import org.example.utils.PropertiesUtils;

public class Constants {
    public static Boolean IGNORE_TABLE_PERFIX;

    public static String SUFFIX_BEAN_PARAM;
    static{
        IGNORE_TABLE_PERFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
    }

    public final static String[] SQL_DATA_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public final static String[] SQL_STRING_TYPE = new String[]{"varchar", "char", "text","mediumtext","longtext"};
    public final static String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};

}
