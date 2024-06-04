package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.NegativeMapper;
import com.crane.springboot.model.entity.Negative;
import com.crane.springboot.service.NegativeService;
import org.springframework.stereotype.Service;

@Service
public class NegativeServiceImpl extends ServiceImpl<NegativeMapper, Negative>
        implements NegativeService {

}
