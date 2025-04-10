package org.example;

import org.example.bean.TableInfo;
import org.example.builder.BuildBase;
import org.example.builder.BuildPo;
import org.example.builder.BuildQuery;
import org.example.builder.BuildTable;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();

        BuildBase.execute();

        for (TableInfo tableInfo : tableInfoList) {
            BuildPo.execute(tableInfo);
            BuildQuery.execute(tableInfo);
        }
    }
}