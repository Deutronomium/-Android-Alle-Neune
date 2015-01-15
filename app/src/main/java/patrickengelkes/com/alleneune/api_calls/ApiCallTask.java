package patrickengelkes.com.alleneune.api_calls;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by patrickengelkes on 15/01/15.
 */
public class ApiCallTask extends AsyncTask<HttpPostEntity, Integer, HttpResponse> {

    @Override
    protected HttpResponse doInBackground(HttpPostEntity... httpPostEntities) {
        HttpPostEntity postEntity = httpPostEntities[0];

        StringEntity stringEntity = postEntity.getStringEntity();
        String url = postEntity.getUrl();

        MyHttpPost myHttpPost = new MyHttpPost(url, stringEntity);
        try {
            return new DefaultHttpClient().execute(myHttpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
