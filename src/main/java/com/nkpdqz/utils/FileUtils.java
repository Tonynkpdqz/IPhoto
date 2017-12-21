package com.nkpdqz.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final String ACCESS_KEY = "5f2Tji54gTVr-o6fpniXfR3zz60TIYPG2Veywmip";
    private static final String SECRET_KEY = "iG_3hOOnuxZPTnVWJ7pPT2VYCVvcmMvafmw1Oyd6";
    private static final String BUCKET_NAME = "iphoto";

    private static Configuration cfg = new Configuration(Zone.zone0());
    private static UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 上传图片到七牛云存储
     * @param reader
     * @param fileName
     * 由于不同地区的对应网址不同，可能会导致图片加载不出，请自行修改Config.UP_HOST
     */
    public static void upload(InputStream reader,String fileName){
        String uptoken;
        Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
        uptoken = auth.uploadToken(BUCKET_NAME);
        try {
            Response response = uploadManager.put(reader, fileName, uptoken, null, null);
            reader.close();
            System.out.println(response.bodyString());
        } catch (QiniuException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String key){
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,cfg);
        try {
            bucketManager.delete(BUCKET_NAME,key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

}
