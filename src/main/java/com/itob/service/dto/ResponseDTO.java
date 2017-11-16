package com.itob.service.dto;

import java.util.List;

public class ResponseDTO {

    private String statusCode;
    private String statusValue;
    private Object data;
    private Object[] dataArray;
    private List dataList;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public Object[] getDataArray() {
        return dataArray;
    }

    public void setDataArray(Object[] dataArray) {
        this.dataArray = dataArray;
    }
}
