/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler.dytt;

import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.common.Link;
import com.sandman.filmCrawler.utils.FileUtils;
import com.sandman.filmCrawler.utils.FilmDataUtils;

import java.util.*;

/**
 * @author sunpeikai
 * @version DyttService, v0.1 2019/1/29 9:17
 */
public class DyttService {

    public static void main(String[] args){
        Link.filmTypes = FilmDataUtils.getAllFilmType();
        Link.deadLine = FilmDataUtils.getRecentlyDate();
        Link.root.add("http://www.ygdy8.net/html/gndy/rihan/list_6_{index}.html");
        Link.root.add("http://www.ygdy8.net/html/gndy/dyzz/list_23_{index}.html");
        Link.root.add("http://www.ygdy8.net/html/gndy/oumei/list_7_{index}.html");
        Link.root.add("http://www.ygdy8.net/html/gndy/china/list_4_{index}.html");
        Link.root.add("http://www.ygdy8.net/html/gndy/jddy/list_63_{index}.html");
        for(int i=0;i<5;i++){
            Thread root = new Thread(new DyttRootThread());
            root.setName("ROOT" + i);
            root.start();
            Link.addThreadCount();
        }
        // 阻塞掉主进程，等待ROOT线程结束再运行Link线程
        canRun();
        // 去重
        removeObj();
        FileUtils.writeInfoFile("ROOT线程共收录[" + Link.films.size() + "]");
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new DyttLinkThread());
            thread.setName("URL" + i);
            thread.start();
            Link.addThreadCount();
        }
        // 阻塞掉主进程，等待Link线程结束再将剩余的数据插入数据库
        canRun();
        // flush缓存
        Link.finishCrawler();
        //FileUtils.writeInfoFile("URL线程共收录[" + Link.filmMap.size() + "]");

    }

    private static void canRun(){
        while (Link.threadCount != 0){
            try{
                // 线程休眠3秒
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void removeObj(){
        long start = System.currentTimeMillis();
        List<Film> list = Link.films;
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).getFilmName().equals(list.get(i).getFilmName()))  {
                    list.remove(j);
                }
            }
        }
        System.out.println("去重所耗时间:[" + (System.currentTimeMillis() - start) + "ms]");
    }

}
