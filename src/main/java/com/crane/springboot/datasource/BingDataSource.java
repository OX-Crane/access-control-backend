package com.crane.springboot.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.exception.BusinessException;
import com.crane.springboot.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 知乎数据源
 *
 */

@Service
public class ZhiHuDataSource {
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
//       https://www.zhihu.com/api/v4/search_v3?gk_version=gz-gaokao&t=general&q=%E5%88%80&correction=1&offset=0&limit=20&filter_fields=&lc_idx=0&show_all_topics=0&search_source=Normal
//        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%d", searchText, current);
        String url = String.format("https://www.zhihu.com/api/v4/search_v3?gk_version=gz-gaokao&t=general&q=%s&correction=1&offset=%d&limit=20&filter_fields=&lc_idx=0&show_all_topics=0&search_source=Normal", searchText, current);
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception exception) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取网页明文内容和失败");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            if (pictureList.size() >= pageSize) {
                break;
            }
            String m = element.select(".iusc").attr("m");
            Map<String, String> map = JSONUtil.toBean(m, Map.class);
            String murl = map.get("murl");
            String title = element.select(".inflnk").attr("aria-label");

            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setMurl(murl);
            pictureList.add(picture);
        }
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictureList);


        return null;
    }
}
