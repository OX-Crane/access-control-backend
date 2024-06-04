package com.crane.springboot;

import com.crane.springboot.model.entity.Bing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;


@SpringBootTest
public class BingCrawlerTest {

    @Test
    void test() throws IOException {
        int pageSize = 10;
        int pageNum = 1;
        String searchText = "原神";
        long current = (pageNum - 1) * pageSize;
//        https://cn.bing.com/search?q=ikun&qs=n&form=QBRE&sp=-1&lq=0&pq=%E6%9E%AA%E6%94%AF12&sc=10-4&sk=&cvid=272FC760C7274A7DBA90066A5D7B81F9&ghsh=0&ghacc=0&ghpl=
        String url = String.format("https://cn.bing.com/search?q=%s&form=HDRSC2&first=%d", searchText, current);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".b_algo");

        ArrayList<Bing> bingList = new ArrayList<>();

        for (Element e : elements) {
            String link = e.getElementsByAttribute("target").attr("href");
            String title = e.getElementsByTag("h2").text();

            System.out.println(new Bing(title, link));
        }


    }

}
