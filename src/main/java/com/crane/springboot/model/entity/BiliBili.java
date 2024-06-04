package com.crane.springboot.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiliBili {

    private String title;

    private String author;

    private String typeName;

    private String aid;

    private String bvid;

    private String description;

//    封面地址
    private String pic;

    private String[] tags;

}
