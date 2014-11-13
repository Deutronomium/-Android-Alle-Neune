package patrickengelkes.com.alleneune.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.JsonBuilder;
import patrickengelkes.com.alleneune.Objects.AbstractEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class AbstractEntityController {
    public static final String TAG = AbstractEntityController.class.getSimpleName();
    private static final String host = "http://192.168.56.1:3000";
    private AbstractEntity abstractEntity;
    private JSONObject createAbstractAnswer;

    public AbstractEntityController(AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
    }

    public boolean createAbstractEntity() {
        try {
            HttpResponse response = new CreateAbstractEntityTask().execute(this.abstractEntity).get();
            if (response != null) {
                createAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                } else {
                    Log.e(TAG, response.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public JSONObject getCreateAbstractAnswer() {
        return this.createAbstractAnswer;
    }


    private class CreateAbstractEntityTask extends AsyncTask<AbstractEntity, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(AbstractEntity... abstractEntities) {
            AbstractEntity abstractEntity = abstractEntities[0];
            try {
                JSONObject abstractObject = new JSONObject();
                String json = abstractEntity.getJsonString();
                StringEntity stringEntity = new StringEntity(json);

                HttpPost httpPost = new HttpPost(AbstractEntityController.host + "/" +
                    abstractEntity.getObjectString() + "s");
                httpPost.setEntity(stringEntity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-Type", "application/json");
                return new DefaultHttpClient().execute(httpPost);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
