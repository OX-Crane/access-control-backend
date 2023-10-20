package com.crane.springboot;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.crane.springboot.model.entity.Picture;
import com.crane.springboot.model.entity.Post;
import com.crane.springboot.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = String.format("https://cn.bing.com/images/search?q=雾山五行&form=HDRSC2&first=%d", current);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").attr("m");
            Map<String, String> map = JSONUtil.toBean(m, Map.class);
            String murl = map.get("murl");
            String title = element.select(".inflnk").attr("aria-label");

            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setMurl(murl);

//            入队
            pictureList.add(picture);

//            System.out.println(murl);
//            System.out.println(title);
        }


    }

    @Test
    void testFetchPassage(){
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
        Assertions.assertTrue(b);

    }
}
