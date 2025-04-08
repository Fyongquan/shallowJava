package org.example.bean;

public class FieldInfo {
    // 定义一个私有字符串变量fieldName，用于存储字段名称
    private String fieldName;
    // 定义一个私有字符串变量propertyName，用于存储属性名称
    private String propertyName;
    // 定义一个私有字符串变量sqlType，用于存储SQL类型
    private String sqlType;
    // 定义一个私有字符串变量javaType，用于存储Java类型
    private String javaType;
    // 定义一个私有字符串变量comment，用于存储注释信息
    private String comment;
    // 定义一个私有布尔型变量isAutoIncrement，用于标识是否自动增长
    private Boolean isAutoIncrement;

    // 字符串类型字段
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // 布尔类型字段（特殊处理）
    public Boolean getIsAutoIncrement() { // 注意：Boolean包装类型建议用get前缀
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(Boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }
}
