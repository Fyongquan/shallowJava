package org.example.builder;

import org.example.bean.Constants;
import org.example.utils.DateUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 创建注释
 */
public class BuildComment {

    public static void createClassComment(BufferedWriter bw, String classComment) {
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
    }

    public static void createFieldComment(BufferedWriter bw, String fieldComment) {
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
    }

    public static void createMethodComment(BufferedWriter bw, String methodComment) {

        /**
         * 创建方法注释
         */
        methodComment = methodComment == null ? "" : methodComment;
        /**
         * 创建字段注释
         * @param bw BufferedWriter对象，用于写入注释
         * @param methodComment 方法的注释内容
         */
        try {
            bw.write("\t/**");
            bw.newLine();
            bw.write("\t * " + methodComment);
            bw.newLine();
            bw.write("\t */");
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
