package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Fine;
import patrickengelkes.com.alleneune.entities.objects.FinePayment;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 26/02/15.
 */
public class FinePaymentController {
    public static String TAG = FinePaymentController.class.getSimpleName();

    @Inject
    public FinePaymentController() {
    }

    public ApiCall create(int userID, int eventID, int fineID) {
        try {
            HttpPostEntity createFinePaymentEntity = createFinePostEntity(userID, eventID, fineID);
            HttpResponse response = new ApiCallTask().execute(createFinePaymentEntity).get();
            JSONObject createAnswer = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 201 && createAnswer != null) {
                return ApiCall.CREATED;
            } else if (response.getStatusLine().getStatusCode() == 422 && createAnswer != null) {
                Log.e(TAG, createAnswer.toString());
                return ApiCall.UNPROCESSABLE_ENTITY;
            } else {
                return ApiCall.BAD_REQUEST;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ApiCall.BAD_REQUEST;
    }

    public HttpPostEntity createFinePostEntity(int userID, int eventID, int fineID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(FinePayment.USER_ID, userID);
        leaf.put(FinePayment.EVENT_ID, eventID);
        leaf.put(FinePayment.FINE_ID, fineID);
        JSONObject root = new JSONObject();
        root.put(FinePayment.ROOT, leaf);

        return new HttpPostEntity(FinePayment.GENERIC_URL, root.toString());
    }

    public List<Fine> getByUserAndEvent(int userID, int eventID) {
        List<Fine> returnList = new ArrayList<Fine>();
        try {
            HttpPostEntity getUserAndEventPostEntity = getByUserAndEventPostEntity(userID, eventID);
            HttpResponse response = new ApiCallTask().execute(getUserAndEventPostEntity).get();
            JSONObject finePaymentJSON = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 200 && finePaymentJSON != null) {
                returnList = FineController.getFineListFromJson(finePaymentJSON);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    private HttpPostEntity getByUserAndEventPostEntity(int userID, int eventID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(FinePayment.USER_ID, userID);
        leaf.put(FinePayment.EVENT_ID, eventID);
        JSONObject root = new JSONObject();
        root.put(FinePayment.ROOT, leaf);

        return new HttpPostEntity(FinePayment.GET_BY_USER_AND_EVENT, root.toString());
    }

}
