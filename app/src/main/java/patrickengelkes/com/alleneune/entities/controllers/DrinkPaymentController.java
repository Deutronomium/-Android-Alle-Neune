package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import patrickengelkes.com.alleneune.entities.objects.DrinkPayment;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 25/02/15.
 */
public class DrinkPaymentController {
    public static String TAG = DrinkPaymentController.class.getSimpleName();

    @Inject
    public DrinkPaymentController() {
    }

    public ApiCall create(int userID, int eventID, int drinkID) {
        try {
            HttpPostEntity createDrinkPaymentEntity = createDrinkPostEntity(userID, eventID, drinkID);
            HttpResponse response = new ApiCallTask().execute(createDrinkPaymentEntity).get();
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

    private HttpPostEntity createDrinkPostEntity(int userID, int eventID, int drinkID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(DrinkPayment.USER_ID, userID);
        leaf.put(DrinkPayment.EVENT_ID, eventID);
        leaf.put(DrinkPayment.DRINK_ID, drinkID);
        JSONObject root = new JSONObject();
        root.put(DrinkPayment.ROOT, leaf);


        return new HttpPostEntity(DrinkPayment.GENERIC_URL, root.toString());
    }

    public List<Drink> getDrinksByUserAndEvent(int userID, int eventID) {
        List<Drink> returnList = new ArrayList<Drink>();
        try {
            HttpPostEntity getDrinksByUserAndEventPostEntity = getDrinksByUserAndEventPostEntity(userID, eventID);
            HttpResponse response = new ApiCallTask().execute(getDrinksByUserAndEventPostEntity).get();
            JSONObject drinkPaymentJSON = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 200 && drinkPaymentJSON != null) {
                returnList = DrinkController.getDrinkListFromJson(drinkPaymentJSON);
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

    private HttpPostEntity getDrinksByUserAndEventPostEntity(int userID, int eventID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(DrinkPayment.USER_ID, userID);
        leaf.put(DrinkPayment.EVENT_ID, eventID);
        JSONObject root = new JSONObject();
        root.put(DrinkPayment.ROOT, leaf);

        return new HttpPostEntity(DrinkPayment.GET_BY_USER_AND_EVENT, root.toString());
    }
}
