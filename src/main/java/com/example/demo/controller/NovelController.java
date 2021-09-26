package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import com.example.demo.entity.Novel;
import com.example.demo.entity.NovelContent;
import com.example.demo.dao.NovelContentDao;
import com.example.demo.dao.NovelDao;
import com.example.demo.entity.NovelTitle;
import com.example.demo.dao.NovelTitleDao;
import com.example.demo.R;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RestController
public class NovelController {

    private Logger logger = LoggerFactory.getLogger(NovelController.class);

    @Autowired
    private NovelDao novelDao;

    @Autowired
    private NovelContentDao novelContentDao;

    @Autowired
    private NovelTitleDao novelTitleDao;

    @RequestMapping("novelList")
    public R novelList(int page, int limit) {

        Page<Novel> list = novelDao.findAll(PageRequest.of(page - 1, limit, Direction.ASC, "endFlag"));
        long count = novelDao.count();

        return R.ok(list.getContent(), count);
    }


    @RequestMapping("save")
    public String save(@RequestBody Novel novel) {
        if (novel.getSerialNo() != null) {
            Novel n = novelDao.findById(novel.getSerialNo()).get();
            novel.setLastTime(n.getLastTime());
        }
        novelDao.save(novel);
        return "index";
    }

    @RequestMapping("updateEndFlag")
    public String updateEndFlag(@RequestBody Novel novel) {
        Novel n = novelDao.findById(novel.getSerialNo()).get();
        n.setEndFlag(novel.getEndFlag());
        novelDao.save(n);
        return "index";
    }

    @RequestMapping("delete")
    public String delete(@RequestBody Novel novel) {
        novelDao.delete(novel);
        return "index";
    }

    @RequestMapping("novelTitleList")
    public R novelTitleList(long novelNo, int page, int limit) {

        NovelTitle nt = new NovelTitle();
        nt.setNovelNo(novelNo);

        Example<NovelTitle> example = Example.of(nt);
        Page<NovelTitle> list = novelTitleDao.findAll(example,
                PageRequest.of(page - 1, limit, Direction.DESC, "serialNo"));
        long count = novelTitleDao.count(example);

        return R.ok(list.getContent(), count);
    }

    @RequestMapping("output")
    public void output(@RequestBody Novel novel, HttpServletResponse response) throws IOException {
        novel = novelDao.getOne(novel.getSerialNo());
        FileUtil.del("D://novel/" + novel.getNovelName() + ".txt");
        FileWriter writer = new FileWriter("D://novel/" + novel.getNovelName() + ".txt");
        List<NovelContent> findByNovelNo = novelContentDao.findByNovelNo(novel.getSerialNo());
        for (NovelContent novelContent : findByNovelNo) {
            writer.append(novelContent.getTitle() + "\r\n" + novelContent.getContent().replace("<br>", "\r\n"));
        }
//		FileOutputStream fos = new FileOutputStream("D://novel/" + novel.getNovelName() + ".txt");
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        // 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(novel.getNovelName() + ".txt".getBytes()));
        // 获取文件输入流
        InputStream in = new FileInputStream("D://novel/" + novel.getNovelName() + ".txt");
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            // 将缓冲区的数据输出到客户端浏览器
            out.write(buffer, 0, len);
        }
        in.close();
    }


    @RequestMapping("outputList")
    public void outputList(@RequestBody List<NovelTitle> novels) throws IOException {
        Novel novel = novelDao.getOne(novels.get(0).getNovelNo());
        FileUtil.del("D://novel/" + novel.getNovelName() + "-自定义章节.txt");
        FileWriter writer = new FileWriter("D://novel/" + novel.getNovelName() + "-自定义章节.txt");
        novels.stream().sorted(Comparator.comparing(n -> n.getSerialNo())).forEach((n) -> {
            NovelContent novelContent = novelContentDao.findByTitleNo(n.getSerialNo());
            writer.append(novelContent.getTitle() + "\r\n" + novelContent.getContent().replace("<br>", "\r\n"));
        });
    }

    @RequestMapping("download")
    public int download(@RequestBody Novel novel) throws IOException {
        novel = novelDao.getOne(novel.getSerialNo());
        int nums = this.start(novel);
        novel.setLastTime(DateUtil.now());
        novelDao.save(novel);
        return nums;
    }

    @RequestMapping("downloadall")
    public int downloadall() throws IOException {
        List<Novel> novel = novelDao.findAll();
        int[] arr = new int[]{0};
        novel.stream().filter(a -> a.getEndFlag() == 0).forEach(a -> {
            try {
                int nums = this.start(a);
                arr[0] += nums;
            } catch (IOException e) {
                e.printStackTrace();
            }
            a.setLastTime(DateUtil.now());
            novelDao.save(a);
        });
        return arr[0];
    }

    public int start(Novel novel) throws IOException {
        int nums = 0;
        String urlHead = novel.getNovelUrl();
        String novelName = novel.getNovelName();
        NovelTitle ntitle = novelTitleDao.findByNovelNoLimitOne(novel.getSerialNo());
        String startIndex = "";
        if (ntitle == null) {
            startIndex = "《" + novelName + "》正文";
        } else {
            startIndex = ntitle.getUrl();
        }
        Connection connect = Jsoup.connect(urlHead);
        // 模拟浏览器的请求头
        connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 开始连接HTTP请求。
        Connection.Response demo = connect.ignoreContentType(true).method(Connection.Method.GET).execute();
        Document documentDemo = demo.parse();
        // 这里就是获取该页面的HTML元素。
        String text = documentDemo.toString();
        String title = text
                .substring(text.indexOf(startIndex, text.indexOf("《" + novelName + "》正文")),
                        text.indexOf("<div id=\"footer\" name=\"footer\">", text.indexOf("《" + novelName + "》正文")))
                .replace("</h1>", "").replace(" ", "");
        String[] urls = title.split("<dd>");

        for (int i = 1; i < urls.length; i++) {
            String url = urls[i].replace("style=\"\"", "");
            url = url.substring(url.indexOf("<ahref=\""), url.indexOf("\">")).replace("<ahref=\"", "");
            url = url.substring(url.lastIndexOf("/") + 1);
            getContent(novel, url);
            nums++;
        }
        return nums;
    }

    public void getContent(Novel novel, String url) throws IOException {
        NovelContent ncontent = new NovelContent();
        ncontent.setUrl(url);
        ncontent.setNovelNo(novel.getSerialNo());
        Connection connect = Jsoup.connect(novel.getNovelUrl() + url);
        // 模拟浏览器的请求头
        connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 开始连接HTTP请求。
        Connection.Response demo = connect.ignoreContentType(true).method(Connection.Method.GET).execute();
        Document documentDemo = demo.parse();
        // 这里就是获取该页面的HTML元素。
        String text = documentDemo.toString();

        String title = text.substring(text.indexOf("<div class=\"bookname\">"), text.indexOf("<div class=\"bottem1\">"))
                .replace("<div class=\"bookname\">", "").replace("<h1>", "").replace("</h1>", "").replace(" ", "");
        String content = text.substring(text.indexOf("<div id=\"content\">"), text.indexOf("<div class=\"bottem2\">"))
                .replace("<div class=\"content\">", "").replace("<br>", "").replace("</br>", "").replace("&nbsp;", "")
                .replace("</div>", "").replace("<script>read3();</script>", "")
                .replace("<script>bdshare();</script>", "").replace("<div id=\"content\">", "").replace(" ", "")
                .replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "<br>").replaceAll("^((\r\n)|\n)", "<br>");

        logger.info("爬取{}--{}", novel.getNovelName(), title);
        ncontent.setTitle(title);
        ncontent.setContent(content);
        NovelTitle ntitle = new NovelTitle();
        ntitle.setNovelNo(novel.getSerialNo());
        ntitle.setTitle(title);
        ntitle.setUrl(url);
        ntitle.setRegDate(DateUtil.now());
        novelTitleDao.save(ntitle);
        ncontent.setTitleNo(ntitle.getSerialNo());
        novelContentDao.save(ncontent);
        FileWriter writer = new FileWriter("D://novel/" + novel.getNovelName() + LocalDate.now().toString() + ".txt");
        writer.append(title + "\r\n" + content.replace("<br>", "\r\n"));
    }
}
