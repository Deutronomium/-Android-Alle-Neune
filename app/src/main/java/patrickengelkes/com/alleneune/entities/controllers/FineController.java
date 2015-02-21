package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Fine;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 18/02/15.
 */
public class FineController {
    public static final String TAG = DrinkController.class.getSimpleName();

    @Inject
    public FineController() {
    }

    private String fineJSON(Fine fine) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(Fine.NAME, fine.getName());
        leaf.put(Fine.AMOUNT, fine.getAmount());
        leaf.put(Fine.CLUB_ID, fine.getClubID());
        JSONObject root = new JSONObject();
        root.put(Fine.ROOT, leaf);

        return root.toString();
    }

    public ApiCall create(Fine fine) {
        try {
            HttpPostEntity createFinePostEntity = createFinePostEntity(fine);
            HttpResponse response = new ApiCallTask().execute(createFinePostEntity).get();
            JSONObject createAnswer = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 201 && createAnswer != null) {
                return ApiCall.CREATED;
            } else if (response.getStatusLine().getStatusCode() == 422 && createAnswer != null) {
                Log.e(TAG, "Creating Fine Failed");
                Log.e(TAG, createAnswer.toString());
                return ApiCall.UNPROCESSABLE_ENTITY;
            } else {
                return ApiCall.BAD_REQUEST;
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

        return ApiCall.BAD_REQUEST;
    }

    private HttpPostEntity createFinePostEntity(Fine fine) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(Fine.GENERIC_URL, fineJSON(fine));
    }

    public List<Fine> getByClub(int clubID) {
        List<Fine> returnList = new ArrayList<Fine>();

        try {
            HttpResponse response = new ApiCallTask().execute(getByClubPostEntity(clubID)).get();
            JSONObject fineResponse = new JsonBuilder().execute(response).get();
            if (response.getStatusLine().getStatusCode() == 200 && fineResponse != null) {
                returnList = getFineListFromJson(fineResponse);
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
        leaf.put(Fine.CLUB_ID, clubID);
        JSONObject root = new JSONObject();
        root.put(Fine.ROOT, leaf);

        return new HttpPostEntity(Fine.GET_BY_CLUB, root.toString());
    }

    private List<Fine> getFineListFromJson(JSONObject finesJson) throws JSONException {
        List<Fine> finesList = new ArrayList<Fine>();

        JSONArray fines = finesJson.getJSONArray(Fine.ROOT + "s");
        for (int i = 0; i < fines.length(); i++) {
            JSONObject fineJson = fines.getJSONObject(i);

            int id = fineJson.getInt(Fine.ID);
            String name = fineJson.getString(Fine.NAME);
            double amount = fineJson.getDouble(Fine.AMOUNT);
            int clubID = fineJson.getInt(Fine.CLUB_ID);

            Fine fine = new Fine(id, name, amount, clubID);
            finesList.add(fine);
        }

        return finesList;
    }

    public ApiCall update(HashMap<String, Object> updateMap, Fine fine) {
        try {
            HttpPostEntity updateFineEntity = updatePostEntity(updateMap, fine);
            HttpResponse response = new ApiCallTask().execute(updateFineEntity).get();
            if (response.getStatusLine().getStatusCode() == 200) {
                return ApiCall.UPDATED;
            } else if (response.getStatusLine().getStatusCode() == 422) {
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

    private HttpPostEntity updatePostEntity(HashMap<String, Object> updateMap, Fine fine) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        Iterator iterator = updateMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();

            leaf.put((String)pairs.getKey(), pairs.getValue());
        }
        JSONObject root = new JSONObject();
        root.put(Fine.ROOT, leaf);

        return new HttpPostEntity(Fine.UPDATE + fine.getId(), root.toString());
    }
}
