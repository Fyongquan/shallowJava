package org.example.builder;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.example.bean.Constants;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础工具类模板构建器
 */
public class BuildBase {
    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);
    public static void execute() {
        List<String> headerInfoList = new ArrayList<>();

        //生成date枚举
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList,"DateTimePatternEnum", Constants.PATH_ENUMS);

        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_UTILS + ";");
        build(headerInfoList,"DateUtils", Constants.PATH_UTILS);
    }

    public static void build(List<String> headerInfoList, String fileName, String outPutPath){

        File floder = new File(outPutPath);
        if(!floder.exists()){
            floder.mkdirs();
        }

        File javaFile = new File(outPutPath, fileName + ".java");

        String templatePath = BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
        try (OutputStream out = new FileOutputStream(javaFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw);

             InputStream in = new FileInputStream(templatePath);
             InputStreamReader inr = new InputStreamReader(in, "UTF-8");
             BufferedReader br = new BufferedReader(inr)) {

            for(String headerInfo : headerInfoList){
                bw.write(headerInfo);
                bw.newLine();
                if(headerInfo.contains("package")){
                    bw.newLine();
                }
            }

            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            
        }catch (Exception e){
            logger.info("生成基础类：{}，发送错误:{}",fileName,e);
        }
    }
}
