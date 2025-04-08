package org.example.utils;


public class StringUtils {

    /**
     * 将字符串的首字母转换为大写
     * @param field 需要转换的字符串
     * @return 转换后的字符串，如果输入为空或null，则直接返回输入
     */
    public static String uperCaseFirstLetter(String field){
        // 检查输入字符串是否为空或null
        if(org.apache.commons.lang3.StringUtils.isEmpty(field)){
            // 如果为空或null，直接返回输入
            return field;
        }

        // 将字符串的首字母转换为大写，并拼接剩余部分
        return field.substring(0,1).toUpperCase() + field.substring(1);
    }

    public static String lowerCaseFirstLetter(String field){
        // 检查输入字符串是否为空或null
        if(org.apache.commons.lang3.StringUtils.isEmpty(field)){
            // 如果为空或null，直接返回输入
            return field;
        }

        // 将字符串的首字母转换为大写，并拼接剩余部分
        return field.substring(0,1).toLowerCase() + field.substring(1);
    }


    /**
     * 主方法，用于测试lowerCaseFirstLetter方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 调用uperCaseFirstLetter方法并打印结果
        System.out.println(lowerCaseFirstLetter("Company"));
    }
}
