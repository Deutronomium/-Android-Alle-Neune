package patrickengelkes.com.alleneune.api_calls;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by patrickengelkes on 30/10/14.
 */
public class JsonBuilder extends AsyncTask<HttpResponse, Integer, JSONObject> {

    @Override
    protected JSONObject doInBackground(HttpResponse... httpResponses) {
        try {
            HttpResponse response = httpResponses[0];
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            String json = builder.toString();
            return new JSONObject(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
