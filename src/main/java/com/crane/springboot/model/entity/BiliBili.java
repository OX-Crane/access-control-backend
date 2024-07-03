package com.crane.springboot.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiliBili {

    private String title;

    private String author;

    private String url;

//    封面地址
    private String pic;

    private String date;
}
