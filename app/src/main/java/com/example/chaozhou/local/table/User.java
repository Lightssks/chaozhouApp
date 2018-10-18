package com.example.chaozhou.local.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity

public class User {

    @Id(autoincrement = true)           //参数autoincrement是设置ID值自增；
    private long uid;
    private String uname;
    @NotNull
    private String phonenumber;
    private String password;
    private String birthday;
    private String sex;
    private int age;
    private String head;
    @Generated(hash = 848301150)
    public User(long uid, String uname, @NotNull String phonenumber,
            String password, String birthday, String sex, int age, String head) {
        this.uid = uid;
        this.uname = uname;
        this.phonenumber = phonenumber;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.age = age;
        this.head = head;
    }
    public User() {
    }
    public long getUid() {
        return this.uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
    public String getUname() {
        return this.uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getPhonenumber() {
        return this.phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
