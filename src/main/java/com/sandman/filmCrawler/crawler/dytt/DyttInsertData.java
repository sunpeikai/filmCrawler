/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler.dytt;

import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.utils.FilmDataUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * @author sunpeikai
 * @version DyttInsertData, v0.1 2019/1/31 11:49
 */
public class DyttInsertData {
    private static int count = 0;
    public static void insert(Map<String, Film> filmMap){
        System.out.println("------开始插入数据库------");
        long start = System.currentTimeMillis();
        Iterator iterator = filmMap.entrySet().iterator();
        while (iterator.hasNext()) {
            try {
                Map.Entry entry = (Map.Entry) iterator.next();
                Film film = (Film) entry.getValue();
                System.out.println("开始插入第[" + count + "]条数据，name[" + film.getFilmName() + "]");
                FilmDataUtils.insertFilm(film);
                count++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("插入消耗时间:" + (System.currentTimeMillis() - start) + "ms");

    }
}
