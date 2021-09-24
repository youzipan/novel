package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        Novel novel = novelDao.findById(serialNo).get();
        List<NovelTitle> list = novelTitleDao.findByNovelNo(serialNo);
        model.addAttribute("list", list);
        model.addAttribute("novelName", novel.getNovelName());
        model.addAttribute("novelNo", serialNo);
        return "detail";
    }

    @RequestMapping("novelInfo/{serialNo}")
    public String novelInfo(@PathVariable Long serialNo, Model model) {
        Novel novel = novelDao.findById(serialNo).get();
        model.addAttribute("novel", novel);
        return "novelInfo";
    }


    @RequestMapping("content/{serialNo}")
    public String content(@PathVariable Long serialNo, Model model) {
        NovelContent content = novelContentDao.findByTitleNo(serialNo);
        model.addAttribute("content", content);
        return "content";
    }
}
