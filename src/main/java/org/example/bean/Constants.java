package org.example.bean;

import org.example.utils.PropertiesUtils;

public class Constants {
    public static String AUTHER;
    public static Boolean IGNORE_TABLE_PERFIX;

    public static String SUFFIX_BEAN_QUERY;

    public static String SUFFIX_BEAN_QUERY_FUZZY;

    public static String SUFFIX_BEAN_QUERY_DATE_START;

    public static String SUFFIX_BEAN_QUERY_DATE_END;

    public static String SUFFIX_MAPPER;

    public static String SUFFIX_SERVICE;

    public static String SUFFIX_SERVICEIMPL;

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
    public static String PATH_JAVA;

    public static String PATH_RESOURCES;

    public static String PACKAGE_BASE;

    public static String PACKAGE_PO;

    public static String PACKAGE_QUERY;

    public static String PACKAGE_VO;

    public static String PACKAGE_UTILS;

    public static String PACKAGE_ENUMS;

    public static String PACKAGE_MAPPER;

    public static String PACKAGE_SERVICE;

    public static String PACKAGE_SERVICEIMPL;

    public static String PATH_PO;

    public static String PATH_QUERY;

    public static String PATH_VO;

    public static String PATH_UTILS;

    public static String PATH_ENUMS;

    public static String PATH_MAPPER;

    public static String PATH_MAPPER_XML;

    public static String PATH_SERVICE;

    public static String PATH_SERVICEIMPL;

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
        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_DATE_START = PropertiesUtils.getString("suffix.bean.query.date.start");
        SUFFIX_BEAN_QUERY_DATE_END = PropertiesUtils.getString("suffix.bean.query.date.end");
        SUFFIX_MAPPER = PropertiesUtils.getString("suffix.mapper");
        SUFFIX_SERVICE = PropertiesUtils.getString("suffix.service");
        SUFFIX_SERVICEIMPL = PropertiesUtils.getString("suffix.service.impl");

        PACKAGE_BASE = PropertiesUtils.getString("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");
        PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.vo");
        PACKAGE_UTILS = PACKAGE_BASE + '.' + PropertiesUtils.getString("package.utils");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");
        PACKAGE_MAPPER = PACKAGE_BASE + "." + PropertiesUtils.getString("package.mapper");
        PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service");
        PACKAGE_SERVICEIMPL = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service.impl");

        PATH_BASE = PropertiesUtils.getString("path.base");
        PATH_JAVA = PATH_BASE + "java";
        PATH_RESOURCES = PATH_BASE + "resources";
        PATH_PO = PATH_JAVA + "/" + PACKAGE_PO.replace(".", "/");
        PATH_QUERY = PATH_JAVA + "/" + PACKAGE_QUERY.replace(".", "/");
        PATH_VO = PATH_JAVA + "/" + PACKAGE_VO.replace(".", "/");
        PATH_UTILS = PATH_JAVA + "/" + PACKAGE_UTILS.replace(".", "/");
        PATH_ENUMS = PATH_JAVA + "/" + PACKAGE_ENUMS.replace(".", "/");
        PATH_MAPPER = PATH_JAVA + "/" + PACKAGE_MAPPER.replace(".", "/");
        PATH_MAPPER_XML = PATH_RESOURCES + "/" + PropertiesUtils.getString("package.mapper").replace(".", "/");
        PATH_SERVICE = PATH_JAVA + "/" + PACKAGE_SERVICE.replace(".", "/");
        PATH_SERVICEIMPL = PATH_JAVA + "/" + PACKAGE_SERVICEIMPL.replace(".", "/");
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
