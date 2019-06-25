/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.utils;

import com.alibaba.fastjson.JSON;
import com.sandman.filmCrawler.Film;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author sunpeikai
 * @version FileUtils, v0.1 2019/1/29 19:46
 */
public class FileUtils {
    public static void writeRootErrorFile(String error){
        try{
            File file = new File("C:\\Users\\dell\\Desktop\\filmCrawler\\rootError.txt");
            OutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((error+"\n").getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeUrlErrorFile(String error){
        try{
            File file = new File("C:\\Users\\dell\\Desktop\\filmCrawler\\urlError.txt");
            OutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((error+"\n\n\n\n\n\n").getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void write404ErrorFile(String url){
        try{
            File file = new File("C:\\Users\\dell\\Desktop\\filmCrawler\\404.txt");
            OutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((url+"\n").getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeUrlFile(String url){
        try{
            File file = new File("C:\\Users\\dell\\Desktop\\filmCrawler\\url.txt");
            OutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((url+"\n").getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeInfoFile(String info){
        try{
            File file = new File("C:\\Users\\dell\\Desktop\\filmCrawler\\info.txt");
            OutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((info+"\n").getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeUpdateImageFailedFile(Film film){
        try{
            File file = new File("C:\\Users\\dell\\Desktop\\filmCrawler\\updateImageFailed.txt");
            OutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((film.getInfoString() +"\n").getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
