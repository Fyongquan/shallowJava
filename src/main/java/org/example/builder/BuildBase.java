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

        //生成date工具类
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_UTILS + ";");
        build(headerInfoList,"DateUtils", Constants.PATH_UTILS);

        //生成BaseMapper
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_MAPPER + ";");
        build(headerInfoList,"BaseMapper", Constants.PATH_MAPPER);

        //生成pageSize枚举
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList,"pageSize", Constants.PATH_ENUMS);

        //生成SimplePage分页查询信息
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_QUERY + ";");
        build(headerInfoList,"SimplePage", Constants.PATH_QUERY);

        //生成BaseQuery基础查询信息
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_QUERY + ";");
        build(headerInfoList,"BaseQuery", Constants.PATH_QUERY);

        //生成PaginationResultVO分页查询结果
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_VO + ";");
        build(headerInfoList,"PaginationResultVO", Constants.PATH_VO);
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
