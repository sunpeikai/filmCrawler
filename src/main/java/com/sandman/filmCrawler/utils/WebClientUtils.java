/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author sunpeikai
 * @version HttpUnitUtils, v0.1 2018/12/19 16:55
 */
public class WebClientUtils {
    /**
     * 获取webclint
     * @auth sunpeikai
     * @param
     * @return
     */
    public static WebClient getWebClient(){
        WebClient webClient=new WebClient(BrowserVersion.BEST_SUPPORTED); // 实例化Web客户端
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        //webClient.waitForBackgroundJavaScript(5 * 1000);//异步JS执行需要耗时,所以这里线程要阻塞5秒,等待异步JS执行结束
        return webClient;
    }
}
