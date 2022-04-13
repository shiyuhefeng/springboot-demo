package com.study.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * lombok插件
 */
@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private String nickName;
    private String sex;
    private String address;
    private Integer role;

    @TableField(exist = false)
    private List<Book> bookList;
}
