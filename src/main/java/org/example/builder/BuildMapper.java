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
import java.util.List;
import java.util.Map;

/**
 * 创建Mapper
 */
public class BuildMapper {
    public static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_MAPPER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPER;

        File poFile = new File(folder, className + ".java");

        try (OutputStream out = new FileOutputStream(poFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw)) {

            bw.write("package " + Constants.PACKAGE_MAPPER + ";");
            bw.newLine();
            bw.newLine();

            bw.write("import org.apache.ibatis.annotations.Param;");
            bw.newLine();
            bw.newLine();

            //构建类注释
            BuildComment.createClassComment(bw, tableInfo.getComment() + "Mapper");

            bw.write("public interface " + className + "<T, P> extends BaseMapper<T, P> {");
            bw.newLine();

            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();

            StringBuilder methodName;
            StringBuilder params;
            StringBuilder commentParams;
            for(Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()){
                methodName = new StringBuilder();
                commentParams = new StringBuilder();
                params = new StringBuilder();

                List<FieldInfo> fieldInfoList = entry.getValue();

                Integer index = 0;
                for( FieldInfo fieldInfo : fieldInfoList){
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    commentParams.append(fieldInfo.getPropertyName());
                    params.append("@Param(\"" + fieldInfo.getPropertyName() + "\") " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if(index < fieldInfoList.size()){
                        methodName.append("And");
                        commentParams.append("和");
                        params.append(", ");
                    }
                }
                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "查询");
                bw.write("\tT selectBy" + methodName + "(" + params + ");");
                bw.newLine();
                bw.newLine();

                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "更新");
                bw.write("\tInteger updateBy" + methodName + "(" + "@Param(\"bean\") T t, " + params + ");");
                bw.newLine();
                bw.newLine();

                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "删除");
                bw.write("\tInteger deleteBy" + methodName + "(" + params + ");");
                bw.newLine();
                bw.newLine();
            }


            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            logger.info("创建mapper失败", e);
        }
    }
}
