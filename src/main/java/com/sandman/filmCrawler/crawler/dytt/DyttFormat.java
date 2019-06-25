/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler.dytt;

import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.utils.FilmDataUtils;

import java.util.List;

/**
 * @author sunpeikai
 * @version DyttFormat, v0.1 2019/2/11 10:04
 */
@Deprecated
public class DyttFormat {
    private static int result = 0;
    public static void main(String[] args){
        List<Film> filmList = FilmDataUtils.getAllFilms();
        for(Film film:filmList){
            System.out.println("处理第[" + result + "]个数据");
            //String actor = format(film.getFilmActor());
            //String desc = format(film.getFilmDesc());
            //film.setFilmActor(actor);
            // 处理desc开头是，
            result += formatDesc(film);

            //String desc = formatDesc(film.getFilmDesc());
            //film.setFilmDesc(desc);

        }
        //System.out.println("处理个数:" + result);
    }

    private static String format(String str){
        return str.replaceAll("[\\x00-\\xff]","")
                //.replaceAll(",","")
                .replaceAll("　+",",")
                //.replaceAll("\\.","")
                .replaceAll("\"","")
                .replaceAll("\\/","")
                .replaceAll("\\(","")
                .replaceAll("\\)","")
                .replaceAll("…","");
    }
    private static int formatDesc(Film film){
        if(film.getFilmDesc().startsWith(",") || film.getFilmActor().startsWith(",")){
            System.out.println("需要处理！！！！！！！");
            if(film.getFilmDesc().startsWith(",")){
                film.setFilmDesc(film.getFilmDesc().substring(1));
            }
            if(film.getFilmActor().startsWith(",")){
                film.setFilmActor(film.getFilmActor().substring(1));
            }
            return FilmDataUtils.formatStr(film);
        }else{
            return 1;
        }
    }
}
