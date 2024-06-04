package com.crane.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "word")
@Data
public class Word {
    private String text;
}
