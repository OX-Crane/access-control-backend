package com.crane.springboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmoChange {

    private String type;

//    1为增加  -1为减少   2为关键词增加     3为图片增加
    private int value;
}
