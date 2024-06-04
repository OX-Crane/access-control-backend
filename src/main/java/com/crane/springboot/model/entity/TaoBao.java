package com.crane.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName(value = "taobao")
@Data
public class TaoBao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String product;

    private String text;

    private BigDecimal price;

    private String url;

}
