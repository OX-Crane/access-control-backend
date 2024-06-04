package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.exception.BusinessException;
import com.crane.springboot.mapper.TaoBaoMapper;
import com.crane.springboot.model.dto.taobao.TaoBaoQueryRequest;
import com.crane.springboot.model.entity.TaoBao;
import com.crane.springboot.service.TaoBaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaoBaoServiceImpl extends ServiceImpl<TaoBaoMapper, TaoBao> implements TaoBaoService {

    @Override
    public List<TaoBao> getTaoBao(List<TaoBao> taoBaoList) {
        if (CollectionUtils.isNotEmpty(taoBaoList)) {
            return new ArrayList<>();
        }
        return taoBaoList;
    }

    @Override
    public QueryWrapper<TaoBao> getQueryWrapper(TaoBaoQueryRequest taoBaoQueryRequest) {
        if (taoBaoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = taoBaoQueryRequest.getId();
        String product = taoBaoQueryRequest.getProduct();
        String url = taoBaoQueryRequest.getUrl();
        String text = taoBaoQueryRequest.getText();
        BigDecimal price = taoBaoQueryRequest.getPrice();
        QueryWrapper<TaoBao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(product), "product", product);
        queryWrapper.eq(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.like(StringUtils.isNotBlank(text), "text", text);
        queryWrapper.eq(price != null, "price", price);
        return queryWrapper;
    }

    @Override
    public Page<TaoBao> listTaoBaoByPage(TaoBaoQueryRequest taoBaoQueryRequest) {
        long current = taoBaoQueryRequest.getCurrent();
        long size = taoBaoQueryRequest.getPageSize();
        Page<TaoBao> taoBaoPage = this.page(new Page<>(current, size),
                this.getQueryWrapper(taoBaoQueryRequest));
        Page<TaoBao> page = new Page<>(current, size, taoBaoPage.getTotal());
        page.setRecords(taoBaoPage.getRecords());

        return page;
    }
}
