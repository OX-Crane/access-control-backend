package com.crane.springboot.common;

import com.crane.springboot.model.entity.Active;
import com.crane.springboot.model.entity.Negative;
import com.crane.springboot.model.entity.Word;
import com.crane.springboot.service.ActiveService;
import com.crane.springboot.service.NegativeService;
import com.crane.springboot.service.WordService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheUtils {
    public static List<String> act = new ArrayList<>();

    public static List<String> neg = new ArrayList<>();

    public static List<String> word = new ArrayList<>();

    @Resource
    private ActiveService activeService;

    @Resource
    private NegativeService negativeService;

    @Resource
    private WordService wordService;

    @PostConstruct
    public void init() {
        List<Active> listAct = activeService.list();
        List<Negative> listNeg = negativeService.list();
        List<Word> listWord = wordService.list();
        for (Active a : listAct) {
            act.add(a.getText());
        }
        for (Negative n : listNeg) {
            neg.add(n.getText());
        }
        for (Word w : listWord) {
            word.add(w.getText());
        }


    }
}
