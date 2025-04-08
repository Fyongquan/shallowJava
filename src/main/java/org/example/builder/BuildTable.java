package org.example.builder;

import com.mysql.cj.log.Log;
import org.example.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.spi.DirectoryManager;
import java.awt.image.DirectColorModel;
import java.sql.*;

import static java.lang.Class.forName;

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
        try {
          ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
          tableResult = ps.executeQuery();
          while (tableResult.next()) {
              String tableName = tableResult.getString("name");
              String comment = tableResult.getString("comment");
              logger.info("表名: {}, 注释: {}", tableName, comment);
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
}
