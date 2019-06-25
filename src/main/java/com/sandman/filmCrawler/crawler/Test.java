/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.crawler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sunpeikai
 * @version Test, v0.1 2019/2/15 17:10
 */
public class Test {
    public static void main(String[] args)throws Exception{
        BufferedInputStream inputStream = FileUtil.getInputStream("C:\\Users\\dell\\Desktop\\mongo.txt");
        BufferedOutputStream outputStream = FileUtil.getOutputStream("C:\\Users\\dell\\Desktop\\id.txt");
        BufferedReader bufferedReader = IoUtil.getReader(inputStream,"UTF-8");
        OutputStreamWriter outputStreamWriter = IoUtil.getWriter(outputStream,"UTF-8");
        Set<String> stringSet = new HashSet<>();
        String result = null;
        while ((result = bufferedReader.readLine())!=null){
            result = result.replaceAll("\\s","");
            if(result.contains("_id")){
                outputStreamWriter.write( result + "\n");
                stringSet.add(result);
                System.out.println(result);
            }
        }
        outputStreamWriter.flush();
        outputStreamWriter.close();
        System.out.println(stringSet.size());
    }
}
