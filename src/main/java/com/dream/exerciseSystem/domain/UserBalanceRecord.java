package com.dream.exerciseSystem.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * store the user balance
 * @TableName UserBalanceRecord
 */
@TableName(value ="UserBalanceRecord")
@Data
public class UserBalanceRecord implements Serializable {
    /**
     * student account id
     */
    @TableId
    private String userId;

    /**
     * FAQ token balance of the student
     */
    private Integer FAQTokenBalance;

    /**
     * recommend token balance of the student
     */
    private Integer recommendTokenBalance;

    /**
     * recommend uses for free
     */
    private Integer recommendFreeUses;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}