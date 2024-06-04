package com.crane.springboot.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.entity.Picture;
import com.crane.springboot.model.entity.TaoBao;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询视图
 */
@Data
public class SearchVo implements Serializable {

    private static final long serialVersionUID = 1L;

//    private List<PostVO> postList;
//
//    private List<UserVO> userList;
//
//    private List<Picture> pictureList;
//
//    private List<TaoBao> taoBaoList;

//    统一的数据源
    private List<?> dataSourceList;

//    关键情感值
    private int keyEmo;

//    图片情感值
    private int picEmo;

//    普通情感值
    private int emo;
}
