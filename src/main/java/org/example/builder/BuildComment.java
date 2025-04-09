package org.example.builder;

import org.example.bean.Constants;
import org.example.utils.DateUtils;
import org.example.utils.PropertiesUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildComment {



    public static void createClassComment(BufferedWriter bw, String classComment){
        try {
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

    public static void createFieldComment(BufferedWriter bw, String fieldComment){
        fieldComment = fieldComment == null ? "" : fieldComment;
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

    public static void createMethodComment(){

    }
}
