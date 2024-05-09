package com.dream.exerciseSystem.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * student model for mybatis
 * @TableName student
 * @author xzy
 * @createDate 2024.5.9
 */
@TableName(value ="student")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Student implements Serializable {
    /**
     * student id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * student name
     */
    private String name;

    /**
     * student email
     */
    private String email;

    /**
     * student password
     */
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Student(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }



    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}