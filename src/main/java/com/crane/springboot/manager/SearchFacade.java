package com.crane.springboot.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.datasource.*;
import com.crane.springboot.model.dto.user.UserQueryRequest;
import com.crane.springboot.model.vo.UserVO;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.exception.BusinessException;
import com.crane.springboot.exception.ThrowUtils;
import com.crane.springboot.model.dto.post.PostQueryRequest;
import com.crane.springboot.model.dto.search.SearchRequest;
import com.crane.springboot.model.entity.Picture;
import com.crane.springboot.model.enums.SearchTypeEnum;
import com.crane.springboot.model.vo.PostVO;
import com.crane.springboot.model.vo.SearchVo;
import com.crane.springboot.service.PictureService;
import com.crane.springboot.service.PostService;
import com.crane.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SearchFacade {

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DataSourceRegister dataSourceRegister;

    public SearchVo searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {

        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtil.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        SearchVo searchVo = new SearchVo();
//        查询所有数据
        if (searchTypeEnum == null) {
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText,current, pageSize);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText,current, pageSize);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText,current, pageSize);
                return picturePage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();

            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                searchVo.setPostList(postVOPage.getRecords());
                searchVo.setUserList(userVOPage.getRecords());
                searchVo.setPictureList(picturePage.getRecords());
                return searchVo;
            } catch (Exception e) {
                log.error("查询异常{}", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
//            根据类型查询数据
        } else {
            DataSource<?> dataSource = dataSourceRegister.getDataSourceByType(type.toLowerCase());
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVo.setDataSourceList(page.getRecords());
            return searchVo;

//            switch (searchTypeEnum) {
//                case USER:
//                    dataSource = userDataSource;
//                    break;
//                case POST:
//                    dataSource = postDataSource;
//                    break;
//                case PICTURE:
//                    dataSource = pictureDataSource;
//                    break;
//                case VIDEO:
//                    break;
//            }
//            Page page = dataSource.doSearch(searchText, current, pageSize);


//            switch (searchTypeEnum) {
//                case USER:
//                    UserQueryRequest userQueryRequest = new UserQueryRequest();
//                    userQueryRequest.setUserName(searchText);
//                    Page<UserVO> userVOPage = userDataSource.doSearch(searchText,current, pageSize);
//                    searchVo.setUserList(userVOPage.getRecords());
//                    break;
//                case POST:
//                    PostQueryRequest postQueryRequest = new PostQueryRequest();
//                    postQueryRequest.setSearchText(searchText);
//                    Page<PostVO> postVOPage = postDataSource.doSearch(searchText,current, pageSize);
//                    searchVo.setPostList(postVOPage.getRecords());
//                    break;
//                case PICTURE:
//                    Page<Picture> picturePage = pictureDataSource.doSearch(searchText,current, pageSize);
//                    searchVo.setPictureList(picturePage.getRecords());
//                    break;
//                default:
//            }
//            return searchVo;
        }
    }
}
