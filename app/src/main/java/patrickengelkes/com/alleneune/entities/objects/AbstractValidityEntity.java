package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 16/01/15.
 */
public interface AbstractValidityEntity extends AbstractEntity {

    public HttpPostEntity checkValidity() throws JSONException, UnsupportedEncodingException;
}
