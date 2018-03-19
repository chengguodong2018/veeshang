package com.vee.shang.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


/**
 * @author cgd
 * @date 2017/12/08.
 */
public class UploadUtil {

    private static final String ACCESSKEY = "pkmTPnqngwRaPbFfghb07Y9RWi5VlleE6LPBKQqr";
    private static final String SECRETKEY = "uZH-8CQn4XnK7SriXt0LN3OxMHL2dp5ivoC81Kkq";
    private static final String BUCKET = "veeshang";

    /**
     * 上传图片
     * @param filePath
     * @param upToken
     * @return
     */
    public static String upload(String filePath,String upToken) {
        String imgKey = "";
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            imgKey = putRet.hash;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return imgKey;
    }

    /**
     * 获取七牛上传凭证
     * @return
     */
    public static String getUploadToken() {
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        String upToken = auth.uploadToken(BUCKET, null, 25*3600L, null, true);
        return  upToken;
    }

}
