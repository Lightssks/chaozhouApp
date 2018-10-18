package com.example.chaozhou.local.table;




/**
 * Created by yfj on 18-4-19.
 * name 信息名称： 例如 头像，性别，昵称，等等
 * message  是User里的属性的值
 * 通过这个实体类来调用User的属性值 ,达到用ListView布局点击效果的问题。
 */
public class Personal_Info {





    private String name;
    private String message;

    public Personal_Info(String name, String message){
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
