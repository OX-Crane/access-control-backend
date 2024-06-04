package com.crane.springboot.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "negative")
public class Negative {

    private String text;
}
