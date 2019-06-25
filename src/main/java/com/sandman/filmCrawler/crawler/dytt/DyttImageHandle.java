/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler.dytt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sandman.filmCrawler.Film;
import com.sandman.filmCrawler.utils.FileUtils;
import com.sandman.filmCrawler.utils.FilmDataUtils;
import com.sandman.filmCrawler.utils.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sunpeikai
 * @version DyttImageHandle, v0.1 2019/2/27 14:48
 */
public class DyttImageHandle {
    // 豆瓣获取电影信息接口
    private static String douban = "https://movie.douban.com/j/subject_suggest?q=";

    private static String startWith = "http://117.48.197.114";
    public static void main(String[] args){
        List<Film> filmList = FilmDataUtils.getNeedUpdateImage();
        List<Film> films = new ArrayList<>();
        for(Film film:filmList){
            if(StringUtils.isBlank(film.getFilmImage()) || !film.getFilmImage().startsWith(startWith)){
                films.add(film);
            }
        }
        System.out.println("一共需要更新 -> [" + films.size() + "]条数据");
        // 先将图片更新到数据库，然后下载图片
        if(films.size()>0){
            updateImage(films);
            downloadImage(films);
        }

    }

    /**
     * 更新图片到数据库中
     * @auth sunpeikai
     * @param
     * @return
     */
    private static void updateImage(List<Film> filmList){
        int count = 0;
        for(Film film:filmList){
            try {
                if(StringUtils.isBlank(film.getFilmImage())){
                    //如果数据库中图片是空的就开始更新
                    String url = douban;
                    String name = null;
                    if(film.getFilmName().contains("/")){
                        name = URLEncode(film.getFilmName().split("/")[0],"UTF-8");
                    }else{
                        name = URLEncode(film.getFilmName(),"UTF-8");
                    }
                    if(StringUtils.isNotBlank(name)){
                        url += name;
                        System.out.println(url);
                        byte[] resultByte = HttpUtils.doGet(url);
                        String resultStr = new String(resultByte);
                        if(resultStr.length()>2){
                            JSONArray resultArr = JSONObject.parseArray(resultStr);
                            JSONObject result = resultArr.getJSONObject(0);
                            if(result != null && result.containsKey("img")){
                                String img = result.getString("img");
                                film.setFilmImage(imgUrl2Jpg(img));
                                FilmDataUtils.updateFilmImage(film);
                                count ++ ;
                                System.out.println("第[" + count + "]条数据更新image成功 -> id:[" + film.getId() + "],name:[" + film.getFilmName() + "]");
                            }
                        }else{
                            FileUtils.writeUpdateImageFailedFile(film);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                FileUtils.writeUpdateImageFailedFile(film);
            }
        }
    }

    /**
     * 下载图片
     * @auth sunpeikai
     * @param
     * @return
     */
    private static void downloadImage(List<Film> filmList){
        int count = 0;
        for(Film film:filmList){
            try {
                if(StringUtils.isNotBlank(film.getFilmImage())){
                    // 图片地址不为空
                    String newImage = "";
                    if(film.getFilmName().contains("/")){
                        newImage = downloadPic(film.getFilmImage(),film.getFilmName().split("/")[0]);
                    }else{
                        newImage = downloadPic(film.getFilmImage(),film.getFilmName());
                    }
                    //System.out.println(newImage);
                    film.setFilmImage(newImage);
                    FilmDataUtils.updateFilmImage(film);
                    count ++;
                    System.out.println("第[" + count + "]条数据更新image成功 -> id:[" + film.getId() + "],name:[" + film.getFilmName() + "]");
                }

            } catch (Exception e) {
                e.printStackTrace();
                FileUtils.writeUpdateImageFailedFile(film);
            }
        }
    }

    /**
     * 下载图片
     * @auth sunpeikai
     * @param
     * @return
     */
    private static String downloadPic(String url,String name){
        String path = "http://117.48.197.114/film/";
        name = getNowStryyyyMMddHHmmss() + name + ".jpg";
        try {
            byte[] resultByte = HttpUtils.doGet(url);
            File file = new File("C:\\Users\\dell\\Desktop\\pic\\" + name);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(resultByte);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path + name;
    }

    /**
     * 获取当前时间的String类型
     * @auth sunpeikai
     * @param
     * @return
     */
    private static String getNowStryyyyMMddHHmmss(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * url编码
     * @auth sunpeikai
     * @param
     * @return
     */
    private static String URLEncode(String str,String code){
        try {
            return URLEncoder.encode(str,code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String imgUrl2Jpg(String fileName){
        return fileName.substring(0,fileName.lastIndexOf(".")) + ".jpg";
    }
}
