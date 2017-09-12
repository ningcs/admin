package com.zhongqi.entity.common;

import javax.persistence.*;

/**
 * Created by ningcs on 2017/8/25.
 */
@Entity
@Table(name = "CpSource")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;
    @Column(name = "name",nullable = true)
    private String name;
    @Column(name = "password",nullable = true)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
