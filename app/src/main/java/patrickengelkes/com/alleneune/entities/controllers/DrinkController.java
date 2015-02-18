package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 13/02/15.
 */
public class DrinkController {
    public static final String TAG = DrinkController.class.getSimpleName();

    private String genericUrl = "/drinks";

    @Inject
    public DrinkController() {
    }

    private String drinkJSON(String name, double price, int clubID) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(Drink.NAME, name);
        leaf.put(Drink.PRICE, price);
        leaf.put("club_id", clubID);
        JSONObject root = new JSONObject();
        root.put(Drink.ROOT, leaf);

        return root.toString();
    }

    public ApiCall create(String name, double price, int clubID) {
        try {
            HttpResponse response = new ApiCallTask().execute(createDrinkPostEntity(name, price, clubID)).get();
            JSONObject createAnswer = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 201 && createAnswer != null) {
                return ApiCall.CREATED;
            } else if (response.getStatusLine().getStatusCode() == 422 && createAnswer != null) {
                Log.e(TAG, "Creating Drink Failed");
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

    private HttpPostEntity createDrinkPostEntity(String name, double price, int clubID) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl, drinkJSON(name, price, clubID));
    }

    public List<Drink> getDrinksByClub(int clubID) {
        List<Drink> returnList = new ArrayList<Drink>();

        try {
            HttpResponse response = new ApiCallTask().execute(getDrinksByClubPostEntity(clubID)).get();
            JSONObject drinksResponse = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 200 && drinksResponse != null) {
                returnList = getDrinkListFromJson(drinksResponse);
            }
            Log.e(TAG, "Test");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    private HttpPostEntity getDrinksByClubPostEntity(int clubID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(Club.ID, clubID);
        JSONObject root = new JSONObject();
        root.put(Drink.ROOT, leaf);

        String url = genericUrl + Drink.GET_BY_CLUB;

        return new HttpPostEntity(url, root.toString());
    }

    private List<Drink> getDrinkListFromJson(JSONObject drinksJson) throws JSONException {
        List<Drink> drinkList = new ArrayList<Drink>();

        JSONArray drinks = drinksJson.getJSONArray("drinks");
        for (int i = 0; i < drinks.length(); i++) {
            JSONObject drinkJson = drinks.getJSONObject(i);
            String name = drinkJson.getString("name");
            double price = drinkJson.getDouble("price");

            Drink drink = new Drink(name, price);
            drinkList.add(drink);
        }

        return drinkList;
    }
}
