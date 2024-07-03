package com.crane.springboot;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.entity.BiliBili;
import com.crane.springboot.model.entity.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class BilibiliCrawlerTest {

    @Test
    void testBiliBili() throws IOException {
//        1.获取数据
        int pageSize = 10;
        int pageNum = 1;
        String searchText = "书";
        String url = "https://search.bilibili.com/all?keyword=" + searchText;
        HashMap<String, String > header = new HashMap<>();
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select(".video-list .bili-video-card__wrap");
        int i = 0;
        for(Element e : elements) {
            if (i >= 10) break;
            Elements a = e.getElementsByTag("a");
            String murl = a.get(0).attr("href");

            Element element = a.select(".v-img").get(0);
            String image = "https:" + element.getElementsByTag("source").get(1).attr("srcset");
            String title = element.getElementsByTag("img").attr("alt");

            String author = e.select(".bili-video-card__info--author").get(0).text();

            String date = e.select(".bili-video-card__info--date").get(0).text().substring(2);


            System.out.println("title:" + title + "author" + author + "image" + image + "date" + date);
            i++;
        }

    }

    @Test
    void testZhiHu() {
//        1.获取数据
        String url = "https://api.bilibili.com/x/web-interface/wbi/search/all/v2?__refresh__=true&_extra=&context=&page=1&page_size=42&order=&duration=&from_source=&from_spmid=333.337&platform=pc&highlight=1&single_column=0&keyword=2222222&qv_id=UQyxiJIE6McwN3UnJ1UrFKmzyxRPzzg7&ad_resource=5646&source_tag=3&web_location=1430654&w_rid=2422204bf552097be92d0872cbc54813&wts=1716876876";
        HashMap<String, String > header = new HashMap<>();
        header.put("Cookie", "buvid3=9F96113C-D10F-E69D-992E-CC533CA7F11124688infoc; b_nut=1703762824; _uuid=F8A15FB3-3779-B995-B4A9-8C9EBE5BBEF124835infoc; buvid4=BEFEB271-B51C-5C25-E9F1-25A0F4DCA38925547-023122811-9sM4r%2F884bff%2FJM%2BeuQf7w%3D%3D; rpdid=|(k|JukRlm~k0J'u~|J~Yk)kY; buvid_fp_plain=undefined; enable_web_push=DISABLE; header_theme_version=CLOSE; hit-dyn-v2=1; LIVE_BUVID=AUTO3517044579065936; CURRENT_BLACKGAP=0; FEED_LIVE_VERSION=V_HEADER_LIVE_NEW_POP; CURRENT_QUALITY=127; bp_video_offset_58803854=931702174840258692; CURRENT_FNVAL=4048; PVID=7; fingerprint=6aa8f38331e1b8d1fb15d53a9a9f96c5; buvid_fp=6aa8f38331e1b8d1fb15d53a9a9f96c5; b_lsid=F25FB6FA_18FBDD231B7; sid=74borunu; home_feed_column=4; browser_resolution=522-716; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTcxMzU4MzQsImlhdCI6MTcxNjg3NjU3NCwicGx0IjotMX0.OEz6LDriwc50YOa8PVi_8mOwQ8KeCesRmq3E3DeuQws; bili_ticket_expires=1717135774; bp_t_offset_58803854=936461728541048853");
        String body = HttpRequest.get(url).addHeaders(header).execute().body();

        Map bean = JSONUtil.toBean(body, Map.class);



        System.out.println(111);


    }

}
