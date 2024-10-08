package com.crane.springboot.model.vo;

import com.crane.springboot.model.entity.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 帖子视图
 */
@Data
public class PostVO implements Serializable {

    //    GSON是Google提供的用来在Java对象和JSON数据之间进行映射的Java类库
    private final static Gson GSON = new Gson();

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 包装类转对象
     *
     * @param postVO
     * @return
     */
    public static Post voToObj(PostVO postVO) {
        if (postVO == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postVO, post);
        List<String> tagList = postVO.getTagList();
        if (tagList != null) {
            post.setTags(GSON.toJson(tagList));
        }
        return post;
    }

    /**
     * 对象转包装类
     *
     * @param post
     * @return
     */
    public static PostVO objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);
        postVO.setTagList(GSON.fromJson(post.getTags(), new TypeToken<List<String>>() {
        }.getType()));
        return postVO;
    }
}
