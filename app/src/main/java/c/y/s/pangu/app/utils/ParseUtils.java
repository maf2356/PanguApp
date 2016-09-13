package c.y.s.pangu.app.utils;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import c.y.s.pangu.app.Constant;
import c.y.s.pangu.app.api.entry.RecommendedEntity;
import c.y.s.pangu.app.api.entry.VideoItem;

/**
 * Created by Administrator on 2016/9/11.
 */
public class ParseUtils {

    public static RecommendedEntity parseRecommended(String s){
        RecommendedEntity bean = new RecommendedEntity();
        Document document = Jsoup.parse(s);
        Elements elementsByClass = document.getElementsByClass("p-thumb");
        Element element = elementsByClass.first();
        String text = "";
        for (Node e: element.childNodes() ) {
            if(TextUtils.isEmpty(bean.img)){
                if(!TextUtils.isEmpty(e.attr("alt"))){
                    bean.img = e.attr("alt");
                }
            }
            if(TextUtils.isEmpty(bean.title)){
                if(!TextUtils.isEmpty(e.attr("title"))){
                    bean.title = e.attr("title");
                }
            }


        };
        return bean;
    }


    public  static List<RecommendedEntity> parseYoukuRecommended(){
        Document doc;
        List<RecommendedEntity> listRecommended =  new ArrayList<>();
        try {
            doc = Jsoup.connect(Constant.YOUKU_URL).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0").get();
            Elements links = doc.select("div.p-thumb");
            int s = 0;
            for (Element link : links) {
                RecommendedEntity recommended = new RecommendedEntity();
                Element parent = link.parent().parent();
                String select = parent.attributes().get("class");
                if(!select.contains("yk-col8")){
                    continue;
                }
                Elements v_href_element = link.select("a[href]");
                Element v_herf_element_item = v_href_element.first();
                String v_url = v_herf_element_item.attr("abs:href");
                if(!v_url.startsWith("http://v.youku.com/v_show/")){
                    continue;
                }
                String v_title = v_herf_element_item.attr("title");
                //img
                Elements v_src_element = link.select("img[alt]");
                Element v_src_element_item = v_src_element.first();
                String v_src_url = v_src_element_item.attr("abs:alt");
                recommended.title = v_title;
                recommended.img = v_src_url;
                /**
                 * get videoId
                 */
                Document document = Jsoup.connect(v_url).get();
                Elements elements = document.getElementsByTag("script");
                for (Element element : elements) {
                    boolean isFind = false;
                    String[] data = element.data().toString().split("var");
                    for(String variable : data){
                        if(variable.contains("=")){
                            if(variable.contains("videoId")){
                                String[]  kvp = variable.split("=");
                                System.out.println(kvp[1]);
                                isFind = true;
                                break;
                            }
                        }
                    }
                    if(isFind){
                        break;
                    }
                }
                /**
                 *get publisherName
                 */
                String name = document.select("div.user-name").attr("subname");
                if(TextUtils.isEmpty(name.trim())){
                    recommended.publisherName = name;
                }
                /**
                 * get desc
                 */
                String v_title_url = document.select("h1.title").first().select("a[href]").attr("href");
                System.out.println(v_title_url);
                Document documentDesc = Jsoup.connect(v_title_url).get();
                Elements descElements = documentDesc.select("span.short").select("span");
                String v_desc_text ="";
                if(descElements.size()>2){
                    v_desc_text = descElements.get(1).text();
                }else if(descElements.size()==1){
                    v_desc_text = descElements.get(0).text();
                }
                recommended.profiles = v_desc_text.replace("　","");
                //get all item
                String v_all_item_url = v_title_url.replace("http://www.youku.com/show_page/", "http://www.youku.com/show_point_");
                v_all_item_url+="?dt=json";
                Document docAllItem = Jsoup.connect(v_all_item_url).get();
                Elements allItemElements = docAllItem.select("div.item");
                recommended.videoItems = new ArrayList<>();
                for (Element itemElement : allItemElements) {
                    VideoItem item = new VideoItem();
                    String itemTitle = itemElement.select("div.title").select("a[title]").text();
                    String itemLink = itemElement.select("div.link").select("a[href]").text();
                    String itemThumb = itemElement.select("div.thumb").select("img[src]").attr("src");
                    String itemTime = itemElement.select("div.time").select("span.num").text();
                    String itemStat = itemElement.select("div.stat").select("span.num").text();
                    String itemDesc = itemElement.select("div.desc").text();
                    item.itemDesc = itemDesc;
                    item.itemLink = itemLink;
                    item.itemThumb = itemThumb;
                    item.itemTime = itemTime;
                    item.itemStat = itemStat;
                    item.itemDesc = itemDesc;
                    item.itemTitle = itemTitle;
                    recommended.videoItems.add(item);
                }

                listRecommended.add(recommended);
            }


            System.out.println("total:" + s);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listRecommended;
    }
}
