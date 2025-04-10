package org.example;

import org.example.bean.TableInfo;
import org.example.builder.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();

        BuildBase.execute();

        for (TableInfo tableInfo : tableInfoList) {
            BuildPo.execute(tableInfo);
            BuildQuery.execute(tableInfo);
            BuildMapper.execute(tableInfo);
            BuildMapperXml.execute(tableInfo);
        }
    }
}