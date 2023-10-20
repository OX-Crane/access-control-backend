package com.crane.springboot.controller;

import com.crane.springboot.manager.SearchFacade;
import com.crane.springboot.model.dto.search.SearchRequest;
import com.crane.springboot.model.vo.SearchVo;
import com.crane.springboot.service.PictureService;
import com.crane.springboot.service.PostService;
import com.crane.springboot.service.UserService;
import com.crane.springboot.common.BaseResponse;
import com.crane.springboot.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 查询控制类——聚合
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));

    }

}
