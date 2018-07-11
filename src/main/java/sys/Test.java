package sys;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test {

    public static void main(String[] args) {
        String rep="http://www.yoyound.com/includes/ueditor/php/../../..";
   String str="<p><br/></p><p><img src=\"http://www.yoyound.com/includes/ueditor/php/../../../bdimages/upload1/20171103/1509693702130687.jpg\" style=\"float:none;\" title=\"01.jpg\"/></p><p><img src=\"http://www.yoyound.com/includes/ueditor/php/../../../bdimages/upload1/20171103/1509693702488152.jpg\" style=\"float:none;\" title=\"01.jpg\"/></p><p><img src=\"http://www.yoyound.com/includes/ueditor/php/../../../bdimages/upload1/20171103/1509693702124736.jpg\" style=\"float:none;\" title=\"02.jpg\"/></p><p><br/></p>";
        Document containerDoc = Jsoup.parse(str);
        Elements e=containerDoc.select("img");
       for(Element ee:e){
           System.out.println(ee.attr("src").replaceAll(rep,""));

       }
   /*
        RedisPlugin redis = new RedisPlugin("redis", "localhost",7777,"hkzh713p@ssw0rd2");
// 与web下唯一区别是需要这里调用一次start()方法
        redis.start();*/

       /* for (int i = 0; i < 1000; i++) {
             //Jedis j = Redis.use().getJedis();
            //log.info(i);
            //RedisTool.set("key", i+"");
           // log.info(RedisTool.get("key").toString());
        }*/


    }
}
