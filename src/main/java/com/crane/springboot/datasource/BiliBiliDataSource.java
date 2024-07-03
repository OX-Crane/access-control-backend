package com.crane.springboot.datasource;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.entity.BiliBili;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BiliBiliDataSource implements DataSource {

    @Value("${bilibili.address}")
    String url;

    @Value(("${bilibili.Cookie}"))
    String cookie;

    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {


        String url = "https://search.bilibili.com/all?keyword=" + searchText;
        HashMap<String, String> header = new HashMap<>();
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("连接bilibili失败");
            throw new RuntimeException(e);
        }
        Elements elements = document.select(".video-list .bili-video-card__wrap");

        ArrayList<BiliBili> biliBiliArrayList = new ArrayList<>();
        int i = 0;
        for (Element e : elements) {
            if (i >= pageSize) break;
            Elements a = e.getElementsByTag("a");
            String murl = a.get(0).attr("href");
            Element element = a.select(".v-img").get(0);
            String image = "https:" + element.getElementsByTag("source").get(1).attr("srcset");
            String title = element.getElementsByTag("img").attr("alt");
            String author = e.select(".bili-video-card__info--author").get(0).text();
            String date = e.select(".bili-video-card__info--date").get(0).text().substring(2);

            biliBiliArrayList.add(new BiliBili(title, author, murl, image, date));
            i++;
        }
        pageSize = 10;
        Page<BiliBili> biliBiliPage = new Page<>(pageNum, pageSize);
        biliBiliPage.setRecords(biliBiliArrayList);

        return biliBiliPage;
    }
}
