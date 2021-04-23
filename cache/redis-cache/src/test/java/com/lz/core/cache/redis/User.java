package com.lz.core.cache.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class User implements Serializable {
    private String name;
    private int age;

    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
