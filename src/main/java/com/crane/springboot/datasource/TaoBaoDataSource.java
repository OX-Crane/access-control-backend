package com.crane.springboot.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.dto.taobao.TaoBaoQueryRequest;
import com.crane.springboot.model.entity.TaoBao;
import com.crane.springboot.service.TaoBaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaoBaoDataSource implements DataSource<TaoBao> {

    @Resource
    private TaoBaoService taoBaoService;
    @Override
    public Page<TaoBao> doSearch(String searchText, long pageNum, long pageSize) {
        TaoBaoQueryRequest taoBaoQueryRequest = new TaoBaoQueryRequest();
        taoBaoQueryRequest.setText(searchText);
        taoBaoQueryRequest.setCurrent(pageNum);
        taoBaoQueryRequest.setPageSize(pageSize);

        Page<TaoBao> page = taoBaoService.listTaoBaoByPage(taoBaoQueryRequest);

        return page;
    }
}
