package com.crane.springboot.controller;

import com.crane.springboot.exception.BusinessException;
import com.crane.springboot.manager.SearchFacade;
import com.crane.springboot.model.dto.search.SearchRequest;
import com.crane.springboot.model.vo.SearchVo;
import com.crane.springboot.common.BaseResponse;
import com.crane.springboot.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.crane.springboot.common.ErrorCode.FORBIDDEN_ERROR;
import static com.crane.springboot.common.ErrorCode.HEALTHY_ERROR;

/**
 * 查询控制类——聚合
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {

//
        SearchVo searchVo = searchFacade.searchAll(searchRequest, request);
        if (searchVo.getDataSourceList() == null) {
            return ResultUtils.error(HEALTHY_ERROR.getCode(), HEALTHY_ERROR.getMessage());
        }
        log.info(String.format("emo:%s \t ketEmo:%s \t picEmo:%s",searchVo.getEmo(),searchVo.getKeyEmo(), searchVo.getPicEmo()));
        return ResultUtils.success(searchVo);

    }

}
