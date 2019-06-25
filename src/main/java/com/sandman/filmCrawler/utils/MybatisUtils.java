/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author sunpeikai
 * @version MybatisUtils, v0.1 2019/1/29 9:32
 */
public class MybatisUtils {
    /**
     * 启动MyBatis去加载配置文件
     * 1.java.io.Reader
     * 2.Resources
     * 3.SqlSessionFactory
     */
    private static Reader reader;
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            reader = Resources.getResourceAsReader("conf.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1.初始化SqlSession, 执行SQL语句
     * 2.告知程序读哪个映射文件
     * 3.告知程序执行映射文件中哪个CRUD操作
     */
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
