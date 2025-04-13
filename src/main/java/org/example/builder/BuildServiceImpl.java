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
public class BuildServiceImpl {
    public static final Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_SERVICE_IMPL);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanName() + Constants.SUFFIX_SERVICEIMPL + ".java");

        String serviceImplName = tableInfo.getBeanName() + Constants.SUFFIX_SERVICEIMPL;
        String serviceName = tableInfo.getBeanName() + Constants.SUFFIX_SERVICE;
        String queryName = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;
        String mapperBeanName = StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + Constants.SUFFIX_MAPPER;

        try (OutputStream out = new FileOutputStream(poFile);
             OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bw = new BufferedWriter(outw)) {

            bw.write("package " + Constants.PACKAGE_SERVICE_IMPL + ";");
            bw.newLine();
            bw.newLine();

            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + ".SimplePage;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_ENUMS + ".PageSize;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_MAPPER + "." + tableInfo.getBeanName() + Constants.SUFFIX_MAPPER + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_SERVICE + "." + serviceName + ";");
            bw.newLine();
            bw.write("import org.springframework.beans.factory.annotation.Autowired;");
            bw.newLine();
            bw.write("import org.springframework.stereotype.Service;");
            bw.newLine();
            bw.newLine();

            bw.write("import java.util.List;");
            bw.newLine();
            bw.newLine();

            BuildComment.createClassComment(bw, tableInfo.getComment() + "ServiceImpl");
            bw.write("@Service(\"" + StringUtils.lowerCaseFirstLetter(serviceName) + "\")");
            bw.newLine();
            bw.write("public class " + serviceImplName + " implements " + serviceName + " {");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Autowired");
            bw.newLine();
            bw.write("\tprivate " + tableInfo.getBeanName() + Constants.SUFFIX_MAPPER + "<" + tableInfo.getBeanName() + ", " + queryName + "> " + mapperBeanName + ";");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "根据条件查询列表");
            bw.write("\tpublic List<" + tableInfo.getBeanName() + "> findListByQuery(" + tableInfo.getBeanParamName() + " query){");
            bw.newLine();
            bw.write("\t\treturn " + mapperBeanName + ".selectList(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "根据条件查询数量");
            bw.write("\tpublic Integer findCountByQuery(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn " + mapperBeanName + ".selectCount(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "分页查询");
            bw.write("\tpublic PaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\tInteger count = findCountByQuery(query);");
            bw.newLine();
            bw.write("\t\tInteger pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();");
            bw.newLine();
            bw.write("\t\tSimplePage page = new SimplePage(query.getPageNo(), count, pageSize);");
            bw.newLine();
            bw.write("\t\tquery.setSimplePage(page);");
            bw.newLine();
            bw.write("\t\tList<" + tableInfo.getBeanName() + "> list = findListByQuery(query);");
            bw.newLine();
            bw.write("\t\tPaginationResultVO<" + tableInfo.getBeanName() + "> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);");
            bw.newLine();
            bw.write("\t\treturn result;");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "新增");
            bw.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean) {");
            bw.newLine();
            bw.write("\t\treturn " + mapperBeanName + ".insert(bean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "批量新增");
            bw.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tif(listBean == null || listBean.isEmpty()){");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn " + mapperBeanName + ".insertBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createMethodComment(bw, "批量新增/修改");
            bw.write("\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tif(listBean == null || listBean.isEmpty()){");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn " + mapperBeanName + ".insertOrUpdateBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
            StringBuilder methodName;
            StringBuilder params;
            StringBuilder propertyNames;
            StringBuilder commentParams;
            for(Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()){
                methodName = new StringBuilder();
                commentParams = new StringBuilder();
                params = new StringBuilder();
                propertyNames = new StringBuilder();

                List<FieldInfo> fieldInfoList = entry.getValue();

                Integer index = 0;
                for( FieldInfo fieldInfo : fieldInfoList){
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    commentParams.append(fieldInfo.getPropertyName());
                    params.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    propertyNames.append(fieldInfo.getPropertyName());
                    if(index < fieldInfoList.size()){
                        methodName.append("And");
                        commentParams.append("和");
                        params.append(", ");
                        propertyNames.append(", ");
                    }
                }
                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "查询对象");
                bw.write("\tpublic " + tableInfo.getBeanName() + " get" + tableInfo.getBeanName() + "By" + methodName + "(" + params + ") {");
                bw.newLine();
                bw.write("\t\treturn " + mapperBeanName + ".selectBy" + methodName + "(" + propertyNames + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "修改");
                bw.write("\tpublic Integer update"+ tableInfo.getBeanName() +"By" + methodName + "(" + tableInfo.getBeanName() + " bean, " + params + ") {");
                bw.newLine();
                bw.write("\t\treturn " + mapperBeanName + ".updateBy" + methodName + "(bean, " + propertyNames + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                //添加注释
                BuildComment.createFieldComment(bw, "根据" + commentParams + "删除");
                bw.write("\tpublic Integer delete" + tableInfo.getBeanName() + "By" + methodName + "(" + params + ") {");
                bw.newLine();
                bw.write("\t\treturn " + mapperBeanName + ".deleteBy" + methodName + "(" + propertyNames + ");");
                bw.newLine();
                bw.write("\t}");
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
