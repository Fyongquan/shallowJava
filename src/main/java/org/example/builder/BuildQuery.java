package org.example.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.example.bean.Constants;
import org.example.bean.FieldInfo;
import org.example.bean.TableInfo;
import org.example.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 创建Query实体
 */
public class BuildQuery {
    public static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;

        File poFile = new File(folder, className + ".java");

        try (OutputStream out = new FileOutputStream(poFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw)) {

            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();

            if (tableInfo.getHaveDateTime()) {
                bw.write("import java.time.LocalDateTime;");
                bw.newLine();
            }
            if (tableInfo.getHaveDate()) {
                bw.write("import java.time.LocalDate;");
                bw.newLine();
            }
            if (tableInfo.getHaveBigDecimal()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATE_PARSE_CLASS);
                bw.newLine();
            }

            //构建类注释
            BuildComment.createClassComment(bw, tableInfo.getComment() + "查询对象");

            bw.write("public class " + className + " {");
            bw.newLine();

            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                BuildComment.createFieldComment(bw, fieldInfo.getComment());

                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();

                //String类型的参数
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ";");
                    bw.newLine();
                    bw.newLine();
                }

                //日期类型的参数
                if (ArrayUtils.contains(Constants.SQL_DATA_TIME_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    bw.write("\tprivate String " + " " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_START + ";");
                    bw.newLine();

                    bw.write("\tprivate String " + " " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_END + ";");
                    bw.newLine();
                    bw.newLine();
                }
            }

            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String tempFieldPropertyName = StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName());
                bw.write("\tpublic void set" + tempFieldPropertyName + "(" + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ") {");
                bw.newLine();
                bw.write("\t\tthis." + fieldInfo.getPropertyName() + " = " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                bw.write("\tpublic " + fieldInfo.getJavaType() + " get" + tempFieldPropertyName + "() {");
                bw.newLine();
                bw.write("\t\treturn " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                //String类型的参数
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    String tempFieldPropertyNameFuzzy = StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()) + Constants.SUFFIX_BEAN_QUERY_FUZZY;
                    bw.write("\tpublic void set" + tempFieldPropertyNameFuzzy + "(" + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ") {");
                    bw.newLine();
                    bw.write("\t\tthis." + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + " = " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tpublic " + fieldInfo.getJavaType() + " get" + tempFieldPropertyNameFuzzy + "() {");
                    bw.newLine();
                    bw.write("\t\treturn " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();
                }

                //日期类型的参数
                if (ArrayUtils.contains(Constants.SQL_DATA_TIME_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    String tempFieldPropertyNameStart = StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()) + Constants.SUFFIX_BEAN_QUERY_DATE_START;
                    bw.write("\tpublic void set" + tempFieldPropertyNameStart + "(String " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_START + ") {");
                    bw.newLine();
                    bw.write("\t\tthis." + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_START + " = " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_START + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tpublic String get" + tempFieldPropertyNameStart + "() {");
                    bw.newLine();
                    bw.write("\t\treturn " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_START + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();

                    String tempFieldPropertyNameEND = StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()) + Constants.SUFFIX_BEAN_QUERY_DATE_END;
                    bw.write("\tpublic void set" + tempFieldPropertyNameEND + "(String " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_END + ") {");
                    bw.newLine();
                    bw.write("\t\tthis." + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_END + " = " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_END + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tpublic String get" + tempFieldPropertyNameEND + "() {");
                    bw.newLine();
                    bw.write("\t\treturn " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_DATE_END + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();
                }
            }

            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            logger.info("创建Query失败", e);
        }
    }
}
