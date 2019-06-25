/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler.dytt;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.common.Link;
import com.sandman.filmCrawler.utils.DateUtils;
import com.sandman.filmCrawler.utils.FileUtils;
import com.sandman.filmCrawler.utils.WebClientUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author sunpeikai
 * @version DyttLinkThread, v0.1 2019/1/29 10:59
 */
public class DyttRootThread implements Runnable {

    private boolean run = true;
    private int page = 1;
    private int count = 0;

    public void run() {
        String url = Link.getRootOne();
        while (run){
            String realUrl = url.replace("{index}",""+page);
            if(StringUtils.isNotBlank(realUrl)){
                crawler(realUrl);
                page ++ ;
            }else{
                run = false;
            }
        }
        Link.reduceThreadCount();
        System.out.println("ROOT线程:[" + Thread.currentThread().getName() + "]，收录[" + count + "]");
        FileUtils.writeInfoFile("ROOT线程:[" + Thread.currentThread().getName() + "]，收录[" + count + "]");
    }

    private void crawler(String url){
        try{
            String root = "http://www.ygdy8.net";
            WebClient webClient = WebClientUtils.getWebClient();
            HtmlPage htmlPage = webClient.getPage(url);
            DomNodeList<DomElement> tables = htmlPage.getElementsByTagName("table");
            if(!htmlPage.getTitleText().contains("您的访问出错了")){
                // 页码超出限制，404
                for(DomElement table:tables){
                    if(table.hasAttribute("class") && "tbspan".equals(table.getAttribute("class"))){
                        String date = "";

                        DomNodeList<HtmlElement> fontList = table.getElementsByTagName("font");
                        for(HtmlElement font:fontList){
                            // 获取发布日期是为了跟数据库比对，防止重复扫描
                            if("#8F8C89".equals(font.getAttribute("color"))){
                                date = font.asText().substring(3,22);
                            }

                        }

                        if(StringUtils.isNotBlank(date)){
                            Date pageDate = DateUtils.formatDate(date);
                            if(pageDate!=null && (DateUtils.before(pageDate,Link.deadLine) || Link.deadLine == null)){

                                Film film = new Film();

                                film.setCreateTime(pageDate);
                                DomNodeList<HtmlElement> bList = table.getElementsByTagName("b");
                                for(HtmlElement b:bList){
                                    DomNodeList<HtmlElement> aList = b.getElementsByTagName("a");
                                    for(HtmlElement a:aList){
                                        String title = a.asText();
                                        String href = root + a.getAttribute("href");
                                        if(!href.contains("index")){
                                            // 不包含index说明该链接是电影详情链接
                                            if(title.contains("《") && title.contains("》")){
                                                film.setFilmName(title.substring(title.indexOf("《")+1,title.lastIndexOf("》")));
                                            }else if(title.contains("《") && !title.contains("》")){
                                                film.setFilmName(title.substring(title.indexOf("《")+1));
                                            }else if(!title.contains("《") && title.contains("》")){
                                                film.setFilmName(title.substring(0,title.lastIndexOf("》")));
                                            }else{
                                                film.setFilmName(title);
                                            }

                                            film.setFilmUrl(href);
                                        }
                                    }
                                }

                                Link.setFilm(film);
                                System.out.println("root预收录 -> url:[" + film.getFilmUrl() + "]");
                                // count ++
                                count++;
                            }else{
                                run = false;
                            }
                        }

                    }

                }
            }else{
                run = false;
            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage() + "\turl:::" + url);
            FileUtils.writeRootErrorFile(e.getLocalizedMessage() + "\turl:::" + url);
        }

    }

}
