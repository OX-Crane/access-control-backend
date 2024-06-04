package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.NegativelyMapper;
import com.crane.springboot.model.entity.Negatively;
import com.crane.springboot.service.NegativelyService;
import org.springframework.stereotype.Service;

@Service
public class NegativelyServiceImpl extends ServiceImpl<NegativelyMapper, Negatively>
        implements NegativelyService {

}
