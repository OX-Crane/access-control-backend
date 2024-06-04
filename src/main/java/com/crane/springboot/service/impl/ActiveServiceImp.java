package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.ActivelyMapper;
import com.crane.springboot.model.entity.Actively;
import com.crane.springboot.service.ActivelyService;
import org.springframework.stereotype.Service;

@Service
public class ActivelyServiceImp extends ServiceImpl<ActivelyMapper, Actively>
        implements ActivelyService {

}
