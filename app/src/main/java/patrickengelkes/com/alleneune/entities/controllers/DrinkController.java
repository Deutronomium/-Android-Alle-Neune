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


    @Inject
    public DrinkController() {
    }

    private String drinkJSON(Drink drink) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(Drink.NAME, drink.getName());
        leaf.put(Drink.PRICE, drink.getPrice());
        leaf.put(Drink.CLUB_ID, drink.getClubID());
        JSONObject root = new JSONObject();
        root.put(Drink.ROOT, leaf);

        return root.toString();
    }

    public ApiCall create(Drink drink) {
        try {
            HttpPostEntity createDrinkPostEntity = createDrinkPostEntity(drink);
            HttpResponse response = new ApiCallTask().execute(createDrinkPostEntity).get();
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

    private HttpPostEntity createDrinkPostEntity(Drink drink) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(Drink.GENERIC_URL, drinkJSON(drink));
    }

    public List<Drink> getByClub(int clubID) {
        List<Drink> returnList = new ArrayList<Drink>();

        try {
            HttpResponse response = new ApiCallTask().execute(getByClubPostEntity(clubID)).get();
            JSONObject drinksResponse = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 200 && drinksResponse != null) {
                returnList = getDrinkListFromJson(drinksResponse);
            }
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

    private HttpPostEntity getByClubPostEntity(int clubID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(Drink.CLUB_ID, clubID);
        JSONObject root = new JSONObject();
        root.put(Drink.ROOT, leaf);

        return new HttpPostEntity(Drink.GET_BY_CLUB, root.toString());
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
