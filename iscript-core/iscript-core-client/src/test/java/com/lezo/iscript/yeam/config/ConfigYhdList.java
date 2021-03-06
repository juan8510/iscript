package com.lezo.iscript.yeam.config;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lezo.iscript.proxy.ProxyClientUtils;
import com.lezo.iscript.rest.http.HttpClientManager;
import com.lezo.iscript.rest.http.HttpClientUtils;
import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.ClientConstant;
import com.lezo.iscript.yeam.service.ConfigParser;
import com.lezo.iscript.yeam.service.DataBean;
import com.lezo.iscript.yeam.writable.TaskWritable;

public class ConfigYhdList implements ConfigParser {
    private DefaultHttpClient client = HttpClientManager.getDefaultHttpClient();

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String doParse(TaskWritable task) throws Exception {
        DataBean dataBean = getDataObject(task);
        return convert2TaskCallBack(dataBean, task);
    }

    private String convert2TaskCallBack(DataBean dataBean, TaskWritable task) throws Exception {
        JSONObject returnObject = new JSONObject();
        if (dataBean != null) {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, dataBean);
            String dataString = writer.toString();

            JSONUtils.put(returnObject, ClientConstant.KEY_CALLBACK_RESULT, dataString);
            JSONUtils.put(returnObject, ClientConstant.KEY_STORAGE_RESULT, dataString);
        }
        return returnObject.toString();
    }

    /**
     * {"dataList":[],"nextList":[]}
     * 
     * @param task
     * @return
     * @throws Exception
     */
    private DataBean getDataObject(TaskWritable task) throws Exception {
        String url = task.get("url").toString();
        url = turnUrl(url);
        HttpGet get = ProxyClientUtils.createHttpGet(url, task);
        String html = HttpClientUtils.getContent(client, get);
        html = turnHtml(html);
        Document dom = Jsoup.parse(html, url);
        Elements urlEls = dom.select("#plist a[href~=item.jd.com/[0-9]{5,}.html$]");
        DataBean dataBean = new DataBean();
        addListArray(dom, dataBean);
        addNextUrls(dom, dataBean);
        List<Object> dataList = dataBean.getDataList();
        Set<String> hasSet = new HashSet<String>();
        for (Element urlEle : urlEls) {
            String sUrl = urlEle.absUrl("href");
            if (!hasSet.contains(sUrl)) {
                dataList.add(sUrl);
                hasSet.add(sUrl);
            }
        }
        return dataBean;
    }

    // @Override
    // public String doParse(TaskWritable task) throws Exception {
    // String url = task.get("url").toString();
    // url = turnUrl(url);
    // System.err.println(url);
    // HttpGet get = new HttpGet(url);
    // String html = HttpClientUtils.getContent(client, get, "UTF-8");
    // html = turnHtml(html);
    // Document dom = Jsoup.parse(html, url);
    //
    // JSONObject listObject = new JSONObject();
    // JSONArray listArray = new JSONArray();
    // JSONUtils.put(listObject, "list", listArray);
    //
    // addListArray(dom, listArray);
    // addNextUrls(dom, listObject);
    // return listObject.toString();
    // }

    private String turnHtml(String html) {
        if (html.startsWith("jsonp")) {
            int beginIndex = html.indexOf("(");
            int endIndex = html.lastIndexOf(")");
            html = html.substring(beginIndex + 1, endIndex);
        }
        JSONObject sObject = JSONUtils.getJSONObject(html);
        if (sObject == null) {
            return html;
        }
        return JSONUtils.getString(sObject, "value");
    }

    private String turnUrl(String url) {
        if (url.endsWith("|")) {
            url = url.substring(0, url.length() - 1);
        }
        url = url.replace("|/?", "?");
        if (url.indexOf("www.yhd.com/ctg/searchPage") > 0) {
            return url;
        }
        Pattern qReg = Pattern.compile("c[0-9]+-.*(k.*?/)?");
        Matcher matcher = qReg.matcher(url);
        if (matcher.find()) {
            String query = matcher.group();
            url = String.format("http://www.yhd.com/ctg/searchPage/%s", query);
        } else {
            qReg = Pattern.compile("vc[0-9]+.*");
            matcher = qReg.matcher(url);
            if (matcher.find()) {
                String query = matcher.group();
                url = String.format("http://www.yhd.com/ctg/searchVirCateAjax/%s", query);
                url = url.replace("/c0/", "/");
                url = url.replace("/b/", "/c0/b/");
            }
        }
        Pattern sReg = Pattern.compile("a-([a-zA-Z0-9]+-)+[a-zA-Z0-9]+");
        matcher = sReg.matcher(url);
        if (matcher.find()) {
            Pattern nReg = Pattern.compile("-p[0-9]+-");
            Matcher nMatcher = nReg.matcher(url);
            String sort = "a-s2-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k";
            if (nMatcher.find()) {
                sort = sort.replace("-p1-", nMatcher.group());
            }
            url = matcher.replaceFirst(sort);
        } else {
            url = url.endsWith("/") ? url : url + "/";
            if (url.indexOf("/k") > 0) {
                url = url.replace("/k", "/b/a-s2-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k");
            } else {
                url += "b/a-s2-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k";
            }
        }
        if (!url.contains("?callback=jsonp")) {
            url += "?callback=jsonp" + System.currentTimeMillis();
        }
        if (url.indexOf("searchVirCateAjax") > 0) {
            url = url.replace("-price-d0-f0-m1-rt0-pid-mid0-k", "-price-d0-mid0-f0");
        }
        url = url.replace("|/?", "?");
        return url;
    }

    private void addNextUrls(Document dom, DataBean dataBean) {
        String url = dom.baseUri();
        Elements curPageAs = dom.select("#turnPageBottom.turn_page span.page_cur");
        if (curPageAs.isEmpty()) {
            return;
        }
        String sCurPage = curPageAs.first().ownText();
        Integer iCurPage = Integer.valueOf(sCurPage);
        if (iCurPage != 1) {
            return;
        }
        Elements pageCoutAs = dom.select("#pageCountPage[value]");
        if (!pageCoutAs.isEmpty()) {
            int count = Integer.valueOf(pageCoutAs.first().attr("value"));
            if (count < 2) {
                return;
            }
            Elements nextEls = dom.select("a[rel=nofollow].page_next[href]");
            if (!nextEls.isEmpty()) {
                String sUrl = nextEls.first().absUrl("href");
                dataBean.getNextList().add(sUrl);
                for (int i = 2; i <= count; i++) {
                    String sNext = sUrl.replace("-p2-", "-p" + i + "-");
                    sNext = sNext.replaceAll("callback=jsonp[0-9]+", "callback=jsonp" + System.currentTimeMillis());
                    dataBean.getNextList().add(sNext);
                }
            } else if (url.indexOf("-p1-") > 0) {
                int index = url.indexOf("?");
                index = index < 0 ? url.length() : index;
                String listHeader = url.substring(0, index);
                for (int i = 2; i <= count; i++) {
                    String sNext = listHeader.replace("-p1-", "-p" + i + "-");
                    sNext += "?callback=jsonp" + System.currentTimeMillis();
                    dataBean.getNextList().add(sNext);
                }
            } else {
                System.err.println("Offer next page,but url:" + url);
            }
        }
    }

    private void addListArray(Document dom, DataBean dataBean) throws Exception {
        Elements ctElements = dom.select("li[id^=producteg_]");
        if (ctElements.isEmpty()) {
            return;
        }
        int size = ctElements.size();
        for (int i = 0; i < size; i++) {
            Elements oNameUrlAs = ctElements.get(i).select("a[id^=pdlink][title][href][pmid]");
            if (!oNameUrlAs.isEmpty()) {
                dataBean.getDataList().add(oNameUrlAs.first().absUrl("href"));
            }
        }
    }
}
