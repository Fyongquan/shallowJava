package org.example.bean;

import org.example.utils.PropertiesUtils;

public class Constants {
    public static Boolean IGNORE_TABLE_PERFIX;

    public static String SUFFIX_BEAN_PARAM;

    //需要忽略的属性
    public static String IGNORE_BEAN_TOJSON_FIELD;
    public static String IGNORE_BEAN_TOJSON_CLASS;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;

    //日期序列化，反序列化
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_PARSE_CLASS;
    public static String BEAN_DATE_PARSE_EXPRESSION;

    public static String PATH_BASE;

    public static String PACKAGE_BASE;

    public static String PATH_PO;

    public static String PACKAGE_PO;

    public static String AUTHER;

    static{
        IGNORE_BEAN_TOJSON_FIELD = PropertiesUtils.getString("ignore.bean.toJson.field");
        IGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.toJson.class");
        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.toJson.expression");

        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_PARSE_CLASS = PropertiesUtils.getString("bean.date.parse.class");
        BEAN_DATE_PARSE_EXPRESSION = PropertiesUtils.getString("bean.date.parse.expression");

        AUTHER = PropertiesUtils.getString("author");

        IGNORE_TABLE_PERFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");

        PACKAGE_BASE = PropertiesUtils.getString("package.base");

        PATH_BASE = PropertiesUtils.getString("path.base") + PACKAGE_BASE;
        PATH_BASE = PATH_BASE.replace(".", "/");

        PATH_PO = PATH_BASE + "/" + PropertiesUtils.getString("package.po").replace(".", "/");

        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
    }

    public final static String[] SQL_DATA_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public final static String[] SQL_STRING_TYPE = new String[]{"varchar", "char", "text","mediumtext","longtext"};
    public final static String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};

    public static void main(String[] args){
        System.out.println(PACKAGE_BASE);
        System.out.println(PATH_BASE);
        System.out.println(PATH_PO);
        System.out.println(PACKAGE_PO);
    }

}
