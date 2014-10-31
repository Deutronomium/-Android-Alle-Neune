package patrickengelkes.com.alleneune;

import android.os.AsyncTask;

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

/**
 * Created by patrickengelkes on 29/10/14.
 */
public class UserController {
    public static final String host = "http://192.168.56.1:3000";

    private UserParams userParams;

    private JSONObject createUserAnswer;

    public UserController(UserParams userParams) {
        this.userParams = userParams;
    }

    public boolean createUser() {
        try {
            HttpResponse response = new CreateUserTask().execute(this.userParams).get();
            if (response != null) {
                createUserAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public JSONObject getCreateUserAnswer() {
        return this.createUserAnswer;
    }

    private class CreateUserTask extends AsyncTask<UserParams, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(UserParams... userParamses) {
            UserParams userParams = userParamses[0];
            try {
                JSONObject user = new JSONObject();
                String json = userParams.getJsonString();
                StringEntity stringEntity = new StringEntity(json);

                HttpPost httpPost = new HttpPost(UserController.host + "/users");
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
