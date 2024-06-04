package com.crane.springboot.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.exception.BusinessException;
import com.crane.springboot.model.entity.Bing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 知乎数据源
 */

@Service
public class BingDataSource implements DataSource {

    @Override
    public Page<Bing> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://cn.bing.com/search?q=%s&form=HDRSC2&first=%d", searchText, current);
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取网页明文内容和失败");
        }

        Elements elements = doc.select(".b_algo");

        ArrayList<Bing> bingList = new ArrayList<>();

        for (Element e : elements) {
            String link = e.getElementsByAttribute("target").attr("href");
            String title = e.getElementsByTag("h2").text();

            bingList.add(new Bing(title, link));
        }
        Page<Bing> bingPage = new Page<>(pageNum, pageSize);
        bingPage.setRecords(bingList);

        return bingPage;

    }
}
