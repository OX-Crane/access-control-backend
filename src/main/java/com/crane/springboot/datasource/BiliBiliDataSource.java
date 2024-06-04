package com.crane.springboot.datasource;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.springboot.model.entity.BiliBili;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class BiliBiliDataSource implements DataSource{

    @Value("${bilibili.address}")
    String url;

    @Value(("${bilibili.Cookie}"))
    String cookie;

    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {

        HashMap<String, String > header = new HashMap<>();

        header.put("Cookie", cookie);
        String body = HttpRequest.get(url).addHeaders(header).execute().body();

        Map<String, JSONObject> bean = JSONUtil.toBean(body, Map.class);

        JSONObject data = bean.get("data");

        ArrayList<JSONObject> result = data.get("result", ArrayList.class);

        JSONObject entries = result.get(11);

        JSONArray jsonArray = entries.get("data", JSONArray.class);

        ArrayList<BiliBili> biliBiliArrayList = new ArrayList<>();

        for (int i = 0; i < 42; i++) {
            JSONObject jsonObject = jsonArray.get(i, JSONObject.class);
            String title = jsonObject.get("title", String.class);
            String author = jsonObject.get("author", String.class);
            String typeName = jsonObject.get("typename", String.class);
            String aid = jsonObject.get("aid", String.class);
            String bvid = jsonObject.get("bvid", String.class);
            String description = jsonObject.get("description", String.class);
            String pic = jsonObject.get("pic", String.class);
            String tag = jsonObject.get("tag", String.class);
            String[] tags= tag.split(",");

            biliBiliArrayList.add(new BiliBili(title,author,typeName, aid, bvid, description, pic, tags));
        }

        pageSize = 42;
        Page<BiliBili> biliBiliPage = new Page<>(pageNum, pageSize);
        biliBiliPage.setRecords(biliBiliArrayList);

        return biliBiliPage;
    }
}
