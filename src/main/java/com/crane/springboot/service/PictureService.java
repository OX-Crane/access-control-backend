package com.crane.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.entity.Picture;

public interface PictureService {
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
