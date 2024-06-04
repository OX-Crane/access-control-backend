package com.crane.springboot.controller;

import com.crane.springboot.common.CacheUtils;
import com.crane.springboot.common.Yolo;
import com.crane.springboot.model.dto.search.SearchRequest;
import com.crane.springboot.model.entity.*;
import com.crane.springboot.model.vo.SearchVo;
import com.crane.springboot.utils.PictureDownloadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class EmoController {

    @Value("${emo.keyEmo.value}")
    private int keyEmoValue;

    @Value("${emo.keyEmo.step}")
    private int keyEmoStep;

    @Value("${emo.picEmo.value}")
    private int picEmoValue;

    @Value("${emo.picEmo.step}")
    private int picEmoStep;

    @Value("${emo.emo.value}")
    private int emoValue;

    @Value("${emo.emo.step}")
    private int emoStep;

    private static ArrayList<EmoChange> emoChanges = new ArrayList<>();

    private int counter;


    /**
     * 根据查询后的结果(图片 加 文字)进行分析情感值
     *
     * @return
     */
    public SearchRequest emotionBySearchResult(SearchVo searchVo, SearchRequest searchRequest) {
        List<?> dataSourceList = searchVo.getDataSourceList();

        ArrayList<String> picList = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();

        for (Object dataSource : dataSourceList) {
            if (dataSource instanceof BiliBili) {
                String title = ((BiliBili) dataSource).getTitle();
                String description = ((BiliBili) dataSource).getDescription();
                results.add(title);
                results.add(description);
                String pic = ((BiliBili) dataSource).getPic();
                picList.add(pic);
            }
            if (dataSource instanceof Bing) {
                String title = ((Bing) dataSource).getTitle();
                results.add(title);
            }
            if (dataSource instanceof Picture) {
                String title = ((Picture) dataSource).getTitle();
                results.add(title);
                String murl = ((Picture) dataSource).getMurl();
                picList.add(murl);
            }
            if (dataSource instanceof TaoBao) {
                String text = ((TaoBao) dataSource).getText();
                String product = ((TaoBao) dataSource).getProduct();
                results.add(text);
                results.add(product);
                String url = ((TaoBao) dataSource).getUrl();
                picList.add(url);
            }
        }

        SearchRequest request = listContainWords(results, picList, searchRequest);

        return request;
    }

    /**
     * 判断列表中是否含有敏感词
     */
    public SearchRequest listContainWords(ArrayList<String> results, ArrayList<String> picList, SearchRequest searchRequest) {
        int emo = searchRequest.getEmo();
        int keyEmo = searchRequest.getKeyEmo();
        int picEmo = searchRequest.getPicEmo();

//        判断文字内容
        for (String result : results) {
            if (CacheUtils.neg.contains(result)) {
                emo += emoStep;
            } else if (CacheUtils.act.contains(result)) {
                emo -= emoStep;
            } else if (CacheUtils.word.contains(result)) {
                keyEmo += keyEmoStep;
            }
        }
//        将图片保存到本地
//        if (!picList.isEmpty()) {
//
//            String path = "E:\\yoloTest\\file";
        ArrayList<String> yolo = new ArrayList<>();
//
//            for (String pic : picList) {
//                String fileName = path + "\\file" + counter + ".jpg";
//                PictureDownloadUtils.downloadPicture(pic, fileName);
//            }
//          判断图片内容
        if (searchRequest.getType().equals("bilibili") || searchRequest.getType().equals("picture")) {
            if (searchRequest.getSearchText().equals("gun")) {
                String path = "E:\\yoloTest\\gun";
                yolo = Yolo.useYolo(path);
            } else if (searchRequest.getSearchText().equals("knife")) {
                String path = "E:\\yoloTest\\knife";
                yolo = Yolo.useYolo(path);
            }


        }

        Long num = 0l;
        if (yolo.size() > 0) {
            for (String result : yolo) {
                String[] s = result.split(" ");
                num = Long.valueOf(s[4]);
                if (CacheUtils.neg.contains(s[5].replace(",", "")) || CacheUtils.word.contains(s[5].replace(",", ""))) {
                    picEmo += (num * picEmoStep);
                }
            }
        }
        searchRequest.setEmo(emo);
        searchRequest.setKeyEmo(keyEmo);
        searchRequest.setPicEmo(picEmo);

        return searchRequest;
    }




/**
 * 情感值不在正常范围内的判断
 *
 * @return
 */
public boolean emoExceed(int keyEmo, int picEmo, int emo) {
    if (keyEmo < keyEmoValue && picEmo < picEmoValue && emo < emoValue) {
        return false;
    }
    return true;
}

/**
 * 记录在发送返回结果前的情感
 * 若用户触发了一次keyEmo，然后触发了picEmo，则认为用户是危险的
 *
 * @return
 */
public boolean textThenPic(SearchRequest searchRequest) {
    String type = searchRequest.getType();
    String searchText = searchRequest.getSearchText();
    if ((type.equals("picture") || type.equals("bilibili")) && (CacheUtils.word.contains(searchText) || CacheUtils.neg.contains(searchText))) {
        int taobao = emoChanges.lastIndexOf(new EmoChange("taobao", 2));
        int bing = emoChanges.lastIndexOf(new EmoChange("bing", 2));
        int size = emoChanges.size();
        if (size - taobao > 4 && size - bing > 4) {
            return false;
        }
    }
    return true;
}

/**
 * 根据关键词进行emo值判断同时记录用户的行为
 *
 * @param searchRequest
 * @return
 */
public SearchRequest emotionBySearchText(SearchRequest searchRequest) {
    int keyEmo = searchRequest.getKeyEmo();
    int emo = searchRequest.getEmo();
    String searchText = searchRequest.getSearchText();

    if (StringUtils.isNoneBlank(searchText)) {
//            根据字典判断搜索词是不是负词语
        boolean netContain = CacheUtils.neg.contains(searchText);
//            根据字典判断搜索词是不是正面词
        boolean actContain = CacheUtils.act.contains(searchText);
//            根据字典判断搜索词是不是重点词语
        boolean wordContain = CacheUtils.word.contains(searchText);
//            根据上述条件,对emo值进行更改
        if (netContain) {
            emo += emoStep;
            emoChanges.add(new EmoChange(searchRequest.getType(), 1));
        }
        if (actContain) {
            emo -= emoStep;
            emoChanges.add(new EmoChange(searchRequest.getType(), -1));
        }
        if (wordContain) {
            keyEmo += keyEmoStep;
            emoChanges.add(new EmoChange(searchRequest.getType(), 2));
        }
    }
    searchRequest.setKeyEmo(keyEmo);
    searchRequest.setEmo(emo);

    return searchRequest;
}


}
