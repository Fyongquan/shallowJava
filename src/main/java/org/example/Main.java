package org.example;

import org.example.bean.TableInfo;
import org.example.builder.BuildTable;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();
    }
}