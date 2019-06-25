/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sandman.filmCrawler.utils.WebClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Date;

/**
 * @author sunpeikai
 * @version TestCrawler, v0.1 2019/1/30 17:14
 */
public class TestCrawler {
    public static void main(String[] args) throws Exception {
        getFilmInfo("http://www.ygdy8.net/html/gndy/dyzz/20121202/40447.html");
    }

    private static void getFilmInfo(String url) throws Exception {
        System.out.println("获取电影详情线程:[" + Thread.currentThread().getName() + "],url:[" + url + "]");
        WebClient webClient = WebClientUtils.getWebClient();
        HtmlPage htmlPage = webClient.getPage(url);
        if (!htmlPage.getTitleText().contains("您的访问出错了") && !htmlPage.getTitleText().contains("404")) {

            DomElement domElement = htmlPage.getElementById("Zoom");
            // 图片

            DomNodeList<HtmlElement> images = domElement.getElementsByTagName("img");
            if (images.size() > 0) {
                String image = domElement.getElementsByTagName("img").get(0).getAttribute("src");
                System.out.println(image);
            } else {
                System.out.println("暂无图片");
            }


            String info = domElement.asText();
            System.out.println(info);
            // 上映日期
            String release = formatStr(info, "◎上映日期　");
            if (StringUtils.isNotBlank(release)) {
                System.out.println(release);
            } else {
                String date = formatStr(info, "◎年　　代　");
                System.out.println(date);
            }
            // 地区
            String area = formatStr(info, "◎产　　地　");
            if (StringUtils.isNotBlank(release)) {
                System.out.println(area);
            } else {
                area = formatStr(info, "◎国　　家　");
                System.out.println(area);
            }


            // 类别
            String type = formatStr(info, "◎类　　别　");
            System.out.println(type);
            // 语言
            String language = formatStr(info, "◎语　　言　");
            System.out.println(language);
            //
            //String zimu = getInfo(info.substring(info.indexOf("◎字　　幕　")+6));

            // 导演
            String director = formatStr(info, "◎导　　演　");
            System.out.println(director);
            // 主演
            String actor = formatStr(info, "◎主　　演　");
            System.out.println(actor);
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
            desc = desc.replaceAll("\\n", "").replaceAll("\\s+", "");
            if (desc.length() >= 500) {
                desc = desc.substring(0, 480);
            }
            System.out.println(desc);
            System.out.println("--------------------------------");
            DomNodeList<HtmlElement> aList = domElement.getElementsByTagName("a");
            for (HtmlElement a : aList) {
                String href = a.getAttribute("href");
                if ("#".equals(href)) {
                    NamedNodeMap attr = a.getAttributes();
                    for (int i = 0; i < attr.getLength(); i++) {
                        Node node = attr.item(i);
                        if (node.getNodeValue().startsWith("thunder:")) {
                            System.out.println(node.getNodeValue());
                        }
                    }
                } else if (href.startsWith("magnet:")) {
                    System.out.println(href);
                }
            }


        } else {
            //FileUtils.write404ErrorFile(film.getFilmUrl());
        }

    }

    private static String formatStr(String source, String format) {
        if (source.contains(format)) {
            return getInfo(source.substring(source.indexOf(format) + 6));
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
                //.replaceAll("\\.","")
                .replaceAll("\"","")
                .replaceAll("\\/","")
                .replaceAll("\\(","")
                .replaceAll("\\)","")
                .replaceAll("…","");
    }
}
