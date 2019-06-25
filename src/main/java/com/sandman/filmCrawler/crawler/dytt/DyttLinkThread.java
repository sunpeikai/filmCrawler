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
import com.sandman.filmCrawler.utils.FileUtils;
import com.sandman.filmCrawler.utils.WebClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Date;

/**
 * @author sunpeikai
 * @version DyttLinkThread, v0.1 2019/1/29 10:59
 */
public class DyttLinkThread implements Runnable {

    private boolean run = true;
    private int count = 0;
    private String url = "";
    private String title = "";
    public void run() {
        while (run){
            try{
                if(Link.films.size()==0 && count==0){
                    System.out.println("正在等待root线程执行");
                    Thread.sleep(1000);
                    continue;
                }
                Film film = Link.getOneFilm();
                if(film != null){
                    url = film.getFilmUrl();
                    title = film.getFilmName();
                    film.setFilmClarity("高清");
                    film.setFilmGold(1);
                    film.setBuyCount(0);
                    film.setStatus(1);
                    film.setUpdateTime(new Date());
                    film.setDelFlag(0);
                    getFilmInfo(film);
                    // 加入到队列
                    Link.putMap(film); //FilmDataUtils.insertFilm(film);
                    count++;
                }else{
                    run = false;
                }
                System.out.println("URL线程:[" + Thread.currentThread().getName() + "],已处理个数:[" + count + "]");
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage() + ";;;;;;\turl::" + url + ";;;;\ntitle::" + title);
                FileUtils.writeUrlErrorFile(e.getLocalizedMessage() + ";;;;;;\turl::" + url + ";;;;\ntitle::" + title);
            }
        }
        Link.reduceThreadCount();
        FileUtils.writeInfoFile("INFO线程:[" + Thread.currentThread().getName() + "]，收录[" + count + "]");
    }

    private static void getFilmInfo(Film film)throws Exception{
        System.out.println("获取电影详情线程:[" + Thread.currentThread().getName() + "],url:[" + film.getFilmUrl() + "]");
        WebClient webClient = WebClientUtils.getWebClient();
        HtmlPage htmlPage = webClient.getPage(film.getFilmUrl());
        if(!htmlPage.getTitleText().contains("您的访问出错了") && !htmlPage.getTitleText().contains("404")){
            film.setFilmUrl(null);
            DomElement domElement = htmlPage.getElementById("Zoom");
            // 图片
/*            DomNodeList<HtmlElement> images = domElement.getElementsByTagName("img");
            if(images.size()>0){
                String image = domElement.getElementsByTagName("img").get(0).getAttribute("src");
                film.setFilmImage(image);
            }else{
                film.setFilmImage("暂无图片");
            }*/


            String info = domElement.asText();
            // 上映日期
            String release = formatStr(info,"◎上映日期　");
            if(StringUtils.isNotBlank(release)){
                film.setFilmTime(release);
            }else{
                String date = formatStr(info,"◎年　　代　");
                film.setFilmTime(date);
            }
            // 地区
            String area = formatStr(info,"◎产　　地　");
            if(StringUtils.isNotBlank(release)){
                film.setFilmArea(area);
            }else{
                area = formatStr(info,"◎国　　家　");
                film.setFilmArea(area);
            }


            // 类别
            String type = formatStr(info,"◎类　　别　");
            film.setFilmType(Link.getFilmType(type));
            // 语言
            String language = formatStr(info,"◎语　　言　");
            film.setFilmLanguage(language);
            //
            //String zimu = getInfo(info.substring(info.indexOf("◎字　　幕　")+6));

            // 导演
            String director = formatStr(info,"◎导　　演　");
            film.setFilmDirector(director);
            // 主演
            String actor = formatStr(info,"◎主　　演　");
            actor = removeIfStartsWith(actor,",");
            film.setFilmActor(actor);
            // 简介

            String desc = "";
            if(info.contains("◎简　　介 ")){
                if(info.contains("【")){
                    desc = info.substring(info.indexOf("◎简　　介 ")+6,info.lastIndexOf("【"));
                }else{
                    desc = info.substring(info.indexOf("◎简　　介 ")+6,info.lastIndexOf("\n"));
                }
            }else if(info.contains("◎简　　介")){
                if(info.contains("【")){
                    desc = info.substring(info.indexOf("◎简　　介")+5,info.lastIndexOf("【"));
                }else{
                    desc = info.substring(info.indexOf("◎简　　介")+5,info.lastIndexOf("\n"));
                }
            }else if(info.contains("简　　介")){
                if(info.contains("【")){
                    desc = info.substring(info.indexOf("简　　介")+4,info.lastIndexOf("【"));
                }else{
                    desc = info.substring(info.indexOf("简　　介")+4,info.lastIndexOf("\n"));
                }
            }

            desc = desc.replaceAll("\\n","").replaceAll("\\s+","");
            if(desc.length()>=500){
                desc = desc.substring(0,480);
            }
            // 去掉desc开头的,
            desc = removeIfStartsWith(desc,",");
            film.setFilmDesc(desc);

            DomNodeList<HtmlElement> aList = domElement.getElementsByTagName("a");
            for(HtmlElement a:aList){
                String href = a.getAttribute("href");
                if("#".equals(href)){
                    NamedNodeMap attr = a.getAttributes();
                    for(int i=0;i<attr.getLength();i++){
                        Node node = attr.item(i);
                        if(node.getNodeValue().startsWith("thunder:")){
                            film.setThunderUrl(node.getNodeValue());
                        }
                    }
                }else if(href.startsWith("magnet:")){
                    film.setMagnetUrl(href);
                }
            }



        }else{
            FileUtils.write404ErrorFile(film.getFilmUrl());
            throw new Exception("页面中资源已经不存在");
        }

    }

    private static String formatStr(String source,String format){
        if(source.contains(format)){
            return getInfo(source.substring(source.indexOf(format)+6));
        }
        return "";
    }

    private static String getInfo(String str){
        String result = "";
        if(str.contains("◎")){
            result = str.substring(0, str.indexOf("◎"));
        }else if(str.contains("简　　介")){
            result = str.substring(0, str.indexOf("简　　介"));
        }else{
            return "";
        }
        return result.substring(0, result.lastIndexOf("\n") - 1)
                .replaceAll("[a-zA-Z]", "")
                .replaceAll("\\n", "")
                .replaceAll("\\s+", "")
                .replaceAll("-", "")
                .replaceAll("　+",",")
                // 去掉括号 (***)
                .replaceAll("[\\(（][^\\)）]*[\\)）]$","")
                .replaceAll("\"","")
                .replaceAll("\\/","")
                .replaceAll("\\(","")
                .replaceAll("\\)","")
                .replaceAll("…","");
    }

    private static String removeIfStartsWith(String str,String format){
        if(str.startsWith(format)){
            return str.substring(format.length());
        }else{
            return str;
        }
    }
}
