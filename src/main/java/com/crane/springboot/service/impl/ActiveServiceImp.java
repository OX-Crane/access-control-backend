package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.ActiveMapper;
import com.crane.springboot.model.entity.Active;
import com.crane.springboot.service.ActiveService;
import org.springframework.stereotype.Service;

@Service
public class ActiveServiceImp extends ServiceImpl<ActiveMapper, Active>
        implements ActiveService {

}
