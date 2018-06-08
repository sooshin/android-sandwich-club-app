package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /** Tag for the log message */
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        // If the JSON String is empty or null, then return early
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject baseJson = new JSONObject(json);

            JSONObject name = baseJson.getJSONObject("name");
            Log.d(TAG, "name: " + name);

            String mainName = name.getString("mainName");
            Log.d(TAG, "mainName: " + mainName);

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");

            List<String> alsoKnownAs = new ArrayList<>();
            if (alsoKnownAsArray.length() != 0) {
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
                }
            } else {
                alsoKnownAs = null;
            }
            Log.d(TAG, "alsoKnownAs: " + alsoKnownAs);

            String placeOfOrigin = baseJson.getString("placeOfOrigin");
            if (placeOfOrigin.isEmpty()) {
                placeOfOrigin = null;
            }
            Log.d(TAG, "placeOfOrigin: " + placeOfOrigin);

            String description = baseJson.getString("description");
            Log.d(TAG, "description: " +description);

            String imageUrl = baseJson.getString("image");
            Log.d(TAG, "imageUrl: " +imageUrl);

            JSONArray ingredientsArray = baseJson.getJSONArray("ingredients");
            Log.d(TAG, "ingredientsArray: " + ingredientsArray);

            List<String> ingredients = new ArrayList<>();
            if (ingredientsArray.length() != 0) {
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredients.add(ingredientsArray.get(j).toString());
                }
            } else {
                ingredients = null;
            }
            Log.d(TAG, "ingredients: " + ingredients);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
