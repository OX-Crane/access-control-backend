package com.crane.springboot.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.common.ErrorCode;
import com.crane.springboot.exception.BusinessException;
import com.crane.springboot.model.entity.Picture;
import com.crane.springboot.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 照片服务实现类
 */
@Service
public class PictureDataSource implements DataSource<Picture> {

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%d", searchText, current);
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
        return picturePage;
    }


}
