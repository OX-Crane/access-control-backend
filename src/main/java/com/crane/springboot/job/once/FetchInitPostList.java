package com.crane.springboot.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.crane.springboot.esdao.PostEsDao;
import com.crane.springboot.model.entity.Post;
import com.crane.springboot.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 获取初始帖子列表
//CommandLineRunner接口会让你实现一个run方法，
// 如果将此类表示为spring所管控的bean会在spring启动时运行一次run方法
//因为运行过一次了故将@Component注释

//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {
    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) throws Exception {
        //        1.获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();
//        System.out.println(result);
//        2. json 转 对象
        Map map = JSONUtil.toBean(result, Map.class);
        Integer code = (Integer)map.get("code");
        List<Post> postList = new ArrayList<>();
//        状态码code为0说明成功抓取到数据
        if (code.intValue() == 0) {
            JSONObject data = (JSONObject)map.get("data");
            JSONArray records = (JSONArray)data.get("records");
            for (Object record: records) {
                JSONObject temRecord = (JSONObject) record;

                Post post = new Post();
                post.setTitle((String)temRecord.get("title"));
                post.setContent((String)temRecord.get("content"));
                post.setUserId(1L);

                JSONArray tags = temRecord.get("tags", JSONArray.class);
                List<String> tagsList = tags.toList(String.class);

                post.setTags(JSONUtil.toJsonStr(tagsList));

                postList.add(post);
            }
        }else{
            throw new RuntimeException("数据抓取失败，目标地址返回code为0");
        }
//        System.out.println(postList);

//        3.数据入库
        boolean b = postService.saveBatch(postList);
        if (b) {
            log.info("初始化帖子成功，条数为{}",postList.size());
        } else {
            log.error("初始化帖子失败");
        }


    }
}
