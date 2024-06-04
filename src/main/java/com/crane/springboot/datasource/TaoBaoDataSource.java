package com.crane.springboot.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.entity.Picture;
import com.crane.springboot.model.entity.TaoBao;

public class TaoBaoDataSource implements DataSource<TaoBao> {
    @Override
    public Page<TaoBao> doSearch(String searchText, long pageNum, long pageSize) {

        return null;
    }
}
