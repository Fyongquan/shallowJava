package org.example.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.example.bean.Constants;
import org.example.bean.FieldInfo;
import org.example.bean.TableInfo;
import org.example.utils.DateUtils;
import org.example.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 *
 */
public class BuildPo {
    public static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanName() + ".java");

        try (OutputStream out = new FileOutputStream(poFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw)) {

            bw.write("package " + Constants.PACKAGE_PO + ";");
            bw.newLine();
            bw.newLine();

            bw.write("import java.io.Serializable;");
            bw.newLine();

            if(tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATE_PARSE_CLASS);
                bw.newLine();
            }
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATE_PARSE_CLASS);
                bw.newLine();
            }
            boolean haveJsonIgnore = false;
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(!haveJsonIgnore && ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), fieldInfo.getPropertyName())){
                    haveJsonIgnore = true;
                    bw.write(Constants.IGNORE_BEAN_TOJSON_CLASS);
                    bw.newLine();
                }
            }
            bw.newLine();

            //构建类注释
            BuildComment.createClassComment(bw,tableInfo.getComment());

            bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
            bw.newLine();

            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                BuildComment.createFieldComment(bw,fieldInfo.getComment());

                if(ArrayUtils.contains(Constants.SQL_DATA_TIME_TYPES, fieldInfo.getSqlType())){
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.DEFAULT_DATETIME_FORMAT));
                    bw.newLine();

                    bw.write("\t" + String.format(Constants.BEAN_DATE_PARSE_EXPRESSION, DateUtils.DEFAULT_DATETIME_FORMAT));
                    bw.newLine();
                }
                if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())){
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.DEFAULT_DATE_FORMAT));
                    bw.newLine();

                    bw.write("\t" + String.format(Constants.BEAN_DATE_PARSE_EXPRESSION, DateUtils.DEFAULT_DATE_FORMAT));
                    bw.newLine();
                }
                if(ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), fieldInfo.getPropertyName())){
                    bw.write("\t" + Constants.IGNORE_BEAN_TOJSON_EXPRESSION);
                    bw.newLine();
                }

                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
            }

            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
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
            }

            //重写toString()方法
            bw.write("\t@Override");
            bw.newLine();
            bw.write("\tpublic String toString() {");
            bw.newLine();
            bw.write("\t\treturn ");

            Integer index = 0;
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(index > 0) {
                    bw.write(" + \", " + fieldInfo.getComment() + ":\" + (" + fieldInfo.getPropertyName() + " == null ? \"空\" : " + fieldInfo.getPropertyName() + ")");
                }else{
                    index++;
                    bw.write("\"" + fieldInfo.getComment() + ":\" + (" + fieldInfo.getPropertyName() + " == null ? \"空\" : " + fieldInfo.getPropertyName() + ")");
                }

            }
            bw.write(";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();

            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            logger.info("创建po失败", e);
        }
    }
}
