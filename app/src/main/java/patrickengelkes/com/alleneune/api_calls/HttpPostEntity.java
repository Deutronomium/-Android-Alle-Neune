package patrickengelkes.com.alleneune.api_calls;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrickengelkes on 15/01/15.
 */
public class HttpPostEntity {
    public static final String host = "http://192.168.56.1:3000";

    private String url;
    private StringEntity stringEntity;

    public HttpPostEntity(String url, String json) throws UnsupportedEncodingException {
        this.url = host + url;
        this.stringEntity = new StringEntity(json);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = host + url;
    }

    public StringEntity getStringEntity() {
        return this.stringEntity;
    }

    public void setStringEntity(String json) throws UnsupportedEncodingException {
        this.stringEntity = new StringEntity(json);
    }
}
