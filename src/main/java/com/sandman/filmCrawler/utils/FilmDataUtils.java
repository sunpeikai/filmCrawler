/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.utils;

import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.FilmType;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunpeikai
 * @version FilmDataUtils, v0.1 2019/1/29 9:51
 */
public class FilmDataUtils {

    public static List<Film> getNeedUpdateImage(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.filmMapper.getNeedUpdateImage";
        List<Film> films = sqlSession.selectList(sql);
        sqlSession.close();
        return films;
    }

    public static List<Film> getAllFilms(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.filmMapper.getAllFilms";
        List<Film> films = sqlSession.selectList(sql);
        sqlSession.close();
        return films;
    }

    public static int updateFilmImage(Film film){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.filmMapper.updateFilmImage";
        int result = sqlSession.update(sql,film);
        sqlSession.commit();
        //System.out.println(film.getId() + ";;;;" + film.getFilmImage() + "结果：" + result);
        sqlSession.close();
        return result;
    }

    public static Date getRecentlyDate(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.filmMapper.getRecentlyDate";
        Film film = sqlSession.selectOne(sql);
        sqlSession.close();
        if(film != null){
            return film.getCreateTime();
        }else{
            return null;
        }

    }

    public static synchronized void insertFilm(Film film){

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.filmMapper.insertFilm";
        sqlSession.insert(sql,film);
        sqlSession.commit();
        //System.out.println(films);
        sqlSession.close();
    }

    public static Map<String,Integer> getAllFilmType(){
        Map<String,Integer> result = new HashMap<String, Integer>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.typeMapper.getAll";
        List<FilmType> filmTypeList = sqlSession.selectList(sql);
        sqlSession.close();
        for(FilmType filmType:filmTypeList){
            result.put(filmType.getName(),filmType.getId());
        }
        return result;
    }

    public static int formatStr(Film film){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sql = "com.sandman.filmCrawler.mapper.filmMapper.formatStr";
        int result = sqlSession.insert(sql,film);
        sqlSession.commit();
        //System.out.println(films);
        sqlSession.close();
        return result;
    }
}
