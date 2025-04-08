package org.example.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {
    // 定义一个私有字符串变量，用于存储表名
    private String tableName;
    // 定义一个私有字符串变量，用于存储Java Bean的名称
    private String beanName;
    // 定义一个私有字符串变量，用于存储Java Bean参数的名称
    private String beanParamName;
    // 定义一个私有字符串变量，用于存储注释信息
    private String comment;
    // 定义一个私有List集合，用于存储字段信息（FieldInfo对象）
    private List<FieldInfo> fieldList;
    // 定义一个私有Map集合，用于存储键索引映射关系，键为字符串，值为FieldInfo对象的List集合
    // 使用LinkedHashMap保证插入顺序
    private Map<String, List<FieldInfo>> keyIndexMap = new LinkedHashMap();
    // 定义一个私有Boolean变量，用于标识是否存在日期类型字段
    private Boolean haveDate;
    // 定义一个私有Boolean变量，用于标识是否存在日期时间类型字段
    private Boolean haveDateTime;
    // 定义一个私有Boolean变量，用于标识是否存在BigDecimal类型字段
    private Boolean haveBigDecimal;

    // String 类型字段
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanParamName() {
        return beanParamName;
    }

    public void setBeanParamName(String beanParamName) {
        this.beanParamName = beanParamName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // 集合类型字段
    public List<FieldInfo> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldInfo> fieldList) {
        this.fieldList = fieldList;
    }

    public Map<String, List<FieldInfo>> getKeyIndexMap() {
        return keyIndexMap;
    }

    public void setKeyIndexMap(Map<String, List<FieldInfo>> keyIndexMap) {
        this.keyIndexMap = keyIndexMap;
    }

    // Boolean 类型字段
    public Boolean getHaveDate() {
        return haveDate;
    }

    public void setHaveDate(Boolean haveDate) {
        this.haveDate = haveDate;
    }

    public Boolean getHaveDateTime() {
        return haveDateTime;
    }

    public void setHaveDateTime(Boolean haveDateTime) {
        this.haveDateTime = haveDateTime;
    }

    public Boolean getHaveBigDecimal() {
        return haveBigDecimal;
    }

    public void setHaveBigDecimal(Boolean haveBigDecimal) {
        this.haveBigDecimal = haveBigDecimal;
    }
}
