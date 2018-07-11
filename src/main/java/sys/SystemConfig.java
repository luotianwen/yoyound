package sys;


import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log4jLog;
import com.jfinal.log.Log4jLogFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.upload.UploadFile;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * @author martins
 */
public class SystemConfig extends JFinalConfig {
    Logger log=Logger.getLogger(SystemConfig.class);
    public void getFilePath(String coverImgSrc){
        File tmpFile = new File(PropKit.get("filetopath") +coverImgSrc);
        if(!tmpFile.getParentFile().exists()){
            tmpFile.getParentFile().mkdirs();
        }

        File file = new File(PropKit.get("filepath") +coverImgSrc);
        try {
            if (file.exists()) {
                //FileUtils.copyFile(file, tmpFile);
                //file.renameTo(tmpFile);
                FileChannel inputChannel = null;
                FileChannel outputChannel = null;
                try {
                    inputChannel = new FileInputStream(file).getChannel();
                    outputChannel = new FileOutputStream(tmpFile).getChannel();
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                } finally {
                    inputChannel.close();
                    outputChannel.close();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void afterJFinalStart() {

        String rep="http://www.yoyound.com/includes/ueditor/php/../../..";
        List<Record> list= Db.find("select goods_id, goods_desc ,goods_thumb ,original_img  from ecs_goods where brand_id=7");
        /*for (Record r:list
             ) {
            getFilePath("/"+r.getStr("goods_thumb"));
            System.out.println(r.getStr("goods_id")+" "+ 0+ " /"+r.getStr("goods_thumb"));
            //log.info(r.getStr("goods_id")+" "+ 0+ " "+r.getStr("goods_thumb"));
        }
        for (Record r:list
                ) {
             getFilePath("/"+r.getStr("goods_thumb"));
           System.out.println(r.getStr("goods_id")+" "+ 1+ " "+r.getStr("original_img"));
        }
        for (Record r:list
                ) {
             getFilePath("/"+r.getStr("goods_thumb"));
           System.out.println(r.getStr("goods_id")+" "+ 2+ " "+r.getStr("original_img"));
        }*/
        for (Record r:list
                ) {

            Document containerDoc = Jsoup.parse(r.getStr("goods_desc"));
            Elements e=containerDoc.select("img");
            for(Element ee:e){
                 //getFilePath( ee.attr("src").replaceAll(rep,""));
               System.out.println(r.getStr("goods_id")+"            "+ee.attr("src").replaceAll(rep,""));
            }

        }

        super.afterJFinalStart();
    }

    @Override
    public void configConstant(Constants me) {

        PropKit.use("a_little_config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));

        //PathKit.
    }
    @Override
    public void configRoute(Routes me) {


    }
    @Override
    public void configEngine(Engine me) {}
    @Override
    public void configPlugin(Plugins me) {
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password").trim());
        me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        me.add(arp);

    }
    @Override
    public void configInterceptor(Interceptors me) {

    }
    @Override
    public void configHandler(Handlers me) {

    }
    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }
}