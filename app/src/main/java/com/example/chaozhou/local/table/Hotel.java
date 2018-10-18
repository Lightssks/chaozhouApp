package com.example.chaozhou.local.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Hotel {

    @Id
    private Long id;

    @Generated(hash = 1880805265)
    public Hotel(Long id) {
        this.id = id;
    }

    @Generated(hash = 890374398)
    public Hotel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
