package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public interface AbstractEntity {

    public HttpPostEntity create() throws JSONException, UnsupportedEncodingException;

    public HttpPostEntity checkValidity() throws JSONException, UnsupportedEncodingException;
}
