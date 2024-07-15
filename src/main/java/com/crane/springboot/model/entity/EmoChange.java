package com.crane.springboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EmoChange {

    private String name;

    private String city;

    private String type;

    private int emo;

    private int keyEmo;

    private int picEmo;

    private String searchText;

    private long pageNum;

    private long pageSize;

    //    1为增加  -1为减少   2为关键词增加     3为图片增加
    private int value;

    public EmoChange(String name, String city, String type, String searchText, long pageNum, long pageSize, int value) {
        this.name = name;
        this.city = city;
        this.type = type;
        this.searchText = searchText;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.value = value;
    }

}
