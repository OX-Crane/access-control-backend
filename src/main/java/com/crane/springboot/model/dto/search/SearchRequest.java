package com.crane.springboot.model.dto.search;

import com.crane.springboot.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索类型
     */
    private String type;
    /**
     * 搜索词
     */
    private String searchText;


    private static final long serialVersionUID = 1L;
}