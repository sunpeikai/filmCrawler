/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunpeikai
 * @version DateUtils, v0.1 2019/1/29 11:33
 */
public class DateUtils {

    private static final String format = "yyyy-MM-dd HH:mm:ss";

    /**
     * 页面上获取的时间与数据库最近时间对比,判断是否将其收录
     * @auth sunpeikai
     * @param
     * @return
     */
    public static boolean before(Date page,Date datasource){
        if(page != null && datasource != null){
            return page.getTime()>datasource.getTime();
        }else if(datasource == null){
            return true;
        }
        return false;
    }

    public static Date formatDate(String date){
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
