package com.crane.springboot.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.common.CacheUtils;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.controller.EmoController;
import com.crane.springboot.datasource.DataSource;
import com.crane.springboot.datasource.DataSourceRegister;
import com.crane.springboot.exception.ThrowUtils;
import com.crane.springboot.model.dto.search.SearchRequest;
import com.crane.springboot.model.enums.SearchTypeEnum;
import com.crane.springboot.model.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class SearchFacade {

    @Resource
    private DataSourceRegister dataSourceRegister;

    @Resource
    private EmoController emoController;

    public SearchVo searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {

        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();

        int keyEmo = searchRequest.getKeyEmo();
        int picEmo = searchRequest.getPicEmo();
        int emo = searchRequest.getEmo();

        SearchVo searchVo = new SearchVo();
        String searchText = searchRequest.getSearchText();

        String type = searchRequest.getType();

        ThrowUtils.throwIf(StringUtil.isBlank(type), ErrorCode.PARAMS_ERROR);


//        分析用户查询的关键词
        SearchRequest emoBySearchText = emoController.emotionBySearchText(searchRequest);
        keyEmo = emoBySearchText.getKeyEmo();
        picEmo = emoBySearchText.getPicEmo();
        emo = emoBySearchText.getEmo();

//        根据类型查询数据
        DataSource<?> dataSource = dataSourceRegister.getDataSourceByType(type.toLowerCase());

        Page<?> page = dataSource.doSearch(searchText, current, pageSize);
        searchVo.setDataSourceList(page.getRecords());
//        根据查询后的结果进行情感值分析
        SearchRequest resultRequest = emoController.emotionBySearchResult(searchVo, searchRequest);

        emo = resultRequest.getEmo();
        keyEmo = resultRequest.getKeyEmo();
        picEmo = resultRequest.getPicEmo();
//        判断用户的情感值是不是在正常的范围
        boolean emoExceed = emoController.emoExceed(keyEmo, picEmo, emo);
        if (emoExceed) {
            return new SearchVo();
        }
        searchVo.setEmo(emo);
        searchVo.setKeyEmo(keyEmo);
        searchVo.setPicEmo(picEmo);
        return searchVo;



    }
}
