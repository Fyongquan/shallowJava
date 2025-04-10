package org.example.builder;

import org.example.bean.Constants;
import org.example.utils.DateUtils;
import org.example.utils.PropertiesUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建注释
 */
public class BuildComment {

    public static void createClassComment(BufferedWriter bw, String classComment){
        try {
    /**
     * 创建类注释
     * @param bw BufferedWriter对象，用于写入注释
     * @param classComment 类的注释内容
     */
            bw.write("/**");
            bw.newLine();
            bw.write(" * @author: " + Constants.AUTHER);
            bw.newLine();
            bw.write(" * @Description: " + classComment);
            bw.newLine();
            bw.write(" * @date: " + DateUtils.format(new Date(), DateUtils.DEFAULT_DATETIME_FORMAT));
            bw.newLine();
            bw.write(" */");
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            // 捕获IO异常并抛出运行时异常
    }

    public static void createFieldComment(BufferedWriter bw, String fieldComment){
        fieldComment = fieldComment == null ? "" : fieldComment;
    /**
     * 创建字段注释
     * @param bw BufferedWriter对象，用于写入注释
     * @param fieldComment 字段的注释内容
     */
        try {
            bw.write("\t/**");
            bw.newLine();
            bw.write("\t * " + fieldComment);
            bw.newLine();
            bw.write("\t */");
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            // 捕获IO异常并抛出运行时异常
    }

    public static void createMethodComment(){

    /**
     * 创建方法注释（方法体尚未实现）
     */
    }
}
