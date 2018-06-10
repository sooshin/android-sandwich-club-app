package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /** Tag for the log message */
    private static final String TAG = JsonUtils.class.getSimpleName();

    /**
     * Return a {@link Sandwich} Object that can be used to populate the UI by parsing the JSON
     */
    public static Sandwich parseSandwichJson(String json) {
        // If the JSON String is empty or null, then return early
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Try to parse the JSON string. If there's a problem with the way the JSON is formatted,
        // a JSONException exception object will be thrown.
        try {
            // Create a JSONObject from the JSON string
            JSONObject baseJson = new JSONObject(json);

            // Extract the JSONObject associated with the key called "name"
            JSONObject name = baseJson.getJSONObject("name");

            // For a given name, extract the value fro the key called "mainName"
            String mainName = name.getString("mainName");

            // For a given name, extract the JSONArray associated with the key called "alsoKnownAs"
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");

            // Create an empty ArrayList
            List<String> alsoKnownAs = new ArrayList<>();
            // If the JSONArray is not empty, add each element in the JSONArray to the ArrayList
            if (alsoKnownAsArray.length() != 0) {
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
                }
            } else {
                alsoKnownAs = null;
            }

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = baseJson.getString("placeOfOrigin");
            if (placeOfOrigin.isEmpty()) {
                placeOfOrigin = null;
            }

            // Extract the value for the key called "description"
            String description = baseJson.getString("description");

            // Extract the value for the key called "image"
            String imageUrl = baseJson.getString("image");

            // Extract the JSONArray associated with the key "ingredients"
            JSONArray ingredientsArray = baseJson.getJSONArray("ingredients");

            // Create an empty ArrayList
            List<String> ingredients = new ArrayList<>();
            // If the JSONArray is not empty, add each element in the JSONArray to the ArrayList
            if (ingredientsArray.length() != 0) {
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredients.add(ingredientsArray.get(j).toString());
                }
            } else {
                ingredients = null;
            }

            // Return the Sandwich object
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app does not crash.
            e.printStackTrace();
            return null;
        }
    }
}
