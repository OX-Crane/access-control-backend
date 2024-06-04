package com.crane.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "active")
public class Active {

    private String text;
}
