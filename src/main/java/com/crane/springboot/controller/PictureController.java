package com.crane.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.common.BaseResponse;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.common.ResultUtils;
import com.crane.springboot.exception.ThrowUtils;
import com.crane.springboot.model.dto.picture.PictureQueryRequest;
import com.crane.springboot.model.entity.Picture;
import com.crane.springboot.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;
    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                        HttpServletRequest request) {

        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(picturePage);
    }


}
