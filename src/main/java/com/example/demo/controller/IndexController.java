package com.example.demo.controller;

import com.example.demo.entity.Novel;
import com.example.demo.entity.NovelContent;
import com.example.demo.dao.NovelContentDao;
import com.example.demo.dao.NovelDao;
import com.example.demo.entity.NovelTitle;
import com.example.demo.dao.NovelTitleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private NovelDao novelDao;

    @Autowired
    private NovelContentDao novelContentDao;

    @Autowired
    private NovelTitleDao novelTitleDao;

    @RequestMapping("index")
    public String index(Model model) {
        return "index";
    }


    @RequestMapping("detail/{serialNo}")
    public String detail(@PathVariable Long serialNo, Model model) {
        Optional<Novel> novel = novelDao.findById(serialNo);
        model.addAttribute("novelName", novel.map(Novel::getNovelName).orElse(""));
        List<NovelTitle> list = novelTitleDao.findByNovelNo(serialNo);
        model.addAttribute("list", list);
        model.addAttribute("novelNo", serialNo);
        return "detail";
    }

    @RequestMapping("novelInfo/{serialNo}")
    public String novelInfo(@PathVariable Long serialNo, Model model) {
        Optional<Novel> novel = novelDao.findById(serialNo);
        model.addAttribute("novel", novel.orElseGet(Novel::new));
        return "novelInfo";
    }


    @RequestMapping("content/{serialNo}")
    public String content(@PathVariable Long serialNo, Model model) {
        NovelContent content = novelContentDao.findByTitleNo(serialNo);
        model.addAttribute("content", content);
        return "content";
    }
}
