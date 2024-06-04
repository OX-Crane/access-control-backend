package com.crane.springboot.model.dto.taobao;

import com.crane.springboot.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaoBaoQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String product;

    private String text;

    private BigDecimal price;

    private String url;





}
