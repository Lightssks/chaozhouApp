package com.example.chaozhou.api.bean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseResponse {
    /**
     * status : -1or0
     * data : "2018-04-23/e85dbc130521449f94363f1ca98c01d1.jpeg" 图片地址
     */
    private int status;
    private String data;
    private String phonenumber;
    private RequestBody jsonBody;
    private MultipartBody.Part file;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RequestBody getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(RequestBody jsonBody) {
        this.jsonBody = jsonBody;
    }

    public MultipartBody.Part getFile() {
        return file;
    }

    public void setFile(MultipartBody.Part file) {
        this.file = file;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", data='" + data + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", jsonBody=" + jsonBody +
                ", file=" + file +
                '}';
    }
}
