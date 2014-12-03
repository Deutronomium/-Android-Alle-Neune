package patrickengelkes.com.alleneune.controllers;

import org.apache.http.entity.StringEntity;

/**
 * Created by patrickengelkes on 01/12/14.
 */
public class MyHttpPost extends org.apache.http.client.methods.HttpPost {

    public MyHttpPost(String url, StringEntity entity) {
        super(url);
        this.setHeader("Accept", "application/json");
        this.setHeader("Content-Type", "application/json");
        this.setEntity(entity);
    }
}
