package com.crane.springboot.model.dto.search;

import com.crane.springboot.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索类型
     */
    private String type;
    /**
     * 搜索词
     */
    private String searchText;
    /**
     * 姓名
     */
    private String name;
    /**
     * 城市
     */
    private String city;

    private long pageSize;

    private long pageNum;
    /**
     * 关键情感值
     */
    private int keyEmo;

    /**
     * 图片情感值
     */
    private int picEmo;

    /**
     * 普通情感值
     */
    private int emo;


    private static final long serialVersionUID = 1L;
}