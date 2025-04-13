package org.example.builder;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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
 * 创建PO实体
 */
public class BuildService {
    public static final Logger logger = LoggerFactory.getLogger(BuildService.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_SERVICE);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanName() + Constants.SUFFIX_SERVICE + ".java");

        String serviceName = tableInfo.getBeanName() + Constants.SUFFIX_SERVICE;

        try (OutputStream out = new FileOutputStream(poFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw)) {

            bw.write("package " + Constants.PACKAGE_SERVICE + ";");
            bw.newLine();
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
            bw.newLine();

            bw.write("import java.io.Serializable;");
            bw.newLine();

            if (tableInfo.getHaveDateTime()) {
                bw.write("import java.time.LocalDateTime;");
                bw.newLine();
            }
            if(tableInfo.getHaveDate()){
                bw.write("import java.time.LocalDate;");
                bw.newLine();

            }
            if(tableInfo.getHaveDateTime() || tableInfo.getHaveDate()){
                bw.write("import " + Constants.PACKAGE_UTILS +".DateUtils;");
                bw.newLine();
                bw.write("import " + Constants.PACKAGE_ENUMS +".DateTimePatternEnum;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATE_PARSE_CLASS);
                bw.newLine();
            }
            if (tableInfo.getHaveBigDecimal()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATE_PARSE_CLASS);
                bw.newLine();
            }
            bw.newLine();

            BuildComment.createClassComment(bw, tableInfo.getComment() + "Service");
            bw.newLine();
            bw.write("public interface " + serviceName + "{");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "根据条件查询列表");
            bw.write("\tList<" + tableInfo.getBeanName() + "> findListByQuery(" + tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "根据条件查询数量");
            bw.write("\tInteger findCountByQuery(" + tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "分页查询");
            bw.write("\tPaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "新增");
            bw.write("\tInteger add(" + tableInfo.getBeanName() + " bean);");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "批量新增");
            bw.write("\tInteger addBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "批量新增/修改");
            bw.write("\tInteger addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bw.newLine();
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
                    params.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if(index < fieldInfoList.size()){
                        methodName.append("And");
                        commentParams.append("和");
                        params.append(", ");
                    }
                }
                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "查询对象");
                bw.write("\t" + tableInfo.getBeanName() + " get" + tableInfo.getBeanName() + "By" + methodName + "(" + params + ");");
                bw.newLine();
                bw.newLine();

                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "修改");
                bw.write("\tInteger update"+ tableInfo.getBeanName() +"By" + methodName + "(" + tableInfo.getBeanName() + " bean," + params + ");");
                bw.newLine();
                bw.newLine();

                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "删除");
                bw.write("\tInteger delete" + tableInfo.getBeanName() + "By" + methodName + "(" + params + ");");
                bw.newLine();
                bw.newLine();
            }



            bw.write("}");
            bw.newLine();




            bw.flush();
        } catch (Exception e) {
            logger.info("创建Service失败", e);
        }
    }
}
