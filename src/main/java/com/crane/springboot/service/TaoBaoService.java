package com.crane.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.springboot.model.dto.taobao.TaoBaoQueryRequest;
import com.crane.springboot.model.entity.TaoBao;

import java.util.List;

public interface TaoBaoService extends IService<TaoBao> {

    List<TaoBao> getTaoBao(List<TaoBao> taoBaoList);
    QueryWrapper<TaoBao> getQueryWrapper(TaoBaoQueryRequest taoBaoQueryRequest);

    Page<TaoBao> listTaoBaoByPage(TaoBaoQueryRequest taoBaoQueryRequest);
}
