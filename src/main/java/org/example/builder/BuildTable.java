package org.example.builder;

import org.example.bean.Constants;
import org.example.bean.TableInfo;
import org.example.utils.PropertiesUtils;
import org.example.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.bean.Constants.SUFFIX_BEAN_PARAM;


public class BuildTable {

    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;

    private static String SQL_SHOW_TABLE_STATUS = "show table status";

    static {
        String diverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String username = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");
        try {
            Class.forName(diverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.error("数据库连接失败",e);
        }
    }

    public static void getTables(){
        PreparedStatement ps = null;
        ResultSet tableResult = null;

        List<TableInfo> tableInfoList = new ArrayList<>();
        try {
          ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
          tableResult = ps.executeQuery();
          while (tableResult.next()) {
              String tableName = tableResult.getString("name");
              String comment = tableResult.getString("comment");
//              logger.info("表名: {}, 注释: {}", tableName, comment);


              String beanName = tableName;
              if(Constants.IGNORE_TABLE_PERFIX) {
                  beanName = tableName.substring(beanName.indexOf("_")+1);
              }
              beanName = processFiled(beanName, true);

              TableInfo tableInfo = new TableInfo();
              tableInfo.setTableName(tableName);
              tableInfo.setBeanName(beanName);
              tableInfo.setComment(comment);
              tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_PARAM);

              logger.info("表：{},备注:{},javabean:{},javaParamBean:{}", tableInfo.getTableName(), tableInfo.getComment(), tableInfo.getBeanName(), tableInfo.getBeanParamName());
          }
        } catch (SQLException e) {
            logger.error("读取表失败",e);
        } finally {
            if(tableResult != null) {
                try {
                    tableResult.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null) {
                try{
                    ps.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String processFiled(String field, Boolean uperCaseFirstLetter){
        StringBuffer sb = new StringBuffer();
        String[] fields = field.split("_");

        sb.append(uperCaseFirstLetter ? StringUtils.uperCaseFirstLetter(fields[0]) : fields[0]);
        for(int i = 1, len = fields.length; i < len; i++){
            sb.append(StringUtils.uperCaseFirstLetter(fields[i]));
        }
        return sb.toString();
    }
}
