/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.entities;

/**
 *
 * @author nditema
 */
public class DataColumn {
    private String columnHeader;
    private String columnValue;
    private Enums.ResultStatus resultStatus;

    public DataColumn(String header, String value, Enums.ResultStatus status) {
        this.columnHeader = header;
        this.columnValue = value;
        this.resultStatus = status;
    }

    public void setColumnHeader(String header) {
        this.columnHeader = header;
    }

    public void setColumnValue(String value) {
        this.columnValue = value;
    }

    public void setResultStatus(Enums.ResultStatus status) {
        this.resultStatus = status;
    }

    public String getColumnHeader() {
        return this.columnHeader;
    }

    public String getColumnValue() {
        return this.columnValue;
    }

    public Enums.ResultStatus getResultStatus() {
        return this.resultStatus;
    }
}
