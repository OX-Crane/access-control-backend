package com.crane.springboot;

import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ZhiHuCrawlerTest {

    @Test
    void test() {
        int pageSize = 10;
        int pageNum = 1;
        String searchText = "刀";
        long current = (pageNum - 1) * pageSize;
//       https://www.zhihu.com/api/v4/search_v3?gk_version=gz-gaokao&t=general&q=%E5%88%80&correction=1&offset=0&limit=20&filter_fields=&lc_idx=0&show_all_topics=0&search_source=Normal
//        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%d", searchText, current);
        String url = String.format("https://www.zhihu.com/api/v4/search_v3?gk_version=gz-gaokao&t=general&q=%s&correction=1&offset=%d&limit=20&filter_fields=&lc_idx=0&show_all_topics=0&search_source=Normal", searchText, current);
        String result = HttpRequest.get(url).execute().body();

        System.out.println(result);




    }

}
