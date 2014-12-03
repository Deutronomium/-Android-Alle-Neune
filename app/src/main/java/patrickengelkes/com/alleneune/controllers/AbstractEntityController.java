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
    public static final String host = "http://192.168.56.1:3000";
    private AbstractEntity abstractEntity;
    private JSONObject createAbstractAnswer;
    private JSONObject validateAbstractAnswer;

    public AbstractEntityController(AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
        this.abstractEntity.prepareEntity();
    }

    public boolean createAbstractEntity() {
        try {
            HttpResponse response = new CreateAbstractEntityTask().execute(this.abstractEntity).get();
            if (response != null) {
                createAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                } else {
                    Log.e(TAG, "Login failed");
                    Log.e(TAG, createAbstractAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkForValidity() {
        try {
            HttpResponse response = new AbstractValidityTask().execute(this.abstractEntity).get();
            if (response != null) {
                validateAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Invalid entity");
                    Log.e(TAG, validateAbstractAnswer.toString());
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

    public JSONObject getValidateAbstractAnswer() { return this.validateAbstractAnswer; }


    private class CreateAbstractEntityTask extends AsyncTask<AbstractEntity, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(AbstractEntity... abstractEntities) {
            AbstractEntity abstractEntity = abstractEntities[0];
            try {
                String json = abstractEntity.getJsonString();
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/" +
                        abstractEntity.getObjectString() + "s", stringEntity);
                return new DefaultHttpClient().execute(myHttpPost);
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

    private class AbstractValidityTask extends AsyncTask<AbstractEntity, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(AbstractEntity... abstractEntities) {
            AbstractEntity abstractEntity = abstractEntities[0];
            try {
                String json = abstractEntity.getJsonString();
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/" +
                        abstractEntity.getObjectString() + "s/validity", stringEntity);
                return new DefaultHttpClient().execute(myHttpPost);
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
