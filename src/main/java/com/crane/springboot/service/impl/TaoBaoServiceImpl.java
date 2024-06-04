package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.TaoBaoMapper;
import com.crane.springboot.model.entity.TaoBao;
import com.crane.springboot.service.TaoBaoService;
import org.springframework.stereotype.Service;

@Service
public class TaoBaoServiceImpl extends ServiceImpl<TaoBaoMapper, TaoBao> implements TaoBaoService {
}
