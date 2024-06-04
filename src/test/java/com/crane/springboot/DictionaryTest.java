package com.crane.springboot;

import com.crane.springboot.model.entity.Active;
import com.crane.springboot.service.ActiveService;
import com.crane.springboot.service.NegativeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DictionaryTest {

    @Resource
    private ActiveService activeService;

    @Resource
    private NegativeService negativeService;

    @Test
    void testIsIncludeAct() {
        List<String> act = new ArrayList<>();
        List<Active> list = activeService.list();
        for (Active a : list) {
            act.add(a.getText());
        }

        boolean contains = act.contains("健全");
        boolean conta = act.contains("1健全");

        System.out.println(contains);
        System.out.println(conta);
    }



}
