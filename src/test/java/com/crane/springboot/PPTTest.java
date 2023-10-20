package com.crane.springboot;

import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PPTTest {

    @Test
    void testFetchPPT() {
        String url = "https://max.book118.com/html/2021/0126/5120032341003114.shtm";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception exception) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取网页明文内容和失败");
        }

        Elements elements = doc.select(".view210.pic");
        log.info("test");
    }
}
