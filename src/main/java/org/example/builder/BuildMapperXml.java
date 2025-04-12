package org.example.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.example.bean.Constants;
import org.example.bean.FieldInfo;
import org.example.bean.TableInfo;
import org.example.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建Mapper
 */
public class BuildMapperXml {
    public static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

    private static final String BASE_COLUMN_LIST = "base_column_list";

    private static final String QUERY_CONDITION = "query_condition";

    private static final String BASE_QUERY_CONDITION = "base_query_condition";

    private static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";

    private static final String BASE_CONDITION = "base_condition";

    private static final String BASE_RESULT_MAP = "base_result_map";

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_MAPPER_XML);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPER;

        File poFile = new File(folder, className + ".xml");

        try (OutputStream out = new FileOutputStream(poFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw)) {

            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPER + "." + className + "\">");
            bw.newLine();
            bw.newLine();

            bw.write("\t<!-- 实体映射 -->");
            bw.newLine();
            String poClass = Constants.PACKAGE_PO + "." + tableInfo.getBeanName();
            bw.write("\t<resultMap id=\"base_result_map\" type=\"" + poClass + "\">");
            bw.newLine();

            FieldInfo idField = null;
            Map<String, List<FieldInfo>> key = tableInfo.getKeyIndexMap();
            for (Map.Entry<String, List<FieldInfo>> entry : key.entrySet()) {
                if ("PRIMARY".equals(entry.getKey())) {
                    List<FieldInfo> fieldInfoList = entry.getValue();
                    if (fieldInfoList.size() == 1) {
                        idField = fieldInfoList.get(0);
                        break;
                    }
                }
            }

            List<FieldInfo> fieldList = tableInfo.getFieldList();
            for (FieldInfo fieldInfo : fieldList) {
                String beanName = BuildTable.processFiled(fieldInfo.getFieldName(), false);

                bw.write("\t\t<!-- " + fieldInfo.getComment() + " -->");
                bw.newLine();

                if (idField != null && idField.getFieldName().equals(fieldInfo.getFieldName())) {
                    bw.write("\t\t<id property=\"" + beanName + "\" column=\"" + fieldInfo.getFieldName() + "\"/>");
                } else {
                    bw.write("\t\t<result property=\"" + beanName + "\" column=\"" + fieldInfo.getFieldName() + "\"/>");
                }
                bw.newLine();
            }


            bw.write("\t</resultMap>");
            bw.newLine();
            bw.newLine();

            //通用查询结果列
            bw.write("\t<!-- 通用查询结果列 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_COLUMN_LIST + "\">");
            bw.newLine();

            boolean index1 = false;
            for (FieldInfo fieldInfo : fieldList) {
                if (index1) {
                    bw.write(", " + fieldInfo.getFieldName());
                } else {
                    bw.write("\t\t" + fieldInfo.getFieldName());
                    index1 = true;
                }
            }
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //基础查询条件
            bw.write("\t<!-- 基础查询条件 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION + "\">");
            bw.newLine();

            for (FieldInfo fieldInfo : fieldList) {
                String beanName = BuildTable.processFiled(fieldInfo.getFieldName(), false);

                if (ArrayUtils.contains(Constants.SQL_DATA_TIME_TYPES, fieldInfo.getSqlType())) {
                    bw.write("\t\t<if test=\"query." + beanName + " != null \">");
                    bw.newLine();
                    bw.write("\t\t\t<![CDATA[ ");
                    bw.newLine();
                    bw.write("\t\t\tAND " + fieldInfo.getFieldName() + " = #{query." + beanName + "}");
                    bw.newLine();
                    bw.write("\t\t\t]]>");
                    bw.newLine();
                    bw.write("\t\t</if>");
                    bw.newLine();
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    bw.write("\t\t<if test=\"query." + beanName + " != null \">");
                    bw.newLine();
                    bw.write("\t\t\tAND " + fieldInfo.getFieldName() + " = #{query." + beanName + "}");
                    bw.newLine();
                    bw.write("\t\t</if>");
                    bw.newLine();
                } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    bw.write("\t\t<if test=\"query." + beanName + " != null and query." + beanName + " != ''\">");
                    bw.newLine();
                    bw.write("\t\t\tAND " + fieldInfo.getFieldName() + " = #{query." + beanName + "}");
                    bw.newLine();
                    bw.write("\t\t</if>");
                    bw.newLine();
                } else {
                    bw.write("\t\t<if test=\"query." + beanName + " != null\">");
                    bw.newLine();
                    bw.write("\t\t\tAND " + fieldInfo.getFieldName() + " = #{query." + beanName + "}");
                    bw.newLine();
                    bw.write("\t\t</if>");
                    bw.newLine();
                }

            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();


            //扩展的查询条件
            bw.write("\t<!-- 扩展的查询条件 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION_EXTEND + "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()) {
                String andWhere = null;
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    andWhere = "\t\t\tAND " + fieldInfo.getFieldName() + " LIKE CONCAT('%',#{query." + fieldInfo.getPropertyName() + "},'%')";
                } else if (ArrayUtils.contains(Constants.SQL_DATA_TIME_TYPES, fieldInfo.getSqlType())) {
                    if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_DATE_START)) {
                        andWhere = "\t\t\t<![CDATA[ AND " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d %H:%i:%s') ]]>";
                    } else {
                        andWhere = "\t\t\t<![CDATA[ AND " + fieldInfo.getFieldName() + " < str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d %H:%i:%s') ]]>";
                    }
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_DATE_START)) {
                        andWhere = "\t\t\t<![CDATA[ AND " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d') ]]>";
                    } else {
                        andWhere = "\t\t\t<![CDATA[ AND " + fieldInfo.getFieldName() + " < DATE_ADD(str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d'), INTERVAL 1 DAY) ]]>";
                    }
                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null and query." + fieldInfo.getPropertyName() + " !=''\">");
                bw.newLine();
                bw.write(andWhere);
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();

            //扩展的查询条件
            bw.write("\t<!-- 扩展的查询条件 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + QUERY_CONDITION + "\">");
            bw.newLine();
            bw.write("\t\t<where>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_EXTEND + "\"/>");
            bw.newLine();
            bw.write("\t\t</where>");
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //查询列表
            bw.write("\t<!-- 查询列表 -->");
            bw.newLine();
            bw.write("\t<select id=\"selectList\" resultMap=\"" + BASE_RESULT_MAP + "\">");
            bw.newLine();
            bw.write("\t\tSELECT <include refid=\"" + BASE_COLUMN_LIST + "\"/> FROM " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t\t<if test=\"query.orderBy!=null\"> order by ${query.orderBy} </if>");
            bw.newLine();
            bw.write("\t\t<if test=\"query.simplePage!=null\"> limit #{query.simplePage.start},#{query.simplePage.end} </if>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            //查询数量
            bw.write("\t<!-- 查询数量 -->");
            bw.newLine();
            bw.write("\t<select id=\"selectCount\" resultType=\"java.lang.Long\">");
            bw.newLine();
            bw.write("\t\tSELECT COUNT(1) FROM " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();

            //单条插入
            bw.write("\t<!-- 插入 （匹配有值的字段） -->");
            bw.newLine();
            bw.write("\t<insert id=\"insert\" parameterType=\"" + poClass + "\">");
            bw.newLine();
            //获取自增长字段
            FieldInfo autoIncrementField = null;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement() != null && fieldInfo.getIsAutoIncrement()) {
                    autoIncrementField = fieldInfo;
                    break;
                }
            }
            if (autoIncrementField != null) {
                bw.write("\t\t<selectKey keyProperty=\"bean." + autoIncrementField.getPropertyName() + "\" order=\"AFTER\" resultType=\"" + autoIncrementField.getJavaType() + "\">");
                bw.newLine();
                bw.write("\t\t\tSELECT LAST_INSERT_ID()");
                bw.newLine();
                bw.write("\t\t</selectKey>");
                bw.newLine();
            }
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName() + " ");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement() != null && fieldInfo.getIsAutoIncrement()) {
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement() != null && fieldInfo.getIsAutoIncrement()) {
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();

            //单条插入或者更新（匹配有值的字段）
            bw.write("\t<!-- 单条插入或者更新 （匹配有值的字段） -->");
            bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdate\" parameterType=\"" + poClass + "\">");
            bw.newLine();
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName() + " ");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
//                if(fieldInfo.getIsAutoIncrement() != null && fieldInfo.getIsAutoIncrement()){
//                    continue;
//                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!= null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
//                if(fieldInfo.getIsAutoIncrement() != null && fieldInfo.getIsAutoIncrement()){
//                    continue;
//                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!= null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t#如果违反了表中定义的任何唯一键约束（如主键或唯一索引），则触发以下更新语句");
            bw.newLine();

            //keyTempMap标注唯一索引，在更新中去除这些字段
            Map<String, String> keyTempMap = new HashMap<>();
            for (Map.Entry<String, List<FieldInfo>> entry : key.entrySet()) {
                List<FieldInfo> fieldInfoList = entry.getValue();
                for (FieldInfo item : fieldInfoList) {
                    keyTempMap.put(item.getFieldName(), item.getFieldName());
                }
            }

            bw.write("\t\tON DUPLICATE KEY UPDATE");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\" >");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                //去除唯一索引的更新
                if (keyTempMap.get(fieldInfo.getFieldName()) != null) {
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!= null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "),");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //添加(批量插入)
            bw.write("\t<!-- 批量插入 -->");
            bw.newLine();
            bw.write("\t<insert id=\"insertBatch\" parameterType=\"" + poClass + "\">");
            bw.newLine();
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName());
            bw.newLine();
            bw.write("\t\t(");
            index1 = false;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement()) {
                    continue;
                }
                if (!index1) {
                    bw.write(fieldInfo.getFieldName());
                    index1 = true;
                } else {
                    bw.write("," + fieldInfo.getFieldName());
                }
            }
            bw.write(")");
            bw.newLine();
            bw.write("\t\tVALUES");
            bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"bean\" index=\"index\" separator=\",\">");
            bw.newLine();
            bw.write("\t\t\t(");
            index1 = false;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement()) {
                    continue;
                }
                if (!index1) {
                    bw.write("#{bean." + fieldInfo.getPropertyName() + "}");
                    index1 = true;
                } else {
                    bw.write(",#{bean." + fieldInfo.getPropertyName() + "}");
                }
            }
            bw.write(")");
            bw.newLine();
            bw.write("\t\t</foreach>");
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //批量新增或修改 (批量插入)
            bw.write("\t<!-- 批量新增或修改 (批量插入) -->");
            bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdateBatch\" parameterType=\"" + poClass + "\">");
            bw.newLine();
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName());
            bw.newLine();
            bw.write("\t\t(");
            index1 = false;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement()) {
                    continue;
                }
                if (!index1) {
                    bw.write(fieldInfo.getFieldName());
                    index1 = true;
                } else {
                    bw.write("," + fieldInfo.getFieldName());
                }
            }
            bw.write(")");
            bw.newLine();
            bw.write("\t\tVALUES");
            bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"bean\" index=\"index\" separator=\",\">");
            bw.newLine();
            bw.write("\t\t\t(");
            index1 = false;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getIsAutoIncrement()) {
                    continue;
                }
                if (!index1) {
                    bw.write("#{bean." + fieldInfo.getPropertyName() + "}");
                    index1 = true;
                } else {
                    bw.write(",#{bean." + fieldInfo.getPropertyName() + "}");
                }
            }
            bw.write(")");
            bw.newLine();
            bw.write("\t\t</foreach>");
            bw.newLine();
            bw.write("\t\tON DUPLICATE KEY UPDATE");
            bw.newLine();
            index1 = false;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                //去除唯一索引的更新
                if (keyTempMap.get(fieldInfo.getFieldName()) != null) {
                    continue;
                }
                if(!index1){
                    bw.write("\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + ")");
                    index1 = true;
                }else{
                    bw.write(",");
                    bw.newLine();
                    bw.write("\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + ")");
                }
            }
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //根据主键修改
            StringBuilder methodName;
            StringBuilder commentParams;
            StringBuffer params;
            for(Map.Entry<String, List<FieldInfo>> entry : key.entrySet()){
                methodName = new StringBuilder();
                commentParams = new StringBuilder();
                params = new StringBuffer();

                List<FieldInfo> fieldInfoList = entry.getValue();

                Integer index = 0;
                for( FieldInfo fieldInfo : fieldInfoList){
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    commentParams.append(fieldInfo.getPropertyName());
                    params.append(fieldInfo.getFieldName() + " = #{" + fieldInfo.getPropertyName() + "}");
                    if(index < fieldInfoList.size()){
                        methodName.append("And");
                        commentParams.append("和");
                        params.append(" and ");
                    }
                }
                //添加注释
                bw.write("\t<!-- \"根据\"" + commentParams + "\"查询\" -->");
                bw.newLine();
                bw.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\">");
                bw.newLine();
                bw.write("\t\tSELECT <include refid=\"" + BASE_COLUMN_LIST + "\"/> from " + tableInfo.getTableName() + " where " + params);
                bw.newLine();
                bw.write("\t</select>");
                bw.newLine();
                bw.newLine();

                //添加注释
                bw.write("\t<!-- \"根据\"" + commentParams + "\"删除\" -->");
                bw.newLine();
                bw.write("\t<delete id=\"selectBy" + methodName + "\">");
                bw.newLine();
                bw.write("\t\tDELETE FROM " + tableInfo.getTableName() + " where " + params);
                bw.newLine();
                bw.write("\t</delete>");
                bw.newLine();
                bw.newLine();
            }






            bw.write("</mapper>");
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            logger.info("创建mapper xml失败", e);
        }
    }
}
