package com.crane.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "stopword")
@Data
public class StopWord {

    private String text;

}
