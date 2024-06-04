package com.crane.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.springboot.mapper.WordMapper;
import com.crane.springboot.model.entity.Word;
import com.crane.springboot.service.WordService;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {
}
