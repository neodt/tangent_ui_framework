/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.entities;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author nditema
 */
public class DataRow {
    private final LinkedList<DataColumn> DataColumns;

    public DataRow() {
        DataColumns = new LinkedList<>();
    }

    public LinkedList<DataColumn> getDataColumns() {
        return this.DataColumns;
    }

    public void addToDataColumns(DataColumn column) {
        this.DataColumns.add(column);
    }

    public String getColumnValue(String columnHeader) {
        Predicate<DataColumn> predicate = c -> c.getColumnHeader().equals(columnHeader);
        Optional<DataColumn> obj = DataColumns.stream().filter(predicate).findFirst();
        if (obj.isPresent()) {
            return obj.get().getColumnValue();
        }
        return "";
    }

    public DataColumn getColumn(String columnHeader) {
        Predicate<DataColumn> predicate = c -> c.getColumnHeader().equals(columnHeader);
        Optional<DataColumn> obj = DataColumns.stream().filter(predicate).findFirst();

        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }
}
