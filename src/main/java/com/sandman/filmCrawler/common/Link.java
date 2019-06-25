/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.common;

import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.crawler.dytt.DyttInsertData;

import java.util.*;

/**
 * @author sunpeikai
 * @version Link, v0.1 2019/1/29 11:16
 */
public class Link {

    public static Date deadLine;
    public static List<String> root = new ArrayList<String>();
    public static List<Film> films = new ArrayList<Film>();
    public static Map<String,Integer> filmTypes = new HashMap<String, Integer>();
    public static int threadCount = 0;
    public static Map<String,Film> filmMap = new HashMap<String, Film>();
    public static List<String> failUrl = new ArrayList<String>();

    public static synchronized void putMap(Film film){
        filmMap.put(film.getFilmName(),film);

        if((filmMap.size()>=10 && threadCount>0) || threadCount==0){
            System.out.println("开始插入数据库啦:size[" + filmMap.size() + "]");
            Map<String,Film> filmMaps = new HashMap<>();
            filmMap.forEach((name,films) ->{
                filmMaps.put(name, films);
            });
            // 循环插入数据库
            DyttInsertData.insert(filmMaps);
            filmMaps.forEach((key,value)->{
                filmMap.remove(key);
            });

        }
    }

    public static synchronized void finishCrawler(){
        if(threadCount==0 && filmMap.size()>0){
            System.out.println("最后插入数据库啦:size[" + filmMap.size() + "]");
            Map<String,Film> filmMaps = new HashMap<>();
            filmMap.forEach((name,films) ->{
                filmMaps.put(name, films);
            });
            // 循环插入数据库
            DyttInsertData.insert(filmMaps);
            filmMaps.forEach((key,value)->{
                filmMap.remove(key);
            });

        }
    }

    public static synchronized void addThreadCount(){
        threadCount ++;
    }
    public static synchronized void reduceThreadCount(){
        threadCount --;
    }
    public static synchronized String getRootOne(){
        String result = "";
        if(root.size()>0){
            result = root.get(0);
            root.remove(0);
        }
        return result;
    }

    public static synchronized void setFilm(Film film){
        films.add(film);
    }

    public static synchronized Film getOneFilm(){
        Film result = null;
        if(films.size()>0){
            result = films.get(0);
            films.remove(0);
        }
        return result;
    }

    public static Integer getFilmType(String type){
        Integer result = null;
        try{
            if(type.contains("/")){
                type = type.split("/")[0];
            }
            result = filmTypes.get(type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
