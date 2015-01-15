package patrickengelkes.com.alleneune.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.JsonBuilder;
import patrickengelkes.com.alleneune.Objects.Event;

/**
 * Created by patrickengelkes on 14/01/15.
 */
public class EventController {
    public static final String TAG = EventController.class.getSimpleName();

    private Event event;
    private JSONObject createAnswer;


    public EventController(Event event) {
        this.event = event;
    }

    public boolean createEvent() {
        try {
            String createEventString = this.event.createEventJsonObject().toString();
            if (createEventString != null) {
                HttpResponse response = new CreateEventTask().
                        execute(createEventString).get();
                if (response != null) {
                    createAnswer = new JsonBuilder().execute(response).get();
                    if (response.getStatusLine().getStatusCode() == 201) {
                        return true;
                    } else {
                        Log.e(TAG, "Could not create entity");
                        Log.e(TAG, createAnswer.toString());
                    }
                }
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    private class CreateEventTask extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/events",
                        stringEntity);
                return new DefaultHttpClient().execute(myHttpPost);
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
