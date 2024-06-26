package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.StopWordMapper;
import com.crane.springboot.model.entity.StopWord;
import com.crane.springboot.service.StopWordService;
import org.springframework.stereotype.Service;

@Service
public class StopWordServiceImpl extends ServiceImpl<StopWordMapper, StopWord> implements StopWordService {

}
