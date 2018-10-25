package com.meteor.curator.web.api.vo;

import java.util.List;

/**
 * Created by y747718944 on 2018/4/28
 * 表格返回集
 */
public class TableResult<T> extends BaseResult {

    TableData<T> tableData;

    public TableResult(long total, List<T> rows) {
        this.tableData = new TableData<T>(total, rows);
    }

    public TableResult() {
        this.tableData = new TableData<T>();
    }

    TableResult<T> total(int total) {
        this.tableData.setTotal(total);
        return this;
    }

    TableResult<T> total(List<T> rows) {
        this.tableData.setRows(rows);
        return this;
    }

    public TableData<T> getTableData() {
        return tableData;
    }

    public void setTableData(TableData<T> tableData) {
        this.tableData = tableData;
    }

    //内部类
    class TableData<T> {
        long total;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
