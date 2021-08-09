package com.feng.officeservice.utils;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.PDFOptions;

import java.util.ArrayList;

/**
 * @author feng
 */
public class HtmlToPdfUrl {

    public void htmlToPdf(String url, String file) {
        Browser browser = null;
        Page page = null;
        try {
            //此方法用来判断是否有chrome浏览器，没有就会下载一个
            //BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> arrayList = new ArrayList<>();
            //生成pdf必须在无厘头模式下才能生效
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(true).build();
            arrayList.add("--no-sandbox");
            arrayList.add("--disable-setuid-sandbox");
            browser = Puppeteer.launch(options);
            page = browser.newPage();
            page.goTo(url);

            //休眠3秒，用来渲染
            Thread.sleep(3000);
            PDFOptions pdfOptions = new PDFOptions();
            pdfOptions.setPath(file);
            pdfOptions.setFormat("A4");
            page.pdf(pdfOptions);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != page) {
                try {
                    page.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (null != browser) {
                browser.close();
            }
        }
    }
}
