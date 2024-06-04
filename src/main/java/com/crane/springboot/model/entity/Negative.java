package com.crane.springboot.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "negatively")
public class Negatively {

    private String text;
}
