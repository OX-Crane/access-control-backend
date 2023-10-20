package com.crane.springboot.datasource;

import com.crane.springboot.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegister {

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    public Map<String, DataSource> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        typeDataSourceMap = new HashMap<String, DataSource>() {{
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }


    public DataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
